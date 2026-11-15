<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>我的提交</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="page">
<div class="wrap">
    <div class="card">
        <div class="topbar">
            <div>
                <h1>我的提交记录</h1>
                <p class="muted">当前用户：${user.realName}（${user.username}）</p>
            </div>
            <div class="nav">
                <a class="btn" href="${pageContext.request.contextPath}/files/upload">上传文件</a>
                <a href="${pageContext.request.contextPath}/logout">退出</a>
            </div>
        </div>
        <c:if test="${not empty success}">
            <div class="msg ok">${success}</div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="msg err">${error}</div>
        </c:if>

        <c:choose>
            <c:when test="${empty files}">
                <div class="empty">暂无提交记录，点击右上角上传文件。</div>
            </c:when>
            <c:otherwise>
                <table>
                    <thead>
                    <tr>
                        <th>原始文件名</th>
                        <th>分类</th>
                        <th>大小</th>
                        <th>上传时间</th>
                        <th>状态</th>
                        <th>下载次数</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${files}" var="f">
                        <tr>
                            <td>${f.originName}</td>
                            <td><span class="tag">${f.category}</span></td>
                            <td>
                                <c:choose>
                                    <c:when test="${f.fileSize lt 1024}">${f.fileSize} B</c:when>
                                    <c:when test="${f.fileSize lt 1048576}">
                                        <fmt:formatNumber value="${f.fileSize / 1024.0}" maxFractionDigits="1"/> KB
                                    </c:when>
                                    <c:otherwise>
                                        <fmt:formatNumber value="${f.fileSize / 1048576.0}" maxFractionDigits="1"/> MB
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td><fmt:formatDate value="${f.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            <td>
                                <c:choose>
                                    <c:when test="${f.frozen == 1}"><span class="tag warn">已冻结</span></c:when>
                                    <c:otherwise>可下载</c:otherwise>
                                </c:choose>
                            </td>
                            <td>${f.downloadCount}</td>
                            <td class="actions">
                                <a class="btn small ghost" href="${pageContext.request.contextPath}/files/download/${f.id}">下载</a>
                                <form method="post" action="${pageContext.request.contextPath}/files/freeze/${f.id}">
                                    <button class="btn small ghost" type="submit">
                                        <c:choose>
                                            <c:when test="${f.frozen == 1}">解冻</c:when>
                                            <c:otherwise>冻结下载</c:otherwise>
                                        </c:choose>
                                    </button>
                                </form>
                                <form method="post" action="${pageContext.request.contextPath}/files/delete/${f.id}"
                                      onsubmit="return confirm('确定逻辑删除该文件吗？');">
                                    <button class="btn small danger" type="submit">删除</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</body>
</html>
