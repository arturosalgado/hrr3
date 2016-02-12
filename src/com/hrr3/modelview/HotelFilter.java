package com.hrr3.modelview;

public class HotelFilter {
	private String brand="", hotelId="", name="", state="", city="";

	/**
	 * @return the hotelId
	 */
	public String getHotelId() {
		return hotelId;
	}

	/**
	 * @param hotelId the hotelId to set
	 */
	public void setHotelId(String hotelId) {
		this.hotelId = hotelId==null?"":hotelId.trim();
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand==null?"":brand.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name==null?"":name.trim();
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state==null?"":state.trim();
	}
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city==null?"":city.trim();
	}
}
