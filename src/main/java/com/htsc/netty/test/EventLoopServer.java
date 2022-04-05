package com.htsc.netty.test;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class EventLoopServer {
    public static void main(String[] args) throws InterruptedException {
        // BossEventLoop
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        // WorkEventLoop
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();


        // 启动器 负责组装netty组件 启动服务器
       new ServerBootstrap()
               // BossEventLoop WorkEventLoop(selector, thread), group组
               .group(bossGroup, workerGroup)
               // 选择 服务器的 ServerSocketChannel 实现
               .channel(NioServerSocketChannel.class)
               // boss 负责处理连接  worker（child）负责处理读写
               .childHandler(new ChannelInitializer<NioSocketChannel>() {
                   @Override
                   protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new StringDecoder());
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
                                System.out.println(o);
                            }
                        });
                   }
               })
               .bind(8080);
    }
}
