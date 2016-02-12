package com.hrr3.entity.ssr;

import java.util.List;

import com.hrr3.entity.ssrMigration.SSRSnapshotDayStar;
import com.hrr3.entity.ssrMigration.SSRSnapshotDayStarData;
import com.hrr3.entity.ssrMigration.SSRSnapshotDayStarHotel;

public class ImportDayStarData {
	
	private SSRSnapshotDayStar glance;
	private List<SSRSnapshotDayStarHotel> response;
	private List<SSRSnapshotDayStarData> summaries; 
	/**
	 * @return the glance
	 */
	public SSRSnapshotDayStar getGlance() {
		return glance;
	}
	/**
	 * @param glance the glance to set
	 */
	public void setGlance(SSRSnapshotDayStar glance) {
		this.glance = glance;
	}
	/**
	 * @return the response
	 */
	public List<SSRSnapshotDayStarHotel> getResponse() {
		return response;
	}
	/**
	 * @param response the response to set
	 */
	public void setResponse(List<SSRSnapshotDayStarHotel> response) {
		this.response = response;
	}
	/**
	 * @return the summaries
	 */
	public List<SSRSnapshotDayStarData> getSummaries() {
		return summaries;
	}
	/**
	 * @param summaries the summaries to set
	 */
	public void setSummaries(List<SSRSnapshotDayStarData> summaries) {
		this.summaries = summaries;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ImportDayStarData [glance=" + glance + ", response=" + response
				+ ", summaries=" + summaries + "]";
	}
	
	
	

}
