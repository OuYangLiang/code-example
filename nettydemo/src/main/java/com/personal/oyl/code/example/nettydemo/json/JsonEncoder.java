package com.personal.oyl.code.example.nettydemo.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;


/**
 * @author OuYang Liang
 * @since 2019-06-06
 */
public class JsonEncoder extends MessageToByteEncoder<Object> {

    private static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object msg, ByteBuf byteBuf) {
        byte[] raw = gson.toJson(msg).getBytes();
        byteBuf.writeBytes(raw);
    }
}
