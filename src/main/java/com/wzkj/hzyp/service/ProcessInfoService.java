package com.wzkj.hzyp.service;

import com.wzkj.hzyp.entity.ProcessInfo;

import java.util.List;
import java.util.Map;

/**
 * @user zhaoMaoJie
 * @date {DATE}
 */
public interface ProcessInfoService {

    /* *
     * 保存流程信息
     * @author zhaoMaoJie
     * @date 2019/8/5 0005
     */
    void saveProcessInfo(ProcessInfo processInfo);

    /* *
     * 通过id获取流程表对象
     * @author zhaoMaoJie
     * @date 2019/8/5 0005
     */
    ProcessInfo getProcessInfoById(String id);

    /* *
     * 通过id删除流程表
     * @author zhaoMaoJie
     * @date 2019/8/5 0005
     */
    void deleteProcessById(String id);

    /* *
     * 通过recevied查询流程信息
     * @author zhaoMaoJie
     * @date 2019/8/7 0007
     */
    List<ProcessInfo> getProcessInfoByReceviedId(String receviedId);

    /* *
     * 根据receviedI的获取
     * @author zhaoMaoJie
     * @date 2019/8/26 0026
     */
    Integer getNewSortNumber(String receviedId);



}
