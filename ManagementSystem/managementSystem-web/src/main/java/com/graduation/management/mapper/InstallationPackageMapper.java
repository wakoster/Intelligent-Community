package com.graduation.management.mapper;

import com.graduation.management.dto.InstallationPackageDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InstallationPackageMapper {
    /**
     * 根据条件查询安装包
     * @param installationPackageDTO
     * @return
     */
    List<InstallationPackageDTO> selectInstallationPackage(InstallationPackageDTO installationPackageDTO);

    /**
     * 根据id查询安装包
     * @param id
     * @return
     */
    InstallationPackageDTO selectInstallationPackageById(Long id);

    /**
     * 更新安装包
     * @param installationPackageDTO
     * @return
     */
    Integer updateInstallationPackage(InstallationPackageDTO installationPackageDTO);

    /**
     * 插入安装包
     * @param installationPackageDTO
     * @return
     */
    Integer insertInstallationPackage(InstallationPackageDTO installationPackageDTO);

    /**
     * 删除安装包
     * @param id
     * @return
     */
    Integer deleteInstallationPackageById(Long id);
}
