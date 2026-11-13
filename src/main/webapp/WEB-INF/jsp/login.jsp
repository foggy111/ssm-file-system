<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>登录</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="page">
<div class="wrap" style="max-width:420px;">
    <div class="card">
        <h1>学员登录</h1>
        <p class="muted">资料上传下载系统</p>
        <c:if test="${not empty error}">
            <div class="msg err">${error}</div>
        </c:if>
        <form method="post" action="${pageContext.request.contextPath}/doLogin">
            <label for="username">账号</label>
            <input id="username" type="text" name="username" value="${username}" required>
            <label for="password">密码</label>
            <input id="password" type="password" name="password" required>
            <p style="margin-top:18px;"><button class="btn" type="submit">登录</button></p>
        </form>
        <p class="muted">测试账号：zhangsan / lisi，密码均为 123456</p>
    </div>
</div>
</body>
</html>
