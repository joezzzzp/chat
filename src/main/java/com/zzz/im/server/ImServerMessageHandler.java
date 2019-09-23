package com.zzz.im.server;

import com.zzz.im.message.MessageData;
import com.zzz.im.message.MessageFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Set;

/**
 * @author zzz
 * @date 2019/8/21 14:15
 **/
public class ImServerMessageHandler extends SimpleChannelInboundHandler<MessageData> {

    private static final Logger logger = LogManager.getLogger(ImServerMessageHandler.class);

    private static final AttributeKey<String> userKey = AttributeKey.newInstance("userName");

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("[active] {}", ctx.channel());
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, MessageData msg) throws Exception {
        logger.info("[receive] {}", msg.toString());
        handleMessage(ctx, msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("[error] {}", cause);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        String user = ctx.attr(userKey).get();
        logger.debug("[unregistered] {}, {}", user, ctx.channel());
        ImServer.CLIENT_POOL.remove(user);
        MessageData offlineMessage = MessageFactory.getInstance().newOfflineMessage();
        offlineMessage.setFrom(user);
        broadCast(offlineMessage);
    }

    private void handleMessage(ChannelHandlerContext context, MessageData messageData) {
        switch (messageData.getMessageType()) {
            case COMMAND:
                handleCommandMessage(context, messageData);
                break;
            case TEXT:
                ChannelHandlerContext to = ImServer.CLIENT_POOL.get(messageData.getTo());
                if (null != to) {
                    handleTextMessage(to, messageData);
                }
                break;
            default:
                break;
        }
    }

    private void handleCommandMessage(ChannelHandlerContext context, MessageData messageData) {
        switch (messageData.getCommandType()) {
            case ONLINE:
                Set<String> users = ImServer.CLIENT_POOL.keySet();
                MessageData userListMessage = MessageFactory.getInstance().newUserListMessage(messageData.getFrom(), users);
                context.writeAndFlush(userListMessage);
                context.attr(userKey).set(messageData.getFrom());
                ImServer.CLIENT_POOL.putIfAbsent(messageData.getFrom(), context);
                break;
            case OFFLINE:
                ImServer.CLIENT_POOL.remove(messageData.getFrom());
                break;
            default:
                break;
        }
        broadCast(messageData);
    }

    private void handleTextMessage(ChannelHandlerContext context, MessageData messageData) {
        context.writeAndFlush(messageData);
    }

    private void broadCast(MessageData messageData) {
        //广播信息
        for (Map.Entry<String, ChannelHandlerContext> user: ImServer.CLIENT_POOL.entrySet()){
            if (!user.getKey().equals(messageData.getFrom())) {
                try {
                    MessageData finalData = messageData.clone();
                    finalData.setTo(user.getKey());
                    logger.info("[broadcast] message: {}, contextUser: {}, remote: {}",
                            finalData.toString(), user.getValue().attr(userKey).get(),
                            user.getValue().channel());
                    user.getValue().writeAndFlush(finalData);
                } catch (CloneNotSupportedException e) {
                    logger.error("无法复制消息对象");
                }
            }
        }
    }
}
