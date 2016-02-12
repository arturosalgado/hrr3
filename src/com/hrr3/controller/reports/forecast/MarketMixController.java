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
import org.zkoss.zul.Checkbox;
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

public class MarketMixController extends SelectorComposer<Component>  {
	private static final long serialVersionUID = 1L;
	
	//Six models to contain diff. snapshots
	private ListModel<Snapshot> snapshotFullYearModel;	
	private ListModel<Snapshot> snapshotBaseModel;	
	private ListModel<Snapshot> snapshotToCompare1Model;	
	private ListModel<Snapshot> snapshotToCompare2Model;			
	
	private AuthenticationService authService;
	private SnapshotDAO snapshotDAO;
	private Hotel currenthotel;
	private Customer currentCustomer;
	
	@Wire
	Combobox snapshotFullYearCombo;
	
	@Wire
	Combobox snapshotBaseCombo;
	
	@Wire
	Combobox snapshotToCompare1Combo;
	
	@Wire
	Combobox snapshotToCompare2Combo;
	
	@Wire
	Combobox fullYearCurrentCombo;
	
	@Wire
	Combobox fullYearToCompareCombo;
	
	@Wire
	Checkbox chkFullReport;
	
	@Wire
	Datebox snapshot1From;
	
	@Wire
	Datebox snapshot1To;
	
	@Wire
	Datebox snapshot2From;
	
	@Wire
	Datebox snapshot2To;
	
	@Wire
	Datebox snapshot3From;
	
	@Wire
	Datebox snapshot3To;
	
		
	@Wire
    Window popupMarketMixDialog;
	
