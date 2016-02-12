package com.hrr3.controller.reports.forecast;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.hrr3.authentication.AuthenticationServiceHRR3Impl;
import com.hrr3.controller.ListboxArrowKeyController;
import com.hrr3.entity.Customer;
import com.hrr3.entity.Hotel;
import com.hrr3.entity.SSRSnapshot;
import com.hrr3.entity.Snapshot;
import com.hrr3.entity.renovationImpact.ReportData;
import com.hrr3.entity.renovationImpact.UserPreferencesRenovationImpact;
import com.hrr3.model.RenovationImpactDAO;
import com.hrr3.model.SSRSnapshotDAO;
import com.hrr3.model.SnapshotDAO;
import com.hrr3.services.AuthenticationService;
import com.hrr3.services.ReportServiceProvider;

public class RenovationImpactController extends ListboxArrowKeyController  {
	private static final long serialVersionUID = 1L;
	
	@Wire
    Window renovationDialog;
	
	//Snapshot section
	@Wire
	private Datebox	statDateFrom;
	@Wire
	private Datebox	statDateTo;
	
	@Wire
	Combobox snapshotsCombo;
	
	@Wire
	Combobox ssrSnapshotsCombo;
	
	@Wire
	Decimalbox unrealizedTdFactor;
	
	@Wire
	Intbox sellOutThreshold;
	
	//TotalRooms
	@Wire
	private Label totalRooms;
		
	
	//Buttons
	@Wire
	private Button printReportBtn;
	
	private AuthenticationService authService;
	private SnapshotDAO snapshotDAO;
	private SSRSnapshotDAO ssrSnapshotDAO;
	private RenovationImpactDAO renovationDAO;
	private Customer currentCustomer;
	private Hotel currentHotel;
	private UserPreferencesRenovationImpact currentRenovationImpactData;
	private ListModel<Snapshot> snapshotsModel;
	private ListModel<SSRSnapshot> ssrSnapshotsModel;
	
	
	public RenovationImpactController() {
		//Fixed header to avoid counting
		super(2);
		authService = new AuthenticationServiceHRR3Impl();
		
		if(authService.getUserData().getCurrentHotel() == null || authService.getUserData().getCurrentHotel().getHotelId() ==null || authService.getUserData().getCurrentHotel().getHotelId() < 1)
		{Messagebox.show("Please select a valid Hotel and return to this section."); return;}
					
		currentCustomer = authService.getUserData().getCurrentCustomer();
		
		currentHotel = authService.getUserData().getCurrentHotel();		
        snapshotDAO = new SnapshotDAO(currentHotel);      
        ssrSnapshotDAO = new SSRSnapshotDAO(currentHotel); 
        renovationDAO = new RenovationImpactDAO(currentHotel);
        
        List<Snapshot> snapshotsModelList = snapshotDAO.getAllSnapshots("REPORT_SNAPSHOT"); 
        snapshotsModel = new ListModelList<Snapshot>(snapshotsModelList);
        List<SSRSnapshot> ssrSnapshotsModelList = ssrSnapshotDAO.getAllSSRSnapshot("REPORT_SNAPSHOT"); 
        ssrSnapshotsModel = new ListModelList<SSRSnapshot>(ssrSnapshotsModelList);
        
        //Get report settings
        renovationDAO = new RenovationImpactDAO(this.currentHotel);
        currentRenovationImpactData = renovationDAO.loadRenovationPreferences(authService.getUserData().getUserId(), currentCustomer.getCustomerId());        
      	System.out.println(currentRenovationImpactData);
      	
	}
	public void doAfterCompose(Component comp) throws Exception {
		
		super.doAfterCompose(comp);
		 
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  	        
        this.renovationDialog.setTitle("Renovation Impact Report");
        //Dates
        if(this.currentRenovationImpactData.getStatDateFrom()!=null) this.statDateFrom.setValue(sdf.parse(this.currentRenovationImpactData.getStatDateFrom()));
        if(this.currentRenovationImpactData.getStatDateTo()!=null) this.statDateTo.setValue(sdf.parse(this.currentRenovationImpactData.getStatDateTo()));
                
        //TotalRooms
        if(this.currentHotel.getTotalRooms()!= null) this.totalRooms.setValue(String.valueOf(this.currentHotel.getTotalRooms()));
        //TDFactor
        this.unrealizedTdFactor.setValue(new BigDecimal(this.currentRenovationImpactData.getUnrealizedTDFactor()));
        //SellOUtThreshold
        this.sellOutThreshold.setValue(this.currentRenovationImpactData.getSelloutThreshold());    
        
        printReportBtn.setDisabled(true);
       
	}
	
