<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="false" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>클라이언트 에러 발생</title>
    <style>
        body {
            background-color: #f8f9fa;
            font-family: Arial, sans-serif;
            text-align: center;
            padding: 100px;
        }
        h1 {
            color: #dc3545;
            font-size: 36px;
            margin-bottom: 20px;
        }
        p {
            color: #343a40;
            font-size: 18px;
            margin-top: 10px;
        }
        a {
            text-decoration: none;
            color: #007bff;
            font-weight: bold;
        }
    </style>
</head>
<body>
<h1>잘못된 요청입니다.</h1>
<p>발생한 예외: ${pageContext.exception}</p>
<p>예외 메시지: ${pageContext.exception.message}</p>
<p>5초 후에 <a href="${pageContext.request.contextPath}/">메인 페이지</a>로 이동합니다.</p>

<script type="text/javascript">
    setTimeout(function() {
        location.href = "${pageContext.request.contextPath}/";
    }, 5000);
</script>
</body>
</html>