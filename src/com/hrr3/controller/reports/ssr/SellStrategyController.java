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
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.hrr3.authentication.AuthenticationServiceHRR3Impl;
import com.hrr3.entity.Customer;
import com.hrr3.entity.Hotel;
import com.hrr3.entity.SSRSnapshot;
import com.hrr3.entity.Snapshot;
import com.hrr3.model.SSRSnapshotDAO;
import com.hrr3.model.SnapshotDAO;
import com.hrr3.services.AuthenticationService;
import com.hrr3.util.reports.JasperServerReportBuilder;
import com.hrr3.util.reports.JasperServerReportParameter;
import com.hrr3.util.reports.JasperServerReportsConfig;

public class SellStrategyController extends SelectorComposer<Component>  {
	private static final long serialVersionUID = 1L;
	
	private ListModel<Snapshot> snapshotsModel;
	private ListModel<SSRSnapshot> ssrSnapshotsModel;
	
	private AuthenticationService authService;
	private SnapshotDAO snapshotDAO;
	private SSRSnapshotDAO ssrSnapshotDAO;
	private Customer currentCustomer;
	private Hotel currenthotel;
		
	@Wire
	Combobox snapshotsCombo;
	
	@Wire
	Combobox ssrSnapshotsCombo;
	
	@Wire
	Datebox dateFrom;
	
	@Wire
	Datebox dateTo;
	
	@Wire
    Window popupDialog;
	
	public SellStrategyController() {
		
		authService = new AuthenticationServiceHRR3Impl();
		
		if(authService.getUserData().getCurrentHotel() == null || authService.getUserData().getCurrentHotel().getHotelId() ==null || authService.getUserData().getCurrentHotel().getHotelId() < 1)
			{Messagebox.show("Please select a valid Hotel and return to this section."); return;}
		
		currentCustomer = authService.getUserData().getCurrentCustomer();
		
		currenthotel = authService.getUserData().getCurrentHotel();		
        snapshotDAO = new SnapshotDAO(currenthotel);      
        ssrSnapshotDAO = new SSRSnapshotDAO(currenthotel); 
        
        List<Snapshot> snapshotsModelList = snapshotDAO.getAllSnapshots("REPORT_SNAPSHOT"); 
        snapshotsModel = new ListModelList<Snapshot>(snapshotsModelList);
        List<SSRSnapshot> ssrSnapshotsModelList = ssrSnapshotDAO.getAllSSRSnapshot("REPORT_SNAPSHOT"); 
        ssrSnapshotsModel = new ListModelList<SSRSnapshot>(ssrSnapshotsModelList);
        
    }
	
	
	public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        
        popupDialog.setTitle("SSR Sell Strategy Report");
	}
	

    @Listen("onClick = #exitReportBtn")
    public void showModal(Event e) {
    	popupDialog.detach();
    }
    
    @Listen("onClick = #printReportBtn")
    public void printReportBtn() {    	
   	
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	    	
    	if(this.snapshotsCombo.getSelectedIndex() == 0) {
    		Messagebox.show("Please select a Snapshot from the list.", "SSR - Sell Strategy Report", Messagebox.OK, Messagebox.EXCLAMATION);
    		return;
    	} 
    	    	
    	if(this.dateTo.getValue().compareTo(this.dateFrom.getValue()) < 0)
		{ Messagebox.show("EndDate must be >= StartDate.", "Sell Strategy Report", Messagebox.OK, Messagebox.EXCLAMATION); return; }
    	    	    	
    	Integer customerId = this.currentCustomer.getCustomerId();
    	Integer hotelId = this.currenthotel.getHotelId();
    	Integer snapshotId = this.snapshotsCombo.getSelectedItem().getValue();
    	Integer ssrSnapshotId = this.ssrSnapshotsCombo.getSelectedItem().getValue();
    	String startDate = dateFormat.format(this.dateFrom.getValue());
    	String endDate = dateFormat.format(this.dateTo.getValue());
    	
    	JasperServerReportParameter p0 = new JasperServerReportParameter("customerId",customerId);
    	JasperServerReportParameter p1 = new JasperServerReportParameter("hotelId",hotelId);
    	JasperServerReportParameter p2 = new JasperServerReportParameter("snapshotIdForecast",snapshotId);
    	JasperServerReportParameter p3 = new JasperServerReportParameter("ssrSnapshotId",ssrSnapshotId);
    	JasperServerReportParameter p4 = new JasperServerReportParameter("startDate",startDate);
    	JasperServerReportParameter p5 = new JasperServerReportParameter("endDate",endDate);
    	
    	List<JasperServerReportParameter> inputReportParameters = new ArrayList<JasperServerReportParameter>();
    	
    	inputReportParameters.add(p0); 
    	inputReportParameters.add(p1);
    	inputReportParameters.add(p2);
    	inputReportParameters.add(p3);
    	inputReportParameters.add(p4);
    	inputReportParameters.add(p5);
    	
    	new JasperServerReportBuilder().buildExecutionURL(JasperServerReportsConfig.SSR_SELL_STRATEGY_REPORT, JasperServerReportsConfig.PDF_FORMAT, inputReportParameters, true); 	
    	
    }
    
    public void initializeFormControls(int type) {

    }
	public ListModel<Snapshot> getSnapshotsModel() {
		return snapshotsModel;
	}

	public ListModel<SSRSnapshot> getSsrSnapshotsModel() {
		return ssrSnapshotsModel;
	}
		
}
