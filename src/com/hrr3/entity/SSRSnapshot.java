package com.hrr3.entity;

import java.io.Serializable;

public class SSRSnapshot implements Serializable, Cloneable{
	
	private static final long serialVersionUID = 1L;

	public SSRSnapshot() {
		// TODO Auto-generated constructor stub
	}
	
	private int snapshotId;
	private int hotelId;
	private String  snapshotName;
	private String dateCreated;
	private int month;
	private int year;
	private String weekNumber;
	private String currentMeetingDate;
	private String lastDate;
	private String restoreTS;
	private boolean isMigrated;
	private String migratedTS;

	/**
	 * @return the restoreTS
	 */
	public String getRestoreTS() {
		return restoreTS;
	}
	/**
	 * @param restoreTS the restoreTS to set
	 */
	public void setRestoreTS(String restoreTS) {
		this.restoreTS = restoreTS;
	}
	/**
	 * @return the isMigrated
	 */
	public boolean isMigrated() {
		return isMigrated;
	}
	/**
	 * @param isMigrated the isMigrated to set
	 */
	public void setMigrated(boolean isMigrated) {
		this.isMigrated = isMigrated;
	}
	/**
	 * @return the migratedTS
	 */
	public String getMigratedTS() {
		return migratedTS;
	}
	/**
	 * @param migratedTS the migratedTS to set
	 */
	public void setMigratedTS(String migratedTS) {
		this.migratedTS = migratedTS;
	}
	/**
	 * @return the snapshotName
	 */
	public String getSnapshotName() {
		return snapshotName;
	}
	/**
	 * @param snapshotName the snapshotName to set
	 */
	public void setSnapshotName(String snapshotName) {
		this.snapshotName = snapshotName;
	}
	/**
	 * @return the dateCreated
	 */
	public String getDateCreated() {
		return dateCreated;
	}
	/**
	 * @param dateCreated the dateCreated to set
	 */
	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}
	/**
	 * @return the month
	 */
	public int getMonth() {
		return month;
	}
	/**
	 * @param month the month to set
	 */
	public void setMonth(int month) {
		this.month = month;
	}
	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}
	/**
	 * @param year the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}
	/**
	 * @return the weekNumber
	 */
	public String getWeekNumber() {
		return weekNumber;
	}
	/**
	 * @param weekNumber the weekNumber to set
	 */
	public void setWeekNumber(String weekNumber) {
		this.weekNumber = weekNumber;
	}
	/**
	 * @return the currentMeetingDate
	 */
	public String getCurrentMeetingDate() {
		return currentMeetingDate;
	}
	/**
	 * @param currentMeetingDate the currentMeetingDate to set
	 */
	public void setCurrentMeetingDate(String currentMeetingDate) {
		this.currentMeetingDate = currentMeetingDate;
	}
	/**
	 * @return the lastDate
	 */
	public String getLastDate() {
		return lastDate;
	}
	/**
	 * @param lastDate the lastDate to set
	 */
	public void setLastDate(String lastDate) {
		this.lastDate = lastDate;
	}
	/**
	 * @return the snapshotId
	 */
	public int getSnapshotId() {
		return snapshotId;
	}
	/**
	 * @param snapshotId the snapshotId to set
	 */
	public void setSnapshotId(int snapshotId) {
		this.snapshotId = snapshotId;
	}
	/**
	 * @return the hotelid
	 */
	public int getHotelId() {
		return hotelId;
	}
	/**
	 * @param hotelid the hotelid to set
	 */
	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}
	
	public String getIsMigrated() {
		
		return this.isMigrated == true ? "Y" : "N";
	}
	
	public String getNameToDisplay () {
		
		if(this.snapshotName.equals("ADD_SNAPSHOT")) {
			
			return "-- Add New Snapshot for Hotel --";
		}
		
		if(this.snapshotName.equals("REPORT_SNAPSHOT")) {
			
			return "-- Select a Snapshot --";
		}
		
		if(this.snapshotName.equals("REPORT_SNAPSHOT_B")) {
			
			return "-- Select Base Snapshot --";
		}
		
		if(this.snapshotName.equals("REPORT_SNAPSHOT_1")) {
			
			return "-- Select Snapshot 1 --";
		}
		
		if(this.snapshotName.equals("REPORT_SNAPSHOT_2")) {
			
			return "-- Select Snapshot 2 --";
		}

		
		else {
			
			/*Date dateObj = rs.getDate("created_ts");
			sCreateDate = dateObj != null ? dateFormat.format(dateObj) : "";
			sYear = Integer.toString(rs.getInt("snapshot_year"));
			sDescription = rs.getString("snapshot_name"); 
			
		    element = sCreateDate + " - " + sYear + " - ";      			            
		    
		    element = element + " " + getMonthName(rs.getInt("su_month")) + " Week# " + rs.getString("wk_no") + " - ";
		    element = element + sDescription;*/
			
			String nameToDisplay = this.dateCreated + " - " + this.year + " - ";      	
			nameToDisplay = nameToDisplay + " " + getMonthName(this.month) + " Week# " + this.weekNumber + " - " + this.snapshotName;
			
			return nameToDisplay;
		}	
		
	}
	
	//Used for Pace Report
	public String getNameToDisplayMinimal () {
		
		
		if(this.snapshotName.equals("REPORT_SNAPSHOT")) {
			
			return "-- Select a Snapshot --";
		}
		
		
		else {
			
			String nameToDisplay = this.year + " - " + this.snapshotName;  
			return nameToDisplay;
		}	
		
	}

	public String getMonthName (int monthNumber){		
	
	 String monthString;
       switch (monthNumber) {
           case 1:  monthString = "January";
                    break;
           case 2:  monthString = "February";
                    break;
           case 3:  monthString = "March";
                    break;
           case 4:  monthString = "April";
                    break;
           case 5:  monthString = "May";
                    break;
           case 6:  monthString = "June";
                    break;
           case 7:  monthString = "July";
                    break;
           case 8:  monthString = "August";
                    break;
           case 9:  monthString = "September";
                    break;
           case 10: monthString = "October";
                    break;
           case 11: monthString = "November";
                    break;
           case 12: monthString = "December";
                    break;
           default: monthString = "Invalid month";
                    break;
       }
	
	return monthString;
}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + snapshotId;
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SSRSnapshot other = (SSRSnapshot) obj;
		if (snapshotId != other.snapshotId)
			return false;
		return true;
	}

}
