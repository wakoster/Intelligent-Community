package com.graduation.management.dto;

import lombok.Data;

/**
 * PageTagInfo表数据类
 */
@Data
public class PageTagInfoDTO {
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
     * 标签图片
     */
    private String img;

    /**
     * 标签标识码
     */
    private String code;

    /**
     * 标签标题
     */
    private String title;

    /**
     * 标签路径
     */
    private String url;

    /**
     * 标签描述
     */
    private String description;


    /**
     * 标签详细信息
     */
    private String information;

    /**
     * 标签类别
     */
    private String sort;
}
