package com.graduation.chat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WebController {

    @RequestMapping(value = "/",method = {RequestMethod.GET})
    public String index(){
        return "index";
    }

    @RequestMapping(value = "/chatSystem",method = {RequestMethod.GET})
    public String chatSystem(){
        return "index";
    }

    @RequestMapping(value = "/chatSystem/chat",method = {RequestMethod.GET})
    public String chat(){
        return "index";
    }

    @RequestMapping(value = "/chatSystem/contacts",method = {RequestMethod.GET})
    public String contacts(){
        return "index";
    }

    @RequestMapping(value = "/chatSystem/userInfo",method = {RequestMethod.GET})
    public String userInfo(){
        return "index";
    }
}
