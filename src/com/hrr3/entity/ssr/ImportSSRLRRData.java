package com.hrr3.entity.ssr;

import java.io.Serializable;
import java.math.BigDecimal;

public class ImportSSRLRRData implements Serializable, Cloneable{
	
	private static final long serialVersionUID = 1L;
	
	private String statdate;
	private BigDecimal lrr1;
	private BigDecimal lrr2;
	private BigDecimal lrr3;
	private BigDecimal lrr4;
	private BigDecimal lrr5;
	private BigDecimal lrr6;
	private BigDecimal lrr7;
	private BigDecimal lrr8;
	private BigDecimal lrr9;
	private BigDecimal lrrHp1;
	private BigDecimal lrrHp2;
	
	
	public ImportSSRLRRData() {
		// TODO Auto-generated constructor stub
		this.lrr1 = new BigDecimal(0);
		this.lrr2 = new BigDecimal(0);
		this.lrr3 = new BigDecimal(0);
		this.lrr4 = new BigDecimal(0);
		this.lrr5 = new BigDecimal(0);
		this.lrr6 = new BigDecimal(0);
		this.lrr7 = new BigDecimal(0);
		this.lrr8 = new BigDecimal(0);
		this.lrr9 = new BigDecimal(0);
		this.lrrHp1 = new BigDecimal(0);
		this.lrrHp2 = new BigDecimal(0);
		
	}


	/**
	 * @return the statdate
	 */
	public String getStatdate() {
		return statdate;
	}


	/**
	 * @param statdate the statdate to set
	 */
	public void setStatdate(String statdate) {
		this.statdate = statdate;
	}


	/**
	 * @return the lrr1
	 */
	public BigDecimal getLrr1() {
		return lrr1;
	}


	/**
	 * @param lrr1 the lrr1 to set
	 */
	public void setLrr1(BigDecimal lrr1) {
		this.lrr1 = lrr1;
	}


	/**
	 * @return the lrr2
	 */
	public BigDecimal getLrr2() {
		return lrr2;
	}


	/**
	 * @param lrr2 the lrr2 to set
	 */
	public void setLrr2(BigDecimal lrr2) {
		this.lrr2 = lrr2;
	}


	/**
	 * @return the lrr3
	 */
	public BigDecimal getLrr3() {
		return lrr3;
	}


	/**
	 * @param lrr3 the lrr3 to set
	 */
	public void setLrr3(BigDecimal lrr3) {
		this.lrr3 = lrr3;
	}


	/**
	 * @return the lrr4
	 */
	public BigDecimal getLrr4() {
		return lrr4;
	}


	/**
	 * @param lrr4 the lrr4 to set
	 */
	public void setLrr4(BigDecimal lrr4) {
		this.lrr4 = lrr4;
	}


	/**
	 * @return the lrr5
	 */
	public BigDecimal getLrr5() {
		return lrr5;
	}


	/**
	 * @param lrr5 the lrr5 to set
	 */
	public void setLrr5(BigDecimal lrr5) {
		this.lrr5 = lrr5;
	}


	/**
	 * @return the lrr6
	 */
	public BigDecimal getLrr6() {
		return lrr6;
	}


	/**
	 * @param lrr6 the lrr6 to set
	 */
	public void setLrr6(BigDecimal lrr6) {
		this.lrr6 = lrr6;
	}


	/**
	 * @return the lrr7
	 */
	public BigDecimal getLrr7() {
		return lrr7;
	}


	/**
	 * @param lrr7 the lrr7 to set
	 */
	public void setLrr7(BigDecimal lrr7) {
		this.lrr7 = lrr7;
	}


	/**
	 * @return the lrr8
	 */
	public BigDecimal getLrr8() {
		return lrr8;
	}


	/**
	 * @param lrr8 the lrr8 to set
	 */
	public void setLrr8(BigDecimal lrr8) {
		this.lrr8 = lrr8;
	}


	/**
	 * @return the lrr9
	 */
	public BigDecimal getLrr9() {
		return lrr9;
	}


	/**
	 * @param lrr9 the lrr9 to set
	 */
	public void setLrr9(BigDecimal lrr9) {
		this.lrr9 = lrr9;
	}


	/**
	 * @return the lrrHp1
	 */
	public BigDecimal getLrrHp1() {
		return lrrHp1;
	}


	/**
	 * @param lrrHp1 the lrrHp1 to set
	 */
	public void setLrrHp1(BigDecimal lrrHp1) {
		this.lrrHp1 = lrrHp1;
	}


	/**
	 * @return the lrrHp2
	 */
	public BigDecimal getLrrHp2() {
		return lrrHp2;
	}


	/**
	 * @param lrrHp2 the lrrHp2 to set
	 */
	public void setLrrHp2(BigDecimal lrrHp2) {
		this.lrrHp2 = lrrHp2;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ImportSSRLRRData [statdate=" + statdate + ", lrr1=" + lrr1
				+ ", lrr2=" + lrr2 + ", lrr3=" + lrr3 + ", lrr4=" + lrr4
				+ ", lrr5=" + lrr5 + ", lrr6=" + lrr6 + ", lrr7=" + lrr7
				+ ", lrr8=" + lrr8 + ", lrr9=" + lrr9 + ", lrrHp1=" + lrrHp1
				+ ", lrrHp2=" + lrrHp2 + "]";
	}

}
