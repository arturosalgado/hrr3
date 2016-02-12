package com.hrr3.model;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.zkoss.zk.ui.Executions;




public class RM3DataSourceConnectionFactory {
	
	
	private static Context initContext = null;
	private static Context envContext  = null;
	private static DataSource datasource = null;
	
	private static String DBSource =Executions.getCurrent().getServerName().equals("localhost") || Executions.getCurrent().getServerName().equals("dev2.valetsoftware.com") ? "HRR3DB-dev": Executions.getCurrent().getServerName().equals("prod.hotelrevenueresources.com") ? "HRR3DB-prod" : "HRR3DB-staging";
	
	
	
	public static synchronized Connection getHRR3Connection() throws NamingException, SQLException {
	
	DBSource = "HRR3DB-staging";
			
		
		if(initContext == null)
			initContext = new InitialContext();
		
		if(envContext == null)
			envContext  = (Context)initContext.lookup("java:/comp/env");
		
		if(datasource == null)
			datasource = (DataSource)envContext.lookup("jdbc/" + DBSource);
			
		Connection conn = datasource.getConnection();
		System.out.println("****** HRR3 Connection requested: " + conn);
		System.out.println("connected to " +" Db source "+DBSource);
		return conn;
				 
		
	}
	

}
