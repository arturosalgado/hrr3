package com.hrr3.controller.reports.ssr;


import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.hrr3.authentication.AuthenticationServiceHRR3Impl;
import com.hrr3.entity.Customer;
import com.hrr3.entity.Hotel;
import com.hrr3.entity.SSRSnapshot;
import com.hrr3.model.SSRSnapshotDAO;
import com.hrr3.services.AuthenticationService;
import com.hrr3.util.reports.JasperServerReportBuilder;
import com.hrr3.util.reports.JasperServerReportParameter;
import com.hrr3.util.reports.JasperServerReportsConfig;

public class WeeklyMeetingMinutesController extends SelectorComposer<Component>  {
	private static final long serialVersionUID = 1L;
	private ListModel<SSRSnapshot> snapshotsModel;	
	
	private AuthenticationService authService;
	private SSRSnapshotDAO ssrSnapshotDAO;
	private Customer currentCustomer;
	private Hotel currenthotel;
		
	@Wire
	Combobox snapshotsCombo;
	
	@Wire
    Window popupDialog;
	
	public WeeklyMeetingMinutesController() {
		
		authService = new AuthenticationServiceHRR3Impl();
		
		if(authService.getUserData().getCurrentHotel() == null || authService.getUserData().getCurrentHotel().getHotelId() ==null || authService.getUserData().getCurrentHotel().getHotelId() < 1)
			{Messagebox.show("Please select a valid Hotel and return to this section."); return;}
		
		currentCustomer = authService.getUserData().getCurrentCustomer();
		
		currenthotel = authService.getUserData().getCurrentHotel();		
		ssrSnapshotDAO = new SSRSnapshotDAO(currenthotel);    
       
        List<SSRSnapshot> snapshotsModelList = ssrSnapshotDAO.getAllSSRSnapshot("REPORT_SNAPSHOT");
        snapshotsModel = new ListModelList<SSRSnapshot>(snapshotsModelList);
        		
    }
	
	
	public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        
        popupDialog.setTitle("Weekly Meeting Minutes Report");
	}
	

    @Listen("onClick = #exitReportBtn")
    public void showModal(Event e) {
    	popupDialog.detach();
    }
    
 
    
    @Listen("onClick = #printReportBtn")
    public void printReportBtn() {
    	    	    	
    	if(this.snapshotsCombo.getSelectedIndex() == 0) {
    		Messagebox.show("Please select a Snapshot from the list.", "SSRWeeklyMeetingMinutesReport", Messagebox.OK, Messagebox.EXCLAMATION);
    		return;
    	} 
    	  	
    	    	    	
    	Integer customerId = this.currentCustomer.getCustomerId();
    	Integer hotelId = this.currenthotel.getHotelId();
    	Integer snapshotId = this.snapshotsCombo.getSelectedItem().getValue();
    	
    	JasperServerReportParameter p0 = new JasperServerReportParameter("p_id_customer",customerId);
    	JasperServerReportParameter p1 = new JasperServerReportParameter("p_id_hotel",hotelId);
    	JasperServerReportParameter p2 = new JasperServerReportParameter("p_id_snapshot_pu",snapshotId);
    	
    	List<JasperServerReportParameter> inputReportParameters = new ArrayList<JasperServerReportParameter>();
    	
    	inputReportParameters.add(p0); 
    	inputReportParameters.add(p1);
    	inputReportParameters.add(p2);
    	
    	new JasperServerReportBuilder().buildExecutionURL(JasperServerReportsConfig.WEEKLY_MEETING_REPORT, JasperServerReportsConfig.PDF_FORMAT, inputReportParameters, true); 	
    	
    }
    
    public void initializeFormControls(int type) {

    }
	public ListModel<SSRSnapshot> getSnapshotsModel() {
		return snapshotsModel;
	}


		
}
