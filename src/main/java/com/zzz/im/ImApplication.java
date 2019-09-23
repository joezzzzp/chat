package com.zzz.im;

import com.zzz.im.client.ImClient;
import com.zzz.im.gui.ImPanel;
import com.zzz.im.message.MessageData;
import com.zzz.im.message.MessageFactory;
import com.zzz.im.server.ImServer;
import io.netty.util.AttributeKey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author zzz
 * @date 2019/8/21 11:11
 **/

public class ImApplication {

    private static final Logger logger = LogManager.getLogger(ImApplication.class);

    private static final Map<String, String> argsMap = new HashMap<>(3);

    static {
        argsMap.put("--mode", "server");
        argsMap.put("--port", "8873");
        argsMap.put("--host", "localHost");
        argsMap.put("--user", "default");
    }

    public static void main(String[] args) {
        parseArgs(args);
        start();
    }

    private static void parseArgs(String[] args) {
        for (String arg : args) {
            String[] keyValue = arg.split("=");
            if (keyValue.length == 2 && argsMap.keySet().contains(keyValue[0])) {
                argsMap.put(keyValue[0], keyValue[1]);
            }
        }
    }

    private static void start() {
        String mode = argsMap.get("--mode");
        String host = argsMap.get("--host");
        int port = Integer.parseInt(argsMap.get("--port"));
        String user = argsMap.get("--user");
        MessageFactory.getInstance().setCurrentUser(user);
        if ("server".equalsIgnoreCase(mode)) {
            startServer(port);
            console();
        } else if ("client".equalsIgnoreCase(mode)) {
            initClient(host, port);
            showGUI();
        }
    }

    private static void startServer(int port) {
        new ImServer(port).start();
    }

    private static void console() {
        Scanner scanner = new Scanner(System.in);
        String line;
        while (true) {
            line = scanner.nextLine();
            if ("exit".equals(line)) {
                break;
            }
            if ("detail".equals(line)) {
                ImServer.CLIENT_POOL.forEach((key, value) ->
                        logger.info(key + " " + value.attr(AttributeKey.valueOf("userName")).get()));
            }
            if ("count".equals(line)) {
                logger.info(ImServer.CLIENT_POOL.size());
            }
        }
    }

    private static void initClient(String host, int port) {
        ImClient imClient = ImClient.getInstance();
        imClient.setHost(host);
        imClient.setPort(port);
        imClient.setCurrentUser(MessageFactory.getInstance().getCurrentUser());
        imClient.init();
    }

    private static void showGUI() {
        ImClient imClient = ImClient.getInstance();
        SwingUtilities.invokeLater(() -> {
            JFrame jFrame = new JFrame(MessageFactory.getInstance().getCurrentUser());
            jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    imClient.close();
                }

                @Override
                public void windowOpened(WindowEvent e) {
                    imClient.start();
                }
            });
            Dimension windowSize = new Dimension(800, 600);
            jFrame.setPreferredSize(windowSize);
            jFrame.setBounds(200, 50, windowSize.width, windowSize.height);
            ImPanel imPanel = new ImPanel();
            jFrame.setContentPane(imPanel);
            jFrame.pack();
            jFrame.setVisible(true);
            imClient.bindUI(imPanel);
        });
    }
}
