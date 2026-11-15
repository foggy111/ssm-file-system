package com.ssm.file.entity;

import java.util.Date;

public class DownloadLog {
    private Integer id;
    private Integer fileId;
    private Integer downloaderId;
    private Date downloadTime;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getFileId() { return fileId; }
    public void setFileId(Integer fileId) { this.fileId = fileId; }
    public Integer getDownloaderId() { return downloaderId; }
    public void setDownloaderId(Integer downloaderId) { this.downloaderId = downloaderId; }
    public Date getDownloadTime() { return downloadTime; }
    public void setDownloadTime(Date downloadTime) { this.downloadTime = downloadTime; }
}
