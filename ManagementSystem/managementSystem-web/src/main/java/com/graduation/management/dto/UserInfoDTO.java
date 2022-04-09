package com.graduation.management.dto;

import lombok.Data;

/**
 * userInfo表数据类
 */
@Data
public class UserInfoDTO {
    /**
     * 编号
     */
    private Long id;

    /**
     * 电话号码(账号)
     */
    private String phoneNumber;

    /**
     * 密码
     */
    private String password;

    /**
     * 名字
     */
    private String name;

    /**
     * 头像图片
     */
    private String userImg;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 修改时间
     */
    private Long updateTime;

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
    private Long type;
}
