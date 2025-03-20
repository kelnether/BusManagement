<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>换乘路线</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/newStyle.css">
</head>
<body>
<%@ include file="header.jsp" %>
<h1>换乘路线</h1>
<table border="1">
    <thead>
    <tr>
        <th>路线</th>
        <th>总距离 (km)</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="routeEntry" items="${routesWithDistances}">
        <tr>
            <td>
                <c:forEach var="transferRoute" items="${routeEntry.key}">
                    <strong>${transferRoute.busLine.lineName}</strong>:
                    <c:forEach var="stop" items="${transferRoute.stops}">
                        ${stop.stopName} <c:if test="${!stop.stopName.equals(transferRoute.stops[transferRoute.stops.size() - 1].stopName)}">-></c:if>
                    </c:forEach>
                    <br>
                </c:forEach>
            </td>
            <td>${routeEntry.value}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<br>
<a href="${pageContext.request.contextPath}/jsp/index.jsp">返回主页</a>
<%@ include file="footer.jsp" %>
</body>
</html>
