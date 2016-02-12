package com.hrr3.util.reports;

public interface JasperServerReportsConfig {
	
	//ReportURIs
	public static String MARKET_SEGMENT_NORMAL_REPORT = "MarketSegment";
	public static String MARKET_SEGMENT_COMPACT_REPORT = "MarketSegmentCompact";
	public static String MARKET_MIX_REPORT = "MarketMixAnalysis";
	public static String ANNUAL_ROOM_REVENUE_REPORT = "AnnualRoomRevenueAnalysis";
	public static String DAY_BY_DAY_REPORT = "DayByDay";
	public static String GROUP_MONTHLY_REPORT = "GroupMonthly";
	public static String ROLLUP_ANNUAL_ROOM_REVENUE_REPORT = "AnnualRoomRevenueRollUp";
	public static String ROLLUP_FORECAST_ACCURACY_REPORT = "ForecastAccuracyRollupReport";
	public static String ROLLUP_MARKET_MIX_REPORT = "RollUpMarketAnalysis";
	public static String ROLLUP_WEEKLY_MEETING_REPORT = "RollupWeeklyMeetingMinutes";
	public static String ROLLUP_NEED_DATE_CALENDAR_REPORT = "RollupNeedDateCalendar";
	
	public static String MONTH_END_ANALYSIS_REPORT = "MonthEndAnalysis";	
	public static String INTERNET_STRATEGY_REPORT = "InternetStrategyEffectiveness";
	public static String WEEKLY_MEETING_REPORT = "WeeklyMeetingMinutes";
	
	
	//SSR Reports
	public static String SSR_TRANSIENT_TREND_REPORT = "SSRTransientTrend";
	public static String SSR_MONTHLY_FORECAST_SUMMARY_REPORT = "SSRMonthlyForecastSummary";
	public static String SSR_ANNUAL_ROOM_REVENUE_REPORT = "SSRAnnualRoomRevenueAnalysis";
	public static String SSR_SELL_STRATEGY_REPORT = "SSRSellStrategy";
	public static String SSR_TRANSIENT_PACE_ANALYSIS = "SSRTransientPace";
	public static String SSR_WEEKLY_STAR = "SSRDayStarTrend";
	public static String SSR_MONTHLY_STAR = "SSRMonthlyStarTrend";
	
	//Output Formats
	public static String PDF_FORMAT = "pdf";
	public static String XLS_FORMAT = "xls";
	public static String DOCX_FORMAT = "docx";
	
	
}
