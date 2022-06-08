package com.etao.mobile.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author jay
 * @since 2022/6/1  14:23
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt;
            String eventType = null;
            switch (event.state()){
                case READER_IDLE:
                    eventType = "read 空闲";
                    break;
                case WRITER_IDLE:
                    eventType = "write 空闲 ";
                    break;
                case ALL_IDLE:
                    eventType = " read and write 空闲 ";
                    break;
            }
            System.out.println(ctx.channel().remoteAddress() + "--over time --" + eventType);
            System.out.println("server handler  ");
            ctx.channel().close();
        }
    }
}
