package com.zzz.im.gui;

import com.zzz.im.message.CommandType;
import com.zzz.im.message.MessageData;

import javax.swing.*;
import java.awt.*;

/**
 * @author created by zzz at 2019/9/17 11:22
 **/

public class ImPanel extends JPanel {

    private ConversationPanel conversationPanel;

    private UserListPanel userListPanel;

    public ImPanel() {
        setLayout(new BorderLayout());
        conversationPanel = new ConversationPanel();
        userListPanel = new UserListPanel(conversationPanel);
        add(userListPanel, BorderLayout.WEST);
        add(conversationPanel, BorderLayout.CENTER);
    }

    public void receiveMessage(MessageData messageData) {
        switch (messageData.getMessageType()) {
            case COMMAND:
                handleCommand(messageData);
                break;
            case TEXT:
                handleText(messageData);
                break;
            default:
                break;
        }
    }

    private void handleCommand(MessageData messageData) {
        switch (messageData.getCommandType()) {
            case USER_LIST:
                String[] users = messageData.getRawContent().split(",");
                for (String user : users) {
                    userListPanel.addUser(user);
                }
                break;
            case ONLINE:
                userListPanel.addUser(messageData.getFrom());
                break;
            case OFFLINE:
                userListPanel.removeUser(messageData.getFrom());
                break;
            default:
                break;
        }
    }

    private void handleText(MessageData messageData) {
        userListPanel.addMessage(messageData);
    }
}
