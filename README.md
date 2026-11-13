# 基于 SSM 的资料上传下载系统

《Java 企业级开发（SSM 框架整合）》课堂实训工程（V2.0 简化版任务书）。

本项目使用 **Spring + Spring MVC + MyBatis**（非 Spring Boot），JSP + JSTL 视图，文件落盘、元信息入库，拦截器统一登录校验。

## 环境要求

- JDK 11 或 17（IDEA 用 17 即可）
- Maven 3.6+
- MySQL 5.7 或 8.x
- **Tomcat 9.x**（必须是 9，不要用 Tomcat 10。Tomcat 10 是 Jakarta 命名空间，和本项目的 `javax.servlet` 不兼容。）

## 如何启动

1. 用 IDEA 打开目录 `ssm-file-system`（Open 已有 Maven 工程）。
2. 在 MySQL 中依次执行：
   - `sql/schema.sql`
   - `sql/data.sql`
3. 复制 `src/main/resources/jdbc.properties` 为同目录下的 `jdbc.local.properties`（该文件已被 git 忽略），只改这一行：

   ```
   jdbc.password=你的MySQL密码
   ```

   不要把真实密码写进 `jdbc.properties`，以免提交到 GitHub。
4. 在 IDEA 中配置 Tomcat 9：
   - Run → Edit Configurations → + → Tomcat Server → Local
   - Application server 选择 Tomcat 9 的安装目录
   - Deployment 部署本模块的 `war` 或 `exploded war`，Application context 建议设为 `/ssm-file-system`
5. 启动后访问：
   - 自检页：`http://localhost:8080/ssm-file-system/hello`
   - 登录页：`http://localhost:8080/ssm-file-system/login`

## 测试账号

| 账号 | 密码 | 说明 |
| --- | --- | --- |
| zhangsan | 123456 | 张三 |
| lisi | 123456 | 李四 |

密码在数据库中为 **SHA-256(salt + 明文)**，盐与哈希分列存储，不是明文。

## 验收演示（四条）

1. 用 `zhangsan` 登录，上传一个 PDF。磁盘 `用户目录/ssm-uploads/zhangsan/年/月/` 下会出现 UUID 命名的文件，`file_info` 表新增一条记录，列表页能看到该文件。
2. 退出登录（或换一个无痕窗口），直接访问 `/files/upload`，会被重定向到登录页。
3. 登录后点击下载，浏览器得到的文件名是中文原名且不乱码。
4. 改用 `lisi` 登录，看不到也下载不了张三的文件。

补充自测：上传超过 10MB 或 `.exe` 会被服务端拒绝；列表只显示自己的记录；上传人可冻结下载；下载次数会增加。

## 主要设计说明

- **磁盘存文件、数据库存元数据**：原始文件名只写入 `file_info.origin_name`，下载时用它生成 `Content-Disposition`。磁盘文件名是服务端生成的 UUID，避免重名和路径注入。
- **按 ID 取路径**：下载接口只接收 `fileId`，存储路径从数据库读取后再拼到配置的根目录下，禁止用用户输入拼接磁盘路径。
- **归属校验**：下载、冻结、删除前都检查 `uploader_id` 是否为当前登录用户。
- **拦截器**：`WebMvcConfig` 实现 `WebMvcConfigurer#addInterceptors`，放行 `/login`、`/doLogin`、`/hello` 以及 `/css/**`、`/js/**`、`/images/**`。
- **上传校验**：大小 10MB、扩展名白名单均在服务端完成；并按扩展名分为文档 / 图片 / 压缩包。支持一次选择多个文件。
- **目录规则**：`{upload.base-dir}/{username}/{yyyy}/{MM}/{uuid}.{ext}`，根目录来自配置文件，不是代码里的绝对路径。
- **`file_info(uploader_id)` 索引**：列表查询只取当前用户的记录，该索引避免全表扫描。
- **选做 F5**：`DownloadLogAspect` 在 `recordDownload` 成功返回后写入 `download_log`。

## 工程结构

```
ssm-file-system
├── pom.xml
├── sql/schema.sql
├── sql/data.sql
├── src/main/java/com/ssm/file
│   ├── controller
│   ├── interceptor
│   ├── config
│   ├── service
│   ├── mapper
│   ├── entity
│   ├── aspect
│   └── util
└── src/main/resources
    ├── spring/
    └── mapper/
```
