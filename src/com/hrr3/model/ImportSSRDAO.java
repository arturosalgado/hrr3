package com.hrr3.model;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.hrr3.entity.Hotel;
import com.hrr3.entity.ssr.ImportSSRData;


public class ImportSSRDAO extends RM3AbstractDAO {

	public ImportSSRDAO(Hotel currentHotel) {
		// TODO Auto-generated constructor stub
		super(currentHotel);
	}
	
	public void closeCurrentConnection() {
		
		close(this.getCurrentHRR3Connection());
	}
	
	
	public int saveTempSSRImport(ImportSSRData  ssrData, int importSession, int userId){
		System.out.println("*********** saveTempSSRImport - BEGIN - " + new Date() +" ***********");

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try{			
			conn = this.getCurrentHRR3Connection();
			
			String statement = "SET @importsessionSSR = ?";
			ps = conn.prepareStatement(statement);
			
			ps.setInt(1, importSession);
			
			ps.executeUpdate();
			
			statement = "";
			ps.close();
			
			// p_hotel_id,p_comment,p_statdate ,p_ssr_transient,p_ssr_grpblock ,
			//p_ssr_grppu, p_ssr_grprem,p_ssr_contract,p_ssr_demandtdm, p_ssr_pricetd
			//p_a1 ,p_b2 ,p_c3 ,p_d4 ,p_e5 ,
			//p_f6 ,p_g7 ,p_h8 ,p_i9 ,p_hp1,
			//p_hp2 ,p_user_id ,p_import_session
			
			statement = ("{call ftImportSSRFile (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,@importsessionSSR)}");
															
			ps = conn.prepareStatement(statement);
			ps.setInt(1, this.getCurrentHotel().getHotelId());
			ps.setString(2, ssrData.getComment());
			ps.setString(3, ssrData.getStatdate());
			ps.setBigDecimal(4, ssrData.getSsrTransient());
			ps.setBigDecimal(5, ssrData.getSsrGrpblock());
			
			ps.setBigDecimal(6, ssrData.getSsrGrppu());
			ps.setBigDecimal(7, ssrData.getSsrGrprem());
			ps.setBigDecimal(8, ssrData.getSsrContract());
			ps.setBigDecimal(9, ssrData.getSsrDemandtd());
			ps.setBigDecimal(10,  ssrData.getSsrPricetd());
			
			ps.setString(11, ssrData.getA1());
			ps.setString(12, ssrData.getB2());
			ps.setString(13, ssrData.getC3());
			ps.setString(14, ssrData.getD4());
			ps.setString(15, ssrData.getE5());
			
			ps.setString(16, ssrData.getF6());
			ps.setString(17, ssrData.getG7());
			ps.setString(18, ssrData.getH8());
			ps.setString(19, ssrData.getI9());
			ps.setString(20, ssrData.getHp1());
			
			ps.setString(21, ssrData.getHp2());
			ps.setInt(22, userId);
			
			//ps.setInt(22, importSession);
			
			ps.executeUpdate();
			
			statement = "";
			ps.close();
			
			statement = "SELECT @importsessionSSR as _p_out";
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
	       System.out.println(" ***********saveTempSSRImport - END - " + new Date()+ " ***********");
		}

	}
	
	public void fillSSRData(String dateFrom, String dateTo){
		System.out.println("fillSSRData - BEGIN - " + new Date());

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;

		try{

			conn = this.getCurrentHRR3Connection();
			
			// Call STORE PROCEDRE;
			String statement = "call ftFillSSRData(?,?,?)";

			ps = conn.prepareStatement(statement);			
			ps.setString(1, dateFrom);
			ps.setString(2, dateTo);
			ps.setInt(3, this.getCurrentHotel().getHotelId());
			
			ps.executeUpdate();
									

		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//return null;
		}

		finally { close(rs, ps);
			System.out.println("fillSSRData - END - " + new Date());
		}

	}
	
