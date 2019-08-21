package com.wzkj.hzyp.entity.wxTemplate;

import java.util.Map;

/**
 * @user zhaoMaoJie
 * @date {DATE}
 */
public class WxMssVo {

    private String touser;//用户openid

    private String templateId;//模版id

    private String page = "index";//默认跳到小程序首页

    private String formIdd;//收集到的用户formid

    private String emphasisKeyword = "keyword1.DATA";//放大那个推送字段

    private Map<String, TemplateData> data;//推送文字

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getFormIdd() {
        return formIdd;
    }

    public void setFormIdd(String formIdd) {
        this.formIdd = formIdd;
    }

    public String getEmphasisKeyword() {
        return emphasisKeyword;
    }

    public void setEmphasisKeyword(String emphasisKeyword) {
        this.emphasisKeyword = emphasisKeyword;
    }

    public Map<String, TemplateData> getData() {
        return data;
    }

    public void setData(Map<String, TemplateData> data) {
        this.data = data;
    }
}
