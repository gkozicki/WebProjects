package com.pandora.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class getUserStations
 */
@WebServlet("/getUserStations")
public class getUserStations extends HttpServlet {
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
		System.out.println("GETTING ALL STATIONS");
	
		ArrayList<String> allStations = getPlaylistNames();
		ArrayList<String> publicStations = getPublicStations();
		for(int i=0;i<publicStations.size();i++){
			allStations.add(publicStations.get(i));
		}
		JSONArray jArray = new JSONArray();

		 for(int z=0;z<allStations.size();z++){
			 JSONObject obj = new JSONObject();
			 try {
				obj.put("name", allStations.get(z));
				System.out.println("JSONObject: "+obj);
				 jArray.put(obj);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		 }
		String output = jArray.toString();
		response.setContentLength(output.length());
		System.out.println("songs :"+jArray);
		PrintWriter out = response.getWriter();
		
		out.print(jArray);
	
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ArrayList<String> allStations = getPlaylistNames();
		ArrayList<String> publicStations = getPublicStations();
		for(int i=0;i<publicStations.size();i++){
			allStations.add(publicStations.get(i));
		}
		JSONArray jArray = new JSONArray();

		 for(int z=0;z<allStations.size();z++){
			 JSONObject obj = new JSONObject();
			 try {
				obj.put("name", allStations.get(z));
				System.out.println("JSONObject: "+obj);
				 jArray.put(obj);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		 }
		String output = jArray.toString();
		response.setContentLength(output.length());
		System.out.println("songs :"+jArray);
		PrintWriter out = response.getWriter();
		
		out.print(output);
		
	
	}

	
	public ArrayList<String> getPlaylistNames()
	  {
		  try{
				//System.out.println("setting class");
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Connecting to database...");
			Connection conn = DriverManager.getConnection(DB_URL,user,pass);
			System.out.println("Connected!!!");
			//System.out.println(conn.toString());
			System.out.println("Creating statement...");
			
			System.out.println("getting all playlists");
			PreparedStatement stmt2 = conn.prepareStatement("SELECT playlistName FROM playlists WHERE userName=?");
			stmt2.setString(1,"user1");
			ResultSet result = stmt2.executeQuery();
			ArrayList<String> plNames = new ArrayList<String>();
			while(result.next()){
				plNames.add(result.getString("playlistName"));
			}
			
			//JSONArray json = convertToJSON(result);
			stmt2.close();
			conn.close();
			//System.out.println("#######"+json);

			return plNames;
			
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
	
	public ArrayList<String> getPublicStations()
	  {
		  try{
				//System.out.println("setting class");
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Connecting to database...");
			Connection conn = DriverManager.getConnection(DB_URL,user,pass);
			System.out.println("Connected!!!");
			//System.out.println(conn.toString());
			System.out.println("Creating statement...");
			
			System.out.println("getting all playlists");
			PreparedStatement stmt2 = conn.prepareStatement("SELECT name FROM publicStations");
			//stmt2.setString(1,"user1");
			ResultSet result = stmt2.executeQuery();
			ArrayList<String> plNames = new ArrayList<String>();
			while(result.next()){
				plNames.add(result.getString("name"));
			}
			
			//JSONArray json = convertToJSON(result);
			stmt2.close();
			conn.close();
			//System.out.println("#######"+json);

			return plNames;
			
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