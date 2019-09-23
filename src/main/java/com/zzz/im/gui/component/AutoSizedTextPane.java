package com.zzz.im.gui.component;

import com.zzz.im.utils.StringUtils;
import sun.font.FontDesignMetrics;

import javax.swing.*;
import java.awt.*;

/**
 * @author created by zzz at 2019/9/26 10:44
 **/

public class AutoSizedTextPane extends JTextPane {

    private int maxWidth;

    public AutoSizedTextPane(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    @Override
    public void setText(String t) {
        if (StringUtils.isEmptyText(t)) {
            return;
        }
        FontMetrics fm = FontDesignMetrics.getMetrics(getFont());
        Insets insets = getInsets();
        int maxContentWidth = maxWidth - insets.left - insets.right;
        t = t.replaceFirst("\r\n", "\n");
        int[] ret = rowCountAndMaxWidth(fm, t.split("\n"), maxContentWidth / fm.charWidth(t.charAt(0)));
        int finalHeight = fm.getHeight() * ret[0] + insets.top + insets.bottom;
        setPreferredSize(new Dimension(ret[1] + insets.left + insets.right, finalHeight));
        super.setText(t);
    }

    private int[] rowCountAndMaxWidth(FontMetrics fm, String[] lines, int maxCharNumber) {
        int rowCount = lines.length;
        int finalWidth = 0;
        for (String line : lines) {
            if (StringUtils.isEmpty(line)) {
                continue;
            }
            int finalNumber;
            int charNumber = line.length();
            if (charNumber >= maxCharNumber) {
                finalNumber = maxCharNumber;
                if (charNumber > maxCharNumber) {
                    rowCount += charNumber / maxCharNumber;
                }
            } else {
                finalNumber = charNumber;
            }
            int lineWidth = finalNumber * fm.charWidth(line.charAt(0));
            if (lineWidth > finalWidth) {
                finalWidth = lineWidth;
            }
        }
        return new int[]{rowCount, finalWidth};
    }


}
