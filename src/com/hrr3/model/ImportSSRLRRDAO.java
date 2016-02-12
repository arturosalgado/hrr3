package com.hrr3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.hrr3.entity.Hotel;
import com.hrr3.entity.ssr.ImportSSRLRRData;

public class ImportSSRLRRDAO extends RM3AbstractDAO {

	public ImportSSRLRRDAO(Hotel currentHotel) {
		// TODO Auto-generated constructor stub
		super(currentHotel);
	}
	
	public void closeCurrentConnection() {
		
		close(this.getCurrentHRR3Connection());
	}
	
	
	public int saveTempSSRLRRImport(ImportSSRLRRData  LRRData, int importSession, int userId){
		System.out.println("*********** saveTempSSRLRRImport - BEGIN - " + new Date() +" ***********");

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try{			
			conn = this.getCurrentHRR3Connection();
			
			String statement = "SET @importsessionLRR = ?";
			ps = conn.prepareStatement(statement);
			
			ps.setInt(1, importSession);
			
			ps.executeUpdate();
			
			statement = "";
			ps.close();
			
			//`p_hotel_id` int,
			//`p_statdate` datetime,
			//`p_a1` decimal(18,3),
			//`p_b2` decimal(18,3),
			//`p_c3` decimal(18,3),
			//`p_d4` decimal(18,3),
			//`p_e5` decimal(18,3),
			//`p_f6` decimal(18,3),
			//`p_g7` decimal(18,3),
			//`p_h8` decimal(18,3),
			//`p_i9` decimal(18,3),
			//`p_hp1` decimal(18,3),
			//`p_hp2` decimal(18,3),
			//`p_user_id` int,
			//INOUT `p_import_session` varchar(50)
			
			statement = "{call ftSSRImportLRRFile (?,?,?,?,?,?,?,?,?,?,?,?,?,?,@importsessionLRR)}";
															
			ps = conn.prepareStatement(statement);
			ps.setInt(1, this.getCurrentHotel().getHotelId());			
			ps.setString(2, LRRData.getStatdate());
			ps.setBigDecimal(3, LRRData.getLrr1());
			ps.setBigDecimal(4, LRRData.getLrr2());
			ps.setBigDecimal(5, LRRData.getLrr3());
			ps.setBigDecimal(6, LRRData.getLrr4());
			ps.setBigDecimal(7, LRRData.getLrr5());
			ps.setBigDecimal(8, LRRData.getLrr6());
			ps.setBigDecimal(9, LRRData.getLrr7());
			ps.setBigDecimal(10, LRRData.getLrr8());
			ps.setBigDecimal(11, LRRData.getLrr9());
			ps.setBigDecimal(12, LRRData.getLrrHp1());
			ps.setBigDecimal(13, LRRData.getLrrHp2());
			ps.setInt(14, userId);
						
			
			//ps.setInt(22, importSession);
			
			ps.executeUpdate();
			
			statement = "";
			ps.close();
			
			statement = "SELECT @importsessionLRR as _p_out";
			ps = conn.prepareStatement(statement);

			rs = ps.executeQuery();
			
			if (rs != null && rs.next() ){
				return rs.getInt("_p_out");
			}	
			
			return ERROR;
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ERROR;
		}

		finally { close(rs, ps);
	       System.out.println(" ***********saveTempSSRLRRImport - END - " + new Date()+ " ***********");
		}

	}
	
	
	public int moveLRRData (int importSession){
		
		System.out.println(" Start moveLRRData - " + new Date());
	
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;		
		
		try{
			conn = this.getCurrentHRR3Connection();
			
			String statement = "{call ftSSRMoveLRR (?)}";
			
			ps = conn.prepareStatement(statement);
			ps.setInt(1, importSession);
			
			ps.executeUpdate();
									
			ps.close();				

			return OK; 
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ERROR;
		}
	
		finally { close(rs, ps);
	       System.out.println("moveLRRData - END - "  + new Date());
		}	
	
	}
	
	
	public int calcSSRLRR (String dateFrom, String dateTo){
		
		System.out.println(" Start calcSSRLRR - " + new Date());
	
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;		
		
		try{
			conn = this.getCurrentHRR3Connection();
			
			String statement = "{call ftSSRCalcLRR (?,?,?,@lowRetRt)}";
			
			ps = conn.prepareStatement(statement);
			ps.setString(1, dateFrom);
			ps.setString(2, dateTo);
			ps.setInt(3, this.getCurrentHotel().getHotelId());
			
			ps.executeUpdate();
									
			ps.close();				

			return OK; 
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ERROR;
		}
	
		finally { close(rs, ps);
	       System.out.println("calcSSRLRR - END - "  + new Date());
		}	
	
	}
	


}
