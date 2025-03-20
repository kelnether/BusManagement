<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>线路信息</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/newStyle.css">
    <style>
        table {
            width: 60%;
            border-collapse: collapse;
            margin: 20px 0;
        }
        table, th, td {
            border: 1px solid black;
        }
        th, td {
            padding: 10px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
<%@ include file="header.jsp" %>
<h2>线路信息</h2>
<p>线路名称: ${busLine.lineName}</p>
<p>运营企业: <a href="${company.website}" target="_blank">${company.name}</a></p> <!-- 修改这一行，添加链接 -->
<h3>站点:</h3>
<table>
    <thead>
    <tr>
        <th>站点名称</th>
        <th>到下一个站点的距离 (km)</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="stop" items="${busLine.stops}">
        <tr>
            <td>${stop.stopName}</td>
            <td>
                <c:choose>
                    <c:when test="${stop.distanceToNextStop != null}">
                        ${stop.distanceToNextStop}
                    </c:when>
                    <c:otherwise>
                        -
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<br>
<a href="${pageContext.request.contextPath}/BusLineController/list">返回</a>
<%@ include file="footer.jsp" %>
</body>
</html>
