package com.tcp.proxy;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @ClassName ProxyServer
 * @Description TODO
 * @Author xuetao
 * @Date 2021/2/1 12:11
 **/
public class ProxyServer {
    public static void main(String[] args) {
//        CenterServer server = new NioCenterServer();
        doProxy();
    }
    public static void doProxy() {
        String message = "GET /eftZ HTTP/1.1\n" +
                "Host: dwz.date\n" +
                "Connection: keep-alive\n" +
                "Upgrade-Insecure-Requests: 1\n" +
                "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.104 Safari/537.36\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\n" +
                "Sec-Fetch-Site: none\n" +
                "Sec-Fetch-Mode: navigate\n" +
                "Sec-Fetch-User: ?1\n" +
                "Sec-Fetch-Dest: document\n" +
                "Accept-Encoding: gzip, deflate, br\n" +
                "Accept-Language: zh-CN,zh;q=0.9\n";

        byte[] bytes =message.getBytes();
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress("103.86.47.33",80));
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(bytes);
            InputStream inputStream = socket.getInputStream();
            byte[] result = new byte[1024 * 3];
            int read = inputStream.read(result);
            if (read > 0) {
                System.out.println(new String(result,"UTF-8"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
