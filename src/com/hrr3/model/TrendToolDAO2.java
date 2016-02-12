package com.hrr3.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.hrr3.entity.Hotel;
import com.hrr3.entity.trendTool.TrendTool;
import com.hrr3.entity.trendTool.TrendToolLists;

public class TrendToolDAO2 extends RM3AbstractDAO {

	public TrendToolDAO2(Hotel currentHotel) {
		super(currentHotel);
		// TODO Auto-generated constructor stub
	}
	
	public List<TrendTool> getTrend(int customerId, String statDate, String endDate, String calling){
		
		
			
		long startTime = System.currentTimeMillis();
		
		
		System.out.println("*********** Trend Tool - getTrend - BEGIN - " + new Date() +" ***********");
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		List<TrendTool> lstTrendTool = new ArrayList<TrendTool>();
		
		try{	
						
			conn = this.getCurrentHRR3Connection();	
			
			String statement = ("{call ftGetTrend (?,?,?,?)}");
			
			ps = conn.prepareStatement(statement);			
			ps.setString(1, statDate);
			ps.setString(2, endDate);
			ps.setInt(3, this.getCurrentHotel().getHotelId());
			ps.setInt(4, customerId);
			
			System.out.println("TT Parameters for >>>> call from  "+calling+" : ");
			
			
			System.out.println("fff1:"+statDate);
			System.out.println("fff2:"+endDate);
			System.out.println("fff3:"+this.getCurrentHotel().getHotelId());
			System.out.println("fff4:"+customerId);
			
			
			rs = ps.executeQuery();
			
			while(rs != null && rs.next()){	
				
				TrendTool trendData = new TrendTool();
				
				trendData.setSegmentName(rs.getString("name"));
				trendData.setSegmentId(rs.getInt("segment_id"));
				
				trendData.setSunADR(rs.getBigDecimal("sunadr"));
				trendData.setSunOCC(rs.getInt("sunocc"));
				
				trendData.setMonADR(rs.getBigDecimal("monadr"));
				trendData.setMonOCC(rs.getInt("monocc"));
				trendData.setTueADR(rs.getBigDecimal("tueadr"));
				trendData.setTueOCC(rs.getInt("tueocc"));
				trendData.setWenADR(rs.getBigDecimal("wedadr"));
				trendData.setWenOCC(rs.getInt("wedocc"));
				trendData.setThuADR(rs.getBigDecimal("thuadr"));
				trendData.setThuOCC(rs.getInt("thuocc"));
				trendData.setFriADR(rs.getBigDecimal("friadr"));
				trendData.setFriOCC(rs.getInt("friocc"));
				trendData.setSatADR(rs.getBigDecimal("satadr"));
				trendData.setSatOCC(rs.getInt("satocc"));
				trendData.setWeekADR(rs.getBigDecimal("weekadr"));
				trendData.setWeekOCC(rs.getInt("weekocc"));
				trendData.setWeekendADR(rs.getBigDecimal("weekendadr"));
				trendData.setWeekendOCC(rs.getInt("weekendocc"));
				trendData.setTotalADR(rs.getBigDecimal("totaladr"));
				trendData.setTotalOCC(rs.getInt("totalocc"));
				
				lstTrendTool.add(trendData);								
					
			}
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
		}
	
		finally { close(rs, ps, conn);};	       	
			
		long estimatedTime = System.currentTimeMillis() - startTime;
		System.out.println("TT >>>> call from  "+calling+" : "+estimatedTime);
	
		
		
		System.out.println("*********** Trend Tool - getTrend - END - " + new Date() +" ***********");
		
		return lstTrendTool;
	}
	
	
	
	
	
	
	public List<TrendTool> populateTrendOldInformation(String statDate, String endDate, int customerId){
		
		
		//long startTime = System.nanoTime();    
		// ... the code being measured ...    
		long startTime = System.currentTimeMillis();

		
		System.out.println("*********** Trend Tool - populateTrendOldInformation - BEGIN - " + new Date() +" ***********");
		
		List<TrendTool>  lstTrendOld = getTrend(customerId, statDate, endDate,"populateTrendOldInformation");
		
		
		for (TrendTool trendOldRow : lstTrendOld){
			trendOldRow.setWeekOCC(trendOldRow.getSunOCC() + trendOldRow.getMonOCC() +  trendOldRow.getTueOCC()+ trendOldRow.getWenOCC() + trendOldRow.getThuOCC());
			trendOldRow.setWeekendOCC(trendOldRow.getFriOCC() +  trendOldRow.getSatOCC());
			trendOldRow.setTotalOCC(trendOldRow.getWeekOCC() + trendOldRow.getWeekendOCC());
			
			trendOldRow.setSunADR(trendOldRow.getSunADR().setScale(2, BigDecimal.ROUND_CEILING));
			trendOldRow.setMonADR(trendOldRow.getMonADR().setScale(2, BigDecimal.ROUND_CEILING));
			trendOldRow.setTueADR(trendOldRow.getTueADR().setScale(2, BigDecimal.ROUND_CEILING));
			trendOldRow.setWenADR(trendOldRow.getWenADR().setScale(2, BigDecimal.ROUND_CEILING));
			trendOldRow.setThuADR(trendOldRow.getThuADR().setScale(2, BigDecimal.ROUND_CEILING));
			trendOldRow.setFriADR(trendOldRow.getFriADR().setScale(2, BigDecimal.ROUND_CEILING));
			trendOldRow.setSatADR(trendOldRow.getSatADR().setScale(2, BigDecimal.ROUND_CEILING));
			trendOldRow.setWeekADR(trendOldRow.getWeekADR().setScale(2, BigDecimal.ROUND_CEILING));
			trendOldRow.setWeekendADR(trendOldRow.getWeekendADR().setScale(2, BigDecimal.ROUND_CEILING));
			trendOldRow.setTotalADR(trendOldRow.getTotalADR().setScale(2, BigDecimal.ROUND_CEILING));
		}
		
		
		
		long estimatedTime = System.currentTimeMillis() - startTime;
		
		System.out.println("*********** Trend Tool - populateTrendOldInformation - END - " + new Date() +" ***********");
		System.out.println("TIME : populateTrendOldInformation : "+estimatedTime);
		//System.exit(0);
		
		
		return lstTrendOld;		
	}
	
	
	
	
	public List<TrendTool> populateTrendNewInformation(String statDate, String endDate, int customerId){
		
		System.out.println("*********** Trend Tool - populateTrendNewInformation - BEGIN - " + new Date() +" ***********");
		
		List<TrendTool>  lstTrendNew = getTrend(customerId, statDate, endDate,"populateTrendNewInformation");
		
		/*
		for (TrendTool trendNewRow : lstTrendNew){
			trendNewRow.setWeekOCC(trendNewRow.getSunOCC() + trendNewRow.getMonOCC() +  trendNewRow.getTueOCC()+ trendNewRow.getWenOCC() + trendNewRow.getThuOCC());
			trendNewRow.setWeekendOCC(trendNewRow.getFriOCC() +  trendNewRow.getSatOCC());
			trendNewRow.setTotalOCC(trendNewRow.getWeekOCC() + trendNewRow.getWeekendOCC());
			
			trendNewRow.setSunADR(trendNewRow.getSunADR().setScale(2, BigDecimal.ROUND_CEILING));
			trendNewRow.setMonADR(trendNewRow.getMonADR().setScale(2, BigDecimal.ROUND_CEILING));
			trendNewRow.setTueADR(trendNewRow.getTueADR().setScale(2, BigDecimal.ROUND_CEILING));
			trendNewRow.setWenADR(trendNewRow.getWenADR().setScale(2, BigDecimal.ROUND_CEILING));
			trendNewRow.setThuADR(trendNewRow.getThuADR().setScale(2, BigDecimal.ROUND_CEILING));
			trendNewRow.setFriADR(trendNewRow.getFriADR().setScale(2, BigDecimal.ROUND_CEILING));
			trendNewRow.setSatADR(trendNewRow.getSatADR().setScale(2, BigDecimal.ROUND_CEILING));
			trendNewRow.setWeekADR(trendNewRow.getWeekADR().setScale(2, BigDecimal.ROUND_CEILING));
			trendNewRow.setWeekendADR(trendNewRow.getWeekendADR().setScale(2, BigDecimal.ROUND_CEILING));
			trendNewRow.setTotalADR(trendNewRow.getTotalADR().setScale(2, BigDecimal.ROUND_CEILING));
		}
		*/
		
		
		System.out.println("*********** Trend Tool - populateTrendNewInformation - END - " + new Date() +" ***********");
		
		return lstTrendNew;		
	}
	
	
	public List<TrendTool> populateActualChangeInformation(List<TrendTool> lstTrendOld, List<TrendTool> lstTrendNew){
		
		System.out.println("*********** Trend Tool - populateActualChangeInformation - BEGIN - " + new Date() +" ***********");
		
		List<TrendTool>  lstActualChange = new ArrayList<TrendTool>();		
		
		if (lstTrendNew.size() == lstTrendOld.size()){
		
			for (int i=0; i < lstTrendOld.size(); i++ ){
				TrendTool actualChangeRow = new TrendTool();
				
				actualChangeRow.setSegmentName(lstTrendOld.get(i).getSegmentName());
				actualChangeRow.setSegmentId(lstTrendOld.get(i).getSegmentId());

				actualChangeRow.setSunOCC(0);
				if (lstTrendOld.get(i).getSunOCC() != 0){						
					BigDecimal valueSun = new BigDecimal((lstTrendNew.get(i).getSunOCC() - lstTrendOld.get(i).getSunOCC()) / lstTrendOld.get(i).getSunOCC());					
					actualChangeRow.setSunOCC(valueSun.setScale(2, BigDecimal.ROUND_CEILING).multiply(new BigDecimal(100)).intValue());
				}
				
				actualChangeRow.setMonOCC(0);
				if(lstTrendOld.get(i).getMonOCC() != 0){					
					BigDecimal valueMon = new BigDecimal((lstTrendNew.get(i).getMonOCC() - lstTrendOld.get(i).getMonOCC()) / lstTrendOld.get(i).getMonOCC());					
					actualChangeRow.setMonOCC(valueMon.setScale(2, BigDecimal.ROUND_CEILING).multiply(new BigDecimal(100)).intValue());
				}
				
				actualChangeRow.setTueOCC(0);
				if(lstTrendOld.get(i).getTueOCC() != 0){
					BigDecimal valueTue = new BigDecimal((lstTrendNew.get(i).getTueOCC() - lstTrendOld.get(i).getTueOCC())/ lstTrendOld.get(i).getTueOCC());
					actualChangeRow.setTueOCC(valueTue.setScale(2, BigDecimal.ROUND_CEILING).multiply(new BigDecimal(100)).intValue());
				}
				
				actualChangeRow.setWenOCC(0);				
				if(lstTrendOld.get(i).getWenOCC() != 0){
					BigDecimal valueWen = new BigDecimal((lstTrendNew.get(i).getWenOCC() - lstTrendOld.get(i).getWenOCC()) /  lstTrendOld.get(i).getWenOCC());
					actualChangeRow.setWenOCC(valueWen.setScale(2, BigDecimal.ROUND_CEILING).multiply(new BigDecimal(100)).intValue());
				}
				
				actualChangeRow.setThuOCC(0);
				if(lstTrendOld.get(i).getThuOCC() != 0){
					BigDecimal valueThu = new BigDecimal((lstTrendNew.get(i).getThuOCC() - lstTrendOld.get(i).getThuOCC()) / lstTrendOld.get(i).getThuOCC());
					actualChangeRow.setThuOCC(valueThu.setScale(2, BigDecimal.ROUND_CEILING).multiply(new BigDecimal(100)).intValue());
					
				}
				
				actualChangeRow.setFriOCC(0);
				if(lstTrendOld.get(i).getFriOCC() != 0){
					BigDecimal valueFri = new BigDecimal((lstTrendNew.get(i).getFriOCC() - lstTrendOld.get(i).getFriOCC()) / lstTrendOld.get(i).getFriOCC());
					actualChangeRow.setFriOCC(valueFri.setScale(2, BigDecimal.ROUND_CEILING).multiply(new BigDecimal(100)).intValue());
				}
				
				actualChangeRow.setSatOCC(0);
				if(lstTrendOld.get(i).getSatOCC() != 0){
					BigDecimal valueSat = new BigDecimal((lstTrendNew.get(i).getSatOCC() - lstTrendOld.get(i).getSatOCC()) / lstTrendOld.get(i).getSatOCC());
					actualChangeRow.setSatOCC(valueSat.setScale(2, BigDecimal.ROUND_CEILING).multiply(new BigDecimal(100)).intValue());
				}
				
				actualChangeRow.setSatOCC(0);
				if(lstTrendOld.get(i).getSatOCC() != 0){
					BigDecimal valueSat = new BigDecimal((lstTrendNew.get(i).getSatOCC() - lstTrendOld.get(i).getSatOCC()) / lstTrendOld.get(i).getSatOCC());
					actualChangeRow.setSatOCC(valueSat.setScale(2, BigDecimal.ROUND_CEILING).multiply(new BigDecimal(100)).intValue());
				}
				
				actualChangeRow.setWeekOCC(0);
				if(lstTrendOld.get(i).getWeekOCC() != 0){
					BigDecimal valueWeek = new BigDecimal((lstTrendNew.get(i).getWeekOCC() - lstTrendOld.get(i).getWeekOCC()) / lstTrendOld.get(i).getWeekOCC());
					actualChangeRow.setWeekOCC(valueWeek.setScale(2, BigDecimal.ROUND_CEILING).multiply(new BigDecimal(100)).intValue());
				}
				
				actualChangeRow.setWeekendOCC(0);
				if(lstTrendOld.get(i).getWeekendOCC() != 0){
					BigDecimal valueWeekend = new BigDecimal((lstTrendNew.get(i).getWeekendOCC() - lstTrendOld.get(i).getWeekendOCC()) / lstTrendOld.get(i).getWeekendOCC());
					actualChangeRow.setWeekendOCC(valueWeekend.setScale(2, BigDecimal.ROUND_CEILING).multiply(new BigDecimal(100)).intValue());
				}
				
				actualChangeRow.setTotalOCC(0);
				if(lstTrendOld.get(i).getTotalOCC() != 0){
					BigDecimal valueTotal = new BigDecimal((lstTrendNew.get(i).getTotalOCC() - lstTrendOld.get(i).getTotalOCC()) / lstTrendOld.get(i).getTotalOCC());
					actualChangeRow.setTotalOCC(valueTotal.setScale(2, BigDecimal.ROUND_CEILING).multiply(new BigDecimal(100)).intValue());
				}
				
				actualChangeRow.setSunADR(BigDecimal.ZERO);
				if(lstTrendOld.get(i).getSunADR().compareTo(BigDecimal.ZERO) != 0){
					BigDecimal adrSun = lstTrendNew.get(i).getSunADR().subtract(lstTrendOld.get(i).getSunADR()).divide(lstTrendOld.get(i).getSunADR(),2,RoundingMode.HALF_EVEN);
					actualChangeRow.setSunADR(adrSun.setScale(2, BigDecimal.ROUND_CEILING).multiply(new BigDecimal(100)));
				}
				
				actualChangeRow.setMonADR(BigDecimal.ZERO);
				if(lstTrendOld.get(i).getMonADR().compareTo(BigDecimal.ZERO) != 0){
					BigDecimal adrMon = lstTrendNew.get(i).getMonADR().subtract(lstTrendOld.get(i).getMonADR()).divide(lstTrendOld.get(i).getMonADR(),2,RoundingMode.HALF_EVEN);
					actualChangeRow.setMonADR(adrMon.setScale(2, BigDecimal.ROUND_CEILING).multiply(new BigDecimal(100)));
				}
				
				actualChangeRow.setTueADR(BigDecimal.ZERO);
				if(lstTrendOld.get(i).getTueADR().compareTo(BigDecimal.ZERO) != 0){
					BigDecimal adrTue = lstTrendNew.get(i).getTueADR().subtract(lstTrendOld.get(i).getTueADR()).divide(lstTrendOld.get(i).getTueADR(),2,RoundingMode.HALF_EVEN);
					actualChangeRow.setTueADR(adrTue.setScale(2, BigDecimal.ROUND_CEILING).multiply(new BigDecimal(100)));
				}
				
				actualChangeRow.setWenADR(BigDecimal.ZERO);
				if(lstTrendOld.get(i).getWenADR().compareTo(BigDecimal.ZERO) != 0){
					BigDecimal adrWen = lstTrendNew.get(i).getWenADR().subtract(lstTrendOld.get(i).getWenADR()).divide(lstTrendOld.get(i).getWenADR(),2,RoundingMode.HALF_EVEN);
					actualChangeRow.setWenADR(adrWen.setScale(2, BigDecimal.ROUND_CEILING).multiply(new BigDecimal(100)));
				}
				
				actualChangeRow.setThuADR(BigDecimal.ZERO);
				if(lstTrendOld.get(i).getThuADR().compareTo(BigDecimal.ZERO) != 0){
					BigDecimal adrThu = lstTrendNew.get(i).getThuADR().subtract(lstTrendOld.get(i).getThuADR()).divide(lstTrendOld.get(i).getThuADR(),2,RoundingMode.HALF_EVEN);
					actualChangeRow.setThuADR(adrThu.setScale(2, BigDecimal.ROUND_CEILING).multiply(new BigDecimal(100)));
				}
				
				actualChangeRow.setFriADR(BigDecimal.ZERO);
				if(lstTrendOld.get(i).getFriADR().compareTo(BigDecimal.ZERO) != 0){
					BigDecimal adrFri = lstTrendNew.get(i).getFriADR().subtract(lstTrendOld.get(i).getFriADR()).divide(lstTrendOld.get(i).getFriADR(),2,RoundingMode.HALF_EVEN);
					actualChangeRow.setFriADR(adrFri.setScale(2, BigDecimal.ROUND_CEILING).multiply(new BigDecimal(100)));
				}
				
				actualChangeRow.setSatADR(BigDecimal.ZERO);
				if(lstTrendOld.get(i).getSatADR().compareTo(BigDecimal.ZERO) != 0){
					BigDecimal adrSat = lstTrendNew.get(i).getSatADR().subtract(lstTrendOld.get(i).getSatADR()).divide(lstTrendOld.get(i).getSatADR(),2,RoundingMode.HALF_EVEN);
					actualChangeRow.setSatADR(adrSat.setScale(2, BigDecimal.ROUND_CEILING).multiply(new BigDecimal(100)));
				}
				
				actualChangeRow.setWeekADR(BigDecimal.ZERO);
				if(lstTrendOld.get(i).getWeekADR().compareTo(BigDecimal.ZERO) != 0){
					BigDecimal adrWeek = lstTrendNew.get(i).getWeekADR().subtract(lstTrendOld.get(i).getWeekADR()).divide(lstTrendOld.get(i).getWeekADR(),2,RoundingMode.HALF_EVEN);
					actualChangeRow.setWeekADR(adrWeek.setScale(2, BigDecimal.ROUND_CEILING).multiply(new BigDecimal(100)));
				}
				
				actualChangeRow.setWeekendADR(BigDecimal.ZERO);
				if(lstTrendOld.get(i).getWeekendADR().compareTo(BigDecimal.ZERO) != 0){
					BigDecimal adrWeekend = lstTrendNew.get(i).getWeekendADR().subtract(lstTrendOld.get(i).getWeekendADR()).divide(lstTrendOld.get(i).getWeekendADR(),2,RoundingMode.HALF_EVEN);
					actualChangeRow.setWeekendADR(adrWeekend.setScale(2, BigDecimal.ROUND_CEILING).multiply(new BigDecimal(100)));
				}
				
				actualChangeRow.setTotalADR(BigDecimal.ZERO);
				if(lstTrendOld.get(i).getTotalADR().compareTo(BigDecimal.ZERO) != 0){
					BigDecimal adrTotal = lstTrendNew.get(i).getTotalADR().subtract(lstTrendOld.get(i).getTotalADR()).divide(lstTrendOld.get(i).getTotalADR(),2,RoundingMode.HALF_EVEN);
					actualChangeRow.setTotalADR(adrTotal.setScale(2, BigDecimal.ROUND_CEILING).multiply(new BigDecimal(100)));
				}
				
				lstActualChange.add(actualChangeRow);

			}
			
		}
		
		System.out.println("*********** Trend Tool - populateActualChangeInformation - END - " + new Date() +" ***********");
		
		return lstActualChange;		
	}
	
	
	public List<TrendTool> populateForecastBaselineInformation(String statDate, String endDate, int customerId){
		
		System.out.println("*********** Trend Tool - populateForecastBaselineInformation - BEGIN - " + new Date() +" ***********");
		
		List<TrendTool>  lstForecastBaseline = getTrend(customerId, statDate, endDate,"populateForecastBaselineInformation");
		/*
		
		for (TrendTool forecastBaselineNewRow : lstForecastBaseline){
			forecastBaselineNewRow.setWeekOCC(forecastBaselineNewRow.getSunOCC() + forecastBaselineNewRow.getMonOCC() +  forecastBaselineNewRow.getTueOCC()+ forecastBaselineNewRow.getWenOCC() + forecastBaselineNewRow.getThuOCC());
			forecastBaselineNewRow.setWeekendOCC(forecastBaselineNewRow.getFriOCC() +  forecastBaselineNewRow.getSatOCC());
			forecastBaselineNewRow.setTotalOCC(forecastBaselineNewRow.getWeekOCC() + forecastBaselineNewRow.getWeekendOCC());
			
			forecastBaselineNewRow.setSunADR(forecastBaselineNewRow.getSunADR().setScale(2, BigDecimal.ROUND_CEILING));
			forecastBaselineNewRow.setMonADR(forecastBaselineNewRow.getMonADR().setScale(2, BigDecimal.ROUND_CEILING));
			forecastBaselineNewRow.setTueADR(forecastBaselineNewRow.getTueADR().setScale(2, BigDecimal.ROUND_CEILING));
			forecastBaselineNewRow.setWenADR(forecastBaselineNewRow.getWenADR().setScale(2, BigDecimal.ROUND_CEILING));
			forecastBaselineNewRow.setThuADR(forecastBaselineNewRow.getThuADR().setScale(2, BigDecimal.ROUND_CEILING));
			forecastBaselineNewRow.setFriADR(forecastBaselineNewRow.getFriADR().setScale(2, BigDecimal.ROUND_CEILING));
			forecastBaselineNewRow.setSatADR(forecastBaselineNewRow.getSatADR().setScale(2, BigDecimal.ROUND_CEILING));
			
			forecastBaselineNewRow.setWeekADR(BigDecimal.ZERO);	
			BigDecimal adrSun = forecastBaselineNewRow.getSunADR().multiply(new BigDecimal(forecastBaselineNewRow.getSunOCC()));
			BigDecimal addrMon = forecastBaselineNewRow.getMonADR().multiply(new BigDecimal(forecastBaselineNewRow.getMonOCC()));
			BigDecimal addrTue =  forecastBaselineNewRow.getTueADR().multiply(new BigDecimal(forecastBaselineNewRow.getTueOCC()));
			BigDecimal addrWen = forecastBaselineNewRow.getWenADR().multiply(new BigDecimal(forecastBaselineNewRow.getWenOCC()));
			BigDecimal addrThu =  forecastBaselineNewRow.getThuADR().multiply(new BigDecimal(forecastBaselineNewRow.getThuOCC()));
			
			BigDecimal adrWeek = adrSun.add(addrMon).add(addrTue).add(addrWen).add(addrThu);
			if (forecastBaselineNewRow.getWeekOCC() > 0){
				forecastBaselineNewRow.setWeekADR(adrWeek.divide(new BigDecimal(forecastBaselineNewRow.getWeekOCC()),2,BigDecimal.ROUND_CEILING));
			}else{
				forecastBaselineNewRow.setWeekADR(BigDecimal.ZERO);
			}
			
			forecastBaselineNewRow.setWeekendADR(BigDecimal.ZERO);
			BigDecimal adrFri =  forecastBaselineNewRow.getFriADR().multiply(new BigDecimal(forecastBaselineNewRow.getFriOCC()));
			BigDecimal adrSat = forecastBaselineNewRow.getSatADR().multiply(new BigDecimal(forecastBaselineNewRow.getSatOCC()));
			
			BigDecimal adrWeekend = adrFri.add(adrSat);
			if (forecastBaselineNewRow.getWeekendOCC() > 0){
				forecastBaselineNewRow.setWeekendADR(adrWeekend.divide(new BigDecimal(forecastBaselineNewRow.getWeekendOCC()),2,BigDecimal.ROUND_CEILING));
			}else{
				forecastBaselineNewRow.setWeekendADR(BigDecimal.ZERO);
			}
			
			forecastBaselineNewRow.setTotalADR(BigDecimal.ZERO);
			if (forecastBaselineNewRow.getTotalOCC() > 0){
				
				BigDecimal adrWeekT = forecastBaselineNewRow.getWeekADR().multiply(new BigDecimal(forecastBaselineNewRow.getWeekOCC()));
				BigDecimal adrWeekendT = forecastBaselineNewRow.getWeekendADR().multiply(new BigDecimal(forecastBaselineNewRow.getWeekendOCC()));
				
				BigDecimal adrTotal = adrWeekT.add(adrWeekendT);
				
				forecastBaselineNewRow.setTotalADR(adrTotal.divide(new BigDecimal(forecastBaselineNewRow.getTotalOCC()),2,BigDecimal.ROUND_CEILING));
				
				//modelWeekRow.setTotalADR(    modelWeekRow.getWeekADR().add(modelWeekRow.getWeekendADR().multiply(new BigDecimal(modelWeekRow.getWeekendOCC())));
				//,2, BigDecimal.ROUND_CEILING));
			}else{
				forecastBaselineNewRow.setTotalADR(BigDecimal.ZERO);
			}
			
			//TO DO: Recalculate the weekday, weekend and total
			// Formula for weekday :  ((sunOCC * sunADR) + ....(friOCC * friADR)) /  weekOCC (this value already exists).
			
			/*if(forecastBaselineNewRow.getWeekADR().compareTo(BigDecimal.ZERO) > 0)
				forecastBaselineNewRow.setWeekADR(forecastBaselineNewRow.getWeekADR().setScale(2, BigDecimal.ROUND_CEILING));
			else
				forecastBaselineNewRow.setWeekADR(BigDecimal.ZERO);
			
			
			forecastBaselineNewRow.setWeekendADR(forecastBaselineNewRow.getWeekendADR().setScale(2, BigDecimal.ROUND_CEILING));
			forecastBaselineNewRow.setTotalADR(forecastBaselineNewRow.getTotalADR().setScale(2, BigDecimal.ROUND_CEILING));
			
		}
				
		System.out.println("*********** Trend Tool - populateForecastBaselineInformation - END - " + new Date() +" ***********");
		*/
		return lstForecastBaseline;		
	}
	
	
	public List<TrendTool> populateModelWeekInformation(List<TrendTool> lstForecastBase, List<TrendTool> lstForecastTrend){
		
		System.out.println("*********** Trend Tool - populateModelWeekInformation - BEGIN - " + new Date() +" ***********");
		
		List<TrendTool> lstModelWeek = new ArrayList<TrendTool>();
		
		if (lstForecastBase.size() == lstForecastTrend.size()){
			
			for (int i=0; i < lstForecastBase.size(); i++ ){
				TrendTool modelWeekRow = new TrendTool();
				
				modelWeekRow.setSegmentName(lstForecastBase.get(i).getSegmentName());
				modelWeekRow.setSegmentId(lstForecastBase.get(i).getSegmentId());
				
				modelWeekRow.setSunOCC(0);
				if(lstForecastBase.get(i).getSunOCC() > 0 &&  lstForecastTrend.get(i).getSunOCC() != null){
					//Integer iSunOccFB = lstForecastBase.get(i).getSunOCC();
					//Integer iSunOccFT = lstForecastTrend.get(i).getSunOCC();
					float occPorc =   lstForecastBase.get(i).getSunOCC() * (lstForecastTrend.get(i).getSunOCC() + 100);  
					float valueFSun = occPorc / 100;   
					BigDecimal valueSun = new BigDecimal(valueFSun); 
					modelWeekRow.setSunOCC(valueSun.setScale(0, BigDecimal.ROUND_CEILING).intValue());
				}
				
				modelWeekRow.setMonOCC(0);
				if(lstForecastBase.get(i).getMonOCC() > 0 &&  lstForecastTrend.get(i).getMonOCC() != null){
					float occPorcMon =   lstForecastBase.get(i).getMonOCC() * (lstForecastTrend.get(i).getMonOCC() + 100);  
					float valueFMon = occPorcMon / 100;  
					BigDecimal valueMon = new BigDecimal(valueFMon);
					modelWeekRow.setMonOCC(valueMon.setScale(0, BigDecimal.ROUND_CEILING).intValue());
				}
				
				modelWeekRow.setTueOCC(0);
				if(lstForecastBase.get(i).getTueOCC() > 0 &&  lstForecastTrend.get(i).getTueOCC() != null){
					float occPorcTue =   lstForecastBase.get(i).getTueOCC() * (lstForecastTrend.get(i).getTueOCC() + 100);  
					float valueFTue = occPorcTue / 100;  
					BigDecimal valueTue = new BigDecimal(valueFTue);
					modelWeekRow.setTueOCC(valueTue.setScale(0, BigDecimal.ROUND_CEILING).intValue());
				}
				
				
				
				modelWeekRow.setWenOCC(0);
				if(lstForecastBase.get(i).getWenOCC() > 0 &&  lstForecastTrend.get(i).getWenOCC() != null){
					float occPorcWen =   lstForecastBase.get(i).getWenOCC() * (lstForecastTrend.get(i).getWenOCC() + 100);  
					float valueFWen = occPorcWen / 100;  
					BigDecimal valueWen = new BigDecimal(valueFWen);
					modelWeekRow.setWenOCC(valueWen.setScale(0, BigDecimal.ROUND_CEILING).intValue());
				}
				
				modelWeekRow.setThuOCC(0);
				if(lstForecastBase.get(i).getThuOCC() > 0 &&  lstForecastTrend.get(i).getThuOCC() != null){
					float occPorcThu =   lstForecastBase.get(i).getThuOCC() * (lstForecastTrend.get(i).getThuOCC() + 100);  
					float valueFThu = occPorcThu / 100;  
					BigDecimal valueThu = new BigDecimal(valueFThu);
					modelWeekRow.setThuOCC(valueThu.setScale(0, BigDecimal.ROUND_CEILING).intValue());
				}
				
				modelWeekRow.setFriOCC(0);
				if(lstForecastBase.get(i).getFriOCC() > 0 &&  lstForecastTrend.get(i).getFriOCC() != null){
					float occPorcFri =   lstForecastBase.get(i).getFriOCC() * (lstForecastTrend.get(i).getFriOCC() + 100);  
					float valueFFri = occPorcFri / 100;  
					BigDecimal valueFri = new BigDecimal(valueFFri);
					modelWeekRow.setFriOCC(valueFri.setScale(0, BigDecimal.ROUND_CEILING).intValue());
				}
				
				modelWeekRow.setSatOCC(0);
				if(lstForecastBase.get(i).getSatOCC() > 0 &&  lstForecastTrend.get(i).getSatOCC() != null){
					float occPorcSat =   lstForecastBase.get(i).getSatOCC() * (lstForecastTrend.get(i).getSatOCC() + 100);  
					float valueFSat = occPorcSat / 100;  
					BigDecimal valueSat = new BigDecimal(valueFSat);
					modelWeekRow.setSatOCC(valueSat.setScale(0, BigDecimal.ROUND_CEILING).intValue());
				}
				
				modelWeekRow.setWeekOCC(modelWeekRow.getSunOCC() + modelWeekRow.getMonOCC() + modelWeekRow.getTueOCC() + modelWeekRow.getWenOCC() +  modelWeekRow.getThuOCC());
				modelWeekRow.setWeekendOCC(modelWeekRow.getFriOCC() +  modelWeekRow.getSatOCC());
				modelWeekRow.setTotalOCC(modelWeekRow.getWeekOCC() +  modelWeekRow.getWeekendOCC());
				
				
				modelWeekRow.setSunADR(BigDecimal.ZERO);				
				if(lstForecastTrend.get(i).getSunADR() != null){
					BigDecimal adrSunBigDecimal = lstForecastTrend.get(i).getSunADR().divide(new BigDecimal(100)).add(new BigDecimal(1));
					modelWeekRow.setSunADR(lstForecastBase.get(i).getSunADR().multiply(adrSunBigDecimal).setScale(2, BigDecimal.ROUND_CEILING));
				}
				
				
				modelWeekRow.setMonADR(BigDecimal.ZERO);				
				if(lstForecastTrend.get(i).getMonADR() != null){
					BigDecimal adrMonBigDecimal = lstForecastTrend.get(i).getMonADR().divide(new BigDecimal(100)).add(new BigDecimal(1));
					modelWeekRow.setMonADR(lstForecastBase.get(i).getMonADR().multiply(adrMonBigDecimal).setScale(2, BigDecimal.ROUND_CEILING));
				}
				
				modelWeekRow.setTueADR(BigDecimal.ZERO);				
				if(lstForecastTrend.get(i).getTueADR() != null){
					BigDecimal adrTueBigDecimal = lstForecastTrend.get(i).getTueADR().divide(new BigDecimal(100)).add(new BigDecimal(1));
					modelWeekRow.setTueADR(lstForecastBase.get(i).getTueADR().multiply(adrTueBigDecimal).setScale(2, BigDecimal.ROUND_CEILING));
				}
				
				modelWeekRow.setWenADR(BigDecimal.ZERO);				
				if(lstForecastTrend.get(i).getWenADR() != null){
					BigDecimal adrWenBigDecimal = lstForecastTrend.get(i).getWenADR().divide(new BigDecimal(100)).add(new BigDecimal(1));
					modelWeekRow.setWenADR(lstForecastBase.get(i).getWenADR().multiply(adrWenBigDecimal).setScale(2, BigDecimal.ROUND_CEILING));
				}
				
				modelWeekRow.setThuADR(BigDecimal.ZERO);				
				if(lstForecastTrend.get(i).getThuADR() != null){
					BigDecimal adrThuBigDecimal = lstForecastTrend.get(i).getThuADR().divide(new BigDecimal(100)).add(new BigDecimal(1));
					modelWeekRow.setThuADR(lstForecastBase.get(i).getThuADR().multiply(adrThuBigDecimal).setScale(2, BigDecimal.ROUND_CEILING));
				}
				
				modelWeekRow.setFriADR(BigDecimal.ZERO);				
				if(lstForecastTrend.get(i).getFriADR() != null){
					BigDecimal adrFriBigDecimal = lstForecastTrend.get(i).getFriADR().divide(new BigDecimal(100)).add(new BigDecimal(1));
					modelWeekRow.setFriADR(lstForecastBase.get(i).getFriADR().multiply(adrFriBigDecimal).setScale(2, BigDecimal.ROUND_CEILING));
				}
				
				modelWeekRow.setSatADR(BigDecimal.ZERO);				
				if(lstForecastTrend.get(i).getSatADR() != null){
					BigDecimal adrSatBigDecimal = lstForecastTrend.get(i).getSatADR().divide(new BigDecimal(100)).add(new BigDecimal(1));
					modelWeekRow.setSatADR(lstForecastBase.get(i).getSatADR().multiply(adrSatBigDecimal).setScale(2, BigDecimal.ROUND_CEILING));
				}
				
				modelWeekRow.setWeekADR(BigDecimal.ZERO);	
				BigDecimal adrSun = modelWeekRow.getSunADR().multiply(new BigDecimal(modelWeekRow.getSunOCC()));
				BigDecimal addrMon = modelWeekRow.getMonADR().multiply(new BigDecimal(modelWeekRow.getMonOCC()));
				BigDecimal addrTue =  modelWeekRow.getTueADR().multiply(new BigDecimal(modelWeekRow.getTueOCC()));
				BigDecimal addrWen = modelWeekRow.getWenADR().multiply(new BigDecimal(modelWeekRow.getWenOCC()));
				BigDecimal addrThu =  modelWeekRow.getThuADR().multiply(new BigDecimal(modelWeekRow.getThuOCC()));
				
				BigDecimal adrWeek = adrSun.add(addrMon).add(addrTue).add(addrWen).add(addrThu);
				if (modelWeekRow.getWeekOCC() > 0){
					modelWeekRow.setWeekADR(adrWeek.divide(new BigDecimal(modelWeekRow.getWeekOCC()),2,BigDecimal.ROUND_CEILING));
				}else{
					modelWeekRow.setWeekADR(BigDecimal.ZERO);
				}
				
				modelWeekRow.setWeekendADR(BigDecimal.ZERO);
				BigDecimal adrFri =  modelWeekRow.getFriADR().multiply(new BigDecimal(modelWeekRow.getFriOCC()));
				BigDecimal adrSat = modelWeekRow.getSatADR().multiply(new BigDecimal(modelWeekRow.getSatOCC()));
				
				BigDecimal adrWeekend = adrFri.add(adrSat);
				if (modelWeekRow.getWeekendOCC() > 0){
					modelWeekRow.setWeekendADR(adrWeekend.divide(new BigDecimal(modelWeekRow.getWeekendOCC()),2,BigDecimal.ROUND_CEILING));
				}else{
					modelWeekRow.setWeekendADR(BigDecimal.ZERO);
				}
				
				modelWeekRow.setTotalADR(BigDecimal.ZERO);
				if (modelWeekRow.getTotalOCC() > 0){
					
					BigDecimal adrWeekT = modelWeekRow.getWeekADR().multiply(new BigDecimal(modelWeekRow.getWeekOCC()));
					BigDecimal adrWeekendT = modelWeekRow.getWeekendADR().multiply(new BigDecimal(modelWeekRow.getWeekendOCC()));
					
					BigDecimal adrTotal = adrWeekT.add(adrWeekendT);
					
					modelWeekRow.setTotalADR(adrTotal.divide(new BigDecimal(modelWeekRow.getTotalOCC()),2,BigDecimal.ROUND_CEILING));
					
					//modelWeekRow.setTotalADR(    modelWeekRow.getWeekADR().add(modelWeekRow.getWeekendADR().multiply(new BigDecimal(modelWeekRow.getWeekendOCC())));
					//,2, BigDecimal.ROUND_CEILING));
				}else{
					modelWeekRow.setTotalADR(BigDecimal.ZERO);
				}
				
				lstModelWeek.add(modelWeekRow);
			}
		}
			
				
		System.out.println("*********** Trend Tool - populateModelWeekInformation - END - " + new Date() +" ***********");
		
		return lstModelWeek;		
	}
	
	
	public List<TrendTool> populateForecastTrendInformation(List<TrendTool> lstOverride, List<TrendTool> lstActualChange, List<TrendTool> lstModelWeek, List<TrendTool> lstForecastBase){
		
		System.out.println("*********** Trend Tool - populateForecastTrendInformation - BEGIN - " + new Date() +" ***********");
		
		List<TrendTool>  lstForecastTrend = new ArrayList<TrendTool>();
		
		
		for (int i=0; i < lstActualChange.size(); i++ ){
			TrendTool forecastTrenRow = new TrendTool();
			
			forecastTrenRow.setSegmentName(lstActualChange.get(i).getSegmentName());
			forecastTrenRow.setSegmentId(lstActualChange.get(i).getSegmentId());
						
			if(lstOverride.get(i).getSunOCC() == null){
				forecastTrenRow.setSunOCC(lstActualChange.get(i).getSunOCC());				
			}else{
				forecastTrenRow.setSunOCC(lstOverride.get(i).getSunOCC());
			}
			
			if(lstOverride.get(i).getMonOCC() == null){
				forecastTrenRow.setMonOCC(lstActualChange.get(i).getMonOCC());				
			}else{
				forecastTrenRow.setMonOCC(lstOverride.get(i).getMonOCC());
			}
			
			if(lstOverride.get(i).getTueOCC() == null){
				forecastTrenRow.setTueOCC(lstActualChange.get(i).getTueOCC());				
			}else{
				forecastTrenRow.setTueOCC(lstOverride.get(i).getTueOCC());
			}
			
			if(lstOverride.get(i).getWenOCC() == null){
				forecastTrenRow.setWenOCC(lstActualChange.get(i).getWenOCC());				
			}else{
				forecastTrenRow.setWenOCC(lstOverride.get(i).getWenOCC());
			}
			
			if(lstOverride.get(i).getThuOCC() == null){
				forecastTrenRow.setThuOCC(lstActualChange.get(i).getThuOCC());				
			}else{
				forecastTrenRow.setThuOCC(lstOverride.get(i).getThuOCC());
			}
			
			
			if(lstOverride.get(i).getFriOCC() == null){
				forecastTrenRow.setFriOCC(lstActualChange.get(i).getFriOCC());				
			}else{
				forecastTrenRow.setFriOCC(lstOverride.get(i).getFriOCC());
			}
			
			
			if(lstOverride.get(i).getSatOCC() == null){
				forecastTrenRow.setSatOCC(lstActualChange.get(i).getSatOCC());				
			}else{
				forecastTrenRow.setSatOCC(lstOverride.get(i).getSatOCC());
			}
			
			
			
			
			if( lstModelWeek.get(i).getWeekOCC() != null  && lstModelWeek.get(i).getWeekOCC() > 0){
								
					Integer forecastBaseVal = lstForecastBase.get(i).getWeekOCC();
				
				if (forecastBaseVal == 0){
					forecastBaseVal = 1;
				}
				
				BigDecimal trendOCCCal = new BigDecimal(lstModelWeek.get(i).getWeekOCC() - forecastBaseVal);				
				trendOCCCal.divide(new BigDecimal(forecastBaseVal),2,BigDecimal.ROUND_CEILING).multiply(new BigDecimal(100));				
				forecastTrenRow.setWeekOCC(trendOCCCal.setScale(0, BigDecimal.ROUND_CEILING).intValue());												
			}
		
			else{
				forecastTrenRow.setWeekOCC(null); // N/A
			}
			
			
			if( lstModelWeek.get(i).getWeekendOCC() != null && lstModelWeek.get(i).getWeekendOCC() > 0){
				
				Integer forecastBaseVal = lstForecastBase.get(i).getWeekendOCC();
				
				if (forecastBaseVal == 0){
					forecastBaseVal = 1;
				}
				
				BigDecimal trendOCCCal = new BigDecimal(lstModelWeek.get(i).getWeekendOCC() - forecastBaseVal);				
				trendOCCCal.divide(new BigDecimal(forecastBaseVal),2,BigDecimal.ROUND_CEILING).multiply(new BigDecimal(100));				
				forecastTrenRow.setWeekendOCC(trendOCCCal.setScale(0, BigDecimal.ROUND_CEILING).intValue());
				
			}else{
				forecastTrenRow.setWeekendOCC(null);
			}
			
			if(lstModelWeek.get(i).getTotalOCC() != null && lstModelWeek.get(i).getTotalOCC() > 0){
				
				Integer forecastBaseVal = lstForecastBase.get(i).getTotalOCC();
				
				if (forecastBaseVal == 0){
					forecastBaseVal = 1;
				}
				
				BigDecimal trendOCCCal = new BigDecimal(lstModelWeek.get(i).getTotalOCC() - forecastBaseVal);				
				trendOCCCal.divide(new BigDecimal(forecastBaseVal),2,BigDecimal.ROUND_CEILING).multiply(new BigDecimal(100));				
				forecastTrenRow.setTotalOCC(trendOCCCal.setScale(0, BigDecimal.ROUND_CEILING).intValue());
				
			}else{
				forecastTrenRow.setTotalOCC(null);
			}
			
			
			if(lstOverride.get(i).getSunADR() == null){
				forecastTrenRow.setSunADR(lstActualChange.get(i).getSunADR());
			}else{
				forecastTrenRow.setSunADR(lstOverride.get(i).getSunADR());
			}
			
			if(lstOverride.get(i).getMonADR() == null){
				forecastTrenRow.setMonADR(lstActualChange.get(i).getMonADR());
			}else{
				forecastTrenRow.setMonADR(lstOverride.get(i).getMonADR());
			}
			
			if(lstOverride.get(i).getTueADR() == null){
				forecastTrenRow.setTueADR(lstActualChange.get(i).getTueADR());
			}else{
				forecastTrenRow.setTueADR(lstOverride.get(i).getTueADR());
			}
			
			if(lstOverride.get(i).getWenADR()== null){
				forecastTrenRow.setWenADR(lstActualChange.get(i).getWenADR());
			}else{
				forecastTrenRow.setWenADR(lstOverride.get(i).getWenADR());
			}
			
			if(lstOverride.get(i).getThuADR()== null){
				forecastTrenRow.setThuADR(lstActualChange.get(i).getThuADR());
			}else{
				forecastTrenRow.setThuADR(lstOverride.get(i).getThuADR());
			}
			
			if(lstOverride.get(i).getFriADR()== null){
				forecastTrenRow.setFriADR(lstActualChange.get(i).getFriADR());
			}else{
				forecastTrenRow.setFriADR(lstOverride.get(i).getFriADR());
			}
			
			if(lstOverride.get(i).getSatADR()== null){
				forecastTrenRow.setSatADR(lstActualChange.get(i).getSatADR());
			}else{
				forecastTrenRow.setSatADR(lstOverride.get(i).getSatADR());
			}
			
			if(lstModelWeek.get(i).getWeekADR() != null &&  lstModelWeek.get(i).getWeekADR().compareTo(BigDecimal.ZERO) > 0){
				
				BigDecimal forecastBaseVal = lstForecastBase.get(i).getWeekADR();
				
				if (forecastBaseVal.compareTo(BigDecimal.ZERO) == 0){
					forecastBaseVal = BigDecimal.ONE;
				}
								
				forecastTrenRow.setWeekADR(lstModelWeek.get(i).getWeekADR().subtract(forecastBaseVal).divide(forecastBaseVal,2,BigDecimal.ROUND_CEILING).multiply(new BigDecimal(100)).setScale(0));
				
			}else{
				forecastTrenRow.setWeekADR(null);
			}
			
			
			if(lstModelWeek.get(i).getWeekendADR()!= null && lstModelWeek.get(i).getWeekendADR().compareTo(BigDecimal.ZERO) > 0){
				
				BigDecimal forecastBaseVal = lstForecastBase.get(i).getWeekendADR();
				
				if (forecastBaseVal.compareTo(BigDecimal.ZERO) == 0){
					forecastBaseVal = BigDecimal.ONE;
				}
								
				forecastTrenRow.setWeekendADR(lstModelWeek.get(i).getWeekendADR().subtract(forecastBaseVal).divide(forecastBaseVal,2,BigDecimal.ROUND_CEILING).multiply(new BigDecimal(100)).setScale(0));
				
			}else{
				forecastTrenRow.setWeekendADR(null);
			}
			
									
			if(lstModelWeek.get(i).getTotalADR()!= null  && lstModelWeek.get(i).getTotalADR().compareTo(BigDecimal.ZERO) > 0){
				
				BigDecimal forecastBaseVal = lstForecastBase.get(i).getTotalADR();
				
				if (forecastBaseVal.compareTo(BigDecimal.ZERO) == 0){
					forecastBaseVal = BigDecimal.ONE;
				}
								
				forecastTrenRow.setTotalADR(lstModelWeek.get(i).getTotalADR().subtract(forecastBaseVal).divide(forecastBaseVal,2,BigDecimal.ROUND_CEILING).multiply(new BigDecimal(100)).setScale(0));
				
			}else{
				forecastTrenRow.setTotalADR(null);
			}
			
			lstForecastTrend.add(forecastTrenRow);
		}
		
		System.out.println("*********** Trend Tool - populateForecastTrendInformation - END - " + new Date() +" ***********");
		
		return lstForecastTrend;		
	}
	
	
	public List<TrendTool> populateOverrideList(List<TrendTool> lstTrendOld ){
		
		System.out.println("*********** Trend Tool - populateOverrideList - BEGIN - " + new Date() +" ***********");
		
		List<TrendTool>  lstOverride = new ArrayList<TrendTool>();
		
		for(int i = 0; i< lstTrendOld.size() ; i++){
			TrendTool overrideRow = new TrendTool();
			
			overrideRow.setSegmentId(lstTrendOld.get(i).getSegmentId());
			overrideRow.setSegmentName(lstTrendOld.get(i).getSegmentName());
			
			overrideRow.setSunOCC(null);
			overrideRow.setMonOCC(null);
			overrideRow.setTueOCC(null);
			overrideRow.setWenOCC(null);
			overrideRow.setThuOCC(null);
			overrideRow.setFriOCC(null);
			overrideRow.setSatOCC(null);
			
			overrideRow.setWeekOCC(null);
			overrideRow.setWeekendOCC(null);
			overrideRow.setTotalOCC(null);
			
			overrideRow.setSunADR(null);
			overrideRow.setMonADR(null);
			overrideRow.setTueADR(null);
			overrideRow.setWenADR(null);
			overrideRow.setThuADR(null);
			overrideRow.setFriADR(null);
			overrideRow.setSatADR(null);
			
			overrideRow.setWeekADR(null);
			overrideRow.setWeekendADR(null);
			overrideRow.setTotalADR(null);
			
			lstOverride.add(overrideRow);						
			
		}
		
		System.out.println("*********** Trend Tool - populateOverrideList - END - " + new Date() +" ***********");
		
		return lstOverride;		
	}
	
	
	public List<TrendTool> populateAdvancedPasting(List<TrendTool> lstTrendOld ){
		
		System.out.println("*********** Trend Tool - populateAdvancedPasting - BEGIN - " + new Date() +" ***********");
		
		List<TrendTool>  lstAdvancedPasting = new ArrayList<TrendTool>();
		
		for(int i = 0; i< lstTrendOld.size() ; i++){
			TrendTool aPasting = new TrendTool();
			
			aPasting.setSegmentId(lstTrendOld.get(i).getSegmentId());
			aPasting.setSegmentName(lstTrendOld.get(i).getSegmentName());
			
			aPasting.setSunOCC(null);
			aPasting.setMonOCC(null);
			aPasting.setTueOCC(null);
			aPasting.setWenOCC(null);
			aPasting.setThuOCC(null);
			aPasting.setFriOCC(null);
			aPasting.setSatOCC(null);
			
			aPasting.setWeekOCC(null);
			aPasting.setWeekendOCC(null);
			aPasting.setTotalOCC(null);
			
			aPasting.setSunADR(null);
			aPasting.setMonADR(null);
			aPasting.setTueADR(null);
			aPasting.setWenADR(null);
			aPasting.setThuADR(null);
			aPasting.setFriADR(null);
			aPasting.setSatADR(null);
			
			aPasting.setWeekADR(null);
			aPasting.setWeekendADR(null);
			aPasting.setTotalADR(null);
			
			lstAdvancedPasting.add(aPasting);						
			
		}
		
		System.out.println("*********** Trend Tool - populateAdvancedPasting - END - " + new Date() +" ***********");
		
		return lstAdvancedPasting;		
	}
	
	
	public List<TrendTool> fillModelWeekZeros(List<TrendTool> lstTrendOld ){
		
		System.out.println("*********** Trend Tool - fillModelWeekZeros - BEGIN - " + new Date() +" ***********");
		
		List<TrendTool>  lstModelWeek = new ArrayList<TrendTool>();
		
		for(int i = 0; i< lstTrendOld.size() ; i++){
			TrendTool modelWeek = new TrendTool();
			
			modelWeek.setSegmentId(lstTrendOld.get(i).getSegmentId());
			modelWeek.setSegmentName(lstTrendOld.get(i).getSegmentName());
			
			modelWeek.setSunOCC(null);
			modelWeek.setMonOCC(null);
			modelWeek.setTueOCC(null);
			modelWeek.setWenOCC(null);
			modelWeek.setThuOCC(null);
			modelWeek.setFriOCC(null);
			modelWeek.setSatOCC(null);
			
			modelWeek.setWeekOCC(null);
			modelWeek.setWeekendOCC(null);
			modelWeek.setTotalOCC(null);
			
			modelWeek.setSunADR(null);
			modelWeek.setMonADR(null);
			modelWeek.setTueADR(null);
			modelWeek.setWenADR(null);
			modelWeek.setThuADR(null);
			modelWeek.setFriADR(null);
			modelWeek.setSatADR(null);
			
			modelWeek.setWeekADR(null);
			modelWeek.setWeekendADR(null);
			modelWeek.setTotalADR(null);
			
			lstModelWeek.add(modelWeek);						
			
		}
		
		System.out.println("*********** Trend Tool - fillModelWeekZeros - END - " + new Date() +" ***********");
		
		return lstModelWeek;		
	}
	
