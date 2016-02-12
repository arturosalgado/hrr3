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
import com.hrr3.entity.renovationImpact.HotelTotalRooms;
import com.hrr3.entity.renovationImpact.ReportData;
import com.hrr3.entity.renovationImpact.SSRSegmentData;
import com.hrr3.entity.renovationImpact.SnapshotSegmentData;
import com.hrr3.entity.renovationImpact.TransientSegmentData;
import com.hrr3.entity.renovationImpact.UserPreferencesRenovationImpact;


public class RenovationImpactDAO extends RM3AbstractDAO {

	public RenovationImpactDAO (Hotel currentHotel) {
		super(currentHotel);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Get User Preferences Renovation Impact
	 * @param userId, companyId, 
	 * @return UserPreferencesRenovationImpact Object
	 */
	public UserPreferencesRenovationImpact  loadRenovationPreferences(int userId, int companyId){
		
		System.out.println("*********** loadRenovationPreferences - BEGIN - " + new Date() +" ***********");
		
		UserPreferencesRenovationImpact	preferences = new UserPreferencesRenovationImpact();
	   
	   
	    PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try{	
						
			conn = this.getCurrentHRR3Connection();	
			
			String statement = ("select * from RM3RenovationUserPreferences where user_id = ? and company_id = ? and hotel_id = ?");
			
			ps = conn.prepareStatement(statement);			
			ps.setInt(1, userId );
			ps.setInt(2, companyId);
			ps.setInt(3, this.getCurrentHotel().getHotelId());
			
			rs = ps.executeQuery();
			
			while(rs != null && rs.next()){	
				
				preferences.setUserPreferencesId(rs.getInt("renovationImpactPreferencesId"));
				preferences.setUserId(rs.getInt("user_id"));
				preferences.setHotelId(rs.getInt("hotel_id"));
				preferences.setCompanyId(rs.getInt("company_id"));
				preferences.setStatDateFrom(rs.getString("statDateFrom"));
				preferences.setStatDateTo(rs.getString("statDateTo"));
				preferences.setForecastSnaphotID(rs.getInt("forecastSnaphotID"));
				preferences.setSsrSnapshotID(rs.getInt("ssrSnapshotID"));
				preferences.setUnrealizedTDFactor(rs.getInt("unrealizedTDFactor"));
				preferences.setSelloutThreshold(rs.getInt("selloutThreshold"));
				preferences.setHotelTotalRooms(this.getCurrentHotel().getTotalRooms());
				
				//preferences.setHotelTotalRooms((preferences.getHotelTotalRooms() * preferences.getSelloutThreshold())/100);				
			}
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
		}
	
		finally { close(rs, ps, conn);};	       	
				
		System.out.println("*********** loadRenovationPreferences - END - " + new Date() +" ***********");		
		
	   return preferences;
	}
	
	
	/**
	 * Update User Preferences Renovation Impact
	 * @param UserPreferencesRenovationImpact Object 
	 * @return 
	 */
	public void  updateRenovationPreferences(UserPreferencesRenovationImpact preferences){
		
		System.out.println("*********** updateRenovationPreferences - BEGIN - " + new Date() +" ***********");
					   	   
	    PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try{	
						
			conn = this.getCurrentHRR3Connection();	
			
			String statement = ("update RM3RenovationUserPreferences set " +
							"statDateFrom	= ?, " +
							"statDateTo	= ?, " +
							"forecastSnaphotID	= ?, " +
							"ssrSnapshotID = ?, " +
							"unrealizedTDFactor = ?," +
							"selloutThreshold = ? " +														
							"where user_id = ? and company_id = ? and hotel_id = ? and  renovationImpactPreferencesId = ?");
			
			ps = conn.prepareStatement(statement);			
			
			ps.setString(1, preferences.getStatDateFrom());
			ps.setString(2, preferences.getStatDateTo());
			ps.setInt(3, preferences.getForecastSnaphotID());
			ps.setInt(4, preferences.getSsrSnapshotID());
			ps.setInt(5, preferences.getUnrealizedTDFactor());
			ps.setInt(6, preferences.getSelloutThreshold());
			ps.setInt(7, preferences.getUserId());
			ps.setInt(8, preferences.getCompanyId());
			ps.setInt(9, preferences.getHotelId());
			ps.setInt(10, preferences.getUserPreferencesId());
			
			ps.executeUpdate();											
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
		}
	
		finally { close(rs, ps, conn);};	       	
				
		System.out.println("*********** updateRenovationPreferences - END - " + new Date() +" ***********");				
	   
	}
	
	
	/**
	 * Insert User Preferences Renovation Impact
	 * @param UserPreferencesRenovationImpact Object 
	 * @return 
	 */
	public void  insertRenovationPreferences(UserPreferencesRenovationImpact preferences){
		
		System.out.println("*********** insertRenovationPreferences - BEGIN - " + new Date() +" ***********");
					   	   
	    PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try{	
						
			conn = this.getCurrentHRR3Connection();	
			
			String statement = ("insert into RM3RenovationUserPreferences values (0,?,?,?,?,?,?,?,?,?)");
			
			ps = conn.prepareStatement(statement);			
			
			ps.setInt(1, preferences.getUserId());
			ps.setInt(2, preferences.getCompanyId());
			ps.setInt(3, preferences.getHotelId());			
			ps.setString(4, preferences.getStatDateFrom());
			ps.setString(5, preferences.getStatDateTo());
			ps.setInt(6, preferences.getForecastSnaphotID());
			ps.setInt(7, preferences.getSsrSnapshotID());
			ps.setInt(8, preferences.getUnrealizedTDFactor());
			ps.setInt(9, preferences.getSelloutThreshold());
						
			
			ps.executeUpdate();											
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
		}
	
		finally { close(rs, ps, conn);};	       	
				
		System.out.println("*********** insertRenovationPreferences - END - " + new Date() +" ***********");					  
	}
	
	
	public UserPreferencesRenovationImpact loadRenovationImpactData(UserPreferencesRenovationImpact preferencesRenovation, int snapshotId, int ssrSnapshotId, String statDateFrom, String statDateTo, int customerId){
		
		preferencesRenovation.setForecastSnaphotID(snapshotId);
		preferencesRenovation.setSsrSnapshotID(ssrSnapshotId);
		
		preferencesRenovation.setLstTotalRooms(loadHotelTotalRoomsData(statDateFrom, statDateTo));
		preferencesRenovation.setLstSnapshotData(loadAppSegmentData(snapshotId, ssrSnapshotId, statDateFrom, statDateTo, customerId));
		preferencesRenovation.setLstSSRData(loadAppSSRSegmentData(ssrSnapshotId, statDateFrom, statDateTo, customerId));
		preferencesRenovation.setLstTransentData(loadAppTransientData(snapshotId, statDateFrom, statDateTo));
		
		generateAppReportData(preferencesRenovation, statDateFrom, statDateTo);
		
		
		return preferencesRenovation;
	}
	
	
	
	
	
	/**
	 * Load total rooms by hotel
	 * @param statDateFrom, statDateTo
	 * @return HotelTotalRooms
	 */
	
	public List<HotelTotalRooms> loadHotelTotalRoomsData(String statDateFrom, String statDateTo){
		
		System.out.println("*********** Renovation impact - LoadHotelTotalRoomsData - BEGIN - " + new Date() +" ***********");
											
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
				
		System.out.println("*********** Renovation impact - LoadHotelTotalRoomsData - END - " + new Date() +" ***********");						
		
		return lstHotelTotalRooms;		
	}
	
	
	public List<SnapshotSegmentData> loadAppSegmentData(int  snapshotId, int ssrSnapshotId, String statDateFrom, String statDateTo,int customerId){
		
		System.out.println("*********** loadAppSegmentData - BEGIN - " + new Date() +" ***********");
	   	   
	    PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		List<SnapshotSegmentData> lstSegmentData = new ArrayList<SnapshotSegmentData>();
		
		try{	
						
			conn = this.getCurrentHRR3Connection();	
			
			String statement = ("{call RFT_SnapshotSegmentData_List_For_RenovationImpact (?,?,?,?,?,?)}");
			
			ps = conn.prepareStatement(statement);			
			ps.setInt(1, snapshotId);			
			ps.setInt(2, ssrSnapshotId );
			ps.setInt(3, this.getCurrentHotel().getHotelId());	
			ps.setString(4, statDateFrom);
			ps.setString(5, statDateTo);
			ps.setInt(6, customerId);
						
			rs = ps.executeQuery();	
			
			if(rs != null ){					

				while(rs.next()) {	
					SnapshotSegmentData segmentData = new SnapshotSegmentData();
					
					segmentData.setHotelId(rs.getInt("HotelID"));
					segmentData.setSnapshotId(rs.getInt("SnapshotID"));
					segmentData.setStatDate(rs.getString("StatDate"));					
					segmentData.setOccTotal(rs.getInt("OccTotal"));
					segmentData.setAdrTotal(rs.getBigDecimal("AdrTotal"));
					segmentData.setRevTotal(rs.getBigDecimal("RevTotal"));
					segmentData.setOooRooms(rs.getBigDecimal("OOORooms"));
					
					lstSegmentData.add(segmentData);
				}
			}
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
		}
	
		finally { close(rs, ps, conn);};	       	
				
		System.out.println("*********** loadAppSegmentData - END - " + new Date() +" ***********");	
		
		return lstSegmentData;
	}
	
	
	public List<SSRSegmentData> loadAppSSRSegmentData(int  ssrSnapshotId, String statDateFrom, String statDateTo,int customerId){
		
		System.out.println("*********** loadAppSSRSegmentData - BEGIN - " + new Date() +" ***********");
	   	   
	    PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		List<SSRSegmentData> lstSsrData = new ArrayList<SSRSegmentData>();
		
		try{	
						
			conn = this.getCurrentHRR3Connection();	
			
			String statement = ("{call RFT_SSRSnapshotSegmentData_List_For_RenovationImpact (?,?,?,?)}");
			
			ps = conn.prepareStatement(statement);			
			ps.setInt(1, ssrSnapshotId);			
			ps.setInt(2, this.getCurrentHotel().getHotelId() );			
			ps.setString(3, statDateFrom);
			ps.setString(4, statDateTo);
			
						
			rs = ps.executeQuery();	
			
			if(rs != null ){					

				while(rs.next()) {	
					SSRSegmentData ssrData = new SSRSegmentData();
					
					ssrData.setSsrSnapshotid(rs.getInt("SSRSnapshotID"));
					ssrData.setHotelId(rs.getInt("HotelID"));
					ssrData.setStatDate(rs.getString("StatDate"));
					ssrData.setGroupDemandTD(rs.getBigDecimal("GroupDemandTD"));
					
					lstSsrData.add(ssrData);
				}
			}
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
		}
	
		finally { close(rs, ps, conn);};	       	
				
		System.out.println("*********** loadAppSSRSegmentData - END - " + new Date() +" ***********");	
		
		return lstSsrData;
	}
	
    public List<TransientSegmentData> loadAppTransientData(int  snapshotId, String statDateFrom, String statDateTo){
		
		System.out.println("*********** loadAppTransientData - BEGIN - " + new Date() +" ***********");
	   	   
	    PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		List<TransientSegmentData> lstTransientData = new ArrayList<TransientSegmentData>();
		
		try{	
						
			conn = this.getCurrentHRR3Connection();	
			
			String statement = ("{call RFT_SnapshotTransientData_List_For_RenovationImpact (?,?,?,?)}");
			
			ps = conn.prepareStatement(statement);			
			ps.setInt(1, snapshotId);			
			ps.setInt(2, this.getCurrentHotel().getHotelId() );			
			ps.setString(3, statDateFrom);
			ps.setString(4, statDateTo);
			
						
			rs = ps.executeQuery();	
			
			if(rs != null ){					

				while(rs.next()) {	
					TransientSegmentData transientData = new TransientSegmentData();
					
					transientData.setSnapshotId(rs.getInt("SnapshotID"));
					transientData.setHotelId(rs.getInt("HotelID"));
					transientData.setStatDate(rs.getString("StatDate"));
					transientData.setComments(rs.getString("Comments"));
					transientData.setDow(rs.getString("DOW"));
					transientData.setIsActual(rs.getInt("IsActual"));
					transientData.setTotOccPct(rs.getBigDecimal("TotOccPct"));
														
					lstTransientData.add(transientData);
				}
			}
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
		}
	
		finally { close(rs, ps, conn);};	       	
				
		System.out.println("*********** loadAppTransientData - END - " + new Date() +" ***********");	
		
		return lstTransientData;
	}
    
    public void generateAppReportData (UserPreferencesRenovationImpact preferencesRenovation, String statDateFrom, String statDateTo){
    	int totalRoomsOfDay;
    	
    	List<ReportData> lstReportData = new ArrayList<ReportData>();
    	
    	for (SnapshotSegmentData segmentDataRow : preferencesRenovation.getLstSnapshotData()){
    		
    		ReportData reportDataRow = new ReportData();
    		reportDataRow.setUserPreferencesObj(preferencesRenovation);//To get a ref to the parent
    		
    		totalRoomsOfDay = FindTotalRoomsOfDay(preferencesRenovation.getLstTotalRooms(), segmentDataRow.getStatDate());
    		
    		reportDataRow.setSummaryRow(false);
    		reportDataRow.setStatDate(segmentDataRow.getStatDate());
    		reportDataRow.setOccRooms(segmentDataRow.getOccTotal());
    		reportDataRow.settTlADR(segmentDataRow.getAdrTotal().setScale(2, BigDecimal.ROUND_HALF_EVEN));
    		reportDataRow.settTlRevenue(segmentDataRow.getRevTotal().setScale(0,BigDecimal.ROUND_HALF_EVEN));
    		reportDataRow.setOooRooms(segmentDataRow.getOooRooms().intValue());
    		reportDataRow.setDemandTdowns (getDemandTdownsValue(preferencesRenovation, segmentDataRow.getStatDate()).intValue());
    		
    		getTransientInfoValues(preferencesRenovation, reportDataRow, segmentDataRow.getStatDate());
    		
    		reportDataRow.setVacant(totalRoomsOfDay - reportDataRow.getOccRooms() -  reportDataRow.getOooRooms());
    		
    		if (totalRoomsOfDay == reportDataRow.getOooRooms()){
    			reportDataRow.setOccPctOOOAvailable(BigDecimal.ZERO);    		
    		}else{
    			reportDataRow.setOccPctOOOAvailable(calculatePercentage(new BigDecimal(reportDataRow.getOccRooms()), new BigDecimal(totalRoomsOfDay - reportDataRow.getOooRooms())).setScale(1,BigDecimal.ROUND_HALF_EVEN));    			
    		}
    		
    		calculateDisplacedValues(reportDataRow, preferencesRenovation.getSelloutThreshold(), this.getCurrentHotel().getTotalRooms(), totalRoomsOfDay);
    		
    		lstReportData.add(reportDataRow);
    	}
    	
    	preferencesRenovation.setLstReportData(lstReportData);
    	
    	preferencesRenovation = generateAppReportDataSummaryRows(preferencesRenovation, statDateFrom, statDateTo);
    }
    
    public UserPreferencesRenovationImpact generateAppReportDataSummaryRows (UserPreferencesRenovationImpact preferencesRenovation, String statDateFrom, String statDateTo){
    	
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
                	if ( reportYearlyRow == null){
                		reportYearlyRow = new ReportData();
                		reportYearlyRow.setSummaryRow(true);
                		//reportYearlyRow.setComments("Summary Annual " + yearInx); CR by Carl.
                		reportYearlyRow.setComments("Total"); //CR by Carl.
                		reportYearlyRow.setDow("0");
                		reportYearlyRow.setStatDate("");
                		reportYearlyRow.setaF("");
                	}
                	
                	// Get summary month
                	reportRowMonth = getMonthlySummaryRow(preferencesRenovation, SummaryDateStart, SummaryDateEnd,statDateStart, statDateEnd);

                	//add month to summary year
                	lstSummaryReportDataRows.add(reportRowMonth);
                	
                	//add month to summary year
                	reportYearlyRow =  updateYearlySummaryRow(reportRowMonth, reportYearlyRow);
                	
                }
                               
         	}
         	
