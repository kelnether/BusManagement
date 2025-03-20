<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>绑定运营企业</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/newStyle.css">
</head>
<body>
<%@ include file="header.jsp" %>
<h2>绑定运营企业</h2>
<form action="${pageContext.request.contextPath}/CompanyController/assignCompanyToLine" method="post">
    <label for="lineId">选择线路:</label>
    <select id="lineId" name="lineId" required>
        <c:forEach var="line" items="${listBusLine}">
            <option value="${line.id}">${line.lineName}</option>
        </c:forEach>
    </select><br>
    <label for="companyId">选择运营企业:</label>
    <select id="companyId" name="companyId" required>
        <c:forEach var="company" items="${listCompany}">
            <option value="${company.id}">${company.name}</option>
        </c:forEach>
    </select><br>
    <input type="submit" value="绑定">
</form>
<br>
<a href="${pageContext.request.contextPath}/CompanyController/list">返回公司列表</a>
<br>
<a href="${pageContext.request.contextPath}/jsp/index.jsp">返回主页</a>
<%@ include file="footer.jsp" %>
</body>
</html>
