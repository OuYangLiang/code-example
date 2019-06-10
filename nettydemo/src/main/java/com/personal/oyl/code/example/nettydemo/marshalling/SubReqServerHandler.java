package com.personal.oyl.code.example.nettydemo.marshalling;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author OuYang Liang
 * @since 2019-06-08
 */
public class SubReqServerHandler extends ChannelHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Request req = (Request) msg;

        if ("OuYangLiang".equalsIgnoreCase(req.getUserName())) {
            System.out.println("Service accept client subscribe req : [" + req.toString() + "]");
            ctx.writeAndFlush(resp(req.getSubReqID()));
        }
    }

    private Response resp(int subReqID) {
        Response r = new Response();
        r.setSubReqID(subReqID);
        r.setRespCode(0);
        r.setDesc("Macbook pro order succeed, 3 days later, sent to the designated address");
        return r;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
