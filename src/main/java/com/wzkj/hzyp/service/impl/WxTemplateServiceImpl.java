package com.wzkj.hzyp.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateData;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateMessage;
import cn.binarywang.wx.miniapp.config.WxMaInMemoryConfig;
import com.alibaba.fastjson.JSONObject;
import com.wzkj.hzyp.service.WxTemplateService;
import me.chanjar.weixin.common.error.WxErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @user zhaoMaoJie
 * @date {DATE}
 */
@Service
public class WxTemplateServiceImpl implements WxTemplateService {

    private static final Logger log = LoggerFactory.getLogger(WxTemplateServiceImpl.class);

    @Value("${project.weixin.mp_app_id}")
    private String APPID;

    @Value("${project.weixin.mp_app_secret}")
    private String APPSECRET;

    @Value("${project.weixin.pushResumeTemplate}")
    private String pushResumeTemplate;

    @Value("${project.weixin.pushResumeJumpPages}")
    private String pushResumeJumpPages;

    @Override
    public String getAccessToken() {
        String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + APPID
                + "&secret=" + APPSECRET;
        System.out.println("URL for getting accessToken accessTokenUrl=" + accessTokenUrl);

        URL url = null;
        try {
            url = new URL(accessTokenUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.connect();

            // 获取返回的字符
            InputStream inputStream = connection.getInputStream();
            int size = inputStream.available();
            byte[] bs = new byte[size];
            inputStream.read(bs);
            String message = new String(bs, "UTF-8");

            // 获取access_token
            JSONObject jsonObject = JSONObject.parseObject(message);
            String accessToken = jsonObject.getString("access_token");
            Map<String, String> TOKEN_MAP = new ConcurrentHashMap<String, String>();
            TOKEN_MAP.put("accessToken", accessToken);
            return accessToken;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public WxMaInMemoryConfig getWxConfig() {
        //1,配置小程序信息
        WxMaInMemoryConfig wxConfig = new WxMaInMemoryConfig();
        //小程序appid
        wxConfig.setAppid(APPID);
        //小程序AppSecret
        wxConfig.setSecret(APPSECRET);
        return wxConfig;
    }

    @Override
    public boolean pushResumeTemplate(String openId, String formId, List<WxMaTemplateData> templateDataList,
                            WxMaService wxMaService) {
        WxMaTemplateMessage templateMessage = WxMaTemplateMessage.builder()
                .toUser(openId)//要推送的用户openid
                .formId(formId)//收集到的formid
                .templateId(pushResumeTemplate)//推送的模版id（在小程序后台设置）
                .data(templateDataList)//模版信息
                .page(pushResumeJumpPages)//要跳转到小程序那个页面
                .build();

        //4，发起推送
        try {
            wxMaService.getMsgService().sendTemplateMsg(templateMessage);
        } catch (WxErrorException e) {
            System.out.println("推送失败：" + e.getMessage());
            log.error("推送失败" + e.getMessage());
            return false;
        }
        return true;
    }
}
