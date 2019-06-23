package com.personal.oyl.code.example.nettydemo.httpxml;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.FullHttpResponse;
import org.dom4j.DocumentException;

import java.io.IOException;
import java.util.List;

/**
 * @author OuYang Liang
 * @since 2019-06-23
 */
public class HttpXmlResponseDecoder extends MessageToMessageDecoder<FullHttpResponse> {

    @Override
    protected void decode(ChannelHandlerContext ctx, FullHttpResponse msg, List<Object> out) throws IOException, DocumentException {

        ByteBuf body = msg.content();
        final byte[] array;
        final int length = body.readableBytes();
        array = new byte[length];
        body.getBytes(body.readerIndex(), array, 0, length);

        HttpXmlResponse resHttpXmlResponse = new HttpXmlResponse(msg, Order.parseFrom(new String(array)));
        out.add(resHttpXmlResponse);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
