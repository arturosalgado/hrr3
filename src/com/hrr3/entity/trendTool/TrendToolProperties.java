package com.hrr3.entity.trendTool;

import java.math.BigDecimal;

import org.zkoss.zul.Textbox;

public class TrendToolProperties {
	
	private String type;
	private String day;
	private TrendTool segmentData;
	
	//Visual comps to link
	private Textbox occTxt;
	private Textbox adrTxt;
	
	public Integer getOccValue(){
		
		if(this.day.equalsIgnoreCase("SUNDAY"))
			return segmentData.getSunOCC();
		else if(this.day.equalsIgnoreCase("MONDAY"))
			return segmentData.getMonOCC();
		else if(this.day.equalsIgnoreCase("TUESDAY"))
			return segmentData.getTueOCC();
		else if(this.day.equalsIgnoreCase("WEDNESDAY"))
			return segmentData.getWenOCC();
		else if(this.day.equalsIgnoreCase("THURSDAY"))
			return segmentData.getThuOCC();
		else if(this.day.equalsIgnoreCase("FRIDAY"))
			return segmentData.getFriOCC();
		else if(this.day.equalsIgnoreCase("SATURDAY"))
			return segmentData.getSatOCC();
		else if(this.day.equalsIgnoreCase("WEEKDAY"))
			return segmentData.getWeekOCC();
		else if(this.day.equalsIgnoreCase("WEEKEND"))
			return segmentData.getWeekendOCC();
		else if(this.day.equalsIgnoreCase("TOTAL"))
			return segmentData.getTotalOCC();
		else
			return 0;
		
	}
	
	public void setOccValue(Integer occValue){
		
		if(this.day.equalsIgnoreCase("SUNDAY"))
			segmentData.setSunOCC(occValue);
		else if(this.day.equalsIgnoreCase("MONDAY"))
			segmentData.setMonOCC(occValue);
		else if(this.day.equalsIgnoreCase("TUESDAY"))
			segmentData.setTueOCC(occValue);
		else if(this.day.equalsIgnoreCase("WEDNESDAY"))
			segmentData.setWenOCC(occValue);
		else if(this.day.equalsIgnoreCase("THURSDAY"))
			segmentData.setThuOCC(occValue);
		else if(this.day.equalsIgnoreCase("FRIDAY"))
			segmentData.setFriOCC(occValue);
		else if(this.day.equalsIgnoreCase("SATURDAY"))
			segmentData.setSatOCC(occValue);
		else if(this.day.equalsIgnoreCase("WEEKDAY"))
			segmentData.setWeekOCC(occValue);
		else if(this.day.equalsIgnoreCase("WEEKEND"))
			segmentData.setWeekendOCC(occValue);
		else if(this.day.equalsIgnoreCase("TOTAL"))
			segmentData.setTotalOCC(occValue);
				
	}
	
	public BigDecimal getAdrValue(){
		
		if(this.day.equalsIgnoreCase("SUNDAY"))
			return segmentData.getSunADR();
		else if(this.day.equalsIgnoreCase("MONDAY"))
			return segmentData.getMonADR();
		else if(this.day.equalsIgnoreCase("TUESDAY"))
			return segmentData.getTueADR();
		else if(this.day.equalsIgnoreCase("WEDNESDAY"))
			return segmentData.getWenADR();
		else if(this.day.equalsIgnoreCase("THURSDAY"))
			return segmentData.getThuADR();
		else if(this.day.equalsIgnoreCase("FRIDAY"))
			return segmentData.getFriADR();
		else if(this.day.equalsIgnoreCase("SATURDAY"))
			return segmentData.getSatADR();
		else if(this.day.equalsIgnoreCase("WEEKDAY"))
			return segmentData.getWeekADR();
		else if(this.day.equalsIgnoreCase("WEEKEND"))
			return segmentData.getWeekendADR();
		else if(this.day.equalsIgnoreCase("TOTAL"))
			return segmentData.getTotalADR();
		else
			return new BigDecimal(0);
		
	}
	
	public void setAdrValue(BigDecimal adrValue){
		
		if(this.day.equalsIgnoreCase("SUNDAY"))
			segmentData.setSunADR(adrValue);
		else if(this.day.equalsIgnoreCase("MONDAY"))
			segmentData.setMonADR(adrValue);
		else if(this.day.equalsIgnoreCase("TUESDAY"))
			segmentData.setTueADR(adrValue);
		else if(this.day.equalsIgnoreCase("WEDNESDAY"))
			segmentData.setWenADR(adrValue);
		else if(this.day.equalsIgnoreCase("THURSDAY"))
			segmentData.setThuADR(adrValue);
		else if(this.day.equalsIgnoreCase("FRIDAY"))
			segmentData.setFriADR(adrValue);
		else if(this.day.equalsIgnoreCase("SATURDAY"))
			segmentData.setSatADR(adrValue);
		else if(this.day.equalsIgnoreCase("WEEKDAY"))
			segmentData.setWeekADR(adrValue);
		else if(this.day.equalsIgnoreCase("WEEKEND"))
			segmentData.setWeekendADR(adrValue);
		else if(this.day.equalsIgnoreCase("TOTAL"))
			segmentData.setTotalADR(adrValue);
				
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
	 * @return the day
	 */
	public String getDay() {
		return day;
	}
	/**
	 * @param day the day to set
	 */
	public void setDay(String day) {
		this.day = day;
	}
	
	/**
	 * @return the occTxt
	 */
	public Textbox getOccTxt() {
		return occTxt;
	}
	/**
	 * @param occTxt the occTxt to set
	 */
	public void setOccTxt(Textbox occTxt) {
		this.occTxt = occTxt;
	}
	/**
	 * @return the adrTxt
	 */
	public Textbox getAdrTxt() {
		return adrTxt;
	}
	/**
	 * @param adrTxt the adrTxt to set
	 */
	public void setAdrTxt(Textbox adrTxt) {
		this.adrTxt = adrTxt;
	}
		
	public static String buildKey(String type, String day, Integer segmentId) {
		return type + day + segmentId;
	}
	/**
	 * @return the segmentData
	 */
	public TrendTool getSegmentData() {
		return segmentData;
	}
	/**
	 * @param segmentData the segmentData to set
	 */
	public void setSegmentData(TrendTool segmentData) {
		this.segmentData = segmentData;
	}

}
