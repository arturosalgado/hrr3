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
import com.hrr3.entity.SSRSnapshot;


public class SSRSnapshotDAO extends RM3AbstractDAO {

	public SSRSnapshotDAO(Hotel currentHotel) {
		super(currentHotel);
		// TODO Auto-generated constructor stub
	}
	
	public List<SSRSnapshot> getAllSSRSnapshot(String firstElementCode){
		
		List<SSRSnapshot> lstSSRSnapshots = new ArrayList<SSRSnapshot>();
		//Build dummy snapshot to include the Add option
		if(firstElementCode != null) {
			SSRSnapshot addSnapshot = new SSRSnapshot();
			addSnapshot.setSnapshotName(firstElementCode);
			lstSSRSnapshots.add(addSnapshot);
		}
		
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
				
		try
		{
			conn = this.getCurrentHRR3Connection();
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");			
			String sCreateDate = null;
			String sYear = null;
			String sDescription = null;
			
			String element = null;
						
			String statement = "select distinct * from RM3SSRSnapshot s inner join RM3SSRSnapshotSUWklyMtg  w on w.ssrSnapshot_id = s.ssrSnapshot_id where s.hotel_id = ? order by created_ts desc";
			
			ps = conn.prepareStatement(statement);			
			ps.setInt(1, this.getCurrentHotel().getHotelId());			
			rs = ps.executeQuery();
			
			while(rs != null && rs.next()){	
				
				SSRSnapshot ssrSnapshot = new SSRSnapshot();
				
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
				
				/*Date dateObj = rs.getDate("created_ts");
				sCreateDate = dateObj != null ? dateFormat.format(dateObj) : "";
				sYear = Integer.toString(rs.getInt("snapshot_year"));
				sDescription = rs.getString("snapshot_name"); 
				
			    element = sCreateDate + " - " + sYear + " - ";      			            
			    
			    element = element + " " + getMonthName(rs.getInt("su_month")) + " Week# " + rs.getString("wk_no") + " - ";
			    element = element + sDescription;*/
															
				lstSSRSnapshots.add(ssrSnapshot);				
			}
						
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//return null;
		}
		
		finally { close(rs, ps); close(conn);}
		
		return lstSSRSnapshots;
	}
	
	
	
	public SSRSnapshot populateSSRSnapshot (int ssrSnapshotId){
		SSRSnapshot ssrSnapshot = new SSRSnapshot();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
		
				
		try
		{
			conn = this.getCurrentHRR3Connection();							
			String statement = "select w.*, s.snapshot_name, s.created_ts from RM3SSRSnapshot s left join RM3SSRSnapshotSUWklyMtg w on w.ssrsnapshot_id = s.ssrsnapshot_id where s.ssrsnapshot_id = ?";
			
			ps = conn.prepareStatement(statement);			
			ps.setInt(1, ssrSnapshotId);			
			rs = ps.executeQuery();
			
			while(rs != null && rs.next()){	
				
				Date dateObj = rs.getDate("created_ts");
				ssrSnapshot.setDateCreated(dateObj != null ? dateFormat2.format(dateObj) : "");
								
				ssrSnapshot.setMonth(rs.getInt("su_month"));
				ssrSnapshot.setYear(rs.getInt("su_year"));
				
				dateObj = rs.getDate("currmeetdate");
				ssrSnapshot.setCurrentMeetingDate(dateObj != null ? dateFormat.format(dateObj) : "");
				
				dateObj = rs.getDate("lastactdate");				
				ssrSnapshot.setLastDate(dateObj != null ? dateFormat.format(dateObj) : "");
				ssrSnapshot.setSnapshotName(rs.getString("snapshot_name"));
			}
						
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//return null;
		}
		
		finally { close(rs, ps); close(conn);}
		
		return ssrSnapshot;
	}
	
	public int takeSSRSnapshot(SSRSnapshot ssrSnapshot){
		int snapshotID = 0;
		
		int isSuccess = saveSSRSnapshotWklyMtg(ssrSnapshot);  
		
		if (isSuccess == 0){
			ChangeSSRActuals();
						
			String dateFrom = Integer.toString(ssrSnapshot.getYear()-1)+ "-01-01";
			String dateTo = Integer.toString(ssrSnapshot.getYear()+1)+ "-01-01";
					
			
			snapshotID = setSSRSnapsthot(ssrSnapshot, dateFrom, dateTo);
			
			return snapshotID;
			
			
		}else{
			return isSuccess;
		}
		
		//return -1  database error
		//return 0   ssrSnapshotID no generated
	}
	
