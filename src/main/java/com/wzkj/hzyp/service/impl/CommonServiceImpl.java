package com.wzkj.hzyp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.wzkj.hzyp.entity.FileEntity;
import com.wzkj.hzyp.service.CommonService;
import com.wzkj.hzyp.utils.AliyunOSSUtil;
import com.wzkj.hzyp.utils.FileUtil;
import com.wzkj.hzyp.utils.ProcessConfig;
import com.wzkj.hzyp.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @user zhaoMaoJie
 * @date {DATE}
 */
@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private ProcessConfig processConfig;

    // 阿里云配置信息
    @Autowired
    private AliyunOSSUtil aliyunOSSUtil;

    @Override
    public Map getMapByList(PageInfo pageInfo, List list) {
        Map map = new HashMap();
        Integer total = Math.toIntExact(pageInfo.getTotal());
        map.put("total",total);
        map.put("list",list);
        return map;
    }

    @Override
    public List<JSONObject> getJsonList(String type, String processStep, boolean isUpdateTime) {
        List<JSONObject> list = new ArrayList();
        //先判断步骤 再判断AB端 再判断是否更改时间
        //短信模板
        if(StringUtils.isNotBlank(processStep) && processStep.equalsIgnoreCase("sms")){
            //对A端功能的文本复制
            String text = processConfig.getSmsTemplateText();
            String method = processConfig.getSmsTemplateMethod();
            JSONObject json = getJsonObjectByString(text,method);
            list.add(json);
            return list;
        }else if(StringUtils.isNotBlank(processStep) && processStep.equalsIgnoreCase("interviewFeedback")){
            //A端反馈
            if(StringUtils.isNotBlank(type) && type.equalsIgnoreCase("A")){
                //取消简历
                String text = processConfig.getCancelResumeText();
                String method = processConfig.getCancelResumeMethod();
                JSONObject json = getJsonObjectByString(text,method);
                list.add(json);
                return list;
            }else {
               //B端 正常
                if(isUpdateTime){
                    String text = processConfig.getPassInterviewText();
                    String method = processConfig.getPassInterviewMethod();
                    JSONObject json = getJsonObjectByString(text,method);
                    String notArriveText = processConfig.getNotArrivedInterviedText();
                    String notArriveMethod = processConfig.getNotArrivedInterviedMethod();
                    JSONObject notArriveJson = getJsonObjectByString(notArriveText,notArriveMethod);
                    String failText = processConfig.getFailInterviedText();
                    String failMethod = processConfig.getFailInterviedMethod();
                    JSONObject failJson = getJsonObjectByString(failText,failMethod);
                    list.add(json);
                    list.add(notArriveJson);
                    list.add(failJson);
                    return list;
                }else {
                    String text = processConfig.getPassInterviewText();
                    String method = processConfig.getPassInterviewMethod();
                    JSONObject json = getJsonObjectByString(text,method);
                    String notArriveText = processConfig.getNotArrivedInterviedText();
                    String notArriveMethod = processConfig.getNotArrivedInterviedMethod();
                    JSONObject notArriveJson = getJsonObjectByString(notArriveText,notArriveMethod);
                    String failText = processConfig.getFailInterviedText();
                    String failMethod = processConfig.getFailInterviedMethod();
                    JSONObject failJson = getJsonObjectByString(failText,failMethod);
                    String updateTimeText = processConfig.getUpdateInterviewText();
                    String updateTimeMethod = processConfig.getUpdateInterviewMethod();
                    JSONObject updateTimeJson = getJsonObjectByString(updateTimeText,updateTimeMethod);
                    list.add(json);
                    list.add(notArriveJson);
                    list.add(failJson);
                    list.add(updateTimeJson);
                    return list;
                }
            }
            //入职反馈
        }else if(StringUtils.isNotBlank(processStep) && processStep.equalsIgnoreCase("entryFeedback")){
            if(StringUtils.isNotBlank(type) && type.equalsIgnoreCase("A")){
                //调用A端通用
                list = commonFeedbackForA();
                return list;
            }else{
                //B端
                //B端更改时间的情况
                if(isUpdateTime){
                    String text = processConfig.getEntrySuccessText();
                    String method = processConfig.getEntrySuccessMethod();
                    JSONObject json = getJsonObjectByString(text,method);
                    String failText = processConfig.getEntryFailText();
                    String failMethod = processConfig.getEntryFailMethod();
                    JSONObject failJson = getJsonObjectByString(failText,failMethod);
                    list.add(json);
                    list.add(failJson);
                    return list;
                }else {
                    //正常情况
                    String text = processConfig.getEntrySuccessText();
                    String method = processConfig.getEntrySuccessMethod();
                    JSONObject json = getJsonObjectByString(text,method);
                    String failText = processConfig.getEntryFailText();
                    String failMethod = processConfig.getEntryFailMethod();
                    JSONObject failJson = getJsonObjectByString(failText,failMethod);
                    String updateTimeText = processConfig.getUpdateEntryTimeText();
                    String updateTimeMethod = processConfig.getUpdateEntryTimeMethod();
                    JSONObject updateTimeJson = getJsonObjectByString(updateTimeText,updateTimeMethod);
                    list.add(json);
                    list.add(failJson);
                    list.add(updateTimeJson);
                    return list;
                }

            }
            //离职反馈
        }else if(StringUtils.isNotBlank(processStep) && processStep.equalsIgnoreCase("quitFeedback")){
            if(StringUtils.isNotBlank(type) && type.equalsIgnoreCase("A")){
                //A端通用调用
                list = commonFeedbackForA();
                return list;
            }else {
                String text = processConfig.getQuitFeedbackText();
                String method = processConfig.getQuitFeedbackMethod();
                JSONObject json = getJsonObjectByString(text,method);
                list.add(json);
                return list;
            }
        }else if(StringUtils.isNotBlank(processStep) && processStep.equalsIgnoreCase("appeal")){
            String text = processConfig.getAppealFeedbackDefaultText();
            String method = processConfig.getAppealFeedbackDefaultText();
            JSONObject json = getJsonObjectByString(text,method);
            String notRecognizedText = processConfig.getAppealFeedbackFeedbackText();
            String notRecognizedMethod = processConfig.getAppealFeedbackFeedbackMethod();
            JSONObject jsonObject = getJsonObjectByString(notRecognizedText,notRecognizedMethod);
            list.add(json);
            list.add(jsonObject);
            return list;
        }
        return null;
    }

    @Override
    public JSONObject getJsonObjectByString(String value1,String value2) {
        if(StringUtils.isNotBlank(value1) && StringUtils.isNotBlank(value2)){
            Map map = new HashMap();
            map.put("text",value1);
            map.put("method",value2);
            JSONObject json = new JSONObject(map);
            return json;
        }
        return null;
    }

    @Override
    public List<JSONObject> commonFeedbackForA() {
        List<JSONObject> list = new ArrayList();
        String text = processConfig.getIsKonwText();
        String method = processConfig.getIsKonwMethod();
        JSONObject json = getJsonObjectByString(text,method);
        String appealText = processConfig.getAppealText();
        String appealMethod = processConfig.getAppealMethod();
        JSONObject appealJson = getJsonObjectByString(appealText,appealMethod);
        list.add(json);
        list.add(appealJson);
        return list;
    }

    @Override
    public String getImgWebUrl(MultipartFile file, String type) {
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
                    FileEntity fileEntity =  aliyunOSSUtil.uploadFile(newFile,type);
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
}
