package com.wzkj.hzyp.dao.mybatis;

import com.wzkj.hzyp.entity.EntryInfo;

public interface EntryInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(EntryInfo record);

    int insertSelective(EntryInfo record);

    EntryInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EntryInfo record);

    int updateByPrimaryKey(EntryInfo record);
}