<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page session="true"%>
<c:set var="loginId" value="${sessionScope.id}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>boardDetail</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <script src="https://code.jquery.com/jquery-1.11.3.js"></script>
  <!-- CSS only -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
  <!-- JavaScript Bundle with Popper -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
  <link rel="stylesheet" href="<c:url value='/resources/css/boardDetail.css'/>">
</head>
<body>
<jsp:include page="../layout/header.jsp"/>

<c:set var="modifyUrl" value="/board/modify${searchCondition.queryString}" />
<c:set var="deleteUrl" value="/board/boardDelete${searchCondition.queryString}"/>
<c:set var="listUrl" value="/board/list${searchCondition.queryString}" />

<script>
let msg = "${msg}";
let modifyUrl = "${modifyUrl}";
let deleteUrl = "${deleteUrl}";
let listUrl = "${listUrl}";
</script>

<script src="<c:url value='/resources/js/msg.js'/>"></script>

<div class="container">
  <h2 class="writing-header"> 게시판 ${mode=="new" ? "글쓰기" : "읽기"}</h2>
  <form id="form" class="frm" action="<c:url value="/board/write"/>" enctype="multipart/form-data" method="post">
    <input type="hidden" name="bno" value=${Board.board.bno}>
    <input name="title" type="text" value="${Board.board.title}" placeholder="  제목을 입력해 주세요." ${mode=="new" ? "" : "readonly='readonly'"} required><br>
    <div id="mainContent">
      <textarea name="content" rows="20" id="mainText" placeholder="내용을 입력해 주세요." ${mode=="new" ? "" : "readonly='readonly'"} required>${Board.board.content}</textarea>
      <c:if test="${fList ne null}">
        <c:forEach items="${fList}" var="fvo">
<%--      <img id="image" src="https://youngmokfile.blob.core.windows.net/youngmokboard/${fList[0].uuid}_${fList[0].file_name}" alt="" style="display:none; width: 300px; height: 250px;">--%>
      <img id="image" src="/fileUpload/${fn:replace(fList[0].save_dir,'\\','/')}/${fList[0].uuid}_${fList[0].file_name}" alt="" style="display:none; width: 300px; height: 250px;">
        </c:forEach>
      </c:if>
    </div>

      <div id="modeDiv" ${mode eq 'new' ? '' : 'style="display: none;"'}>
<%--      <img id="preview-image" style="height: 50px; width: 50px" src="https://youngmokfile.blob.core.windows.net/youngmokboard/${fList[0].uuid}_${fList[0].file_name}" alt="미리보기">--%>
      <img id="preview-image" style="height: 50px; width: 50px" src="/fileUpload/${fn:replace(fList[0].save_dir,'\\','/')}/${fList[0].uuid}_${fList[0].file_name}" alt="미리보기">
    <label>이미지 첨부파일</label>
    <input style="height: 100px"  type="file" id="files" name="files" accept="image/png, image/jpg, image/jpeg, image/gif" onchange="previewImage(event)"><br>
        <button type="submit" id="writeBtn" class="btn btn-write"><i class="fa fa-pencil"></i>게시글등록</button>
      </div>

    <c:if test="${Board.board.writer eq loginId}">
      <button type="button" id="modifyBtn" class="btn btn-modify"><i class="fa fa-edit"></i> 수정</button>
      <button type="button" id="deleteBtn" class="btn btn-delete"><i class="fa fa-trash"></i> 삭제</button>
    </c:if>
    <button type="button" id="listBtn" class="btn btn-list"><i class="fa fa-bars"></i> 목록</button>
  </form>
</div>

<div id="space"></div>

<section class="mb-5">
  <div class="card bg-light">
    <div class="card-body">
      <!-- Comment form-->
      <c:if test="${loginId != null && mode != 'new'}">
      <div class="mb-4">작성자: ${loginId}</div>
      <div class="mb-4">
        <textarea class="form-control" rows="3" placeholder="이 게시글에 대한 당신의 의견을 들려주세요." id="cmtText"></textarea>
          <button type="button" id="cmtPostBtn" class="btn btn-primary">댓글등록</button>
          <input type="hidden" id="cmtWriter" value="작성자">
          <input type="hidden" id="cmtimage" value="이미지파일">
      </div>
      </c:if>
      <!-- Comment with nested comments-->
        <div class="d-flex" id="Comment">
        </div>
    </div>
  </div>
</section>

<div id="replyForm" style="display:none">
  <div class="mb-4">작성자: ${loginId}</div>
  <div class="mb-4">
    <textarea class="form-control" rows="3" placeholder="이 댓글에 대한 당신의 의견을 들려주세요." id="replyText"></textarea>
    <button type="button" id="cmtReplybtn" class="btn btn-primary">답글등록</button>
  </div>
</div>

<jsp:include page="../layout/footer.jsp"/>

<script type="text/javascript" src="<c:url value='/resources/js/boardDetail.js'/>"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/boardComment.js"></script>

<script>
  const bnoVal = '<c:out value="${Board.board.bno}" />';
  const login = "${sessionScope.id}";
  console.log(bnoVal+" : "+login);

  getCommentList(bnoVal,login);

</script>



</body>
</html>