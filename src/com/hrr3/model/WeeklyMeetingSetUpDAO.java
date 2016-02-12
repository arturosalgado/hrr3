package com.hrr3.model;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.hrr3.entity.Hotel;
import com.hrr3.entity.WeeklyMtgMin;
import com.hrr3.entity.WeeklyMtgMinGroupPace;
import com.hrr3.entity.WeeklyMtgMinSalesPaces;
import com.hrr3.entity.ssrMigration.SSRSnapshotSUWklyMtg;
import com.hrr3.entity.ssrMigration.SSRSnapshotSUWklyMtgHD;
import com.hrr3.entity.ssrMigration.SSRSnapshotSUWklyMtgSS;

public class WeeklyMeetingSetUpDAO  extends  RM3AbstractDAO {

	public WeeklyMeetingSetUpDAO(Hotel hotel) {	
		
		super(hotel);
		
	}

	public void closeCurrentConnection() {
		
		close(this.getCurrentHRR3Connection());
	}
	
	/**
	 * Get Information  (attendees,critique,other,outlook, etc)
	 * @param 
	 * @return WeeklyMtgMin Object
	 */
	public WeeklyMtgMin fillDataWklyMtg(){
		
		System.out.println("*********** Weekly Meeting Setup - fillDataWklyMtg - BEGIN - " + new Date() +" ***********");
		
		WeeklyMtgMin wklyMtgMin =  new WeeklyMtgMin();
				
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try{	
						
			conn = this.getCurrentHRR3Connection();	
			String statement = "call ftSSRWklyMtgMin(?)";
			
			ps = conn.prepareStatement(statement);			
			ps.setInt(1, this.getCurrentHotel().getHotelId());
						
			rs = ps.executeQuery();
			
			if(rs != null ){	
				

				while(rs.next()) {
													           
				    wklyMtgMin.setHotelName(rs.getString("name"));
				    
				    SSRSnapshotSUWklyMtg ssrSnapshotSUWklyMtg = new SSRSnapshotSUWklyMtg();
					
				    ssrSnapshotSUWklyMtg.setTransotbEntered(rs.getString("transotb_entered"));
				    ssrSnapshotSUWklyMtg.setAttendees(rs.getString("attendees"));
				    ssrSnapshotSUWklyMtg.setOther(rs.getString("other"));
				    ssrSnapshotSUWklyMtg.setCritique(rs.getString("critique"));
				    ssrSnapshotSUWklyMtg.setOutlook(rs.getString("outlook"));
				    ssrSnapshotSUWklyMtg.setCurrmeetdate(rs.getString("currmeetdate"));				   
				    ssrSnapshotSUWklyMtg.setSuMonth(rs.getInt("su_month"));
				    ssrSnapshotSUWklyMtg.setSuYear(rs.getInt("su_year"));
				    ssrSnapshotSUWklyMtg.setWkNo(rs.getString("wk_no"));
				    //SSR Snapshot for Wkly.
				    ssrSnapshotSUWklyMtg.setSavedSnapshotid(rs.getInt("saved_snapshotid"));
				    
				    wklyMtgMin.setSsrSnapshotSUWklyMtg(ssrSnapshotSUWklyMtg);				    
				    wklyMtgMin.setActivityDate(rs.getString("activity_date"));				    				    
					
				}
			}	
						
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
		}
	
		finally { close(rs, ps, conn);};	       	
				
		System.out.println("*********** Weekly Meeting Setup - fillDataWklyMtg - END - " + new Date() +" ***********");
		
		return wklyMtgMin;
	}
	
	
	/**
	 * Get Spot soft information
	 * @param 
	 * @return SSRSnapshotSUWklyMtgSS Object
	 */
	public List<SSRSnapshotSUWklyMtgSS> fillDataSoftSpot(){
		
		System.out.println("*********** Weekly Meeting Setup - fillDataSoftSpot - BEGIN - " + new Date() +" ***********");
		
		List<SSRSnapshotSUWklyMtgSS> lstSoftSpot = new ArrayList<SSRSnapshotSUWklyMtgSS>();						
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try{	
						
			conn = this.getCurrentHRR3Connection();	
			
			String statement = ("SELECT * FROM RM3SSRSUWklyMtgSS WHERE hotel_id = ? order by id");
			
			ps = conn.prepareStatement(statement);			
			ps.setInt(1, this.getCurrentHotel().getHotelId());
						
			rs = ps.executeQuery();
			
			if(rs != null ){					

				while(rs.next()) {					
					SSRSnapshotSUWklyMtgSS softSpot =  new SSRSnapshotSUWklyMtgSS();
					
					softSpot.setSoftspot(rs.getString("softspot"));
					softSpot.setStrategy(rs.getString("strategy"));
					softSpot.setSuwklyId(rs.getInt("suwkly_id"));
					softSpot.setSoftId(rs.getInt("id"));
					
					lstSoftSpot.add(softSpot);
				}
			}	
						
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
		}
	
		finally { close(rs, ps, conn);};	       	
				
		System.out.println("*********** Weekly Meeting Setup - fillDataSoftSpot - END - " + new Date() +" ***********");
		
		return lstSoftSpot;
	}
	
	
	/**
	 * Get High Demand information
	 * @param 
	 * @return SSRSnapshotSUWklyMtgHD Object
	 */
	public List<SSRSnapshotSUWklyMtgHD> fillDataHighDemand(){
		
		System.out.println("*********** Weekly Meeting Setup - fillDataHighDemand - BEGIN - " + new Date() +" ***********");
		
		List<SSRSnapshotSUWklyMtgHD> lstHighDemand = new ArrayList<SSRSnapshotSUWklyMtgHD>();						
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try{	
						
			conn = this.getCurrentHRR3Connection();	
			
			String statement = ("SELECT * FROM RM3SSRSUWklyMtgHD WHERE hotel_id = ? order by id");
			
			ps = conn.prepareStatement(statement);			
			ps.setInt(1, this.getCurrentHotel().getHotelId());
						
			rs = ps.executeQuery();
			
			if(rs != null ){					

				while(rs.next()) {	
					
					SSRSnapshotSUWklyMtgHD highDemand =  new SSRSnapshotSUWklyMtgHD();
					
					highDemand.setOutcome(rs.getString("outcome"));
					highDemand.setPeakdate(rs.getString("peakdate"));
					highDemand.setSellingRest(rs.getString("selling_rest"));
					highDemand.setSuwklyId(rs.getInt("suwkly_id"));
					highDemand.setHighId(rs.getInt("id"));
					
					lstHighDemand.add(highDemand);
					
				}
			}	
						
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
		}
	
		finally { close(rs, ps, conn);};	       	
				
		System.out.println("*********** Weekly Meeting Setup - fillDataHighDemand - END - " + new Date() +" ***********");
		
		return lstHighDemand;
	}
	
