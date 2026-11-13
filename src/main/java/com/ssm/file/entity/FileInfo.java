package com.ssm.file.entity;

import java.util.Date;

public class FileInfo {
    private Integer id;
    private String originName;
    private String storeName;
    private String filePath;
    private String ext;
    private Long fileSize;
    private String contentType;
    private String category;
    private Integer uploaderId;
    private Date createTime;
    private Integer status;
    private Integer frozen;
    private Integer downloadCount;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getOriginName() { return originName; }
    public void setOriginName(String originName) { this.originName = originName; }
    public String getStoreName() { return storeName; }
    public void setStoreName(String storeName) { this.storeName = storeName; }
    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
    public String getExt() { return ext; }
    public void setExt(String ext) { this.ext = ext; }
    public Long getFileSize() { return fileSize; }
    public void setFileSize(Long fileSize) { this.fileSize = fileSize; }
    public String getContentType() { return contentType; }
    public void setContentType(String contentType) { this.contentType = contentType; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public Integer getUploaderId() { return uploaderId; }
    public void setUploaderId(Integer uploaderId) { this.uploaderId = uploaderId; }
    public Date getCreateTime() { return createTime; }
    public void setCreateTime(Date createTime) { this.createTime = createTime; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public Integer getFrozen() { return frozen; }
    public void setFrozen(Integer frozen) { this.frozen = frozen; }
    public Integer getDownloadCount() { return downloadCount; }
    public void setDownloadCount(Integer downloadCount) { this.downloadCount = downloadCount; }
}
