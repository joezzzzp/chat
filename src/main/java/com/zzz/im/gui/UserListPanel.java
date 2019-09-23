package com.zzz.im.gui;

import com.zzz.im.gui.data.UIMessageType;
import com.zzz.im.message.MessageData;
import com.zzz.im.utils.StringUtils;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author created by zzz at 2019/9/18 16:07
 **/

public class UserListPanel extends JScrollPane implements ListSelectionListener {

    private DefaultListModel<String> userListModel;

    private ConversationPanel conversationPanel;

    private static final Map<String, JPanel> messageContainers = new HashMap<>();

    public UserListPanel(ConversationPanel conversationPanel) {
        this.userListModel = new DefaultListModel<>();
        JList<String> userList = new JList<>(userListModel);
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userList.addListSelectionListener(this);
        setViewportView(userList);
        setPreferredSize(new Dimension(200, 0));
        this.conversationPanel = conversationPanel;
        if (userListModel.getSize() > 0) {
            userList.setSelectedIndex(0);
        }
    }

    public void addUser(String user) {
        if (!userListModel.contains(user)) {
            userListModel.addElement(user);
        }
    }

    public void removeUser(String user) {
        userListModel.removeElement(user);
    }

    public void addMessage(MessageData messageData) {
        String from = messageData.getFrom();
        if (StringUtils.isNotEmpty(from) && messageContainers.containsKey(from)) {
            JPanel messageContainer = messageContainers.get(from);
            conversationPanel.addMessage(messageContainer, messageData, UIMessageType.RECEIVED);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            JList<String> userList = (JList<String>) e.getSource();
            String user = userList.getSelectedValue();
            setMessageContainers(user);
        }
    }

    private void setMessageContainers(String user) {
        JPanel container = messageContainers.computeIfAbsent(user, s -> conversationPanel.buildMessageContainer());
        conversationPanel.setMessageContainer(user, container);
    }
}
