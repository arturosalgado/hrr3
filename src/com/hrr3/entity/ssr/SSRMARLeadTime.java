package com.hrr3.entity.ssr;

import java.io.Serializable;
import java.math.BigDecimal;

public class SSRMARLeadTime implements Serializable, Cloneable {
	
	private static final long serialVersionUID = 1L;

	public SSRMARLeadTime() {
		// TODO Auto-generated constructor stub
	}
	
	private int leadTime;
	private String dow;
	private String statdate;
	private BigDecimal ftTotOcc;
	private BigDecimal ftTotOccPcnt;
	private String overrides;
	private String marrate;

	/**
	 * @return the leadTime
	 */
	public int getLeadTime() {
		return leadTime;
	}
	/**
	 * @param leadTime the leadTime to set
	 */
	public void setLeadTime(int leadTime) {
		this.leadTime = leadTime;
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
	 * @return the statdate
	 */
	public String getStatdate() {
		return statdate;
	}
	/**
	 * @param statdate the statdate to set
	 */
	public void setStatdate(String statdate) {
		this.statdate = statdate;
	}
	/**
	 * @return the ftTotOcc
	 */
	public BigDecimal getFtTotOcc() {
		return ftTotOcc;
	}
	/**
	 * @param ftTotOcc the ftTotOcc to set
	 */
	public void setFtTotOcc(BigDecimal ftTotOcc) {
		this.ftTotOcc = ftTotOcc;
	}
	/**
	 * @return the ftTotOccPcnt
	 */
	public BigDecimal getFtTotOccPcnt() {
		return ftTotOccPcnt;
	}
	/**
	 * @param ftTotOccPcnt the ftTotOccPcnt to set
	 */
	public void setFtTotOccPcnt(BigDecimal ftTotOccPcnt) {
		this.ftTotOccPcnt = ftTotOccPcnt;
	}
	/**
	 * @return the overrides
	 */
	public String getOverrides() {
		return overrides;
	}
	/**
	 * @param overrides the overrides to set
	 */
	public void setOverrides(String overrides) {
		this.overrides = overrides;
	}
	/**
	 * @return the marrate
	 */
	public String getMarrate() {
		return marrate;
	}
	/**
	 * @param marrate the marrate to set
	 */
	public void setMarrate(String marrate) {
		this.marrate = marrate;
	}
	

}
