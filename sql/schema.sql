-- 基于 SSM 的资料上传下载系统 建表脚本
-- MySQL 5.7+ / 8.x，字符集 utf8mb4
CREATE DATABASE IF NOT EXISTS ssm_file DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE ssm_file;

DROP TABLE IF EXISTS download_log;
DROP TABLE IF EXISTS file_info;
DROP TABLE IF EXISTS sys_user;

CREATE TABLE sys_user (
    id          INT PRIMARY KEY AUTO_INCREMENT,
    username    VARCHAR(50)  NOT NULL UNIQUE,
    password    VARCHAR(64)  NOT NULL COMMENT 'SHA-256(salt + 明文) 的十六进制',
    salt        VARCHAR(32)  NOT NULL,
    real_name   VARCHAR(50)  NOT NULL,
    create_time DATETIME     NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

CREATE TABLE file_info (
    id             INT PRIMARY KEY AUTO_INCREMENT,
    origin_name    VARCHAR(255) NOT NULL COMMENT '用户原始文件名，仅存库不用于拼磁盘路径',
    store_name     VARCHAR(255) NOT NULL COMMENT '服务端生成的 UUID 文件名',
    file_path      VARCHAR(500) NOT NULL COMMENT '相对存储路径 username/yyyy/MM/uuid.ext',
    ext            VARCHAR(20)  NOT NULL,
    file_size      BIGINT       NOT NULL COMMENT '字节',
    content_type   VARCHAR(100) DEFAULT NULL,
    category       VARCHAR(20)  NOT NULL COMMENT '文件分类：文档/图片/压缩包',
    uploader_id    INT          NOT NULL,
    create_time    DATETIME     NOT NULL,
    status         TINYINT      NOT NULL DEFAULT 1 COMMENT '1 正常，0 逻辑删除',
    frozen         TINYINT      NOT NULL DEFAULT 0 COMMENT '1 冻结下载',
    download_count INT          NOT NULL DEFAULT 0,
    INDEX idx_uploader (uploader_id),
    CONSTRAINT fk_file_uploader FOREIGN KEY (uploader_id) REFERENCES sys_user(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件元信息';

-- 选做 F5：下载日志
CREATE TABLE download_log (
    id            INT PRIMARY KEY AUTO_INCREMENT,
    file_id       INT      NOT NULL,
    downloader_id INT      NOT NULL,
    download_time DATETIME NOT NULL,
    INDEX idx_log_file (file_id),
    INDEX idx_log_user (downloader_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='下载日志';
