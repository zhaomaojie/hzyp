package com.wzkj.hzyp.service;

import com.wzkj.hzyp.entity.AuserInfo;
import com.wzkj.hzyp.vo.CashOutListVO;

import java.util.List;
import java.util.Map;

/**
 * @user zhaoMaoJie
 * @date {DATE}
 */
public interface AUserService {

    /* *
     * 通过电话号码判断用户是否存在
     * 存在则返回true
     * @author zhaoMaoJie
     * @date 2019/7/25 0025
     */
    public boolean queryUserIsExist(String phoneNumber);

    /* *
     * 保存用户信息
     * @author zhaoMaoJie
     * @date 2019/7/26 0026
     */
    void saveUser(AuserInfo userInfoEntity);

    /* *
     * 通过手机号获取用户信息
     * @author zhaoMaoJie
     * @date 2019/7/26 0026
     */
    AuserInfo getUserInfoByPhone(String phoneNumber);

    /* *
     * 通过id查找用户
     * @author zhaoMaoJie
     * @date 2019/8/1 0001
     */
    AuserInfo getUserById(String id);

    /* *
     * 更改用于信息
     * @author zhaoMaoJie
     * @date 2019/8/2 0002
     */
    void updateUserInfo(AuserInfo userInfo);

    /* *
     * A 获取推荐的简历数
     * @author zhaoMaoJie
     * @date 2019/8/11 0011
     */
    Integer getRecommenNumber(String userId);

    /* *
     * 获取成功入职人数
     * @author zhaoMaoJie
     * @date 2019/8/11 0011
     */
    Integer getEntrySuccessNumber(String userId);

    /* *
     *
     * @author zhaoMaoJie
     * @date 2019/8/11 0011
     */
    Integer getNoCashNumber(String userId);

    /* *
     * 获取提现总额
     * @author zhaoMaoJie
     * @date 2019/8/11 0011
     */
    Integer getTotalMoney(String userId);

    /* *
     * 获取需要改变岗位总金额的 id和金额
     * @author zhaoMaoJie
     * @date 2019/8/11 0011
     */
    List<Map<String,Object>> getChangeJobInfo(String userId);

    /* *
     * 改变岗位金额
     * @author zhaoMaoJie
     * @date 2019/8/11 0011
     */
    void changeJobMoney(List<Map<String,Object>> list,String id);

    /* *
     * 获取需要改变的id
     * @author zhaoMaoJie
     * @date 2019/8/11 0011
     */
    List<String> getNeedChangeIds(String userId);

    /* *
     * 改变入职表iscash字段
     * @author zhaoMaoJie
     * @date 2019/8/11 0011
     */
    void updateEntryIsCashById(List<String> list);

    /* *
     * 通过openId 获取用户
     * @author zhaoMaoJie
     * @date 2019/8/15 0015
     */
    AuserInfo getAuserInfoByOpenId(String openId);

    /* *
     * 获取可提现列表
     * @author zhaoMaoJie
     * @date 2019/9/9 0009
     */
    List<CashOutListVO> getCashoutList(String aUserId);

    /* *
     * 提现操作
     * @author zhaoMaoJie
     * @date 2019/9/9 0009
     */
    boolean cashout(String aUserId);

    /* *
     * 获取提现历史信息
     * @author zhaoMaoJie
     * @date 2019/9/11 0011
     */
    List<CashOutListVO> getCashoutHistary(String aUserId);
}
