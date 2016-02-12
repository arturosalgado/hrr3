package com.hrr3.entity;

import java.io.Serializable;

public class Customer implements Serializable, Cloneable {
	
	private String logo;
	private String name;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -580101449846619591L;
	public Customer(Integer id, String name, String logo) {
		
		this.customerId = id;
		this.name = name				;
		this.logo = logo;
		
	}
	
	public Customer() {}
	
	public Customer (Integer customerId) {
		
		this.customerId = customerId;
	}
	
	private Integer customerId;
	/**
	 * @return the id
	 */
	public Integer getCustomerId() {
		return customerId;
	}
	/**
	 * @param id the id to set
	 */
	public void setCustomerId(Integer id) {
		this.customerId = id;
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
	 * @return the logo
	 */
	public String getLogo() {
		return logo;
	}
	/**
	 * @param logo the logo to set
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name;
	}

	
}
