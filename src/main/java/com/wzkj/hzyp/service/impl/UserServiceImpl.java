package com.wzkj.hzyp.service.impl;

import com.wzkj.hzyp.dao.jpa.AUserInfoRepository;
import com.wzkj.hzyp.dao.jpa.CashoutDetailRepository;
import com.wzkj.hzyp.dao.jpa.EntryInfoRepository;
import com.wzkj.hzyp.dao.jpa.JobInfoRepository;
import com.wzkj.hzyp.dao.mybatis.AuserInfoMapper;
import com.wzkj.hzyp.dao.mybatis.EntryInfoMapper;
import com.wzkj.hzyp.dao.mybatis.JobInfoMapper;
import com.wzkj.hzyp.entity.AuserInfo;
import com.wzkj.hzyp.entity.CashoutDetail;
import com.wzkj.hzyp.entity.EntryInfo;
import com.wzkj.hzyp.entity.JobInfo;
import com.wzkj.hzyp.service.AUserService;
import com.wzkj.hzyp.service.EntryInfoService;
import com.wzkj.hzyp.utils.StringUtils;
import com.wzkj.hzyp.vo.CashOutListVO;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @user zhaoMaoJie
 * @date {DATE}
 */
@Service
@Transactional
public class UserServiceImpl implements AUserService {

    @Autowired
    private JobInfoMapper jobInfoMapper;

    @Autowired
    private JobInfoRepository jobInfoRepository;

    @Autowired
    private EntryInfoMapper entryInfoMapper;

    @Autowired
    private EntryInfoRepository entryInfoRepository;

    @Autowired
    private CashoutDetailRepository cashoutDetailRepository;

    @Autowired
    private AUserInfoRepository repository;

    @Autowired
    private AuserInfoMapper mapper;

    @Override
    public boolean queryUserIsExist(String phoneNumber) {
        AuserInfo userInfoEntity = repository.findByEmpowerPhone(phoneNumber);
        if(userInfoEntity != null){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void saveUser(AuserInfo userInfoEntity) {
        if(userInfoEntity != null){
            repository.save(userInfoEntity);
        }
    }

    @Override
    public AuserInfo getUserInfoByPhone(String phoneNumber) {
        if(phoneNumber != null){
            AuserInfo aUserInfoEntity = repository.findByEmpowerPhone(phoneNumber);
            return aUserInfoEntity;
        }
        return null;
    }

    @Override
    public AuserInfo getUserById(String id) {
        if(StringUtils.isNotBlank(id)){
            AuserInfo mybatisUser = mapper.selectByPrimaryKey(id);
            return mybatisUser;
        }
        return null;
    }

    @Override
    public void updateUserInfo(AuserInfo userInfo) {
        userInfo.setUpdateTime(new Date());
        repository.save(userInfo);
    }

    @Override
    public Integer getRecommenNumber(String userId) {
        Integer number = mapper.getRecommenNumber(userId);
        return number;
    }

    @Override
    public Integer getEntrySuccessNumber(String userId) {
        Integer number = mapper.getEntrySuccessNumber(userId);
        return number;
    }

    @Override
    public Integer getNoCashNumber(String userId) {
        Integer number = mapper.getNoCashNumber(userId);
        return number;
    }

    @Override
    public Integer getTotalMoney(String userId) {
        Integer number = mapper.getTotalMoney(userId);
        return number;
    }

    @Override
    public List<Map<String, Object>> getChangeJobInfo(String userId) {
        List<Map<String, Object>> list = mapper.getChangeJobInfo(userId);
        return list;
    }

    @Override
    public void changeJobMoney(List<Map<String, Object>> list,String id) {
        for (int i = 0; i < list.size(); i ++){
            Map<String,Object> map = list.get(i);
            JobInfo jobInfo = jobInfoMapper.selectByPrimaryKey((String) map.get("jobId"));
            jobInfo.setTotalAmount(jobInfo.getTotalAmount() - (Integer) map.get("sum"));
            //提现详情表
            CashoutDetail cashoutDetail = new CashoutDetail();
            cashoutDetail.setJobId(jobInfo.getId());
            cashoutDetail.setJobName(jobInfo.getJobName());
            cashoutDetail.setCashoutTime(new Date());
            cashoutDetail.setCashoutTime(new Date());
            cashoutDetail.setMoney((Integer) map.get("sum"));
            cashoutDetail.setaUserId(id);
            cashoutDetail.setbUserId(jobInfo.getbUserId());
            cashoutDetailRepository.save(cashoutDetail);

            jobInfoRepository.save(jobInfo);
        }
    }

    @Override
    public List<String> getNeedChangeIds(String userId) {
        List<String> list = mapper.getNeedChangeIds(userId);
        return list;
    }

    @Override
    public void updateEntryIsCashById(List<String> list) {
        for (int i = 0; i < list.size(); i++){
            EntryInfo entryInfo = entryInfoMapper.selectByPrimaryKey(list.get(i));
            entryInfo.setIsCash(1);
            entryInfoRepository.save(entryInfo);
        }
    }

    @Override
    public AuserInfo getAuserInfoByOpenId(String openId) {
        AuserInfo auserInfo = repository.findByOpenId(openId);
        return auserInfo;
    }

    @Override
    public List<CashOutListVO> getCashoutList(String aUserId) {
        List<CashOutListVO> list = mapper.getCashoutList(aUserId);
        return list;
    }

    @Override
    public void cashout(String aUserId) {
        mapper.cashout(aUserId);
        mapper.updateEntryInfoIsCash(aUserId);
    }


}
