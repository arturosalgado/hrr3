package com.hrr3.entity;

import java.io.Serializable;

public class Segment implements Serializable, Cloneable {
	

	private String name;
	private Integer id;

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

	public Segment(String name) {
		super();
		this.name = name;
	}
	
	public Segment() {
		super();
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	public String getSegmentName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	

	
}
