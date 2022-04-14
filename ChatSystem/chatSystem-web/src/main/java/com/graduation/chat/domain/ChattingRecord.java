package com.graduation.chat.domain;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

/**
 * ChattingRecord表实体类
 * 聊天信息
 */
@Data
@Entity
public class ChattingRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 创建时间
     */
    @Column(updatable = false, nullable = false)
    private Long create_time;

    /**
     * 发送方id
     */
    @OneToOne(cascade = CascadeType.ALL)
    private UserInfo sender_user_info;

    /**
     * 接收方id
     */
    @OneToOne(cascade = CascadeType.ALL)
    private UserInfo receiver_user_info;

    /**
     * 聊天记录
     */
    @Column(nullable = false,updatable = false)
    private String chatting_record;

    /**
     * 是否被查看过
     */
    @Column(nullable = false,updatable = false)
    @ColumnDefault("false")
    private String unread_message;

}
