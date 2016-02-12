package com.hrr3.entity;

import java.io.Serializable;

public class Snapshot implements Serializable, Cloneable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int snapshotId;
	private int hotelId;
	private String name;
	private int year;
	private int month;
	private int budget;
	private int forecast;
	private int currentForecast;
	private int quarterlyForecast;
	private String createdTS;
	private String restoreTS;
	private boolean isMigrated;
	private String migratedTS;
	/**
	 * @return the snapshotId
	 */
	public int getSnapshotId() {
		return snapshotId;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + snapshotId;
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
		Snapshot other = (Snapshot) obj;
		if (snapshotId != other.snapshotId)
			return false;
		return true;
	}
	/**
	 * @param snapshotId the snapshotId to set
	 */
	public void setSnapshotId(int snapshotId) {
		this.snapshotId = snapshotId;
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
	 * @return the year
	 */
	public int getYear() {
		return year;
	}
	/**
	 * @param year the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}
	/**
	 * @return the month
	 */
	public int getMonth() {
		return month;
	}
	/**
	 * @param month the month to set
	 */
	public void setMonth(int month) {
		this.month = month;
	}
	/**
	 * @return the budget
	 */
	public int getBudget() {
		return budget;
	}
	/**
	 * @param budget the budget to set
	 */
	public void setBudget(int budget) {
		this.budget = budget;
	}
	/**
	 * @return the forecast
	 */
	public int getForecast() {
		return forecast;
	}
	/**
	 * @param forecast the forecast to set
	 */
	public void setForecast(int forecast) {
		this.forecast = forecast;
	}
	/**
	 * @return the currentForecast
	 */
	public int getCurrentForecast() {
		return currentForecast;
	}
	/**
	 * @param currentForecast the currentForecast to set
	 */
	public void setCurrentForecast(int currentForecast) {
		this.currentForecast = currentForecast;
	}
	/**
	 * @return the quarterlyForecast
	 */
	public int getQuarterlyForecast() {
		return quarterlyForecast;
	}
	/**
	 * @param quarterlyForecast the quarterlyForecast to set
	 */
	public void setQuarterlyForecast(int quarterlyForecast) {
		this.quarterlyForecast = quarterlyForecast;
	}
	/**
	 * @return the createdTS
	 */
	public String getCreatedTS() {
		return createdTS;
	}
	/**
	 * @param createdTS the createdTS to set
	 */
	public void setCreatedTS(String createdTS) {
		this.createdTS = createdTS;
	}
	
	/**
	 * @return the restoreTS
	 */
	public String getRestoreTS() {
		return restoreTS;
	}
	/**
	 * @param restoreTS the restoreTS to set
	 */
	public void setRestoreTS(String restoreTS) {
		this.restoreTS = restoreTS;
	}
	
	/**
	 * @return the isMigrated
	 */
	public boolean isMigrated() {
		return isMigrated;
	}
	/**
	 * @param isMigrated the isMigrated to set
	 */
	public void setMigrated(boolean isMigrated) {
		this.isMigrated = isMigrated;
	}
	/**
	 * @return the migratedTS
	 */
	public String getMigratedTS() {
		return migratedTS;
	}
	/**
	 * @param migratedTS the migratedTS to set
	 */
	public void setMigratedTS(String migratedTS) {
		this.migratedTS = migratedTS;
	}
	
	public String getIsMigrated() {
		
		return this.isMigrated == true ? "Y" : "N";
	}
	
	public String getNameToDisplay () {
		
		if(this.name.equals("ADD_SNAPSHOT")) {
			
			return "-- Add New Snapshot for Hotel --";
		}
		
		if(this.name.equals("REPORT_SNAPSHOT")) {
			
			return "-- Select a Snapshot --";
		}
		
		if(this.name.equals("REPORT_SNAPSHOT_B")) {
			
			return "-- Select Base Snapshot --";
		}
		
		if(this.name.equals("REPORT_SNAPSHOT_1")) {
			
			return "-- Select Snapshot 1 --";
		}
		
		if(this.name.equals("REPORT_SNAPSHOT_2")) {
			
			return "-- Select Snapshot 2 --";
		}

		
		else {
			
			String forecastType = this.budget == 1 ? "[BUDGET] - " : "";
			forecastType+= this.forecast == 1 ? "[UF] - " : "";
			forecastType+= this.quarterlyForecast == 1 ? "[QF] - " : "";
			forecastType+= this.currentForecast == 1 ? "[CF] - " : "";
			
			return this.createdTS + " - " + this.year + " - " + forecastType + this.name;
			
		}	
		
	}
	
	//Used for Pace Report
	public String getNameToDisplayMinimal () {
			
			
			if(this.name.equals("REPORT_SNAPSHOT")) {
				
				return "-- Select a Snapshot --";
			}
			
			
			else {
				
				String nameToDisplay = this.year + " - " + this.name;  
				return nameToDisplay;
			}	
			
		}
	
	
}
