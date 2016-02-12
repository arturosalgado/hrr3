package com.hrr3.controller.reports.forecast;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.hrr3.authentication.AuthenticationServiceHRR3Impl;
import com.hrr3.entity.Customer;
import com.hrr3.entity.Hotel;
import com.hrr3.services.AuthenticationService;
import com.hrr3.services.ReportServiceProvider;
import com.hrr3.util.reports.JasperServerReportBuilder;
import com.hrr3.util.reports.JasperServerReportParameter;
import com.hrr3.util.reports.JasperServerReportsConfig;

public class AnnualRoomRevenueController extends SelectorComposer<Component>  {
	private static final long serialVersionUID = 1L;
	
	private AuthenticationService authService;
	
	@Wire
	Combobox yearCombo;
	@Wire
    Window popupDialog;
	
	private Hotel currentHotel;
	private Customer currentCustomer;
	
	public AnnualRoomRevenueController() {
		
		authService = new AuthenticationServiceHRR3Impl();
		
		if(authService.getUserData().getCurrentHotel() == null || authService.getUserData().getCurrentHotel().getHotelId() ==null || authService.getUserData().getCurrentHotel().getHotelId() < 1)
			{Messagebox.show("Please select a valid Hotel and return to this section."); return;}		
		
		currentHotel = authService.getUserData().getCurrentHotel();	
		currentCustomer = authService.getUserData().getCurrentCustomer();	
		
    }
	
	
	public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        
        popupDialog.setTitle("Annual Room Revenue Report");
	}
	

    @Listen("onClick = #exitReportBtn")
    public void showModal(Event e) {
    	popupDialog.detach();
    }
    
    @Listen("onClick = #printReportBtn")
    public void printReportBtn() {
    	
    	if(this.yearCombo.getSelectedIndex() == 0)
		{ Messagebox.show("Please select a year from the dropdown.", "Annual Room Revenue Report", Messagebox.OK, Messagebox.ERROR); return; }
    	
    	int reportYear = Integer.parseInt((String)this.yearCombo.getSelectedItem().getValue());
    		
    	JasperServerReportParameter p1 = new JasperServerReportParameter("customerId",this.currentCustomer.getCustomerId());
    	JasperServerReportParameter p2 = new JasperServerReportParameter("hotelId",this.currentHotel.getHotelId());
    	JasperServerReportParameter p3 = new JasperServerReportParameter("StartDate",reportYear + "-01-01");
    	JasperServerReportParameter p4 = new JasperServerReportParameter("EndDate",reportYear + "-12-31");
    	List<JasperServerReportParameter> inputReportParameters = new ArrayList<JasperServerReportParameter>();
    	
    	inputReportParameters.add(p1);
    	inputReportParameters.add(p2);
    	inputReportParameters.add(p3);
    	inputReportParameters.add(p4);
    	
    	new JasperServerReportBuilder().buildExecutionURL(JasperServerReportsConfig.ANNUAL_ROOM_REVENUE_REPORT, JasperServerReportsConfig.PDF_FORMAT, inputReportParameters, true); 	
    	
    }
    
    public void initializeFormControls(int type) {

    }
}
