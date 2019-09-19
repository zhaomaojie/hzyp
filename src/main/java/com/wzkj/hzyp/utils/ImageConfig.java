package com.wzkj.hzyp.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @user zhaoMaoJie
 * @date {DATE}
 */
@Configuration
public class ImageConfig {

    @Value("${project.image.terminanA.man}")
    private String manAvatarForA;

    @Value("${project.image.terminanA.woman}")
    private String womanAvatarForA;

    @Value("${project.image.terminalB.man}")
    private String manAvatarForB;

    @Value("${project.image.terminalB.woman}")
    private String womanAvatarForB;

    @Value("${project.image.resume.man}")
    private String resumeMan;

    @Value("${project.image.resume.woman}")
    private String resumeWoman;

    public String getManAvatarForA() {
        return manAvatarForA;
    }

    public void setManAvatarForA(String manAvatarForA) {
        this.manAvatarForA = manAvatarForA;
    }

    public String getWomanAvatarForA() {
        return womanAvatarForA;
    }

    public void setWomanAvatarForA(String womanAvatarForA) {
        this.womanAvatarForA = womanAvatarForA;
    }

    public String getManAvatarForB() {
        return manAvatarForB;
    }

    public void setManAvatarForB(String manAvatarForB) {
        this.manAvatarForB = manAvatarForB;
    }

    public String getWomanAvatarForB() {
        return womanAvatarForB;
    }

    public void setWomanAvatarForB(String womanAvatarForB) {
        this.womanAvatarForB = womanAvatarForB;
    }

    public String getResumeMan() {
        return resumeMan;
    }

    public void setResumeMan(String resumeMan) {
        this.resumeMan = resumeMan;
    }

    public String getResumeWoman() {
        return resumeWoman;
    }

    public void setResumeWoman(String resumeWoman) {
        this.resumeWoman = resumeWoman;
    }

}
