package com.hrr3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.hrr3.entity.Hotel;
import com.hrr3.entity.ssrMigration.SSRSnapshotDayStar;
import com.hrr3.entity.ssrMigration.SSRSnapshotDayStarData;
import com.hrr3.entity.ssrMigration.SSRSnapshotDayStarHotel;



public class ImportSSRDayStarDAO extends RM3AbstractDAO {

	public ImportSSRDayStarDAO(Hotel currentHotel) {
		// TODO Auto-generated constructor stub
		super(currentHotel);
	}
	
	public void closeCurrentConnection() {
		
		close(this.getCurrentHRR3Connection());
	}
	
	/**
	 * Save Glance sheet
	 * @param dayStar
	 * @return dayStartId (SessionId)
	 */
	public int saveDayStarTemp(SSRSnapshotDayStar  dayStar){		
		
		System.out.println("*********** saveDayStarTemp (Glance) - BEGIN - " + new Date() +" ***********");

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try{			
			conn = this.getCurrentHRR3Connection();	
			
			String statement = "SET @dayStarId = 0";
			ps = conn.prepareStatement(statement);
									
			ps.executeUpdate();
			
			statement = "";
			ps.close();
			
			
			
			statement = ("{call ftSSRImportDayStar (?,?,?,?,?,?,@dayStarId)}");
															
			ps = conn.prepareStatement(statement);
			ps.setInt(1, this.getCurrentHotel().getHotelId());
			ps.setString(2, dayStar.getDateFrom());
			ps.setString(3, dayStar.getDateTo());
			ps.setString(4, dayStar.getCapHotel());
			ps.setString(5, dayStar.getCapHotel2());
			ps.setString(6, dayStar.getCapWeek());
			
			ps.executeUpdate();
			
			statement = "";
			ps.close();
			
			statement = "SELECT @dayStarId as _p_out";
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
	       System.out.println(" ***********saveDayStarTemp (Glance) - END - " + new Date()+ " ***********");
		}

	}
	
