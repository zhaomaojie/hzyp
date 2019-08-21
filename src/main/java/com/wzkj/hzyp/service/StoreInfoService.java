package com.wzkj.hzyp.service;

import com.wzkj.hzyp.entity.StoreInfo;

/**
 * @user zhaoMaoJie
 * @date {DATE}
 */
public interface StoreInfoService {

    /* *
     *根据id获取
     * @author zhaoMaoJie
     * @date 2019/8/5 0005
     */
    StoreInfo getStoreInfoById(String id);

    /* *
     * 保存商户对象
     * @author zhaoMaoJie
     * @date 2019/8/8 0008
     */
    void saveStoreInfo(StoreInfo storeInfo);

    /* *
     * 删除商户信息
     * @author zhaoMaoJie
     * @date 2019/8/9 0009
     */
    void deleteStoreById(String id);

    /* *
     * 通过用户ID获取商户信息
     * @author zhaoMaoJie
     * @date 2019/8/9 0009
     */
    StoreInfo getStoreByUserId(String userId);
}
