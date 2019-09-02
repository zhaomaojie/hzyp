package com.wzkj.hzyp.service.impl;

import com.wzkj.hzyp.dao.jpa.EntryInfoRepository;
import com.wzkj.hzyp.dao.mybatis.EntryInfoMapper;
import com.wzkj.hzyp.entity.EntryInfo;
import com.wzkj.hzyp.service.EntryInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @user zhaoMaoJie
 * @date {DATE}
 */
@Service
public class EntryInfoServiceImpl implements EntryInfoService {

    @Autowired
    private EntryInfoRepository entryInfoRepository;

    @Autowired
    private EntryInfoMapper entryInfoMapper;

    @Override
    public EntryInfo getEntryInfoById(String id) {
        EntryInfo entryInfo = entryInfoMapper.selectByPrimaryKey(id);
        return entryInfo;
    }

    @Override
    @Transactional
    public void saveEntryInfo(EntryInfo entryInfo) {
        if(entryInfo != null){
            entryInfoRepository.save(entryInfo);
        }
    }
}
