package com.hrr3.entity.ssrMigration;

import java.io.Serializable;
import java.math.BigDecimal;

public class SSRSnapshotData implements Serializable, Cloneable {
	
	private static final long serialVersionUID = 1L;
	
	private	int	ssrSnapshotId;
	private	int	hotelId;
	private	String	statdate;
	private	String	comments;
	private	int	isException;
	private	String	dow;
	private	int	isActual;
	private	String	ratecat1;
	private	String	ratecat2;
	private	String	ratecat3;
	private	String	ratecat4;
	private	String	ratecat5;
	private	String	ratecat6;
	private	String	ratecat7;
	private	String	ratecat8;
	private	String	ratecat9;
	private	String	hp;
	private	String	hp2;
	private	String	oversell_factor;
	private	BigDecimal	lrr1;
	private	BigDecimal	lrr2;
	private	BigDecimal	lrr3;
	private	BigDecimal	lrr4;
	private	BigDecimal	lrr5;
	private	BigDecimal	lrr6;
	private	BigDecimal	lrr7;
	private	BigDecimal	lrr8;
	private	BigDecimal	lrr9;
	private	String	lrr;
	private	BigDecimal	lrrhp1;
	private	BigDecimal	lrrhp2;
	private	int	leadtime;
	private	String	MARrate;
	private	BigDecimal	SeasonalMARrate;
	private	String	overrides;
	private	String	createdTs;
	private	String	modifiedTs;



	public SSRSnapshotData() {
		// TODO Auto-generated constructor stub
	}



	/**
	 * @return the ssrSnapshotId
	 */
	public int getSsrSnapshotId() {
		return ssrSnapshotId;
	}



