package com.wzkj.hzyp.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @user zhaoMaoJie
 * @date {DATE}
 */
public class ResumeRecordVO {

    private String jobName;

    private String statusContent;

    private String storeName;

    @JsonFormat(pattern="yyyy-MM-dd ",timezone = "GMT+8")
    private Date time;

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getStatusContent() {
        return statusContent;
    }

    public void setStatusContent(String statusContent) {
        this.statusContent = statusContent;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
