package com.graduation.management.controller;

import com.graduation.management.result.BaseResult;
import com.graduation.management.service.InstallationPackageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    BaseResult getAllInstallationPackage(HttpSession session){
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

    /**
     * 上传安装包
     * @param installationPackage
     * @return
     */
    @RequestMapping(value="/uploadInstallationPackage",method=RequestMethod.POST)
    @ResponseBody
    BaseResult uploadInstallationPackage(@RequestParam("installationPackage") MultipartFile installationPackage){
        return installationPackageService.uploadInstallationPackage(installationPackage);
    }

    /**
     * 删除安装包
     * @param params
     * @return
     */
    @RequestMapping(value="/deleteInstallationPackage",method=RequestMethod.POST)
    @ResponseBody
    BaseResult deleteInstallationPackage(@RequestBody HashMap<String,Object> params){
        return installationPackageService.deleteInstallationPackage(Long.valueOf((Integer)params.get("id")));
    }


    /**
     * 查询安装包操作记录
     * @param rows
     * @return
     */
    @RequestMapping(value = "/selectOperatingRecord",method = RequestMethod.GET)
    @ResponseBody
    BaseResult selectOperatingRecord(@RequestParam("rows") Integer rows){
        return installationPackageService.selectOperatingRecord(rows);
    }

    /**
     * 删除安装包操作记录
     * @param params
     * @return
     */
    @RequestMapping(value = "/deleteOperatingRecord",method = RequestMethod.POST)
    @ResponseBody
    BaseResult deleteOperatingRecord(@RequestBody HashMap<String,Object> params){
        return installationPackageService.deleteOperatingRecord(Long.valueOf((Integer)params.get("id")));
    }
}
