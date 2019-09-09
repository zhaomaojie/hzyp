package com.wzkj.hzyp.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @user zhaoMaoJie
 * @date {DATE}
 */
public class CashOutListVO {

    private String jobName;

    private String name;

    private Integer reward;

    @JsonFormat(pattern="yyyy-MM-dd ",timezone = "GMT+8")
    private Date entryTime;

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getReward() {
        return reward;
    }

    public void setReward(Integer reward) {
        this.reward = reward;
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }
}
