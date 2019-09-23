package com.zzz.im.gui.message;

import com.zzz.im.gui.data.UIMessageData;

import javax.swing.*;
import java.awt.*;

/**
 * @author created by zzz at 2019/9/24 14:39
 **/

public class TimeMessage extends BaseMessage {

    public TimeMessage(UIMessageData messageData) {
        super(messageData);
    }

    @Override
    public JComponent getContent() {
        JTextArea time = new JTextArea();
        time.setEditable(false);
        time.setFont(new Font("Default", Font.PLAIN, 12));
        return time;
    }
}
