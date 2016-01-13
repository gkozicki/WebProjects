package com.pandora.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class addStation
 */
@WebServlet("/addStation")
public class addStation extends HttpServlet {
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
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {   
	  
  }
  
  public void doPost(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException
  {
	  String[] station = new String[8];
	  station[0]  = request.getParameter("station");
	System.out.println("station name: "+station[0]);
	  String sucess = saveToDB(station);
	  PrintWriter out= response.getWriter();
	  out.print(sucess);
	  out.flush();
	  out.close();
	 // request.getRequestDispatcher("Employee_saved.jsp").forward(request, response);

      
  }
  
  public void destroy()
  {
      // do nothing.
  }
  
  private String saveToDB(String[] answers)
  {
	  try{
			//System.out.println("setting class");
		Class.forName("com.mysql.jdbc.Driver");
		System.out.println("Connecting to database...");
		Connection conn = DriverManager.getConnection(DB_URL,user,pass);
		System.out.println("Connected!!!");
		//System.out.println(conn.toString());
		System.out.println("Creating statement...");
		
		System.out.println("Adding station");
		PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO Stations VALUES(?,?,?,?)");
		stmt2.setString(1,answers[0] );
		stmt2.setString(2,"band1" );
		stmt2.setString(3,"band2" );
		stmt2.setString(4,"band3" );

		//stmt2.setString();
		
		stmt2.executeUpdate();
		stmt2.close();
		return "sucess";
	  }
	  catch(SQLException e){
			System.out.println("cannot connect");
			e.printStackTrace();
			return "error";
		}
		catch(Exception e)
		{
			System.out.println("Driver not found");
			e.printStackTrace();
			return "error";
		}
	 
  }
}
