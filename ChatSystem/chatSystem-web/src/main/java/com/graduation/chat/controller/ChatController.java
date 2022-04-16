package com.graduation.chat.controller;

import com.graduation.chat.result.BaseResult;
import com.graduation.chat.service.ChatService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;

@Controller
@CrossOrigin
@RequestMapping(value = "/chat")
public class ChatController {
    @Resource
    ChatService chatService;

    /**
     * 获取用户名
     * @return
     */
    @RequestMapping(value = "/getChatList",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult getChatList(@RequestBody HashMap<String,String> params){
        return chatService.selectChatList(params.get("phoneNumber"),params.get("userId"));
    }

    /**
     * 获取聊天记录
     * @return
     */
    @RequestMapping(value = "/getChattingRecord",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult getChattingRecord(@RequestBody HashMap<String,String> params){
        return chatService.selectChattingRecord(Long.valueOf(params.get("id")),Long.valueOf(params.get("chatUserId")),Integer.valueOf(params.get("page")));
    }
}
