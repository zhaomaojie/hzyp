package com.wzkj.hzyp.dao.jpa;

import com.wzkj.hzyp.entity.ProcessInfo;
import com.wzkj.hzyp.entity.ReceviedInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/* *
 *
 * @author zhaoMaoJie
 * @date 2019/7/18 0018
 */
@Repository
public interface ReceviedInfoRepository extends JpaRepository<ReceviedInfo,String> {

    ReceviedInfo findByJobIdAndResumeId(String jobId,String resumeId);
}
