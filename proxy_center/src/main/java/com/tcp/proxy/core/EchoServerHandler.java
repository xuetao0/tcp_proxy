package com.tcp.proxy.core;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;

import java.util.Date;

/**
 * @ClassName EchoServerHandler
 * @Description TODO
 * @Author xuetao
 * @Date 2021/2/3 13:02
 **/
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext context, Object msg) {
        FullHttpRequest request = (FullHttpRequest) msg;
        String uri = request.uri();
        System.out.println(uri);
        System.out.println("=============================================================================================================");
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.MOVED_PERMANENTLY);
        response.headers().add("Location", "https://eac.goldentec.com/static/product/1464-110105.html");
        context.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable couse) {
        couse.printStackTrace();
        context.close();
    }

    private String nullMessage() {
        return "＜html＞\n" +
                "＜head＞\n" +
                "＜title＞Wrox Homepage＜/title＞\n" +
                "＜/head＞\n" +
                "＜body＞\n" +
                "＜!-- body goes here --＞\n" +
                "＜/body＞\n" +
                "＜/html＞";
    }

    private String getMessage() {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"zh-CN\">\n" +
                "  <head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <title>............ - Sina.lt.........</title>\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "    <meta name=\"description\" content=\"sina.lt ...........................\">\n" +
                "    <meta name=\"keywords\" content=\"...............,............,sina,............\">\n" +
                "    <meta name=\"author\" content=\"sina.lt\">\n" +
                "    <meta name=\"robots\" content=\"index,follow\">\n" +
                "    <meta name=\"application-name\" content=\"sina.lt\">\n" +
                "\t<link rel=\"stylesheet\" type=\"text/css\" href=\"https://libs.baidu.com/bootstrap/3.3.4/css/bootstrap.min.css\" media=\"screen\" />\n" +
                "    <style>\n" +
                "        body{background: #f2f2f2;color:#777;font-family: \"ff-tisa-web-pro-1\", \"ff-tisa-web-pro-2\", \"Lucida Grande\", \"Helvetica Neue\", Helvetica, Arial, \"Hiragino Sans GB\", \"Hiragino Sans GB W3\", \"WenQuanYi Micro Hei\", sans-serif;}\n" +
                "        .notice {font-size: 24px;z-index: 9;bottom: 12px;left: 0;right: 0;text-align: center;margin: 0;padding:10px;}\n" +
                "        .box{width:60%;margin:10% auto;}\n" +
                "        .url{color:#337ab7;word-wrap: break-word;}\n" +
                "        .removetip{float:right;font-size: 12px;margin:20px 10px 0 0;clear:both;}\n" +
                "        .removetip_en{float:right;font-size: 12px;margin:0px 10px 0 0;clear:both;}\n" +
                "        .noticebg {position: relative;margin-bottom: 40px;padding-bottom: 60px;background-color: #fff;-webkit-box-shadow: 0 1px 2px 0 rgba(0,0,0,0.1);box-shadow: 0 1px 2px 0 rgba(0,0,0,0.1);}\n" +
                "        @media screen and (max-width:1024px){.box{width: 100%;}}\n" +
                "    </style>\n" +
                "\n" +
                "  </head>\n" +
                "  <body class=\"home-template\">\n" +
                "\n" +
                "<div class=\"container\">\n" +
                "        <div class=\"box\">\n" +
                "            <div style=\"width:100%;\">\n" +
                "                <article class=\"noticebg\">\n" +
                "                    <div class=\"notice\">\n" +
                "                                                    <p>...............</p>\n" +
                "                            <p class=\"url\">https://eac.goldentec.com/static/product/1464-110105.html</p>\n" +
                "                            <p><button type=\"button\" class=\"btn btn-success\" onclick=\"javascript:location.href='https://eac.goldentec.com/static/product/1464-110105.html'\">............</button></p>\n" +
                "                                                <p style=\"font-size:16px;margin:20px 0 0 0\">Sina.lt..........................................................................................</p>\n" +
                "                        <p style=\"font-size:12px;margin:10px 0 0 0\">Sina.lt assumes no responsibility for the security of this site,please be caution for your confidential.</p>\n" +
                "                        <p class=\"removetip\"><a href=\"https://sina.lt/submit_site.php?url=https://eac.goldentec.com/static/product/1464-110105.html&type=2\" target=\"_blank\">............</a> <br>\n" +
                "\n" +
                "                        </p>\n" +
                "                        <p class=\"removetip_en\"><a href=\"https://sina.lt/submit_site.php?url=https://eac.goldentec.com/static/product/1464-110105.html&type=2\" target=\"_blank\">report abuse</a></p>\n" +
                "                    </div>\n" +
                "                </article>\n" +
                "            </div>\n" +
                "\n" +
                "        </div>\n" +
                "\n" +
                "    </div>\n" +
                " </body>\n" +
                "</html>";
    }
}
