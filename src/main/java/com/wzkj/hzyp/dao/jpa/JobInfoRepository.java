package com.wzkj.hzyp.dao.jpa;

import com.wzkj.hzyp.entity.JobInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @user zhaoMaoJie
 * @date {DATE}
 */
@Repository
public interface JobInfoRepository extends JpaRepository<JobInfo,String>{
}
