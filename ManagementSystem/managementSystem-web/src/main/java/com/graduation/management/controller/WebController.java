package com.graduation.management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class WebController {

    @RequestMapping(value = "/",method = {RequestMethod.GET})
    public String index(){
        return "index";
    }

    @RequestMapping(value = "/login",method = {RequestMethod.GET})
    public String login(){
        return "index";
    }

    @RequestMapping(value = "/homepage",method = {RequestMethod.GET})
    public String homepage(){
        return "index";
    }

    @RequestMapping(value = "/settingPage",method = {RequestMethod.GET})
    public String settingPage(){
        return "index";
    }

    @RequestMapping(value = "/settingPage/installationPackageManagement",method = {RequestMethod.GET})
    public String settingPage_installationPackageManagement(){
        return "index";
    }
}
