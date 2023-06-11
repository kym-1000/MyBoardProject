<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>header</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/header.css'/>">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>

<header>
    <nav>
<%--        <div id="mySidenav" class="sidenav">--%>
<%--            <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>--%>


<%--        </div>--%>

        <div class="navbar">
            <c:if test="${pageContext.request.servletPath ne '/WEB-INF/views/home.jsp'}">
                <a class="active" href="<c:url value='/'/>"><i class="fa fa-fw fa-home"></i> 홈</a>
            </c:if>
            <c:if test="${user.id == null}">
                <a href="<c:url value='/login/login'/>"><i class="fa fa-fw fa-user"></i> 로그인</a>
            </c:if>
            <c:if test="${user.id != null}">
                <a href="<c:url value='/login/logout'/>">로그아웃</a>
            </c:if>
            <a href="<c:url value='/register/add'/>">회원가입</a>
            <a href="<c:url value='/board/list'/>">게시판</a>
            <c:if test="${user.id != null}">
                <a href="<c:url value='/register/uesrModify'/>" >회원정보수정</a>
                <a href="<c:url value='/register/unregister'/>" onclick="return unregister()">회원탈퇴</a>
            </c:if>

            <c:if test="${user.id != null}">
            <span style="color: white; text-align: right"><img alt="이미지없음" src="https://myboard.blob.core.windows.net/youngmokboard/${user.profile}" style="width: 25px; height: 25px; border-radius: 50%;">${user.id}님 어서오세요!</span>
            </c:if>

        </div>

    </nav>
</header>

<script>
    function unregister() {
        return confirm("회원탈퇴 하시겠습니까?");
    }
</script>

</body>
</html>