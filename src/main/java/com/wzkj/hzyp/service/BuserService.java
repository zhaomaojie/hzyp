package com.wzkj.hzyp.service;

import com.wzkj.hzyp.entity.BuserInfo;
import com.wzkj.hzyp.entity.CashoutDetail;
import com.wzkj.hzyp.vo.CashOutListVO;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * @user zhaoMaoJie
 * @date {DATE}
 */
public interface BuserService {

    /* *
     * 通过电话判断用户是否存在
     * @author zhaoMaoJie
     * @date 2019/8/8 0008
     */
    boolean queryUserIsExist(String phone);

    /* *
     * 通过手机号返回用户信息
     * @author zhaoMaoJie
     * @date 2019/8/8 0008
     */
    BuserInfo getUserInfoByPhone(String phone);

    /* *
     * 保存b端用户
     * @author zhaoMaoJie
     * @date 2019/8/8 0008
     */
    void saveUser(BuserInfo buserInfo);

    /* *
     * 通过id获取商户对象
     * @author zhaoMaoJie
     * @date 2019/8/8 0008
     */
    BuserInfo getBuserById(String id);

    /* *
     * 获取履约金余额
     * @author zhaoMaoJie
     * @date 2019/8/12 0012
     */
    Integer getAmountBalance(String userId);

    /* *
     * 获取发布的岗位数
     * @author zhaoMaoJie
     * @date 2019/8/12 0012
     */
    Integer getJobNum(String userId);

    /* *
     * 获取收到的简历数
     * @author zhaoMaoJie
     * @date 2019/8/12 0012
     */
    Integer getResumeNum(String userId);

    /* *
     * 获取B端累计花费
     * @author zhaoMaoJie
     * @date 2019/8/12 0012
     */
    Integer getSumMoney(String userId);


    /* *
     * Bduan 获取提现详情
     * @author zhaoMaoJie
     * @date 2019/8/12 0012
     */
    List<CashOutListVO> getCashoutDetail(String userId);

    /* *
     *
     * @author zhaoMaoJie
     * @date 2019/8/29 0029
     */
    BuserInfo getBuserInfoByOpenId(String oepnId);

    /* *
     * 通过id查询是否有B端对应商户信息
     * @author zhaoMaoJie
     * @date 2019/9/3 0003
     */
    boolean isReister(String id);

}
