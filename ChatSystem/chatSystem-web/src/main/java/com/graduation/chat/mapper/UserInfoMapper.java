package com.graduation.chat.mapper;

import com.graduation.chat.dto.UserInfoDTO;
import com.graduation.chat.result.ContactResult;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserInfoMapper {
    /**
     * 根据条件查询用户
     * @param userInfoDTO
     * @return
     */
    List<UserInfoDTO> selectUserInfo(UserInfoDTO userInfoDTO);

    /**
     * 根据条件查询用户
     * @param phoneNumber
     * @param name
     * @return
     */
    List<ContactResult> selectUserListByNameWithoutPhoneNumber(String phoneNumber, String name);

    /**
     * 根据电话查询用户
     * @param phoneNumber
     * @return
     */
    UserInfoDTO selectUserInfoByPhoneNumber(String phoneNumber);

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    UserInfoDTO selectUserInfoById(Long id);

    /**
     * 更新用户信息
     * @param userInfoDTO
     * @return
     */
    Integer updateUserInfo(UserInfoDTO userInfoDTO);

    /**
     * 修改密码
     * @param id
     * @param password
     * @return
     */
    Integer updatePassword(Long id,String password);
}
