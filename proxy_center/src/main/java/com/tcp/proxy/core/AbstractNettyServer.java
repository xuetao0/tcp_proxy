package com.tcp.proxy.core;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * @ClassName AbstractNettyServer
 * @Description TODO
 * @Author xuetao
 * @Date 2021/2/2 16:58
 **/
public class AbstractNettyServer {

    public static void main(String[] args) {
        start();
    }

    protected static void start() {

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            int port = 10119;
            ServerBootstrap sb = new ServerBootstrap();
            sb.group(group)
                    .localAddress(new InetSocketAddress(port))
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new NettyClientInitializer());
            ChannelFuture future = sb.bind().sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException ignored) {
        } finally {
            group.shutdownGracefully();
        }
    }
}
