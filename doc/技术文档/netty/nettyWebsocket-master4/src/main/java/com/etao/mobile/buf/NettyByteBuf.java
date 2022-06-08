package com.etao.mobile.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author jay
 * @since 2022/6/1  9:48
 */
public class NettyByteBuf {
    public static void main(String[] args) {
        //byte[10]
        ByteBuf buffer = Unpooled.buffer(10);
        for(int i=0;i<10;i++){
            buffer.writeByte(i);
        }
        //
        for(int i=0;i<buffer.capacity();i++){
            System.out.println(buffer.getByte(i));
        }
        for(int i = 0 ;i<buffer.capacity();i++){
            System.out.println(buffer.readByte());
        }
    }
}
