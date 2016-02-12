package com.hrr3.entity;

public class SegmentSetting extends Segment {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	boolean includeInReport;
	boolean includeInTotal;

	/**
	 * @return the includeInReport
	 */
	public boolean isIncludeInReport() {
		return includeInReport;
	}
	
	public boolean getIsIncludeInReport() {
		return includeInReport;
	}
	
	public boolean getIsIncludeInTotal() {
		return includeInTotal;
	}

	/**
	 * @param includeInReport the includeInReport to set
	 */
	public void setIncludeInReport(boolean includeInReport) {
		this.includeInReport = includeInReport;
	}

	/**
	 * @return the includeInTotal
	 */
	public boolean isIncludeInTotal() {
		return includeInTotal;
	}
	
	public boolean getIncludeInTotal() {
		return includeInTotal;
	}

	/**
	 * @param includeInTotal the includeInTotal to set
	 */
	public void setIncludeInTotal(boolean includeInTotal) {
		this.includeInTotal = includeInTotal;
	}
	

}
