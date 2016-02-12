package com.hrr3.model.input;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import com.hrr3.entity.Hotel;
import com.hrr3.model.RM3AbstractDAO;

public class InputDAO extends RM3AbstractDAO{

	public InputDAO(Hotel currentHotel) {
		super(currentHotel);
		// TODO Auto-generated constructor stub
	}
	
	
	protected void hasTotalAndSegmenetDataForDate(String dateFrom, String dateTo, int customerId, String Type){
		System.out.println("hasTotalAndSegmenetDataForDate - BEGIN - " + new Date());

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;

		try{

			conn = this.getCurrentHRR3Connection();						
			
			String statement = "{call fillInputTotalAndSegmentData(?,?,?,?,?)}";						

			ps = conn.prepareStatement(statement);
			ps.setInt(1, this.getCurrentHotel().getHotelId());
			ps.setInt(2, customerId);
			ps.setString(3, Type);
			ps.setString(4, dateFrom);
			ps.setString(5, dateTo);
			

			ps.executeUpdate();
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally { close(rs, ps,conn);
	       System.out.println("hasTotalAndSegmenetDataForDate - END - " + new Date());
		}
	}
	
	protected void hasDataForDate(String dateFrom, String dateTo){

		System.out.println("hasDataForDate - BEGIN - " + new Date());

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;

		try{

			conn = this.getCurrentHRR3Connection();
		
			String statement = "{call fillInputTotalData(?,?,?)}";

			ps = conn.prepareStatement(statement);
			ps.setInt(1, this.getCurrentHotel().getHotelId());
			ps.setString(2, dateFrom);
			ps.setString(3, dateTo);

			ps.executeUpdate();
					

		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//return null;
		}

		finally { close(rs, ps,conn);
			System.out.println("hasDataForDate - END - " + new Date());
		}

	}

	protected void hasDataSegmentForDate(String dateFrom, String dateTo, int customerId, String Type){
		System.out.println("hasDataSegmentForData - BEGIN - " + new Date());

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;

		try{

			conn = this.getCurrentHRR3Connection();
			
			//Martin´s hotels have type = 'DP'  when they should be 'TM', so in the query removes the type.
			//We need to know if there is data in specific date, not care the type.
			
			String statement = "{call fillInputSegmentData(?,?,?,?,?)}";						

			ps = conn.prepareStatement(statement);
			ps.setInt(1, this.getCurrentHotel().getHotelId());
			ps.setString(2, dateFrom);
			ps.setString(3, dateTo);
			ps.setInt(4, customerId);
			ps.setString(5, Type);

			ps.executeUpdate();
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally { close(rs, ps,conn);
	       System.out.println("hasSegmentDataForDate - END - " + new Date());
		}
	}
	
	protected void insertSegmentData (int customerId, String statdate, String type){
		System.out.println("insertSegmentData - BEGIN - " + new Date());

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;

		try{
			conn = this.getCurrentHRR3Connection();
			String statement = "insert into RM3TransientSegmentData (segment_id,hotel_id,statdate,type,tot_occ,tot_rev," +
				"tot_adr,def_occ,def_rev,def_adr,ten_occ,ten_rev,ten_adr,created_ts) " +
			    "select segment_id,?,?,?,0,0,0,0,0,0,0,0,0,now() from RM3Segments where customer_id=? " +
			    "and type=? and istotal = 0 and isactive=1 order by sequence";

			ps = conn.prepareStatement(statement);
			ps.setInt(1, this.getCurrentHotel().getHotelId());
			ps.setString(2, statdate);
			ps.setString(3, type);
			ps.setInt(4, customerId);
			ps.setString(5, type);

			ps.executeUpdate();

		}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			finally { close(rs, ps); System.out.println("insertSegmentData - END - " + new Date()); }
	}

	
	public void insertEmptyData(String statdate){

		System.out.println("insertEmptyData - BEGIN - " + new Date());

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;

		try {

			conn =  this.getCurrentHRR3Connection();
			String statement;

			statement = "insert into RM3TransientData (hotel_id,statdate,comments,isException,dow,isActual," +
						"tot_occ_pct,tot_adr,tot_rev_par,tot_occ_rooms,tot_rev,gtot_occ,gtot_rev,gtot_adr," +
						"gdef_occ,gdef_rev,gdef_adr,gten_occ,gten_rev,gten_adr,created_ts) values" +
						"(?,?,'',0,DATE_FORMAT(?,'%W'),0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,now())";

			ps = conn.prepareStatement(statement);
			ps.setInt(1, this.getCurrentHotel().getHotelId());
			ps.setString(2, statdate);
			ps.setString(3, statdate);

			ps.executeUpdate();


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//return null;
		}

		finally { close(rs, ps); System.out.println("insertData - END - " + new Date());}
	}
	
	
	protected void calculateStatData ( int customerId, String dateFrom, String dateTo, int totalRooms){

		System.out.println("calculateStatData - BEGIN - " + new Date());

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;

		try{
			conn = this.getCurrentHRR3Connection();

				String statement = "{call ftCalculateStatData(?,?,?,?,?)}";
				ps = conn.prepareStatement(statement);
				ps.setInt(1, this.getCurrentHotel().getHotelId());
				ps.setString(2, dateFrom);
				ps.setString(3, dateTo);
				ps.setInt(4, customerId);
				ps.setInt(5, totalRooms);

				ps.executeUpdate();

				

		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally { close(rs, ps, conn); System.out.println("calculateStatData - END - " + new Date());}
	}

	public BigDecimal displayADR (Integer occ, BigDecimal adr, BigDecimal revenue){

		//To Display ADR: From Telman, there are cases when OCC=0 ADR=0 but there is revenue.  Show ADR as the Revenue
		if((occ == 0) && (adr.compareTo(BigDecimal.ZERO)== 0)){
			return revenue;
		}else{
			return adr.setScale(2, RoundingMode.HALF_EVEN);
		}
	}
	
   public String getSegmentDataType (int segmentId, int customerId){
	   System.out.println("*********** getSegmentDataType (Glance) - BEGIN - " + new Date() +" ***********");

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try{			
			conn = this.getCurrentHRR3Connection();			
			
			String statement = "SELECT data_type  from RM3Segments where segment_id = ? and customer_id = ?";
			
			ps = conn.prepareStatement(statement);
			ps.setInt(1, segmentId);
			ps.setInt(2, customerId);
			
			rs = ps.executeQuery();
			
			if(rs != null ){			
				while(rs.next()) {					
						return rs.getString("data_type");					
				}
			}
			
			return null;
		
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		finally { close( rs, ps,conn);
	       System.out.println(" ***********getSegmentDataType - END - " + new Date()+ " ***********");
		}
		
   }

}
