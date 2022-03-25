package com.graduation.management.controller;

import com.graduation.management.dto.PageTagInfoDTO;
import com.graduation.management.result.BaseResult;
import com.graduation.management.service.InstallationPackageService;
import com.graduation.management.service.PageTagService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Objects;

@Controller
@CrossOrigin
@RequestMapping(value = "/pageTag")
public class PageTagController {
    @Resource
    PageTagService pageTagService;

    @Resource
    InstallationPackageService installationPackageService;

    /**
     * 查询标签页面
     * @return
     */
    @RequestMapping(value = "/getAllPageTag",method = RequestMethod.GET)
    @ResponseBody
    public BaseResult getAllPageTag(){
        return pageTagService.selectAllPageTagInfo();
    }

    /**
     * 查询页面标签的分类信息
     * @return
     */
    @RequestMapping(value = "/getPageTagSort",method = RequestMethod.GET)
    @ResponseBody
    public BaseResult getPageTagSort(){
        return pageTagService.getPageTagSort();
    }

    /**
     * 查询页面标签的分类信息
     * @return
     */
    @RequestMapping(value = "/getPageTagById",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult getPageTagById(@RequestBody HashMap<String,Object> params){
        return pageTagService.getPageTagInfoById(Long.valueOf((String) params.get("id")));
    }

    /**
     * 查询标签页面
     * @return
     */
    @RequestMapping(value = "/setPageTagInfo",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult setPageTagInfo(@RequestBody HashMap<String,Object> params){
        /**
         * 先插入页面标签
         */
        if(Objects.isNull(params.get("code"))){
            return BaseResult.FAIL((long) -1,"请填写标签标识码",null);
        }
        if(Objects.isNull(params.get("title"))){
            return BaseResult.FAIL((long) -1,"请填写标签标题",null);
        }
        if(Objects.isNull(params.get("url"))){
            return BaseResult.FAIL((long) -1,"请填写标签路径",null);
        }
        PageTagInfoDTO pageTagInfoDTO = new PageTagInfoDTO();
        pageTagInfoDTO.setImg((String) params.get("img"));
        pageTagInfoDTO.setCode((String) params.get("code"));
        pageTagInfoDTO.setTitle((String) params.get("title"));
        pageTagInfoDTO.setUrl((String) params.get("url"));
        pageTagInfoDTO.setDescription((String) params.get("description"));
        pageTagInfoDTO.setInformation((String) params.get("information"));
        pageTagInfoDTO.setSort((String) params.get("sort"));
        BaseResult baseResult = pageTagService.setPageTagInfo(pageTagInfoDTO);
        /**
         * 页面标签插入失败返回
         */
        if(baseResult.getCode() != 0){
            return baseResult;
        }
        /**
         * 再根据页面标签id关联安装包
         */
        return installationPackageService.setPageTagInfoId((Long)baseResult.getData(),Long.valueOf((String) params.get("installationPackageId")));
    }
}
