package com.graduation.management.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * PageTagSort表实体类
 * 保存页面标签的分类信息
 */
@Data
@Entity
public class PageTagSort {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 创建时间
     */
    @Column(updatable = false, nullable = false)
    private Long create_time;

    /**
     * 修改时间
     */
    @Column(nullable = false)
    private Long update_time;

    /**
     * 标签类别
     */
    @Column(nullable = false,unique = true)
    private String sort;
}
