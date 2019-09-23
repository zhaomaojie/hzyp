package com.wzkj.hzyp.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 读取流程配置文件
 * @user zhaoMaoJie
 * @date {DATE}
 */
@Configuration
public class ProcessConfig {

    @Value("${project.process.smsTemplate.text}")
    private String smsTemplateText;

    @Value("${project.process.smsTemplate.method}")
    private String smsTemplateMethod;

    @Value("${project.process.interviewFeedback.terminalA.text}")
    private String cancelResumeText;

    @Value("${project.process.interviewFeedback.terminalA.method}")
    private String cancelResumeMethod;

    @Value("${project.process.interviewFeedback.terminalB.default.text}")
    private String passInterviewText;

    @Value("${project.process.interviewFeedback.terminalB.default.method}")
    private String passInterviewMethod;

    @Value("${project.process.interviewFeedback.terminalB.notArrived.text}")
    private String notArrivedInterviedText;

    @Value("${project.process.interviewFeedback.terminalB.notArrived.method}")
    private String notArrivedInterviedMethod;

    @Value("${project.process.interviewFeedback.terminalB.fail.text}")
    private String failInterviedText;

    @Value("${project.process.interviewFeedback.terminalB.fail.method}")
    private String failInterviedMethod;

    @Value("${project.process.interviewFeedback.terminalB.updateTime.text}")
    private String updateInterviewText;

    @Value("${project.process.interviewFeedback.terminalB.updateTime.method}")
    private String updateInterviewMethod;

    @Value("${project.process.entryFeedback.terminalA.default.text}")
    private String isKonwText;

    @Value("${project.process.entryFeedback.terminalA.default.method}")
    private String isKonwMethod;

    @Value("${project.process.entryFeedback.terminalA.appeal.text}")
    private String appealText;

    @Value("${project.process.entryFeedback.terminalA.appeal.method}")
    private String appealMethod;

    @Value("${project.process.entryFeedback.terminalB.success.text}")
    private String entrySuccessText;

    @Value("${project.process.entryFeedback.terminalB.success.method}")
    private String entrySuccessMethod;

    @Value("${project.process.entryFeedback.terminalB.fail.text}")
    private String entryFailText;

    @Value("${project.process.entryFeedback.terminalB.fail.method}")
    private String entryFailMethod;

    @Value("${project.process.entryFeedback.terminalB.updateTime.text}")
    private String updateEntryTimeText;

    @Value("${project.process.entryFeedback.terminalB.updateTime.method}")
    private String updateEntryTimeMethod;

    @Value("${project.process.quitFeedback.terminalB.default.text}")
    private String quitFeedbackText;

    @Value("${project.process.quitFeedback.terminalB.default.method}")
    private String quitFeedbackMethod;

    @Value("${project.process.appealfeedback.terminalA.default.text}")
    private String recognizedText;

    @Value("${project.process.appealfeedback.terminalA.default.method}")
    private String recognizedMethod;

    @Value("${project.process.appealfeedback.terminalA.notRecognized.text}")
    private String notRecognizedText;

    @Value("${project.process.appealfeedback.terminalA.notRecognized.method}")
    private String notRecognizedMethod;

    @Value("${project.process.appealfeedback.terminalB.default.text}")
    private String appealFeedbackDefaultText;

    @Value("${project.process.appealfeedback.terminalB.default.method}")
    private String appealFeedbackDefaultMethod;

    @Value("${project.process.appealfeedback.terminalB.feedback.text}")
    private String appealFeedbackFeedbackText;

    @Value("${project.process.appealfeedback.terminalB.feedback.method}")
    private String appealFeedbackFeedbackMethod;

    public String getCancelResumeText() {
        return cancelResumeText;
    }

    public void setCancelResumeText(String cancelResumeText) {
        this.cancelResumeText = cancelResumeText;
    }

    public String getCancelResumeMethod() {
        return cancelResumeMethod;
    }

    public void setCancelResumeMethod(String cancelResumeMethod) {
        this.cancelResumeMethod = cancelResumeMethod;
    }

    public String getPassInterviewText() {
        return passInterviewText;
    }

    public void setPassInterviewText(String passInterviewText) {
        this.passInterviewText = passInterviewText;
    }

    public String getPassInterviewMethod() {
        return passInterviewMethod;
    }

    public void setPassInterviewMethod(String passInterviewMethod) {
        this.passInterviewMethod = passInterviewMethod;
    }

    public String getNotArrivedInterviedText() {
        return notArrivedInterviedText;
    }

    public void setNotArrivedInterviedText(String notArrivedInterviedText) {
        this.notArrivedInterviedText = notArrivedInterviedText;
    }

    public String getNotArrivedInterviedMethod() {
        return notArrivedInterviedMethod;
    }

    public void setNotArrivedInterviedMethod(String notArrivedInterviedMethod) {
        this.notArrivedInterviedMethod = notArrivedInterviedMethod;
    }

    public String getFailInterviedText() {
        return failInterviedText;
    }

    public void setFailInterviedText(String failInterviedText) {
        this.failInterviedText = failInterviedText;
    }

    public String getFailInterviedMethod() {
        return failInterviedMethod;
    }

