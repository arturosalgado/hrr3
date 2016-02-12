package com.hrr3.entity.ssrMigration;

import java.io.Serializable;

public class SSRSnapshotSUWklyMtgHD implements Serializable, Cloneable {
	
	private static final long serialVersionUID = 1L;
	
	private	int	ssrSnapshotId;
	private	int	suwklyId;
	private	int	hotelId;
	private	String	peakdate;
	private	String	sellingRest;
	private	String	outcome;
	private int highId;


	public SSRSnapshotSUWklyMtgHD() {
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
	 * @return the suwklyId
	 */
	public int getSuwklyId() {
		return suwklyId;
	}


	/**
	 * @param suwklyId the suwklyId to set
	 */
	public void setSuwklyId(int suwklyId) {
		this.suwklyId = suwklyId;
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
	 * @return the peakdate
	 */
	public String getPeakdate() {
		return peakdate;
	}


	/**
	 * @param peakdate the peakdate to set
	 */
	public void setPeakdate(String peakdate) {
		this.peakdate = peakdate;
	}


	/**
	 * @return the sellingRest
	 */
	public String getSellingRest() {
		return sellingRest;
	}


	/**
	 * @param sellingRest the sellingRest to set
	 */
	public void setSellingRest(String sellingRest) {
		this.sellingRest = sellingRest;
	}


	/**
	 * @return the outcome
	 */
	public String getOutcome() {
		return outcome;
	}


	/**
	 * @param outcome the outcome to set
	 */
	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}


	/**
	 * @return the highId
	 */
	public int getHighId() {
		return highId;
	}


	/**
	 * @param highId the highId to set
	 */
	public void setHighId(int highId) {
		this.highId = highId;
	}


	

}
