package com.wzkj.hzyp.entity;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "A_USER_INFO")
public class AuserInfo implements java.io.Serializable{

    /**  */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "ID")
    private String id;

    /** 年龄 */
    @Column(name = "AGE")
    private Integer age;

    /** 创建时间 */
    @Column(name = "CREATE_TIME")
    private Date createTime;

    /** 有效标识 0有效 1无效 */
    @Column(name = "DEL_FLAG")
    private Integer delFlag;

    /** 授权号 用户唯一标识 */
    @Column(name = "EMPOWER_PHONE")
    private String empowerPhone;

    /**  最后一次登录时间*/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "LAST_LOGIN_TIME")
    private Date lastLoginTime;

    /**  姓名*/
    @Column(name = "NAME")
    private String name;

    /**  更新时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    /**  验证码 */
    @Column(name = "VERIFY_CODE")
    private String verifyCode;

    /**  发送验证码的时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "VERDIFY_CODE_TIME")
    private Date verdifyCodeTime;

    /** 用户唯一标识 */
    @Column(name = "OPEN_ID")
    private String openId;

    /** 性别 */
    @Column(name = "GENDER")
    private Integer gender;

    /** 性别 */
    private String avatar;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getEmpowerPhone() {
        return empowerPhone;
    }

    public void setEmpowerPhone(String empowerPhone) {
        this.empowerPhone = empowerPhone == null ? null : empowerPhone.trim();
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode == null ? null : verifyCode.trim();
    }

    public Date getVerdifyCodeTime() {
        return verdifyCodeTime;
    }

    public void setVerdifyCodeTime(Date verdifyCodeTime) {
        this.verdifyCodeTime = verdifyCodeTime;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}