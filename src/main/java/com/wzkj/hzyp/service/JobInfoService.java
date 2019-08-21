package com.wzkj.hzyp.service;

import com.wzkj.hzyp.entity.CollectionJob;
import com.wzkj.hzyp.entity.JobInfo;
import com.wzkj.hzyp.entity.JpCityInfo;
import com.wzkj.hzyp.entity.JpIndustryInfo;

import java.util.List;
import java.util.Map;

/**
 * @user zhaoMaoJie
 * @date {DATE}
 */
public interface JobInfoService {

    /* *
     * 获取岗位列表
     * @author zhaoMaoJie
     * @date 2019/7/22 0022
     */
    List<Map<String,Object>> getJobList(String keyWord, Integer label);

    /* *
     * 根据id获取岗位详情
     * @author zhaoMaoJie
     * @date 2019/8/4 0004
     */
    @org.apache.ibatis.annotations.MapKey("id")
    Map<String,Object> jobDetail(String jobId);

    /* *
     * 收藏岗位
     * @author zhaoMaoJie
     * @date 2019/8/4 0004
     */
    void collectionJob(CollectionJob collectionJob);

    /* *
     * 判断该岗位是否被收藏
     * @author zhaoMaoJie
     * @date 2019/8/4 0004
     */
    boolean isCollection(String id,String jobId);

    /* *
     * 通过id获取岗位信息
     * @author zhaoMaoJie
     * @date 2019/8/4 0004
     */
    JobInfo getJobInfoById(String id);

    /* *
     * 保存岗位信息
     * @author zhaoMaoJie
     * @date 2019/8/5 0005
     */
    void saveJobInfo(JobInfo jobInfo);

    /* *
     * 改变收藏岗位的状态
     * @author zhaoMaoJie
     * @date 2019/8/5 0005
     */
    void changeCollectionStatus(String aUserId,String jobId,Integer status);

    /* *
     * 查询我收藏的岗位
     * @author zhaoMaoJie
     * @date 2019/8/6 0006
     */
    List<Map<String,Object>> collectionJobList(String aUserId,Integer status);

    /* *
     * 获取B端发布的岗位
     * @author zhaoMaoJie
     * @date 2019/8/9 0009
     */
    List<Map<String,Object>> publishJobList(String bUserId,String jobName,Integer status);

    /* *
     * 更改岗位状态
     * @author zhaoMaoJie
     * @date 2019/8/10 0010
     */
    void updateStatus(String jobId,Integer status);

    /* *
     * 通过parentCode获取地址信息
     * @author zhaoMaoJie
     * @date 2019/8/12 0012
     */
    List<JpCityInfo> getAreaByParentCode(String parentCode);

    /* *
     * 获取行业信息
     * @author zhaoMaoJie
     * @date 2019/8/12 0012
     */
    List<JpIndustryInfo> getIndustryInfo();

}
