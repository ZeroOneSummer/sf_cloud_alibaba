package com.formssi.mall.order.infrastructure.config.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * 事件处理
 */
@Slf4j
@Component
public class WebSocketOmsHandler implements WebSocketHandler {

    /**
     * 建立连接
     * @param webSocketSession
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        //缓存用户信息等等
        log.info("com.formssi.mall.order.infrastructure.config.websocket.DefaultHandler.afterConnectionEstablished");
    }

    /**
     *接收消息
     * @param webSocketSession
     * @param webSocketMessage
     * @throws Exception
     */
    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        log.info("com.formssi.mall.order.infrastructure.config.websocket.DefaultHandler.handleMessage");
    }

    /**
     * 连接异常
     * @param webSocketSession
     * @param throwable
     * @throws Exception
     */
    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        // 清除用户缓存信息
        log.info("com.formssi.mall.order.infrastructure.config.websocket.DefaultHandler.handleMessage");
    }

    /**
     * 关闭连接
     * @param webSocketSession
     * @param closeStatus
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        // 清除用户缓存信息
        log.info("com.formssi.mall.order.infrastructure.config.websocket.DefaultHandler.handleMessage");
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
