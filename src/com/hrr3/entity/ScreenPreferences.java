package com.hrr3.entity;

import java.io.Serializable;

/**
 * Hotel entity
 */
public class ScreenPreferences implements Serializable,Cloneable {
	private static final long serialVersionUID = 1L;
	
	private int width;
	private String rowColor;
	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	private int height;
	/**
	 * @return the rowColor
	 */
	public String getRowColor() {
		
		if (rowColor != null && !rowColor.equals(""))
		return rowColor;
		else
			return "#CDEFFF";
	}
	/**
	 * @param rowColor the rowColor to set
	 */
	public void setRowColor(String rowColor) {
		this.rowColor = rowColor;
	}
	
}