	public int moveSSRData (int importSession, int updateSSR,  int updateComments, 
			int  updateRestrictions, int updatePriceDemandsTurnDows, int  updateBlockedRooms){
		 System.out.println("ImportSSRDato.MoveSSRData- END - " + new Date());
		//	Move summary data
		int result = SSRTranslate(importSession, updateComments, updateRestrictions);
		
		if (result == 0) {
			return 0;
		}
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;		
		
		try{
			conn = this.getCurrentHRR3Connection();
			
			String statement = "SELECT segment_id FROM RM3SegmentsSSR WHERE isActive = 1 and isTotal = 0 and type = 'SSR'";
			ps = conn.prepareStatement(statement);
			
			rs = ps.executeQuery();
			
			if(rs != null){				
				if(rs.next()){	
					
					do{
						 rs.getInt("segment_id");
						
						if(updateSSR == 1  && updatePriceDemandsTurnDows == 1 && updateBlockedRooms == 1 ){					
							
							SSRTranslateSegment(importSession,rs.getInt("segment_id"), 0);
							
						}else{
							
							if(updateSSR == 1){
								SSRTranslateSegment(importSession, rs.getInt("segment_id"), 1);
							}
							
							if (updatePriceDemandsTurnDows == 1){
								SSRTranslateSegment(importSession,rs.getInt("segment_id"), 2);
							}
							
							if( updateBlockedRooms == 1){
								SSRTranslateSegment(importSession, rs.getInt("segment_id"), 3);
							}
								
						}						
					}while (rs.next());	
										
				}
			}
			
			ps.close();
					

			return 1;
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	
		finally { close(rs, ps);
	       System.out.println("ImportSSRDato.MoveSSRData- END - ");
		}	
	
	}
	
	protected void SSRTranslateSegment(int importSession, int segmentId, int importType){
		System.out.println(" ImportSSRDAO.SSRTranslateSegment - BEGIN - ");

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try{
			conn = this.getCurrentHRR3Connection();
			
			String statement = "call ftSSRTranslateSegments(?,?,?,?)";

			ps = conn.prepareStatement(statement);
			ps.setInt(1, this.getCurrentHotel().getHotelId());
			ps.setInt(2, segmentId);
			ps.setInt(3,importSession);			
			ps.setInt(4, importType);
			
			ps.executeUpdate();

			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		finally { close(rs, ps);
	       System.out.println(" ImportSSRDAO.SSRTranslateSegment -> - END - ");
		}		
		
	}
	
	protected int SSRTranslate(int importSession, int updateComments, int  updateRestrictions ){
		
		System.out.println(" ImportSSRDAO.SSRTranslate - BEGIN - ");

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try{
			conn = this.getCurrentHRR3Connection();
			
			String statement = "call ftSSRTranslate(?,?,?)";

			ps = conn.prepareStatement(statement);
			ps.setInt(1,importSession);
			ps.setInt(2, updateComments);
			ps.setInt(3, updateRestrictions);
			
			ps.executeUpdate();
			
			return 1;
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	
		finally { close(rs, ps);
	       System.out.println(" ImportSSRDAO.SSRTranslate -> - END - ");
		}		

	}
	
	
	public int CalculateLRR (String dateFrom, String dateTo){
		
		
		//Date tempDate;
		
		BigDecimal lrr1 =  new BigDecimal(0);
		BigDecimal lrr2 =  new BigDecimal(0);
		BigDecimal lrr3 =  new BigDecimal(0);
		BigDecimal lrr4 =  new BigDecimal(0);
		BigDecimal lrr5 =  new BigDecimal(0);
		BigDecimal lrr6 =  new BigDecimal(0);
		BigDecimal lrr7 =  new BigDecimal(0);
		BigDecimal lrr8 =  new BigDecimal(0);
		BigDecimal lrr9 =  new BigDecimal(0); 
		BigDecimal lrrhp2 =  new BigDecimal(0);

		String lowRetRt = "";
		String ratecat1 = "";
		String ratecat2 = "";
		String ratecat3 = "";
		String ratecat4 = "";
		String ratecat5 = "";
		String ratecat6 = "";
		String ratecat7 = "";
		String ratecat8 = "";
		String ratecat9 = "";
		String hp2 = "";
		
		BigDecimal bdhp2 = new BigDecimal(0);
		
		BigDecimal tempLRR = new BigDecimal(0);
		int col ;
		int stop;
		
		Date dDateFrom = null;
		Date dDateTo = null;
		Date dDateCalc = null;
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			
			dDateFrom = dateFormat.parse(dateFrom);
			dDateTo =  dateFormat.parse(dateTo);
			dDateCalc = dDateFrom;
			
			
			while (dDateCalc.compareTo(dDateTo)<= 0){
				
				String statement = "SELECT lrr1, lrr2, lrr3, lrr4, lrr5, lrr6, lrr7, lrr8, lrr9, ratecat1, ratecat2, ratecat3, " +
						           "ratecat4, ratecat5, ratecat6, ratecat7, ratecat8, ratecat9, hp2, lrrhp2 FROM RM3SSRData " +
						           "WHERE hotel_id = ? and statdate = ?";
				
				
				conn = this.getCurrentHRR3Connection();
				ps = conn.prepareStatement(statement);
				ps.setInt(1, this.getCurrentHotel().getHotelId());
				ps.setString(2, dateFormat.format(dDateCalc));
				
				rs = ps.executeQuery(); 
				
				while(rs != null && rs.next()){
					lrr1 = rs.getBigDecimal("lrr1");
					lrr2 = rs.getBigDecimal("lrr2");
					lrr3 = rs.getBigDecimal("lrr3");
					lrr4 = rs.getBigDecimal("lrr4");
					lrr5 = rs.getBigDecimal("lrr5");
					lrr6 = rs.getBigDecimal("lrr6");
					lrr7 = rs.getBigDecimal("lrr7");
					lrr8 = rs.getBigDecimal("lrr8");
					lrr9 = rs.getBigDecimal("lrr9"); 					

					ratecat1 = rs.getString("ratecat1");
					ratecat2 = rs.getString("ratecat2");
					ratecat3 = rs.getString("ratecat3");
					ratecat4 = rs.getString("ratecat4");
					ratecat5 = rs.getString("ratecat5");
					ratecat6 = rs.getString("ratecat6");
					ratecat7 = rs.getString("ratecat7");
					ratecat8 = rs.getString("ratecat8");
					ratecat9 = rs.getString("ratecat9");
					hp2 =  rs.getString("h2");
					lrrhp2 = rs.getBigDecimal("lrrhp2");
				}
				
				if (!hp2.isEmpty() && hp2 != "" && hp2!= null ){
					
					hp2.replace("$", "");
					
					bdhp2 = new BigDecimal(hp2);
					
					tempLRR =  new BigDecimal(0);
					
					// lrr >= hp2
					if ((lrr9.compareTo(bdhp2) >= 0)  && (tempLRR.compareTo(BigDecimal.ZERO) == 0))
						tempLRR = lrr9;
					
					if ((lrr8.compareTo(bdhp2) >= 0)  && (tempLRR.compareTo(BigDecimal.ZERO) == 0))
						tempLRR = lrr8;
					
					if ((lrr7.compareTo(bdhp2) >= 0)  && (tempLRR.compareTo(BigDecimal.ZERO) == 0))
						tempLRR = lrr7;
					
					if ((lrr7.compareTo(bdhp2) >= 0)  && (tempLRR.compareTo(BigDecimal.ZERO) == 0))
						tempLRR = lrr7;
					
					if ((lrr6.compareTo(bdhp2) >= 0)  && (tempLRR.compareTo(BigDecimal.ZERO) == 0))
						tempLRR = lrr6;
					
					if ((lrr5.compareTo(bdhp2) >= 0)  && (tempLRR.compareTo(BigDecimal.ZERO) == 0))
						tempLRR = lrr5;
					
					if ((lrr4.compareTo(bdhp2) >= 0)  && (tempLRR.compareTo(BigDecimal.ZERO) == 0))
						tempLRR = lrr4;
					
					if ((lrr3.compareTo(bdhp2) >= 0)  && (tempLRR.compareTo(BigDecimal.ZERO) == 0))
						tempLRR = lrr3;
					
					if ((lrr2.compareTo(bdhp2) >= 0)  && (tempLRR.compareTo(BigDecimal.ZERO) == 0))
						tempLRR = lrr2;
					
					if ((lrr1.compareTo(bdhp2) >= 0)  && (tempLRR.compareTo(BigDecimal.ZERO) == 0))
						tempLRR = lrr1;
					
					if ((tempLRR.compareTo(BigDecimal.ZERO)== 0) && (bdhp2.compareTo(BigDecimal.ZERO)>0)){
						lowRetRt = "Closed";
					}
				
				}else{
					
					col = 0;
					stop = 0;
					
					if (ratecat1 != ""){
						lowRetRt = "Closed";
					
					}else{
						
						if (ratecat1 == "" && stop == 0) 
							lowRetRt = lrr1.toString();
						else
							stop = 1;
						
						if (ratecat2 == "" && stop == 0) 
							lowRetRt = lrr2.toString();
						else
							stop = 1;
						
						if (ratecat3 == "" && stop == 0) 
							lowRetRt = lrr3.toString();
						else
							stop = 1;
						
						if (ratecat4 == "" && stop == 0) 
							lowRetRt = lrr4.toString();
						else
							stop = 1;
						
						if (ratecat5 == "" && stop == 0) 
							lowRetRt = lrr5.toString();
						else
							stop = 1;
						
						if (ratecat6 == "" && stop == 0) 
							lowRetRt = lrr6.toString();
						else
							stop = 1;
						
						if (ratecat7 == "" && stop == 0) 
							lowRetRt = lrr7.toString();
						else
							stop = 1;
						
						if (ratecat8 == "" && stop == 0) 
							lowRetRt = lrr8.toString();
						else
							stop = 1;
						
						if (ratecat9 == "" && stop == 0) 
							lowRetRt = lrr9.toString();
						else
							stop = 1;
					}
					
					
																	
				}
				
				if (lowRetRt == ""){
					lowRetRt = tempLRR.toString();
				}				
				
				ps.close();
				statement = "";
				
				statement = "UPDATE RM3SSRData SET llr = ? WHERE  hotel_id = ? AND statdate = ?";
				
				ps = conn.prepareStatement(statement);
				ps.setString(1, lowRetRt);
				ps.setInt(2, this.getCurrentHotel().getHotelId());
				ps.setString(3, dateFormat.format(dDateCalc));

				ps.executeUpdate();								
				
				Calendar cal = Calendar.getInstance();
				cal.setTime(dDateCalc);
				cal.add(Calendar.DATE, 1); //add 1 day

				dDateCalc = cal.getTime();
			}
			
			return 1;
			
		} catch (SQLException |ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}									
	}

}
