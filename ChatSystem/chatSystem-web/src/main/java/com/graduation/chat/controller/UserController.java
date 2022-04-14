package com.graduation.chat.controller;

import com.graduation.chat.result.BaseResult;
import com.graduation.chat.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;

@Controller
@CrossOrigin
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    UserService userService;

    /**
     * 获取用户名
     * @return
     */
    @RequestMapping(value = "/changePassword",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult changePassword(@RequestBody HashMap<String,String> params){
        return userService.changePassword(params.get("phoneNumber"),params.get("oldPassword"),params.get("newPassword"));
    }

    /**
     * 获取用户名
     * @return
     */
    @RequestMapping(value = "/changeInformation",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult changeInformation(@RequestBody HashMap<String,String> params){
        return userService.changeInformation(params.get("phoneNumber"),params.get("userImg"),params.get("mailbox"),params.get("address"),params.get("sign"));
    }

    /**
     * 获取用户名
     * @return
     */
    @RequestMapping(value = "/selectInformation",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult selectInformation(@RequestBody HashMap<String,String> params){
        return userService.selectInformationByPhoneNumber(params.get("phoneNumber"));
    }

    /**
     * 获取用户名
     * @return
     */
    @RequestMapping(value = "/selectUserList",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult selectUserList(@RequestBody HashMap<String,String> params){
        return userService.selectUserList(params.get("phoneNumber"),params.get("name"));
    }
}
