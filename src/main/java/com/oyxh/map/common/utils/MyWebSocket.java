package com.oyxh.map.common.utils;


import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
 
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;


@Component
@ServerEndpoint(value = "/ws/myWebSocket")
@Slf4j
public class MyWebSocket {
    //每个客户端都会有相应的session,服务端可以发送相关消息
    private Session session;
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    //J.U.C包下线程安全的类，主要用来存放每个客户端对应的webSocket连接，为什么说他线程安全。在文末做简单介绍
    private static CopyOnWriteArraySet<MyWebSocket> copyOnWriteArraySet = new CopyOnWriteArraySet<MyWebSocket>();
 
 
    /**
     * 打开连接。进入页面后会自动发请求到此进行连接
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        copyOnWriteArraySet.add(this);
        log.info("websocket有新的连接, 总数:"+ copyOnWriteArraySet.size());
 
    }
 
    /**
     * 用户关闭页面，即关闭连接
     */
    @OnClose
    public void onClose() {
        copyOnWriteArraySet.remove(this);
        log.info("websocket连接断开, 总数:"+ copyOnWriteArraySet.size());
    }
 
    /**
     * 测试客户端发送消息，测试是否联通
     * @param message
     */
    @OnMessage
    public void onMessage(String message) {
        log.info("websocket收到客户端发来的消息:"+message);
    }
 
 
    /**
     * 出现错误
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误：" + error.getMessage(), session.getId());
        error.printStackTrace();
    }
 
    /**
     * 用于发送给客户端消息（群发）
     * @param message
     */
 
    public void sendMessage(String message) {
 
 
        //遍历客户端
        for (MyWebSocket webSocket : copyOnWriteArraySet) {
            log.info("websocket广播消息：" + message);
            try {
                //服务器主动推送
                webSocket.session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
 
    /**
     * 用于发送给指定客户端消息,这里写的不好。。不管了
     *
     * @param message
     */
    public void sendMessage(String sessionId, String message) throws IOException {
        Session session = null;
        MyWebSocket tempWebSocket = null;
        for (MyWebSocket webSocket : copyOnWriteArraySet) {
            if (webSocket.session.getId().equals(sessionId)) {
                tempWebSocket = webSocket;
                session = webSocket.session;
                break;
            }
        }
        if (session != null) {
            tempWebSocket.session.getBasicRemote().sendText(message);
        } else {
            log.warn("没有找到你指定ID的会话：{}", sessionId);
        }
    }
 
    /**
     * springboot内置tomcat的话，需要配一下这个。。如果没有这个对象，无法连接到websocket
     * 别问为什么。。很坑。。。
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        //这个对象说一下，貌似只有服务器是tomcat的时候才需要配置,具体我没有研究
        return new ServerEndpointExporter();
    }
 
 
}