package com.hrr3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hrr3.entity.Hotel;
import com.hrr3.entity.Snapshot;
import com.hrr3.entity.SnapshotExport;

public class SnapshotDAO extends RM3AbstractDAO{
	
	public SnapshotDAO(Hotel currentHotel){
		super(currentHotel);
	}
	
	public List<Snapshot> getAllSnapshots(String firstElementCode){
		
		List<Snapshot> lstSnapshots = new ArrayList<Snapshot>();
		//Build dummy snapshot to include the Add option
		Snapshot addSnapshot = new Snapshot();
		addSnapshot.setName(firstElementCode);
		lstSnapshots.add(addSnapshot);
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
				
		try
		{
			conn = this.getCurrentHRR3Connection();
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
						
			String statement = "select * from RM3Snapshot where hotel_id=? order by created_ts DESC";
			
			ps = conn.prepareStatement(statement);			
			ps.setInt(1, this.getCurrentHotel().getHotelId());			
			rs = ps.executeQuery();
			
			while(rs != null && rs.next()){	
				Snapshot snapshot = new Snapshot();
				
				snapshot.setSnapshotId(rs.getInt("snapshot_id"));
				snapshot.setHotelId(rs.getInt("hotel_id"));
				snapshot.setName(rs.getString("snapshot_name"));
				snapshot.setYear(rs.getInt("snapshot_year"));
				snapshot.setMonth(rs.getInt("snapshot_month"));
				snapshot.setBudget(rs.getInt("budget"));
				snapshot.setForecast(rs.getInt("forecast"));
				snapshot.setCurrentForecast(rs.getInt("current_forecast"));
				snapshot.setQuarterlyForecast(rs.getInt("quarterly_forecast"));
				//date
				Date dateObj = rs.getDate("created_ts");				
				snapshot.setCreatedTS(dateObj != null ? dateFormat.format(dateObj) : "");
								
				lstSnapshots.add(snapshot);				
			}
						
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//return null;
		}
		
		finally { close(rs, ps); close(conn);}
		
		return lstSnapshots;
	}
	
	public Snapshot getSnapshotById(int snapshotId){
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		Snapshot snapshot = new Snapshot();
				
		try
		{
			conn = this.getCurrentHRR3Connection();
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");			
			String statement = "select * from RM3Snapshot where hotel_id=? order by snapshot_id DESC";
			
			ps = conn.prepareStatement(statement);			
			ps.setInt(1, this.getCurrentHotel().getHotelId());			
			rs = ps.executeQuery();
			
			while(rs != null && rs.next()){									
				snapshot.setSnapshotId(rs.getInt("snapshot_id"));
				snapshot.setHotelId(rs.getInt("hotel_id"));
				snapshot.setName(rs.getString("snapshot_name"));
				snapshot.setYear(rs.getInt("snapshot_year"));
				snapshot.setMonth(rs.getInt("snapshot_month"));
				snapshot.setBudget(rs.getInt("budget"));
				snapshot.setForecast(rs.getInt("forecast"));
				snapshot.setCurrentForecast(rs.getInt("current_forecast"));
				snapshot.setQuarterlyForecast(rs.getInt("quarterly_forecast"));
				//date
				Date dateObj = rs.getDate("created_ts");				
				snapshot.setCreatedTS(dateObj != null ? dateFormat.format(dateObj) : "");
											
			}
						
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//return null;
		}
		
		finally { close(rs, ps); close(conn);}
		
		return snapshot;
	}
	
