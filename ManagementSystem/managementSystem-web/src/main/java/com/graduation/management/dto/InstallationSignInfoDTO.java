package com.graduation.management.dto;

import lombok.Data;

/**
 * InstallationSignInfo表数据类
 */
@Data
public class InstallationSignInfoDTO{
    /**
     * 用户编号
     */
    private Long userInfoId;

    /**
     * 安装包编号
     */
    private Long installationPackageId;

    /**
     * 标注sign信息
     */
    private Boolean sign;
}

