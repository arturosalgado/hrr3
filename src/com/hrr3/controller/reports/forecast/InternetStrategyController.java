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
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
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

public class InternetStrategyController extends SelectorComposer<Component>  {
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
	Decimalbox unrealizedTdFactor;
	
	@Wire
	Intbox sellOutThreshold;
	
	@Wire
    Window popupDialog;
	
	public InternetStrategyController() {
		
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
        
        popupDialog.setTitle("Internet Strategy Effectiveness Report");
	}
	

    @Listen("onClick = #exitReportBtn")
    public void showModal(Event e) {
    	popupDialog.detach();
    }
    
    @Listen("onClick = #printReportBtn")
    public void printReportBtn() {    	
   	
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	    	
    	if(this.snapshotsCombo.getSelectedIndex() == 0) {
    		Messagebox.show("Please select a Snapshot from the list.", "Internet Strategy Report", Messagebox.OK, Messagebox.EXCLAMATION);
    		return;
    	} 
    	    	
    	if(this.dateTo.getValue().compareTo(this.dateFrom.getValue()) < 0)
		{ Messagebox.show("EndDate must be >= StartDate.", "Internet Strategy Report", Messagebox.OK, Messagebox.EXCLAMATION); return; }
    	
    	if(unrealizedTdFactor.getValue() == null || unrealizedTdFactor.getValue().intValue() < -10 || unrealizedTdFactor.getValue().intValue() > 10)
		{Messagebox.show("Please select a valid TDFactor value (-10 to 10."); return;}
		
		if(sellOutThreshold.getValue() == null || sellOutThreshold.getValue().intValue() < 0 || sellOutThreshold.getValue().intValue() > 100)
		{Messagebox.show("Please select a valid TDFactor value (0 to 100."); return;}
    	    	    	
    	Integer customerId = this.currentCustomer.getCustomerId();
    	Integer hotelId = this.currenthotel.getHotelId();
    	Integer snapshotId = this.snapshotsCombo.getSelectedItem().getValue();
    	Integer ssrSnapshotId = this.ssrSnapshotsCombo.getSelectedItem().getValue();
    	String startDate = dateFormat.format(this.dateFrom.getValue());
    	String endDate = dateFormat.format(this.dateTo.getValue());
    	String tdFactor = unrealizedTdFactor.getValue().toPlainString();
    	Integer sellOut = sellOutThreshold.getValue();
    	
    	
    	JasperServerReportParameter p0 = new JasperServerReportParameter("p_id_customer",customerId);
    	JasperServerReportParameter p1 = new JasperServerReportParameter("p_id_hotel",hotelId);
    	JasperServerReportParameter p2 = new JasperServerReportParameter("p_id_forecast_snapshot",snapshotId);
    	JasperServerReportParameter p3 = new JasperServerReportParameter("p_id_ssr_snapshot",ssrSnapshotId);
    	JasperServerReportParameter p4 = new JasperServerReportParameter("p_fe_start",startDate);
    	JasperServerReportParameter p5 = new JasperServerReportParameter("p_fe_end",endDate);
    	JasperServerReportParameter p6 = new JasperServerReportParameter("p_td_factor",tdFactor);
    	JasperServerReportParameter p7 = new JasperServerReportParameter("p_sell_out_threshold",sellOut);
    	
    	
    	
    	List<JasperServerReportParameter> inputReportParameters = new ArrayList<JasperServerReportParameter>();
    	
    	inputReportParameters.add(p0); 
    	inputReportParameters.add(p1);
    	inputReportParameters.add(p2);
    	inputReportParameters.add(p3);
    	inputReportParameters.add(p4);
    	inputReportParameters.add(p5);
    	inputReportParameters.add(p6);
    	inputReportParameters.add(p7);
    	
    	new JasperServerReportBuilder().buildExecutionURL(JasperServerReportsConfig.INTERNET_STRATEGY_REPORT, JasperServerReportsConfig.PDF_FORMAT, inputReportParameters, true); 	
    	
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
