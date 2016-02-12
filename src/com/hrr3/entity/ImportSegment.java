package com.hrr3.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class ImportSegment implements Serializable,Cloneable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	int hotelId;
	int userId;
	int customerId;
	String segmentName;	
	String transDate;
    BigDecimal defOCC;
    BigDecimal defADR;
    BigDecimal tenOCC;
    BigDecimal tenADR;
    BigDecimal totOCC;
    BigDecimal totADR;
    String WSName;
    String tableType;
        
	public ImportSegment() {
		// TODO Auto-generated constructor stub
	}
	
	public ImportSegment(int hotelId,  int userId, String segmentName, String transDate, String WSName,  String tableType) {
		this.hotelId = hotelId;
		this.userId =  userId;		
		this.segmentName = segmentName;
		this.transDate = transDate;
		this.defOCC = new BigDecimal(0);
		this.defADR = new BigDecimal(0);
		this.tenOCC = new BigDecimal(0);		
		this.tenADR = new BigDecimal(0);
		this.totOCC = new BigDecimal(0);
		this.totADR = new BigDecimal(0);
		this.WSName = WSName;
		this.tableType = tableType;
		
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
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return the customerId
	 */
	public int getCustomerId() {
		return customerId;
	}

	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the segmentName
	 */
	public String getSegmentName() {
		return segmentName;
	}

	/**
	 * @param segmentName the segmentName to set
	 */
	public void setSegmentName(String segmentName) {
		this.segmentName = segmentName;
	}

	/**
	 * @return the transDate
	 */
	public String getTransDate() {
		return transDate;
	}

	/**
	 * @param transDate the transDate to set
	 */
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}

	/**
	 * @return the defOCC
	 */
	public BigDecimal getDefOCC() {
		return defOCC;
	}

	/**
	 * @param defOCC the defOCC to set
	 */
	public void setDefOCC(BigDecimal defOCC) {
		this.defOCC = defOCC;
	}

	/**
	 * @return the defADR
	 */
	public BigDecimal getDefADR() {
		return defADR;
	}

	/**
	 * @param defADR the defADR to set
	 */
	public void setDefADR(BigDecimal defADR) {
		this.defADR = defADR;
	}

	/**
	 * @return the tenOCC
	 */
	public BigDecimal getTenOCC() {
		return tenOCC;
	}

	/**
	 * @param tenOCC the tenOCC to set
	 */
	public void setTenOCC(BigDecimal tenOCC) {
		this.tenOCC = tenOCC;
	}

	/**
	 * @return the tenADR
	 */
	public BigDecimal getTenADR() {
		return tenADR;
	}

	/**
	 * @param tenADR the tenADR to set
	 */
	public void setTenADR(BigDecimal tenADR) {
		this.tenADR = tenADR;
	}

	/**
	 * @return the totOCC
	 */
	public BigDecimal getTotOCC() {
		return totOCC;
	}

	/**
	 * @param totOCC the totOCC to set
	 */
	public void setTotOCC(BigDecimal totOCC) {
		this.totOCC = totOCC;
	}

	/**
	 * @return the totADR
	 */
	public BigDecimal getTotADR() {
		return totADR;
	}

	/**
	 * @param totADR the totADR to set
	 */
	public void setTotADR(BigDecimal totADR) {
		this.totADR = totADR;
	}

	/**
	 * @return the wSName
	 */
	public String getWSName() {
		return WSName;
	}

	/**
	 * @param wSName the wSName to set
	 */
	public void setWSName(String wSName) {
		WSName = wSName;
	}

	/**
	 * @return the tableType
	 */
	public String getTableType() {
		return tableType;
	}

	/**
	 * @param tableType the tableType to set
	 */
	public void setTableType(String tableType) {
		this.tableType = tableType;
	}

}