	public int saveSSRSnapshotWklyMtg (SSRSnapshot ssrSnapshot){
		
		System.out.println("*********** saveSSRSnapshotWklyMtg - BEGIN - " + new Date() +" ***********");
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
					
		try
		{
			conn = this.getCurrentHRR3Connection();
						
			String statement = ("{call ftSSRSaveWklyMtg2 (?,?,?,?,?,?,?,?)}");			
			
			//p_hotelid int,p_month,p_year int,p_wkno varchar(5),p_meetdate datetime,p_actualdate datetime,p_snapshotid int,p_ssrSnapshot_id int,p_su_name varchar(100)
			ps = conn.prepareStatement(statement);			
			ps.setInt(1, this.getCurrentHotel().getHotelId());
			ps.setInt(2, ssrSnapshot.getMonth());
			ps.setInt(3, ssrSnapshot.getYear());			
			ps.setString(4, ssrSnapshot.getWeekNumber());
			ps.setString(5, ssrSnapshot.getCurrentMeetingDate());
			ps.setString(6, ssrSnapshot.getLastDate());
			ps.setInt(7, 0);			
			ps.setString(8, ssrSnapshot.getSnapshotName());			
			
			rs = ps.executeQuery();
			while(rs != null && rs.next()){	
				return rs.getInt("isSuccess");
			}
			
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		
		finally { close(rs, ps); close(conn);}
		
		System.out.println(" ***********saveSSRSnapshotWklyMtg - END - " + new Date()+ " ***********");
		
		return 0;
	}
	
	public void ChangeSSRActuals(){
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
					
		try
		{
			conn = this.getCurrentHRR3Connection();
						
			String statement = ("{call ftSSRChangeisActual (?)}");			
			ps = conn.prepareStatement(statement);	
			ps.setInt(1, this.getCurrentHotel().getHotelId());
			
			rs = ps.executeQuery();
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		finally { close(rs, ps); close(conn);}
		
	}
	
	public int setSSRSnapsthot(SSRSnapshot ssrSnapshot, String dateFrom, String dateTo){
		System.out.println("*********** saveSSRSnapsthot - BEGIN - " + new Date() +" ***********");
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
					
		try
		{
			conn = this.getCurrentHRR3Connection();
						
			String statement = ("{call ftSSRSnapshotCreate (?,?,?,?,?,?)}");			
			
			//p_hotelID int
			//p_snapshotname varchar(100)
			//p_year int
			//p_month` int
			//p_date_from string,
			//p_date_to String,
			
			ps = conn.prepareStatement(statement);			
			ps.setInt(1, this.getCurrentHotel().getHotelId());
			ps.setString(2, ssrSnapshot.getSnapshotName());	
			ps.setInt(3, ssrSnapshot.getYear());			
			ps.setInt(4, ssrSnapshot.getMonth());								
			ps.setString(5, dateFrom);
			ps.setString(6,dateTo);			
			
					
			
			rs = ps.executeQuery();
			while(rs != null && rs.next()){	
				return rs.getInt("snapshotID");
			}
			
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		
		finally { close(rs, ps); close(conn);}
		
		System.out.println(" ***********saveSSRSnapsthot - END - " + new Date()+ " ***********");
		
		return 0;
	}
	
	public String setupSnapshotInformation(int ssrSnapshotId){
				 					
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;	
		
		String ssrSnapshotInfo = "";
				
		try
		{
			conn = this.getCurrentHRR3Connection();							
			String statement = "select * from RM3SSRSnapshot where hotel_id=? and ssrsnapshot_id= ?";
			
			ps = conn.prepareStatement(statement);
			ps.setInt(1, this.getCurrentHotel().getHotelId());
			ps.setInt(2, ssrSnapshotId);			
			rs = ps.executeQuery();
			
			while(rs != null && rs.next()){	
								
				ssrSnapshotInfo = rs.getString("snapshot_name")+ Integer.toString(rs.getInt("snapshot_year"));
			}
						
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}
		
		finally { close(rs, ps); close(conn);}
		
		return ssrSnapshotInfo;
	}
	
	public boolean restoreSSRSnapshot(int ssrSnapshotId){
		System.out.println("*********** restoreSSRSnapshot - BEGIN - " + new Date() +" ***********");
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
					
		try
		{
			conn = this.getCurrentHRR3Connection();
						
			String statement = ("{call ftSSRSnapshotRestore (?)}");			
			
			//p_hotelID int			
			//p_date_from string,
			//p_date_to String,
			
			ps = conn.prepareStatement(statement);			
			ps.setInt(1, ssrSnapshotId);													
			
					
			
			rs = ps.executeQuery();
			while(rs != null && rs.next()){	
				boolean result =  rs.getInt("isSuccess") == 0 ? true : false;				
				return result;
			}
			
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		finally { close(rs, ps); close(conn);}
		
		System.out.println(" ***********restoreSSRSnapshot - END - " + new Date()+ " ***********");
		
		
		return false;
	}
	
	public int deleteSSRSnapshot (int ssrSnapshotId ){
System.out.println("*********** deleteSSRSnapshot - BEGIN - " + new Date() +" ***********");
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
					
		try
		{
			conn = this.getCurrentHRR3Connection();
						
			String statement = ("{call ftSSRSnapshotDelete (?)}");			
			
			
			ps = conn.prepareStatement(statement);			
			ps.setInt(1, ssrSnapshotId);										
											
			
			rs = ps.executeQuery();
			while(rs != null && rs.next()){	
				return rs.getInt("isSuccess");
			}
			
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		
		finally { close(rs, ps); close(conn);}
		
		System.out.println(" ***********deleteSSRSnapshot - END - " + new Date()+ " ***********");
		
		
		return -1;
	}
	

}
