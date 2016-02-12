package com.hrr3.controller.reports.forecast.rollup;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;

import com.hrr3.entity.Hotel;
import com.hrr3.util.reports.JasperServerReportBuilder;
import com.hrr3.util.reports.JasperServerReportCustomFunctions;
import com.hrr3.util.reports.JasperServerReportParameter;
import com.hrr3.util.reports.JasperServerReportsConfig;

public class AnnualRoomRevenueController extends CommonRollUpController  {
	private static final long serialVersionUID = 1L;
		
	@Wire
	Combobox yearCombo;
	
	@Wire
	Combobox monthCombo;
	
	@Wire
	Radiogroup currentOrUpdatedForecastGroup;
	
	@Wire
	Radio updatedForecastRadio;
	
	@Wire
	Radio currentForecastRadio;
		
	public AnnualRoomRevenueController() {
		
		super();
    }
	
	
	public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        
        popupDialog.setTitle("Annual Room Revenue Roll Up Report");
        this.monthCombo.setDisabled(true);
	}
	
	@Listen("onCreate =#yearCombo")
	    public void selectCurrentYearCombo() {
	    	
			int posToSelect = 0;
			String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
		
			for(Comboitem item : yearCombo.getItems()) {
				if(item.getLabel().equals(year)) {
					posToSelect = item.getIndex();
				}
			}
			yearCombo.setSelectedIndex(posToSelect);
	    }

    @Listen("onCheck =#updatedForecastRadio")
    public void disableCurrentForecastCombo() {
    	
    	this.monthCombo.setDisabled(true);
    }
    
    @Listen("onCheck =#currentForecastRadio")
    public void disableUpdateForecastCombo  () {
    	
    	this.monthCombo.setDisabled(false);
    }
    
    @Listen("onClick =#printSummaryBtn")    
    public void printSummaryReportBtn() {
    	
    	this.executeReport(false);
    }
    
    @Listen("onClick =#printDetailBtn;")   
    public void printDetailReportBtn() {
    	
    	this.executeReport(true);
    }
    
    private void executeReport (boolean isDetail) {
    	
    	
    	String year = this.yearCombo.getSelectedItem().getValue();
    	String month = null;
    	String UF = this.updatedForecastRadio.isChecked() ? "1" : "0";
    	String CF = this.currentForecastRadio.isChecked() ? "1" : "0";
    	String Detail = isDetail ? "1" : "0";    
    	
    	if(this.hotelListBox.getSelectedCount() <= 0)
    		{ Messagebox.show("Please select at least one hotel.", "Rollup Annual Room Revenue", Messagebox.OK, Messagebox.EXCLAMATION);	return; }
    	
    	if(this.currentForecastRadio.isChecked() && this.monthCombo.getSelectedIndex() <= 0)
    		{ Messagebox.show("Please select at least one hotel.", "Rollup Annual Room Revenue", Messagebox.OK, Messagebox.EXCLAMATION);	return; }
    	
    	month = this.monthCombo.getSelectedIndex()== 0 ? null : this.monthCombo.getSelectedItem().getValue().toString();
    	
    	Set<Hotel> hotelSet = ((ListModelList<Hotel>)hotelsModel).getSelection();      			
    	String hotelIds = JasperServerReportCustomFunctions.splitHotelsWithPipes(hotelSet);
    	
    	String brand = this.hotelBrandCombo.getSelectedIndex() == 0 ? null : this.hotelBrandCombo.getSelectedItem().getValue().toString();
    	String region = this.hotelRegionCombo.getSelectedIndex() == 0 ? null : this.hotelRegionCombo.getSelectedItem().getValue().toString();
    	
    	JasperServerReportParameter p1 = new JasperServerReportParameter("Year",year);//Requerido
    	JasperServerReportParameter p2 = new JasperServerReportParameter("Month",month);//Requerido si CF
    	JasperServerReportParameter p3 = new JasperServerReportParameter("UF",UF);//No Requerido
    	JasperServerReportParameter p4 = new JasperServerReportParameter("CF",CF);//No Requerido
    	JasperServerReportParameter p5 = new JasperServerReportParameter("ADRM", (Integer)this.adrmUsersCombo.getSelectedItem().getValue() == 0 ? null : (Integer)this.adrmUsersCombo.getSelectedItem().getValue());//No Requerido
    	JasperServerReportParameter p6 = new JasperServerReportParameter("RVPSales", (Integer)this.rvpSalesUsersCombo.getSelectedItem().getValue() == 0 ? null : (Integer)this.rvpSalesUsersCombo.getSelectedItem().getValue());//No Requerido
    	JasperServerReportParameter p7 = new JasperServerReportParameter("RVPOperations", (Integer)this.rvpOpsUsersCombo.getSelectedItem().getValue() == 0 ? null: (Integer)this.rvpOpsUsersCombo.getSelectedItem().getValue());//No Requerido
    	JasperServerReportParameter p8 = new JasperServerReportParameter("RDRM", (Integer)this.rdrmUsersCombo.getSelectedItem().getValue() == 0 ? null: (Integer)this.rdrmUsersCombo.getSelectedItem().getValue());//No Requerido
    	JasperServerReportParameter p9 = new JasperServerReportParameter("Braind", brand);//No Requerido
    	JasperServerReportParameter p10 = new JasperServerReportParameter("Region", region);//No Requerido
    	JasperServerReportParameter p11 = new JasperServerReportParameter("Hotels", hotelIds);//Requerido
    	JasperServerReportParameter p12 = new JasperServerReportParameter("Detail", Detail);//Requerido, 1 = Detail, 0 = Summary
    	
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
    	
    	new JasperServerReportBuilder().buildExecutionURL(JasperServerReportsConfig.ROLLUP_ANNUAL_ROOM_REVENUE_REPORT, JasperServerReportsConfig.PDF_FORMAT, inputReportParameters, true); 	
    	
    }
    
    public void initializeFormControls(int type) {

    }
   

}
