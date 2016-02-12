package com.hrr3.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hrr3.entity.Hotel;
import com.hrr3.entity.ssr.SSRMARLeadTime;
import com.hrr3.entity.ssr.SSRSUMARRate;

public class SSRGroupMarDAO extends RM3AbstractDAO {

	public SSRGroupMarDAO(Hotel currentHotel) {
		super(currentHotel);
		// TODO Auto-generated constructor stub
	}
	
	
	public SSRSUMARRate setupLeadTime(){
		System.out.println("*********** SSRGroupMar : setsetupLeadTime - BEGIN - " + new Date() +" ***********");
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;	
				
		
		SSRSUMARRate  marRate = new SSRSUMARRate();
				
		try
		{
			conn = this.getCurrentHRR3Connection();							
			String statement = "select * from RM3SSRSUMARRate WHERE hotel_id = ?";
			
			ps = conn.prepareStatement(statement);
			ps.setInt(1, this.getCurrentHotel().getHotelId());
					
			rs = ps.executeQuery();
			
			while(rs != null && rs.next()){	
								
				marRate.setSun(rs.getInt("sun"));
				marRate.setMon(rs.getInt("mon"));
				marRate.setTue(rs.getInt("tue"));
				marRate.setWed(rs.getInt("wed"));
				marRate.setThu(rs.getInt("thu"));
				marRate.setFri(rs.getInt("fri"));
				marRate.setSat(rs.getInt("sat"));
				marRate.setCpor(rs.getBigDecimal("cpor"));
				marRate.setOccTfdr(rs.getBigDecimal("occ_tfdr"));
			}
						
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}
		
		finally { close(rs, ps); close(conn);}
				
		System.out.println("*********** SSRGroupMar : setsetupLeadTime - END - " + new Date() +" ***********");
		
		return marRate;
	}
	
	public void saveMARRate(SSRSUMARRate marRate,String dateFrom, String dateTo, int customerId){
		System.out.println("*********** SSRGroupMar : saveMARRate - BEGIN - " + new Date() +" ***********");
		
		this.saveLeadTime(marRate);
		
		SSRInputDAO  ssrDAO = new SSRInputDAO(this.getCurrentHotel());
		
		ssrDAO.calculateMARRate(dateFrom, dateTo, customerId);
				
		System.out.println("*********** SSRGroupMar : saveMARRate - END - " + new Date() +" ***********");		
	}
	
	
	public SSRSUMARRate saveLeadTime( SSRSUMARRate marRate){
		System.out.println("*********** SSRGroupMar : saveLeadTime - BEGIN - " + new Date() +" ***********");
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;	
								
				
		try
		{
			conn = this.getCurrentHRR3Connection();							
			String statement = ("{call ftSSRSaveMARLeadTime (?,?,?,?,?,?,?,?,?,?)}");
			
			ps = conn.prepareStatement(statement);
			ps.setInt(1, this.getCurrentHotel().getHotelId());
			ps.setInt(2, marRate.getSun());
			ps.setInt(3, marRate.getMon());
			ps.setInt(4, marRate.getTue());
			ps.setInt(5, marRate.getWed());
			ps.setInt(6, marRate.getThu());
			ps.setInt(7, marRate.getFri());
			ps.setInt(8, marRate.getSat());
			ps.setBigDecimal(9, marRate.getCpor());
			ps.setBigDecimal(10, marRate.getOccTfdr());
					
			rs = ps.executeQuery();						
						
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}
		
		finally { close(rs, ps); close(conn);}
				
		System.out.println("*********** SSRGroupMar : saveLeadTime - END - " + new Date() +" ***********");
		
		return marRate;
	}
	
	
	public List<SSRMARLeadTime> loadSSRData(String dateFrom, String dateTo, int customerId){
		
		System.out.println("*********** SSRGroupMar.getSSRData (Inline Edition) - BEGIN - " + new Date() +" ***********");
				
		ImportSSRDAO  ssrDao =  new ImportSSRDAO(this.getCurrentHotel());
		SSRInputDAO  ssrInputDao =  new SSRInputDAO(this.getCurrentHotel());		
		
		List<SSRMARLeadTime> lstMARRate = new ArrayList<SSRMARLeadTime>();
				
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
					
					SSRMARLeadTime marRateLeadTime = new SSRMARLeadTime();
					
					marRateLeadTime.setLeadTime(rs.getInt("leadtime"));				
					marRateLeadTime.setDow(rs.getString("dow"));
					Date dateObj = rs.getDate("statdate");
					marRateLeadTime.setStatdate(dateObj != null ? dateFormat.format(dateObj) : "");
					marRateLeadTime.setFtTotOcc(rs.getBigDecimal("ft_tot_occ").setScale(0));
					marRateLeadTime.setFtTotOccPcnt(rs.getBigDecimal("ft_tot_occ_pcnt").setScale(1, RoundingMode.HALF_EVEN));
					marRateLeadTime.setOverrides(rs.getString("overrides"));
					marRateLeadTime.setMarrate(rs.getString("MARrate"));
																									
					lstMARRate.add(marRateLeadTime);					
				}
			}						
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}
	
		finally { close(rs, ps, conn);
	       System.out.println(" *********** SSRGroupMar.getSSRData (Inline Edition) - END - " + new Date()+ " ***********");
		}
		
		return lstMARRate;
	}
	
	
   public void updateOverride(String statdate, String override){
		
		System.out.println("*********** SSRGroupMar.updateOverride (Inline Edition) - BEGIN - " + new Date() +" ***********");
				
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try{	
							
			conn = this.getCurrentHRR3Connection();	
			
			String statement = ("{call ftSSRSaveMARRate  (?,?,?)}");
			
			ps = conn.prepareStatement(statement);			
			ps.setInt(1, this.getCurrentHotel().getHotelId());
			ps.setString(2, statdate);
			ps.setString(3, override);
			

			ps.executeUpdate();		
						
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}
	
		finally { close(rs, ps, conn);
	       System.out.println(" *********** SSRGroupMar.updateOverride (Inline Edition) - END - " + new Date()+ " ***********");
		}
				
	}
   
   public String calculateSSRMARRate (String dateFrom, String dateTo, int customerId){
		
	   System.out.println("*********** SSRGroupMar.calculateSSRMARRate (Inline Edition) - BEGIN - " + new Date() +" ***********");
	   	PreparedStatement ps = null;		
		Connection currentConnection = null;
		ResultSet rs = null;
		
		try {
			
			currentConnection = this.getCurrentHRR3Connection();
						
			
			/* p_hotel_id int,
			 * p_date_from datetime,
			 * p_date_to` datetime,
			 * p_customer_id int,
			 * OUT p_MARRate varchar(25)
			 */

			String statement = "call ftSSRCalcMARRate(?,?,?,?,@p_marRate)";

			ps = currentConnection.prepareStatement(statement);	
			ps.setInt(1, this.getCurrentHotel().getHotelId());
			ps.setString(2, dateFrom);
			ps.setString(3, dateTo);
			ps.setInt(4,customerId);			
			
			ps.executeUpdate();
												
			statement = "";
			ps.close();
			
			statement = "SELECT @p_marRate as _MARRate";
			ps = currentConnection.prepareStatement(statement);

			rs = ps.executeQuery();
			
			if (rs != null && rs.next() ){
				return rs.getString("_MARRate");
			}	
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		finally { close(rs, ps,currentConnection);
			System.out.println(" SSRGroupMar.calculateSSRMARRate  - END calculateMARRate - " + new Date());
		}
		
		return "0";
		
	}
}
