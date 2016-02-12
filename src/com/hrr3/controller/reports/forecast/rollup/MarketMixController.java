package com.hrr3.controller.reports.forecast.rollup;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.naming.NamingException;

import org.zkoss.zhtml.Link;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Window;

import com.hrr3.authentication.AuthenticationServiceHRR3Impl;
import com.hrr3.entity.Brand;
import com.hrr3.entity.Hotel;
import com.hrr3.entity.Region;
import com.hrr3.entity.Snapshot;
import com.hrr3.entity.User;
import com.hrr3.model.BrandDAO;
import com.hrr3.model.HotelDAO;
import com.hrr3.model.RM2MigrationDAO;
import com.hrr3.model.RegionDAO;
import com.hrr3.model.SnapshotDAO;
import com.hrr3.model.UserDAO;
import com.hrr3.services.AuthenticationService;
import com.hrr3.util.reports.JasperServerConnectionFactory;
import com.hrr3.util.reports.JasperServerReportBuilder;
import com.hrr3.util.reports.JasperServerReportCustomFunctions;
import com.hrr3.util.reports.JasperServerReportParameter;
import com.hrr3.util.reports.JasperServerReportsConfig;

public class MarketMixController extends CommonRollUpController {
	private static final long serialVersionUID = 1L;
		
	@Wire
	Combobox budgetYearCombo;
	
	@Wire
	Datebox lastYearFrom;
	
	@Wire
	Datebox lastYearTo;
	
	@Wire
	Datebox budgetFrom;
	
	@Wire
	Datebox budgetTo;
	
	@Wire
	Datebox updatedForecastFrom;
	
	@Wire
	Datebox updatedForecastTo;
	
	
	
	
	@Wire
	Combobox adrmUsersCombo;
	
	@Wire
	Combobox rvpSalesUsersCombo;
	
	@Wire
	Combobox rvpOpsUsersCombo;
	
	@Wire
	Combobox rdrmUsersCombo;
	
	@Wire
	Combobox hotelBrandCombo;
	
	@Wire
	Combobox hotelRegionCombo;
	
	@Wire
    Window popupDialog;
	
	@Wire
	Listbox hotelListBox;
	
	@Wire
	Button printReportBtn;
	
