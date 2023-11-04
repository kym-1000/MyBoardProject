<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<html>
<head>
	<meta charset="UTF-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>Home</title>
	<base href="/" />
	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/main.css'/>">
	<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
	<script src="https://code.jquery.com/jquery-migrate-3.3.2.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.0.0/sockjs.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
	<style>
		#chat-container {
			background-color: #f1f1f1;
			width: 230px;
			height: 450px;
			border-radius: 10px;
			padding: 10px;
			box-shadow: 0 0 5px rgba(0, 0, 0, 0.2);
		}

		#chat-messages {
			width: 220px;
			height: 300px;
			overflow-y: scroll;
			color: #333;
			background-color: #fff;
			padding: 10px;
			border-radius: 5px;
			box-shadow: 0 0 3px rgba(0, 0, 0, 0.1);
		}
	</style>
</head>
<script> let msg = "${msg}";
		 let id = "${id}";
		 let logoutId = "${logout}";
</script>

<body>


<script src="<c:url value='/resources/js/msg.js'/>"></script>

<jsp:include page="layout/header.jsp"/>

	<main>
		<section>
			<h2>어서오세요!</h2>
		</section>
	</main>

<div id="chat-container">
	<h1>실시간 채팅</h1>
	<div style="color: black; width: 200px; height: 25px">
	<i class="fa fa-fw fa-user" ></i><span id="userCount">로그인이 필요합니다.</span>
	</div>

	<div id="chat-messages"></div>
	<c:if test="${id != null}">
	<form id="chat-form">
		<input type="text" id="message-input" placeholder="메시지 입력" required>
		<button type="submit">전송</button>
	</form>
	</c:if>
	<c:if test="${id == null}">
		<a href="<c:url value='/login/loginForm'/>" style="color: black">로그인이 필요합니다.</a>
	</c:if>
</div>

<div style="width: 250px;height: 50px; background-color: white; border-radius: 5px;">
	<p style="color:black; font-size: 10px;">
		관리자 ID : adad11 <br>
		관리자 비밀번호 : 123456@@q
	</p>
</div>

<jsp:include page="layout/footer.jsp"/>

<script>
	if(id !== '' || logoutId !== '') {
		$(function () {
			// let socket = new WebSocket('ws://' + window.location.host + '/chat');
			let socket = new WebSocket('wss://' + window.location.hostname + '/chat');

			socket.onopen = function () {   // 소켓 연결
				console.log('연결 성공');
				if (id != null && id !== '') {
					socket.send(id + '님이 입장하셨습니다.');
				} else {
					socket.send(logoutId + "님이 퇴장하셨습니다.");
				}

				socket.onmessage = function (event) {  // 소켓 메시지 처리
					const message = JSON.parse(event.data);
					if (message.type === 'userCount') {
						$('#userCount').text(message.content + "명"); // 사용자 수 업데이트
					} else if (message.type === 'chat' && logoutId ==='') {
						$('#chat-messages').append(message.content + '<br>'); // 채팅 메시지 업데이트
				 	} else if(message.type === 'chat' && logoutId !==''){
						$('#chat-messages').append(message.content + '<br>'); // 퇴장 메시지
						socket.close();
						location.reload();
					}
				};
			};

			$(document).on('submit', '#chat-form', function (event) {
				if (id != null) {
					event.preventDefault();
					let message = id + ' : ' + $('#message-input').val();
					socket.send(message);
					$('#message-input').val('');
				} else {
					alert("로그인이 필요합니다.");
				}
			});
		});
	}

</script>


</body>
</html>
