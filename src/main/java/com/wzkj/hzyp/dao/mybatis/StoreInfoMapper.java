package com.wzkj.hzyp.dao.mybatis;

import com.wzkj.hzyp.entity.StoreInfo;

public interface StoreInfoMapper {

    int deleteByPrimaryKey(String id);

    int insert(StoreInfo record);

    int insertSelective(StoreInfo record);

    StoreInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(StoreInfo record);

    int updateByPrimaryKey(StoreInfo record);
}