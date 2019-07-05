package com.personal.oyl.code.example.nettydemo.httpxml;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author OuYang Liang
 * @since 2019-06-23
 */
public class HttpXmlResponseEncoder extends MessageToMessageEncoder<HttpXmlResponse> {
    @Override
    protected void encode(ChannelHandlerContext ctx, HttpXmlResponse msg, List<Object> out) throws Exception {
        ByteBuf body = Unpooled.copiedBuffer(msg.getResult().toXml().getBytes(StandardCharsets.UTF_8));

        FullHttpResponse response = msg.getHttpResponse();
        if (response == null) {
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, body);
        } else {
            response = new DefaultFullHttpResponse(msg.getHttpResponse().protocolVersion(), msg.getHttpResponse().status(), body);
        }

        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/xml; charset=UTF-8");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, Integer.toString(response.content().readableBytes()));

        out.add(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
