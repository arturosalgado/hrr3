package com.hrr3.controller.reports.forecast.rollup;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import com.hrr3.entity.Hotel;
import com.hrr3.util.reports.JasperServerReportBuilder;
import com.hrr3.util.reports.JasperServerReportCustomFunctions;
import com.hrr3.util.reports.JasperServerReportParameter;
import com.hrr3.util.reports.JasperServerReportsConfig;

public class WeeklyMeetingController extends CommonRollUpController  {
	private static final long serialVersionUID = 1L;
		
	public WeeklyMeetingController() {
		
		super();
    }
	
	
	public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);        
        popupDialog.setTitle("Weekly Meeting Minutes Roll-Up Report");        
	}
	    
    @Listen("onClick =#printReportBtn;")   
    public void printReportBtn() {
    	    	 
    	this.executeReport();
    }
    
    private void executeReport () {
    	
    	//To Format dates
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	    	    	    	    	
    	if(this.hotelListBox.getSelectedCount() <= 0)
    		{ Messagebox.show("Please select at least one hotel.", "Need Date Calendar Roll up", Messagebox.OK, Messagebox.EXCLAMATION);	return; }
    	
    	Set<Hotel> hotelSet = ((ListModelList<Hotel>)hotelsModel).getSelection();
    	String hotelIds = JasperServerReportCustomFunctions.splitHotelsWithPipes(hotelSet); 
    	
    	String brand = this.hotelBrandCombo.getSelectedIndex() == 0 ? null : this.hotelBrandCombo.getSelectedItem().getValue().toString();
    	String region = this.hotelRegionCombo.getSelectedIndex() == 0 ? null : this.hotelRegionCombo.getSelectedItem().getValue().toString();
    	    	
    	JasperServerReportParameter p0 = new JasperServerReportParameter("p_id_customer", this.authService.getUserData().getCurrentCustomer().getCustomerId()); 	    	
    	JasperServerReportParameter p1 = new JasperServerReportParameter("p_ds_hotels", hotelIds);
    	JasperServerReportParameter p2 = new JasperServerReportParameter("ADRM", (Integer)this.adrmUsersCombo.getSelectedItem().getValue() == 0 ? null : (Integer)this.adrmUsersCombo.getSelectedItem().getValue());//No Requerido
    	JasperServerReportParameter p3 = new JasperServerReportParameter("RVPSales", (Integer)this.rvpSalesUsersCombo.getSelectedItem().getValue() == 0 ? null : (Integer)this.rvpSalesUsersCombo.getSelectedItem().getValue());//No Requerido
    	JasperServerReportParameter p4 = new JasperServerReportParameter("RVPOperations", (Integer)this.rvpOpsUsersCombo.getSelectedItem().getValue() == 0 ? null: (Integer)this.rvpOpsUsersCombo.getSelectedItem().getValue());//No Requerido
    	JasperServerReportParameter p5 = new JasperServerReportParameter("RDRM", (Integer)this.rdrmUsersCombo.getSelectedItem().getValue() == 0 ? null: (Integer)this.rdrmUsersCombo.getSelectedItem().getValue());//No Requerido    	 
    	JasperServerReportParameter p6 = new JasperServerReportParameter("braind", brand);//No Requerido
    	JasperServerReportParameter p7 = new JasperServerReportParameter("region", region);//No Requerido
    	
    	List<JasperServerReportParameter> inputReportParameters = new ArrayList<JasperServerReportParameter>();
    	inputReportParameters.add(p0);
    	inputReportParameters.add(p1);
    	inputReportParameters.add(p2);
    	inputReportParameters.add(p3);
    	inputReportParameters.add(p4);
    	inputReportParameters.add(p5);  
    	inputReportParameters.add(p6);
    	inputReportParameters.add(p7);
    	
    	new JasperServerReportBuilder().buildExecutionURL(JasperServerReportsConfig.ROLLUP_WEEKLY_MEETING_REPORT, JasperServerReportsConfig.PDF_FORMAT, inputReportParameters, true); 	
    	
    }
    
    public void initializeFormControls(int type) {

    }
   
}
