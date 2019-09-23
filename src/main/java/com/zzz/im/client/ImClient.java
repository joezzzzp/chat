package com.zzz.im.client;

import com.zzz.im.codec.MessageDecoder;
import com.zzz.im.codec.MessageEncoder;
import com.zzz.im.gui.ImPanel;
import com.zzz.im.message.CommandType;
import com.zzz.im.message.MessageData;
import com.zzz.im.message.MessageFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.net.InetSocketAddress;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author zzz
 * @date 2019/8/27 15:21
 **/

public class ImClient extends Thread {

    private static final Logger logger = LogManager.getLogger(ImClient.class);

    private String currentUser;

    private EventLoopGroup eventLoopGroup;

    private Bootstrap bootstrap;

    private ImClientHandler imClientHandler;

    private BlockingQueue<MessageData> messageQueue = new ArrayBlockingQueue<>(256);

    private ImPanel imPanel;

    private String host;

    private int port;

    private ImClient() {
        //empty
    }

    public static ImClient getInstance() {
        return ImClientHolder.INSTANCE;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public void init() {
        eventLoopGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        imClientHandler = new ImClientHandler();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .remoteAddress(new InetSocketAddress(host, port))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast("decoder", new MessageDecoder());
                        ch.pipeline().addLast("encoder", new MessageEncoder());
                        ch.pipeline().addLast("business", imClientHandler);
                    }
                });
    }

    public void bindUI(ImPanel imPanel) {
        this.imPanel = imPanel;
    }

    @Override
    public void run() {
        try {
            connect();
            MessageData message;
            for (;;) {
                message = messageQueue.take();
                if (currentUser.equals(message.getFrom())) {
                    sendMessage(message);
                } else if (currentUser.equals(message.getTo())) {
                    receiveMessage(message);
                }
            }
        } catch (InterruptedException e) {
            logger.error("线程被中断", e);
            Thread.currentThread().interrupt();
        }
    }

    public boolean offerMessage(MessageData messageData) {
        return messageQueue.offer(messageData);
    }

    private void connect() throws InterruptedException {
        bootstrap.connect().sync();
    }

    private void sendMessage(MessageData rawMessage) {
        imClientHandler.sendMessage(rawMessage);
    }

    private void receiveMessage(MessageData receiveMessage) {
        SwingUtilities.invokeLater(() -> imPanel.receiveMessage(receiveMessage));
    }

    public void close() {
        eventLoopGroup.shutdownGracefully();
    }

    private static class ImClientHolder {
        private static final ImClient INSTANCE = new ImClient();
    }
}
