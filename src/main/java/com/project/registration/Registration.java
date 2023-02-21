package com.project.registration;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class Registration extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String uname = request.getParameter("name");
		String upassword = request.getParameter("password");
		String uemail = request.getParameter("email");
		String umobile = request.getParameter("contact");

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/company";
			String userName = "root";
			String userPassword = "root";
			Connection con = DriverManager.getConnection(url, userName, userPassword);
			System.out.println("Successfully connected to the database...");
			
			PreparedStatement pst = con.prepareStatement("INSERT INTO project(uname, upassword, uemail, umobile) values(?,?,?,?)");
			pst.setString(1, uname);
			pst.setString(2, upassword);
			pst.setString(3, uemail);
			pst.setString(4, umobile);
			
			int result = pst.executeUpdate();
			if(result > 0) {
				request.setAttribute("status", "success");
				System.out.println(result+" data inserted successfully.");
			}else {
				request.setAttribute("status", "failed");
			}
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("registration.jsp");
			dispatcher.forward(request, response);
			
			//validation
			if(uname == null || uname.equals("")) {
				request.setAttribute("status", "Invalid Name");
				dispatcher = request.getRequestDispatcher("login.jsp");
				dispatcher.forward(request, response);
			}
			if(upassword == null || upassword.equals("")) {
				request.setAttribute("status", "Invalid Password");
				dispatcher = request.getRequestDispatcher("login.jsp");
				dispatcher.forward(request, response);
			}
			if(uemail == null || uemail.equals("")) {
				request.setAttribute("status", "Invalid Email");
				dispatcher = request.getRequestDispatcher("login.jsp");
				dispatcher.forward(request, response);
			}
			if(umobile == null || umobile.equals("")) {
				request.setAttribute("status", "Invalid Mobile");
				dispatcher = request.getRequestDispatcher("login.jsp");
				dispatcher.forward(request, response);
			}else if(umobile.length()>10){
				request.setAttribute("status", "Length should not be greater than 10 digit");
				dispatcher = request.getRequestDispatcher("login.jsp");
				dispatcher.forward(request, response);
			}
			
			con.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
