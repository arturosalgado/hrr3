package com.hrr3.services;

import java.io.IOException;
import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class EmailServiceSIZEImpl implements EmailService {
	
	private String SMTP_SERVER;
	private String SMTP_PORT;
	private String EMAIL_ACCOUNT;
	private String EMAIL_PASSWORD;
	private String SMTP_AUTH = "true";
	private String SMTP_TLS = "true";
	
	public static Properties properties = System.getProperties();
	public static Authenticator authenticator;

	/**
	 * Metodo para setear los valores de conexion al SMTP Server, crear el Properties y el Authtentication
	 * @param server
	 * @param port
	 * @param account
	 * @param password
	 */
	public EmailServiceSIZEImpl(String server, String port, String account,	String password) {
		
		this.SMTP_SERVER = server;
		this.SMTP_PORT = port;
		this.EMAIL_ACCOUNT = account;
		this.EMAIL_PASSWORD = password;
		
	}
	
	public boolean initializeEmailService() {
		
		try{
			
			if(authenticator == null) {
				
				// Get system properties
			    properties.put("mail.smtp.host", this.SMTP_SERVER); //SMTP Host
			    properties.put("mail.smtp.port", this.SMTP_PORT); //TLS Port
			    properties.put("mail.smtp.auth", this.SMTP_AUTH); //enable authentication
			    properties.put("mail.smtp.starttls.enable", this.SMTP_TLS); //enable STARTTLS
			      
			      //create Authenticator object to pass in Session.getInstance argument
			    authenticator = new Authenticator() {
			            //override the getPasswordAuthentication method
			            protected PasswordAuthentication getPasswordAuthentication() {
			                return new PasswordAuthentication(EMAIL_ACCOUNT, EMAIL_PASSWORD);
			            }
			        };
				
			}
			
			
			
		} catch(Exception e) {
			
			return false;
		}
		
		
	        
	   return true;     
	}
	
	

	@Override
	public boolean sendEmail(String subject, String content, String to) throws IOException {
		//Test envio de email
		
	      // Get the default Session object.
	      Session session = Session.getInstance(properties, authenticator);

	      try{
	         // Create a default MimeMessage object.
	         MimeMessage message = new MimeMessage(session);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(this.EMAIL_ACCOUNT));

	         message.addRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
	         
	         message.setDataHandler(new DataHandler(new javax.mail.util.ByteArrayDataSource(content, "text/html")));
	        	 
	     
	         // Set Subject: header field
	         message.setSubject(subject);
	         //message.setText(content);
	         Transport.send(message);
	         System.out.println("************* HHR3 - EmailService - Mensaje enviado.");
	         
	         return true;
	      }catch (MessagingException mex) {
	         mex.printStackTrace();
	         return false;
	      }
	   
	}

	

}
