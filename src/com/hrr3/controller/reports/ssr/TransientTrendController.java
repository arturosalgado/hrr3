package com.hrr3.controller.reports.ssr;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.hrr3.authentication.AuthenticationServiceHRR3Impl;
import com.hrr3.entity.Customer;
import com.hrr3.entity.Hotel;
import com.hrr3.services.AuthenticationService;
import com.hrr3.util.reports.JasperServerReportBuilder;
import com.hrr3.util.reports.JasperServerReportParameter;
import com.hrr3.util.reports.JasperServerReportsConfig;

public class TransientTrendController extends SelectorComposer<Component>  {
	private static final long serialVersionUID = 1L;

	private AuthenticationService authService;
	private Customer currentCustomer;
	private Hotel currenthotel;
	
	@Wire
	Datebox trendBaseFrom;
	
	@Wire
	Datebox trendBaseTo;
	
	@Wire
	Datebox trendNewFrom;
	
	@Wire
	Datebox trendNewTo;
	
	@Wire
	Datebox trendOldFrom;
	
	@Wire
	Datebox trendOldTo;
	
	@Wire
    Window popupDialog;
	
	public TransientTrendController() {
		
		authService = new AuthenticationServiceHRR3Impl();
		
		if(authService.getUserData().getCurrentHotel() == null || authService.getUserData().getCurrentHotel().getHotelId() ==null || authService.getUserData().getCurrentHotel().getHotelId() < 1)
			{Messagebox.show("Please select a valid Hotel and return to this section."); return;}
		
		currentCustomer = authService.getUserData().getCurrentCustomer();
		
		this.currenthotel = authService.getUserData().getCurrentHotel();		
        		
    }
	
	
	public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        
        popupDialog.setTitle("SSR Transient Trend Analysis Report");
	}
	

    @Listen("onClick = #exitReportBtn")
    public void showModal(Event e) {
    	popupDialog.detach();
    }
    
 
    
    @Listen("onClick = #printReportBtn")
    public void printReportBtn() {
    	
   	
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	   	    	
    	if(this.trendBaseTo.getValue().compareTo(this.trendBaseFrom.getValue()) < 0)
		{ Messagebox.show("EndDate must be >= StartDate.", "Transient Trend Report", Messagebox.OK, Messagebox.EXCLAMATION); return; }
    	
    	Integer customerId = this.currentCustomer.getCustomerId();
    	Integer hotelId = this.currenthotel.getHotelId();
    	String startDateFB = dateFormat.format(this.trendBaseFrom.getValue());
    	String endDateFB = dateFormat.format(this.trendBaseTo.getValue());
    	String startDateTN = dateFormat.format(this.trendNewFrom.getValue());
    	String endDateTN = dateFormat.format(this.trendNewTo.getValue());
    	String startDateTO = dateFormat.format(this.trendOldFrom.getValue());
    	String endDateTO = dateFormat.format(this.trendOldTo.getValue());
    	   	
    	JasperServerReportParameter p0 = new JasperServerReportParameter("customerId",customerId);
    	JasperServerReportParameter p1 = new JasperServerReportParameter("hotelId",hotelId);
    	JasperServerReportParameter p2 = new JasperServerReportParameter("startDateFB", startDateFB);
    	JasperServerReportParameter p3 = new JasperServerReportParameter("endDateFB", endDateFB);
    	JasperServerReportParameter p4 = new JasperServerReportParameter("startDateTN", startDateTN);
    	JasperServerReportParameter p5 = new JasperServerReportParameter("endDateTN", endDateTN);
    	JasperServerReportParameter p6 = new JasperServerReportParameter("startDateTO", startDateTO);
    	JasperServerReportParameter p7 = new JasperServerReportParameter("endDateTO", endDateTO);
    	
    	
    	
    	List<JasperServerReportParameter> inputReportParameters = new ArrayList<JasperServerReportParameter>();
    	
    	inputReportParameters.add(p0);
    	inputReportParameters.add(p1); 
    	inputReportParameters.add(p2);
    	inputReportParameters.add(p3);
    	inputReportParameters.add(p4); 
    	inputReportParameters.add(p5);
    	inputReportParameters.add(p6);
    	inputReportParameters.add(p7);
    	
    	new JasperServerReportBuilder().buildExecutionURL(JasperServerReportsConfig.SSR_TRANSIENT_TREND_REPORT, JasperServerReportsConfig.PDF_FORMAT, inputReportParameters, true); 	
    	
    }
    
    public void initializeFormControls(int type) {

    }


	

		
}
