<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="java.net.URLDecoder"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    <link rel="stylesheet" href="<c:url value='/resources/css/register.css'/>">

    <script src="https://code.jquery.com/jquery-3.6.0.js"></script>
    <script src="https://code.jquery.com/jquery-migrate-3.3.2.js"></script>
    <title>Register</title>
    <style>

        body {
            margin: 0;
        }

        footer {
            background-color: black;
        }

    </style>
</head>
<body>

<jsp:include page="../layout/header.jsp"/>

<c:url value="${mode == 'new' ? '/register/join' : '/register/userModify'}" var="url" />

<form  action="${url}" enctype="multipart/form-data" method="POST" ${mode == "new" ? 'onsubmit="return registerFormCheck(this)"' : 'onsubmit="return modFormCheck(this)"'} >
    <div class="title" style="font-weight: bold">${mode=="new" ? "Sign up" : "Edit User"}</div>
    <div id="msg" class="msg"><form:errors path="id"/></div>
    <c:if test="${mode eq 'new'}">
        <label style="margin-bottom: 25px" ><b>아이디</b><button class="idChk"  type="button" id="idChk" value="N" onclick=fn_idChk()>check</button></label>
    <div class="flex-container">
        <input class="input-field" type="text" name="id" id="id" placeholder="5~12자리의 영대소문자와 숫자 조합" value="${user.id}">
        <span id="username-check" class="hidden"><i class="fas fa-check" style="color: black;"></i></span>
    </div>
    </c:if>
    <label><b>비밀번호</b></label>
    <input class="input-field" type="password" name="pwd" placeholder="6~12자리 영대소문자와 숫자와 특문 조합" required>
    <label><b>비밀번호 확인</b></label>
    <div class="flex-container">
    <input class="input-field" type="password" name="pwd-confirm" placeholder="6~12자리 영대소문자와 숫자와 특문 조합" required>
    <button class="hidden"  type="button" id="pwdChk" value="N"  ></button>
    <span id="userpwd-check" class="hidden" ><i class="fas fa-check" style="color: black;" ></i></span>
    </div>
    <label ><b>이름</b></label>
    <input class="input-field" type="text" name="name" placeholder="홍길동" value="${user.name}">
    <label ><b>이메일</b></label>
    <input class="input-field" type="text" name="email" placeholder="example@naver.com" value="${user.email}">
    <label ><b>생일</b></label>
    <input style="height: 40px" class="input-field" type="date" name="birth" id="birth"  placeholder="2020-12-31" value=${userBirth}>
    <label><b>프로필사진</b></label>
<%--    <img id="preview-image" style="height: 50px; width: 50px" src="/fileUpload/${fn:replace(file.save_dir,'\\','/')}/${file.uuid}_${file.file_name}" alt="미리보기">--%>
    <img id="preview-image" style="height: 50px; width: 50px" src="https://myboard.blob.core.windows.net/youngmokboard/${file.uuid}_${file.file_name}" alt="미리보기">

    <input style="height: 40px" class="input-field" type="file" id="file" name="file" accept="image/png, image/jpg, image/jpeg, image/gif" >
    <button class="formButton">${mode=="new" ? "회원가입" : "회원정보수정"}</button>
</form>
<jsp:include page="../layout/footer.jsp"/>

<script type="text/javascript" src="<c:url value='/resources/js/registerForm.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/imgFile.js'/>"></script>

</body>
</html>