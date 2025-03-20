package controller;

import model.User;
import service.UserService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

public class RegisterController extends HttpServlet {
    private UserService userService;

    @Override
    public void init() {
        try {
            this.userService = new UserService(); // 确保UserService初始化中不抛异常
        } catch (Exception e) {
            try {
                throw new ServletException("Failed to initialize RegisterController", e);
            } catch (ServletException ex) {
                throw new RuntimeException(ex);
            }
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        try {
            userService.registerUser(user);
            response.sendRedirect("jsp/login.jsp"); // After registration, redirect to the login page
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Registration failed: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/register.jsp");
            dispatcher.forward(request, response);
        }
    }
}
