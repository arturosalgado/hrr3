package com.hrr3.entity.ssr;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class ImportSSRData {

	/**
	 * 
	 */
	
	String comment;
	String dow;
	String statdate;
	BigDecimal ssrTransient;
	BigDecimal ssrGrpblock;
	BigDecimal ssrGrppu;
	BigDecimal ssrGrprem;
	BigDecimal ssrContract;
	BigDecimal ssrDemandtd;
	BigDecimal ssrPricetd;
	String a1;
	String b2;
	String c3;
	String d4;
	String e5;
	String f6;
	String g7;
	String h8;
	String i9;
	String hp1;
	String hp2;
	
	//Useful info for render to mantain
		private String startDate;
		private String endDate;
	
	public ImportSSRData (){
		
	}
	
	public ImportSSRData (String comment, String dow, String statdate, BigDecimal ssrTransient, BigDecimal ssrGrpblock,	BigDecimal ssrGrppu,
							BigDecimal ssrGrprem, BigDecimal ssrContract, BigDecimal ssrDemandtd, BigDecimal ssrPricetd,String a1, String b2,
							String c3, String d4, String e5, String f6, String g7, String h8, String i9, String hp1, String hp2){
		
		this.comment = comment;
		this.dow = dow;
		this.statdate = statdate;
		this.ssrTransient = ssrTransient;
		this.ssrGrpblock = ssrGrpblock;
		this.ssrGrppu = ssrGrppu;
		this.ssrGrprem = ssrGrprem;
		this.ssrContract = ssrContract;
		this.ssrDemandtd = ssrDemandtd;
		this.ssrPricetd = ssrPricetd;
		this.a1 = a1;
		this.b2= b2;
		this.c3 = c3;
		this.d4 = d4;
		this.e5 = e5;
		this.f6 = f6;
		this.g7 = g7;
		this.h8 = h8;
		this.i9 = i9;
		this.hp1 = hp1;
		this.hp2 = hp2;
		
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return the dow
	 */
	public String getDow() {
		return dow;
	}

	/**
	 * @param dow the dow to set
	 */
	public void setDow(String dow) {
		this.dow = dow;
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
	 * @return the ssrTransient
	 */
	public BigDecimal getSsrTransient() {
		return ssrTransient;
	}
	public String getSsrTransientS() {
		DecimalFormat formatter = new DecimalFormat("###,###.##");
		return formatter.format(ssrTransient);
	}

	/**
	 * @param ssrTransient the ssrTransient to set
	 */
	public void setSsrTransient(BigDecimal ssrTransient) {
		this.ssrTransient = ssrTransient;
	}

	/**
	 * @return the ssrGrpblock
	 */
	public BigDecimal getSsrGrpblock() {
		return ssrGrpblock;
	}

	/**
	 * @param ssrGrpblock the ssrGrpblock to set
	 */
	public void setSsrGrpblock(BigDecimal ssrGrpblock) {
		this.ssrGrpblock = ssrGrpblock;
	}

	/**
	 * @return the ssrGrppu
	 */
	public BigDecimal getSsrGrppu() {
		return ssrGrppu;
	}

	/**
	 * @param ssrGrppu the ssrGrppu to set
	 */
	public void setSsrGrppu(BigDecimal ssrGrppu) {
		this.ssrGrppu = ssrGrppu;
	}

	/**
	 * @return the ssrGrprem
	 */
	public BigDecimal getSsrGrprem() {
		return ssrGrprem;
	}

	/**
	 * @param ssrGrprem the ssrGrprem to set
	 */
	public void setSsrGrprem(BigDecimal ssrGrprem) {
		this.ssrGrprem = ssrGrprem;
	}

	/**
	 * @return the ssrContract
	 */
	public BigDecimal getSsrContract() {
		return ssrContract;
	}

	/**
	 * @param ssrContract the ssrContract to set
	 */
	public void setSsrContract(BigDecimal ssrContract) {
		this.ssrContract = ssrContract;
	}

	/**
	 * @return the ssrDemandtd
	 */
	public BigDecimal getSsrDemandtd() {
		return ssrDemandtd;
	}

	/**
	 * @param ssrDemandtd the ssrDemandtd to set
	 */
	public void setSsrDemandtd(BigDecimal ssrDemandtd) {
		this.ssrDemandtd = ssrDemandtd;
	}

	/**
	 * @return the ssrPricetd
	 */
	public BigDecimal getSsrPricetd() {
		return ssrPricetd;
	}

	/**
	 * @param ssrPricetd the ssrPricetd to set
	 */
	public void setSsrPricetd(BigDecimal ssrPricetd) {
		this.ssrPricetd = ssrPricetd;
	}

	/**
	 * @return the a1
	 */
	public String getA1() {
		return a1;
	}
	
	public String getA1S() {
		 return String.format("%d",a1);
	}

	/**
	 * @param a1 the a1 to set
	 */
	public void setA1(String a1) {
		this.a1 = a1;
	}

	/**
	 * @return the b2
	 */
	public String getB2() {
		return b2;
	}

	/**
	 * @param b2 the b2 to set
	 */
	public void setB2(String b2) {
		this.b2 = b2;
	}

	/**
	 * @return the c3
	 */
	public String getC3() {
		return c3;
	}

	/**
	 * @param c3 the c3 to set
	 */
	public void setC3(String c3) {
		this.c3 = c3;
	}

	/**
	 * @return the d4
	 */
	public String getD4() {
		return d4;
	}

	/**
	 * @param d4 the d4 to set
	 */
	public void setD4(String d4) {
		this.d4 = d4;
	}

	/**
	 * @return the e5
	 */
	public String getE5() {
		return e5;
	}

	/**
	 * @param e5 the e5 to set
	 */
	public void setE5(String e5) {
		this.e5 = e5;
	}

	/**
	 * @return the f6
	 */
	public String getF6() {
		return f6;
	}

	/**
	 * @param f6 the f6 to set
	 */
	public void setF6(String f6) {
		this.f6 = f6;
	}

	/**
	 * @return the g7
	 */
	public String getG7() {
		return g7;
	}

	/**
	 * @param g7 the g7 to set
	 */
	public void setG7(String g7) {
		this.g7 = g7;
	}

	/**
	 * @return the h8
	 */
	public String getH8() {
		return h8;
	}

	/**
	 * @param h8 the h8 to set
	 */
	public void setH8(String h8) {
		this.h8 = h8;
	}

	/**
	 * @return the i9
	 */
	public String getI9() {
		return i9;
	}

	/**
	 * @param i9 the i9 to set
	 */
	public void setI9(String i9) {
		this.i9 = i9;
	}

	/**
	 * @return the hp1
	 */
	public String getHp1() {
		return hp1;
	}

	/**
	 * @param hp1 the hp1 to set
	 */
	public void setHp1(String hp1) {
		this.hp1 = hp1;
	}

	/**
	 * @return the hp2
	 */
	public String getHp2() {
		return hp2;
	}

	/**
	 * @param hp2 the hp2 to set
	 */
	public void setHp2(String hp2) {
		this.hp2 = hp2;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "ImportSSRData [comment=" + comment + ", dow=" + dow
				+ ", statdate=" + statdate + ", ssrTransient=" + ssrTransient
				+ ", ssrGrpblock=" + ssrGrpblock + ", ssrGrppu=" + ssrGrppu
				+ ", ssrGrprem=" + ssrGrprem + ", ssrContract=" + ssrContract
				+ ", ssrDemandtd=" + ssrDemandtd + ", ssrPricetd=" + ssrPricetd
				+ ", a1=" + a1 + ", b2=" + b2 + ", c3=" + c3 + ", d4=" + d4
				+ ", e5=" + e5 + ", f6=" + f6 + ", g7=" + g7 + ", h8=" + h8
				+ ", i9=" + i9 + ", hp1=" + hp1 + ", hp2=" + hp2 + "]";
	}

}
