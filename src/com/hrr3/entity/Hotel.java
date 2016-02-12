package com.hrr3.entity;

import java.io.Serializable;

/**
 * Hotel entity
 */
public class Hotel implements Serializable,Cloneable {
	private static final long serialVersionUID = 1L;
	
	public Hotel(Integer hotelId, String name, String address, String city,
			String state, String country, String actualsVendorHotelId, String groupVendorHotelId) {
		
		super();
		
		this.hotelId = hotelId;
		this.name = name;
		this.address = address;
		this.city = city;
		this.state = state;
		this.country = country;
		this.actualsVendorHotelId = actualsVendorHotelId;
		this.groupVendorHotelId = groupVendorHotelId;
	
	}

	private Integer hotelId;
	private String name;
	private String address;
	private String city;
	private String state;
	private String country;
	private Integer brand;
	private Integer region;
	private String actualsVendorHotelId;
	private String groupVendorHotelId;
	private Integer snapshotId;
	private Integer ssrSnapshotId;
	private Integer totalRooms;
	
	public Hotel() {
		
	}
	
	public Integer getHotelId() {
		return hotelId;
	}




	public void setHotelId(Integer hotelId) {
		this.hotelId = hotelId;
	}




	public String getName() {
		return name;
	}




	public void setHotelName(String name) {
		this.name = name;
	}




	public String getAddress() {
		return address;
	}




	public void setAddress(String address) {
		this.address = address;
	}




	public String getCity() {
		return city;
	}




	public void setCity(String city) {
		this.city = city;
	}




	public String getState() {
		return state;
	}




	public void setState(String state) {
		this.state = state;
	}




	public String getCountry() {
		return country;
	}




	public void setCountry(String country) {
		this.country = country;
	}



	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the snapshotId
	 */
	public Integer getSnapshotId() {
		return snapshotId;
	}

	/**
	 * @param snapshotId the snapshotId to set
	 */
	public void setSnapshotId(Integer snapshotId) {
		this.snapshotId = snapshotId;
	}

	/**
	 * @return the actualsVendorHotelId
	 */
	public String getActualsVendorHotelId() {
		return actualsVendorHotelId;
	}

	/**
	 * @param actualsVendorHotelId the actualsVendorHotelId to set
	 */
	public void setActualsVendorHotelId(String actualsVendorHotelId) {
		this.actualsVendorHotelId = actualsVendorHotelId;
	}

	/**
	 * @return the groupVendorHotelId
	 */
	public String getGroupVendorHotelId() {
		return groupVendorHotelId;
	}
	
	public boolean getIsValidGroupVendorId(){
		
		if(this.groupVendorHotelId != null && this.groupVendorHotelId.length() == 8)
			return true;
		else
			return false;
	}
	
	public boolean getIsValidActualVendorId(){
		
		if(this.actualsVendorHotelId!= null && !this.actualsVendorHotelId.equals("N/A") && !this.actualsVendorHotelId.equals("TBD"))
			return true;
		else
			return false;
	}

	/**
	 * @param groupVendorHotelId the groupVendorHotelId to set
	 */
	public void setGroupVendorHotelId(String groupVendorHotelId) {
		this.groupVendorHotelId = groupVendorHotelId;
	}

	/**
	 * @return the brand
	 */
	public Integer getBrand() {
		return brand;
	}

	/**
	 * @param brand the brand to set
	 */
	public void setBrand(Integer brand) {
		this.brand = brand;
	}

	public static Hotel clone(Hotel hotel){
		try {
			return (Hotel)hotel.clone();
		} catch (CloneNotSupportedException e) {
			//not possible
		}
		return null;
	}



	
	/**
	 * @return the ssrSnapshotId
	 */
	public Integer getSsrSnapshotId() {
		return ssrSnapshotId;
	}

	/**
	 * @param ssrSnapshotId the ssrSnapshotId to set
	 */
	public void setSsrSnapshotId(Integer ssrSnapshotId) {
		this.ssrSnapshotId = ssrSnapshotId;
	}

	/**
	 * @return the region
	 */
	public Integer getRegion() {
		return region;
	}

	/**
	 * @param region the region to set
	 */
	public void setRegion(Integer region) {
		this.region = region;
	}

	@Override
	public String toString() {
		return name;
	}

	/**
	 * @return the totalRooms
	 */
	public Integer getTotalRooms() {
		return totalRooms;
	}

	/**
	 * @param totalRooms the totalRooms to set
	 */
	public void setTotalRooms(Integer totalRooms) {
		this.totalRooms = totalRooms;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hotelId == null) ? 0 : hotelId.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Hotel other = (Hotel) obj;
		if (hotelId == null) {
			if (other.hotelId != null)
				return false;
		} else if (!hotelId.equals(other.hotelId))
			return false;
		return true;
	}
}
