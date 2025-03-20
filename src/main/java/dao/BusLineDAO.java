package dao;

import model.BusLine;
import model.BusStop;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BusLineDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/Bus";
    private String jdbcUsername = "root";
    private String jdbcPassword = "123456";
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

    public void saveBusLine(BusLine busLine) throws SQLException {
        connect();

        String sql = "INSERT INTO bus_line (line_name, company_id) VALUES (?, ?)";
        PreparedStatement statement = jdbcConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, busLine.getLineName());
        statement.setInt(2, busLine.getCompanyId());

        int affectedRows = statement.executeUpdate();

        if (affectedRows > 0) {
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                busLine.setId(generatedKeys.getInt(1));
            }
            generatedKeys.close();
        }

        statement.close();
        disconnect();
    }

    public void insertBusLine(BusLine busLine, List<Integer> stopIds) throws SQLException {
        String sql = "INSERT INTO bus_line (line_name, company_id) VALUES (?, ?)";
        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, busLine.getLineName());
        statement.setInt(2, busLine.getCompanyId());

        statement.executeUpdate();

        ResultSet generatedKeys = statement.getGeneratedKeys();
        if (generatedKeys.next()) {
            int lineId = generatedKeys.getInt(1);
            busLine.setId(lineId);

            // 插入 bus_stop_line 表
            for (int stopId : stopIds) {
                insertBusStopLine(lineId, stopId);
            }
        }

        statement.close();
        disconnect();
    }

    private void insertBusStopLine(int lineId, int stopId) throws SQLException {
        String sql = "INSERT INTO bus_stop_line (line_id, stop_id) VALUES (?, ?)";
        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, lineId);
        statement.setInt(2, stopId);

        statement.executeUpdate();
        statement.close();
        disconnect();
    }

    public List<BusLine> listAllBusLines() throws SQLException {
        List<BusLine> listBusLine = new ArrayList<>();

        String sql = "SELECT b.*, c.name AS company_name FROM bus_line b LEFT JOIN companies c ON b.company_id = c.id";

        connect();

        Statement statement = jdbcConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String lineName = resultSet.getString("line_name");
            int companyId = resultSet.getInt("company_id");
            String companyName = resultSet.getString("company_name");

            BusLine busLine = new BusLine(id, lineName, companyId);
            busLine.setCompanyName(companyName);

            listBusLine.add(busLine);
        }

        resultSet.close();
        statement.close();

        disconnect();

        return listBusLine;
    }

    public BusLine getBusLine(int id) throws SQLException {
        BusLine busLine = null;
        String sql = "SELECT b.*, c.name AS company_name FROM bus_line b LEFT JOIN companies c ON b.company_id = c.id WHERE b.id = ?";

        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            String lineName = resultSet.getString("line_name");
            int companyId = resultSet.getInt("company_id");
            String companyName = resultSet.getString("company_name");

            busLine = new BusLine(id, lineName, companyId);
            busLine.setCompanyName(companyName);

            // 获取并设置线路的站点列表
            List<BusStop> stops = getBusStopsByLineId(id);
            busLine.setStops(stops);
        }

        resultSet.close();
        statement.close();

        disconnect();

        return busLine;
    }

    private List<BusStop> getBusStopsByLineId(int lineId) throws SQLException {
        List<BusStop> stops = new ArrayList<>();
        String sql = "SELECT s.*, l.sequence FROM bus_stop s " +
                "JOIN bus_stop_line l ON s.id = l.stop_id " +
                "WHERE l.line_id = ? ORDER BY l.sequence";

        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, lineId);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String stopName = resultSet.getString("stop_name");
            int sequence = resultSet.getInt("sequence");

            BusStop stop = new BusStop(id, stopName, sequence);
            stops.add(stop);
        }

        resultSet.close();
        statement.close();

        disconnect();

        return stops;
    }

    public void updateBusLine(BusLine busLine) throws SQLException {
        String sql = "UPDATE bus_line SET line_name = ?, company_id = ? WHERE id = ?";
        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, busLine.getLineName());
        statement.setInt(2, busLine.getCompanyId());
        statement.setInt(3, busLine.getId());

        statement.executeUpdate();
        statement.close();
        disconnect();
    }

    private void deleteBusStopLines(int lineId) throws SQLException {
        String sql = "DELETE FROM bus_stop_line WHERE line_id = ?";
        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, lineId);

        statement.executeUpdate();
        statement.close();
        disconnect();
    }

    public void deleteBusLine(int id) throws SQLException {
        String sql = "DELETE FROM bus_line WHERE id = ?";
        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, id);

        statement.executeUpdate();
        statement.close();
        disconnect();
    }

    public List<BusLine> searchBusLines(String query) throws SQLException {
        List<BusLine> busLines = new ArrayList<>();

        String sql = "SELECT b.*, c.name AS company_name FROM bus_line b LEFT JOIN companies c ON b.company_id = c.id WHERE b.line_name LIKE ?";
        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, "%" + query + "%");

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String lineName = resultSet.getString("line_name");
            int companyId = resultSet.getInt("company_id");
            String companyName = resultSet.getString("company_name");

            BusLine busLine = new BusLine(id, lineName, companyId);
            busLine.setCompanyName(companyName);

            busLines.add(busLine);
        }

        resultSet.close();
        statement.close();
        disconnect();

        return busLines;
    }
}
