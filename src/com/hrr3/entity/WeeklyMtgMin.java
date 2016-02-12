package com.hrr3.entity;

import java.io.Serializable;

import com.hrr3.entity.ssrMigration.SSRSnapshotSUWklyMtg;

public class WeeklyMtgMin implements Serializable, Cloneable {
	
	private static final long serialVersionUID = 1L;
	

	public WeeklyMtgMin() {
		// TODO Auto-generated constructor stub
	}
	
	private String hotelName;
	private SSRSnapshotSUWklyMtg  ssrSnapshotSUWklyMtg;
	private String activityDate;


	/**
	 * @return the hotelName
	 */
	public String getHotelName() {
		return hotelName;
	}
	/**
	 * @param hotelName the hotelName to set
	 */
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	/**
	 * @return the ssrSnapshotSUWklyMtg
	 */
	public SSRSnapshotSUWklyMtg getSsrSnapshotSUWklyMtg() {
		return ssrSnapshotSUWklyMtg;
	}
	/**
	 * @param ssrSnapshotSUWklyMtg the ssrSnapshotSUWklyMtg to set
	 */
	public void setSsrSnapshotSUWklyMtg(SSRSnapshotSUWklyMtg ssrSnapshotSUWklyMtg) {
		this.ssrSnapshotSUWklyMtg = ssrSnapshotSUWklyMtg;
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

}
