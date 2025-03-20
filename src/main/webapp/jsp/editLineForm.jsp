<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>编辑线路</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/newStyle.css">

    <script type="text/javascript">
        function addStopField() {
            var stopsDiv = document.getElementById('stops');
            var newStopDiv = document.createElement('div');
            newStopDiv.className = "stop-distance-group";

            var newStopInput = document.createElement('input');
            newStopInput.type = 'text';
            newStopInput.name = 'stops';
            newStopInput.placeholder = 'Stop Name';
            newStopInput.required = true;
            newStopDiv.appendChild(newStopInput);

            var newDistanceInput = document.createElement('input');
            newDistanceInput.type = 'text';
            newDistanceInput.name = 'distances';
            newDistanceInput.placeholder = 'Distance to Previous Stop';
            newDistanceInput.required = true;
            newStopDiv.appendChild(newDistanceInput);

            stopsDiv.appendChild(newStopDiv);
        }
    </script>
    <style>
        .stop-distance-group {
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
<%@ include file="header.jsp" %>
<h1>编辑公交线路</h1>
<form action="${pageContext.request.contextPath}/BusLineController/update" method="post">
    <input type="hidden" name="id" value="${busLine.id}">
    <label for="lineName">Line Name:</label>
    <input type="text" id="lineName" name="lineName" value="${busLine.lineName}" required><br><br>

    <label>站点:</label><br>
    <div id="stops">
        <c:forEach var="stop" items="${busLine.stops}">
            <div class="stop-distance-group">
                <input type="text" name="stops" value="${stop.stopName}" required>
                <c:if test="${!stop.equals(busLine.stops[busLine.stops.size() - 1])}">
                    <input type="text" name="distances" value="${stop.distanceToNextStop}" placeholder="到下一站的距离（km）" required>
                </c:if>
                <br><br>
            </div>
        </c:forEach>
    </div>
    <button type="button" onclick="addStopField()">添加站点</button><br><br>

    <input type="submit" value="保存">
</form>
<br>
<a href="${pageContext.request.contextPath}/jsp/index.jsp">返回主页</a>
<%@ include file="footer.jsp" %>
</body>
</html>
