package com.wzkj.hzyp.entity;

/* *
 * 文件上传后的回调文件类型
 * @author zhaoMaoJie
 * @date 2019/8/9 0009
 */
public class FileEntity {

    /**
     * 文件大小
     */
    private Long fileSize;
    /**
     * 文件的绝对路径
     */
    private String fileAPUrl;

    /**
     * 文件的web访问地址
     */
    private String webUrl;

    /**
     * 文件后缀
     */
    private String fileSuffix;
    /**
     * 存储的bucket
     */
    private String fileBucket;

    /**
     * 原文件名
     */
    private String oldFileName;
    /**
     * 存储的文件夹
     */
    private String folder;

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileAPUrl() {
        return fileAPUrl;
    }

    public void setFileAPUrl(String fileAPUrl) {
        this.fileAPUrl = fileAPUrl;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getFileSuffix() {
        return fileSuffix;
    }

    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }

    public String getFileBucket() {
        return fileBucket;
    }

    public void setFileBucket(String fileBucket) {
        this.fileBucket = fileBucket;
    }

    public String getOldFileName() {
        return oldFileName;
    }

    public void setOldFileName(String oldFileName) {
        this.oldFileName = oldFileName;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public FileEntity(){

    }

    public FileEntity(Long fileSize, String fileAPUrl, String webUrl, String fileSuffix, String fileBucket, String oldFileName, String folder) {
        this.fileSize = fileSize;
        this.fileAPUrl = fileAPUrl;
        this.webUrl = webUrl;
        this.fileSuffix = fileSuffix;
        this.fileBucket = fileBucket;
        this.oldFileName = oldFileName;
        this.folder = folder;
    }
}
