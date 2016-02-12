package com.hrr3.entity;

import java.io.Serializable;
import java.math.BigDecimal;



public class SnapshotExport implements Serializable, Cloneable  {
	
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
	String createdTsTotal;
			
	int segmentId;	
	String type;
	String tsdstatdate;
	BigDecimal totOcc;
	BigDecimal totRevSeg;
	BigDecimal totAdrSeg;
	BigDecimal defOcc;
	BigDecimal defRev;
	BigDecimal defAdr;
	BigDecimal tenOcc;
	BigDecimal tenRev;
	BigDecimal tenAdr;
	String createdTsSegment;
	int admin;
	
	
	public SnapshotExport() {
		// TODO Auto-generated constructor stub
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
	 * @return the createdTsTotal
	 */
	public String getCreatedTsTotal() {
		return createdTsTotal;
	}


	/**
	 * @param createdTsTotal the createdTsTotal to set
	 */
	public void setCreatedTsTotal(String createdTsTotal) {
		this.createdTsTotal = createdTsTotal;
	}


	/**
	 * @return the segmentId
	 */
	public int getSegmentId() {
		return segmentId;
	}


	/**
	 * @param segmentId the segmentId to set
	 */
	public void setSegmentId(int segmentId) {
		this.segmentId = segmentId;
	}


	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}


	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}


	/**
	 * @return the tsdstatdate
	 */
	public String getTsdstatdate() {
		return tsdstatdate;
	}


	/**
	 * @param tsdstatdate the tsdstatdate to set
	 */
	public void setTsdstatdate(String tsdstatdate) {
		this.tsdstatdate = tsdstatdate;
	}


	/**
	 * @return the totOcc
	 */
	public BigDecimal getTotOcc() {
		return totOcc;
	}


	/**
	 * @param totOcc the totOcc to set
	 */
	public void setTotOcc(BigDecimal totOcc) {
		this.totOcc = totOcc;
	}


	/**
	 * @return the totRevSeg
	 */
	public BigDecimal getTotRevSeg() {
		return totRevSeg;
	}


	/**
	 * @param totRevSeg the totRevSeg to set
	 */
	public void setTotRevSeg(BigDecimal totRevSeg) {
		this.totRevSeg = totRevSeg;
	}


	/**
	 * @return the totAdrSeg
	 */
	public BigDecimal getTotAdrSeg() {
		return totAdrSeg;
	}


	/**
	 * @param totAdrSeg the totAdrSeg to set
	 */
	public void setTotAdrSeg(BigDecimal totAdrSeg) {
		this.totAdrSeg = totAdrSeg;
	}


	/**
	 * @return the defOcc
	 */
	public BigDecimal getDefOcc() {
		return defOcc;
	}


	/**
	 * @param defOcc the defOcc to set
	 */
	public void setDefOcc(BigDecimal defOcc) {
		this.defOcc = defOcc;
	}


	/**
	 * @return the defRev
	 */
	public BigDecimal getDefRev() {
		return defRev;
	}


	/**
	 * @param defRev the defRev to set
	 */
	public void setDefRev(BigDecimal defRev) {
		this.defRev = defRev;
	}


	/**
	 * @return the defAdr
	 */
	public BigDecimal getDefAdr() {
		return defAdr;
	}


	/**
	 * @param defAdr the defAdr to set
	 */
	public void setDefAdr(BigDecimal defAdr) {
		this.defAdr = defAdr;
	}


	/**
	 * @return the tenOcc
	 */
	public BigDecimal getTenOcc() {
		return tenOcc;
	}


	/**
	 * @param tenOcc the tenOcc to set
	 */
	public void setTenOcc(BigDecimal tenOcc) {
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


	/**
	 * @return the createdTsSegment
	 */
	public String getCreatedTsSegment() {
		return createdTsSegment;
	}


	/**
	 * @param createdTsSegment the createdTsSegment to set
	 */
	public void setCreatedTsSegment(String createdTsSegment) {
		this.createdTsSegment = createdTsSegment;
	}


	/**
	 * @return the admin
	 */
	public int getAdmin() {
		return admin;
	}


	/**
	 * @param admin the admin to set
	 */
	public void setAdmin(int admin) {
		this.admin = admin;
	}

	
}
