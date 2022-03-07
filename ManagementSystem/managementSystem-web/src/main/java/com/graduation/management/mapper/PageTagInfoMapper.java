package com.graduation.management.mapper;

import com.graduation.management.domain.PageTagInfo;
import com.graduation.management.dto.PageTagInfoDTO;
import com.graduation.management.dto.UserInfoDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PageTagInfoMapper {
    /**
     * 根据条件查询用户
     * @param pageTagInfoDTO
     * @return
     */
    List<PageTagInfoDTO> selectPageTagInfo(PageTagInfoDTO pageTagInfoDTO);

    /**
     * 根据标签类别查询用户
     * @param sort
     * @return
     */
    List<PageTagInfoDTO> selectPageTagInfoBySort(String sort);

    /**
     * 更新图片标注信息
     * @param pageTagInfoDTO
     * @return
     */
    Integer updatePageTagInfo(PageTagInfoDTO pageTagInfoDTO);

    /**
     * 插入图片标注信息
     * @param pageTagInfoDTO
     * @return
     */
    Integer insertPageTagInfo(PageTagInfoDTO pageTagInfoDTO);

    /**
     * 删除图片标注信息
     * @param id
     * @return
     */
    Integer deletePageTagInfoById(Long id);
}
