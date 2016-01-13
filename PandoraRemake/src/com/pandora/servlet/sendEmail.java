package com.pandora.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

/**
 * Servlet implementation class sendEmail
 */
@WebServlet("/sendEmail")
public class sendEmail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public sendEmail() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String toEmail = request.getParameter("email");
		System.out.println("send email to= "+toEmail);
		final String username = "oochdev@gmail.com";
		final String password = "jig5can6had3";
		
		Properties properties = new Properties();
	    properties.put("mail.smtp.auth", true);
	    properties.put("mail.smtp.ssl.trust","smtp.gmail.com");
	    properties.put("mail.smtp.starttls.enable", true);
	    properties.put( "mail.transport.protocol","smtp");
	    properties.put("mail.smtp.host", "smtp.gmail.com");
	    
	    properties.setProperty("mail.smtp.port", "587");
	    
	    //props.put("mail.smtp.host", host);
	   // props.setProperty("mail.smtp.port", "587");
	   // props.put("mail.smtp.auth", true);

	    //Bypass the SSL authentication
	    properties.put("mail.smtp.ssl.enable", false);
	   // props.put("mail.smtp.starttls.enable", false);
	    
	    Session session = Session.getInstance(properties,new javax.mail.Authenticator(){
	    	protected PasswordAuthentication getPasswordAuthentication(){
	    		return new PasswordAuthentication(username,password);
	    	}
	    });
	    try{
	         Message message = new MimeMessage(session);
	         message.setFrom(new InternetAddress("from-oochdev@gmail.com"));
	         message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("oochdev@gmail.com"));
	         message.setSubject("testing");
	         message.setText("message text");
	         
	         Transport.send(message);
	         
	         System.out.println("done");
	        
	      }catch (MessagingException mex) {
	         mex.printStackTrace();
	      }
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String toEmail = request.getParameter("email");
		System.out.println("send email to= "+toEmail);
		final String username = "oochdev@gmail.com";
		final String password = "jig5can6had3";
		
		Properties properties = new Properties();
	    properties.put("mail.smtp.auth", true);
	    properties.put("mail.smtp.ssl.trust","smtp.gmail.com");
	    properties.put("mail.smtp.starttls.enable", true);
	    properties.put( "mail.transport.protocol","smtp");
	    properties.put("mail.smtp.host", "smtp.gmail.com");
	    
	    properties.setProperty("mail.smtp.port", "587");
	    
	    //props.put("mail.smtp.host", host);
	   // props.setProperty("mail.smtp.port", "587");
	   // props.put("mail.smtp.auth", true);

	    //Bypass the SSL authentication
	    properties.put("mail.smtp.ssl.enable", false);
	   // props.put("mail.smtp.starttls.enable", false);
	    
	    Session session = Session.getInstance(properties,new javax.mail.Authenticator(){
	    	protected PasswordAuthentication getPasswordAuthentication(){
	    		return new PasswordAuthentication(username,password);
	    	}
	    });
	    try{
	         Message message = new MimeMessage(session);
	         message.setFrom(new InternetAddress("from-oochdev@gmail.com"));
	         message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
	         message.setSubject("testing");
	         message.setText("message text");
	         
	         Transport.send(message);
	         
	         System.out.println("done");
	        
	      }catch (MessagingException mex) {
	         mex.printStackTrace();
	      }
	}

}
