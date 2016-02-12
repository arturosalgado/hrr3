package com.hrr3.entity.renovationImpact;

import java.io.Serializable;
import java.math.BigDecimal;

public class ReportData implements Serializable,Cloneable {
	
	private static final long serialVersionUID = 1L;
	
	private boolean isSummaryRow;
	private String comments;
	private String dow;
	private String statDate;
	private String aF;
	private int vacant;
	private BigDecimal occPctOOOAvailable;
	private BigDecimal occPctTtlAvailable;
	private int occRooms;
	private BigDecimal tTlADR;
	private BigDecimal tTlRevenue;
	private int demandTdowns;
	private int oooRooms;
	private int displacedRooms;
	private int displacedRevenue;
	private BigDecimal totalRevPar;	
	private UserPreferencesRenovationImpact userPreferencesObj;
	
	public ReportData() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the isSummaryRow
	 */
	public boolean isSummaryRow() {
		return isSummaryRow;
	}

	/**
	 * @param isSummaryRow the isSummaryRow to set
	 */
	public void setSummaryRow(boolean isSummaryRow) {
		this.isSummaryRow = isSummaryRow;
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
	 * @return the aF
	 */
	public String getaF() {
		return aF;
	}

	/**
	 * @param aF the aF to set
	 */
	public void setaF(String aF) {
		this.aF = aF;
	}

	/**
	 * @return the vacant
	 */
	public int getVacant() {
		return vacant;
	}

	/**
	 * @param vacant the vacant to set
	 */
	public void setVacant(int vacant) {
		this.vacant = vacant;
	}

	/**
	 * @return the occPctOOOAvailable
	 */
	public BigDecimal getOccPctOOOAvailable() {
		return occPctOOOAvailable;
	}

	/**
	 * @param occPctOOOAvailable the occPctOOOAvailable to set
	 */
	public void setOccPctOOOAvailable(BigDecimal occPctOOOAvailable) {
		this.occPctOOOAvailable = occPctOOOAvailable;
	}

	/**
	 * @return the occPctTtlAvailable
	 */
	public BigDecimal getOccPctTtlAvailable() {
		return occPctTtlAvailable;
	}

	/**
	 * @param occPctTtlAvailable the occPctTtlAvailable to set
	 */
	public void setOccPctTtlAvailable(BigDecimal occPctTtlAvailable) {
		this.occPctTtlAvailable = occPctTtlAvailable;
	}

	/**
	 * @return the occRooms
	 */
	public int getOccRooms() {
		return occRooms;
	}

	/**
	 * @param occRooms the occRooms to set
	 */
	public void setOccRooms(int occRooms) {
		this.occRooms = occRooms;
	}

	/**
	 * @return the tTlADR
	 */
	public BigDecimal gettTlADR() {
		return tTlADR;
	}

	/**
	 * @param tTlADR the tTlADR to set
	 */
	public void settTlADR(BigDecimal tTlADR) {
		this.tTlADR = tTlADR;
	}

	/**
	 * @return the tTlRevenue
	 */
	public BigDecimal gettTlRevenue() {
		return tTlRevenue;
	}

	/**
	 * @param tTlRevenue the tTlRevenue to set
	 */
	public void settTlRevenue(BigDecimal tTlRevenue) {
		this.tTlRevenue = tTlRevenue;
	}

	/**
	 * @return the demandTdowns
	 */
	public int getDemandTdowns() {
		return demandTdowns;
	}

	/**
	 * @param demandTdowns the demandTdowns to set
	 */
	public void setDemandTdowns(int demandTdowns) {
		this.demandTdowns = demandTdowns;
	}

	/**
	 * @return the oooRooms
	 */
	public int getOooRooms() {
		return oooRooms;
	}

	/**
	 * @param oooRooms the oooRooms to set
	 */
	public void setOooRooms(int oooRooms) {
		this.oooRooms = oooRooms;
	}

	/**
	 * @return the displacedRooms
	 */
	public int getDisplacedRooms() {
		return displacedRooms;
	}

	/**
	 * @param displacedRooms the displacedRooms to set
	 */
	public void setDisplacedRooms(int displacedRooms) {
		this.displacedRooms = displacedRooms;
	}

	/**
	 * @return the displacedRevenue
	 */
	public int getDisplacedRevenue() {
		return displacedRevenue;
	}

	/**
	 * @param displacedRevenue the displacedRevenue to set
	 */
	public void setDisplacedRevenue(int displacedRevenue) {
		this.displacedRevenue = displacedRevenue;
	}

	/**
	 * @return the totalRevPar
	 */
	public BigDecimal getTotalRevPar() {
		return totalRevPar;
	}

	/**
	 * @param totalRevPar the totalRevPar to set
	 */
	public void setTotalRevPar(BigDecimal totalRevPar) {
		this.totalRevPar = totalRevPar;
	}

	/**
	 * @return the userPreferencesObj
	 */
	public UserPreferencesRenovationImpact getUserPreferencesObj() {
		return userPreferencesObj;
	}

	/**
	 * @param userPreferencesObj the userPreferencesObj to set
	 */
	public void setUserPreferencesObj(
			UserPreferencesRenovationImpact userPreferencesObj) {
		this.userPreferencesObj = userPreferencesObj;
	}

}
