package com.wzkj.hzyp.service;

import com.wzkj.hzyp.entity.EntryInfo;

/**
 * @user zhaoMaoJie
 * @date {DATE}
 */
public interface EntryInfoService {

    /* *
     * 根据id获取 入职表
     * @author zhaoMaoJie
     * @date 2019/8/11 0011
     */
    EntryInfo getEntryInfoById(String id);

    /* *
     * 保存 入职信息
     * @author zhaoMaoJie
     * @date 2019/8/11 0011
     */
    void saveEntryInfo(EntryInfo entryInfo);
}
