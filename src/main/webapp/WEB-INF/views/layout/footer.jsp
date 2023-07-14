<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>footer</title>
    <%--    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/footer.css'/>">--%>
    <style>
        body {
            padding-bottom: 30px; /* footer의 높이와 같은 값 */
            display: flex;
            flex-direction: column;
        }

        footer {
            flex-shrink: 0;
            display: flex;
            align-items: center;
            justify-content: center;
            position: fixed;
            bottom: 0;
            width: 100%;
            height: 30px;
            line-height: 30px;
            color: white;
            background-color: #555;
        }

        .footerA {
            color: white;
        }
    </style>
</head>
<body>

<footer>
    <span>Copyright © 2023</span> &nbsp;&nbsp;
    <span><a href="<c:url value='/version'/>" class="footerA">버전 1.04</a></span> &nbsp;&nbsp;
    <span>Java Spring 게시판</span> &nbsp;&nbsp;
    <span>Azure 배포</span> &nbsp;&nbsp;
    <c:if test="${authority eq 0}" >
    <span><a href="<c:url value='/ad/userList'/>" class="footerA">회원목록</a></span>
    </c:if>

    <c:if test="${authority eq 1}" >
        <span><a href="#" class="footerA" onclick="return authoritychk()" >회원목록</a></span>
    </c:if>

</footer>

</body>
<script>
    function authoritychk() {
        alert("관리자만 접근 가능합니다.")
        return false;
    }

</script>


</html>
