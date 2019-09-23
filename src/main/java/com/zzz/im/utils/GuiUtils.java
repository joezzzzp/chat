package com.zzz.im.utils;

import java.awt.*;

/**
 * @author created by zzz at 2019/9/19 19:03
 **/

public class GuiUtils {

    public static Dimension calDimension(Container component) {
        Dimension size = component.getPreferredSize();
        Insets insets = component.getInsets();
        int width = size.width - insets.left - insets.right;
        int height = size.height - insets.top - insets.bottom;
        return new Dimension(width, height);
    }
}
