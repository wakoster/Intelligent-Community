package com.graduation.management.domain;

import lombok.Data;

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
}
