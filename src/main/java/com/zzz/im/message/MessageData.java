package com.zzz.im.message;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.util.UUID;

/**
 * @author zzz
 * @date 2019/8/29 11:23
 **/
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageData implements Cloneable{

    private String uuid = UUID.randomUUID().toString();

    private MessageType messageType = MessageType.COMMAND;

    private CommandType commandType = CommandType.NONE;

    private String from;

    private String to;

    private String rawContent;

    private String content;

    private Date messageTime = new Date();

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public void setCommandType(CommandType commandType) {
        this.commandType = commandType;
    }

    public String getRawContent() {
        return rawContent;
    }

    public void setRawContent(String rawContent) {
        this.rawContent = rawContent;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Date getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(Date messageTime) {
        this.messageTime = messageTime;
    }

    @Override
    public String toString() {
        return "MessageData{" +
                "uuid='" + uuid + '\'' +
                ", messageType=" + messageType +
                ", commandType=" + commandType +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", rawContent='" + rawContent + '\'' +
                ", content='" + content + '\'' +
                ", messageTime=" + messageTime +
                '}';
    }

    @Override
    public MessageData clone() throws CloneNotSupportedException {
        MessageData data = (MessageData) super.clone();
        data.setUuid(UUID.randomUUID().toString());
        return data;
    }
}
