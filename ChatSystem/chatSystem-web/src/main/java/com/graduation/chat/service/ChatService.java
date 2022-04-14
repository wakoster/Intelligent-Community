package com.graduation.chat.service;

import com.graduation.chat.result.BaseResult;

public interface ChatService {
    /**
     * 查询聊天列表
     * @param phoneNumber
     * @return
     */
    BaseResult selectChatList(String phoneNumber);
}
