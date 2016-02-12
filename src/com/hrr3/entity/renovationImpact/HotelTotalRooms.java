package com.hrr3.entity.renovationImpact;

import java.io.Serializable;

	public class HotelTotalRooms implements Serializable,Cloneable {
	
		private static final long serialVersionUID = 1L;

		public HotelTotalRooms() {
			// TODO Auto-generated constructor stub
		}
		
		private int hotelId;
		private String totalRoomsDate;
		private int totalRooms;

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
		 * @return the totalRoomsDate
		 */
		public String getTotalRoomsDate() {
			return totalRoomsDate;
		}
		/**
		 * @param totalRoomsDate the totalRoomsDate to set
		 */
		public void setTotalRoomsDate(String totalRoomsDate) {
			this.totalRoomsDate = totalRoomsDate;
		}
		/**
		 * @return the totalRooms
		 */
		public int getTotalRooms() {
			return totalRooms;
		}
		/**
		 * @param totalRooms the totalRooms to set
		 */
		public void setTotalRooms(int totalRooms) {
			this.totalRooms = totalRooms;
		}

	}
