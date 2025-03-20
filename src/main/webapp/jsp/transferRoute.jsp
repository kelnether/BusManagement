<!-- transferRoutes.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Transfer Routes</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/newStyle.css">

</head>
<body>
<%@ include file="header.jsp" %>
<h1>Transfer Routes</h1>
<c:forEach var="route" items="${transferRoutes}">
    <div>
        <c:forEach var="line" items="${route}">
            ${line} <c:if test="${!line.equals(route[route.size() - 1])}"> -> </c:if>
        </c:forEach>
    </div>
</c:forEach>
<%@ include file="footer.jsp" %>
</body>
</html>
