package com.wzkj.hzyp.dao.mybatis;

import com.wzkj.hzyp.entity.ResumeInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ResumeInfoMapper {

    /* *
     * 获取我的简历列表
     * @author zhaoMaoJie
     * @date 2019/8/5 0005
     */
    List<ResumeInfo> getResumeList(@Param("id") String id,@Param("name") String name,@Param("phone") String phone);

    /* *
     * 根据用户和岗位id获取推荐的简历列表
     * @author zhaoMaoJie
     * @date 2019/8/7 0007
     */
    List<Map<String,Object>> pushResumeList(@Param("aUserId") String aUserId,@Param("jobId") String jobId,@Param("status") Integer status,@Param("name") String name,@Param("phone") String phone);

    /* *
     * 查看用户创建的简历列表
     * 用户id 简历状态 姓名 电话
     * @author zhaoMaoJie
     * @date 2019/8/7 0007
     */
    List<ResumeInfo> resumeList(@Param("aUserId") String aUserId,@Param("status") Integer status,@Param("name") String name,@Param("phone") String phone);

    /* *
     * B端 获取我的候选人
     * @author zhaoMaoJie
     * @date 2019/8/11 0011
     */
    List<Map<String, Object>> myCandidate(@Param("bUserId") String bUserId,@Param("status") Integer status,@Param("name")String name,@Param("phone") String phone);

    /* *
     * 获取简历推荐记录
     * @author zhaoMaoJie
     * @date 2019/8/8 0008
     */
    List<Map<String,Object>> resumeRecord(@Param("resumeId") String resumeId);


    int deleteByPrimaryKey(String id);

    int insert(ResumeInfo record);

    int insertSelective(ResumeInfo record);

    ResumeInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ResumeInfo record);

    int updateByPrimaryKey(ResumeInfo record);
}