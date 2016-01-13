package com.pandora.servlet;

import java.io.IOException;
import java.io.PrintWriter;
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
 * Servlet implementation class getAllStations
 */
@WebServlet("/getAllStations")
public class getAllStations extends HttpServlet {
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
		JSONArray data = pullFromDB();
		String output = data.toString();
		response.setContentLength(output.length());
	    //And write the string to output.
		//response.getOutputStream().write(output.getBytes());
		//response.getOutputStream().flush();
		//response.getOutputStream().close();
		PrintWriter out= response.getWriter();
		out.print(data);
		
	
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		JSONArray data = pullFromDB();
		String output = data.toString();
		response.setContentLength(output.length());
	    //And write the string to output.
		//response.getOutputStream().write(output.getBytes());
		//response.getOutputStream().flush();
		//response.getOutputStream().close();
		PrintWriter out= response.getWriter();
		out.print(data);
		
	}
	
	public JSONArray pullFromDB()
	  {
		  try{
				//System.out.println("setting class");
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Connecting to database...");
			Connection conn = DriverManager.getConnection(DB_URL,user,pass);
			System.out.println("Connected!!!");
			//System.out.println(conn.toString());
			System.out.println("Creating statement...");
			
			System.out.println("getting all stations");
			PreparedStatement stmt2 = conn.prepareStatement("SELECT * FROM Stations");
			ResultSet result = stmt2.executeQuery();	
			JSONArray json = convertToJSON(result);
			stmt2.close();
			return json;
			
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
	
	public static JSONArray convertToJSON(ResultSet resultSet)
            throws Exception {
        JSONArray jsonArray = new JSONArray();
        while (resultSet.next()) {
            int total_rows = resultSet.getMetaData().getColumnCount();
            JSONObject obj = new JSONObject();
            for (int i = 0; i < total_rows; i++) {
                obj.put(resultSet.getMetaData().getColumnLabel(i + 1)
                        .toLowerCase(), resultSet.getObject(i + 1));
            }
            jsonArray.put(obj);
        }
        return jsonArray;
    }

}
