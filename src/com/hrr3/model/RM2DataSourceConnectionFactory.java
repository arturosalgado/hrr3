package com.hrr3.model;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;




public class RM2DataSourceConnectionFactory {
	
	
	private static Context initContext = null;
	private static Context envContext  = null;
	private static DataSource datasource = null;
	
	private static String DBSource = "RM2DB";
	
	
	public static synchronized Connection getRM2Connection() throws NamingException, SQLException {
	
		if(initContext == null)
			initContext = new InitialContext();
		
		if(envContext == null)
			envContext  = (Context)initContext.lookup("java:/comp/env");
		
		if(datasource == null)
			datasource = (DataSource)envContext.lookup("jdbc/" + DBSource);
		
		
		
		Connection conn = datasource.getConnection();
		
				   
		System.out.println("****** RM2 Connection requested: " + conn);
		
		return conn;
				 
		
	}
	

}
