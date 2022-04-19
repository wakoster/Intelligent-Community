package com.graduation.chat.controller;

import com.google.gson.Gson;
import com.graduation.chat.dto.ChattingRecordDTO;
import com.graduation.chat.mapper.ChatMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Service
@ServerEndpoint(value = "/websocket/{userId}")
public class WebSocketServer {

    /**
     * 用来解决webSocket中无法注入mapper
     */
    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        WebSocketServer.applicationContext = applicationContext;
    }
    @Resource
    ChatMapper chatMapper;

    private static final Map<String,Session> onlineSessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) throws IOException {
        // 对连接建立后的操作，这里添加会话或者对上线信息进行发送
        onlineSessions.put(userId,session);
    }

    /**
     * 关闭连接
     */
    @OnClose
    public void onClose(@PathParam("userId") String userId) {
        // 关闭连接时的操作，这里在关闭连接时会调用此方法，可以做退出提示
        onlineSessions.remove(userId);
    }

    /**
     * 发生错误时
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    /**
     * 消息发送（有点相像Controller层的方法参数设计，只是多了session）
     * @param jsonChattingRecordDTO 消息
     */
    @OnMessage
    public void OnMessage(Session session, String jsonChattingRecordDTO) throws IOException {
        ChattingRecordDTO chattingRecordDTO = new ChattingRecordDTO();
        Gson gson = new Gson();
        chattingRecordDTO = gson.fromJson(jsonChattingRecordDTO,ChattingRecordDTO.class);
        try{
            chatMapper = applicationContext.getBean(ChatMapper.class);
            chatMapper.insertChattingRecord(chattingRecordDTO);
        }catch (Exception ignored){
            return;
        }
        jsonChattingRecordDTO = gson.toJson(chattingRecordDTO);
        if(Objects.nonNull(chattingRecordDTO.getReceiverUserInfo())){
            session.getBasicRemote().sendText(jsonChattingRecordDTO);
            if(Objects.nonNull(onlineSessions.get(String.valueOf(chattingRecordDTO.getReceiverUserInfo())))) {
                onlineSessions.get(String.valueOf(chattingRecordDTO.getReceiverUserInfo())).getBasicRemote().sendText(jsonChattingRecordDTO);
            }
        }
    }
}

