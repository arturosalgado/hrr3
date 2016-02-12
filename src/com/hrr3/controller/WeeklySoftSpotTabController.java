package com.hrr3.controller;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import com.hrr3.authentication.AuthenticationServiceHRR3Impl;
import com.hrr3.entity.ssrMigration.SSRSnapshotSUWklyMtgSS;
import com.hrr3.model.WeeklyMeetingSetUpDAO;
import com.hrr3.services.AuthenticationService;


public class WeeklySoftSpotTabController extends SelectorComposer<Component> {
	
	private static final long serialVersionUID = 1L;
	
	//AuthService
	private AuthenticationService authService;
	private WeeklyMeetingSetUpDAO weeklyDAO;
	private ListModel<SSRSnapshotSUWklyMtgSS> ssModel;
	
	@Wire
	private Grid softSpotGrid;
	@Wire
	private Button updateSsRow;
	
	public WeeklySoftSpotTabController(){
		
		this.authService = new AuthenticationServiceHRR3Impl();
		this.weeklyDAO = new WeeklyMeetingSetUpDAO(authService.getUserData().getCurrentHotel());
		List<SSRSnapshotSUWklyMtgSS> ssList = weeklyDAO.fillDataSoftSpot();
		this.ssModel = new ListModelList<SSRSnapshotSUWklyMtgSS>(this.addDummyRow(ssList));
	}
		
	public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);        
       
	}
	
	@SuppressWarnings("unchecked")
	@Listen("onClick=#updateSsRow;")
	public void updateSoftSpotRow(Event event) {
		
		if(event.getData() instanceof SSRSnapshotSUWklyMtgSS) {
			SSRSnapshotSUWklyMtgSS ssRow = (SSRSnapshotSUWklyMtgSS) event.getData(); 
			
			this.weeklyDAO.saveRowSoftSpot(ssRow.getSoftspot(), ssRow.getStrategy(), ssRow.getSoftId());
			List<SSRSnapshotSUWklyMtgSS> ssList = weeklyDAO.fillDataSoftSpot();
			this.ssModel = new ListModelList<SSRSnapshotSUWklyMtgSS>(this.addDummyRow(ssList));
			this.softSpotGrid.setModel(ssModel);
		}
		
		else {
			 Messagebox.show("Error has occurred. Please notify IT support.","Soft Spot Tab", Messagebox.OK, null);
		}
	}
	
	private List<SSRSnapshotSUWklyMtgSS> addDummyRow(List<SSRSnapshotSUWklyMtgSS> ssList) {
		
		SSRSnapshotSUWklyMtgSS dummyRow = new SSRSnapshotSUWklyMtgSS();
		ssList.add(dummyRow);
		return ssList;
	}
	
	/**
	 * @return the hdModel
	 */
	public ListModel<SSRSnapshotSUWklyMtgSS> getSsModel() {
		return ssModel;
	}

	/**
	 * @param hdModel the hdModel to set
	 */
	public void setSsModel(ListModel<SSRSnapshotSUWklyMtgSS> ssModel) {
		this.ssModel = ssModel;
	}

	
}
