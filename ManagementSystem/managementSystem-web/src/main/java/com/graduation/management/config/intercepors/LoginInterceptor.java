package com.graduation.management.config.intercepors;

import com.graduation.management.enumeration.AccessAuthorityEnum;
import com.graduation.management.result.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

/**
 * 访问权限控制逻辑
 */
@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    private RedisTemplate<String, HttpSession> redisTemplate;

    /**
     * 访问权限控制数据
     */
    public static HashMap<String, AccessAuthorityEnum> authority = new HashMap<String, AccessAuthorityEnum>(){
        {
            put("/homepage", AccessAuthorityEnum.DEFAULT_ACCESS);
            put("/settingPage", AccessAuthorityEnum.ONLY_MANAGER);
            put("/logout", AccessAuthorityEnum.DEFAULT_ACCESS);
            put("/pageTag", AccessAuthorityEnum.DEFAULT_ACCESS);
            put("/installationPackage", AccessAuthorityEnum.ONLY_MANAGER);
            put("/user", AccessAuthorityEnum.DEFAULT_ACCESS);
        }
    };


    //todo:此项目采用cookie加session双重登录验证，单项目运行时可正常使用，但此项目通常需要与其他组件共享登录状态，因本人实力不足，暂未解决共享session的需要，所以其他组件仅能通过cookie来判断登录状态，这是极为不安全的
    //todo:阅读建议https://www.cnblogs.com/andy-zhou/p/5360107.html，本文详解cookie和session
    //todo:为实现不同组件间共享cookie，在访问session时必须采用request.getSession(false)方法而不能使用request.getSession()方法，避免造成session失效导致频繁登录问题
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         * 0.通过访问路径设置重定向路径
         * (逻辑扩充之后可以提取出来重写为函数)
         */
        String redirect;
        String fullPath = request.getServletPath();
        String path = "/" + request.getServletPath().split("/")[1];
        if((Objects.isNull(authority.get(fullPath)) ? authority.get(path) : authority.get(fullPath)) == AccessAuthorityEnum.ONLY_MANAGER){
            redirect = "/login?active=settingPage";
        }else {
            redirect = "/login";
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
         * 4.获取HttpSession对象
         */
        HttpSession session = request.getSession(false);
        /**
         * 5.如果cookie或session为空或不相同，重定向到登录界面
         */
        if (StringUtils.isEmpty(cookie_userPhoneNumber)|| Objects.isNull(session) || Objects.isNull(session.getAttribute("userSession"))) {
            response.sendRedirect(redirect);
            return false;
        }
        /**
         * 6.判断访问权限
         */
        Map map = (Map) session.getAttribute("userSession");
        if((Long)map.get("type") != 0 && (Objects.isNull(authority.get(fullPath)) ? authority.get(path).getCode() & (Long)map.get("type") : authority.get(fullPath).getCode() & (Long)map.get("type")) == 0){
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
}
