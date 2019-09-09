package com.wzkj.hzyp.dao.mybatis;

import com.wzkj.hzyp.entity.AuserInfo;
import com.wzkj.hzyp.vo.CashOutListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AuserInfoMapper {

    /* *
     * A 获取推荐的简历数
     * @author zhaoMaoJie
     * @date 2019/8/11 0011
     */
    Integer getRecommenNumber(@Param("aUserId") String aUserId);

    /* *
     * 获取成功入职人数
     * @author zhaoMaoJie
     * @date 2019/8/11 0011
     */
    Integer getEntrySuccessNumber(@Param("aUserId") String aUserId);

    /* *
     *
     * @author zhaoMaoJie
     * @date 2019/8/11 0011
     */
    Integer getNoCashNumber(@Param("aUserId") String aUserId);

    /* *
     * 获取提现总额
     * @author zhaoMaoJie
     * @date 2019/8/11 0011
     */
    Integer getTotalMoney(@Param("aUserId") String aUserId);

    /* *
     * 获取需要改变岗位总金额的 id和金额
     * @author zhaoMaoJie
     * @date 2019/8/11 0011
     */
    List<Map<String,Object>> getChangeJobInfo(@Param("aUserId") String aUserId);

    /* *
     * 改变入职表iscash字段
     * @author zhaoMaoJie
     * @date 2019/8/11 0011
     */
    void updateEntryIsCashById(@Param("aUserId") String aUserId);

    /* *
     * 获取需要改变的id
     * @author zhaoMaoJie
     * @date 2019/8/11 0011
     */
    List<String> getNeedChangeIds(@Param("aUserId") String aUserId);

    /* *
     * 获取可提现列表
     * @author zhaoMaoJie
     * @date 2019/9/9 0009
     */
    List<CashOutListVO> getCashoutList(@Param("aUserId") String aUserId);

    /* *
     * 提现操作
     * @author zhaoMaoJie
     * @date 2019/9/9 0009
     */
    Integer cashout(@Param("aUserId") String aUserId);

    /* *
     * 修改状态
     * @author zhaoMaoJie
     * @date 2019/9/9 0009
     */
    void updateEntryInfoIsCash(@Param("aUserId") String aUserId);

    int deleteByPrimaryKey(String id);

    int insert(AuserInfo record);

    int insertSelective(AuserInfo record);

    AuserInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AuserInfo record);

    int updateByPrimaryKey(AuserInfo record);
}