	/**
	 * SaveSummary sheet (!Does not exists in excel file. For future use)
	 * @param dayStarData
	 */
	public void saveDayStarDataTemp(SSRSnapshotDayStarData dayStarData){
		
		System.out.println("*********** saveDayStarDataTemp (Summary) - BEGIN - " + new Date() +" ***********");

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try{			
			conn = this.getCurrentHRR3Connection();						
			
			
			String statement = ("{call ftSSRImportDayStarData (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
															
			ps = conn.prepareStatement(statement);
			ps.setInt(1, dayStarData.getDaystarId());
			ps.setInt(2, this.getCurrentHotel().getHotelId());						
			ps.setString(3, dayStarData.getType());
			ps.setInt(4, dayStarData.getTab());
			ps.setInt(5, dayStarData.getSequence());
			ps.setString(6, dayStarData.getDatethisyr());
			ps.setString(7, dayStarData.getDatelastyr());
			ps.setString(8, dayStarData.getDaterange());
			ps.setString(9, dayStarData.getCurrentyr());
			ps.setString(10, dayStarData.getLastyr());
			ps.setString(11, dayStarData.getOccProp());			
			ps.setString(12, dayStarData.getOccPropPc());
			ps.setString(13, dayStarData.getOccCompset());
			ps.setString(14, dayStarData.getOccCompsetPc());
			ps.setString(15, dayStarData.getOccIndex());
			ps.setString(16, dayStarData.getOccIndexPc());
			ps.setString(17, dayStarData.getArrProp());
			ps.setString(18, dayStarData.getArrPropPc());
			ps.setString(19, dayStarData.getArrCompset());
			ps.setString(20, dayStarData.getArrCompsetPc());
			ps.setString(21, dayStarData.getArrIndex());
			ps.setString(22, dayStarData.getArrIndexPc());
			ps.setString(23, dayStarData.getRevparProp());
			ps.setString(24, dayStarData.getRevparPropPc());
			ps.setString(25, dayStarData.getRevparCompset());
			ps.setString(26, dayStarData.getRevparCompsetPc());
			ps.setString(27, dayStarData.getRevparIndex());
			ps.setString(28, dayStarData.getRevparIndexPc());
			
			ps.executeUpdate();
						
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}

		finally { close(rs, ps);
	       System.out.println(" *********** saveDayStarDataTemp (Summary) - END - " + new Date()+ " ***********");
		}
		
	}
	
	/**
	 * Save Response sheet
	 * @param dayStarHotel
	 */
	public void saveDayStarHotelTemp(SSRSnapshotDayStarHotel dayStarHotel){
		
		System.out.println("*********** saveDayStarHotelTemp (Response) - BEGIN - " + new Date() +" ***********");

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try{			
			conn = this.getCurrentHRR3Connection();						
			
			
			String statement = ("{call ftSSRImportDayStarHotel (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
															
			ps = conn.prepareStatement(statement);
			ps.setInt(1, dayStarHotel.getDaystarId());			
			ps.setInt(2, this.getCurrentHotel().getHotelId());
			ps.setString(3, dayStarHotel.getStrId());
			ps.setString(4, dayStarHotel.getHotel());
			ps.setString(5, dayStarHotel.getCity());
			ps.setString(6, dayStarHotel.getZip());
			ps.setString(7, dayStarHotel.getPhone());
			ps.setString(8, dayStarHotel.getRooms());
			ps.setString(9, dayStarHotel.getOpendate());
			ps.setString(10, dayStarHotel.getDate1());
			ps.setString(11, dayStarHotel.getDate2());
			ps.setString(12, dayStarHotel.getDate3());
			ps.setString(13, dayStarHotel.getDate4());
			ps.setString(14, dayStarHotel.getDate5());
			ps.setString(15, dayStarHotel.getDate6());
			ps.setString(16, dayStarHotel.getDate7());
			ps.setString(17, dayStarHotel.getDataDaystar1());
			ps.setString(18, dayStarHotel.getDataDaystar2());
			ps.setString(19, dayStarHotel.getDataDaystar3());
			ps.setString(20, dayStarHotel.getDataDaystar4());			
			ps.setString(21, dayStarHotel.getDataDaystar5());
			ps.setString(22, dayStarHotel.getDataDaystar6());
			ps.setString(23, dayStarHotel.getDataDaystar7());
			ps.setString(24, dayStarHotel.getDataSeg1());
			ps.setString(25, dayStarHotel.getDataSeg2());
			ps.setString(26, dayStarHotel.getDataSeg3());
			ps.setString(27, dayStarHotel.getDataSeg4());
			ps.setString(28, dayStarHotel.getDataSeg5());
			ps.setString(29, dayStarHotel.getDataSeg6());
			ps.setString(30, dayStarHotel.getDataSeg7());
			ps.setString(31, dayStarHotel.getDataFb1());
			ps.setString(32, dayStarHotel.getDataFb2());
			ps.setString(33, dayStarHotel.getDataFb3());
			ps.setString(34, dayStarHotel.getDataFb4());
			ps.setString(35, dayStarHotel.getDataFb5());
			ps.setString(36, dayStarHotel.getDataFb6());
			ps.setString(37, dayStarHotel.getDataFb7());
			
			
			ps.executeUpdate();
						
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}

		finally { close(rs, ps);
	       System.out.println(" ***********saveDayStarHotelTemp (Response) - END - " + new Date()+ " ***********");
		}
		
	}
	
	public void deleteDayStarTemp(int importSession){
		//When occur error and importSession <> 0 
		System.out.println("*********** deleteDayStarTemp - BEGIN - " + new Date() +" ***********");

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try{			
			conn = this.getCurrentHRR3Connection();						
			
			
			String statement = ("{call ftdeleteDayStarTempData (?)}");
															
			ps = conn.prepareStatement(statement);
			ps.setInt(1, importSession);						
			
			
			ps.executeUpdate();
						
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}

		finally { close(rs, ps);
	       System.out.println(" ***********deleteDayStarTemp - END - " + new Date()+ " ***********");
		}
		
	}
	
	public int moveDaySTAR(int importSession){
		// Error if return value is 0 or -1   
		
		System.out.println("*********** moveDaySTAR - BEGIN - " + new Date() +" ***********");

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try{			
			conn = this.getCurrentHRR3Connection();		
			
			String statement = "SET @newDayStar_id = 0";
			ps = conn.prepareStatement(statement);
									
			ps.executeUpdate();
			
			statement = "";
			ps.close();
			
			
			statement = ("{call ftSSRMoveDayStar (?,?, @newDayStar_id)}");
															
			ps = conn.prepareStatement(statement);
			ps.setInt(1, this.getCurrentHotel().getHotelId());
			ps.setInt(2, importSession);						
			
			ps.executeUpdate();

			statement = "";
			ps.close();
			
			statement = "SELECT @newDayStar_id as _pid_out";
			ps = conn.prepareStatement(statement);

			rs = ps.executeQuery();
			
			if (rs != null && rs.next() ){
				return rs.getInt("_pid_out");
			}	
			
			return 0;
						
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();	
			return 0;
		}

		finally { close(rs, ps);
	       System.out.println(" ***********moveDaySTAR - END - " + new Date()+ " ***********");
		}
		
	}
}
