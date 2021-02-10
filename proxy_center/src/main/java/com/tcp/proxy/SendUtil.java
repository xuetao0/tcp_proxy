package com.tcp.proxy;


import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

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

    public static ByteBuffer getPingMessage() {
        return PING_MESSAGE;
    }

    public static void ping(SocketChannel channel) {
        try {
            channel.write(PING_MESSAGE);
            PING_MESSAGE.flip();
        } catch (IOException ignored) {

        }
    }

    public static boolean checkPing(ByteBuffer buffer) {
        byte[] bytes = new byte[1];
        buffer.get(bytes);
        return PING == bytes[0];
    }
}
