package com.wzkj.hzyp.service.impl;

import com.wzkj.hzyp.common.AjaxResponse;
import com.wzkj.hzyp.common.ResponseCode;
import com.wzkj.hzyp.dao.jpa.CollectionJobInfoRepository;
import com.wzkj.hzyp.dao.jpa.ReceviedInfoRepository;
import com.wzkj.hzyp.dao.jpa.ResumeInfoRepository;
import com.wzkj.hzyp.dao.mybatis.ResumeInfoMapper;
import com.wzkj.hzyp.entity.CollectionJob;
import com.wzkj.hzyp.entity.FileEntity;
import com.wzkj.hzyp.entity.ReceviedInfo;
import com.wzkj.hzyp.entity.ResumeInfo;
import com.wzkj.hzyp.service.ResumeInfoService;
import com.wzkj.hzyp.utils.AliyunOSSUtil;
import com.wzkj.hzyp.utils.FileUtil;
import com.wzkj.hzyp.utils.StringUtils;
import com.wzkj.hzyp.vo.ResumeRecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
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

    // 阿里云配置信息
    @Autowired
    private AliyunOSSUtil aliyunOSSUtil;

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
    public List<Map<String, Object>> pushResumeList(String aUserId,String jobId,Integer status,String keyWord) {
        List<Map<String, Object>> list = resumeInfoMapper.pushResumeList(aUserId, jobId,status,keyWord);
        return list;
    }

    @Override
    public List<ResumeInfo> resumeList(String userId, Integer status,String keyWord) {
        List<ResumeInfo> list = resumeInfoMapper.resumeList(userId, status, keyWord);
        return list;
    }

    @Override
    public     List<ResumeRecordVO> resumeRecord(String resumeId) {
        List<ResumeRecordVO> list = resumeInfoMapper.resumeRecord(resumeId);
        return list;
    }

    @Override
    public List<Map<String, Object>> myCandidate(String bUserId,Integer status,String keyWord) {
        List<Map<String, Object>> list = resumeInfoMapper.myCandidate(bUserId, status, keyWord);
        return list;
    }

    @Override
    public boolean bindingAvatar(String id, MultipartFile file) {
        ResumeInfo resumeInfo = resumeInfoMapper.selectByPrimaryKey(id);
        String filename = file.getOriginalFilename();
        try {
            // 判断文件
            if (file!=null) {
                if (!"".equals(filename.trim())) {
                    File newFile = new File(filename);
                    FileOutputStream os = new FileOutputStream(newFile);
                    os.write(file.getBytes());
                    os.close();
                    file.transferTo(newFile);
                    // 上传到OSS
                    FileEntity fileEntity =  aliyunOSSUtil.uploadFile(newFile,"user");
                    if(fileEntity == null ){
                        return false;
                    }
                    //删除本地图片
                    File directory = new File("");//参数为空
                    String courseFile = directory.getCanonicalPath() ;
                    String path = courseFile + "\\" + filename;
                    FileUtil.deleteFile(path);
                    //返回地址
                    String webUrl = fileEntity.getWebUrl();
                    resumeInfo.setAvatar(webUrl);
                    resumeInfoRepository.save(resumeInfo);
                    return true;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }


}