	public boolean setSnapshot( String snapshotName,int year, int month, int budget, int forecast, int current, int quarterly, String dateFrom, String dateTo ){
		//setSnapshot(CStr(globalHotelID), txtName.Text, txtYear.Text, chkBudget.Value, chkForecast.Value, iMonth, chkCurrFcst.Value, 
		//chkQuarterForecast.Value, sDateFrom, sDateTo)		
		boolean isSaved = false;
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
				
		try
		{
			conn = this.getCurrentHRR3Connection();
			
			//call ftSnapshotAdd (1,'Snapshot 7',2014,0,1,2, 0,0,'2014-02-01','2014-02-28')
			String statement = ("{call ftSnapshotAdd (?,?,?,?,?,?,?,?,?,?)}");
			
			ps = conn.prepareStatement(statement);			
			ps.setInt(1, this.getCurrentHotel().getHotelId());
			ps.setString(2, snapshotName);
			ps.setInt(3, year);			
			ps.setInt(4, budget);
			ps.setInt(5, forecast);
			ps.setInt(6, month);
			ps.setInt(7, current);
			ps.setInt(8, quarterly);
			ps.setString(9, dateFrom);
			ps.setString(10, dateTo);
			
			rs = ps.executeQuery();
			
			isSaved = true;
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return isSaved;
		}
		
		finally { close(rs, ps); close(conn);}
		
		return isSaved;
	}

	public boolean hasBudget(int year) {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		int total = 0;
						
		try
		{
			conn = this.getCurrentHRR3Connection();
			
			String statement = "select 1 from RM3Snapshot where budget=1 and hotel_id=? and snapshot_year= ?";
			ps = conn.prepareStatement(statement);			
			ps.setInt(1, this.getCurrentHotel().getHotelId());
			ps.setInt(2, year);
			
			rs = ps.executeQuery();
			
			if (rs != null && rs.next() ){
				total =  rs.getInt("totalSnapshots");
			}	
			
			if (total == 0)
				return false;
			else
				return true;
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}		
		finally { close(rs, ps); close(conn);}			
				
	}
	
	public boolean hasForecast() {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		int total = 0;
						
		try
		{
			conn = this.getCurrentHRR3Connection();						 
			String statement = "select 1 from RM3Snapshot where forecast=1 and hotel_id=? ";
			ps = conn.prepareStatement(statement);			
			ps.setInt(1, this.getCurrentHotel().getHotelId());			
			
			rs = ps.executeQuery();
			
			if (rs != null && rs.next() ){
				total =  rs.getInt("totalSnapshots");
			}	
			
			if (total == 0)
				return false;
			else
				return true;
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}		
		finally { close(rs, ps); close(conn);}			

	}
	
	public boolean hasCurrForecast(int year, int month) {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		int total = 0;
						
		try
		{
			conn = this.getCurrentHRR3Connection();
						
			String statement = "select count(*) as totalSnapshots from RM3Snapshot where current_forecast=1 and hotel_id=? and snapshot_year = ? and snapshot_month = ? "; 
			ps = conn.prepareStatement(statement);			
			ps.setInt(1, this.getCurrentHotel().getHotelId());
			ps.setInt(2, year);
			ps.setInt(3, month);
			
			rs = ps.executeQuery();
			
			if (rs != null && rs.next() ){
				total =  rs.getInt("totalSnapshots");
			}	
			
			if (total == 0)
				return false;
			else
				return true;
						
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}		
		finally { close(rs, ps); close(conn);}								

	}
	
	public boolean hasQuarterForecast(int year, int month) {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		int total = 0;

		//Quarter
		
		String currentQuarter = "";
		
		if(month >= 1 && month <= 3){
			currentQuarter =" in (1,2,3)";
		}
		
		if(month >= 4 && month <= 6){
			currentQuarter =" in (4,5,6)";
		}
		
		if(month >= 7 && month <= 9){
			currentQuarter =" in (7,8,9)";
		}
		
		if(month >= 10 && month <= 11){
			currentQuarter =" in (10,11,12)";
		}
		
		
		try
		{
			conn = this.getCurrentHRR3Connection();
						
			String statement = "select count(*) as totalSnapshots from RM3Snapshot where quarterly_forecast=1 and hotel_id=? and snapshot_year = ?  and snapshot_month " + currentQuarter;
			ps = conn.prepareStatement(statement);			
			ps.setInt(1, this.getCurrentHotel().getHotelId());
			ps.setInt(2, year);
			rs = ps.executeQuery();
			
			if (rs != null && rs.next() ){
				total =  rs.getInt("totalSnapshots");
			}	
			
			if (total == 0)
				return false;
			else
				return true;
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}		
		finally { close(rs, ps); close(conn);}			
				
	}
	
