<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    model.User user = (model.User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
        return;
    }
    String username = user.getUsername();
%>

<html>
<head>
    <title>公交管理系统</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/newStyle.css">
</head>
<body>
<%@ include file="header.jsp" %>

<div class="container mt-4">
    <h1 class="text-center mb-4">欢迎使用公交管理系统</h1>

    <div class="row">
        <div class="col-md-6">
            <div class="card mb-4">
                <div class="card-header">
                    <h2>换乘方案查询</h2>
                </div>
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/BusLineController/transfer" method="post">
                        <div class="form-group">
                            <label for="startStop">出发地:</label>
                            <input type="text" id="startStop" name="startStopName" class="form-control" required list="stopNames">
                        </div>
                        <div class="form-group">
                            <label for="endStop">目的地:</label>
                            <input type="text" id="endStop" name="endStopName" class="form-control" required list="stopNames">
                        </div>
                        <datalist id="stopNames">
                            <c:forEach var="stop" items="${listBusStop}">
                                <option value="${stop.stopName}">${stop.stopName}</option>
                            </c:forEach>
                        </datalist>
                        <button type="submit" class="btn btn-primary">查询换乘</button>
                    </form>
                </div>
            </div>
        </div>

        <div class="col-md-6">
            <div class="card mb-4">
                <div class="card-header">
                    <h2>管理线路</h2>
                </div>
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/BusLineController/new" method="post" class="mb-2">
                        <button type="submit" class="btn btn-primary btn-block">添加新线路</button>
                    </form>
                    <form action="${pageContext.request.contextPath}/BusLineController/list" method="post">
                        <button type="submit" class="btn btn-primary btn-block">查看所有线路</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-6">
            <div class="card mb-4">
                <div class="card-header">
                    <h2>管理运营企业</h2>
                </div>
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/CompanyController/list" method="get" class="mb-2">
                        <button type="submit" class="btn btn-primary btn-block">查看所有运营企业</button>
                    </form>
                    <form action="${pageContext.request.contextPath}/CompanyController/assign" method="get">
                        <button type="submit" class="btn btn-primary btn-block">为线路绑定运营企业</button>
                    </form>
                </div>
            </div>
        </div>

        <div class="col-md-6">
            <div class="card mb-4">
                <div class="card-header">
                    <h2>站点&线路查询</h2>
                </div>
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/BusLineController/search" method="get">
                        <div class="form-group">
                            <label for="query">输入线路或站点名（支持模糊查询）:</label>
                            <input type="text" id="query" name="query" class="form-control">
                        </div>
                        <button type="submit" class="btn btn-primary btn-block">查询</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <h2 class="text-center">欢迎，用户 <%= username %>!</h2>
</div>

<%@ include file="footer.jsp" %>
</body>
</html>
