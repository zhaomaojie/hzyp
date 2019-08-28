package com.wzkj.hzyp.vo;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * @user zhaoMaoJie
 * @date {DATE}
 */
public class AUserVO {

    /** 版本号 */
    private static final long serialVersionUID = -8473976893875841572L;

    /**  */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    private String token;

    /** 姓名 */
    private String name;

    /** 年龄 */
    private Integer age;

    /** 授权号 */
    private String empowerPhone;

    /** 验证码 */
    private String verifyCode;

    /** 创建时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

    /** 最后一次登录时间 */
    private Date lastLoginTime;

    /** 有效标志0未删 1已删 */
    private Integer delFlag;

    /** 是否登录  0是  1否*/
    private Integer isLogin;

    /** 是否注册  0是  1否*/
    private Integer isRegister;

    /** 用户类型 A */
    private Integer type;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 获取
     *
     * @return
     */
    public String getId() {
        return this.id;
    }

    /**
     * 设置
     *
     * @param id
     *
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取姓名
     *
     * @return 姓名
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置姓名
     *
     * @param name
     *          姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取年龄
     *
     * @return 年龄
     */
    public Integer getAge() {
        return this.age;
    }

    /**
     * 设置年龄
     *
     * @param age
     *          年龄
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * 获取授权号
     *
     * @return 授权号
     */
    public String getEmpowerPhone() {
        return this.empowerPhone;
    }

    /**
     * 设置授权号
     *
     * @param empowerPhone
     *          授权号
     */
    public void setEmpowerPhone(String empowerPhone) {
        this.empowerPhone = empowerPhone;
    }

    /**
     * 获取验证码
     *
     * @return 验证码
     */
    public String getVerifyCode() {
        return this.verifyCode;
    }

    /**
     * 设置验证码
     *
     * @param verifyCode
     *          验证码
     */
    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    /**
     * 获取创建时间
     *
     * @return 创建时间
     */
    public Date getCreateTime() {
        return this.createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime
     *          创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     *
     * @return 更新时间
     */
    public Date getUpdateTime() {
        return this.updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime
     *          更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取最后一次登录时间
     *
     * @return 最后一次登录时间
     */
    public Date getLastLoginTime() {
        return this.lastLoginTime;
    }

    /**
     * 设置最后一次登录时间
     *
     * @param lastLoginTime
     *          最后一次登录时间
     */
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * 获取有效标志0未删 1已删
     *
     * @return 有效标志0未删 1已删
     */
    public Integer getDelFlag() {
        return this.delFlag;
    }

    /**
     * 设置有效标志0未删 1已删
     *
     * @param delFlag
     *          有效标志0未删 1已删
     */
    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(Integer isLogin) {
        this.isLogin = isLogin;
    }

    public Integer getIsRegister() {
        return isRegister;
    }

    public void setIsRegister(Integer isRegister) {
        this.isRegister = isRegister;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
