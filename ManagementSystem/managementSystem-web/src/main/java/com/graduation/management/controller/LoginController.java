package com.graduation.management.controller;

import com.google.gson.Gson;
import com.graduation.management.enumeration.AccessAuthorityEnum;
import com.graduation.management.result.BaseResult;
import com.graduation.management.service.UserService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@CrossOrigin
public class LoginController {

    @Resource
    UserService userService;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 应用系统登录
     * @param params: phoneNumber,password
     * @param session
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/loginConsumer",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult loginConsumer(@RequestBody HashMap<String,Object> params, HttpSession session, HttpServletRequest request, HttpServletResponse response){
        return userService.selectUserInfoDoLogin((String) params.get("phoneNumber"),(String) params.get("password"), AccessAuthorityEnum.DEFAULT_ACCESS,session,request,response);
    }
    /**
     * 管理系统登录
     * @param params: phoneNumber,password
     * @param session
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/loginManager",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult loginManager(@RequestBody HashMap<String,Object> params, HttpSession session, HttpServletRequest request, HttpServletResponse response){
        return userService.selectUserInfoDoLogin((String) params.get("phoneNumber"),(String) params.get("password"), AccessAuthorityEnum.ONLY_MANAGER,session,request,response);
    }

    /**
     * 退出登录
     * @param session
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/logout")
    @ResponseBody
    public BaseResult logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        // 删除session里面的用户信息
        session.removeAttribute("userSession");
        // 清空redis中对应的信息
        Cookie[] cookies = request.getCookies();
        if (Objects.nonNull(cookies)) {
            for (Cookie item : cookies) {
                if ("cookie_userPhoneNumber".equals(item.getName())) {
                    redisTemplate.delete(item.getValue());
                    break;
                }
            }
        }
        // 清空cookie
        Cookie cookie = new Cookie("cookie_userPhoneNumber", null);
        cookie.setMaxAge(0);
        cookie.setPath(request.getContextPath());
        // 向客户端发送cookie
        response.addCookie(cookie);
        return BaseResult.SUCCESS(null);
    }
}
