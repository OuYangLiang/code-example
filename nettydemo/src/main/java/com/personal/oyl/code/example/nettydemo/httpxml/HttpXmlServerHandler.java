package com.personal.oyl.code.example.nettydemo.httpxml;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpRequest;

/**
 * @author OuYang Liang
 * @since 2019-06-23
 */
public class HttpXmlServerHandler extends SimpleChannelInboundHandler<HttpXmlRequest> {
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, HttpXmlRequest msg) {
        HttpRequest request = msg.getRequest();
        Order order = msg.getBody();
        System.out.println("Http server receive request headers : " + msg.getRequest().headers().names());
        System.out.println("Http server receive request : " + order);
        this.doBusiness(order);
        ChannelFuture future = ctx.writeAndFlush(new HttpXmlResponse(null, order));
        future.addListener(ChannelFutureListener.CLOSE);
    }

    private void doBusiness(Order order) {
        order.setTotal(order.getTotal() + 100F);
    }
}
