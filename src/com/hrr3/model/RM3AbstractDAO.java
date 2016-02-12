package com.hrr3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.NamingException;

import com.hrr3.entity.Hotel;

public abstract class RM3AbstractDAO {
	
	public static final int ERROR = 0;
	public static final int OK = 1;
	public static final int WARNING = 2;	
	
	private Hotel currentHotel;
	
	public RM3AbstractDAO(Hotel currentHotel) {
		super();
		this.currentHotel = currentHotel;
	}
	
	public RM3AbstractDAO() {
		super();
	}


	/**
	 * @return the currentHotel
	 */
	public Hotel getCurrentHotel() {
		return this.currentHotel;
	}


	/**
	 * @param currentHotel the currentHotel to set
	 */
	public void setCurrentHotel(Hotel currentHotel) {
		this.currentHotel = currentHotel;
	}

	private Connection currentHRR3Connection = null;
	private Connection currentRM2Connection = null;
	
	public Connection getCurrentHRR3Connection () {
		
		try {
			if(currentHRR3Connection == null || currentHRR3Connection.isClosed())
				this.currentHRR3Connection = RM3DataSourceConnectionFactory.getHRR3Connection();
			
			return this.currentHRR3Connection;
			
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
	
public Connection getCurrentRM2Connection () {
		
		try {
			if(currentRM2Connection == null || currentRM2Connection.isClosed())
				this.currentRM2Connection = RM2DataSourceConnectionFactory.getRM2Connection();
			
			return this.currentRM2Connection;
			
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
		  protected void close(ResultSet rs, Statement st, Connection conn) {
			  
			  System.out.println("****** Closing rs/ps/conn objects ******");
			  
		    try {
		      if (rs != null) {
		        rs.close();
		      }

		      if (st != null) {
		    	  st.close();
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
