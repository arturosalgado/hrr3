/**
 * 
 */
package com.hrr3.entity.migration;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author ozoe
 *
 */
public class SnapshotTransientData implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	int snapshotId;
	int hotelId;
	String statdate;
	String comments;
	int isException;
	String dow;
	int isActual;
	BigDecimal totOccPct;
	BigDecimal totAdr;
	BigDecimal totRevPar;
	BigDecimal totOccRooms;
	BigDecimal totRev;
	BigDecimal gtotOcc;
	BigDecimal gtotRev;
	BigDecimal gtotAdr;
	BigDecimal gdefOcc;
	BigDecimal gdefRev;
	BigDecimal gdefAdr;
	BigDecimal gtenOcc;
	BigDecimal gtenRev;
	BigDecimal gtenAdr;
	String createdTs;
	
	public SnapshotTransientData() {
		this.statdate = "";
		this.comments = "";
		this.isException = 0;
		this.dow = "";
		this.isActual = 1;
		this.totOccPct = new BigDecimal(0);
		this.totAdr = new BigDecimal(0);
		this.totRevPar = new BigDecimal(0);
		this.totOccRooms = new BigDecimal(0);
		this.totRev = new BigDecimal(0);
		this.gtotOcc = new BigDecimal(0);
		this.gtotRev = new BigDecimal(0);
		this.gtotAdr = new BigDecimal(0);
		this.gdefOcc = new BigDecimal(0);
		this.gdefAdr = new BigDecimal(0);
		this.gdefRev = new BigDecimal(0);
		this.gtenAdr = new BigDecimal(0);
		this.gtenOcc = new BigDecimal(0);
		this.gtenRev = new BigDecimal(0);
		this.createdTs = "";			
	}

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
	public BigDecimal getTotOccRooms() {
		return totOccRooms;
	}

	/**
	 * @param totOccRooms the totOccRooms to set
	 */
	public void setTotOccRooms(BigDecimal totOccRooms) {
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
	 * @return the gtotOcc
	 */
	public BigDecimal getGtotOcc() {
		return gtotOcc;
	}

	/**
	 * @param gtotOcc the gtotOcc to set
	 */
	public void setGtotOcc(BigDecimal gtotOcc) {
		this.gtotOcc = gtotOcc;
	}

	/**
	 * @return the gtotRev
	 */
	public BigDecimal getGtotRev() {
		return gtotRev;
	}

	/**
	 * @param gtotRev the gtotRev to set
	 */
	public void setGtotRev(BigDecimal gtotRev) {
		this.gtotRev = gtotRev;
	}

	/**
	 * @return the gtotAdr
	 */
	public BigDecimal getGtotAdr() {
		return gtotAdr;
	}

	/**
	 * @param gtotAdr the gtotAdr to set
	 */
	public void setGtotAdr(BigDecimal gtotAdr) {
		this.gtotAdr = gtotAdr;
	}

	/**
	 * @return the gdefOcc
	 */
	public BigDecimal getGdefOcc() {
		return gdefOcc;
	}

	/**
	 * @param gdefOcc the gdefOcc to set
	 */
	public void setGdefOcc(BigDecimal gdefOcc) {
		this.gdefOcc = gdefOcc;
	}

	/**
	 * @return the gdefRev
	 */
	public BigDecimal getGdefRev() {
		return gdefRev;
	}

	/**
	 * @param gdefRev the gdefRev to set
	 */
	public void setGdefRev(BigDecimal gdefRev) {
		this.gdefRev = gdefRev;
	}

	/**
	 * @return the gdefAdr
	 */
	public BigDecimal getGdefAdr() {
		return gdefAdr;
	}

	/**
	 * @param gdefAdr the gdefAdr to set
	 */
	public void setGdefAdr(BigDecimal gdefAdr) {
		this.gdefAdr = gdefAdr;
	}

	/**
	 * @return the gtenOcc
	 */
	public BigDecimal getGtenOcc() {
		return gtenOcc;
	}

	/**
	 * @param gtenOcc the gtenOcc to set
	 */
	public void setGtenOcc(BigDecimal gtenOcc) {
		this.gtenOcc = gtenOcc;
	}

	/**
	 * @return the gtenRev
	 */
	public BigDecimal getGtenRev() {
		return gtenRev;
	}

	/**
	 * @param gtenRev the gtenRev to set
	 */
	public void setGtenRev(BigDecimal gtenRev) {
		this.gtenRev = gtenRev;
	}

	/**
	 * @return the gtenAdr
	 */
	public BigDecimal getGtenAdr() {
		return gtenAdr;
	}

	/**
	 * @param gtenAdr the gtenAdr to set
	 */
	public void setGtenAdr(BigDecimal gtenAdr) {
		this.gtenAdr = gtenAdr;
	}

	/**
	 * @return the createdTs
	 */
	public String getCreatedTs() {
		return createdTs;
	}

	/**
	 * @param createdTs the createdTs to set
	 */
	public void setCreatedTs(String createdTs) {
		this.createdTs = createdTs;
	}

     
}
