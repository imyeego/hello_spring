package com.imyeego.websocket;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;

public class SpringWebSocketHandler extends TextWebSocketHandler {

    private static final String USER_ID = "WEBSOCKET_USERID";
    private static final Map<String, WebSocketSession> users;

    static {
        users = new HashMap<>();
    }

    public SpringWebSocketHandler() {
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        System.out.println("----websocket 建立连接----");
        String userId = (String) session.getAttributes().get(USER_ID);
        users.put(userId, session);
        System.out.println("当前连接数:" + users.size());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);

    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        if (session.isOpen()) session.close();
        System.out.println("出现异常,关闭websocket连接");
        String userId = (String) session.getAttributes().get(USER_ID);
        users.remove(userId);
    }
}
