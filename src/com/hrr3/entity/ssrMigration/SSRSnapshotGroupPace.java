package com.hrr3.entity.ssrMigration;

import java.io.Serializable;
import java.math.BigDecimal;

public class SSRSnapshotGroupPace implements Serializable, Cloneable {
	
	private static final long serialVersionUID = 1L;
	
	private	int	ssrSnapshotId;
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
	 * @return the gpYear
	 */
	public int getGpYear() {
		return gpYear;
	}


	/**
	 * @param gpYear the gpYear to set
	 */
	public void setGpYear(int gpYear) {
		this.gpYear = gpYear;
	}


	/**
	 * @return the gpMonth
	 */
	public int getGpMonth() {
		return gpMonth;
	}


	/**
	 * @param gpMonth the gpMonth to set
	 */
	public void setGpMonth(int gpMonth) {
		this.gpMonth = gpMonth;
	}


	/**
	 * @return the occ
	 */
	public BigDecimal getOcc() {
		return occ;
	}


	/**
	 * @param occ the occ to set
	 */
	public void setOcc(BigDecimal occ) {
		this.occ = occ;
	}


	private	int	hotelId;
	private	int	gpYear;
	private	int	gpMonth;
	private	BigDecimal	occ;


	public SSRSnapshotGroupPace() {
		// TODO Auto-generated constructor stub
	}

}