	public boolean deleteSnapshost(int snapshotId){
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
						
		try
		{
			conn = this.getCurrentHRR3Connection();
						
			String statement = ("{call ftSnapshotDelete(?)}");
			ps = conn.prepareStatement(statement);			
			ps.setInt(1, snapshotId);
			
			ps.executeQuery();
			
			return true;
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}		
		finally { close(rs, ps); close(conn);}
		
	}
	
	public boolean updateHotelSnapshotId(int snapshotId){
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
						
		try
		{
			conn = this.getCurrentHRR3Connection();
						
			String statement = "update RM3HotelDetails set SnapshotId=? where HotelId=?";
			ps = conn.prepareStatement(statement);			
			ps.setInt(1, snapshotId);
			ps.setInt(2, this.getCurrentHotel().getHotelId());			
			ps.executeUpdate();
			
			return true;
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}		
		finally { close(rs, ps); close(conn);}
		
	}
	
	public boolean restoreSnapshot(int snapshotId, String dateFrom, String dateTo){
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
						
		try
		{
			conn = this.getCurrentHRR3Connection();
						
			String statement = ("{call ftSnapshotRestore(?,?,?)}");
			ps = conn.prepareStatement(statement);			
			ps.setInt(1, snapshotId);
			ps.setString(2, dateFrom);
			ps.setString(3, dateTo);
			
			ps.executeQuery();
			
			return true;
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}		
		finally { close(rs, ps); close(conn);}		
		
	}
	
	public List<Snapshot> getSnapshotByHotelId(int hotelId){
		
		List<Snapshot> lstSnapshots = new ArrayList<Snapshot>();		
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
				
		try
		{
			conn = this.getCurrentHRR3Connection();
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
						
			String statement = "Select * 	From RM3Snapshot Where hotel_id = ? Order By created_ts desc";
			
			ps = conn.prepareStatement(statement);			
			ps.setInt(1, this.getCurrentHotel().getHotelId());			
			rs = ps.executeQuery();
			
			while(rs != null && rs.next()){	
				Snapshot snapshot = new Snapshot();
				
				snapshot.setSnapshotId(rs.getInt("snapshot_id"));
				snapshot.setHotelId(rs.getInt("hotel_id"));
				snapshot.setName(rs.getString("snapshot_name"));
				snapshot.setYear(rs.getInt("snapshot_year"));
				snapshot.setMonth(rs.getInt("snapshot_month"));
				snapshot.setBudget(rs.getInt("budget"));
				snapshot.setForecast(rs.getInt("forecast"));
				snapshot.setCurrentForecast(rs.getInt("current_forecast"));
				snapshot.setQuarterlyForecast(rs.getInt("quarterly_forecast"));
				//date
				Date dateObj = rs.getDate("created_ts");				
				snapshot.setCreatedTS(dateObj != null ? dateFormat.format(dateObj) : "");
								
				lstSnapshots.add(snapshot);				
			}
						
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//return null;
		}
		
		finally { close(rs, ps); close(conn);}
		
		return lstSnapshots;
	}
	
