package com.zzz.im.gui.message;

import com.zzz.im.gui.data.UIMessageData;
import com.zzz.im.message.MessageType;

/**
 * @author created by zzz at 2019/9/23 18:04
 **/

public class MessageLineFactory {

    private MessageLineFactory()  {
        //empty
    }

    public static MessageLineFactory getInstance() {
        return MessageLineFactoryHolder.INSTANCE;
    }

    public BaseMessage buildMessageLine(UIMessageData messageData) {
        if (MessageType.TEXT.equals(messageData.getRaw().getMessageType())) {
            return new TextMessage(messageData);
        }
        return null;
    }

    private static class MessageLineFactoryHolder {
        private static final MessageLineFactory INSTANCE = new MessageLineFactory();
    }
}
