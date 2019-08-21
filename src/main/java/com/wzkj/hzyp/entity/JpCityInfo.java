package com.wzkj.hzyp.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="jp_city_info")
public class JpCityInfo  implements java.io.Serializable{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name = "code")
    private String code;
    @Column(name = "parent_code")
    private String parentCode;
    @Column(name = "sort_num")
    private Integer sortNum;
    @Column(name = "del_flag")
    private Integer delFlag;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "create_user_code")
    private Long createUserCode;
    @Column(name = "update_time")
    private Date updateTime;
    @Column(name = "update_user_code")
    private Long updateUserCode;
    @Column(name = "full_name")
    private String fullName;
    @Transient
    private List<JpCityInfo> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateUserCode() {
        return createUserCode;
    }

    public void setCreateUserCode(Long createUserCode) {
        this.createUserCode = createUserCode;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateUserCode() {
        return updateUserCode;
    }

    public void setUpdateUserCode(Long updateUserCode) {
        this.updateUserCode = updateUserCode;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<JpCityInfo> getChildren() {
        return children;
    }

    public void setChildren(List<JpCityInfo> children) {
        this.children = children;
    }
}