<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/newStyle.css">
</head>
<body>
<%@ include file="header.jsp" %>
<div class="container">
    <h1 class="text-center">注册新用户</h1>
    <form action="${pageContext.request.contextPath}/RegisterController" method="post">
        <div class="form-group">
            <label for="username">用户名:</label>
            <input type="text" id="username" name="username" class="form-control" required>
        </div>
        <div class="form-group">
            <label for="password">密码:</label>
            <input type="password" id="password" name="password" class="form-control" required>
        </div>
        <button type="submit" class="btn btn-primary">注册</button>
    </form>
    <br>
    <a href="${pageContext.request.contextPath}/jsp/login.jsp" class="btn btn-link">返回登录页面</a>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>
