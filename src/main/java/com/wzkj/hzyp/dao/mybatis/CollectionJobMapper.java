package com.wzkj.hzyp.dao.mybatis;

import com.wzkj.hzyp.entity.CollectionJob;

public interface CollectionJobMapper {
    int deleteByPrimaryKey(String id);

    int insert(CollectionJob record);

    int insertSelective(CollectionJob record);

    CollectionJob selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CollectionJob record);

    int updateByPrimaryKey(CollectionJob record);
}