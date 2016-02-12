package com.hrr3.entity.ssrMigration;

import java.io.Serializable;

public class SSRSnapshotSUWklyMtgSS implements Serializable, Cloneable {
	
	private static final long serialVersionUID = 1L;
	
	private	int	ssrSnapshotId;
	private	int	suwklyId;
	private	int	hotelId;
	private	String	softspot;
	private	String	strategy;
	private int softId;


	public SSRSnapshotSUWklyMtgSS() {
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
	 * @return the softspot
	 */
	public String getSoftspot() {
		return softspot;
	}


	/**
	 * @param softspot the softspot to set
	 */
	public void setSoftspot(String softspot) {
		this.softspot = softspot;
	}


	/**
	 * @return the strategy
	 */
	public String getStrategy() {
		return strategy;
	}


	/**
	 * @param strategy the strategy to set
	 */
	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}


	/**
	 * @return the softId
	 */
	public int getSoftId() {
		return softId;
	}


	/**
	 * @param softId the softId to set
	 */
	public void setSoftId(int softId) {
		this.softId = softId;
	}

}
