package com.hrr3.entity.renovationImpact;

import java.io.Serializable;
import java.math.BigDecimal;

public class SSRSegmentData implements Serializable,Cloneable {
	
	private static final long serialVersionUID = 1L;
	
	private int ssrSnapshotid;
	private int hotelId;
	private String statDate;
	private BigDecimal groupDemandTD;

	public SSRSegmentData() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the ssrSnapshotid
	 */
	public int getSsrSnapshotid() {
		return ssrSnapshotid;
	}

	/**
	 * @param ssrSnapshotid the ssrSnapshotid to set
	 */
	public void setSsrSnapshotid(int ssrSnapshotid) {
		this.ssrSnapshotid = ssrSnapshotid;
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
	 * @return the statDate
	 */
	public String getStatDate() {
		return statDate;
	}

	/**
	 * @param statDate the statDate to set
	 */
	public void setStatDate(String statDate) {
		this.statDate = statDate;
	}

	/**
	 * @return the groupDemandTD
	 */
	public BigDecimal getGroupDemandTD() {
		return groupDemandTD;
	}

	/**
	 * @param groupDemandTD the groupDemandTD to set
	 */
	public void setGroupDemandTD(BigDecimal groupDemandTD) {
		this.groupDemandTD = groupDemandTD;
	}

}