         	 lstSummaryReportDataRows.add(reportYearlyRow);
    		 
    	 }
    	
    	 preferencesRenovation.setLstSummaryReportData(lstSummaryReportDataRows);
    	
    	
    	return preferencesRenovation;
    }
    
    
    
    public void calculateDisplacedValues( ReportData reportDataRow, int sellOutThreshold, int hotelTotalRooms, int totalRoomsOfDay){
    	
    	BigDecimal displaced = null;
    	
    	displaced = calculatePercentage(reportDataRow.getTotalRevPar().add(new BigDecimal(reportDataRow.getOooRooms())), new BigDecimal(hotelTotalRooms));
    	
    	if ( displaced.compareTo(new BigDecimal(sellOutThreshold))<= 0){
    		reportDataRow.setDisplacedRooms(0);
    		reportDataRow.setDisplacedRevenue(0);
    		return;
    		
    	}
    	
    	BigDecimal value = new BigDecimal(totalRoomsOfDay).subtract(reportDataRow.getTotalRevPar()); 

    	if (value.compareTo(new BigDecimal(reportDataRow.getOooRooms())) <= 0){
    		
    		BigDecimal  bdDisplaced = reportDataRow.getTotalRevPar().subtract(new BigDecimal(totalRoomsOfDay));
    		reportDataRow.setDisplacedRooms(bdDisplaced.add(new BigDecimal(reportDataRow.getOooRooms())).intValue());    	
    	}else{
    		reportDataRow.setDisplacedRooms(0);
    	}
         
    	reportDataRow.setDisplacedRevenue( reportDataRow.gettTlADR().multiply(new BigDecimal(reportDataRow.getDisplacedRooms())).setScale(0, BigDecimal.ROUND_HALF_EVEN).intValue()); 
    	//reportDataRow.getDisplacedRooms() * reportDataRow.gettTlADR().intValue());
                                    	
    }
    
    
    public void getTransientInfoValues (UserPreferencesRenovationImpact preferencesRenovation, ReportData reportDataRow,String statDate){
    	
    	TransientSegmentData transientDataRow = TransientfindByStatDate(preferencesRenovation.getLstTransentData(), statDate);
    	    
    	if (transientDataRow != null){
    		reportDataRow.setComments(transientDataRow.getComments());
    		reportDataRow.setDow(transientDataRow.getDow());
    		int isActual =  transientDataRow.getIsActual();  		
    		reportDataRow.setaF((isActual== 1)? "True" : "False");
    		reportDataRow.setOccPctTtlAvailable(transientDataRow.getTotOccPct().setScale(1, BigDecimal.ROUND_HALF_EVEN));
    		
    		int revPar =  reportDataRow.getOccRooms() + reportDataRow.getDemandTdowns();
    		
    		int totalRoomsOfDay = FindTotalRoomsOfDay(preferencesRenovation.getLstTotalRooms(), statDate);                       		
            
    		if ( revPar > totalRoomsOfDay){
    			reportDataRow.setTotalRevPar(new BigDecimal(totalRoomsOfDay));
    		}else {
    			reportDataRow.setTotalRevPar(new BigDecimal(revPar));
    		}    		
    	}
    	
    	
    }
    
    
    public int FindTotalRoomsOfDay (List<HotelTotalRooms> lstTotalRooms, String statDate){
						
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
    
    public BigDecimal getDemandTdownsValue(UserPreferencesRenovationImpact preferencesRenovation, String statDate){
    	BigDecimal retValue = new BigDecimal(0);
    	
    	SSRSegmentData ssrSegmentData =  findByStatDate(preferencesRenovation.getLstSSRData(), statDate);
    	
        if (ssrSegmentData != null){
        	retValue = ssrSegmentData.getGroupDemandTD().multiply( new BigDecimal(preferencesRenovation.getUnrealizedTDFactor()));
        }
        
    	return retValue;
    }
    
    public SSRSegmentData  findByStatDate(List<SSRSegmentData> lstSsrData, String statDate){
		
    	SSRSegmentData ssrSegmentData = null;
		
		for(SSRSegmentData ssrData : lstSsrData){
			if (statDate.equals(ssrData.getStatDate())){
				return ssrData;
			}
		}
		
		return ssrSegmentData;
	}
    
    
    public TransientSegmentData  TransientfindByStatDate(List<TransientSegmentData> lstTransientData, String statDate){
		
    	TransientSegmentData transientSegmentData = null;
		
		for(TransientSegmentData transientData : lstTransientData){
			if (statDate.equals(transientData.getStatDate())){
				return transientData;
			}
		}
		
		return transientSegmentData;
	}
    
    public BigDecimal calculatePercentage (BigDecimal numerador, BigDecimal denominador){
    	if (denominador == null || denominador.compareTo(BigDecimal.ZERO) == 0){
    		return BigDecimal.ZERO;
    	}
    	
    	BigDecimal result =  numerador.divide(denominador,4,RoundingMode.HALF_EVEN);
    	    	
    	return result.multiply(new BigDecimal(100).setScale(2, RoundingMode.HALF_EVEN));    		    	    
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
    
    
    public ReportData getMonthlySummaryRow(UserPreferencesRenovationImpact preferencesRenovation, Calendar SummaryDateStart, Calendar  SummaryDateEnd,Calendar statDateStart,Calendar statDateEnd){
		
		ReportData monthlySummaryRow = null;
		int summaryDaysRooms;
		
		monthlySummaryRow = getSumReportDataRows(preferencesRenovation.getLstReportData(), SummaryDateStart, SummaryDateEnd);
		
		monthlySummaryRow.setSummaryRow(true);		
		monthlySummaryRow.setComments(new SimpleDateFormat("MMMM").format(SummaryDateStart.getTime()));//CR by Carl. Display only Month.
		
		summaryDaysRooms = getSummaryTotalRooms(preferencesRenovation.getLstTotalRooms(),SummaryDateStart, SummaryDateEnd, statDateStart, statDateEnd);
		monthlySummaryRow.setDow(String.valueOf(summaryDaysRooms));//CR by Carl. Hide these numbers
		monthlySummaryRow.setaF("");
		monthlySummaryRow.setVacant(summaryDaysRooms - monthlySummaryRow.getOooRooms());
		
		if (monthlySummaryRow.getVacant() == 0){
			monthlySummaryRow.setOccPctOOOAvailable(BigDecimal.ZERO);
		}else{
			monthlySummaryRow.setOccPctOOOAvailable(calculatePercentage(new BigDecimal(monthlySummaryRow.getOccRooms()), new BigDecimal(monthlySummaryRow.getVacant())).setScale(1,BigDecimal.ROUND_HALF_EVEN));			
		}
		 	
		if (summaryDaysRooms == 0){
			monthlySummaryRow.setOccPctTtlAvailable(BigDecimal.ZERO);
		}else{
			monthlySummaryRow.setOccPctTtlAvailable(calculatePercentage(new BigDecimal(monthlySummaryRow.getOccRooms()), new BigDecimal(summaryDaysRooms)).setScale(1,BigDecimal.ROUND_HALF_EVEN));
		}
		
		monthlySummaryRow.settTlADR(calculateADR(monthlySummaryRow.getOccRooms(), monthlySummaryRow.gettTlRevenue()));
						
		return monthlySummaryRow;
		
	}
    
   public ReportData getSumReportDataRows(List<ReportData> lstReportData, Calendar dateStart, Calendar dateEnd){
		
		int summaryOOORooms = 0;
		int summaryVacant = 0;
		int summaryOCCRooms = 0;
		BigDecimal summaryTtlRevenue = BigDecimal.ZERO;
		int summaryDemandTdowns = 0;
		int summaryDisplacedRooms = 0;
		int summaryDisplacedRevenue = 0;
		BigDecimal summaryTotalRevPar = BigDecimal.ZERO;								
		
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
				
				summaryOOORooms = summaryOOORooms + reportDataRow.getOooRooms();
				summaryVacant = summaryVacant + reportDataRow.getVacant();				
				summaryOCCRooms = summaryOCCRooms + reportDataRow.getOccRooms();				
				summaryTtlRevenue = summaryTtlRevenue.add(reportDataRow.gettTlRevenue());				
				summaryDemandTdowns = summaryDemandTdowns + reportDataRow.getDemandTdowns();				
				summaryDisplacedRooms = summaryDisplacedRooms + reportDataRow.getDisplacedRooms();								
				summaryDisplacedRevenue = summaryDisplacedRevenue + reportDataRow.getDisplacedRevenue();				
				summaryTotalRevPar = summaryTotalRevPar.add(reportDataRow.getTotalRevPar());													
			}
			
		}
		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		ReportData summaryRows = new ReportData();
		
		summaryRows.setOooRooms(summaryOOORooms);
		summaryRows.setVacant(summaryVacant);
		summaryRows.setOccRooms(summaryOCCRooms);
		summaryRows.settTlRevenue(summaryTtlRevenue);
		summaryRows.setDemandTdowns(summaryDemandTdowns);		
		summaryRows.setDisplacedRooms(summaryDisplacedRooms);
		summaryRows.setDisplacedRevenue(summaryDisplacedRevenue);
		summaryRows.setTotalRevPar(summaryTotalRevPar);		
		
		return summaryRows;
		
	}
   
   public ReportData updateYearlySummaryRow (ReportData reportRowMonth, ReportData reportYearlyRow){
	   
	   reportYearlyRow.setStatDate("");
	   reportYearlyRow.setaF("");
	   
	   int rooms = Integer.parseInt(reportYearlyRow.getDow()) + Integer.parseInt(reportRowMonth.getDow());
	   reportYearlyRow.setDow(String.valueOf(rooms));
	   reportYearlyRow.setOooRooms(reportYearlyRow.getOooRooms() +  reportRowMonth.getOooRooms());
	   reportYearlyRow.setVacant(reportYearlyRow.getVacant() +  reportRowMonth.getVacant());
	   reportYearlyRow.setOccRooms(reportYearlyRow.getOccRooms() + reportRowMonth.getOccRooms());
       
       if (reportYearlyRow.getVacant() ==  0){
    	   reportYearlyRow.setOccPctOOOAvailable(BigDecimal.ZERO);
       }else{
    	   reportYearlyRow.setOccPctOOOAvailable(calculatePercentage(new BigDecimal(reportYearlyRow.getOccRooms()), new BigDecimal(reportYearlyRow.getVacant())).setScale(1,BigDecimal.ROUND_HALF_EVEN));    	   
       }
    	   
       if (rooms == 0){
    	   reportYearlyRow.setOccPctTtlAvailable(BigDecimal.ZERO);
       }else{
    	   reportYearlyRow.setOccPctTtlAvailable(calculatePercentage(new BigDecimal(reportYearlyRow.getOccRooms()), new BigDecimal(rooms)));
       }
       
       reportYearlyRow.settTlRevenue((reportYearlyRow.gettTlRevenue() != null) ? reportRowMonth.gettTlRevenue().add(reportYearlyRow.gettTlRevenue()) : reportRowMonth.gettTlRevenue() );
       reportYearlyRow.settTlADR(calculateADR(reportYearlyRow.getOccRooms(), reportYearlyRow.gettTlRevenue()));       
       reportYearlyRow.setDemandTdowns(reportYearlyRow.getDemandTdowns() + reportRowMonth.getDemandTdowns());
       reportYearlyRow.setDisplacedRooms(reportYearlyRow.getDisplacedRooms() +  reportRowMonth.getDisplacedRooms());
       reportYearlyRow.setDisplacedRevenue(reportYearlyRow.getDisplacedRevenue() + reportRowMonth.getDisplacedRevenue());
       reportYearlyRow.setTotalRevPar((reportYearlyRow.getTotalRevPar() != null )? reportYearlyRow.getTotalRevPar().add(reportRowMonth.getTotalRevPar()) : reportRowMonth.getTotalRevPar());          	   
	   
	   return reportYearlyRow;
   }
   
   public int getSummaryTotalRooms (List<HotelTotalRooms> lstTotalRooms,Calendar SummaryDateStart,Calendar SummaryDateEnd,Calendar statDateStart, Calendar statDateEnd){
	    int retValue = 0;	    
	   
		Calendar dateCalc  = Calendar.getInstance();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		dateCalc.setTime(SummaryDateStart.getTime());		
				
		while (dateCalc.compareTo(SummaryDateEnd)<= 0){
			
			if (dateCalc.compareTo(statDateStart)>= 0 && dateCalc.compareTo(statDateEnd)<=0)
			
//			if (isSummaryDateIncluded(dateCalc, dateCalc, statDateStart, statDateEnd))
			{
				String statDate = dateFormat.format(dateCalc.getTime());
			
				retValue = retValue + FindTotalRoomsOfDay(lstTotalRooms, statDate);
	    	
				
			}
			
			dateCalc.add(Calendar.DATE, 1);
			
		}
				
		return retValue;	    	  
   }
      
   
   public BigDecimal calculateADR (int occ, BigDecimal rev){
		if (occ == 0)
			return BigDecimal.ZERO;
		else
			return rev.divide(new BigDecimal(occ),2,RoundingMode.HALF_EVEN);		
	}
    
   public UserPreferencesRenovationImpact updateInputRowData(UserPreferencesRenovationImpact preferencesRenovation, ReportData rowToUpdate){
	   
	   System.out.println("*********** Renovation impact - updateInputRowData - BEGIN - " + new Date() +" ***********");
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
								
		try{	
						
			conn = this.getCurrentHRR3Connection();	
			
			String statement = ("{call ftInsertUpdateRenovationImpactData (?,?,?,?,?,?)}");
			
			ps = conn.prepareStatement(statement);			
			ps.setInt(1, preferencesRenovation.getUserId());			
			ps.setInt(2, preferencesRenovation.getHotelId());
			ps.setInt(3, preferencesRenovation.getForecastSnaphotID());
			ps.setInt(4, preferencesRenovation.getSsrSnapshotID());
			ps.setString(5, rowToUpdate.getStatDate());
			ps.setInt(6, rowToUpdate.getOooRooms());									
			ps.executeQuery();
						
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
		}
					
		finally { close(rs, ps, conn);};
		
		int totalRoomsOfDay = FindTotalRoomsOfDay(preferencesRenovation.getLstTotalRooms(), rowToUpdate.getStatDate());
		
		getTransientInfoValues(preferencesRenovation, rowToUpdate, rowToUpdate.getStatDate());
		rowToUpdate.setVacant(totalRoomsOfDay - rowToUpdate.getOccRooms() -  rowToUpdate.getOooRooms());
		
		if (totalRoomsOfDay == rowToUpdate.getOooRooms()){
			rowToUpdate.setOccPctOOOAvailable(BigDecimal.ZERO);    		
		}else{
			rowToUpdate.setOccPctOOOAvailable(calculatePercentage(new BigDecimal(rowToUpdate.getOccRooms()), new BigDecimal(totalRoomsOfDay - rowToUpdate.getOooRooms())));    			
		}
		
		calculateDisplacedValues(rowToUpdate, preferencesRenovation.getSelloutThreshold(), this.getCurrentHotel().getTotalRooms(), totalRoomsOfDay);
		
		generateAppReportDataSummaryRows(preferencesRenovation, preferencesRenovation.getStatDateFrom(), preferencesRenovation.getStatDateTo());
		
		System.out.println("*********** Renovation impact - updateInputRowData - END - " + new Date() +" ***********");
	   
	   return preferencesRenovation;
   }
}
