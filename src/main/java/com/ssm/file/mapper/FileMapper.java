package com.ssm.file.mapper;

import com.ssm.file.entity.FileInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FileMapper {

    int insert(FileInfo fileInfo);

    FileInfo findById(@Param("id") Integer id);

    List<FileInfo> findByUploaderId(@Param("uploaderId") Integer uploaderId);

    int updateFrozen(@Param("id") Integer id, @Param("frozen") Integer frozen);

    int updateStatus(@Param("id") Integer id, @Param("status") Integer status);

    int incrementDownloadCount(@Param("id") Integer id);
}
