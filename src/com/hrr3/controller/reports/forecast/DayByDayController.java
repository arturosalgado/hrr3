package com.hrr3.controller.reports.forecast;


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
import org.zkoss.zul.Window;

import com.hrr3.authentication.AuthenticationServiceHRR3Impl;
import com.hrr3.entity.Customer;
import com.hrr3.entity.Hotel;
import com.hrr3.entity.Snapshot;
import com.hrr3.model.SnapshotDAO;
import com.hrr3.services.AuthenticationService;
import com.hrr3.util.reports.JasperServerReportBuilder;
import com.hrr3.util.reports.JasperServerReportParameter;
import com.hrr3.util.reports.JasperServerReportsConfig;

public class DayByDayController extends SelectorComposer<Component>  {
	private static final long serialVersionUID = 1L;
	
	private ListModel<Snapshot> snapshotsUpdateForecastModel;	
	private ListModel<Snapshot> snapshotsCurrentForecastModel;	
	private ListModel<Snapshot> snapshotsBudgetModel;	
	private ListModel<Snapshot> snapshotsLastYearModel;	
	
	private AuthenticationService authService;
	private SnapshotDAO snapshotDAO;
	private Customer currentCustomer;
	
	@Wire
	Combobox updateForecastCombo;
	
	@Wire
	Combobox currentForecastCombo;
	
	@Wire
	Combobox budgetCombo;
	
	@Wire
	Combobox lastYearCombo;
	
	@Wire
	Datebox dayByDayFrom;
	
	@Wire
	Datebox dayByDayTo;
	
	@Wire
    Window popupDialog;
	
	public DayByDayController() {
		
		authService = new AuthenticationServiceHRR3Impl();
		
		if(authService.getUserData().getCurrentHotel() == null || authService.getUserData().getCurrentHotel().getHotelId() ==null || authService.getUserData().getCurrentHotel().getHotelId() < 1)
			{Messagebox.show("Please select a valid Hotel and return to this section."); return;}
		
		currentCustomer = authService.getUserData().getCurrentCustomer();
		
		Hotel currenthotel = authService.getUserData().getCurrentHotel();		
        snapshotDAO = new SnapshotDAO(currenthotel);       
        
        List<Snapshot> snapshotUpdateForecastList = snapshotDAO.getAllSnapshots("REPORT_SNAPSHOT");      
        List<Snapshot> snapshotCurrentForecastList = snapshotDAO.getAllSnapshots("REPORT_SNAPSHOT"); 
        List<Snapshot> snapshotBudgetList = snapshotDAO.getAllSnapshots("REPORT_SNAPSHOT"); 
        List<Snapshot> snapshotLastYearList = snapshotDAO.getAllSnapshots("REPORT_SNAPSHOT"); 
        
        snapshotsUpdateForecastModel = new ListModelList<Snapshot>(snapshotUpdateForecastList);
        snapshotsCurrentForecastModel = new ListModelList<Snapshot>(snapshotCurrentForecastList);
        snapshotsBudgetModel = new ListModelList<Snapshot>(snapshotBudgetList);
        snapshotsLastYearModel = new ListModelList<Snapshot>(snapshotLastYearList);
        		
    }
	
	
	public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        
        popupDialog.setTitle("Day By Day Report");
	}
	

    @Listen("onClick = #exitReportBtn")
    public void showModal(Event e) {
    	popupDialog.detach();
    }
    
 
    
    @Listen("onClick = #printReportBtn")
    public void printReportBtn() {
    	
   	
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	
    	
    	if(this.updateForecastCombo.getSelectedIndex() == 0) {
    		Messagebox.show("Please select an Update Forecast Snapshot from the list.", "Day By Day Report", Messagebox.OK, Messagebox.EXCLAMATION);
    		return;
    	}
    	
    	if(this.currentForecastCombo.getSelectedIndex() == 0) {
    		Messagebox.show("Please select a Current Forecast Snapshot from the list.", "Day By Day Report", Messagebox.OK, Messagebox.EXCLAMATION);
    		return;
    	} 
    	
    	if(this.budgetCombo.getSelectedIndex() == 0) {    		
    		Messagebox.show("Please select a Budget Snapshot from the list.", "Day By Day Report", Messagebox.OK, Messagebox.EXCLAMATION);
    		return;
    	} 
    	
    	if(this.lastYearCombo.getSelectedIndex() == 0) {
    		Messagebox.show("Please select a Last Year Snapshot from the list.", "Day By Day Report", Messagebox.OK, Messagebox.EXCLAMATION);
    		return;
    	} 
    	
    	if(this.dayByDayTo.getValue().compareTo(this.dayByDayFrom.getValue()) < 0)
		{ Messagebox.show("EndDate must be >= StartDate.", "Day By Day Report", Messagebox.OK, Messagebox.EXCLAMATION); return; }

    	
    	
    	Integer customerId = this.currentCustomer.getCustomerId();
    	String startDate = dateFormat.format(this.dayByDayFrom.getValue());
    	String endDate = dateFormat.format(this.dayByDayTo.getValue());
    	
    	
    	Integer UpdateForecast = this.updateForecastCombo.getSelectedItem().getValue();
    	Integer CurrentSnapshot = this.currentForecastCombo.getSelectedItem().getValue();
    	Integer Budget = this.budgetCombo.getSelectedItem().getValue();
    	Integer LastYear = this.lastYearCombo.getSelectedItem().getValue();
    	
    	JasperServerReportParameter p0 = new JasperServerReportParameter("customerId",customerId);
    	JasperServerReportParameter p1 = new JasperServerReportParameter("UpdateForecast",UpdateForecast);
    	JasperServerReportParameter p2 = new JasperServerReportParameter("CurrentSnapshot",CurrentSnapshot);
    	JasperServerReportParameter p3 = new JasperServerReportParameter("Budget",Budget);
    	JasperServerReportParameter p4 = new JasperServerReportParameter("LastYear",LastYear);
    	JasperServerReportParameter p5 = new JasperServerReportParameter("StartDate", startDate);
    	JasperServerReportParameter p6 = new JasperServerReportParameter("EndDate", endDate);
    	
    	
    	
    	List<JasperServerReportParameter> inputReportParameters = new ArrayList<JasperServerReportParameter>();
    	
    	inputReportParameters.add(p0);
    	inputReportParameters.add(p1);
    	inputReportParameters.add(p2);
    	inputReportParameters.add(p3);
    	inputReportParameters.add(p4);
    	inputReportParameters.add(p5); 
    	inputReportParameters.add(p6);
    	
    	new JasperServerReportBuilder().buildExecutionURL(JasperServerReportsConfig.DAY_BY_DAY_REPORT, JasperServerReportsConfig.PDF_FORMAT, inputReportParameters, true); 	
    	    	
    }
    
    public void initializeFormControls(int type) {

    }


	public ListModel<Snapshot> getSnapshotsUpdateForecastModel() {
		return snapshotsUpdateForecastModel;
	}


	public ListModel<Snapshot> getSnapshotsCurrentForecastModel() {
		return snapshotsCurrentForecastModel;
	}


	public ListModel<Snapshot> getSnapshotsBudgetModel() {
		return snapshotsBudgetModel;
	}


	public ListModel<Snapshot> getSnapshotsLastYearModel() {
		return snapshotsLastYearModel;
	}


		
}
