package com.wzkj.hzyp.entity;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "C_RESUME_INFO")
public class ResumeInfo {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "ID")
    private String id;

    /** 头像地址*/
    @Column(name = "AVATAR")
    private String avatar;

    /** 姓名*/
    @Column(name = "NAME")
    private String name;

    /** 电话号码 */
    @Column(name = "phone")
    private String phone;

    /** 性别 0男 1女 */
    @Column(name = "GENDER")
    private Integer gender;

    /** 年龄 */
    @Column(name = "AGE")
    private Integer age;

    /** 创建时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "CREATE_TIME")
    private Date createTime;

    /** 更新时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    /** 有效标志 */
    @Column(name = "DEL_FLAG")
    private Integer delFlag;

    /** a端用户标识 */
    @Column(name = "A_USER_INFO")
    private String aUserId;

    /** 学历 */
    @Column(name = "EDUCATION_DEGREE")
    private Integer educationDegree;

    /** 工作经验 */
    @Column(name = "WORK_EXPERIENCE")
    private Integer workExperience;

    /** 毕业学校 */
    @Column(name = "UNIVERSITY")
    private String university;

    /** 籍贯 */
    @Column(name = "NATIVE_PLACE")
    private String nativePlace;

    /** 0未推荐 1已推荐 */
    @Column(name = "STATUS")
    private Integer status;

    /** 从事工作 */
    @Column(name = "PAST_WORK")
    private String pastWork;

    /** 意向工作 */
    @Column(name = "INTENTIONAL_WORK")
    private String intentionalWork;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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

    public Integer getEducationDegree() {
        return educationDegree;
    }

    public void setEducationDegree(Integer educationDegree) {
        this.educationDegree = educationDegree;
    }

    public Integer getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(Integer workExperience) {
        this.workExperience = workExperience;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university == null ? null : university.trim();
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace == null ? null : nativePlace.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPastWork() {
        return pastWork;
    }

    public void setPastWork(String pastWork) {
        this.pastWork = pastWork;
    }

    public String getIntentionalWork() {
        return intentionalWork;
    }

    public void setIntentionalWork(String intentionalWork) {
        this.intentionalWork = intentionalWork;
    }
}