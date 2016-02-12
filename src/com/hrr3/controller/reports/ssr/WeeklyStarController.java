package com.hrr3.controller.reports.ssr;


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
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.hrr3.authentication.AuthenticationServiceHRR3Impl;
import com.hrr3.entity.Customer;
import com.hrr3.entity.Hotel;
import com.hrr3.services.AuthenticationService;
import com.hrr3.util.reports.JasperServerReportBuilder;
import com.hrr3.util.reports.JasperServerReportParameter;
import com.hrr3.util.reports.JasperServerReportsConfig;

public class WeeklyStarController extends SelectorComposer<Component>  {
	private static final long serialVersionUID = 1L;
	
	private AuthenticationService authService;
	
	@Wire
    Window popupDialog;
	
	@Wire
	Checkbox addCharToReport;
	
	@Wire
	Intbox maxAxisValueBox;
	
	
	private Hotel currentHotel;
	private Customer currentCustomer;
	
	public WeeklyStarController() {
		
		authService = new AuthenticationServiceHRR3Impl();
		
		if(authService.getUserData().getCurrentHotel() == null || authService.getUserData().getCurrentHotel().getHotelId() ==null || authService.getUserData().getCurrentHotel().getHotelId() < 1)
			{Messagebox.show("Please select a valid Hotel and return to this section."); return;}		
		
		currentHotel = authService.getUserData().getCurrentHotel();	
		currentCustomer = authService.getUserData().getCurrentCustomer();	
      
    }
	
	
	public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        
        popupDialog.setTitle("Weekly Star Report");
	}
	

    @Listen("onClick = #exitReportBtn")
    public void showModal(Event e) {
    	popupDialog.detach();
    }
    
    @Listen("onClick = #printReportBtn")
    public void printReportBtn() {
    
    	int maxAxisValue = this.maxAxisValueBox.getValue();
    	
    	JasperServerReportParameter p1 = new JasperServerReportParameter("p_id_hotel",this.currentHotel.getHotelId());
    	JasperServerReportParameter p2 = new JasperServerReportParameter("p_fg_chart", addCharToReport.isChecked() == true ? "1" : "0");
    	JasperServerReportParameter p3 = new JasperServerReportParameter("p_no_max_axis_value", maxAxisValue);
    	    	
    	List<JasperServerReportParameter> inputReportParameters = new ArrayList<JasperServerReportParameter>();
    	
    	inputReportParameters.add(p1);
    	inputReportParameters.add(p2);
    	inputReportParameters.add(p3);
    	
    	new JasperServerReportBuilder().buildExecutionURLWithDiffReportID(JasperServerReportsConfig.SSR_WEEKLY_STAR, "SSRWeeklyStarReport", JasperServerReportsConfig.PDF_FORMAT, inputReportParameters, true); 	
    	
    }
    
    public void initializeFormControls(int type) {

    }
}
