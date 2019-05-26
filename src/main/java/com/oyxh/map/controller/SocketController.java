package com.oyxh.map.controller;

import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oyxh.map.common.utils.MyWebSocket;

@RestController
public class SocketController {
    @Resource
    MyWebSocket myWebSocket;
 
    @RequestMapping("many")
    public String helloManyWebSocket(){
        //向所有人发送消息
        myWebSocket.sendMessage("你好~！");
 
        return "发送成功";
    }
 
    @RequestMapping("one")
    public String helloOneWebSocket(String sessionId) throws IOException {
        //向某个人发送消息
        myWebSocket.sendMessage(sessionId,"你好~！，单个用户");
 
        return "发送成功";
    }
 
 

}