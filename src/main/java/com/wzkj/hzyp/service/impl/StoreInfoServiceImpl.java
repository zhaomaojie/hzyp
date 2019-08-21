package com.wzkj.hzyp.service.impl;

import com.wzkj.hzyp.dao.jpa.StoreInfoRepository;
import com.wzkj.hzyp.dao.mybatis.StoreInfoMapper;
import com.wzkj.hzyp.entity.StoreInfo;
import com.wzkj.hzyp.service.StoreInfoService;
import com.wzkj.hzyp.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @user zhaoMaoJie
 * @date {DATE}
 */
@Service
public class StoreInfoServiceImpl implements StoreInfoService {

    @Autowired
    private StoreInfoMapper storeInfoMapper;

    @Autowired
    private StoreInfoRepository storeInfoRepository;

    @Override
    public StoreInfo getStoreInfoById(String id) {
        if(StringUtils.isNotBlank(id)){
            StoreInfo storeInfo = storeInfoMapper.selectByPrimaryKey(id);
            return storeInfo;
        }
        return null;
    }

    @Override
    public void saveStoreInfo(StoreInfo storeInfo) {
        if(storeInfo != null){
            storeInfoRepository.save(storeInfo);
        }
    }

    @Override
    public void deleteStoreById(String id) {
        StoreInfo storeInfo = storeInfoMapper.selectByPrimaryKey(id);
        storeInfoRepository.delete(storeInfo);
    }

    @Override
    public StoreInfo getStoreByUserId(String userId) {
        StoreInfo storeInfo = storeInfoRepository.findByBUserId(userId);
        return storeInfo;
    }
}
