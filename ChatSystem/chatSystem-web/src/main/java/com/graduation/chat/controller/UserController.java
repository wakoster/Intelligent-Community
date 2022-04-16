package com.graduation.chat.controller;

import com.graduation.chat.config.intercepors.LoginInterceptor;
import com.graduation.chat.result.BaseResult;
import com.graduation.chat.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@Controller
@CrossOrigin
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    UserService userService;

    /**
     * 修改密码
     * @return
     */
    @RequestMapping(value = "/changePassword",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult changePassword(@RequestBody HashMap<String,String> params){
        return userService.changePassword(params.get("phoneNumber"),params.get("oldPassword"),params.get("newPassword"));
    }

    /**
     * 修改信息
     * @return
     */
    @RequestMapping(value = "/changeInformation",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult changeInformation(@RequestBody HashMap<String,String> params){
        return userService.changeInformation(params.get("phoneNumber"),params.get("userImg"),params.get("mailbox"),params.get("address"),params.get("sign"));
    }

    /**
     * 查询信息
     * @return
     */
    @RequestMapping(value = "/selectInformation",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult selectInformation(@RequestBody HashMap<String,String> params){
        return userService.selectInformationByPhoneNumber(params.get("phoneNumber"));
    }

    /**
     * 查询用户列表
     * @return
     */
    @RequestMapping(value = "/selectUserList",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult selectUserList(@RequestBody HashMap<String,String> params){
        return userService.selectUserList(params.get("phoneNumber"),params.get("name"));
    }


    /**
     * 根据用户id查询用户信息
     * @return
     */
    @RequestMapping(value = "/getUserInfo",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult getUserInfo(@RequestBody HashMap<String,String> params){
        return userService.selectUserInfoById(Long.valueOf(params.get("id")));
    }

    /**
     * 根据用户id查询用户信息
     * @return
     */
    @RequestMapping(value = "/getSelfUserInfo",method = RequestMethod.GET)
    @ResponseBody
    public BaseResult getSelfUserInfo(HttpServletRequest request,HttpServletResponse response) throws IOException {
        BaseResult baseResult = userService.selectSelfUserInfo(request, response);
        if(baseResult.getCode() == -1){
            response.sendRedirect(LoginInterceptor.loginPath);
        }
        return baseResult;
    }
}
