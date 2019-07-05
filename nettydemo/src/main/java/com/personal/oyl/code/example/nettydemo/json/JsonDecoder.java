package com.personal.oyl.code.example.nettydemo.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author OuYang Liang
 * @since 2019-06-06
 */
public class JsonDecoder extends MessageToMessageDecoder<ByteBuf> {

    private static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) {
        final byte[] array;
        final int length = byteBuf.readableBytes();
        array = new byte[length];
        byteBuf.getBytes(byteBuf.readerIndex(), array, 0, length);
        System.out.println(new String(array, StandardCharsets.UTF_8));
        list.add(gson.fromJson(new String(array, StandardCharsets.UTF_8), Message.class));
    }
}
