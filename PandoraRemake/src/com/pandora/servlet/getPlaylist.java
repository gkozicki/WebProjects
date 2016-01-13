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
 * gets all songs from specific playlist, supply plName=?
 */
@WebServlet("/getPlaylist")
public class getPlaylist extends HttpServlet {
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
		/*
		String playlistName = request.getParameter("plName");
		System.out.println("!!!!!~~~name = "+playlistName);
		int plID = getPlaylistID(playlistName,"user1");
		ArrayList<Integer> songIDs = getPlaylistSongs(plID);
		ArrayList<String> songPaths = new ArrayList<String>();
 		for(int i=0;i<songIDs.size();i++){
			songPaths.add(getSongPath(songIDs.get(i)));
		}
		JSONArray jArray = new JSONArray();

 		 for(int z=0;z<songPaths.size();z++){
 			 JSONObject obj = new JSONObject();
 			 try {
				obj.put("name", songPaths.get(z));
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
		*/
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String playlistName = request.getParameter("plName");
		System.out.println("!!!!!~~~name = "+playlistName);
		int plID = getPlaylistID(playlistName,"user1");
		ArrayList<Integer> songIDs = getPlaylistSongs(plID);
		ArrayList<String> songPaths = new ArrayList<String>();
 		for(int i=0;i<songIDs.size();i++){
			songPaths.add(getSongPath(songIDs.get(i)));
		}
 		JSONArray jArray = new JSONArray();

		 for(int z=0;z<songPaths.size();z++){
			 JSONObject obj = new JSONObject();
			 try {
				obj.put("name", songPaths.get(z));
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
 		
 		/*
 		int size=-1;
 		for(int s=0;s<songPaths.size();s++){
 			size +=songPaths.get(s).length()+2;
 		}
		response.setContentLength(size);
		PrintWriter out= response.getWriter();
		System.out.println("songs :"+songPaths);
		out.print(songPaths);
		*/
	}
	
	public ArrayList<Integer> getPlaylistSongs(int playlistID)
	  {
		System.out.println("~~~~PLAYLISTID = "+playlistID);
		ArrayList<Integer> songIDs = new ArrayList<Integer>();
		  try{
				System.out.println("GETTING PLAYLIST SONGS");
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Connecting to database...");
			Connection conn = DriverManager.getConnection(DB_URL,user,pass);
			//System.out.println("Connected!!!");
			//System.out.println(conn.toString());
			System.out.println("Creating statement...");
			
			System.out.println("getting all Playlist songs");
			PreparedStatement stmt2 = conn.prepareStatement("SELECT songID FROM playlist_songs WHERE playlistID=? ");
			stmt2.setInt(1, playlistID);
			ResultSet result = stmt2.executeQuery();
			while (result.next()) {
				 songIDs.add(result.getInt("songID"));
				 }
			//JSONArray json = convertToJSON(result);
			stmt2.close();
			conn.close();
			return songIDs;
			
		  }
		  catch(SQLException e){
				System.out.println("cannot connect");
				e.printStackTrace();
			}
			catch(Exception e)
			{
				System.out.println("GETTING PLAYLIST SONGS Driver not found");
				e.printStackTrace();
			}
		  return null;
	  }
	
	  private int getPlaylistID(String plName, String username){
		  try{
				System.out.println("GETTING PLAYLIST IDs");
			Class.forName("com.mysql.jdbc.Driver");
			//System.out.println("Connecting to database...");
			Connection conn = DriverManager.getConnection(DB_URL,user,pass);
		//	System.out.println("Connected!!!");
			//System.out.println(conn.toString());
			System.out.println("Creating statement...");
			
			System.out.println("getting PlaylistIDfor plname="+plName+" and name="+username);
			PreparedStatement stmt2 = conn.prepareStatement("SELECT playlistID FROM playlists WHERE playlistName=? AND userName=? ");
			stmt2.setString(1,plName );
			stmt2.setString(2,username);	
			ResultSet result = stmt2.executeQuery();
			System.out.println("result set = "+result);
			ArrayList<Integer> ids = new ArrayList<Integer>();
			 while (result.next()) {
				
			 ids.add(result.getInt("playlistID"));
			 System.out.println("playlistid = "+ids.get(0));
			 }
			// System.out.println("id = "+ids.get(0));
			//int id = result.getInt("playlistID");
			//System.out.println("id = "+id);	
			stmt2.close();
			conn.close();
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
				System.out.println("GETTING PLAYLIST IDs Driver not found");
				e.printStackTrace();
				return -1;
			}
		  
		  
	  }
	  
	  public String getSongPath(int songID)
	  {
		  try{
				System.out.println("GETTING SONG PATHS");
			Class.forName("com.mysql.jdbc.Driver");
			//System.out.println("Connecting to database...");
			Connection conn = DriverManager.getConnection(DB_URL,user,pass);
			//System.out.println("Connected!!!");
			//System.out.println(conn.toString());
			System.out.println("Creating statement...");
			
			System.out.println("getting all songs");
			PreparedStatement stmt2 = conn.prepareStatement("SELECT path FROM songs WHERE id=?");
			stmt2.setInt(1,songID);
			ResultSet result = stmt2.executeQuery();
			ArrayList<String> paths = new ArrayList<String>();
			while(result.next()){
				paths.add(result.getString("path"));
			}
			//JSONArray json = convertToJSON(result);
			stmt2.close();
			conn.close();
			return paths.get(0);
			
		  }
		  catch(SQLException e){
				System.out.println("cannot connect");
				e.printStackTrace();
			}
			catch(Exception e)
			{
				System.out.println("GETTING SONG PATHS Driver not found");
				e.printStackTrace();
			}
		  return null;
	  }
	  
/*	  public static JSONArray convertToJSON(ResultSet resultSet)
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
		*/


}
