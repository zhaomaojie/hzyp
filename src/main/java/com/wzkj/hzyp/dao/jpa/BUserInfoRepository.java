package com.wzkj.hzyp.dao.jpa;

import com.wzkj.hzyp.entity.BuserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/* *
 *
 * @author zhaoMaoJie
 * @date 2019/7/18 0018
 */
@Repository
public interface BUserInfoRepository extends JpaRepository<BuserInfo,String> {

    /* *
     * 通过授权号查询用户是否存在
     * @author zhaoMaoJie
     * @date 2019/8/8 0008
     */
    BuserInfo findByEmpowerPhone(String empowerPhone);
}