	public MarketMixController() {
		
		authService = new AuthenticationServiceHRR3Impl();
		
		if(authService.getUserData().getCurrentHotel() == null || authService.getUserData().getCurrentHotel().getHotelId() ==null || authService.getUserData().getCurrentHotel().getHotelId() < 1)
			{Messagebox.show("Please select a valid Hotel and return to this section."); return;}		
		
		currenthotel = authService.getUserData().getCurrentHotel();	
		currentCustomer = authService.getUserData().getCurrentCustomer();
        snapshotDAO = new SnapshotDAO(currenthotel);    
        
        //List for each combo
        List<Snapshot> listA = snapshotDAO.getAllSnapshots("REPORT_SNAPSHOT_2");         
        List<Snapshot> listB = snapshotDAO.getAllSnapshots("REPORT_SNAPSHOT_B");       
        List<Snapshot> listC = snapshotDAO.getAllSnapshots("REPORT_SNAPSHOT_1");       
        List<Snapshot> listD = snapshotDAO.getAllSnapshots("REPORT_SNAPSHOT_2");  
        //Model associated to a list
        snapshotFullYearModel = new ListModelList<Snapshot>(listA);
        snapshotBaseModel = new ListModelList<Snapshot>(listB);
        snapshotToCompare1Model = new ListModelList<Snapshot>(listC);
        snapshotToCompare2Model = new ListModelList<Snapshot>(listD);
        
     
		
    }
	
	
	public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        
        popupMarketMixDialog.setTitle("Market Mix Analysis Report");
	}
	

    @Listen("onClick = #exitReportBtn")
    public void showModal(Event e) {
    	popupMarketMixDialog.detach();
    }
    
    @Listen("onClick =#chkFullReport")
    public void enableFullSection() {
    	
    	if(chkFullReport.isChecked()) {
    		
    		//Enable Full report section
        	this.fullYearCurrentCombo.setDisabled(false);
        	this.snapshotFullYearCombo.setDisabled(false);
        	this.fullYearToCompareCombo.setDisabled(false);
        	
        	//Disable Snapshot report section
        	this.snapshotBaseCombo.setDisabled(true);
        	this.snapshotToCompare1Combo.setDisabled(true);
        	this.snapshotToCompare2Combo.setDisabled(true);
        	
        	//Disable dates
        	this.snapshot1From.setDisabled(true);
        	this.snapshot1To.setDisabled(true);
        	this.snapshot2From.setDisabled(true);
        	this.snapshot2To.setDisabled(true);
        	this.snapshot3From.setDisabled(true);
        	this.snapshot3To.setDisabled(true);
    		
    	}
    	
    	else {
    		
    		//Enable Full report section
        	this.fullYearCurrentCombo.setDisabled(true);
        	this.snapshotFullYearCombo.setDisabled(true);
        	this.fullYearToCompareCombo.setDisabled(true);
        	
        	//Disable Snapshot report section
        	this.snapshotBaseCombo.setDisabled(false);
        	this.snapshotToCompare1Combo.setDisabled(false);
        	this.snapshotToCompare2Combo.setDisabled(false);        	
        	
        	//Enable dates
        	this.snapshot1From.setDisabled(false);
        	this.snapshot1To.setDisabled(false);
        	this.snapshot2From.setDisabled(false);
        	this.snapshot2To.setDisabled(false);
        	this.snapshot3From.setDisabled(false);
        	this.snapshot3To.setDisabled(false);
    		
    	}
    	
    	
    	
    	
    }
    
    @Listen("onCheck =#selectSnapshotToCompareRadio")
    public void enableSnapshotCompareCombo  () {
    	
    	//this.snapshotCompareCombo.setDisabled(false);
    }
    
    @Listen("onClick = #printReportBtn")
    public void printReportBtn() {
    	
    	if(chkFullReport.isChecked()) {
    		
    		if(this.fullYearCurrentCombo.getSelectedIndex() == 0)
    			{ Messagebox.show("Please select a year for the current snapshot.", "SSR Import", Messagebox.OK, Messagebox.ERROR); return; }
    		if(this.snapshotFullYearCombo.getSelectedIndex() == 0)
    			{ Messagebox.show("Please select a snapshot to compare with.", "SSR Import", Messagebox.OK, Messagebox.ERROR); return; }
    		if(this.fullYearToCompareCombo.getSelectedIndex() == 0)
    			{ Messagebox.show("Please select a year for the snapshot to compare with.", "SSR Import", Messagebox.OK, Messagebox.ERROR); return; }
    		
    	}
    	
    	else {
    		
    		if(this.snapshotBaseCombo.getSelectedIndex() == 0)
    			{ Messagebox.show("Please select a base snapshot.", "SSR Import", Messagebox.OK, Messagebox.ERROR); return; }
    		if(this.snapshotToCompare1Combo.getSelectedIndex() == 0)
    			{ Messagebox.show("Please select a snapshot1 from the list.", "SSR Import", Messagebox.OK, Messagebox.ERROR); return; }
    		if(this.snapshotToCompare2Combo.getSelectedIndex() == 0)
    			{ Messagebox.show("Please select a snapshot2 from the list.", "SSR Import", Messagebox.OK, Messagebox.ERROR); return; }
    		
    		if(this.snapshot1To.getValue().compareTo(this.snapshot1From.getValue()) < 0)
    			{ Messagebox.show("EndDate must be >= StartDate for Base Snapshot.", "SSR Import", Messagebox.OK, Messagebox.ERROR); return; }
    		if(this.snapshot2To.getValue().compareTo(this.snapshot2From.getValue()) < 0)
    			{ Messagebox.show("EndDate must be >= StartDate for Snapshot1 Snapshot.", "SSR Import", Messagebox.OK, Messagebox.ERROR); return; }
    		if(this.snapshot3To.getValue().compareTo(this.snapshot3From.getValue()) < 0)
    			{ Messagebox.show("EndDate must be >= StartDate for Snapshot2 Snapshot.", "SSR Import", Messagebox.OK, Messagebox.ERROR); return; }
    		
    	}
    	
    	//General info
    	Integer customerId = this.currentCustomer.getCustomerId();
    	Integer hotelId = this.currenthotel.getHotelId();
    	//List to save paramteters
    	List<JasperServerReportParameter> inputReportParameters;
    	//To Format dates
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	
    	//If fullReport, then send specific parameters
    	if(this.chkFullReport.isChecked()) {
    		
    		Integer isFullReport = 1;
    		//Full Report section   
        	String reportFullYearBase = this.fullYearCurrentCombo.getSelectedItem().getValue();
        	Integer snapshot2FullYear = this.snapshotFullYearCombo.getSelectedItem().getValue();
        	String reportFullYear2 = this.fullYearToCompareCombo.getSelectedItem().getValue();
        	
        	//General info
        	JasperServerReportParameter p1 = new JasperServerReportParameter("customerId",customerId);
        	JasperServerReportParameter p2 = new JasperServerReportParameter("hotelId",hotelId);
        	//Full Report section    	
        	JasperServerReportParameter p3 = new JasperServerReportParameter("isFullReport",isFullReport);
        	JasperServerReportParameter p4 = new JasperServerReportParameter("ReportFullYearBase",reportFullYearBase);
        	JasperServerReportParameter p5 = new JasperServerReportParameter("Snapshot2FullYear", snapshot2FullYear);    	
        	JasperServerReportParameter p6 = new JasperServerReportParameter("ReportFullYear2", reportFullYear2);
        	
        	inputReportParameters = new ArrayList<JasperServerReportParameter>();
        	
        	inputReportParameters.add(p1);
        	inputReportParameters.add(p2);
        	
        	inputReportParameters.add(p3);
        	inputReportParameters.add(p4);
        	inputReportParameters.add(p5); 
        	inputReportParameters.add(p6); 
    	}
    	
    	//Otherwise, send only snapshot parameters
    	else {
    		
    		Integer isFullReport = 0;
    		//Snapshot Reports section
        	Integer snapshotBase = this.snapshotBaseCombo.getSelectedItem().getValue();
        	Integer snapshot2 = this.snapshotToCompare1Combo.getSelectedItem().getValue();
        	Integer snapshot3 = this.snapshotToCompare2Combo.getSelectedItem().getValue();
        	String startDateBase = dateFormat.format(this.snapshot1From.getValue());
        	String endDateBase = dateFormat.format(this.snapshot1To.getValue());
        	String startDate2 = dateFormat.format(this.snapshot2From.getValue());
        	String endDate2 = dateFormat.format(this.snapshot2To.getValue());
        	String startDate3 = dateFormat.format(this.snapshot3From.getValue());
        	String endDate3 = dateFormat.format(this.snapshot3To.getValue());     	
        	
        	JasperServerReportParameter p1 = new JasperServerReportParameter("customerId",customerId);
        	JasperServerReportParameter p2 = new JasperServerReportParameter("hotelId",hotelId);
        	JasperServerReportParameter p3 = new JasperServerReportParameter("isFullReport",isFullReport);
        	//Snapshot Reports section
        	JasperServerReportParameter p7 = new JasperServerReportParameter("SnapshotBase", snapshotBase);
        	JasperServerReportParameter p8 = new JasperServerReportParameter("Snapshot2", snapshot2);
        	JasperServerReportParameter p9 = new JasperServerReportParameter("Snapshot3", snapshot3);
        	JasperServerReportParameter p10 = new JasperServerReportParameter("StarDateBase", startDateBase);
        	JasperServerReportParameter p11 = new JasperServerReportParameter("EndDateBase", endDateBase);
        	JasperServerReportParameter p12 = new JasperServerReportParameter("StarDate2", startDate2);
        	JasperServerReportParameter p13 = new JasperServerReportParameter("EndDate2", endDate2);
        	JasperServerReportParameter p14 = new JasperServerReportParameter("StarDate3", startDate3);
        	JasperServerReportParameter p15 = new JasperServerReportParameter("EndDate3", endDate3);
        	
        	inputReportParameters = new ArrayList<JasperServerReportParameter>();
        	
        	inputReportParameters.add(p1); 
        	inputReportParameters.add(p2);
        	inputReportParameters.add(p3);
        	
        	inputReportParameters.add(p7); 
        	inputReportParameters.add(p8); 
        	inputReportParameters.add(p9); 
        	inputReportParameters.add(p10); 
        	inputReportParameters.add(p11); 
        	inputReportParameters.add(p12); 
        	inputReportParameters.add(p13); 
        	inputReportParameters.add(p14); 
        	inputReportParameters.add(p15); 
    		
    	}
    	
    	new JasperServerReportBuilder().buildExecutionURL(JasperServerReportsConfig.MARKET_MIX_REPORT, JasperServerReportsConfig.PDF_FORMAT, inputReportParameters, true); 	
    	
    }
    
    public void initializeFormControls(int type) {

    }


	public ListModel<Snapshot> getSnapshotToCompare1Model() {
		return snapshotToCompare1Model;
	}


	public ListModel<Snapshot> getSnapshotToCompare2Model() {
		return snapshotToCompare2Model;
	}


	public ListModel<Snapshot> getSnapshotFullYearModel() {
		return snapshotFullYearModel;
	}


	public ListModel<Snapshot> getSnapshotBaseModel() {
		return snapshotBaseModel;
	}
}
