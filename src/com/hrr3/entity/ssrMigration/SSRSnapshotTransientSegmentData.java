package com.hrr3.entity.ssrMigration;

import java.io.Serializable;
import java.math.BigDecimal;

public class SSRSnapshotTransientSegmentData implements Serializable, Cloneable {
	
	private static final long serialVersionUID = 1L;
	
	private int ssrSnapshotId;
	private int hotelId;
	private int segmentId;
	private String statdate;
	private String type;	
	private BigDecimal totOcc;
	private BigDecimal totRev;
	private BigDecimal totAdr;
	private BigDecimal defOcc;
	private BigDecimal defRev;
	private BigDecimal defAdr;
	private BigDecimal tenOcc;
	private BigDecimal tenRev;
	private BigDecimal tenAdr;
	private String createdTs;
	private int admin;
	
	public SSRSnapshotTransientSegmentData() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the ssrSnapshotId
	 */
	public int getSsrSnapshotId() {
		return ssrSnapshotId;
	}

	/**
	 * @param ssrSnapshotId the ssrSnapshotId to set
	 */
	public void setSsrSnapshotId(int ssrSnapshotId) {
		this.ssrSnapshotId = ssrSnapshotId;
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
