package com.graduation.management.mapper;

import com.graduation.management.dto.InstallationSignInfoDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InstallationSignInfoMapper {
    /**
     * 根据条件查询安装包关注信息
     * @param installationSignInfoDTO
     * @return
     */
    List<InstallationSignInfoDTO> selectInstallationSignInfo(InstallationSignInfoDTO installationSignInfoDTO);

    /**
     * 根据用户和安装包唯一查询关注信息
     * @param userId
     * @param installationPackageId
     * @return
     */
    InstallationSignInfoDTO selectInstallationSignInfoByUserAndPackage(Long userId,Long installationPackageId);

    /**
     * 更新安装包关注信息
     * @param installationSignInfoDTO
     * @return
     */
    Integer updateInstallationSignInfo(InstallationSignInfoDTO installationSignInfoDTO);

    /**
     * 插入安装包关注信息
     * @param installationSignInfoDTO
     * @return
     */
    Integer insertInstallationSignInfo(InstallationSignInfoDTO installationSignInfoDTO);

    /**
     * 删除安装包关注信息
     * @param id
     * @return
     */
    Integer deleteInstallationSignInfoById(Long id);
}
