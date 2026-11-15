package com.ssm.file.aspect;

import com.ssm.file.entity.DownloadLog;
import com.ssm.file.mapper.DownloadLogMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 选做 F5：在下载次数入库成功后，同步写入 download_log。
 */
@Aspect
@Component
public class DownloadLogAspect {

    private static final Logger log = LoggerFactory.getLogger(DownloadLogAspect.class);

    private final DownloadLogMapper downloadLogMapper;

    public DownloadLogAspect(DownloadLogMapper downloadLogMapper) {
        this.downloadLogMapper = downloadLogMapper;
    }

    @Pointcut("execution(* com.ssm.file.service.impl.FileServiceImpl.recordDownload(..))")
    public void downloadRecord() {
    }

    @AfterReturning("downloadRecord()")
    public void writeLog(JoinPoint jp) {
        Object[] args = jp.getArgs();
        if (args == null || args.length < 2) {
            return;
        }
        if (!(args[0] instanceof Integer) || !(args[1] instanceof Integer)) {
            return;
        }
        try {
            DownloadLog row = new DownloadLog();
            row.setFileId((Integer) args[0]);
            row.setDownloaderId((Integer) args[1]);
            row.setDownloadTime(new Date());
            downloadLogMapper.insert(row);
        } catch (Exception e) {
            log.warn("写入下载日志失败: {}", e.toString());
        }
    }
}
