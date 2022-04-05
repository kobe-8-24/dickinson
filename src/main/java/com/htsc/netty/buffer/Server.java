package com.htsc.netty.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.charset.StandardCharsets;

public class Server {
    public static void main(String[] args) {
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer();
        System.out.println(byteBuf);
        StringBuilder builder = new StringBuilder();
        for (int i=0; i<300;i++) {
            builder.append("a");
        }
        byteBuf.writeBytes(builder.toString().getBytes(StandardCharsets.UTF_8));
        System.out.println(byteBuf);
    }
}
