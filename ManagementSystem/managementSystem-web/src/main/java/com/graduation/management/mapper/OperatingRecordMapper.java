package com.graduation.management.mapper;

import com.graduation.management.dto.OperatingRecordDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OperatingRecordMapper {
    /**
     * 根据条件查询安装包操作记录
     * @param operatingRecordDTO
     * @return
     */
    List<OperatingRecordDTO> selectOperatingRecord(OperatingRecordDTO operatingRecordDTO,Integer rows);

    /**
     * 插入安装包操作记录
     * @param operatingRecordDTO
     * @return
     */
    Integer insertOperatingRecord(OperatingRecordDTO operatingRecordDTO);

    /**
     * 设置安装包操作记录不可见
     * @param id
     * @return
     */
    Integer hideOperatingRecordById(Long id);
}
