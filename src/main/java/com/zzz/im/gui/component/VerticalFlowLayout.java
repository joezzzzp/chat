package com.zzz.im.gui.component;

import java.awt.*;

/**
 * @author created by zzz at 2019/9/26 17:38
 **/

public class VerticalFlowLayout extends FlowLayout {

    @Override
    public Dimension preferredLayoutSize(Container target) {
        Component[] components = target.getComponents();
        int height = 0;
        for (Component component : components) {
            Dimension componentDimension = component.getPreferredSize();
            height += componentDimension.height;
        }
        int width = target.getWidth();
        return new Dimension(width, height);
    }

    @Override
    public void layoutContainer(Container target) {
        synchronized (target.getTreeLock()) {
            int currentHeight = 0;
            for (int i = 0; i < target.getComponentCount(); i++) {
                Component component = target.getComponent(i);
                Dimension containerSize = target.getSize();
                Dimension componentSize = component.getPreferredSize();
                Insets containerInsets = target.getInsets();
                if (containerSize.width > componentSize.width) {
                    Dimension realPreferredSize =
                            new Dimension(containerSize.width - containerInsets.left - containerInsets.right,
                            componentSize.height) ;
                    component.setPreferredSize(realPreferredSize);
                }
                component.setSize(component.getPreferredSize());
                component.setLocation(containerInsets.left, currentHeight);
                currentHeight += component.getPreferredSize().height;
            }
        }
    }


}
