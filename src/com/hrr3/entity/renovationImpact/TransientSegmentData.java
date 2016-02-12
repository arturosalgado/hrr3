package com.hrr3.entity.renovationImpact;

import java.io.Serializable;
import java.math.BigDecimal;

public class TransientSegmentData implements Serializable,Cloneable {
	
	private static final long serialVersionUID = 1L;
	
	private int snapshotId;
	private int hotelId;
	private String statDate;
	private String comments;
	private String dow;
	private int isActual;
	private BigDecimal totOccPct;

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
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return the dow
	 */
	public String getDow() {
		return dow;
	}

	/**
	 * @param dow the dow to set
	 */
	public void setDow(String dow) {
		this.dow = dow;
	}

	/**
	 * @return the isActual
	 */
	public int getIsActual() {
		return isActual;
	}

	/**
	 * @param isActual the isActual to set
	 */
	public void setIsActual(int isActual) {
		this.isActual = isActual;
	}

	/**
	 * @return the totOccPct
	 */
	public BigDecimal getTotOccPct() {
		return totOccPct;
	}

	/**
	 * @param totOccPct the totOccPct to set
	 */
	public void setTotOccPct(BigDecimal totOccPct) {
		this.totOccPct = totOccPct;
	}

	public TransientSegmentData() {
		// TODO Auto-generated constructor stub
	}

}
