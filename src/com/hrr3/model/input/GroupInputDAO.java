package com.hrr3.model.input;

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
import com.hrr3.entity.transients.GroupDataRow;
import com.hrr3.entity.transients.GroupDataTotal;
import com.hrr3.entity.transients.GroupSegmentData;
import com.hrr3.model.HotelDAO;

public class GroupInputDAO extends InputDAO{

	public GroupInputDAO(Hotel currentHotel) {
		super(currentHotel);
		// TODO Auto-generated constructor stub
	}
	
	private void removeInactiveSegmentData(int customerId, String dateFrom, String dateTo){

		System.out.println("*********** removeInactiveSegmentData - BEGIN - " + new Date()+ " ***********");

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;

		try
		{
			conn = this.getCurrentHRR3Connection();

			String statement = "delete from RM3TransientSegmentData where hotel_id=? and statdate" +
					" between ?  and ?  and segment_id in  (select segment_id" +
					" from RM3Segments where isActive=0 and customer_id =?)";

			ps = conn.prepareStatement(statement);
			ps.setInt(1, this.getCurrentHotel().getHotelId());
			ps.setString(2, dateFrom);
			ps.setString(3, dateTo);
			ps.setInt(4, customerId);
			//rs = ps.executeQuery();
			ps.executeUpdate();



		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//return null;
		}

		finally { close(rs, ps); System.out.println("*********** removeInactiveSegmentData - END - " + new Date()+ " ***********");}

	}
	
