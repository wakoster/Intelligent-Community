package com.graduation.chat.service.impl;

import com.graduation.chat.config.intercepors.LoginInterceptor;
import com.graduation.chat.result.ContactResult;
import com.graduation.chat.dto.UserInfoDTO;
import com.graduation.chat.mapper.UserInfoMapper;
import com.graduation.chat.result.BaseResult;
import com.graduation.chat.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserInfoMapper userInfoMapper;

    @Override
    public BaseResult changePassword(String phoneNumber, String oldPassword, String newPassword) {
        /**
         * 1.判断输入是否为空
         */
        if (StringUtils.isEmpty(phoneNumber) || StringUtils.isEmpty(oldPassword) || StringUtils.isEmpty(newPassword)) {
            return BaseResult.FAIL((long) -1,"输入缺失（输入应包括用户电话号码，新旧密码）",null);
        }
        /**
         * 2.查询用户
         */
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        try {
            userInfoDTO = userInfoMapper.selectUserInfoByPhoneNumber(phoneNumber);
        } catch(Exception e){
            return BaseResult.ERROR((long) -1,e.getMessage(),null);
        }
        /**
         * 3.验证用户是否存在
         */
        if(Objects.isNull(userInfoDTO)){
            return BaseResult.FAIL((long) -1,"账号不存在",null);
        }
        /**
         * 4.判断旧密码是否正确
         */
        if(!StringUtils.equals(oldPassword, DigestUtils.md5DigestAsHex((userInfoDTO.getPassword()+"KEY").getBytes()))){
            return BaseResult.FAIL((long) -1,"旧密码错误",null);
        }
        /**
         * 5.更新密码
         */
        try {
            userInfoMapper.updatePassword(userInfoDTO.getId(),newPassword);
        } catch(Exception e){
            return BaseResult.ERROR((long) -1,e.getMessage(),null);
        }
        return BaseResult.SUCCESS(null);
    }

    @Override
    public BaseResult changeInformation(String phoneNumber, String userImg, String mailbox, String address, String sign) {
        /**
         * 1.判断输入是否为空
         */
        if (StringUtils.isEmpty(phoneNumber)) {
            return BaseResult.FAIL((long) -1,"电话号码输入有误",null);
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
         * 4.组装更新内容
         */
        UserInfoDTO userInfo = new UserInfoDTO();
        userInfo.setId(userInfoDTO.getId());
        userInfo.setUserImg(userImg);
        userInfo.setMailbox(mailbox);
        userInfo.setAddress(address);
        userInfo.setSign(sign);
        /**
         * 5.执行更新
         */
        try {
            userInfoMapper.updateUserInfo(userInfo);
        } catch(Exception e){
            return BaseResult.ERROR((long) -1,e.getMessage(),null);
        }
        return BaseResult.SUCCESS(null);
    }

    @Override
    public BaseResult selectInformationByPhoneNumber(String phoneNumber) {
        /**
         * 1.查询信息
         */
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        try {
            userInfoDTO = userInfoMapper.selectUserInfoByPhoneNumber(phoneNumber);
        } catch(Exception e){
            return BaseResult.ERROR((long) -1,e.getMessage(),null);
        }
        /**
         * 2.按前端需要封装
         */
        HashMap<String,Object> map = new HashMap<>();
        if(Objects.nonNull(userInfoDTO)) {
            map.put("userImg", userInfoDTO.getUserImg());
            map.put("mailbox", userInfoDTO.getMailbox());
            map.put("address", userInfoDTO.getAddress());
            map.put("sign", userInfoDTO.getSign());
        }
        return BaseResult.SUCCESS(map);
    }

    @Override
    public BaseResult selectUserList(String phoneNumber, String name) {
        List<HashMap<String,Object>> result = new ArrayList<>();
        /**
         * 1.查询信息
         */
        List<ContactResult> contactResultList = new ArrayList<>();
        try {
            contactResultList = userInfoMapper.selectUserListByNameWithoutPhoneNumber(phoneNumber,name);
        } catch(Exception e){
            return BaseResult.ERROR((long) -1,e.getMessage(),null);
        }
        /**
         * 2.将数据分组
         */
        HashMap<String,List<ContactResult>> contactMap = new HashMap<>();
        for(ContactResult contactResult : contactResultList){
            List<ContactResult> contactResults = contactMap.get(contactResult.getDepartment());
            if(Objects.isNull(contactResults)){
                contactResults = new ArrayList<>();
            }
            contactResults.add(contactResult);
            contactMap.put(contactResult.getDepartment(), contactResults);
        }
        for(HashMap.Entry<String,List<ContactResult>> entry : contactMap.entrySet()){
            HashMap<String,Object> map = new HashMap<>();
            map.put("groupName", StringUtils.isEmpty(entry.getKey())?"默认分组":entry.getKey());
            map.put("list", entry.getValue());
            result.add(map);
        }
        return BaseResult.SUCCESS(result);
    }

    @Override
    public BaseResult selectUserInfoById(Long id) {
        /**
         * 1.查询信息
         */
        ContactResult contactResult = new ContactResult();
        try {
            contactResult = userInfoMapper.selectUserInfoById(id);
        } catch(Exception e){
            return BaseResult.ERROR((long) -1,e.getMessage(),null);
        }
        return BaseResult.SUCCESS(contactResult);
    }

    @Override
    public BaseResult selectSelfUserInfo(HttpServletRequest request, HttpServletResponse response){
        /**
         * 1.获得cookie
         */
        Cookie[] cookies = request.getCookies();
        /**
         * 2.获取cookie里面的电话号码
         */
        String cookie_userPhoneNumber = null;
        for (Cookie item : cookies) {
            if ("cookie_userPhoneNumber".equals(item.getName())) {
                cookie_userPhoneNumber = item.getValue();
                break;
            }
        }
        /**
         * 3.查询用户
         */
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        try {
            userInfoDTO = userInfoMapper.selectUserInfoByPhoneNumber(cookie_userPhoneNumber);
            /**
             * 4.验证用户是否存在
             */
            if(Objects.isNull(userInfoDTO)){
                return BaseResult.FAIL((long) -1,"请重新登录",null);
            }
        } catch(Exception e){
            return BaseResult.ERROR((long) -1,e.getMessage(),null);
        }
        Map<String,Object> map = new HashMap<>();
        /**
         * 5.组装
         */
        map.put("id",userInfoDTO.getId());
        map.put("phoneNumber",userInfoDTO.getPhoneNumber());
        map.put("name",userInfoDTO.getName());
        map.put("userImg",userInfoDTO.getUserImg());
        map.put("mailbox",userInfoDTO.getMailbox());
        map.put("address",userInfoDTO.getAddress());
        map.put("sign",userInfoDTO.getSign());
        map.put("department",userInfoDTO.getDepartment());
        return BaseResult.SUCCESS(map);
    }
}
