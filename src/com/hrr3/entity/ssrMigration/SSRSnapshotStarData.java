package com.hrr3.entity.ssrMigration;

import java.io.Serializable;

public class SSRSnapshotStarData implements Serializable, Cloneable {
	
	private static final long serialVersionUID = 1L;
	
	private	int	dataId;
	private	int	ssrSnapshotId;
	private	int	hotelId;
	private	String	hotelCaption;
	private	String	monthCaption;
	private	int	isSummary;
	private	String	type;
	private	int	group;
	private	int	sequence;
	private	String	tract;
	private	String	tractScale;
	private	int	starYear;
	private	String	starMonth;
	private	String	occProp;
	private	String	occPropPc;
	private	String	occCompset;
	private	String	occCompsetPc;
	private	String	occIndex;
	private	String	occIndexPc;
	private	String	arrProp;
	private	String	arrPropPc;
	private	String	arrCompset;
	private	String	arrCompsetPc;
	private	String	arrIndex;
	private	String	arrIndexPc;
	private	String	revparProp;
	private	String	revparPropPc;
	private	String	revparCompset;
	private	String	revparCompsetPc;
	private	String	revparIndex;
	private	String	revparIndexPc;
	private	String	mktshSupply;
	private	String	mktshDemand;
	private	String	mktshRev;
	private	String	createdTs;

	
	public SSRSnapshotStarData() {
		// TODO Auto-generated constructor stub
	}


	/**
	 * @return the dataId
	 */
	public int getDataId() {
		return dataId;
	}


