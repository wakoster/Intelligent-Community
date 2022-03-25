package com.graduation.management.domain;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

/**
 * OperatingRecord表实体类
 * 保存安装包操作记录
 */
@Data
@Entity
public class OperatingRecord {
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
     * 禁止修改操作记录
     */
    @Column(nullable = false)
    private Long update_time;

    /**
     * 安装包编号
     */
    @OneToOne(cascade = CascadeType.ALL)
    private InstallationPackage installation_package;

    /**
     * 状态
     */
    @Column(updatable = false, nullable = false)
    private String type;

    /**
     * 信息
     */
    @Column(updatable = false)
    private String info;

    /**
     * 是否显示
     */
    @Column(nullable = false)
    @ColumnDefault("true")
    private Boolean is_show;

    /**
     * 操作用户编号
     */
    @OneToOne(cascade = CascadeType.ALL)
    private UserInfo user_info;
}
