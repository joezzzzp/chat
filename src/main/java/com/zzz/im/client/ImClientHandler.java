package com.zzz.im.client;

import com.zzz.im.message.MessageData;
import com.zzz.im.message.MessageFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author zzz
 * @date 2019/8/27 15:30
 **/
@ChannelHandler.Sharable
public class ImClientHandler extends SimpleChannelInboundHandler<MessageData> {

    private static final Logger logger = LogManager.getLogger(ImClientHandler.class);

    private ChannelHandlerContext context;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("[active] {}", ctx.channel());
        this.context = ctx;
        ctx.writeAndFlush(MessageFactory.getInstance().newOnlineMessage());
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, MessageData msg) throws Exception {
        logger.info("[receive] {}", msg.toString());
        ImClient.getInstance().offerMessage(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("[error] {}", cause);
    }

    public void sendMessage(MessageData messageData) {
        context.writeAndFlush(messageData);
    }
}
