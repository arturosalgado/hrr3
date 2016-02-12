package com.hrr3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.NamingException;

import com.hrr3.entity.Customer;
import com.hrr3.entity.Segment;
import com.hrr3.entity.SegmentSetting;


public class CustomerDAO extends RM3AbstractDAO {
	
	
	
	public Customer findCustomer(Integer customerId){
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try {
			
			conn = RM3DataSourceConnectionFactory.getHRR3Connection();
			
			String statement = "select * from Customers where CustomerId=?";
			ps = conn.prepareStatement(statement);
			ps.setInt(1, Integer.valueOf(customerId));
			rs = ps.executeQuery();
			
			if(rs != null && rs.next()){
				
				Customer customer = new Customer();
				customer.setCustomerId(rs.getInt("CustomerId"));
				customer.setName(rs.getString("CompanyName"));
				customer.setLogo(rs.getString("Logo"));
				
				
				return customer;
			}
			
			return null;
			
				
			
			
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		finally { close(rs, ps, conn); }
	}
	

	public ArrayList<Segment> getSegmentNames(Integer customerId, Integer type){
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try {
			
			conn = RM3DataSourceConnectionFactory.getHRR3Connection();
			
			String statement = "select segment_id, name from RM3Segments where customer_id=? and class_id=? and isTotal = 0 and type='TM' and isActive=1 order by sequence,name ";
			ps = conn.prepareStatement(statement);
			ps.setInt(1, Integer.valueOf(customerId));
			ps.setInt(2, Integer.valueOf(type));
			rs = ps.executeQuery();
			
			ArrayList<Segment> segments = null;
			
			if(rs != null){
				
				segments = new ArrayList<Segment>();
				
				while(rs.next()){
					
					Segment segmentOcc = new Segment();
					segmentOcc.setId(rs.getInt("segment_id"));
					segmentOcc.setName(rs.getString("name"));						
					segments.add(segmentOcc);
					
				}		
				
				return segments;
			}					
			
				return null;
			
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		finally { close(rs, ps, conn); }
	}
	
	public ArrayList<Segment> getGroupSegmentNames(Integer customerId){
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try {
			
			conn = RM3DataSourceConnectionFactory.getHRR3Connection();
						
			String statement = "select segment_id, name from RM3Segments where customer_id = ? and istotal = 0 and isactive = 1 and type='DP'";
			ps = conn.prepareStatement(statement);
			ps.setInt(1, Integer.valueOf(customerId));			
			rs = ps.executeQuery();
			
			ArrayList<Segment> segments = null;
			
			if(rs != null){
				
				segments = new ArrayList<Segment>();
				
				while(rs.next()){
					
					Segment segmentOcc = new Segment();
					segmentOcc.setId(rs.getInt("segment_id"));
					segmentOcc.setName(rs.getString("name"));						
					segments.add(segmentOcc);
					
				}		
				
				return segments;
			}					
			
				return null;
			
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		finally { close(rs, ps, conn); }
	}
	
	
	public ArrayList<Customer> findCustomersByUserId(Integer userId){
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try {
			
			conn = RM3DataSourceConnectionFactory.getHRR3Connection();
			
			String statement = "SELECT c.CustomerId, c.CompanyName, c.Logo FROM Customers c INNER JOIN User2Customer uc ON uc.CustomerId=c.CustomerId  WHERE  uc.UserId=?";
			ps = conn.prepareStatement(statement);
			ps.setInt(1, Integer.valueOf(userId));
			rs = ps.executeQuery();
			
			ArrayList<Customer> customers = null;
			
			if(rs != null){
				
				customers = new ArrayList<Customer>();
				
				while(rs.next()){
					
					Customer customer = new Customer();
					customer.setCustomerId(rs.getInt("CustomerId"));
					customer.setName(rs.getString("CompanyName"));
					customer.setLogo(rs.getString("Logo"));		
					
					customers.add(customer);
				}				
				
				return customers;
			}
			
			return null;
			
				
			
			
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		finally { close(rs, ps, conn); }
	}

public ArrayList<Customer> getAllCustomers(){
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try {
			
			char activeCustomer = '1';
			
			conn = RM3DataSourceConnectionFactory.getHRR3Connection();
			
			String statement = "SELECT c.CustomerId, c.CompanyName, c.Logo FROM Customers c where c.Active=?";
			ps = conn.prepareStatement(statement);
			ps.setString(1, String.valueOf(activeCustomer));
			rs = ps.executeQuery();
			
			ArrayList<Customer> customers = null;
			
			if(rs != null){
				
				customers = new ArrayList<Customer>();
				
				while(rs.next()){
					
					Customer customer = new Customer();
					customer.setCustomerId(rs.getInt("CustomerId"));
					customer.setName(rs.getString("CompanyName"));
					customer.setLogo(rs.getString("Logo"));		
					
					customers.add(customer);
				}				
				
				return customers;
			}
			
			return null;
			
				
			
			
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		finally { close(rs, ps, conn); }
	}

   public List<SegmentSetting> getSegmentsSettings (int customerId, int hotelId){
	   System.out.println("*********** Customers - getSegmentsSettings - BEGIN - " + new Date() +" ***********");
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		List<SegmentSetting> lstSegmentsSettings = new ArrayList<SegmentSetting>();
		
		try{	
						
			conn = this.getCurrentHRR3Connection();	
			
			String statement = ("{call ReportSettings_Select (?,?)}");
			
			ps = conn.prepareStatement(statement);			
			
			
			ps.setInt(1, customerId);
			ps.setInt(2, hotelId);
			
			
			rs = ps.executeQuery();
			
			while(rs != null && rs.next()){	
				
				SegmentSetting SS = new SegmentSetting();
				
				SS.setId(rs.getInt("segment_id"));
				SS.setName(rs.getString("name"));
				SS.setIncludeInReport(rs.getBoolean("includeInReport"));
				SS.setIncludeInTotal(rs.getBoolean("includeInTotal"));
												
				lstSegmentsSettings.add(SS);								
					
			}
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
		}
	
		finally { close(rs, ps, conn);};	       	
				
		System.out.println("*********** Customers - getSegmentsSettings - END - " + new Date() +" ***********");
		
		return lstSegmentsSettings;
	   
   }
   
  
   public void updateSegmentSettings(List<SegmentSetting> lstSegmentsSettings, int hotelId, List<SegmentSetting> lstSegmentsSettingsDelete){
	   System.out.println("*********** Customers - updateSegmentSettings - BEGIN - " + new Date() +" ***********");
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		
		
		try{
			
			this.deleteSegmentSettings(hotelId);
						
			conn = this.getCurrentHRR3Connection();
			
			String statement = "";
			
			for(SegmentSetting SS: lstSegmentsSettings){
				
				//statement = "Insert into RM3ReportSettings (HotelID,SegmentID,Include_Report, Include_Total) values (?,?,?,?)";
				
				statement = "Insert into RM3ReportSettings (HotelID,SegmentID,Include_Report, Include_Total) values (?,?,?,?) " +
						"ON DUPLICATE KEY UPDATE Include_Report = ?, Include_Total = ? ";
						
				ps = conn.prepareStatement(statement);			
				
				ps.setInt(1, hotelId);
				ps.setInt(2, SS.getId());
				ps.setBoolean(3, SS.getIsIncludeInReport());
				ps.setBoolean(4, SS.getIncludeInTotal());
				ps.setBoolean(5, SS.getIsIncludeInReport());
				ps.setBoolean(6, SS.getIncludeInTotal());
				
				
				ps.executeUpdate();
				
				statement = "";
				
			}
			
				
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
		}
	
		finally { close(rs, ps, conn);};	       	
				
		System.out.println("*********** Customers - updateSegmentSettings - END - " + new Date() +" ***********");
	
   }
   
   public void deleteSegmentSettings(int hotelId){
	   
	   System.out.println("*********** Customers - deleteSegmentSettings - BEGIN - " + new Date() +" ***********");
		
			PreparedStatement ps = null;
			ResultSet rs = null;
			Connection conn = null;
			
			try{	
							
				conn = this.getCurrentHRR3Connection();	
				
				String statement = ("delete from RM3ReportSettings where HotelID = ?");
				
				ps = conn.prepareStatement(statement);			
						
				ps.setInt(1, hotelId);
				
				ps.executeUpdate();
				
						
				
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();		
			}
		
			finally { close(rs, ps, conn);};	       	
					
			System.out.println("*********** Customers - deleteSegmentSettings - END - " + new Date() +" ***********");
   }

   public void deleteSegmentSettings(List<SegmentSetting> lstSegmentsSettings,int hotelId){
	   
	   System.out.println("*********** Customers - deleteSegmentSettings - BEGIN - " + new Date() +" ***********");
		
			PreparedStatement ps = null;
			ResultSet rs = null;
			Connection conn = null;
			
			try{	
							
				conn = this.getCurrentHRR3Connection();	
				
				for(SegmentSetting SS: lstSegmentsSettings){
					String statement = ("delete from RM3ReportSettings where HotelID = ? and SegmentID = ?");
					
					ps = conn.prepareStatement(statement);			
							
					ps.setInt(1, hotelId);
					ps.setInt(2, SS.getId());
					
					ps.executeUpdate();
				}	
					
						
				
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();		
			}
		
			finally { close(rs, ps, conn);};	       	
					
			System.out.println("*********** Customers - deleteSegmentSettings - END - " + new Date() +" ***********");
   }

	
}
