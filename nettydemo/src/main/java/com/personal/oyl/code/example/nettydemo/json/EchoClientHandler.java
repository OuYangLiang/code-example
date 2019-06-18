package com.personal.oyl.code.example.nettydemo.json;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author OuYang Liang
 * @since 2019-06-06
 */
public class EchoClientHandler extends ChannelHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {

        Message msg = new Message();
        msg.setMsgId(1);
        msg.setMsgInfo("Hello, it's json message...");

        ctx.writeAndFlush(msg);
    }



    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
