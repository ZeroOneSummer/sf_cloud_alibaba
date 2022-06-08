package com.formssi.mall.order.infrastructure.config.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * 拦截器
 */
@Slf4j
@Component
public class WebSocketHandlerInterceptor implements HandshakeInterceptor {

    /**
     * 拦截器可以在 websocket 连接握手阶段做一些校验，还可以存储用户的连接信息
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @param webSocketHandler
     * @param map
     * @return false 拒绝连接，true 则通过
     * @throws Exception
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        log.info("com.formssi.mall.order.infrastructure.config.websocket.DefaultInterceptor.beforeHandshake");
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
        log.info("com.formssi.mall.order.infrastructure.config.websocket.DefaultInterceptor.afterHandshake");
    }
}
