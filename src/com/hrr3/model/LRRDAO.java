package com.hrr3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hrr3.entity.Hotel;
import com.hrr3.entity.LRRData;


public class LRRDAO extends RM3AbstractDAO{

	public LRRDAO(Hotel currentHotel) {
		super(currentHotel);
		// TODO Auto-generated constructor stub
	}

	
	public List<LRRData> getSSRData(String dateFrom, String dateTo, int customerId){
		
		System.out.println("*********** LRRDAO.getSSRData (Inline Edition) - BEGIN - " + new Date() +" ***********");
				
		ImportSSRDAO  ssrDao =  new ImportSSRDAO(this.getCurrentHotel());
		SSRInputDAO  ssrInputDao =  new SSRInputDAO(this.getCurrentHotel());		
		
		List<LRRData> lstLRRData = new ArrayList<LRRData>();
				
		try {
			//Fill Data zero.
			ssrDao.fillSSRData(dateFrom, dateTo);
			ssrDao.closeCurrentConnection();
		}
		
		catch (Exception e) {
			// TODO Auto-generated catch block
			if(ssrDao != null) ssrDao.closeCurrentConnection();
			e.printStackTrace();
		}
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try{	
			
			ssrInputDao.calculateMARRate(dateFrom, dateTo, customerId);
			
			conn = this.getCurrentHRR3Connection();	
			
			String statement = ("{call ftSSRgetData  (?,?,?,?)}");
			
			ps = conn.prepareStatement(statement);			
			ps.setInt(1, this.getCurrentHotel().getHotelId());
			ps.setString(2, dateFrom);
			ps.setString(3, dateTo);
			ps.setInt(4, customerId);

			rs = ps.executeQuery();
			
			if(rs != null ){	
				SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

				while(rs.next()) {
					
					LRRData lrrData = new LRRData();
					
					lrrData.setComment(rs.getString("comments"));					
					lrrData.setDow(rs.getString("dow"));
					Date dateObj = rs.getDate("statdate");
					lrrData.setStatdate(dateObj != null ? dateFormat.format(dateObj) : "");
										
					lrrData.setLrr(rs.getString("lrr"));
					lrrData.setLrr1(rs.getBigDecimal("lrr1"));
					lrrData.setLrr2(rs.getBigDecimal("lrr2"));
					lrrData.setLrr3(rs.getBigDecimal("lrr3"));
					lrrData.setLrr4(rs.getBigDecimal("lrr4"));
					lrrData.setLrr5(rs.getBigDecimal("lrr5"));
					lrrData.setLrr6(rs.getBigDecimal("lrr6"));
					lrrData.setLrr7(rs.getBigDecimal("lrr7"));
					lrrData.setLrr8(rs.getBigDecimal("lrr8"));
					lrrData.setLrr9(rs.getBigDecimal("lrr9"));
										
					lstLRRData.add(lrrData);					
				}
			}						
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}
	
		finally { close(rs, ps, conn);
	       System.out.println(" *********** LRRDAO.getSSRData (Inline Edition) - END - " + new Date()+ " ***********");
		}
		
		return lstLRRData;
	}
	
	
	public String updateSSRLRR (LRRData lrrData) throws ParseException{
		
		System.out.println("*********** LRRDAO.updateSSRLRR (Inline Edition) - BEGIN - " + new Date() +" ***********");
		
		String lrr = "0";
		SimpleDateFormat dateFormatDB = new SimpleDateFormat("yyyy/MM/dd");
		SimpleDateFormat dateFormatUI = new SimpleDateFormat("MM/dd/yyyy");
		String dateFormatted = dateFormatDB.format(dateFormatUI.parse(lrrData.getStatdate()));
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		
		try{
			conn = this.getCurrentHRR3Connection();	
			
			String statement = ("{call ftUpdateSSRDatalrr(?,?,?,?,?,?,?,?,?,?,?,?)}");
			
			ps = conn.prepareStatement(statement);			
			ps.setInt(1, this.getCurrentHotel().getHotelId());			
			ps.setString(2, dateFormatted);			
			ps.setString(3, lrrData.getComment());
			ps.setBigDecimal(4, lrrData.getLrr1());
			ps.setBigDecimal(5, lrrData.getLrr2());
			ps.setBigDecimal(6, lrrData.getLrr3());
			ps.setBigDecimal(7, lrrData.getLrr4());
			ps.setBigDecimal(8, lrrData.getLrr5());
			ps.setBigDecimal(9, lrrData.getLrr6());
			ps.setBigDecimal(10, lrrData.getLrr7());
			ps.setBigDecimal(11, lrrData.getLrr8());
			ps.setBigDecimal(12, lrrData.getLrr9());
			
			ps.executeUpdate();
						
			lrr = calSSRLRR(this.getCurrentHotel().getHotelId(), dateFormatted, dateFormatted);						
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}
	
		finally { close(rs, ps, conn);
	       System.out.println(" *********** LRRDAO.getSSRData (Inline Edition) - " + new Date()+ " ***********");
		}
		
		return lrr;
	}
	
	
	public String calSSRLRR(int hotelId, String dateFrom, String dateTo){
		
		System.out.println("*********** LRRDAO.calSSRLRR (Inline Edition) - BEGIN - " + new Date() +" ***********");
		
		String lrr = "";
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try{
			conn = this.getCurrentHRR3Connection();	
			
			String statement = "SET @lowRetRt = 0";
			ps = conn.prepareStatement(statement);					
			
			ps.executeUpdate();
			
			statement = "";
			ps.close();
			
			statement = ("{call ftSSRCalcLRR(?,?,?,@lowRetRt)}");
			
			ps = conn.prepareStatement(statement);
			ps.setString(1, dateFrom);
			ps.setString(2, dateTo);
			ps.setInt(3, this.getCurrentHotel().getHotelId());
			
			ps.executeUpdate();
			
			statement = "";
			ps.close();
			
			statement = "SELECT @lowRetRt as lowRetRtValue";
			ps = conn.prepareStatement(statement);

			rs = ps.executeQuery();
			
			if (rs != null && rs.next() ){
				lrr = rs.getString("lowRetRtValue");
			}	
			
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}
	
		finally { close(rs, ps, conn);
	       System.out.println(" *********** LRRDAO.calSSRLRR (Inline Edition) - END - " + new Date()+ " ***********");
		}
		
		return lrr;
		
	}
}
