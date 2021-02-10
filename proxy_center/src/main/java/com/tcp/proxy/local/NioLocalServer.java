package com.tcp.proxy.local;


import com.tcp.proxy.core.AbstractNioServer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.NetworkChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName NioLocalServer
 * @Description TODO
 * @Author xuetao
 * @Date 2021/2/1 10:10
 **/

public class NioLocalServer extends AbstractNioServer implements LocalServer {

    private SocketChannel clientChannel;
    private volatile boolean hasClient = false;
    private final BlockingQueue<byte[]> blockingQueue = new ArrayBlockingQueue<>(1);

    public NioLocalServer() {
        startServer();
    }

    @Override
    public boolean hasClient() {
        return hasClient;
    }

    @Override
    public void proxy(ByteBuffer bf) {
        try {
            clientChannel.write(bf);
            bf.clear();
            blockingQueue.clear();
            byte[] exchange = blockingQueue.poll(2, TimeUnit.SECONDS);
            if (exchange == null) {
                String errorMessage = "client time out";
                exchange = errorMessage.getBytes();
            }
            bf.put(exchange);
            bf.flip();
        } catch (IOException | InterruptedException ignored) {
        }
    }

    @Override
    protected int getThreadSize() {
        return 1;
    }

    @Override
    protected int getPort() {
        return 9919;
    }

    private void closeChannel(SelectionKey key, NetworkChannel channel) {
        key.cancel();
        try {
            channel.close();
        } catch (IOException ignored) {
        }
    }

    private void doReadable(ByteBuffer buffer) {
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        try {
            blockingQueue.put(bytes);
        } catch (InterruptedException ignored) {
        }
    }

    private boolean checkPing(ByteBuffer buffer) {
        byte[] bytes = new byte[1];
        buffer.get(bytes);
        return 1 == bytes[0];
    }

    @Override
    protected void handleReadable(SelectionKey key) {
        SocketChannel channel = (SocketChannel) key.channel();
        int readCount = -1;
        ByteBuffer buffer = ByteBuffer.allocate(1024 * 3);
        try {
            readCount = channel.read(buffer);
        } catch (IOException ignored) {
        }
        if (readCount < 0) {
            closeChannel(key, channel);
        }
        buffer.flip();
        if (readCount == 1) {
            if (checkPing(buffer)) {
                System.out.println("client registry");
                clientChannel = channel;
                hasClient = true;
            }
        } else if (readCount > 0) {
            doReadable(buffer);
        }
    }
}
