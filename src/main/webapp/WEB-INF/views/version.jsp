<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>버전 정보</title>
<link rel="stylesheet" href="<c:url value="/resources/css/version.css"/>">
</head>
<body>
  <jsp:include page="layout/header.jsp"/>

<div class="container">
  <h2>자바 버전 및 도구 정보</h2>
  <p class="version-info">
    JDK 11 <br>
    스프링 프레임워크 5.3.10 <br>
    마이바티스 3.5.9 <br>
    jQuery 3.6.0<br>
    IntelliJ 2022-02<br>
    코드 저장소: <a href="https://github.com/kym-1000/MyBoardProject" target="_blank">GitHub</a><br>
  </p>

  <h2>버전 1.01</h2>
  <p class="version-info">
    날짜: 2023-06-23 <br>
    1. 댓글에 프로필 아이콘 추가 <br>
    2. 회원 프로필 수정에서 프로필 사진 미리보기 오류 수정 <br>
    3. 프로필 사진이 등록되지 않았을 때 오류 수정 <br>
  </p>

  <h2>버전 1.02</h2>
  <p class="version-info">
    날짜: 2023-06-27 <br>
    1. 네비게이션 영역 디자인 변경 <br>
    2. 네비게이션 영역에 프로필 사진 추가 <br>
  </p>

  <h2>버전 1.03</h2>
  <p class="version-info">
    날짜: 2023-07-08 <br>
    1. 회원 목록 추가 <br>
    2. 관리자가 회원을 탈퇴시킬 수 있는 기능 추가 <br>
    3. 관리자가 게시글을 삭제할 수 있는 기능 추가 <br>
  </p>

  <h2>버전 1.04</h2>
  <p class="version-info">
    날짜: 2023-07-19 <br>
    1. 메인 페이지에 실시간 웹 소켓 채팅 추가 <br>
    2. 공지사항 추가 <br>
  </p>

  <h2>버전 1.05</h2>
  <p class="version-info">
    날짜: 2023-09-16 <br>
    1. 게시글 이미지 파일 미리보기 추가<br>
  </p>
</div>

  <jsp:include page="layout/footer.jsp"/>
</body>
</html>