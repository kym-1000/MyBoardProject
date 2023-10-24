<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>footer</title>
        <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/footer.css'/>">
</head>
<body>

<footer>
    <span>Copyright © 2023</span> &nbsp;&nbsp;
    <span><a href="<c:url value='/version'/>" class="footerA">버전 1.05</a></span> &nbsp;&nbsp;
    <span>Java Spring 게시판</span> &nbsp;&nbsp;
    <span>Azure 배포</span> &nbsp;&nbsp;
    <c:if test="${authority eq 0}" >
    <span><a href="<c:url value='/ad/userList'/>" class="footerA">회원목록</a></span>
    </c:if>
</footer>

</body>
</html>
