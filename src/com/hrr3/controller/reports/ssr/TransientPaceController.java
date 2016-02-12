package com.hrr3.controller.reports.ssr;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
import org.zkoss.zul.Listbox;
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
import com.hrr3.util.reports.JasperServerReportCustomFunctions;
import com.hrr3.util.reports.JasperServerReportParameter;
import com.hrr3.util.reports.JasperServerReportsConfig;

public class TransientPaceController extends SelectorComposer<Component>  {
	private static final long serialVersionUID = 1L;
	
	private ListModel<Snapshot> currentYearFtSnapshotsModel;
	private ListModel<SSRSnapshot> lastYearSsrSnapshotsModel;
	private ListModel<SSRSnapshot> currentYearSsrSnapshotsModel;
	private ListModel<SSRSnapshot> weeklyMeetingSnapshotsModel;
	
	
	private AuthenticationService authService;
	private SnapshotDAO snapshotDAO;
	private SSRSnapshotDAO ssrSnapshotDAO;
	private Customer currentCustomer;
	private Hotel currenthotel;
		
	@Wire
	Combobox currentYearFtSnapshotsCombo;
	
	@Wire
	Combobox lastYearSsrSnapshotsCombo;
	
	@Wire
	Combobox currentYearSsrSnapshotsCombo;
	
	@Wire
	Listbox weeklySnapshotsListBox;
	
	@Wire
	Datebox dateFrom;
	
	@Wire
	Datebox dateTo;
	
	@Wire
	Datebox upTo;
		
	@Wire
    Window popupDialog;
	
