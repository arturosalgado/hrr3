package com.hrr3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;

public class RM2AbstractDAO {
	
	
	private Connection currentConnection = null;
	
	public Connection getCurrentConnection () {
		
		try {
			if(currentConnection == null || currentConnection.isClosed())
				this.currentConnection = RM2DataSourceConnectionFactory.getRM2Connection();
			
			return this.currentConnection;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
		
	}
	
	
	// You need to close the resultSet
		  protected void close(ResultSet rs, PreparedStatement ps, Connection conn) {
			  
			  System.out.println("****** Closing rs/ps/conn objects ******");
			  
		    try {
		      if (rs != null) {
		        rs.close();
		      }

		      if (ps != null) {
		        ps.close();
		      }

		      if (conn != null) {
		    	  conn.close();
		      }
		    } catch (Exception e) {

		    }
		  }
		  
			// You need to close the resultSet
		  protected void close(Connection conn) {
			  
			  System.out.println("****** Closing connection object ******");
			  
		    try {

		      if (conn != null) {
		    	  conn.close(); conn = null;
		      }
		    } catch (Exception e) {

		    }
		  }		
		  
			// You need to close the resultSet
		  protected void close(ResultSet rs, PreparedStatement ps) {
			  
			  System.out.println("****** Closing resultet and preparest ******");
			  
			  try {
			      if (rs != null) {
			        rs.close();
			      }

			      if (ps != null) {
			        ps.close();
			      }

			     
			    } catch (Exception e) {

			    }
		  }		  		  

}
