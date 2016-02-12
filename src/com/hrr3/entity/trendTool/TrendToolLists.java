package com.hrr3.entity.trendTool;

import java.io.Serializable;
import java.util.List;

public class TrendToolLists implements Serializable,Cloneable {
	private static final long serialVersionUID = 1L;
	
	List<TrendTool> lstTrendOld;
	List<TrendTool> lstTrendNew;
	List<TrendTool> lstForecastBase;
	List<TrendTool> lstForecastTrend;
	List<TrendTool> lstActualChange;
	List<TrendTool> lstOverride;
	List<TrendTool> lstModelWeek;
	List<TrendTool> lstAdvancedPasting;

	public TrendToolLists() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the lstTrendOld
	 */
	public List<TrendTool> getLstTrendOld() {
		return lstTrendOld;
	}

	/**
	 * @param lstTrendOld the lstTrendOld to set
	 */
	public void setLstTrendOld(List<TrendTool> lstTrendOld) {
		this.lstTrendOld = lstTrendOld;
	}

	/**
	 * @return the lstTrendNew
	 */
	public List<TrendTool> getLstTrendNew() {
		return lstTrendNew;
	}

	/**
	 * @param lstTrendNew the lstTrendNew to set
	 */
	public void setLstTrendNew(List<TrendTool> lstTrendNew) {
		this.lstTrendNew = lstTrendNew;
	}

	/**
	 * @return the lstForecastBase
	 */
	public List<TrendTool> getLstForecastBase() {
		return lstForecastBase;
	}

	/**
	 * @param lstForecastBase the lstForecastBase to set
	 */
	public void setLstForecastBase(List<TrendTool> lstForecastBase) {
		this.lstForecastBase = lstForecastBase;
	}

	/**
	 * @return the lstForecastTrend
	 */
	public List<TrendTool> getLstForecastTrend() {
		return lstForecastTrend;
	}

	/**
	 * @param lstForecastTrend the lstForecastTrend to set
	 */
	public void setLstForecastTrend(List<TrendTool> lstForecastTrend) {
		this.lstForecastTrend = lstForecastTrend;
	}

	/**
	 * @return the lstChangeActual
	 */
	public List<TrendTool> getLstActualChange() {
		return lstActualChange;
	}

	/**
	 * @param lstChangeActual the lstChangeActual to set
	 */
	public void setLstActualChange(List<TrendTool> lstActualChange) {
		this.lstActualChange = lstActualChange;
	}

	/**
	 * @return the lstOverride
	 */
	public List<TrendTool> getLstOverride() {
		return lstOverride;
	}

	/**
	 * @param lstOverride the lstOverride to set
	 */
	public void setLstOverride(List<TrendTool> lstOverride) {
		this.lstOverride = lstOverride;
	}

	/**
	 * @return the lstModelWeek
	 */
	public List<TrendTool> getLstModelWeek() {
		return lstModelWeek;
	}

	/**
	 * @param lstModelWeek the lstModelWeek to set
	 */
	public void setLstModelWeek(List<TrendTool> lstModelWeek) {
		this.lstModelWeek = lstModelWeek;
	}

	/**
	 * @return the lstAdvancedPasting
	 */
	public List<TrendTool> getLstAdvancedPasting() {
		return lstAdvancedPasting;
	}

	/**
	 * @param lstAdvancedPasting the lstAdvancedPasting to set
	 */
	public void setLstAdvancedPasting(List<TrendTool> lstAdvancedPasting) {
		this.lstAdvancedPasting = lstAdvancedPasting;
	}

	@Override
	public String toString() {
		return "TrendToolLists [lstTrendOld=" + lstTrendOld + ", lstTrendNew=" + lstTrendNew + ", lstForecastBase="
				+ lstForecastBase + ", lstForecastTrend=" + lstForecastTrend + ", lstActualChange=" + lstActualChange
				+ ", lstOverride=" + lstOverride + ", lstModelWeek=" + lstModelWeek + ", lstAdvancedPasting="
				+ lstAdvancedPasting + "]";
	}
	
}
