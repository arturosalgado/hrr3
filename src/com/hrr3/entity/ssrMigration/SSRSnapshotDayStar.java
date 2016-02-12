package com.hrr3.entity.ssrMigration;

import java.io.Serializable;

public class SSRSnapshotDayStar implements Serializable, Cloneable {
	
	private static final long serialVersionUID = 1L;
	
	private	int	ssrSnapshotId;
	private	int	hotelId;
	private	String	dateFrom;
	private	String	dateTo;
	private	String	capHotel;
	private	String	capHotel2;
	private	String	capWeek;
	private	String	createdTs;
	private int dayStartId;
	private int dayStartIdRM2;

	public SSRSnapshotDayStar() {
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
	 * @return the dateFrom
	 */
	public String getDateFrom() {
		return dateFrom;
	}

	/**
	 * @param dateFrom the dateFrom to set
	 */
	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	/**
	 * @return the dateTo
	 */
	public String getDateTo() {
		return dateTo;
	}

	/**
	 * @param dateTo the dateTo to set
	 */
	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	/**
	 * @return the capHotel
	 */
	public String getCapHotel() {
		return capHotel;
	}

	/**
	 * @param capHotel the capHotel to set
	 */
	public void setCapHotel(String capHotel) {
		this.capHotel = capHotel;
	}

	/**
	 * @return the capHotel2
	 */
	public String getCapHotel2() {
		return capHotel2;
	}

	/**
	 * @param capHotel2 the capHotel2 to set
	 */
	public void setCapHotel2(String capHotel2) {
		this.capHotel2 = capHotel2;
	}

	/**
	 * @return the capWeek
	 */
	public String getCapWeek() {
		return capWeek;
	}

	/**
	 * @param capWeek the capWeek to set
	 */
	public void setCapWeek(String capWeek) {
		this.capWeek = capWeek;
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

	/**
	 * @return the dayStartId
	 */
	public int getDayStartId() {
		return dayStartId;
	}

	/**
	 * @param dayStartId the dayStartId to set
	 */
	public void setDayStartId(int dayStartId) {
		this.dayStartId = dayStartId;
	}

	/**
	 * @return the dayStartIdRM2
	 */
	public int getDayStartIdRM2() {
		return dayStartIdRM2;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SSRSnapshotDayStar [ssrSnapshotId=" + ssrSnapshotId
				+ ", hotelId=" + hotelId + ", dateFrom=" + dateFrom
				+ ", dateTo=" + dateTo + ", capHotel=" + capHotel
				+ ", capHotel2=" + capHotel2 + ", capWeek=" + capWeek
				+ ", createdTs=" + createdTs + ", dayStartId=" + dayStartId
				+ ", dayStartIdRM2=" + dayStartIdRM2 + "]";
	}

	/**
	 * @param dayStartIdRM2 the dayStartIdRM2 to set
	 */
	public void setDayStartIdRM2(int dayStartIdRM2) {
		this.dayStartIdRM2 = dayStartIdRM2;
	}

}
