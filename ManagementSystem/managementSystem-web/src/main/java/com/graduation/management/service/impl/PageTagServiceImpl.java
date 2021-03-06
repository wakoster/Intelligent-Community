package com.graduation.management.service.impl;

import com.graduation.management.dto.PageTagInfoDTO;
import com.graduation.management.dto.PageTagSortDTO;
import com.graduation.management.mapper.PageTagInfoMapper;
import com.graduation.management.mapper.PageTagSortMapper;
import com.graduation.management.result.BaseResult;
import com.graduation.management.service.PageTagService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class PageTagServiceImpl implements PageTagService {

    @Resource
    PageTagInfoMapper pageTagInfoMapper;

    @Resource
    PageTagSortMapper pageTagSortMapper;

    @Override
    public BaseResult selectAllPageTagInfo() {
        /**
         * 1.查询所有页面标签的分类信息
         */
        List<PageTagSortDTO> pageTagSortDTOList = new ArrayList<>();
        try{
            pageTagSortDTOList = pageTagSortMapper.selectPageTagSort(new PageTagSortDTO());
        }catch (Exception e){
            return BaseResult.ERROR((long) -1,e.getMessage(),null);
        }
        /**
         * 2.通过页面标签的分类信息查询页面标签
         */
        List<HashMap<String,Object>> result = new ArrayList<>();
        List<PageTagInfoDTO> pageTagInfoDTOList = new ArrayList<>();
        HashMap<String,Object> map = new HashMap();
        /**
         * 2-1.先查询没有sort的页面标签
         */
        try {
            pageTagInfoDTOList = pageTagInfoMapper.selectPageTagInfoBySort("默认");
        }catch (Exception e){
            return BaseResult.ERROR((long) -1,e.getMessage(),null);
        }
        /**
         * 2-2按前端需要封装
         */
        if(Objects.nonNull(pageTagInfoDTOList) && !pageTagInfoDTOList.isEmpty()){
            map.put("sort","默认");
            map.put("list",pageTagInfoDTOList);
            result.add(map);
        }
        for(PageTagSortDTO pageTagSortDTO : pageTagSortDTOList){
            map = new HashMap();
            /**
             * 2-3.再查询有sort的页面标签
             */
            try {
                pageTagInfoDTOList = pageTagInfoMapper.selectPageTagInfoBySort(pageTagSortDTO.getSort());
            }catch (Exception e){
                return BaseResult.ERROR((long) -1,e.getMessage(),null);
            }
            /**
             * 2-4按前端需要封装
             */
            if(Objects.nonNull(pageTagInfoDTOList) && !pageTagInfoDTOList.isEmpty()) {
                map.put("sort", pageTagSortDTO.getSort());
                map.put("list", pageTagInfoDTOList);
                result.add(map);
            }
        }
        return BaseResult.SUCCESS(result);
    }

    @Override
    public BaseResult getPageTagInfoById(Long id) {
        PageTagInfoDTO pageTagInfoDTO = new PageTagInfoDTO();
        pageTagInfoDTO.setId(id);
        try {
            return BaseResult.SUCCESS(pageTagInfoMapper.selectPageTagInfo(pageTagInfoDTO).get(0));
        }catch (Exception e){
            return BaseResult.ERROR((long) -1,e.getMessage(),null);
        }
    }

    @Override
    public BaseResult getPageTagSort() {
        /**
         * 查询所有页面标签的分类信息
         */
        try{
            return BaseResult.SUCCESS(pageTagSortMapper.selectPageTagSort(new PageTagSortDTO()));
        }catch (Exception e){
            return BaseResult.ERROR((long) -1,e.getMessage(),null);
        }
    }

    @Override
    public BaseResult setPageTagInfo(PageTagInfoDTO pageTagInfoDTO) {
        /**
         * 1.查询sort是否存在,不存在则插入
         */
        try{
            PageTagSortDTO pageTagSortDTO = new PageTagSortDTO();
            pageTagSortDTO.setSort(pageTagInfoDTO.getSort());
            List<PageTagSortDTO> pageTagSortDTOList = pageTagSortMapper.selectPageTagSort(pageTagSortDTO);
            if(pageTagSortDTOList.isEmpty()){
                pageTagSortMapper.insertPageTagSort(pageTagSortDTO);
            }
        }catch (Exception e){
            return BaseResult.ERROR((long) -1,e.getMessage(),null);
        }
        /**
         * 2.如果id为空则插入（同时判断条件），不为空则更新
         */
        if(Objects.nonNull(pageTagInfoDTO.getId())) {
            try {
                pageTagInfoMapper.updatePageTagInfo(pageTagInfoDTO);
                return BaseResult.SUCCESS(pageTagInfoDTO.getId());
            } catch (Exception e) {
                return BaseResult.ERROR((long) -1, e.getMessage(), null);
            }
        }else {
            /**
             * 2-1.判断code是否唯一
             */
            try {
                PageTagInfoDTO pageTagInfo = new PageTagInfoDTO();
                pageTagInfo.setCode(pageTagInfoDTO.getCode());
                List<PageTagInfoDTO> pageTagInfoDTOList = pageTagInfoMapper.selectPageTagInfo(pageTagInfo);
                if(!pageTagInfoDTOList.isEmpty()){
                    return BaseResult.FAIL((long) -1, "标签标码重复", null);
                }
            } catch (Exception e) {
                return BaseResult.ERROR((long) -1, e.getMessage(), null);
            }
            /**
             * 2-2.判断url是否唯一
             */
            try {
                PageTagInfoDTO pageTagInfo = new PageTagInfoDTO();
                pageTagInfo.setUrl(pageTagInfoDTO.getUrl());
                List<PageTagInfoDTO> pageTagInfoDTOList = pageTagInfoMapper.selectPageTagInfo(pageTagInfo);
                if(!pageTagInfoDTOList.isEmpty()){
                    return BaseResult.FAIL((long) -1, "标签路径重复", null);
                }
            } catch (Exception e) {
                return BaseResult.ERROR((long) -1, e.getMessage(), null);
            }
            /**
             * 2-3.插入
             */
            try {
                pageTagInfoMapper.insertPageTagInfo(pageTagInfoDTO);
                return BaseResult.SUCCESS(pageTagInfoDTO.getId());
            } catch (Exception e) {
                return BaseResult.ERROR((long) -1, e.getMessage(), null);
            }
        }
    }
}
