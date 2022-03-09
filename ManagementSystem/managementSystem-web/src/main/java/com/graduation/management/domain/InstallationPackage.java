package com.graduation.management.domain;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

/**
 * InstallationPackage表实体类
 * 保存安装包及其信息
 */
@Data
@Entity
public class InstallationPackage {
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
     * 安装包文件
     */
    @Column(nullable = false,columnDefinition = "LONGBLOB")
    private String installation_package;

    /**
     * 名称
     */
    @Column(nullable = false)
    private String name;

    /**
     * 主版本号
     */
    @Column(nullable = false)
    @ColumnDefault("1")
    private String major_version_number;

    /**
     * 子版本号
     */
    @Column(nullable = false)
    @ColumnDefault("0")
    private String minor_version_number;

    /**
     * 修正版本号
     */
    @Column(nullable = false)
    @ColumnDefault("0")
    private String revision_version_number;

    /**
     * 状态
     */
    @Column(nullable = false)
    @ColumnDefault("0")
    private Long state;

    /**
     * 操作状态
     */
    @Column(nullable = false)
    @ColumnDefault("0")
    private Long operatingState;

    /**
     * 页面设置编号
     */
    @OneToOne(cascade = CascadeType.ALL)
    private PageTagInfo pageTagInfo;
}
