package com.wzkj.hzyp.dao.mybatis;

import com.wzkj.hzyp.entity.JobInfo;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface JobInfoMapper {

    /* *
     * 获取岗位列表
     * keyWord 关键字 可以是岗位名称或者公司名称
     * label 岗位标签
     * @author zhaoMaoJie
     * @date 2019/8/2 0002
     */
    List<Map<String,Object>> getJobList(@Param("keyWord") String keyWord, @Param("label") Integer label);

    /* *
     * 根据id获取岗位详情
     * @author zhaoMaoJie
     * @date 2019/8/4 0004
     */
    @MapKey("id")
    Map<String,Object> jobDetail(@Param("id") String id);

    /* *
     * 获取我的收藏岗位
     * @author zhaoMaoJie
     * @date 2019/8/6 0006
     */
    List<Map<String,Object>> collectionJobList(@Param("aUserId") String aUserId,@Param("status") Integer status);

    /* *
     * B端用户id 岗位名称（模糊查询） 岗位状态
     * @author zhaoMaoJie
     * @date 2019/8/9 0009
     */
    public List<Map<String, Object>> publishJobList(@Param("bUserId") String bUserId,@Param("jobName") String jobName,@Param("status") Integer status);

    int deleteByPrimaryKey(String id);

    int insert(JobInfo record);

    int insertSelective(JobInfo record);

    JobInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(JobInfo record);

    int updateByPrimaryKey(JobInfo record);
}