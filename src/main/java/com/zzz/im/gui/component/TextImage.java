package com.zzz.im.gui.component;

import javax.swing.*;
import java.awt.*;

/**
 * @author created by zzz at 2019/9/24 18:27
 **/

public class TextImage extends JLabel {

    public TextImage(char content) {
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
        setPreferredSize(new Dimension(32, 32));
        setMaximumSize(new Dimension(32, 32));
        setFont(new Font("Default", Font.BOLD, 20));
        setForeground(Color.WHITE);
        setBackground(Color.CYAN);
        setOpaque(true);
        setText(new String(new char[]{content}));
    }
}
