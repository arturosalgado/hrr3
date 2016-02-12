package com.hrr3.entity.transients;

import java.io.Serializable;
import java.math.BigDecimal;

public class TransientSegmentData implements Serializable, Cloneable {
	
	private static final long serialVersionUID = 1L;
	
	protected String statdate;
	protected String segmentName;
	protected int segmentId;
	protected Integer defOcc;
	protected BigDecimal defRev;
	protected BigDecimal defAdr;
	
		
	public TransientSegmentData() {
		super();
		// TODO Auto-generated constructor stub
	}
		
	public TransientSegmentData(String statdate, String segmentName,
			int segmentId, Integer def_occ, BigDecimal def_rev, BigDecimal def_adr) {
		super();
		this.statdate = statdate;
		this.segmentName = segmentName;
		this.segmentId = segmentId;
		this.defOcc = def_occ;
		this.defRev = def_rev;
		this.defAdr = def_adr;
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
	 * @return the segmentName
	 */
	public String getSegmentName() {
		return segmentName;
	}

	/**
	 * @param segmentName the segmentName to set
	 */
	public void setSegmentName(String segmentName) {
		this.segmentName = segmentName;
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
	 * @return the defOcc
	 */
	public Integer getDefOcc() {
		return defOcc;
	}

	/**
	 * @param defOcc the defOcc to set
	 */
	public void setDefOcc(Integer defOcc) {
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
	
	
	
}
