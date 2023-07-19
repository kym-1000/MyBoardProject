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


<h2> 자바 버전 및 사용 Tool</h2>

<p>
  jdk 11 <br>
  spring framework 5.3.10 <br>
  mybatis 3.5.9 <br>
  jQuery 3.6.0<br>
  Intellij 2022-02<br>
</p>


<h2>버전 1.01</h2>
<p>
  2023-06-23 <br>

  1. 댓글프로필 아이콘 추가 <br>
  2. 회원수정 프로필 파일 미리보기 오류수정 <br>
  3. 프로필 미 등록시 에러 수정
</p>

<h2>버전 1.02</h2>
<p>
  2023-06-27 <br>

  1. 네비영역 디자인 변경 <br>
  2. 네비영역 프로필 사진 추가 <br>
</p>

<h2>버전 1.03</h2>
<p>
  2023-07-08 <br>

  1. 회원목록 추가 <br>
  2. 관리자 회원 탈퇴 기능 추가 <br>
  3. 관리자 게시글 삭제 기능 추가

</p>

<h2>버전 1.04</h2>
<p>
  2023-07-19 <br>

  1. 메인페이지 웹 소켓 실시간 채팅 추가 <br>
  2. 공지사항 추가 <br>

</p>

<jsp:include page="layout/footer.jsp"/>

</body>
</html>
