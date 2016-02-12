package com.hrr3.model.input;

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
import com.hrr3.entity.transients.TransientDataRow;
import com.hrr3.entity.transients.TransientDataTotal;
import com.hrr3.entity.transients.TransientDataTotals;
import com.hrr3.entity.transients.TransientSegmentData;
import com.hrr3.model.HotelDAO;

public class TransientInputDAO extends InputDAO {


	public TransientInputDAO(Hotel currentHotel) {
		super(currentHotel);
		// TODO Auto-generated constructor stub
	}			
	

	public List<TransientDataRow> getTransientDataRow(int customerId, String dateFrom, String dateTo, int classId, int totalType, int numberOfSegments){

		System.out.println("getTransientDataRow - BEGIN - " + new Date());
		
		Connection currentConnection = null;

		List<TransientDataRow> transientDataRowList = new ArrayList<TransientDataRow>();

		try {

			currentConnection = this.getCurrentHRR3Connection();

			//Total Object creation, representing Contract, Misc, Group or Transient Total

				//Get TransientDataTotal List
				List<TransientDataTotal> tdtMisc = new ArrayList<TransientDataTotal>();
				List<TransientDataTotal> tdtContract = new ArrayList<TransientDataTotal>();
				List<TransientDataTotal> tdtTransient = new ArrayList<TransientDataTotal>();
				List<TransientDataTotal> tdtGroup = new ArrayList<TransientDataTotal>();
				List<TransientDataTotal> tdtHotel = new ArrayList<TransientDataTotal>();
				List<TransientDataTotal> tdtPaid = new ArrayList<TransientDataTotal>();

				//TODO Set ClassId for each total type and call function to fill them out
				tdtMisc =  this.getTransientDataTotal( customerId, dateFrom , dateTo, 3, totalType);
				tdtContract =  this.getTransientDataTotal( customerId, dateFrom , dateTo, 2, totalType);
				tdtTransient =  this.getTransientDataTotal(customerId, dateFrom , dateTo, 1, totalType);
				tdtGroup =  this.getTransientDataTotal(customerId, dateFrom , dateTo, 0, 3);
				//Totals calculated
				tdtHotel = this.sumTotalHotel(tdtTransient, tdtContract,tdtMisc, tdtGroup);
				tdtPaid = this.sumPaidRooms(tdtTransient, tdtContract,tdtGroup);

				//Get TransientDataSegment[] List in pieces of numberOfSegments
				List<TransientSegmentData[]> tsdList = new ArrayList<TransientSegmentData[]>();
				tsdList =  this.getTransietSegmentData(classId, dateFrom, dateTo, numberOfSegments);

				//get Summary data
				TransientDataRow tdrSummary = new TransientDataRow(classId, totalType);
				//TransientSegmentData[] tsdSummary = getSummaryData(tsdList, numberOfSegments);
				tdrSummary.setTransientDataSegmentList(getSummaryData(tsdList, numberOfSegments));

				TransientDataTotals totalsSummary = new TransientDataTotals();
				//Create all summary objects
				TransientDataTotal hotelTotal = new TransientDataTotal();
				TransientDataTotal paidTotal = new TransientDataTotal();
				TransientDataTotal groupTotal = new TransientDataTotal();
				TransientDataTotal contractTotal = new TransientDataTotal();
				TransientDataTotal transientTotal = new TransientDataTotal();
				TransientDataTotal miscTotal = new TransientDataTotal();
				//Get all summaries per totaltype
				hotelTotal = getSummaryDataTotal(tdtHotel);
				paidTotal = getSummaryDataTotal(tdtPaid);
				groupTotal = getSummaryDataTotal(tdtGroup);
				contractTotal = getSummaryDataTotal(tdtContract);
				transientTotal = getSummaryDataTotal(tdtTransient);
				miscTotal = getSummaryDataTotal(tdtMisc);
				//Set all summaries in the TransientTotals container
				totalsSummary.setHotelTotal(hotelTotal);
				totalsSummary.setPaidRooms(paidTotal);
				totalsSummary.setGroupTotal(groupTotal);
				totalsSummary.setContractTotal(contractTotal);
				totalsSummary.setTransientTotal(transientTotal);
				totalsSummary.setMiscTotal(miscTotal);
				//Set TransientTotals containet in the first Row
				tdrSummary.setTransientDataTotals(totalsSummary);
				//Add startDate and endDate info USEFUL !! for Render when updating Summary and Total info (first row)
				tdrSummary.setStartDate(dateFrom);
				tdrSummary.setEndDate(dateTo);
				//Set first row for summary
				transientDataRowList.add(tdrSummary);
				//We need a validation here to verify if the 4 total types and the list of segments contains same size

				for(int i=0; i < tsdList.size(); i++){
					//Create 1 row
					TransientDataRow tdr = new TransientDataRow(classId, totalType);
					//TransientDataTotals container
					TransientDataTotals totals = new TransientDataTotals();
					//Set the 4 Total Types inside Totals object
					totals.setContractTotal(tdtContract.get(i));
					totals.setMiscTotal(tdtMisc.get(i));
					totals.setTransientTotal(tdtTransient.get(i));
					totals.setGroupTotal(tdtGroup.get(i));
					totals.setHotelTotal(tdtHotel.get(i));
					totals.setPaidRooms(tdtPaid.get(i));

					//Set TransientDataTotals in the row
					tdr.setTransientDataTotals(totals);
					//Set DataSegmentList[] in the row
					tdr.setTransientDataSegmentList(tsdList.get(i));
					//Add startDate and endDate info USEFUL !! for Render when updating Summary and Total info (first row)
					tdr.setStartDate(dateFrom);
					tdr.setEndDate(dateTo);
					//Add row to the rowList
					transientDataRowList.add(tdr);
				}



			return transientDataRowList;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		finally { close(currentConnection); System.out.println("getTransientDataRow - END - " + new Date()); }
	}

	public boolean prepareTransientData(int hotelId,  int customerId, String dateFrom, String dateTo) {

		System.out.println("prepareTransientData - BEGIN - " + new Date());

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;

		try {
			
			//HotelDAO hotelDao = new HotelDAO();
			//int totalRooms = hotelDao.getHotelRooms(hotelId);

			conn = this.getCurrentHRR3Connection();

			String statement = "{call ftPrepareTransientData (?,?,?,?,?,?)} ";

			ps = conn.prepareStatement(statement);
			ps.setInt(1, this.getCurrentHotel().getHotelId());
			ps.setString(2, dateFrom);
			ps.setString(3, dateTo);
			ps.setInt(4, customerId);
			ps.setString(5, "TM");
			ps.setInt(6, this.getCurrentHotel().getTotalRooms());
			
			System.out.print("Calling params .....");
			System.out.print(this.getCurrentHotel().getHotelId());
			System.out.print(dateFrom);
			System.out.print(dateTo);
			System.out.print(customerId);
			System.out.print("TM");
			System.out.print(this.getCurrentHotel().getTotalRooms());
			
			
			rs = ps.executeQuery();
			int result = rs.getInt("p_out");
			
			System.out.print("p_out Result is "+result);
			
			if(rs != null ){			
				while(rs.next()) {
					if ( result == 1){						
						
						return true;
					}
					else
						return false;
				}
			}
			
			/*
			this.hasDataSegmentForDate(dateFrom, dateTo, customerId, "TM");
			this.hasDataForDate(dateFrom, dateTo);						
			this.hasTotalAndSegmenetDataForDate(dateFrom, dateTo, customerId, "TM");
			
			this.calculateStatData(customerId, dateFrom, dateTo, totalRooms);
			*/
					
			return true;

		}catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("SQL Error transiendInputDAO"+e.getMessage());
			e.printStackTrace();
			return false;
		}

		finally { close(rs, ps, conn); System.out.println("prepareTransientData - END - " + new Date());}

	}