	public void calcuateGroup (int hotelId, String dateFrom, String dateTo, int customerId){
		
		System.out.println("*********** calcuateGroup - BEGIN - " + new Date()+ " ***********");

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;

		try
		{
			conn = this.getCurrentHRR3Connection();

			String statement = "update RM3TransientData d set d.gtot_occ=0, d.gtot_rev=0, d.gtot_adr=0,d.gdef_occ = 0,d.gdef_rev = 0," +
								"d.gdef_adr = 0,d.gten_occ = 0,d.gten_rev = 0,d.gten_adr = 0 " +
								"WHERE d.hotel_id = ? and d.statdate between ? and ?"; 

			ps = conn.prepareStatement(statement);
			ps.setInt(1, this.getCurrentHotel().getHotelId());
			ps.setString(2, dateFrom);
			ps.setString(3, dateTo);
						
			ps.executeUpdate();
			System.out.println("*********** GroupInputDAO -> calcuateGroup [ Updated Group values (Step 1) ]***********");
			
			ps.close();
			statement = "";
			
			statement = "update RM3TransientData d, (select hotel_id, statdate,	sum(IFNULL(def_occ,0)) as gdef_occ," +
						" sum(IFNULL(def_rev,0)) as gdef_rev, sum(IFNULL(ten_occ,0)) as gten_occ, sum(IFNULL(ten_rev,0)) as gten_rev" +
						" from RM3TransientSegmentData where statdate between ? and ? and hotel_id= ? and segment_id in " +
						" (select segment_id from RM3Segments where customer_id = ? and istotal = 0 and isactive = 1 and type='DP')" +
						" group by hotel_id, statdate) as sd  set" +
						" d.gdef_occ = IFNULL(sd.gdef_occ,0)," +
						" d.gdef_rev = IFNULL(sd.gdef_rev,0)," +
						" d.gten_occ = IFNULL(sd.gten_occ,0)," +
						" d.gten_rev = IFNULL(sd.gten_rev,0) WHERE d.hotel_id = sd.hotel_id and d.statdate = sd.statdate" +
						" AND d.hotel_id = ? and d.statdate between ? and ?"; 
			
			ps = conn.prepareStatement(statement);
			ps.setString(1, dateFrom);
			ps.setString(2, dateTo);
			ps.setInt(3, hotelId);
			ps.setInt(4, customerId);
			ps.setInt(5, hotelId);
			ps.setString(6, dateFrom);
			ps.setString(7, dateTo);
			
			ps.executeUpdate();
			System.out.println("*********** GroupInputDAO -> calcuateGroup [ Updated Group values (Step 2) ]***********");
			
			ps= null;
			statement = "";
			
			statement = "update RM3TransientData set gdef_adr=IFNULL(gdef_rev,0)/case gdef_occ when 0 then 1 else gdef_occ end," +
						" gten_adr=IFNULL(gten_rev,0)/case gten_occ when 0 then 1 else gten_occ end where hotel_id=? and statdate between ? and ?";
			
			ps = conn.prepareStatement(statement);
			ps.setInt(1, hotelId);
			ps.setString(2, dateFrom);
			ps.setString(3, dateTo);
			
			ps.executeUpdate();
			System.out.println("*********** GroupInputDAO -> calcuateGroup [ Updated Group values (Step 3) ]***********");
			
			ps = null;
			statement = "";
			
			// Group Total 
			statement = "update RM3TransientData set gtot_occ=IFNULL(gdef_occ,0)+ IFNULL(gten_occ,0), gtot_rev=IFNULL(gdef_rev,0)+ IFNULL(gten_rev,0)" +
						" where hotel_id=? and statdate between ? and ?";
			
			ps = conn.prepareStatement(statement);
			ps.setInt(1, hotelId);
			ps.setString(2, dateFrom);
			ps.setString(3, dateTo);
			
			ps.executeUpdate();
			System.out.println("*********** GroupInputDAO -> calcuateGroup [ Updated Group values (Step 4) ]***********");
			
			ps = null;
			statement = "";
			
			statement = "update RM3TransientData set gtot_adr=IFNULL(gtot_rev,0)/case gtot_occ when 0 then 1 else gtot_occ end " +
						" where hotel_id=? and statdate between ? and ?";
			
			ps = conn.prepareStatement(statement);
			ps.setInt(1, hotelId);
			ps.setString(2, dateFrom);
			ps.setString(3, dateTo);
			
			ps.executeUpdate();
			System.out.println("*********** GroupInputDAO -> calcuateGroup [ Updated Group values (Step 5) ]***********");
			
			ps = null;
			statement = "";
			
			statement = "update RM3TransientData set gtot_adr=0 where hotel_id=? and statdate between ? and ? and (gtot_occ = 0 or gtot_occ is null)";
			
			ps = conn.prepareStatement(statement);
			ps.setInt(1, hotelId);
			ps.setString(2, dateFrom);
			ps.setString(3, dateTo);
			
			ps.executeUpdate();
			System.out.println("*********** GroupInputDAO -> calcuateGroup [ Updated Group values (Step 6) ]***********");
			
			ps = null;
			statement = "";
			
			// Transient Totals.
			statement = "update RM3TransientSegmentData set tot_occ=IFNULL(def_occ,0)+ IFNULL(ten_occ,0),tot_rev=IFNULL(def_rev,0)+ IFNULL(ten_rev,0)" +
						" where hotel_id=? and statdate between ? and ?";
			
			ps = conn.prepareStatement(statement);
			ps.setInt(1, hotelId);
			ps.setString(2, dateFrom);
			ps.setString(3, dateTo);
			
			ps.executeUpdate();
			System.out.println("*********** GroupInputDAO -> calcuateGroup [ Updated Group values (Step 7) ]***********");
			
			ps = null;
			statement = "";
			
			statement = "update RM3TransientSegmentData set tot_adr=IFNULL(tot_rev,0)/case tot_occ when 0 then 1 else tot_occ end" +
						" where hotel_id=? and statdate between ? and ?";
			
			ps = conn.prepareStatement(statement);
			ps.setInt(1, hotelId);
			ps.setString(2, dateFrom);
			ps.setString(3, dateTo);
			
			ps.executeUpdate();
			System.out.println("*********** GroupInputDAO -> calcuateGroup [ Updated Group values (Step 8) ]***********");
			
			ps = null;
			statement = "";
			
			statement = "update RM3TransientSegmentData set tot_adr=0 where hotel_id=? and statdate between ? and ? and (tot_occ = 0 or tot_occ is null)";
			
			ps = conn.prepareStatement(statement);
			ps.setInt(1, hotelId);
			ps.setString(2, dateFrom);
			ps.setString(3, dateTo);
			
			ps.executeUpdate();
			System.out.println("*********** GroupInputDAO -> calcuateGroup [ Updated Group values (Step 9) ]***********");
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//return null;
		}

		finally { close(rs, ps); System.out.println("removeInactiveSegmentData - END - " + new Date());}
				
	}
	
