package com.hrr3.entity.ssrMigration;

import java.io.Serializable;
import java.math.BigDecimal;

public class SSRSnapshotSUMARrate  implements Serializable, Cloneable {
	
	private static final long serialVersionUID = 1L;
	
	private	int	ssrSnapshotId;
	private	int	hotelId;
	private	int	sun;
	private	int	mon;
	private	int	tue;
	private	int	wed;
	private	int	thu;
	private	int	fri;
	private	int	sat;
	private	BigDecimal	cpor;
	private	BigDecimal	occTfdr;
	private	String	modifiedTs;

	public SSRSnapshotSUMARrate() {
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
	 * @return the sun
	 */
	public int getSun() {
		return sun;
	}

	/**
	 * @param sun the sun to set
	 */
	public void setSun(int sun) {
		this.sun = sun;
	}

	/**
	 * @return the mon
	 */
	public int getMon() {
		return mon;
	}

	/**
	 * @param mon the mon to set
	 */
	public void setMon(int mon) {
		this.mon = mon;
	}

	/**
	 * @return the tue
	 */
	public int getTue() {
		return tue;
	}

	/**
	 * @param tue the tue to set
	 */
	public void setTue(int tue) {
		this.tue = tue;
	}

	/**
	 * @return the wed
	 */
	public int getWed() {
		return wed;
	}

	/**
	 * @param wed the wed to set
	 */
	public void setWed(int wed) {
		this.wed = wed;
	}

	/**
	 * @return the thu
	 */
	public int getThu() {
		return thu;
	}

	/**
	 * @param thu the thu to set
	 */
	public void setThu(int thu) {
		this.thu = thu;
	}

	/**
	 * @return the fri
	 */
	public int getFri() {
		return fri;
	}

	/**
	 * @param fri the fri to set
	 */
	public void setFri(int fri) {
		this.fri = fri;
	}

	/**
	 * @return the sat
	 */
	public int getSat() {
		return sat;
	}

	/**
	 * @param sat the sat to set
	 */
	public void setSat(int sat) {
		this.sat = sat;
	}

	/**
	 * @return the cpor
	 */
	public BigDecimal getCpor() {
		return cpor;
	}

	/**
	 * @param cpor the cpor to set
	 */
	public void setCpor(BigDecimal cpor) {
		this.cpor = cpor;
	}

	/**
	 * @return the occTfdr
	 */
	public BigDecimal getOccTfdr() {
		return occTfdr;
	}

	/**
	 * @param occTfdr the occTfdr to set
	 */
	public void setOccTfdr(BigDecimal occTfdr) {
		this.occTfdr = occTfdr;
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
