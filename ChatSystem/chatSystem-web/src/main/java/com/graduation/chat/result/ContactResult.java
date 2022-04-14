package com.graduation.chat.result;

import lombok.Data;

/**
 * 用户信息返回DTO
 */
@Data
public class ContactResult {
    /**
     * 编号
     */
    private Long id;

    /**
     * 电话号码(账号)
     */
    private String phoneNumber;

    /**
     * 名字
     */
    private String name;

    /**
     * 头像图片
     */
    private String userImg;

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
     * 所属部门
     */
    private String department;
}
