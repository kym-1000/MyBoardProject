<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<html>
<head>
    <title>서버 에러 발생</title>
    <style>
        body {
            background-color: #f8f9fa;
            font-family: Arial, sans-serif;
        }
        h1 {
            color: #dc3545;
            font-size: 36px;
        }
        p {
            color: #343a40;
            font-size: 18px;
            margin-top: 30px;
        }
    </style>
</head>
<body>
<h1>서버 에러가 발생하였습니다.</h1>
<p>
    발생한 예외: ${pageContext.exception}<br>
    예외 메시지: ${pageContext.exception.message}
</p>
<p>
    5초 뒤에 <a href="${pageContext.request.contextPath}/">메인 페이지</a>로 이동합니다.
</p>
</body>

<script type="text/javascript">
    window.setTimeout(function() {
        location.href = "${pageContext.request.contextPath}/";
    }, 5000);
</script>

</html>