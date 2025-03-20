<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>查看公交线路</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/newStyle.css">
</head>
<body>
<%@ include file="header.jsp" %>
<h1>公交线路</h1>
<table border="1">
    <tr>
        <th>ID</th>
        <th>线路名</th>
        <th>运营企业</th>
        <th>操作</th>
    </tr>
    <c:forEach var="busLine" items="${listBusLine}">
        <tr>
            <td>${busLine.id}</td>
            <td>${busLine.lineName}</td>
            <td>
                <c:choose>
                    <c:when test="${busLine.companyName != null}">
                        ${busLine.companyName}
                    </c:when>
                    <c:otherwise>
                        --
                    </c:otherwise>
                </c:choose>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/BusLineController/edit?id=${busLine.id}">编辑</a>
                <a href="${pageContext.request.contextPath}/BusLineController/delete?id=${busLine.id}">删除</a>
                <a href="${pageContext.request.contextPath}/BusLineController/info?id=${busLine.id}">详细信息</a>
            </td>
        </tr>
    </c:forEach>
</table>
<a href="${pageContext.request.contextPath}/BusLineController/new">添加新线路</a>

<%@ include file="footer.jsp" %>
</body>
</html>
