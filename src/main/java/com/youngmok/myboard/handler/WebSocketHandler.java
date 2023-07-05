package com.youngmok.myboard.handler;

import com.google.gson.JsonObject;
import com.youngmok.myboard.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
@RequestMapping("/chat")
public class WebSocketHandler extends TextWebSocketHandler {

    private List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    public WebSocketHandler(){};

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        broadcastUserCount();

    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String content = message.getPayload();
        broadcastChatMessage(content);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        broadcastUserCount();
    }

    private void broadcastUserCount() throws IOException {
        int userCount = sessions.size();
        
        String message = MessageType("userCount", String.valueOf(userCount)); // 사용자 수 메시지 생성

        for (WebSocketSession session : sessions) { // 모든 세션에 메시지 전송
            session.sendMessage(new TextMessage(message));
        }
    }

    private void broadcastChatMessage(String content) throws IOException {
        String message = MessageType("chat", content); // 채팅 메시지 메시지 생성

        for (WebSocketSession session : sessions) { // 모든 세션에 메시지 전송
            session.sendMessage(new TextMessage(message));
        }
    }

    private String MessageType(String type, String content) {
        JsonObject json = new JsonObject();   // 웹소켓 메시지 전송을 위한 json 객체 생성
        json.addProperty("type", type);   // type 설정
        json.addProperty("content", content); // type 설정
        return json.toString();
    }

}