    public void setFailInterviedMethod(String failInterviedMethod) {
        this.failInterviedMethod = failInterviedMethod;
    }

    public String getUpdateInterviewText() {
        return updateInterviewText;
    }

    public void setUpdateInterviewText(String updateInterviewText) {
        this.updateInterviewText = updateInterviewText;
    }

    public String getUpdateInterviewMethod() {
        return updateInterviewMethod;
    }

    public void setUpdateInterviewMethod(String updateInterviewMethod) {
        this.updateInterviewMethod = updateInterviewMethod;
    }

    public String getIsKonwText() {
        return isKonwText;
    }

    public void setIsKonwText(String isKonwText) {
        this.isKonwText = isKonwText;
    }

    public String getIsKonwMethod() {
        return isKonwMethod;
    }

    public void setIsKonwMethod(String isKonwMethod) {
        this.isKonwMethod = isKonwMethod;
    }

    public String getAppealText() {
        return appealText;
    }

    public void setAppealText(String appealText) {
        this.appealText = appealText;
    }

    public String getAppealMethod() {
        return appealMethod;
    }

    public void setAppealMethod(String appealMethod) {
        this.appealMethod = appealMethod;
    }

    public String getEntrySuccessText() {
        return entrySuccessText;
    }

    public void setEntrySuccessText(String entrySuccessText) {
        this.entrySuccessText = entrySuccessText;
    }

    public String getEntrySuccessMethod() {
        return entrySuccessMethod;
    }

    public void setEntrySuccessMethod(String entrySuccessMethod) {
        this.entrySuccessMethod = entrySuccessMethod;
    }

    public String getEntryFailText() {
        return entryFailText;
    }

    public void setEntryFailText(String entryFailText) {
        this.entryFailText = entryFailText;
    }

    public String getEntryFailMethod() {
        return entryFailMethod;
    }

    public void setEntryFailMethod(String entryFailMethod) {
        this.entryFailMethod = entryFailMethod;
    }

    public String getUpdateEntryTimeText() {
        return updateEntryTimeText;
    }

    public void setUpdateEntryTimeText(String updateEntryTimeText) {
        this.updateEntryTimeText = updateEntryTimeText;
    }

    public String getUpdateEntryTimeMethod() {
        return updateEntryTimeMethod;
    }

    public void setUpdateEntryTimeMethod(String updateEntryTimeMethod) {
        this.updateEntryTimeMethod = updateEntryTimeMethod;
    }

    public String getQuitFeedbackText() {
        return quitFeedbackText;
    }

    public void setQuitFeedbackText(String quitFeedbackText) {
        this.quitFeedbackText = quitFeedbackText;
    }

    public String getQuitFeedbackMethod() {
        return quitFeedbackMethod;
    }

    public void setQuitFeedbackMethod(String quitFeedbackMethod) {
        this.quitFeedbackMethod = quitFeedbackMethod;
    }

    public String getRecognizedText() {
        return recognizedText;
    }

    public void setRecognizedText(String recognizedText) {
        this.recognizedText = recognizedText;
    }

    public String getRecognizedMethod() {
        return recognizedMethod;
    }

    public void setRecognizedMethod(String recognizedMethod) {
        this.recognizedMethod = recognizedMethod;
    }

    public String getNotRecognizedText() {
        return notRecognizedText;
    }

    public void setNotRecognizedText(String notRecognizedText) {
        this.notRecognizedText = notRecognizedText;
    }

    public String getNotRecognizedMethod() {
        return notRecognizedMethod;
    }

    public void setNotRecognizedMethod(String notRecognizedMethod) {
        this.notRecognizedMethod = notRecognizedMethod;
    }

    public String getSmsTemplateText() {
        return smsTemplateText;
    }

    public void setSmsTemplateText(String smsTemplateText) {
        this.smsTemplateText = smsTemplateText;
    }

    public String getSmsTemplateMethod() {
        return smsTemplateMethod;
    }

    public void setSmsTemplateMethod(String smsTemplateMethod) {
        this.smsTemplateMethod = smsTemplateMethod;
    }

    public String getAppealFeedbackDefaultText() {
        return appealFeedbackDefaultText;
    }

    public void setAppealFeedbackDefaultText(String appealFeedbackDefaultText) {
        this.appealFeedbackDefaultText = appealFeedbackDefaultText;
    }

    public String getAppealFeedbackDefaultMethod() {
        return appealFeedbackDefaultMethod;
    }

    public void setAppealFeedbackDefaultMethod(String appealFeedbackDefaultMethod) {
        this.appealFeedbackDefaultMethod = appealFeedbackDefaultMethod;
    }

    public String getAppealFeedbackFeedbackText() {
        return appealFeedbackFeedbackText;
    }

    public void setAppealFeedbackFeedbackText(String appealFeedbackFeedbackText) {
        this.appealFeedbackFeedbackText = appealFeedbackFeedbackText;
    }

    public String getAppealFeedbackFeedbackMethod() {
        return appealFeedbackFeedbackMethod;
    }

    public void setAppealFeedbackFeedbackMethod(String appealFeedbackFeedbackMethod) {
        this.appealFeedbackFeedbackMethod = appealFeedbackFeedbackMethod;
    }
}
