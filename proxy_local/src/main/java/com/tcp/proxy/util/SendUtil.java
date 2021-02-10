package com.tcp.proxy.util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @ClassName SendUtil
 * @Description TODO
 * @Author xuetao
 * @Date 2021/2/1 19:11
 **/
public class SendUtil {
    private static final ByteBuffer PING_MESSAGE;
    private static final byte PING = 1;


    static {
        PING_MESSAGE = ByteBuffer.allocate(1);
        byte[] message = new byte[]{PING};
        PING_MESSAGE.put(message, 0, 1);
        PING_MESSAGE.flip();
    }


    public static void ping(SocketChannel sc) {
        try {
            sc.write(PING_MESSAGE);
            PING_MESSAGE.flip();
        } catch (IOException ignored) {

        }
    }

    public static boolean checkPing(ByteBuffer buffer) {
        byte[] bytes = new byte[1];
        buffer.get(bytes);
        return PING == bytes[0];
    }


    public static ByteBuffer getPingMessage() {
        return PING_MESSAGE;
    }
}
