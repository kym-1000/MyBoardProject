<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
  <meta charset="UTF-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>version</title>
  <style>
    * {
      margin: 0;
      padding: 0;
    }

    body {
      background-color: burlywood;
    }

  </style>
</head>

<body>

<jsp:include page="layout/header.jsp"/>


<h2>버전 1.01</h2>
<p>
  2023-05-25 <br>

  1. 댓글프로필 아이콘 추가 <br>
  2. 회원수정 프로필 파일 미리보기 오류수정 <br>
  3. 프로필 미 등록시 에러 수정
</p>

<h2>버전 1.02</h2>
<p>
  2023-05-29 <br>

  1. 네비영역 디자인 변경 <br>
  2. 네비영역 프로필 사진 추가 <br>
</p>

<h2>버전 1.03</h2>
<p>
  2023-06-11 <br>

  1. 메인페이지 웹 소켓 실시간 채팅 추가
</p>

<jsp:include page="layout/footer.jsp"/>

</body>
</html>
