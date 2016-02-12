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
public class SnapshotTransientSegmentData   implements Serializable, Cloneable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 202538205959221760L;
	
	int snapshotId;
	int tsegmentdataId;
	int hotelId;
	int segmentId;
	String statdate;
	String type;
	BigDecimal totOcc;
	BigDecimal totRev;
	BigDecimal totAdr;
	BigDecimal defOcc;
	BigDecimal defRev;
	BigDecimal defAdr;
	BigDecimal tenOcc;
	BigDecimal tenRev;
	BigDecimal tenAdr;
	String createdTs;
	int admin;
		

	/**
	 * 
	 */
	public SnapshotTransientSegmentData() {
		// TODO Auto-generated constructor stub
		this.statdate = "";
		this.type = "";
		this.totOcc = new BigDecimal(0);
		this.totRev = new BigDecimal(0);
		this.totAdr = new BigDecimal(0);
		this.defOcc = new BigDecimal(0);
		this.defRev = new BigDecimal(0);
		this.defAdr = new BigDecimal(0);
		this.tenOcc = new BigDecimal(0);
		this.tenRev = new BigDecimal(0);
		this.tenAdr = new BigDecimal(0);
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
	 * @return the tsegmentdataId
	 */
	public int getTsegmentdataId() {
		return tsegmentdataId;
	}


	/**
	 * @param tsegmentdataId the tsegmentdataId to set
	 */
	public void setTsegmentdataId(int tsegmentdataId) {
		this.tsegmentdataId = tsegmentdataId;
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
