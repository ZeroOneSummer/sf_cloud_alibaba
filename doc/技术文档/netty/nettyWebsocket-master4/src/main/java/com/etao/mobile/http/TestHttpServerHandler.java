package com.etao.mobile.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * @author jay
 * @since 2022/5/31  15:49
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
       if(msg instanceof HttpRequest){
           System.out.println("pipeline hashcode" + ctx.pipeline().hashCode() + " "+ this.hashCode());
           System.out.println("msg " + msg.getClass());
           System.out.println(" " + ctx.channel().remoteAddress());

           HttpRequest httpRequest = (HttpRequest) msg;
           URI uri = new URI(httpRequest.uri());
           if("/favicon.ico".equals(uri.getPath())){
               System.out.println("request favicon.ico");
               return;
           }

           ByteBuf content = Unpooled.copiedBuffer("hello,server服务器", CharsetUtil.UTF_16);
           FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
           response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
           response.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());
           ctx.writeAndFlush(response);
       }
    }
}
