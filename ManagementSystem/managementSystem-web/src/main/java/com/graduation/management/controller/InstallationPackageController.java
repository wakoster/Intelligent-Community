package com.graduation.management.controller;

import com.graduation.management.result.BaseResult;
import com.graduation.management.service.InstallationPackageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
@CrossOrigin
@RequestMapping(value = "/installationPackage")
public class InstallationPackageController {
    @Resource
    InstallationPackageService installationPackageService;

    /**
     * 查找全部安装包信息
     * @return
     */
    @RequestMapping(value = "/getAllInstallationPackage",method = RequestMethod.GET)
    @ResponseBody
    BaseResult selectAllInstallationPackage(HttpSession session){
        return installationPackageService.selectAllInstallationPackage(session);
    }

    /**
     * 修改安装包关注信息
     * @return
     */
    @RequestMapping(value = "/changeSignInfo",method = RequestMethod.POST)
    @ResponseBody
    BaseResult changeSignInfo(@RequestBody HashMap<String,Object> params, HttpSession session){
        return installationPackageService.changeSignInfo(Long.valueOf((Integer)params.get("installationPackageId")),session,(Boolean) params.get("sign"));
    }
}
