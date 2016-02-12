package com.hrr3.controller;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Window;

import com.hrr3.authentication.AuthenticationServiceHRR3Impl;
import com.hrr3.entity.Customer;
import com.hrr3.entity.Hotel;
import com.hrr3.entity.SSRSnapshot;
import com.hrr3.entity.Snapshot;
import com.hrr3.model.HotelDAO;
import com.hrr3.model.SSRSnapshotDAO;
import com.hrr3.model.SnapshotDAO;
import com.hrr3.services.AuthenticationService;
import com.hrr3.util.reports.JasperServerReportBuilder;
import com.hrr3.util.reports.JasperServerReportParameter;
import com.hrr3.util.reports.JasperServerReportsConfig;

public class DataStatusController extends SelectorComposer<Component>  {
	private static final long serialVersionUID = 1L;
		
	private AuthenticationService authService;
	
	private ListModel<Hotel> hotelsModel;
		
	@Wire
	Combobox hotelsCombo;
	
	@Wire
	Radio actualRadioBtn;
		
	@Wire
	Datebox dateFrom;
	
	@Wire
	Datebox dateTo;
	
	@Wire
    Window popupDialog;
	
	public DataStatusController() {
		
		authService = new AuthenticationServiceHRR3Impl();		
		List<Hotel> hotelList = authService.getUserData().getHotels();  
		hotelsModel = new ListModelList<Hotel>(hotelList);
        
    }
	
	
	public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        
        popupDialog.setTitle("Change Data Status");
	}
	

    @Listen("onClick = #exitBtn")
    public void showModal(Event e) {
    	popupDialog.detach();
    }
    
    @Listen("onClick = #changeStatusBtn")
    public void changeStatusBtn() {    	
   	
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	   	    	
    	if(this.dateTo.getValue().compareTo(this.dateFrom.getValue()) < 0)
		{ Messagebox.show("EndDate must be >= StartDate.", "Data Status", Messagebox.OK, Messagebox.EXCLAMATION); return; }
    	    	    	
    	int hotelId = this.hotelsCombo.getSelectedItem().getValue();
    	String startDate = dateFormat.format(this.dateFrom.getValue());
    	String endDate = dateFormat.format(this.dateTo.getValue());
    	int isActual = actualRadioBtn.isChecked() ? 1 : 0;
    	
    	HotelDAO hotelDAO = new HotelDAO();
    	hotelDAO.changeDataStatus(hotelId, startDate, endDate, isActual);
    	Messagebox.show("Data Status successfully changed.", "Data Status", Messagebox.OK, Messagebox.INFORMATION);
    }
    
    public void initializeFormControls(int type) {

    }


	/**
	 * @return the hotelsModel
	 */
	public ListModel<Hotel> getHotelsModel() {
		return hotelsModel;
	}
	
}
