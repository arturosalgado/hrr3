package com.hrr3.entity.ssrMigration;

import java.io.Serializable;

public class SSRSnapshotCOTB implements Serializable, Cloneable {
	
	private static final long serialVersionUID = 1L;
	
	private	int	ssrSnapshotId;
	private	int	hotelId;
	private	int	cotbYear;
	private	int	cotbMonth;
	private	String	activityDate;
	private	int	curDef;
	private	int	prevDef;
	private	String	createdTs;


	public SSRSnapshotCOTB() {
		// TODO Auto-generated constructor stub
	}


	/**
	 * @return the ssrSnapshotId
	 */
	public int getSsrSnapshotId() {
		return ssrSnapshotId;
	}


	/**
	 * @param ssrSnapshotId the ssrSnapshotId to set
	 */
	public void setSsrSnapshotId(int ssrSnapshotId) {
		this.ssrSnapshotId = ssrSnapshotId;
	}


	/**
	 * @return the hotelId
	 */
	public int getHotelId() {
		return hotelId;
	}


	/**
	 * @param hotelId the hotelId to set
	 */
	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}


	/**
	 * @return the cotbYear
	 */
	public int getCotbYear() {
		return cotbYear;
	}


	/**
	 * @param cotbYear the cotbYear to set
	 */
	public void setCotbYear(int cotbYear) {
		this.cotbYear = cotbYear;
	}


	/**
	 * @return the cotbMonth
	 */
	public int getCotbMonth() {
		return cotbMonth;
	}


	/**
	 * @param cotbMonth the cotbMonth to set
	 */
	public void setCotbMonth(int cotbMonth) {
		this.cotbMonth = cotbMonth;
	}


	/**
	 * @return the activityDate
	 */
	public String getActivityDate() {
		return activityDate;
	}


	/**
	 * @param activityDate the activityDate to set
	 */
	public void setActivityDate(String activityDate) {
		this.activityDate = activityDate;
	}


	/**
	 * @return the curDef
	 */
	public int getCurDef() {
		return curDef;
	}


	/**
	 * @param curDef the curDef to set
	 */
	public void setCurDef(int curDef) {
		this.curDef = curDef;
	}


	/**
	 * @return the prevDef
	 */
	public int getPrevDef() {
		return prevDef;
	}


	/**
	 * @param prevDef the prevDef to set
	 */
	public void setPrevDef(int prevDef) {
		this.prevDef = prevDef;
	}


	/**
	 * @return the createdTs
	 */
	public String getCreatedTs() {
		return createdTs;
	}


	/**
	 * @param createdTs the createdTs to set
	 */
	public void setCreatedTs(String createdTs) {
		this.createdTs = createdTs;
	}

}
