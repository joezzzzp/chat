package com.zzz.im.gui.message;

import com.zzz.im.gui.component.AutoSizedTextPane;
import com.zzz.im.gui.constant.ColorConstant;
import com.zzz.im.gui.data.UIMessageData;
import com.zzz.im.gui.data.UIMessageType;

import javax.swing.*;
import java.awt.*;

/**
 * @author created by zzz at 2019/9/23 18:02
 **/

public class TextMessage extends BaseMessage{

    public TextMessage(UIMessageData uiMessageData) {
        super(uiMessageData);
    }

    @Override
    public JComponent getContent() {
        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());
        content.setOpaque(false);
        String contentText = getUIMessageData().getRaw().getRawContent();
        AutoSizedTextPane textPane = new AutoSizedTextPane(300);
        content.setFont(new Font("Default", Font.PLAIN, 16));
        textPane.setEditable(false);
        textPane.setText(contentText);

        JPanel space = new JPanel();
        space.setOpaque(false);
        space.setPreferredSize(new Dimension(0, textPane.getPreferredSize().height));
        content.add(space, BorderLayout.CENTER);
        String textArea;
        if (UIMessageType.SENT.equals(getUIMessageType())) {
            textPane.setBackground(ColorConstant.LIGHT_GREEN);
            textArea = BorderLayout.EAST;
        } else {
            textPane.setBackground(ColorConstant.WHITE);
            textArea = BorderLayout.WEST;
        }
        content.add(textPane, textArea);
        return content;
    }
}

