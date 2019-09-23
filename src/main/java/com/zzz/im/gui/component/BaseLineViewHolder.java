package com.zzz.im.gui.component;

import javax.swing.*;
import java.util.List;

/**
 * @author created by zzz at 2019/9/25 10:57
 **/

public abstract class BaseLineViewHolder<T extends JPanel> {

    private List<T> views;

    abstract JPanel createNewView();

    abstract void refreshView(JPanel view);
}
