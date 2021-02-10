package com.tcp.proxy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @ClassName NioLocalServer
 * @Description TODO
 * @Author xuetao
 * @Date 2021/2/1 13:11
 **/
public class NioLocalServer {


    private String getServerHost() {
        return "127.0.0.1";
    }

    private int getServerPort() {
        return 9919;
    }

    private void handleSelector(SelectionKey key, Selector selector) {
        if (key.isConnectable()) {
            doConnectable(key, selector);
        } else if (key.isReadable()) {
            doReadable(key);
        }
    }

    private void ping(SocketChannel channel) {
        byte[] message = new byte[]{(byte) 1};
        ByteBuffer bf = ByteBuffer.allocate(1);
        bf.put(message, 0, 1);
        bf.flip();
        try {
            channel.write(bf);
        } catch (IOException ignored) {

        }
    }

    private void doConnectable(SelectionKey key, Selector selector) {
        SocketChannel channel = (SocketChannel) key.channel();
        try {
            if (channel.finishConnect()) {
                ping(channel);
                channel.register(selector, SelectionKey.OP_READ);
            }
        } catch (IOException ignored) {
        }
    }

    private void closeChannel(SelectionKey key, SocketChannel channel) {
        key.cancel();
        try {
            channel.close();
        } catch (IOException ignored) {

        }
    }

    private void doReadable(SelectionKey key) {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024 * 2);
        int readCount = -1;
        try {
            readCount = channel.read(buffer);
        } catch (IOException ignored) {
        }
        if (readCount < 0) {
            closeChannel(key, channel);
        } else if (readCount > 0) {
            buffer.flip();
            NioLocalProxy.doProxy(buffer);
            try {
                channel.write(buffer);
            } catch (IOException ignored) {
            }
        }
    }

    private void startSelect(Selector selector) {
        while (true) {
            try {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                if (!selectionKeys.isEmpty()) {
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        if (key.isValid()) {
                            handleSelector(key, selector);
                        }
                    }
                }
            } catch (IOException ignored) {

            }

        }
    }

    public void start() {
        try {
            Selector selector = Selector.open();
            SocketChannel channel = SocketChannel.open();
            channel.configureBlocking(false);
            boolean connect = channel.connect(new InetSocketAddress(getServerHost(), getServerPort()));
            if (connect) {
                ping(channel);
                channel.register(selector, SelectionKey.OP_READ);
            } else {
                channel.register(selector, SelectionKey.OP_CONNECT);
            }
            new Thread(() -> {
                startSelect(selector);
            }).start();
        } catch (IOException ignored) {
        }
    }
}
