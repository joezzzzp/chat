package com.zzz.im.codec;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzz.im.message.MessageData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author zzz
 * @date 2019/8/29 14:19
 **/
public class MessageDecoder extends ByteToMessageDecoder {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (out.isEmpty()) {
            if (in.readableBytes() >= 4) {
                Integer length = in.readInt();
                out.add(length);
            }
        } else {
            Integer size = (Integer) out.get(0);
            if (in.readableBytes() >= size) {
                byte[] bytes = new byte[size];
                in.readBytes(bytes);
                out.add(MAPPER.readValue(bytes, MessageData.class));
            }
        }

    }
}
