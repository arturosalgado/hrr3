package com.hrr3.controller.reports.forecast.rollup;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import com.hrr3.entity.Hotel;
import com.hrr3.util.reports.JasperServerReportBuilder;
import com.hrr3.util.reports.JasperServerReportCustomFunctions;
import com.hrr3.util.reports.JasperServerReportParameter;
import com.hrr3.util.reports.JasperServerReportsConfig;

public class AccuracyController extends CommonRollUpController  {
	private static final long serialVersionUID = 1L;
	
	
	@Wire
	Combobox budgetYearCombo;
	
	@Wire
	Combobox currentForecastYearCombo;
	
	@Wire
	Combobox currentForecastMonthCombo;
	
	@Wire
	Datebox updatedForecastFrom;
	
	@Wire
	Datebox updatedForecastTo;
	
	@Wire
	Datebox budgetFrom;
	
	@Wire
	Datebox budgetTo;
	
	@Wire
	Datebox currentForecastFrom;
	
	@Wire
	Datebox currentForecastTo;
	
	
	public AccuracyController() {
		
		super();
    }
	
	
	public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        
        popupDialog.setTitle("Forecast Accuracy Roll-Up Report");
        
	}
	    
    @Listen("onClick =#printReportBtn;")   
    public void printReportBtn() {
    	
    	this.executeReport();
    }
    
    private void executeReport () {
    	
    	//To Format dates
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	
    	//Validate inputs
    	
    	String budgetYear = this.budgetYearCombo.getSelectedItem().getValue().toString();
    	String currentForecastMonth = this.currentForecastMonthCombo.getSelectedItem().getValue().toString();
    	String currentForecastYear = this.currentForecastYearCombo.getSelectedItem().getValue().toString();
    	
    	if(budgetYear.equals("0"))
    		{ Messagebox.show("Please select budget year.", "Forecast Accuracy Roll up", Messagebox.OK, Messagebox.EXCLAMATION);	return; }
    	if(currentForecastMonth.equals("0"))
			{ Messagebox.show("Please select forecast month.", "Forecast Accuracy Roll up", Messagebox.OK, Messagebox.EXCLAMATION);	return; }
    	if(currentForecastYear.equals("0"))
			{ Messagebox.show("Please select forecast year.", "Forecast Accuracy Roll up", Messagebox.OK, Messagebox.EXCLAMATION);	return; }    	    	
    	if(this.hotelListBox.getSelectedCount() <= 0)
    		{ Messagebox.show("Please select at least one hotel.", "Forecast Accuracy Roll up", Messagebox.OK, Messagebox.EXCLAMATION);	return; }
    	
    	Set<Hotel> hotelSet = ((ListModelList<Hotel>)hotelsModel).getSelection();
    	String hotelIds = JasperServerReportCustomFunctions.splitHotelsWithPipes(hotelSet); 
    	
    	String brand = this.hotelBrandCombo.getSelectedIndex() == 0 ? null : this.hotelBrandCombo.getSelectedItem().getValue().toString();
    	String region = this.hotelRegionCombo.getSelectedIndex() == 0 ? null : this.hotelRegionCombo.getSelectedItem().getValue().toString();
    	
    	JasperServerReportParameter p1 = new JasperServerReportParameter("startDateUF",dateFormat.format(this.updatedForecastFrom.getValue()));
    	JasperServerReportParameter p2 = new JasperServerReportParameter("endDateUF",dateFormat.format(this.updatedForecastTo.getValue()));
    	JasperServerReportParameter p3 = new JasperServerReportParameter("startDateBudget",dateFormat.format(this.budgetFrom.getValue()));
    	JasperServerReportParameter p4 = new JasperServerReportParameter("endDateBudget",dateFormat.format(this.budgetTo.getValue()));
    	JasperServerReportParameter p5 = new JasperServerReportParameter("yearBudget", budgetYear);
    	JasperServerReportParameter p6 = new JasperServerReportParameter("startDateCF",dateFormat.format(this.currentForecastFrom.getValue()));
    	JasperServerReportParameter p7 = new JasperServerReportParameter("endDateCF",dateFormat.format(this.currentForecastTo.getValue()));
    	JasperServerReportParameter p8 = new JasperServerReportParameter("monthCF", currentForecastMonth);
    	JasperServerReportParameter p9 = new JasperServerReportParameter("yearCF", currentForecastYear);
    	JasperServerReportParameter p10 = new JasperServerReportParameter("hotels", hotelIds);
    	JasperServerReportParameter p11 = new JasperServerReportParameter("ADRM", (Integer)this.adrmUsersCombo.getSelectedItem().getValue() == 0 ? null : (Integer)this.adrmUsersCombo.getSelectedItem().getValue());//No Requerido
    	JasperServerReportParameter p12 = new JasperServerReportParameter("RVPSales", (Integer)this.rvpSalesUsersCombo.getSelectedItem().getValue() == 0 ? null : (Integer)this.rvpSalesUsersCombo.getSelectedItem().getValue());//No Requerido
    	JasperServerReportParameter p13 = new JasperServerReportParameter("RVPOperations", (Integer)this.rvpOpsUsersCombo.getSelectedItem().getValue() == 0 ? null: (Integer)this.rvpOpsUsersCombo.getSelectedItem().getValue());//No Requerido
    	JasperServerReportParameter p14 = new JasperServerReportParameter("RDRM", (Integer)this.rdrmUsersCombo.getSelectedItem().getValue() == 0 ? null: (Integer)this.rdrmUsersCombo.getSelectedItem().getValue());//No Requerido    	 
    	JasperServerReportParameter p15 = new JasperServerReportParameter("brand", brand);//No Requerido
    	JasperServerReportParameter p16 = new JasperServerReportParameter("region", region);//No Requerido
    	
    	List<JasperServerReportParameter> inputReportParameters = new ArrayList<JasperServerReportParameter>();
    	
    	inputReportParameters.add(p1);
    	inputReportParameters.add(p2);
    	inputReportParameters.add(p3);
    	inputReportParameters.add(p4);
    	inputReportParameters.add(p5);  
    	inputReportParameters.add(p6);
    	inputReportParameters.add(p7);
    	inputReportParameters.add(p8);
    	inputReportParameters.add(p9);
    	inputReportParameters.add(p10);
    	inputReportParameters.add(p11);
    	inputReportParameters.add(p12);
    	inputReportParameters.add(p13);
    	inputReportParameters.add(p14);
    	inputReportParameters.add(p15);
    	inputReportParameters.add(p16);
    	
    	new JasperServerReportBuilder().buildExecutionURL(JasperServerReportsConfig.ROLLUP_FORECAST_ACCURACY_REPORT, JasperServerReportsConfig.PDF_FORMAT, inputReportParameters, true); 	
    	
    }
    
    public void initializeFormControls(int type) {

    }
   

}
