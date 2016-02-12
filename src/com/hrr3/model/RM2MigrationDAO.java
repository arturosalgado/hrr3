package com.hrr3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.naming.NamingException;

import com.hrr3.entity.RM2Hotel;
import com.hrr3.entity.SSRSnapshot;
import com.hrr3.entity.Snapshot;
import com.hrr3.entity.migration.SnapshotTransientData;
import com.hrr3.entity.migration.SnapshotTransientSegmentData;
import com.hrr3.entity.ssrMigration.SSRSnapshotCOTB;
import com.hrr3.entity.ssrMigration.SSRSnapshotData;
import com.hrr3.entity.ssrMigration.SSRSnapshotDayStar;
import com.hrr3.entity.ssrMigration.SSRSnapshotDayStarData;
import com.hrr3.entity.ssrMigration.SSRSnapshotDayStarHotel;
import com.hrr3.entity.ssrMigration.SSRSnapshotGroupPace;
import com.hrr3.entity.ssrMigration.SSRSnapshotSUMARrate;
import com.hrr3.entity.ssrMigration.SSRSnapshotSUWklyMtg;
import com.hrr3.entity.ssrMigration.SSRSnapshotSUWklyMtgHD;
import com.hrr3.entity.ssrMigration.SSRSnapshotSUWklyMtgSS;
import com.hrr3.entity.ssrMigration.SSRSnapshotSegmentData;
import com.hrr3.entity.ssrMigration.SSRSnapshotStarData;


public class RM2MigrationDAO extends RM3AbstractDAO {
	
	public static final int MIGRATION_OK = 1;
	public static final int MIGRATION_WARNING = 2;
	public static final int MIGRATION_ERROR = 3;
	
	
	public List<RM2Hotel> getRM2Hotels() {
		
		Statement st = null;
		ResultSet rs = null;
		Connection conn = null;
		List<RM2Hotel> hotelList = null;
		
		try {			
			
			conn = this.getCurrentRM2Connection();
						
			String statement = "SELECT hotel_id, name , brand, city, state, company_id FROM ftHotel where company_id IN(6,7,8)";
					
			st = conn.createStatement();
			System.out.println("*********** Statement created *************");		
			rs = st.executeQuery(statement);
			System.out.println("*********** Query executed *************");
			if(rs != null){
				System.out.println("*********** HotelList object will be created *************");	
				if(rs.next()) {
					
					hotelList = new ArrayList<RM2Hotel>();
					
					
					do {
					
						System.out.println("*********** Filling out hotel *************");
						RM2Hotel hotel = new RM2Hotel();
						hotel.setHotelId(rs.getInt("hotel_id"));
						hotel.setName(rs.getString("name") == null ? "" : rs.getString("name"));
						hotel.setBrand(rs.getString("brand") == null ? "" : rs.getString("brand"));
						hotel.setCity(rs.getString("city") == null ? "" : rs.getString("city"));
						hotel.setState(rs.getString("state") == null ? "" : rs.getString("state"));
						
						hotelList.add(hotel);
					
					} while(rs.next());
					
				}
				
			}
			
			return hotelList;
				
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("*********** Exception while calling DB *************");
			e.printStackTrace();
			return null;
		}
		
		finally { close(rs, st, conn); }
	}

	
	
	public List<Snapshot> getSnapshotsByHotel (Integer hotelId) {
		
		List<Snapshot> lstSnapshots = new ArrayList<Snapshot>();				
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
				
		try
		{
			//get the migrated snapshots ID's  in RM3 database
			System.out.println("**************** Getting MigratedSnapshots ******************");
			HashMap<Integer,String> snapshotMigrated = getSnapshotsMigrated(hotelId);
			
			conn = this.getCurrentRM2Connection();
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
						
			String statement = "select * from ftSnapshot where hotel_id=? order by snapshot_id DESC";
			
			ps = conn.prepareStatement(statement);			
			ps.setInt(1, hotelId);			
			rs = ps.executeQuery();
			
			while(rs != null && rs.next()){	
				Snapshot snapshot = new Snapshot();
				
				System.out.println("**************** Getting SnapshotId-"+ rs.getInt("snapshot_id") +" ******************");
				
				snapshot.setSnapshotId(rs.getInt("snapshot_id"));
				snapshot.setHotelId(rs.getInt("hotel_id"));
				snapshot.setName(rs.getString("snapshot_name"));
				snapshot.setYear(rs.getInt("snapshot_year"));
				snapshot.setMonth(rs.getInt("snapshot_month"));
				snapshot.setBudget(rs.getInt("budget"));
				
				snapshot.setForecast(rs.getInt("current_forecast"));
				snapshot.setCurrentForecast(rs.getInt("forecast"));
								
				snapshot.setQuarterlyForecast(rs.getInt("quarterly_forecast"));
				//date
				Date dateObj = rs.getDate("created_ts");				
				snapshot.setCreatedTS(dateObj != null ? dateFormat.format(dateObj) : "");
				
				snapshot.setMigrated(false);
				
				//has list of migrated snapshotsIds
				if (!snapshotMigrated.isEmpty()){
					//find out if the RM2SnapshotId is in the list of  migrated snapshot ids
					if (snapshotMigrated.containsKey(snapshot.getSnapshotId())){
						//Set boolean attribute and migrated date.
						System.out.println("**************** Setting SnapshotId-"+ rs.getInt("snapshot_id") +" to TRUE ******************");
						snapshot.setMigrated(true);
						snapshot.setMigratedTS(snapshotMigrated.get(snapshot.getSnapshotId()));
					}
				}
								
				lstSnapshots.add(snapshot);				
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		finally { close(rs, ps, conn); }
		
		System.out.println("**************** Returning SnapshotList-"+ lstSnapshots.size() +" ******************");
		return lstSnapshots;
	}
	
	@SuppressWarnings("resource")
	public int  migrateSnapshotRm2ToRm3(int snapshotId, HashMap<Integer, String> segmentsMapped){					
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		Connection RM2Conn = null;
		Connection RM3Conn = null;
		int RM3SnapshotId = 0;
				
		try
		{
			RM2Conn = this.getCurrentRM2Connection();
			RM3Conn = this.getCurrentHRR3Connection();
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");					
			
			String statement = "SELECT * FROM ftSnapshot WHERE  snapshot_id =?";
			
			ps = RM2Conn.prepareStatement(statement);			
			ps.setInt(1, snapshotId);			
			rs = ps.executeQuery();
						
			if(rs != null && rs.next()){	
				Snapshot snapshot = new Snapshot();
				
				snapshot.setSnapshotId(rs.getInt("snapshot_id"));
				snapshot.setHotelId(rs.getInt("hotel_id"));
				snapshot.setName(rs.getString("snapshot_name"));
				snapshot.setYear(rs.getInt("snapshot_year"));
				snapshot.setMonth(rs.getInt("snapshot_month"));
				snapshot.setBudget(rs.getInt("budget"));
				
				snapshot.setForecast(rs.getInt("current_forecast"));
				snapshot.setCurrentForecast(rs.getInt("forecast"));
				
				snapshot.setQuarterlyForecast(rs.getInt("quarterly_forecast"));								
				
				//dates
				Date dateObj = rs.getDate("created_ts");				
				snapshot.setCreatedTS(dateObj != null ? dateFormat.format(dateObj) : "");
				
				dateObj = rs.getDate("restore_ts");
				snapshot.setRestoreTS(dateObj != null ? dateFormat.format(dateObj) : "");								
				
				snapshot.setMigratedTS(dateFormat.format(new Date()));
										
				statement="";
				//Insert the snapshot info and insert the map (RM3SnapshotId (new), RM2SnapshotId)
				statement = ("{call RM3MigrateSnapshot (?,?,?,?,?,?,?,?,?,?,?,?)}");
				
				ps = null;
				ps = RM3Conn.prepareStatement(statement);
				ps.setInt(1, snapshot.getHotelId());
				ps.setString(2, snapshot.getName());
				ps.setInt(3, snapshot.getYear());
				ps.setInt(4, snapshot.getBudget());
				ps.setInt(5, snapshot.getForecast());
				ps.setInt(6, snapshot.getMonth());
				ps.setInt(7, snapshot.getCurrentForecast());
				ps.setInt(8, snapshot.getQuarterlyForecast());
				ps.setString(9, snapshot.getCreatedTS());
				ps.setString(10, snapshot.getRestoreTS());
				ps.setString(11, snapshot.getMigratedTS());
				ps.setInt(12, snapshot.getSnapshotId());
								
				rs2 = ps.executeQuery();
				
				if(rs2 != null && rs2.next()){
					
					RM3SnapshotId = rs2.getInt("newSnapshotId");
					
					if(RM3SnapshotId > 0){
						System.out.println("*********** invoke migrateSnapshotTransientData method the snapshotId =  "+ rs2.getInt("newSnapshotId") +  "*************");
						if(migrateSnapshotTransientData(snapshotId, RM3SnapshotId) == MIGRATION_OK)
						{
							System.out.println("*********** invoke migrateSnapshotTransientSegmentData method the snapshotId =  "+ rs2.getInt("newSnapshotId") +  "*************");
							return migrateSnapshotTransientSegmentData(snapshotId, RM3SnapshotId, segmentsMapped);
						}
						else return MIGRATION_ERROR;
					}
					else{
						System.out.println("***********Error: the generated snapshotId =  "+ RM3SnapshotId +  "*************");
						return MIGRATION_ERROR;
					}
				}	
				
				else return MIGRATION_ERROR; 
				
				
			}//EndIf
			else return MIGRATION_ERROR;
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("*********** Exception while calling DB *************");
			e.printStackTrace();
			deleteMigratedSnashot(RM3SnapshotId);
			return MIGRATION_ERROR;
		}
		
		finally {close(rs, ps, RM2Conn); close(rs2, ps, RM3Conn);}
		
		}
	
	public HashMap<Integer, String> getSnapshotsMigrated(int hotelId){
		//Select the info of migrated snapshots for one hotel
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		//use to set the RM2SnapshotId and migrated date.
		HashMap<Integer,String> snapshotMigrated = new HashMap<Integer, String>();
	
		try
		{
			conn = RM3DataSourceConnectionFactory.getHRR3Connection();					
			
			String statement = "SELECT RM3SnapshotId, RM2SnapshotId, migratedTS FROM RM3SnapshotMigrated WHERE hotelId = ?";
			
			ps = conn.prepareStatement(statement);			
			ps.setInt(1, hotelId);	
			
			rs = ps.executeQuery();
			
			while(rs != null && rs.next()){	
				System.out.println("**************** Adding Migrated Snapshot"+ rs.getInt("RM2SnapshotId") +" ******************");
				snapshotMigrated.put(rs.getInt("RM2SnapshotId"), rs.getString("migratedTS"));				
			}
			
		}catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			System.out.println("*********** Exception while calling DB *************");
			e.printStackTrace();
		}

		finally { close(rs, ps, conn);}
		
		return snapshotMigrated;
			
	}
		
	@SuppressWarnings("resource")
	public int migrateSnapshotTransientData(int RM2SnapshotId,int RM3SnapshotId){
		PreparedStatement ps = null;
		ResultSet rs = null;		
		int isCompleted = MIGRATION_ERROR;
		Connection RM2Conn = null;		
				
		try
		{
			System.out.println("*********** Start migrateSnapshotTransientData method  *************");
			RM2Conn = this.getCurrentRM2Connection();
						
			//SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");					
			
			String statement = "select hotel_id,statdate,comments,isException,dow,isActual,tot_occ_pct,tot_adr,tot_rev_par,tot_occ_rooms,tot_rev," +
					           "gtot_occ,gtot_rev,gtot_adr,gdef_occ,gdef_rev,gdef_adr,gten_occ,gten_rev,gten_adr,created_ts from " +
					           "ftSnapshotTransientData where snapshot_id=?";
			
			ps = RM2Conn.prepareStatement(statement);			
			ps.setInt(1, RM2SnapshotId);
			System.out.println("*********** Statement created *************");	
			
			System.out.println(statement + RM2SnapshotId);	
			
			rs = ps.executeQuery();
			
			System.out.println("*********** Query executed *************");
			
			if(rs != null){
				if(rs.next()){
					
				do{
									
					SnapshotTransientData std = new SnapshotTransientData();
					
					System.out.println("**************** Getting SnapshotTransientData ******************");
					std.setHotelId(rs.getInt("hotel_id"));
					std.setSnapshotId(RM3SnapshotId);
					std.setStatdate(rs.getString("statdate"));
					std.setComments(rs.getString("comments"));
					std.setIsException(rs.getInt("isException"));
					std.setDow(rs.getString("dow"));
					std.setIsActual(rs.getInt("isActual"));
					std.setTotOccPct(rs.getBigDecimal("tot_occ_pct"));
					std.setTotAdr(rs.getBigDecimal("tot_adr"));
					std.setTotRevPar(rs.getBigDecimal("tot_rev_par"));
					std.setTotOccRooms(rs.getBigDecimal("tot_occ_rooms"));
					std.setTotRev(rs.getBigDecimal("tot_rev"));
					std.setGtotOcc(rs.getBigDecimal("gtot_occ"));
					std.setGtotRev(rs.getBigDecimal("gtot_rev"));
					std.setGtotAdr(rs.getBigDecimal("gtot_adr"));
					std.setGdefOcc(rs.getBigDecimal("gdef_occ"));
					std.setGdefRev(rs.getBigDecimal("gdef_rev"));
					std.setGdefAdr(rs.getBigDecimal("gdef_adr"));
					std.setGtenOcc(rs.getBigDecimal("gten_occ"));
					std.setGtenRev(rs.getBigDecimal("gten_rev"));
					std.setGtenAdr(rs.getBigDecimal("gten_adr"));
					std.setCreatedTs(rs.getString("created_ts"));
					
					isCompleted = insertSnapshotTransientData(std);
					
					if (isCompleted == MIGRATION_ERROR){						
						return MIGRATION_ERROR;
					}					
				  }while (rs.next());
				}	
			}
			System.out.println("**************** SnapshotTransientData Migrated for RM2SnapshotId = " +RM2SnapshotId+ " ******************");			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("*********** Exception while calling DB *************");
			e.printStackTrace();
			return MIGRATION_ERROR;
		}
		
		finally {close(rs, ps, RM2Conn);}
		
			return isCompleted;		
	}
	
	public int insertSnapshotTransientData (SnapshotTransientData std){
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;		
	
		try
		{
			System.out.println("*********** Start insertSnapshotTransientData method  *************");
			conn = this.getCurrentHRR3Connection();
			
			String statement = "insert into RM3SnapshotTransientData (snapshot_id,hotel_id,statdate,comments,isException,dow,isActual," +
					           "tot_occ_pct,tot_adr,tot_rev_par,tot_occ_rooms,tot_rev,gtot_occ,gtot_rev,gtot_adr,gdef_occ,gdef_rev,gdef_adr," +
					           "gten_occ,gten_rev,gten_adr,created_ts) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			ps = conn.prepareStatement(statement);			
			ps.setInt(1, std.getSnapshotId());
			ps.setInt(2, std.getHotelId());
			ps.setString(3, std.getStatdate());
			ps.setString(4, std.getComments());
			ps.setInt(5, std.getIsException());
			ps.setString(6, std.getDow());
			ps.setInt(7, std.getIsActual());
			
			ps.setBigDecimal(8, std.getTotOccPct());
			ps.setBigDecimal(9, std.getTotAdr());
			ps.setBigDecimal(10, std.getTotRevPar());
			ps.setBigDecimal(11, std.getTotOccRooms());
			ps.setBigDecimal(12, std.getTotRev());
			ps.setBigDecimal(13, std.getGtotOcc());
			ps.setBigDecimal(14, std.getGtotRev());
			ps.setBigDecimal(15, std.getGtotAdr());
			ps.setBigDecimal(16, std.getGdefOcc());
			ps.setBigDecimal(17, std.getGdefRev());
			ps.setBigDecimal(18, std.getGdefAdr());
			
			ps.setBigDecimal(19, std.getGtenOcc());
			ps.setBigDecimal(20, std.getGtenRev());
			ps.setBigDecimal(21, std.getGtenAdr());
			ps.setString(22, std.getCreatedTs());
			
			System.out.println("*********** Statement created *************");	
			
			
			ps.executeUpdate();
			
			System.out.println("*********** Insert executed *************");
				
			return  MIGRATION_OK;
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("*********** Exception while calling DB *************");
			deleteMigratedSnashot(std.getSnapshotId());
			e.printStackTrace();	
			return MIGRATION_ERROR;
		}	
		finally { close(rs, ps);}
		
		
	}
	
	@SuppressWarnings("resource")
	public int migrateSnapshotTransientSegmentData (int RM2SnapshotId, int RM3SnapshotId, HashMap<Integer, String> segmentsMapped){
		PreparedStatement ps = null;
		ResultSet rs = null;		
		int isCompleted = MIGRATION_ERROR;
		Connection RM2Conn = null;	
		boolean warnings = false;
				
		try
		{
			System.out.println("*********** Start migrateSnapshotTransientSegmentData method  *************");
			RM2Conn = this.getCurrentRM2Connection();
						
			//SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");					
			
			String statement = "select hotel_id,segment_id,statdate,type,tot_occ,tot_rev,tot_adr,def_occ,def_rev,def_adr," +
					           "ten_occ,ten_rev,ten_adr,created_ts,admin from ftSnapshotTransientSegmentData where snapshot_id = ?";
			
			ps = RM2Conn.prepareStatement(statement);			
			ps.setInt(1, RM2SnapshotId);
			System.out.println("*********** Statement created *************");	
			
			System.out.println(statement + RM2SnapshotId);	
			
			rs = ps.executeQuery();
			
			System.out.println("*********** Query executed *************");
			
			if(rs != null){
				if(rs.next()){
					
					do{
						System.out.println("**************** Getting SnapshotTransientSegmentData ******************");
						SnapshotTransientSegmentData stsd = new SnapshotTransientSegmentData();
						
						stsd.setSnapshotId(RM3SnapshotId);
						
						stsd.setHotelId(rs.getInt("hotel_id"));
						stsd.setSegmentId(rs.getInt("segment_id"));
						stsd.setStatdate(rs.getString("statdate"));
						stsd.setType(rs.getString("type"));
						stsd.setTotOcc(rs.getBigDecimal("tot_occ"));
						stsd.setTotRev(rs.getBigDecimal("tot_rev"));
						stsd.setTotAdr(rs.getBigDecimal("tot_adr"));
						stsd.setDefOcc(rs.getBigDecimal("def_occ"));
						stsd.setDefRev(rs.getBigDecimal("def_rev"));
						stsd.setDefAdr(rs.getBigDecimal("def_adr"));
						stsd.setTenOcc(rs.getBigDecimal("ten_occ"));
						stsd.setTenRev(rs.getBigDecimal("ten_rev"));
						stsd.setTenAdr(rs.getBigDecimal("ten_adr"));
						stsd.setCreatedTs(rs.getString("created_ts"));
						stsd.setAdmin(rs.getInt("Admin"));
						
						if(segmentsMapped.containsKey(stsd.getSegmentId())){
							isCompleted = insertSnapshotTransientSegmentData(stsd, segmentsMapped);
							
							if (isCompleted == MIGRATION_ERROR){						
								return MIGRATION_ERROR;
							}
						}else{
							System.out.println("**************** migrateSnapshotTransientSegmentData -> WARNING  SegmentId : " + stsd.getSegmentId() + " NOT FOUNDED  ******************");
							warnings = true;
						}
						
						
						
					}while(rs.next());
				}
			}	
			
			System.out.println("**************** SnapshotTransientSegmentData Migrated for RM2SnapshotId = " +RM2SnapshotId+ " ******************");
			
			if (isCompleted == MIGRATION_OK && warnings == true){
				return MIGRATION_WARNING;
			}
			
			return isCompleted;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("*********** Exception while calling DB *************");
			e.printStackTrace();
			return MIGRATION_ERROR;
		}
		
		finally {close(rs, ps, RM2Conn);}						
	}
	
	private int insertSnapshotTransientSegmentData (SnapshotTransientSegmentData stsd, HashMap<Integer, String> segmensMapped){
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;		
	
		try
		{
			System.out.println("*********** Start insertSnapshotTransientSegmentData method  *************");
			conn = this.getCurrentHRR3Connection();
			
			String statement = "insert into RM3SnapshotTransientSegmentData (snapshot_id, hotel_id,segment_id,statdate,type,tot_occ,tot_rev,tot_adr," +
					           "def_occ,def_rev,def_adr,ten_occ,ten_rev,ten_adr,created_ts,admin, _year, _month, _isWeekend) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, year(statdate), month(statdate), if(weekday(statdate) in (4,5), 1, 0))";
			
			
			
			ps = conn.prepareStatement(statement);			
			ps.setInt(1, stsd.getSnapshotId());
			ps.setInt(2, stsd.getHotelId());
			System.out.println("***********RM2 SegmentId - "+ stsd.getSegmentId() +" *************");
			
			String tmpSegmentId = segmensMapped.get(stsd.getSegmentId());
			int itmpSegmentId = Integer.parseInt(tmpSegmentId);
			
			System.out.println("***********SegmentId String - int : "+ tmpSegmentId + " - " + itmpSegmentId +" *************");	
			ps.setInt(3, itmpSegmentId);			
			
			ps.setString(4, stsd.getStatdate());
			ps.setString(5, stsd.getType());
			ps.setBigDecimal(6, stsd.getTotOcc());
			ps.setBigDecimal(7, stsd.getTotRev());
			ps.setBigDecimal(8, stsd.getTotAdr());
			ps.setBigDecimal(9, stsd.getDefOcc());
			ps.setBigDecimal(10, stsd.getDefRev());
			ps.setBigDecimal(11, stsd.getDefAdr());
			ps.setBigDecimal(12, stsd.getTenOcc());
			ps.setBigDecimal(13, stsd.getTenRev());
			ps.setBigDecimal(14, stsd.getTenAdr());
			ps.setString(15, stsd.getCreatedTs());
			ps.setInt(16, stsd.getAdmin());
			
			System.out.println("*********** Statement created *************");	
			
			
			ps.executeUpdate();
			
			System.out.println("*********** Insert SnapshotTransientSegmentData executed *************");
			
			return  MIGRATION_OK;
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("*********** Exception while calling DB *************");
			deleteMigratedSnashot(stsd.getSnapshotId());
			e.printStackTrace();	
			return MIGRATION_ERROR;
		}
		//Close only rs and ps to keep RM3 connection alive
		finally { close(rs, ps);}			
	}
	
	public HashMap<Integer, String> getRM2SegmentsByHotel (int  hotelId){
		PreparedStatement ps = null;
		ResultSet rs = null;				
		Connection RM2Conn = null;	
		HashMap<Integer, String> RM2Segments = new HashMap<Integer, String>();
				
		try
		{
			System.out.println("*********** Start getRM2SegmentsByHotel method  *************");
			RM2Conn = RM2DataSourceConnectionFactory.getRM2Connection();													
			
			String statement = "select segment_id, name from ftSegment where hotel_id = ? order by name";
						
			ps = RM2Conn.prepareStatement(statement);			
			ps.setInt(1, hotelId);
			System.out.println("*********** Statement created *************");
			
			rs = ps.executeQuery();
			
			System.out.println("*********** Query executed *************");
			
			if(rs != null){
				if(rs.next()){
					System.out.println("**************** Getting RM2Segments ******************");
					do{												
						RM2Segments.put(rs.getInt("segment_id"), rs.getString("name"));
						
					}while (rs.next());
				}
			}	
					
		}catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			System.out.println("*********** Exception while calling DB *************");
			e.printStackTrace();			
		}
	
		finally { close(rs, ps, RM2Conn);}
		System.out.println("**************** Returning RM2Segments List-"+ RM2Segments.size() +" ******************");
		return RM2Segments;
		
	}
	
