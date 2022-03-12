package com.graduation.management.service.impl;

import com.graduation.management.dto.InstallationPackageDTO;
import com.graduation.management.dto.InstallationSignInfoDTO;
import com.graduation.management.mapper.InstallationPackageMapper;
import com.graduation.management.mapper.InstallationSignInfoMapper;
import com.graduation.management.result.BaseResult;
import com.graduation.management.service.InstallationPackageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

@Service
public class InstallationPackageServiceImpl implements InstallationPackageService {
    @Resource
    InstallationPackageMapper installationPackageMapper;

    @Resource
    InstallationSignInfoMapper installationSignInfoMapper;

    @Override
    public BaseResult selectAllInstallationPackage(HttpSession session) {
        List<HashMap<String,Object>> result = new ArrayList<>();
        /**
         * 1.查找安装包
         */
        List<InstallationPackageDTO> installationPackageDTOList = new ArrayList<>();
        try{
            installationPackageDTOList = installationPackageMapper.selectInstallationPackage(new InstallationPackageDTO());
        }catch (Exception e){
            return BaseResult.FAIL((long) -1,e.getMessage(),null);
        }
        /**
         * 2.获取登录的用户信息
         */
        if(Objects.isNull(session.getAttribute("userSession"))){
            return BaseResult.FAIL((long) -1,"失去登录信息",null);
        }
        Long userId = (Long)((Map)session.getAttribute("userSession")).get("userID");
        /**
         * 3.3-1、3-2
         */
        for(InstallationPackageDTO installationPackageDTO : installationPackageDTOList){
            HashMap<String,Object> map = new HashMap<>();
            /**
             * 3-1.查询关注情况
             */
            InstallationSignInfoDTO installationSignInfoDTO = new InstallationSignInfoDTO();
            try {
                installationSignInfoDTO = installationSignInfoMapper.selectInstallationSignInfoByUserAndPackage(userId,installationPackageDTO.getId());
            }catch (Exception e){
                return BaseResult.FAIL((long) -1,e.getMessage(),null);
            }
            /**
             * 3-2.按前端需要封装
             */
            if(Objects.nonNull(installationSignInfoDTO)) {
                map.put("sign", installationSignInfoDTO.getSign());
            }else {
                /**
                 * 如果没有找到,则创建
                 */
                installationSignInfoDTO = new InstallationSignInfoDTO();
                installationSignInfoDTO.setInstallationPackageId(installationPackageDTO.getId());
                installationSignInfoDTO.setUserInfoId(userId);
                installationSignInfoDTO.setSign(false);
                try {
                    Integer success = installationSignInfoMapper.insertInstallationSignInfo(installationSignInfoDTO);
                    if(Objects.isNull(success) || success != 1){
                        return BaseResult.FAIL((long) -1,"安装包关注信息创建失败",null);
                    }
                }catch (Exception e){
                    return BaseResult.FAIL((long) -1,e.getMessage(),null);
                }
                map.put("sign", false);
            }
            map.put("id", installationPackageDTO.getId());
            map.put("name", installationPackageDTO.getName());
            map.put("versions", installationPackageDTO.getMajorVersionNumber() + "." + installationPackageDTO.getMinorVersionNumber() + "." + installationPackageDTO.getRevisionVersionNumber());
            map.put("state", installationPackageDTO.getState());
            map.put("operatingState", installationPackageDTO.getOperatingState());
            result.add(map);
        }
        return BaseResult.SUCCESS(result);
    }

    @Override
    public BaseResult changeSignInfo(Long installationPackageId, HttpSession session,Boolean sign) {
        /**
         * 1.获取登录的用户信息
         */
        if(Objects.isNull(session.getAttribute("userSession"))){
            return BaseResult.FAIL((long) -1,"失去登录信息",null);
        }
        Long userId = (Long)((Map)session.getAttribute("userSession")).get("userID");
        /**
         * 2.拼装查询条件
         */
        InstallationSignInfoDTO installationSignInfoDTO = new InstallationSignInfoDTO();
        installationSignInfoDTO.setInstallationPackageId(installationPackageId);
        installationSignInfoDTO.setUserInfoId(userId);
        installationSignInfoDTO.setSign(sign);
        /**
         * 3.修改关注信息
         */
        try {
            Integer success = installationSignInfoMapper.updateInstallationSignInfo(installationSignInfoDTO);
            if(Objects.isNull(success) || success != 1){
                return BaseResult.FAIL((long) -1,"安装包关注信息修改失败",null);
            }
        }catch (Exception e){
            return BaseResult.FAIL((long) -1,e.getMessage(),null);
        }
        return BaseResult.SUCCESS(null);
    }
}
