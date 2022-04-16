package com.graduation.chat.config.intercepors;

import com.google.gson.Gson;
import com.graduation.chat.enumeration.AccessAuthorityEnum;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.RequestFacade;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 访问权限控制逻辑
 */
@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    public static String loginPath;

    @Value("${login.path}")
    public void setLoginPath(String loginPath) {
        LoginInterceptor.loginPath = loginPath;
    }

    /**
     * 访问权限控制数据
     */
    public static HashMap<String, AccessAuthorityEnum> authority = new HashMap<String, AccessAuthorityEnum>(){
        {
            put("/chatSystem", AccessAuthorityEnum.DEFAULT_ACCESS);
            put("/user", AccessAuthorityEnum.DEFAULT_ACCESS);
            put("/chat", AccessAuthorityEnum.DEFAULT_ACCESS);
        }
    };

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         * 0.通过访问路径设置重定向路径
         * (逻辑扩充之后可以提取出来重写为函数)
         */
        String redirect;
        String fullPath = request.getServletPath();
        String path;
        if(StringUtils.equals(fullPath,"/")){
            path = "/";
        }else{
            path = "/" + request.getServletPath().split("/")[1];
        }
        if((Objects.isNull(authority.get(fullPath)) ? authority.get(path) : authority.get(fullPath)) == AccessAuthorityEnum.ONLY_MANAGER){
            redirect = loginPath + "?active=settingPage";
        }else {
            redirect = loginPath;
        }
        /**
         * 1.获得cookie
         */
        Cookie[] cookies = request.getCookies();
        /**
         * 2.没有cookie信息，则重定向到登录界面
         */
        if (Objects.isNull(cookies)) {
            response.sendRedirect(redirect);
            return false;
        }
        /**
         * 3.获取cookie里面的一些用户信息
         */
        String cookie_userPhoneNumber = null;
        for (Cookie item : cookies) {
            if ("cookie_userPhoneNumber".equals(item.getName())) {
                cookie_userPhoneNumber = item.getValue();
                break;
            }
        }
        /**
         * 4.如果cookie为空，重定向到登录界面
         */
        if (StringUtils.isEmpty(cookie_userPhoneNumber)) {
            response.sendRedirect(redirect);
            return false;
        }
        /**
         * 5.获取redis中的HttpSession对象
         * todo: 实力有限这里是map
         */
        SessionDTO sessionDTO;
        try {
            Gson gson = new Gson();
            sessionDTO = gson.fromJson(redisTemplate.opsForValue().get(cookie_userPhoneNumber),SessionDTO.class);
        } catch(Exception e){
            response.sendRedirect(redirect);
            return false;
        }
        if (Objects.isNull(sessionDTO)) {
            response.sendRedirect(redirect);
            return false;
        }
        /**
         * 6.判断访问权限
         */
        if(sessionDTO.getType() != 0 && (Objects.isNull(authority.get(fullPath)) ? authority.get(path).getCode() & sessionDTO.getType() : authority.get(fullPath).getCode() & sessionDTO.getType()) == 0){
            response.sendRedirect(redirect);
            return false;
        }
        /**
         * 7.更新cookie存活时间
         */
        Cookie cookie = new Cookie("cookie_userPhoneNumber", cookie_userPhoneNumber);
        // 设置cookie的持久化时间
        cookie.setMaxAge(12 * 60 * 60);
        // 设置为当前项目下都携带这个cookie
        cookie.setPath("/");
        response.addCookie(cookie);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }

    /**
     * 用于接受来自redis反序列化出的数据
     */
    @Data
    public static class SessionDTO {
        private Long userID;
        private String phoneNumber;
        private String name;
        private Long type;
        private String userImg;
    }
}
