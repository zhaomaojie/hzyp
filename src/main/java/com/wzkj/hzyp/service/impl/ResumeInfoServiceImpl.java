package com.wzkj.hzyp.service.impl;

import com.wzkj.hzyp.dao.jpa.CollectionJobInfoRepository;
import com.wzkj.hzyp.dao.jpa.ReceviedInfoRepository;
import com.wzkj.hzyp.dao.jpa.ResumeInfoRepository;
import com.wzkj.hzyp.dao.mybatis.ResumeInfoMapper;
import com.wzkj.hzyp.entity.CollectionJob;
import com.wzkj.hzyp.entity.ReceviedInfo;
import com.wzkj.hzyp.entity.ResumeInfo;
import com.wzkj.hzyp.service.ResumeInfoService;
import com.wzkj.hzyp.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @user zhaoMaoJie
 * @date {DATE}
 */
@Service
public class ResumeInfoServiceImpl implements ResumeInfoService {

    @Autowired
    private CollectionJobInfoRepository collectionJobInfoRepository;

    @Autowired
    private ReceviedInfoRepository receviedInfoRepository;

    @Autowired
    private ResumeInfoRepository resumeInfoRepository;

    @Autowired
    private ResumeInfoMapper resumeInfoMapper;
    @Override
    public List<ResumeInfo> getResumeList(String userId, String name, String phone) {
        return null;
    }

    @Override
    public void saveResume(ResumeInfo resumeInfo) {
        if(resumeInfo != null){
            resumeInfoRepository.save(resumeInfo);
        }
    }

    @Override
    public ResumeInfo getResumeById(String id) {
        if(StringUtils.isNotBlank(id)){
            ResumeInfo resumeInfo = resumeInfoMapper.selectByPrimaryKey(id);
            return resumeInfo;
        }
        return null;
    }

    @Override
    public void deleteResumeById(String id) {
        ResumeInfo resumeInfo = resumeInfoMapper.selectByPrimaryKey(id);
        resumeInfoRepository.delete(resumeInfo);
    }

    @Override
    public boolean isPush(String jobId, String resumeId) {
        ReceviedInfo receviedInfo = receviedInfoRepository.findByJobIdAndResumeId(jobId, resumeId);
        if(receviedInfo != null){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void pushResume(ReceviedInfo receviedInfo) {
        if(receviedInfo != null){
            receviedInfoRepository.save(receviedInfo);
        }
    }

    @Override
    public List<Map<String, Object>> pushResumeList(String aUserId,String jobId,Integer status,String name,String phone) {
        List<Map<String, Object>> list = resumeInfoMapper.pushResumeList(aUserId, jobId,status, name, phone);
        return list;
    }

    @Override
    public List<ResumeInfo> resumeList(String userId, Integer status, String name, String phone) {
        List<ResumeInfo> list = resumeInfoMapper.resumeList(userId, status, name, phone);
        return list;
    }

    @Override
    public List<Map<String, Object>> resumeRecord( String resumeId) {
        List<Map<String, Object>> list = resumeInfoMapper.resumeRecord(resumeId);
        return list;
    }

    @Override
    public List<Map<String, Object>> myCandidate(String bUserId,Integer status,String name,String phone) {
        List<Map<String, Object>> list = resumeInfoMapper.myCandidate(bUserId, status, name, phone);
        return list;
    }


}
