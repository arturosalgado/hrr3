package com.hrr3.model;

import java.math.BigDecimal;
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

import com.hrr3.entity.ImportSegment;


public class ImportSegmentDAO extends RM3AbstractDAO {
	
	private String[] hspSegments = new String[10];
	
	
	public ImportSegmentDAO (){
		hspSegments[0] = "GCorp";				
		hspSegments[1] = "GSMERF";
		hspSegments[2] = "GSports";
		hspSegments[3] = "GTour";
		hspSegments[4] = "GGovt";
		hspSegments[5] = "GAssoc";
		hspSegments[6] = "GConv";
		hspSegments[7] = "GHosp";
		hspSegments[8] = "GOnline";
		hspSegments[9] = "GEntmt";
	}

	public int fillHSPSegmentsZero(String dateFrom, String dateTo, int hotelId, int userId,int customerId){
		
		System.out.println("*********** ImportSegmentsDato -> fillHSPSegmentsZero - BEGIN - *********** ");
		
		Date dDateFrom = null;
		Date dDateTo = null;
		Date dDateCalc = null;
		int importSession = 0;
				
		try {
		
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			
			dDateFrom = dateFormat.parse(dateFrom);		
			dDateTo =  dateFormat.parse(dateTo);
			dDateCalc = dDateFrom;						
			
			System.out.println("*********** INVOKE - ImportSegmentsDato -> getGroupSegmetns *********** ");
			//Get list of Group segments
			List<String> segmentsGroup =  getGroupSegmetns(customerId);
			
			if (!segmentsGroup.isEmpty()){
				
				while (dDateCalc.compareTo(dDateTo)<= 0){

					//for each date insert all group segments with 0
					for (String segmentGroup : segmentsGroup) {
						ImportSegment impSegment = new ImportSegment();
												
						impSegment.setHotelId(hotelId);
						impSegment.setUserId(userId);
						impSegment.setCustomerId(customerId);
						impSegment.setSegmentName(segmentGroup);
						impSegment.setTransDate( dateFormat.format(dDateCalc));
						impSegment.setDefOCC(new BigDecimal(0));
						impSegment.setDefADR(new BigDecimal(0));
						impSegment.setTenOCC(new BigDecimal(0));
						impSegment.setTenADR(new BigDecimal(0));
						impSegment.setTotOCC(new BigDecimal(0));
						impSegment.setTotADR(new BigDecimal(0));
						impSegment.setWSName("Group");
						impSegment.setTableType("DP");
						
						System.out.println("*********** INVOKE - ImportSegmentsDato -> insertHSPZeroData [ " +impSegment.getSegmentName() + "] *********** ");
						importSession = insertHSPZeroData(impSegment, importSession);
						
						if(importSession == 0){
							return 0;
						}
					
					}
					
					Calendar cal = Calendar.getInstance();
					cal.setTime(dDateCalc);
					cal.add(Calendar.DATE, 1); //add 1 day
					dDateCalc = cal.getTime();
				 }
			}																		
						
			System.out.println("***********  ImportSegmentsDato -> fillHSPSegmentsZero - END - *********** ");
			
			return importSession;						
		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
				
	}
	
	public int insertHSPZeroData(ImportSegment impSegment, int importSession){
		System.out.println("*********** insertHSPZeroData - BEGIN - " + new Date() +" ***********");

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try{

			conn = this.getCurrentHRR3Connection();
			
			String statement = "SET @importsessionHSP = ?";
			ps = conn.prepareStatement(statement);
			
			ps.setInt(1, importSession);
			
			ps.executeUpdate();
			
			statement = "";
			ps.close();
			
			statement = ("{call ftSSRImportRFTFile (?,?,?,?,?,?,?,?,?,?,?,@importsessionHSP)}");
															
			ps = conn.prepareStatement(statement);
			ps.setInt(1, impSegment.getHotelId());
			ps.setInt(2, impSegment.getCustomerId());
			ps.setString(3, impSegment.getTransDate());
			ps.setString(4, impSegment.getSegmentName());
			ps.setBigDecimal(5, impSegment.getDefOCC());
			ps.setBigDecimal(6, impSegment.getDefADR());
			ps.setBigDecimal(7, impSegment.getTenOCC());
			ps.setBigDecimal(8, impSegment.getTenADR());
			ps.setString(9, impSegment.getTableType());
			ps.setString(10, impSegment.getWSName());
			ps.setInt(11, impSegment.getUserId());
			//ps.setInt(12, importSession);
			
			ps.executeUpdate();
			
			statement = "";
			ps.close();
			
			statement = "SELECT @importsessionHSP as _p_out";
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
	       System.out.println(" ***********insertHSPZeroData - END - " + new Date()+ " ***********");
		}
	}
							
	
	
	public String mapHSPSegments(String hspSegment){
		
		String RM3Segment = "";
		
		switch (hspSegment) {
		case "Corporate":
			RM3Segment = hspSegments[0]; // "GCorp";
			break;
			
		case "Corporate Group":
			RM3Segment = hspSegments[0]; // "GCorp";
			break;
			
		case "":
			RM3Segment = hspSegments[0]; // "GCorp";
			break;
			
		case "Reunion":
			RM3Segment = hspSegments[1]; // "GSMERF";
			break;
			
		case "Wedding":
			RM3Segment = hspSegments[1]; // "GSMERF";
			break;
		
		case "SMERF":
			RM3Segment = hspSegments[1]; // "GSMERF";
			break;
			
		case "SMERF Govt/Military":
			RM3Segment = hspSegments[1]; // "GSMERF";
			break;
			
		case "SMERF Social":
			RM3Segment = hspSegments[1]; // "GSMERF";
			break;
			
		case "SMERF Education":
			RM3Segment = hspSegments[1]; // "GSMERF";
			break;
			
		case "SMERF Religious":
			RM3Segment = hspSegments[1]; // "GSMERF";
			break;
			
		case "SMERF Local Sports":
			RM3Segment = hspSegments[1]; // "GSMERF";
			break;
			
		case "Sports":
			RM3Segment = hspSegments[2]; // "GSports";
			break;

		case "Tour & Travel":
			RM3Segment = hspSegments[3]; // "GTour";
			break;

		case "Nat Sales Tour":
			RM3Segment = hspSegments[3]; // "GTour";
			break;

		case "Tour &amp; Travel":
			RM3Segment = hspSegments[3]; // "GTour";
			break;

		case "National Sales Tour & Travel":
			RM3Segment = hspSegments[3]; // "GTour";
			break;

		case "National Sales Tour &amp; Travel":
			RM3Segment = hspSegments[3]; // "GTour";
			break;
			
		case "National Sales":
			RM3Segment = hspSegments[3]; // "GTour";
			break;
			
		case "National Sales FIT":
			RM3Segment = hspSegments[3]; // "GTour";
			break;
			
		case "Government":
			RM3Segment =  hspSegments[4]; // "GGovt";
			break;
			
		case "Government Group":
			RM3Segment =  hspSegments[4]; // "GGovt";
			break;
			
		case "Association*" :
			RM3Segment = hspSegments[5]; // "GAssoc";
			break;
			
		case "Association" :
			RM3Segment = hspSegments[5]; // "GAssoc";
			break;
			
		case "Association Group":
			RM3Segment = hspSegments[5]; // "GAssoc";
			break;
			
		case "Convention":
			RM3Segment = hspSegments[6]; // "GConv";
			break;
				
		default:
			break;
		}
		    	
		return RM3Segment;
		 
	}
	
	public List<String> getGroupSegmetns(int customerId){
		System.out.println(" ImportSegmentsDato -> getGroupSegmetns - BEGIN - ");

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		List<String> lstSegmentsGroup = null;
		
		try{
			conn = this.getCurrentHRR3Connection();
			
			String statement = "select name  from RM3Segments where type =  'DP' and isTotal = 0 and customer_id = ?";

			ps = conn.prepareStatement(statement);
			ps.setInt(1,customerId );
			
			rs = ps.executeQuery();
			
			if(rs != null){
				if(rs.next()){
					lstSegmentsGroup =  new ArrayList<String>();
					System.out.println("**************** Getting Group Segments ******************");
					do{	
						String segmentName = rs.getString("name");
						lstSegmentsGroup.add(segmentName);						
					}while (rs.next());
				}
			}
			
			System.out.println("**************** Returning SegmentsNameGroupList-"+ lstSegmentsGroup.size() +" ******************");
			return lstSegmentsGroup;
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	
		finally { close(rs, ps);
	       System.out.println("ImportSegmentsDato -> getGroupSegmetns - END - ");
		}				
				
	}
	
	public void deleteDataRM3Import (int hotelId){		
				
		System.out.println("****************  deleteDataRM3Import - BEGIN - ****************");

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try{
			conn = this.getCurrentHRR3Connection();
			
			String statement = "DELETE FROM RM3Import WHERE hotel_id=? and type = 'DP'";

			ps = conn.prepareStatement(statement);
			ps.setInt(1,hotelId);
			
			ps.executeUpdate();
			
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}
	
		finally { close(rs, ps);
	       System.out.println("****************  deleteDataRM3Import -> getGroupSegmetns - END - **************** ");
		}				
				
	}

}
