package com.hrr3.model;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.hrr3.entity.Hotel;
import com.hrr3.entity.RateCategory;

public class RateCategoryDAO extends RM3AbstractDAO{

	public RateCategoryDAO(Hotel currentHotel) {
		super(currentHotel);
		// TODO Auto-generated constructor stub
	}
	
	public RateCategory populateColumnData (){
		
		RateCategory rateCat = new RateCategory();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection currentConnection = null;
		
		try {
									
			currentConnection = this.getCurrentHRR3Connection();
			
			String statement = "select * from RM3SSRRateCat where hotel_id = ?";

			ps = currentConnection.prepareStatement(statement);			

			ps.setInt(1, this.getCurrentHotel().getHotelId());
			
			rs = ps.executeQuery();
			
			if(rs != null ){			

				while(rs.next()) {
					
					rateCat.setColumnNameA(rs.getString("A0_Name"));
					rateCat.setColumnAHidden(rs.getInt("A0_is_Hidden"));
					rateCat.setColumnNameB(rs.getString("B1_Name"));
					rateCat.setColumnBHidden(rs.getInt("B1_is_Hidden"));
					rateCat.setColumnNameC(rs.getString("C2_Name"));
					rateCat.setColumnCHidden(rs.getInt("C2_is_Hidden"));
					rateCat.setColumnNameD(rs.getString("D3_Name"));
					rateCat.setColumnDHidden(rs.getInt("D3_is_Hidden"));
					rateCat.setColumnNameE(rs.getString("E4_Name"));
					rateCat.setColumnEHidden(rs.getInt("E4_is_Hidden"));
					rateCat.setColumnNameF(rs.getString("F5_Name"));
					rateCat.setColumnFHidden(rs.getInt("F5_is_Hidden"));
					rateCat.setColumnNameG(rs.getString("G6_Name"));
					rateCat.setColumnGHidden(rs.getInt("G6_is_Hidden"));
					rateCat.setColumnNameH(rs.getString("H7_Name"));
					rateCat.setColumnHHidden(rs.getInt("H7_is_Hidden"));
					rateCat.setColumnNameI(rs.getString("I8_Name"));
					rateCat.setColumnIHidden(rs.getInt("I8_is_Hidden"));
					rateCat.setColumnNameHP1(rs.getString("HP1_Name"));
					rateCat.setColumnHP1Hidden(rs.getInt("HP1_is_Hidden"));
					rateCat.setColumnNameHP2(rs.getString("HP2_Name"));
					rateCat.setColumnHP2Hidden(rs.getInt("HP2_is_Hidden"));
					
					rateCat.setOversell(rs.getInt("Include_Oversell"));
				}
			}
									
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		finally { close(rs, ps,currentConnection);

		}
						
		return rateCat;
	}
    
	public void saveRateCategoryData(RateCategory rateCat){
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection currentConnection = null;
		
		try {
									
			currentConnection = this.getCurrentHRR3Connection();
			
			String statement = "select COUNT(hotel_id) as exist from RM3SSRRateCat where hotel_id = ?";

			ps = currentConnection.prepareStatement(statement);			

			ps.setInt(1, this.getCurrentHotel().getHotelId());					
			
			rs = ps.executeQuery();
			int result = 0;
			
			if(rs != null ){			

				while(rs.next()) {
					result = rs.getInt("exist");
				}
			}	
			
			if (result == 0) {
				insertRateCategoryData(rateCat);
			}else{
				updateRateCategoryData(rateCat);
			}
			
									
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		finally { close(rs, ps,currentConnection);

		}							
	}
	
	public void insertRateCategoryData(RateCategory rateCat){
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection currentConnection = null;
		
		try {
									
			currentConnection = this.getCurrentHRR3Connection();
			
			String statement = "INSERT INTO RM3SSRRateCat VALUES(?,?,?,?,?,?,?,?,?,? ,?,?,?,?,?,?,?,?,?,?,?,?,?,?,NOW(),NOW());";

			ps = currentConnection.prepareStatement(statement);			

			ps.setInt(1, this.getCurrentHotel().getHotelId());
			
			ps.setString(2, rateCat.getColumnNameA());
			ps.setInt(3, rateCat.getColumnAHidden());
			ps.setString(4, rateCat.getColumnNameB());
			ps.setInt(5, rateCat.getColumnBHidden());
			ps.setString(6, rateCat.getColumnNameC());
			ps.setInt(7, rateCat.getColumnCHidden());
			ps.setString(8, rateCat.getColumnNameD());
			ps.setInt(9, rateCat.getColumnDHidden());
			ps.setString(10, rateCat.getColumnNameE());
			ps.setInt(11, rateCat.getColumnEHidden());
			ps.setString(12, rateCat.getColumnNameF());
			ps.setInt(13, rateCat.getColumnFHidden());
			ps.setString(14, rateCat.getColumnNameG());
			ps.setInt(15, rateCat.getColumnGHidden());
			ps.setString(16, rateCat.getColumnNameH());
			ps.setInt(17, rateCat.getColumnHHidden());
			ps.setString(18, rateCat.getColumnNameI());
			ps.setInt(19, rateCat.getColumnIHidden());
			
			ps.setString(20, rateCat.getColumnNameHP1());
			ps.setInt(21, rateCat.getColumnHP1Hidden());
			ps.setString(22, rateCat.getColumnNameHP2());
			ps.setInt(23, rateCat.getColumnHP2Hidden());
			
			ps.setInt(24, rateCat.getOversell());
			
			ps.executeUpdate();
			
			
									
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		finally { close(rs, ps,currentConnection);

		}							
	}
	
	
	public void updateRateCategoryData(RateCategory rateCat){
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection currentConnection = null;
		
		try {
									
			currentConnection = this.getCurrentHRR3Connection();
			
			String statement = "UPDATE RM3SSRRateCat  set  A0_Name = ? , A0_is_Hidden = ?, B1_Name = ?, B1_is_Hidden = ?, C2_Name = ?, C2_is_Hidden = ?, " +
					"D3_Name = ?, D3_is_Hidden = ?, E4_Name = ?, E4_is_Hidden = ?, F5_Name = ?, F5_is_Hidden = ?, G6_Name = ?, G6_is_Hidden = ? ,H7_Name = ?, " +
					"H7_is_Hidden = ?, I8_Name = ?, I8_is_Hidden = ?, HP1_Name =?, HP1_is_Hidden = ?, HP2_Name = ?, HP2_is_Hidden = ?, Include_Oversell = ?, modified_ts = NOW() where hotel_id=?;";

			ps = currentConnection.prepareStatement(statement);			
					
			ps.setString(1, rateCat.getColumnNameA());
			ps.setInt(2, rateCat.getColumnAHidden());
			ps.setString(3, rateCat.getColumnNameB());
			ps.setInt(4, rateCat.getColumnBHidden());
			ps.setString(5, rateCat.getColumnNameC());
			ps.setInt(6, rateCat.getColumnCHidden());
			ps.setString(7, rateCat.getColumnNameD());
			ps.setInt(8, rateCat.getColumnDHidden());
			ps.setString(9, rateCat.getColumnNameE());
			ps.setInt(10, rateCat.getColumnEHidden());
			ps.setString(11, rateCat.getColumnNameF());
			ps.setInt(12, rateCat.getColumnFHidden());
			ps.setString(13, rateCat.getColumnNameG());
			ps.setInt(14, rateCat.getColumnGHidden());
			ps.setString(15, rateCat.getColumnNameH());
			ps.setInt(16, rateCat.getColumnHHidden());
			ps.setString(17, rateCat.getColumnNameI());
			ps.setInt(18, rateCat.getColumnIHidden());
			
			ps.setString(19, rateCat.getColumnNameHP1());
			ps.setInt(20, rateCat.getColumnHP1Hidden());
			ps.setString(21, rateCat.getColumnNameHP2());
			ps.setInt(22, rateCat.getColumnHP2Hidden());
			
			ps.setInt(23, rateCat.getOversell());
			ps.setInt(24, this.getCurrentHotel().getHotelId());
			ps.executeUpdate();
			
			
									
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		finally { close(rs, ps,currentConnection);

		}
							
	}
}
