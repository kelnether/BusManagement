<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>添加新线路</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/newStyle.css">

    <script type="text/javascript">
        function addStopField() {
            var stopsDiv = document.getElementById('stops');
            var newStopDiv = document.createElement('div');
            var newStopInput = document.createElement('input');
            newStopInput.type = 'text';
            newStopInput.name = 'stops';
            newStopInput.placeholder = 'Stop Name';
            newStopDiv.appendChild(newStopInput);
            stopsDiv.appendChild(newStopDiv);
        }

        function addDistanceField() {
            var distancesDiv = document.getElementById('distances');
            var newDistanceDiv = document.createElement('div');
            var newStartStopInput = document.createElement('input');
            newStartStopInput.type = 'text';
            newStartStopInput.name = 'startStop';
            newStartStopInput.placeholder = '起始站点';
            var newEndStopInput = document.createElement('input');
            newEndStopInput.type = 'text';
            newEndStopInput.name = 'endStop';
            newEndStopInput.placeholder = '结束站点';
            var newDistanceInput = document.createElement('input');
            newDistanceInput.type = 'text';
            newDistanceInput.name = 'distance';
            newDistanceInput.placeholder = '两站点间距离';
            newDistanceDiv.appendChild(newStartStopInput);
            newDistanceDiv.appendChild(newEndStopInput);
            newDistanceDiv.appendChild(newDistanceInput);
            distancesDiv.appendChild(newDistanceDiv);
        }
    </script>
</head>
<body>
<%@ include file="header.jsp" %>
<h1>添加新线路</h1>
<form action="${pageContext.request.contextPath}/BusLineController/insert" method="post">
    <label for="lineName">线路名:</label>
    <input type="text" id="lineName" name="lineName" required><br><br>

    <div class="form-group">
        <label for="companyId">运营企业:</label>
        <select id="companyId" name="companyId" class="form-control" required>
            <c:forEach var="company" items="${listCompanies}">
                <option value="${company.id}">${company.name}</option>
            </c:forEach>
        </select>
    </div>

    <div id="stops">
        <label>站点:</label><br>
        <div>
            <input type="text" name="stops" placeholder="站点名" required><br><br>
        </div>
    </div>
    <button type="button" onclick="addStopField()">添加站点</button><br><br>

    <div id="distances">
        <label>据下一站的距离:</label><br>
        <div>
            <input type="text" name="startStop" placeholder="起始站点" required>
            <input type="text" name="endStop" placeholder="结束站点" required>
            <input type="text" name="distance" placeholder="两站点间距离" required><br><br>
        </div>
    </div>
    <button type="button" onclick="addDistanceField()">添加距离</button><br><br>

    <input type="submit" value="保存">
</form>
<br>
<a href="${pageContext.request.contextPath}/jsp/index.jsp">返回主页</a>
<%@ include file="footer.jsp" %>
</body>
</html>
