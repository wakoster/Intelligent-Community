package com.graduation.management.mapper;

import com.graduation.management.domain.PageTagInfo;
import com.graduation.management.dto.PageTagSortDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PageTagSortMapper {
    /**
    * 根据条件查询页面标签
    * @param pageTagSortDTO
    * @return
    */
    List<PageTagSortDTO> selectPageTagSort(PageTagSortDTO pageTagSortDTO);

    /**
     * 更新页面标签
     * @param pageTagSortDTO
     * @return
     */
    Integer updatePageTagSort(PageTagSortDTO pageTagSortDTO);

    /**
     * 插入页面标签
     * @param pageTagSortDTO
     * @return
     */
    Integer insertPageTagSort(PageTagSortDTO pageTagSortDTO);

    /**
     * 删除页面标签
     * @param id
     * @return
     */
    Integer deletePageTagSortById(Long id);
}
