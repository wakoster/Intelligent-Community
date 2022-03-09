package com.graduation.management.service.impl;

import com.graduation.management.dto.UserInfoDTO;
import com.graduation.management.enumeration.AccessAuthorityEnum;
import com.graduation.management.mapper.UserInfoMapper;
import com.graduation.management.result.BaseResult;
import com.graduation.management.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserInfoMapper userInfoMapper;

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
        List<UserInfoDTO> userInfos = null;
        try {
            userInfos = userInfoMapper.selectUserInfo(userInfo);
        } catch(Exception e){
            return BaseResult.FAIL((long) -1,e.getMessage(),null);
        }
        /**
         * 4.验证用户是否存在
         */
        if(Objects.isNull(userInfos) || userInfos.size() == 0){
            return BaseResult.FAIL((long) -1,"账号不存在",null);
        }
        userInfo = userInfos.get(0);
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
        /**
         * 8.保存cookie
         */
        Cookie cookie = new Cookie("cookie_username", userInfo.getName());
        // 设置cookie的持久化时间
        cookie.setMaxAge(2 * 60 * 60);
        // 设置为当前项目下都携带这个cookie
        cookie.setPath(request.getContextPath());
        /**
         * 9.向客户端发送cookie
         */
        response.addCookie(cookie);
        response.addCookie(new Cookie("cookie", userInfo.getPhoneNumber()));

        return BaseResult.SUCCESS(null);
    }
}
