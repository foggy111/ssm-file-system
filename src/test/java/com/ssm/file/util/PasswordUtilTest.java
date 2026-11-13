package com.ssm.file.util;

import org.junit.Assert;
import org.junit.Test;

public class PasswordUtilTest {

    @Test
    public void encryptIsStableAndSalted() {
        String salt = "23a8b7110cc826d9";
        String hash = PasswordUtil.encrypt("123456", salt);
        Assert.assertEquals("28fd8481cf82b8f1e6f595baa7db7c087847e114b7889ee06ae0a78011078ddc", hash);
        Assert.assertTrue(PasswordUtil.matches("123456", salt, hash));
        Assert.assertFalse(PasswordUtil.matches("1234567", salt, hash));
    }
}
