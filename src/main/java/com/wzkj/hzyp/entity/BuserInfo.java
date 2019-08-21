package com.wzkj.hzyp.entity;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "B_USER_INFO")
public class BuserInfo implements java.io.Serializable{

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "ID")
    private String id;

    /** 姓名 */
    @Column(name = "NAME")
    private String name;

    /**  年龄 */
    @Column(name = "AGE")
    private Integer age;

    /**  授权号 用户唯一标识*/
    @Column(name = "EMPOWER_PHONE")
    private String empowerPhone;

    /**  验证码 */
    @Column(name = "VERIFY_CODE")
    private String verifyCode;

    /**  创建时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "CREATE_TIME")
    private Date createTime;

    /** 更新时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    /** 最后一次登录时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "last_login_time")
    private Date lastLoginTime;

    /**  有效标志 0，有效 1无效*/
    @Column(name = "del_flag")
    private Integer delFlag;

    /**  发送验证码时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "VERDIFY_CODE_TIME")
    private Date verdifyCodeTime;

    /** 用户唯一标识 */
    @Column(name = "OPEN_ID")
    private String openId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmpowerPhone() {
        return empowerPhone;
    }

    public void setEmpowerPhone(String empowerPhone) {
        this.empowerPhone = empowerPhone == null ? null : empowerPhone.trim();
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode == null ? null : verifyCode.trim();
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

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
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
}