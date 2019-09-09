package com.wzkj.hzyp.service;

import com.wzkj.hzyp.entity.ReceviedInfo;
import com.wzkj.hzyp.entity.ResumeInfo;
import com.wzkj.hzyp.vo.ResumeRecordVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @user zhaoMaoJie
 * @date {DATE}
 */
public interface ResumeInfoService {

    /* *
     * 获取简历信息列表
     * @author zhaoMaoJie
     * @date 2019/8/5 0005
     */
    List<ResumeInfo> getResumeList(String userId,String name,String phone);

    /* *
     * 新增简历
     * @author zhaoMaoJie
     * @date 2019/8/5 0005
     */
    void saveResume(ResumeInfo resumeInfo);

    /* *
     * 通过id返回简历对象
     * @author zhaoMaoJie
     * @date 2019/8/5 0005
     */
    ResumeInfo getResumeById(String id);

    /* *
     * 通过id删除简历
     * @author zhaoMaoJie
     * @date 2019/8/5 0005
     */
    void deleteResumeById(String id);

    /* *
     * 根据岗位id和简历id判断该简历是否被推送
     * @author zhaoMaoJie
     * @date 2019/8/5 0005
     */
    boolean isPush(String jobId,String resumeId);

    /* *
     * A端推荐简历
     * 推送简历 岗位id 简历id
     * @author zhaoMaoJie
     * @date 2019/8/5 0005
     */
    void pushResume(ReceviedInfo receviedInfo);

    /* *
     * A端 根据用户和岗位id获取推荐的简历列表
     * @author zhaoMaoJie
     * @date 2019/8/7 0007
     */
    List<Map<String,Object>> pushResumeList(String aUserId,String jobId,Integer status,String keyWord);

    /* *
     * A端 查看用户创建的简历列表
     * 用户id 简历状态 姓名 电话
     * @author zhaoMaoJie
     * @date 2019/8/7 0007
     */
    List<ResumeInfo> resumeList(String userId,Integer status,String keyWord);

    /* *
     * A端 获取简历推荐记录
     * @author zhaoMaoJie
     * @date 2019/8/8 0008
     */
    List<ResumeRecordVO> resumeRecord(String resumeId);

    /* *
     * B端 获取我的候选人信息
     * @author zhaoMaoJie
     * @date 2019/8/11 0011
     */
    List<Map<String,Object>> myCandidate(String bUserId,Integer status,String keyWord);

    /* *
     * 绑定头像到简历页
     * @author zhaoMaoJie
     * @date 2019/8/26 0026
     */
    boolean bindingAvatar(String id,MultipartFile file);
}
