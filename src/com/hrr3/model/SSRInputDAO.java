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
import com.hrr3.entity.ssr.SSRInputData;




public class SSRInputDAO extends RM3AbstractDAO{

	public SSRInputDAO(Hotel currentHotel) {
		super(currentHotel);
		// TODO Auto-generated constructor stub
	}
	
	public List<SSRInputData> getSSRInputData(String dateFrom, String dateTo, int userId, int customerId){
		ImportSSRDAO  ssrDao =  new ImportSSRDAO(this.getCurrentHotel());
		SSRSnapshotDAO ssrSnapshotDao = new SSRSnapshotDAO(this.getCurrentHotel());		
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
		
		//Update actuals values
		ssrSnapshotDao.ChangeSSRActuals();
		
		List<SSRInputData> lstSSRData = new ArrayList<SSRInputData>();
		
		//Get input data
		lstSSRData =  this.loadData(dateFrom, dateTo, userId, customerId);
		
		//Get the summary data and add to the list
		lstSSRData = summarySSRData(lstSSRData);
		
		return 	lstSSRData;	
		
			
	}
	
	public  List<SSRInputData> loadData(String dateFrom, String dateTo, int userId, int customerId){
		
		System.out.println("SSRInputDAO loadData - BEGIN - " + new Date());
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection currentConnection = null;
		List<SSRInputData> lstSsrData = new ArrayList<SSRInputData>();
						
		try {
			
			calculateMARRate(dateFrom, dateTo, customerId);
			
			currentConnection = this.getCurrentHRR3Connection();
			
			/* p_StatDateFrom datetime,
			 * p_StatDateTo datetime,
			 * p_hotel_id int,
			 * p_user_id int,
			 * p_snapshot_id int,
			 * p_snapshot_id_pu int,
			 * p_oversell int,
			 * p_customer_id` int
			 */
			String statement = "call ftSSRgetInputSummary2(?,?,?,?,?,?,?,?)";

			ps = currentConnection.prepareStatement(statement);			
			ps.setString(1, dateFrom);
			ps.setString(2, dateTo);
			ps.setInt(3, this.getCurrentHotel().getHotelId());
			ps.setInt(4, userId);
			ps.setInt(5, 0);
			ps.setInt(6, 0);
			ps.setInt(7, 0);
			ps.setInt(8, customerId);
						
			
			//System.out.println("final statmen in SSRInputDA"+statement);
			
			
			rs = ps.executeQuery();
			
			if(rs != null ){

				SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

				while(rs.next()) {
					SSRInputData ssrData = new SSRInputData();
					ssrData.setId(rs.getLong("ssr_id"));
					ssrData.setComment(rs.getString("comments"));
					ssrData.setIsException(rs.getInt("isexception"));
					ssrData.setDow(rs.getString("dow"));
					Date dateObj = rs.getDate("statdate");
					ssrData.setStatdate(dateObj != null ? dateFormat.format(dateObj) : "");
					ssrData.setIsActual(rs.getInt("isActual"));
					ssrData.setOccpcnt(rs.getBigDecimal("occpcnt").setScale(1, RoundingMode.HALF_EVEN));
					ssrData.setTotOcc(rs.getBigDecimal("tot_occ"));
					ssrData.setLrr(rs.getString("lrr"));
					ssrData.setA1(rs.getString("ratecat1"));
					ssrData.setB2(rs.getString("ratecat2"));
					ssrData.setC3(rs.getString("ratecat3"));
					ssrData.setD4(rs.getString("ratecat4"));
					ssrData.setE5(rs.getString("ratecat5"));
					ssrData.setF6(rs.getString("ratecat6"));
					ssrData.setG7(rs.getString("ratecat7"));
					ssrData.setH8(rs.getString("ratecat8"));
					ssrData.setI9(rs.getString("ratecat9"));
					ssrData.setHp1(rs.getString("hp"));
					ssrData.setHp2(rs.getString("hp2"));
					ssrData.setOversellFactor(rs.getString("oversell_factor"));
					ssrData.setRotbTrans(rs.getBigDecimal("rotb_trans"));
					ssrData.setRotbGroup(rs.getBigDecimal("rotb_group"));
					ssrData.setGrpPickedup(rs.getBigDecimal("grp_pickedup"));
					ssrData.setGrpRmsRem(rs.getBigDecimal("grp_rms_rem"));
					ssrData.setRotbCont(rs.getBigDecimal("rotb_cont"));
					ssrData.setGrpDemandtd(rs.getBigDecimal("grp_demandtd"));
					ssrData.setGrpPricetd(rs.getBigDecimal("grp_pricetd"));
					ssrData.setSeasonalmarrate(rs.getBigDecimal("SeasonalMARrate"));
					ssrData.setMarrate(rs.getString("MARrate"));
					
					///Add startDate and endDate info USEFUL !! for Render when updating Summary and Total info (first row)
					ssrData.setStartDate(dateFrom);
					ssrData.setEndDate(dateTo);
					
					lstSsrData.add(ssrData);
					
				}
			}
									
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		finally { close(rs, ps,currentConnection);
			System.out.println("fillSSRData - END - " + new Date());
		}
			
		return lstSsrData;
	}
	
	public void calculateMARRate (String dateFrom, String dateTo, int customerId){
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

			String statement = "call ftSSRCalcMARRate(?,?,?,?,?)";

			ps = currentConnection.prepareStatement(statement);	
			ps.setInt(1, this.getCurrentHotel().getHotelId());
			ps.setString(2, dateFrom);
			ps.setString(3, dateTo);
			ps.setInt(4,customerId);
			ps.setString(5, "@p_marRate");
												
			ps.executeUpdate();
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		finally { close(rs, ps,currentConnection);
			System.out.println(" - END calculateMARRate - " + new Date());
		}
		
	}
	
	public  List<SSRInputData> summarySSRData (List<SSRInputData> lstSSRData){
		
		List<SSRInputData> lstSSRInpuData = new ArrayList<SSRInputData>();
		
		if (lstSSRData.size() > 0) {
			String title = "Summary Information";
			BigDecimal TotalOcc = new BigDecimal(0);
			BigDecimal TotalOccPcnt = new BigDecimal(0);			
			BigDecimal TotRotbTrans = new BigDecimal(0);		    		    
			BigDecimal TotRotbGroup = new BigDecimal(0);		    		    
			BigDecimal TotGrpPickedup = new BigDecimal(0);		    		    
			BigDecimal TotGrpRmsRem = new BigDecimal(0);		    		    
			BigDecimal TotRotbCont = new BigDecimal(0);		    		    
			BigDecimal TotGrpPricetd = new BigDecimal(0);		    		    
			BigDecimal TotGrpDemandtd = new BigDecimal(0);
			Integer AvilRooms = 0;
			
			for(int i=0; i < lstSSRData.size(); i++){
				TotalOcc = TotalOcc.add(lstSSRData.get(i).getTotOcc());							
				//Sum summary
				AvilRooms = AvilRooms +  new Integer(TotalAvailRooms(lstSSRData.get(i).getStatdate()));
				if (AvilRooms != 0){
					BigDecimal occPcntDiv = TotalOcc.divide(BigDecimal.valueOf(AvilRooms.doubleValue()), RoundingMode.HALF_EVEN);
					TotalOccPcnt = occPcntDiv.multiply(new BigDecimal(100));
				}else{
					TotalOccPcnt = new BigDecimal(0);
				}
				
				TotRotbTrans = TotRotbTrans.add(lstSSRData.get(i).getRotbTrans());
				TotRotbGroup = TotRotbGroup.add(lstSSRData.get(i).getRotbGroup());
				TotGrpPickedup = TotGrpPickedup.add(lstSSRData.get(i).getGrpPickedup());
				TotGrpRmsRem = TotGrpRmsRem.add(lstSSRData.get(i).getGrpRmsRem());
				TotRotbCont = TotRotbCont.add(lstSSRData.get(i).getRotbCont());
				TotGrpPricetd = TotGrpPricetd.add(lstSSRData.get(i).getGrpPricetd());
				TotGrpDemandtd = TotGrpDemandtd.add(lstSSRData.get(i).getGrpDemandtd());
				
			}
			
			//Set values of summary section 
			SSRInputData inputData = new SSRInputData();
			inputData.setComment(title);
			inputData.setTotOcc(TotalOcc);
			
			
			inputData.setOccpcnt(TotalOccPcnt);
			inputData.setRotbTrans(TotRotbTrans);
			inputData.setRotbGroup(TotRotbGroup);
			inputData.setGrpPickedup(TotGrpPickedup);
			inputData.setGrpRmsRem(TotGrpRmsRem);
			inputData.setRotbCont(TotRotbCont);
			inputData.setGrpPricetd(TotGrpPricetd);
			inputData.setGrpDemandtd(TotGrpDemandtd);
			
			///Add startDate and endDate info USEFUL !! for Render when updating Summary and Total info (first row)
			inputData.setStartDate(lstSSRData.get(0).getStartDate());
			inputData.setEndDate(lstSSRData.get(0).getEndDate());
						
			//Add the summary element to the list
			lstSSRInpuData.add(inputData);
			
			//copy the list to total list
			for(int i=0; i < lstSSRData.size(); i++){
				lstSSRInpuData.add(lstSSRData.get(i));				
			}
			
		}
		
		return lstSSRInpuData;
	}
	
	private int TotalAvailRooms(String sDate){
		
		java.sql.CallableStatement  ps = null;		
		ResultSet rs = null;
		Connection currentConnection = null;
		
		int result = 0;
		
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			Date dDate = dateFormat.parse(sDate);
			currentConnection = this.getCurrentHRR3Connection();
			
			dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			
			String statement = "SELECT fn_get_hotel_total_rooms2 (?,?)";
			ps = currentConnection.prepareCall(statement);	
			ps.setInt(1, this.getCurrentHotel().getHotelId());
			ps.setString(2, dateFormat.format(dDate));
			
			rs = ps.executeQuery();
			
			if(rs != null ){								
				while(rs.next()) {
					result = rs.getInt(1);										
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		finally { close(rs, ps,currentConnection);
			System.out.println(" - END TotalAvailRooms - " + new Date());
		}
		
		return result;
	}
	
	
	/**
	 * Update Comment,Exception and Rates values (A/1, B/2, B/3, ... I/9, HP1, HP2, Over sell factor) ONLY if Actual = F
	 * @param SSRInputData
	 * @return 0 if the database could not get updated,  1 if update success
	 */
	
	public int UpdateSSRData (SSRInputData ssrData){
		
		System.out.println("*********** UpdateSSRData (Inline Edition) - BEGIN - " + new Date() +" ***********");
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try{			
			conn = this.getCurrentHRR3Connection();	
			
			String statement = ("{call ftUpdateSSRData (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			
			ps = conn.prepareStatement(statement);
			
			ps.setInt(1, this.getCurrentHotel().getHotelId());
			ps.setString(2,  ssrData.getStatdate());
			ps.setString(3, ssrData.getComment());
			ps.setInt(4, ssrData.getIsException());
			ps.setString(5, ssrData.getA1());
			ps.setString(6, ssrData.getB2());
			ps.setString(7, ssrData.getC3());
			ps.setString(8, ssrData.getD4());
			ps.setString(9, ssrData.getE5());
			ps.setString(10, ssrData.getF6());
			ps.setString(11, ssrData.getG7());
			ps.setString(12, ssrData.getH8());
			ps.setString(13, ssrData.getI9());
			ps.setString(14, ssrData.getHp1());
			ps.setString(15, ssrData.getHp2());
			ps.setString(16, ssrData.getOversellFactor());
						
						
			rs = ps.executeQuery();
			
			if(rs != null ){			
				while(rs.next()) {
					if (rs.getInt("p_out") == 1)
						return 1;
					else
						return 0;
				}
			}
		
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}

		finally { close(rs, ps, conn);
	       System.out.println(" *********** UpdateSSRData (Inline Edition) - " + new Date()+ " ***********");
		}
				
		return 0;
	}
	
	
	/**
	 * Update Segments (Transient,Group Block,Group P/U,Group Remaining,Contract,Demand TD,Price TD) ONLY if Actual = F
	 * @param statdate, SSRsegmentID, OCC
	 * @return 0 if the database could not get updated,  1 if update success
	 */
	
	public int UpdateSSRSegmentData (String statdate, int ssrSegmentId, BigDecimal occ){
		
		System.out.println("*********** UpdateSSRSegmentData (Inline Edition) - BEGIN - " + new Date() +" ***********");
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try{			
			conn = this.getCurrentHRR3Connection();	
			
			String statement = ("{call ftUpdateSSRSegmentData (?,?,?,?,?,?)}");
			
			ps = conn.prepareStatement(statement);
			
			ps.setInt(1, this.getCurrentHotel().getHotelId());
			ps.setString(2, statdate);
			ps.setInt(3, ssrSegmentId);
			ps.setBigDecimal(4, occ);
			ps.setBigDecimal(5, new BigDecimal(0));
			ps.setBigDecimal(6, new BigDecimal(0));
															
			rs = ps.executeQuery();
			
			if(rs != null ){			
				while(rs.next()) {
					if (rs.getInt("p_out") == 1)
						return 1;
					else
						return 0;
				}
			}
		
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}

		finally { close(rs, ps, conn);
	       System.out.println(" *********** UpdateSSRSegmentData (Inline Edition) - " + new Date()+ " ***********");
		}
				
		return 0;
	}
	
	
	/**
	 * Calculate and get the values lrr,tot_occ, tot_occ_pcnt,Marrate to set them in the grid.
	 * @param dateFrom, dateTo, customerId
	 * @return SSRInputData object 
	 */
	
	public SSRInputData getSSRData(String dateFrom, String dateTo, int customerId){
		
		System.out.println("*********** getSSRData (Inline Edition) - BEGIN - " + new Date() +" ***********");
		
		ImportSSRDAO  ssrDao =  new ImportSSRDAO(this.getCurrentHotel());
		SSRInputData ssrData = new SSRInputData();
				
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
			
			calculateMARRate(dateFrom, dateTo, customerId);
			
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
					
					ssrData.setComment(rs.getString("comments"));
					ssrData.setIsException(rs.getInt("isexception"));
					ssrData.setDow(rs.getString("dow"));
					Date dateObj = rs.getDate("statdate");
					ssrData.setStatdate(dateObj != null ? dateFormat.format(dateObj) : "");
					ssrData.setIsActual(rs.getInt("isActual"));
					ssrData.setOccpcnt(rs.getBigDecimal("occpcnt").setScale(1, RoundingMode.HALF_EVEN));
					ssrData.setTotOcc(rs.getBigDecimal("tot_occ"));
					ssrData.setLrr(rs.getString("lrr"));
					ssrData.setA1(rs.getString("ratecat1"));
					ssrData.setB2(rs.getString("ratecat2"));
					ssrData.setC3(rs.getString("ratecat3"));
					ssrData.setD4(rs.getString("ratecat4"));
					ssrData.setE5(rs.getString("ratecat5"));
					ssrData.setF6(rs.getString("ratecat6"));
					ssrData.setG7(rs.getString("ratecat7"));
					ssrData.setH8(rs.getString("ratecat8"));
					ssrData.setI9(rs.getString("ratecat9"));
					ssrData.setHp1(rs.getString("hp"));
					ssrData.setHp2(rs.getString("hp2"));
					ssrData.setOversellFactor(rs.getString("oversell_factor"));
					ssrData.setRotbTrans(rs.getBigDecimal("rotb_trans"));
					ssrData.setRotbGroup(rs.getBigDecimal("rotb_group"));
					ssrData.setGrpPickedup(rs.getBigDecimal("grp_pickedup"));
					ssrData.setGrpRmsRem(rs.getBigDecimal("grp_rms_rem"));
					ssrData.setRotbCont(rs.getBigDecimal("rotb_cont"));
					ssrData.setGrpDemandtd(rs.getBigDecimal("grp_demandtd"));
					ssrData.setGrpPricetd(rs.getBigDecimal("grp_pricetd"));
					ssrData.setSeasonalmarrate(rs.getBigDecimal("SeasonalMARrate"));
					ssrData.setMarrate(rs.getString("MARrate"));
					
				}
			}	
			
			
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	
		finally { close(rs, ps, conn);
	       System.out.println(" *********** getSSRData (Inline Edition) - " + new Date()+ " ***********");
		}
		
		return ssrData;
	}
	
	
	
}
