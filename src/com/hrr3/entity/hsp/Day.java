package com.hrr3.entity.hsp;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "Day")
public class Day {
	
	private Date day_Block_Date; 
	private String day_Block_WeekDayName; 
	private int day_Block_Room_Block; 
	private int day_Block_Net_Block;
	private int Day_Block_Actual_Block; 
	private BigDecimal day_Block_Actual_Dollars; 
	private int day_Block_Room_1_Type;
	private int day_Block_Room_2_Type; 
	private int day_Block_Room_3_Type; 
	private int day_Block_Room_4_Type; 
	private int day_Block_Room_5_Type;
	/**
	 * @return the day_Block_Date
	 */
	public Date getDay_Block_Date() {
		return day_Block_Date;
	}
	/**
	 * @param day_Block_Date the day_Block_Date to set
	 */
	@XmlAttribute(name = "Day_Block_Date")
	@XmlJavaTypeAdapter(DateAdapter.class)
	public void setDay_Block_Date(Date day_Block_Date) {
		this.day_Block_Date = day_Block_Date;
	}
	/**
	 * @return the day_Block_WeekDayName
	 */
	public String getDay_Block_WeekDayName() {
		return day_Block_WeekDayName;
	}
	/**
	 * @param day_Block_WeekDayName the day_Block_WeekDayName to set
	 */
	@XmlAttribute(name = "Day_Block_WeekDayName")
	public void setDay_Block_WeekDayName(String day_Block_WeekDayName) {
		this.day_Block_WeekDayName = day_Block_WeekDayName;
	}
	/**
	 * @return the day_Block_Room_Block
	 */
	public int getDay_Block_Room_Block() {
		return day_Block_Room_Block;
	}
	/**
	 * @param day_Block_Room_Block the day_Block_Room_Block to set
	 */
	@XmlAttribute(name = "Day_Block_Room_Block")
	public void setDay_Block_Room_Block(int day_Block_Room_Block) {
		this.day_Block_Room_Block = day_Block_Room_Block;
	}
	/**
	 * @return the day_Block_Net_Block
	 */
	public int getDay_Block_Net_Block() {
		return day_Block_Net_Block;
	}
	/**
	 * @param day_Block_Net_Block the day_Block_Net_Block to set
	 */
	@XmlAttribute(name = "Day_Block_Net_Block")
	public void setDay_Block_Net_Block(int day_Block_Net_Block) {
		this.day_Block_Net_Block = day_Block_Net_Block;
	}
	/**
	 * @return the day_Block_Actual_Block
	 */
	public int getDay_Block_Actual_Block() {
		return Day_Block_Actual_Block;
	}
	/**
	 * @param day_Block_Actual_Block the day_Block_Actual_Block to set
	 */
	@XmlAttribute(name = "Day_Block_Actual_Block")
	public void setDay_Block_Actual_Block(int day_Block_Actual_Block) {
		Day_Block_Actual_Block = day_Block_Actual_Block;
	}
	/**
	 * @return the day_Block_Actual_Dollars
	 */
	public BigDecimal getDay_Block_Actual_Dollars() {
		return day_Block_Actual_Dollars;
	}
	/**
	 * @param day_Block_Actual_Dollars the day_Block_Actual_Dollars to set
	 */
	@XmlAttribute(name = "Day_Block_Actual_Dollars")
	public void setDay_Block_Actual_Dollars(BigDecimal day_Block_Actual_Dollars) {
		this.day_Block_Actual_Dollars = day_Block_Actual_Dollars;
	}
	/**
	 * @return the day_Block_Room_1_Type
	 */
	public int getDay_Block_Room_1_Type() {
		return day_Block_Room_1_Type;
	}
	/**
	 * @param day_Block_Room_1_Type the day_Block_Room_1_Type to set
	 */
	@XmlAttribute(name = "Day_Block_Room_1_Type")
	public void setDay_Block_Room_1_Type(int day_Block_Room_1_Type) {
		this.day_Block_Room_1_Type = day_Block_Room_1_Type;
	}
	/**
	 * @return the day_Block_Room_2_Type
	 */
	public int getDay_Block_Room_2_Type() {
		return day_Block_Room_2_Type;
	}
	/**
	 * @param day_Block_Room_2_Type the day_Block_Room_2_Type to set
	 */
	@XmlAttribute(name = "Day_Block_Room_2_Type")
	public void setDay_Block_Room_2_Type(int day_Block_Room_2_Type) {
		this.day_Block_Room_2_Type = day_Block_Room_2_Type;
	}
	/**
	 * @return the day_Block_Room_3_Type
	 */
	public int getDay_Block_Room_3_Type() {
		return day_Block_Room_3_Type;
	}
	/**
	 * @param day_Block_Room_3_Type the day_Block_Room_3_Type to set
	 */
	@XmlAttribute(name = "Day_Block_Room_3_Type")
	public void setDay_Block_Room_3_Type(int day_Block_Room_3_Type) {
		this.day_Block_Room_3_Type = day_Block_Room_3_Type;
	}
	/**
	 * @return the day_Block_Room_4_Type
	 */
	public int getDay_Block_Room_4_Type() {
		return day_Block_Room_4_Type;
	}
	/**
	 * @param day_Block_Room_4_Type the day_Block_Room_4_Type to set
	 */
	@XmlAttribute(name = "Day_Block_Room_4_Type")
	public void setDay_Block_Room_4_Type(int day_Block_Room_4_Type) {
		this.day_Block_Room_4_Type = day_Block_Room_4_Type;
	}
	/**
	 * @return the day_Block_Room_5_Type
	 */
	public int getDay_Block_Room_5_Type() {
		return day_Block_Room_5_Type;
	}
	/**
	 * @param day_Block_Room_5_Type the day_Block_Room_5_Type to set
	 */
	@XmlAttribute(name = "Day_Block_Room_5_Type")
	public void setDay_Block_Room_5_Type(int day_Block_Room_5_Type) {
		this.day_Block_Room_5_Type = day_Block_Room_5_Type;
	}

}
