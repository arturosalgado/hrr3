package com.hrr3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import com.hrr3.entity.RoleType;
import com.hrr3.entity.ScreenPreferences;
import com.hrr3.entity.SecurityType;
import com.hrr3.entity.User;


public class UserDAO extends RM3AbstractDAO {
	
	
	
	public User findUser(String account){
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try {
			
			conn = RM3DataSourceConnectionFactory.getHRR3Connection();
			
			String statement = "select * from Users where Account=? and Active = '1'";
			ps = conn.prepareStatement(statement);
			ps.setString(1, account);
			rs = ps.executeQuery();
			
			if(rs != null && rs.next()){
				
				User user = new User();
				user.setUserId(rs.getInt("UserId"));
				user.setAccount(rs.getString("Account"));
				user.setFirstName(rs.getString("FirstName"));
				user.setLastName(rs.getString("LastName"));
				user.setPassword(rs.getString("Password"));
				
				ScreenPreferences sp = user.getScreenPreferences();
				sp.setRowColor(rs.getString("RowColor"));
				
				if(rs.getInt("RoleId") == RoleType.ADMIN.getValue())
					user.setRole(RoleType.ADMIN);
				else
					user.setRole(RoleType.USER);
				
				user.setRm3Role(new SecurityType(rs.getInt("RoleId")));
				
				return user;
			}
			
			return null;
		
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		finally { close(rs, ps, conn); }
	}
	
	
	public List<User> getUsersByTitle(int titleId, int customerId, int userIdToExclude){
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		List<User> userList = null;
		
		try {
			
			conn = RM3DataSourceConnectionFactory.getHRR3Connection();
			
			//String statement = "select UserId, FirstName, LastName from Users where UserTitleId = ? and UserId not in(?) and Active = '1'";
			String statement = "select u.UserId, u.FirstName, u.LastName from Users u " +
			" where u.UserTitleId = ? and u.UserId in (select UserId from User2Customer uc where uc.CustomerId = ?) and u.UserId not in(?) and Active = '1'"; 

			ps = conn.prepareStatement(statement);
			ps.setInt(1, titleId);
			ps.setInt(2, customerId);
			ps.setInt(3, userIdToExclude);
			rs = ps.executeQuery();
			
			if(rs != null){
				
				userList = new ArrayList<User>();
				User label = new User();
				label.setUserId(0);
				label.setFirstName("-- Select ");
				label.setLastName("User --");
				userList.add(label);
				
				while(rs.next()) {
					
					User user = new User();
					user.setUserId(rs.getInt("UserId"));
					user.setFirstName(rs.getString("FirstName"));
					user.setLastName(rs.getString("LastName"));
					
					userList.add(user);
				}	
				
				return userList;
			}
			
			return null;
		
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		finally { close(rs, ps, conn); }
	}
	
	public ArrayList<String>  getReasonType(){
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		ArrayList<String> lstReasonTypes = new ArrayList<String>(); ;
		
		try {
			
			conn = RM3DataSourceConnectionFactory.getHRR3Connection();
			
			
			String statement = "select * from RM3ContactReasonType"; 
			

			ps = conn.prepareStatement(statement);			
			rs = ps.executeQuery();
			
			if(rs != null){
								
				
				while(rs.next()) {
					lstReasonTypes.add(rs.getString("reasonTypeDescription"));					
				}	
				
				return lstReasonTypes;
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
