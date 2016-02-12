package com.hrr3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.NamingException;

import com.hrr3.entity.Customer;
import com.hrr3.entity.Hotel;


public class HotelDAO extends RM3AbstractDAO {
	
	public Hotel getHotelDetails(Integer hotelId){
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try {
			
			conn = RM3DataSourceConnectionFactory.getHRR3Connection();
			
			String statement = "SELECT h.HotelId, h.LegalName, hd.SnapshotId, hd.ssrSnapshotId,  hd.ActualsVendorHotelId, hd.GroupVendorHotelId, h.Address1, h.City, h.TotalRooms from RM3HotelDetails hd " +
								" inner join Hotels h on hd.HotelId=h.HotelId where hd.HotelId = ? ";
								
			
			
			ps = conn.prepareStatement(statement);
			ps.setInt(1, Integer.valueOf(hotelId));
			rs = ps.executeQuery();
			
			
			if(rs != null ){
				
				Hotel newHotel = null;
				
				if(rs.next()){
					
					newHotel = new Hotel();
					newHotel.setHotelId(rs.getInt("HotelId"));
					newHotel.setHotelName(rs.getString("LegalName"));
					newHotel.setCity(rs.getString("City"));
					newHotel.setActualsVendorHotelId(rs.getString("ActualsVendorHotelId"));
					newHotel.setGroupVendorHotelId(rs.getString("GroupVendorHotelId"));
					newHotel.setSnapshotId(rs.getInt("SnapshotId"));
					newHotel.setSsrSnapshotId(rs.getInt("ssrSnapshotId"));
					newHotel.setTotalRooms(rs.getInt("TotalRooms"));
										
				}		
				
				
				return newHotel;
			}
			
			
			
			return null;
			
				
			
			
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		finally { close(rs, ps, conn); }
	}
	
	public Customer getCustomer(Integer hotelId){
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try {
			
			conn = RM3DataSourceConnectionFactory.getHRR3Connection();
			
			String statement = "SELECT CustomerId from RM3Customer2Hotels where HotelId = ? ";
								
			
			ps = conn.prepareStatement(statement);
			ps.setInt(1, Integer.valueOf(hotelId));
			rs = ps.executeQuery();
			
			if(rs != null ){
				
				Customer customer = null;
				
				if(rs.next()){
					
					customer = new Customer();
					customer.setCustomerId(rs.getInt("CustomerId"));	
				}		
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

	
	public ArrayList<Hotel> findUserHotels(Integer userId, Integer customerId){
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try {
			
			conn = RM3DataSourceConnectionFactory.getHRR3Connection();
			
			String statement = "SELECT uh.UserId, uh.CustomerId, h.HotelId, h.BrandId, h.LegalName,hd.RegionId, hd.SnapshotId, hd.ssrSnapshotId, hd.ActualsVendorHotelId, hd.GroupVendorHotelId, h.Address1, h.City, h.TotalRooms from RM3HotelDetails hd " +
								" inner join Hotels h on hd.HotelId=h.HotelId " +
								" inner join RM3Customer2Hotels ch on h.HotelId=ch.HotelId " +
								" inner join RM3Customer2HotelUsers uh on ch.CustomerId=uh.CustomerId " +
								" and ch.HotelId=uh.HotelId " +
								" and ch.CustomerId=? and uh.UserId=? order by h.LegalName";
			
			
			ps = conn.prepareStatement(statement);
			ps.setInt(1, Integer.valueOf(customerId));
			ps.setInt(2, Integer.valueOf(userId));
			rs = ps.executeQuery();
			
			ArrayList<Hotel> hotels = null;
			
			if(rs != null ){
				
				hotels = new ArrayList<Hotel>();
				
				while(rs.next()){
					
					Hotel newHotel = new Hotel();
					newHotel.setHotelId(rs.getInt("HotelId"));
					newHotel.setHotelName(rs.getString("LegalName"));
					newHotel.setCity(rs.getString("City"));
					newHotel.setActualsVendorHotelId(rs.getString("ActualsVendorHotelId"));
					newHotel.setGroupVendorHotelId(rs.getString("GroupVendorHotelId"));
					newHotel.setSnapshotId(rs.getInt("SnapshotId"));
					newHotel.setSsrSnapshotId(rs.getInt("ssrSnapshotId"));
					
					
					newHotel.setBrand(rs.getInt("BrandId"));
					newHotel.setRegion(rs.getInt("RegionId"));
					
					newHotel.setTotalRooms(rs.getInt("TotalRooms"));
					
					hotels.add(newHotel);
					
				}		
				
				
				return hotels;
			}
			
			
			
			return null;
			
				
			
			
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		finally { close(rs, ps, conn); }
	}

		
public ArrayList<Hotel> findCustomerHotels(Integer customerId){
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try {
			
			conn = RM3DataSourceConnectionFactory.getHRR3Connection();
			
			String statement = "SELECT ch.CustomerId, h.HotelId, h.LegalName, hd.SnapshotId, h.BrandId,hd.RegionId, hd.ssrSnapshotId, hd.ActualsVendorHotelId, hd.GroupVendorHotelId, h.Address1, h.City, h.TotalRooms from RM3HotelDetails hd " +
					 " inner join Hotels h on hd.HotelId=h.HotelId " +
					 " inner join RM3Customer2Hotels ch on h.HotelId=ch.HotelId " +
					 " and ch.CustomerId=? order by h.LegalName";
			
			
			ps = conn.prepareStatement(statement);
			ps.setInt(1, Integer.valueOf(customerId));	
			rs = ps.executeQuery();
			
			ArrayList<Hotel> hotels = null;
			
			if(rs != null ){
				
				hotels = new ArrayList<Hotel>();
				
				while(rs.next()){
					
					Hotel newHotel = new Hotel();
					newHotel.setHotelId(rs.getInt("HotelId"));
					newHotel.setHotelName(rs.getString("LegalName"));
					newHotel.setCity(rs.getString("City"));
					newHotel.setActualsVendorHotelId(rs.getString("ActualsVendorHotelId"));
					newHotel.setGroupVendorHotelId(rs.getString("GroupVendorHotelId"));
					newHotel.setSnapshotId(rs.getInt("SnapshotId"));
					newHotel.setSsrSnapshotId(rs.getInt("ssrSnapshotId"));
					
					newHotel.setBrand(rs.getInt("BrandId"));
					newHotel.setRegion(rs.getInt("RegionId"));
					newHotel.setTotalRooms(rs.getInt("TotalRooms"));
					
					
					hotels.add(newHotel);
					
				}		
				
				
				return hotels;
			}
			
			
			
			return null;
			
				
			
			
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		finally { close(rs, ps, conn); }
	}

	public int getHotelRooms(int hotelId){
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try{
			conn = RM3DataSourceConnectionFactory.getHRR3Connection();
			
			String statement = "Select TotalRooms from Hotels where hotelId = ?";
			
			ps = conn.prepareStatement(statement);
			ps.setInt(1, hotelId);
			
			rs = ps.executeQuery();
			
			if (rs != null && rs.next()){
				return rs.getInt("TotalRooms");				
			}
			
			return 0;
			
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
		finally { close(rs, ps, conn); }
		}
	
	
	public void changeDataStatus(int hotelId, String startDate, String endDate,  int isActual){
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try{
			conn = RM3DataSourceConnectionFactory.getHRR3Connection();
			
			String statement = "call ftChangeDataStatus(?,?,?,?)";
			
			ps = conn.prepareStatement(statement);
			ps.setInt(1, hotelId);
			ps.setString(2, startDate);
			ps.setString(3, endDate);
			ps.setInt(4, isActual);
			
			rs = ps.executeQuery();
			
			
			
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}
		
		finally { close(rs, ps, conn); }
		}
	
}
