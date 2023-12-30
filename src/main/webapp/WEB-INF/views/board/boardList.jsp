<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ page session="true"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>boardList</title>

  <link rel="stylesheet" href="<c:url value='/resources/css/boardList.css'/>">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <script src="https://code.jquery.com/jquery-3.6.0.js"></script>
  <script src="https://code.jquery.com/jquery-migrate-3.3.2.js"></script>
  <style>
    footer {
      background-color: black;
    }

    #adBoardDeleteBtn {
      padding: 8px 16px;
      background-color: #f44336;
      color: #fff;
      border: none;
      border-radius: 4px;
      font-size: 14px;
      cursor: pointer;
      transition: background-color 0.3s ease;
    }

    #adBoardDeleteBtn:hover {
      background-color: #d32f2f;
    }
  </style>
</head>
<body>

<jsp:include page="../layout/header.jsp"/>

<c:set var="listUrl" value="/board/list${ph.sc.queryString}" />

<script>
  let msg = "${msg}";
  console.log(msg);
  let listUrl = "${listUrl}";
  console.log(listUrl);
</script>
<script src="<c:url value='/resources/js/msg.js'/>"></script>

<div style="text-align:center; margin-top: 50px">
  <div class="board-container">
    <table>
      <tr>
        <th class="no">번호</th>
        <th class="img">이미지</th>
        <th class="title">제목</th>
        <th class="writer">아이디</th>
        <th class="regdate">등록일</th>
        <th class="viewcnt">조회수</th>
        <th class="like">추천</th>
        <c:if test="${authority eq 0}">
        <th>삭제</th>
        </c:if>
      </tr>

      <%-- 공지사항  --%>
      <c:forEach var="noticeList" items="${noticeList}">
        <tr style="font-weight: bold;">
          <td class="no">공지사항</td>
          <td> <img style="height: 30px; width: 30px" src="<c:url value='/resources/img/board_notice.png' />" alt="No Image"> </td>
          <td class="title"><a href="<c:url value="/board/read${ph.sc.queryString}&bno=${noticeList.bno}"/>"><c:out value="${noticeList.title}"/> &nbsp&nbsp
            <c:if test="${noticeList.comment_cnt ne 0}">
            [${noticeList.comment_cnt}]</a></td>
          </c:if>
          <td class="writer" style="text-align: center;">${noticeList.writer}</td>
          <c:choose>
            <c:when test="${noticeList.reg_date.time >= startOfToday}">
              <td class="regdate"><fmt:formatDate value="${noticeList.reg_date}" pattern="HH:mm" type="time"/></td>
            </c:when>
            <c:otherwise>
              <td class="regdate"><fmt:formatDate value="${noticeList.reg_date}" pattern="yyyy-MM-dd" type="date"/></td>
            </c:otherwise>
          </c:choose>
          <td class="viewcnt" style="text-align: center;">${noticeList.cnt}</td>
          <td class="like" style="text-align: center;">${noticeList.board_like}</td>
          <c:if test="${authority eq 0}">
            <td><input type="checkbox" name="check" value="${noticeList.bno}" style="width: 30px; height: 30px;" ></td>
          </c:if>
        </tr>
      </c:forEach>

      <%-- 일반 게시글 --%>
      <c:forEach var="board" items="${list}">
        <tr>
          <td class="no">${board.board.bno}</td>
          <td>
            <c:choose>
              <c:when test="${empty board.uuid_file_name}">
                <img style="height: 30px; width: 30px" src="<c:url value='/resources/img/board_file_empty.png' />" alt="No Image">
              </c:when>
              <c:otherwise>
                <img style="height: 50px; width: 50px" src="https://myboard.blob.core.windows.net/youngmokboard/${board.uuid_file_name}" alt="Image">
              </c:otherwise>
            </c:choose>
          </td>
          <td class="title"><a href="<c:url value="/board/read${ph.sc.queryString}&bno=${board.board.bno}"/>"><c:out value="${board.board.title}"/> &nbsp&nbsp
            <c:if test="${board.board.comment_cnt ne 0}">
              [${board.board.comment_cnt}]</a></td>
            </c:if>
          <td class="writer" style="text-align: center;">${board.board.writer}</td>
          <c:choose>
            <c:when test="${board.board.reg_date.time >= startOfToday}">
              <td class="regdate"><fmt:formatDate value="${board.board.reg_date}" pattern="HH:mm" type="time"/></td>
            </c:when>
            <c:otherwise>
              <td class="regdate"><fmt:formatDate value="${board.board.reg_date}" pattern="yyyy-MM-dd" type="date"/></td>
            </c:otherwise>
          </c:choose>
          <td class="viewcnt" style="text-align: center;">${board.board.cnt}</td>
          <td class="like" style="text-align: center;">${board.board.board_like}</td>
          <c:if test="${authority eq 0}">
            <td><input type="checkbox" name="check" value="${board.board.bno}" style="width: 30px; height: 30px;" ></td>
          </c:if>
        </tr>
      </c:forEach>
    </table>
    <br>
    <div class="paging-container">
      <div class="paging">
        <c:if test="${totalCnt==null || totalCnt==0}">
          <div> 게시물이 없습니다. </div>
        </c:if>
        <c:if test="${totalCnt!=null && totalCnt!=0}">
          <c:if test="${ph.showPrev}">
            <a class="page" href="<c:url value="/board/list${ph.sc.getQueryString(ph.beginPage-1)}"/>">&lt;</a>
          </c:if>
          <c:forEach var="i" begin="${ph.beginPage}" end="${ph.endPage}">
            <a class="page ${i==ph.sc.page? "paging-active" : ""}" href="<c:url value="/board/list${ph.sc.getQueryString(i)}"/>">${i}</a>
          </c:forEach>
          <c:if test="${ph.showNext}">
            <a class="page" href="<c:url value="/board/list${ph.sc.getQueryString(ph.endPage+1)}"/>">&gt;</a>
          </c:if>
        </c:if>
      </div>
    </div>

    <c:if test="${authority eq 0}">
      <button  id="adBoardDeleteBtn">삭제</button>
    </c:if>

    <div class="search-container">

      <form action="<c:url value="/board/list"/>" class="search-form" method="get">
        <select class="search-option" name="option">
          <option value="A" ${ph.sc.option=='A' || ph.sc.option=='' ? "selected" : ""}>제목+내용</option>
          <option value="T" ${ph.sc.option=='T' ? "selected" : ""}>제목만</option>
          <option value="W" ${ph.sc.option=='W' ? "selected" : ""}>작성자</option>
        </select>

        <select class="search-option" name="sortoption">
          <option value="D" ${ph.sc.sortoption=='D' || ph.sc.sortoption=='' ? "selected" : ""}>날짜순</option>
          <option value="C" ${ph.sc.sortoption=='C' ? "selected" : ""}>조회수순</option>
          <option value="L" ${ph.sc.sortoption=='L' ? "selected" : ""}>추천순</option>
        </select>

        <input type="text" name="keyword" class="search-input" type="text" value="${ph.sc.keyword}" placeholder="검색어를 입력해주세요">
        <input type="submit" class="search-button" value="검색">
      </form>
      <c:if test="${id != null}">
        <button id="writeBtn" class="btn-write" onclick="location.href='<c:url value="/board/write"/>'"><i class="fa fa-pencil"></i> 글쓰기</button>
      </c:if>
      <c:if test="${id == null}">
        <button id="loginBtn" class="btn-write" ><i class="fas fa-pen"></i> 글쓰기</button>
      </c:if>

    </div>


  </div>
</div>

<jsp:include page="../layout/footer.jsp"/>

<script>
  $("#loginBtn").on("click", function(){
    alert("로그인이 필요합니다");
    location.href="<c:url value='/login/login?toURL=/board/write'/>";
  });

  $("#adBoardDeleteBtn").on("click",function(){
    if(confirm("해당 게시글들을 삭제하시겠습니까?")){

      let deleteList = [];

      $("input[name='check']:checked").each(function() {
        deleteList.push($(this).val());
      });

      let DeleteListDTO = {
        deleteList: deleteList
      };

      console.log(DeleteListDTO);

      $.ajax({
        type: "POST",
        url: "/ad/boardDelete",
        contentType: "application/json",
        data: JSON.stringify(DeleteListDTO),
        success: function(response) {
          alert("해당 게시글들을 삭제하였습니다.");
          window.location.href = listUrl;
        },
        error: function() {
          alert("게시글 삭제를 실패하였습니다.");
        }
      });
    } else {
      alert("삭제가 취소되었습니다");
    }
  });

</script>


</body>
</html>