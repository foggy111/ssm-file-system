<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>提示</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="page">
<div class="wrap">
    <div class="card">
        <h1>提示</h1>
        <p class="msg err">
            <c:out value="${empty message ? '操作未能完成，请返回后重试。' : message}"/>
        </p>
        <p><a class="btn ghost" href="${pageContext.request.contextPath}/files">返回列表</a></p>
    </div>
</div>
</body>
</html>
