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
import java.sql.SQLException;


//@WebServlet("/registerServlet")
public class registerServlet extends HttpServlet {
	public  void doPost(HttpServletRequest req,HttpServletResponse resp) {
		String name=req.getParameter("username");
		String email=req.getParameter("email");
		String pasword=req.getParameter("password");
		String url="jdbc:mysql://localhost:3306/yugandhar";
		String un="root";
		String pw="root123";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection(url, un, pw);
			String quary="insert into user(name,email,password) values(?,?,?)";
			PreparedStatement ps=con.prepareStatement(quary);
			ps.setString(1,name);
			ps.setString(2,email);
			ps.setString(3,pasword);
			int res=ps.executeUpdate();
			if(res>0) {
				resp.sendRedirect("login.html");
			}
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
		}
	}
}
