package com.wzkj.hzyp.service;

import com.wzkj.hzyp.entity.StoreInfo;
import org.springframework.web.multipart.MultipartFile;

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

    /* *
     * 上传图片通用接口 返回图片的url地址
     * @author zhaoMaoJie
     * @date 2019/9/5 0005
     */
    String getUploadImageUrl(MultipartFile file);

    /* *
     * 绑定商户营业执照
     * @author zhaoMaoJie
     * @date 2019/9/5 0005
     */
    boolean bindStoreBusinessImg(String id,MultipartFile file);

    /* *
     * 绑定商户营业执照
     * @author zhaoMaoJie
     * @date 2019/9/5 0005
     */
    boolean bindStoreCompanyImg(String id,MultipartFile file);

    /* *
     * 绑定公司logo
     * @author zhaoMaoJie
     * @date 2019/9/5 0005
     */
    boolean bindStoreLogo(String id,MultipartFile file);
}
