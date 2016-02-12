package com.hrr3.entity;

public class Region {
	
	private Integer id;
	private String name;
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
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
	
	public String getNameToDisplay () {		
		
		
		if(this.name.equals("SELECT_ELEMENT")) {
			
			return "-- Select Region --";
		}
		
		
		
		else {
			
			return this.name;
			
		}	
		
	}	

}
