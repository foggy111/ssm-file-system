<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>环境自检</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="page">
<div class="wrap">
    <div class="card">
        <h1>资料上传下载系统</h1>
        <p class="muted">${message}</p>
        <p>接下来请先执行 sql/schema.sql 与 sql/data.sql，再进入登录页。</p>
        <p><a class="btn" href="${pageContext.request.contextPath}/login">去登录</a></p>
    </div>
</div>
</body>
</html>
