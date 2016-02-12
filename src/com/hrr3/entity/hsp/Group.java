package com.hrr3.entity.hsp;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "Group")
public class Group {
	
	private int property_ID;     
	private int group_ID; 
    private String group_Name;
    private String salesPerson; 
    private String group_Status;
    private Date date_Definite; 
    private Date date_Cancelled; 
    private Date release_Date; 
    private String group_Type; 
    private Date group_Date_Created; 
    private Date group_Date; 
    private String room_1_Type; 
    private String room_2_Type; 
    private String room_3_Type; 
    private String room_4_Type; 
    private String room_5_Type; 
    private BigDecimal room_1_Rate;
    private BigDecimal room_2_Rate; 
    private BigDecimal room_3_Rate; 
    private BigDecimal room_4_Rate; 
    private BigDecimal room_5_Rate; 
    private int room_1_Block; 
    private int room_2_Block;
    private int room_3_Block; 
    private int room_4_Block;
    private int room_5_Block; 
    private int room_Block_Total;
    private int group_Net; 
    private int actual_Nights; 
    private int actual_Dollars; 
    private BigDecimal actual_ADR;
    
    private Days days;
	/**
	 * @return the property_ID
	 */
	public int getProperty_ID() {
		return property_ID;
	}
	/**
	 * @param property_ID the property_ID to set
	 */
	@XmlAttribute(name = "Property_ID")
	public void setProperty_ID(int property_ID) {
		this.property_ID = property_ID;
	}
	/**
	 * @return the group_ID
	 */
	public int getGroup_ID() {
		return group_ID;
	}
	/**
	 * @param group_ID the group_ID to set
	 */
	@XmlAttribute(name = "Group_ID")
	public void setGroup_ID(int group_ID) {
		this.group_ID = group_ID;
	}
	/**
	 * @return the group_Name
	 */
	public String getGroup_Name() {
		return group_Name;
	}
	/**
	 * @param group_Name the group_Name to set
	 */
	@XmlAttribute(name = "Group_Name")
	public void setGroup_Name(String group_Name) {
		this.group_Name = group_Name;
	}
	/**
	 * @return the salesPerson
	 */
	public String getSalesPerson() {
		return salesPerson;
	}
	/**
	 * @param salesPerson the salesPerson to set
	 */
	@XmlAttribute(name = "SalesPerson")
	public void setSalesPerson(String salesPerson) {
		this.salesPerson = salesPerson;
	}
	/**
	 * @return the group_Status
	 */
	public String getGroup_Status() {
		return group_Status;
	}
	/**
	 * @param group_Status the group_Status to set
	 */
	@XmlAttribute(name = "Group_Status")
	public void setGroup_Status(String group_Status) {
		this.group_Status = group_Status;
	}
	/**
	 * @return the date_Definite
	 */
	public Date getDate_Definite() {
		return date_Definite;
	}
	/**
	 * @param date_Definite the date_Definite to set
	 */
	@XmlAttribute(name = "Date_Definite")
	@XmlJavaTypeAdapter(DateAdapter.class)
	public void setDate_Definite(Date date_Definite) {
		this.date_Definite = date_Definite;
	}
	/**
	 * @return the date_Cancelled
	 */
	public Date getDate_Cancelled() {
		return date_Cancelled;
	}
	/**
	 * @param date_Cancelled the date_Cancelled to set
	 */
	@XmlAttribute(name = "Date_Cancelled")
	@XmlJavaTypeAdapter(DateAdapter.class)
	public void setDate_Cancelled(Date date_Cancelled) {
		this.date_Cancelled = date_Cancelled;
	}
	/**
	 * @return the release_Date
	 */
	public Date getRelease_Date() {
		return release_Date;
	}
	/**
	 * @param release_Date the release_Date to set
	 */
	@XmlAttribute(name = "Release_Date")
	@XmlJavaTypeAdapter(DateAdapter.class)
	public void setRelease_Date(Date release_Date) {
		this.release_Date = release_Date;
	}
	/**
	 * @return the group_Type
	 */
	public String getGroup_Type() {
		return group_Type;
	}
	/**
	 * @param group_Type the group_Type to set
	 */
	@XmlAttribute(name = "Group_Type")
	public void setGroup_Type(String group_Type) {
		this.group_Type = group_Type;
	}
	/**
	 * @return the group_Date_Created
	 */
	public Date getGroup_Date_Created() {
		return group_Date_Created;
	}
	/**
	 * @param group_Date_Created the group_Date_Created to set
	 */
	@XmlAttribute(name = "Group_Date_Created")
	@XmlJavaTypeAdapter(DateAdapter.class)
	public void setGroup_Date_Created(Date group_Date_Created) {
		this.group_Date_Created = group_Date_Created;
	}
	/**
	 * @return the group_Date
	 */
	public Date getGroup_Date() {
		return group_Date;
	}
	/**
	 * @param group_Date the group_Date to set
	 */
	@XmlAttribute(name = "Group_Date")
	@XmlJavaTypeAdapter(DateAdapter.class)
	public void setGroup_Date(Date group_Date) {
		this.group_Date = group_Date;
	}
	/**
	 * @return the room_1_Type
	 */
	public String getRoom_1_Type() {
		return room_1_Type;
	}
	/**
	 * @param room_1_Type the room_1_Type to set
	 */
	@XmlAttribute(name = "Room_1_Type")
	public void setRoom_1_Type(String room_1_Type) {
		this.room_1_Type = room_1_Type;
	}
	/**
	 * @return the room_2_Type
	 */
	public String getRoom_2_Type() {
		return room_2_Type;
	}
	/**
	 * @param room_2_Type the room_2_Type to set
	 */
	@XmlAttribute(name = "Room_2_Type")
	public void setRoom_2_Type(String room_2_Type) {
		this.room_2_Type = room_2_Type;
	}
	/**
	 * @return the room_3_Type
	 */
	public String getRoom_3_Type() {
		return room_3_Type;
	}
	/**
	 * @param room_3_Type the room_3_Type to set
	 */
	@XmlAttribute(name = "Room_3_Type")
	public void setRoom_3_Type(String room_3_Type) {
		this.room_3_Type = room_3_Type;
	}
	/**
	 * @return the room_4_Type
	 */
	public String getRoom_4_Type() {
		return room_4_Type;
	}
	/**
	 * @param room_4_Type the room_4_Type to set
	 */
	@XmlAttribute(name = "Room_4_Type")
	public void setRoom_4_Type(String room_4_Type) {
		this.room_4_Type = room_4_Type;
	}
	/**
	 * @return the room_5_Type
	 */
	public String getRoom_5_Type() {
		return room_5_Type;
	}
	/**
	 * @param room_5_Type the room_5_Type to set
	 */
	@XmlAttribute(name = "Room_5_Type")
	public void setRoom_5_Type(String room_5_Type) {
		this.room_5_Type = room_5_Type;
	}
	/**
	 * @return the room_1_Rate
	 */	
	public BigDecimal getRoom_1_Rate() {
		return room_1_Rate;
	}
	/**
	 * @param room_1_Rate the room_1_Rate to set
	 */
	@XmlAttribute(name = "Room_1_Rate")
	public void setRoom_1_Rate(BigDecimal room_1_Rate) {
		this.room_1_Rate = room_1_Rate;
	}
	/**
	 * @return the room_2_Rate
	 */
	public BigDecimal getRoom_2_Rate() {
		return room_2_Rate;
	}
	/**
	 * @param room_2_Rate the room_2_Rate to set
	 */
	@XmlAttribute(name = "Room_2_Rate")
	public void setRoom_2_Rate(BigDecimal room_2_Rate) {
		this.room_2_Rate = room_2_Rate;
	}
	/**
	 * @return the room_3_Rate
	 */
	public BigDecimal getRoom_3_Rate() {
		return room_3_Rate;
	}
	/**
	 * @param room_3_Rate the room_3_Rate to set
	 */
	@XmlAttribute(name = "Room_3_Rate")
	public void setRoom_3_Rate(BigDecimal room_3_Rate) {
		this.room_3_Rate = room_3_Rate;
	}
	/**
	 * @return the room_4_Rate
	 */
	public BigDecimal getRoom_4_Rate() {
		return room_4_Rate;
	}
	/**
	 * @param room_4_Rate the room_4_Rate to set
	 */
	@XmlAttribute(name = "Room_4_Rate")
	public void setRoom_4_Rate(BigDecimal room_4_Rate) {
		this.room_4_Rate = room_4_Rate;
	}
	/**
	 * @return the room_5_Rate
	 */
	public BigDecimal getRoom_5_Rate() {
		return room_5_Rate;
	}
	/**
	 * @param room_5_Rate the room_5_Rate to set
	 */
	@XmlAttribute(name = "Room_5_Rate")
	public void setRoom_5_Rate(BigDecimal room_5_Rate) {
		this.room_5_Rate = room_5_Rate;
	}
	/**
	 * @return the room_1_Block
	 */
	public int getRoom_1_Block() {
		return room_1_Block;
	}
	/**
	 * @param room_1_Block the room_1_Block to set
	 */
	@XmlAttribute(name = "Room_1_Block")
	public void setRoom_1_Block(int room_1_Block) {
		this.room_1_Block = room_1_Block;
	}
	/**
	 * @return the room_2_Block
	 */
	public int getRoom_2_Block() {
		return room_2_Block;
	}
	/**
	 * @param room_2_Block the room_2_Block to set
	 */
	@XmlAttribute(name = "Room_2_Block")
	public void setRoom_2_Block(int room_2_Block) {
		this.room_2_Block = room_2_Block;
	}
	/**
	 * @return the room_3_Block
	 */
	public int getRoom_3_Block() {
		return room_3_Block;
	}
	/**
	 * @param room_3_Block the room_3_Block to set
	 */
	@XmlAttribute(name = "Room_3_Block")
	public void setRoom_3_Block(int room_3_Block) {
		this.room_3_Block = room_3_Block;
	}
	/**
	 * @return the room_4_Block
	 */
	public int getRoom_4_Block() {
		return room_4_Block;
	}
	/**
	 * @param room_4_Block the room_4_Block to set
	 */
	@XmlAttribute(name = "Room_4_Block")
	public void setRoom_4_Block(int room_4_Block) {
		this.room_4_Block = room_4_Block;
	}
	/**
	 * @return the room_5_Block
	 */
	public int getRoom_5_Block() {
		return room_5_Block;
	}
	/**
	 * @param room_5_Block the room_5_Block to set
	 */
	@XmlAttribute(name = "Room_5_Block")
	public void setRoom_5_Block(int room_5_Block) {
		this.room_5_Block = room_5_Block;
	}
	/**
	 * @return the room_Block_Total
	 */
	public int getRoom_Block_Total() {
		return room_Block_Total;
	}
	/**
	 * @param room_Block_Total the room_Block_Total to set
	 */
	@XmlAttribute(name = "Room_Block_Total")
	public void setRoom_Block_Total(int room_Block_Total) {
		this.room_Block_Total = room_Block_Total;
	}
	/**
	 * @return the group_Net
	 */
	public int getGroup_Net() {
		return group_Net;
	}
	/**
	 * @param group_Net the group_Net to set
	 */
	@XmlAttribute(name = "Group_Net")
	public void setGroup_Net(int group_Net) {
		this.group_Net = group_Net;
	}
	/**
	 * @return the actual_Nights
	 */
	public int getActual_Nights() {
		return actual_Nights;
	}
	/**
	 * @param actual_Nights the actual_Nights to set
	 */
	@XmlAttribute(name = "Actual_Nights")
	public void setActual_Nights(int actual_Nights) {
		this.actual_Nights = actual_Nights;
	}
	/**
	 * @return the actual_Dollars
	 */
	public int getActual_Dollars() {
		return actual_Dollars;
	}
	/**
	 * @param actual_Dollars the actual_Dollars to set
	 */
	@XmlAttribute(name = "Actual_Dollars")
	public void setActual_Dollars(int actual_Dollars) {
		this.actual_Dollars = actual_Dollars;
	}
	/**
	 * @return the actual_ADR
	 */
	public BigDecimal getActual_ADR() {
		return actual_ADR;
	}
	/**
	 * @param actual_ADR the actual_ADR to set
	 */
	@XmlAttribute(name = "Actual_ADR")
	public void setActual_ADR(BigDecimal actual_ADR) {
		this.actual_ADR = actual_ADR;
	}
	/**
	 * @return the days
	 */
	public Days getDays() {
		return days;
	}
	/**
	 * @param days the days to set
	 */
	@XmlElement(name = "Days", type = Days.class)
	public void setDays(Days days) {
		this.days = days;
	}
	
}
