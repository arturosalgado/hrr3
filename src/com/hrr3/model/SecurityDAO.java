package com.hrr3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;


public class SecurityDAO extends RM3AbstractDAO {
	

	
	public int isMenuOptionEnabled(int menuOptionId, int roleId){
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try {
			
			conn = RM3DataSourceConnectionFactory.getHRR3Connection();
			
			String statement = "select count(SecurityTypeId) as isAllowed from RM3MenuOptions2UserRoles where menu_option_id = ? and SecurityTypeId = ?";
			ps = conn.prepareStatement(statement);
			ps.setInt(1, menuOptionId);
			ps.setInt(2, roleId);
			rs = ps.executeQuery();
			
			if(rs !=null & rs.next()) {
				return rs.getInt("isAllowed");
			}
			
			return 0;
			
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
		finally { close(rs, ps, conn); }
	}

		

	
}
