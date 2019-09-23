package com.zzz.im.gui.message;

import com.zzz.im.gui.data.UIMessageData;
import com.zzz.im.gui.data.UIMessageType;

import javax.swing.*;

/**
 * 聊天框信息
 * @author created by zzz at 2019/9/23 16:44
 **/

public interface Message {

    /**
     * 获取头部组件
     * @return 头部组件
     */
    JComponent getHeader();

    /**
     * 获取头像组件
     * @return 头像组件
     */
    JComponent getAvatar();

    /**
     * 获取内容组件
     * @return 内容组件
     */
    JComponent getContent();

    /**
     * 获取底部组件
     * @return 底部组件
     */
    JComponent getFooter();

    /**
     * 获取UIMessage
     * @return 本信息的原始UIMessage
     */
    UIMessageData getUIMessageData();

    /**
     * 获取消息类型
     * @return 消息类型
     */
    UIMessageType getUIMessageType();

    /**
     * 获取Message所占行的比例
     * @return 比例
     */
    float getXRatio();
}
