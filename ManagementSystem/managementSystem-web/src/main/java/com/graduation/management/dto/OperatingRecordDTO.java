package com.graduation.management.dto;

import lombok.Data;

/**
 * OperatingRecord表数据类
 */
@Data
public class OperatingRecordDTO {
    /**
     * 编号
     */
    private Long id;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 创建时间
     */
    private Long updateTime;

    /**
     * 安装包编号
     */
    private Long installationPackageId;

    /**
     * 状态
     */
    private String type;

    /**
     * 信息
     */
    private String info;

    /**
     * 是否显示
     */
    private Boolean isShow;

    /**
     * 操作用户编号
     */
    private Long userInfoId;
}
