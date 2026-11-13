USE ssm_file;

-- 测试账号密码均为 123456，算法：SHA-256(salt + 明文密码)，禁止明文存储
INSERT INTO sys_user (username, password, salt, real_name, create_time) VALUES
('zhangsan', '28fd8481cf82b8f1e6f595baa7db7c087847e114b7889ee06ae0a78011078ddc', '23a8b7110cc826d9', '张三', NOW()),
('lisi',     '425b3c5a1677a6c6c8333276158b7e79ca0e2bcbe1673923e40921ffa5b4e040', '7f30e233ffd8e8fa', '李四', NOW());
