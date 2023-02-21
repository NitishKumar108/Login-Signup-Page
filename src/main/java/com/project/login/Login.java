package com.project.login;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class Login extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String uemail = req.getParameter("username");
		String upassword = req.getParameter("password");
		
		HttpSession session = req.getSession();
		RequestDispatcher dispatcher = null;
		
		//validation
		if(uemail == null || uemail.equals("")) {
			req.setAttribute("status", "Invalid Email");
			dispatcher = req.getRequestDispatcher("login.jsp");
			dispatcher.forward(req, resp);
		}
		if(upassword == null || upassword.equals("")) {
			req.setAttribute("status", "Invalid Password");
			dispatcher = req.getRequestDispatcher("login.jsp");
			dispatcher.forward(req, resp);
		}
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/company";
			String userName = "root";
			String userPassword = "root";
			Connection con = DriverManager.getConnection(url, userName, userPassword);
			
			PreparedStatement pst = con.prepareStatement("SELECT * FROM project WHERE uemail=? and upassword=?");
			pst.setString(1, uemail);
			pst.setString(2, upassword);
			
			ResultSet result = pst.executeQuery();
			if(result.next()) {
				session.setAttribute("name", result.getString("uname"));
				dispatcher = req.getRequestDispatcher("index.jsp");
			}else {
				req.setAttribute("status", "failed");
				dispatcher = req.getRequestDispatcher("login.jsp");
			}
			dispatcher.forward(req, resp);
			con.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
