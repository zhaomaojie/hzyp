package com.wzkj.hzyp.service.impl;

import com.wzkj.hzyp.common.AjaxResponse;
import com.wzkj.hzyp.common.ResponseCode;
import com.wzkj.hzyp.dao.jpa.StoreInfoRepository;
import com.wzkj.hzyp.dao.mybatis.StoreInfoMapper;
import com.wzkj.hzyp.entity.FileEntity;
import com.wzkj.hzyp.entity.StoreInfo;
import com.wzkj.hzyp.service.StoreInfoService;
import com.wzkj.hzyp.utils.AliyunOSSUtil;
import com.wzkj.hzyp.utils.FileUtil;
import com.wzkj.hzyp.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;

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

    // 阿里云配置信息
    @Autowired
    private AliyunOSSUtil aliyunOSSUtil;

    @Override
    public StoreInfo getStoreInfoById(String id) {
        if(StringUtils.isNotBlank(id)){
            StoreInfo storeInfo = storeInfoMapper.selectByPrimaryKey(id);
            return storeInfo;
        }
        return null;
    }

    @Override
    @Transactional
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

    @Override
    public String getUploadImageUrl(MultipartFile file) {
        try {
            // 判断文件
            String filename = file.getOriginalFilename();
            if (file!=null) {
                if (!"".equals(filename.trim())) {
                    File newFile = new File(filename);
                    FileOutputStream os = new FileOutputStream(newFile);
                    os.write(file.getBytes());
                    os.close();
                    file.transferTo(newFile);
                    // 上传到OSS
                    FileEntity fileEntity =  aliyunOSSUtil.uploadFile(newFile,"store");
                    if(fileEntity == null){
                        return null;
                    }
                    //删除本地图片
                    File directory = new File("");//参数为空
                    String courseFile = directory.getCanonicalPath() ;
                    String path = courseFile + "\\" + filename;
                    FileUtil.deleteFile(path);
                    //返回地址
                    String webUrl = fileEntity.getWebUrl();
                    return webUrl;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    @Transactional
    public boolean bindStoreBusinessImg(String id, MultipartFile file) {
        StoreInfo storeInfo = storeInfoMapper.selectByPrimaryKey(id);
        String imgUrl = getUploadImageUrl(file);
        if(StringUtils.isNotBlank(imgUrl)){
            storeInfo.setBusinessImg(imgUrl);
            return true;
        }else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean bindStoreCompanyImg(String id, MultipartFile file) {
        StoreInfo storeInfo = storeInfoMapper.selectByPrimaryKey(id);
        String imgUrl = getUploadImageUrl(file);
        if(StringUtils.isNotBlank(imgUrl)){
            storeInfo.setCompanyImg(imgUrl);
            return true;
        }else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean bindStoreLogo(String id, MultipartFile file) {
        StoreInfo storeInfo = storeInfoMapper.selectByPrimaryKey(id);
        String imgUrl = getUploadImageUrl(file);
        if(StringUtils.isNotBlank(imgUrl)){
            storeInfo.setLogo(imgUrl);
            return true;
        }else {
            return false;
        }
    }
}
