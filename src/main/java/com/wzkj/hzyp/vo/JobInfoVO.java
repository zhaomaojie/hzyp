package com.wzkj.hzyp.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wzkj.hzyp.entity.JobInfo;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @user zhaoMaoJie
 * @date {DATE}
 */
public class JobInfoVO extends JobInfo {

    private String address;

    private String logo;

    private String storeName;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date recriptTime;

    private Integer collectionJobStatus;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Date getRecriptTime() {
        return recriptTime;
    }

    public void setRecriptTime(Date recriptTime) {
        this.recriptTime = recriptTime;
    }

    public Integer getCollectionJobStatus() {
        return collectionJobStatus;
    }

    public void setCollectionJobStatus(Integer collectionJobStatus) {
        this.collectionJobStatus = collectionJobStatus;
    }
}
