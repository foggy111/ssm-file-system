package com.ssm.file.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/** 扩展名白名单与文件分类。 */
public final class FileTypes {

    public static final long MAX_SIZE = 10L * 1024 * 1024;

    private static final Set<String> ALLOWED = new HashSet<>(
            Arrays.asList("pdf", "jpg", "png", "doc", "docx", "zip"));

    private FileTypes() {
    }

    public static String normalizeExt(String originalFilename) {
        if (originalFilename == null) {
            return "";
        }
        int dot = originalFilename.lastIndexOf('.');
        if (dot < 0 || dot == originalFilename.length() - 1) {
            return "";
        }
        return originalFilename.substring(dot + 1).toLowerCase(Locale.ROOT);
    }

    public static boolean allowed(String ext) {
        return ALLOWED.contains(ext);
    }

    /** 按扩展名分类：文档 / 图片 / 压缩包。 */
    public static String classify(String ext) {
        if ("pdf".equals(ext) || "doc".equals(ext) || "docx".equals(ext)) {
            return "文档";
        }
        if ("jpg".equals(ext) || "png".equals(ext)) {
            return "图片";
        }
        if ("zip".equals(ext)) {
            return "压缩包";
        }
        return "其他";
    }
}
