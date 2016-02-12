package com.hrr3.entity.transients;

import java.io.Serializable;
import java.math.BigDecimal;

public class GeneralDataTotal implements Serializable, Cloneable {
	

	private static final long serialVersionUID = 1L;
	
	protected String statdate;
	protected String comments;
	protected int isException;
	protected String dow;
	protected int isActual;
	protected BigDecimal totOccPct;

	public GeneralDataTotal() {
		// TODO Auto-generated constructor stub
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
	 * @return the isException
	 */
	public int getIsException() {
		return isException;
	}

	/**
	 * @param isException the isException to set
	 */
	public void setIsException(int isException) {
		this.isException = isException;
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

}
