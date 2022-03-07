package com.graduation.management.service;

import com.graduation.management.enumeration.AccessAuthorityEnum;
import com.graduation.management.result.BaseResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface UserService {
    /**
     * 查找用户（登录用）
     * @param phoneNumber
     * @param password
     * @param accessAuthorityEnum
     * @return
     */
    BaseResult selectUserInfoDoLogin(String phoneNumber, String password, AccessAuthorityEnum accessAuthorityEnum, HttpSession session, HttpServletRequest request, HttpServletResponse response);
}
