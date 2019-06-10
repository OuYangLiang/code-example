package com.personal.oyl.code.example.nettydemo.marshalling;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author OuYang Liang
 * @since 2019-06-08
 */
public class SubReqClientHandler extends ChannelHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        try {
            for (int i = 0; i < 10; i++) {
                ctx.write(subReq(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ctx.flush();
    }

    private Request subReq(int i) {
        Request r = new Request();
        r.setSubReqID(i);
        r.setUserName("OuYangLiang");
        r.setProductName("Macbook Pro");
        r.setAddress("Nan Jing");
        return r;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("Receive server response : [" + msg + "]");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
