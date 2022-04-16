package com.graduation.chat.dto;

import com.graduation.chat.domain.UserInfo;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

/**
 * ChattingRecord表数据类
 * 聊天信息
 */
@Data
public class ChattingRecordDTO {
    private Long id;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 发送方id
     */
    private Long senderUserInfo;

    /**
     * 接收方id
     */
    private Long receiverUserInfo;

    /**
     * 聊天记录
     */
    private String chattingRecord;

    /**
     * 是否被查看过
     */
    private String unreadMessage;

}
