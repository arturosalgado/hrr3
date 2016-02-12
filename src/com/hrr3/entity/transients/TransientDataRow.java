package com.hrr3.entity.transients;

import java.io.Serializable;

public class TransientDataRow implements Serializable, Cloneable {
	
	private static final long serialVersionUID = 1L;	

	private TransientDataTotals transientDataTotals;
	private TransientSegmentData[] transientDataSegmentList;
	
	private int classId;
	private int totalType;
	
	//Useful info for render to mantain
	private String startDate;
	private String endDate;
	
	public TransientDataRow(int classId, int totalType) {
		
		this.classId = classId;
		this.totalType = totalType;
	}
	
	/**
	 * @return the classId
	 */
	public int getClassId() {
		return classId;
	}
	/**
	 * @param classId the classId to set
	 */
	public void setClassId(int classId) {
		this.classId = classId;
	}
	/**
	 * @return the totalType
	 */
	public int getTotalType() {
		return totalType;
	}
	/**
	 * @param totalType the totalType to set
	 */
	public void setTotalType(int totalType) {
		this.totalType = totalType;
	}
	/**
	 * @return the transientDataSegmentList
	 */
	public TransientSegmentData[]getTransientDataSegmentList() {
		return transientDataSegmentList;
	}
	/**
	 * @param transientDataSegmentList the transientDataSegmentList to set
	 */
	public void setTransientDataSegmentList(
			TransientSegmentData[] transientDataSegmentList) {
		this.transientDataSegmentList = transientDataSegmentList;
	}
	/**
	 * @return the transientDataTotal
	 */
	public TransientDataTotals getTransientDataTotals() {
		return transientDataTotals;
	}
	/**
	 * @param transientDataTotal the transientDataTotal to set
	 */
	public void setTransientDataTotals(TransientDataTotals transientDataTotals) {
		this.transientDataTotals = transientDataTotals;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	
	
	
}
