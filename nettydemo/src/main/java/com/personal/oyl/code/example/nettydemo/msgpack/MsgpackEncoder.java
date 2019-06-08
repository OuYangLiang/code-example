package com.personal.oyl.code.example.nettydemo.msgpack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;


/**
 * @author OuYang Liang
 * @since 2019-06-06
 */
public class MsgpackEncoder extends MessageToByteEncoder<Object> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object msg, ByteBuf byteBuf) throws Exception {
        try {
            MessagePack messagePack = new MessagePack();
            byte[] raw = messagePack.write(msg);
            byteBuf.writeBytes(raw);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}
