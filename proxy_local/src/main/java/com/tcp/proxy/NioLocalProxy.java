package com.tcp.proxy;



import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.nio.ByteBuffer;

/**
 * @ClassName NioLocalProxy
 * @Description TODO
 * @Author xuetao
 * @Date 2021/2/1 14:25
 **/
public class NioLocalProxy {
    private static int i = 1;
    private static final int port = 30881;

    public static void doProxy(ByteBuffer buffer) {
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        buffer.clear();
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(port));
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(bytes);
            InputStream inputStream = socket.getInputStream();
            byte[] result = new byte[1024 * 3];
            int read = inputStream.read(result);
            if (read > 0) {
                buffer.put(result, 0, read);
            }
            buffer.flip();
        } catch (IOException ignored) {

        }
    }


}
