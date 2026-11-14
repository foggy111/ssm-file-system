package com.ssm.file.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

/**
 * 磁盘存储：根目录来自配置文件 upload.base-dir，
 * 落盘子路径为 {username}/{year}/{month}/{uuid}.{ext}。
 */
@Component
public class FileStorageService {

    @Value("${upload.base-dir}")
    private String baseDir;

    public Path getBaseDir() {
        return Paths.get(baseDir).toAbsolutePath().normalize();
    }

    /**
     * @return 相对路径（写入 file_info.file_path），不是绝对路径
     */
    public String store(MultipartFile file, String username, String ext) throws IOException {
        LocalDate today = LocalDate.now();
        String storeName = UUID.randomUUID().toString().replace("-", "") + "." + ext;
        String relative = String.format("%s/%d/%02d/%s",
                username, today.getYear(), today.getMonthValue(), storeName);
        Path target = resolve(relative);
        Files.createDirectories(target.getParent());
        file.transferTo(target.toFile());
        return relative;
    }

    public Path resolve(String relativePath) {
        Path base = getBaseDir();
        Path target = base.resolve(relativePath).normalize();
        if (!target.startsWith(base)) {
            throw new IllegalArgumentException("非法存储路径");
        }
        return target;
    }
}
