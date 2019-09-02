package com.wzkj.hzyp.entity;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "B_PROCESS_INFO")
public class ProcessInfo {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "ID")
    private String id;

    /** 接收简历id*/
    @Column(name = "RECEVIED_ID")
    private String receviedId;

    /** b端id */
    @Column(name = "b_user_id")
    private String bUserId;

    /** a端id */
    @Column(name = "a_user_id")
    private String aUserId;

    /** 当前流程状态 */
    @Column(name = "STATUS")
    private Integer status;

    /** 是否反馈 */
    @Column(name = "IS_FEEDBACK")
    private Integer isFeedback;

    /** 反馈id */
    @Column(name = "FEEDBACK_ID")
    private String feedbackId;

    /** 最晚处理时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "LAST_HANDLE_TIME")
    private Date lastHandleTime;

    /** 有效标识 */
    @Column(name = "DEL_FLAG")
    private Integer delFlag;

    /** 流程内容 */
    @Column(name = "PROCESS_CONTENT")
    private String processContent;

    /** 所有者 */
    @Column(name = "OWNER")
    private Integer owner;

    /** 是否面试 */
    @Column(name = "IS_INTERVIEW")
    private Integer isInterview;

    /** 是否入职 */
    @Column(name = "IS_ENTRY")
    private Integer isEntry;

    /** 面试时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "INTERVIEW_TIME")
    private Date interviewTime;

    /** 入职时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "ENTRY_TIME")
    private Date entryTime;

    /** 是否离职 */
    @Column(name = "IS_QUIT")
    private Integer isQuit;

    /** 离职时间*/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "QUIT_TIME")
    private Date quitTime;

    /** 是否结束 */
    @Column(name = "IS_END")
    private Integer isEnd;

    /** 排序号 */
    @Column(name = "SORT_NUMBER")
    private Integer sortNumber;

    /** A端按钮 */
    private String buttonA;

    /** B端按钮 */
    private String buttonB;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getReceviedId() {
        return receviedId;
    }

    public void setReceviedId(String receviedId) {
        this.receviedId = receviedId == null ? null : receviedId.trim();
    }

    public String getbUserId() {
        return bUserId;
    }

    public void setbUserId(String bUserId) {
        this.bUserId = bUserId == null ? null : bUserId.trim();
    }

    public String getaUserId() {
        return aUserId;
    }

    public void setaUserId(String aUserId) {
        this.aUserId = aUserId == null ? null : aUserId.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsFeedback() {
        return isFeedback;
    }

    public void setIsFeedback(Integer isFeedback) {
        this.isFeedback = isFeedback;
    }

    public String getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(String feedbackId) {
        this.feedbackId = feedbackId;
    }

    public Date getLastHandleTime() {
        return lastHandleTime;
    }

    public void setLastHandleTime(Date lastHandleTime) {
        this.lastHandleTime = lastHandleTime;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getProcessContent() {
        return processContent;
    }

    public void setProcessContent(String processContent) {
        this.processContent = processContent == null ? null : processContent.trim();
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public Integer getIsInterview() {
        return isInterview;
    }

    public void setIsInterview(Integer isInterview) {
        this.isInterview = isInterview;
    }

    public Integer getIsEntry() {
        return isEntry;
    }

    public void setIsEntry(Integer isEntry) {
        this.isEntry = isEntry;
    }

    public Date getInterviewTime() {
        return interviewTime;
    }

    public void setInterviewTime(Date interviewTime) {
        this.interviewTime = interviewTime;
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    public Integer getIsQuit() {
        return isQuit;
    }

    public void setIsQuit(Integer isQuit) {
        this.isQuit = isQuit;
    }

    public Date getQuitTime() {
        return quitTime;
    }

    public void setQuitTime(Date quitTime) {
        this.quitTime = quitTime;
    }

    public Integer getIsEnd() {
        return isEnd;
    }

    public void setIsEnd(Integer isEnd) {
        this.isEnd = isEnd;
    }

    public Integer getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(Integer sortNumber) {
        this.sortNumber = sortNumber;
    }

    public String getButtonA() {
        return buttonA;
    }

    public void setButtonA(String buttonA) {
        this.buttonA = buttonA;
    }

    public String getButtonB() {
        return buttonB;
    }

    public void setButtonB(String buttonB) {
        this.buttonB = buttonB;
    }
}