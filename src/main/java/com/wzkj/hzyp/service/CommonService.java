package com.wzkj.hzyp.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @user zhaoMaoJie
 * @date {DATE}
 */
public interface CommonService {

    /* *
     * 根据查询的list获取分页相关信息
     * @author zhaoMaoJie
     * @date 2019/9/18 0018
     */
    Map getMapByList(PageInfo pageInfo, List list);

    /* *
     * 根据类型 流程id 以及条件参数获取按钮
     * @author zhaoMaoJie
     * @date 2019/9/18 0018
     */
    List<JSONObject> getJsonList(String type,String processStep,boolean isUpdateTime);

    /* *
     * String转json
     * @author zhaoMaoJie
     * @date 2019/9/18 0018
     */
    JSONObject getJsonObjectByString(String value1,String value2);

    /* *
     * A端通用按钮
     * @author zhaoMaoJie
     * @date 2019/9/18 0018
     */
    List<JSONObject> commonFeedbackForA();

    /* *
     * 上传图片并获取图片的url地址
     * type类型包括 user store
     * @author zhaoMaoJie
     * @date 2019/9/18 0018
     */
    String getImgWebUrl(MultipartFile file,String type);
}