	@Listen("onCreate=#ssrSnapshotsCombo;")
	public void selectSSRSnapshot(){
	
		SSRSnapshot snapshotSaved = new SSRSnapshot();
		if(this.currentRenovationImpactData.getSsrSnapshotID() == 0)
			this.ssrSnapshotsCombo.setSelectedIndex(0);
		else {
			snapshotSaved.setSnapshotId(currentRenovationImpactData.getSsrSnapshotID());
			int index = (((ListModelList<SSRSnapshot>)this.ssrSnapshotsModel).indexOf(snapshotSaved));
			this.ssrSnapshotsCombo.setSelectedIndex(index);	
		}				
	}
	
	@Listen("onCreate=#snapshotsCombo;")
	public void selectSnapshot(){
	
		Snapshot snapshotSaved = new Snapshot();
		if(this.currentRenovationImpactData.getForecastSnaphotID() == 0)
			this.snapshotsCombo.setSelectedIndex(0);
		else {
			snapshotSaved.setSnapshotId(currentRenovationImpactData.getForecastSnaphotID());
			int index = (((ListModelList<Snapshot>)this.snapshotsModel).indexOf(snapshotSaved));
			this.snapshotsCombo.setSelectedIndex(index);	
		}				
	}
	

    @Listen("onClick = #exitReportBtn")
    public void showModal(Event e) {
    	renovationDialog.detach();
    }
    
    @Listen("onClick = #loadReportBtn")
    public void loadReportBtn() throws WrongValueException, ParseException {
    	DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
    	//Collect settings before loading report.
    	String result = this.validateUserPreferences();
    	if(result!=null)
    	{Messagebox.show(result); return;}
    	this.updateRenovationDataObj();
    	//Returns a new obj with the proper lists to fill out the Grid (lstReportData, lstSummaryReportData)    
    	//UserPreferencesRenovationImpact preferencesRenovation, int snapshotId, int ssrSnapshotId, String statDateFrom, String statDateTo, int customerId
    	this.currentRenovationImpactData = this.renovationDAO.loadRenovationImpactData(this.currentRenovationImpactData, this.currentRenovationImpactData.getForecastSnaphotID(), this.currentRenovationImpactData.getSsrSnapshotID(), sdf.format(statDateFrom.getValue()), sdf.format(statDateTo.getValue()), this.currentCustomer.getCustomerId());
    	
    	List<ReportData> gridList = new ArrayList<ReportData>();    	
    	
    	List<ReportData> summaryList = currentRenovationImpactData.getLstSummaryReportData();
    	List<ReportData> reportList = currentRenovationImpactData.getLstReportData();
    	
    	gridList.addAll(summaryList);    	
    	gridList.addAll(reportList);
    	    	
    	ListModel<ReportData> gridLisDataModel = new ListModelList<ReportData>(gridList);
    	
    	genericListBox.setModel(gridLisDataModel);
    	//itemRenderer="com.hrr3.controller.reports.forecast.RenovationImpactRender"
    	genericListBox.setItemRenderer(new RenovationImpactRender(2));
    	genericListBox.renderAll();
    	printReportBtn.setDisabled(false);
    	
    }
       