	public Integer getSnapshotNumberLimit(){
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
						
		try
		{
			conn = this.getCurrentHRR3Connection();
						
			String statement = "select HotelSnapshotMax from RM3HotelDetails where HotelId = ?";
			ps = conn.prepareStatement(statement);			
			ps.setInt(1, this.getCurrentHotel().getHotelId());
			
			rs = ps.executeQuery();
			
			if(rs != null && rs.next())
				return rs.getInt("HotelSnapshotMax");
			
			else return 0;
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}		
		finally { close(rs, ps); close(conn);}
		
	}
		
	
	public Snapshot  getSnapshotInfo(int snapshotId){
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;		
		
		Snapshot snapshot = new Snapshot();
		
		try
		{
			conn = this.getCurrentHRR3Connection();						
			
			String statement = "select snapshot_id, hotel_id, snapshot_name, snapshot_year, snapshot_month, budget, forecast, current_forecast, " +
								 " quarterly_forecast, created_ts, restore_ts from RM3Snapshot where snapshot_id = ?";
			ps = conn.prepareStatement(statement);			
			ps.setInt(1, snapshotId);
			
			rs = ps.executeQuery();						
			
			if(rs != null && rs.next()){							
				snapshot.setSnapshotId(rs.getInt("snapshot_id"));
				snapshot.setHotelId(rs.getInt("hotel_id"));
				snapshot.setName(rs.getString("snapshot_name"));
				snapshot.setYear(rs.getInt("snapshot_year"));
				snapshot.setMonth(rs.getInt("snapshot_month"));
				snapshot.setBudget(rs.getInt("budget"));
				snapshot.setForecast(rs.getInt("forecast"));
				snapshot.setCurrentForecast(rs.getInt("current_forecast"));
				snapshot.setQuarterlyForecast(rs.getInt("quarterly_forecast"));										
				snapshot.setCreatedTS(rs.getString("created_ts"));				
				snapshot.setRestoreTS(rs.getString("restore_ts"));
															
			}
						
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}		
		finally { close(rs, ps); close(conn);}
		
		return snapshot;
	}
	
