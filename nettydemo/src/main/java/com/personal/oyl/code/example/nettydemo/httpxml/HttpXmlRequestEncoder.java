package com.personal.oyl.code.example.nettydemo.httpxml;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.*;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

/**
 * @author OuYang Liang
 * @since 2019-06-23
 */
public class HttpXmlRequestEncoder extends MessageToMessageEncoder<HttpXmlRequest> {

    @Override
    protected void encode(ChannelHandlerContext ctx, HttpXmlRequest msg, List<Object> out) throws IOException {
        ByteBuf body = Unpooled.copiedBuffer(msg.getBody().toXml().getBytes());
        FullHttpRequest request = msg.getRequest();
        if (request == null) {
            request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, "/", body);
            HttpHeaders headers = request.headers();
            headers.set(HttpHeaderNames.HOST, InetAddress.getLocalHost().getHostAddress());
            headers.set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
            headers.set(HttpHeaderNames.ACCEPT_ENCODING, HttpHeaderValues.GZIP + "," + HttpHeaderValues.DEFLATE.toString());
            headers.set(HttpHeaderNames.ACCEPT_CHARSET, "ISO-8859-1,utf-8");
            headers.set(HttpHeaderNames.ACCEPT_LANGUAGE, "zh");
            headers.set(HttpHeaderNames.USER_AGENT, "Netty xml http client side");
            headers.set(HttpHeaderNames.ACCEPT, "text/html,text/xml,application/xhtml+xml,application/xml");
        }

        request.headers().set(HttpHeaderNames.CONTENT_LENGTH, Integer.toString(request.content().readableBytes()));
        out.add(request);
    }
}
