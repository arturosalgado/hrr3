package com.hrr3.controller.reports.forecast;


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
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Window;

import com.hrr3.authentication.AuthenticationServiceHRR3Impl;
import com.hrr3.entity.Hotel;
import com.hrr3.entity.Snapshot;
import com.hrr3.model.SnapshotDAO;
import com.hrr3.services.AuthenticationService;
import com.hrr3.util.reports.JasperServerReportBuilder;
import com.hrr3.util.reports.JasperServerReportParameter;
import com.hrr3.util.reports.JasperServerReportsConfig;

public class MarketSegmentController extends SelectorComposer<Component>  {
	private static final long serialVersionUID = 1L;
	
	private ListModel<Snapshot> snapshotsBaseModel;	
	private ListModel<Snapshot> snapshotsCompareModel;	
	private AuthenticationService authService;
	private SnapshotDAO snapshotDAO;
	
	@Wire
	Combobox snapshotBaseCombo;
	
	@Wire
	Combobox snapshotCompareCombo;
	
	@Wire
	Combobox yearCombo;
	
	@Wire
	Radiogroup dataIncGroup;
	
	@Wire
	Radiogroup formattingGroup;
	
	@Wire
	Radio previousYearRadio;
	
	@Wire
    Window popupMarketSegmentsDialog;
	
	public MarketSegmentController() {
		
		authService = new AuthenticationServiceHRR3Impl();
		
		if(authService.getUserData().getCurrentHotel() == null || authService.getUserData().getCurrentHotel().getHotelId() ==null || authService.getUserData().getCurrentHotel().getHotelId() < 1)
			{Messagebox.show("Please select a valid Hotel and return to this section."); return;}
		
		
		Hotel currenthotel = authService.getUserData().getCurrentHotel();		
        snapshotDAO = new SnapshotDAO(currenthotel);        
        List<Snapshot> snapshotList = snapshotDAO.getAllSnapshots("REPORT_SNAPSHOT");       
        snapshotsBaseModel = new ListModelList<Snapshot>(snapshotList);
        snapshotsCompareModel = new ListModelList<Snapshot>(snapshotList);
        
     
		
    }
	
	
	public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        
        popupMarketSegmentsDialog.setTitle("Market Segments Report");
	}
	

    @Listen("onClick = #exitReportBtn")
    public void showModal(Event e) {
    	popupMarketSegmentsDialog.detach();
    }
    
    @Listen("onCheck =#previousYearRadio")
    public void disnableSnapshotCompareCombo() {
    	
    	this.snapshotCompareCombo.setDisabled(true);
    }
    
    @Listen("onCheck =#selectSnapshotToCompareRadio")
    public void enableSnapshotCompareCombo  () {
    	
    	this.snapshotCompareCombo.setDisabled(false);
    }
    
    @Listen("onClick = #printReportBtn")
    public void printReportBtn() {
    	
    	Integer snapshotID1;
    	Integer snapshotID2;
    	Integer reportYear1;
    	Integer reportYear2;
    	String dataIncl = "";
    	boolean isPreviousSelected = this.previousYearRadio.isSelected() ;
    	
    	if(this.snapshotBaseCombo.getSelectedIndex() == 0) {
    		Messagebox.show("Please select a Base Snapshot from the list.");
    		return;
    	}    	
    	
    	if(!isPreviousSelected && this.snapshotCompareCombo.getSelectedIndex() == 0) {
    		Messagebox.show("Please select a Snapshot to compare from the list.");
    		return;
    	}
    		
    	snapshotID1 =  this.snapshotBaseCombo.getSelectedItem().getValue();
    	reportYear1 = Integer.parseInt((String)this.yearCombo.getSelectedItem().getValue());
    	    	
    	if(isPreviousSelected) {    		
    		
	    	reportYear2 = reportYear1;
	    	reportYear2--;
	    	snapshotID2 = snapshotID1;
    	}
    	
    	else {
    		
    		snapshotID2 =  this.snapshotCompareCombo.getSelectedItem().getValue();
    		reportYear2 = reportYear1;
    	}
    	
    	dataIncl = this.dataIncGroup.getSelectedItem().getValue();	    	
    	
    	JasperServerReportParameter p1 = new JasperServerReportParameter("SnapshotID1",snapshotID1);
    	JasperServerReportParameter p2 = new JasperServerReportParameter("SnapshotID2",snapshotID2);
    	JasperServerReportParameter p3 = new JasperServerReportParameter("ReportYear1",reportYear1);
    	JasperServerReportParameter p4 = new JasperServerReportParameter("ReportYear2",reportYear2);
    	JasperServerReportParameter p5 = new JasperServerReportParameter("DataIncl", dataIncl);
    	
    	List<JasperServerReportParameter> inputReportParameters = new ArrayList<JasperServerReportParameter>();
    	
    	inputReportParameters.add(p1);
    	inputReportParameters.add(p2);
    	inputReportParameters.add(p3);
    	inputReportParameters.add(p4);
    	inputReportParameters.add(p5);    	
    	
    	if(formattingGroup.getSelectedIndex() == 0)
    		new JasperServerReportBuilder().buildExecutionURL(JasperServerReportsConfig.MARKET_SEGMENT_NORMAL_REPORT, JasperServerReportsConfig.PDF_FORMAT, inputReportParameters, true);
    	else
    		new JasperServerReportBuilder().buildExecutionURL(JasperServerReportsConfig.MARKET_SEGMENT_COMPACT_REPORT, JasperServerReportsConfig.PDF_FORMAT, inputReportParameters, true);
    	
    }
    
    public void initializeFormControls(int type) {

    }

	public ListModel<Snapshot> getSnapshotsBaseModel() {
        return snapshotsBaseModel;
    }
	
	public ListModel<Snapshot> getSnapshotsCompareModel() {
        return snapshotsCompareModel;
    }
		
}
