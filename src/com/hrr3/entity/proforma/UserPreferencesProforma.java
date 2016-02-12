package com.hrr3.entity.proforma;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class UserPreferencesProforma implements Serializable,Cloneable {
	private static final long serialVersionUID = 1L;	
	
	private int userPreferencesId;
	private int userId;
	private int hotelId;
	private String hotelName;
	private int companyId;
	private String	statDateFrom;
	private String	statDateTo;
	private int	forecastSnaphotID;
	private String	groupName;
	private String	partADateFrom;
	private String	partADateTo;
	private int	partARmsAllWeekDays;
	private int	partARmsMonday;
	private int	partARmsTuesday;
	private int	partARmsWednesday;
	private int	partARmsThursday;
	private int	partARmsFriday;
	private int	partARmsSaturday;
	private int	partARmsSunday;
	private int	partAADRAllWeekDays;
	private int	partAADRMonday;
	private int	partAADRTuesday;
	private int	partAADRWednesday;
	private int	partAADRThursday;
	private int	partAADRFriday;
	private int	partAADRSaturday;
	private int	partAADRSunday;
	private String	partBDateFrom;
	private String	partBDateTo;
	private int	partBRmsAllWeekDays;
	private int	partBRmsMonday;
	private int	partBRmsTuesday;
	private int	partBRmsWednesday;
	private int	partBRmsThursday;
	private int	partBRmsFriday;
	private int	partBRmsSaturday;
	private int	partBRmsSunday;
	private int	partBADRAllWeekDays;
	private int	partBADRMonday;
	private int	partBADRTuesday;
	private int	partBADRWednesday;
	private int	partBADRThursday;
	private int	partBADRFriday;
	private int	partBADRSaturday;
	private int	partBADRSunday;
	private int	fBRevenue;
	private int	fBRevenueProfitPct;
	private int	fBRevenueProfit;
	private int	otherRevenue;
	private int	otherRevenueProfitPct;
	private int	otherRevenueProfit;
	private BigDecimal	displaced;
	private BigDecimal	base;
	
	/*Values*/	
	private List<HotelTotalRooms> lstTotalRooms ;
	private List<SnapshotSegmentData> lstSegmentData ;
	private List<SnapshotTransientData> lstTransientData;
	private List<ProFormaData> lstProFormaData;
	
	private List<ReportData> lstReportData;
	private List<ReportData> lstSummaryReportData;
	
	public UserPreferencesProforma() {
		// TODO Auto-generated constructor stub
		this.displaced = BigDecimal.ZERO;
		this.base = BigDecimal.ZERO;
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
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}
	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	/**
	 * @return the partADateFrom
	 */
	public String getPartADateFrom() {
		return partADateFrom;
	}
	/**
	 * @param partADateFrom the partADateFrom to set
	 */
	public void setPartADateFrom(String partADateFrom) {
		this.partADateFrom = partADateFrom;
	}
	/**
	 * @return the partADateTo
	 */
	public String getPartADateTo() {
		return partADateTo;
	}
	/**
	 * @param partADateTo the partADateTo to set
	 */
	public void setPartADateTo(String partADateTo) {
		this.partADateTo = partADateTo;
	}
	/**
	 * @return the partARmsAllWeekDays
	 */
	public int getPartARmsAllWeekDays() {
		return partARmsAllWeekDays;
	}
	/**
	 * @param partARmsAllWeekDays the partARmsAllWeekDays to set
	 */
	public void setPartARmsAllWeekDays(int partARmsAllWeekDays) {
		this.partARmsAllWeekDays = partARmsAllWeekDays;
	}
	/**
	 * @return the partARmsMonday
	 */
	public int getPartARmsMonday() {
		return partARmsMonday;
	}
	/**
	 * @param partARmsMonday the partARmsMonday to set
	 */
	public void setPartARmsMonday(int partARmsMonday) {
		this.partARmsMonday = partARmsMonday;
	}
	/**
	 * @return the partARmsTuesday
	 */
	public int getPartARmsTuesday() {
		return partARmsTuesday;
	}
	/**
	 * @param partARmsTuesday the partARmsTuesday to set
	 */
	public void setPartARmsTuesday(int partARmsTuesday) {
		this.partARmsTuesday = partARmsTuesday;
	}
	/**
	 * @return the partARmsWednesday
	 */
	public int getPartARmsWednesday() {
		return partARmsWednesday;
	}
	/**
	 * @param partARmsWednesday the partARmsWednesday to set
	 */
	public void setPartARmsWednesday(int partARmsWednesday) {
		this.partARmsWednesday = partARmsWednesday;
	}
	/**
	 * @return the partARmsThursday
	 */
	public int getPartARmsThursday() {
		return partARmsThursday;
	}
	/**
	 * @param partARmsThursday the partARmsThursday to set
	 */
	public void setPartARmsThursday(int partARmsThursday) {
		this.partARmsThursday = partARmsThursday;
	}
	/**
	 * @return the partARmsFriday
	 */
	public int getPartARmsFriday() {
		return partARmsFriday;
	}
	/**
	 * @param partARmsFriday the partARmsFriday to set
	 */
	public void setPartARmsFriday(int partARmsFriday) {
		this.partARmsFriday = partARmsFriday;
	}
	/**
	 * @return the partARmsSaturday
	 */
	public int getPartARmsSaturday() {
		return partARmsSaturday;
	}
	/**
	 * @param partARmsSaturday the partARmsSaturday to set
	 */
	public void setPartARmsSaturday(int partARmsSaturday) {
		this.partARmsSaturday = partARmsSaturday;
	}
	/**
	 * @return the partARmsSunday
	 */
	public int getPartARmsSunday() {
		return partARmsSunday;
	}
	/**
	 * @param partARmsSunday the partARmsSunday to set
	 */
	public void setPartARmsSunday(int partARmsSunday) {
		this.partARmsSunday = partARmsSunday;
	}
	/**
	 * @return the partAADRAllWeekDays
	 */
	public int getPartAADRAllWeekDays() {
		return partAADRAllWeekDays;
	}
	/**
	 * @param partAADRAllWeekDays the partAADRAllWeekDays to set
	 */
	public void setPartAADRAllWeekDays(int partAADRAllWeekDays) {
		this.partAADRAllWeekDays = partAADRAllWeekDays;
	}
	/**
	 * @return the partAADRMonday
	 */
	public int getPartAADRMonday() {
		return partAADRMonday;
	}
	/**
	 * @param partAADRMonday the partAADRMonday to set
	 */
	public void setPartAADRMonday(int partAADRMonday) {
		this.partAADRMonday = partAADRMonday;
	}
	/**
	 * @return the partAADRTuesday
	 */
	public int getPartAADRTuesday() {
		return partAADRTuesday;
	}
	/**
	 * @param partAADRTuesday the partAADRTuesday to set
	 */
	public void setPartAADRTuesday(int partAADRTuesday) {
		this.partAADRTuesday = partAADRTuesday;
	}
	/**
	 * @return the partAADRWednesday
	 */
	public int getPartAADRWednesday() {
		return partAADRWednesday;
	}
	/**
	 * @param partAADRWednesday the partAADRWednesday to set
	 */
	public void setPartAADRWednesday(int partAADRWednesday) {
		this.partAADRWednesday = partAADRWednesday;
	}
	/**
	 * @return the partAADRThursday
	 */
	public int getPartAADRThursday() {
		return partAADRThursday;
	}
	/**
	 * @param partAADRThursday the partAADRThursday to set
	 */
	public void setPartAADRThursday(int partAADRThursday) {
		this.partAADRThursday = partAADRThursday;
	}
	/**
	 * @return the partAADRFriday
	 */
	public int getPartAADRFriday() {
		return partAADRFriday;
	}
	/**
	 * @param partAADRFriday the partAADRFriday to set
	 */
	public void setPartAADRFriday(int partAADRFriday) {
		this.partAADRFriday = partAADRFriday;
	}
	/**
	 * @return the partAADRSaturday
	 */
	public int getPartAADRSaturday() {
		return partAADRSaturday;
	}
	/**
	 * @param partAADRSaturday the partAADRSaturday to set
	 */
	public void setPartAADRSaturday(int partAADRSaturday) {
		this.partAADRSaturday = partAADRSaturday;
	}
	/**
	 * @return the partAADRSunday
	 */
	public int getPartAADRSunday() {
		return partAADRSunday;
	}
	/**
	 * @param partAADRSunday the partAADRSunday to set
	 */
	public void setPartAADRSunday(int partAADRSunday) {
		this.partAADRSunday = partAADRSunday;
	}
	/**
	 * @return the partBDateFrom
	 */
	public String getPartBDateFrom() {
		return partBDateFrom;
	}
	/**
	 * @param partBDateFrom the partBDateFrom to set
	 */
	public void setPartBDateFrom(String partBDateFrom) {
		this.partBDateFrom = partBDateFrom;
	}
	/**
	 * @return the partBDateTo
	 */
	public String getPartBDateTo() {
		return partBDateTo;
	}
	/**
	 * @param partBDateTo the partBDateTo to set
	 */
	public void setPartBDateTo(String partBDateTo) {
		this.partBDateTo = partBDateTo;
	}
	/**
	 * @return the partBRmsAllWeekDays
	 */
	public int getPartBRmsAllWeekDays() {
		return partBRmsAllWeekDays;
	}
	/**
	 * @param partBRmsAllWeekDays the partBRmsAllWeekDays to set
	 */
	public void setPartBRmsAllWeekDays(int partBRmsAllWeekDays) {
		this.partBRmsAllWeekDays = partBRmsAllWeekDays;
	}
	/**
	 * @return the partBRmsMonday
	 */
	public int getPartBRmsMonday() {
		return partBRmsMonday;
	}
	/**
	 * @param partBRmsMonday the partBRmsMonday to set
	 */
	public void setPartBRmsMonday(int partBRmsMonday) {
		this.partBRmsMonday = partBRmsMonday;
	}
	/**
	 * @return the partBRmsTuesday
	 */
	public int getPartBRmsTuesday() {
		return partBRmsTuesday;
	}
	/**
	 * @param partBRmsTuesday the partBRmsTuesday to set
	 */
	public void setPartBRmsTuesday(int partBRmsTuesday) {
		this.partBRmsTuesday = partBRmsTuesday;
	}
	/**
	 * @return the partBRmsWednesday
	 */
	public int getPartBRmsWednesday() {
		return partBRmsWednesday;
	}
	/**
	 * @param partBRmsWednesday the partBRmsWednesday to set
	 */
	public void setPartBRmsWednesday(int partBRmsWednesday) {
		this.partBRmsWednesday = partBRmsWednesday;
	}
	/**
	 * @return the partBRmsThursday
	 */
	public int getPartBRmsThursday() {
		return partBRmsThursday;
	}
	/**
	 * @param partBRmsThursday the partBRmsThursday to set
	 */
	public void setPartBRmsThursday(int partBRmsThursday) {
		this.partBRmsThursday = partBRmsThursday;
	}
	/**
	 * @return the partBRmsFriday
	 */
	public int getPartBRmsFriday() {
		return partBRmsFriday;
	}
	/**
	 * @param partBRmsFriday the partBRmsFriday to set
	 */
	public void setPartBRmsFriday(int partBRmsFriday) {
		this.partBRmsFriday = partBRmsFriday;
	}
	/**
	 * @return the partBRmsSaturday
	 */
	public int getPartBRmsSaturday() {
		return partBRmsSaturday;
	}
	/**
	 * @param partBRmsSaturday the partBRmsSaturday to set
	 */
	public void setPartBRmsSaturday(int partBRmsSaturday) {
		this.partBRmsSaturday = partBRmsSaturday;
	}
	/**
	 * @return the partBRmsSunday
	 */
	public int getPartBRmsSunday() {
		return partBRmsSunday;
	}
	/**
	 * @param partBRmsSunday the partBRmsSunday to set
	 */
	public void setPartBRmsSunday(int partBRmsSunday) {
		this.partBRmsSunday = partBRmsSunday;
	}
	/**
	 * @return the partBADRAllWeekDays
	 */
	public int getPartBADRAllWeekDays() {
		return partBADRAllWeekDays;
	}
	/**
	 * @param partBADRAllWeekDays the partBADRAllWeekDays to set
	 */
	public void setPartBADRAllWeekDays(int partBADRAllWeekDays) {
		this.partBADRAllWeekDays = partBADRAllWeekDays;
	}
	/**
	 * @return the partBADRMonday
	 */
	public int getPartBADRMonday() {
		return partBADRMonday;
	}
	/**
	 * @param partBADRMonday the partBADRMonday to set
	 */
	public void setPartBADRMonday(int partBADRMonday) {
		this.partBADRMonday = partBADRMonday;
	}
	/**
	 * @return the partBADRTuesday
	 */
	public int getPartBADRTuesday() {
		return partBADRTuesday;
	}
	/**
	 * @param partBADRTuesday the partBADRTuesday to set
	 */
	public void setPartBADRTuesday(int partBADRTuesday) {
		this.partBADRTuesday = partBADRTuesday;
	}
	/**
	 * @return the partBADRWednesday
	 */
	public int getPartBADRWednesday() {
		return partBADRWednesday;
	}
	/**
	 * @param partBADRWednesday the partBADRWednesday to set
	 */
	public void setPartBADRWednesday(int partBADRWednesday) {
		this.partBADRWednesday = partBADRWednesday;
	}
	/**
	 * @return the partBADRThursday
	 */
	public int getPartBADRThursday() {
		return partBADRThursday;
	}
	/**
	 * @param partBADRThursday the partBADRThursday to set
	 */
	public void setPartBADRThursday(int partBADRThursday) {
		this.partBADRThursday = partBADRThursday;
	}
	/**
	 * @return the partBADRFriday
	 */
	public int getPartBADRFriday() {
		return partBADRFriday;
	}
	/**
	 * @param partBADRFriday the partBADRFriday to set
	 */
	public void setPartBADRFriday(int partBADRFriday) {
		this.partBADRFriday = partBADRFriday;
	}
	/**
	 * @return the partBADRSaturday
	 */
	public int getPartBADRSaturday() {
		return partBADRSaturday;
	}
	/**
	 * @param partBADRSaturday the partBADRSaturday to set
	 */
	public void setPartBADRSaturday(int partBADRSaturday) {
		this.partBADRSaturday = partBADRSaturday;
	}
	/**
	 * @return the partBADRSunday
	 */
	public int getPartBADRSunday() {
		return partBADRSunday;
	}
	/**
	 * @param partBADRSunday the partBADRSunday to set
	 */
	public void setPartBADRSunday(int partBADRSunday) {
		this.partBADRSunday = partBADRSunday;
	}
	/**
	 * @return the fBRevenue
	 */
	public int getfBRevenue() {
		return fBRevenue;
	}
	/**
	 * @param fBRevenue the fBRevenue to set
	 */
	public void setfBRevenue(int fBRevenue) {
		this.fBRevenue = fBRevenue;
	}
	/**
	 * @return the fBRevenueProfitPct
	 */
	public int getfBRevenueProfitPct() {
		return fBRevenueProfitPct;
	}
	/**
	 * @param fBRevenueProfitPct the fBRevenueProfitPct to set
	 */
	public void setfBRevenueProfitPct(int fBRevenueProfitPct) {
		this.fBRevenueProfitPct = fBRevenueProfitPct;
	}
	/**
	 * @return the fBRevenueProfit
	 */
	public int getfBRevenueProfit() {
		return fBRevenueProfit;
	}
	/**
	 * @param fBRevenueProfit the fBRevenueProfit to set
	 */
	public void setfBRevenueProfit(int fBRevenueProfit) {
		this.fBRevenueProfit = fBRevenueProfit;
	}
	/**
	 * @return the otherRevenue
	 */
	public int getOtherRevenue() {
		return otherRevenue;
	}
	/**
	 * @param otherRevenue the otherRevenue to set
	 */
	public void setOtherRevenue(int otherRevenue) {
		this.otherRevenue = otherRevenue;
	}
	/**
	 * @return the otherRevenueProfitPct
	 */
	public int getOtherRevenueProfitPct() {
		return otherRevenueProfitPct;
	}
	/**
	 * @param otherRevenueProfitPct the otherRevenueProfitPct to set
	 */
	public void setOtherRevenueProfitPct(int otherRevenueProfitPct) {
		this.otherRevenueProfitPct = otherRevenueProfitPct;
	}
	/**
	 * @return the otherRevenueProfit
	 */
	public int getOtherRevenueProfit() {
		return otherRevenueProfit;
	}
	/**
	 * @param otherRevenueProfit the otherRevenueProfit to set
	 */
	public void setOtherRevenueProfit(int otherRevenueProfit) {
		this.otherRevenueProfit = otherRevenueProfit;
	}
	/**
	 * @return the displaced
	 */
	public BigDecimal getDisplaced() {
		return displaced;
	}
	/**
	 * @param displaced the displaced to set
	 */
	public void setDisplaced(BigDecimal displaced) {
		this.displaced = displaced;
	}
	/**
	 * @return the base
	 */
	public BigDecimal getBase() {
		return base;
	}
	/**
	 * @param base the base to set
	 */
	public void setBase(BigDecimal base) {
		this.base = base;
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
	 * @return the lstSegmentData
	 */
	public List<SnapshotSegmentData> getLstSegmentData() {
		return lstSegmentData;
	}
	/**
	 * @param lstSegmentData the lstSegmentData to set
	 */
	public void setLstSegmentData(List<SnapshotSegmentData> lstSegmentData) {
		this.lstSegmentData = lstSegmentData;
	}
	/**
	 * @return the lstTransientData
	 */
	public List<SnapshotTransientData> getLstTransientData() {
		return lstTransientData;
	}
	/**
	 * @param lstTransientData the lstTransientData to set
	 */
	public void setLstTransientData(List<SnapshotTransientData> lstTransientData) {
		this.lstTransientData = lstTransientData;
	}
	/**
	 * @return the lstProFormaData
	 */
	public List<ProFormaData> getLstProFormaData() {
		return lstProFormaData;
	}
	/**
	 * @param lstProFormaData the lstProFormaData to set
	 */
	public void setLstProFormaData(List<ProFormaData> lstProFormaData) {
		this.lstProFormaData = lstProFormaData;
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

	/**
	 * @return the hotelName
	 */
	public String getHotelName() {
		return hotelName;
	}

	/**
	 * @param hotelName the hotelName to set
	 */
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserPreferencesProforma [userPreferencesId="
				+ userPreferencesId + ", userId=" + userId + ", hotelId="
				+ hotelId + ", companyId=" + companyId + ", statDateFrom="
				+ statDateFrom + ", statDateTo=" + statDateTo
				+ ", forecastSnaphotID=" + forecastSnaphotID + ", groupName="
				+ groupName + ", partADateFrom=" + partADateFrom
				+ ", partADateTo=" + partADateTo + ", partARmsAllWeekDays="
				+ partARmsAllWeekDays + ", partARmsMonday=" + partARmsMonday
				+ ", partARmsTuesday=" + partARmsTuesday
				+ ", partARmsWednesday=" + partARmsWednesday
				+ ", partARmsThursday=" + partARmsThursday
				+ ", partARmsFriday=" + partARmsFriday + ", partARmsSaturday="
				+ partARmsSaturday + ", partARmsSunday=" + partARmsSunday
				+ ", partAADRAllWeekDays=" + partAADRAllWeekDays
				+ ", partAADRMonday=" + partAADRMonday + ", partAADRTuesday="
				+ partAADRTuesday + ", partAADRWednesday=" + partAADRWednesday
				+ ", partAADRThursday=" + partAADRThursday
				+ ", partAADRFriday=" + partAADRFriday + ", partAADRSaturday="
				+ partAADRSaturday + ", partAADRSunday=" + partAADRSunday
				+ ", partBDateFrom=" + partBDateFrom + ", partBDateTo="
				+ partBDateTo + ", partBRmsAllWeekDays=" + partBRmsAllWeekDays
				+ ", partBRmsMonday=" + partBRmsMonday + ", partBRmsTuesday="
				+ partBRmsTuesday + ", partBRmsWednesday=" + partBRmsWednesday
				+ ", partBRmsThursday=" + partBRmsThursday
				+ ", partBRmsFriday=" + partBRmsFriday + ", partBRmsSaturday="
				+ partBRmsSaturday + ", partBRmsSunday=" + partBRmsSunday
				+ ", partBADRAllWeekDays=" + partBADRAllWeekDays
				+ ", partBADRMonday=" + partBADRMonday + ", partBADRTuesday="
				+ partBADRTuesday + ", partBADRWednesday=" + partBADRWednesday
				+ ", partBADRThursday=" + partBADRThursday
				+ ", partBADRFriday=" + partBADRFriday + ", partBADRSaturday="
				+ partBADRSaturday + ", partBADRSunday=" + partBADRSunday
				+ ", fBRevenue=" + fBRevenue + ", fBRevenueProfitPct="
				+ fBRevenueProfitPct + ", fBRevenueProfit=" + fBRevenueProfit
				+ ", otherRevenue=" + otherRevenue + ", otherRevenueProfitPct="
				+ otherRevenueProfitPct + ", otherRevenueProfit="
				+ otherRevenueProfit + ", displaced=" + displaced + ", base="
				+ base + ", lstTotalRooms=" + lstTotalRooms
				+ ", lstSegmentData=" + lstSegmentData + ", lstTransientData="
				+ lstTransientData + ", lstProFormaData=" + lstProFormaData
				+ ", lstReportData=" + lstReportData
				+ ", lstSummaryReportData=" + lstSummaryReportData + "]";
	}

}