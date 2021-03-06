package com.graduation.chat.mapper;

import com.graduation.chat.dto.ChattingRecordDTO;
import com.graduation.chat.result.ChatListResult;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatMapper {
    /**
     * 根据用户id查询通讯列表
     * @param userId
     * @return 消息对象列表，仅id号
     */
    List<Long> selectChatListToGetChatUserId(Long userId);

    /**
     * 根据用户id和聊天对象id查询通讯列表
     * @param userId
     * @param chatUserId
     * @return
     */
    ChatListResult selectChatList(Long userId,Long chatUserId);

    /**
     * 根据用户id和聊天对象id查询聊天记录
     * @param userId
     * @param chatUserId
     * @return
     */
    List<ChattingRecordDTO> selectChattingRecord(Long userId, Long chatUserId,Integer rows);

    /**
     * 设置消息已读
     * @param userId
     * @param chatUserId
     * @return
     */
    Integer updateUnreadMessage(Long userId, Long chatUserId);

    /**
     * 插入聊天记录
     * @param chattingRecordDTO
     * @return
     */
    Integer insertChattingRecord(ChattingRecordDTO chattingRecordDTO);
}
