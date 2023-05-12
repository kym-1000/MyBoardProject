<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>header</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/header.css'/>">
</head>
<body>

<header>
    <nav>
        <div id="mySidenav" class="sidenav">
            <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
            <c:if test="${pageContext.request.servletPath ne '/WEB-INF/views/home.jsp'}">
                <a href="<c:url value='/'/>">홈</a>
            </c:if>
            <c:if test="${id == null}">
                <a href="<c:url value='/login/login'/>">로그인</a>
            </c:if>
            <c:if test="${id != null}">
                <a href="<c:url value='/login/logout'/>">로그아웃</a>
                <p style="color: white">${id}님 어서오세요!</p>
            </c:if>
            <a href="<c:url value='/register/add'/>">회원가입</a>
            <a href="<c:url value='/board/list'/>">게시판</a>
            <c:if test="${id != null}">
                <a href="<c:url value='/register/uesrModify'/>" >회원정보수정</a>
                <a href="<c:url value='/register/unregister'/>" onclick="return unregister()">회원탈퇴</a>
            </c:if>

        </div>
        <span id="openBtn" onclick="openNav()">&#9776; menu</span>
    </nav>
</header>

<script>
    function openNav() {
        document.getElementById("mySidenav").style.width = "250px";
    }
    function closeNav() {
        document.getElementById("mySidenav").style.width = "0";
    }

    function unregister() {
        return confirm("회원탈퇴 하시겠습니까?");
    }
</script>

</body>
</html>