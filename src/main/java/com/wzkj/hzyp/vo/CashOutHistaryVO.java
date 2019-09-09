package com.wzkj.hzyp.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @user zhaoMaoJie
 * @date {DATE}
 */
public class CashOutHistaryVO {

    private String jobName;

    private String name;

    private Integer reward;

    @JsonFormat(pattern="yyyy-MM-dd ",timezone = "GMT+8")
    private Date cashoutTime;

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

    public Date getCashoutTime() {
        return cashoutTime;
    }

    public void setCashoutTime(Date cashoutTime) {
        this.cashoutTime = cashoutTime;
    }
}
