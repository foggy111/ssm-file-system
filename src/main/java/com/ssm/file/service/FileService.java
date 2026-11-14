package com.ssm.file.service;

import com.ssm.file.entity.FileInfo;
import com.ssm.file.entity.SysUser;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    List<FileInfo> listMine(Integer uploaderId);

    String upload(MultipartFile[] files, SysUser user);
}
