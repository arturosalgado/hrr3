package com.hrr3.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Progressmeter;

import com.google.gson.Gson;
import com.hrr3.authentication.AuthenticationServiceHRR3Impl;
import com.hrr3.entity.Hotel;
import com.hrr3.entity.ImportSegment;
import com.hrr3.entity.hsp.Groups;
import com.hrr3.model.ImportSegmentDAO;
import com.hrr3.services.AuthenticationService;
import com.hrr3.services.HSPImportService;

public class HSPImportController extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;
	
	private AuthenticationService authService;
	private HSPImportService hspImportService;
	private ImportSegmentDAO importDAO;
	@Wire
	private Button hspImportButton;
	@Wire
	private Datebox hspImportFrom;
	@Wire
	private Datebox hspImportTo;
	@Wire
	private Progressmeter hspProgressBar;
	
	public Component mainApp;
	
	private Hotel currentHotel;
	private Integer userId;
	private Integer customerId;
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	public HSPImportController() {
		//Auth service
		authService = new AuthenticationServiceHRR3Impl();		
		
		if(authService.getUserData().getCurrentHotel() == null || authService.getUserData().getCurrentHotel().getHotelId() ==null || authService.getUserData().getCurrentHotel().getHotelId() < 1)
			{Messagebox.show("Please select a valid Hotel and return to this section."); currentHotel = null; return; }
		
		currentHotel = authService.getUserData().getCurrentHotel();		
		String clientId = currentHotel.getGroupVendorHotelId();
		customerId = authService.getUserData().getCurrentCustomer().getCustomerId();
		System.out.println("************************* HSPHotelId: " + clientId + " *************************");
		
		if(currentHotel.getGroupVendorHotelId().trim().equals("") || currentHotel.getGroupVendorHotelId().trim().equals("N/A"))
		{Messagebox.show("This Hotel does not contains a ClientId for HSP. Notify support team.", "HSP Import - warning", Messagebox.OK, Messagebox.EXCLAMATION); currentHotel = null; return; }
		
		userId = authService.getUserData().getUserId();
    }
	
	public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        if(currentHotel==null)
        	hspImportButton.setDisabled(true);
        
        this.hspImportFrom.setValue(new Date());
	}	
	
	@Listen("onClick = #hspImportButton")
    public void executeHSPImport() {
		
		mainApp = this.getSelf().getParent().getParent();
		
		Clients.showBusy(mainApp,"Importing HSP Data...");

		mainApp.addEventListener(Events.ON_CLIENT_INFO, new EventListener<Event>() {
	        @Override
	        public void onEvent(Event event) throws Exception {
	        	executeImport();    
	        	
	            Clients.clearBusy(mainApp);
	        }
	    });
		
		Events.echoEvent(Events.ON_CLIENT_INFO, mainApp, null);
	}
	
	public void executeImport() {
		
		//Instanciate
		hspImportService = new HSPImportService();
		importDAO = new ImportSegmentDAO();
		
		//Validate start and end Dates
		if(this.hspImportFrom.getValue().getTime() > this.hspImportTo.getValue().getTime()) {			
			Messagebox.show("EndDate must be equals or greater than StartDate", "HSP Import - warning", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		//Delete previous data in RM3Import table.
		importDAO.deleteDataRM3Import(this.currentHotel.getHotelId());
		
		int wasSuccess = importDAO.fillHSPSegmentsZero(this.dateFormat.format(hspImportFrom.getValue()), this.dateFormat.format(hspImportTo.getValue()), this.currentHotel.getHotelId(), this.userId, this.customerId);
		if(wasSuccess == 0) {
			Messagebox.show("Error while trying to fill out RM3 tables with zeros.", "HSP Import - Error", Messagebox.OK, Messagebox.ERROR);
			return;
		}	
		
		//If filling out with zeros was success, then we use the value returned as importSession in the invokeHSPImportRM3Api
		int importSession = wasSuccess;
		
		System.out.println("**************** ImportSession created: " + importSession + " **********************");
		
		//get HSP Token
		String token = hspImportService.getHSPToken(currentHotel.getGroupVendorHotelId(), HSPImportService.DEFAULT_CLIENT_SECRET);
		
		if(token==null) {
			Messagebox.show("HSP Token for this hotel is invalid. Please notify support team.", "HSP Import - warning", Messagebox.OK, Messagebox.ERROR);		
			return;
		}
		
		Groups groupData = hspImportService.getHSPData(token, dateFormat.format(this.hspImportFrom.getValue()));
		
		if(groupData==null || groupData.getGroups() == null || groupData.getGroups().size() < 1) {
			Messagebox.show("HSP Service did not provide data, but import with zeros will be applied.", "HSP Import - Information", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
					
		List<ImportSegment> segmentsToImport = hspImportService.validateGroupData(groupData.getGroups(), this.userId, this.currentHotel.getHotelId(), this.hspImportFrom.getValue(), this.hspImportTo.getValue());
		
		if(segmentsToImport==null || segmentsToImport.size() < 1) {
			Messagebox.show("HSP Segment validation has failed, but import with zeros will be applied.", "HSP Import - Information", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		
		// create a new Gson instance
		Gson gson = new Gson();
		// convert your list to json
		String importDataJson = gson.toJson(segmentsToImport);
		
		int result = hspImportService.invokeHSPImportRM3Api(importDataJson, importSession);
		
		if(result == 0)
			Messagebox.show("HSP Service did not provide valid data, but import with zeros will be applied.. Please contact support team.", "HSP Import - Error", Messagebox.OK, Messagebox.ERROR);		
		else {
			
			String returnValue = hspImportService.moveImportRM3Api(importSession,this.hspImportFrom.getValue(), this.hspImportTo.getValue());
			
			if (returnValue.equals("OK"))
				Messagebox.show("HSP Import has been successfully executed.");
			else
				Messagebox.show("HSP Import failed while applying calculations. Please contact support team.", "HSP Import - Error", Messagebox.OK, Messagebox.ERROR);			
			
		}
			
		
	}
	
		
	
}
