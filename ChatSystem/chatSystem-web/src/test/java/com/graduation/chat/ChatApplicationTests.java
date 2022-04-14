package com.graduation.chat;

import com.graduation.chat.result.BaseResult;
import com.graduation.chat.service.ChatService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class ChatApplicationTests {
    @Resource
    ChatService chatService;
    @Test
    public void test(){
        BaseResult baseResult = chatService.selectChatList("15110322010");
        int i = 1;
    }
}
