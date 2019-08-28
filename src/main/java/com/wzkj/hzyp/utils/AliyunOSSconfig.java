package com.wzkj.hzyp.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @user zhaoMaoJie
 * @date {DATE}
 */
@Configuration
public class AliyunOSSconfig {

    @Value("${project.oss.endpoint}")
    private String endpoint;

    @Value("${project.oss.accessKeyId}")
    private String accessKeyId;

    @Value("${project.oss.accessKeySecret}")
    private String accessKeySecret;

    @Value("${project.oss.bucketName}")
    private String bucketName;

    @Value("${project.oss.storeFolder}")
    private String storeFolder;

    @Value("${project.oss.userFolder}")
    private String userFolder;

    @Value("${project.oss.webUrl}")
    private String webUrl;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getStoreFolder() {
        return storeFolder;
    }

    public void setStoreFolder(String storeFolder) {
        this.storeFolder = storeFolder;
    }

    public String getUserFolder() {
        return userFolder;
    }

    public void setUserFolder(String userFolder) {
        this.userFolder = userFolder;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }
}
