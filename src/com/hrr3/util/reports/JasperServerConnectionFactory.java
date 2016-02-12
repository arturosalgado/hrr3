package com.hrr3.util.reports;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.zkoss.zk.ui.Executions;


public class JasperServerConnectionFactory {
	
	
	private static Context initContext = null;
	private static Context envContext  = null;
	
	private static String jasperServerURL = null;
	private static String jasperServerUser = null;
	private static String jasperServerPass = null;
	
	private static String JasperServerURLSource = Executions.getCurrent().getServerName().equals("localhost") || Executions.getCurrent().getServerName().equals("dev2.valetsoftware.com") ?"jasperserverurl-dev": Executions.getCurrent().getServerName().equals("prod.hotelrevenueresources.com") ? "jasperserverurl-prod" : "jasperserverurl-staging";
	private static String JasperServerUserSource = "jasperserveruser";
	private static String JasperServerPassSource = "jasperserverpass";
	
	
	public static synchronized String getJasperServerURL() throws NamingException {
	
		if(initContext == null)
			initContext = new InitialContext();
		
		if(envContext == null)
			envContext  = (Context)initContext.lookup("java:/comp/env");
		
		if(jasperServerURL == null )
			jasperServerURL = (String)envContext.lookup(JasperServerURLSource);		
		
		System.out.println("****** JasperServer URL: " + jasperServerURL + " ********");
		
		return jasperServerURL;			 
		
	}
	
	public static synchronized String getJasperServerUser() throws NamingException {
		
		if(initContext == null)
			initContext = new InitialContext();
		
		if(envContext == null)
			envContext  = (Context)initContext.lookup("java:/comp/env");
		
		if(jasperServerUser == null )
			jasperServerUser = (String)envContext.lookup(JasperServerUserSource);		
		
		return jasperServerUser;			 
		
	}
	
	public static synchronized String getJasperServerPass() throws NamingException {
		
		if(initContext == null)
			initContext = new InitialContext();
		
		if(envContext == null)
			envContext  = (Context)initContext.lookup("java:/comp/env");
		
		if(jasperServerPass == null )
			jasperServerPass = (String)envContext.lookup(JasperServerPassSource);		
		
		return jasperServerPass;			 
		
	}
	

}
