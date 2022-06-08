package com.formssi.mall.order.infrastructure.config.websocket;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Component
@Slf4j
@ServerEndpoint("/webSocket/{userId}/{loginType}")
public class WebSocketServer {


    private static AtomicInteger maximumConnection = new AtomicInteger(0);

    public static HashBasedTable<String, String, Session> result = HashBasedTable.create();


    @OnOpen
    public synchronized void onOpen(@PathParam("userId") String userId,
                                    @PathParam("loginType") String loginType,
                                    Session session) {
        Assert.isNull(result.get(userId, loginType), () -> "用户已建立连接:" + userId);
        send(userId,loginType,()->String.format("用户%s通过%s登录系统",userId,loginType),true);
        result.put(userId, loginType, session);
        maximumConnection.addAndGet(1);
        log.info("webSocket用户id:{},登录类型:{} 连接成功，当前连接数:{}", userId, loginType,maximumConnection.get());
    }



    @OnClose
    public void onClose(@PathParam("userId") String userId,
                        @PathParam("loginType") String loginType) {
        result.remove(userId, loginType);
        maximumConnection.decrementAndGet();
        log.info("webSocket用户id:{},登录类型:{}下线,当前连接数:{}", userId, loginType, maximumConnection.get());
    }



    @OnMessage
    public void onMessage(String message,
                          Session session,
                          @PathParam("userId") String userId,
                          @PathParam("loginType") String loginType) {
        log.info("webSocket用户收到来自用户id为：{} 的消息：{}", userId, message);
        send(userId,loginType,()->message,true);
    }


    @OnError
    public void onError(Session session,
                        Throwable error,
                        @PathParam("userId") String userId) {
        log.info("webSocket用户用户id为：{}的连接发送错误", userId);
        error.printStackTrace();
    }



    /**
     *
     * @param userId 用户
     * @param loginType 登录系统各类型
     * @param msg 发送消息
     * @param sendAll 是否给当前用户的所有客户端发送短信
     */
    public void send(String userId,String loginType, Supplier<String> msg,Boolean sendAll){
        Map<String, Session> webSocketSessionMap = Optional.ofNullable(userId).map(uId -> result.row(userId)).orElse(Maps.newHashMap());
        try {
            //给自己的客户端发送
            if (!sendAll){
                Session sendSession = webSocketSessionMap.get(loginType);
                sendSession.getBasicRemote().sendText(msg.get());
                return;
            }
            //给其他客户端发送
            for (Map.Entry<String, Session> entry: webSocketSessionMap.entrySet()){
                Session wbSession = entry.getValue();
                if (entry.getKey().equals(loginType)){
                    continue;
                }
                wbSession.getBasicRemote().sendText(msg.get());
            }
        }catch (IOException e){
            log.error("send error:e{}",e);
        }

    }
}
