package com.wzkj.hzyp.dao.jpa;

import com.wzkj.hzyp.entity.ProcessInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/* *
 *
 * @author zhaoMaoJie
 * @date 2019/7/18 0018
 */
@Repository
public interface ProcessInfoRepository extends JpaRepository<ProcessInfo,String> {

    Optional<ProcessInfo> findById(String id);
}
