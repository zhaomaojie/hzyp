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

    /* *
     * 根据receviedI的获取
     * @author zhaoMaoJie
     * @date 2019/8/26 0026
     */
    Integer getNewSortNumber(@Param("receviedId") String receviedId);
    int deleteByPrimaryKey(String id);

    ProcessInfo selectByPrimaryKey(String id);



}