package com.graduation.management.service.impl;

import com.google.gson.Gson;
import com.graduation.management.dto.UserInfoDTO;
import com.graduation.management.enumeration.AccessAuthorityEnum;
import com.graduation.management.mapper.UserInfoMapper;
import com.graduation.management.result.BaseResult;
import com.graduation.management.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserInfoMapper userInfoMapper;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public BaseResult selectUserInfoDoLogin(String phoneNumber, String password, AccessAuthorityEnum accessAuthorityEnum, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        /**
         * 1.判断输入是否为空
         */
        if (StringUtils.isEmpty(phoneNumber) || StringUtils.isEmpty(password) || Objects.isNull(accessAuthorityEnum)) {
            return BaseResult.FAIL((long) -1,"请填写账号或密码",null);
        }
        /**
         * 2.创建查询条件
         */
        UserInfoDTO userInfo = new UserInfoDTO();
        userInfo.setPhoneNumber(phoneNumber);
        /**
         * 3.查询用户
         */
        List<UserInfoDTO> userInfoDTOList = new ArrayList<>();
        try {
            userInfoDTOList = userInfoMapper.selectUserInfo(userInfo);
        } catch(Exception e){
            return BaseResult.ERROR((long) -1,e.getMessage(),null);
        }
        /**
         * 4.验证用户是否存在
         */
        if(Objects.isNull(userInfoDTOList) || userInfoDTOList.size() == 0){
            return BaseResult.FAIL((long) -1,"账号不存在",null);
        }
        userInfo = userInfoDTOList.get(0);
        /**
         * 5.验证密码
         * md5加密，盐：KEY
         */
        if(!StringUtils.equals(password, DigestUtils.md5DigestAsHex((userInfo.getPassword()+"KEY").getBytes()))){
            return BaseResult.FAIL((long) -1,"密码错误",null);
        }
        /**
         * 6.验证权限
         */
        if(userInfo.getType() != 0 && (userInfo.getType() & accessAuthorityEnum.getCode()) == 0){
            return BaseResult.FAIL((long) -1,"没有登录权限",null);
        }
        /**
         * 7.将登录用户信息保存到session中
         */
        Map map = new HashMap();
        map.put("userID",userInfo.getId());
        map.put("phoneNumber",userInfo.getPhoneNumber());
        map.put("name",userInfo.getName());
        map.put("type",userInfo.getType());
        map.put("userImg",userInfo.getUserImg());
        session.setAttribute("userSession",map);
        try {
            Gson gson = new Gson();
            redisTemplate.opsForValue().set(userInfo.getPhoneNumber(), gson.toJson(map),4 * 60 * 60, TimeUnit.SECONDS);
        } catch(Exception e){
            return BaseResult.ERROR((long) -1,"redis出错，请检查redis所在服务器",e.getMessage());
        }
        /**
         * 8.保存cookie
         */
        Cookie cookie = new Cookie("cookie_userPhoneNumber", userInfo.getPhoneNumber());
        // 设置cookie的持久化时间
        cookie.setMaxAge(12 * 60 * 60);
        // 设置为当前项目下都携带这个cookie
        cookie.setPath(request.getContextPath());
        /**
         * 9.向客户端发送cookie
         */
        response.addCookie(cookie);
        return BaseResult.SUCCESS(null);
    }
}
