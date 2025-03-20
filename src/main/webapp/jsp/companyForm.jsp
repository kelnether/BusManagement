<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>添加/编辑企业</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/newStyle.css">
</head>
<body>
<%@ include file="header.jsp" %>
<h2>${company == null ? "添加新企业" : "编辑企业"}</h2>
<form action="${pageContext.request.contextPath}/CompanyController/${company == null ? "insert" : "update"}" method="post">
    <input type="hidden" name="id" value="${company != null ? company.id : ''}">
    <label for="name">企业名称:</label>
    <input type="text" id="name" name="name" value="${company != null ? company.name : ''}" required><br>
    <label for="website">企业网站:</label>
    <input type="url" id="website" name="website" value="${company != null ? company.website : ''}" required><br>
    <input type="submit" value="保存">
</form>
<br>
<a href="${pageContext.request.contextPath}/CompanyController/list">返回企业列表</a>
<br>
<a href="${pageContext.request.contextPath}/jsp/index.jsp">返回主页</a>
<%@ include file="footer.jsp" %>
</body>
</html>
