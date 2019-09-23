package com.zzz.im.gui;

import com.zzz.im.client.ImClient;
import com.zzz.im.gui.component.VerticalFlowLayout;
import com.zzz.im.gui.constant.ColorConstant;
import com.zzz.im.gui.constant.StringConstant;
import com.zzz.im.gui.data.UIMessageData;
import com.zzz.im.gui.data.UIMessageType;
import com.zzz.im.gui.message.MessageLineFactory;
import com.zzz.im.message.MessageData;
import com.zzz.im.message.MessageFactory;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

/**
 * @author created by zzz at 2019/9/19 10:19
 **/

public class ConversationPanel extends JPanel {

    private String toUser;

    private JPanel messageContainer;

    private JScrollPane messagesPane;

    private JTextArea inputField;

    private ImClient imClient;

    public ConversationPanel() {
        imClient = ImClient.getInstance();
        setLayout(new BorderLayout());
        buildMessagePane();
        buildInputField();
        JScrollPane inputPane = new JScrollPane(inputField);
        inputPane.setPreferredSize(new Dimension(530, 150));
        setOpaque(false);
        add(messagesPane, BorderLayout.CENTER);
        add(inputPane, BorderLayout.SOUTH);
    }

    private void buildMessagePane() {
        //消息滚动板
        messagesPane = new JScrollPane();
        messagesPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        messagesPane.setOpaque(true);
        messagesPane.setBackground(ColorConstant.LIGHT_LIGHT_GRAY);
        messagesPane.getViewport().setOpaque(false);
        JScrollBar scrollBar = messagesPane.getVerticalScrollBar();
        Dimension scrollBarDimension = scrollBar.getPreferredSize();
        scrollBar.setPreferredSize(new Dimension(scrollBarDimension.width * 2 / 3, scrollBarDimension.height));
    }

    public JPanel buildMessageContainer() {
        //消息列表
        JPanel container = new JPanel();
        container.setLayout(new VerticalFlowLayout());
        container.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        container.setOpaque(false);
        return container;
    }

    private void buildInputField() {
        inputField = new JTextArea();
        inputField.setLineWrap(true);
        inputField.setWrapStyleWord(true);
        Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
        inputField.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        inputField.addKeyListener(new MessageKeyListener());
        inputField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                setText(inputField, "");
            }

            @Override
            public void focusLost(FocusEvent e) {
                setHint(inputField, StringConstant.INPUT_FIELD_HINT);
            }
        });
        setHint(inputField, StringConstant.INPUT_FIELD_HINT);
    }

    public void setMessageContainer(String toWhom, JPanel messageContainer) {
        this.toUser = toWhom;
        this.messageContainer = messageContainer;
        messagesPane.setViewportView(messageContainer);
        messagesPane.validate();
    }

    public JPanel getMessageContainer() {
        return messageContainer;
    }

    public void addMessage(MessageData messageData, UIMessageType type) {
        addMessage(messageContainer, messageData, type);
    }

    public void addMessage(JPanel container, MessageData messageData, UIMessageType type) {
        UIMessageData uiMessageData = new UIMessageData(messageData);
        uiMessageData.setType(type);
        JPanel item = MessageLineFactory.getInstance().buildMessageLine(uiMessageData);
        container.add(item);
        container.validate();
    }

    private void setHint(JTextArea textArea, String src) {
        textArea.setFont(new Font("Default", Font.PLAIN, 14));
        textArea.setForeground(Color.LIGHT_GRAY);
        textArea.setText(src);
    }

    private void setText(JTextArea textArea, String src) {
        textArea.setFont(new Font("Default", Font.PLAIN, 16));
        textArea.setForeground(Color.BLACK);
        textArea.setText(src);
    }

    class MessageKeyListener implements KeyListener {

        private boolean controlPressed;

        private boolean enterPressed;

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (KeyEvent.VK_CONTROL == e.getKeyCode()) {
                controlPressed = true;
            }
            if (KeyEvent.VK_ENTER == e.getKeyCode()) {
                enterPressed = true;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            String rawText = inputField.getText();
            //Ctrl + Enter
            if (controlPressed && enterPressed) {
                setText(inputField, rawText + System.lineSeparator());
            }
            //Enter
            if (!controlPressed && enterPressed) {
                MessageData messageData = MessageFactory.getInstance().newTextMessage(toUser, rawText);
                addMessage(messageData, UIMessageType.SENT);
                imClient.offerMessage(messageData);
                setText(inputField, "");
            }
            if (KeyEvent.VK_CONTROL == e.getKeyCode()) {
                controlPressed = false;
            }
            if (KeyEvent.VK_ENTER == e.getKeyCode()) {
                enterPressed = false;
            }
        }
    }
}
