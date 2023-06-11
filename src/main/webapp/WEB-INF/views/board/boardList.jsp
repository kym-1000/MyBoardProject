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
  <script src="https://code.jquery.com/jquery-1.11.3.js"></script>
  <style>
    footer {
      background-color: black;
    }

  </style>
</head>
<body>

<jsp:include page="../layout/header.jsp"/>

<script> let msg = "${msg}";</script>
<script src="<c:url value='/resources/js/msg.js'/>"></script>

<div style="text-align:center; margin-top: 50px">
  <div class="board-container">
    <table>
      <tr>
        <th class="no">번호</th>
        <th class="title">제목</th>
        <th class="writer">아이디</th>
        <th class="regdate">등록일</th>
        <th class="viewcnt">조회수</th>
        <th class="like">추천수</th>
      </tr>
      <c:forEach var="board" items="${list}">
        <tr>
          <td class="no">${board.bno}</td>
          <td class="title"><a href="<c:url value="/board/read${ph.sc.queryString}&bno=${board.bno}"/>">${board.title} &nbsp&nbsp
            <c:if test="${board.comment_cnt ne 0}">
              [${board.comment_cnt}]</a></td>
            </c:if>
          <td class="writer" style="text-align: center;">${board.writer}</td>
          <c:choose>
            <c:when test="${board.reg_date.time >= startOfToday}">
              <td class="regdate"><fmt:formatDate value="${board.reg_date}" pattern="HH:mm" type="time"/></td>
            </c:when>
            <c:otherwise>
              <td class="regdate"><fmt:formatDate value="${board.reg_date}" pattern="yyyy-MM-dd" type="date"/></td>
            </c:otherwise>
          </c:choose>
          <td class="viewcnt" style="text-align: center;">${board.cnt}</td>
          <td class="like" style="text-align: center;">${board.board_like}</td>
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
      <c:if test="${sessionScope.id != null}">
        <button id="writeBtn" class="btn-write" onclick="location.href='<c:url value="/board/write"/>'"><i class="fa fa-pencil"></i> 글쓰기</button>
      </c:if>
      <c:if test="${sessionScope.id == null}">
        <button id="loginBtn" class="btn-write" ><i class="fa fa-pencil"></i> 글쓰기</button>
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
</script>


</body>
</html>