package com.pandora.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Servlet implementation class loginAuthentication
 */
@WebServlet("/loginAuthentication")
public class loginAuthentication extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	static final String DB_URL = "jdbc:mysql://127.0.0.1/Pandora";
	static final String user = "root";
	static final String pass = "";

	public void init() throws ServletException
	  {
	      // Do required initialization
	    //  message = "Data Saved!";
	    //  message2 = "Redirecting...";
	  }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out= response.getWriter();
		out.print("hello");
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Connected to login Auth");
	
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String storedPassword = getPassword(username);
		if(storedPassword.equals(password))
		{
			response.setContentLength(1);
			PrintWriter out= response.getWriter();
			out.print(1);
		}
		else{
			response.setContentLength(1);
			PrintWriter out= response.getWriter();
			out.print(0);
		}
	
		
	}
	
	public String getPassword(String email)
	  {
		  try{
				//System.out.println("setting class");
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Connecting to database...");
			Connection conn = DriverManager.getConnection(DB_URL,user,pass);
			System.out.println("Connected!!!");
			//System.out.println(conn.toString());
			System.out.println("Creating statement...");
			
			System.out.println("checking username and password");
			PreparedStatement stmt2 = conn.prepareStatement("SELECT password FROM users WHERE email=?");
			stmt2.setString(1, email);
			ResultSet result = stmt2.executeQuery();
			ArrayList<String> pswds = new ArrayList<String>();
			while(result.next()){
				pswds.add(result.getString("password"));
			}
			stmt2.close();
			System.out.println("password is: "+pswds.get(0));
			return pswds.get(0);
			
		  }
		  catch(SQLException e){
				System.out.println("cannot connect");
				e.printStackTrace();
			}
			catch(Exception e)
			{
				System.out.println("Driver not found");
				e.printStackTrace();
			}
		  return null;
	  }

}