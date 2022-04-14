package com.graduation.chat.result;

import lombok.Data;

/**
 * 聊天列表查询返回类
 */
@Data
public class ChatListResult {
    private Long id;

    /**
     * 接收方id(receiver)
     */
    private Long userInfoId;

    /**
     * 接收方名字
     */
    private String name;

    /**
     * 接收方头像图片
     */
    private String userImg;

    /**
     * 未读消息数量
     */
    private String unreadMessage;

    /**
     * 最后一条聊天记录
     */
    private String talkInfo;

    /**
     * 最后一条聊天记录时间
     */
    private Long talkTime;
}
