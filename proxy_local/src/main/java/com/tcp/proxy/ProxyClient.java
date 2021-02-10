package com.tcp.proxy;

/**
 * @ClassName ProxyClient
 * @Description TODO
 * @Author xuetao
 * @Date 2021/2/1 14:34
 **/
public class ProxyClient {
    public static void main(String[] args) {
        NioLocalServer server = new NioLocalServer();
        server.start();
    }
}
