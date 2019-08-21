package com.wzkj.hzyp.service.impl;

import com.wzkj.hzyp.dao.jpa.ReceviedInfoRepository;
import com.wzkj.hzyp.dao.mybatis.ReceviedInfoMapper;
import com.wzkj.hzyp.entity.ReceviedInfo;
import com.wzkj.hzyp.service.ReceviedInfoService;
import com.wzkj.hzyp.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @user zhaoMaoJie
 * @date {DATE}
 */
@Service
public class ReceviedInfoServiceImpl implements ReceviedInfoService {

    @Autowired
    private ReceviedInfoRepository receviedInfoRepository;

    @Autowired
    private ReceviedInfoMapper receviedInfoMapper;

    @Override
    public ReceviedInfo getReceviedInfoById(String id) {
        if(StringUtils.isNotBlank(id)){
            ReceviedInfo receviedInfo = receviedInfoMapper.selectByPrimaryKey(id);
            return receviedInfo;
        }
        return null;
    }

    @Override
    public void deleteReceviedInfoById(String id) {
        ReceviedInfo receviedInfo = receviedInfoMapper.selectByPrimaryKey(id);
        receviedInfoRepository.delete(receviedInfo);
    }

    @Override
    public void saveReceviedInfo(ReceviedInfo receviedInfo) {
        if(receviedInfo != null){
            receviedInfoRepository.save(receviedInfo);
        }
    }
}
