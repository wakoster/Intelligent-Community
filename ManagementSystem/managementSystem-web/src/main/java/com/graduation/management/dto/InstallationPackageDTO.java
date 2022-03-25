package com.graduation.management.dto;

import lombok.Data;

/**
 * InstallationPackage表数据类
 */
@Data
public class InstallationPackageDTO {
    /**
     * 编号
     */
    private Long id;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 修改时间
     */
    private Long updateTime;

    /**
     * 安装包文件
     */
    private String installationPackage;

    /**
     * 名称
     */
    private String name;

    /**
     * 主版本号
     */
    private Long majorVersionNumber;

    /**
     * 子版本号
     */
    private Long minorVersionNumber;

    /**
     * 修正版本号
     */
    private Long revisionVersionNumber;

    /**
     * 状态
     */
    private String state;

    /**
     * 操作状态
     */
    private String operatingState;

    /**
     * 页面设置编号
     */
    private Long pageTagInfoId;
}
