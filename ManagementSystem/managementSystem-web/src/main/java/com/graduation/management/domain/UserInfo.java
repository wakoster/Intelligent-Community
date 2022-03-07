package com.graduation.management.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * userInfo表实体类
 * 保存用户信息
 */
@Data
@Entity
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 电话号码(账号)
     */
    @Column(nullable = false,unique = true)
    private String phone_number;

    /**
     * 密码
     */
    @Column(nullable = false)
    private String password;

    /**
     * 名字
     */
    @Column(nullable = false)
    private String name;

    /**
     * 头像图片
     */
    @Column(columnDefinition = "MEDIUMTEXT")
    private String user_img;

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
     * 邮箱地址
     */
    private String mailbox;

    /**
     * 所在地区
     */
    private String address;

    /**
     * 个人签名
     */
    private String sign;

    /**
     * 账号类型
     */
    @Column(nullable = false)
    private Long type;
}
