package com.ssm.file.service;

import com.ssm.file.entity.FileInfo;
import com.ssm.file.entity.SysUser;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;

public interface FileService {

    List<FileInfo> listMine(Integer uploaderId);

    String upload(MultipartFile[] files, SysUser user);

    /**
     * 按 ID 取文件并校验归属。无权或不存在返回 null。
     */
    FileInfo findOwned(Integer fileId, Integer userId);

    Path resolveDiskPath(FileInfo info);

    void toggleFrozen(Integer fileId, Integer userId);

    void logicalDelete(Integer fileId, Integer userId);

    /** 下载成功后增加次数；下载日志由 AOP 同步写入。 */
    void recordDownload(Integer fileId, Integer downloaderId);
}
