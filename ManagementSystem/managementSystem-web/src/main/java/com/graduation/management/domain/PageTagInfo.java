package com.graduation.management.domain;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

/**
 * PageTagInfo表实体类
 * 保存页面标签和路径信息
 */
@Data
@Entity
public class PageTagInfo {
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
     * 标签图片
     */
    @Column(columnDefinition = "MEDIUMTEXT")
    private String img;

    /**
     * 标签标识码
     */
    @Column(nullable = false,unique = true)
    private String code;

    /**
     * 标签标题
     */
    @Column(nullable = false)
    private String title;

    /**
     * 标签路径
     */
    @Column(nullable = false,unique = true)
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
    @ColumnDefault("'默认'")
    private String sort;
}
