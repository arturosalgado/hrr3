package com.hrr3.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
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
import org.zkoss.zul.Window;

import com.hrr3.authentication.AuthenticationServiceHRR3Impl;
import com.hrr3.entity.Customer;
import com.hrr3.entity.Hotel;
import com.hrr3.entity.trendTool.TrendToolLists;
import com.hrr3.model.TrendToolDAO;
import com.hrr3.services.AuthenticationService;

public class PasteAdvancedController extends SelectorComposer<Component>  {
	private static final long serialVersionUID = 1L;
	
	protected Component mainApp;
	@Wire
    Window pasteAdvancedDialog;
	@Wire
	private Datebox	fromPasteDate;
	@Wire
	private Datebox	toPasteDate;
	
	//Buttons
	@Wire
	private Button pasteModelWeekSubmit;
	@Wire
	private Button applyTrendSubmit;
	@Wire
	private Button cancelarSubmit;
	
	private AuthenticationService authService;
	private TrendToolDAO trendDAO;
	
	private Customer currentCustomer;
	private Hotel currentHotel;
	private TrendToolLists currentTrendToolData;
	
	public PasteAdvancedController() {
		
		authService = new AuthenticationServiceHRR3Impl();
		
		if(authService.getUserData().getCurrentHotel() == null || authService.getUserData().getCurrentHotel().getHotelId() ==null || authService.getUserData().getCurrentHotel().getHotelId() < 1)
		{Messagebox.show("Please select a valid Hotel and return to this section."); return;}
					
		currentCustomer = authService.getUserData().getCurrentCustomer();		
		currentHotel = authService.getUserData().getCurrentHotel();	      
		this.trendDAO = new TrendToolDAO(currentHotel);
		
		final Execution execution = Executions.getCurrent();
		this.currentTrendToolData = (TrendToolLists) execution.getArg().get("currentTrendToolData");
    }	
	
	public void doAfterCompose(Component comp) throws Exception {		
		super.doAfterCompose(comp);
		
		Date baseDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(baseDate);
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);		
		Date startDate = calendar.getTime();
		
		this.fromPasteDate.setValue(startDate);	
		
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		Date endDate = calendar.getTime();
		
		this.toPasteDate.setValue(endDate);
	}
	
	@SuppressWarnings("unchecked")
	@Listen("onClick = #pasteModelWeekSubmit")
    public void pasteModelWeekSubmit(Event e) {
		
		mainApp = this.pasteAdvancedDialog;		 	
		Clients.showBusy(mainApp,"Executing Advanced Paste, please wait...");
		mainApp.addEventListener(Events.ON_CLIENT_INFO, new EventListener<Event>() {
	        @Override
	        public void onEvent(Event event) throws Exception {
	        	System.out.println("*********** PASTE MODEL WEEK - BEGIN ***************");
	        	DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
	        	trendDAO.advancedPasting(currentTrendToolData.getLstAdvancedPasting(), currentTrendToolData.getLstModelWeek(), sdf.format(fromPasteDate.getValue()), sdf.format(toPasteDate.getValue()), "ModelWeek");
	        	trendDAO.reCalculateAll(sdf.format(fromPasteDate.getValue()), sdf.format(toPasteDate.getValue()), currentCustomer.getCustomerId());
	        	Messagebox.show("Paste Model Week was succesfully executed","Advanced Paste", Messagebox.OK, null);
	        	System.out.println("*********** PASTE MODEL WEEK - END ***************");
	            Clients.clearBusy(mainApp);		
	            mainApp.removeEventListener(Events.ON_CLIENT_INFO, this);
	        }
	    });
		
		Events.echoEvent(Events.ON_CLIENT_INFO, mainApp, null);	
    }
	
	@SuppressWarnings("unchecked")
	@Listen("onClick = #applyTrendSubmit")
    public void applyTrendSubmit(Event e) {
		
		mainApp = this.pasteAdvancedDialog;		 	
		Clients.showBusy(mainApp,"Executing Advanced Paste, please wait...");
		mainApp.addEventListener(Events.ON_CLIENT_INFO, new EventListener<Event>() {
	        @Override
	        public void onEvent(Event event) throws Exception {
	        	System.out.println("*********** APPLY TREND - BEGIN ***************");
	        	DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   	
	    		trendDAO.advancedPasting(currentTrendToolData.getLstAdvancedPasting(), currentTrendToolData.getLstForecastTrend(), sdf.format(fromPasteDate.getValue()), sdf.format(toPasteDate.getValue()), "ForecastTrend");
	    		trendDAO.reCalculateAll(sdf.format(fromPasteDate.getValue()), sdf.format(toPasteDate.getValue()), currentCustomer.getCustomerId());
	    		Messagebox.show("Apply Trend was succesfully executed","Advanced Paste", Messagebox.OK, null);
	        	System.out.println("*********** APPLY TREND - END ***************");
	            Clients.clearBusy(mainApp);		
	            mainApp.removeEventListener(Events.ON_CLIENT_INFO, this);
	        }
	    });
		
		Events.echoEvent(Events.ON_CLIENT_INFO, mainApp, null);				
    }
	
    @Listen("onClick = #cancelarSubmit")
    public void showModal(Event e) {
    	pasteAdvancedDialog.detach();
    }
    
    

}
