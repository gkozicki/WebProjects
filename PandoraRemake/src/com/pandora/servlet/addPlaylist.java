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
 * Servlet implementation class addPlaylist
 */
@WebServlet("/addPlaylist")
public class addPlaylist extends HttpServlet {
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
	  System.out.println("~~Adding Playlist~~");
	  ArrayList<String> songs = new ArrayList<String>();
	 // JSONObject jObj    = new JSONObject();
	  String temp = request.getParameter("songs");
	  String plName = request.getParameter("playlistName");
	//  String r = temp.substring(1, temp.length()-1);
	//  System.out.println("temp = "+r);
	  //parsing input for song names
	try {
		JSONArray  jsonArray = new JSONArray(temp);
		for (int i = 0; i < jsonArray.length(); i++) {
		    JSONObject jsonobject = jsonArray.getJSONObject(i);
		    String name = jsonobject.getString("wav");
		    songs.add(name);
		   // System.out.println("json object #"+i+" = "+name);
		}//end for
		ArrayList<Integer> songIDs = new ArrayList<Integer>();
		for(int j=0;j<songs.size();j++){
			songIDs.add(getSongID(songs.get(j)));
			//System.out.println("song: "+songs.get(j)+" id = "+songIDs.get(j));
		}
		
		int playlistID = savePlaylistName(plName);
		for(int a=0;a<songIDs.size();a++){
			System.out.println("##### song id = "+songIDs.get(a)+"######");
			savePlaylistSongs(songIDs.get(a),playlistID);
		}	
		
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 
	  PrintWriter out= response.getWriter();
	  out.print("sucess");
	  out.flush();
	  out.close();
      
  }
  
  public void destroy()
  {
      // do nothing.
  }
  
  private String savePlaylistSongs(int songID, int plID  )
  {
	  try{
			//System.out.println("setting class");
		Class.forName("com.mysql.jdbc.Driver");
		System.out.println("Connecting to database...");
		Connection conn = DriverManager.getConnection(DB_URL,user,pass);
		System.out.println("Connected!!!");
		//System.out.println(conn.toString());
		System.out.println("Creating statement...");
		
		System.out.println("Adding Playlist Song");
		PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO playlist_songs(playlistID,songID) VALUES(?,?)");
		stmt2.setInt(1,plID );
		stmt2.setInt(2,songID );

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
  
  private int getSongID(String songPath){
	 // ArrayList<Integer> songIDs = new ArrayList<Integer>();
	  try{
			//System.out.println("setting class");
		Class.forName("com.mysql.jdbc.Driver");
		System.out.println("Connecting to database...");
		Connection conn = DriverManager.getConnection(DB_URL,user,pass);
		System.out.println("Connected!!!");
		//System.out.println(conn.toString());
		System.out.println("Creating statement...");
		
		System.out.println("getting songID");
		PreparedStatement stmt2 = conn.prepareStatement("SELECT id FROM songs WHERE path=? ");
		stmt2.setString(1, songPath);
		ResultSet result = stmt2.executeQuery();
		ArrayList<Integer> ids = new ArrayList<Integer>();

		while (result.next()){
			ids.add(result.getInt("id"));
			//System.out.println("for song: "+songPath+" id = "+id);
			
		}
		System.out.println("for song: "+songPath+" id = "+ids.get(0));
	//	System.out.println("get songId= "+result);
		//JSONArray json = convertToJSON(result);
		stmt2.close();
		return ids.get(0);
		//return json;
		
	  }
	  catch(SQLException e){
			System.out.println("cannot connect");
			e.printStackTrace();
			return -1;
		}
		catch(Exception e)
		{
			System.out.println("Driver not found");
			e.printStackTrace();
			return -1;
		}
	
	  //return 1;
  }
  
  private int savePlaylistName(String plName){
	  try{
			//System.out.println("setting class");
		Class.forName("com.mysql.jdbc.Driver");
		System.out.println("Connecting to database...");
		Connection conn = DriverManager.getConnection(DB_URL,user,pass);
		System.out.println("Connected!!!");
		//System.out.println(conn.toString());
		System.out.println("Creating statement...");
		
		System.out.println("adding playlist data");
		PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO playlists(playlistName,userName) VALUES(?,?)");
		stmt2.setString(1,plName );
		stmt2.setString(2,"user1");	
		stmt2.executeUpdate();
		stmt2.close();
		//getting ID
		System.out.println("Getting PlaylistID");
		int id = getPlaylistID(plName,"user1");
		/*
		PreparedStatement stmt3 = conn.prepareStatement("SELECT playlistID FROM playlists WHERE playlistName=? AND userName=? ");
		stmt3.setString(1, plName);
		stmt3.setString(2, "user1");
		ResultSet result = stmt3.executeQuery();
			int id = result.getInt("playlistID");
			System.out.println("id = "+id);	
		stmt3.close();
		*/
		return id;
	  }
	  catch(SQLException e){
			System.out.println("cannot connect");
			e.printStackTrace();
			return -1;
		}
		catch(Exception e)
		{
			System.out.println("Driver not found");
			e.printStackTrace();
			return -1;
		}
	  
  }
  
  private int getPlaylistID(String plName, String username){
	  try{
			//System.out.println("setting class");
		Class.forName("com.mysql.jdbc.Driver");
		System.out.println("Connecting to database...");
		Connection conn = DriverManager.getConnection(DB_URL,user,pass);
		System.out.println("Connected!!!");
		//System.out.println(conn.toString());
		System.out.println("Creating statement...");
		
		System.out.println("getting PlaylistID");
		PreparedStatement stmt2 = conn.prepareStatement("SELECT playlistID FROM playlists WHERE playlistName=? AND userName=? ");
		stmt2.setString(1,plName );
		stmt2.setString(2,username);	
		ResultSet result = stmt2.executeQuery();
		ArrayList<Integer> ids = new ArrayList<Integer>();
		 while (result.next()) {
		 ids.add(result.getInt("playlistID"));
		 }
		 System.out.println("id = "+ids.get(0));
		//int id = result.getInt("playlistID");
		//System.out.println("id = "+id);	
		stmt2.close();
		//getting ID
		//System.out.println("Getting PlaylistID");
		//int id = getPlaylistID(plName,"user1");
		/*
		PreparedStatement stmt3 = conn.prepareStatement("SELECT playlistID FROM playlists WHERE playlistName=? AND userName=? ");
		stmt3.setString(1, plName);
		stmt3.setString(2, "user1");
		ResultSet result = stmt3.executeQuery();
			int id = result.getInt("playlistID");
			System.out.println("id = "+id);	
		stmt3.close();
		*/
		return ids.get(0);
	  }
	  catch(SQLException e){
			System.out.println("cannot connect");
			e.printStackTrace();
			return -1;
		}
		catch(Exception e)
		{
			System.out.println("Driver not found");
			e.printStackTrace();
			return -1;
		}
	  
	  
  }
  
  private void savePlaylistSongs(){
	  
  }
}
