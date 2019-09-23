package com.zzz.im.gui.message;

import com.zzz.im.gui.constant.ColorConstant;
import com.zzz.im.utils.ImageLoader;
import com.zzz.im.gui.data.UIMessageData;
import com.zzz.im.gui.data.UIMessageType;

import javax.swing.*;
import java.awt.*;

/**
 * @author created by zzz at 2019/9/23 16:50
 **/

public abstract class BaseMessage extends JPanel implements Message {

    private UIMessageData uiMessageData;

    public BaseMessage(UIMessageData uiMessageData) {
        if (uiMessageData == null || uiMessageData.getType() == null || uiMessageData.getRaw() == null) {
            JPanel emptyMessage = new JPanel();
            emptyMessage.setMaximumSize(new Dimension(0, 0));
            return;
        }
        this.uiMessageData = uiMessageData;
        setLayout(new BorderLayout());
        setOpaque(false);
        createMessageLine();
    }

    private void createMessageLine() {
        JPanel realContent = new JPanel();
        realContent.setLayout(new BorderLayout());
        realContent.setOpaque(false);
        createRealContent(realContent);
        add(realContent, BorderLayout.CENTER);
    }

    private void createRealContent(JPanel container) {
        JComponent title = getHeader();
        if (title != null) {
            if (UIMessageType.SENT.equals(uiMessageData.getType())) {
                title.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            }
            container.add(title, BorderLayout.NORTH);
        }
        JComponent content = getContent();
        if (content != null) {
            container.add(content, BorderLayout.CENTER);
        }
        JComponent footer = getFooter();
        if (footer != null) {
            if (UIMessageType.SENT.equals(uiMessageData.getType())) {
                footer.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            }
            container.add(footer, BorderLayout.SOUTH);
        }
    }

    @Override
    public JComponent getHeader() {
        String headerText = uiMessageData.getRaw().getFrom();
        JTextPane header = new JTextPane();
        header.setEditable(false);
        header.setFont(new Font("Default", Font.PLAIN, 14));
        header.setForeground(ColorConstant.GRAY);
        header.setText(headerText);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        header.setOpaque(false);
        return header;
    }

    @Override
    public JComponent getAvatar() {
        JLabel avatar = new JLabel();
        avatar.setPreferredSize(new Dimension(32, 32));
        ImageLoader.loadImage(avatar, "http://ww1.sinaimg.cn/thumbnail/befc1efdgw1eszwx4utcbj2050050aa8.jpg");
        return avatar;
    }

    @Override
    public JComponent getFooter() {
        String footerText = uiMessageData.getLocalDateTime().toString().replace("T", " ")
                .replaceAll("\\.\\d+", "") + " " + uiMessageData.getType().toString();
        JTextPane footer = new JTextPane();
        footer.setEditable(false);
        footer.setFont(new Font("Default", Font.PLAIN, 12));
        footer.setForeground(ColorConstant.GRAY);
        footer.setText(footerText);
        footer.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        footer.setOpaque(false);
        return footer;
    }

    @Override
    public UIMessageData getUIMessageData() {
        return uiMessageData;
    }

    @Override
    public UIMessageType getUIMessageType() {
        return uiMessageData.getType();
    }

    @Override
    public float getXRatio() {
        return 0.7f;
    }
}
