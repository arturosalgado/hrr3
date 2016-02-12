package com.hrr3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.NamingException;

import com.hrr3.entity.ProductType;


public class ProductDAO extends RM3AbstractDAO {
	

	
public ArrayList<ProductType> findProductsByUserId(Integer userId){
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try {
			
			conn = RM3DataSourceConnectionFactory.getHRR3Connection();
			
			String statement = "SELECT ProductId from User2Products where UserId=?";
			ps = conn.prepareStatement(statement);
			ps.setInt(1, Integer.valueOf(userId));
			rs = ps.executeQuery();
			
			ArrayList<ProductType> products = null;
			
			if(rs != null ){
				
				products = new ArrayList<ProductType>();
				
				while(rs.next()){
					
					if(rs.getInt("ProductId") == ProductType.RM3.getValue()){
						products.add(ProductType.RM3);
						System.out.println("*************** Adding Product["+ rs.getInt("ProductId") +"] = RM3 *****************");
					}
												
					else if(rs.getInt("ProductId") == ProductType.EPVC.getValue()){
						System.out.println("*************** Adding Product["+ rs.getInt("ProductId") +"] = EPVC *****************");
						products.add(ProductType.EPVC);
					}
						
					
					
				}		
				
				
				return products;
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
