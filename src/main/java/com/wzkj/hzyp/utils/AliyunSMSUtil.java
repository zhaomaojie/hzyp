package com.wzkj.hzyp.utils;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

import java.util.Random;

/**
 * @user zhaoMaoJie
 * @date {DATE}
 */
public class AliyunSMSUtil {

    public static void sendVerdifyCode(String phone,String code){
//        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAIzmtNRsDxODPn\n", "g9V3dQjInqeqVoHxukWCofqFEDkX9q");
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAIuDKu5Jaj9C7P", "XAUK4143XZ6va8786WQSzU4MkWoW1F");

        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "街聘");
        request.putQueryParameter("TemplateCode", "SMS_155225079");
        request.putQueryParameter("TemplateParam","{\"code\":\""+code+"\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

//    public static void sendInterviewMessage(String phone,String name,String date,String time,String address{
//        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAIuDKu5Jaj9C7P", "XAUK4143XZ6va8786WQSzU4MkWoW1F");
//
//        IAcsClient client = new DefaultAcsClient(profile);
//
//        CommonRequest request = new CommonRequest();
//        request.setMethod(MethodType.POST);
//        request.setDomain("dysmsapi.aliyuncs.com");
//        request.setVersion("2017-05-25");
//        request.setAction("SendSms");
//        request.putQueryParameter("RegionId", "cn-hangzhou");
//        request.putQueryParameter("PhoneNumbers", phone);
//        request.putQueryParameter("SignName", "街聘");
//        request.putQueryParameter("TemplateCode", "SMS_155225079");
//        request.putQueryParameter("TemplateParam","{\"name\":\""+name+"\"}");
//        request.putQueryParameter("TemplateParam","{\"date\":\""+date+"\"}");
//        request.putQueryParameter("TemplateParam","{\"address\":\""+address+"\"}");
//        try {
//            CommonResponse response = client.getCommonResponse(request);
//            System.out.println(response.getData());
//        } catch (ServerException e) {
//            e.printStackTrace();
//        } catch (ClientException e) {
//            e.printStackTrace();
//        }
//    }

    public static String getVderdifyCode(int num){
        String code = "";
        Random random = new Random();
        for (int i = 0; i < num; i++) {

            int r = random.nextInt(10); //每次随机出一个数字（0-9）

            code = code + r;  //把每次随机出的数字拼在一起

        }
        return code;
    }


}
