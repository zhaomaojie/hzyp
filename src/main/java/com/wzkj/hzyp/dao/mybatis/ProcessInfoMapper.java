package com.wzkj.hzyp.dao.mybatis;

import com.wzkj.hzyp.entity.ProcessInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ProcessInfoMapper {

    /* *
     * 通过receviedId查询流程信息
     * @author zhaoMaoJie
     * @date 2019/8/7 0007
     */
    List<ProcessInfo> getProcessInfoByReceviedId(@Param("receviedId") String receviedId);
    int deleteByPrimaryKey(String id);

    int insert(ProcessInfo record);

    int insertSelective(ProcessInfo record);

    ProcessInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProcessInfo record);

    int updateByPrimaryKey(ProcessInfo record);
}