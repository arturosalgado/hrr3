package com.hrr3.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
import com.hrr3.entity.proforma.HotelTotalRooms;
import com.hrr3.entity.proforma.ProFormaData;
import com.hrr3.entity.proforma.ReportData;
import com.hrr3.entity.proforma.SnapshotSegmentData;
import com.hrr3.entity.proforma.SnapshotTransientData;
import com.hrr3.entity.proforma.UserPreferencesProforma;

public class ProformaDAO extends RM3AbstractDAO {

	public ProformaDAO (Hotel currentHotel) {
		super(currentHotel);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Get User Preferences Proforma
	 * @param userId, companyId, 
	 * @return UserPreferencesProforma Object
	 */
	public UserPreferencesProforma  loadProformaPreferences(int userId, int companyId){
		
		System.out.println("*********** loadProformaPreferences - BEGIN - " + new Date() +" ***********");
		
		UserPreferencesProforma	preferences = new UserPreferencesProforma();
	   
	   
	    PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try{	
						
			conn = this.getCurrentHRR3Connection();	
			
			String statement = ("select * from RM3ProformaUserPreferences where user_id = ? and company_id = ? and hotel_id = ?");
			
			ps = conn.prepareStatement(statement);			
			ps.setInt(1, userId );
			ps.setInt(2, companyId);
			ps.setInt(3, this.getCurrentHotel().getHotelId());
			
			rs = ps.executeQuery();
			
			while(rs != null && rs.next()){	
				
				preferences.setUserPreferencesId(rs.getInt("userPreferencesId"));
				preferences.setUserId(rs.getInt("user_id"));
				preferences.setHotelId(rs.getInt("hotel_id"));
				preferences.setCompanyId(rs.getInt("company_id"));
				preferences.setStatDateFrom(rs.getString("StatDateFrom"));
				preferences.setStatDateTo(rs.getString("StatDateTo"));
				preferences.setForecastSnaphotID(rs.getInt("ForecastSnaphotID"));
				preferences.setGroupName(rs.getString("GroupName"));
				preferences.setPartADateFrom(rs.getString("PartA_DateFrom"));
				preferences.setPartADateTo(rs.getString("PartA_DateTo"));
				preferences.setPartARmsAllWeekDays(rs.getInt("PartA_RmsAllWeekDays"));
				preferences.setPartARmsMonday(rs.getInt("PartA_RmsMonday"));
				preferences.setPartARmsTuesday(rs.getInt("PartA_RmsTuesday"));
				preferences.setPartARmsWednesday(rs.getInt("PartA_RmsWednesday"));
				preferences.setPartARmsThursday(rs.getInt("PartA_RmsThursday"));
				preferences.setPartARmsFriday(rs.getInt("PartA_RmsFriday"));
				preferences.setPartARmsSaturday(rs.getInt("PartA_RmsSaturday"));
				preferences.setPartARmsSunday(rs.getInt("PartA_RmsSunday"));
				preferences.setPartAADRAllWeekDays(rs.getInt("PartA_ADRAllWeekDays"));
				preferences.setPartAADRMonday(rs.getInt("PartA_ADRMonday"));
				preferences.setPartAADRTuesday(rs.getInt("PartA_ADRTuesday"));
				preferences.setPartAADRWednesday(rs.getInt("PartA_ADRWednesday"));
				preferences.setPartAADRThursday(rs.getInt("PartA_ADRThursday"));
				preferences.setPartAADRFriday(rs.getInt("PartA_ADRFriday"));
				preferences.setPartAADRSaturday(rs.getInt("PartA_ADRSaturday"));
				preferences.setPartAADRSunday(rs.getInt("PartA_ADRSunday"));
				preferences.setPartBDateFrom(rs.getString("PartB_DateFrom"));
				preferences.setPartBDateTo(rs.getString("PartB_DateTo"));
				preferences.setPartBRmsAllWeekDays(rs.getInt("PartB_RmsAllWeekDays"));
				preferences.setPartBRmsMonday(rs.getInt("PartB_RmsMonday"));
				preferences.setPartBRmsTuesday(rs.getInt("PartB_RmsTuesday"));
				preferences.setPartBRmsWednesday(rs.getInt("PartB_RmsWednesday"));
				preferences.setPartBRmsThursday(rs.getInt("PartB_RmsThursday"));
				preferences.setPartBRmsFriday(rs.getInt("PartB_RmsFriday"));
				preferences.setPartBRmsSaturday(rs.getInt("PartB_RmsSaturday"));
				preferences.setPartBRmsSunday(rs.getInt("PartB_RmsSunday"));
				preferences.setPartBADRAllWeekDays(rs.getInt("PartB_ADRAllWeekDays"));
				preferences.setPartBADRMonday(rs.getInt("PartB_ADRMonday"));
				preferences.setPartBADRTuesday(rs.getInt("PartB_ADRTuesday"));
				preferences.setPartBADRWednesday(rs.getInt("PartB_ADRWednesday"));
				preferences.setPartBADRThursday(rs.getInt("PartB_ADRThursday"));
				preferences.setPartBADRFriday(rs.getInt("PartB_ADRFriday"));
				preferences.setPartBADRSaturday(rs.getInt("PartB_ADRSaturday"));
				preferences.setPartBADRSunday(rs.getInt("PartB_ADRSunday"));
				preferences.setfBRevenue(rs.getInt("FBRevenue"));
				preferences.setfBRevenueProfitPct(rs.getInt("FBRevenueProfitPct"));
				preferences.setfBRevenueProfit(rs.getInt("FBRevenueProfit"));
				preferences.setOtherRevenue(rs.getInt("OtherRevenue"));
				preferences.setOtherRevenueProfitPct(rs.getInt("OtherRevenueProfitPct"));
				preferences.setOtherRevenueProfit(rs.getInt("OtherRevenueProfit"));
				preferences.setDisplaced(rs.getBigDecimal("Displaced"));
				preferences.setBase(rs.getBigDecimal("Base"));
				
			}
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
		}
	
		finally { close(rs, ps, conn);};	       	
				
		System.out.println("*********** loadProformaPreferences - END - " + new Date() +" ***********");		
		
	   return preferences;
	}
	
	public static void main(String arg[]){
		
		Hotel hotel = new Hotel();
		hotel.setHotelId(41);
		
		ProformaDAO uppDAO = new ProformaDAO(hotel);
		
		UserPreferencesProforma pp = new UserPreferencesProforma();
		
		pp = uppDAO.loadProformaPreferences(1, 7);
	}
	
	
	/**
	 * Update User Preferences Proforma
	 * @param UserPreferencesProforma Object 
	 * @return 
	 */
	public void  updateProformaPreferences(UserPreferencesProforma preferences){
		
		System.out.println("*********** updateProformaPreferences - BEGIN - " + new Date() +" ***********");
					   	   
	    PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try{	
						
			conn = this.getCurrentHRR3Connection();	
			
			String statement = ("update RM3ProformaUserPreferences set " +
							"StatDateFrom	= ?, " +
							"StatDateTo	= ?, " +
							"ForecastSnaphotID	= ?, " +
							"GroupName	= ?, " +
							"PartA_DateFrom	= ?, " +
							"PartA_DateTo	= ?, " +
							"PartA_RmsAllWeekDays	= ?, " +
							"PartA_RmsMonday	= ?, " +
							"PartA_RmsTuesday	= ?, " +
							"PartA_RmsWednesday	= ?, " +
							"PartA_RmsThursday	= ?, " +
							"PartA_RmsFriday	= ?, " +
							"PartA_RmsSaturday	= ?, " +
							"PartA_RmsSunday	= ?, " +
							"PartA_ADRAllWeekDays	= ?, " +
							"PartA_ADRMonday	= ?, " +
							"PartA_ADRTuesday	= ?, " +
							"PartA_ADRWednesday	= ?, " +
							"PartA_ADRThursday	= ?, " +
							"PartA_ADRFriday	= ?, " +
							"PartA_ADRSaturday	= ?, " +
							"PartA_ADRSunday	= ?, " +
							"PartB_DateFrom	= ?, " +
							"PartB_DateTo	= ?, " +
							"PartB_RmsAllWeekDays	= ?, " +
							"PartB_RmsMonday	= ?, " +
							"PartB_RmsTuesday	= ?, " +
							"PartB_RmsWednesday	= ?, " +
							"PartB_RmsThursday	= ?, " +
							"PartB_RmsFriday	= ?, " +
							"PartB_RmsSaturday	= ?, " +
							"PartB_RmsSunday	= ?, " +
							"PartB_ADRAllWeekDays	= ?, " +
							"PartB_ADRMonday	= ?, " +
							"PartB_ADRTuesday	= ?, " +
							"PartB_ADRWednesday	= ?, " +
							"PartB_ADRThursday	= ?, " +
							"PartB_ADRFriday	= ?, " +
							"PartB_ADRSaturday	= ?, " +
							"PartB_ADRSunday	= ?, " +
							"FBRevenue	= ?, " +
							"FBRevenueProfitPct	= ?, " +
							"FBRevenueProfit	= ?, " +
							"OtherRevenue	= ?, " +
							"OtherRevenueProfitPct	= ?, " +
							"OtherRevenueProfit	= ?, " +
							"Displaced	= ?, " +
							"Base	= ?	 "+
							"where user_id = ? and company_id = ? and hotel_id = ? and  userPreferencesId = ?");
			
			ps = conn.prepareStatement(statement);			
			
			ps.setString(1, preferences.getStatDateFrom());
			ps.setString(2, preferences.getStatDateTo());
			ps.setInt(3, preferences.getForecastSnaphotID());
			ps.setString(4, preferences.getGroupName());
			ps.setString(5, preferences.getPartADateFrom());
			ps.setString(6, preferences.getPartADateTo());
			ps.setInt(7, preferences.getPartARmsAllWeekDays());
			ps.setInt(8, preferences.getPartARmsMonday());
			ps.setInt(9, preferences.getPartARmsTuesday());
			ps.setInt(10, preferences.getPartARmsWednesday());
			ps.setInt(11, preferences.getPartARmsThursday());
			ps.setInt(12, preferences.getPartARmsFriday());
			ps.setInt(13, preferences.getPartARmsSaturday());
			ps.setInt(14, preferences.getPartARmsSunday());
			ps.setInt(15, preferences.getPartAADRAllWeekDays());
			ps.setInt(16, preferences.getPartAADRMonday());
			ps.setInt(17, preferences.getPartAADRTuesday());
			ps.setInt(18, preferences.getPartAADRWednesday());
			ps.setInt(19, preferences.getPartAADRThursday());
			ps.setInt(20, preferences.getPartAADRFriday());
			ps.setInt(21, preferences.getPartAADRSaturday());
			ps.setInt(22, preferences.getPartAADRSunday());
			ps.setString(23, preferences.getPartBDateFrom());
			ps.setString(24, preferences.getPartBDateTo());
			ps.setInt(25, preferences.getPartBRmsAllWeekDays());
			ps.setInt(26, preferences.getPartBRmsMonday());
			ps.setInt(27, preferences.getPartBRmsTuesday());
			ps.setInt(28, preferences.getPartBRmsWednesday());
			ps.setInt(29, preferences.getPartBRmsThursday());
			ps.setInt(30, preferences.getPartBRmsFriday());
			ps.setInt(31, preferences.getPartBRmsSaturday());
			ps.setInt(32, preferences.getPartBRmsSunday());
			ps.setInt(33, preferences.getPartBADRAllWeekDays());
			ps.setInt(34, preferences.getPartBADRMonday());
			ps.setInt(35, preferences.getPartBADRTuesday());
			ps.setInt(36, preferences.getPartBADRWednesday());
			ps.setInt(37, preferences.getPartBADRThursday());
			ps.setInt(38, preferences.getPartBADRFriday());
			ps.setInt(39, preferences.getPartBADRSaturday());
			ps.setInt(40, preferences.getPartBADRSunday());
			ps.setInt(41, preferences.getfBRevenue());
			ps.setInt(42, preferences.getfBRevenueProfitPct());
			ps.setInt(43, preferences.getfBRevenueProfit());
			ps.setInt(44, preferences.getOtherRevenue());
			ps.setInt(45, preferences.getOtherRevenueProfitPct());
			ps.setInt(46, preferences.getOtherRevenueProfit());
			ps.setBigDecimal(47, preferences.getDisplaced());
			ps.setBigDecimal(48, preferences.getBase());
			ps.setInt(49, preferences.getUserId());
			ps.setInt(50, preferences.getCompanyId());
			ps.setInt(51, preferences.getHotelId());
			ps.setInt(52, preferences.getUserPreferencesId());
			
			ps.executeUpdate();											
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
		}
	
		finally { close(rs, ps, conn);};	       	
				
		System.out.println("*********** updateProformaPreferences - END - " + new Date() +" ***********");		
		
	   
	}
	
	
	/**
	 * Insert User Preferences Proforma
	 * @param UserPreferencesProforma Object 
	 * @return 
	 */
	public void  insertProformaPreferences(UserPreferencesProforma preferences){
		
		System.out.println("*********** insertProformaPreferences - BEGIN - " + new Date() +" ***********");
					   	   
	    PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try{	
						
			conn = this.getCurrentHRR3Connection();	
			
			String statement = ("insert into RM3ProformaUserPreferences values (0,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			
			ps = conn.prepareStatement(statement);			
			
			ps.setInt(1, preferences.getUserId());
			ps.setInt(2, preferences.getHotelId());
			ps.setInt(3, preferences.getCompanyId());
			ps.setString(4, preferences.getStatDateFrom());
			ps.setString(5, preferences.getStatDateTo());
			ps.setInt(6, preferences.getForecastSnaphotID());
			ps.setString(7, preferences.getGroupName());
			ps.setString(8, preferences.getPartADateFrom());
			ps.setString(9, preferences.getPartADateTo());
			ps.setInt(10, preferences.getPartARmsAllWeekDays());
			ps.setInt(11, preferences.getPartARmsMonday());
			ps.setInt(12, preferences.getPartARmsTuesday());
			ps.setInt(13, preferences.getPartARmsWednesday());
			ps.setInt(14, preferences.getPartARmsThursday());
			ps.setInt(15, preferences.getPartARmsFriday());
			ps.setInt(16, preferences.getPartARmsSaturday());
			ps.setInt(17, preferences.getPartARmsSunday());
			ps.setInt(18, preferences.getPartAADRAllWeekDays());
			ps.setInt(19, preferences.getPartAADRMonday());
			ps.setInt(20, preferences.getPartAADRTuesday());
			ps.setInt(21, preferences.getPartAADRWednesday());
			ps.setInt(22, preferences.getPartAADRThursday());
			ps.setInt(23, preferences.getPartAADRFriday());
			ps.setInt(24, preferences.getPartAADRSaturday());
			ps.setInt(25, preferences.getPartAADRSunday());
			ps.setString(26, preferences.getPartBDateFrom());
			ps.setString(27, preferences.getPartBDateTo());
			ps.setInt(28, preferences.getPartBRmsAllWeekDays());
			ps.setInt(29, preferences.getPartBRmsMonday());
			ps.setInt(30, preferences.getPartBRmsTuesday());
			ps.setInt(31, preferences.getPartBRmsWednesday());
			ps.setInt(32, preferences.getPartBRmsThursday());
			ps.setInt(33, preferences.getPartBRmsFriday());
			ps.setInt(34, preferences.getPartBRmsSaturday());
			ps.setInt(35, preferences.getPartBRmsSunday());
			ps.setInt(36, preferences.getPartBADRAllWeekDays());
			ps.setInt(37, preferences.getPartBADRMonday());
			ps.setInt(38, preferences.getPartBADRTuesday());
			ps.setInt(39, preferences.getPartBADRWednesday());
			ps.setInt(40, preferences.getPartBADRThursday());
			ps.setInt(41, preferences.getPartBADRFriday());
			ps.setInt(42, preferences.getPartBADRSaturday());
			ps.setInt(43, preferences.getPartBADRSunday());
			ps.setInt(44, preferences.getfBRevenue());
			ps.setInt(45, preferences.getfBRevenueProfitPct());
			ps.setInt(46, preferences.getfBRevenueProfit());
			ps.setInt(47, preferences.getOtherRevenue());
			ps.setInt(48, preferences.getOtherRevenueProfitPct());
			ps.setInt(49, preferences.getOtherRevenueProfit());
			ps.setBigDecimal(50, preferences.getDisplaced());
			ps.setBigDecimal(51, preferences.getBase());
						
			
			ps.executeUpdate();											
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
		}
	
		finally { close(rs, ps, conn);};	       	
				
		System.out.println("*********** insertProformaPreferences - END - " + new Date() +" ***********");		
		
	   
	}
	
	
	
	public UserPreferencesProforma LoadProFormaData(UserPreferencesProforma userPreferencesProforma, int snapshotId, String statDateFrom, String statDateTo, int customerId){
		
		// TO DO: Clear all
		
		List<HotelTotalRooms> lstTotalRooms;
		List<SnapshotSegmentData> lstSegmentData;
		List<SnapshotTransientData> lstTransientData;
		List<ProFormaData> lstProFormaData ;
		
		lstTotalRooms = this.LoadHotelTotalRoomsData(statDateFrom, statDateTo);
		lstSegmentData = this.LoadAppSegmentData(snapshotId, statDateFrom, statDateTo, customerId);
		lstTransientData = this.LoadAppTransientData(snapshotId, statDateFrom, statDateTo); 
		lstProFormaData = this.LoadAppProFormaData(snapshotId, statDateFrom, statDateTo);
		
		/* Set values */
		userPreferencesProforma.setLstTotalRooms(lstTotalRooms);
		userPreferencesProforma.setLstSegmentData(lstSegmentData);
		userPreferencesProforma.setLstTransientData(lstTransientData);
		userPreferencesProforma.setLstProFormaData(lstProFormaData);	
		
		userPreferencesProforma = this.generateAppReportData(userPreferencesProforma, statDateFrom, statDateTo);
		
		//Testing
		/*ReportData tstReportData = userPreferencesProforma.getLstReportData().get(0);
		
		tstReportData.setChangeRms(50);
		tstReportData.setChangeRate(new BigDecimal(120));
		tstReportData.setDispAdrOverride(10);
		
		userPreferencesProforma = this.UpdateReportData(userPreferencesProforma, tstReportData, statDateFrom, statDateTo);
		*/
		return userPreferencesProforma;
		
	}
	
	
	/**
	 * Load total rooms by hotel
	 * @param statDateFrom, statDateTo
	 * @return HotelTotalRooms
	 */
	
	public List<HotelTotalRooms> LoadHotelTotalRoomsData(String statDateFrom, String statDateTo){
		
		System.out.println("*********** ProFormaData - LoadHotelTotalRoomsData - BEGIN - " + new Date() +" ***********");
											
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
				
		List<HotelTotalRooms> lstHotelTotalRooms = new ArrayList<HotelTotalRooms>();
		
		try{	
						
			conn = this.getCurrentHRR3Connection();	
			
			String statement = ("{call RFT_ftHotelTotalRooms_List_By_DateRange (?,?,?)}");
			
			ps = conn.prepareStatement(statement);			
			ps.setInt(1, this.getCurrentHotel().getHotelId());			
			ps.setString(2, statDateFrom);
			ps.setString(3, statDateTo);					
						
			rs = ps.executeQuery();
			
			if(rs != null ){					

				while(rs.next()) {
					HotelTotalRooms totalRooms = new HotelTotalRooms();
					totalRooms.setHotelId(rs.getInt("HotelID"));
					totalRooms.setTotalRoomsDate(rs.getString("TotalRoomsDate"));
					totalRooms.setTotalRooms(rs.getInt("TotalRooms"));	
					
					lstHotelTotalRooms.add(totalRooms);
				}
			}
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
		}
	
		finally { close(rs, ps, conn);};	       	
				
		System.out.println("*********** ProFormaData - LoadHotelTotalRoomsData - END - " + new Date() +" ***********");						
		
		return lstHotelTotalRooms;		
	}
	
	/**
	 * Load Segment data (Snapshot Transient Segment Data)
	 * @param snapshotId, statDateFrom, statDateTo, customerId
	 * @return SnapshotSegmentData
	 */
	
	public List<SnapshotSegmentData> LoadAppSegmentData(int snapshotId, String statDateFrom, String statDateTo, int customerId){
		
		System.out.println("*********** ProFormaData - LoadAppSegmentData - BEGIN - " + new Date() +" ***********");
											
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
						
		List<SnapshotSegmentData> lstSegmentData = new ArrayList<SnapshotSegmentData>();
		
		try{	
						
			conn = this.getCurrentHRR3Connection();	
			
			String statement = ("{call RFT_SnapshotSegmentData_List_For_ProForma (?,?,?,?,?)}");
			
			ps = conn.prepareStatement(statement);			
			ps.setInt(1, snapshotId);
			ps.setInt(2, this.getCurrentHotel().getHotelId());
			ps.setString(3, statDateFrom);
			ps.setString(4, statDateTo);
			ps.setInt(5, customerId);
		
						
			rs = ps.executeQuery();
			
			if(rs != null ){					

				while(rs.next()) {	
					
					SnapshotSegmentData segmentData = new SnapshotSegmentData();
					
					segmentData.setSnapshotId(rs.getInt("SnapshotID"));
					segmentData.setHotelId(rs.getInt("HotelID"));
					segmentData.setStatDate(rs.getString("StatDate"));
					segmentData.setStatDateName(rs.getString("StatDateName"));
					segmentData.setOccTotal(rs.getInt("OccTotal"));
					segmentData.setRevTotal(rs.getBigDecimal("RevTotal"));
					segmentData.setAdrTotal(rs.getBigDecimal("AdrTotal"));
					
					lstSegmentData.add(segmentData);
				}
			}
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
		}
	
		finally { close(rs, ps, conn);};	       	
				
		System.out.println("*********** ProFormaData - LoadAppSegmentData - END - " + new Date() +" ***********");						
		
		return lstSegmentData;
	}
	
	
	/**
	 * Load Transient data (Snapshot Transient  Data)
	 * @param snapshotId, statDateFrom, statDateTo
	 * @return SnapshotSegmentData
	 */
	
	public List<SnapshotTransientData> LoadAppTransientData(int snapshotId, String statDateFrom, String statDateTo){
		
		System.out.println("*********** ProFormaData - LoadAppTransientData - BEGIN - " + new Date() +" ***********");
											
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;				
		
		List<SnapshotTransientData> lsTransientData = new ArrayList<SnapshotTransientData>();
		
		try{	
						
			conn = this.getCurrentHRR3Connection();	
			
			String statement = ("{call RFT_SnapshotTransientData_List_For_ProForma (?,?,?,?)}");
			
			ps = conn.prepareStatement(statement);			
			ps.setInt(1, snapshotId);
			ps.setInt(2, this.getCurrentHotel().getHotelId());
			ps.setString(3, statDateFrom);
			ps.setString(4, statDateTo);					
						
			rs = ps.executeQuery();
			
			if(rs != null ){					

				while(rs.next()) {
					
					SnapshotTransientData transientData = new SnapshotTransientData();
					
					transientData.setSnapshotId(rs.getInt("SnapshotID"));
					transientData.setHotelId(rs.getInt("HotelID"));
					transientData.setStatDate(rs.getString("StatDate"));
					transientData.setComments(rs.getString("Comments"));
					transientData.setDow(rs.getString("DOW"));
					
					lsTransientData.add(transientData);
				}
			}
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
		}
	
		finally { close(rs, ps, conn);};	       	
				
		System.out.println("*********** ProFormaData - LoadAppTransientData - END - " + new Date() +" ***********");						
		
		return lsTransientData;
	}
	
	

	/**
	 * Load Proforma Data
	 * @param snapshotId, statDateFrom, statDateTo
	 * @return ProFormaData
	 */
	
	public List<ProFormaData> LoadAppProFormaData(int snapshotId, String statDateFrom, String statDateTo){
		
		System.out.println("*********** ProFormaData - LoadAppProFormaData - BEGIN - " + new Date() +" ***********");
											
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
			
		List<ProFormaData> lstProfoFormaData = new ArrayList<ProFormaData>();
		
		try{	
						
			conn = this.getCurrentHRR3Connection();	
			
			String statement = ("{call RFT_ftProFormaData_List (?,?,?,?)}");
			
			ps = conn.prepareStatement(statement);			
			ps.setInt(1, snapshotId);
			ps.setInt(2, this.getCurrentHotel().getHotelId());
			ps.setString(3, statDateFrom);
			ps.setString(4, statDateTo);					
						
			rs = ps.executeQuery();
			
			if(rs != null ){					

				while(rs.next()) {	
					
					ProFormaData proformaData = new ProFormaData();
					
					proformaData.setSnapshotId(rs.getInt("SnapshotID"));
					proformaData.setHotelId(rs.getInt("HotelID"));
					proformaData.setiChangeRms(rs.getInt("ichange_rms"));
					proformaData.setiChangeRate(rs.getDouble("IChangeRate"));
					proformaData.setiPropBaseRooms(rs.getInt("IPropBaseRooms"));
					proformaData.setiPropBaseRate(rs.getDouble("IPropBaseRate"));
					proformaData.setiDispAdrOverride(rs.getDouble("IDispAdrOverride"));
					
					lstProfoFormaData.add(proformaData);
				}
			}
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
		}
	
		finally { close(rs, ps, conn);};	       	
				
		System.out.println("*********** ProFormaData - LoadAppProFormaData - END - " + new Date() +" ***********");						
		
		return lstProfoFormaData;
	}
	
	
	public UserPreferencesProforma generateAppReportData(UserPreferencesProforma userPreferencesProforma, String statDateFrom, String statDateTo ){
		
		List<ReportData> lstReportData = new ArrayList<ReportData>();
						
		for (SnapshotSegmentData segmentDataRow : userPreferencesProforma.getLstSegmentData()){
			
			ReportData reportDataRow = new ReportData();
			
			reportDataRow.setIsSummaryRow(false);
			reportDataRow.setStatDate(segmentDataRow.getStatDate());
			reportDataRow.setBaseRms(segmentDataRow.getOccTotal());
			reportDataRow.setBaseRev(segmentDataRow.getRevTotal().setScale(0, RoundingMode.HALF_EVEN));
			reportDataRow.setBaseRate(this.calculateADR(reportDataRow.getBaseRms(), reportDataRow.getBaseRev()));
			
			int totalRoomsOfDay = this.FindTotalRoomsOfDay(userPreferencesProforma.getLstTotalRooms(), segmentDataRow.getStatDate());
			
			reportDataRow = this.getTransientInfoValues(userPreferencesProforma.getLstTransientData(), reportDataRow, segmentDataRow.getStatDate(), totalRoomsOfDay);
			reportDataRow = this.getProFormaValues(userPreferencesProforma, reportDataRow, segmentDataRow, totalRoomsOfDay);
			reportDataRow = this.calculateProFormaValues(userPreferencesProforma, reportDataRow, segmentDataRow.getStatDate(), totalRoomsOfDay);
																		
			
			lstReportData.add(reportDataRow);
			
		}				
		
		userPreferencesProforma.setLstReportData(lstReportData);
		
		userPreferencesProforma = this.generateAppReportDataSummaryRows(userPreferencesProforma, statDateFrom, statDateTo);
		 
			
		return userPreferencesProforma;
	}
	
	
	public ReportData getTransientInfoValues(List<SnapshotTransientData> lstTransientData, ReportData reportDataRow, String segmentRowStatDate,int  totalRoomsOfDay){
		
		for(SnapshotTransientData  transientData: lstTransientData){
			
			if(transientData.getStatDate().equals(segmentRowStatDate)){
				reportDataRow.setComments(transientData.getComments());
				reportDataRow.setDow(transientData.getDow());			
			}
		}		
		return reportDataRow;		
	}

	
	public ReportData getProFormaValues(UserPreferencesProforma userPreferencesProforma, ReportData reportDataRow, SnapshotSegmentData segmentRow,int  totalRoomsOfDay){
		
		ProFormaData proFormaData = null;
		proFormaData = this.findByStatDate(userPreferencesProforma.getLstProFormaData(), segmentRow.getStatDate());
		 
		if (proFormaData == null){
			proFormaData = new ProFormaData();
		}
			
		//	if(proFormaData.getStatDate().equals(segmentRow.getStatDate())){
				
		reportDataRow.setChangeRms(proFormaData.getiChangeRms());
		reportDataRow.setChangeRate(new BigDecimal(proFormaData.getiChangeRate()));
		reportDataRow.setChangeRev(this.calculateRevenue(reportDataRow.getChangeRms(), reportDataRow.getChangeRev(),0));
		reportDataRow.setPropBaseRms(proFormaData.getiPropBaseRooms());
		reportDataRow.setPropBaseRate(new BigDecimal(proFormaData.getiPropBaseRate()));
		reportDataRow.setDispAdrOverride(proFormaData.getiDispAdrOverride());
		
		if (this.validateWeeklyInputEntered(userPreferencesProforma)){
			//1 = part A
			//2 = part B
			//0 = none
			int retType = this.findWeeklyInputValues(userPreferencesProforma, segmentRow.getStatDate());
			
			if (retType > 0) {
				reportDataRow.setPropBaseRms(this.getWeelyInputRooms(userPreferencesProforma, segmentRow.getStatDateName(), retType));	
				reportDataRow.setPropBaseRate(this.getWeelyInputRate(userPreferencesProforma, segmentRow.getStatDateName(), retType));
			}										
		}
							
			//}
			
		
		return reportDataRow;
	}

	 
	public ReportData calculateProFormaValues(UserPreferencesProforma userPreferencesProforma, ReportData reportDataRow,String statDate,int  totalRoomsOfDay){
		
		 
		int occRooms  = reportDataRow.getBaseRms() + reportDataRow.getChangeRms();
		BigDecimal occRev = reportDataRow.getBaseRev().add(reportDataRow.getChangeRev());
		
		if (occRooms > totalRoomsOfDay)
			reportDataRow.setOccRms(totalRoomsOfDay);
		else
			reportDataRow.setOccRms(occRooms);
		
		// verificar el tipo de dato BigDecimal
		reportDataRow.setTotAdr(calculateADR(reportDataRow.getOccRms(), occRev));
		reportDataRow.setTotRev(calculateRevenue(reportDataRow.getOccRms(), reportDataRow.getTotAdr(),0));
		
        if((reportDataRow.getPropBaseRms() + reportDataRow.getOccRms()) < totalRoomsOfDay)
        	reportDataRow.setDispRms(0);
        else
        	reportDataRow.setDispRms(totalRoomsOfDay - reportDataRow.getPropBaseRms() - reportDataRow.getOccRms()); 
        	
        if (reportDataRow.getDispAdrOverride() > 0) 
        	reportDataRow.setDispAdr(new BigDecimal(reportDataRow.getDispAdrOverride()));
        	      			
        else
        	reportDataRow.setDispAdr(reportDataRow.getTotAdr());
        	
       //Formula : .IncRev = (.PropBaseRms * .PropBaseRate) + (.DispRms * .DispAdr)
    	BigDecimal incRevProp = reportDataRow.getPropBaseRate().multiply(new BigDecimal(reportDataRow.getPropBaseRms()));    			
    	BigDecimal incRevDisp = reportDataRow.getDispAdr().multiply(new BigDecimal(reportDataRow.getDispRms())); 
    	
    	reportDataRow.setIncRev(incRevProp.add(incRevDisp ).setScale(0, RoundingMode.HALF_EVEN));
        	
    	reportDataRow.setNewTotRev(reportDataRow.getTotRev().add(reportDataRow.getIncRev()).setScale(0, RoundingMode.HALF_EVEN));

    	// Formula : .IncProfit = (.PropBaseRms * (.PropBaseRate - Me.AppUserPreferences.CPOR.Base)) + (.DispRms * (.DispAdr - Me.AppUserPreferences.CPOR.Displaced))

    	BigDecimal incProfit1 =  reportDataRow.getPropBaseRate().subtract(userPreferencesProforma.getBase()).multiply(new BigDecimal(reportDataRow.getPropBaseRms())); 
        BigDecimal incProfit2 =	 reportDataRow.getDispAdr().subtract(userPreferencesProforma.getDisplaced()).multiply(new BigDecimal(reportDataRow.getDispRms())); 
    	
        reportDataRow.setIncProfit(incProfit1.add(incProfit2).setScale(0, RoundingMode.HALF_EVEN));                    		
		
		return reportDataRow;
	}
	
	
	
	// Generate Summary Rows
	public UserPreferencesProforma generateAppReportDataSummaryRows(UserPreferencesProforma userPreferencesProforma, String statDateFrom, String statDateTo){
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
							
				
	    Calendar statDateStart = Calendar.getInstance();
	    Calendar statDateEnd = Calendar.getInstance();
	    
	    try {
	    	
	    	statDateStart.setTime(dateFormat.parse(statDateFrom));
	    	statDateStart.set(Calendar.HOUR_OF_DAY, 0);
	    	statDateStart.set(Calendar.MINUTE,0);
	    	statDateStart.set(Calendar.SECOND,0);
	    	statDateStart.set(Calendar.MILLISECOND, 0);
	    	
			statDateEnd.setTime(dateFormat.parse(statDateTo));
			statDateEnd.set(Calendar.HOUR_OF_DAY, 0);
			statDateEnd.set(Calendar.MINUTE,0);
			statDateEnd.set(Calendar.SECOND,0);
			statDateEnd.set(Calendar.MILLISECOND, 0);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    	 	    	    	    	    	   		  		
	    /*
		int startYear = statDateStart.get(Calendar.YEAR);
        int endYear =  statDateEnd.get(Calendar.YEAR);        
		int startMonth = statDateStart.get(Calendar.MONTH) + 1;
        int starDay = statDateStart.get(Calendar.DAY_OF_MONTH);
        int endMonth = statDateEnd.get(Calendar.MONTH) + 1; 
        int endDay = statDateEnd.get(Calendar.DAY_OF_MONTH);
       */
        
        ReportData reportRowMonth = null;
        ReportData reportYearlyRow = null;
        
        List<ReportData> lstSummaryReportDataRows = new ArrayList<ReportData>();
        
        for(int yearInx = statDateStart.get(Calendar.YEAR); yearInx <= statDateEnd.get(Calendar.YEAR); yearInx++){
        	
        	// Clear Report Yearly Row
        	if (reportYearlyRow !=  null){
        		reportYearlyRow = null;
        	}
        	
        	for(int monthInx=1; monthInx <= 12; monthInx++){
        		
        		Calendar SummaryDateStart = Calendar.getInstance();
                Calendar SummaryDateEnd = Calendar.getInstance();

                SummaryDateStart.set(yearInx, monthInx - 1, 1,0,0,0); //Last Zeros are for hour, minute and second
                SummaryDateStart.set(Calendar.MILLISECOND, 0);
                SummaryDateEnd.set(yearInx, monthInx - 1,  SummaryDateStart.getActualMaximum(Calendar.DAY_OF_MONTH),0,0,0);
                SummaryDateEnd.set(Calendar.MILLISECOND, 0);
                                                
        		
        		if(isSummaryDateIncluded(SummaryDateStart, SummaryDateEnd, statDateStart, statDateEnd)){        			        			
        			
        			// Check and Prepare Yearly Total Row
        			if (reportYearlyRow == null){
        				reportYearlyRow = new ReportData();
        				reportYearlyRow.setIsSummaryRow(true);
        				reportYearlyRow.setComments("Annual Total");        				
        			}
        			
        			// Get summary month
        			reportRowMonth = this.getMonthlySummaryRow(userPreferencesProforma.getLstReportData(), SummaryDateStart, SummaryDateEnd);
        			
        			lstSummaryReportDataRows.add(reportRowMonth);
        			
        			//add month to summary year
        			reportYearlyRow =  this.updateYearlySummaryRow(reportRowMonth, reportYearlyRow);        			        			
        			
        		}
        		
        	}
        	
        	 //F&B Profit and Other Profit to be added to the annual inc. profit
        	 // incRev  =  incRev + otherRevenue + fbRevenue
        	reportYearlyRow.setIncRev(reportYearlyRow.getIncRev().add(new BigDecimal(userPreferencesProforma.getOtherRevenue())).add(new BigDecimal(userPreferencesProforma.getfBRevenue())).setScale(0, RoundingMode.CEILING));
        	reportYearlyRow.setIncProfit(reportYearlyRow.getIncProfit().add(new BigDecimal(userPreferencesProforma.getOtherRevenueProfit())).add(new BigDecimal(userPreferencesProforma.getfBRevenueProfit())));
        	
        	lstSummaryReportDataRows.add(reportYearlyRow);
        	
        }
		
        userPreferencesProforma.setLstSummaryReportData(lstSummaryReportDataRows);
		
		return userPreferencesProforma;
	}
	
	
	public ReportData updateYearlySummaryRow(ReportData reportRowMonth, ReportData reportYearlyRow){
		
		
		reportYearlyRow.setBaseRms(reportYearlyRow.getBaseRms() + reportRowMonth.getBaseRms());
		reportYearlyRow.setBaseRev(reportYearlyRow.getBaseRev().add(reportRowMonth.getBaseRev()));
		reportYearlyRow.setBaseRate(this.calculateADR(reportYearlyRow.getBaseRms(), reportYearlyRow.getBaseRev()));
		
		reportYearlyRow.setChangeRms(reportYearlyRow.getChangeRms() + reportRowMonth.getChangeRms());
		reportYearlyRow.setChangeRev(reportYearlyRow.getChangeRev().add(reportRowMonth.getChangeRev()));
		reportYearlyRow.setChangeRate(this.calculateADR(reportYearlyRow.getChangeRms(), reportYearlyRow.getChangeRev()));
		
		reportYearlyRow.setOccRms(reportYearlyRow.getOccRms() +  reportRowMonth.getOccRms());
		reportYearlyRow.setTotRev(reportYearlyRow.getTotRev().add(reportRowMonth.getTotRev()));
		reportYearlyRow.setTotAdr(this.calculateADR(reportYearlyRow.getOccRms(), reportYearlyRow.getTotRev()));
		
		reportYearlyRow.setPropBaseRms(reportYearlyRow.getPropBaseRms() + reportRowMonth.getPropBaseRms());
		// .PropBaseRate = Nothing

        reportYearlyRow.setDispRms(reportYearlyRow.getDispRms() +  reportRowMonth.getDispRms());
        reportYearlyRow.setIncRev(reportYearlyRow.getIncRev().add(reportRowMonth.getIncRev()));
        reportYearlyRow.setNewTotRev(reportYearlyRow.getNewTotRev().add(reportRowMonth.getNewTotRev()).setScale(0, RoundingMode.HALF_EVEN));
        reportYearlyRow.setIncProfit(reportYearlyRow.getIncProfit().add(reportRowMonth.getIncProfit()));
                                
        // .DispAdr = Nothing
        reportYearlyRow.setDispAdrOverride(reportYearlyRow.getDispAdrOverride() + reportRowMonth.getDispAdrOverride());
		
        return reportYearlyRow;
	}
	
	
	public ReportData getMonthlySummaryRow(List<ReportData> lstReportData, Calendar SummaryDateStart, Calendar  SummaryDateEnd){
		
		ReportData monthlySummaryRow = null;
		
		monthlySummaryRow = this.getSumReportDataRows(lstReportData, SummaryDateStart, SummaryDateEnd);
		
		
		monthlySummaryRow.setIsSummaryRow(true);
		monthlySummaryRow.setComments(new SimpleDateFormat("MMMM").format(SummaryDateStart.getTime()));
		monthlySummaryRow.setDow("");
		monthlySummaryRow.setStatDate("");
		 					
		return monthlySummaryRow;
		
	}
	
	/*
	public int getSummaryTotalRooms (List<HotelTotalRooms> lstTotalRooms, Calendar dateStart, Calendar dateEnd){
		
		//Numero de días del mes
		int days = (int)(dateStart.getTimeInMillis() - dateEnd.getTimeInMillis()) / (1000 * 60 * 60 * 24);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		int totalRooms = 0;
		String statDate = dateFormat.format(dateStart.getTime());
		
		for(int nDay = 0; nDay <= days; nDay++){
									
			totalRooms += FindTotalRoomsOfDay(lstTotalRooms,statDate );
			dateStart.add(Calendar.DATE, 1);
			statDate = dateFormat.format(dateStart.getTime());
			
		}			
		
		return totalRooms;
	}
	
	*/
	public ReportData getSumReportDataRows(List<ReportData> lstReportData, Calendar dateStart, Calendar dateEnd){
		
		int summaryBaseRms = 0;
		BigDecimal summaryBaseRev = BigDecimal.ZERO;
		BigDecimal summaryBaseRate = BigDecimal.ZERO;
		int summaryChangeRms = 0;
		BigDecimal summaryChangeRev = BigDecimal.ZERO;
		BigDecimal summaryChangeRate = BigDecimal.ZERO;
		int summaryOccRms = 0;
		BigDecimal summaryTotRev = BigDecimal.ZERO;
		BigDecimal summaryTotAdr = BigDecimal.ZERO;;
		int summaryPropBaseRms = 0;
		int summaryDispRms = 0;
		BigDecimal summaryIncRev = BigDecimal.ZERO;
		BigDecimal summaryNewToRev = BigDecimal.ZERO;
		BigDecimal summaryIncProfit = BigDecimal.ZERO;
		//BigDecimal summaryDispAdr = BigDecimal.ZERO;
		double summaryDisAdrOverride = 0;										
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
		for(ReportData reportDataRow : lstReportData){
			Calendar reportStatDateRow = Calendar.getInstance();
			
				reportStatDateRow.setTime(dateFormat.parse(reportDataRow.getStatDate()));
				reportStatDateRow.set(Calendar.HOUR_OF_DAY, 0);
				reportStatDateRow.set(Calendar.MINUTE,0);
				reportStatDateRow.set(Calendar.SECOND,0);
				reportStatDateRow.set(Calendar.MILLISECOND, 0);
			
			if(reportStatDateRow.compareTo(dateStart) >= 0 && reportStatDateRow.compareTo(dateEnd)<=0){
				summaryBaseRms = summaryBaseRms + reportDataRow.getBaseRms();
				summaryBaseRev = summaryBaseRev.add(reportDataRow.getBaseRev());
				
				summaryChangeRms = summaryChangeRms + reportDataRow.getChangeRms();
				summaryChangeRev = summaryChangeRev.add(reportDataRow.getChangeRev());
				
				summaryOccRms = summaryOccRms + reportDataRow.getOccRms();
				summaryTotRev = summaryTotRev.add(reportDataRow.getTotRev());
				
				summaryPropBaseRms = summaryPropBaseRms + reportDataRow.getPropBaseRms();
				summaryDispRms = summaryDispRms + reportDataRow.getDispRms();
				summaryIncRev = summaryIncRev.add(reportDataRow.getIncRev());
				summaryNewToRev = summaryNewToRev.add(reportDataRow.getNewTotRev());
				summaryIncProfit = summaryIncProfit.add(reportDataRow.getIncProfit());
				summaryDisAdrOverride = summaryDisAdrOverride + reportDataRow.getDispAdrOverride();				
				
			}
			
		}
		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		summaryBaseRate = this.calculateADR(summaryBaseRms, summaryBaseRev);
		summaryChangeRate = this.calculateADR(summaryChangeRms, summaryChangeRev);
		summaryTotAdr = this.calculateADR(summaryOccRms, summaryTotRev);
		
		ReportData summaryRows = new ReportData();
		
		summaryRows.setBaseRms(summaryBaseRms);
		summaryRows.setBaseRev(summaryBaseRev);
		summaryRows.setBaseRate(summaryBaseRate);
		summaryRows.setChangeRms(summaryChangeRms);
		summaryRows.setChangeRev(summaryChangeRev);
		summaryRows.setChangeRate(summaryChangeRate);
		summaryRows.setOccRms(summaryOccRms);
		summaryRows.setTotRev(summaryTotRev);
		summaryRows.setTotAdr(summaryTotAdr);
		summaryRows.setPropBaseRms(summaryPropBaseRms);
		summaryRows.setDispRms(summaryDispRms);
		summaryRows.setIncRev(summaryIncRev);
		summaryRows.setNewTotRev(summaryNewToRev);
		summaryRows.setIncProfit(summaryIncProfit);
		summaryRows.setDispAdrOverride(summaryDisAdrOverride);
		
		
		return summaryRows;
		
	}
	
	
	public boolean isSummaryDateIncluded(Calendar SummaryDateStart, Calendar SummaryDateEnd, Calendar statDateStart, Calendar statDateEnd) {
		
		Calendar cStatDateStart = Calendar.getInstance();
		Calendar cStatDateEnd = Calendar.getInstance();
		
		cStatDateStart.set(statDateStart.get(Calendar.YEAR), statDateStart.get(Calendar.MONTH), 1, 0, 0, 0);
		cStatDateEnd.set(statDateEnd.get(Calendar.YEAR), statDateEnd.get(Calendar.MONTH), statDateEnd.getActualMaximum(Calendar.DAY_OF_MONTH), 0, 0, 0);
		        
        if ((SummaryDateStart.compareTo(cStatDateStart)>= 0 && SummaryDateStart.compareTo(cStatDateEnd)<=0) || (SummaryDateEnd.compareTo(cStatDateStart)>=0 && SummaryDateEnd.compareTo(cStatDateEnd)<=0) ){
        	return true;
        }
		
		return false;
	}
	
   
	public ProFormaData  findByStatDate(List<ProFormaData> lstProForma, String statDate){
		
		ProFormaData proForma = null;
		
		for(ProFormaData proFormaData : lstProForma){
			if (statDate.equals(proFormaData.getStatDate())){
				return proFormaData;
			}
		}
		
		return proForma;
	}
	
	public int getWeelyInputRooms(UserPreferencesProforma preferencesProforma, String statDateName, int type){
		int rooms = 0;
				
		switch (statDateName) {
		case "Sunday":
			rooms = (type  == 1)? preferencesProforma.getPartARmsSunday() : preferencesProforma.getPartBRmsSunday();
			break;								
		case "Monday":
			rooms = (type  == 1)?preferencesProforma.getPartARmsMonday() : preferencesProforma.getPartBRmsMonday();
			break;	
		case "Tuesday":
			rooms = (type  == 1)?preferencesProforma.getPartARmsTuesday() : preferencesProforma.getPartBRmsTuesday();
			break;	
		case "Wednesday":
			rooms = (type  == 1)?preferencesProforma.getPartARmsWednesday() : preferencesProforma.getPartBRmsWednesday();
			break;	
		case "Thursday":
			rooms =(type  == 1)? preferencesProforma.getPartARmsThursday() : preferencesProforma.getPartBRmsThursday();
			break;	
		case "Friday":
			rooms = (type  == 1)?preferencesProforma.getPartARmsFriday() : preferencesProforma.getPartBRmsFriday();
			break;	
		case "Saturday":
			rooms = (type  == 1)?preferencesProforma.getPartARmsSaturday(): preferencesProforma.getPartBRmsSaturday();
			break;					
		default:
			break;
		}	
		
		return rooms;
	}
	
	
	public BigDecimal getWeelyInputRate(UserPreferencesProforma preferencesProforma, String statDateName, int type){
		int rate =0;
				
		switch (statDateName) {
		case "Sunday":
			rate = (type  == 1)? preferencesProforma.getPartAADRSunday() : preferencesProforma.getPartBADRSunday();
			break;								
		case "Monday":
			rate = (type  == 1)?preferencesProforma.getPartAADRMonday() : preferencesProforma.getPartBADRMonday();
			break;	
		case "Tuesday":
			rate = (type  == 1)?preferencesProforma.getPartAADRTuesday() : preferencesProforma.getPartBADRTuesday();
			break;	
		case "Wednesday":
			rate = (type  == 1)?preferencesProforma.getPartAADRWednesday() : preferencesProforma.getPartBADRWednesday();
			break;	
		case "Thursday":
			rate =(type  == 1)? preferencesProforma.getPartAADRThursday() : preferencesProforma.getPartBADRThursday();
			break;	
		case "Friday":
			rate = (type  == 1)?preferencesProforma.getPartAADRFriday() : preferencesProforma.getPartBADRFriday();
			break;	
		case "Saturday":
			rate = (type  == 1)?preferencesProforma.getPartAADRSaturday(): preferencesProforma.getPartBADRSaturday();
			break;					
		default:
			break;
		}	
		
		return new BigDecimal(rate);
	}
	
	
	public int findWeeklyInputValues(UserPreferencesProforma preferencesProforma, String statDate){
		//Si llegan a este metodo, debe tener las fechas de ambas partes en el objeto
		// las fechas de part A y part B no debe sobreponerse.
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		Date partADateFrom = null;
		Date partADateTo = null;		
		Date partBDateFrom = null;
		Date partBDateTo = null;
		Date dStatDate = null;
				
		try {
			
			partADateFrom = dateFormat.parse(preferencesProforma.getPartADateFrom());
			partADateTo =  dateFormat.parse(preferencesProforma.getPartADateTo());
			partBDateFrom = dateFormat.parse(preferencesProforma.getPartBDateFrom());
			partBDateTo =  dateFormat.parse(preferencesProforma.getPartBDateTo());
			
			dStatDate = dateFormat.parse(statDate);
			
			if ( dStatDate.compareTo(partADateFrom) >= 0  && dStatDate.compareTo(partADateTo) <= 0){
				return 1;
			}
			
			if ( dStatDate.compareTo(partBDateFrom) >= 0  && dStatDate.compareTo(partBDateTo) <= 0){
				return 2;
			}											
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
							
		return 0;
	}
	
	public boolean validateWeeklyInputEntered(UserPreferencesProforma preferencesProforma){
		
		boolean partA = true;
		boolean partB = true;
		
		if (preferencesProforma.getPartARmsSunday() == 0 
			&& preferencesProforma.getPartARmsMonday() == 0
			&& preferencesProforma.getPartARmsTuesday() == 0
			&& preferencesProforma.getPartARmsWednesday() == 0
			&& preferencesProforma.getPartARmsThursday() == 0
			&& preferencesProforma.getPartARmsFriday() == 0
			&& preferencesProforma.getPartARmsSaturday() == 0
			&& preferencesProforma.getPartAADRSaturday() == 0
			&& preferencesProforma.getPartAADRMonday() == 0
			&& preferencesProforma.getPartAADRTuesday() == 0
			&& preferencesProforma.getPartAADRWednesday() == 0
			&& preferencesProforma.getPartAADRThursday() == 0
			&& preferencesProforma.getPartAADRFriday() == 0
			&& preferencesProforma.getPartAADRSaturday() == 0){
			
			partA =  false;
		}
		
		if (preferencesProforma.getPartARmsSunday() == 0 
			&& preferencesProforma.getPartBRmsMonday() == 0
			&& preferencesProforma.getPartBRmsTuesday() == 0
			&& preferencesProforma.getPartBRmsWednesday() == 0
			&& preferencesProforma.getPartBRmsThursday() == 0
			&& preferencesProforma.getPartBRmsFriday() == 0
			&& preferencesProforma.getPartBRmsSaturday() == 0
			&& preferencesProforma.getPartBADRSaturday() == 0
			&& preferencesProforma.getPartBADRMonday() == 0
			&& preferencesProforma.getPartBADRTuesday() == 0
			&& preferencesProforma.getPartBADRWednesday() == 0
			&& preferencesProforma.getPartBADRThursday() == 0
			&& preferencesProforma.getPartBADRFriday() == 0
			&& preferencesProforma.getPartBADRSaturday() == 0){
			
			partB = false;
		}
					
		
		if (partA == false && partB == false)
			return false;
		
		return true;
		
	}	
	
	public BigDecimal calculateADR (int occ, BigDecimal rev){
		if (occ == 0)
			return BigDecimal.ZERO;
		else
			return rev.divide(new BigDecimal(occ),2,RoundingMode.HALF_EVEN);		
	}

	public BigDecimal calculateRevenue(int occ, BigDecimal rate, int roundValue){
      //No decimals, round half even
		if (occ == 0 || rate.compareTo(BigDecimal.ZERO) == 0)
			return BigDecimal.ZERO;
		else{				 
			return  rate.multiply(new BigDecimal(occ)).setScale(roundValue, RoundingMode.HALF_EVEN);
		}
	}
	
	public int FindTotalRoomsOfDay (List<HotelTotalRooms> lstTotalRooms, String statDate){
		
		int rooms = 0;
		
		for(HotelTotalRooms totalRooms : lstTotalRooms){
			
			if (statDate.equals(totalRooms.getTotalRoomsDate())){
				if (totalRooms.getTotalRooms()  == 0)
					return this.getCurrentHotel().getTotalRooms();
				else
					return totalRooms.getTotalRooms();
			}
		}
		
		return this.getCurrentHotel().getTotalRooms();		
	}
	
	
	
	public UserPreferencesProforma UpdateReportData(UserPreferencesProforma userPreferencesProforma, ReportData rowToUpdate, String statDateFrom, String statDateTo){
		
		int totalRoomsOfDay = FindTotalRoomsOfDay(userPreferencesProforma.getLstTotalRooms(), rowToUpdate.getStatDate());
		
		// Check if Disp ADR Override has changed
		if (rowToUpdate.getDispAdrOverride() >  0){
			rowToUpdate.setDispAdr(new BigDecimal(rowToUpdate.getDispAdrOverride()));				
		}
		
        // Calculate ReportData Row			
		rowToUpdate.setChangeRev(this.calculateRevenue(rowToUpdate.getChangeRms(), rowToUpdate.getChangeRate(), 0));
									
		rowToUpdate =  this.calculateProFormaValues(userPreferencesProforma, rowToUpdate, rowToUpdate.getStatDate(), totalRoomsOfDay);
		
		userPreferencesProforma.setLstReportData(this.replaceReportDataRow(userPreferencesProforma.getLstReportData(), rowToUpdate));
		
		userPreferencesProforma = this.generateAppReportDataSummaryRows(userPreferencesProforma, statDateFrom, statDateTo);
		
		return userPreferencesProforma;
	}
	
	
	public ReportData  findByStatDateReportData(List<ReportData> lstReportData, String statDate){
		
		ReportData reportData = null;
		
		for(ReportData reportDataRow : lstReportData){
			if (statDate.equals(reportDataRow.getStatDate())){
				return reportDataRow;
			}
		}
		
		return reportData;
	}
	
	public List<ReportData> replaceReportDataRow ( List<ReportData> lstReportData,  ReportData newReportData){
		
		for(ReportData reportData : lstReportData){
			if(reportData.equals(newReportData.getStatDate())){
				reportData.setBaseRate(newReportData.getBaseRate());
				reportData.setBaseRev(newReportData.getBaseRev());
				reportData.setBaseRms(newReportData.getBaseRms());
				reportData.setChangeRate(newReportData.getChangeRate());
				reportData.setChangeRev(newReportData.getChangeRev());
				reportData.setChangeRms(newReportData.getChangeRms());
				reportData.setDispAdr(newReportData.getDispAdr());
				reportData.setDispAdrOverride(newReportData.getDispAdrOverride());
				reportData.setDispRms(newReportData.getDispRms());
				reportData.setIncProfit(newReportData.getIncProfit());
				reportData.setIncRev(newReportData.getIncRev());
				reportData.setNewTotRev(newReportData.getNewTotRev());
				reportData.setOccRms(newReportData.getOccRms());
				reportData.setPropBaseRate(newReportData.getPropBaseRate());
				reportData.setPropBaseRms(newReportData.getPropBaseRms());
				reportData.setTotAdr(newReportData.getTotAdr());
				reportData.setTotRev(newReportData.getTotRev());				
			}
			
		}
				
		return  lstReportData;
	}
	
}
