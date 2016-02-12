package com.hrr3.entity.proforma;

import java.io.Serializable;

public class ProFormaData implements Serializable,Cloneable {
	private static final long serialVersionUID = 1L;

	public ProFormaData() {
		// TODO Auto-generated constructor stub
	}
	
	private int snapshotId;
	private int hotelId;
	private String statDate;
	private int iChangeRms;
	private double iChangeRate;
	private int iPropBaseRooms;
	private double iPropBaseRate;
	private double iDispAdrOverride;

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
	 * @return the iChangeRms
	 */
	public int getiChangeRms() {
		return iChangeRms;
	}
	/**
	 * @param iChangeRms the iChangeRms to set
	 */
	public void setiChangeRms(int iChangeRms) {
		this.iChangeRms = iChangeRms;
	}
	/**
	 * @return the iChangeRate
	 */
	public double getiChangeRate() {
		return iChangeRate;
	}
	/**
	 * @param iChangeRate the iChangeRate to set
	 */
	public void setiChangeRate(double iChangeRate) {
		this.iChangeRate = iChangeRate;
	}
	/**
	 * @return the iPropBaseRooms
	 */
	public int getiPropBaseRooms() {
		return iPropBaseRooms;
	}
	/**
	 * @param iPropBaseRooms the iPropBaseRooms to set
	 */
	public void setiPropBaseRooms(int iPropBaseRooms) {
		this.iPropBaseRooms = iPropBaseRooms;
	}
	/**
	 * @return the iPropBaseRate
	 */
	public double getiPropBaseRate() {
		return iPropBaseRate;
	}
	/**
	 * @param iPropBaseRate the iPropBaseRate to set
	 */
	public void setiPropBaseRate(double iPropBaseRate) {
		this.iPropBaseRate = iPropBaseRate;
	}
	/**
	 * @return the iDispAdrOverride
	 */
	public double getiDispAdrOverride() {
		return iDispAdrOverride;
	}
	/**
	 * @param iDispAdrOverride the iDispAdrOverride to set
	 */
	public void setiDispAdrOverride(double iDispAdrOverride) {
		this.iDispAdrOverride = iDispAdrOverride;
	}
	

}
