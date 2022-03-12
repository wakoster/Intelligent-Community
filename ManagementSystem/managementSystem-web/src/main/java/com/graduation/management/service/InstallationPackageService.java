package com.graduation.management.service;

import com.graduation.management.result.BaseResult;

import javax.servlet.http.HttpSession;

public interface InstallationPackageService {
    /**
     * 查找全部安装包信息
     * @return
     */
    BaseResult selectAllInstallationPackage(HttpSession session);
    
    /**
     * 查找全部安装包信息
     * @return
     */
    BaseResult changeSignInfo(Long installationPackageId,HttpSession session,Boolean sign);
}
