package com.wzkj.hzyp.dao.mybatis;

import com.wzkj.hzyp.entity.JpCityInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface JpCityInfoMapper {

    /* *
     * 通过parentCode获取地址信息
     * @author zhaoMaoJie
     * @date 2019/8/12 0012
     */
    List<JpCityInfo> getAreaByParentCode(@Param("parentCode") String parentCode);
    int deleteByPrimaryKey(Integer id);

    int insert(JpCityInfo record);

    int insertSelective(JpCityInfo record);

    JpCityInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JpCityInfo record);

    int updateByPrimaryKey(JpCityInfo record);
}