package com.tcp.proxy;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @ClassName Thread
 * @Description TODO
 * @Author xuetao
 * @Date 2021/2/5 9:43
 **/
public class ThreadTest {
    public static void main(String[] args) {
        Object[] o = null;
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                while (true) {
                    startConnect();
                }
            }).start();
        }
    }

    private static String message() {
        return "GET / HTTP/1.1\n" +
                "Host: 10.96.18.12:10119\n" +
                "Connection: keep-alive\n" +
                "Cache-Control: max-age=0\n" +
                "Upgrade-Insecure-Requests: 1\n" +
                "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.146 Safari/537.36\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\n" +
                "Sec-Fetch-Site: none\n" +
                "Sec-Fetch-Mode: navigate\n" +
                "Sec-Fetch-User: ?1\n" +
                "Sec-Fetch-Dest: document\n" +
                "Accept-Encoding: gzip, deflate, br\n" +
                "Accept-Language: zh-CN,zh;q=0.9\n" +
                "content-length: 0";
    }

    public static void startConnect() {
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress("10.96.18.12", 10119));
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(message().getBytes());
            InputStream inputStream = socket.getInputStream();
            byte[] result = new byte[1024];
            inputStream.read(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
