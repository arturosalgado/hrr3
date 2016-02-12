package com.hrr3.entity.transients;

import java.io.Serializable;
import java.math.BigDecimal;

public  class TransientDataTotal extends GeneralDataTotal implements Serializable, Cloneable {
	
	private static final long serialVersionUID = 1L;
	
	
	
	private BigDecimal totAdr;
	private BigDecimal totRevPar;
	private Integer  totOccRooms;
	private BigDecimal totRev;
	private Integer totOcc;
		
		
	public TransientDataTotal() {
		super();
		
		// TODO Auto-generated constructor stub
	}

	public TransientDataTotal(String statdate, String comments,
			int isException, String dow, Integer isActual, BigDecimal totOccPct,
			BigDecimal totAdr, BigDecimal totRevPar, Integer totOccRooms, BigDecimal totRev, int totOcc) {
		super();
		this.statdate = statdate;
		this.comments = comments;
		this.isException = isException;
		this.dow = dow;
		this.isActual = isActual;
		this.totOccPct = totOccPct;
		this.totAdr = totAdr;
		this.totRevPar = totRevPar;
		this.totOccRooms = totOccRooms;
		this.totRev = totRev;
		this.totOcc = totOcc;
		
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
	 * @return the totRevPar
	 */
	public BigDecimal getTotRevPar() {
		return totRevPar;
	}

	/**
	 * @param totRevPar the totRevPar to set
	 */
	public void setTotRevPar(BigDecimal totRevPar) {
		this.totRevPar = totRevPar;
	}

	/**
	 * @return the totOccRooms
	 */
	public Integer getTotOccRooms() {
		return totOccRooms;
	}

	/**
	 * @param totOccRooms the totOccRooms to set
	 */
	public void setTotOccRooms(Integer totOccRooms) {
		this.totOccRooms = totOccRooms;
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
	 * @return the totOcc
	 */
	public Integer getTotOcc() {
		return totOcc;
	}

	/**
	 * @param totOcc the totOcc to set
	 */
	public void setTotOcc(Integer totOcc) {
		this.totOcc = totOcc;
	}		
}
