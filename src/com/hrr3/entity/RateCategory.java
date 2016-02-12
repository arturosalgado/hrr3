package com.hrr3.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RateCategory implements Serializable, Cloneable{
	
	private static final long serialVersionUID = 1L;

	private String columnNameA;
	private String columnNameB;
	private String columnNameC;
	private String columnNameD;
	private String columnNameE;
	private String columnNameF;
	private String columnNameG;
	private String columnNameH;
	private String columnNameI;
	private String columnNameHP1;
	private String columnNameHP2;
	
	private int columnAHidden;
	private int columnBHidden;
	private int columnCHidden;
	private int columnDHidden;
	private int columnEHidden;
	private int columnFHidden;
	private int columnGHidden;
	private int columnHHidden;
	private int columnIHidden;
	private int columnHP1Hidden;
	private int columnHP2Hidden;
	
	private int oversell;

  
	public RateCategory() {
		// TODO Auto-generated constructor stub
		this.setColumnNameA("A/0");
		this.setColumnNameB("B/1");
		this.setColumnNameC("C/2");
		this.setColumnNameD("D/3");
		this.setColumnNameE("E/4");
		this.setColumnNameF("F/5");
		this.setColumnNameG("G/6");
		this.setColumnNameH("H/7");
		this.setColumnNameI("I/8");
		this.setColumnNameHP1("HP1");
		this.setColumnNameHP2("HP2");
		
		//Type int default value is 0.			
	}
	
	public List<String> getVisibleColsWithAlias() {
		
		List<String> cols = new ArrayList<String>();
		
		if(columnAHidden == 0) cols.add(columnNameA);
		if(columnBHidden == 0) cols.add(columnNameB);
		if(columnCHidden == 0) cols.add(columnNameC);
		if(columnDHidden == 0) cols.add(columnNameD);
		if(columnEHidden == 0) cols.add(columnNameE);
		if(columnFHidden == 0) cols.add(columnNameF);
		if(columnGHidden == 0) cols.add(columnNameG);
		if(columnHHidden == 0) cols.add(columnNameH);
		if(columnIHidden == 0) cols.add(columnNameI);
		if(columnHP1Hidden == 0) cols.add(columnNameHP1);
		if(columnHP2Hidden == 0) cols.add(columnNameHP2);
		if(oversell == 1) cols.add("Oversell Factor");
		
		return cols;
	}
	
	/**
	 * @return the columnNameA
	 */
	public String getColumnNameA() {
		return columnNameA;
	}

	/**
	 * @param columnNameA the columnNameA to set
	 */
	public void setColumnNameA(String columnNameA) {
		this.columnNameA = columnNameA;
	}

	/**
	 * @return the columnNameB
	 */
	public String getColumnNameB() {
		return columnNameB;
	}

	/**
	 * @param columnNameB the columnNameB to set
	 */
	public void setColumnNameB(String columnNameB) {
		this.columnNameB = columnNameB;
	}

	/**
	 * @return the columnNameC
	 */
	public String getColumnNameC() {
		return columnNameC;
	}

	/**
	 * @param columnNameC the columnNameC to set
	 */
	public void setColumnNameC(String columnNameC) {
		this.columnNameC = columnNameC;
	}

	/**
	 * @return the columnNameD
	 */
	public String getColumnNameD() {
		return columnNameD;
	}

	/**
	 * @param columnNameD the columnNameD to set
	 */
	public void setColumnNameD(String columnNameD) {
		this.columnNameD = columnNameD;
	}

	/**
	 * @return the columnNameE
	 */
	public String getColumnNameE() {
		return columnNameE;
	}

	/**
	 * @param columnNameE the columnNameE to set
	 */
	public void setColumnNameE(String columnNameE) {
		this.columnNameE = columnNameE;
	}

	/**
	 * @return the columnNameF
	 */
	public String getColumnNameF() {
		return columnNameF;
	}

	/**
	 * @param columnNameF the columnNameF to set
	 */
	public void setColumnNameF(String columnNameF) {
		this.columnNameF = columnNameF;
	}

	/**
	 * @return the columnNameG
	 */
	public String getColumnNameG() {
		return columnNameG;
	}

	/**
	 * @param columnNameG the columnNameG to set
	 */
	public void setColumnNameG(String columnNameG) {
		this.columnNameG = columnNameG;
	}

	/**
	 * @return the columnNameH
	 */
	public String getColumnNameH() {
		return columnNameH;
	}

	/**
	 * @param columnNameH the columnNameH to set
	 */
	public void setColumnNameH(String columnNameH) {
		this.columnNameH = columnNameH;
	}

	/**
	 * @return the columnNameI
	 */
	public String getColumnNameI() {
		return columnNameI;
	}

	/**
	 * @param columnNameI the columnNameI to set
	 */
	public void setColumnNameI(String columnNameI) {
		this.columnNameI = columnNameI;
	}

	/**
	 * @return the columnNameHP1
	 */
	public String getColumnNameHP1() {
		return columnNameHP1;
	}

	/**
	 * @param columnNameHP1 the columnNameHP1 to set
	 */
	public void setColumnNameHP1(String columnNameHP1) {
		this.columnNameHP1 = columnNameHP1;
	}

	/**
	 * @return the columnNameHP2
	 */
	public String getColumnNameHP2() {
		return columnNameHP2;
	}

	/**
	 * @param columnNameHP2 the columnNameHP2 to set
	 */
	public void setColumnNameHP2(String columnNameHP2) {
		this.columnNameHP2 = columnNameHP2;
	}

	/**
	 * @return the columnAHidden
	 */
	public int getColumnAHidden() {
		return columnAHidden;
	}
	
	/**
	 * @return the columnAHidden
	 */
	public String getColumnAHiddenString() {
		return columnAHidden == 1 ? "true" : "false";
	}
	
	/**
	 * @return the columnAHidden
	 */
	public String getColumnBHiddenString() {
		return columnBHidden == 1 ? "true" : "false";
	}
	
	/**
	 * @return the columnAHidden
	 */
	public String getColumnCHiddenString() {
		return columnCHidden == 1 ? "true" : "false";
	}
	/**
	 * @return the columnAHidden
	 */
	public String getColumnDHiddenString() {
		return columnDHidden == 1 ? "true" : "false";
	}
	public String getColumnEHiddenString() {
		return columnEHidden == 1 ? "true" : "false";
	}

	/**
	 * @return the columnAHidden
	 */
	public String getColumnFHiddenString() {
		return columnFHidden == 1 ? "true" : "false";
	}
	/**
	 * @return the columnAHidden
	 */
	public String getColumnGHiddenString() {
		return columnGHidden == 1 ? "true" : "false";
	}
	/**
	 * @return the columnAHidden
	 */
	public String getColumnHHiddenString() {
		return columnHHidden == 1 ? "true" : "false";
	}
	/**
	 * @return the columnAHidden
	 */
	public String getColumnIHiddenString() {
		return columnIHidden == 1 ? "true" : "false";
	}
	/**
	 * @return the columnAHidden
	 */
	public String getColumnHP1HiddenString() {
		return columnHP1Hidden == 1 ? "true" : "false";
	}
	/**
	 * @return the columnAHidden
	 */
	public String getColumnHP2HiddenString() {
		return columnHP2Hidden == 1 ? "true" : "false";
	}

	/**
	 * @param columnAHidden the columnAHidden to set
	 */
	public void setColumnAHiddenString(String columnAHidden) {
		this.columnAHidden = columnAHidden.equals("true") ? 1 : 0;
	}
	public void setColumnBHiddenString(String columnBHidden) {
		this.columnBHidden = columnBHidden.equals("true") ? 1 : 0;
	}
	public void setColumnCHiddenString(String columnCHidden) {
		this.columnCHidden = columnCHidden.equals("true") ? 1 : 0;
	}
	public void setColumnDHiddenString(String columnDHidden) {
		this.columnDHidden = columnDHidden.equals("true") ? 1 : 0;
	}
	public void setColumnEHiddenString(String columnEHidden) {
		this.columnEHidden = columnEHidden.equals("true") ? 1 : 0;
	}
	public void setColumnFHiddenString(String columnFHidden) {
		this.columnFHidden = columnFHidden.equals("true") ? 1 : 0;
	}
	public void setColumnGHiddenString(String columnGHidden) {
		this.columnGHidden = columnGHidden.equals("true") ? 1 : 0;
	}
	public void setColumnHHiddenString(String columnHHidden) {
		this.columnHHidden = columnHHidden.equals("true") ? 1 : 0;
	}
	public void setColumnIHiddenString(String columnIHidden) {
		this.columnIHidden = columnIHidden.equals("true") ? 1 : 0;
	}
	public void setColumnHP1HiddenString(String columnHP1Hidden) {
		this.columnHP1Hidden = columnHP1Hidden.equals("true") ? 1 : 0;
	}
	public void setColumnHP2HiddenString(String columnHP2Hidden) {
		this.columnHP2Hidden = columnHP2Hidden.equals("true") ? 1 : 0;
	}
	/**
	 * @param columnAHidden the columnAHidden to set
	 */
	public void setColumnAHidden(int columnAHidden) {
		this.columnAHidden = columnAHidden;
	}

	/**
	 * @return the columnBHidden
	 */
	public int getColumnBHidden() {
		return columnBHidden;
	}

	/**
	 * @param columnBHidden the columnBHidden to set
	 */
	public void setColumnBHidden(int columnBHidden) {
		this.columnBHidden = columnBHidden;
	}

	/**
	 * @return the columnCHidden
	 */
	public int getColumnCHidden() {
		return columnCHidden;
	}

	/**
	 * @param columnCHidden the columnCHidden to set
	 */
	public void setColumnCHidden(int columnCHidden) {
		this.columnCHidden = columnCHidden;
	}

	/**
	 * @return the columnDHidden
	 */
	public int getColumnDHidden() {
		return columnDHidden;
	}

	/**
	 * @param columnDHidden the columnDHidden to set
	 */
	public void setColumnDHidden(int columnDHidden) {
		this.columnDHidden = columnDHidden;
	}

	/**
	 * @return the columnEHidden
	 */
	public int getColumnEHidden() {
		return columnEHidden;
	}

	/**
	 * @param columnEHidden the columnEHidden to set
	 */
	public void setColumnEHidden(int columnEHidden) {
		this.columnEHidden = columnEHidden;
	}

	/**
	 * @return the columnFHidden
	 */
	public int getColumnFHidden() {
		return columnFHidden;
	}

	/**
	 * @param columnFHidden the columnFHidden to set
	 */
	public void setColumnFHidden(int columnFHidden) {
		this.columnFHidden = columnFHidden;
	}

	/**
	 * @return the columnGHidden
	 */
	public int getColumnGHidden() {
		return columnGHidden;
	}

	/**
	 * @param columnGHidden the columnGHidden to set
	 */
	public void setColumnGHidden(int columnGHidden) {
		this.columnGHidden = columnGHidden;
	}

	/**
	 * @return the columnHHidden
	 */
	public int getColumnHHidden() {
		return columnHHidden;
	}

	/**
	 * @param columnHHidden the columnHHidden to set
	 */
	public void setColumnHHidden(int columnHHidden) {
		this.columnHHidden = columnHHidden;
	}

	/**
	 * @return the columnIHidden
	 */
	public int getColumnIHidden() {
		return columnIHidden;
	}

	/**
	 * @param columnIHidden the columnIHidden to set
	 */
	public void setColumnIHidden(int columnIHidden) {
		this.columnIHidden = columnIHidden;
	}

	/**
	 * @return the columnHP1Hidden
	 */
	public int getColumnHP1Hidden() {
		return columnHP1Hidden;
	}

	/**
	 * @param columnHP1Hidden the columnHP1Hidden to set
	 */
	public void setColumnHP1Hidden(int columnHP1Hidden) {
		this.columnHP1Hidden = columnHP1Hidden;
	}

	/**
	 * @return the columnHP2Hidden
	 */
	public int getColumnHP2Hidden() {
		return columnHP2Hidden;
	}

	/**
	 * @param columnHP2Hidden the columnHP2Hidden to set
	 */
	public void setColumnHP2Hidden(int columnHP2Hidden) {
		this.columnHP2Hidden = columnHP2Hidden;
	}

	/**
	 * @return the oversell
	 */
	public int getOversell() {
		return oversell;
	}
	
	/**
	 * @return the oversell
	 */
	public String getOversellString() {
		return oversell ==1 ? "true" : "false";
	}

	/**
	 * @param oversell the oversell to set
	 */
	public void setOversell(int oversell) {
		this.oversell = oversell;
	}
	
	/**
	 * @param oversell the oversell to set
	 */
	public void setOversellString(String oversell) {
		this.oversell = oversell.equals("true") ? 1 : 0;
	}
}
