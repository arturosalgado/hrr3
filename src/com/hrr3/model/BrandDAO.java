package com.hrr3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import com.hrr3.entity.Brand;


public class BrandDAO extends RM3AbstractDAO {
	

	
	public List<Brand> getBrands(String firstElementCode){
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		List<Brand> brands = new ArrayList<Brand>();
		//Build dummy brand to include the Select option
		Brand brandTmp = new Brand();
		brandTmp.setName(firstElementCode);
		brands.add(brandTmp);
		
		try {
			
			conn = RM3DataSourceConnectionFactory.getHRR3Connection();
			
			String statement = "SELECT * from HotelBrands";
			ps = conn.prepareStatement(statement);			
			rs = ps.executeQuery();
			
			if(rs != null ){
				
				while(rs.next()){
					
					Brand brand = new Brand();
					brand.setId(rs.getInt("HotelBrandId"));
					brand.setName(rs.getString("HotelBrandDesc"));
					
					brands.add(brand);
				}		
				
				
				return brands;
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
