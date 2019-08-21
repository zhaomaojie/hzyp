package com.wzkj.hzyp.dao.jpa;

import com.wzkj.hzyp.entity.CollectionJob;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @user zhaoMaoJie
 * @date {DATE}
 */
public interface CollectionJobInfoRepository extends JpaRepository<CollectionJob,String> {

    CollectionJob findByAUserIdAndAndJobId(String userId,String jobId);
}
