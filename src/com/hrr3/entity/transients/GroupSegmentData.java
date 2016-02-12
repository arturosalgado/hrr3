package com.hrr3.entity.transients;

import java.io.Serializable;
import java.math.BigDecimal;

public class GroupSegmentData extends TransientSegmentData implements Serializable, Cloneable {
	
	private static final long serialVersionUID = 1L;
	
	private int totOcc;
	private BigDecimal totRev;
	private BigDecimal totAdr;
	private int tenOcc;
	private BigDecimal tenRev;
	private BigDecimal tenAdr;
	
	public GroupSegmentData() {
		// TODO Auto-generated constructor stub
		this.totAdr = new BigDecimal(0);
		this.totRev = new BigDecimal(0);
		this.totOcc = 0;
		this.tenRev = new BigDecimal(0);
		this.tenAdr = new BigDecimal(0);
		this.tenOcc = 0;
		this.defAdr = new BigDecimal(0);
		this.defOcc = 0;
		this.defRev = new BigDecimal(0);
	}

	/**
	 * @return the totOcc
	 */
	public int getTotOcc() {
		return totOcc;
	}

	/**
	 * @param totOcc the totOcc to set
	 */
	public void setTotOcc(int totOcc) {
		this.totOcc = totOcc;
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
	 * @return the tenOcc
	 */
	public int getTenOcc() {
		return tenOcc;
	}

	/**
	 * @param tenOcc the tenOcc to set
	 */
	public void setTenOcc(int tenOcc) {
		this.tenOcc = tenOcc;
	}

	/**
	 * @return the tenRev
	 */
	public BigDecimal getTenRev() {
		return tenRev;
	}

	/**
	 * @param tenRev the tenRev to set
	 */
	public void setTenRev(BigDecimal tenRev) {
		this.tenRev = tenRev;
	}

	/**
	 * @return the tenAdr
	 */
	public BigDecimal getTenAdr() {
		return tenAdr;
	}

	/**
	 * @param tenAdr the tenAdr to set
	 */
	public void setTenAdr(BigDecimal tenAdr) {
		this.tenAdr = tenAdr;
	}

}
