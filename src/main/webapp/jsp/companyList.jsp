<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>公司列表</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/newStyle.css">
</head>
<body>
<%@ include file="header.jsp" %>
<h2>公司列表</h2>
<a href="${pageContext.request.contextPath}/CompanyController/new">添加新公司</a>
<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>名称</th>
        <th>网站</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="company" items="${listCompany}">
        <tr>
            <td>${company.id}</td>
            <td>${company.name}</td>
            <td><a href="${company.website}" target="_blank">${company.website}</a></td>
            <td>
                <a href="${pageContext.request.contextPath}/CompanyController/edit?id=${company.id}">编辑</a>
                <a href="${pageContext.request.contextPath}/CompanyController/delete?id=${company.id}" onclick="return confirm('确定删除吗？')">删除</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<br>
<a href="${pageContext.request.contextPath}/jsp/index.jsp">返回主页</a>
<%@ include file="footer.jsp" %>
</body>
</html>
