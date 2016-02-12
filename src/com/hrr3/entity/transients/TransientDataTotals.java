package com.hrr3.entity.transients;

import java.io.Serializable;

public class TransientDataTotals implements Serializable, Cloneable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private TransientDataTotal transientTotal;
	private TransientDataTotal groupTotal;
	private TransientDataTotal contractTotal;
	private TransientDataTotal miscTotal;
	private TransientDataTotal hotelTotal;
	private TransientDataTotal paidRooms;

	
	/**
	 * @return the hotelTotal
	 */
	public TransientDataTotal getHotelTotal() {
		return hotelTotal;
	}
	/**
	 * @return the transientTotal
	 */
	public TransientDataTotal getTransientTotal() {
		return transientTotal;
	}
	/**
	 * @param transientTotal the transientTotal to set
	 */
	public void setTransientTotal(TransientDataTotal transientTotal) {
		this.transientTotal = transientTotal;
	}
	/**
	 * @return the groupTotal
	 */
	public TransientDataTotal getGroupTotal() {
		return groupTotal;
	}
	/**
	 * @param groupTotal the groupTotal to set
	 */
	public void setGroupTotal(TransientDataTotal groupTotal) {
		this.groupTotal = groupTotal;
	}
	/**
	 * @return the contractTotal
	 */
	public TransientDataTotal getContractTotal() {
		return contractTotal;
	}
	/**
	 * @param contractTotal the contractTotal to set
	 */
	public void setContractTotal(TransientDataTotal contractTotal) {
		this.contractTotal = contractTotal;
	}
	/**
	 * @return the miscTotal
	 */
	public TransientDataTotal getMiscTotal() {
		return miscTotal;
	}
	/**
	 * @param miscTotal the miscTotal to set
	 */
	public void setMiscTotal(TransientDataTotal miscTotal) {
		this.miscTotal = miscTotal;
	}
	
	//TODO Implement logic for summing all 4 totals
	/**
	 * @return the paidRooms
	 */
	public TransientDataTotal getPaidRooms() {
		return paidRooms;
	}
	/**
	 * @param paidRooms the paidRooms to set
	 */
	public void setPaidRooms(TransientDataTotal paidRooms) {
		this.paidRooms = paidRooms;
	}
	/**
	 * @param hotelTotal the hotelTotal to set
	 */
	public void setHotelTotal(TransientDataTotal hotelTotal) {
		this.hotelTotal = hotelTotal;
	}
	

}
