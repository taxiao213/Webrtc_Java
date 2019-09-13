package com.demo;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class WebSocketMapUtil {
    public static ConcurrentMap<String, WebSocketServer> webSocketMap = new ConcurrentHashMap<>();

    public static void put(String key, WebSocketServer webSocketServer) {
        webSocketMap.put(key, webSocketServer);
    }

    public static WebSocketServer get(String key) {
        return webSocketMap.get(key);
    }

    public static void remove(String key) {
        webSocketMap.remove(key);
    }

    public static Collection<WebSocketServer> getValues() {
        return webSocketMap.values();
    }
}