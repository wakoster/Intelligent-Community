package com.graduation.management.mapper;

import com.graduation.management.domain.PageTagInfo;
import com.graduation.management.dto.PageTagSortDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PageTagSortMapper {
    /**
    * 根据条件查询用户
    * @param pageTagSortDTO
    * @return
    */
    List<PageTagSortDTO> selectPageTagSort(PageTagSortDTO pageTagSortDTO);

    /**
     * 更新图片标注信息
     * @param pageTagSortDTO
     * @return
     */
    Integer updatePageTagSort(PageTagSortDTO pageTagSortDTO);

    /**
     * 插入图片标注信息
     * @param pageTagSortDTO
     * @return
     */
    Integer insertPageTagSort(PageTagSortDTO pageTagSortDTO);

    /**
     * 删除图片标注信息
     * @param id
     * @return
     */
    Integer deletePageTagSortById(Long id);
}