	/**
	 * @param ssrSnapshotId the ssrSnapshotId to set
	 */
	public void setSsrSnapshotId(int ssrSnapshotId) {
		this.ssrSnapshotId = ssrSnapshotId;
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
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}



	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}



	/**
	 * @return the isException
	 */
	public int getIsException() {
		return isException;
	}



	/**
	 * @param isException the isException to set
	 */
	public void setIsException(int isException) {
		this.isException = isException;
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
	 * @return the isActual
	 */
	public int getIsActual() {
		return isActual;
	}



	/**
	 * @param isActual the isActual to set
	 */
	public void setIsActual(int isActual) {
		this.isActual = isActual;
	}



	/**
	 * @return the ratecat1
	 */
	public String getRatecat1() {
		return ratecat1;
	}



	/**
	 * @param ratecat1 the ratecat1 to set
	 */
	public void setRatecat1(String ratecat1) {
		this.ratecat1 = ratecat1;
	}



	/**
	 * @return the ratecat2
	 */
	public String getRatecat2() {
		return ratecat2;
	}



	/**
	 * @param ratecat2 the ratecat2 to set
	 */
	public void setRatecat2(String ratecat2) {
		this.ratecat2 = ratecat2;
	}



	/**
	 * @return the ratecat3
	 */
	public String getRatecat3() {
		return ratecat3;
	}



	/**
	 * @param ratecat3 the ratecat3 to set
	 */
	public void setRatecat3(String ratecat3) {
		this.ratecat3 = ratecat3;
	}



	/**
	 * @return the ratecat4
	 */
	public String getRatecat4() {
		return ratecat4;
	}



	/**
	 * @param ratecat4 the ratecat4 to set
	 */
	public void setRatecat4(String ratecat4) {
		this.ratecat4 = ratecat4;
	}



	/**
	 * @return the ratecat5
	 */
	public String getRatecat5() {
		return ratecat5;
	}



	/**
	 * @param ratecat5 the ratecat5 to set
	 */
	public void setRatecat5(String ratecat5) {
		this.ratecat5 = ratecat5;
	}



	/**
	 * @return the ratecat6
	 */
	public String getRatecat6() {
		return ratecat6;
	}



	/**
	 * @param ratecat6 the ratecat6 to set
	 */
	public void setRatecat6(String ratecat6) {
		this.ratecat6 = ratecat6;
	}



	/**
	 * @return the ratecat7
	 */
	public String getRatecat7() {
		return ratecat7;
	}



	/**
	 * @param ratecat7 the ratecat7 to set
	 */
	public void setRatecat7(String ratecat7) {
		this.ratecat7 = ratecat7;
	}



	/**
	 * @return the ratecat8
	 */
	public String getRatecat8() {
		return ratecat8;
	}



	/**
	 * @param ratecat8 the ratecat8 to set
	 */
	public void setRatecat8(String ratecat8) {
		this.ratecat8 = ratecat8;
	}



	/**
	 * @return the ratecat9
	 */
	public String getRatecat9() {
		return ratecat9;
	}



	/**
	 * @param ratecat9 the ratecat9 to set
	 */
	public void setRatecat9(String ratecat9) {
		this.ratecat9 = ratecat9;
	}



	/**
	 * @return the hp
	 */
	public String getHp() {
		return hp;
	}



	/**
	 * @param hp the hp to set
	 */
	public void setHp(String hp) {
		this.hp = hp;
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
	 * @return the oversell_factor
	 */
	public String getOversell_factor() {
		return oversell_factor;
	}



	/**
	 * @param oversell_factor the oversell_factor to set
	 */
	public void setOversell_factor(String oversell_factor) {
		this.oversell_factor = oversell_factor;
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
	 * @return the lrr
	 */
	public String getLrr() {
		return lrr;
	}



	/**
	 * @param lrr the lrr to set
	 */
	public void setLrr(String lrr) {
		this.lrr = lrr;
	}



	/**
	 * @return the lrrhp1
	 */
	public BigDecimal getLrrhp1() {
		return lrrhp1;
	}



	/**
	 * @param lrrhp1 the lrrhp1 to set
	 */
	public void setLrrhp1(BigDecimal lrrhp1) {
		this.lrrhp1 = lrrhp1;
	}



	/**
	 * @return the lrrhp2
	 */
	public BigDecimal getLrrhp2() {
		return lrrhp2;
	}



	/**
	 * @param lrrhp2 the lrrhp2 to set
	 */
	public void setLrrhp2(BigDecimal lrrhp2) {
		this.lrrhp2 = lrrhp2;
	}



	/**
	 * @return the leadtime
	 */
	public int getLeadtime() {
		return leadtime;
	}



	/**
	 * @param leadtime the leadtime to set
	 */
	public void setLeadtime(int leadtime) {
		this.leadtime = leadtime;
	}



	/**
	 * @return the mARrate
	 */
	public String getMARrate() {
		return MARrate;
	}



	/**
	 * @param mARrate the mARrate to set
	 */
	public void setMARrate(String mARrate) {
		MARrate = mARrate;
	}



	/**
	 * @return the seasonalMARrate
	 */
	public BigDecimal getSeasonalMARrate() {
		return SeasonalMARrate;
	}



	/**
	 * @param seasonalMARrate the seasonalMARrate to set
	 */
	public void setSeasonalMARrate(BigDecimal seasonalMARrate) {
		SeasonalMARrate = seasonalMARrate;
	}



	/**
	 * @return the overrides
	 */
	public String getOverrides() {
		return overrides;
	}



	/**
	 * @param overrides the overrides to set
	 */
	public void setOverrides(String overrides) {
		this.overrides = overrides;
	}



	/**
	 * @return the createdTs
	 */
	public String getCreatedTs() {
		return createdTs;
	}



	/**
	 * @param createdTs the createdTs to set
	 */
	public void setCreatedTs(String createdTs) {
		this.createdTs = createdTs;
	}



	/**
	 * @return the modifiedTs
	 */
	public String getModifiedTs() {
		return modifiedTs;
	}



	/**
	 * @param modifiedTs the modifiedTs to set
	 */
	public void setModifiedTs(String modifiedTs) {
		this.modifiedTs = modifiedTs;
	}

}
