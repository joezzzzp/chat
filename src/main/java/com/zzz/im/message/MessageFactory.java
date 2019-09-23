package com.zzz.im.message;

import java.util.Collection;

/**
 * @author zzz
 * @date 2019/8/30 14:24
 **/

public class MessageFactory {

    private volatile String currentUser = "";

    private MessageFactory () {
        //empty
    }

    public static MessageFactory getInstance() {
        return MessageFactoryHolder.INSTANCE;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public MessageData newOnlineMessage() {
        MessageData onlineMessageData = new MessageData();
        onlineMessageData.setMessageType(MessageType.COMMAND);
        onlineMessageData.setCommandType(CommandType.ONLINE);
        onlineMessageData.setFrom(currentUser);
        return onlineMessageData;
    }

    public MessageData newOfflineMessage() {
        MessageData offlineMessageData = new MessageData();
        offlineMessageData.setMessageType(MessageType.COMMAND);
        offlineMessageData.setCommandType(CommandType.OFFLINE);
        offlineMessageData.setFrom(currentUser);
        return offlineMessageData;
    }

    public MessageData newTextMessage(String to, String content) {
        MessageData textMessageData = new MessageData();
        textMessageData.setMessageType(MessageType.TEXT);
        textMessageData.setCommandType(CommandType.NONE);
        textMessageData.setFrom(currentUser);
        textMessageData.setTo(to);
        textMessageData.setRawContent(content);
        textMessageData.setContent(content);
        return textMessageData;
    }

    public MessageData newUserListMessage(String to, Collection<String> users) {
        MessageData userListMessageData = new MessageData();
        userListMessageData.setMessageType(MessageType.COMMAND);
        userListMessageData.setCommandType(CommandType.USER_LIST);
        userListMessageData.setFrom(currentUser);
        userListMessageData.setTo(to);
        userListMessageData.setRawContent(String.join(",", users));
        return userListMessageData;
    }

    private static class MessageFactoryHolder {
        private static final MessageFactory INSTANCE = new MessageFactory();
    }
}
