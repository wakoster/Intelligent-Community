package com.graduation.chat.service;

import com.graduation.chat.result.BaseResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserService {
    /**
     * 更新密码
     * @param phoneNumber
     * @param oldPassword
     * @param newPassword
     * @return
     */
    BaseResult changePassword(String phoneNumber, String oldPassword, String newPassword);

    /**
     * 更新个人信息
     * @param phoneNumber
     * @param userImg
     * @param mailbox
     * @param address
     * @param sign
     * @return
     */
    BaseResult changeInformation(String phoneNumber, String userImg, String mailbox, String address, String sign);

    /**
     * 根据电话号码查询基本信息
     * @param phoneNumber
     * @return
     */
    BaseResult selectInformationByPhoneNumber(String phoneNumber);

    /**
     * 查询用户列表
     * @param name
     * @return
     */
    BaseResult selectUserList(String phoneNumber,String name);

    /**
     * 查询用户列表
     * @param id
     * @return
     */
    BaseResult selectUserInfoById(Long id);

    /**
     * 查询自身id
     * @return
     */
    BaseResult selectSelfUserInfo(HttpServletRequest request, HttpServletResponse response);
}
