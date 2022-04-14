package com.graduation.chat.service.impl;

import com.graduation.chat.dto.UserInfoDTO;
import com.graduation.chat.mapper.ChatMapper;
import com.graduation.chat.mapper.UserInfoMapper;
import com.graduation.chat.result.BaseResult;
import com.graduation.chat.result.ChatListResult;
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
    public BaseResult selectChatList(String phoneNumber) {
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
        /**
         * 获取聊天列表
         */
        List<ChatListResult> chatListResultList = new ArrayList<>();
        for(Long chatUserId:chatUserIdList){
            try {
                chatListResultList.add(chatMapper.selectChatList(userInfoDTO.getId(),chatUserId));
            } catch(Exception e){
                return BaseResult.ERROR((long) -1,e.getMessage(),null);
            }
        }
        return BaseResult.SUCCESS(chatListResultList);
    }
}
