package com.etao.mobile.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * @author jay
 * @since 2022/6/1  10:01
 */
public class NettyByteBuf02 {
    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello,world 你好", Charset.forName("utf-8"));
        if(byteBuf.hasArray()){
            byte[] content = byteBuf.array();
            System.out.println(new String(content, CharsetUtil.UTF_8));
            System.out.println("byteBuf=" + byteBuf);
            System.out.println(byteBuf.arrayOffset());
            System.out.println(byteBuf.readerIndex());
            System.out.println(byteBuf.writerIndex());
            int len = byteBuf.readableBytes();
            System.out.println(len);
            System.out.println(byteBuf.getCharSequence(0,4,Charset.forName("utf-8")));
        }
    }
}
