package com.hrr3.entity.ssrMigration;

import java.io.Serializable;

public class SSRSnapshotSUWklyMtg implements Serializable, Cloneable {
	
	private static final long serialVersionUID = 1L;
	
	private	int	ssrSnapshotId;
	private	int	hotelId;
	private	int	suMonth;
	private	int	suYear;
	private	String	wkNo;
	private	String	currmeetdate;
	private	String	lastwkdate;
	private	String	lastactdate;
	private	int	snapshotId;
	private	String	attendees;
	private	String	critique;
	private	String	outlook;
	private	String	other;
	private	int	savedSnapshotid;
	private	String	transotbEntered;


	public SSRSnapshotSUWklyMtg() {
		// TODO Auto-generated constructor stub
	}


	/**
	 * @return the ssrSnapshoId
	 */
	public int getSsrSnapshotId() {
		return ssrSnapshotId;
	}


	/**
	 * @param ssrSnapshoId the ssrSnapshoId to set
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
	 * @return the suMonth
	 */
	public int getSuMonth() {
		return suMonth;
	}


	/**
	 * @param suMonth the suMonth to set
	 */
	public void setSuMonth(int suMonth) {
		this.suMonth = suMonth;
	}


	/**
	 * @return the suYear
	 */
	public int getSuYear() {
		return suYear;
	}


	/**
	 * @param suYear the suYear to set
	 */
	public void setSuYear(int suYear) {
		this.suYear = suYear;
	}


	/**
	 * @return the wkNo
	 */
	public String getWkNo() {
		return wkNo;
	}


	/**
	 * @param wkNo the wkNo to set
	 */
	public void setWkNo(String wkNo) {
		this.wkNo = wkNo;
	}


	/**
	 * @return the currmeetdate
	 */
	public String getCurrmeetdate() {
		return currmeetdate;
	}


	/**
	 * @param currmeetdate the currmeetdate to set
	 */
	public void setCurrmeetdate(String currmeetdate) {
		this.currmeetdate = currmeetdate;
	}


	/**
	 * @return the lastwkdate
	 */
	public String getLastwkdate() {
		return lastwkdate;
	}


	/**
	 * @param lastwkdate the lastwkdate to set
	 */
	public void setLastwkdate(String lastwkdate) {
		this.lastwkdate = lastwkdate;
	}


	/**
	 * @return the lastactdate
	 */
	public String getLastactdate() {
		return lastactdate;
	}


	/**
	 * @param lastactdate the lastactdate to set
	 */
	public void setLastactdate(String lastactdate) {
		this.lastactdate = lastactdate;
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
	 * @return the attendees
	 */
	public String getAttendees() {
		return attendees;
	}


	/**
	 * @param attendees the attendees to set
	 */
	public void setAttendees(String attendees) {
		this.attendees = attendees;
	}


	/**
	 * @return the critique
	 */
	public String getCritique() {
		return critique;
	}


	/**
	 * @param critique the critique to set
	 */
	public void setCritique(String critique) {
		this.critique = critique;
	}


	/**
	 * @return the outlook
	 */
	public String getOutlook() {
		return outlook;
	}


	/**
	 * @param outlook the outlook to set
	 */
	public void setOutlook(String outlook) {
		this.outlook = outlook;
	}


	/**
	 * @return the other
	 */
	public String getOther() {
		return other;
	}


	/**
	 * @param other the other to set
	 */
	public void setOther(String other) {
		this.other = other;
	}


	/**
	 * @return the savedSnapshotid
	 */
	public int getSavedSnapshotid() {
		return savedSnapshotid;
	}


	/**
	 * @param savedSnapshotid the savedSnapshotid to set
	 */
	public void setSavedSnapshotid(int savedSnapshotid) {
		this.savedSnapshotid = savedSnapshotid;
	}


	/**
	 * @return the transotbEntered
	 */
	public String getTransotbEntered() {
		return transotbEntered;
	}


	/**
	 * @param transotbEntered the transotbEntered to set
	 */
	public void setTransotbEntered(String transotbEntered) {
		this.transotbEntered = transotbEntered;
	}

}
