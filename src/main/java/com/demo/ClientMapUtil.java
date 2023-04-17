package com.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ClientMapUtil {
    private static final Logger log = LoggerFactory.getLogger(ClientMapUtil.class);

    public static ConcurrentMap<String, String> webSocketMap = new ConcurrentHashMap<>();

    public static void put(String key, String value) {
        log.info("客户端id = {} 注册成功", key);
        webSocketMap.put(key, value);
    }

    public static String get(String key) {
        String s = webSocketMap.get(key);

        if (s == null) {
            log.error("客户端id = {} 不存在", key);
        }

        return s;
    }

    public static void remove(String key) {
        webSocketMap.remove(key);
    }

    public static Collection<String> getValues() {
        return webSocketMap.values();
    }
}