    @Listen("onClick = #printReportBtn")
    public void printReportBtn() throws JRException, WrongValueException, ParseException, IOException {
    	
    	//Update all userRenovationImpact object before sending it to the report
    	this.updateRenovationDataObj();
    	UserPreferencesRenovationImpact upp = this.currentRenovationImpactData;
    	
    	String webAppDir = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/");
    	String reportRelativeDir = "WEB-INF" + File.separator + "reports" + File.separator + "renovation" + File.separator;
    	
    	JasperReport jr = (JasperReport)JRLoader.loadObject(new File(webAppDir + reportRelativeDir + "RenovationImpact.jasper"));    	
    	
    	Map<String, Object> parms = new HashMap<String, Object>();
    	parms.put("totalRooms", this.currentHotel.getTotalRooms());
    	parms.put("forecastSnaphotID", upp.getForecastSnaphotID());
    	parms.put("ssrSnapshotID", upp.getSsrSnapshotID());
    	parms.put("statDateFrom", upp.getStatDateFrom());
    	parms.put("statDateTo", upp.getStatDateTo());
    	parms.put("unrealizedTDFactor", upp.getUnrealizedTDFactor());
    	parms.put("selloutThreshold", upp.getSelloutThreshold());
    	parms.put("SUBREPORT_DIR", webAppDir + reportRelativeDir); // La ruta de los .jasper

    	JasperPrint jasperPrint = JasperFillManager.fillReport(jr, parms, new JRBeanCollectionDataSource(Arrays.asList(upp)));
    	byte[] baos = JasperExportManager.exportReportToPdf(jasperPrint);
    	
    	if (baos.length > 0) {
    		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    		ByteArrayOutputStream baosObj = new ByteArrayOutputStream();
    		baosObj.write(baos);
			Sessions.getCurrent().setAttribute(ReportServiceProvider.REPORT_STREAM, baosObj);
			Sessions.getCurrent().setAttribute(ReportServiceProvider.FILE_TYPE, "application/pdf");
			Sessions.getCurrent().setAttribute(ReportServiceProvider.FILE_HEADER, "filename=" + dateFormat.format(Calendar.getInstance().getTime()) + "-" +"RenovationImpactReport" +".pdf");
			Executions.getCurrent().sendRedirect("/reportserviceprovider", "_blank");
		}
		
    }
    
    @Listen("onClick = #saveSettingsBtn")
    public void saveSettings() throws WrongValueException, ParseException {
    	//Before saving settings, validate userPreferences
    	String result = validateUserPreferences();
    	if(result!=null)
    	{Messagebox.show(result); return;}	
    	//Collect all objects before proceding to save settings
    	this.updateRenovationDataObj();
    	if(currentRenovationImpactData.getUserPreferencesId() == 0)
    		renovationDAO.insertRenovationPreferences(currentRenovationImpactData);
    	else
    		renovationDAO.updateRenovationPreferences(currentRenovationImpactData);
    	Messagebox.show("Preferences saved properly.");
    }
    
    private String validateUserPreferences() {
    	
    	String result;
    	    	
    	boolean snapshotSelected = this.snapshotsCombo.getSelectedIndex() > 0;
    	boolean ssrSnapshotSelected = this.ssrSnapshotsCombo.getSelectedIndex() > 0;
    	
    	if(snapshotSelected && ssrSnapshotSelected) result = null;
    	else result = "Please select a valid snapshot/ssrsnapshot.";
    	
    	return result;
    	
    	
    }
    
    private void updateRenovationDataObj() throws WrongValueException, ParseException {
    	
    	DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
    	//Snapshot
    	this.currentRenovationImpactData.setForecastSnaphotID(((Integer)this.snapshotsCombo.getSelectedItem().getValue()).intValue());
    	//SnapshotSSR
    	this.currentRenovationImpactData.setSsrSnapshotID(((Integer)this.ssrSnapshotsCombo.getSelectedItem().getValue()).intValue());
    	
    	if(this.statDateFrom.getValue()!=null) this.currentRenovationImpactData.setStatDateFrom(sdf.format(statDateFrom.getValue()));
    	else
    		this.currentRenovationImpactData.setStatDateFrom(null);
    		
    	if(this.statDateTo.getValue()!=null) this.currentRenovationImpactData.setStatDateTo(sdf.format(statDateTo.getValue()));
    	else
    		this.currentRenovationImpactData.setStatDateTo(null);
    	
    	if(this.unrealizedTdFactor.getValue() != null) this.currentRenovationImpactData.setUnrealizedTDFactor(this.unrealizedTdFactor.getValue().intValue());
    	else
    		this.currentRenovationImpactData.setUnrealizedTDFactor(0);
    	
    	if(this.sellOutThreshold.getValue() != null) this.currentRenovationImpactData.setSelloutThreshold(this.sellOutThreshold.getValue());
    	else
    		this.currentRenovationImpactData.setSelloutThreshold(0);
        
        //Rewrite Hotel, User, and Company in case this is the first time saving settings.
        this.currentRenovationImpactData.setUserId(authService.getUserData().getUserId());
        this.currentRenovationImpactData.setHotelId(this.currentHotel.getHotelId());
        this.currentRenovationImpactData.setCompanyId(this.currentCustomer.getCustomerId());
    }

	/**
	 * @return the snapshotsModel
	 */
	public ListModel<Snapshot> getSnapshotsModel() {
		return snapshotsModel;
	}

	/**
	 * @return the ssrSnapshotsModel
	 */
	public ListModel<SSRSnapshot> getSsrSnapshotsModel() {
		return ssrSnapshotsModel;
	}

	
   
}
