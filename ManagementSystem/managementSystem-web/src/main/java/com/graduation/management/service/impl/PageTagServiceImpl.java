package com.graduation.management.service.impl;

import com.graduation.management.domain.PageTagSort;
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
        List<PageTagSortDTO> pageTagSorts = null;
        try{
            pageTagSorts = pageTagSortMapper.selectPageTagSort(new PageTagSortDTO());
        }catch (Exception e){
            return BaseResult.FAIL((long) -1,e.getMessage(),null);
        }
        /**
         * 2.通过页面标签的分类信息查询页面标签
         */
        List<HashMap<String,Object>> result = new ArrayList<>();
        List<PageTagInfoDTO> pageTagInfoDTOS = null;
        HashMap<String,Object> map = new HashMap();
        /**
         * 2-1.先查询没有sort的页面标签
         */
        try {
            pageTagInfoDTOS = pageTagInfoMapper.selectPageTagInfoBySort("默认");
        }catch (Exception e){
            return BaseResult.FAIL((long) -1,e.getMessage(),null);
        }
        /**
         * 2-2按前端需要封装
         */
        if(Objects.nonNull(pageTagInfoDTOS) && !pageTagInfoDTOS.isEmpty()){
            map.put("sort","默认");
            map.put("list",pageTagInfoDTOS);
            result.add(map);
        }
        for(PageTagSortDTO pageTagSortDTO : pageTagSorts){
            map = new HashMap();
            /**
             * 2-3.再查询有sort的页面标签
             */
            try {
                pageTagInfoDTOS = pageTagInfoMapper.selectPageTagInfoBySort(pageTagSortDTO.getSort());
            }catch (Exception e){
                return BaseResult.FAIL((long) -1,e.getMessage(),null);
            }
            /**
             * 2-4按前端需要封装
             */
            if(Objects.nonNull(pageTagInfoDTOS) && !pageTagInfoDTOS.isEmpty()) {
                map.put("sort", pageTagSortDTO.getSort());
                map.put("list", pageTagInfoDTOS);
            }
            result.add(map);
        }

        return BaseResult.SUCCESS(result);
    }
}
