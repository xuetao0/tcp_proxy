package com.tcp.proxy.local;

import java.nio.ByteBuffer;

/**
 * @ClassName LocalServer
 * @Description TODO
 * @Author xuetao
 * @Date 2021/2/1 16:43
 **/
public interface LocalServer {
    boolean hasClient();

    void proxy(ByteBuffer bf);
}
