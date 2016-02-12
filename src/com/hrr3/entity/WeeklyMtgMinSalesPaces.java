package com.hrr3.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class WeeklyMtgMinSalesPaces implements Serializable, Cloneable {
	
	private static final long serialVersionUID = 1L;

	public WeeklyMtgMinSalesPaces() {
		// TODO Auto-generated constructor stub
	}
	
	private String pickupSince;
	private String rowType;
	private int seg;
	private BigDecimal janOcc;
	private BigDecimal febOcc;
	private BigDecimal marOcc;
	private BigDecimal aprOcc;
	private BigDecimal mayOcc;
	private BigDecimal junOcc;
	private BigDecimal julOcc;
	private BigDecimal augOcc;
	private BigDecimal sepOcc;
	private BigDecimal octOcc;
	private BigDecimal novOcc;
	private BigDecimal decOcc;

	/**
	 * @return the pickupSince
	 */
	public String getPickupSince() {
		return pickupSince;
	}
	/**
	 * @param pickupSince the pickupSince to set
	 */
	public void setPickupSince(String pickupSince) {
		this.pickupSince = pickupSince;
	}
	/**
	 * @return the rowType
	 */
	public String getRowType() {
		return rowType;
	}
	
	public String getRowTypeTranslated() {
		
		if(rowType.equalsIgnoreCase("ROTB_TRANS")) return "Transient on the Books";
		else if(rowType.equalsIgnoreCase("ROTB_TRANS_PU")) return "pick-up since last wk";
		else if(rowType.equalsIgnoreCase("ROTB_GROUP")) return "Group on the Books";
		else if(rowType.equalsIgnoreCase("ROTB_GROUP_PU")) return "pick-up since last wk";
		else if(rowType.equalsIgnoreCase("ROTB_CONT")) return "Contract on the Books";
		else if(rowType.equalsIgnoreCase("ROTB_CONT_PU")) return "pick-up since last wk";
		
		else return "Unknow";
		
	}
	/**
	 * @param rowType the rowType to set
	 */
	public void setRowType(String rowType) {
		this.rowType = rowType;
	}
	/**
	 * @return the seg
	 */
	public int getSeg() {
		return seg;
	}
	/**
	 * @param seg the seg to set
	 */
	public void setSeg(int seg) {
		this.seg = seg;
	}
	/**
	 * @return the janOcc
	 */
	public BigDecimal getJanOcc() {
		return janOcc;
	}
	/**
	 * @param janOcc the janOcc to set
	 */
	public void setJanOcc(BigDecimal janOcc) {
		this.janOcc = janOcc;
	}
	/**
	 * @return the febOcc
	 */
	public BigDecimal getFebOcc() {
		return febOcc;
	}
	/**
	 * @param febOcc the febOcc to set
	 */
	public void setFebOcc(BigDecimal febOcc) {
		this.febOcc = febOcc;
	}
	/**
	 * @return the marOcc
	 */
	public BigDecimal getMarOcc() {
		return marOcc;
	}
	/**
	 * @param marOcc the marOcc to set
	 */
	public void setMarOcc(BigDecimal marOcc) {
		this.marOcc = marOcc;
	}
	/**
	 * @return the aprOcc
	 */
	public BigDecimal getAprOcc() {
		return aprOcc;
	}
	/**
	 * @param aprOcc the aprOcc to set
	 */
	public void setAprOcc(BigDecimal aprOcc) {
		this.aprOcc = aprOcc;
	}
	/**
	 * @return the mayOcc
	 */
	public BigDecimal getMayOcc() {
		return mayOcc;
	}
	/**
	 * @param mayOcc the mayOcc to set
	 */
	public void setMayOcc(BigDecimal mayOcc) {
		this.mayOcc = mayOcc;
	}
	/**
	 * @return the junOcc
	 */
	public BigDecimal getJunOcc() {
		return junOcc;
	}
	/**
	 * @param junOcc the junOcc to set
	 */
	public void setJunOcc(BigDecimal junOcc) {
		this.junOcc = junOcc;
	}
	/**
	 * @return the julOcc
	 */
	public BigDecimal getJulOcc() {
		return julOcc;
	}
	/**
	 * @param julOcc the julOcc to set
	 */
	public void setJulOcc(BigDecimal julOcc) {
		this.julOcc = julOcc;
	}
	/**
	 * @return the augOcc
	 */
	public BigDecimal getAugOcc() {
		return augOcc;
	}
	/**
	 * @param augOcc the augOcc to set
	 */
	public void setAugOcc(BigDecimal augOcc) {
		this.augOcc = augOcc;
	}
	/**
	 * @return the sepOcc
	 */
	public BigDecimal getSepOcc() {
		return sepOcc;
	}
	/**
	 * @param sepOcc the sepOcc to set
	 */
	public void setSepOcc(BigDecimal sepOcc) {
		this.sepOcc = sepOcc;
	}
	/**
	 * @return the octOcc
	 */
	public BigDecimal getOctOcc() {
		return octOcc;
	}
	/**
	 * @param octOcc the octOcc to set
	 */
	public void setOctOcc(BigDecimal octOcc) {
		this.octOcc = octOcc;
	}
	/**
	 * @return the novOcc
	 */
	public BigDecimal getNovOcc() {
		return novOcc;
	}
	/**
	 * @param novOcc the novOcc to set
	 */
	public void setNovOcc(BigDecimal novOcc) {
		this.novOcc = novOcc;
	}
	/**
	 * @return the decOcc
	 */
	public BigDecimal getDecOcc() {
		return decOcc;
	}
	/**
	 * @param decOcc the decOcc to set
	 */
	public void setDecOcc(BigDecimal decOcc) {
		this.decOcc = decOcc;
	}

}
