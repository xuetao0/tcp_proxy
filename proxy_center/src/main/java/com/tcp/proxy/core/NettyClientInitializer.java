package com.tcp.proxy.core;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

import javax.net.ssl.SSLException;
import java.security.cert.CertificateException;


/**
 * @ClassName NettyClientInitializer
 * @Description TODO
 * @Author xuetao
 * @Date 2021/2/3 11:09
 **/
public class NettyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    public void initChannel(SocketChannel ch) throws Exception {
//        SslContext sslCtx = null;
//        try {
//            SelfSignedCertificate ssc = new SelfSignedCertificate();
//            sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
//        } catch (CertificateException | SSLException e) {
//            e.printStackTrace();
//        }


        ChannelPipeline p = ch.pipeline();
//        if(sslCtx!=null){
//            p.addLast(sslCtx.newHandler(ch.alloc()));
//        }
        /**
         * 请求报文解码
         */
        p.addLast("decoder", new HttpRequestDecoder());
        /**
         * 应答报文编码
         */
        p.addLast("encoder", new HttpResponseEncoder());
        /**
         * 聚合完整报文
         */
        p.addLast("aggregator", new HttpObjectAggregator(1024 * 1024));
        /**
         * 压缩应答报文
         */
//        p.addLast("compressor",new HttpContentCompressor());
        p.addLast("log", new LoggingHandler(LogLevel.INFO));
//        p.addLast("codec", new HttpClientCodec());
        p.addLast("handler", new EchoServerHandler());
    }
}
