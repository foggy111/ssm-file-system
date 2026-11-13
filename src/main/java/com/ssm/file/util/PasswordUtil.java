package com.ssm.file.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * 密码工具：SHA-256 加盐。
 * 存储格式为 salt 单独一列，password 列为 SHA-256(salt + 明文) 的小写十六进制。
 */
public final class PasswordUtil {

    private PasswordUtil() {
    }

    public static String randomSalt() {
        byte[] buf = new byte[8];
        new SecureRandom().nextBytes(buf);
        return toHex(buf);
    }

    public static String encrypt(String rawPassword, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest((salt + rawPassword).getBytes(StandardCharsets.UTF_8));
            return toHex(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 不可用", e);
        }
    }

    public static boolean matches(String rawPassword, String salt, String storedHex) {
        if (rawPassword == null || salt == null || storedHex == null) {
            return false;
        }
        return storedHex.equalsIgnoreCase(encrypt(rawPassword, salt));
    }

    private static String toHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
