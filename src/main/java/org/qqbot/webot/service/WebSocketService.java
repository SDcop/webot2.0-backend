package org.qqbot.webot.service;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

@Component
@ServerEndpoint("/WebSocket")
public class WebSocketService {

    Log log = LogFactory.get();

    @OnOpen
    public void onOpen(Session session) {
        log.info("与SessionId：{}建立连接", session.getId());
    }

    @OnClose
    public void onClose() {
        log.info("WebSocket连接关闭");
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("来自SessionId:{}的消息:{}", session.getId(), message);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("Session:{}的WebSocket发生错误", session.getId(), error);
    }
}