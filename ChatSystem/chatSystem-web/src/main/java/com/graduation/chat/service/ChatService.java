package com.graduation.chat.service;

import com.graduation.chat.result.BaseResult;

public interface ChatService {
    /**
     * 查询聊天列表
     * @param phoneNumber
     * @return
     */
    BaseResult selectChatList(String phoneNumber,String userId);

    /**
     * 查询聊天列表
     * @param userId
     * @param chatUserId
     * @param page
     * @return
     */
    BaseResult selectChattingRecord(Long userId, Long chatUserId,Integer page);
}
