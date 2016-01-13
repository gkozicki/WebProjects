package com.pandora.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class UploadDownloadFileServlet
 */
@WebServlet("/UploadDownloadFileServlet")
public class UploadDownloadFileServlet extends HttpServlet {
 static final long serialVersionUID = 1L;
 private ServletFileUpload uploader = null;
 static final String DB_URL = "jdbc:mysql://127.0.0.1/Pandora";
	static final String user = "root";
	static final String pass = "";

    @Override
    public void init() throws ServletException{
        System.out.println("attributes= "+getServletContext().getAttribute("FILES_DIR_FILE"));

        DiskFileItemFactory fileFactory = new DiskFileItemFactory();
        File filesDir = (File) getServletContext().getAttribute("FILES_DIR_FILE");
        fileFactory.setRepository(filesDir);
        this.uploader = new ServletFileUpload(fileFactory);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	//System.out.println("calling upload");

        String fileName = request.getParameter("fileName");
        if(fileName == null || fileName.equals("")){
            throw new ServletException("File Name can't be null or empty");
        }

        File file = new File(request.getServletContext().getAttribute("FILES_DIR")+File.separator+fileName);
        if(!file.exists()){
            throw new ServletException("File doesn't exists on server.");
        }
        System.out.println("File location on server::"+file.getAbsolutePath());
        ServletContext ctx = getServletContext();
        InputStream fis = new FileInputStream(file);
        String mimeType = ctx.getMimeType(file.getAbsolutePath());
        response.setContentType(mimeType != null? mimeType:"application/octet-stream");
        response.setContentLength((int) file.length());
      //  response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        ServletOutputStream os       = response.getOutputStream();
        byte[] bufferData = new byte[1024];
        int read=0;

        while((read = fis.read(bufferData))!= -1){
           os.write(bufferData, 0, read);
        }
        os.flush();
        os.close();
        fis.close();
        System.out.println("File downloaded at client successfully");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	System.out.println("calling upload");

        if(!ServletFileUpload.isMultipartContent(request)){
            throw new ServletException("Content type is not multipart/form-data");
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.write("<html><head></head><body>");

        try {
            List<FileItem> fileItemsList = uploader.parseRequest(request);
            Iterator<FileItem> fileItemsIterator = fileItemsList.iterator();

            while(fileItemsIterator.hasNext()){

                FileItem fileItem = fileItemsIterator.next();
                System.out.println("FieldName="+fileItem.getFieldName());
                System.out.println("FileName="+fileItem.getName());
                System.out.println("ContentType="+fileItem.getContentType());
                System.out.println("Size in bytes="+fileItem.getSize());

                System.out.println("attributes= "+File.separator+fileItem.getName());
                File file = new File(request.getServletContext().getAttribute("FILES_DIR")+File.separator+fileItem.getName());
                System.out.println("Absolute Path at server="+file.getAbsolutePath());
                fileItem.write(file);
             //   out.write("File "+fileItem.getName()+ " uploaded successfully.");
             //   out.write("<br>");
                out.write("<a href=\"UploadDownloadFileServlet?fileName="+fileItem.getName()+"\"> Download "+fileItem.getName()+" </a>");
           //full path = http://localhost:8080/PandoraRemake/UploadDownloadFileServlet?fileName=Fonda01.wav
                   saveToDB(fileItem.getName(),fileItem.getContentType(),fileItem.getSize(),
                		   "UploadDownloadFileServlet?fileName="+fileItem.getName());
            }

        } catch (FileUploadException e) {

            out.write("Exception in uploading file.");
            e.printStackTrace();

        } catch (Exception e) {

            out.write("Exception in uploading file.");
            e.printStackTrace();
        }

        out.write("</body></html>");

    }
    
    private String saveToDB(String name, String type, long size, String path)
    {
  	  try{
  			//System.out.println("setting class");
  		Class.forName("com.mysql.jdbc.Driver");
  		System.out.println("Connecting to database...");
  		Connection conn = DriverManager.getConnection(DB_URL,user,pass);
  		System.out.println("Connected!!!");
  		//System.out.println(conn.toString());
  		System.out.println("Creating statement...");
  		
  		System.out.println("Adding song");
  		PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO Songs(name,type,size,path) VALUES(?,?,?,?)");
  		stmt2.setString(1,name );
  		stmt2.setString(2,type );
  		stmt2.setLong(3, size);
  		stmt2.setString(4,path );

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
