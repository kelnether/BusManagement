<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Search Bus Lines and Stops</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/newStyle.css">

</head>
<body>
<%@ include file="header.jsp" %>
<h1>Search Bus Lines and Stops</h1>
<form action="${pageContext.request.contextPath}/BusLineController/search" method="get">
    <label for="query">输入站点名或线路名:</label>
    <input type="text" id="query" name="query" required>
    <input type="submit" value="Search">
</form>
<br>
<a href="${pageContext.request.contextPath}/jsp/index.jsp">返回主页</a>
<%@ include file="footer.jsp" %>
</body>
</html>
