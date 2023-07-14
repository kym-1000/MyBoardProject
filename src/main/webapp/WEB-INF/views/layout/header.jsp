<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>header</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/header.css'/>">
<%--    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">--%>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>

<header>
    <nav>
        <div class="navbar">
            <c:if test="${pageContext.request.servletPath ne '/WEB-INF/views/home.jsp'}">
                <a class="active" href="<c:url value='/'/>"><i class="fa fa-fw fa-home"></i> 홈</a>
            </c:if>
            <c:if test="${id == null}">
                <a href="<c:url value='/login/login'/>" ><i class="fa fa-fw fa-user"></i> 로그인</a>
            </c:if>
            <c:if test="${id != null}">
                <a href="<c:url value='/login/logout'/>" ><i class="fas fa-user-xmark"></i>로그아웃</a>
            </c:if>
            <a href="<c:url value='/register/add'/>"><i class="fas fa-user-pen"></i>회원가입</a>
            <a href="<c:url value='/board/list'/>"><i class="fas fa-clipboard-list"></i> 게시판</a>
            <c:if test="${id != null && authority eq 1}">
                <a href="<c:url value='/register/uesrModify'/>" ><i class="fas fa-user-gear"></i>회원정보수정</a>
                <a href="<c:url value='/register/unregister'/>" onclick="return unregister()"><i class="fas fa-user-slash"></i>회원탈퇴</a>
            </c:if>

            <c:if test="${id != null}">
                <p style="color: white; text-align: right; display: flex; align-items: center;  margin: 0; padding: 0; line-height: 1;;">
                    <c:choose>
                        <c:when test="${profile != null}">
                            <img alt="이미지깨짐" class="profileImg" src="https://myboard.blob.core.windows.net/youngmokboard/${profile}" >
                        </c:when>
                        <c:otherwise>
                            <img alt="이미지깨짐" class="profileImg" src="<c:url value='/resources/img/user.png' />" >
                        </c:otherwise>
                    </c:choose>
                        ${id}님 어서오세요!
                </p>
            </c:if>

            <c:if test="${authority eq 0}">
                <span style="color:white;"> 관리자 계정 </span>
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