<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<meta charset="UTF-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>Home</title>
	<base href="/" />
	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/main.css'/>">
</head>
<body>

<script> let msg = "${msg}";</script>
<script src="<c:url value='/resources/js/msg.js'/>"></script>

<jsp:include page="layout/header.jsp"/>

	<main>
		<section>
			<h2>어서오세요!</h2>
		</section>
	</main>


<jsp:include page="layout/footer.jsp"/>

</body>
</html>
