package dao;

import model.Company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyDAO {
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

    public Company getCompanyById(int id) throws SQLException {
        Company company = null;
        String sql = "SELECT * FROM companies WHERE id = ?";

        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            String name = resultSet.getString("name");
            String website = resultSet.getString("website"); // 获取 website 列
            company = new Company(id, name, website);
        }

        resultSet.close();
        statement.close();

        disconnect();

        return company;
    }

    public List<Company> listAllCompanies() throws SQLException {
        List<Company> listCompany = new ArrayList<>();

        String sql = "SELECT * FROM companies";

        connect();

        Statement statement = jdbcConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String website = resultSet.getString("website"); // 获取 website 列

            Company company = new Company(id, name, website);
            listCompany.add(company);
        }

        resultSet.close();
        statement.close();

        disconnect();

        return listCompany;
    }

    public void saveCompany(Company company) throws SQLException {
        String sql = "INSERT INTO companies (name, website) VALUES (?, ?)"; // 修改插入语句
        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, company.getName());
        statement.setString(2, company.getWebsite()); // 设置 website

        int affectedRows = statement.executeUpdate();

        if (affectedRows > 0) {
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                company.setId(generatedKeys.getInt(1));
            }
            generatedKeys.close();
        }

        statement.close();
        disconnect();
    }

    public void updateCompany(Company company) throws SQLException {
        String sql = "UPDATE companies SET name = ?, website = ? WHERE id = ?"; // 修改更新语句
        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, company.getName());
        statement.setString(2, company.getWebsite()); // 设置 website
        statement.setInt(3, company.getId());

        statement.executeUpdate();
        statement.close();
        disconnect();
    }

    public void deleteCompany(int id) throws SQLException {
        String sql = "DELETE FROM companies WHERE id = ?";
        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, id);

        statement.executeUpdate();
        statement.close();
        disconnect();
    }
}
