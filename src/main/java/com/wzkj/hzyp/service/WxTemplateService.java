package com.wzkj.hzyp.service;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateData;
import cn.binarywang.wx.miniapp.config.WxMaInMemoryConfig;

import java.util.List;

/**
 * @user zhaoMaoJie
 * @date {DATE}
 */
public interface WxTemplateService {

    /* *
     * 获取accessToken
     * @author zhaoMaoJie
     * @date 2019/8/19 0019
     */
    String getAccessToken();

    /* *
     * 获取微信相关配置
     * @author zhaoMaoJie
     * @date 2019/8/19 0019
     */
    WxMaInMemoryConfig getWxConfig();

    /* *
     * 要推送的用户id，formid tuisongshuju1
     * @author zhaoMaoJie
     * @date 2019/8/19 0019
     */
    boolean pushResumeTemplate(String openId,String formId,List<WxMaTemplateData> templateDataList,WxMaService wxMaService);
}
