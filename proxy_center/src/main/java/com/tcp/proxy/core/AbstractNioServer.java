package com.tcp.proxy.core;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName AbstractNioServer
 * @Description TODO
 * @Author xuetao
 * @Date 2021/2/1 11:09
 **/
public abstract class AbstractNioServer {
    private volatile boolean startFlag = true;
    private final ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(getThreadSize(), getThreadSize(), 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), x -> new Thread(x, ""));
    private Selector selector;

    /**
     * 获取线程数
     *
     * @return
     */
    protected abstract int getThreadSize();

    /**
     * 获取服务端口号
     *
     * @return
     */
    protected abstract int getPort();


    /**
     * 处理 read key
     *
     * @param key
     */
    protected abstract void handleReadable(SelectionKey key);

    protected final Selector getSelector() {
        return selector;
    }

    protected final void stopServer() {
        startFlag = false;
    }

    protected final void startServer() {
        try {
            selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(getPort()));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            poolExecutor.execute(this::startSelector);
            System.out.println("start server " + getPort());
        } catch (IOException e) {
            throw new Error(e);
        }
    }

    protected void handleAcceptable(SelectionKey key) {
        ServerSocketChannel channel = (ServerSocketChannel) key.channel();
        try {
            SocketChannel socketChannel = channel.accept();
            socketChannel.configureBlocking(false);
            socketChannel.register(getSelector(), SelectionKey.OP_READ);
        } catch (IOException ignored) {
        }
    }

    /**
     * 处理selector
     *
     * @param key
     */
    protected void handleSelectionKey(SelectionKey key) {
        if (key.isAcceptable()) {
            handleAcceptable(key);
        } else if (key.isReadable()) {
            handleReadable(key);
        }
    }

    private void startSelector() {
        while (startFlag) {
            try {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                if (!selectionKeys.isEmpty()) {
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        if (key.isValid()) {
                            handleSelectionKey(key);
                        }
                    }
                }
            } catch (IOException e) {
                throw new Error(e);
            }
        }
    }
}
