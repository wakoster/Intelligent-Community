package com.graduation.management.dto;

import lombok.Data;

/**
 * PageTagSort表数据类
 */
@Data
public class PageTagSortDTO {
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
     * 标签类别
     */
    private String sort;
}