	public MarketMixController() {
		super();
    }
	
	
	public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        
        popupDialog.setTitle("Market Mix Analysis Roll-Up Report");
        
	}
	

    @Listen("onClick = #exitReportBtn")
    public void showModal(Event e) {
    	popupDialog.detach();
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
    	
    	if(budgetYear.equals("0"))
    		{ Messagebox.show("Please select budget year.", "Market Mix Roll up", Messagebox.OK, Messagebox.EXCLAMATION);	return; }
    	    	    	
    	if(this.hotelListBox.getSelectedCount() <= 0)
    		{ Messagebox.show("Please select at least one hotel.", "Market Mix Roll up", Messagebox.OK, Messagebox.EXCLAMATION);	return; }
    	
    	Set<Hotel> hotelSet = ((ListModelList<Hotel>)hotelsModel).getSelection();
    	String hotelIds = JasperServerReportCustomFunctions.splitHotelsWithPipes(hotelSet); 
    	
    	String brand = this.hotelBrandCombo.getSelectedIndex() == 0 ? null : this.hotelBrandCombo.getSelectedItem().getValue().toString();
    	String region = this.hotelRegionCombo.getSelectedIndex() == 0 ? null : this.hotelRegionCombo.getSelectedItem().getValue().toString();
    	
    	JasperServerReportParameter p0 = new JasperServerReportParameter("p_id_customer",this.authService.getUserData().getCurrentCustomer().getCustomerId());
    	JasperServerReportParameter p1 = new JasperServerReportParameter("p_dt_start_uf",dateFormat.format(this.updatedForecastFrom.getValue()));    	
    	JasperServerReportParameter p2 = new JasperServerReportParameter("p_dt_end_uf",dateFormat.format(this.updatedForecastTo.getValue()));
    	JasperServerReportParameter p3 = new JasperServerReportParameter("p_dt_start_budget",dateFormat.format(this.budgetFrom.getValue()));
    	JasperServerReportParameter p4 = new JasperServerReportParameter("p_dt_end_budget",dateFormat.format(this.budgetTo.getValue()));
    	JasperServerReportParameter p5 = new JasperServerReportParameter("p_no_year_budget", budgetYear);
    	JasperServerReportParameter p6 = new JasperServerReportParameter("p_dt_start_ly",dateFormat.format(this.lastYearFrom.getValue()));
    	JasperServerReportParameter p7 = new JasperServerReportParameter("p_dt_end_ly",dateFormat.format(this.lastYearTo.getValue()));    	
    	JasperServerReportParameter p10 = new JasperServerReportParameter("p_ds_hotels", hotelIds);
    	JasperServerReportParameter p11 = new JasperServerReportParameter("ADRM", (Integer)this.adrmUsersCombo.getSelectedItem().getValue() == 0 ? null : (Integer)this.adrmUsersCombo.getSelectedItem().getValue());//No Requerido
    	JasperServerReportParameter p12 = new JasperServerReportParameter("RVPSales", (Integer)this.rvpSalesUsersCombo.getSelectedItem().getValue() == 0 ? null : (Integer)this.rvpSalesUsersCombo.getSelectedItem().getValue());//No Requerido
    	JasperServerReportParameter p13 = new JasperServerReportParameter("RVPOperations", (Integer)this.rvpOpsUsersCombo.getSelectedItem().getValue() == 0 ? null: (Integer)this.rvpOpsUsersCombo.getSelectedItem().getValue());//No Requerido
    	JasperServerReportParameter p14 = new JasperServerReportParameter("RDRM", (Integer)this.rdrmUsersCombo.getSelectedItem().getValue() == 0 ? null: (Integer)this.rdrmUsersCombo.getSelectedItem().getValue());//No Requerido    	 
    	JasperServerReportParameter p15 = new JasperServerReportParameter("braind", brand);//No Requerido
    	JasperServerReportParameter p16 = new JasperServerReportParameter("region", region);//No Requerido
    	
    	List<JasperServerReportParameter> inputReportParameters = new ArrayList<JasperServerReportParameter>();
    	inputReportParameters.add(p0);
    	inputReportParameters.add(p1);
    	inputReportParameters.add(p2);
    	inputReportParameters.add(p3);
    	inputReportParameters.add(p4);
    	inputReportParameters.add(p5);  
    	inputReportParameters.add(p6);
    	inputReportParameters.add(p7);
    	inputReportParameters.add(p10);
    	inputReportParameters.add(p11);
    	inputReportParameters.add(p12);
    	inputReportParameters.add(p13);
    	inputReportParameters.add(p14);
    	inputReportParameters.add(p15);
    	inputReportParameters.add(p16);
    	
    	new JasperServerReportBuilder().buildExecutionURL(JasperServerReportsConfig.ROLLUP_MARKET_MIX_REPORT, JasperServerReportsConfig.PDF_FORMAT, inputReportParameters, true); 	
    	    	
    }
    
    public void initializeFormControls(int type) {

    }
   

	/**
	 * @return the hotelsModel
	 */
	public ListModel<Hotel> getHotelsModel() {
		return hotelsModel;
	}
	
	@Listen("onAfterRender=#hotelListBox;")
	public void hotelListAfterRender(){
		
		//this.hotelListBox.selectAll();
	}


	/**
	 * @return the brandsModel
	 */
	public ListModel<Brand> getBrandsModel() {
		return brandsModel;
	}


	/**
	 * @return the regionsModel
	 */
	public ListModel<Region> getRegionsModel() {
		return regionsModel;
	}


	/**
	 * @return the rvpSalesUsersModel
	 */
	public ListModel<User> getRvpSalesUsersModel() {
		return rvpSalesUsersModel;
	}


	/**
	 * @return the rvpOpsUsersModel
	 */
	public ListModel<User> getRvpOpsUsersModel() {
		return rvpOpsUsersModel;
	}


	/**
	 * @return the rdrmUsersModel
	 */
	public ListModel<User> getRdrmUsersModel() {
		return rdrmUsersModel;
	}


	/**
	 * @return the adrmUsersModel
	 */
	public ListModel<User> getAdrmUsersModel() {
		return adrmUsersModel;
	}


}
