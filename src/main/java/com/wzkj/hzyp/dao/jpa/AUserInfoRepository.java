package com.wzkj.hzyp.dao.jpa;

import com.wzkj.hzyp.entity.AuserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @user zhaoMaoJie
 * @date {DATE}
 */
public interface AUserInfoRepository extends JpaRepository<AuserInfo,String> {

    AuserInfo findByEmpowerPhone(String empowerPhone);

    AuserInfo getAUserInfoById(String id);

    AuserInfo findByOpenId(String openId);
}
