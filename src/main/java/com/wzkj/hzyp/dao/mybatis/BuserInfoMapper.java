package com.wzkj.hzyp.dao.mybatis;

import com.wzkj.hzyp.entity.BuserInfo;
import com.wzkj.hzyp.entity.CashoutDetail;
import com.wzkj.hzyp.vo.CashOutListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BuserInfoMapper {

    /* *
     * 获取履约金余额
     * @author zhaoMaoJie
     * @date 2019/8/12 0012
     */
    Integer getAmountBalance(@Param("bUserId") String bUserId);

    /* *
     * 获取发布的岗位数
     * @author zhaoMaoJie
     * @date 2019/8/12 0012
     */
    Integer getJobNum(@Param("bUserId") String bUserId);

    /* *
     * 获取收到的简历数
     * @author zhaoMaoJie
     * @date 2019/8/12 0012
     */
    Integer getResumeNum(@Param("bUserId") String bUserId);

    /* *
     * 获取B端累计花费
     * @author zhaoMaoJie
     * @date 2019/8/12 0012
     */
    Integer getSumMoney(@Param("bUserId") String bUserId);


    /* *
     * Bduan 获取提现详情
     * @author zhaoMaoJie
     * @date 2019/8/12 0012
     */
    List<CashOutListVO> getCashoutDetail(@Param("bUserId") String bUserId);
    int deleteByPrimaryKey(String id);

    int insert(BuserInfo record);

    int insertSelective(BuserInfo record);

    BuserInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BuserInfo record);

    int updateByPrimaryKey(BuserInfo record);
}