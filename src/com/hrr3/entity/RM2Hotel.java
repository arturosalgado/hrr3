package com.hrr3.entity;

import java.io.Serializable;

/**
 * Hotel entity
 */
public class RM2Hotel implements Serializable,Cloneable {
	private static final long serialVersionUID = 1L;
	
	private Integer hotelId;
	private String name;
	private String address;
	private String city;
	private String state;
	private String country;
	private String customerId;
	private String brand;
	
	public RM2Hotel(Integer hotelId, String name, String address, String city,
			String state, String country, String customerId) {
		
		super();
		
		this.hotelId = hotelId;
		this.name = name;
		this.address = address;
		this.city = city;
		this.state = state;
		this.country = country;
		this.customerId = customerId;
		
	}
	
	public RM2Hotel() {
		
	}

	/**
	 * @return the hotelId
	 */
	public Integer getHotelId() {
		return hotelId;
	}
	
	public String getHotelIdString() {
		return String.valueOf(hotelId);
	}




	/**
	 * @param hotelId the hotelId to set
	 */
	public void setHotelId(Integer hotelId) {
		this.hotelId = hotelId;
	}




	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}




	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}




	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}




	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}




	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}




	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}




	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}




	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}




	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}




	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}




	/**
	 * @return the customerId
	 */
	public String getCustomerId() {
		return customerId;
	}




	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}


	
	

	/**
	 * @return the brand
	 */
	public String getBrand() {
		return brand;
	}

	/**
	 * @param brand the brand to set
	 */
	public void setBrand(String brand) {
		this.brand = brand;
	}

	public static RM2Hotel clone(RM2Hotel hotel){
		try {
			return (RM2Hotel)hotel.clone();
		} catch (CloneNotSupportedException e) {
			//not possible
		}
		return null;
	}



	
	@Override
	public String toString() {
		return name;
	}
}
