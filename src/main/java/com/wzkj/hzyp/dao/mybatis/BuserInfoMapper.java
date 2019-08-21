package com.wzkj.hzyp.dao.mybatis;

import com.wzkj.hzyp.entity.BuserInfo;
import org.apache.ibatis.annotations.Param;

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
    int deleteByPrimaryKey(String id);

    int insert(BuserInfo record);

    int insertSelective(BuserInfo record);

    BuserInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BuserInfo record);

    int updateByPrimaryKey(BuserInfo record);
}