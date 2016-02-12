package com.hrr3.entity.transients;

import java.io.Serializable;
import java.math.BigDecimal;

public class GroupDataTotal extends GeneralDataTotal implements Serializable, Cloneable {
	
	private static final long serialVersionUID = 1L;
	
;
	private int gTotOcc;
	private BigDecimal gTotRev;
	private BigDecimal gTotAdr;
	private int gDefOcc;
	private BigDecimal gDefRev;
	private BigDecimal gDefAdr;
	private int gTenOcc;
	private BigDecimal gTenRev;
	private BigDecimal gTenAdr;
    

	public GroupDataTotal() {
		// TODO Auto-generated constructor stub
		this.gTotAdr = new BigDecimal(0);
		this.gTotOcc = 0;
		this.gTotRev = new BigDecimal(0);
		this.gTenAdr = new BigDecimal(0);
		this.gTenOcc = 0;
		this.gTenRev = new BigDecimal(0);
		this.gDefAdr = new BigDecimal(0);
		this.gDefOcc = 0;
		this.gDefRev = new BigDecimal(0);
		this.totOccPct = new BigDecimal(0);
		this.isActual = 0;
		this.isException = 0;
		
	}

	/**
	 * @return the gTotOcc
	 */
	public int getgTotOcc() {
		return gTotOcc;
	}


	/**
	 * @param gTotOcc the gTotOcc to set
	 */
	public void setgTotOcc(int gTotOcc) {
		this.gTotOcc = gTotOcc;
	}


	/**
	 * @return the gTotRev
	 */
	public BigDecimal getgTotRev() {
		return gTotRev;
	}


	/**
	 * @param gTotRev the gTotRev to set
	 */
	public void setgTotRev(BigDecimal gTotRev) {
		this.gTotRev = gTotRev;
	}


	/**
	 * @return the gTotAdr
	 */
	public BigDecimal getgTotAdr() {
		return gTotAdr;
	}


	/**
	 * @param gTotAdr the gTotAdr to set
	 */
	public void setgTotAdr(BigDecimal gTotAdr) {
		this.gTotAdr = gTotAdr;
	}


	/**
	 * @return the gDefOcc
	 */
	public int getgDefOcc() {
		return gDefOcc;
	}


	/**
	 * @param gDefOcc the gDefOcc to set
	 */
	public void setgDefOcc(int gDefOcc) {
		this.gDefOcc = gDefOcc;
	}


	/**
	 * @return the gDefRev
	 */
	public BigDecimal getgDefRev() {
		return gDefRev;
	}


	/**
	 * @param gDefRev the gDefRev to set
	 */
	public void setgDefRev(BigDecimal gDefRev) {
		this.gDefRev = gDefRev;
	}


	/**
	 * @return the gDefAdr
	 */
	public BigDecimal getgDefAdr() {
		return gDefAdr;
	}


	/**
	 * @param gDefAdr the gDefAdr to set
	 */
	public void setgDefAdr(BigDecimal gDefAdr) {
		this.gDefAdr = gDefAdr;
	}


	/**
	 * @return the gTenOcc
	 */
	public int getgTenOcc() {
		return gTenOcc;
	}


	/**
	 * @param gTenOcc the gTenOcc to set
	 */
	public void setgTenOcc(int gTenOcc) {
		this.gTenOcc = gTenOcc;
	}


	/**
	 * @return the gTenRev
	 */
	public BigDecimal getgTenRev() {
		return gTenRev;
	}


	/**
	 * @param gTenRev the gTenRev to set
	 */
	public void setgTenRev(BigDecimal gTenRev) {
		this.gTenRev = gTenRev;
	}


	/**
	 * @return the gTenAdr
	 */
	public BigDecimal getgTenAdr() {
		return gTenAdr;
	}


	/**
	 * @param gTenAdr the gTenAdr to set
	 */
	public void setgTenAdr(BigDecimal gTenAdr) {
		this.gTenAdr = gTenAdr;
	}

}
