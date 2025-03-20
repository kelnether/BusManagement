package dao;

import model.BusStop;
import model.BusLine;
import model.BusStopDistance;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BusStopDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/Bus";
    private String jdbcUsername = "root"; // 使用你的MySQL用户名
    private String jdbcPassword = "123456"; // 使用你的MySQL密码
    private Connection jdbcConnection;

    protected void connect() throws SQLException {
        if (jdbcConnection == null || jdbcConnection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        }
    }

    protected void disconnect() throws SQLException {
        if (jdbcConnection != null && !jdbcConnection.isClosed()) {
            jdbcConnection.close();
        }
    }

    public List<BusStop> listAllBusStops() throws SQLException {
        List<BusStop> listBusStop = new ArrayList<>();

        String sql = "SELECT * FROM bus_stop";

        connect();

        Statement statement = jdbcConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String stopName = resultSet.getString("stop_name");

            BusStop busStop = new BusStop();
            busStop.setId(id);
            busStop.setStopName(stopName);

            listBusStop.add(busStop);
        }

        resultSet.close();
        statement.close();

        disconnect();

        return listBusStop;
    }

    public BusStop getBusStopByName(String stopName) throws SQLException {
        BusStop stop = null;
        String sql = "SELECT * FROM bus_stop WHERE stop_name = ?";

        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, stopName);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            stop = new BusStop();
            stop.setId(resultSet.getInt("id"));
            stop.setStopName(resultSet.getString("stop_name"));
        }

        resultSet.close();
        statement.close();

        return stop;
    }

    public void insertBusStop(BusStop busStop) throws SQLException {
        String sql = "INSERT INTO bus_stop (stop_name) VALUES (?)";
        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, busStop.getStopName());

        statement.executeUpdate();

        ResultSet generatedKeys = statement.getGeneratedKeys();
        if (generatedKeys.next()) {
            busStop.setId(generatedKeys.getInt(1));
        }

        statement.close();
        disconnect();
    }

    public void insertBusStopLine(int lineId, int stopId, int sequence) throws SQLException {
        connect();

        String sql = "INSERT INTO bus_stop_line (line_id, stop_id, sequence) VALUES (?, ?, ?)";
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, lineId);
        statement.setInt(2, stopId);
        statement.setInt(3, sequence);

        statement.executeUpdate();
        statement.close();
        disconnect();
    }


    public List<BusLine> getBusLinesByStop(int stopId) throws SQLException {
        List<BusLine> busLines = new ArrayList<>();
        String sql = "SELECT bl.id, bl.line_name FROM bus_line bl " +
                "JOIN bus_stop_line bsl ON bl.id = bsl.line_id " +
                "WHERE bsl.stop_id = ?";

        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, stopId);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String lineName = resultSet.getString("line_name");

            BusLine busLine = new BusLine();
            busLine.setId(id);
            busLine.setLineName(lineName);

            busLines.add(busLine);
        }

        resultSet.close();
        statement.close();

        disconnect();

        return busLines;
    }

    public List<BusStop> getBusStopsByLine(int lineId) throws SQLException {
        List<BusStop> stops = new ArrayList<>();
        String query = "SELECT bs.id, bs.stop_name FROM bus_stop bs " +
                "JOIN bus_stop_line bsl ON bs.id = bsl.stop_id " +
                "WHERE bsl.line_id = ? ORDER BY bsl.sequence";
        connect();
        PreparedStatement statement = jdbcConnection.prepareStatement(query);
        statement.setInt(1, lineId);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            BusStop stop = new BusStop();
            stop.setId(resultSet.getInt("id"));
            stop.setStopName(resultSet.getString("stop_name"));
            stops.add(stop);
        }
        resultSet.close();
        statement.close();
        disconnect();
        return stops;
    }



    public void insertBusStopDistance(BusStopDistance distance) throws SQLException {
        String sql = "INSERT INTO bus_stop_distance (start_stop_id, end_stop_id, distance) VALUES (?, ?, ?)";
        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, distance.getStartStopId());
        statement.setInt(2, distance.getEndStopId());
        statement.setDouble(3, distance.getDistance());

        statement.executeUpdate();
        statement.close();
        disconnect();
    }

    public void insertBusStopDistance(int startStopId, int endStopId, double distance) throws SQLException {
        String sql = "INSERT INTO bus_stop_distance (start_stop_id, end_stop_id, distance) VALUES (?, ?, ?)";
        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, startStopId);
        statement.setInt(2, endStopId);
        statement.setDouble(3, distance);

        statement.executeUpdate();
        statement.close();
        disconnect();
    }


    public List<BusStopDistance> listAllBusStopDistances() throws SQLException {
        List<BusStopDistance> listBusStopDistance = new ArrayList<>();
        String sql = "SELECT * FROM bus_stop_distance";

        connect();

        Statement statement = jdbcConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            int startStopId = resultSet.getInt("start_stop_id");
            int endStopId = resultSet.getInt("end_stop_id");
            double distance = resultSet.getDouble("distance");

            BusStopDistance busStopDistance = new BusStopDistance(id, startStopId, endStopId, distance);
            listBusStopDistance.add(busStopDistance);
        }

        resultSet.close();
        statement.close();
        disconnect();

        return listBusStopDistance;
    }

    public BusStopDistance getBusStopDistance(int startStopId, int endStopId) throws SQLException {
        BusStopDistance busStopDistance = null;
        String sql = "SELECT * FROM bus_stop_distance WHERE start_stop_id = ? AND end_stop_id = ?";

        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, startStopId);
        statement.setInt(2, endStopId);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            int id = resultSet.getInt("id");
            double distance = resultSet.getDouble("distance");
            busStopDistance = new BusStopDistance(id, startStopId, endStopId, distance);
        } else {
            sql = "SELECT * FROM bus_stop_distance WHERE start_stop_id = ? AND end_stop_id = ?";
            statement = jdbcConnection.prepareStatement(sql);
            statement.setInt(1, endStopId);
            statement.setInt(2, startStopId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                double distance = resultSet.getDouble("distance");
                busStopDistance = new BusStopDistance(id, endStopId, startStopId, distance);
            }
        }

        System.out.println("Distance between stops " + startStopId + " and " + endStopId + ": " + (busStopDistance != null ? busStopDistance.getDistance() : "Not found")); // 添加调试信息

        resultSet.close();
        statement.close();
        disconnect();

        return busStopDistance;
    }


    public void updateBusStopDistance(BusStopDistance distance) {
    }

    public List<BusStop> getBusStopsWithDistancesByLine(int lineId) throws SQLException {
        List<BusStop> stops = new ArrayList<>();
        String sql = "SELECT bs.id, bs.stop_name, bsl.sequence, bsd.distance " +
                "FROM bus_stop bs " +
                "JOIN bus_stop_line bsl ON bs.id = bsl.stop_id " +
                "LEFT JOIN bus_stop_distance bsd ON bs.id = bsd.start_stop_id " +
                "WHERE bsl.line_id = ? " +
                "ORDER BY bsl.sequence"; // 使用sequence列来确定站点顺序

        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, lineId);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String stopName = resultSet.getString("stop_name");
            Double distance = resultSet.getObject("distance", Double.class);

            BusStop stop = new BusStop();
            stop.setId(id);
            stop.setStopName(stopName);
            stop.setDistanceToNextStop(distance);

            stops.add(stop);
        }

        resultSet.close();
        statement.close();
        disconnect();

        // Ensure each stop has the correct distance to the next stop
        for (int i = 0; i < stops.size() - 1; i++) {
            BusStop currentStop = stops.get(i);
            BusStop nextStop = stops.get(i + 1);
            Double distance = getDistanceBetweenStops(currentStop.getId(), nextStop.getId());
            currentStop.setDistanceToNextStop(distance);
        }

        // Remove the last stop's distance as it's not needed
        if (!stops.isEmpty()) {
            stops.get(stops.size() - 1).setDistanceToNextStop(null);
        }

        return stops;
    }

    public Double getDistanceBetweenStops(int startStopId, int endStopId) throws SQLException {
        String query = "SELECT distance FROM bus_stop_distance WHERE (start_stop_id = ? AND end_stop_id = ?) OR (start_stop_id = ? AND end_stop_id = ?)";
        Double distance = null;
        connect();
        PreparedStatement statement = jdbcConnection.prepareStatement(query);
        statement.setInt(1, startStopId);
        statement.setInt(2, endStopId);
        statement.setInt(3, endStopId);  // 反向检查
        statement.setInt(4, startStopId);  // 反向检查
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            distance = resultSet.getDouble("distance");
        }
        resultSet.close();
        statement.close();
        disconnect();
        return distance;
    }


    public void deleteBusStopLinesByLineId(int lineId) throws SQLException {
        connect();

        String sql = "DELETE FROM bus_stop_line WHERE line_id = ?";
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, lineId);

        statement.executeUpdate();
        statement.close();
        disconnect();
    }

    public List<BusStop> searchBusStops(String query) throws SQLException {
        List<BusStop> busStops = new ArrayList<>();

        String sql = "SELECT * FROM bus_stop WHERE stop_name LIKE ?";
        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, "%" + query + "%");

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String stopName = resultSet.getString("stop_name");

            BusStop busStop = new BusStop();
            busStop.setId(id);
            busStop.setStopName(stopName);

            busStops.add(busStop);
        }

        resultSet.close();
        statement.close();
        disconnect();

        return busStops;
    }

    public List<BusStop> getBusStopsByLineId(int lineId) throws SQLException {
        List<BusStop> stops = new ArrayList<>();
        String sql = "SELECT s.id, s.stop_name, d.distance AS distanceToNextStop FROM bus_stop s " +
                "JOIN bus_stop_line l ON s.id = l.stop_id " +
                "LEFT JOIN bus_stop_distance d ON s.id = d.start_stop_id AND d.end_stop_id = " +
                "(SELECT stop_id FROM bus_stop_line WHERE line_id = l.line_id AND sequence = l.sequence + 1) " +
                "WHERE l.line_id = ? ORDER BY l.sequence";

        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, lineId);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String stopName = resultSet.getString("stop_name");
            Double distanceToNextStop = resultSet.getDouble("distanceToNextStop");

            BusStop stop = new BusStop(id, stopName);
            stop.setDistanceToNextStop(distanceToNextStop);

            stops.add(stop);
        }

        resultSet.close();
        statement.close();
        disconnect();

        return stops;
    }


}

