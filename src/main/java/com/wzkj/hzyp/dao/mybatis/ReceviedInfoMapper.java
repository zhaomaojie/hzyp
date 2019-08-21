package com.wzkj.hzyp.dao.mybatis;

import com.wzkj.hzyp.entity.ReceviedInfo;

public interface ReceviedInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(ReceviedInfo record);

    int insertSelective(ReceviedInfo record);

    ReceviedInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ReceviedInfo record);

    int updateByPrimaryKey(ReceviedInfo record);
}