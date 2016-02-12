package com.hrr3.controller.reports.forecast;


import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.hrr3.authentication.AuthenticationServiceHRR3Impl;
import com.hrr3.entity.Customer;
import com.hrr3.entity.Hotel;
import com.hrr3.services.AuthenticationService;
import com.hrr3.util.reports.JasperServerReportBuilder;
import com.hrr3.util.reports.JasperServerReportParameter;
import com.hrr3.util.reports.JasperServerReportsConfig;

public class MonthEndAnalysisController extends SelectorComposer<Component>  {
	private static final long serialVersionUID = 1L;
	
	private AuthenticationService authService;
	
	@Wire
	Combobox yearCombo;
	
	@Wire
	Combobox monthCombo;
	
	@Wire
    Window popupDialog;
	
	@Wire
	Checkbox dailyMix;
	
	@Wire
	Checkbox dayWeekStat;
	
	@Wire
	Checkbox dayWeekAvg;
	
	
	private Hotel currentHotel;
	private Customer currentCustomer;
	
	public MonthEndAnalysisController() {
		
		authService = new AuthenticationServiceHRR3Impl();
		
		if(authService.getUserData().getCurrentHotel() == null || authService.getUserData().getCurrentHotel().getHotelId() ==null || authService.getUserData().getCurrentHotel().getHotelId() < 1)
			{Messagebox.show("Please select a valid Hotel and return to this section."); return;}		
		
		currentHotel = authService.getUserData().getCurrentHotel();	
		currentCustomer = authService.getUserData().getCurrentCustomer();	
      
    }
	
	
	public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        
        popupDialog.setTitle("Month End Analysis Report");
	}
	

    @Listen("onClick = #exitReportBtn")
    public void showModal(Event e) {
    	popupDialog.detach();
    }
    
    @Listen("onClick = #printReportBtn")
    public void printReportBtn() {
    	
    	if(this.yearCombo.getSelectedIndex() == 0)
		{ Messagebox.show("Please select a year from the dropdown.", "Month End Analysis Report", Messagebox.OK, Messagebox.EXCLAMATION); return; }
    	
    	if(this.monthCombo.getSelectedIndex() == 0)
		{ Messagebox.show("Please select a month from the dropdown.", "Month End Analysis Report", Messagebox.OK, Messagebox.EXCLAMATION); return; }
    	
    	if(!dailyMix.isChecked() && !dayWeekStat.isChecked() && !dayWeekAvg.isChecked()) 
    	{ Messagebox.show("Please select at least one report type.", "Month End Analysis Report", Messagebox.OK, Messagebox.EXCLAMATION); return; }
    	
    	int reportYear = Integer.parseInt((String)this.yearCombo.getSelectedItem().getValue());
    	int reportMonth = Integer.parseInt((String)this.monthCombo.getSelectedItem().getValue());
    	
    	JasperServerReportParameter p1 = new JasperServerReportParameter("p_id_customer",this.currentCustomer.getCustomerId());
    	JasperServerReportParameter p2 = new JasperServerReportParameter("p_id_hotel",this.currentHotel.getHotelId());
    	JasperServerReportParameter p3 = new JasperServerReportParameter("p_no_year", reportYear);
    	JasperServerReportParameter p4 = new JasperServerReportParameter("p_no_month", reportMonth);
    	JasperServerReportParameter p5 = new JasperServerReportParameter("p_fg_DMA", dailyMix.isChecked() == true ? "1" : "0");
    	JasperServerReportParameter p6 = new JasperServerReportParameter("p_fg_DOWS", dayWeekStat.isChecked() == true ? "1" : "0");
    	JasperServerReportParameter p7 = new JasperServerReportParameter("p_fg_DOWAS", dayWeekAvg.isChecked() == true ? "1" : "0");
    	    	
    	List<JasperServerReportParameter> inputReportParameters = new ArrayList<JasperServerReportParameter>();
    	
    	inputReportParameters.add(p1);
    	inputReportParameters.add(p2);
    	inputReportParameters.add(p3);
    	inputReportParameters.add(p4);
    	inputReportParameters.add(p5);
    	inputReportParameters.add(p6);
    	inputReportParameters.add(p7);
    	
    	new JasperServerReportBuilder().buildExecutionURL(JasperServerReportsConfig.MONTH_END_ANALYSIS_REPORT, JasperServerReportsConfig.PDF_FORMAT, inputReportParameters, true); 	
    	
    }
    
    public void initializeFormControls(int type) {

    }
}