	/**
	 * Get Sale Paces information
	 * @param ssrSnapshotId, dateFrom, dateTo, customerId
	 *        ej. dateFrom: 2014-10-1  dateTo: 2014-09-30, calculated from current month and year.
	 * @return WeeklyMtgMinSalesPaces Object
	 */
	public List<WeeklyMtgMinSalesPaces> fillDataSalesPaces(int ssrSnapshotId, String dateFrom, String dateTo, int customerId){
		
		System.out.println("*********** Weekly Meeting Setup - fillDataSalesPaces - BEGIN - " + new Date() +" ***********");
		
		List<WeeklyMtgMinSalesPaces> lstSalePaces = new ArrayList<WeeklyMtgMinSalesPaces>();						
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try{	
						
			conn = this.getCurrentHRR3Connection();	
			
			String statement = ("{call ftSSRSalesPace (?,?,?,?,?,?)}");
			
			ps = conn.prepareStatement(statement);			
			ps.setInt(1, this.getCurrentHotel().getHotelId());
			ps.setString(2, dateFrom);
			ps.setString(3, dateTo);
			ps.setInt(4, 0);
			ps.setInt(5, ssrSnapshotId);
			ps.setInt(6, customerId);
						
			rs = ps.executeQuery();
			
			if(rs != null ){					

				while(rs.next()) {	
					
					WeeklyMtgMinSalesPaces salePaces =  new WeeklyMtgMinSalesPaces();
					
					salePaces.setSeg(rs.getInt("seq"));
					salePaces.setRowType(rs.getString("rowtype"));
					salePaces.setPickupSince(rs.getString("pickupsince"));
					salePaces.setJanOcc(rs.getBigDecimal("jan_occ"));
					salePaces.setFebOcc(rs.getBigDecimal("feb_occ"));
					salePaces.setMarOcc(rs.getBigDecimal("mar_occ"));
					salePaces.setAprOcc(rs.getBigDecimal("apr_occ"));
					salePaces.setMayOcc(rs.getBigDecimal("may_occ"));
					salePaces.setJunOcc(rs.getBigDecimal("jun_occ"));
					salePaces.setJulOcc(rs.getBigDecimal("jul_occ"));
					salePaces.setAugOcc(rs.getBigDecimal("aug_occ"));
					salePaces.setSepOcc(rs.getBigDecimal("sep_occ"));
					salePaces.setOctOcc(rs.getBigDecimal("oct_occ"));
					salePaces.setNovOcc(rs.getBigDecimal("nov_occ"));
					salePaces.setDecOcc(rs.getBigDecimal("dec_occ"));
					
					lstSalePaces.add(salePaces);
				}
			}	
						
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
		}
	
		finally { close(rs, ps, conn);};	       	
				
		System.out.println("*********** Weekly Meeting Setup - fillDataSalesPaces - END - " + new Date() +" ***********");
		
		return lstSalePaces;
	}
	
