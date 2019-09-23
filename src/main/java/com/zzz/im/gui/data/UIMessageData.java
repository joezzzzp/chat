package com.zzz.im.gui.data;

import com.zzz.im.message.MessageData;
import com.zzz.im.message.MessageType;

import java.time.LocalDateTime;
import java.util.TimeZone;

/**
 * @author created by zzz at 2019/9/23 11:03
 **/

public class UIMessageData {

    private MessageData raw;

    private UIMessageType type;

    private MessageType messageType;

    private LocalDateTime localDateTime;

    public UIMessageData(MessageData raw) {
        this.raw = raw;
        this.messageType = raw.getMessageType();
        this.localDateTime = LocalDateTime.ofInstant(raw.getMessageTime().toInstant(), TimeZone.getDefault().toZoneId());
    }

    public UIMessageData(UIMessageType type, LocalDateTime time) {
        this.type = type;
        this.localDateTime = time;
    }

    public MessageData getRaw() {
        return raw;
    }

    public void setRaw(MessageData raw) {
        this.raw = raw;
    }

    public UIMessageType getType() {
        return type;
    }

    public void setType(UIMessageType type) {
        this.type = type;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}
