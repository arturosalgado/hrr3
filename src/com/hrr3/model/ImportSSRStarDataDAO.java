package com.hrr3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.hrr3.entity.Hotel;
import com.hrr3.entity.ssrMigration.SSRSnapshotStarData;

public class ImportSSRStarDataDAO extends RM3AbstractDAO {

	public ImportSSRStarDataDAO(Hotel currentHotel) {
		// TODO Auto-generated constructor stub
		super(currentHotel);
	}
	
	
	public void closeCurrentConnection() {		
		close(this.getCurrentHRR3Connection());
	}
	
	/**
	 * Save comp and summary sheet
	 * @param starData (SSRSnapshotStarData)
	 * @return startId (import session  if values is 0 then error)
	 */
	
	public int saveStarDataTemp (SSRSnapshotStarData   starData, int importStarSession){
		System.out.println("*********** saveStarDataTemp - BEGIN - " + new Date() +" ***********");

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try{			
			conn = this.getCurrentHRR3Connection();	
			
			String statement = "SET @starId = ?";
			ps = conn.prepareStatement(statement);
			ps.setInt(1, importStarSession);
									
			ps.executeUpdate();
			
			statement = "";
			ps.close();
			
						
			statement = ("{call ftSSRImportStarData (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,@starId)}");
															
			ps = conn.prepareStatement(statement);
			ps.setInt(1, this.getCurrentHotel().getHotelId());
			ps.setString(2, starData.getType());
			ps.setInt(3, starData.getGroup());
			ps.setInt(4, starData.getSequence());
			ps.setString(5, starData.getStarMonth());
			ps.setInt(6, starData.getStarYear());
			ps.setString(7, starData.getMonthCaption());
			ps.setString(8, starData.getHotelCaption());
			ps.setString(9, starData.getTract());
			ps.setString(10, starData.getTractScale());			
			ps.setString(11, starData.getOccProp());
			ps.setString(12, starData.getOccPropPc());
			ps.setString(13, starData.getOccCompset());
			ps.setString(14, starData.getOccCompsetPc());
			ps.setString(15, starData.getOccIndex());
			ps.setString(16, starData.getOccIndexPc());
			ps.setString(17, starData.getArrProp());
			ps.setString(18, starData.getArrPropPc());
			ps.setString(19, starData.getArrCompset());
			ps.setString(20, starData.getArrCompsetPc());
			ps.setString(21, starData.getArrIndex());
			ps.setString(22, starData.getArrIndexPc());
			ps.setString(23, starData.getRevparProp());
			ps.setString(24, starData.getRevparPropPc());
			ps.setString(25, starData.getRevparCompset());
			ps.setString(26, starData.getRevparCompsetPc());
			ps.setString(27, starData.getRevparIndex());
			ps.setString(28, starData.getRevparIndexPc());
			ps.setString(29, starData.getMktshSupply());
			ps.setString(30, starData.getMktshDemand());
			ps.setString(31, starData.getMktshRev());
			
			ps.executeUpdate();
			
			statement = "";
			ps.close();
			
			statement = "SELECT @starId as _p_out";
			ps = conn.prepareStatement(statement);

			rs = ps.executeQuery();
			
			if (rs != null && rs.next() ){
				return rs.getInt("_p_out");
			}	
			
			return 0;
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}

		finally { close(rs, ps);
	       System.out.println(" ***********saveStarDataTemp - END - " + new Date()+ " ***********");
		}

	}
	
	/**
	 * Move data
	 * @param importStarSession 
	 * @return newstartId  if values is 0 then error
	 */
	
	public int moveStarData (int importStarSession){
		System.out.println("*********** moveStarData - BEGIN - " + new Date() +" ***********");

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try{			
			conn = this.getCurrentHRR3Connection();	
			
			String statement = "SET @newStarId= 0";
			ps = conn.prepareStatement(statement);
									
			ps.executeUpdate();
			
			statement = "";
			ps.close();
			
						
			statement = ("{call ftSSRMoveStar (?,?,@newStarId)}");
															
			ps = conn.prepareStatement(statement);
			ps.setInt(1, importStarSession);
			ps.setInt(2, getCurrentHotel().getHotelId());
			
			ps.executeUpdate();
			
			statement = "";
			ps.close();
			
			statement = "SELECT @newStarId as _p_out";
			ps = conn.prepareStatement(statement);

			rs = ps.executeQuery();
			
			if (rs != null && rs.next() ){
				return rs.getInt("_p_out");
			}	
			
			return 0;
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}

		finally { close(rs, ps);
	       System.out.println(" ***********moveStarData - END - " + new Date()+ " ***********");
		}

	}
	
	public void deleteStarTemp(int importStarSession){
		
		System.out.println("*********** deleteStarTemp - BEGIN - " + new Date() +" ***********");

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try{			
			conn = this.getCurrentHRR3Connection();						
			
			
			String statement = ("DELETE FROM  RM3SSRImportStarData WHERE star_id = ?");
															
			ps = conn.prepareStatement(statement);
			ps.setInt(1, importStarSession);						
			
			
			ps.executeUpdate();
						
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}

		finally { close(rs, ps);
	       System.out.println(" ***********deleteStarTemp - END - " + new Date()+ " ***********");
		}
		
	}

}
