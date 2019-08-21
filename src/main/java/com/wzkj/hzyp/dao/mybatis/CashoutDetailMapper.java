package com.wzkj.hzyp.dao.mybatis;

import com.wzkj.hzyp.entity.CashoutDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CashoutDetailMapper {

    /* *
     * Bduan 获取提现详情
     * @author zhaoMaoJie
     * @date 2019/8/12 0012
     */
    List<CashoutDetail> getCashoutDetail(@Param("bUserId") String userId);
    int deleteByPrimaryKey(String id);

    int insert(CashoutDetail record);

    int insertSelective(CashoutDetail record);

    CashoutDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CashoutDetail record);

    int updateByPrimaryKey(CashoutDetail record);
}