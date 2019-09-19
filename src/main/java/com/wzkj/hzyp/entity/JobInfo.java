package com.wzkj.hzyp.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "B_JOB_INFO")
public class JobInfo implements java.io.Serializable{

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "ID")
    private String id;

    /** 商户id*/
    @Column(name = "b_store_id")
    private String bStoreId;

    /**  发布人id */
    @Column(name = "B_USER_ID")
    private String bUserId;

    /**  行业代码 与行业表code字段对应*/
    @Column(name = "INDUSTRY_CODE")
    private String industryCode;

    /**  岗位名称 */
    @Column(name = "JOB_NAME")
    private String jobName;

    /**  岗位描述 */
    @Column(name = "DESCRIPTION")
    private String description;

    /**  招聘人数 */
    @Column(name = "NUM")
    private Integer num;

    /**  不限，高中，大专，本科及以上*/
    @Column(name = "EDUCATION_DEGREE")
    private Integer educationDegree;

    /**  不限，一年以上，三年以上，五年以上；*/
    @Column(name = "WORK_EXPERIENCE")
    private Integer workExperience;

    /**  年龄段 */
    @Column(name = "AGE_MIN")
    private Integer ageMin;

    /**  年龄段 */
    @Column(name = "AGE_MAX")
    private Integer ageMax;

    /** 试用期（0：没有试用期；1：有试用期） */
    @Column(name = "PROBATION")
    private Integer probation;

    /** 可面试时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "TRIAL_TIME")
    private String trialTime;

    /**  试用底薪*/
    @Column(name = "TRIAL_SALARY")
    private String trialSalary;

    /**  正式薪资*/
    @Column(name = "OFFICIAL_SALARY")
    private String officialSalary;

    /** 平均薪资*/
    @Column(name = "AVG_SALARY")
    private String avgSalary;

    /** 可面试时间 */
    @Column(name = "INTERVIEW_TIME")
    private String interviewTime;

    /**  岗位赏金*/
    @Column(name = "REWARD")
    private Integer reward;

    /**  过保时间*/
    @Column(name = "OVERTIME")
    private Integer overtime;

    /** 履约金*/
    @Column(name = "TOTAL_AMOUNT")
    private Integer totalAmount;

    /**  岗位状态，0审核中；1已发布。2已下架 */
    @Column(name = "STATUS")
    private Integer status;

    /**  有效标志 0有效 1无效*/
    @Column(name = "DEL_FLAG")
    private Integer delFlag;

    /** 收到的简历数 */
    @Column(name = "RECEVIED_RESUME_NUMBER")
    private Integer receviedResumeNumber;

    /** 能够已收到的总简历数*/
    @Column(name = "TOTAL_RESUME_NUMBER")
    private Integer totalResumeNumber;

    /** 创建时间 */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "CREATE_TIME")
    private Date createTime;

    /**  更新时间*/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    /** 标签 */
    @Column(name = "LABEL")
    private Integer label;

    /** 是否支付履约金 0未支付 1已支付 */
    @Column(name = "IS_PAY")
    private Integer isPay;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getbStoreId() {
        return bStoreId;
    }

    public void setbStoreId(String bStoreId) {
        this.bStoreId = bStoreId == null ? null : bStoreId.trim();
    }

    public String getbUserId() {
        return bUserId;
    }

    public void setbUserId(String bUserId) {
        this.bUserId = bUserId == null ? null : bUserId.trim();
    }

    public String getIndustryCode() {
        return industryCode;
    }

    public void setIndustryCode(String industryCode) {
        this.industryCode = industryCode == null ? null : industryCode.trim();
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName == null ? null : jobName.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getEducationDegree() {
        return educationDegree;
    }

    public void setEducationDegree(Integer educationDegree) {
        this.educationDegree = educationDegree == null ? null : educationDegree;
    }

    public Integer getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(Integer workExperience) {
        this.workExperience = workExperience == null ? null : workExperience;
    }


    public Integer getProbation() {
        return probation;
    }

    public void setProbation(Integer probation) {
        this.probation = probation;
    }

    public String getTrialTime() {
        return trialTime;
    }

    public void setTrialTime(String trialTime) {
        this.trialTime = trialTime == null ? null : trialTime.trim();
    }

    public String getTrialSalary() {
        return trialSalary;
    }

    public void setTrialSalary(String trialSalary) {
        this.trialSalary = trialSalary == null ? null : trialSalary.trim();
    }

    public String getOfficialSalary() {
        return officialSalary;
    }

    public void setOfficialSalary(String officialSalary) {
        this.officialSalary = officialSalary == null ? null : officialSalary.trim();
    }

    public String getAvgSalary() {
        return avgSalary;
    }

    public void setAvgSalary(String avgSalary) {
        this.avgSalary = avgSalary == null ? null : avgSalary.trim();
    }

    public String getInterviewTime() {
        return interviewTime;
    }

    public void setInterviewTime(String interviewTime) {
        this.interviewTime = interviewTime == null ? null : interviewTime.trim();
    }

    public Integer getReward() {
        return reward;
    }

    public void setReward(Integer reward) {
        this.reward = reward == null ? null : reward;
    }

    public Integer getOvertime() {
        return overtime;
    }

    public void setOvertime(Integer overtime) {
        this.overtime = overtime == null ? null : overtime;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount == null ? null : totalAmount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getReceviedResumeNumber() {
        return receviedResumeNumber;
    }

    public void setReceviedResumeNumber(Integer receviedResumeNumber) {
        this.receviedResumeNumber = receviedResumeNumber;
    }

    public Integer getTotalResumeNumber() {
        return totalResumeNumber;
    }

    public void setTotalResumeNumber(Integer totalResumeNumber) {
        this.totalResumeNumber = totalResumeNumber;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getLabel() {
        return label;
    }

    public void setLabel(Integer label) {
        this.label = label;
    }

    public Integer getAgeMin() {
        return ageMin;
    }

    public void setAgeMin(Integer ageMin) {
        this.ageMin = ageMin;
    }

    public Integer getAgeMax() {
        return ageMax;
    }

    public void setAgeMax(Integer ageMax) {
        this.ageMax = ageMax;
    }

    public Integer getIsPay() {
        return isPay;
    }

    public void setIsPay(Integer isPay) {
        this.isPay = isPay;
    }
}