	public List<TransientDataTotal> getTransientDataTotal(int customerId, String dateFrom, String dateTo, int classId, int totalType){
		/*
		 *  <radio "Total Hotel " value="1"/>
			<radio "Total Paid Rooms" value="2"/>
			<radio "Total Group " value="3"/>
			<radio "Total Contract " value="4"/>
			<radio "Total Transient " value="5"/>
			<radio "Total Misc " value="6"/>
			<comboitem label="Transient" value="1" />
			<comboitem label="Contract" value="2" />
			<comboitem label="MISC" value="3" />
			<comboitem label="O/B" value="4"/>
		 */

		System.out.println("getTransientDataTotal - BEGIN - " + new Date());

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;

		try{

			conn = this.getCurrentHRR3Connection();
			List<TransientDataTotal> tdtList = new ArrayList<TransientDataTotal>();

			//HotelDAO hotel = new HotelDAO();
			//new Integer(hotel.getHotelRooms(this.getCurrentHotel().getHotelId()));
			Integer totalsRooms = this.getCurrentHotel().getTotalRooms(); 
			String statement="";

			if (classId == 0 & totalType == 3){

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

						TransientDataTotal totalObj = new TransientDataTotal();
						Date dateObj = rs.getDate("statdate");
						totalObj.setStatdate(dateObj != null ? dateFormat.format(dateObj) : "");
						totalObj.setComments(rs.getString("comments"));
						totalObj.setIsException(rs.getInt("isException"));
						totalObj.setIsActual(rs.getInt("isActual"));
						totalObj.setDow(rs.getString("dow"));
						totalObj.setTotRev(rs.getBigDecimal("gtot_rev").setScale(0,RoundingMode.HALF_EVEN));
						totalObj.setTotOcc(rs.getInt("gtot_occ"));
						totalObj.setTotOccRooms(rs.getInt("gtot_occ"));

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
						}

						tdtList.add(totalObj);
					}
				}

			}
			else{
					statement = "select t.statdate,comments, isException, dow, isActual, tot_occ_pct,tot_adr,tot_rev_par,tot_occ_rooms," +
										"IFNULL(s.tot_rev,0) as tot_rev, IFNULL(s.tot_occ,0) as tot_occ from RM3TransientData t " +
										"left join " +
										            "(select hotel_id, statdate, sum(tot_rev) as tot_rev, sum(tot_occ) As tot_occ From RM3TransientSegmentData " +
										            "Where  segment_id in (select segment_id from RM3Segments where customer_id=? and type= 'TM' and  " +
										            "istotal = '0' and isactive=1 and class_id = ? and includetot = 1) " +
										            " and hotel_id = ? and statdate >=? and statdate <= ? group by statdate, hotel_id ) as s " +
										"on t.hotel_id = s.hotel_id " +
										"and t.statdate = s.statdate where t.hotel_id=? and t.statdate >=? and t.statdate <= ? order by t.statdate";

					ps = conn.prepareStatement(statement);
					ps.setInt(1, customerId);
					ps.setInt(2, classId);				
					ps.setInt(3, this.getCurrentHotel().getHotelId());
					ps.setString(4, dateFrom);
					ps.setString(5, dateTo);
					ps.setInt(6, this.getCurrentHotel().getHotelId());
					ps.setString(7, dateFrom);
					ps.setString(8, dateTo);

					rs = ps.executeQuery();

					if(rs != null ){

						SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

						while(rs.next()) {


							TransientDataTotal totalObj = new TransientDataTotal();

							Date dateObj = rs.getDate("statdate");
							totalObj.setStatdate(dateObj != null ? dateFormat.format(dateObj) : "");
							totalObj.setComments(rs.getString("comments"));
							totalObj.setIsException(rs.getInt("isException"));
							totalObj.setDow(rs.getString("dow"));
							totalObj.setIsActual(rs.getInt("isActual"));
							totalObj.setTotOccPct(rs.getBigDecimal("tot_occ_pct"));
							totalObj.setTotAdr(rs.getBigDecimal("tot_adr"));
							totalObj.setTotRevPar(rs.getBigDecimal("tot_rev_par"));
							totalObj.setTotOccRooms(new Integer(rs.getInt("tot_occ_rooms")));
							BigDecimal totRev = rs.getBigDecimal("tot_rev");
							totRev.setScale(0, RoundingMode.HALF_EVEN);
							totalObj.setTotRev(totRev);
							totalObj.setTotOcc(new Integer(rs.getInt("tot_occ")));

							totalObj = sumTotalsByInputType(totalObj, totalsRooms);
							//Add TransientDataTotals to the list
							tdtList.add(totalObj);
						}
					}

			}// else

				return tdtList;

		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		finally { close(rs, ps); System.out.println("getTransientDataTotal - END - " + new Date());}

	}

	private TransientDataTotal sumTotalsByInputType (TransientDataTotal inputData, Integer totalRooms){
			BigDecimal totalRev = inputData.getTotRev();
			Integer totalOcc = inputData.getTotOcc();

			if (totalRooms > 0){
				inputData.setTotRevPar(totalRev.divide(BigDecimal.valueOf(totalRooms.doubleValue()), 2, RoundingMode.HALF_EVEN));
				double div = totalOcc.doubleValue() / totalRooms.doubleValue();
				double totOccPct = div * 100;
				inputData.setTotOccPct(new BigDecimal(totOccPct).setScale(1, RoundingMode.HALF_EVEN));
			}
			else{
				inputData.setTotRevPar(new BigDecimal(0));
				inputData.setTotOccPct(new BigDecimal(0));
			}

			if (inputData.getTotOcc() != 0){
				inputData.setTotAdr(totalRev.divide(BigDecimal.valueOf(totalOcc.doubleValue()),2, RoundingMode.HALF_EVEN));
			}
			else{
				inputData.setTotAdr(totalRev.setScale(2, RoundingMode.HALF_EVEN));
			}

			inputData.setTotRev(totalRev.setScale(0, RoundingMode.HALF_EVEN));
			//TotalOCCRoom has the data of transient if we need another classId  TotOcc has the correct value
			inputData.setTotOccRooms(inputData.getTotOcc());

		return inputData;

	}

	private List<TransientSegmentData[]>  getTransietSegmentData (int classId, String dataFrom, String dateTo, int numberOfSegments){

		System.out.println("getTransietSegmentData[] - BEGIN - " + new Date());

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;

		try{
			conn = this.getCurrentHRR3Connection();
			

			String statement = "select S.statdate,Seg.name as segment_name, Seg.segment_id, S.def_occ,S.def_rev,S.def_adr from RM3TransientSegmentData S " +
			" left join RM3Segments Seg ON Seg.segment_id = S.segment_id where S.hotel_id=? and S.statdate >= ?" +
			" and S.statdate <= ? and Seg.isactive=1 and Seg.type= 'TM' and  Seg.istotal = 0 and class_id = ? "   +
			" order by  S.statdate ASC, Seg.sequence ";


			 ps = conn.prepareStatement(statement);
			 ps.setInt(1, this.getCurrentHotel().getHotelId());
			 ps.setString(2, dataFrom);
			 ps.setString(3, dateTo);
			 ps.setInt(4, classId);

			 rs = ps.executeQuery();

			 if (rs != null){

				 TransientSegmentData[] transientSegmentArray;
				 List<TransientSegmentData[]> tsdList = new ArrayList<TransientSegmentData[]>();
				 transientSegmentArray = new TransientSegmentData[numberOfSegments];

				 int arrayCont = 0;

				 while(rs.next()){

					 TransientSegmentData tsd = new TransientSegmentData();

					 Date dateObj = rs.getDate("statdate");
					 tsd.setStatdate(dateObj != null ? dateObj.toString() : "");
					 tsd.setSegmentName(rs.getString("segment_name"));
					 tsd.setSegmentId(rs.getInt("segment_id"));
					 tsd.setDefOcc(rs.getInt("def_occ"));
					 BigDecimal defAdr = rs.getBigDecimal("def_adr");
					 defAdr.setScale(2, RoundingMode.HALF_EVEN);
					 tsd.setDefAdr(defAdr);
					 BigDecimal defRev = rs.getBigDecimal("def_rev");
					 defRev.setScale(2, RoundingMode.HALF_EVEN);
					 tsd.setDefRev(defRev);

					 tsd.setDefAdr(displayADR(tsd.getDefOcc(), tsd.getDefAdr(), tsd.getDefRev()));

					 transientSegmentArray[arrayCont] = tsd;

					 arrayCont++;

					 if(arrayCont % numberOfSegments == 0) {
						 tsdList.add(transientSegmentArray);//Save a set of N. segments into the list
						 transientSegmentArray = new TransientSegmentData[numberOfSegments];//Create a new set of segments
						 arrayCont = 0;//Reset counter
					 }

				 }

				 return tsdList;
			 }

			 return null;


		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		finally { close(rs, ps); System.out.println("getTransietSegmentData[] - END - " + new Date());}

	}

	

	
	private List<TransientDataTotal> sumTotalHotel(List<TransientDataTotal> Trans, List<TransientDataTotal> contract, List<TransientDataTotal> misc, List<TransientDataTotal> group){

		List<TransientDataTotal> thList = new ArrayList<TransientDataTotal>();

		//HotelDAO hotel = new HotelDAO();
		//hotel.getHotelRooms(this.getCurrentHotel().getHotelId());
		
		Integer totalRooms = this.getCurrentHotel().getTotalRooms(); 

		for(int i=0; i< Trans.size(); i++) //all list should have the same size
		{
			TransientDataTotal th = new TransientDataTotal();


			BigDecimal rev = Trans.get(i).getTotRev().add(contract.get(i).getTotRev()).add(misc.get(i).getTotRev()).add(group.get(i).getTotRev());
			Integer occ = Trans.get(i).getTotOcc() + contract.get(i).getTotOcc() + misc.get(i).getTotOcc() +  group.get(i).getTotOcc();

			th.setTotRev(rev.setScale(0, RoundingMode.HALF_EVEN));
			th.setTotOcc(occ);
			th.setTotOccRooms(occ);

			//All lists should have the same values for the following attributes
			//We can take any list to fill out the new object
			th.setComments(Trans.get(i).getComments());
			th.setDow(Trans.get(i).getDow());
			th.setStatdate(Trans.get(i).getStatdate());
			th.setIsActual(Trans.get(i).getIsActual());

			if (totalRooms > 0){
				th.setTotRevPar(th.getTotRev().divide(BigDecimal.valueOf(totalRooms.doubleValue()), 2, RoundingMode.HALF_EVEN));
				BigDecimal totOccPct = new BigDecimal(th.getTotOcc().doubleValue() / totalRooms.doubleValue() * 100);
				th.setTotOccPct(totOccPct.setScale(1, RoundingMode.HALF_EVEN));
			}
			else{
				th.setTotRevPar(new BigDecimal(0));
				th.setTotOccPct(new BigDecimal(0));
			}

			if(th.getTotOcc() != 0){
				th.setTotAdr(th.getTotRev().divide(BigDecimal.valueOf(th.getTotOcc().doubleValue()), 2, RoundingMode.HALF_EVEN));
			}else{
				th.setTotAdr(th.getTotRev());
			}
			thList.add(th);
		}

		return thList;
	}

	private List<TransientDataTotal> sumPaidRooms(List<TransientDataTotal> Trans, List<TransientDataTotal> contract, List<TransientDataTotal> group){
		List<TransientDataTotal> tpList = new ArrayList<TransientDataTotal>();

		//HotelDAO hotel = new HotelDAO();
		//hotel.getHotelRooms(this.getCurrentHotel().getHotelId());
		Integer totalRooms = this.getCurrentHotel().getTotalRooms();

		for(int i=0; i< Trans.size(); i++) //all list should have the same size
		{
			TransientDataTotal th = new TransientDataTotal();

			BigDecimal rev = Trans.get(i).getTotRev().add(contract.get(i).getTotRev()).add(group.get(i).getTotRev());
			Integer occ = Trans.get(i).getTotOcc() + contract.get(i).getTotOcc()+  group.get(i).getTotOcc();

			th.setTotRev(rev);
			th.setTotOcc(occ);
			th.setTotOccRooms(occ);
			//All lists should have the same values for the following attributes
			//We can take any list to fill out the new object
			th.setComments(Trans.get(i).getComments());
			th.setDow(Trans.get(i).getDow());
			th.setStatdate(Trans.get(i).getStatdate());
			th.setIsActual(Trans.get(i).getIsActual());

			if (totalRooms > 0){
				th.setTotRevPar(th.getTotRev().divide(BigDecimal.valueOf(totalRooms.doubleValue()), 2, RoundingMode.HALF_EVEN));
				th.setTotOccPct(new BigDecimal(th.getTotOcc().doubleValue() / totalRooms.doubleValue() * 100).setScale(1, RoundingMode.HALF_EVEN));
			}
			else{
				th.setTotRevPar(new BigDecimal(0));
				th.setTotOccPct(new BigDecimal(0));
			}

			if(th.getTotOcc() != 0){
				th.setTotAdr(th.getTotRev().divide(BigDecimal.valueOf(th.getTotOcc().doubleValue()),2,RoundingMode.HALF_EVEN));
			}else{
				th.setTotAdr(th.getTotRev());
			}
			tpList.add(th);
		}

		return tpList;
	}

	private TransientSegmentData[] getSummaryData(List<TransientSegmentData[]> tsdList, int numberOfSegments){

		int countSeg = 0;
		TransientSegmentData[] tsdSummary  = new TransientSegmentData[numberOfSegments];

		while (countSeg < numberOfSegments)
		{
			BigDecimal sumRev = new BigDecimal(0);
			Integer sumOcc = 0;


			for(int i=0; i < tsdList.size(); i++){
				TransientSegmentData[] tsdArray;
				tsdArray =  tsdList.get(i);
				TransientSegmentData  tsd = new TransientSegmentData();

				tsd = tsdArray[countSeg];

				sumRev = sumRev.add(tsd.getDefRev());
				sumOcc = sumOcc + tsd.getDefOcc();

				}
		    TransientSegmentData ttsd = new TransientSegmentData();
		    ttsd.setDefRev(sumRev);
		    ttsd.setDefOcc(sumOcc);

		    if (sumOcc != 0){
		    	ttsd.setDefAdr(sumRev.divide(BigDecimal.valueOf(sumOcc.doubleValue()),2, RoundingMode.HALF_EVEN));
		    }else{
		    	ttsd.setDefAdr(sumRev);
		    }

		    tsdSummary[countSeg] = ttsd;
			countSeg ++;

		}

		return tsdSummary;
	}

	private TransientDataTotal getSummaryDataTotal(List<TransientDataTotal> totals){
		BigDecimal sumRev = new BigDecimal(0);
		Integer sumOcc = 0;
		Integer totAvailRooms;

		//HotelDAO hotelDao = new HotelDAO();
		TransientDataTotal tdSummary = new TransientDataTotal();
		//hotelDao.getHotelRooms(this.getCurrentHotel().getHotelId()) * totals.size();
		totAvailRooms = this.getCurrentHotel().getTotalRooms() * totals.size();

		for(TransientDataTotal tdt: totals){
			sumRev = sumRev.add(tdt.getTotRev());
			sumOcc = sumOcc + tdt.getTotOcc();
		}

		if (sumOcc != 0){
			tdSummary.setTotAdr(sumRev.divide(BigDecimal.valueOf(sumOcc.doubleValue()),2, RoundingMode.HALF_EVEN));
			tdSummary.setTotOccPct(new BigDecimal(sumOcc.doubleValue() / totAvailRooms.doubleValue() * 100).setScale(1, RoundingMode.HALF_EVEN));
		}
		else{
			tdSummary.setTotAdr(sumRev);
			tdSummary.setTotOccPct(new BigDecimal(0));
		}

		tdSummary.setTotRevPar(sumRev.divide(BigDecimal.valueOf(totAvailRooms.doubleValue()), 2, RoundingMode.HALF_EVEN));
		tdSummary.setTotRev(sumRev.setScale(0, RoundingMode.HALF_EVEN));
		tdSummary.setTotOccRooms(sumOcc);

		tdSummary.setComments("Summary Information");

		return tdSummary;

	}
	
	
	/**
	 * Update OCC,ADR
	 * @param segmentId, state, occ, adr
	 * @return 0 if the database could not get updated,  1 if update success
	 */
	public int UpdateTransientSegmentData(int customerId,int segmentId, String statedate, BigDecimal OCC, BigDecimal ADR){
		
		System.out.println("*********** UpdateTransientSegmentData (InlineEdition) - BEGIN - " + new Date() +" ***********");

		InputDAO input = new InputDAO(this.getCurrentHotel());		
		String dataType = input.getSegmentDataType(segmentId, customerId);
		BigDecimal TotRev;
		
		if (dataType != null && dataType.equals("REV"))
			TotRev = ADR;
		else
			TotRev = ADR.multiply(OCC);
		
		
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try{			
			conn = this.getCurrentHRR3Connection();	
			
			String statement = ("{call ftUpdateTransientSegmentData (?,?,?,?,?,?)}");
			
			ps = conn.prepareStatement(statement);
			ps.setInt(1, this.getCurrentHotel().getHotelId());
			ps.setString(2,  statedate);
			ps.setInt(3, segmentId);
			ps.setBigDecimal(4, OCC);
			ps.setBigDecimal(5, TotRev);
			ps.setBigDecimal(6, ADR);
			
			rs = ps.executeQuery();
			
			if(rs != null ){			
				while(rs.next()) {
					if (rs.getInt("p_out") == 1){						
						//HotelDAO hotelDao = new HotelDAO();
						//int totalRooms = hotelDao.getHotelRooms(this.getCurrentHotel().getHotelId());
						input.calculateStatData(customerId, statedate, statedate,this.getCurrentHotel().getHotelId());
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
	       System.out.println(" *********** UpdateTransientSegmentData (InlineEdition) - " + new Date()+ " ***********");
		}
				
		return 0;
	}
	
	/**
	 * Update Comment,Exception
	 * @param stateDate, Comment, Exception
	 * @return 0 if the database could not get updated,  1 if update success
	 */
	
	public int UpdateTransientData (String statedate, String comment, int exception){
		
		System.out.println("*********** UpdateTransientData (Inline Edition) - BEGIN - " + new Date() +" ***********");
		
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
	       System.out.println(" *********** UpdateTransientData (Inline Edition) - " + new Date()+ " ***********");
		}
				
		return 0;
	}
	
	public TransientDataRow UpdateTransientDataRow(List<TransientDataRow> lstTDR, int classId, int totalType, int numberOfSegments){
				
		try {

			

			//Total Object creation, representing Contract, Misc, Group or Transient Total

			List<TransientDataTotal> tdtMisc = new ArrayList<TransientDataTotal>();
			List<TransientDataTotal> tdtContract = new ArrayList<TransientDataTotal>();
			List<TransientDataTotal> tdtTransient = new ArrayList<TransientDataTotal>();
			List<TransientDataTotal> tdtGroup = new ArrayList<TransientDataTotal>();
			List<TransientDataTotal> tdtHotel = new ArrayList<TransientDataTotal>();
			List<TransientDataTotal> tdtPaid = new ArrayList<TransientDataTotal>();

			List<TransientSegmentData[]> tsdList = new ArrayList<TransientSegmentData[]>();
							
			//Populate the list of Totals per type and segment data
			for(int i=0; i < lstTDR.size(); i++){
				
				TransientDataTotals allTotals = new TransientDataTotals();
				
				//position 0 is summary info
				if ( i>0){
				allTotals = lstTDR.get(i).getTransientDataTotals();
				
				tdtMisc.add(allTotals.getMiscTotal());
				tdtContract.add(allTotals.getContractTotal());
				tdtGroup.add(allTotals.getGroupTotal());
				tdtTransient.add(allTotals.getTransientTotal());
				
				TransientSegmentData[] sdDay = lstTDR.get(i).getTransientDataSegmentList();
				
				tsdList.add(sdDay);
				}
				
			}

				
			//Calculate these Totals type,... 	
			tdtHotel = this.sumTotalHotel(tdtTransient, tdtContract,tdtMisc, tdtGroup);
			tdtPaid = this.sumPaidRooms(tdtTransient, tdtContract,tdtGroup);

			
			//get Summary data
			TransientDataRow tdrSummary = new TransientDataRow(classId, totalType);
			//TransientSegmentData[] tsdSummary = getSummaryData(tsdList, numberOfSegments);
			tdrSummary.setTransientDataSegmentList(getSummaryData(tsdList, numberOfSegments));

			TransientDataTotals totalsSummary = new TransientDataTotals();
			//Create all summary objects
			TransientDataTotal hotelTotal = new TransientDataTotal();
			TransientDataTotal paidTotal = new TransientDataTotal();
			TransientDataTotal groupTotal = new TransientDataTotal();
			TransientDataTotal contractTotal = new TransientDataTotal();
			TransientDataTotal transientTotal = new TransientDataTotal();
			TransientDataTotal miscTotal = new TransientDataTotal();
			//Get all summaries per totaltype
			hotelTotal = getSummaryDataTotal(tdtHotel);
			paidTotal = getSummaryDataTotal(tdtPaid);
			groupTotal = getSummaryDataTotal(tdtGroup);
			contractTotal = getSummaryDataTotal(tdtContract);
			transientTotal = getSummaryDataTotal(tdtTransient);
			miscTotal = getSummaryDataTotal(tdtMisc);
			//Set all summaries in the TransientTotals container
			totalsSummary.setHotelTotal(hotelTotal);
			totalsSummary.setPaidRooms(paidTotal);
			totalsSummary.setGroupTotal(groupTotal);
			totalsSummary.setContractTotal(contractTotal);
			totalsSummary.setTransientTotal(transientTotal);
			totalsSummary.setMiscTotal(miscTotal);
			//Set TransientTotals containet in the first Row
			tdrSummary.setTransientDataTotals(totalsSummary);
			

			return tdrSummary;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}


}
