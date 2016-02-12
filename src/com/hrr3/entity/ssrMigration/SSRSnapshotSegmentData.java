package com.hrr3.entity.ssrMigration;

import java.io.Serializable;
import java.math.BigDecimal;

public class SSRSnapshotSegmentData implements Serializable, Cloneable {
	
	private static final long serialVersionUID = 1L;
	
	private	int	ssrSnapshotId;
	private	int	hotelId;
	private	int	segmentId;
	private	String	statdate;
	private	BigDecimal	totOcc;
	private	BigDecimal	totRev;
	private	BigDecimal	totAdr;
	private	String	createdTs;
	private	int	admin;
	private	String	modifiedTs;
	
	
	public SSRSnapshotSegmentData() {
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


	/**
	 * @return the modifiedTs
	 */
	public String getModifiedTs() {
		return modifiedTs;
	}


	/**
	 * @param modifiedTs the modifiedTs to set
	 */
	public void setModifiedTs(String modifiedTs) {
		this.modifiedTs = modifiedTs;
	}

}
