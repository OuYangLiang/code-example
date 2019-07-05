package com.personal.oyl.code.example.nettydemo.httpxml;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.FullHttpRequest;
import org.dom4j.DocumentException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author OuYang Liang
 * @since 2019-06-23
 */
public class HttpXmlRequestDecoder extends MessageToMessageDecoder<FullHttpRequest> {

    @Override
    protected void decode(ChannelHandlerContext ctx, FullHttpRequest msg, List<Object> out) throws IOException, DocumentException {
        ByteBuf body = msg.content();
        final byte[] array;
        final int length = body.readableBytes();
        array = new byte[length];
        body.getBytes(body.readerIndex(), array, 0, length);

        HttpXmlRequest request = new HttpXmlRequest(msg, Order.parseFrom(new String(array, StandardCharsets.UTF_8)));
        out.add(request);
    }
}
