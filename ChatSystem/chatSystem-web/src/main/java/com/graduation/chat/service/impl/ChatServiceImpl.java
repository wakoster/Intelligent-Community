package com.graduation.chat.service.impl;

import com.graduation.chat.dto.ChattingRecordDTO;
import com.graduation.chat.dto.UserInfoDTO;
import com.graduation.chat.mapper.ChatMapper;
import com.graduation.chat.mapper.UserInfoMapper;
import com.graduation.chat.result.BaseResult;
import com.graduation.chat.result.ChatListResult;
import com.graduation.chat.result.ContactResult;
import com.graduation.chat.service.ChatService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ChatServiceImpl implements ChatService {
    @Resource
    UserInfoMapper userInfoMapper;

    @Resource
    ChatMapper chatMapper;

    @Override
    public BaseResult selectChatList(String phoneNumber, String userId) {
        /**
         * 1.判断输入是否为空
         */
        if (StringUtils.isEmpty(phoneNumber)) {
            return BaseResult.FAIL((long) -1,"请输入电话号码",null);
        }
        /**
         * 2.查询用户
         */
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        try {
            userInfoDTO = userInfoMapper.selectUserInfoByPhoneNumber(phoneNumber);
            /**
             * 3.验证用户是否存在
             */
            if(Objects.isNull(userInfoDTO)){
                return BaseResult.FAIL((long) -1,"账号不存在",null);
            }
        } catch(Exception e){
            return BaseResult.ERROR((long) -1,e.getMessage(),null);
        }
        /**
         * 3.查询与当前用户有过聊天记录的用户
         */
        List<Long> chatUserIdList = new ArrayList<>();
        try {
            chatUserIdList = chatMapper.selectChatListToGetChatUserId(userInfoDTO.getId());
        } catch(Exception e){
            return BaseResult.ERROR((long) -1,e.getMessage(),null);
        }
        List<ChatListResult> chatListResultList = new ArrayList<>();
        /**
         * 4.如果userId不为空，说明是从联系人界面发起的聊天
         * 则判断是否以前进行过聊天，没有则创建
         */
        if(!StringUtils.isEmpty(userId) && !chatUserIdList.contains(Long.valueOf(userId))){
            ContactResult contactResult = userInfoMapper.selectUserInfoById(Long.valueOf(userId));
            ChatListResult chatListResult = new ChatListResult();
            chatListResult.setId((long) 0);
            chatListResult.setUserId(contactResult.getId());
            chatListResult.setUserName(contactResult.getName());
            chatListResult.setUserImg(contactResult.getUserImg());
            chatListResultList.add(chatListResult);
        }
        /**
         * 5.获取聊天列表
         */
        for(Long chatUserId:chatUserIdList){
            try {
                chatListResultList.add(chatMapper.selectChatList(userInfoDTO.getId(),chatUserId));
            } catch(Exception e){
                return BaseResult.ERROR((long) -1,e.getMessage(),null);
            }
        }
        return BaseResult.SUCCESS(chatListResultList);
    }

    @Override
    public BaseResult selectChattingRecord(Long userId, Long chatUserId, Integer page) {
        List<ChattingRecordDTO> chattingRecordDTOList = new ArrayList<>();
        /**
         * 1.查询消息
         */
        try {
            chattingRecordDTOList = chatMapper.selectChattingRecord(userId,chatUserId,20*page);
        } catch(Exception e){
            return BaseResult.ERROR((long) -1,e.getMessage(),null);
        }
        /**
         * 2.设置已读
         */
        try {
            chatMapper.updateUnreadMessage(userId,chatUserId);
        } catch(Exception e){
            return BaseResult.ERROR((long) -1,e.getMessage(),null);
        }
        return BaseResult.SUCCESS(chattingRecordDTOList);
    }

    @Override
    public BaseResult updateReadMessage(Long userId, Long chatUserId) {
        try {
            chatMapper.updateUnreadMessage(userId,chatUserId);
        } catch(Exception e){
            return BaseResult.ERROR((long) -1,e.getMessage(),null);
        }
        return BaseResult.SUCCESS(null);
    }
}
