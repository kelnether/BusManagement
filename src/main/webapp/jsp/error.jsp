<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>错误</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/newStyle.css">

</head>
<body>
<%@ include file="header.jsp" %>
<h2>错误</h2>
<p>${errorMessage}</p>
<a href="${pageContext.request.contextPath}/BusLineController/list">返回</a>
<%@ include file="footer.jsp" %>
</body>
</html>
