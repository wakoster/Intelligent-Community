package com.graduation.management.service;

import com.graduation.management.dto.PageTagInfoDTO;
import com.graduation.management.result.BaseResult;

public interface PageTagService {
    /**
     * 查找全部页面标签信息
     * @return
     */
    BaseResult selectAllPageTagInfo();

    /**
     * 根据id查找页面标签信息
     * @return
     */
    BaseResult getPageTagInfoById(Long id);

    /**
     * 查找页面标签分类信息
     * @return
     */
    BaseResult getPageTagSort();

    /**
     * 设置页面标签信息
     * 返回值是页面标签的自增id
     * @return
     */
    BaseResult setPageTagInfo(PageTagInfoDTO pageTagInfoDTO);
}
