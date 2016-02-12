package com.hrr3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import com.hrr3.entity.Region;


public class RegionDAO extends RM3AbstractDAO {
	

	
	public List<Region> getRegions(Integer customer, String firstElementCode){
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		List<Region> regions = new ArrayList<Region>();
		//Build dummy brand to include the Select option
		Region regionTmp = new Region();
		regionTmp.setName(firstElementCode);
		regions.add(regionTmp);
		
		try {
			
			conn = RM3DataSourceConnectionFactory.getHRR3Connection();
			
			String statement = "SELECT * from RM3HotelRegions where CustomerId=?";
			ps = conn.prepareStatement(statement);			
			ps.setInt(1, customer);
			rs = ps.executeQuery();
			
			if(rs != null ){
				
				while(rs.next()){
					
					Region region = new Region();
					region.setId(rs.getInt("RegionId"));
					region.setName(rs.getString("RegionDesc"));
					
					regions.add(region);
				}	
				
				
				return regions;
			}
			
			return null;
			
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		finally { close(rs, ps, conn); }
	}

		

	
}
