package com.hrr3.entity.trendTool;

import java.io.Serializable;
import java.math.BigDecimal;

public class TrendTool implements Serializable,Cloneable {
	@Override
	public String toString() {
		return "TrendTool [segmentName=" + segmentName + ", segmentId=" + segmentId + ", sunADR=" + sunADR + ", monADR="
				+ monADR + ", tueADR=" + tueADR + ", wenADR=" + wenADR + ", thuADR=" + thuADR + ", friADR=" + friADR
				+ ", satADR=" + satADR + ", weekADR=" + weekADR + ", weekendADR=" + weekendADR + ", totalADR="
				+ totalADR + ", sunOCC=" + sunOCC + ", monOCC=" + monOCC + ", tueOCC=" + tueOCC + ", wenOCC=" + wenOCC
				+ ", thuOCC=" + thuOCC + ", friOCC=" + friOCC + ", satOCC=" + satOCC + ", weekOCC=" + weekOCC
				+ ", weekendOCC=" + weekendOCC + ", totalOCC=" + totalOCC + "]";
	}


	private static final long serialVersionUID = 1L;
	
	private String segmentName;
	private Integer segmentId;
	
	private BigDecimal sunADR;
	private BigDecimal monADR;
	private BigDecimal tueADR;
	private BigDecimal wenADR;
	private BigDecimal thuADR;
	private BigDecimal friADR;
	private BigDecimal satADR;
	private BigDecimal weekADR;
	private BigDecimal weekendADR;
	private BigDecimal totalADR;
	
	private Integer sunOCC;
	private Integer monOCC;
	private Integer tueOCC;
	private Integer wenOCC;
	private Integer thuOCC;
	private Integer friOCC;
	private Integer satOCC;
	private Integer weekOCC;
	private Integer weekendOCC;
	private Integer totalOCC;
	
	
	public TrendTool() {
		// TODO Auto-generated constructor stub
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
	public Integer getSegmentId() {
		return segmentId;
	}


	/**
	 * @param segmentId the segmentId to set
	 */
	public void setSegmentId(Integer segmentId) {
		this.segmentId = segmentId;
	}


	/**
	 * @return the sunADR
	 */
	public BigDecimal getSunADR() {
		return sunADR;
	}


	/**
	 * @param sunADR the sunADR to set
	 */
	public void setSunADR(BigDecimal sunADR) {
		this.sunADR = sunADR;
	}


	/**
	 * @return the monADR
	 */
	public BigDecimal getMonADR() {
		return monADR;
	}


	/**
	 * @param monADR the monADR to set
	 */
	public void setMonADR(BigDecimal monADR) {
		this.monADR = monADR;
	}


	/**
	 * @return the tueADR
	 */
	public BigDecimal getTueADR() {
		return tueADR;
	}


	/**
	 * @param tueADR the tueADR to set
	 */
	public void setTueADR(BigDecimal tueADR) {
		this.tueADR = tueADR;
	}


	/**
	 * @return the wendADR
	 */
	public BigDecimal getWenADR() {
		return wenADR;
	}


	/**
	 * @param wenADR the wenADR to set
	 */
	public void setWenADR(BigDecimal wenADR) {
		this.wenADR = wenADR;
	}


	/**
	 * @return the thuADR
	 */
	public BigDecimal getThuADR() {
		return thuADR;
	}


	/**
	 * @param thuADR the thuADR to set
	 */
	public void setThuADR(BigDecimal thuADR) {
		this.thuADR = thuADR;
	}


	/**
	 * @return the friADR
	 */
	public BigDecimal getFriADR() {
		return friADR;
	}


	/**
	 * @param friADR the friADR to set
	 */
	public void setFriADR(BigDecimal friADR) {
		this.friADR = friADR;
	}


	/**
	 * @return the satADR
	 */
	public BigDecimal getSatADR() {
		return satADR;
	}


	/**
	 * @param satADR the satADR to set
	 */
	public void setSatADR(BigDecimal satADR) {
		this.satADR = satADR;
	}


	/**
	 * @return the weekADR
	 */
	public BigDecimal getWeekADR() {
		return weekADR;
	}


	/**
	 * @param weekADR the weekADR to set
	 */
	public void setWeekADR(BigDecimal weekADR) {
		this.weekADR = weekADR;
	}


	/**
	 * @return the weekendADR
	 */
	public BigDecimal getWeekendADR() {
		return weekendADR;
	}


	/**
	 * @param weekendADR the weekendADR to set
	 */
	public void setWeekendADR(BigDecimal weekendADR) {
		this.weekendADR = weekendADR;
	}


	/**
	 * @return the totalADR
	 */
	public BigDecimal getTotalADR() {
		return totalADR;
	}


	/**
	 * @param totalADR the totalADR to set
	 */
	public void setTotalADR(BigDecimal totalADR) {
		this.totalADR = totalADR;
	}


	/**
	 * @return the sunOCC
	 */
	public Integer getSunOCC() {
		return sunOCC;
	}


	/**
	 * @param sunOCC the sunOCC to set
	 */
	public void setSunOCC(Integer sunOCC) {
		this.sunOCC = sunOCC;
	}


	/**
	 * @return the monOCC
	 */
	public Integer getMonOCC() {
		return monOCC;
	}


	/**
	 * @param monOCC the monOCC to set
	 */
	public void setMonOCC(Integer monOCC) {
		this.monOCC = monOCC;
	}


	/**
	 * @return the tueOCC
	 */
	public Integer getTueOCC() {
		return tueOCC;
	}


	/**
	 * @param tueOCC the tueOCC to set
	 */
	public void setTueOCC(Integer tueOCC) {
		this.tueOCC = tueOCC;
	}


	/**
	 * @return the wenOCC
	 */
	public Integer getWenOCC() {
		return wenOCC;
	}


	/**
	 * @param wenOCC the wenOCC to set
	 */
	public void setWenOCC(Integer wenOCC) {
		this.wenOCC = wenOCC;
	}


	/**
	 * @return the thuOCC
	 */
	public Integer getThuOCC() {
		return thuOCC;
	}


	/**
	 * @param thuOCC the thuOCC to set
	 */
	public void setThuOCC(Integer thuOCC) {
		this.thuOCC = thuOCC;
	}


	/**
	 * @return the friOCC
	 */
	public Integer getFriOCC() {
		return friOCC;
	}


	/**
	 * @param friOCC the friOCC to set
	 */
	public void setFriOCC(Integer friOCC) {
		this.friOCC = friOCC;
	}


	/**
	 * @return the satOCC
	 */
	public Integer getSatOCC() {
		return satOCC;
	}


	/**
	 * @param satOCC the satOCC to set
	 */
	public void setSatOCC(Integer satOCC) {
		this.satOCC = satOCC;
	}


	/**
	 * @return the weekOCC
	 */
	public Integer getWeekOCC() {
		return weekOCC;
	}


	/**
	 * @param weekOCC the weekOCC to set
	 */
	public void setWeekOCC(Integer weekOCC) {
		this.weekOCC = weekOCC;
	}


	/**
	 * @return the weekendOCC
	 */
	public Integer getWeekendOCC() {
		return weekendOCC;
	}


	/**
	 * @param weekendOCC the weekendOCC to set
	 */
	public void setWeekendOCC(Integer weekendOCC) {
		this.weekendOCC = weekendOCC;
	}


	/**
	 * @return the totalOCC
	 */
	public Integer getTotalOCC() {
		return totalOCC;
	}


	/**
	 * @param totalOCC the totalOCC to set
	 */
	public void setTotalOCC(Integer totalOCC) {
		this.totalOCC = totalOCC;
	}

}
