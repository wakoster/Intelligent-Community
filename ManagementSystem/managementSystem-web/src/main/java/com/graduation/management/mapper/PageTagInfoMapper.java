package com.graduation.management.mapper;

import com.graduation.management.dto.PageTagInfoDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PageTagInfoMapper {
    /**
     * 根据条件查询页面标签
     * @param pageTagInfoDTO
     * @return
     */
    List<PageTagInfoDTO> selectPageTagInfo(PageTagInfoDTO pageTagInfoDTO);

    /**
     * 根据标签类别查询页面标签
     * @param sort
     * @return
     */
    List<PageTagInfoDTO> selectPageTagInfoBySort(String sort);

    /**
     * 更新页面标签
     * @param pageTagInfoDTO
     * @return
     */
    Integer updatePageTagInfo(PageTagInfoDTO pageTagInfoDTO);

    /**
     * 插入页面标签
     * 返回插入标签的主键id
     * @param pageTagInfoDTO
     * @return
     */
    Long insertPageTagInfo(PageTagInfoDTO pageTagInfoDTO);

    /**
     * 删除页面标签
     * @param id
     * @return
     */
    Integer deletePageTagInfoById(Long id);
}