	public TrendToolLists populateMainLists (String trendNewStatDate, String trendNewEndDate, String trendOldStatDate, String trendOldEndDate, String forecastBaseStatDate, String forecastBaseEndDate, int customerId){
		
		// ... do something ...
		
		TrendToolLists  trendToolList = new TrendToolLists();

		// All list should have the same size.
		
		long startTime = System.currentTimeMillis();
			trendToolList.setLstTrendOld(populateTrendOldInformation(trendOldStatDate, trendOldEndDate, customerId));
		long estimatedTime = System.currentTimeMillis() - startTime;
		System.out.println("TT setLstTrendOld "+estimatedTime);
		
		
		startTime = System.currentTimeMillis();
			trendToolList.setLstTrendNew(populateTrendNewInformation(trendNewStatDate, trendNewEndDate, customerId));
		estimatedTime = System.currentTimeMillis() - startTime;
		System.out.println("TT setLstTrendNew "+estimatedTime);
		
		startTime = System.currentTimeMillis();
			trendToolList.setLstActualChange(populateActualChangeInformation(trendToolList.getLstTrendOld(), trendToolList.getLstTrendNew()));
		estimatedTime = System.currentTimeMillis() - startTime;
		System.out.println("TT setLstActualChange "+estimatedTime);
	
		
		startTime = System.currentTimeMillis();
			trendToolList.setLstOverride(populateOverrideList(trendToolList.getLstTrendOld()));
		estimatedTime = System.currentTimeMillis() - startTime;
		System.out.println("TT setLstOverride "+estimatedTime);

		
		
		if (trendToolList.getLstModelWeek() == null){
			trendToolList.setLstModelWeek(fillModelWeekZeros(trendToolList.getLstTrendOld()));
		}
	
		startTime = System.currentTimeMillis();
			trendToolList.setLstForecastBase(populateForecastBaselineInformation(forecastBaseStatDate, forecastBaseEndDate, customerId));
		estimatedTime = System.currentTimeMillis() - startTime;
		System.out.println("TT setLstForecastBase "+estimatedTime);

		
		
		// param: lstModelWeek with zeros.
		startTime = System.currentTimeMillis();
			trendToolList.setLstForecastTrend(populateForecastTrendInformation(trendToolList.getLstOverride(), trendToolList.getLstActualChange(), 
																			trendToolList.getLstModelWeek(),trendToolList.getLstForecastBase()));
		
		estimatedTime = System.currentTimeMillis() - startTime;
		System.out.println("TT setLstForecastTrend "+estimatedTime);

			
		startTime = System.currentTimeMillis();
			trendToolList.setLstModelWeek(populateModelWeekInformation(trendToolList.getLstForecastBase(),trendToolList.getLstForecastTrend()));
		estimatedTime = System.currentTimeMillis() - startTime;
		System.out.println("TT setLstModelWeek "+estimatedTime);

		//param: lstModel with real values.
		startTime = System.currentTimeMillis();
		
		trendToolList.setLstForecastTrend(populateForecastTrendInformation(trendToolList.getLstOverride(), trendToolList.getLstActualChange(), 
				trendToolList.getLstModelWeek(),trendToolList.getLstForecastBase()));
		estimatedTime = System.currentTimeMillis() - startTime;
		System.out.println("TT setLstForecastTrend "+estimatedTime);

		
		startTime = System.currentTimeMillis();
		
		trendToolList.setLstAdvancedPasting(populateAdvancedPasting(trendToolList.getLstTrendOld()));
		estimatedTime = System.currentTimeMillis() - startTime;
		System.out.println("TT setLstAdvancedPasting Week "+estimatedTime);

		
		System.out.println("*********** Trend Tool - populateMainLists - END - " + new Date() +" ***********");
		
		return trendToolList;
	}
	
