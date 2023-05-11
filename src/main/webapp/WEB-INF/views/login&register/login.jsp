<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false"%>
<%@ page import="java.net.URLDecoder"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>login</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/login.css'/>">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css"/>
    <script src="https://code.jquery.com/jquery-1.11.3.js"></script>
    <style>
        footer {
            background-color: black;
        }
        #openBtn {
            background-color: black;
        }
    </style>
</head>
<body>

<jsp:include page="../layout/header.jsp"/>

<form id="inputForm" action="<c:url value="/login/login"/>" method="post" onsubmit="return formCheck(this);">
    <h3 id="title">Login</h3>
    <div id="msg">
        <c:if test="${not empty param.msg}">
            <i class="fa fa-exclamation-circle"> ${URLDecoder.decode(param.msg)}</i>
        </c:if>
    </div>
    <input type="text" name="id" value="${cookie.id.value}" placeholder="아이디 입력" autofocus>
    <input type="password" name="pwd" placeholder="비밀번호">
    <input type="hidden" name="toURL" value="${param.toURL}">
    <button class="formButton">로그인</button>
    <div>
        <label><input type="checkbox" name="rememberId" value="on" ${empty cookie.id.value ? "":"checked"}> 아이디 기억</label> |
        <a id="myBtn">비밀번호찾기</a> |
        <a href="<c:url value='/register/add'/>" >회원가입</a>
    </div>
</form>

<!-- The Modal -->
<div id="myModal" class="modal">

    <!-- Modal content -->
    <div class="modal-content">
        <span class="close">&times;</span>
        <div id="serForm">
        <h2>비밀번호를 잊으셨나요? 찾으실 계정정보를 입력해주세요.</h2>

        <input type="text" name="id" id="userId" placeholder="아이디 입력">
        <input type="text" name="name" id="userName" placeholder="이름 입력">
        <input type="email" name="email" id="userEmail" placeholder="이메일 입력">
        <button id="pwdSer" class="formButton">계정정보 전송</button>

        </div>
        <div id="rePwdDiv" STYLE="display: none">
            <h2>새로운 비밀번호를 입력해주세요</h2>
            <label><b>비밀번호</b></label>
            <input STYLE="display: none" id="reId"  value="" >
            <input class="input-field" type="password" id="rePwd" placeholder="6~12자리의 영대소문자와 숫자 조합" required>
            <label><b>비밀번호 확인</b></label>
            <input class="input-field" type="password" id="rePwd-confirm" placeholder="6~12자리의 영대소문자와 숫자 조합" required>
            <input class="hidden" id="pwdChk" value="N" STYLE="display: none">
            <span id="userpwd-check" STYLE="display: none" ><i class="fas fa-check" ></i></span>
            <button id="rePwdSubmit" class="formButton">비밀번호 변경</button>
        </div>
    </div>

</div>

<jsp:include page="../layout/footer.jsp"/>

<script>
    const pwdField = $("#rePwd");
    const pwdConfirmField = $("#rePwd-confirm");
    const checkSpan = $('#userpwd-check');
</script>

<script type="text/javascript" src="<c:url value='/resources/js/login.js'/>"></script>
</body>
</html>