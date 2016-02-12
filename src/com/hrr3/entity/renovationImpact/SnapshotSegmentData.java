package com.hrr3.entity.renovationImpact;

import java.math.BigDecimal;

public class SnapshotSegmentData {

	private int snapshotId;
	private int hotelId;
	private String statDate;
	private int occTotal;
	private BigDecimal revTotal;
	private BigDecimal adrTotal;
	private BigDecimal oooRooms;
	
	
	public SnapshotSegmentData() {
		// TODO Auto-generated constructor stub
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
	 * @return the occTotal
	 */
	public int getOccTotal() {
		return occTotal;
	}


	/**
	 * @param occTotal the occTotal to set
	 */
	public void setOccTotal(int occTotal) {
		this.occTotal = occTotal;
	}


	/**
	 * @return the revTotal
	 */
	public BigDecimal getRevTotal() {
		return revTotal;
	}


	/**
	 * @param revTotal the revTotal to set
	 */
	public void setRevTotal(BigDecimal revTotal) {
		this.revTotal = revTotal;
	}


	/**
	 * @return the adrTotal
	 */
	public BigDecimal getAdrTotal() {
		return adrTotal;
	}


	/**
	 * @param adrTotal the adrTotal to set
	 */
	public void setAdrTotal(BigDecimal adrTotal) {
		this.adrTotal = adrTotal;
	}


	/**
	 * @return the oooRooms
	 */
	public BigDecimal getOooRooms() {
		return oooRooms;
	}


	/**
	 * @param oooRooms the oooRooms to set
	 */
	public void setOooRooms(BigDecimal oooRooms) {
		this.oooRooms = oooRooms;
	}	

}