	public boolean prepareTransientData(int hotelId,  int customerId, String dateFrom, String dateTo) {

		System.out.println("prepareTransientData - BEGIN - " + new Date());

		Connection currentConnection = null;

		try {

			currentConnection = this.getCurrentHRR3Connection();

			//Calculate values for Group
			this.removeInactiveSegmentData(customerId,  dateFrom, dateTo);
			this.calcuateGroup(hotelId, dateFrom, dateTo, customerId);
             
			/*
			this.hasDataForDate(dateFrom, dateTo);
			this.hasDataSegmentForDate(dateFrom, dateTo, customerId, "DP");
            */
			
			this.hasTotalAndSegmenetDataForDate(dateFrom, dateTo, customerId, "TM");

			HotelDAO hotelDao = new HotelDAO();
			int totalRooms = hotelDao.getHotelRooms(hotelId);
			this.calculateStatData(customerId, dateFrom, dateTo, totalRooms);

			return true;

		} catch (Exception e) { return false;}

		finally { close(currentConnection); System.out.println("prepareTransientData - END - " + new Date());}

	}
	
	public List<GroupDataRow> getGroupDataRow(int customerId, String dateFrom, String dateTo,  int numberOfSegments){
		Connection currentConnection = null;

		List<GroupDataRow> groupDataRowList = new ArrayList<GroupDataRow>();

		try {

			currentConnection = this.getCurrentHRR3Connection();
			
			//Get GroupDataTotal List			
			List<GroupDataTotal> gdtList = new ArrayList<GroupDataTotal>();			
			gdtList =  this.getGroupDataTotal(customerId, dateFrom , dateTo);
			
			//Get TransientDataSegment[] List in pieces of numberOfSegments
			List<GroupSegmentData[]> gsdList = new ArrayList<GroupSegmentData[]>();
			gsdList =  this.getGroupSegmentData(dateFrom, dateTo, numberOfSegments);
			
			GroupDataRow groupRow =  new GroupDataRow();
			
			groupRow.setGroupDataSegmentList(getSummaryData(gsdList, numberOfSegments));			
			groupRow.setGroupDataTotal(getSummaryDataTotal(gdtList, dateFrom, dateTo));
			groupRow.setStartDate(dateFrom);
			groupRow.setEndDate(dateTo);
			
			// First element the summary
			groupDataRowList.add(groupRow);
			
			for(int i=0; i < gsdList.size(); i++){
				GroupDataRow gdr =  new GroupDataRow();
				
				gdr.setGroupDataTotal(gdtList.get(i));
				gdr.setGroupDataSegmentList(gsdList.get(i));
				//Add startDate and endDate info USEFUL !! for Render when updating Summary and Total info (first row)
				gdr.setStartDate(dateFrom);
				gdr.setEndDate(dateTo);
				groupDataRowList.add(gdr);				
			}																	
			
		return groupDataRowList;
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		finally { close(currentConnection); }
	}



	private List<GroupDataTotal> getGroupDataTotal(int customerId, String dateFrom, String dateTo){
		

		System.out.println(" *************** GroupDAO -> getGroupDataTotal - BEGIN - " + new Date() + " ***************");

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;

		try{

			conn = this.getCurrentHRR3Connection();
			List<GroupDataTotal> gdtList = new ArrayList<GroupDataTotal>();

			HotelDAO hotel = new HotelDAO();
			Integer totalsRooms = new Integer(hotel.getHotelRooms(this.getCurrentHotel().getHotelId()));
			String statement="";

			

			statement = "select statdate,comments, isException, dow, isActual, tot_occ_pct,gtot_occ,gtot_rev,gtot_adr,gdef_occ, " +
					    "gdef_rev,gdef_adr,gten_occ,gten_rev, gten_adr from RM3TransientData where hotel_id=? and statdate >=? and statdate <= ? " +
					    "order by statdate";						
			
			ps = conn.prepareStatement(statement);
			ps.setInt(1, this.getCurrentHotel().getHotelId());
			ps.setString(2, dateFrom);
			ps.setString(3, dateTo);

			rs = ps.executeQuery();

			if(rs != null ){

				SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

				while(rs.next()) {

					GroupDataTotal totalObj = new GroupDataTotal();
					Date dateObj = rs.getDate("statdate");
					totalObj.setStatdate(dateObj != null ? dateFormat.format(dateObj) : "");
					totalObj.setComments(rs.getString("comments"));
					totalObj.setIsException(rs.getInt("isException"));
					totalObj.setIsActual(rs.getInt("isActual"));
					totalObj.setDow(rs.getString("dow"));
					
					totalObj.setTotOccPct(rs.getBigDecimal("tot_occ_pct"));
					totalObj.setgTotRev(rs.getBigDecimal("gtot_rev").setScale(0,RoundingMode.HALF_EVEN));
					totalObj.setgTotOcc(rs.getInt("gtot_occ"));
					totalObj.setgTotAdr(rs.getBigDecimal("gtot_adr"));
										
					totalObj.setgDefOcc(rs.getInt("gdef_occ"));
					totalObj.setgDefRev(rs.getBigDecimal("gdef_rev").setScale(0, RoundingMode.HALF_EVEN));
					totalObj.setgDefAdr(rs.getBigDecimal("gdef_adr"));
					
					totalObj.setgTenOcc(rs.getInt("gten_occ"));
					totalObj.setgTenRev(rs.getBigDecimal("gten_rev").setScale(0, RoundingMode.HALF_EVEN));
					totalObj.setgTenAdr(rs.getBigDecimal("gten_adr"));

					/*
					if (totalsRooms > 0){
						totalObj.setTotRevPar(totalObj.getTotRev().divide(BigDecimal.valueOf(totalsRooms.doubleValue()), 2, RoundingMode.HALF_EVEN));
						totalObj.setTotOccPct(new BigDecimal(totalObj.getTotOcc().doubleValue() / totalsRooms.doubleValue() * 100).setScale(1, RoundingMode.HALF_EVEN));
					}
					else{
						totalObj.setTotRevPar(new BigDecimal(0 ));
						totalObj.setTotOccPct(new BigDecimal(0));
					}

					if (totalObj.getTotOcc() != 0){
						totalObj.setTotAdr(totalObj.getTotRev().divide(BigDecimal.valueOf(totalObj.getTotOcc().doubleValue()), 2, RoundingMode.HALF_EVEN));
					}else{
						totalObj.setTotAdr(totalObj.getTotRev());
					}*/

					gdtList.add(totalObj);
				}
			}
					
		return gdtList;

		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		finally { close(rs, ps); System.out.println(" *************** GroupDAO -> getGroupDataTotal - END - " + new Date() + " ***************");}
	}
	
	private List<GroupSegmentData[]>  getGroupSegmentData (String dataFrom, String dateTo, int numberOfSegments){

		System.out.println(" *************** GroupDAO ->getTransietSegmentData[] - BEGIN - " + new Date() + " ***************");

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;

		try{
			conn = this.getCurrentHRR3Connection();

			String statement = "select S.statdate,seg.name as segment_name, seg.segment_id, S.tot_occ,S.tot_rev,S.tot_adr," +
							   "S.def_occ,S.def_rev,S.def_adr,S.ten_occ,S.ten_rev,S.ten_adr from RM3TransientData T" +
							   " left join RM3TransientSegmentData  S on T.statdate=S.statdate and T.hotel_id=S.hotel_id" +
							   " left join RM3Segments seg on seg.segment_id = S.segment_id where T.hotel_id= ? and " +
							   " S.statdate >=? and S.statdate <= ? and seg.isActive=1 and seg.type = 'DP' and seg.istotal = 0" +
							   " order by S.statdate ASC, seg.sequence";


			 ps = conn.prepareStatement(statement);
			 ps.setInt(1, this.getCurrentHotel().getHotelId());
			 ps.setString(2, dataFrom);
			 ps.setString(3, dateTo);			 

			 rs = ps.executeQuery();

			 if (rs != null){

				 GroupSegmentData[] groupSegmentArray;
				 List<GroupSegmentData[]> gsdList = new ArrayList<GroupSegmentData[]>();
				 groupSegmentArray = new GroupSegmentData[numberOfSegments];

				 int arrayCont = 0;

				 while(rs.next()){

					 GroupSegmentData gsd = new GroupSegmentData();

					 Date dateObj = rs.getDate("statdate");
					 gsd.setStatdate(dateObj != null ? dateObj.toString() : "");
					 gsd.setSegmentName(rs.getString("segment_name"));
					 gsd.setSegmentId(rs.getInt("segment_id"));
					 
					 gsd.setTotOcc(rs.getInt("tot_occ"));
					 gsd.setTotRev(rs.getBigDecimal("tot_rev").setScale(0, RoundingMode.HALF_EVEN));					 
					 BigDecimal totAdr = rs.getBigDecimal("tot_adr").setScale(2, RoundingMode.HALF_EVEN);					 
					 gsd.setTotAdr(displayADR(gsd.getTotOcc(),totAdr , gsd.getTotRev()));
					 
					 
					 gsd.setDefOcc(rs.getInt("def_occ"));
					 gsd.setDefRev(rs.getBigDecimal("def_rev").setScale(0, RoundingMode.HALF_EVEN));										 
					 BigDecimal defAdr = rs.getBigDecimal("def_adr").setScale(2, RoundingMode.HALF_EVEN);									
					 gsd.setDefAdr(displayADR(gsd.getDefOcc(), defAdr, gsd.getDefRev()));
					 
					 gsd.setTenOcc(rs.getInt("ten_occ"));
					 gsd.setTenRev(rs.getBigDecimal("ten_rev").setScale(0, RoundingMode.HALF_EVEN));
					 BigDecimal tenAdr = rs.getBigDecimal("ten_adr").setScale(2, RoundingMode.HALF_EVEN);
					 gsd.setTenAdr(displayADR(gsd.getTenOcc(), tenAdr, gsd.getTenRev()));							 

					 groupSegmentArray[arrayCont] = gsd;

					 arrayCont++;

					 if(arrayCont % numberOfSegments == 0) {
						 gsdList.add(groupSegmentArray);//Save a set of N. segments into the list
						 groupSegmentArray = new GroupSegmentData[numberOfSegments];//Create a new set of segments
						 arrayCont = 0;//Reset counter
					 }
				 }

				 return gsdList;
			 }

			 return null;


		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		finally { close(rs, ps); System.out.println(" *************** GroupDAO ->getTransietSegmentData[] - END - " + new Date()+ " ***************");}
	}
	
	
	private GroupSegmentData[] getSummaryData(List<GroupSegmentData[]> gsdList, int numberOfSegments){

		int countSeg = 0;
		GroupSegmentData[] tsdSummary  = new GroupSegmentData[numberOfSegments];

		while (countSeg < numberOfSegments)
		{
			BigDecimal sumTenRev = new BigDecimal(0);
			Integer sumTenOcc = 0;
			
			BigDecimal sumDefRev = new BigDecimal(0);
			Integer sumDefOcc = 0;


			for(int i=0; i < gsdList.size(); i++){
				GroupSegmentData[] gsdArray;
				gsdArray =  gsdList.get(i);
				GroupSegmentData  gsd = new GroupSegmentData();

				gsd = gsdArray[countSeg];

				sumTenRev = sumTenRev.add(gsd.getTenRev());
				sumTenOcc = sumTenOcc + gsd.getTenOcc();
				
				sumDefRev = sumDefRev.add(gsd.getDefRev());
				sumDefOcc = sumDefOcc + gsd.getDefOcc();

				}
			GroupSegmentData tgsd = new GroupSegmentData();
		    tgsd.setDefRev(sumDefRev);
		    tgsd.setDefOcc(sumDefOcc);
		    
		    tgsd.setTenRev(sumTenRev);
		    tgsd.setTenOcc(sumTenOcc);

		    if (sumTenOcc != 0){
		    	tgsd.setTenAdr(sumTenRev.divide(BigDecimal.valueOf(sumTenOcc.doubleValue()),2, RoundingMode.HALF_EVEN));
		    }else{
		    	tgsd.setTenAdr(sumTenRev);
		    }

		    if (sumDefOcc != 0){
		    	tgsd.setDefAdr(sumDefRev.divide(BigDecimal.valueOf(sumDefOcc.doubleValue()),2, RoundingMode.HALF_EVEN));
		    }
		    
		    //Totals
		    BigDecimal totalSumTenRev = tgsd.getTenRev().add(tgsd.getDefRev());
	    	Integer totalSumOcc = tgsd.getTenOcc() + tgsd.getDefOcc();
	    	
		    if (totalSumOcc!= 0 ){		    		    	
		    	tgsd.setTotAdr(totalSumTenRev.divide(BigDecimal.valueOf(totalSumOcc.doubleValue()), 2, RoundingMode.HALF_EVEN));		    	
		    }
		    else {
		    	tgsd.setTotAdr(totalSumTenRev);
		    }
		    
		    tgsd.setTotOcc(totalSumOcc);
		    
		    tsdSummary[countSeg] = tgsd;
			countSeg ++;

		}

		return tsdSummary;
	}
	
	
	private GroupDataTotal getSummaryDataTotal(List<GroupDataTotal> totals, String dateFrom, String dateTo){
		BigDecimal sumTotTenRev = new BigDecimal(0);
		Integer sumTotTenOcc = 0;
		
		BigDecimal sumTotDefRev = new BigDecimal(0);
		Integer sumTotDefOcc = 0;
		
		BigDecimal sumTotRev = new BigDecimal(0);
		Integer sumTotOcc = 0;
		
		Integer totAvailRooms;

		HotelDAO hotelDao = new HotelDAO();
		GroupDataTotal gdSummary = new GroupDataTotal();
		totAvailRooms = hotelDao.getHotelRooms(this.getCurrentHotel().getHotelId()) * totals.size();

		for(GroupDataTotal gdt: totals){
			sumTotTenRev = sumTotTenRev.add(gdt.getgTenRev());
			sumTotTenOcc = sumTotTenOcc + gdt.getgTenOcc();
			
			sumTotDefRev = sumTotDefRev.add(gdt.getgDefRev());
			sumTotDefOcc = sumTotDefOcc + gdt.getgDefOcc();
			
			//sumTotRev = sumTotRev.add(gdt.getgTotRev());
			//sumTotOcc = sumTotOcc + gdt.getgTotOcc();
		}

		if (sumTotTenOcc != 0){
			gdSummary.setgTenAdr(sumTotTenRev.divide(BigDecimal.valueOf(sumTotTenOcc.doubleValue()),2, RoundingMode.HALF_EVEN));
		}
		else{
			gdSummary.setgTenAdr(sumTotTenRev);
		}
		
		if (sumTotDefOcc != 0){
			gdSummary.setgDefAdr(sumTotDefRev.divide(BigDecimal.valueOf(sumTotDefOcc.doubleValue()),2, RoundingMode.HALF_EVEN));
		}
		else{
			gdSummary.setgDefAdr(sumTotDefRev);
		}				
		
		sumTotRev = sumTotTenRev.add(sumTotDefRev);
		sumTotOcc = sumTotTenOcc + sumTotDefOcc;
		if (sumTotOcc  != 0){
			gdSummary.setgTotAdr(sumTotRev.divide(BigDecimal.valueOf(sumTotOcc.doubleValue()),2, RoundingMode.HALF_EVEN));
		}
		else{
			gdSummary.setgTotAdr(sumTotRev);			
		}

		gdSummary.setTotOccPct(sumTotRev.divide(BigDecimal.valueOf(totAvailRooms.doubleValue()), 2, RoundingMode.HALF_EVEN));
		gdSummary.setgTotRev(sumTotRev.setScale(0, RoundingMode.HALF_EVEN));
		gdSummary.setgTotOcc(sumTotOcc);
		
		gdSummary.setgDefOcc(sumTotDefOcc);
		gdSummary.setgTenOcc(sumTotTenOcc);
		
		gdSummary.setgDefRev(sumTotDefRev.setScale(0, RoundingMode.HALF_EVEN));
		gdSummary.setgTenRev(sumTotTenRev.setScale(0, RoundingMode.HALF_EVEN));

		gdSummary.setComments("Summary Information");
		
		gdSummary.setTotOccPct(getTotOccPct(dateFrom, dateTo, totAvailRooms ));

		return gdSummary;

	}


	public boolean prepareGroupData(int hotelId,  int customerId, String dateFrom, String dateTo) {

		System.out.println(" *************** GroupDAO ->prepareGroupData - BEGIN - " + new Date());

		Connection currentConnection = null;

		try {

			currentConnection = this.getCurrentHRR3Connection();

			//Calculate values for Group
			this.removeInactiveSegmentData(customerId,  dateFrom, dateTo);
			this.calcuateGroup(hotelId, dateFrom, dateTo, customerId);

			/*this.hasDataForDate(dateFrom, dateTo);
			this.hasDataSegmentForDate(dateFrom, dateTo, customerId, "DP");
			*/
			
			this.hasTotalAndSegmenetDataForDate(dateFrom, dateTo, customerId, "DP");
			
			HotelDAO hotelDao = new HotelDAO();
			int totalRooms = hotelDao.getHotelRooms(hotelId);
			this.calculateStatData(customerId, dateFrom, dateTo, totalRooms);

			return true;

		} catch (Exception e) { return false;}

		finally { close(currentConnection); System.out.println(" *************** GroupDAO ->prepareGroupData - END - " + new Date()+ " ***************");}

	}
	
	public static void main(String arg[]){
		Hotel hotel = new Hotel();
		hotel.setHotelId(41);
		GroupInputDAO gid = new GroupInputDAO(hotel); 
		
		//gid.prepareGroupData(41, 1, "2013-01-01", "2013-01-05");
		
		gid.UpdateGroupSegmentData(1, 147, "2013-01-01", new BigDecimal(3),new BigDecimal(12),new BigDecimal(2),new BigDecimal(10));
	}
	
	private BigDecimal getTotOccPct(String dateFrom, String dateTo,int totAvailRooms){
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		BigDecimal totOccPctValue = new BigDecimal(0);

		try
		{
			conn = this.getCurrentHRR3Connection();
			String statement = "select ifnull(sum(ifnull(tot_occ_rooms,0)),0) + ifnull(sum(ifnull(gtot_occ,0)),0) as totRooms" +
					" from RM3TransientData where hotel_id= ? and statdate>= ? and statdate<= ?";

			ps = conn.prepareStatement(statement);
			ps.setInt(1, this.getCurrentHotel().getHotelId());
			ps.setString(2, dateFrom);
			ps.setString(3, dateTo);

			rs = ps.executeQuery();
			
			int totalRooms = 0;
			
			if(rs != null ){
				
				if(rs.next()){
					totalRooms = rs.getInt("totRooms");
				}
			}
			
			/*
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				
			Date dDateFrom = dateFormat.parse(dateFrom);
			Date dDateTo = dateFormat.parse(dateTo);
						
			
			Calendar ci = Calendar.getInstance();
						
	        ci.setTime(dDateFrom);
	        ci.add(Calendar.DATE, -1);
	        
	        Calendar cf = Calendar.getInstance();
	        cf.setTime(dDateTo);

	        long ntime = cf.getTimeInMillis() - ci.getTimeInMillis();

	        int iNumDays =  (int)Math.ceil((double)ntime / 1000 / 3600 / 24);	        	        
	        
	        double mult = iNumDays * numHotelRooms;
	        
	        */
	        
	        double div =  totalRooms / (double)totAvailRooms;
	        
	        double calc = div * 100;
	        
	        totOccPctValue = new BigDecimal(calc);
	        						
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//return null;
		}
		finally { close(rs, ps);}
		
		return totOccPctValue;
		
	}	
	
	/**
	 * Update Tentative OCC, Tentative ADR, Definitive OCC, Definite ADR
	 * @param segmentId, state, TOCC, TADR, DOCC, DADR
	 * @return 0 if the database could not get updated,  1 if update success
	 */
	public int UpdateGroupSegmentData(int customerId,int segmentId, String statedate, BigDecimal TOCC, BigDecimal TADR, BigDecimal DOCC, BigDecimal DADR){
		
		System.out.println("*********** UpdateGroupSegmentData (InlineEdition) - BEGIN - " + new Date() +" ***********");

		
		BigDecimal TotOCC = new BigDecimal(0);
		BigDecimal TotREV = new BigDecimal(0);
		BigDecimal TotADR = new BigDecimal(0);
		BigDecimal TREV = new BigDecimal(0);
		BigDecimal DREV = new BigDecimal(0);
		
		TREV = TOCC.multiply(TADR);
		DREV = DOCC.multiply(DADR);
		
		TotOCC = DOCC.add(TOCC);
		TotREV = DREV.add(TREV);
		
		if (TotOCC.intValue() == 0) {
			TotADR = TotREV;
		}else{
			TotADR = TotREV.divide(TotOCC, 2, RoundingMode.HALF_EVEN);
		}
		
		
		InputDAO input = new InputDAO(this.getCurrentHotel());		
		
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try{			
			conn = this.getCurrentHRR3Connection();	
			
			/*
			p_hotelid int,
			p_statdate smalldatetime,
			p_gsegmentid int,
			p_totocc,
			p_totrev,
			p_totadr,
			p_defocc,
			p_defrev,
			p_defadr,
			p_tenocc,
			p_tenrev,
			p_tenadr			  			  
			 */
			String statement = ("{call ftUpdateGroupSegmentData (?,?,?,?,?,?,?,?,?,?,?,?)}");
			
			ps = conn.prepareStatement(statement);
			ps.setInt(1, this.getCurrentHotel().getHotelId());
			ps.setString(2,  statedate);
			ps.setInt(3, segmentId);
			ps.setBigDecimal(4, TotOCC);
			ps.setBigDecimal(5, TotREV);
			ps.setBigDecimal(6, TotADR);
			ps.setBigDecimal(7, DOCC);
			ps.setBigDecimal(8, DREV);
			ps.setBigDecimal(9, DADR);
			ps.setBigDecimal(10, TOCC);
			ps.setBigDecimal(11, TREV);
			ps.setBigDecimal(12, TADR);
			
			
			rs = ps.executeQuery();
														
			
			if(rs != null ){			
				while(rs.next()) {
					if (rs.getInt("p_out") == 1){						
						HotelDAO hotelDao = new HotelDAO();
						int totalRooms = hotelDao.getHotelRooms(this.getCurrentHotel().getHotelId());
						input.calculateStatData(customerId, statedate, statedate, totalRooms);
						return 1;
					}
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
	       System.out.println(" *********** UpdateGroupSegmentData (InlineEdition) - " + new Date()+ " ***********");
		}
				
		return 0;
	}
	
	/**
	 * Update Comment,Exception
	 * @param stateDate, Comment, Exception
	 * @return 0 if the database could not get updated,  1 if update success
	 */
	
	public int UpdateGroupData (String statedate, String comment, int exception){
		
		System.out.println("*********** UpdateGroupData (Inline Edition) - BEGIN - " + new Date() +" ***********");
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try{			
			conn = this.getCurrentHRR3Connection();	
			
			String statement = ("{call ftUpdateTransientData (?,?,?,?)}");
			
			ps = conn.prepareStatement(statement);
			ps.setInt(1, this.getCurrentHotel().getHotelId());
			ps.setString(2,  statedate);
			ps.setString(3, comment);
			ps.setInt(4, exception);
			
			
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
	       System.out.println(" *********** UpdateGroupData (Inline Edition) - " + new Date()+ " ***********");
		}
				
		return 0;
	}

}