	public List<SnapshotExport>  getExportSnapshotData(int snapshotId){
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;		
		
		List<SnapshotExport> lstSnapshotData = new ArrayList<SnapshotExport>();
		
		try
		{
			conn = this.getCurrentHRR3Connection();						
			
			String statement = "select td.snapshot_id, td.hotel_id, td.statdate, td.comments, td.isException, td.dow," +
					"td.isActual, td.tot_occ_pct, td.tot_adr, td.tot_rev_par, td.tot_occ_rooms, td.tot_rev, td.gtot_occ," +
					"td.gtot_rev, td.gtot_adr, td.gdef_occ, td.gdef_rev, td.gdef_adr, td.gten_occ, td.gten_rev, td.gten_adr, td.created_ts as created_ts_tot," +
					"tsd.snapshot_id, tsd.tsegmentdata_id, tsd.hotel_id as tsdhotel_id, tsd.segment_id, tsd.statdate as tsdstatdate, tsd.type," +
					"tsd.tot_occ, tsd.tot_rev as tot_rev_seg, tsd.tot_adr as tot_adr_seg, tsd.def_occ, tsd.def_rev, tsd.def_adr, tsd.ten_occ, tsd.ten_rev, tsd.ten_adr, tsd.created_ts as created_ts_seg," +
					"tsd.admin, tsd._year, tsd._month, tsd._isWeekend from RM3SnapshotTransientData  as td INNER JOIN RM3SnapshotTransientSegmentData tsd" +
					" on td.snapshot_id = tsd.snapshot_id and td.hotel_id = tsd.hotel_id and td.statdate = tsd.statdate where  td.snapshot_id = ?" +
					" and td.hotel_id = ? order by td.statdate";

			ps = conn.prepareStatement(statement);			
			ps.setInt(1, snapshotId);
			ps.setInt(2, this.getCurrentHotel().getHotelId());
			
			rs = ps.executeQuery();						
			
			while(rs != null && rs.next()){	
				
				SnapshotExport sExportData = new SnapshotExport();
				
				sExportData.setHotelId(rs.getInt("hotel_id"));
				sExportData.setSnapshotId(rs.getInt("snapshot_id"));
				sExportData.setStatdate(rs.getString("statdate"));
				sExportData.setComments(rs.getString("comments"));
				sExportData.setIsException(rs.getInt("isException"));
				sExportData.setDow(rs.getString("dow"));
				sExportData.setIsActual(rs.getInt("isActual"));
				sExportData.setTotOccPct(rs.getBigDecimal("tot_occ_pct"));
				sExportData.setTotAdr(rs.getBigDecimal("tot_adr"));
				sExportData.setTotRevPar(rs.getBigDecimal("tot_rev_par"));
				sExportData.setTotOccRooms(rs.getBigDecimal("tot_occ_rooms"));
				sExportData.setTotRev(rs.getBigDecimal("tot_rev"));
				sExportData.setGtotOcc(rs.getBigDecimal("gtot_occ"));
				sExportData.setGtotRev(rs.getBigDecimal("gtot_rev"));
				sExportData.setGtotAdr(rs.getBigDecimal("gtot_adr"));
				sExportData.setGdefOcc(rs.getBigDecimal("gdef_occ"));
				sExportData.setGdefRev(rs.getBigDecimal("gdef_rev"));
				sExportData.setGdefAdr(rs.getBigDecimal("gdef_adr"));
				sExportData.setGtenOcc(rs.getBigDecimal("gten_occ"));
				sExportData.setGtenRev(rs.getBigDecimal("gten_rev"));
				sExportData.setGtenAdr(rs.getBigDecimal("gten_adr"));															
				sExportData.setCreatedTsTotal(rs.getString("created_ts_tot"));
								
				sExportData.setSegmentId(rs.getInt("segment_id"));
				sExportData.setTsdstatdate(rs.getString("tsdstatdate"));
				sExportData.setType(rs.getString("type"));
				sExportData.setTotOcc(rs.getBigDecimal("tot_occ"));
				sExportData.setTotRev(rs.getBigDecimal("tot_rev_seg"));
				sExportData.setTotAdr(rs.getBigDecimal("tot_adr_seg"));
				sExportData.setDefOcc(rs.getBigDecimal("def_occ"));
				sExportData.setDefRev(rs.getBigDecimal("def_rev"));
				sExportData.setDefAdr(rs.getBigDecimal("def_adr"));
				sExportData.setTenOcc(rs.getBigDecimal("ten_occ"));
				sExportData.setTenRev(rs.getBigDecimal("ten_rev"));
				sExportData.setTenAdr(rs.getBigDecimal("ten_adr"));								
				sExportData.setCreatedTsSegment(rs.getString("created_ts_seg"));
				
				sExportData.setAdmin(rs.getInt("admin"));
								
				lstSnapshotData.add(sExportData);									
			}
						
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}		
		finally { close(rs, ps); close(conn);}
		
		return lstSnapshotData;
	}
	
	public void saveSnapshotUF(int snapshotId ){
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
				
		try
		{
			conn = this.getCurrentHRR3Connection();
			
			String statement = ("{call ftSnapshotSaveUF (?,?)}");
			
			ps = conn.prepareStatement(statement);
			ps.setInt(1, snapshotId);
			ps.setInt(2, this.getCurrentHotel().getHotelId());
			
						
			rs = ps.executeQuery();
									
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}
		
		finally { close(rs, ps); close(conn);}
				
	}
	
	
	public void updateSnapshotName(int snapshotId, String snapshotName) {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
						
		try
		{
			conn = this.getCurrentHRR3Connection();
						
			String statement = "update RM3Snapshot set snapshot_name = ? where hotel_id = ? and snapshot_id = ?;";
			ps = conn.prepareStatement(statement);
			
			ps.setString(1, snapshotName);
			ps.setInt(2, this.getCurrentHotel().getHotelId());
			ps.setInt(3, snapshotId);
			
			ps.executeUpdate();
			
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}		
		finally { close(rs, ps); close(conn);}			
				
	}

}
