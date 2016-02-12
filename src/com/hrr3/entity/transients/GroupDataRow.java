package com.hrr3.entity.transients;

import java.io.Serializable;

public class GroupDataRow implements Serializable, Cloneable {
	
	private static final long serialVersionUID = 1L;
	
	private GroupDataTotal groupDataTotal;
	private GroupSegmentData [] groupDataSegmentList;
	
	//Useful info for render to mantain
	private String startDate;
	private String endDate;

	public GroupDataRow() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the groupDataTotal
	 */
	public GroupDataTotal getGroupDataTotal() {
		return groupDataTotal;
	}

	/**
	 * @param groupDataTotal the groupDataTotal to set
	 */
	public void setGroupDataTotal(GroupDataTotal groupDataTotal) {
		this.groupDataTotal = groupDataTotal;
	}

	/**
	 * @return the groupDataSegmentList
	 */
	public GroupSegmentData[] getGroupDataSegmentList() {
		return groupDataSegmentList;
	}

	/**
	 * @param groupDataSegmentList the groupDataSegmentList to set
	 */
	public void setGroupDataSegmentList(GroupSegmentData[] groupDataSegmentList) {
		this.groupDataSegmentList = groupDataSegmentList;
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
