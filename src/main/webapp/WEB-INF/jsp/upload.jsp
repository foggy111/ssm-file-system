<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>提交作业</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="page">
<div class="wrap">
    <div class="card">
        <div class="topbar">
            <div>
                <h1>提交作业</h1>
                <p class="muted">当前用户：${user.realName}（${user.username}）</p>
            </div>
            <div class="nav">
                <a href="${pageContext.request.contextPath}/files">返回列表</a>
                <a href="${pageContext.request.contextPath}/logout">退出</a>
            </div>
        </div>
        <c:if test="${not empty error}">
            <div class="msg err">${error}</div>
        </c:if>
        <form method="post" action="${pageContext.request.contextPath}/files/upload" enctype="multipart/form-data">
            <label for="files">选择文件（可同时选择多个）</label>
            <input id="files" type="file" name="files" multiple required>
            <p class="muted">单个文件不超过 10MB；允许 pdf / jpg / png / doc / docx / zip。系统会按扩展名自动分类。</p>
            <p style="margin-top:18px;"><button class="btn" type="submit">上传</button></p>
        </form>
    </div>
</div>
</body>
</html>
