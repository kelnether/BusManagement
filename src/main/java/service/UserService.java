package service;

import dao.UserDAO;
import jakarta.servlet.ServletException;
import model.User;
import util.DBConnection;

import java.sql.SQLException;

public class UserService {
    private UserDAO userDAO;

    public UserService() {
        try {
            this.userDAO = new UserDAO(DBConnection.getConnection()); // 这里获取连接可能抛异常
        } catch (ServletException e) {
            throw new RuntimeException("Unable to connect to database", e);
        }
    }


    public boolean validateUser(User user) throws SQLException {
        return userDAO.validate(user);
    }

    public void registerUser(User user) throws SQLException {
        userDAO.saveUser(user);
    }
}