	public HashMap<Integer, String> mapSegmentsToMigrate (int hotelId, int customerId){
		HashMap<Integer, String> RM2Segments = new HashMap<Integer, String>();
		HashMap<Integer, String> RM3Segments = new HashMap<Integer, String>();
		HashMap<Integer, String> mappedSegments = new HashMap<Integer, String>();
 		String segmentsNotFounded = "";
 		
 		System.out.println("*********** Start mapSegmentsToMigrate method  *************");

 		System.out.println("****************Invoke getRM2SegmentsByHotel ******************");
		RM2Segments = getRM2SegmentsByHotel(hotelId);
		System.out.println("****************Invoke getRM3SegmentsByCustomer ******************");
		RM3Segments = getRM3SegmentsByCustomer(customerId);
		
		if (RM2Segments.isEmpty() || RM3Segments.isEmpty()){			
			System.out.println("**************** Return no have segments. ******************");
			return mappedSegments; 
		}
				
		for(Entry<Integer,String> segment: RM2Segments.entrySet()){
			
			if (segment.getValue().equals("TFIT")){
				segment.setValue("TF.I.T.");
			}
			
			if (RM3Segments.containsValue(segment.getValue())){
				int RM2SegmentId = 0;
				int RM3SegmentId = 0;
				
				RM2SegmentId =  segment.getKey();
				
				for(Entry<Integer,String> segment2: RM3Segments.entrySet()){
					if(segment2.getValue().equals(segment.getValue())){
						RM3SegmentId = segment2.getKey();
					}
				}
				
				if (RM2SegmentId > 0 & RM3SegmentId > 0){
					mappedSegments.put(RM2SegmentId,Integer.toString(RM3SegmentId));
				}												
			}
			else{				
				segmentsNotFounded = segmentsNotFounded + segment.getValue() +",";				
			}			
		}
		
		if ( segmentsNotFounded != ""){
			System.out.println("****************No mapping segments =" + segmentsNotFounded +" ******************");
			mappedSegments.clear();
			mappedSegments.put(0, segmentsNotFounded);			
		}
		System.out.println("**************** Returning MappedSegments List-"+ mappedSegments.size() +" ******************");
		
		for(Entry<Integer,String> segmentmap: mappedSegments.entrySet()){
			System.out.println("RM2SegmentId : "+ segmentmap.getKey() + " RM3SegmentId : "+ segmentmap.getValue());
		}
		return mappedSegments;
	}
	
	private HashMap<Integer, String> getRM3SegmentsByCustomer (int customerId){
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;	
		HashMap<Integer, String> RM3Segments = new HashMap<Integer, String>();
	
		try
		{
			System.out.println("*********** Start getRM3SegmentsByCustomer method  *************");
			conn = RM3DataSourceConnectionFactory.getHRR3Connection();
			
			String statement = "select segment_id, name from RM3Segments where customer_id = ? order by name";
					           
			ps = conn.prepareStatement(statement);			
			ps.setInt(1, customerId);
			System.out.println("*********** Statement created *************");
			
			rs = ps.executeQuery();
			System.out.println("*********** Query executed *************");
			
			if(rs != null){
				if(rs.next()){
					System.out.println("**************** Getting RM3Segments ******************");
					do{												
						RM3Segments.put(rs.getInt("segment_id"), rs.getString("name"));
						
					}while (rs.next());
				}
			}
		}catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			System.out.println("*********** Exception while calling DB *************");
			e.printStackTrace();
			//return null;
		}
	