	public TransientPaceController() {
		
		authService = new AuthenticationServiceHRR3Impl();
		
		if(authService.getUserData().getCurrentHotel() == null || authService.getUserData().getCurrentHotel().getHotelId() ==null || authService.getUserData().getCurrentHotel().getHotelId() < 1)
			{Messagebox.show("Please select a valid Hotel and return to this section."); return;}
		
		currentCustomer = authService.getUserData().getCurrentCustomer();
		
		currenthotel = authService.getUserData().getCurrentHotel();		
        snapshotDAO = new SnapshotDAO(currenthotel);      
        ssrSnapshotDAO = new SSRSnapshotDAO(currenthotel); 
        
        List<Snapshot> snapshotsModelList = snapshotDAO.getAllSnapshots("REPORT_SNAPSHOT"); 
        currentYearFtSnapshotsModel = new ListModelList<Snapshot>(snapshotsModelList);
        
        List<SSRSnapshot> ssrSnapshotsModelListEmptyRow = ssrSnapshotDAO.getAllSSRSnapshot(null);//null to avoid returning  
        List<SSRSnapshot> ssrSnapshotsModelListDummyRow = ssrSnapshotDAO.getAllSSRSnapshot("REPORT_SNAPSHOT");
        
        lastYearSsrSnapshotsModel = new ListModelList<SSRSnapshot>(ssrSnapshotsModelListDummyRow);
        currentYearSsrSnapshotsModel = new ListModelList<SSRSnapshot>(ssrSnapshotsModelListDummyRow);
        weeklyMeetingSnapshotsModel = new ListModelList<SSRSnapshot>(ssrSnapshotsModelListEmptyRow);
                 
        //Set multiple
        ((ListModelList<SSRSnapshot>)weeklyMeetingSnapshotsModel).setMultiple(true);
        
    }
	
	
	public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        
        popupDialog.setTitle("SSR Transient Pace Analysis Report");
	}
	

    @Listen("onClick = #exitReportBtn")
    public void showModal(Event e) {
    	popupDialog.detach();
    }
    
    @Listen("onClick = #printReportBtn")
    public void printReportBtn() {    	
   	
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	    	
    	if(this.currentYearFtSnapshotsCombo.getSelectedIndex() == 0) {
    		Messagebox.show("Please select a FT Snapshot from the list.", "SSR Transient Pace Analysis Report", Messagebox.OK, Messagebox.EXCLAMATION);
    		return;
    	} 
    	
    	if(this.lastYearSsrSnapshotsCombo.getSelectedIndex() == 0) {
    		Messagebox.show("Please select a Last Year SSR Snapshot from the list.", "SSR Transient Pace Analysis Report", Messagebox.OK, Messagebox.EXCLAMATION);
    		return;
    	} 
    	
    	if(this.currentYearSsrSnapshotsCombo.getSelectedIndex() == 0) {
    		Messagebox.show("Please select a Current Year SSR Snapshot from the list.", "SSR Transient Pace Analysis Report", Messagebox.OK, Messagebox.EXCLAMATION);
    		return;
    	} 
    	
    	if(this.weeklySnapshotsListBox.getSelectedCount() == 0) {
    		Messagebox.show("Please select a Weekly Snapshots from the list.", "SSR Transient Pace Analysis Report", Messagebox.OK, Messagebox.EXCLAMATION);
    		return;
    	} 
    	    	
    	if(this.dateTo.getValue().compareTo(this.dateFrom.getValue()) < 0)
		{ Messagebox.show("EndDate must be >= StartDate.", "Sell Strategy Report", Messagebox.OK, Messagebox.EXCLAMATION); return; }
    	
    	if(this.upTo.getText().isEmpty())
    	{ Messagebox.show("Up to Date must be selected.", "Sell Strategy Report", Messagebox.OK, Messagebox.EXCLAMATION); return; }
    	    	    	
    	Integer customerId = this.currentCustomer.getCustomerId();
    	Integer hotelId = this.currenthotel.getHotelId();
    	Integer snapshotCyFtId = this.currentYearFtSnapshotsCombo.getSelectedItem().getValue();
    	Integer ssrSnapshotCyId = this.currentYearSsrSnapshotsCombo.getSelectedItem().getValue();
    	Integer ssrSnapshotLyId = this.lastYearSsrSnapshotsCombo.getSelectedItem().getValue();
    	String startDate = dateFormat.format(this.dateFrom.getValue());
    	String endDate = dateFormat.format(this.dateTo.getValue());
    	String upToDate = dateFormat.format(this.upTo.getValue());
    	
    	Set<SSRSnapshot> snapshotSet = ((ListModelList<SSRSnapshot>)this.weeklyMeetingSnapshotsModel).getSelection();      			
    	String weeklySnapshotIds = JasperServerReportCustomFunctions.splitSSRSnapshotsWithPipes(snapshotSet);
    	
    	JasperServerReportParameter p0 = new JasperServerReportParameter("p_id_customer",customerId);
    	JasperServerReportParameter p1 = new JasperServerReportParameter("p_id_hotel",hotelId);
    	JasperServerReportParameter p2 = new JasperServerReportParameter("p_id_snapshot_cyft",snapshotCyFtId);
    	JasperServerReportParameter p3 = new JasperServerReportParameter("p_id_ssr_snapshot_cy",ssrSnapshotCyId);
    	JasperServerReportParameter p4 = new JasperServerReportParameter("p_id_ssr_snapshot_ly",ssrSnapshotLyId);
    	JasperServerReportParameter p5 = new JasperServerReportParameter("p_ds_snapshots_rolling_avg",weeklySnapshotIds);
    	JasperServerReportParameter p6 = new JasperServerReportParameter("p_dt_start",startDate);
    	JasperServerReportParameter p7 = new JasperServerReportParameter("p_dt_end",endDate);
    	JasperServerReportParameter p8 = new JasperServerReportParameter("p_dt_upto",upToDate);
    	
    	List<JasperServerReportParameter> inputReportParameters = new ArrayList<JasperServerReportParameter>();
    	
    	inputReportParameters.add(p0); 
    	inputReportParameters.add(p1);
    	inputReportParameters.add(p2);
    	inputReportParameters.add(p3);
    	inputReportParameters.add(p4);
    	inputReportParameters.add(p5);
    	inputReportParameters.add(p6);
    	inputReportParameters.add(p7);
    	inputReportParameters.add(p8);
    	
    	new JasperServerReportBuilder().buildExecutionURL(JasperServerReportsConfig.SSR_TRANSIENT_PACE_ANALYSIS, JasperServerReportsConfig.PDF_FORMAT, inputReportParameters, true); 	
    	
    }
    
    public void initializeFormControls(int type) {

    }


	/**
	 * @return the currentYearFtSnapshotsModel
	 */
	public ListModel<Snapshot> getCurrentYearFtSnapshotsModel() {
		return currentYearFtSnapshotsModel;
	}


	/**
	 * @return the lastYearSsrSnapshotsModel
	 */
	public ListModel<SSRSnapshot> getLastYearSsrSnapshotsModel() {
		return lastYearSsrSnapshotsModel;
	}


	/**
	 * @return the currentYearSsrSnapshotsModel
	 */
	public ListModel<SSRSnapshot> getCurrentYearSsrSnapshotsModel() {
		return currentYearSsrSnapshotsModel;
	}


	/**
	 * @return the weeklyMeetingSnapshotsModel
	 */
	public ListModel<SSRSnapshot> getWeeklyMeetingSnapshotsModel() {
		return weeklyMeetingSnapshotsModel;
	}
	
		
}
