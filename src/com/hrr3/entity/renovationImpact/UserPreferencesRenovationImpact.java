package com.hrr3.entity.renovationImpact;

import java.io.Serializable;
import java.util.List;


public class UserPreferencesRenovationImpact implements Serializable,Cloneable {
	private static final long serialVersionUID = 1L;	
	
	private int userPreferencesId;
	private int userId;
	private int hotelId;	
	private int companyId;
	private String	statDateFrom;
	private String	statDateTo;
	private int	forecastSnaphotID;
	private int ssrSnapshotID;
	private int unrealizedTDFactor;
	private int selloutThreshold;
	private int hotelTotalRooms;
	
	private List<SnapshotSegmentData> lstSnapshotData;
	private List<SSRSegmentData> lstSSRData;
	private List<HotelTotalRooms> lstTotalRooms;
	private List<TransientSegmentData> lstTransentData;
	
	private List<ReportData> lstReportData;
	private List<ReportData> lstSummaryReportData;
	
	
	/**
	 * @return the lstTransentData
	 */
	public List<TransientSegmentData> getLstTransentData() {
		return lstTransentData;
	}


	/**
	 * @param lstTransentData the lstTransentData to set
	 */
	public void setLstTransentData(List<TransientSegmentData> lstTransentData) {
		this.lstTransentData = lstTransentData;
	}


	public UserPreferencesRenovationImpact() {
		// TODO Auto-generated constructor stub
	}


	/**
	 * @return the userPreferencesId
	 */
	public int getUserPreferencesId() {
		return userPreferencesId;
	}


	/**
	 * @param userPreferencesId the userPreferencesId to set
	 */
	public void setUserPreferencesId(int userPreferencesId) {
		this.userPreferencesId = userPreferencesId;
	}


	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}


	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
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
	 * @return the companyId
	 */
	public int getCompanyId() {
		return companyId;
	}


	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}


	/**
	 * @return the statDateFrom
	 */
	public String getStatDateFrom() {
		return statDateFrom;
	}


	/**
	 * @param statDateFrom the statDateFrom to set
	 */
	public void setStatDateFrom(String statDateFrom) {
		this.statDateFrom = statDateFrom;
	}


	/**
	 * @return the statDateTo
	 */
	public String getStatDateTo() {
		return statDateTo;
	}


	/**
	 * @param statDateTo the statDateTo to set
	 */
	public void setStatDateTo(String statDateTo) {
		this.statDateTo = statDateTo;
	}


	/**
	 * @return the forecastSnaphotID
	 */
	public int getForecastSnaphotID() {
		return forecastSnaphotID;
	}


	/**
	 * @param forecastSnaphotID the forecastSnaphotID to set
	 */
	public void setForecastSnaphotID(int forecastSnaphotID) {
		this.forecastSnaphotID = forecastSnaphotID;
	}


	/**
	 * @return the ssrSnapshotID
	 */
	public int getSsrSnapshotID() {
		return ssrSnapshotID;
	}


	/**
	 * @param ssrSnapshotID the ssrSnapshotID to set
	 */
	public void setSsrSnapshotID(int ssrSnapshotID) {
		this.ssrSnapshotID = ssrSnapshotID;
	}


	/**
	 * @return the unrealizedTDFactor
	 */
	public int getUnrealizedTDFactor() {
		return unrealizedTDFactor;
	}


	/**
	 * @param unrealizedTDFactor the unrealizedTDFactor to set
	 */
	public void setUnrealizedTDFactor(int unrealizedTDFactor) {
		this.unrealizedTDFactor = unrealizedTDFactor;
	}


	/**
	 * @return the selloutThreshold
	 */
	public int getSelloutThreshold() {
		return selloutThreshold;
	}


	/**
	 * @param selloutThreshold the selloutThreshold to set
	 */
	public void setSelloutThreshold(int selloutThreshold) {
		this.selloutThreshold = selloutThreshold;
	}


	/**
	 * @return the hotelTotalRooms
	 */
	public int getHotelTotalRooms() {
		return hotelTotalRooms;
	}


	/**
	 * @param hotelTotalRooms the hotelTotalRooms to set
	 */
	public void setHotelTotalRooms(int hotelTotalRooms) {
		this.hotelTotalRooms = hotelTotalRooms;
	}


	/**
	 * @return the lstSnapshotData
	 */
	public List<SnapshotSegmentData> getLstSnapshotData() {
		return lstSnapshotData;
	}


	/**
	 * @param lstSnapshotData the lstSnapshotData to set
	 */
	public void setLstSnapshotData(List<SnapshotSegmentData> lstSnapshotData) {
		this.lstSnapshotData = lstSnapshotData;
	}


	/**
	 * @return the lstSSRData
	 */
	public List<SSRSegmentData> getLstSSRData() {
		return lstSSRData;
	}


	/**
	 * @param lstSSRData the lstSSRData to set
	 */
	public void setLstSSRData(List<SSRSegmentData> lstSSRData) {
		this.lstSSRData = lstSSRData;
	}


	/**
	 * @return the lstTotalRooms
	 */
	public List<HotelTotalRooms> getLstTotalRooms() {
		return lstTotalRooms;
	}


	/**
	 * @param lstTotalRooms the lstTotalRooms to set
	 */
	public void setLstTotalRooms(List<HotelTotalRooms> lstTotalRooms) {
		this.lstTotalRooms = lstTotalRooms;
	}


	/**
	 * @return the lstReportData
	 */
	public List<ReportData> getLstReportData() {
		return lstReportData;
	}


	/**
	 * @param lstReportData the lstReportData to set
	 */
	public void setLstReportData(List<ReportData> lstReportData) {
		this.lstReportData = lstReportData;
	}


	/**
	 * @return the lstSummaryReportData
	 */
	public List<ReportData> getLstSummaryReportData() {
		return lstSummaryReportData;
	}


	/**
	 * @param lstSummaryReportData the lstSummaryReportData to set
	 */
	public void setLstSummaryReportData(List<ReportData> lstSummaryReportData) {
		this.lstSummaryReportData = lstSummaryReportData;
	}

}
