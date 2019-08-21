package com.wzkj.hzyp.service.impl;

import com.wzkj.hzyp.dao.jpa.BUserInfoRepository;
import com.wzkj.hzyp.dao.mybatis.BuserInfoMapper;
import com.wzkj.hzyp.dao.mybatis.CashoutDetailMapper;
import com.wzkj.hzyp.entity.BuserInfo;
import com.wzkj.hzyp.entity.CashoutDetail;
import com.wzkj.hzyp.service.BuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @user zhaoMaoJie
 * @date {DATE}
 */
@Service
public class BuserServiceImpl implements BuserService {

    @Autowired
    private CashoutDetailMapper cashoutDetailMapper;

    @Autowired
    private BUserInfoRepository bUserInfoRepository;

    @Autowired
    private BuserInfoMapper bUserInfoMapper;

    @Override
    public boolean queryUserIsExist(String phone) {
        BuserInfo buserInfo = bUserInfoRepository.findByEmpowerPhone(phone);
        if(buserInfo != null){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public BuserInfo getUserInfoByPhone(String phone) {
        BuserInfo buserInfo = bUserInfoRepository.findByEmpowerPhone(phone);
        return buserInfo;
    }

    @Override
    public void saveUser(BuserInfo buserInfo) {
        bUserInfoRepository.save(buserInfo);
    }

    @Override
    public BuserInfo getBuserById(String id) {
        BuserInfo buserInfo = bUserInfoMapper.selectByPrimaryKey(id);
        return buserInfo;
    }

    @Override
    public Integer getAmountBalance(String userId) {
        Integer num = bUserInfoMapper.getAmountBalance(userId);
        return num;
    }

    @Override
    public Integer getJobNum(String userId) {
        Integer jobNum = bUserInfoMapper.getJobNum(userId);
        return jobNum;
    }

    @Override
    public Integer getResumeNum(String userId) {
        Integer resumeNum = bUserInfoMapper.getResumeNum(userId);
        return resumeNum;
    }

    @Override
    public Integer getSumMoney(String userId) {
        Integer sumMoney = bUserInfoMapper.getSumMoney(userId);
        return sumMoney;
    }

    @Override
    public List<CashoutDetail> getCashoutDetail(String userId) {
        return null;
    }


}
