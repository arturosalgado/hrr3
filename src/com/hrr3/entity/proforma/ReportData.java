package com.hrr3.entity.proforma;

import java.io.Serializable;
import java.math.BigDecimal;

public class ReportData implements Serializable,Cloneable {
	private static final long serialVersionUID = 1L;

	private boolean IsSummaryRow;
	private String comments;
	private String dow;
	private String statDate;
	private int baseRms;
	private BigDecimal baseRate;
	private BigDecimal baseRev;
	private int changeRms;
	private BigDecimal changeRate;
	private BigDecimal changeRev;
	private int occRms;
	private BigDecimal totAdr;
	private BigDecimal totRev;
	private int propBaseRms;
	private BigDecimal propBaseRate;
	private int dispRms;
	private BigDecimal incRev;
	private BigDecimal newTotRev;
	private BigDecimal incProfit;
	private BigDecimal dispAdr;
	private double dispAdrOverride;
	private int newNonCrew;

	public ReportData() {
		// TODO Auto-generated constructor stub
		
		this.baseRate = BigDecimal.ZERO;
		this.baseRev = BigDecimal.ZERO;
		this.changeRate = BigDecimal.ZERO;
		this.changeRev = BigDecimal.ZERO;
		
		this.totAdr = BigDecimal.ZERO;
		this.totRev = BigDecimal.ZERO;
		
		this.propBaseRate = BigDecimal.ZERO;
		
		this.incRev = BigDecimal.ZERO;
		this.newTotRev  = BigDecimal.ZERO;
		this.incProfit = BigDecimal.ZERO;
		this.dispAdr = BigDecimal.ZERO;		
		
	}
		
	/**
	 * @return the isSummaryRow
	 */
	public boolean isIsSummaryRow() {
		return IsSummaryRow;
	}
	/**
	 * @param isSummaryRow the isSummaryRow to set
	 */
	public void setIsSummaryRow(boolean isSummaryRow) {
		IsSummaryRow = isSummaryRow;
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
	 * @return the baseRms
	 */
	public int getBaseRms() {
		return baseRms;
	}
	/**
	 * @param baseRms the baseRms to set
	 */
	public void setBaseRms(int baseRms) {
		this.baseRms = baseRms;
	}
	/**
	 * @return the baseRate
	 */
	public BigDecimal getBaseRate() {
		return baseRate;
	}
	/**
	 * @param baseRate the baseRate to set
	 */
	public void setBaseRate(BigDecimal baseRate) {
		this.baseRate = baseRate;
	}
	/**
	 * @return the baseRev
	 */
	public BigDecimal getBaseRev() {
		return baseRev;
	}
	/**
	 * @param baseRev the baseRev to set
	 */
	public void setBaseRev(BigDecimal baseRev) {
		this.baseRev = baseRev;
	}
	/**
	 * @return the changeRms
	 */
	public int getChangeRms() {
		return changeRms;
	}
	/**
	 * @param changeRms the changeRms to set
	 */
	public void setChangeRms(int changeRms) {
		this.changeRms = changeRms;
	}
	/**
	 * @return the changeRate
	 */
	public BigDecimal getChangeRate() {
		return changeRate;
	}
	/**
	 * @param changeRate the changeRate to set
	 */
	public void setChangeRate(BigDecimal changeRate) {
		this.changeRate = changeRate;
	}
	/**
	 * @return the changeRev
	 */
	public BigDecimal getChangeRev() {
		return changeRev;
	}
	/**
	 * @param changeRev the changeRev to set
	 */
	public void setChangeRev(BigDecimal changeRev) {
		this.changeRev = changeRev;
	}
	/**
	 * @return the occRms
	 */
	public int getOccRms() {
		return occRms;
	}
	/**
	 * @param occRms the occRms to set
	 */
	public void setOccRms(int occRms) {
		this.occRms = occRms;
	}
	/**
	 * @return the totAdr
	 */
	public BigDecimal getTotAdr() {
		return totAdr;
	}
	/**
	 * @param totAdr the totAdr to set
	 */
	public void setTotAdr(BigDecimal totAdr) {
		this.totAdr = totAdr;
	}
	/**
	 * @return the totRev
	 */
	public BigDecimal getTotRev() {
		return totRev;
	}
	/**
	 * @param totRev the totRev to set
	 */
	public void setTotRev(BigDecimal totRev) {
		this.totRev = totRev;
	}
	/**
	 * @return the propBaseRms
	 */
	public int getPropBaseRms() {
		return propBaseRms;
	}
	/**
	 * @param propBaseRms the propBaseRms to set
	 */
	public void setPropBaseRms(int propBaseRms) {
		this.propBaseRms = propBaseRms;
	}
	/**
	 * @return the propBaseRate
	 */
	public BigDecimal getPropBaseRate() {
		return propBaseRate;
	}
	/**
	 * @param propBaseRate the propBaseRate to set
	 */
	public void setPropBaseRate(BigDecimal propBaseRate) {
		this.propBaseRate = propBaseRate;
	}
	/**
	 * @return the dispRms
	 */
	public int getDispRms() {
		return dispRms;
	}
	/**
	 * @param dispRms the dispRms to set
	 */
	public void setDispRms(int dispRms) {
		this.dispRms = dispRms;
	}
	/**
	 * @return the incRev
	 */
	public BigDecimal getIncRev() {
		return incRev;
	}
	/**
	 * @param incRev the incRev to set
	 */
	public void setIncRev(BigDecimal incRev) {
		this.incRev = incRev;
	}
	/**
	 * @return the newTotRev
	 */
	public BigDecimal getNewTotRev() {
		return newTotRev;
	}
	/**
	 * @param newTotRev the newTotRev to set
	 */
	public void setNewTotRev(BigDecimal newTotRev) {
		this.newTotRev = newTotRev;
	}
	/**
	 * @return the incProfit
	 */
	public BigDecimal getIncProfit() {
		return incProfit;
	}
	/**
	 * @param incProfit the incProfit to set
	 */
	public void setIncProfit(BigDecimal incProfit) {
		this.incProfit = incProfit;
	}
	/**
	 * @return the dispAdr
	 */
	public BigDecimal getDispAdr() {
		return dispAdr;
	}
	/**
	 * @param dispAdr the dispAdr to set
	 */
	public void setDispAdr(BigDecimal dispAdr) {
		this.dispAdr = dispAdr;
	}
	/**
	 * @return the dispAdrOverride
	 */
	public double getDispAdrOverride() {
		return dispAdrOverride;
	}
	/**
	 * @param dispAdrOverride the dispAdrOverride to set
	 */
	public void setDispAdrOverride(double dispAdrOverride) {
		this.dispAdrOverride = dispAdrOverride;
	}
	/**
	 * @return the newNonCrew
	 */
	public int getNewNonCrew() {
		return newNonCrew;
	}
	/**
	 * @param newNonCrew the newNonCrew to set
	 */
	public void setNewNonCrew(int newNonCrew) {
		this.newNonCrew = newNonCrew;
	}

}
