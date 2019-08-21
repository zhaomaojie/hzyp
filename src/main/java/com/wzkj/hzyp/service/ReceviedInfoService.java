package com.wzkj.hzyp.service;

import com.wzkj.hzyp.entity.ReceviedInfo;

/**
 * @user zhaoMaoJie
 * @date {DATE}
 */
public interface ReceviedInfoService {


    /* *
     * 通过id获取接收
     * @author zhaoMaoJie
     * @date 2019/8/5 0005
     */
    ReceviedInfo getReceviedInfoById(String id);

    /* *
     * 通过id删除ReceviedInfo对象
     * @author zhaoMaoJie
     * @date 2019/8/5 0005
     */
    void deleteReceviedInfoById(String id);

    /* *
     * 保存接收简历
     * @author zhaoMaoJie
     * @date 2019/8/11 0011
     */
    void saveReceviedInfo(ReceviedInfo receviedInfo);
}
