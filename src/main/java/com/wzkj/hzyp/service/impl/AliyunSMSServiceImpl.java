package com.wzkj.hzyp.service.impl;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.wzkj.hzyp.service.AliyunSMSService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @user zhaoMaoJie
 * @date {DATE}
 */
@Service
public class AliyunSMSServiceImpl implements AliyunSMSService{

    @Value("${project.aliyun_sms.app_id}")
    private String accessKeyId;

    @Value("${project.aliyun_sms.app_secret}")
    private String secret;

    @Value("${project.aliyun_sms.region}")
    private String region;

    @Value("${project.aliyun_sms.val_code_template}")
    private String templateCode;

    @Value("${project.aliyun_sms.sign}")
    private String sign;

    @Override
    public boolean sendVerdifyCode(String phone, String code) {
        DefaultProfile profile = DefaultProfile.getProfile(region, accessKeyId, secret);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", region);
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", sign);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam","{\"code\":\""+code+"\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return true;
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return true;
    }

}
