package com.tcp.proxy.core;

import io.netty.handler.codec.http.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * @ClassName NioRedirectServer
 * @Description TODO
 * @Author xuetao
 * @Date 2021/2/3 11:46
 **/
public class NioRedirectServer extends AbstractNioServer {
    public NioRedirectServer() {
        startServer();
    }

    @Override
    protected int getThreadSize() {
        return 1;
    }

    @Override
    protected int getPort() {
        return 10110;
    }

    @Override
    protected void handleReadable(SelectionKey key) {
        SocketChannel sc = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024 * 2);
        int readCount = -1;
        try {
            readCount = sc.read(buffer);
        } catch (IOException ignored) {
        }
        if (readCount < 0) {
            key.cancel();
            try {
                sc.close();
            } catch (IOException ignored) {
            }
        }
        if (readCount > 0) {
            try {
                buffer.flip();
                byte[] bytes = new byte[buffer.remaining()];
                buffer.get(bytes);
                String x = new String(bytes, "UTF-8");
                System.out.println(x);
                System.out.println("=========================over======================================");
                String s = x + " ack ";
                byte[] bytes1 = s.getBytes();
                ByteBuffer bf=ByteBuffer.allocate(bytes1.length);
                bf.put(bytes1);
                bf.flip();
                sc.write(bf);
            } catch (IOException ignored) {
            }
        }
    }
}
