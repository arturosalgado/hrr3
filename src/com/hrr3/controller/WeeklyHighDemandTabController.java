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
import com.hrr3.entity.ssrMigration.SSRSnapshotSUWklyMtgHD;
import com.hrr3.model.WeeklyMeetingSetUpDAO;
import com.hrr3.services.AuthenticationService;


public class WeeklyHighDemandTabController extends SelectorComposer<Component> {
	
	private static final long serialVersionUID = 1L;
	
	//AuthService
	private AuthenticationService authService;
	private WeeklyMeetingSetUpDAO weeklyDAO;
	private ListModel<SSRSnapshotSUWklyMtgHD> hdModel;
	
	@Wire
	private Grid highDemandGrid;
	@Wire
	private Button updateHdRow;
	
	public WeeklyHighDemandTabController(){
		
		this.authService = new AuthenticationServiceHRR3Impl();
		this.weeklyDAO = new WeeklyMeetingSetUpDAO(authService.getUserData().getCurrentHotel());
		List<SSRSnapshotSUWklyMtgHD> hdList = weeklyDAO.fillDataHighDemand();
		this.hdModel = new ListModelList<SSRSnapshotSUWklyMtgHD>(this.addDummyRow(hdList));
	}
		
	public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);        
       
	}
	
	@SuppressWarnings("unchecked")
	@Listen("onClick=#updateHdRow;")
	public void updateHighDemandRow(Event event) {
		
		if(event.getData() instanceof SSRSnapshotSUWklyMtgHD) {
			SSRSnapshotSUWklyMtgHD hdRow = (SSRSnapshotSUWklyMtgHD) event.getData(); 
			
			this.weeklyDAO.saveRowHiDmd(hdRow.getPeakdate(), hdRow.getSellingRest(), hdRow.getOutcome(), hdRow.getHighId());
			List<SSRSnapshotSUWklyMtgHD> hdList = weeklyDAO.fillDataHighDemand();
			this.hdModel = new ListModelList<SSRSnapshotSUWklyMtgHD>(this.addDummyRow(hdList));
			this.highDemandGrid.setModel(hdModel);
		}
		
		else {
			 Messagebox.show("Error has occurred. Please notify IT support.","High Demand Tab", Messagebox.OK, null);
		}
	}
	
	private List<SSRSnapshotSUWklyMtgHD> addDummyRow(List<SSRSnapshotSUWklyMtgHD> hdList) {
		
		SSRSnapshotSUWklyMtgHD dummyRow = new SSRSnapshotSUWklyMtgHD();
		hdList.add(dummyRow);
		return hdList;
	}
	
	/**
	 * @return the hdModel
	 */
	public ListModel<SSRSnapshotSUWklyMtgHD> getHdModel() {
		return hdModel;
	}

	/**
	 * @param hdModel the hdModel to set
	 */
	public void setHdModel(ListModel<SSRSnapshotSUWklyMtgHD> hdModel) {
		this.hdModel = hdModel;
	}

	
}
