<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>회원목록</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/boardList.css'/>">
    <script src="https://code.jquery.com/jquery-1.11.3.js"></script>

</head>
<style>

    body {
        margin: 0;
    }

    #administratorArea {
        text-align: center;

    }

    #userList {
        width: 800px;
        margin-left: 25%;
    }

    .userDelete {
        padding: 8px 16px;
        background-color: #f44336;
        color: #fff;
        border: none;
        border-radius: 4px;
        font-size: 14px;
        cursor: pointer;
        transition: background-color 0.3s ease;
    }

    .userDelete:hover {
        background-color: #d32f2f;
    }

</style>
<body>

<jsp:include page="layout/header.jsp"/>

<c:set var="content" value="${content}"/>

<div id="administratorArea">
    <h1> 관리자 ${id}님 어서오세요!</h1>
    <span><a href="/ad/userList">회원목록</a></span>

        <div id="userList">
            <table style="text-align: center">
                <tr>
                    <th>ID</th>
                    <th>e-mail</th>
                    <th>가입날짜</th>
<%--                    <th>게시글보기</th>--%>
                    <th>회원 탈퇴</th>
                </tr>
                <c:forEach var="user" items="${userList}">
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.email}</td>
                        <td>${user.reg_date}</td>
                        <td><button type="button" class="userDelete" data-value="${user.id}" >탈퇴</button></td>
                    </tr>
                </c:forEach>
            </table>
        </div>

</div>

<jsp:include page="layout/footer.jsp"/>

<script>

    $('.userDelete').click(function(event) {
        let deleteId = $(this).data('value');

        console.log(deleteId);

        if(confirm("해당회원을 탈퇴시키겠습니까?")){

            $.ajax({
                type:'DELETE',
                url: "/ad/userDelete/"+deleteId,
                success : function(result){
                    alert("해당회원이 탈퇴되었습니다.");
                    window.location.href = "/ad/userList";
                },
                error   : function(){ alert("error") }
            });
        } else {
            alert("회원탈퇴가 취소되었습니다");
        }

    });


</script>


</body>
</html>
