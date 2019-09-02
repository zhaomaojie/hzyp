package com.wzkj.hzyp.service;

/**
 * @user zhaoMaoJie
 * @date {DATE}
 */
public interface AliyunSMSService {

    /* *
     * 为指定电话发送验证码
     * @author zhaoMaoJie
     * @date 2019/8/30 0030
     */
    boolean sendVerdifyCode(String phone,String code);
}
