package com.ssm.file.service.impl;

import com.ssm.file.entity.FileInfo;
import com.ssm.file.entity.SysUser;
import com.ssm.file.mapper.FileMapper;
import com.ssm.file.service.FileService;
import com.ssm.file.util.FileStorageService;
import com.ssm.file.util.FileTypes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    private final FileMapper fileMapper;
    private final FileStorageService storageService;

    public FileServiceImpl(FileMapper fileMapper, FileStorageService storageService) {
        this.fileMapper = fileMapper;
        this.storageService = storageService;
    }

    @Override
    public List<FileInfo> listMine(Integer uploaderId) {
        return fileMapper.findByUploaderId(uploaderId);
    }

    @Override
    @Transactional
    public String upload(MultipartFile[] files, SysUser user) {
        if (files == null || files.length == 0) {
            return "请选择要上传的文件";
        }
        List<String> errors = new ArrayList<>();
        int success = 0;
        for (MultipartFile file : files) {
            if (file == null || file.isEmpty()) {
                continue;
            }
            String message = saveOne(file, user);
            if (message == null) {
                success++;
            } else {
                errors.add(file.getOriginalFilename() + "：" + message);
            }
        }
        if (success == 0 && errors.isEmpty()) {
            return "请选择要上传的文件";
        }
        if (!errors.isEmpty()) {
            String joined = String.join("；", errors);
            if (success > 0) {
                return "成功 " + success + " 个，失败：" + joined;
            }
            return joined;
        }
        return null;
    }

    private String saveOne(MultipartFile file, SysUser user) {
        if (file.getSize() > FileTypes.MAX_SIZE) {
            return "文件大小超过 10MB，已被拒绝";
        }
        String ext = FileTypes.normalizeExt(file.getOriginalFilename());
        if (!FileTypes.allowed(ext)) {
            return "扩展名不被允许（仅支持 pdf/jpg/png/doc/docx/zip）";
        }
        try {
            String relative = storageService.store(file, user.getUsername(), ext);
            Path stored = storageService.resolve(relative);
            FileInfo info = new FileInfo();
            info.setOriginName(file.getOriginalFilename());
            info.setStoreName(stored.getFileName().toString());
            info.setFilePath(relative);
            info.setExt(ext);
            info.setFileSize(file.getSize());
            info.setContentType(file.getContentType());
            info.setCategory(FileTypes.classify(ext));
            info.setUploaderId(user.getId());
            info.setCreateTime(new Date());
            info.setStatus(1);
            info.setFrozen(0);
            info.setDownloadCount(0);
            fileMapper.insert(info);
            return null;
        } catch (IOException e) {
            return "保存失败，请稍后重试";
        }
    }
}
