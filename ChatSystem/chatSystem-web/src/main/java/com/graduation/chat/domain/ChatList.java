package com.graduation.chat.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * chatList表实体类
 * 聊天简略信息
 */
@Data
@Entity
@IdClass(ChatList.ChatListUPK.class)
public class ChatList {
    /**
     * 创建时间
     */
    @Column(updatable = false, nullable = false)
    private Long first_talk_time;

    /**
     * 发送方id
     */
    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    private UserInfo sender_user_info;

    /**
     * 接收方id
     */
    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    private UserInfo receiver_user_info;

    @Data
    static class ChatListUPK implements Serializable {
        private UserInfo sender_user_info;
        private UserInfo receiver_user_info;
    }
}
