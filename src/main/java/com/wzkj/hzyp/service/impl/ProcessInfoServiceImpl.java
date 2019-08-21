package com.wzkj.hzyp.service.impl;

import com.wzkj.hzyp.dao.jpa.ProcessInfoRepository;
import com.wzkj.hzyp.dao.mybatis.ProcessInfoMapper;
import com.wzkj.hzyp.entity.ProcessInfo;
import com.wzkj.hzyp.service.ProcessInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @user zhaoMaoJie
 * @date {DATE}
 */
@Service
public class ProcessInfoServiceImpl implements ProcessInfoService {

    @Autowired
    private ProcessInfoRepository processInfoRepository;

    @Autowired
    private ProcessInfoMapper processInfoMapper;

    @Override
    public void saveProcessInfo(ProcessInfo processInfo) {
        if(processInfo != null){
            processInfoRepository.save(processInfo);
        }
    }

    @Override
    public ProcessInfo getProcessInfoById(String id) {
        ProcessInfo processInfo = processInfoMapper.selectByPrimaryKey(id);
        return processInfo;
    }

    @Override
    public void deleteProcessById(String id) {
        ProcessInfo processInfo = processInfoMapper.selectByPrimaryKey(id);
        processInfoRepository.delete(processInfo);
    }

    @Override
    public List<ProcessInfo> getProcessInfoByReceviedId(String receviedId) {
        List<ProcessInfo> processInfoList = processInfoMapper.getProcessInfoByReceviedId(receviedId);
        return processInfoList;
    }
}
