package com.wzkj.hzyp.dao.mybatis;

import com.wzkj.hzyp.entity.JpIndustryInfo;

import java.util.List;

public interface JpIndustryInfoMapper {

    /* *
     * 获取行业信息
     * @author zhaoMaoJie
     * @date 2019/8/12 0012
     */
    List<JpIndustryInfo> getIndustryInfo();

    int deleteByPrimaryKey(Integer id);

    int insert(JpIndustryInfo record);

    int insertSelective(JpIndustryInfo record);

    JpIndustryInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JpIndustryInfo record);

    int updateByPrimaryKey(JpIndustryInfo record);
}