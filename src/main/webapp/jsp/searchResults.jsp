<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Search Results</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/newStyle.css">

</head>
<body>
<%@ include file="header.jsp" %>
<h1>查询结果</h1>

<h2>显示顺序</h2>
<form action="${pageContext.request.contextPath}/BusLineController/search" method="get">
    <input type="hidden" name="query" value="${query}">
    <label for="sortBy">排序依据:</label>
    <select name="sortBy" id="sortBy">
        <option value="lineName">名称顺序</option>
        <option value="ascii">ASCII顺序</option>
    </select>
    <label for="sortOrder">顺序:</label>
    <select name="sortOrder" id="sortOrder">
        <option value="asc">递增</option>
        <option value="desc">递减</option>
    </select>
    <input type="submit" value="Sort">
</form>

<h2>线路</h2>
<c:if test="${not empty busLines}">
    <table border="1">
        <tr>
            <th>ID</th>
            <th>线路名</th>
            <th>操作</th>
        </tr>
        <c:forEach var="busLine" items="${busLines}">
            <tr>
                <td>${busLine.id}</td>
                <td>${busLine.lineName}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/BusLineController/info?id=${busLine.id}">查看详情</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>
<c:if test="${empty busLines}">
    <p>找不到此线路.</p>
</c:if>

<h2>站点</h2>
<c:if test="${not empty busStops}">
    <table border="1">
        <tr>
            <th>ID</th>
            <th>站点名</th>
        </tr>
        <c:forEach var="busStop" items="${busStops}">
            <tr>
                <td>${busStop.id}</td>
                <td>${busStop.stopName}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>
<c:if test="${empty busStops}">
    <p>找不到此站点.</p>
</c:if>


<br>
<a href="${pageContext.request.contextPath}/jsp/index.jsp">返回主页</a>
<%@ include file="footer.jsp" %>
</body>
</html>
