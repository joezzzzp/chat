package com.zzz.im.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author created by zzz at 2019/9/25 16:28
 **/

public class ImageLoader {

    private static final Logger logger = LogManager.getLogger(ImageLoader.class);

    private static final ExecutorService executor = Executors.newFixedThreadPool(5);

    private static final Map<String, ImageIcon> cache = new HashMap<>();

    public static void loadImage(JLabel imageView, String imageUrl) {
        executor.submit(() -> {
            try {
                ImageIcon icon;
                if (cache.containsKey(imageUrl)) {
                    icon = cache.get(imageUrl);
                } else {
                    URL imageUrl1 = new URL(imageUrl);
                    icon = new ImageIcon(imageUrl1);
                    cache.put(imageUrl, icon);
                }
                if (icon != null) {
                    fireImageLoadFinish(imageView, icon);
                }
            } catch (MalformedURLException e) {
                logger.error("无法解析图片URL", e);
            }
        });
    }

    private static void fireImageLoadFinish(JLabel imageView, ImageIcon imageIcon) {
        SwingUtilities.invokeLater(() -> imageView.setIcon(imageIcon));
    }
}