		finally { close(rs, ps, conn);}
		System.out.println("**************** Returning RM3Segments List-"+ RM3Segments.size() +" ******************");
		return RM3Segments;							
	}
	
	public void deleteMigratedSnashot (int RM3SnapshotId){
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;	
		
		try{
			System.out.println("*********** Start deleteMigratedSnashot method  *************");
			conn = this.getCurrentHRR3Connection();
			
			String statement = ("{call RM3DeleteSnapshotMigrated(?)}");
					           
			ps = conn.prepareStatement(statement);	
			ps.setInt(1, RM3SnapshotId);
			System.out.println("*********** Statement created *************");
			
			ps.executeUpdate();
			System.out.println("*********** Query executed *************");

		}catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("*********** Exception while calling DB *************");
			e.printStackTrace();
			//return null;
		}
	
		finally { close(rs, ps, conn);}
		
	}
	
	public List<SSRSnapshot> getSSRSnapshotsByHotel (Integer hotelId) {
		
		List<SSRSnapshot> lstSSRSnapshots = new ArrayList<SSRSnapshot>();				
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
				
		try
		{
			//get the migrated snapshots ID's  in RM3 database
			System.out.println("**************** Getting MigratedSnapshots ******************");
			HashMap<Integer,String> ssrSnapshotMigrated = getSSRSnapshotsMigrated(hotelId);
			
			conn = this.getCurrentRM2Connection();
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
						
			String statement = "select distinct * from ssrSnapshot s inner join ssrSnapshot_SU_WklyMtg   w " +
					"on w.ssrSnapshot_id = s.ssrSnapshot_id where s.hotel_id = ? order by created_ts desc";
			
			ps = conn.prepareStatement(statement);			
			ps.setInt(1, hotelId);			
			rs = ps.executeQuery();
			
			while(rs != null && rs.next()){	
				SSRSnapshot ssrSnapshot = new SSRSnapshot();
				
				System.out.println("**************** Getting SnapshotId-"+ rs.getInt("ssrsnapshot_id") +" ******************");
				
				ssrSnapshot.setSnapshotId(rs.getInt("ssrSnapshot_id"));
				ssrSnapshot.setHotelId(rs.getInt("hotel_id"));
				ssrSnapshot.setSnapshotName(rs.getString("snapshot_name"));
				ssrSnapshot.setYear(rs.getInt("snapshot_year"));
				ssrSnapshot.setMonth(rs.getInt("snapshot_month"));								
				ssrSnapshot.setWeekNumber(rs.getString("wk_no"));				
				
				//created_ts
				Date created_ts = rs.getDate("created_ts");				
				ssrSnapshot.setDateCreated(created_ts != null ? dateFormat.format(created_ts) : "");
				
				//lastactdate
				Date lastactdate = rs.getDate("lastactdate");				
				ssrSnapshot.setLastDate(lastactdate != null ? dateFormat.format(lastactdate) : "");
				
				//currmeetdate
				Date currmeetdate = rs.getDate("currmeetdate");				
				ssrSnapshot.setCurrentMeetingDate(currmeetdate != null ? dateFormat.format(currmeetdate) : "");
															
				ssrSnapshot.setMigrated(false);
				
				//has list of migrated snapshotsIds
				if (!ssrSnapshotMigrated.isEmpty()){
					//find out if the RM2SnapshotId is in the list of  migrated snapshot ids
					if (ssrSnapshotMigrated.containsKey(ssrSnapshot.getSnapshotId())){
						//Set boolean attribute and migrated date.
						System.out.println("**************** Setting SnapshotId-"+ rs.getInt("ssrSnapshot_id") +" to TRUE ******************");
						ssrSnapshot.setMigrated(true);
						ssrSnapshot.setMigratedTS(ssrSnapshotMigrated.get(ssrSnapshot.getSnapshotId()));
					}
				}
								
				lstSSRSnapshots.add(ssrSnapshot);				
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		finally { close(rs, ps, conn); }
		
		System.out.println("**************** Returning SnapshotList-"+ lstSSRSnapshots.size() +" ******************");
		return lstSSRSnapshots;
	}
	
	public HashMap<Integer, String> getSSRSnapshotsMigrated(int hotelId){
		//Select the info of migrated snapshots for one hotel
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		//use to set the RM2SnapshotId and migrated date.
		HashMap<Integer,String> ssrSnapshotMigrated = new HashMap<Integer, String>();
	
		try
		{
			conn = RM3DataSourceConnectionFactory.getHRR3Connection();					
			
			String statement = "SELECT RM3SSRSnapshotId, RM2SSRSnapshotId, migratedTS FROM RM3SSRSnapshotMigrated WHERE hotelId = ?";
			
			ps = conn.prepareStatement(statement);			
			ps.setInt(1, hotelId);	
			
			rs = ps.executeQuery();
			
			while(rs != null && rs.next()){	
				System.out.println("**************** Adding Migrated Snapshot"+ rs.getInt("RM2SSRSnapshotId") +" ******************");
				ssrSnapshotMigrated.put(rs.getInt("RM2SSRSnapshotId"), rs.getString("migratedTS"));				
			}
			
		}catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			System.out.println("*********** Exception while calling DB *************");
			e.printStackTrace();
		}

		finally { close(rs, ps, conn);}
		
		return ssrSnapshotMigrated;
			
	}
	
	
	public HashMap<Integer, String> getRM2SSRSegmentsByHotel (int  hotelId){
		PreparedStatement ps = null;
		ResultSet rs = null;				
		Connection RM2Conn = null;	
		HashMap<Integer, String> RM2SSRSegments = new HashMap<Integer, String>();
				
		try
		{
			System.out.println("*********** Start getRM2SSRSegmentsByHotel method  *************");
			RM2Conn = RM2DataSourceConnectionFactory.getRM2Connection();													
			
			String statement = "select segment_id, name from ftSegmentSSR where hotel_id = ? order by name";
					           
			
			ps = RM2Conn.prepareStatement(statement);			
			ps.setInt(1, hotelId);
			System.out.println("*********** Statement created *************");
			
			rs = ps.executeQuery();
			
			System.out.println("*********** Query executed *************");
			
			if(rs != null){
				if(rs.next()){
					System.out.println("**************** Getting RM2SSRSegments ******************");
					do{												
						RM2SSRSegments.put(rs.getInt("segment_id"), rs.getString("name"));
						
					}while (rs.next());
				}
			}	
					
		}catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			System.out.println("*********** Exception while calling DB *************");
			e.printStackTrace();			
		}
	
		finally { close(rs, ps, RM2Conn);}
		System.out.println("**************** Returning RM2SSRSegments List-"+ RM2SSRSegments.size() +" ******************");
		return RM2SSRSegments;
		
	}
	
	private HashMap<Integer, String> getRM3SSRSegments(){
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;	
		HashMap<Integer, String> RM3SSRSegments = new HashMap<Integer, String>();
	
		try
		{
			System.out.println("*********** Start getRM3SSRSegmentsByCustomer method  *************");
			conn = RM3DataSourceConnectionFactory.getHRR3Connection();
			
			String statement = "SELECT segment_id, name FROM RM3SegmentsSSR WHERE isActive = 1 and isTotal = 0 and type = 'SSR' order by name";
					           
			ps = conn.prepareStatement(statement);						
			System.out.println("*********** Statement created *************");
			
			rs = ps.executeQuery();
			System.out.println("*********** Query executed *************");
			
			if(rs != null){
				if(rs.next()){
					System.out.println("**************** Getting RM3SSRSegments ******************");
					do{												
						RM3SSRSegments.put(rs.getInt("segment_id"), rs.getString("name"));
						
					}while (rs.next());
				}
			}
		}catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			System.out.println("*********** Exception while calling DB *************");
			e.printStackTrace();
			//return null;
		}
	
		finally { close(rs, ps, conn);}
		System.out.println("**************** Returning RM3SSRSegments List-"+ RM3SSRSegments.size() +" ******************");
		return RM3SSRSegments;							
	}
	
	public HashMap<Integer, String> mapSSRSegmentsToMigrate (int hotelId){
		HashMap<Integer, String> RM2SSRSegments = new HashMap<Integer, String>();
		HashMap<Integer, String> RM3SSRSegments = new HashMap<Integer, String>();
		HashMap<Integer, String> mappedSegments = new HashMap<Integer, String>();
 		String segmentsNotFounded = "";
 		
 		System.out.println("*********** Start mapSegmentsToMigrate method  *************");

 		System.out.println("****************Invoke getRM2SegmentsByHotel ******************");
		RM2SSRSegments = getRM2SSRSegmentsByHotel(hotelId);
		System.out.println("****************Invoke getRM3SegmentsByCustomer ******************");
		RM3SSRSegments = getRM3SSRSegments();
		
		if (RM2SSRSegments.isEmpty() || RM3SSRSegments.isEmpty()){			
			System.out.println("**************** Return no have segments. ******************");
			return mappedSegments; 
		}
				
		for(Entry<Integer,String> segment: RM2SSRSegments.entrySet()){
									
			if (RM3SSRSegments.containsValue(segment.getValue())){
				int RM2SegmentId = 0;
				int RM3SegmentId = 0;
				
				RM2SegmentId =  segment.getKey();
				
				for(Entry<Integer,String> segment2: RM3SSRSegments.entrySet()){
					if(segment2.getValue().equals(segment.getValue())){
						RM3SegmentId = segment2.getKey();
					}
				}
				
				if (RM2SegmentId > 0 & RM3SegmentId > 0){
					mappedSegments.put(RM2SegmentId,Integer.toString(RM3SegmentId));
				}												
			}
			else{				
				segmentsNotFounded = segmentsNotFounded + segment.getValue() +",";				
			}			
		}
		
		if ( segmentsNotFounded != ""){
			System.out.println("****************No mapping segments =" + segmentsNotFounded +" ******************");
			mappedSegments.clear();
			mappedSegments.put(0, segmentsNotFounded);			
		}
		System.out.println("**************** Returning MappedSegments List-"+ mappedSegments.size() +" ******************");
		
		for(Entry<Integer,String> segmentmap: mappedSegments.entrySet()){
			System.out.println("RM2SegmentId : "+ segmentmap.getKey() + " RM3SegmentId : "+ segmentmap.getValue());
		}
		return mappedSegments;
	}

	public int  migrateSSRSnapshotRm2ToRm3(int RM2SSRSnapshotId, HashMap<Integer, String> segmentsMapped, HashMap<Integer, String> SSRSegmentsMapped){					
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		Connection RM2Conn = null;
		Connection RM3Conn = null;
		int RM3SSRSnapshotId = 0;
		int result = 0;
		
		System.out.println("*********** Start migrateSSRSnapshotRm2ToRm3 method  *************");
		try
		{
			RM2Conn = this.getCurrentRM2Connection();
			RM3Conn = this.getCurrentHRR3Connection();
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");					
			
			System.out.println("*********** get RM2 SSRSnapshot information  *************");
			
			String statement = "select * from ssrSnapshot  where ssrSnapshot_id = ? and hotel_id = ?";
			
			ps = RM2Conn.prepareStatement(statement);			
			ps.setInt(1, RM2SSRSnapshotId);
			ps.setInt(2, this.getCurrentHotel().getHotelId());
			rs = ps.executeQuery();
						
			if(rs != null && rs.next()){	
				SSRSnapshot SSRSnapshot = new SSRSnapshot();
				
				SSRSnapshot.setSnapshotId(rs.getInt("ssrSnapshot_id"));
				SSRSnapshot.setSnapshotName(rs.getString("snapshot_name"));
				SSRSnapshot.setHotelId(rs.getInt("hotel_id"));
				SSRSnapshot.setYear(rs.getInt("snapshot_year"));
				SSRSnapshot.setMonth(rs.getInt("snapshot_month"));
				
				Date dateObj = rs.getDate("created_ts");				
				SSRSnapshot.setDateCreated(dateObj != null ? dateFormat.format(dateObj) : "");
				
				Date dateObj2 = rs.getDate("restore_ts");
				SSRSnapshot.setRestoreTS(dateObj2 != null ? dateFormat.format(dateObj2) : "");
				
				
				SSRSnapshot.setMigratedTS(dateFormat.format(new Date()));
										
				statement="";
				//Insert the snapshot info and insert the map (RM3SnapshotId (new), RM2SnapshotId)
				
				/* `p_hotel_Id` int,
				 * `p_snapshotname` varchar(50),
				 * `p_year` int,
				 * `p_month` int,
				 * `p_createdTS` datetime,
				 * `p_restoreTS` datetime,
				 * `p_RM2SSRSnapshotId` int,
				 * `p_migratedTS` datetime
				 */
				
				System.out.println("*********** Invoke SP  -  RM3MigrateSSRSnapshot *************");
				statement = ("{call RM3MigrateSSRSnapshot (?,?,?,?,?,?,?,?)}");
				
				ps = null;
				ps = RM3Conn.prepareStatement(statement);
				ps.setInt(1, SSRSnapshot.getHotelId());
				ps.setString(2, SSRSnapshot.getSnapshotName());
				ps.setInt(3, SSRSnapshot.getYear());
				ps.setInt(4, SSRSnapshot.getMonth());
				ps.setString(5, SSRSnapshot.getDateCreated());
				ps.setString(6, SSRSnapshot.getRestoreTS());
				ps.setInt(7, RM2SSRSnapshotId);
				ps.setString(8, SSRSnapshot.getMigratedTS());
				
								
				rs2 = ps.executeQuery();
				
				if(rs2 != null && rs2.next()){
					
					RM3SSRSnapshotId = rs2.getInt("newSSRSnapshotId");
					
					if(RM3SSRSnapshotId > 0){
						
						System.out.println("*********** RM3MigrateSSRSnapshot ID : " + RM3SSRSnapshotId);
						
						System.out.println("*********** invoke migrateSSRSnapshotTransientData method the ssrSnapshotId =  "+ rs2.getInt("newSSRSnapshotId") +  "*************");												
						if(this.migrateSSRSnapshotTransientData(RM2SSRSnapshotId, RM3SSRSnapshotId) == MIGRATION_ERROR) return MIGRATION_ERROR;
						
						System.out.println("*********** invoke migrateSnapshotTransientSegmentData method the snapshotId =  "+ rs2.getInt("newSSRSnapshotId") +  "*************");
						//possible warnigs
						result = this.migrateSSRSnapshotTransientSegmentData(RM2SSRSnapshotId, RM3SSRSnapshotId, segmentsMapped); 
						if (result == MIGRATION_ERROR)  return MIGRATION_ERROR;
																	
						System.out.println("*********** invoke migrateSSRSnapshotData method the ssrSnapshotId =  "+ rs2.getInt("newSSRSnapshotId") +  "*************");
						if(this.migrateSSRSnapshotData(RM2SSRSnapshotId, RM3SSRSnapshotId) == MIGRATION_ERROR) return MIGRATION_ERROR;
						
						System.out.println("*********** invoke migrateSSRSnapshotSegmentData method the ssrSnapshotId =  "+ rs2.getInt("newSSRSnapshotId") +  "*************");
						//possible warnigs
						result = this.migrateSSRSnapshotSegmentData(RM2SSRSnapshotId, RM3SSRSnapshotId, SSRSegmentsMapped);
						if(result == MIGRATION_ERROR)  return MIGRATION_ERROR;
						
						System.out.println("*********** invoke migrateSSRSnapshotSUMARrate method the ssrSnapshotId =  "+ rs2.getInt("newSSRSnapshotId") +  "*************");
						if(this.migrateSSRSnapshotSUMARrate(RM2SSRSnapshotId, RM3SSRSnapshotId) == MIGRATION_ERROR) return MIGRATION_ERROR;
						
						System.out.println("*********** invoke migrateSSRSnapshotSUWklyMtg method the ssrSnapshotId =  "+ rs2.getInt("newSSRSnapshotId") +  "*************");
						if(this.migrateSSRSnapshotSUWklyMtg(RM2SSRSnapshotId, RM3SSRSnapshotId) == MIGRATION_ERROR) return MIGRATION_ERROR;
						
						System.out.println("*********** invoke migrateSSRSnapshotSUWklyMtgHD method the ssrSnapshotId =  "+ rs2.getInt("newSSRSnapshotId") +  "*************");
						if(this.migrateSSRSnapshotSUWklyMtgHD(RM2SSRSnapshotId, RM3SSRSnapshotId) == MIGRATION_ERROR) return MIGRATION_ERROR;
						
						System.out.println("*********** invoke migrateSSRSnapshotSUWklyMtgSS method the ssrSnapshotId =  "+ rs2.getInt("newSSRSnapshotId") +  "*************");
						if(this.migrateSSRSnapshotSUWklyMtgSS(RM2SSRSnapshotId, RM3SSRSnapshotId) == MIGRATION_ERROR) return MIGRATION_ERROR;
						
						System.out.println("*********** invoke migrateSSRSnapshotDayStar method the ssrSnapshotId =  "+ rs2.getInt("newSSRSnapshotId") +  "*************");
						if(this.migrateSSRSnapshotDayStar(RM2SSRSnapshotId, RM3SSRSnapshotId) == MIGRATION_ERROR) return MIGRATION_ERROR;
						
						System.out.println("*********** invoke migrateSSRSnapshotDayStarData method the ssrSnapshotId =  "+ rs2.getInt("newSSRSnapshotId") +  "*************");
						if(this.migrateSSRSnapshotDayStarData(RM2SSRSnapshotId, RM3SSRSnapshotId) == MIGRATION_ERROR) return MIGRATION_ERROR;
						
						System.out.println("*********** invoke migrateSSRSnapshotDayStarHotel method the ssrSnapshotId =  "+ rs2.getInt("newSSRSnapshotId") +  "*************");
						if(this.migrateSSRSnapshotDayStarHotel(RM2SSRSnapshotId, RM3SSRSnapshotId) == MIGRATION_ERROR) return MIGRATION_ERROR;
						
						System.out.println("*********** invoke migrateSSRSnapshotStarData method the ssrSnapshotId =  "+ rs2.getInt("newSSRSnapshotId") +  "*************");
						if(this.migrateSSRSnapshotStarData(RM2SSRSnapshotId, RM3SSRSnapshotId) == MIGRATION_ERROR) return MIGRATION_ERROR;
						
						System.out.println("*********** invoke migrateSSRSnapshotCOTB method the ssrSnapshotId =  "+ rs2.getInt("newSSRSnapshotId") +  "*************");
						if(this.migrateSSRSnapshotCOTB(RM2SSRSnapshotId, RM3SSRSnapshotId) == MIGRATION_ERROR) return MIGRATION_ERROR;
						
						System.out.println("*********** invoke migrateSSRSnapshotGroupPace method the ssrSnapshotId =  "+ rs2.getInt("newSSRSnapshotId") +  "*************");
						if(this.migrateSSRSnapshotGroupPace(RM2SSRSnapshotId, RM3SSRSnapshotId)== MIGRATION_ERROR) return MIGRATION_ERROR;
						
					}
					else{
						System.out.println("***********Error: the generated snapshotId =  "+ RM3SSRSnapshotId +  "*************");	
						return MIGRATION_ERROR;
					}
					
				}else	return MIGRATION_ERROR;;
				
						
			}else return MIGRATION_ERROR;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("*********** Exception while calling DB *************");
			e.printStackTrace();
			deleteMigratedSSRSnashot(RM3SSRSnapshotId);
			return MIGRATION_ERROR;
		}
		
		finally {close(rs, ps, RM2Conn); close(rs2, ps, RM3Conn);}
		
		System.out.println("**************** END - migrateSSRSnapshotRm2ToRm3 for RM2SSRSnapshotId = " +RM2SSRSnapshotId+ " RM3SSRSnapshotId : " + RM3SSRSnapshotId +" ******************");
		
		return (result == MIGRATION_WARNING)? MIGRATION_WARNING : MIGRATION_OK;
		
	}
	
	private int migrateSSRSnapshotTransientData(int RM2SSRSnapshotId,int RM3SSRSnapshotId){
		PreparedStatement ps = null;
		ResultSet rs = null;		
		int isCompleted = MIGRATION_OK;
		Connection RM2Conn = null;		
				
		try
		{
			System.out.println("*********** Start migrateSSRSnapshotTransientData method  *************");
			RM2Conn = this.getCurrentRM2Connection();
										
									
			String statement = "select hotel_id,statdate,comments,isException,dow,isActual,tot_occ_pct," +
							   "tot_adr,tot_rev_par,tot_occ_rooms,tot_rev,gtot_occ,gtot_rev,gtot_adr,gdef_occ," +
							   "gdef_rev,gdef_adr,gten_occ,gten_rev,gten_adr,created_ts " +
							   "from ssrSnapshot_TransientData where ssrSnapshot_id = ?";
									
			
			ps = RM2Conn.prepareStatement(statement);			
			ps.setInt(1, RM2SSRSnapshotId);
						
			System.out.println("*********** Statement created *************");	
			
			System.out.println(statement + RM2SSRSnapshotId);	
			
			rs = ps.executeQuery();
			
			System.out.println("*********** Query executed *************");
			
			if(rs != null){
				if(rs.next()){
					
				do{
									
					SnapshotTransientData std = new SnapshotTransientData();
					
					System.out.println("**************** Getting SSRSnapshotTransientData ******************");
					std.setSnapshotId(RM3SSRSnapshotId);
					std.setHotelId(rs.getInt("hotel_id"));
					std.setSnapshotId(RM3SSRSnapshotId);
					std.setStatdate(rs.getString("statdate"));
					std.setComments(rs.getString("comments"));
					std.setIsException(rs.getInt("isException"));
					std.setDow(rs.getString("dow"));
					std.setIsActual(rs.getInt("isActual"));
					std.setTotOccPct(rs.getBigDecimal("tot_occ_pct"));
					std.setTotAdr(rs.getBigDecimal("tot_adr"));
					std.setTotRevPar(rs.getBigDecimal("tot_rev_par"));
					std.setTotOccRooms(rs.getBigDecimal("tot_occ_rooms"));
					std.setTotRev(rs.getBigDecimal("tot_rev"));
					std.setGtotOcc(rs.getBigDecimal("gtot_occ"));
					std.setGtotRev(rs.getBigDecimal("gtot_rev"));
					std.setGtotAdr(rs.getBigDecimal("gtot_adr"));
					std.setGdefOcc(rs.getBigDecimal("gdef_occ"));
					std.setGdefRev(rs.getBigDecimal("gdef_rev"));
					std.setGdefAdr(rs.getBigDecimal("gdef_adr"));
					std.setGtenOcc(rs.getBigDecimal("gten_occ"));
					std.setGtenRev(rs.getBigDecimal("gten_rev"));
					std.setGtenAdr(rs.getBigDecimal("gten_adr"));
					std.setCreatedTs(rs.getString("created_ts"));
					
					isCompleted = insertSSRSnapshotTransientData(std);
					
					if (isCompleted == MIGRATION_ERROR){						
						return MIGRATION_ERROR;
					}					
				  }while (rs.next());
				}	
			}
			System.out.println("**************** SSRSnapshotTransientData Migrated for RM2SnapshotId = " +RM2SSRSnapshotId+ " ******************");			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("*********** Exception while calling DB *************");
			e.printStackTrace();
			return MIGRATION_ERROR;
		}
		
		finally {close(rs, ps, RM2Conn);}
		
			return isCompleted;		
	}
	
	private int insertSSRSnapshotTransientData (SnapshotTransientData std){
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;		
	
		try
		{
			System.out.println("*********** Start insertSSRSnapshotTransientData method  *************");
			conn = this.getCurrentHRR3Connection();
			
			String statement = "insert into RM3SSRSnapshotTransientData(ssrSnapshot_id,hotel_id,statdate,comments,isException,dow,isActual," +
							   "tot_occ_pct,tot_adr,tot_rev_par,tot_occ_rooms,tot_rev,gtot_occ,gtot_rev,gtot_adr,gdef_occ,gdef_rev,gdef_adr," +
							   "gten_occ,gten_rev,gten_adr,created_ts) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			ps = conn.prepareStatement(statement);			
			ps.setInt(1, std.getSnapshotId()); //ssrSnapshotId
			ps.setInt(2, std.getHotelId());
			ps.setString(3, std.getStatdate());
			ps.setString(4, std.getComments());
			ps.setInt(5, std.getIsException());
			ps.setString(6, std.getDow());
			ps.setInt(7, std.getIsActual());
			
			ps.setBigDecimal(8, std.getTotOccPct());
			ps.setBigDecimal(9, std.getTotAdr());
			ps.setBigDecimal(10, std.getTotRevPar());
			ps.setBigDecimal(11, std.getTotOccRooms());
			ps.setBigDecimal(12, std.getTotRev());
			ps.setBigDecimal(13, std.getGtotOcc());
			ps.setBigDecimal(14, std.getGtotRev());
			ps.setBigDecimal(15, std.getGtotAdr());
			ps.setBigDecimal(16, std.getGdefOcc());
			ps.setBigDecimal(17, std.getGdefRev());
			ps.setBigDecimal(18, std.getGdefAdr());
			
			ps.setBigDecimal(19, std.getGtenOcc());
			ps.setBigDecimal(20, std.getGtenRev());
			ps.setBigDecimal(21, std.getGtenAdr());
			ps.setString(22, std.getCreatedTs());
			
			System.out.println("*********** Statement created *************");	
			
			
			ps.executeUpdate();
			
			System.out.println("*********** Insert executed *************");
				
			return  MIGRATION_OK;
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("*********** Exception while calling DB *************");
			deleteMigratedSSRSnashot(std.getSnapshotId());
			e.printStackTrace();	
			return MIGRATION_ERROR;
		}	
		finally { close(rs, ps);}
				
	}
	
	@SuppressWarnings("resource")
	private int migrateSSRSnapshotTransientSegmentData (int RM2SSRSnapshotId, int RM3SSRSnapshotId, HashMap<Integer, String> segmentsMapped){
		PreparedStatement ps = null;
		ResultSet rs = null;		
		int isCompleted = MIGRATION_OK;
		Connection RM2Conn = null;	
		boolean warnings = false;
				
		try
		{
			System.out.println("*********** Start migrateSSRSnapshotTransientSegmentData method  *************");
			RM2Conn = this.getCurrentRM2Connection();
															
			String statement = "select hotel_id,segment_id,statdate,type,tot_occ,tot_rev,tot_adr,def_occ,def_rev,def_adr," +
					           "ten_occ,ten_rev,ten_adr,created_ts,admin from ssrSnapshot_TransientSegmentData where ssrSnapshot_id = ?";
			
			ps = RM2Conn.prepareStatement(statement);			
			ps.setInt(1, RM2SSRSnapshotId);
			System.out.println("*********** Statement created *************");	
			
			System.out.println(statement + RM2SSRSnapshotId);	
			
			rs = ps.executeQuery();
			
			System.out.println("*********** Query executed *************");
			
			if(rs != null){
				if(rs.next()){
					
					do{
						System.out.println("**************** Getting SSRSnapshotTransientSegmentData ******************");
						SnapshotTransientSegmentData stsd = new SnapshotTransientSegmentData();
						
						stsd.setSnapshotId(RM3SSRSnapshotId); //ssrSnapshotId						
						stsd.setHotelId(rs.getInt("hotel_id"));
						stsd.setSegmentId(rs.getInt("segment_id"));
						stsd.setStatdate(rs.getString("statdate"));
						stsd.setType(rs.getString("type"));
						stsd.setTotOcc(rs.getBigDecimal("tot_occ"));
						stsd.setTotRev(rs.getBigDecimal("tot_rev"));
						stsd.setTotAdr(rs.getBigDecimal("tot_adr"));
						stsd.setDefOcc(rs.getBigDecimal("def_occ"));
						stsd.setDefRev(rs.getBigDecimal("def_rev"));
						stsd.setDefAdr(rs.getBigDecimal("def_adr"));
						stsd.setTenOcc(rs.getBigDecimal("ten_occ"));
						stsd.setTenRev(rs.getBigDecimal("ten_rev"));
						stsd.setTenAdr(rs.getBigDecimal("ten_adr"));
						stsd.setCreatedTs(rs.getString("created_ts"));
						stsd.setAdmin(rs.getInt("admin"));
						
						if(segmentsMapped.containsKey(stsd.getSegmentId())){
							isCompleted = insertSSRSnapshotTransientSegmentData(stsd, segmentsMapped);
							
							if (isCompleted == MIGRATION_ERROR){						
								return MIGRATION_ERROR;
							}
						}else{
							System.out.println("**************** migrateSSRSnapshotTransientSegmentData -> WARNING  SegmentId : " + stsd.getSegmentId() + " NOT FOUNDED  ******************");
							warnings = true;
						}
						
						
						
					}while(rs.next());
				}
			}	
			
			System.out.println("**************** SSRSnapshotTransientSegmentData Migrated for RM2SnapshotId = " +RM2SSRSnapshotId+ " ******************");
			
			if (isCompleted == MIGRATION_OK & warnings == true){
				return MIGRATION_WARNING;
			}
			
			return isCompleted;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("*********** Exception while calling DB *************");
			e.printStackTrace();
			return MIGRATION_ERROR;
		}
		
		finally {close(rs, ps, RM2Conn);}						
	}
	
	private int insertSSRSnapshotTransientSegmentData (SnapshotTransientSegmentData stsd, HashMap<Integer, String> segmensMapped){
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;		
	
		try
		{
			System.out.println("*********** Start insertSnapshotTransientSegmentData method  *************");
			conn = this.getCurrentHRR3Connection();
			
			String statement = "insert into RM3SSRSnapshotTransientSegmentData (ssrSnapshot_id, hotel_id,segment_id,statdate,type,tot_occ,tot_rev,tot_adr," +
					           "def_occ,def_rev,def_adr,ten_occ,ten_rev,ten_adr,created_ts,admin) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
										                                   
			
			ps = conn.prepareStatement(statement);			
			ps.setInt(1, stsd.getSnapshotId());
			ps.setInt(2, stsd.getHotelId());
			System.out.println("***********RM2 SegmentId - "+ stsd.getSegmentId() +" *************");
			
			String tmpSegmentId = segmensMapped.get(stsd.getSegmentId());
			int itmpSegmentId = Integer.parseInt(tmpSegmentId);
			
			System.out.println("***********RM3 SegmentId String - int : "+ tmpSegmentId + " - " + itmpSegmentId +" *************");	
			ps.setInt(3, itmpSegmentId);			
			
			ps.setString(4, stsd.getStatdate());
			ps.setString(5, stsd.getType());
			ps.setBigDecimal(6, stsd.getTotOcc());
			ps.setBigDecimal(7, stsd.getTotRev());
			ps.setBigDecimal(8, stsd.getTotAdr());
			ps.setBigDecimal(9, stsd.getDefOcc());
			ps.setBigDecimal(10, stsd.getDefRev());
			ps.setBigDecimal(11, stsd.getDefAdr());
			ps.setBigDecimal(12, stsd.getTenOcc());
			ps.setBigDecimal(13, stsd.getTenRev());
			ps.setBigDecimal(14, stsd.getTenAdr());
			ps.setString(15, stsd.getCreatedTs());
			ps.setInt(16, stsd.getAdmin());
			
			System.out.println("*********** Statement created *************");	
			
			
			ps.executeUpdate();
			
			System.out.println("*********** Insert SSRSnapshotTransientSegmentData executed *************");
			
			return  MIGRATION_OK;
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("*********** Exception while calling DB *************");
			deleteMigratedSSRSnashot(stsd.getSnapshotId());
			e.printStackTrace();	
			return MIGRATION_ERROR;
		}
		//Close only rs and ps to keep RM3 connection alive
		finally { close(rs, ps);}			
	}
	
	@SuppressWarnings("resource")
	private int migrateSSRSnapshotData (int RM2SSRSnapshotId, int RM3SSRSnapshotId){
		PreparedStatement ps = null;
		ResultSet rs = null;		
		int isCompleted = MIGRATION_OK;
		Connection RM2Conn = null;	
		
				
		try
		{
			System.out.println("*********** Start migrateSSRSnapshotData method  *************");
			RM2Conn = this.getCurrentRM2Connection();												
			
			String statement = "select ssrSnapshot_id, hotel_id, statdate, comments, isException, dow, isActual, ratecat1, " +
							   "ratecat2, ratecat3, ratecat4, ratecat5, ratecat6, ratecat7, ratecat8, ratecat9, hp, hp2, oversell_factor, " +
							   "lrr1, lrr2, lrr3, lrr4, lrr5, lrr6, lrr7, lrr8, lrr9, lrr, lrrhp1, lrrhp2, leadtime, MARrate, SeasonalMARrate, " +
							   "overrides, created_ts, modified_ts " +
							   "from ssrSnapshot_Data where ssrSnapshot_id = ?";
			
			ps = RM2Conn.prepareStatement(statement);			
			ps.setInt(1, RM2SSRSnapshotId);
			System.out.println("*********** Statement created *************");	
			
			System.out.println(statement + RM2SSRSnapshotId);	
			
			rs = ps.executeQuery();
			
			System.out.println("*********** Query executed *************");
			
			if(rs != null){
				if(rs.next()){
					
					do{
						System.out.println("**************** Getting SSRSnapshotData ******************");
						SSRSnapshotData ssrSData = new SSRSnapshotData();
						
						ssrSData.setSsrSnapshotId(RM3SSRSnapshotId);
						ssrSData.setHotelId(rs.getInt("hotel_id"));
						ssrSData.setStatdate(rs.getString("statdate"));
						ssrSData.setComments(rs.getString("comments"));
						ssrSData.setIsException(rs.getInt("isException"));
						ssrSData.setDow(rs.getString("dow"));
						ssrSData.setIsActual(rs.getInt("isActual"));
						ssrSData.setRatecat1(rs.getString("ratecat1"));
						ssrSData.setRatecat2(rs.getString("ratecat2"));
						ssrSData.setRatecat3(rs.getString("ratecat3"));
						ssrSData.setRatecat4(rs.getString("ratecat4"));
						ssrSData.setRatecat5(rs.getString("ratecat5"));
						ssrSData.setRatecat6(rs.getString("ratecat6"));
						ssrSData.setRatecat7(rs.getString("ratecat7"));
						ssrSData.setRatecat8(rs.getString("ratecat8"));
						ssrSData.setRatecat9(rs.getString("ratecat9"));
						ssrSData.setHp(rs.getString("hp"));
						ssrSData.setHp2(rs.getString("hp2"));
						ssrSData.setOversell_factor(rs.getString("oversell_factor"));
						ssrSData.setLrr1(rs.getBigDecimal("lrr1"));
						ssrSData.setLrr2(rs.getBigDecimal("lrr2"));
						ssrSData.setLrr3(rs.getBigDecimal("lrr3"));
						ssrSData.setLrr4(rs.getBigDecimal("lrr4"));
						ssrSData.setLrr5(rs.getBigDecimal("lrr5"));
						ssrSData.setLrr6(rs.getBigDecimal("lrr6"));
						ssrSData.setLrr7(rs.getBigDecimal("lrr7"));
						ssrSData.setLrr8(rs.getBigDecimal("lrr8"));
						ssrSData.setLrr9(rs.getBigDecimal("lrr9"));
						ssrSData.setLrr(rs.getString("lrr"));
						ssrSData.setLrrhp1(rs.getBigDecimal("lrrhp1"));
						ssrSData.setLrrhp2(rs.getBigDecimal("lrrhp2"));
						ssrSData.setLeadtime(rs.getInt("leadtime"));
						ssrSData.setMARrate(rs.getString("MARrate"));
						ssrSData.setSeasonalMARrate(rs.getBigDecimal("SeasonalMARrate"));
						ssrSData.setOverrides(rs.getString("overrides"));
						ssrSData.setCreatedTs(rs.getString("created_ts"));
						ssrSData.setModifiedTs(rs.getString("modified_ts"));
																																						
						isCompleted = insertSSRSnapshotData(ssrSData);
							
						if (isCompleted == MIGRATION_ERROR){						
								return MIGRATION_ERROR;
						}						
												
						
					}while(rs.next());
				}
			}	
			
			System.out.println("**************** SSRSnapshotData Migrated for RM2SnapshotId = " +RM2SSRSnapshotId+ " ******************");						
			
			return isCompleted;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("*********** Exception while calling DB *************");
			e.printStackTrace();
			return MIGRATION_ERROR;
		}
		
		finally {close(rs, ps, RM2Conn);}						
	}
	
	private int insertSSRSnapshotData (SSRSnapshotData ssrSnapshotData){
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;		
	
		try
		{
			System.out.println("*********** Start insertSSRSnapshotData method  *************");
			conn = this.getCurrentHRR3Connection();
			
			String statement = "insert into RM3SSRSnapshotData (ssrSnapshot_id, hotel_id, statdate, comments, isException, dow, isActual," +
							   "ratecat1, ratecat2, ratecat3, ratecat4, ratecat5, ratecat6, ratecat7, ratecat8, ratecat9, hp, hp2, oversell_factor, " +
							   "lrr1, lrr2, lrr3, lrr4, lrr5, lrr6, lrr7, lrr8, lrr9, lrr, lrrhp1, lrrhp2, leadtime, MARrate, SeasonalMARrate, " +
							   "overrides, created_ts, modified_ts) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
										                                   
			
			ps = conn.prepareStatement(statement);			
			ps.setInt(1, ssrSnapshotData.getSsrSnapshotId());
			ps.setInt(2, ssrSnapshotData.getHotelId());													
			ps.setString(3, ssrSnapshotData.getStatdate());			
			ps.setString(4, ssrSnapshotData.getComments());
			ps.setInt(5, ssrSnapshotData.getIsException());
			ps.setString(6, ssrSnapshotData.getDow());
			ps.setInt(7, ssrSnapshotData.getIsActual());
			ps.setString(8, ssrSnapshotData.getRatecat1());
			ps.setString(9, ssrSnapshotData.getRatecat2());
			ps.setString(10, ssrSnapshotData.getRatecat3());
			ps.setString(11, ssrSnapshotData.getRatecat4());
			ps.setString(12, ssrSnapshotData.getRatecat5());
			ps.setString(13, ssrSnapshotData.getRatecat6());
			ps.setString(14, ssrSnapshotData.getRatecat7());
			ps.setString(15, ssrSnapshotData.getRatecat8());
			ps.setString(16, ssrSnapshotData.getRatecat9());
			ps.setString(17, ssrSnapshotData.getHp());
			ps.setString(18, ssrSnapshotData.getHp2());
			ps.setString(19, ssrSnapshotData.getOversell_factor());
			ps.setBigDecimal(20, ssrSnapshotData.getLrr1());
			ps.setBigDecimal(21, ssrSnapshotData.getLrr2());
			ps.setBigDecimal(22, ssrSnapshotData.getLrr3());
			ps.setBigDecimal(23, ssrSnapshotData.getLrr4());
			ps.setBigDecimal(24, ssrSnapshotData.getLrr5());
			ps.setBigDecimal(25, ssrSnapshotData.getLrr6());
			ps.setBigDecimal(26, ssrSnapshotData.getLrr7());
			ps.setBigDecimal(27, ssrSnapshotData.getLrr8());
			ps.setBigDecimal(28, ssrSnapshotData.getLrr9());
			ps.setString(29, ssrSnapshotData.getLrr());
			ps.setBigDecimal(30, ssrSnapshotData.getLrrhp1());
			ps.setBigDecimal(31, ssrSnapshotData.getLrrhp2());
			ps.setInt(32, ssrSnapshotData.getLeadtime());
			ps.setString(33, ssrSnapshotData.getMARrate());
			ps.setBigDecimal(34, ssrSnapshotData.getSeasonalMARrate());
			ps.setString(35,ssrSnapshotData.getOverrides());			
			ps.setString(36, ssrSnapshotData.getCreatedTs());
			ps.setString(37, ssrSnapshotData.getModifiedTs());
			
			
			System.out.println("*********** Statement created *************");	
			
			
			ps.executeUpdate();
			
			System.out.println("*********** Insert insertSSRSnapshotData executed *************");
			
			return  MIGRATION_OK;
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("*********** Exception while calling DB *************");
			deleteMigratedSSRSnashot(ssrSnapshotData.getSsrSnapshotId());
			e.printStackTrace();	
			return MIGRATION_ERROR;
		}
		//Close only rs and ps to keep RM3 connection alive
		finally { close(rs, ps);}			
	}
	
	@SuppressWarnings("resource")
	private int migrateSSRSnapshotSegmentData (int RM2SSRSnapshotId, int RM3SSRSnapshotId, HashMap<Integer, String> SSRSegmentsMapped){
		PreparedStatement ps = null;
		ResultSet rs = null;		
		int isCompleted = MIGRATION_OK;
		Connection RM2Conn = null;	
		boolean warnings = false;
				
		try
		{
			System.out.println("*********** Start migrateSSRSnapshotSegmentData method  *************");
			RM2Conn = this.getCurrentRM2Connection();
										
			String statement = "Select ssrSnapshot_id, hotel_id, segment_id, statdate, tot_occ, tot_rev, tot_adr, created_ts, modified_ts " +
					           "FROM ssrSnapshot_SegmentData WHERE ssrSnapshot_id =?";
			
			ps = RM2Conn.prepareStatement(statement);			
			ps.setInt(1, RM2SSRSnapshotId);
			
			System.out.println("*********** Statement created *************");				
			System.out.println(statement + RM2SSRSnapshotId);	
			
			rs = ps.executeQuery();
			
			System.out.println("*********** Query executed *************");
			
			if(rs != null){
				if(rs.next()){
					
					do{
						System.out.println("**************** Getting SSRSnapshotSegmentData ******************");
						SSRSnapshotSegmentData ssd = new SSRSnapshotSegmentData();
						
						ssd.setSsrSnapshotId(RM3SSRSnapshotId);
						ssd.setHotelId(rs.getInt("hotel_id"));
						ssd.setSegmentId(rs.getInt("segment_id"));
						ssd.setStatdate(rs.getString("statdate"));
						ssd.setTotOcc(rs.getBigDecimal("tot_occ"));
						ssd.setTotRev(rs.getBigDecimal("tot_rev"));
						ssd.setTotAdr(rs.getBigDecimal("tot_adr"));
						ssd.setCreatedTs(rs.getString("created_ts"));
						ssd.setModifiedTs(rs.getString("modified_ts"));
												
						
						if(SSRSegmentsMapped.containsKey(ssd.getSegmentId())){
							isCompleted = insertSSRSnapshotSegmentData(ssd, SSRSegmentsMapped);
							
							if (isCompleted == MIGRATION_ERROR){						
								return MIGRATION_ERROR;
							}
						}else{
							System.out.println("**************** migrateSSRSnapshotSegmentData -> WARNING  SegmentId : " + ssd.getSegmentId() + " NOT FOUNDED  ******************");
							warnings = true;
						}
						
						
						
					}while(rs.next());
				}
			}	
			
			System.out.println("**************** migrateSSRSnapshotSegmentData Migrated for RM2SnapshotId = " +RM2SSRSnapshotId+ " ******************");
			
			if (isCompleted == MIGRATION_OK & warnings == true){
				return MIGRATION_WARNING;
			}
			
			return isCompleted;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("*********** Exception while calling DB *************");
			e.printStackTrace();
			return MIGRATION_ERROR;
		}
		
		finally {close(rs, ps, RM2Conn);}						
	}
	
	private int insertSSRSnapshotSegmentData (SSRSnapshotSegmentData ssd, HashMap<Integer, String> SSRSegmensMapped){
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;		
	
		try
		{
			System.out.println("*********** Start insertSSRSnapshotSegmentData method  *************");
			conn = this.getCurrentHRR3Connection();
			
			String statement = "insert into RM3SSRSnapshotSegmentData (ssrSnapshot_id, hotel_id, segment_id, statdate, tot_occ, tot_rev, " +
							   "tot_adr, created_ts, modified_ts) values (?,?,?,?,?,?,?,?,?)";	
										                                   
			
			ps = conn.prepareStatement(statement);			
			ps.setInt(1, ssd.getSsrSnapshotId());
			ps.setInt(2, ssd.getHotelId());
			
			System.out.println("***********RM2 SSRSegmentId - "+ ssd.getSegmentId() +" *************");
			
			String tmpSegmentId = SSRSegmensMapped.get(ssd.getSegmentId());
			int itmpSegmentId = Integer.parseInt(tmpSegmentId);
			
			System.out.println("***********SSRSegmentId String - int : "+ tmpSegmentId + " - " + itmpSegmentId +" *************");	
			ps.setInt(3, itmpSegmentId);			
			
			ps.setString(4, ssd.getStatdate());			
			ps.setBigDecimal(5, ssd.getTotOcc());
			ps.setBigDecimal(6, ssd.getTotRev());
			ps.setBigDecimal(7, ssd.getTotAdr());			
			ps.setString(8, ssd.getCreatedTs());
			ps.setString(9, ssd.getModifiedTs());
			
			System.out.println("*********** Statement created *************");	
			
			
			ps.executeUpdate();
			
			System.out.println("*********** Insert SSRSnapshotSegmentData executed *************");
			
			return  MIGRATION_OK;
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("*********** Exception while calling DB *************");
			deleteMigratedSSRSnashot(ssd.getSsrSnapshotId());
			e.printStackTrace();	
			return MIGRATION_ERROR;
		}
		
		finally { close(rs, ps);}			
	}
	
	private int migrateSSRSnapshotSUMARrate (int RM2SSRSnapshotId, int RM3SSRSnapshotId){
		PreparedStatement ps = null;
		ResultSet rs = null;		
		int isCompleted = MIGRATION_OK;
		Connection RM2Conn = null;	
		boolean warnings = false;
				
		try
		{
			System.out.println("*********** Start migrateSSRSnapshotSUMARrate method  *************");
			RM2Conn = this.getCurrentRM2Connection();												
			
			String statement = "select ssrSnapshot_id, hotel_id, sun, mon, tue, wed, thu, fri, sat, cpor, occ_tfdr, modified_ts " +
					           " from ssrSnapshot_SU_MARrate where ssrSnapshot_id = ?";
			
			ps = RM2Conn.prepareStatement(statement);			
			ps.setInt(1, RM2SSRSnapshotId);
			System.out.println("*********** Statement created *************");	
			
			System.out.println(statement + RM2SSRSnapshotId);	
			
			rs = ps.executeQuery();
			
			System.out.println("*********** Query executed *************");
			
			if(rs != null){
				if(rs.next()){
					
					do{
						System.out.println("**************** Getting SSRSnapshotSUMARrate ******************");						
						
						SSRSnapshotSUMARrate sumarrate = new SSRSnapshotSUMARrate();
						sumarrate.setSsrSnapshotId(RM3SSRSnapshotId);
						sumarrate.setHotelId(rs.getInt("hotel_id"));
						sumarrate.setSun(rs.getInt("sun"));
						sumarrate.setMon(rs.getInt("mon"));
						sumarrate.setTue(rs.getInt("tue"));
						sumarrate.setWed(rs.getInt("wed"));
						sumarrate.setThu(rs.getInt("thu"));
						sumarrate.setFri(rs.getInt("fri"));
						sumarrate.setSat(rs.getInt("sat"));
						sumarrate.setCpor(rs.getBigDecimal("cpor"));
						sumarrate.setOccTfdr(rs.getBigDecimal("occ_tfdr"));
						sumarrate.setModifiedTs(rs.getString("modified_ts"));
						
						
																																						
						isCompleted = insertSSRSnapshotSUMARrate(sumarrate);
							
						if (isCompleted == MIGRATION_ERROR){						
								return MIGRATION_ERROR;
						}						
												
						
					}while(rs.next());
				}
			}	
			
			System.out.println("**************** SSRSnapshotSUMARrate Migrated for RM2SnapshotId = " +RM2SSRSnapshotId+ " ******************");
						
			return isCompleted;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("*********** Exception while calling DB *************");
			e.printStackTrace();
			return MIGRATION_ERROR;
		}
		
		finally {close(rs, ps, RM2Conn);}						
	}
	
	private int insertSSRSnapshotSUMARrate (SSRSnapshotSUMARrate sumarate){
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;		
	
		try
		{
			System.out.println("*********** Start insertSSRSnapshotSUMARrate method  *************");
			conn = this.getCurrentHRR3Connection();
			
			String statement = "INSERT INTO RM3SSRSnapshotSUMARrate(ssrSnapshot_id, hotel_id, sun, mon, tue, wed, thu, fri, " +
							   "sat, cpor, occ_tfdr, modified_ts) values (?,?,?,?,?,?,?,?,?,?,?,?)";					                                   
			
			ps = conn.prepareStatement(statement);	
			
			ps.setInt(1, sumarate.getSsrSnapshotId());
			ps.setInt(2, sumarate.getHotelId());
			ps.setInt(3, sumarate.getSun());
			ps.setInt(4, sumarate.getMon());
			ps.setInt(5, sumarate.getTue());
			ps.setInt(6, sumarate.getWed());
			ps.setInt(7, sumarate.getThu());
			ps.setInt(8, sumarate.getFri());
			ps.setInt(9, sumarate.getSat());
			ps.setBigDecimal(10, sumarate.getCpor());
			ps.setBigDecimal(11, sumarate.getOccTfdr());
			ps.setString(12, sumarate.getModifiedTs());
									
			
			System.out.println("*********** Statement created *************");	
			
			
			ps.executeUpdate();
			
			System.out.println("*********** Insert SSRSnapshotSUMARrate executed *************");
			
			return  MIGRATION_OK;
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("*********** Exception while calling DB *************");
			deleteMigratedSSRSnashot(sumarate.getSsrSnapshotId());
			e.printStackTrace();	
			return MIGRATION_ERROR;
		}
		
		finally { close(rs, ps);}			
	}
	
	
	private int migrateSSRSnapshotSUWklyMtg (int RM2SSRSnapshotId, int RM3SSRSnapshotId){
		PreparedStatement ps = null;
		ResultSet rs = null;		
		int isCompleted = MIGRATION_OK;
		Connection RM2Conn = null;	
		boolean warnings = false;
				
		try
		{
			System.out.println("*********** Start migrateSSRSnapshotSUWklyMtg method  *************");
			RM2Conn = this.getCurrentRM2Connection();												
			
			String statement = 	"select ssrSnapshot_id, hotel_id, su_month, su_year, wk_no, currmeetdate, lastwkdate, lastactdate, snapshot_id, " +
								"attendees, critique, outlook, saved_snapshotid, other from ssrSnapshot_SU_WklyMtg where ssrSnapshot_id = ?";
			
			ps = RM2Conn.prepareStatement(statement);			
			ps.setInt(1, RM2SSRSnapshotId);
			System.out.println("*********** Statement created *************");	
			
			System.out.println(statement + RM2SSRSnapshotId);	
			
			rs = ps.executeQuery();
			
			System.out.println("*********** Query executed *************");
			
			if(rs != null){
				if(rs.next()){
					
					do{
						System.out.println("**************** Getting SRSnapshotSUWklyMtg ******************");	
						
						SSRSnapshotSUWklyMtg suwklymtg = new SSRSnapshotSUWklyMtg();
						
						suwklymtg.setSsrSnapshotId(RM3SSRSnapshotId);
						suwklymtg.setHotelId(rs.getInt("hotel_id"));
						suwklymtg.setSuMonth(rs.getInt("su_month"));
						suwklymtg.setSuYear(rs.getInt("su_year"));
						suwklymtg.setWkNo(rs.getString("wk_no"));
						suwklymtg.setCurrmeetdate(rs.getString("currmeetdate"));
						suwklymtg.setLastwkdate(rs.getString("lastwkdate"));
						suwklymtg.setLastactdate(rs.getString("lastactdate"));
						suwklymtg.setSnapshotId(rs.getInt("snapshot_id"));
						suwklymtg.setAttendees(rs.getString("attendees"));
						suwklymtg.setCritique(rs.getString("critique"));
						suwklymtg.setOutlook(rs.getString("outlook"));
						suwklymtg.setSavedSnapshotid(rs.getInt("saved_snapshotid"));
						suwklymtg.setOther(rs.getString("other"));
												
																																						
						isCompleted = insertSSRSnapshotSUWklyMtg(suwklymtg);
							
						if (isCompleted == MIGRATION_ERROR){						
								return MIGRATION_ERROR;
						}						
												
						
					}while(rs.next());
				}
			}	
			
			System.out.println("**************** SSRSnapshotSUWklyMtg (int RM2SSRSnapshotId, int RM3SSRSnapshotId) Migrated for RM2SnapshotId = " +RM2SSRSnapshotId+ " ******************");					
			
			return isCompleted;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("*********** Exception while calling DB *************");
			e.printStackTrace();
			return MIGRATION_ERROR;
		}
		
		finally {close(rs, ps, RM2Conn);}						
	}
	
	private int insertSSRSnapshotSUWklyMtg (SSRSnapshotSUWklyMtg suwklymtg){
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;		
	
		try
		{
			System.out.println("*********** Start insertSSRSnapshotSUWklyMtg method  *************");
			conn = this.getCurrentHRR3Connection();
			
			String statement = "INSERT INTO RM3SSRSnapshotSUWklyMtg( ssrSnapshot_id, hotel_id, su_month, su_year, wk_no, currmeetdate, " +
							   "lastwkdate, lastactdate, snapshot_id, attendees, critique, outlook, saved_snapshotid, other) " +
							   "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";					                                   
			
			ps = conn.prepareStatement(statement);	
			
			ps.setInt(1, suwklymtg.getSsrSnapshotId());
			ps.setInt(2, suwklymtg.getHotelId()); 
			ps.setInt(3, suwklymtg.getSuMonth());
			ps.setInt(4, suwklymtg.getSuYear());
			ps.setString(5, suwklymtg.getWkNo());
			ps.setString(6,	suwklymtg.getCurrmeetdate());
			ps.setString(7, suwklymtg.getLastwkdate());
			ps.setString(8, suwklymtg.getLastactdate());
			ps.setInt(9, suwklymtg.getSnapshotId());
			ps.setString(10, suwklymtg.getAttendees());
			ps.setString(11, suwklymtg.getCritique());
			ps.setString(12, suwklymtg.getOutlook());
			ps.setInt(13, suwklymtg.getSavedSnapshotid());
			ps.setString(14, suwklymtg.getOther());
			
									
			
			System.out.println("*********** Statement created *************");	
			
			
			ps.executeUpdate();
			
			System.out.println("*********** Insert SSRSnapshotSUWklyMtg executed *************");
			
			return  MIGRATION_OK;
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("*********** Exception while calling DB *************");
			deleteMigratedSSRSnashot(suwklymtg.getSsrSnapshotId());
			e.printStackTrace();	
			return MIGRATION_ERROR;
		}
		
		finally { close(rs, ps);}			
	}
	
	private int migrateSSRSnapshotSUWklyMtgHD(int RM2SSRSnapshotId, int RM3SSRSnapshotId){
		PreparedStatement ps = null;
		ResultSet rs = null;		
		int isCompleted = MIGRATION_OK;
		Connection RM2Conn = null;	
		boolean warnings = false;
				
		try
		{
			System.out.println("*********** Start migrateSSRSnapshotSUWklyMtgHD method  *************");
			RM2Conn = this.getCurrentRM2Connection();												
			
			String statement = 	"select ssrSnapshot_id, suwkly_id, hotel_id, peakdate, selling_rest, outcome from ssrSnapshot_SU_WklyMtg_HD " +
								"where ssrSnapshot_id = ?";
			
			ps = RM2Conn.prepareStatement(statement);			
			ps.setInt(1, RM2SSRSnapshotId);
			System.out.println("*********** Statement created *************");	
			
			System.out.println(statement + RM2SSRSnapshotId);	
			
			rs = ps.executeQuery();
			
			System.out.println("*********** Query executed *************");
			
			if(rs != null){
				if(rs.next()){
					
					do{
						System.out.println("**************** Getting SSRSnapshotSUWklyMtgHD ******************");	
						
						SSRSnapshotSUWklyMtgHD suwklymtgHd = new SSRSnapshotSUWklyMtgHD();
						
						suwklymtgHd.setSsrSnapshotId(RM3SSRSnapshotId);
						suwklymtgHd.setSuwklyId(rs.getInt("suwkly_id"));
						suwklymtgHd.setHotelId(rs.getInt("hotel_id"));  
						suwklymtgHd.setPeakdate(rs.getString("peakdate"));
						suwklymtgHd.setSellingRest(rs.getString("selling_rest"));
						suwklymtgHd.setOutcome(rs.getString("outcome"));						
																								
																																						
						isCompleted = insertSSRSnapshotSUWklyMtgHD(suwklymtgHd);
							
						if (isCompleted == MIGRATION_ERROR){						
								return MIGRATION_ERROR;
						}						
												
						
					}while(rs.next());
				}
			}	
			
			System.out.println("**************** SSRSnapshotSUWklyMtgHD Migrated for RM2SnapshotId = " +RM2SSRSnapshotId+ " ******************");
						
			return isCompleted;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("*********** Exception while calling DB *************");
			e.printStackTrace();
			return MIGRATION_ERROR;
		}
		
		finally {close(rs, ps, RM2Conn);}						
	}
	
	private int insertSSRSnapshotSUWklyMtgHD (SSRSnapshotSUWklyMtgHD suwklymtgHd){
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;		
	
		try
		{
			System.out.println("*********** Start insertSSRSnapshotSUWklyMtgHD method  *************");
			conn = this.getCurrentHRR3Connection();
			
			String statement = "INSERT INTO RM3SSRSnapshotSUWklyMtgHD(ssrSnapshot_id, suwkly_id, hotel_id, peakdate, selling_rest, outcome) values (?,?,?,?,?,?)";					                                   
			
			ps = conn.prepareStatement(statement);	
			
			ps.setInt(1, suwklymtgHd.getSsrSnapshotId());
			ps.setInt(2, suwklymtgHd.getSuwklyId());
			ps.setInt(3, suwklymtgHd.getHotelId()); 
			ps.setString(4, suwklymtgHd.getPeakdate());
			ps.setString(5, suwklymtgHd.getSellingRest());
			ps.setString(6, suwklymtgHd.getOutcome());
			
									
			
			System.out.println("*********** Statement created *************");	
			
			
			ps.executeUpdate();
			
			System.out.println("*********** Insert SSRSnapshotSUWklyMtgHD executed *************");
			
			return  MIGRATION_OK;
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("*********** Exception while calling DB *************");
			deleteMigratedSSRSnashot(suwklymtgHd.getSsrSnapshotId());
			e.printStackTrace();	
			return MIGRATION_ERROR;
		}
		
		finally { close(rs, ps);}			
	}
	
	private int migrateSSRSnapshotSUWklyMtgSS(int RM2SSRSnapshotId, int RM3SSRSnapshotId){
		PreparedStatement ps = null;
		ResultSet rs = null;		
		int isCompleted = MIGRATION_OK;
		Connection RM2Conn = null;	
		boolean warnings = false;
				
		try
		{
			System.out.println("*********** Start migrateSSRSnapshotSUWklyMtgSS method  *************");
			RM2Conn = this.getCurrentRM2Connection();												
			
			String statement = 	"select ssrSnapshot_id, suwkly_id, hotel_id, softspot, strategy from ssrSnapshot_SU_WklyMtg_SS " +
								"where ssrSnapshot_id = ?";
			
			ps = RM2Conn.prepareStatement(statement);			
			ps.setInt(1, RM2SSRSnapshotId);
			System.out.println("*********** Statement created *************");	
			
			System.out.println(statement + RM2SSRSnapshotId);	
			
			rs = ps.executeQuery();
			
			System.out.println("*********** Query executed *************");
			
			if(rs != null){
				if(rs.next()){
					
					do{
						System.out.println("**************** Getting SSRSnapshotSUWklyMtgSS ******************");	
						
						SSRSnapshotSUWklyMtgSS suwklymtgSs = new SSRSnapshotSUWklyMtgSS();
						
						suwklymtgSs.setSsrSnapshotId(RM3SSRSnapshotId);
						suwklymtgSs.setSuwklyId(rs.getInt("suwkly_id"));
						suwklymtgSs.setHotelId(rs.getInt("hotel_id"));
						suwklymtgSs.setSoftspot(rs.getString("softspot"));
						suwklymtgSs.setStrategy(rs.getString("strategy"));
																								
																																						
						isCompleted = insertSSRSnapshotSUWklyMtgSS(suwklymtgSs);
							
						if (isCompleted == MIGRATION_ERROR){						
								return MIGRATION_ERROR;
						}						
												
						
					}while(rs.next());
				}
			}	
			
			System.out.println("**************** SSRSnapshotSUWklyMtgSS Migrated for RM2SnapshotId = " +RM2SSRSnapshotId+ " ******************");
			
			return isCompleted;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("*********** Exception while calling DB *************");
			e.printStackTrace();
			return MIGRATION_ERROR;
		}
		
		finally {close(rs, ps, RM2Conn);}						
	}
	
	private int insertSSRSnapshotSUWklyMtgSS (SSRSnapshotSUWklyMtgSS suwklymtgSd){
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;		
	
		try
		{
			System.out.println("*********** Start insertSSRSnapshotSUWklyMtgSS method  *************");
			conn = this.getCurrentHRR3Connection();
			
			String statement = "INSERT INTO RM3SSRSnapshotSUWklyMtgSS(ssrSnapshot_id, hotel_id, softspot, strategy) values (?,?,?,?)";					                                   
			
			ps = conn.prepareStatement(statement);	
			
			ps.setInt(1, suwklymtgSd.getSsrSnapshotId());
			ps.setInt(2, suwklymtgSd.getHotelId()); 
			ps.setString(3, suwklymtgSd.getSoftspot());
			ps.setString(4, suwklymtgSd.getStrategy());
			

			System.out.println("*********** Statement created *************");	
			
			
			ps.executeUpdate();
			
			System.out.println("*********** Insert SSRSnapshotSUWklyMtgSS executed *************");
			
			return  MIGRATION_OK;
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("*********** Exception while calling DB *************");
			deleteMigratedSSRSnashot(suwklymtgSd.getSsrSnapshotId());
			e.printStackTrace();	
			return MIGRATION_ERROR;
		}
		//Close only rs and ps to keep RM3 connection alive
		finally { close(rs, ps);}			
	}
	
	private int migrateSSRSnapshotDayStar(int RM2SSRSnapshotId, int RM3SSRSnapshotId){
		PreparedStatement ps = null;
		ResultSet rs = null;		
		int isCompleted = MIGRATION_OK;
		Connection RM2Conn = null;	
		
				
		try
		{
			System.out.println("*********** Start migrateSSRSnapshotDayStar method  *************");
			RM2Conn = this.getCurrentRM2Connection();												
			
			String statement = 	"select ssrSnapshot_id, daystar_id, hotel_id, date_from, date_to, cap_hotel, cap_hotel2, cap_week, created_ts " +
								"from ssrSnapshot_DayStar where ssrSnapshot_id = ?";
			
			ps = RM2Conn.prepareStatement(statement);			
			ps.setInt(1, RM2SSRSnapshotId);
			System.out.println("*********** Statement created *************");	
			
			System.out.println(statement + RM2SSRSnapshotId);	
			
			rs = ps.executeQuery();
			
			System.out.println("*********** Query executed *************");
			
			if(rs != null){
				if(rs.next()){
					
					do{
						System.out.println("**************** Getting SSRSnapshotDayStar ******************");	
						
						SSRSnapshotDayStar ssrDayStar = new SSRSnapshotDayStar();
						
						ssrDayStar.setSsrSnapshotId(RM3SSRSnapshotId);
						ssrDayStar.setDayStartIdRM2(rs.getInt("daystar_id"));
						ssrDayStar.setHotelId(rs.getInt("hotel_id"));
						ssrDayStar.setDateFrom(rs.getString("date_from"));
						ssrDayStar.setDateTo(rs.getString("date_to"));
						ssrDayStar.setCapHotel(rs.getString("cap_hotel"));
						ssrDayStar.setCapHotel2(rs.getString("cap_hotel2"));
						ssrDayStar.setCapWeek(rs.getString("cap_week"));
						ssrDayStar.setCreatedTs(rs.getString("created_ts"));
																								
																																						
						isCompleted = insertSSRSnapshotDayStar(ssrDayStar);
							
						if (isCompleted == MIGRATION_ERROR){						
								return MIGRATION_ERROR;
						}						
												
						
					}while(rs.next());
				}
			}	
			
			System.out.println("**************** SSRSnapshotDayStar Migrated for RM2SnapshotId = " +RM2SSRSnapshotId+ " ******************");
					
			return isCompleted;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("*********** Exception while calling DB *************");
			e.printStackTrace();
			return MIGRATION_ERROR;
		}
		
		finally {close(rs, ps, RM2Conn);}						
	}
	
	private int insertSSRSnapshotDayStar (SSRSnapshotDayStar ssrDayStar){
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;		
	
		try
		{
			System.out.println("*********** Start insertSSRSnapshotDayStar method  *************");
			conn = this.getCurrentHRR3Connection();
			
			String statement = "INSERT INTO RM3SSRSnapshotDayStar(ssrSnapshot_id, daystar_id_RM2, hotel_id, date_from, date_to, cap_hotel, " +
								"cap_hotel2, cap_week, created_ts) " +
								"values (?,?,?,?,?,?,?,?,?)";					                                   
			
			ps = conn.prepareStatement(statement);	
			
			ps.setInt(1, ssrDayStar.getSsrSnapshotId());
			ps.setInt(2, ssrDayStar.getDayStartIdRM2()); 
			ps.setInt(3, ssrDayStar.getHotelId());
			ps.setString(4, ssrDayStar.getDateFrom());
			ps.setString(5, ssrDayStar.getDateTo());
			ps.setString(6, ssrDayStar.getCapHotel());
			ps.setString(7, ssrDayStar.getCapHotel2());
			ps.setString(8, ssrDayStar.getCapWeek());
			ps.setString(9,	ssrDayStar.getCreatedTs());
			

			System.out.println("*********** Statement created *************");	
			
			
			ps.executeUpdate();
			
			System.out.println("*********** Insert SSRSnapshotDayStar executed *************");
			
			return  MIGRATION_OK;
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("*********** Exception while calling DB *************");
			deleteMigratedSSRSnashot(ssrDayStar.getSsrSnapshotId());
			e.printStackTrace();	
			return MIGRATION_ERROR;
		}		
		finally { close(rs, ps);}			
	}
	
	private int migrateSSRSnapshotDayStarData(int RM2SSRSnapshotId, int RM3SSRSnapshotId){
		PreparedStatement ps = null;
		ResultSet rs = null;		
		int isCompleted = MIGRATION_OK;
		Connection RM2Conn = null;			
				
		try
		{
			System.out.println("*********** Start migrateSSRSnapshotDayStarData method  *************");
			RM2Conn = this.getCurrentRM2Connection();												
			
			String statement = 	"select ssrSnapshot_id, daystar_id, hotel_id, type, tab, [sequence], datethisyr, datelastyr, daterange, currentyr, " +
								"lastyr, occ_prop, occ_prop_pc, occ_compset, occ_compset_pc, occ_index, occ_index_pc, arr_prop, arr_prop_pc, " +
								"arr_compset, arr_compset_pc, arr_index, arr_index_pc, revpar_prop, revpar_prop_pc, revpar_compset, revpar_compset_pc, " +
								"revpar_index, revpar_index_pc, created_ts " +
								"from ssrSnapshot_DayStarData where ssrSnapshot_id = ?";
			
			ps = RM2Conn.prepareStatement(statement);			
			ps.setInt(1, RM2SSRSnapshotId);
			System.out.println("*********** Statement created *************");	
			
			System.out.println(statement + RM2SSRSnapshotId);	
			
			rs = ps.executeQuery();
			
			System.out.println("*********** Query executed *************");
			
			if(rs != null){
				if(rs.next()){
					
					do{
						System.out.println("**************** Getting SSRSnapshotDayStarData ******************");													
						
						SSRSnapshotDayStarData starData = new SSRSnapshotDayStarData();
						
						starData.setSsrSnapshotId(RM3SSRSnapshotId);
						starData.setDaystarId(rs.getInt("daystar_id"));
						starData.setHotelId(rs.getInt("hotel_id"));
						starData.setType(rs.getString("type"));
						starData.setTab(rs.getInt("tab"));
						starData.setSequence(rs.getInt("sequence"));
						starData.setDatethisyr(rs.getString("datethisyr"));
						starData.setDatelastyr(rs.getString("datelastyr"));
						starData.setDaterange(rs.getString("daterange"));
						starData.setCurrentyr(rs.getString("currentyr"));
						starData.setLastyr(rs.getString("lastyr"));
						starData.setOccProp(rs.getString("occ_prop"));
						starData.setOccPropPc(rs.getString("occ_prop_pc"));
						starData.setOccCompset(rs.getString("occ_compset"));
						starData.setOccCompsetPc(rs.getString("occ_compset_pc"));
						starData.setOccIndex(rs.getString("occ_index"));
						starData.setOccIndexPc(rs.getString("occ_index_pc"));
						starData.setArrProp(rs.getString("arr_prop"));
						starData.setArrPropPc(rs.getString("arr_prop_pc"));
						starData.setArrCompset(rs.getString("arr_compset"));
						starData.setArrCompsetPc(rs.getString("arr_compset_pc"));
						starData.setArrIndex(rs.getString("arr_index"));
						starData.setArrIndexPc(rs.getString("arr_index_pc"));
						starData.setRevparProp(rs.getString("revpar_prop"));
						starData.setRevparPropPc(rs.getString("revpar_prop_pc"));
						starData.setRevparCompset(rs.getString("revpar_compset"));
						starData.setRevparCompsetPc(rs.getString("revpar_compset_pc"));
						starData.setRevparIndex(rs.getString("revpar_index"));
						starData.setRevparIndexPc(rs.getString("revpar_index_pc"));
						starData.setCreatedTs(rs.getString("created_ts"));
																								
																																						
						isCompleted = insertSSRSnapshotDayStarData(starData);
							
						if (isCompleted == MIGRATION_ERROR){						
								return MIGRATION_ERROR;
						}						
												
						
					}while(rs.next());
				}
			}	
			
			System.out.println("**************** SSRSnapshotDayStarData Migrated for RM2SnapshotId = " +RM2SSRSnapshotId+ " ******************");
			
			return isCompleted;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("*********** Exception while calling DB *************");
			e.printStackTrace();
			return MIGRATION_ERROR;
		}
		
		finally {close(rs, ps, RM2Conn);}						
	}
	
	private int insertSSRSnapshotDayStarData (SSRSnapshotDayStarData starData){
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;		
	
		try
		{
			System.out.println("*********** Start insertSSRSnapshotData method  *************");
			conn = this.getCurrentHRR3Connection();
			
			String statement = "INSERT INTO RM3SSRSnapshotDayStarData(ssrSnapshot_id, daystar_id, hotel_id, type, tab, sequence, datethisyr, " +
							   "datelastyr, daterange, currentyr, lastyr, occ_prop, occ_prop_pc, occ_compset, occ_compset_pc, occ_index, occ_index_pc, " +
							   "arr_prop, arr_prop_pc, arr_compset, arr_compset_pc, arr_index, arr_index_pc, revpar_prop, revpar_prop_pc, " +
							   "revpar_compset, revpar_compset_pc, revpar_index, revpar_index_pc, created_ts) " +
							   "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";					                                   
			
			ps = conn.prepareStatement(statement);	
			
			ps.setInt(1, starData.getSsrSnapshotId());
			ps.setInt(2, starData.getDaystarId()); 
			ps.setInt(3, starData.getHotelId());
			ps.setString(4, starData.getType());
			ps.setInt(5, starData.getTab());
			ps.setInt(6, starData.getSequence());
			ps.setString(7, starData.getDatethisyr());
			ps.setString(8, starData.getDatelastyr());
			ps.setString(9, starData.getDaterange());
			ps.setString(10, starData.getCurrentyr());
			ps.setString(11, starData.getLastyr());
			ps.setString(12, starData.getOccProp());			
			ps.setString(13, starData.getOccPropPc());
			ps.setString(14, starData.getOccCompset());
			ps.setString(15, starData.getOccCompsetPc());
			ps.setString(16, starData.getOccIndex());
			ps.setString(17, starData.getOccIndexPc());
			ps.setString(18, starData.getArrProp());
			ps.setString(19, starData.getArrPropPc());
			ps.setString(20, starData.getArrCompset());
			ps.setString(21, starData.getArrCompsetPc());
			ps.setString(22, starData.getArrIndex());
			ps.setString(23, starData.getArrIndexPc());
			ps.setString(24, starData.getRevparProp());
			ps.setString(25, starData.getRevparPropPc());
			ps.setString(26, starData.getRevparCompset());
			ps.setString(27, starData.getRevparCompsetPc());
			ps.setString(28, starData.getRevparIndex());
			ps.setString(29, starData.getRevparIndexPc());
			ps.setString(30, starData.getCreatedTs());
			  					

			System.out.println("*********** Statement created *************");	
			
			
			ps.executeUpdate();
			
			System.out.println("*********** Insert SSRSnapshotDayStarData executed *************");
			
			return  MIGRATION_OK;
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("*********** Exception while calling DB *************");
			deleteMigratedSSRSnashot(starData.getSsrSnapshotId());
			e.printStackTrace();	
			return MIGRATION_ERROR;
		}
		//Close only rs and ps to keep RM3 connection alive
		finally { close(rs, ps);}			
	}
	
	
	private int migrateSSRSnapshotDayStarHotel(int RM2SSRSnapshotId, int RM3SSRSnapshotId){
		PreparedStatement ps = null;
		ResultSet rs = null;		
		int isCompleted = MIGRATION_OK;
		Connection RM2Conn = null;			
				
		try
		{
			System.out.println("*********** Start migrateSSRSnapshotDayStarHotel method  *************");
			RM2Conn = this.getCurrentRM2Connection();												
			
			String statement = 	"select ssrSnapshot_id, daystar_id,  [id], hotel_id, str_id, hotel, city, zip, phone, rooms, opendate, date1, " +
								"date2, date3, date4, date5, date6, date7, data_daystar1, data_daystar2, data_daystar3, data_daystar4, " +
								"data_daystar5, data_daystar6, data_daystar7, data_seg1, data_seg2, data_seg3, data_seg4, data_seg5, data_seg6, " +
								"data_seg7, data_fb1, data_fb2, data_fb3, data_fb4, data_fb5, data_fb6, data_fb7, created_ts " +
								"from ssrSnapshot_DayStarHotel where ssrSnapshot_id = ?";
			
			ps = RM2Conn.prepareStatement(statement);			
			ps.setInt(1, RM2SSRSnapshotId);
			System.out.println("*********** Statement created *************");	
			
			System.out.println(statement + RM2SSRSnapshotId);	
			
			rs = ps.executeQuery();
			
			System.out.println("*********** Query executed *************");
			
			if(rs != null){
				if(rs.next()){
					
					do{
						System.out.println("**************** Getting SSRSnapshotDayStarHotel ******************");													
						
						SSRSnapshotDayStarHotel starDataHotel = new SSRSnapshotDayStarHotel();
						
						starDataHotel.setSsrSnapshotId(RM3SSRSnapshotId);
						starDataHotel.setDaystarId(rs.getInt("daystar_id"));
						starDataHotel.setId(rs.getInt("id"));
						starDataHotel.setHotelId(rs.getInt("hotel_id"));
						starDataHotel.setStrId(rs.getString("str_id"));
						starDataHotel.setHotel(rs.getString("hotel"));
						starDataHotel.setCity(rs.getString("city"));
						starDataHotel.setZip(rs.getString("zip"));
						starDataHotel.setPhone(rs.getString("phone"));
						starDataHotel.setRooms(rs.getString("rooms"));
						starDataHotel.setOpendate(rs.getString("opendate"));
						starDataHotel.setDate1(rs.getString("date1"));
						starDataHotel.setDate2(rs.getString("date2"));
						starDataHotel.setDate3(rs.getString("date3"));
						starDataHotel.setDate4(rs.getString("date4"));
						starDataHotel.setDate5(rs.getString("date5"));
						starDataHotel.setDate6(rs.getString("date6"));
						starDataHotel.setDate7(rs.getString("date7"));
						starDataHotel.setDataDaystar1(rs.getString("data_daystar1"));
						starDataHotel.setDataDaystar2(rs.getString("data_daystar2"));
						starDataHotel.setDataDaystar3(rs.getString("data_daystar3"));
						starDataHotel.setDataDaystar4(rs.getString("data_daystar4"));
						starDataHotel.setDataDaystar5(rs.getString("data_daystar5"));
						starDataHotel.setDataDaystar6(rs.getString("data_daystar6"));
						starDataHotel.setDataDaystar7(rs.getString("data_daystar7"));
						starDataHotel.setDataSeg1(rs.getString("data_seg1"));
						starDataHotel.setDataSeg2(rs.getString("data_seg2"));
						starDataHotel.setDataSeg3(rs.getString("data_seg3"));
						starDataHotel.setDataSeg4(rs.getString("data_seg4"));
						starDataHotel.setDataSeg5(rs.getString("data_seg5"));
						starDataHotel.setDataSeg6(rs.getString("data_seg6"));
						starDataHotel.setDataSeg7(rs.getString("data_seg7"));
						starDataHotel.setDataFb1(rs.getString("data_fb1"));
						starDataHotel.setDataFb2(rs.getString("data_fb2"));
						starDataHotel.setDataFb3(rs.getString("data_fb3"));
						starDataHotel.setDataFb4(rs.getString("data_fb4"));
						starDataHotel.setDataFb5(rs.getString("data_fb5"));
						starDataHotel.setDataFb6(rs.getString("data_fb6"));
						starDataHotel.setDataFb7(rs.getString("data_fb7"));
						starDataHotel.setCreatedTs(rs.getString("created_ts"));
						
																								
																																						
						isCompleted = insertSSRSnapshotDayStarHotel(starDataHotel);
							
						if (isCompleted == MIGRATION_ERROR){						
								return MIGRATION_ERROR;
						}						
												
						
					}while(rs.next());
				}
			}	
			
			System.out.println("**************** SSRSnapshotDayStarHotel Migrated for RM2SnapshotId = " +RM2SSRSnapshotId+ " ******************");
			
			return isCompleted;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("*********** Exception while calling DB *************");
			e.printStackTrace();
			return MIGRATION_ERROR;
		}
		
		finally {close(rs, ps, RM2Conn);}						
	}
	
	private int insertSSRSnapshotDayStarHotel (SSRSnapshotDayStarHotel starDataHotel){
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;		
	
		try
		{
			System.out.println("*********** Start insertSSRSnapshotDayStarHotel method  *************");
			conn = this.getCurrentHRR3Connection();
			
			String statement = "INSERT INTO RM3SSRSnapshotDayStarHotel (ssrSnapshot_id, daystar_id,  id, hotel_id, str_id, hotel, city, zip, phone, " +
							   "rooms, opendate, date1, date2, date3, date4, date5, date6, date7, data_daystar1, data_daystar2, data_daystar3, " +
							   "data_daystar4, data_daystar5, data_daystar6, data_daystar7, data_seg1, data_seg2, data_seg3, data_seg4, data_seg5, " +
							   "data_seg6, data_seg7, data_fb1, data_fb2, data_fb3, data_fb4, data_fb5, data_fb6, data_fb7, created_ts) " +
							   "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";					                                   
			
			ps = conn.prepareStatement(statement);	
			
			ps.setInt(1, starDataHotel.getSsrSnapshotId());
			ps.setInt(2, starDataHotel.getDaystarId());
			ps.setInt(3, starDataHotel.getId());
			ps.setInt(4, starDataHotel.getHotelId());
			ps.setString(5, starDataHotel.getStrId());
			ps.setString(6, starDataHotel.getHotel());
			ps.setString(7, starDataHotel.getCity());
			ps.setString(8, starDataHotel.getZip());
			ps.setString(9, starDataHotel.getPhone());
			ps.setString(10, starDataHotel.getRooms());
			ps.setString(11, starDataHotel.getOpendate());
			ps.setString(12, starDataHotel.getDate1());
			ps.setString(13, starDataHotel.getDate2());
			ps.setString(14, starDataHotel.getDate3());
			ps.setString(15, starDataHotel.getDate4());
			ps.setString(16, starDataHotel.getDate5());
			ps.setString(17, starDataHotel.getDate6());
			ps.setString(18, starDataHotel.getDate7());
			ps.setString(19, starDataHotel.getDataDaystar1());
			ps.setString(20, starDataHotel.getDataDaystar2());
			ps.setString(21, starDataHotel.getDataDaystar3());
			ps.setString(22, starDataHotel.getDataDaystar4());			
			ps.setString(23, starDataHotel.getDataDaystar5());
			ps.setString(24, starDataHotel.getDataDaystar6());
			ps.setString(25, starDataHotel.getDataDaystar7());
			ps.setString(26, starDataHotel.getDataSeg1());
			ps.setString(27, starDataHotel.getDataSeg2());
			ps.setString(28, starDataHotel.getDataSeg3());
			ps.setString(29, starDataHotel.getDataSeg4());
			ps.setString(30, starDataHotel.getDataSeg5());
			ps.setString(31, starDataHotel.getDataSeg6());
			ps.setString(32, starDataHotel.getDataSeg7());
			ps.setString(33, starDataHotel.getDataFb1());
			ps.setString(34, starDataHotel.getDataFb2());
			ps.setString(35, starDataHotel.getDataFb3());
			ps.setString(36, starDataHotel.getDataFb4());
			ps.setString(37, starDataHotel.getDataFb5());
			ps.setString(38, starDataHotel.getDataFb6());
			ps.setString(39, starDataHotel.getDataFb7());
			ps.setString(40, starDataHotel.getCreatedTs());						
			  					

			System.out.println("*********** Statement created *************");	
			
			
			ps.executeUpdate();
			
			System.out.println("*********** Insert SSRSnapshotDayStarHotel executed *************");
			
			return  MIGRATION_OK;
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("*********** Exception while calling DB *************");
			deleteMigratedSSRSnashot(starDataHotel.getSsrSnapshotId());
			e.printStackTrace();	
			return MIGRATION_ERROR;
		}
		//Close only rs and ps to keep RM3 connection alive
		finally { close(rs, ps);}			
	}
	
	private int migrateSSRSnapshotStarData(int RM2SSRSnapshotId, int RM3SSRSnapshotId){
		PreparedStatement ps = null;
		ResultSet rs = null;		
		int isCompleted = MIGRATION_OK;
		Connection RM2Conn = null;			
				
		try
		{
			System.out.println("*********** Start migrateSSRSnapshotStarData method  *************");
			RM2Conn = this.getCurrentRM2Connection();												
			
			String statement = 	"select ssrSnapshot_id, hotel_id, hotel_caption, month_caption, isSummary, type, [group], [sequence], tract, " +
								"tract_scale, star_year, star_month, occ_prop, occ_prop_pc, occ_compset, occ_compset_pc, occ_index, occ_index_pc, " +
								"arr_prop, arr_prop_pc, arr_compset, arr_compset_pc, arr_index, arr_index_pc, revpar_prop, revpar_prop_pc, " +
								"revpar_compset, revpar_compset_pc, revpar_index, revpar_index_pc, mktsh_supply, mktsh_demand, mktsh_rev, created_ts " +
								"from ssrSnapshot_StarData where ssrSnapshot_id = ?";
			
			ps = RM2Conn.prepareStatement(statement);			
			ps.setInt(1, RM2SSRSnapshotId);
			System.out.println("*********** Statement created *************");	
			
			System.out.println(statement + RM2SSRSnapshotId);	
			
			rs = ps.executeQuery();
			
			System.out.println("*********** Query executed *************");
			
			if(rs != null){
				if(rs.next()){
					
					do{
						System.out.println("**************** Getting SSRSnapshotStarData ******************");													
						
						SSRSnapshotStarData starData = new SSRSnapshotStarData();
						
						starData.setSsrSnapshotId(RM3SSRSnapshotId);
						starData.setHotelId(rs.getInt("hotel_id"));
						starData.setHotelCaption(rs.getString("hotel_caption"));
						starData.setMonthCaption(rs.getString("month_caption"));
						starData.setIsSummary(rs.getInt("isSummary"));
						starData.setType(rs.getString("type"));
						starData.setGroup(rs.getInt("group"));
						starData.setSequence(rs.getInt("sequence"));
						starData.setTract(rs.getString("tract"));
						starData.setTractScale(rs.getString("tract_scale"));
						starData.setStarYear(rs.getInt("star_year"));
						starData.setStarMonth(rs.getString("star_month"));
						starData.setOccProp(rs.getString("occ_prop"));
						starData.setOccPropPc(rs.getString("occ_prop_pc"));
						starData.setOccCompset(rs.getString("occ_compset"));
						starData.setOccCompsetPc(rs.getString("occ_compset_pc"));
						starData.setOccIndex(rs.getString("occ_index"));
						starData.setOccIndexPc(rs.getString("occ_index_pc"));
						starData.setArrProp(rs.getString("arr_prop"));
						starData.setArrPropPc(rs.getString("arr_prop_pc"));
						starData.setArrCompset(rs.getString("arr_compset"));
						starData.setArrCompsetPc(rs.getString("arr_compset_pc"));
						starData.setArrIndex(rs.getString("arr_index"));
						starData.setArrIndexPc(rs.getString("arr_index_pc"));
						starData.setRevparProp(rs.getString("revpar_prop"));
						starData.setRevparPropPc(rs.getString("revpar_prop_pc"));
						starData.setRevparCompset(rs.getString("revpar_compset"));
						starData.setRevparCompsetPc(rs.getString("revpar_compset_pc"));
						starData.setRevparIndex(rs.getString("revpar_index"));
						starData.setRevparIndexPc(rs.getString("revpar_index_pc"));
						starData.setMktshSupply(rs.getString("mktsh_supply"));
						starData.setMktshDemand(rs.getString("mktsh_demand"));
						starData.setMktshRev(rs.getString("mktsh_rev"));
						starData.setCreatedTs(rs.getString("created_ts"));
																		
																																					
						isCompleted = insertSSRSnapshotStarData(starData);
							
						if (isCompleted == MIGRATION_ERROR){						
								return MIGRATION_ERROR;
						}						
												
						
					}while(rs.next());
				}
			}	
			
			System.out.println("**************** SSRSnapshotStarData Migrated for RM2SnapshotId = " +RM2SSRSnapshotId+ " ******************");
			
			return isCompleted;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("*********** Exception while calling DB *************");
			e.printStackTrace();
			return MIGRATION_ERROR;
		}
		
		finally {close(rs, ps, RM2Conn);}						
	}
	
	private int insertSSRSnapshotStarData (SSRSnapshotStarData starData){
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;		
	
		try
		{
			System.out.println("*********** Start insertSSRSnapshotStarData method  *************");
			conn = this.getCurrentHRR3Connection();
			
			String statement = "INSERT INTO RM3SSRSnapshotStarData(ssrSnapshot_id, hotel_id, hotel_caption, month_caption, isSummary, type,`group`, " +
							   "sequence, tract, tract_scale, star_year, star_month, occ_prop, occ_prop_pc, occ_compset, occ_compset_pc, occ_index, " +
							   "occ_index_pc, arr_prop, arr_prop_pc, arr_compset, arr_compset_pc, arr_index, arr_index_pc, revpar_prop, revpar_prop_pc, " +
							   "revpar_compset, revpar_compset_pc, revpar_index, revpar_index_pc, mktsh_supply, mktsh_demand, mktsh_rev, created_ts) " +
							   "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";					                                   
			
			ps = conn.prepareStatement(statement);	
			
			ps.setInt(1, starData.getSsrSnapshotId());
			ps.setInt(2, starData.getHotelId());
			ps.setString(3, starData.getHotelCaption());
			ps.setString(4, starData.getMonthCaption());
			ps.setInt(5, starData.getIsSummary());
			ps.setString(6, starData.getType());
			ps.setInt(7, starData.getGroup());
			ps.setInt(8, starData.getSequence());
			ps.setString(9, starData.getTract());
			ps.setString(10, starData.getTractScale());
			ps.setInt(11, starData.getStarYear());
			ps.setString(12, starData.getStarMonth());
			ps.setString(13, starData.getOccProp());
			ps.setString(14, starData.getOccPropPc());
			ps.setString(15, starData.getOccCompset());
			ps.setString(16, starData.getOccCompsetPc());
			ps.setString(17, starData.getOccIndex());
			ps.setString(18, starData.getOccIndexPc());
			ps.setString(19, starData.getArrProp());
			ps.setString(20, starData.getArrPropPc());
			ps.setString(21, starData.getArrCompset());
			ps.setString(22, starData.getArrCompsetPc());
			ps.setString(23, starData.getArrIndex());
			ps.setString(24, starData.getArrIndexPc());
			ps.setString(25, starData.getRevparProp());
			ps.setString(26, starData.getRevparPropPc());
			ps.setString(27, starData.getRevparCompset());
			ps.setString(28, starData.getRevparCompsetPc());
			ps.setString(29, starData.getRevparIndex());
			ps.setString(30, starData.getRevparIndexPc());
			ps.setString(31, starData.getMktshSupply());
			ps.setString(32, starData.getMktshDemand());
			ps.setString(33, starData.getMktshRev());
			ps.setString(34, starData.getCreatedTs());
						

			System.out.println("*********** Statement created *************");	
			
			
			ps.executeUpdate();
			
			System.out.println("*********** Insert SSRSnapshotStarData executed *************");
			
			return  MIGRATION_OK;
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("*********** Exception while calling DB *************");
			deleteMigratedSSRSnashot(starData.getSsrSnapshotId());
			e.printStackTrace();	
			return MIGRATION_ERROR;
		}
		//Close only rs and ps to keep RM3 connection alive
		finally { close(rs, ps);}			
	}

	private int migrateSSRSnapshotCOTB(int RM2SSRSnapshotId, int RM3SSRSnapshotId){
		PreparedStatement ps = null;
		ResultSet rs = null;		
		int isCompleted = MIGRATION_OK;
		Connection RM2Conn = null;			
				
		try
		{
			System.out.println("*********** Start migrateSSRSnapshotCOTB method  *************");
			RM2Conn = this.getCurrentRM2Connection();												
			
			String statement = 	"select ssrSnapshot_id, hotel_id, cotb_year, cotb_month, activity_date, cur_def, prev_def, created_ts " +
								"from ssrSnapshot_COTB where ssrSnapshot_id = ?";
			
			ps = RM2Conn.prepareStatement(statement);			
			ps.setInt(1, RM2SSRSnapshotId);
			System.out.println("*********** Statement created *************");	
			
			System.out.println(statement + RM2SSRSnapshotId);	
			
			rs = ps.executeQuery();
			
			System.out.println("*********** Query executed *************");
			
			if(rs != null){
				if(rs.next()){
					
					do{
						System.out.println("**************** Getting SSRSnapshotCOTB ******************");													
						
						SSRSnapshotCOTB cotb = new SSRSnapshotCOTB();
						
						cotb.setSsrSnapshotId(RM3SSRSnapshotId);
						cotb.setHotelId(rs.getInt("hotel_id"));
						cotb.setCotbYear(rs.getInt("cotb_year"));
						cotb.setCotbMonth(rs.getInt("cotb_month"));
						cotb.setActivityDate(rs.getString("activity_date"));
						cotb.setCurDef(rs.getInt("cur_def"));
						cotb.setPrevDef(rs.getInt("prev_def"));
						cotb.setCreatedTs(rs.getString("created_ts"));
																																																												
						isCompleted = insertSSRSnapshotCOTB(cotb);
							
						if (isCompleted == MIGRATION_ERROR){						
								return MIGRATION_ERROR;
						}						
												
						
					}while(rs.next());
				}
			}	
			
			System.out.println("**************** SSRSnapshotCOTB Migrated for RM2SnapshotId = " +RM2SSRSnapshotId+ " ******************");
			
			return isCompleted;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("*********** Exception while calling DB *************");
			e.printStackTrace();
			return MIGRATION_ERROR;
		}
		
		finally {close(rs, ps, RM2Conn);}						
	}
	
	private int insertSSRSnapshotCOTB (SSRSnapshotCOTB cotb){
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;		
	
		try
		{
			System.out.println("*********** Start insertSSRSnapshotCOTB method  *************");
			conn = this.getCurrentHRR3Connection();
			
			String statement = "INSERT INTO RM3SSRSnapshotCOTB (ssrSnapshot_id, hotel_id, cotb_year, cotb_month, activity_date, cur_def, " +
							   "prev_def, created_ts ) " +
							   " values (?,?,?,?,?,?,?,?)";					                                   
			
			ps = conn.prepareStatement(statement);	
			
			ps.setInt(1, cotb.getSsrSnapshotId());
			ps.setInt(2, cotb.getHotelId());
			ps.setInt(3, cotb.getCotbYear());
			ps.setInt(4, cotb.getCotbMonth());
			ps.setString(5, cotb.getActivityDate());
			ps.setInt(6, cotb.getCurDef());
			ps.setInt(7, cotb.getPrevDef());
			ps.setString(8, cotb.getCreatedTs());
			
								
			System.out.println("*********** Statement created *************");	
			
			
			ps.executeUpdate();
			
			System.out.println("*********** Insert SSRSnapshotCOTB executed *************");
			
			return  MIGRATION_OK;
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("*********** Exception while calling DB *************");
			deleteMigratedSSRSnashot(cotb.getSsrSnapshotId());
			e.printStackTrace();	
			return MIGRATION_ERROR;
		}
		//Close only rs and ps to keep RM3 connection alive
		finally { close(rs, ps);}			
	}
	
	private int migrateSSRSnapshotGroupPace(int RM2SSRSnapshotId, int RM3SSRSnapshotId){
		PreparedStatement ps = null;
		ResultSet rs = null;		
		int isCompleted = MIGRATION_OK;
		Connection RM2Conn = null;			
				
		try
		{
			System.out.println("*********** Start migrateSSRSnapshotGroupPace method  *************");
			RM2Conn = this.getCurrentRM2Connection();												
			
			String statement = 	"select ssrSnapshot_id, hotel_id, gp_year, gp_month, occ " +
								"from ssrSnapshot_GroupPace where ssrSnapshot_id = ?";
			
			ps = RM2Conn.prepareStatement(statement);			
			ps.setInt(1, RM2SSRSnapshotId);
			System.out.println("*********** Statement created *************");	
			
			System.out.println(statement + RM2SSRSnapshotId);	
			
			rs = ps.executeQuery();
			
			System.out.println("*********** Query executed *************");
			
			if(rs != null){
				if(rs.next()){
					
					do{
						System.out.println("**************** Getting SSRSnapshotGroupPace ******************");													
						
						SSRSnapshotGroupPace groupPace = new SSRSnapshotGroupPace();
						
						groupPace.setSsrSnapshotId(RM3SSRSnapshotId);
						groupPace.setHotelId(rs.getInt("hotel_id"));
						groupPace.setGpYear(rs.getInt("gp_year"));
						groupPace.setGpMonth(rs.getInt("gp_month"));
						groupPace.setOcc(rs.getBigDecimal("occ"));
						
																																															
						isCompleted = insertSSRSnapshotGroupPace(groupPace);
							
						if (isCompleted == MIGRATION_ERROR){						
								return MIGRATION_ERROR;
						}						
												
						
					}while(rs.next());
				}
			}	
			
			System.out.println("**************** SSRSnapshotGroupPace Migrated for RM2SnapshotId = " +RM2SSRSnapshotId+ " ******************");
			
			return isCompleted;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("*********** Exception while calling DB *************");
			e.printStackTrace();
			return MIGRATION_ERROR;
		}
		
		finally {close(rs, ps, RM2Conn);}						
	}
	
	private int insertSSRSnapshotGroupPace (SSRSnapshotGroupPace groupPace){
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;		
	
		try
		{
			System.out.println("*********** Start insertSSRSnapshotGroupPace method  *************");
			conn = this.getCurrentHRR3Connection();
			
			String statement = "INSERT INTO RM3SSRSnapshotGroupPace (ssrSnapshot_id, hotel_id, gp_year, gp_month, occ) " +
							   "values (?,?,?,?,?)";					                                   
			
			ps = conn.prepareStatement(statement);	
			
			ps.setInt(1, groupPace.getSsrSnapshotId());
			ps.setInt(2, groupPace.getHotelId());
			ps.setInt(3, groupPace.getGpYear());
			ps.setInt(4, groupPace.getGpMonth());
			ps.setBigDecimal(5, groupPace.getOcc());						
								
			System.out.println("*********** Statement created *************");	
			
			
			ps.executeUpdate();
			
			System.out.println("*********** Insert SSRSnapshotGroupPace executed *************");
			
			return  MIGRATION_OK;
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("*********** Exception while calling DB *************");
			deleteMigratedSSRSnashot(groupPace.getSsrSnapshotId());
			e.printStackTrace();	
			return MIGRATION_ERROR;
		}
		//Close only rs and ps to keep RM3 connection alive
		finally { close(rs, ps);}			
	}
	
	public void deleteMigratedSSRSnashot (int RM3SnapshotId){
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;	
		
		try{
			System.out.println("*********** Start deleteMigrated SSR-Snapshot method  *************");
			conn = this.getCurrentHRR3Connection();
			
			String statement = ("{call RM3DeleteSSRSnapshotMigrated(?)}");
					           
			ps = conn.prepareStatement(statement);	
			ps.setInt(1, RM3SnapshotId);
			System.out.println("*********** Statement created *************");
			
			ps.executeUpdate();
			System.out.println("*********** Query executed *************");

		}catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("*********** Exception while calling DB *************");
			e.printStackTrace();
			//return null;
		}
	
		finally { close(rs, ps, conn);}
		
	}
	
	public void setCurrentSSRSnapshot (int RM3SnapshotId){
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;	
		
		try{
			System.out.println("*********** Start setCurrentSSRSnapshot SSR-Snapshot method  *************");
			conn = this.getCurrentHRR3Connection();
			
			String statement = ("UPDATE RM3HotelDetails SET ssrSnapshotId = ? WHERE HotelId = ?;");
					           
			ps = conn.prepareStatement(statement);	
			ps.setInt(1, RM3SnapshotId);
			ps.setInt(2, this.getCurrentHotel().getHotelId());
			System.out.println("*********** Statement created *************");
			
			ps.executeUpdate();
			System.out.println("*********** Query executed *************");

		}catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("*********** Exception while calling DB *************");
			e.printStackTrace();
			//return null;
		}
	
		finally {
			System.out.println("*********** End setCurrentSSRSnapshot SSR-Snapshot method  *************");
			close(rs, ps, conn);
		}
		
	}
}
