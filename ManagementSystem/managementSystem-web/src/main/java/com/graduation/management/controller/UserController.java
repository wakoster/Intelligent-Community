package com.graduation.management.controller;

import com.graduation.management.result.BaseResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Objects;

@Controller
@CrossOrigin
@RequestMapping(value = "/user")
public class UserController {
    /**
     * 获取用户名
     * @return
     */
    @RequestMapping(value = "/getUserName",method = RequestMethod.GET)
    @ResponseBody
    public BaseResult getUserName(HttpServletRequest request){
        HttpSession session = request.getSession();
        /**
         * 2.获取登录的用户信息
         */
        if(Objects.isNull(session.getAttribute("userSession"))){
            return BaseResult.FAIL((long) -1,"失去登录信息",null);
        }
        String userName = String.valueOf(((Map)session.getAttribute("userSession")).get("name"));
        return BaseResult.SUCCESS(userName);
    }
}