	public int hasActuals(String startDate, String endDate){
		int result = 0;
		
		System.out.println("*********** Trend Tool - hasActuals - BEGIN - " + new Date() +" ***********");
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		conn = this.getCurrentHRR3Connection();	
		
		String statement = ("select count(1) as actuals from RM3TransientData where hotel_id = ? and statdate between ? and ? and isActual = 1");
		
		try {
			ps = conn.prepareStatement(statement);			
			
			ps.setInt(1, this.getCurrentHotel().getHotelId());
			ps.setString(2, startDate);
			ps.setString(3, endDate);
			
			rs = ps.executeQuery();
			
			while(rs != null && rs.next()){	
				result = rs.getInt("actuals");
			}
		
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
		}
	
		finally { close(rs, ps, conn);};
		
		System.out.println("*********** Trend Tool - hasActuals - END - " + new Date() +" ***********");
		
		return result;
	
	}
	
			
	public void pasteForecast ( List<TrendTool>  pasteDataList, String startDate, String endDate, String typePasteForecast){
		
		System.out.println("*********** Trend Tool - pasteForecast - BEGIN - " + new Date() +" ***********");
		
		
		Calendar statDateStart = Calendar.getInstance();
	    Calendar statDateEnd = Calendar.getInstance();
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    
	    Connection currentConnection = null;
	    
	    try {
	    	
	    	currentConnection = this.getCurrentHRR3Connection();
	    	
	    	statDateStart.setTime(dateFormat.parse(startDate));
	    	statDateStart.set(Calendar.HOUR_OF_DAY, 0);
	    	statDateStart.set(Calendar.MINUTE,0);
	    	statDateStart.set(Calendar.SECOND,0);
	    	statDateStart.set(Calendar.MILLISECOND, 0);
	    	
			statDateEnd.setTime(dateFormat.parse(endDate));
			statDateEnd.set(Calendar.HOUR_OF_DAY, 0);
			statDateEnd.set(Calendar.MINUTE,0);
			statDateEnd.set(Calendar.SECOND,0);
			statDateEnd.set(Calendar.MILLISECOND, 0);
			
		
			    			
		Calendar dateCalc  = Calendar.getInstance();						
		dateCalc.setTime(statDateStart.getTime());		
				
		while (dateCalc.compareTo(statDateEnd)<= 0){
			
			int ind = 1; 
			for (TrendTool pasteDataRow : pasteDataList){
				System.out.println("For indice :" + ind );
				
				int day = dateCalc.get(Calendar.DAY_OF_WEEK);
				Integer pOCC = null;
				BigDecimal pADR = null;
				int updateType = 1; //default both values
				
				switch (day) {
				case 1: //Sunday
					pOCC = pasteDataRow.getSunOCC();
					pADR = pasteDataRow.getSunADR();
					break;
				case 2:
					pOCC = pasteDataRow.getMonOCC();
					pADR = pasteDataRow.getMonADR();
					break;
				case 3:
					pOCC = pasteDataRow.getTueOCC();
					pADR = pasteDataRow.getTueADR();
					break;
				case 4:
					pOCC = pasteDataRow.getWenOCC();
					pADR = pasteDataRow.getWenADR();
					break;
				case 5:
					pOCC = pasteDataRow.getThuOCC();
					pADR = pasteDataRow.getThuADR();
					break;
				case 6:
					pOCC = pasteDataRow.getFriOCC();
					pADR = pasteDataRow.getFriADR();
					break;
				case 7:
					pOCC = pasteDataRow.getSatOCC();
					pADR = pasteDataRow.getSatADR();
					break;
				default:
					break;
				}
				
				if (pOCC != null && pADR != null){
					updateType = 1;
				}else{
					if ( pOCC != null && pADR == null){
						updateType = 2;
					}
					
					if (pOCC == null && pADR != null){
						updateType = 3;
					}
				}
				
				//Update DB
				if (typePasteForecast.equals("ModelWeek")){
					updateTransientSegmentDataModelWeek(dateCalc, pasteDataRow.getSegmentId(), pOCC, pADR, updateType);
				}
				
				if (typePasteForecast.equals("ForecastTrend")){
					updateTransientSegmentDataTrends(dateCalc, pasteDataRow.getSegmentId(), pOCC, pADR, updateType);
				}
				
				ind++;
			}
			dateCalc.add(Calendar.DATE, 1);
			
		}
		
	    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    finally{close(currentConnection);}
											
		System.out.println("*********** Trend Tool - pasteForecast - END - " + new Date() +" ***********");
	}
	
	
	
	public void advancedPasting (List<TrendTool> advancedPastingList,  List<TrendTool>  pasteDataList, String startDate, String endDate, String typePasteForecast){
		
		System.out.println("*********** Trend Tool - advancedPasting - BEGIN - " + new Date() +" ***********");
		
		
		Calendar statDateStart = Calendar.getInstance();
	    Calendar statDateEnd = Calendar.getInstance();
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    
	    Connection currentConnection =  null;
	    
	    try {
	    	
	    	currentConnection = this.getCurrentHRR3Connection();
	    	
	    	statDateStart.setTime(dateFormat.parse(startDate));
	    	statDateStart.set(Calendar.HOUR_OF_DAY, 0);
	    	statDateStart.set(Calendar.MINUTE,0);
	    	statDateStart.set(Calendar.SECOND,0);
	    	statDateStart.set(Calendar.MILLISECOND, 0);
	    	
			statDateEnd.setTime(dateFormat.parse(endDate));
			statDateEnd.set(Calendar.HOUR_OF_DAY, 0);
			statDateEnd.set(Calendar.MINUTE,0);
			statDateEnd.set(Calendar.SECOND,0);
			statDateEnd.set(Calendar.MILLISECOND, 0);
			
		
			    			
		Calendar dateCalc  = Calendar.getInstance();						
		dateCalc.setTime(statDateStart.getTime());		
				
		while (dateCalc.compareTo(statDateEnd)<= 0){
			
			for (TrendTool advancedDataRow : advancedPastingList){
				int day = dateCalc.get(Calendar.DAY_OF_WEEK);
				Integer aOCC = null;
				BigDecimal aADR = null;
				int updateType = 1; //default both values
				
				TrendTool dataToPaste =  getDataToPaste(pasteDataList, advancedDataRow.getSegmentName());
				
				if (dataToPaste != null){
				
					switch (day) {
					case 1: //Sunday												
						if(advancedDataRow.getSunOCC() != null && advancedDataRow.getSunADR() != null){
							aOCC = dataToPaste.getSunOCC();
							aADR = dataToPaste.getSunADR();
							updateType = 1;
						}else if (advancedDataRow.getSunOCC() != null){
							aOCC = dataToPaste.getSunOCC();
							updateType = 2;
						}else if (advancedDataRow.getSunADR() != null){
							aADR = dataToPaste.getSunADR();
							updateType = 3;
						}else{
							updateType = 0;
						}						
						break;
					case 2:
						if(advancedDataRow.getMonOCC() != null && advancedDataRow.getMonADR() != null){
							aOCC = advancedDataRow.getMonOCC();
							aADR = advancedDataRow.getMonADR();
							updateType = 1;
						}else if (advancedDataRow.getMonOCC() != null){
							aOCC = dataToPaste.getMonOCC();
							updateType = 2;
						}else if (advancedDataRow.getMonADR() != null){
							aADR = dataToPaste.getMonADR();
							updateType = 3;
						}else{
							updateType = 0;
						}			
						break;
					case 3:

						if(advancedDataRow.getTueOCC() != null && advancedDataRow.getTueADR() != null){
							aOCC = advancedDataRow.getTueOCC();
							aADR = advancedDataRow.getTueADR();
							updateType = 1;
						}else if (advancedDataRow.getTueOCC() != null){
							aOCC = dataToPaste.getTueOCC();
							updateType = 2;
						}else if (advancedDataRow.getTueADR() != null){
							aADR = dataToPaste.getTueADR();
							updateType = 3;
						}else{
							updateType = 0;
						}			
						break;
					case 4:
						
						if(advancedDataRow.getWenOCC() != null && advancedDataRow.getWenADR() != null){
							aOCC = advancedDataRow.getWenOCC();
							aADR = advancedDataRow.getWenADR();
							updateType = 1;
						}else if (advancedDataRow.getWenOCC() != null){
							aOCC = dataToPaste.getWenOCC();
							updateType = 2;
						}else if (advancedDataRow.getWenADR() != null){
							aADR = dataToPaste.getWenADR();
							updateType = 3;
						}else{
							updateType = 0;
						}			
						break;
					case 5:
						
						if(advancedDataRow.getThuOCC() != null && advancedDataRow.getThuADR() != null){
							aOCC = advancedDataRow.getThuOCC();
							aADR = advancedDataRow.getThuADR();
							updateType = 1;
						}else if (advancedDataRow.getThuOCC() != null){
							aOCC = dataToPaste.getThuOCC();
							updateType = 2;
						}else if (advancedDataRow.getThuADR() != null){
							aADR = dataToPaste.getThuADR();
							updateType = 3;
						}else{
							updateType = 0;
						}			
						break;
					case 6:
						
						if(advancedDataRow.getFriOCC() != null && advancedDataRow.getFriADR() != null){
							aOCC = advancedDataRow.getFriOCC();
							aADR = advancedDataRow.getFriADR();
							updateType = 1;
						}else if (advancedDataRow.getFriOCC() != null){
							aOCC = dataToPaste.getFriOCC();
							updateType = 2;
						}else if (advancedDataRow.getFriADR() != null){
							aADR = dataToPaste.getFriADR();
							updateType = 3;
						}else{
							updateType = 0;
						}			
						break;
					case 7:
						if(advancedDataRow.getSatOCC() != null && advancedDataRow.getSatADR() != null){
							aOCC = advancedDataRow.getSatOCC();
							aADR = advancedDataRow.getSatADR();
							updateType = 1;
						}else if (advancedDataRow.getSatOCC() != null){
							aOCC = dataToPaste.getSatOCC();
							updateType = 2;
						}else if (advancedDataRow.getSatADR() != null){
							aADR = dataToPaste.getSatADR();
							updateType = 3;
						}else{
							updateType = 0;
						}			
						break;
					default:
						break;
					}
					
					if (updateType > 0) {
						//Update DB
						if (typePasteForecast.equals("ModelWeek")){
							updateTransientSegmentDataModelWeek(dateCalc, advancedDataRow.getSegmentId(), aOCC, aADR, updateType);
						}
						
						if (typePasteForecast.equals("ForecastTrend")){
							updateTransientSegmentDataTrends(dateCalc, advancedDataRow.getSegmentId(), aOCC, aADR, updateType);
						}
					}	
				}
			}
			dateCalc.add(Calendar.DATE, 1);
			
		}
		
	    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    finally{ close(currentConnection);}
											
		System.out.println("*********** Trend Tool - advancedPasting - END - " + new Date() +" ***********");
	}

	
	public TrendTool getDataToPaste (List<TrendTool> dataList, String segmentName){
		for(TrendTool dataRow : dataList){
			if (dataRow.getSegmentName().equals(segmentName)){
				return dataRow;
			}			
		}
		
		return null;	
	}

	/*
	public void pasteForecast (TrendToolLists  trendList, String startDate, String endDate, String typePasteForecast){
		
		System.out.println("*********** Trend Tool - pasteForecast - BEGIN - " + new Date() +" ***********");
		
		
		Calendar statDateStart = Calendar.getInstance();
	    Calendar statDateEnd = Calendar.getInstance();
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    
	    try {
	    	
	    	statDateStart.setTime(dateFormat.parse(startDate));
	    	statDateStart.set(Calendar.HOUR_OF_DAY, 0);
	    	statDateStart.set(Calendar.MINUTE,0);
	    	statDateStart.set(Calendar.SECOND,0);
	    	statDateStart.set(Calendar.MILLISECOND, 0);
	    	
			statDateEnd.setTime(dateFormat.parse(endDate));
			statDateEnd.set(Calendar.HOUR_OF_DAY, 0);
			statDateEnd.set(Calendar.MINUTE,0);
			statDateEnd.set(Calendar.SECOND,0);
			statDateEnd.set(Calendar.MILLISECOND, 0);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    
		if (typePasteForecast.equals("ModelWeek")){
			
			Calendar dateCalc  = Calendar.getInstance();
								
			dateCalc.setTime(statDateStart.getTime());		
					
			while (dateCalc.compareTo(statDateEnd)<= 0){
				
				for (TrendTool modelWeekRow : trendList.getLstModelWeek()){
					int day = dateCalc.get(Calendar.DAY_OF_WEEK);
					Integer mwOCC = null;
					BigDecimal mwADR = null;
					int updateType = 1; //default both values
					
					switch (day) {
					case 1: //Sunday
						mwOCC = modelWeekRow.getSunOCC();
						mwADR = modelWeekRow.getSunADR();
						break;
					case 2:
						mwOCC = modelWeekRow.getMonOCC();
						mwADR = modelWeekRow.getMonADR();
						break;
					case 3:
						mwOCC = modelWeekRow.getTueOCC();
						mwADR = modelWeekRow.getTueADR();
						break;
					case 4:
						mwOCC = modelWeekRow.getWenOCC();
						mwADR = modelWeekRow.getWenADR();
						break;
					case 5:
						mwOCC = modelWeekRow.getThuOCC();
						mwADR = modelWeekRow.getThuADR();
						break;
					case 6:
						mwOCC = modelWeekRow.getFriOCC();
						mwADR = modelWeekRow.getFriADR();
						break;
					case 7:
						mwOCC = modelWeekRow.getSunOCC();
						mwADR = modelWeekRow.getSunADR();
						break;
					default:
						break;
					}
					
					if (mwOCC != null && mwADR != null){
						updateType = 1;
					}else{
						if ( mwOCC != null && mwADR == null){
							updateType = 2;
						}
						
						if (mwOCC == null && mwADR != null){
							updateType = 3;
						}
					}
					
					//Update DB
					updateTransientSegmentDataModelWeek(dateCalc, modelWeekRow.getSegmentId(), mwOCC, mwADR, updateType);
				}
				dateCalc.add(Calendar.DATE, 1);
				
			}
			
		}else{
			if (typePasteForecast.equals("ForecastTrend")){
				
				Calendar dateCalc  = Calendar.getInstance();
				
				dateCalc.setTime(statDateStart.getTime());		
						
				while (dateCalc.compareTo(statDateEnd)<= 0){
					
					for (TrendTool trendRow : trendList.getLstForecastTrend()){
						int day = dateCalc.get(Calendar.DAY_OF_WEEK);
						Integer trendOCC = null;
						BigDecimal trendADR = null;
						int updateType = 1; //default both values
						
						switch (day) {
						case 1: //Sunday
							trendOCC = trendRow.getSunOCC();
							trendADR = trendRow.getSunADR();
							break;
						case 2:
							trendOCC = trendRow.getMonOCC();
							trendADR = trendRow.getMonADR();
							break;
						case 3:
							trendOCC = trendRow.getTueOCC();
							trendADR = trendRow.getTueADR();
							break;
						case 4:
							trendOCC = trendRow.getWenOCC();
							trendADR = trendRow.getWenADR();
							break;
						case 5:
							trendOCC = trendRow.getThuOCC();
							trendADR = trendRow.getThuADR();
							break;
						case 6:
							trendOCC = trendRow.getFriOCC();
							trendADR = trendRow.getFriADR();
							break;
						case 7:
							trendOCC = trendRow.getSunOCC();
							trendADR = trendRow.getSunADR();
							break;
						default:
							break;
						}
						
						if (trendOCC != null && trendADR != null){
							updateType = 1;
						}else{
							if ( trendOCC != null && trendADR == null){
								updateType = 2;
							}
							
							if (trendOCC == null && trendADR != null){
								updateType = 3;
							}
						}
						
						//Update DB
						updateTransientSegmentDataTrends(dateCalc, trendRow.getSegmentId(), trendOCC, trendADR, updateType);
												
					}
					dateCalc.add(Calendar.DATE, 1);
					
				}															
			}
			
		}		
		
		System.out.println("*********** Trend Tool - pasteForecast - END - " + new Date() +" ***********");
	}
	*/
	
	public void updateTransientSegmentDataModelWeek (Calendar statdate, int segmentId, Integer OCC,  BigDecimal ADR, int updateType ){
		
		System.out.println("*********** Trend Tool - updateTransientSegmentDataModelWeek - BEGIN - " + new Date() +" ***********");
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String sStatdate = dateFormat.format(statdate.getTime());
		
		try{	
						
			conn = this.getCurrentHRR3Connection();	
			
			String statement = ("{call ftUpdateTransientSegmentDataModelWeek (?,?,?,?,?,?)}");
			
			ps = conn.prepareStatement(statement);			
			ps.setInt(1, this.getCurrentHotel().getHotelId());
			ps.setString(2, sStatdate);
			ps.setInt(3, segmentId);
			ps.setInt(4, OCC);			
			ps.setBigDecimal(5, ADR);
			ps.setInt(6, updateType);
			
			ps.executeUpdate();			
			
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
		}
	
		finally { close(rs, ps);};	
		
		System.out.println("*********** Trend Tool - updateTransientSegmentDataModelWeek - END - " + new Date() +" ***********");
	}
	
	public void updateTransientSegmentDataTrends (Calendar statdate, int segmentId, Integer OCC,  BigDecimal ADR, int updateType ){
		
		System.out.println("*********** Trend Tool - updateTransientSegmentDataTrends - BEGIN - " + new Date() +" ***********");
		
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");				
		
		try{	
						
			conn = this.getCurrentHRR3Connection();	
			
			String statement = ("{call ftUpdateTransientSegmentDataTrends (?,?,?,?,?,?,?)}");
			
			ps = conn.prepareStatement(statement);			
			ps.setInt(1, this.getCurrentHotel().getHotelId());
			ps.setString(2, dateFormat.format(statdate.getTime()));
			ps.setString(3, dateFormat.format(statdate.getTime()));
			ps.setInt(4, segmentId);
			ps.setInt(5, OCC);			
			ps.setBigDecimal(6, ADR);
			ps.setInt(7, updateType);
			
			ps.executeUpdate();			
			
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
		}
	
		finally { close(rs, ps);};
		
		System.out.println("*********** Trend Tool - updateTransientSegmentDataTrends - END - " + new Date() +" ***********");
	}
	
	
	public void reCalculateAll(String startDate, String endDate, int customerId){
		
		System.out.println("*********** Trend Tool - reCalculateAll - BEGIN - " + new Date() +" ***********");
		
		int totalRooms = this.getCurrentHotel().getTotalRooms();
		
		Calendar statDateStart = Calendar.getInstance();
	    Calendar statDateEnd = Calendar.getInstance();
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    
	    Connection currentConnection = null;
	    
	    try {
	    	
	    	currentConnection = this.getCurrentHRR3Connection();
	
		    	
	    	statDateStart.setTime(dateFormat.parse(startDate));
	    	statDateStart.set(Calendar.HOUR_OF_DAY, 0);
	    	statDateStart.set(Calendar.MINUTE,0);
	    	statDateStart.set(Calendar.SECOND,0);
	    	statDateStart.set(Calendar.MILLISECOND, 0);
	    	
			statDateEnd.setTime(dateFormat.parse(endDate));
			statDateEnd.set(Calendar.HOUR_OF_DAY, 0);
			statDateEnd.set(Calendar.MINUTE,0);
			statDateEnd.set(Calendar.SECOND,0);
			statDateEnd.set(Calendar.MILLISECOND, 0);
				
							    		
		
			calculateGroup(startDate,endDate, totalRooms,customerId);					
				
			calculateGroupFinish(startDate, endDate, totalRooms);		
					
			calculateGroupTotals(startDate, endDate, totalRooms, customerId);			
			
			
			Calendar dateCalc  = Calendar.getInstance();						
			dateCalc.setTime(statDateStart.getTime());	
			
			while (dateCalc.compareTo(statDateEnd)<= 0){
				
				calculateTransient(dateFormat.format(dateCalc.getTime()), totalRooms, customerId);
				
				dateCalc.add(Calendar.DATE, 1);
			}
		
	    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    finally{close(currentConnection);}
	    
		System.out.println("*********** Trend Tool - reCalculateAll - END - " + new Date() +" ***********");
		
	}
	
	public void calculateGroup(String startDate, String endDate, int totalRooms, int customerId){
		
		System.out.println("*********** Trend Tool - calculateGroup - BEGIN - " + new Date() +" ***********");
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;		
		
		
		try{	
						
			conn = this.getCurrentHRR3Connection();	
			

			String statement = "update RM3TransientData d LEFT JOIN (select hotel_id, statdate, " +
					    "sum(IFNULL(def_occ,0)) as gdef_occ, sum(IFNULL(def_rev,0)) as gdef_rev, " +
					    "sum(IFNULL(ten_occ,0)) as gten_occ, sum(IFNULL(ten_rev,0)) as gten_rev " +
					    "from RM3TransientSegmentData where statdate between ? and ? and hotel_id=? " +
					    "and segment_id in (select segment_id from RM3Segments where customer_id = ? and " +
					    "istotal = 0 and isactive = 1 and type='TM') group by hotel_id, statdate) as sd " +
					    "ON d.hotel_id = sd.hotel_id set " +
					    "d.gtot_occ=0, " +
					    "d.gtot_rev=0, " +
					    "d.gtot_adr=0, " +
					    "d.gdef_occ = IFNULL(sd.gdef_occ,0), " +
					    "d.gdef_rev = IFNULL(sd.gdef_rev,0), " +
					    "d.gten_occ = IFNULL(sd.gten_occ,0), " +
					    "d.gten_rev = IFNULL(sd.gten_rev,0) " +
					    "WHERE d.statdate = sd.statdate AND d.hotel_id = ? and d.statdate between ? and ?";

			ps = conn.prepareStatement(statement);
			ps.setString(1,  startDate);
			ps.setString(2, endDate);
			ps.setInt(3, this.getCurrentHotel().getHotelId());
			ps.setInt(4, customerId);
			ps.setInt(5, this.getCurrentHotel().getHotelId());
			ps.setString(6,startDate);
			ps.setString(7,endDate);

			ps.executeUpdate();
			
			ps.close();
			
			
			statement = "";

			statement = "update RM3TransientData d LEFT JOIN (select hotel_id, statdate, " +
					    "sum(IFNULL(def_occ,0)) as gdef_occ, sum(IFNULL(def_rev,0)) as gdef_rev, " +
					    "sum(IFNULL(ten_occ,0)) as gten_occ, sum(IFNULL(ten_rev,0)) as gten_rev " +
					    "from RM3TransientSegmentData where statdate between ? and ? and hotel_id=? " +
					    "and segment_id in (select segment_id from RM3Segments where customer_id = ? and " +
					    "istotal = 0 and isactive = 0 and type='DP') group by hotel_id, statdate) as sd " +
					    "ON d.hotel_id = sd.hotel_id set " +
					    "d.gdef_occ = IFNULL(sd.gdef_occ,0), " +
					    "d.gdef_rev = IFNULL(sd.gdef_rev,0), " +
					    "d.gten_occ = IFNULL(sd.gten_occ,0), " +
					    "d.gten_rev = IFNULL(sd.gten_rev,0) " +
					    "WHERE d.statdate = sd.statdate AND d.hotel_id = ? and d.statdate between ? and ?";	
			
			ps = conn.prepareStatement(statement);
			ps.setString(1,  startDate);
			ps.setString(2, endDate);
			ps.setInt(3, this.getCurrentHotel().getHotelId());
			ps.setInt(4, customerId);
			ps.setInt(5, this.getCurrentHotel().getHotelId());
			ps.setString(6,startDate);
			ps.setString(7,endDate);

			ps.executeUpdate();
			
			ps.close();
			
			statement = "update RM3TransientData set " +
					    "gtot_occ=ifnull(gdef_occ,0)+ifnull(gten_occ,0), " +
					    "gtot_rev=ifnull(gdef_rev,0)+ifnull(gten_rev,0), " +
					    "gtot_adr=(ifnull(gdef_rev,0)+ifnull(gten_rev,0))/(case ifnull(gdef_occ,0)+ifnull(gten_occ,0) when 0 then 1 else ifnull(gdef_occ,0)+ifnull(gten_occ,0) end) " +
					    "where hotel_id= ? and statdate between ? and ? ";
			
			ps = conn.prepareStatement(statement);
			ps.setInt(1, this.getCurrentHotel().getHotelId());
			ps.setString(2,  startDate);
			ps.setString(3, endDate);			
			
			ps.executeUpdate();
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
		}
	
		finally { close(rs, ps, conn);};	
		
		System.out.println("*********** Trend Tool - calculateGroup - END - " + new Date() +" ***********");
		
	}
	
	public void calculateGroupFinish(String startDate, String endDate, int totalRooms){
		
		System.out.println("*********** Trend Tool - calculateGroupFinish - BEGIN - " + new Date() +" ***********");
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;		
		
		
		try{	
						
			conn = this.getCurrentHRR3Connection();	
			
			String statement = "update RM3TransientData set gdef_adr=ifnull(gdef_rev,0)/case gdef_occ when 0 then 1 else gdef_occ end , " +
							   "gten_adr=ifnull(gten_rev,0)/case gten_occ when 0 then 1 else gten_occ end " +
							   "where hotel_id= ?  and statdate between ? and ? ";

			ps = conn.prepareStatement(statement);
			ps = conn.prepareStatement(statement);
			ps.setInt(1, this.getCurrentHotel().getHotelId());
			ps.setString(2,  startDate);
			ps.setString(3, endDate);
			
			ps.executeUpdate();
			
			ps.close();
			
			
			statement = "";

			statement = "update RM3TransientData set gtot_occ=ifnull(gdef_occ,0)+ifnull(gten_occ,0), " +
					    "gtot_rev=ifnull(gdef_rev,0)+ifnull(gten_rev,0) " +
					    "where hotel_id= ?  and statdate between ? and ? ";	
						
			ps = conn.prepareStatement(statement);
			ps.setInt(1, this.getCurrentHotel().getHotelId());
			ps.setString(2,  startDate);
			ps.setString(3, endDate);
			
			

			ps.executeUpdate();
			
			ps.close();
			
			statement = "";
			statement = "update RM3TransientSegmentData set tot_occ=ifnull(def_occ,0)+ifnull(ten_occ,0), " +
					    "tot_rev=ifnull(def_rev,0)+ifnull(ten_rev,0) " +
					    "where hotel_id= ?  and statdate between ? and ? ";
			
			ps = conn.prepareStatement(statement);
			ps.setInt(1, this.getCurrentHotel().getHotelId());
			ps.setString(2,  startDate);
			ps.setString(3, endDate);			
			
			ps.executeUpdate();
			
			
			ps.close();
			
			statement = "";
			statement = "update RM3TransientData set gtot_adr=gtot_rev/gtot_occ,tot_occ_pct=gtot_occ/ ? " +
					    "where hotel_id= ?  and statdate between ? and ? and gtot_occ > 0  ";
			
			ps = conn.prepareStatement(statement);
			ps.setInt(1, totalRooms);
			ps.setInt(2, this.getCurrentHotel().getHotelId());
			ps.setString(3,  startDate);
			ps.setString(4, endDate);			
			
			ps.executeUpdate();
			
			
			ps.close();
			
			statement = "";
			statement = "update RM3TransientSegmentData set tot_adr=tot_rev/tot_occ  " +
						"where hotel_id= ?  and statdate between ? and ?  and tot_occ > 0 ";
			
			ps = conn.prepareStatement(statement);
			ps.setInt(1, this.getCurrentHotel().getHotelId());
			ps.setString(2,  startDate);
			ps.setString(3, endDate);			
			
			ps.executeUpdate();
			
			
			ps.close();
			
			statement = "";
			statement = "update RM3TransientData set gtot_adr=0  " +
						"where hotel_id= ?  and statdate between ? and ? " +
						"and (gtot_occ = 0 or gtot_occ is null)";
			
			ps = conn.prepareStatement(statement);
			ps.setInt(1, this.getCurrentHotel().getHotelId());
			ps.setString(2,  startDate);
			ps.setString(3, endDate);			
			
			ps.executeUpdate();
			
			
			ps.close();
			
			statement = "";
			statement = "update RM3TransientSegmentData set tot_adr=0 " +
						"where hotel_id= ?  and statdate between ? and ? " +
						"and (tot_occ = 0 or tot_occ is null) ";
		
			ps = conn.prepareStatement(statement);
			ps.setInt(1, this.getCurrentHotel().getHotelId());
			ps.setString(2,  startDate);
			ps.setString(3, endDate);			
			
			ps.executeUpdate();
									
			
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
		}
	
		finally { close(rs, ps, conn);};	
		
		System.out.println("*********** Trend Tool - calculateGroupFinish - END - " + new Date() +" ***********");
		
	}
	
	public void calculateGroupTotals(String startDate, String endDate, int totalRooms, int customerId){
		
		System.out.println("*********** Trend Tool - calculateGroupTotals - BEGIN - " + new Date() +" ***********");
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;		
		
		
		try{	
						
			conn = this.getCurrentHRR3Connection();				
			
			
			String statement = "";
			statement = "update RM3TransientData set tot_occ_pct=(ifnull(gtot_occ,0)+ifnull(tot_occ_rooms,0))/ ? " +
						"where hotel_id= ? and statdate between ? and ? ";
			
			ps = conn.prepareStatement(statement);
			ps.setInt(1, totalRooms);
			ps.setInt(2, this.getCurrentHotel().getHotelId());			
			ps.setString(3,  startDate);
			ps.setString(4, endDate);			
			
			ps.executeUpdate();
			
			
			ps.close();
			
			statement = "";
			statement = "update RM3TransientData set gtot_adr=gtot_rev/gtot_occ,tot_occ_pct=gtot_occ/ ? " +
					    "where hotel_id= ?  and statdate between ? and ? and gtot_occ > 0  ";
			
			ps = conn.prepareStatement(statement);
			ps.setInt(1, totalRooms);
			ps.setInt(2, this.getCurrentHotel().getHotelId());
			ps.setString(3,  startDate);
			ps.setString(4, endDate);			
			
			ps.executeUpdate();
			
			
			ps.close();
			
			statement = "";
			statement = "update RM3TransientData d LEFT JOIN " +
						"(select  hotel_id, statdate, " +
						"sum(ifnull(tot_occ,0)) as tot_occ, " +
						"sum(ifnull(tot_rev,0)) as tot_rev " +
						"from RM3TransientSegmentData " +
						"where statdate between ? and ? and hotel_id= ? " +
						"and segment_id in (select segment_id from RM3Segments where customer_id = ? " +
						"and isTotal=0 and isactive = 1 and type='TM') GROUP BY  hotel_id, statdate) as sd " +
						"ON d.hotel_id = sd.hotel_id " +
						"set d.tot_occ_rooms = ifnull(sd.tot_occ,0), " +
						"d.tot_rev = ifnull(sd.tot_rev,0) " +
						"where d.statdate = sd.statdate AND d.hotel_id = ? and d.statdate between ? and ? "; 
			
			ps = conn.prepareStatement(statement);
			
			ps.setString(1,  startDate);
			ps.setString(2, endDate);		
			ps.setInt(3, this.getCurrentHotel().getHotelId());
			ps.setInt(4, customerId);
			ps.setInt(5, this.getCurrentHotel().getHotelId());
			ps.setString(6,  startDate);
			ps.setString(7, endDate);
			
			
			ps.executeUpdate();
			
			
			ps.close();
			

			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
		}
	
		finally { close(rs, ps, conn);};	
		
		System.out.println("*********** Trend Tool - calculateGroupTotals - END - " + new Date() +" ***********");
		
	}
	
	public void calculateTransient(String stateDate, int totalRooms, int customerId){
		
		System.out.println("*********** Trend Tool - calculateTransient - BEGIN - " + new Date() +" ***********");
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;		
		
		
		try{	
						
			conn = this.getCurrentHRR3Connection();				
			
			
			String statement = "";
			statement = "{call sp_calculate_transient (?,?,?,?)}";
			
			ps = conn.prepareStatement(statement);			
			ps.setInt(1, this.getCurrentHotel().getHotelId());
			ps.setInt(2, totalRooms);
			ps.setString(3,  stateDate);
			ps.setInt(4, customerId);
			
			ps.executeUpdate();											
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
		}
	
		finally { close(rs, ps);};	
		
		System.out.println("*********** Trend Tool - calculateTransient - END - " + new Date() +" ***********");
		
	}
	
}
