package com.imyeego.websocket;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SpringWebSocketHandler extends TextWebSocketHandler {

    private static final String USER_ID = "WEBSOCKET_USERID";
    private static final Map<String, WebSocketSession> users;
    private static SpringWebSocketHandler INSTANCE;

    static {
        users = new HashMap<>();
    }

    public static SpringWebSocketHandler instance() {
        if (INSTANCE == null) {
            synchronized (SpringWebSocketHandler.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SpringWebSocketHandler();
                }
            }
        }

        return INSTANCE;
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
        System.out.println("---服务器收到消息--- " + message.toString());
//        if(message.getPayload().startsWith("#anyone#")){ //单发某人
//
//            sendToUser((String)session.getAttributes().get(USER_ID), new TextMessage("服务器单发：" +message.getPayload())) ;
//
//        }else if(message.getPayload().startsWith("#everyone#")){
//
//            sendToUsers("服务器群发：" +message.getPayload());
//
//        }
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
