package com.tcp.proxy.center;

import com.tcp.proxy.core.AbstractNioServer;
import com.tcp.proxy.local.LocalServer;
import com.tcp.proxy.local.NioLocalServer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;


/**
 * @ClassName CenterServer
 * @Description TODO
 * @Author xuetao
 * @Date 2021/2/1 10:10
 **/
public class NioCenterServer extends AbstractNioServer implements CenterServer {
    private final LocalServer localServer;

    public NioCenterServer() {
        localServer = new NioLocalServer();
        startServer();
    }
    @Override
    protected int getThreadSize() {
        return 1;
    }

    @Override
    protected int getPort() {
        return 9182;
    }

    @Override
    protected void handleReadable(SelectionKey key) {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024 * 2);
        int readCount = -1;
        try {
            readCount = socketChannel.read(buffer);
        } catch (IOException ignored) {
        }
        if (readCount < 0) {
            key.cancel();
            try {
                socketChannel.close();
            } catch (IOException ignored) {
            }
        }
        if (readCount > 0) {
            buffer.flip();
            if (localServer.hasClient()) {
                localServer.proxy(buffer);
                try {
                    socketChannel.write(buffer);
                    buffer.clear();
                } catch (IOException ignored) {
                }
            }
        }
    }
}
