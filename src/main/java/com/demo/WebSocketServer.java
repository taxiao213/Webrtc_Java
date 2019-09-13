package com.demo;

import com.google.gson.Gson;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * websocket 服务端
 * Created by taxiao on 2019/8/14
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 * 微信公众号:他晓
 */
@ServerEndpoint(value = "/websocket")
@Component
public class WebSocketServer {

    private Session session;

    /**
     * 连接建立后触发的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        System.out.println("onOpen" + session.getId());
        WebSocketMapUtil.put(session.getId(), this);
    }


    /**
     * 连接关闭后触发的方法
     */

    @OnClose
    public void onClose() {
        //从map中删除
        WebSocketMapUtil.remove(session.getId());
        Set<Map.Entry<String, String>> entries = ClientMapUtil.webSocketMap.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            String value = next.getValue();
            if (StringUtils.pathEquals(session.getId(), value)) {
                iterator.remove();
            }
        }
        System.out.println("====== onClose:" + session.getId() + " ======");
    }


    /**
     * 接收到客户端消息时触发的方法
     */

    @OnMessage
    public void onMessage(String params, Session session) {
        //获取服务端到客户端的通道
        WebSocketServer myWebSocket = WebSocketMapUtil.get(session.getId());
        System.out.println("收到来自" + session.getId() + "的消息" + params);
        String result = "收到来自" + session.getId() + "的消息" + params;
        //返回消息给Web Socket客户端（浏览器）
//        myWebSocket.sendMessage(1, "成功", result);
        if (!StringUtils.isEmpty(params)) {
            Gson gson = new Gson();
            try {
                Model modelTest = gson.fromJson(params, Model.class);
                if (modelTest != null) {
                    String id = modelTest.getId();
                    if (!StringUtils.isEmpty(id)) {
                        switch (id) {
                            case Constant.REGISTER:
                                register(myWebSocket, modelTest);
                                break;
                            case Constant.CALL:
                                call(myWebSocket, modelTest);
                                break;
                            case Constant.INCALL:
                                incall(modelTest);
                                break;
                            case Constant.OFFER:
                                offer(modelTest);
                                break;
                            case Constant.CANDIDATE:
                                candidate(modelTest);
                                break;
                        }
                    }
                }
            } catch (Exception e) {

            }
        }
    }

    /**
     * 发生错误时触发的方法
     */

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println(session.getId() + "连接发生错误" + error.getMessage());
        error.printStackTrace();
    }


    public void sendMessage(int status, String message, Object datas) throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("status", status);
        params.put("message", message);
        params.put("datas", datas);
        this.session.getBasicRemote().sendText(new Gson().toJson(params));
    }

    private void sendMessage(Model message) {
        try {
            this.session.getBasicRemote().sendText(new Gson().toJson(message));
        } catch (Exception e) {

        }
    }

    /**
     * 注册
     *
     * @param myWebSocket
     * @param modelTest
     */
    private void register(WebSocketServer myWebSocket, Model modelTest) {
        String from = modelTest.getFrom();
        String sessionFrom = ClientMapUtil.webSocketMap.get(from);
        Model test = new Model();
        test.setId(Constant.REGISTER_RESPONSE);
        if (StringUtils.isEmpty(sessionFrom)) {
            ClientMapUtil.webSocketMap.put(from, myWebSocket.session.getId());
            test.setIsSucceed(Constant.RESPONSE_SUCCEED);
        } else {
            test.setIsSucceed(Constant.RESPONSE_FAILURE);
        }
        myWebSocket.sendMessage(test);
    }

    /**
     * 呼叫
     *
     * @param myWebSocket
     * @param modelTest
     */
    private void call(WebSocketServer myWebSocket, Model modelTest) {
        String to = modelTest.getTo();
        String sessionTo = ClientMapUtil.get(to);
        Model test = new Model();
        if (StringUtils.isEmpty(sessionTo)) {
            test.setId(Constant.CALL_RESPONSE);
            test.setIsSucceed(Constant.RESPONSE_FAILURE);
            myWebSocket.sendMessage(test);
        } else {
            WebSocketServer socketServer = WebSocketMapUtil.get(sessionTo);
            if (socketServer != null) {
                BeanUtils.copyProperties(modelTest, test);
                test.setId(Constant.INCALL);
                socketServer.sendMessage(test);
            }
        }
    }

    /**
     * 被呼叫
     *
     * @param modelTest
     */
    private void incall(Model modelTest) {
        String to = modelTest.getTo();
        String sessinTo = ClientMapUtil.get(to);
        if (!StringUtils.isEmpty(sessinTo)) {
            WebSocketServer socketServer = WebSocketMapUtil.get(sessinTo);
            if (socketServer != null) {
                Model test = new Model();
                BeanUtils.copyProperties(modelTest, test);
                test.setId(Constant.INCALL_RESPONSE);
                socketServer.sendMessage(test);
            }
        }
    }

    /**
     * 发送流
     *
     * @param modelTest
     */
    private void offer(Model modelTest) {
        String to = modelTest.getTo();
        String sessinTo = ClientMapUtil.get(to);
        if (!StringUtils.isEmpty(sessinTo)) {
            WebSocketServer socketServer = WebSocketMapUtil.get(sessinTo);
            if (socketServer != null)
                socketServer.sendMessage(modelTest);
        }
    }

    /**
     * ice交互
     *
     * @param modelTest
     */
    private void candidate(Model modelTest) {
        String to = modelTest.getTo();
        String sessinTo = ClientMapUtil.get(to);
        if (!StringUtils.isEmpty(sessinTo)) {
            WebSocketServer socketServer = WebSocketMapUtil.get(sessinTo);
            if (socketServer != null)
                socketServer.sendMessage(modelTest);
        }
    }


}