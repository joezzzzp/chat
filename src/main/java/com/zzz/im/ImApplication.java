package com.zzz.im;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zzz
 * @date 2019/8/21 11:11
 **/

public class ImApplication {

    private static final Map<String, String> argsMap = new HashMap<>(3);

    static {
        argsMap.put("--mode", "server");
        argsMap.put("--port", "8873");
        argsMap.put("--host", "localHost");
    }

    public static void main(String[] args) {
        for (String arg : args) {
            String[] keyValue = arg.split("=");
            if (keyValue.length == 2 && argsMap.keySet().contains(keyValue[0])) {
                argsMap.put(keyValue[0], keyValue[1]);
            }
        }
        String mode = argsMap.get("--mode");
        String host = argsMap.get("host");
        int port = Integer.parseInt(argsMap.get("port"));
        if (mode == "server") {

        }
    }
}
