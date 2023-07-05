<%@ page language="java" contentType="text/html; charset=UTF-8"%>
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
    </style>
</head>
<body>

<footer>
    <span>Copyright © 2023</span> &nbsp;&nbsp;
    <span><a href="<c:url value='/version'/>" style="color: white">버전 1.03</a></span> &nbsp;&nbsp;
    <span>Java Spring 게시판</span> &nbsp;&nbsp;
    <span>Azure 배포</span> &nbsp;&nbsp;
</footer>

</body>
</html>