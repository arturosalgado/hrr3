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

public class AnnualRoomRevenueController extends SelectorComposer<Component>  {
	private static final long serialVersionUID = 1L;
	private ListModel<Snapshot> snapshotsModel;	
	
	private AuthenticationService authService;
	private SnapshotDAO snapshotDAO;
	private Customer currentCustomer;
	private Hotel currenthotel;
		
	@Wire
	Combobox snapshotsCombo;
	
	@Wire
	Combobox yearCombo;
	
	@Wire
    Window popupDialog;
	
	public AnnualRoomRevenueController() {
		
		authService = new AuthenticationServiceHRR3Impl();
		
		if(authService.getUserData().getCurrentHotel() == null || authService.getUserData().getCurrentHotel().getHotelId() ==null || authService.getUserData().getCurrentHotel().getHotelId() < 1)
			{Messagebox.show("Please select a valid Hotel and return to this section."); return;}
		
		currentCustomer = authService.getUserData().getCurrentCustomer();
		
		currenthotel = authService.getUserData().getCurrentHotel();		
        snapshotDAO = new SnapshotDAO(currenthotel);      
       
        List<Snapshot> snapshotsModelList = snapshotDAO.getAllSnapshots("REPORT_SNAPSHOT"); 
        snapshotsModel = new ListModelList<Snapshot>(snapshotsModelList);
        		
    }
	
	
	public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        
        popupDialog.setTitle("SSR Annual Room Revenue Report");
	}
	

    @Listen("onClick = #exitReportBtn")
    public void showModal(Event e) {
    	popupDialog.detach();
    }
    
 
    
    @Listen("onClick = #printReportBtn")
    public void printReportBtn() {
    	
   	
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	    	
    	if(this.snapshotsCombo.getSelectedIndex() == 0) {
    		Messagebox.show("Please select a Snapshot from the list.", "SSR - Annual Room Revenue Report", Messagebox.OK, Messagebox.EXCLAMATION);
    		return;
    	} 
    	
    	if(this.yearCombo.getSelectedIndex() == 0)
		{ Messagebox.show("Please select a year from the dropdown.", "SSR - Annual Room Revenue Report", Messagebox.OK, Messagebox.EXCLAMATION); return; }
    	    	
    	int reportYear = Integer.parseInt((String)this.yearCombo.getSelectedItem().getValue());
    	    	
    	Integer customerId = this.currentCustomer.getCustomerId();
    	Integer hotelId = this.currenthotel.getHotelId();
    	Integer snapshotIdLastWeek = this.snapshotsCombo.getSelectedItem().getValue();
    	
    	JasperServerReportParameter p0 = new JasperServerReportParameter("customerId",customerId);
    	JasperServerReportParameter p1 = new JasperServerReportParameter("hotelId",hotelId);
    	JasperServerReportParameter p2 = new JasperServerReportParameter("snapshotIdLastWeek",snapshotIdLastWeek);
    	JasperServerReportParameter p3 = new JasperServerReportParameter("year", reportYear);	
    	
    	List<JasperServerReportParameter> inputReportParameters = new ArrayList<JasperServerReportParameter>();
    	
    	inputReportParameters.add(p0); 
    	inputReportParameters.add(p1);
    	inputReportParameters.add(p2);
    	inputReportParameters.add(p3);
    	
    	new JasperServerReportBuilder().buildExecutionURL(JasperServerReportsConfig.SSR_ANNUAL_ROOM_REVENUE_REPORT, JasperServerReportsConfig.PDF_FORMAT, inputReportParameters, true); 	
    	
    }
    
    public void initializeFormControls(int type) {

    }
	public ListModel<Snapshot> getSnapshotsModel() {
		return snapshotsModel;
	}


		
}
