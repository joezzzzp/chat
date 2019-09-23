package com.zzz.im.codec;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzz.im.message.MessageData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.CharsetUtil;

/**
 * @author zzz
 * @date 2019/8/29 14:10
 **/
@ChannelHandler.Sharable
public class MessageEncoder extends MessageToByteEncoder<MessageData> {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    protected void encode(ChannelHandlerContext ctx, MessageData msg, ByteBuf out) throws Exception {
        String jsonString = MAPPER.writeValueAsString(msg);
        byte[] jsonBytes = jsonString.getBytes(CharsetUtil.UTF_8);
        int size = jsonBytes.length;
        out.writeInt(size);
        out.writeBytes(jsonBytes);
    }
}
