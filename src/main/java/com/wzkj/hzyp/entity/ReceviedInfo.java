package com.wzkj.hzyp.entity;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "B_RECEVIED_INFO")
public class ReceviedInfo {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "ID")
    private String id;

    /**  简历id*/
    @Column(name = "RESUME_ID")
    private String resumeId;

    /** 岗位id */
    @Column(name = "JOB_ID")
    private String jobId;

    /** 创建时间*/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "CREATE_TIME")
    private Date createTime;

    /** 有效标志 0有效 1无效*/
    @Column(name = "DEL_FLAG")
    private Integer delFlag;

    /** a端用户标识*/
    @Column(name = "A_USER_ID")
    private String aUserId;

    /** b端用户id */
    @Column(name = "B_USER_ID")
    private String bUserId;

    /** 当前状态 */
    @Column(name = "LATEST_FEEDBACK")
    private String latestFeedback;

    /** 交付状态 0交付中 1已完成 2未完成 */
    @Column(name = "status")
    private Integer status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getResumeId() {
        return resumeId;
    }

    public void setResumeId(String resumeId) {
        this.resumeId = resumeId == null ? null : resumeId.trim();
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getaUserId() {
        return aUserId;
    }

    public void setaUserId(String aUserId) {
        this.aUserId = aUserId == null ? null : aUserId.trim();
    }

    public String getbUserId() {
        return bUserId;
    }

    public void setbUserId(String bUserId) {
        this.bUserId = bUserId == null ? null : bUserId.trim();
    }

    public String getLatestFeedback() {
        return latestFeedback;
    }

    public void setLatestFeedback(String latestFeedback) {
        this.latestFeedback = latestFeedback;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}