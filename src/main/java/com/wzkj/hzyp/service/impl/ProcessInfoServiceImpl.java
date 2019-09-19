package com.wzkj.hzyp.service.impl;

import com.wzkj.hzyp.dao.jpa.ProcessInfoRepository;
import com.wzkj.hzyp.dao.mybatis.ProcessInfoMapper;
import com.wzkj.hzyp.entity.ProcessInfo;
import com.wzkj.hzyp.service.ProcessInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    @Transactional
    public void saveProcessInfo(ProcessInfo processInfo) {
        if(processInfo != null){
            processInfoRepository.save(processInfo);
        }
    }

    @Override
    public ProcessInfo getProcessInfoById(String id) {
        ProcessInfo processInfo1 = processInfoRepository.getOne(id);
        Optional<ProcessInfo> processInfo2 = processInfoRepository.findById(id);
        ProcessInfo processInfo = processInfoMapper.selectByPrimaryKey(id);

        return processInfo;
    }

    @Override
    @Transactional
    public void deleteProcessById(String id) {
        ProcessInfo processInfo = processInfoMapper.selectByPrimaryKey(id);
        processInfoRepository.delete(processInfo);
    }

    @Override
    public List<ProcessInfo> getProcessInfoByReceviedId(String receviedId) {
        List<ProcessInfo> processInfoList = processInfoMapper.getProcessInfoByReceviedId(receviedId);
        return processInfoList;
    }

    @Override
    @Transactional
    public Integer getNewSortNumber(String receviedId) {
        Integer sortNumber = processInfoMapper.getNewSortNumber(receviedId);
        sortNumber += 1;
        return sortNumber;
    }
}
