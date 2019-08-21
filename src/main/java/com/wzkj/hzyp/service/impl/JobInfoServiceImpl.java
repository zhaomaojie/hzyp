package com.wzkj.hzyp.service.impl;

import com.wzkj.hzyp.dao.jpa.CollectionJobInfoRepository;
import com.wzkj.hzyp.dao.jpa.JobInfoRepository;
import com.wzkj.hzyp.dao.mybatis.JobInfoMapper;
import com.wzkj.hzyp.dao.mybatis.JpCityInfoMapper;
import com.wzkj.hzyp.dao.mybatis.JpIndustryInfoMapper;
import com.wzkj.hzyp.entity.CollectionJob;
import com.wzkj.hzyp.entity.JobInfo;
import com.wzkj.hzyp.entity.JpCityInfo;
import com.wzkj.hzyp.entity.JpIndustryInfo;
import com.wzkj.hzyp.service.JobInfoService;
import com.wzkj.hzyp.utils.StringUtils;
import org.apache.ibatis.annotations.MapKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/* *
 *
 * @author zhaoMaoJie
 * @date 2019/8/1 0001
 */
@Service
@Transactional
public class JobInfoServiceImpl implements JobInfoService {

    @Autowired
    private CollectionJobInfoRepository collectionJobInfoRepository;

    @Autowired
    private JobInfoRepository jobInfoRepository;

    @Autowired
    private JpCityInfoMapper jpCityInfoMapper;

    @Autowired
    private JpIndustryInfoMapper jpIndustryInfoMapper;

    @Autowired
    private JobInfoMapper mapper;

    @Override
    public List<Map<String,Object>> getJobList(String keyWord, Integer label) {
        List<Map<String,Object>> list = mapper.getJobList(keyWord,label);
        return list;
    }

    @Override
    @MapKey("id")
    public Map<String,Object> jobDetail(String jobId) {
        Map<String,Object> jobInfo = mapper.jobDetail(jobId);
        return jobInfo;
    }

    @Override
    public void collectionJob(CollectionJob collectionJob) {
        if(collectionJob != null){
            collectionJobInfoRepository.save(collectionJob);
        }
    }

    @Override
    public boolean isCollection(String id,String jobId) {
        CollectionJob collectionJob = collectionJobInfoRepository.findByAUserIdAndAndJobId(id,jobId);
        if(collectionJob != null){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public JobInfo getJobInfoById(String id) {
        if(StringUtils.isNotBlank(id)){
            JobInfo jobInfo = mapper.selectByPrimaryKey(id);
            return jobInfo;
        }
        return null;
    }

    @Override
    public void saveJobInfo(JobInfo jobInfo) {
        if(jobInfo != null){
            jobInfoRepository.save(jobInfo);
        }
    }

    @Override
    public void changeCollectionStatus(String userId, String jobId,Integer status) {
        CollectionJob collectionJob = collectionJobInfoRepository.findByAUserIdAndAndJobId(userId, jobId);
        collectionJob.setStatus(status);
        collectionJobInfoRepository.save(collectionJob);
    }

    @Override
    public List<Map<String,Object>> collectionJobList(String aUserId,Integer status) {
            List<Map<String,Object>> list = mapper.collectionJobList(aUserId, status);
            return list;
    }

    @Override
    public List<Map<String, Object>> publishJobList(String bUserId, String jobName, Integer status) {
        List<Map<String, Object>> list = mapper.publishJobList(bUserId, jobName, status);
        return list;
    }

    @Override
    public void updateStatus(String jobId, Integer status) {
        JobInfo jobInfo = mapper.selectByPrimaryKey(jobId);
        jobInfo.setStatus(status);
        jobInfoRepository.save(jobInfo);
    }

    @Override
    public List<JpCityInfo> getAreaByParentCode(String parentCode) {
        List<JpCityInfo> list = jpCityInfoMapper.getAreaByParentCode(parentCode);
        return list;
    }

    @Override
    public List<JpIndustryInfo> getIndustryInfo() {
        List<JpIndustryInfo> list = jpIndustryInfoMapper.getIndustryInfo();
        return list;
    }


}