	/**
	 * @param dataId the dataId to set
	 */
	public void setDataId(int dataId) {
		this.dataId = dataId;
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
	 * @return the hotelCaption
	 */
	public String getHotelCaption() {
		return hotelCaption;
	}


	/**
	 * @param hotelCaption the hotelCaption to set
	 */
	public void setHotelCaption(String hotelCaption) {
		this.hotelCaption = hotelCaption;
	}


	/**
	 * @return the monthCaption
	 */
	public String getMonthCaption() {
		return monthCaption;
	}


	/**
	 * @param monthCaption the monthCaption to set
	 */
	public void setMonthCaption(String monthCaption) {
		this.monthCaption = monthCaption;
	}


	/**
	 * @return the isSummary
	 */
	public int getIsSummary() {
		return isSummary;
	}


	/**
	 * @param isSummary the isSummary to set
	 */
	public void setIsSummary(int isSummary) {
		this.isSummary = isSummary;
	}


	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}


	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}


	/**
	 * @return the group
	 */
	public int getGroup() {
		return group;
	}


	/**
	 * @param group the group to set
	 */
	public void setGroup(int group) {
		this.group = group;
	}


	/**
	 * @return the sequence
	 */
	public int getSequence() {
		return sequence;
	}


	/**
	 * @param sequence the sequence to set
	 */
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}


	/**
	 * @return the tract
	 */
	public String getTract() {
		return tract;
	}


	/**
	 * @param tract the tract to set
	 */
	public void setTract(String tract) {
		this.tract = tract;
	}


	/**
	 * @return the tractScale
	 */
	public String getTractScale() {
		return tractScale;
	}


	/**
	 * @param tractScale the tractScale to set
	 */
	public void setTractScale(String tractScale) {
		this.tractScale = tractScale;
	}


	/**
	 * @return the starYear
	 */
	public int getStarYear() {
		return starYear;
	}


	/**
	 * @param starYear the starYear to set
	 */
	public void setStarYear(int starYear) {
		this.starYear = starYear;
	}


	/**
	 * @return the starMonth
	 */
	public String getStarMonth() {
		return starMonth;
	}


	/**
	 * @param starMonth the starMonth to set
	 */
	public void setStarMonth(String starMonth) {
		this.starMonth = starMonth;
	}


	/**
	 * @return the occProp
	 */
	public String getOccProp() {
		return occProp;
	}


	/**
	 * @param occProp the occProp to set
	 */
	public void setOccProp(String occProp) {
		this.occProp = occProp;
	}


	/**
	 * @return the occPropPc
	 */
	public String getOccPropPc() {
		return occPropPc;
	}


	/**
	 * @param occPropPc the occPropPc to set
	 */
	public void setOccPropPc(String occPropPc) {
		this.occPropPc = occPropPc;
	}


	/**
	 * @return the occCompset
	 */
	public String getOccCompset() {
		return occCompset;
	}


	/**
	 * @param occCompset the occCompset to set
	 */
	public void setOccCompset(String occCompset) {
		this.occCompset = occCompset;
	}


	/**
	 * @return the occCompsetPc
	 */
	public String getOccCompsetPc() {
		return occCompsetPc;
	}


	/**
	 * @param occCompsetPc the occCompsetPc to set
	 */
	public void setOccCompsetPc(String occCompsetPc) {
		this.occCompsetPc = occCompsetPc;
	}


	/**
	 * @return the occIndex
	 */
	public String getOccIndex() {
		return occIndex;
	}


	/**
	 * @param occIndex the occIndex to set
	 */
	public void setOccIndex(String occIndex) {
		this.occIndex = occIndex;
	}


	/**
	 * @return the occIndexPc
	 */
	public String getOccIndexPc() {
		return occIndexPc;
	}


	/**
	 * @param occIndexPc the occIndexPc to set
	 */
	public void setOccIndexPc(String occIndexPc) {
		this.occIndexPc = occIndexPc;
	}


	/**
	 * @return the arrProp
	 */
	public String getArrProp() {
		return arrProp;
	}


	/**
	 * @param arrProp the arrProp to set
	 */
	public void setArrProp(String arrProp) {
		this.arrProp = arrProp;
	}


	/**
	 * @return the arrPropPc
	 */
	public String getArrPropPc() {
		return arrPropPc;
	}


	/**
	 * @param arrPropPc the arrPropPc to set
	 */
	public void setArrPropPc(String arrPropPc) {
		this.arrPropPc = arrPropPc;
	}


	/**
	 * @return the arrCompset
	 */
	public String getArrCompset() {
		return arrCompset;
	}


	/**
	 * @param arrCompset the arrCompset to set
	 */
	public void setArrCompset(String arrCompset) {
		this.arrCompset = arrCompset;
	}


	/**
	 * @return the arrCompsetPc
	 */
	public String getArrCompsetPc() {
		return arrCompsetPc;
	}


	/**
	 * @param arrCompsetPc the arrCompsetPc to set
	 */
	public void setArrCompsetPc(String arrCompsetPc) {
		this.arrCompsetPc = arrCompsetPc;
	}


	/**
	 * @return the arrIndex
	 */
	public String getArrIndex() {
		return arrIndex;
	}


	/**
	 * @param arrIndex the arrIndex to set
	 */
	public void setArrIndex(String arrIndex) {
		this.arrIndex = arrIndex;
	}


	/**
	 * @return the arrIndexPc
	 */
	public String getArrIndexPc() {
		return arrIndexPc;
	}


	/**
	 * @param arrIndexPc the arrIndexPc to set
	 */
	public void setArrIndexPc(String arrIndexPc) {
		this.arrIndexPc = arrIndexPc;
	}


	/**
	 * @return the revparProp
	 */
	public String getRevparProp() {
		return revparProp;
	}


	/**
	 * @param revparProp the revparProp to set
	 */
	public void setRevparProp(String revparProp) {
		this.revparProp = revparProp;
	}


	/**
	 * @return the revparPropPc
	 */
	public String getRevparPropPc() {
		return revparPropPc;
	}


	/**
	 * @param revparPropPc the revparPropPc to set
	 */
	public void setRevparPropPc(String revparPropPc) {
		this.revparPropPc = revparPropPc;
	}


	/**
	 * @return the revparCompset
	 */
	public String getRevparCompset() {
		return revparCompset;
	}


	/**
	 * @param revparCompset the revparCompset to set
	 */
	public void setRevparCompset(String revparCompset) {
		this.revparCompset = revparCompset;
	}


	/**
	 * @return the revparCompsetPc
	 */
	public String getRevparCompsetPc() {
		return revparCompsetPc;
	}


	/**
	 * @param revparCompsetPc the revparCompsetPc to set
	 */
	public void setRevparCompsetPc(String revparCompsetPc) {
		this.revparCompsetPc = revparCompsetPc;
	}


	/**
	 * @return the revparIndex
	 */
	public String getRevparIndex() {
		return revparIndex;
	}


	/**
	 * @param revparIndex the revparIndex to set
	 */
	public void setRevparIndex(String revparIndex) {
		this.revparIndex = revparIndex;
	}


	/**
	 * @return the revparIndexPc
	 */
	public String getRevparIndexPc() {
		return revparIndexPc;
	}


	/**
	 * @param revparIndexPc the revparIndexPc to set
	 */
	public void setRevparIndexPc(String revparIndexPc) {
		this.revparIndexPc = revparIndexPc;
	}


	/**
	 * @return the mktshSupply
	 */
	public String getMktshSupply() {
		return mktshSupply;
	}


	/**
	 * @param mktshSupply the mktshSupply to set
	 */
	public void setMktshSupply(String mktshSupply) {
		this.mktshSupply = mktshSupply;
	}


	/**
	 * @return the mktshDemand
	 */
	public String getMktshDemand() {
		return mktshDemand;
	}


	/**
	 * @param mktshDemand the mktshDemand to set
	 */
	public void setMktshDemand(String mktshDemand) {
		this.mktshDemand = mktshDemand;
	}


	/**
	 * @return the mktshRev
	 */
	public String getMktshRev() {
		return mktshRev;
	}


	/**
	 * @param mktshRev the mktshRev to set
	 */
	public void setMktshRev(String mktshRev) {
		this.mktshRev = mktshRev;
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


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SSRSnapshotStarData [dataId=" + dataId + ", ssrSnapshotId="
				+ ssrSnapshotId + ", hotelId=" + hotelId + ", hotelCaption="
				+ hotelCaption + ", monthCaption=" + monthCaption
				+ ", isSummary=" + isSummary + ", type=" + type + ", group="
				+ group + ", sequence=" + sequence + ", tract=" + tract
				+ ", tractScale=" + tractScale + ", starYear=" + starYear
				+ ", starMonth=" + starMonth + ", occProp=" + occProp
				+ ", occPropPc=" + occPropPc + ", occCompset=" + occCompset
				+ ", occCompsetPc=" + occCompsetPc + ", occIndex=" + occIndex
				+ ", occIndexPc=" + occIndexPc + ", arrProp=" + arrProp
				+ ", arrPropPc=" + arrPropPc + ", arrCompset=" + arrCompset
				+ ", arrCompsetPc=" + arrCompsetPc + ", arrIndex=" + arrIndex
				+ ", arrIndexPc=" + arrIndexPc + ", revparProp=" + revparProp
				+ ", revparPropPc=" + revparPropPc + ", revparCompset="
				+ revparCompset + ", revparCompsetPc=" + revparCompsetPc
				+ ", revparIndex=" + revparIndex + ", revparIndexPc="
				+ revparIndexPc + ", mktshSupply=" + mktshSupply
				+ ", mktshDemand=" + mktshDemand + ", mktshRev=" + mktshRev
				+ ", createdTs=" + createdTs + "]";
	}

}
