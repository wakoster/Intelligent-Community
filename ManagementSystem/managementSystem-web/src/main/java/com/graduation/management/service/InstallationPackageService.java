package com.graduation.management.service;

import com.graduation.management.result.BaseResult;
import org.springframework.web.multipart.MultipartFile;

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

    /**
     * 查找可见的安装包操作记录(is_show = true)
     * @return
     */
    BaseResult selectOperatingRecord(Integer rows);

    /**
     * 设置安装包操作记录为不可见(is_show = true)
     * @return
     */
    BaseResult deleteOperatingRecord(Long id);

    /**
     * 上传安装包
     * @return
     */
    BaseResult uploadInstallationPackage(MultipartFile installationPackage);

    /**
     * 删除安装包
     * @return
     */
    BaseResult deleteInstallationPackage(Long id);

    /**
     * 设置安装包与页面标签的关联
     * @return
     */
    BaseResult setPageTagInfoId(Long pageTagInfoId,Long installationPackageId);

    /**
     * 安装安装包
     * @param installationPackageId
     * @param session
     * @return
     */
    BaseResult installInstallationPackage(Long installationPackageId,HttpSession session);

    /**
     * 启动安装包
     * @param installationPackageId
     * @param session
     * @return
     */
    BaseResult startInstallationPackage(Long installationPackageId,HttpSession session);

    /**
     * 停止安装包
     * @param installationPackageId
     * @param session
     * @return
     */
    BaseResult stopInstallationPackage(Long installationPackageId,HttpSession session);

    /**
     * 删除安装包
     * @param installationPackageId
     * @param session
     * @return
     */
    BaseResult removeInstallationPackage(Long installationPackageId,HttpSession session);
}
