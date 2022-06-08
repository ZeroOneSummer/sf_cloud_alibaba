**接上篇继续完善WebSocket，那我们开始：**
#####WebSocket依赖注入
```
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-websocket</artifactId>
        </dependency>
```
#####WebSocket配置类
```
/**
 * WebSocket配置类
 */
@EnableWebSocket
@Configuration
public class WebSocketServerConfig implements WebSocketConfigurer {


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(new WebSocketOmsHandler(),"/webSocket").addInterceptors(new WebSocketHandlerInterceptor()).setAllowedOrigins("*").withSockJS();
    }

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {

        return new ServerEndpointExporter();
    }

}

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

```
***(我的拦截器和事件处理未生效，还暂时不清楚原因在哪，晚点再研究，这里暂时不影响WebSocket使用)*
#####WebSocketService

```

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
```
#####通过PostMan模拟前端请求
**按顺序通过三个请求模拟一个用户分别从H5，PC，小程序登录：**
- ws://127.0.0.1:18086/webSocket/高先生/H5端
- ws://127.0.0.1:18086/webSocket/高先生/PC端
- ws://127.0.0.1:18086/webSocket/高先生/小程序端
先看看服务器日志：三个请求都登录成功，这个时候三个请求都还一直保持着长链接，未断开。

![H5收到的通知.png](https://upload-images.jianshu.io/upload_images/28076536-60e0efb07f666878.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![PC收到的通知.png](https://upload-images.jianshu.io/upload_images/28076536-30ed8f353ee634a1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![小程序收到的通知.png](https://upload-images.jianshu.io/upload_images/28076536-16c3b4ad3efc4ce2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

因为H5是最先登录，所以后面的PC端登录和小程序登录都收到了消息；PC端后登录，PC端只能收到小程序登录信息，小程序是最后登录的，所以小程序这里没收到其他客户端的任何信息；
#####到这里基本上应该全部结束了，我想换个场景来使用webSocket
**需求背景：**当某个用户点击了下载订单，我们不做实时下载，而是通过webSocket异步通知前端；
**需求分析：**要异步推送要么用多线程，要么用中间件，正好现在服务器有现成的Rocketmq服务器，那就用MQ做推送；
```
        <dependency>
            <groupId>org.apache.rocketmq</groupId>
            <artifactId>rocketmq-spring-boot-starter</artifactId>
            <version>2.0.3</version>
        </dependency>
```
**Controller、Service实现**
```
    @GetMapping("/webSocket/download")
    public CommonResp download(@PathParam("userId") String userId,
                               @PathParam("loginType") String loginType){
        return iomsOrderItemService.download(userId,loginType);
    }

    @Override
    public CommonResp download(String userId, String loginType) {
        orderProducer.send(userId,loginType);
        return CommonResp.ok();
    }
```
**MQ:Producer、Consumer实现**
```
@Component
public class OrderProducer {

    @Value("${rocketmq.oms-order-topic}")
    private String omsOrderTopic;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public void send(String userId,String loginType){
        OrderConsumerVo orderConsumerVo = OrderConsumerVo.builder().userId(userId).loginType(loginType).build();
        SendResult sendResult = rocketMQTemplate.syncSend(omsOrderTopic, orderConsumerVo);
        Optional.ofNullable(sendResult).map(SendResult::getSendStatus).filter(val-> SendStatus.SEND_OK.equals(val)).orElseThrow(()->new IllegalStateException("订单下载消费异常"));
    }
}

/**
 * 配置文件参考：RocketMQProperties
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = "${rocketmq.oms-order-topic}",consumerGroup = "${rocketmq.consumer-group}")
public class OrderConsumer implements RocketMQListener<OrderConsumerVo> {

    @Autowired
    private WebSocketServer webSocketServer;

    @Override
    public void onMessage(OrderConsumerVo message) {
        log.info("OrderConsumer.onMessage:{}",message);

        //下载逻辑
        System.out.println("订单下载成功");
        //拿到用户ID和客户端类型通知前端
        webSocketServer.send(message.getUserId(),message.getLoginType(),()->"订单下载完成",false);
    }
}

-- 配置文件
rocketmq:
  oms-order-topic: Pay_Topic
  consumer-group: Pay_Topic_Group
  name-server: 10.207.0.164:9876
  producer:
    group: Order_Group_01
```
#####客户端测试
- 浏览器访问：[http://127.0.0.1:18086/oms/webSocket/download?userId=高先生&loginType=PC端](http://127.0.0.1:18086/oms/webSocket/download?userId=高先生&loginType=PC端)
![模拟页面请求下载.png](https://upload-images.jianshu.io/upload_images/28076536-fab9424e04af27d6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![PC端收到消息.png](https://upload-images.jianshu.io/upload_images/28076536-cfbb71aa4ef70308.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
测试完成后，下线所有用户：
![客户端下线.png](https://upload-images.jianshu.io/upload_images/28076536-782415e95863f711.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

目前还遗留一个问题：就是明明配置了WebSocketConfigurer ，就是没生效，后面再研究吧!