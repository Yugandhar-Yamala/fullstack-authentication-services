package com.p3.Servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class loginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String email = req.getParameter("username"); // Using username field for email
        String password = req.getParameter("password");

        // Database connection details
        String url = "jdbc:mysql://localhost:3306/yugandhar";
        String un = "root";
        String pw = "root123";

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, un, pw);

            // Query to find a user with the given email and password
            String query = "SELECT * FROM user WHERE email = ? AND password = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, password);

            rs = ps.executeQuery();

            if (rs.next()) {
                // Login successful: redirect to a welcome page
                resp.sendRedirect("WelcomePage.html"); 
            } else {
                // Login failed: redirect back to the login page with an error parameter
                resp.sendRedirect("login.html?error=invalid"); 
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            // In case of a database error, show an error page or message
            resp.getWriter().println("An error occurred during login. Please try again.");
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}