	/**
	 * Get Group Pace information
	 * @param year (get from su_year in fillDataWklyMtg if it is 0 then set current year), customerId
	 * @return WeeklyMtgMinGroupPace Object
	 */
	public  List<WeeklyMtgMinGroupPace> fillDataGroupPace(int year, int customerId){
		
		System.out.println("*********** Weekly Meeting Setup - fillDataGroupPace - BEGIN - " + new Date() +" ***********");
		
		List<WeeklyMtgMinGroupPace> lstGroupPace = new ArrayList<WeeklyMtgMinGroupPace>();		
				
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try{	
						
			conn = this.getCurrentHRR3Connection();	
			
			String statement = ("{call ftSSRGroupPace (?,?,?)}");
			
			ps = conn.prepareStatement(statement);			
			ps.setInt(1, this.getCurrentHotel().getHotelId());			
			ps.setInt(2, year);
			ps.setInt(3, customerId);			
						
			rs = ps.executeQuery();
			
			if(rs != null ){					

				while(rs.next()) {						
					
					WeeklyMtgMinGroupPace groupPace =  new WeeklyMtgMinGroupPace();
					
					groupPace.setRowType(rs.getString("rowtype"));
					groupPace.setRowCaption(rs.getString("rowcaption"));
					groupPace.setJanOcc(rs.getBigDecimal("jan_occ"));
					groupPace.setFebOcc(rs.getBigDecimal("feb_occ"));
					groupPace.setMarOcc(rs.getBigDecimal("mar_occ"));
					groupPace.setAprOcc(rs.getBigDecimal("apr_occ"));
					groupPace.setMayOcc(rs.getBigDecimal("may_occ"));
					groupPace.setJunOcc(rs.getBigDecimal("jun_occ"));
					groupPace.setJulOcc(rs.getBigDecimal("jul_occ"));
					groupPace.setAugOcc(rs.getBigDecimal("aug_occ"));
					groupPace.setSepOcc(rs.getBigDecimal("sep_occ"));
					groupPace.setOctOcc(rs.getBigDecimal("oct_occ"));
					groupPace.setNovOcc(rs.getBigDecimal("nov_occ"));
					groupPace.setDecOcc(rs.getBigDecimal("dec_occ"));
					groupPace.setTotOcc(rs.getBigDecimal("total"));
					
					lstGroupPace.add(groupPace);
				}
			}	
						
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
		}
	
		finally { close(rs, ps, conn);};	       	
				
		System.out.println("*********** Weekly Meeting Setup - fillDatafillDataGroupPacealesPaces - END - " + new Date() +" ***********");
		
		return lstGroupPace;
	}
	
	
	/**
	 * Save weekly Mtg Min information
	 * @param wklyMtgMin, 
	 *        Tab Sales Pace: savedSnapshotId, 
	 *        Tab Group Pace: currentYearGroupPace,previousYearGroupPace,nextYearGroupPace
	 *        year (get from su_year in fillDataWklyMtg if it is 0 then set current year)
	 * @return WeeklyMtgMinGroupPace Object
	 */
	public void saveWeeklyMtgMin(WeeklyMtgMin wklyMtgMin, int savedSnapshotId, WeeklyMtgMinGroupPace currentYearGroupPace, 
			                    WeeklyMtgMinGroupPace previousYearGroupPace, WeeklyMtgMinGroupPace nextYearGroupPace, int year){
		
		System.out.println("*********** SaveWeeklyMtgMin - BEGIN - " + new Date() +" ***********");
							
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try{	
						
			conn = this.getCurrentHRR3Connection();	
			
			String statement = ("{call ftSSRSaveWklyMtgMin (?,?,?,?,?,?,?)}");
			
			ps = conn.prepareStatement(statement);			
			ps.setInt(1, this.getCurrentHotel().getHotelId());			
			ps.setString(2, wklyMtgMin.getSsrSnapshotSUWklyMtg().getAttendees());
			ps.setString(3, wklyMtgMin.getSsrSnapshotSUWklyMtg().getCritique());
			ps.setString(4, wklyMtgMin.getSsrSnapshotSUWklyMtg().getOutlook());
			ps.setString(5, wklyMtgMin.getSsrSnapshotSUWklyMtg().getOther());
			
			if (savedSnapshotId == 0)
				ps.setNull(6, java.sql.Types.INTEGER);
			else
				ps.setInt(6, savedSnapshotId);
			
			ps.setString(7, wklyMtgMin.getSsrSnapshotSUWklyMtg().getTransotbEntered());
			
						
			ps.execute();
			
			//Update GroupPace
			
			this.saveCOTB(currentYearGroupPace, year, wklyMtgMin.getActivityDate());
			this.saveCOTB(previousYearGroupPace, year, wklyMtgMin.getActivityDate());
			this.saveCOTB(nextYearGroupPace, year, wklyMtgMin.getActivityDate());
			
			
			//Here the update Soft and High demand grids 				
						
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
		}
	
		finally { close(rs, ps, conn);};	       	
				
		System.out.println("*********** SaveWeeklyMtgMins - END - " + new Date() +" ***********");
		
		//return groupPace;
	}
	
	
	/**
	 * Save COTB information, used for current, previous and next year.
	 * @param Tab Group Pace: groupPace object,  
	 *                        year (get from su_year in fillDataWklyMtg if it is 0 then set current year), 
	 *                        activityDate  from Tab Group Pace
	 * @return WeeklyMtgMinGroupPace Object
	 */
	public void saveCOTB(WeeklyMtgMinGroupPace groupPace, int year,String activityDate ){
		
		System.out.println("*********** saveCOTB - BEGIN - " + new Date() +" ***********");
							
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		int defYear = 0;
		String defActivityDate = "";
		
		try{
			
			
			if (groupPace.getRowType().equals("CURRENT")){
				defYear = year;
				defActivityDate = activityDate;
			}else if(groupPace.getRowType().equals("NEXT")){
				defYear = year + 1;
				defActivityDate = activityDate;
			}else if(groupPace.getRowType().equals("PREVIOUS")){
				defYear = year -1;
				
				if (!activityDate.isEmpty() || activityDate != "" || activityDate != null){
					Date calcDate = null;				
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					calcDate = dateFormat.parse(activityDate);
					
					Calendar cal = Calendar.getInstance();
					cal.setTime(calcDate);
					cal.add(Calendar.DATE, -1); //rest 1 day

					calcDate = cal.getTime();
					
					defActivityDate = dateFormat.format(calcDate);
				}
				
			}
			
			conn = this.getCurrentHRR3Connection();	
								
			String statement = ("{call ftSSRGroupPaceUpdate (?,?,?,?,?)}");
			
			ps = conn.prepareStatement(statement);
			
			//Jan			
			ps.setInt(1, this.getCurrentHotel().getHotelId());			
			ps.setBigDecimal(2, groupPace.getJanOcc());
			ps.setInt(3, defYear);
			ps.setInt(4, 1);
			ps.setString(5, defActivityDate);									
			ps.execute();
			
			//Feb			
			ps.setInt(1, this.getCurrentHotel().getHotelId());			
			ps.setBigDecimal(2, groupPace.getFebOcc());
			ps.setInt(3, defYear);
			ps.setInt(4, 2);
			ps.setString(5, defActivityDate);									
			ps.execute();
			
			//Mar	
			ps.setInt(1, this.getCurrentHotel().getHotelId());			
			ps.setBigDecimal(2, groupPace.getMarOcc());
			ps.setInt(3, defYear);
			ps.setInt(4, 3);
			ps.setString(5, defActivityDate);									
			ps.execute();
			
			//Apr
			ps.setInt(1, this.getCurrentHotel().getHotelId());			
			ps.setBigDecimal(2, groupPace.getAprOcc());
			ps.setInt(3, defYear);
			ps.setInt(4, 4);
			ps.setString(5, defActivityDate);									
			ps.execute();
			
			//May
			ps.setInt(1, this.getCurrentHotel().getHotelId());			
			ps.setBigDecimal(2, groupPace.getMayOcc());
			ps.setInt(3, defYear);
			ps.setInt(4, 5);
			ps.setString(5, defActivityDate);									
			ps.execute();
			
			//Jun
			ps.setInt(1, this.getCurrentHotel().getHotelId());			
			ps.setBigDecimal(2, groupPace.getJunOcc());
			ps.setInt(3, defYear);
			ps.setInt(4, 6);
			ps.setString(5, defActivityDate);									
			ps.execute();
			
			//Jul
			ps.setInt(1, this.getCurrentHotel().getHotelId());			
			ps.setBigDecimal(2, groupPace.getJulOcc());
			ps.setInt(3, defYear);
			ps.setInt(4, 7);
			ps.setString(5, defActivityDate);									
			ps.execute();
			
			//Aug
			ps.setInt(1, this.getCurrentHotel().getHotelId());			
			ps.setBigDecimal(2, groupPace.getAugOcc());
			ps.setInt(3, defYear);
			ps.setInt(4, 8);
			ps.setString(5, defActivityDate);									
			ps.execute();
			
			//Sep
			ps.setInt(1, this.getCurrentHotel().getHotelId());			
			ps.setBigDecimal(2, groupPace.getSepOcc());
			ps.setInt(3, defYear);
			ps.setInt(4, 9);
			ps.setString(5, defActivityDate);									
			ps.execute();
			
			//Oct
			ps.setInt(1, this.getCurrentHotel().getHotelId());			
			ps.setBigDecimal(2, groupPace.getOctOcc());
			ps.setInt(3, defYear);
			ps.setInt(4, 10);
			ps.setString(5, defActivityDate);									
			ps.execute();
			
			//Nov
			ps.setInt(1, this.getCurrentHotel().getHotelId());			
			ps.setBigDecimal(2, groupPace.getNovOcc());
			ps.setInt(3, defYear);
			ps.setInt(4, 11);
			ps.setString(5, defActivityDate);									
			ps.execute();
			
			//Dic
			ps.setInt(1, this.getCurrentHotel().getHotelId());			
			ps.setBigDecimal(2, groupPace.getDecOcc());
			ps.setInt(3, defYear);
			ps.setInt(4, 12);
			ps.setString(5, defActivityDate);									
			ps.execute();
											
					
			
		}catch (SQLException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
		}
	
		finally { close(rs, ps, conn);};	       	
				
		System.out.println("*********** saveCOTB - END - " + new Date() +" ***********");
		
		//return groupPace;
	}
	
	
	/**
	 * Save Soft Spot information
	 * @param softspot, strategy, idSoftRow
	 * if idSoftRow = 0 and softspot = value and strategy = value, INSERT row
	 * if idSoftRow = value, and softspot = NULL and strategy = NULL, DELETE row
	 * if idSoftRow = value, and softspot = value and strategy = value, UPDATE row
	 * @return idSoftRow
	 */
	public int saveRowSoftSpot(String softspot, String strategy, int idSoftRow ){
		
		System.out.println("*********** SaveRowSoftSpot - BEGIN - " + new Date() +" ***********");
							
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;		
		
		try{
								
			conn = this.getCurrentHRR3Connection();	
								
			String statement = ("{call ftssrSU_WklyMtg_SoftSpotUpdate (?,?,?,?)}");
			
			ps = conn.prepareStatement(statement);
							
			ps.setInt(1, this.getCurrentHotel().getHotelId());	
			
			if (softspot == null || softspot.isEmpty() )
				ps.setNull(2, java.sql.Types.VARCHAR);
			else
				ps.setString(2, softspot);
			
			if(strategy == null || strategy.isEmpty())
				ps.setNull(3, java.sql.Types.VARCHAR);
			else
				ps.setString(3, strategy);
			
			ps.setInt(4, idSoftRow);
			
			rs = ps.executeQuery();
			
			if(rs != null ){					

				while(rs.next()) {						
					
					idSoftRow = rs.getInt("p_id");
					
				}
			}							
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
		}
	
		finally { close(rs, ps, conn);};	       	
								
		System.out.println("*********** SaveRowSoftSpot - END - " + new Date() +" ***********");
		
		return idSoftRow;
				
	}
	
	
	/**
	 * Save Soft Spot information
	 * @param softspot, strategy, idSoftRow
	 * if idHiDRow = 0 and peakdate = value and sellingRest = value, and outcome = value, INSERT row
	 * if idHiDRow = value, and peakdate = NULL and sellingRest = NULL and outcome = NULL, DELETE row
	 * if idHiDRow = value, and peakdate = value and sellingRest = value and outcome = value, UPDATE row
	 * @return idHiDRow
	 */
	public int saveRowHiDmd(String peakdate, String sellingRest, String outcome, int idHiDRow ){
		
		System.out.println("*********** SaveRowHiDmd - BEGIN - " + new Date() +" ***********");
							
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;		
		
		try{
								
			conn = this.getCurrentHRR3Connection();	
								
			String statement = ("{call ftssrSU_WklyMtg_HDUpdate (?,?,?,?,?)}");
			
			ps = conn.prepareStatement(statement);
							
			ps.setInt(1, this.getCurrentHotel().getHotelId());	
			
			if (peakdate == null || peakdate.isEmpty())
				ps.setNull(2, java.sql.Types.VARCHAR);
			else
				ps.setString(2, peakdate);
			
			if(sellingRest == null || sellingRest.isEmpty())
				ps.setNull(3, java.sql.Types.VARCHAR);
			else
				ps.setString(3, sellingRest);
			
			if(outcome == null || outcome.isEmpty())
				ps.setNull(4, java.sql.Types.VARCHAR);
			else
				ps.setString(4, outcome);
			
			ps.setInt(5, idHiDRow);
			
			rs = ps.executeQuery();
			
			if(rs != null ){					

				while(rs.next()) {						
					
					idHiDRow = rs.getInt("p_id");
					
				}
			}							
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
		}
	
		finally { close(rs, ps, conn);};	       	
								
		System.out.println("*********** SaveRowHiDmd - END - " + new Date() +" ***********");
		
		return idHiDRow;
				
	}
	
}
