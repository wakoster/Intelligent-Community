package com.graduation.management.service.impl;

import com.graduation.management.dto.InstallationPackageDTO;
import com.graduation.management.dto.InstallationSignInfoDTO;
import com.graduation.management.dto.OperatingRecordDTO;
import com.graduation.management.dto.PageTagInfoDTO;
import com.graduation.management.enumeration.InstallationPackageOperatingStateEnum;
import com.graduation.management.enumeration.InstallationPackageStateEnum;
import com.graduation.management.mapper.InstallationPackageMapper;
import com.graduation.management.mapper.InstallationSignInfoMapper;
import com.graduation.management.mapper.OperatingRecordMapper;
import com.graduation.management.mapper.PageTagInfoMapper;
import com.graduation.management.result.BaseResult;
import com.graduation.management.service.InstallationPackageService;
import com.graduation.management.util.FileUtil;
import com.graduation.management.util.NSSMUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

@Service
public class InstallationPackageServiceImpl implements InstallationPackageService {
    @Resource
    InstallationPackageMapper installationPackageMapper;

    @Resource
    InstallationSignInfoMapper installationSignInfoMapper;

    @Resource
    OperatingRecordMapper operatingRecordMapper;

    @Resource
    PageTagInfoMapper pageTagInfoMapper;

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
            return BaseResult.ERROR((long) -1,e.getMessage(),null);
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
            /**
             * 访问不到地址跳过
             */
            if(StringUtils.isEmpty(installationPackageDTO.getInstallationPackage())){
                continue;
            }
            HashMap<String,Object> map = new HashMap<>();
            /**
             * 3-1.查询关注情况
             */
            InstallationSignInfoDTO installationSignInfoDTO = new InstallationSignInfoDTO();
            try {
                installationSignInfoDTO = installationSignInfoMapper.selectInstallationSignInfoByUserAndPackage(userId,installationPackageDTO.getId());
            }catch (Exception e){
                return BaseResult.ERROR((long) -1,e.getMessage(),null);
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
                    return BaseResult.ERROR((long) -1,e.getMessage(),null);
                }
                map.put("sign", false);
            }
            map.put("id", installationPackageDTO.getId());
            map.put("name", installationPackageDTO.getName());
            map.put("versions", installationPackageDTO.getMajorVersionNumber() + "." + installationPackageDTO.getMinorVersionNumber() + "." + installationPackageDTO.getRevisionVersionNumber());
            map.put("state", installationPackageDTO.getState());
            map.put("operatingState", installationPackageDTO.getOperatingState());
            map.put("pageTagInfoId", installationPackageDTO.getPageTagInfoId());
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
            return BaseResult.ERROR((long) -1,e.getMessage(),null);
        }
        return BaseResult.SUCCESS(null);
    }

    @Override
    public BaseResult selectOperatingRecord(Integer rows) {
        List<HashMap<String,Object>> result = new ArrayList<>();
        /**
         * 1.查找安装包操作记录
         */
        List<OperatingRecordDTO> operatingRecordDTOList = new ArrayList<>();
        try{
            OperatingRecordDTO operatingRecordDTO = new OperatingRecordDTO();
            operatingRecordDTO.setIsShow(true);
            operatingRecordDTOList = operatingRecordMapper.selectOperatingRecord(operatingRecordDTO,rows);
        }catch (Exception e){
            return BaseResult.ERROR((long) -1,e.getMessage(),null);
        }
        /**
         * 2.补全安装包操作记录信息
         */
        for(OperatingRecordDTO operatingRecordDTO : operatingRecordDTOList){
            HashMap<String,Object> map = new HashMap<>();
            /**
             * 2-1.查询安装包名称
             */
            InstallationPackageDTO installationPackageDTO = new InstallationPackageDTO();
            try {
                installationPackageDTO = installationPackageMapper.selectInstallationPackageById(operatingRecordDTO.getInstallationPackageId());
            }catch (Exception e){
                return BaseResult.ERROR((long) -1,e.getMessage(),null);
            }
            /**
             * 2-2.按前端需要封装
             */
            if(Objects.nonNull(installationPackageDTO)) {
                map.put("id", operatingRecordDTO.getId());
                map.put("name", installationPackageDTO.getName());
                map.put("type", operatingRecordDTO.getType());
                map.put("time", operatingRecordDTO.getCreateTime());
                map.put("info", operatingRecordDTO.getInfo());
                result.add(map);
            }
        }
        return BaseResult.SUCCESS(result);
    }

    @Override
    public BaseResult deleteOperatingRecord(Long id) {
        try{
            Integer success = operatingRecordMapper.hideOperatingRecordById(id);
            if(Objects.isNull(success) || success != 1){
                return BaseResult.FAIL((long) -1,"安装包操作信息删除失败",null);
            }
        }catch (Exception e){
            return BaseResult.ERROR((long) -1,e.getMessage(),null);
        }
        return BaseResult.SUCCESS(null);
    }

    @Override
    public BaseResult uploadInstallationPackage(MultipartFile installationPackage) {
        InstallationPackageDTO installationPackageDTO = new InstallationPackageDTO();
        String installationPackageName = installationPackage.getOriginalFilename();
        installationPackageDTO.setFileName(installationPackageName);
        if(StringUtils.isEmpty(installationPackageName)){
            return BaseResult.FAIL((long) -1,"安装包名称获取失败",null);
        }
        String[] nameSplit = installationPackageName.split("-");
        /**
         * 1.查询安装包是否存在
         */
        List<InstallationPackageDTO> installationPackageDTOList = new ArrayList<>();
        try{
            installationPackageDTO.setName(nameSplit[0]);
            installationPackageDTOList = installationPackageMapper.selectInstallationPackage(installationPackageDTO);
        }catch (Exception e){
            return BaseResult.ERROR((long) -1,e.getMessage(),null);
        }
        /**
         * 2.获取安装包信息
         */
        try{
            String[] versionNumberSplit = nameSplit[1].split("\\.");
            installationPackageDTO.setMajorVersionNumber(Long.valueOf(versionNumberSplit[0]));
            installationPackageDTO.setMinorVersionNumber(Long.valueOf(versionNumberSplit[1]));
            installationPackageDTO.setRevisionVersionNumber(Long.valueOf(versionNumberSplit[2]));
            installationPackageDTO.setInstallationPackage(FileUtil.saveMultipartFile(installationPackage));
            installationPackageDTO.setState(InstallationPackageStateEnum.UNINSTALLED.getCode());
            installationPackageDTO.setOperatingState(InstallationPackageOperatingStateEnum.UNINSTALLED.getCode());
        }catch (Exception e){
            return BaseResult.FAIL((long) -1,"安装包信息获取失败（常见原因：安装包命名不规范）",null);
        }
        /**
         * 3.如果不存在，则插入
         * 否则先判断是否为卸载，是则更新
         */
        if(installationPackageDTOList.isEmpty()){
            try{
                Integer success = installationPackageMapper.insertInstallationPackage(installationPackageDTO);
                if(Objects.isNull(success) || success != 1){
                    return BaseResult.FAIL((long) -1,"上传失败",null);
                }
            }catch (Exception e){
                return BaseResult.ERROR((long) -1,e.getMessage(),null);
            }
        } else {
            if(!StringUtils.equals(installationPackageDTOList.get(0).getState(),InstallationPackageStateEnum.UNINSTALLED.getCode())){
                return BaseResult.FAIL((long) -1,"请先卸载当前版本",null);
            }
            try{
                installationPackageDTO.setId(installationPackageDTOList.get(0).getId());
                Integer success = installationPackageMapper.updateInstallationPackage(installationPackageDTO);
                if(Objects.isNull(success) || success != 1){
                    return BaseResult.FAIL((long) -1,"上传失败",null);
                }
            }catch (Exception e){
                return BaseResult.ERROR((long) -1,e.getMessage(),null);
            }
        }
        return BaseResult.SUCCESS(null);
    }

    @Override
    public BaseResult deleteInstallationPackage(Long id) {
        try{
            Integer success = installationPackageMapper.deleteInstallationPackageById(id);
            if(Objects.isNull(success) || success != 1){
                return BaseResult.FAIL((long) -1,"删除失败",null);
            }
        }catch (Exception e){
            return BaseResult.ERROR((long) -1,e.getMessage(),null);
        }
        return BaseResult.SUCCESS(null);
    }

    @Override
    public BaseResult setPageTagInfoId(Long pageTagInfoId, Long installationPackageId) {
        InstallationPackageDTO installationPackageDTO = new InstallationPackageDTO();
        installationPackageDTO.setId(installationPackageId);
        installationPackageDTO.setPageTagInfoId(pageTagInfoId);
        try{
            Integer success = installationPackageMapper.updateInstallationPackage(installationPackageDTO);
            if(Objects.isNull(success) || success != 1){
                return BaseResult.FAIL((long) -1,"安装包标签设置失败#setPageTagInfoId",null);
            }
        }catch (Exception e){
            return BaseResult.ERROR((long) -1,e.getMessage(),null);
        }
        return BaseResult.SUCCESS(null);
    }

    @Override
    public BaseResult installInstallationPackage(Long installationPackageId, HttpSession session) {
        /**
         * 1.查询安装包
         */
        InstallationPackageDTO installationPackageDTO = new InstallationPackageDTO();
        try {
            installationPackageDTO = installationPackageMapper.selectInstallationPackageById(installationPackageId);
        }catch (Exception e){
            return BaseResult.ERROR((long) -1,e.getMessage(),null);
        }
        /**
         * 2.获取登录的用户信息
         */
        if(Objects.isNull(session.getAttribute("userSession"))){
            return BaseResult.FAIL((long) -1,"失去登录信息",null);
        }
        Long userId = (Long)((Map)session.getAttribute("userSession")).get("userID");
        /**
         * 3.安装
         */
        try {
            BaseResult result = NSSMUtil.install(installationPackageDTO.getName(),installationPackageDTO.getFileName());
            if(result.getCode() == 0){
                addOperatingRecord(installationPackageDTO.getId(),"安装成功",(String)result.getData(),userId);
                installationPackageDTO.setState(InstallationPackageStateEnum.INSTALLED.getCode());
                installationPackageDTO.setOperatingState(InstallationPackageOperatingStateEnum.INSTALLED.getCode());
            }else {
                addOperatingRecord(installationPackageDTO.getId(),"安装失败",(String)result.getData(),userId);
            }
            installationPackageMapper.updateInstallationPackage(installationPackageDTO);
        }catch (Exception e){
            return BaseResult.ERROR((long) -1,e.getMessage(),null);
        }
        return BaseResult.SUCCESS(null);
    }

    @Override
    public BaseResult startInstallationPackage(Long installationPackageId, HttpSession session) {
        /**
         * 1.查询安装包
         */
        InstallationPackageDTO installationPackageDTO = new InstallationPackageDTO();
        try {
            installationPackageDTO = installationPackageMapper.selectInstallationPackageById(installationPackageId);
        }catch (Exception e){
            return BaseResult.ERROR((long) -1,e.getMessage(),null);
        }
        /**
         * 2.获取登录的用户信息
         */
        if(Objects.isNull(session.getAttribute("userSession"))){
            return BaseResult.FAIL((long) -1,"失去登录信息",null);
        }
        Long userId = (Long)((Map)session.getAttribute("userSession")).get("userID");
        /**
         * 3.启动
         */
        try {
            BaseResult result = NSSMUtil.start(installationPackageDTO.getName());
            if(result.getCode() == 0){
                addOperatingRecord(installationPackageDTO.getId(),"启动成功",(String)result.getData(),userId);
                installationPackageDTO.setOperatingState(InstallationPackageOperatingStateEnum.NORMAL_OPERATION.getCode());
                if(Objects.nonNull(installationPackageDTO.getPageTagInfoId())){
                    PageTagInfoDTO pageTagInfoDTO = new PageTagInfoDTO();
                    pageTagInfoDTO.setId(installationPackageDTO.getPageTagInfoId());
                    pageTagInfoDTO.setIsShow(true);
                    pageTagInfoMapper.updatePageTagInfo(pageTagInfoDTO);
                }
            }else {
                addOperatingRecord(installationPackageDTO.getId(),"启动失败",(String)result.getData(),userId);
                installationPackageDTO.setOperatingState(InstallationPackageOperatingStateEnum.DEAD_STAR.getCode());
            }
            installationPackageMapper.updateInstallationPackage(installationPackageDTO);
        }catch (Exception e){
            return BaseResult.ERROR((long) -1,e.getMessage(),null);
        }
        return BaseResult.SUCCESS(null);
    }

    @Override
    public BaseResult stopInstallationPackage(Long installationPackageId, HttpSession session) {
        /**
         * 1.查询安装包
         */
        InstallationPackageDTO installationPackageDTO = new InstallationPackageDTO();
        try {
            installationPackageDTO = installationPackageMapper.selectInstallationPackageById(installationPackageId);
        }catch (Exception e){
            return BaseResult.ERROR((long) -1,e.getMessage(),null);
        }
        /**
         * 2.获取登录的用户信息
         */
        if(Objects.isNull(session.getAttribute("userSession"))){
            return BaseResult.FAIL((long) -1,"失去登录信息",null);
        }
        Long userId = (Long)((Map)session.getAttribute("userSession")).get("userID");
        /**
         * 3.停止
         */
        try {
            BaseResult result = NSSMUtil.stop(installationPackageDTO.getName());
            if(result.getCode() == 0){
                addOperatingRecord(installationPackageDTO.getId(),"停止成功",(String)result.getData(),userId);
                installationPackageDTO.setOperatingState(InstallationPackageOperatingStateEnum.STOPPED.getCode());
                if(Objects.nonNull(installationPackageDTO.getPageTagInfoId())){
                    PageTagInfoDTO pageTagInfoDTO = new PageTagInfoDTO();
                    pageTagInfoDTO.setId(installationPackageDTO.getPageTagInfoId());
                    pageTagInfoDTO.setIsShow(false);
                    pageTagInfoMapper.updatePageTagInfo(pageTagInfoDTO);
                }
            }else {
                addOperatingRecord(installationPackageDTO.getId(),"停止失败",(String)result.getData(),userId);
            }
            installationPackageMapper.updateInstallationPackage(installationPackageDTO);
        }catch (Exception e){
            return BaseResult.ERROR((long) -1,e.getMessage(),null);
        }
        return BaseResult.SUCCESS(null);
    }

    @Override
    public BaseResult removeInstallationPackage(Long installationPackageId, HttpSession session) {
        /**
         * 1.查询安装包
         */
        InstallationPackageDTO installationPackageDTO = new InstallationPackageDTO();
        try {
            installationPackageDTO = installationPackageMapper.selectInstallationPackageById(installationPackageId);
        }catch (Exception e){
            return BaseResult.ERROR((long) -1,e.getMessage(),null);
        }
        /**
         * 2.获取登录的用户信息
         */
        if(Objects.isNull(session.getAttribute("userSession"))){
            return BaseResult.FAIL((long) -1,"失去登录信息",null);
        }
        Long userId = (Long)((Map)session.getAttribute("userSession")).get("userID");
        /**
         * 3.卸载
         */
        try {
            BaseResult result = NSSMUtil.remove(installationPackageDTO.getName());
            if(result.getCode() == 0){
                addOperatingRecord(installationPackageDTO.getId(),"卸载成功",(String)result.getData(),userId);
                installationPackageDTO.setState(InstallationPackageStateEnum.UNINSTALLED.getCode());
                installationPackageDTO.setOperatingState(InstallationPackageOperatingStateEnum.UNINSTALLED.getCode());
            }else {
                addOperatingRecord(installationPackageDTO.getId(),"卸载失败",(String)result.getData(),userId);
            }
            installationPackageMapper.updateInstallationPackage(installationPackageDTO);
        }catch (Exception e){
            return BaseResult.ERROR((long) -1,e.getMessage(),null);
        }
        return BaseResult.SUCCESS(null);
    }

    /**
     * 添加安装包操作信息
     * @param installationPackageId 安装包编号
     * @param type
     * @param info
     * @param userInfoId 用户编号
     */
    private void addOperatingRecord(Long installationPackageId,String type,String info,Long userInfoId){
        OperatingRecordDTO operatingRecordDTO = new OperatingRecordDTO();
        operatingRecordDTO.setInstallationPackageId(installationPackageId);
        operatingRecordDTO.setType(type);
        operatingRecordDTO.setInfo(info);
        operatingRecordDTO.setUserInfoId(userInfoId);
        operatingRecordMapper.insertOperatingRecord(operatingRecordDTO);
    }
}
