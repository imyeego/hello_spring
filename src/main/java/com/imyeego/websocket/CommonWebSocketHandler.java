package com.imyeego.websocket;

import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CommonWebSocketHandler extends AbstractWebSocketHandler {

    private static final String USER_ID = "WEBSOCKET_USERID";
    private static final Map<String, WebSocketSession> users;
    private static CommonWebSocketHandler INSTANCE;

    static {
        users = new HashMap<>();
    }

    public static CommonWebSocketHandler instance() {
        if (INSTANCE == null) {
            synchronized (CommonWebSocketHandler.class) {
                if (INSTANCE == null) {
                    INSTANCE = new CommonWebSocketHandler();
                }
            }
        }

        return INSTANCE;
    }

    public CommonWebSocketHandler() {
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        String userId = session.getId();
        System.out.println("----websocket 建立连接 设备id: " + userId + "----");

        users.put(userId, session); // 使用session.getId可能存在同一个设备多个session的问题，建议在Interceptor的beforeHandshake中设置
        System.out.println("当前连接数:" + users.size());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        System.out.println("---服务器收到文本消息--- " + message.getPayload());

    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
        super.handleBinaryMessage(session, message);
        System.out.println("---服务器收到消息--- " + new String(message.getPayload().array()));

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        if (session.isOpen()) session.close();
        String userId = session.getId();
        users.remove(userId);
        System.out.println("关闭websocket连接，连接数: " + users.size());

    }

    public void sendToUser(String userId, String message) {
        TextMessage textMessage = new TextMessage(message);

        for (String id : users.keySet()) {
            if (userId.equals(id)) {
                try {
                    if (users.get(id).isOpen()) {
                        users.get(id).sendMessage(textMessage);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            break;
        }
    }

    public void sendToUsers(String message) {
        TextMessage textMessage = new TextMessage(message);
        for (String id : users.keySet()) {
            try {
                if (users.get(id).isOpen())
                    users.get(id).sendMessage(textMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
