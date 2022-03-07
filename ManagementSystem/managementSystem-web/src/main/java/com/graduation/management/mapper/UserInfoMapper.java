package com.graduation.management.mapper;

import com.graduation.management.dto.UserInfoDTO;
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
     * 更新用户信息
     * @param userInfoDTO
     * @return
     */
    Integer updateUserInfo(UserInfoDTO userInfoDTO);

    /**
     * 修改密码
     * @param phoneNumber
     * @param password
     * @return
     */
    Integer updatePassword(String phoneNumber,String password);

    /**
     * 插入用户信息
     * @param userInfoDTO
     * @return
     */
    Integer insertUserInfo(UserInfoDTO userInfoDTO);

    /**
     * 删除用户信息
     * @param id
     * @return
     */
    Integer deleteUserInfoById(Long id);
}
