package com.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class WebSocketMapUtil {
    private static final Logger log = LoggerFactory.getLogger(WebSocketMapUtil.class);

    public static ConcurrentMap<String, WebSocketServer> webSocketMap = new ConcurrentHashMap<>();

    public static void put(String key, WebSocketServer webSocketServer) {
        log.info("userId: {} 注册成功", key);
        webSocketMap.put(key, webSocketServer);
    }

    public static WebSocketServer get(String key) {
        WebSocketServer webSocketServer = webSocketMap.get(key);

        if (webSocketServer == null) {
            log.error("userId: {} 不存在", key);
        }
        return webSocketServer;
    }

    public static void remove(String key) {
        webSocketMap.remove(key);
    }

    public static Collection<WebSocketServer> getValues() {
        return webSocketMap.values();
    }
}