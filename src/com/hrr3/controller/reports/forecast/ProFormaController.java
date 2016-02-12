package com.hrr3.controller.reports.forecast;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
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
import org.zkoss.zul.Grid;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.hrr3.authentication.AuthenticationServiceHRR3Impl;
import com.hrr3.entity.Customer;
import com.hrr3.entity.Hotel;
import com.hrr3.entity.Snapshot;
import com.hrr3.entity.proforma.ReportData;
import com.hrr3.entity.proforma.UserPreferencesProforma;
import com.hrr3.model.ProformaDAO;
import com.hrr3.model.SnapshotDAO;
import com.hrr3.services.AuthenticationService;
import com.hrr3.services.ReportServiceProvider;

public class ProFormaController extends SelectorComposer<Component>  {
	private static final long serialVersionUID = 1L;
	
	private AuthenticationService authService;	
	@Wire
    Window proformaDialog;
	
	//Snapshot section
	@Wire
	private Datebox	statDateFrom;
	@Wire
	private Datebox	statDateTo;
	@Wire
	private Textbox	groupName;
	@Wire
	private Combobox forecastSnapshot;
	//Part A section
	@Wire
	private Datebox	partADateFrom;
	@Wire
	private Datebox	partADateTo;
	@Wire
	private Intbox	partARmsAllWeekDays;
	@Wire
	private Intbox	partARmsMonday;
	@Wire
	private Intbox	partARmsTuesday;
	@Wire
	private Intbox	partARmsWednesday;
	@Wire
	private Intbox	partARmsThursday;
	@Wire
	private Intbox	partARmsFriday;
	@Wire
	private Intbox	partARmsSaturday;
	@Wire
	private Intbox	partARmsSunday;	
	@Wire
	private Intbox	partAADRAllWeekDays;
	@Wire
	private Intbox	partAADRMonday;
	@Wire
	private Intbox	partAADRTuesday;
	@Wire
	private Intbox	partAADRWednesday;
	@Wire
	private Intbox	partAADRThursday;
	@Wire
	private Intbox	partAADRFriday;
	@Wire
	private Intbox	partAADRSaturday;
	@Wire
	private Intbox	partAADRSunday;	
	//Part B section
	@Wire
	private Datebox	partBDateFrom;
	@Wire
	private Datebox	partBDateTo;
	@Wire
	private Intbox	partBRmsAllWeekDays;
	@Wire
	private Intbox	partBRmsMonday;
	@Wire
	private Intbox	partBRmsTuesday;
	@Wire
	private Intbox	partBRmsWednesday;
	@Wire
	private Intbox	partBRmsThursday;
	@Wire
	private Intbox	partBRmsFriday;
	@Wire
	private Intbox	partBRmsSaturday;
	@Wire
	private Intbox	partBRmsSunday;	
	@Wire
	private Intbox	partBADRAllWeekDays;
	@Wire
	private Intbox	partBADRMonday;
	@Wire
	private Intbox	partBADRTuesday;
	@Wire
	private Intbox	partBADRWednesday;
	@Wire
	private Intbox	partBADRThursday;
	@Wire
	private Intbox	partBADRFriday;
	@Wire
	private Intbox	partBADRSaturday;
	@Wire
	private Intbox	partBADRSunday;
	//Revenue
	@Wire
	private Intbox	fBRevenue;
	@Wire
	private Intbox	fBRevenueProfitPct;
	@Wire
	private Intbox	fBRevenueProfit;
	@Wire
	private Intbox	otherRevenue;
	@Wire
	private Intbox	otherRevenueProfitPct;
	@Wire
	private Intbox	otherRevenueProfit;
	
	//CPOR
	@Wire
	private Decimalbox	displaced;
	@Wire
	private Decimalbox	base;
	
	//TotalRooms
	@Wire
	private Label totalRooms;
	
	//Grid
	@Wire
	private Grid proformaGrid;
	
	//Buttons
	@Wire
	private Button printReportBtn;
	
	//
	@Wire
	private Iframe ifReport;
	
	private Hotel currentHotel;
	private Customer currentCustomer;
	private UserPreferencesProforma currentProformaData;
	private ListModel<Snapshot> snapshotsForecastModel;
	private ProformaDAO proformaDAO;
	
	public ProFormaController() {
		
		authService = new AuthenticationServiceHRR3Impl();
		
		if(authService.getUserData().getCurrentHotel() == null || authService.getUserData().getCurrentHotel().getHotelId() ==null || authService.getUserData().getCurrentHotel().getHotelId() < 1)
			{Messagebox.show("Please select a valid Hotel and return to this section."); return;}		
		
		currentHotel = authService.getUserData().getCurrentHotel();	
		currentCustomer = authService.getUserData().getCurrentCustomer();	
				
		List<Snapshot> snapshotsForecastList = new SnapshotDAO(this.currentHotel).getAllSnapshots("REPORT_SNAPSHOT"); 		
		snapshotsForecastModel = new ListModelList<Snapshot>(snapshotsForecastList);
		
		//Get report settings
		proformaDAO = new ProformaDAO(this.currentHotel);
		currentProformaData = proformaDAO.loadProformaPreferences(authService.getUserData().getUserId(), currentCustomer.getCustomerId());
		currentProformaData.setHotelName(this.currentHotel.getName());
		System.out.println(currentProformaData);
      
    }	
	
	public void doAfterCompose(Component comp) throws Exception {
		
		super.doAfterCompose(comp);
		 
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  	        
        this.proformaDialog.setTitle("Pro Forma Report");
        //Snapshot
        if(this.currentProformaData.getStatDateFrom()!=null) this.statDateFrom.setValue(sdf.parse(this.currentProformaData.getStatDateFrom()));
        if(this.currentProformaData.getStatDateTo()!=null) this.statDateTo.setValue(sdf.parse(this.currentProformaData.getStatDateTo()));
        this.groupName.setValue(this.currentProformaData.getGroupName());
        //CPOR
        if(this.currentProformaData.getDisplaced() != null) this.displaced.setValue(this.currentProformaData.getDisplaced());
        if(this.currentProformaData.getBase() != null) this.base.setValue(this.currentProformaData.getBase());
        //TotalRooms
        if(this.currentHotel.getTotalRooms()!= null) this.totalRooms.setValue(String.valueOf(this.currentHotel.getTotalRooms()));
        //Revenue
        this.fBRevenue.setValue(this.currentProformaData.getfBRevenue());
        this.fBRevenueProfitPct.setValue(this.currentProformaData.getfBRevenueProfitPct());
        this.fBRevenueProfit.setValue(this.currentProformaData.getfBRevenueProfit());
        this.otherRevenue.setValue(this.currentProformaData.getOtherRevenue());
        this.otherRevenueProfitPct.setValue(this.currentProformaData.getOtherRevenueProfitPct());
        this.otherRevenueProfit.setValue(this.currentProformaData.getOtherRevenueProfit());
        //Part A
        if(this.currentProformaData.getPartADateFrom() != null) this.partADateFrom.setValue(sdf.parse(this.currentProformaData.getPartADateFrom()));
        if(this.currentProformaData.getPartADateTo() != null) this.partADateTo.setValue(sdf.parse(this.currentProformaData.getPartADateTo()));
        if(this.currentProformaData.getPartARmsAllWeekDays() > 0) this.partARmsAllWeekDays.setValue(this.currentProformaData.getPartARmsAllWeekDays());
        if(this.currentProformaData.getPartARmsMonday() > 0) this.partARmsMonday.setValue(this.currentProformaData.getPartARmsMonday());
        if(this.currentProformaData.getPartARmsTuesday() > 0) this.partARmsTuesday.setValue(this.currentProformaData.getPartARmsTuesday());
        if(this.currentProformaData.getPartARmsWednesday() > 0) this.partARmsWednesday.setValue(this.currentProformaData.getPartARmsWednesday());
        if(this.currentProformaData.getPartARmsThursday() > 0) this.partARmsThursday.setValue(this.currentProformaData.getPartARmsThursday());
        if(this.currentProformaData.getPartARmsFriday() > 0) this.partARmsFriday.setValue(this.currentProformaData.getPartARmsFriday());
        if(this.currentProformaData.getPartARmsSaturday() > 0) this.partARmsSaturday.setValue(this.currentProformaData.getPartARmsSaturday());
        if(this.currentProformaData.getPartARmsSunday() > 0) this.partARmsSunday.setValue(this.currentProformaData.getPartARmsSunday());
        if(this.currentProformaData.getPartAADRAllWeekDays() > 0) this.partAADRAllWeekDays.setValue(this.currentProformaData.getPartAADRAllWeekDays());
        if(this.currentProformaData.getPartAADRMonday() > 0) this.partAADRMonday.setValue(this.currentProformaData.getPartAADRMonday());
        if(this.currentProformaData.getPartAADRTuesday() > 0) this.partAADRTuesday.setValue(this.currentProformaData.getPartAADRTuesday());
        if(this.currentProformaData.getPartAADRWednesday() > 0) this.partAADRWednesday.setValue(this.currentProformaData.getPartAADRWednesday());
        if(this.currentProformaData.getPartAADRThursday() > 0) this.partAADRThursday.setValue(this.currentProformaData.getPartAADRThursday());
        if(this.currentProformaData.getPartAADRFriday() > 0) this.partAADRFriday.setValue(this.currentProformaData.getPartAADRFriday());
        if(this.currentProformaData.getPartAADRSaturday() > 0) this.partAADRSaturday.setValue(this.currentProformaData.getPartAADRSaturday());
        if(this.currentProformaData.getPartAADRSunday() > 0) this.partAADRSunday.setValue(this.currentProformaData.getPartAADRSunday());
        //Part B
        if(this.currentProformaData.getPartBDateFrom() != null) this.partBDateFrom.setValue(sdf.parse(this.currentProformaData.getPartBDateFrom()));
        if(this.currentProformaData.getPartBDateTo() != null) this.partBDateTo.setValue(sdf.parse(this.currentProformaData.getPartBDateTo()));
        if(this.currentProformaData.getPartBRmsAllWeekDays() > 0) this.partBRmsAllWeekDays.setValue(this.currentProformaData.getPartBRmsAllWeekDays());
        if(this.currentProformaData.getPartBRmsMonday() > 0) this.partBRmsMonday.setValue(this.currentProformaData.getPartBRmsMonday());
        if(this.currentProformaData.getPartBRmsTuesday() > 0) this.partBRmsTuesday.setValue(this.currentProformaData.getPartBRmsTuesday());
        if(this.currentProformaData.getPartBRmsWednesday() > 0) this.partBRmsWednesday.setValue(this.currentProformaData.getPartBRmsWednesday());
        if(this.currentProformaData.getPartBRmsThursday() > 0) this.partBRmsThursday.setValue(this.currentProformaData.getPartBRmsThursday());
        if(this.currentProformaData.getPartBRmsFriday() > 0) this.partBRmsFriday.setValue(this.currentProformaData.getPartBRmsFriday());
        if(this.currentProformaData.getPartBRmsSaturday() > 0) this.partBRmsSaturday.setValue(this.currentProformaData.getPartBRmsSaturday());
        if(this.currentProformaData.getPartBRmsSunday() > 0) this.partBRmsSunday.setValue(this.currentProformaData.getPartBRmsSunday());
        if(this.currentProformaData.getPartBADRAllWeekDays() > 0) this.partBADRAllWeekDays.setValue(this.currentProformaData.getPartBADRAllWeekDays());
        if(this.currentProformaData.getPartBADRMonday() > 0) this.partBADRMonday.setValue(this.currentProformaData.getPartBADRMonday());
        if(this.currentProformaData.getPartBADRTuesday() > 0) this.partBADRTuesday.setValue(this.currentProformaData.getPartBADRTuesday());
        if(this.currentProformaData.getPartBADRWednesday() > 0) this.partBADRWednesday.setValue(this.currentProformaData.getPartBADRWednesday());
        if(this.currentProformaData.getPartBADRThursday() > 0) this.partBADRThursday.setValue(this.currentProformaData.getPartBADRThursday());
        if(this.currentProformaData.getPartBADRFriday() > 0) this.partBADRFriday.setValue(this.currentProformaData.getPartBADRFriday());
        if(this.currentProformaData.getPartBADRSaturday() > 0) this.partBADRSaturday.setValue(this.currentProformaData.getPartBADRSaturday());
        if(this.currentProformaData.getPartBADRSunday() > 0) this.partBADRSunday.setValue(this.currentProformaData.getPartBADRSunday());        
        //Refresh total value for Revenue
        this.updateRevenue();
        
	}
	
	@Listen("onCreate=#forecastSnapshot;")
	public void selectSnapshot(){
	
		Snapshot snapshotSaved = new Snapshot();
		if(this.currentProformaData.getForecastSnaphotID() == 0)
			this.forecastSnapshot.setSelectedIndex(0);
		else {
			snapshotSaved.setSnapshotId(currentProformaData.getForecastSnaphotID());
			int index = (((ListModelList<Snapshot>)this.snapshotsForecastModel).indexOf(snapshotSaved));
			this.forecastSnapshot.setSelectedIndex(index);	
		}				
	}
	
    @Listen("onChange = #partARmsAllWeekDays")
    public void getPartARmsAllWeekDays() {
    	
    	this.partARmsMonday.setText(partARmsAllWeekDays.getText());
    	this.partARmsTuesday.setText(partARmsAllWeekDays.getText());
    	this.partARmsWednesday.setText(partARmsAllWeekDays.getText());
    	this.partARmsThursday.setText(partARmsAllWeekDays.getText());
    	this.partARmsFriday.setText(partARmsAllWeekDays.getText());
    	this.partARmsSaturday.setText(partARmsAllWeekDays.getText());
    	this.partARmsSunday.setText(partARmsAllWeekDays.getText());
    }
    
    @Listen("onChange = #partAADRAllWeekDays")
    public void getPartAADRAllWeekDays() {
    	
    	this.partAADRMonday.setText(partAADRAllWeekDays.getText());
    	this.partAADRTuesday.setText(partAADRAllWeekDays.getText());
    	this.partAADRWednesday.setText(partAADRAllWeekDays.getText());
    	this.partAADRThursday.setText(partAADRAllWeekDays.getText());
    	this.partAADRFriday.setText(partAADRAllWeekDays.getText());
    	this.partAADRSaturday.setText(partAADRAllWeekDays.getText());
    	this.partAADRSunday.setText(partAADRAllWeekDays.getText());
    }
    
    @Listen("onChange = #partBRmsAllWeekDays")
    public void getPartBRmsAllWeekDays() {
    	
    	this.partBRmsMonday.setText(partBRmsAllWeekDays.getText());
    	this.partBRmsTuesday.setText(partBRmsAllWeekDays.getText());
    	this.partBRmsWednesday.setText(partBRmsAllWeekDays.getText());
    	this.partBRmsThursday.setText(partBRmsAllWeekDays.getText());
    	this.partBRmsFriday.setText(partBRmsAllWeekDays.getText());
    	this.partBRmsSaturday.setText(partBRmsAllWeekDays.getText());
    	this.partBRmsSunday.setText(partBRmsAllWeekDays.getText());
    }
    
    @Listen("onChange = #partBADRAllWeekDays")
    public void getPartBADRAllWeekDays() {
    	
    	this.partBADRMonday.setText(partBADRAllWeekDays.getText());
    	this.partBADRTuesday.setText(partBADRAllWeekDays.getText());
    	this.partBADRWednesday.setText(partBADRAllWeekDays.getText());
    	this.partBADRThursday.setText(partBADRAllWeekDays.getText());
    	this.partBADRFriday.setText(partBADRAllWeekDays.getText());
    	this.partBADRSaturday.setText(partBADRAllWeekDays.getText());
    	this.partBADRSunday.setText(partBADRAllWeekDays.getText());
    }
    
    @Listen("onChange = #fBRevenue, #fBRevenueProfitPct, #otherRevenue, #otherRevenueProfitPct")
    public void updateRevenue() {
    	
    	if(this.fBRevenue.getValue() !=null && this.fBRevenueProfitPct.getValue() !=null)
    		this.fBRevenueProfit.setValue((this.fBRevenue.intValue() * this.fBRevenueProfitPct.intValue())/100);
    	else
    		this.fBRevenueProfit.setValue(0);
    	
    	if(this.otherRevenue.getValue() !=null && this.otherRevenueProfitPct.getValue() !=null)
    		this.otherRevenueProfit.setValue((this.otherRevenue.intValue() * this.otherRevenueProfitPct.intValue())/100);
    	else
    		this.otherRevenueProfit.setValue(0);
    }
    

    @Listen("onClick = #exitReportBtn")
    public void showModal(Event e) {
    	proformaDialog.detach();
    }
    
    @Listen("onClick = #loadReportBtn")
    public void loadReportBtn() throws WrongValueException, ParseException {
    	DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
    	//Collect settings before loading report.
    	String result = this.validateUserPreferences();
    	if(result!=null)
    	{Messagebox.show(result); return;}
    	this.updateProformaDataObj();
    	//Returns a new obj with the proper lists to fill out the Grid (lstReportData, lstSummaryReportData)    	
    	this.currentProformaData = this.proformaDAO.LoadProFormaData(this.currentProformaData, this.currentProformaData.getForecastSnaphotID(), sdf.format(statDateFrom.getValue()), sdf.format(statDateTo.getValue()), this.currentCustomer.getCustomerId());
    	
    	List<ReportData> gridList = new ArrayList<ReportData>();    	
    	
    	List<ReportData> summaryList = currentProformaData.getLstSummaryReportData();
    	List<ReportData> reportList = currentProformaData.getLstReportData();
    	
    	gridList.addAll(summaryList);    	
    	gridList.addAll(reportList);
    	    	
    	ListModel<ReportData> gridLisDataModel = new ListModelList<ReportData>(gridList);
    	
    	proformaGrid.setModel(gridLisDataModel);
    	proformaGrid.setRowRenderer(new ProFormaRender());
    	printReportBtn.setDisabled(false);
    	Messagebox.show("The empty Room/ADR inputs will appear as 0 in the report.");
    	
    }
       
    @Listen("onClick = #printReportBtn")
    public void printReportBtn() throws JRException, WrongValueException, ParseException, IOException {
    	
    	//Update all userProforma object before sending it to the report
    	this.updateProformaDataObj();
    	UserPreferencesProforma upp = this.currentProformaData;
    	
    	String webAppDir = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/");
    	String reportRelativeDir = "WEB-INF" + File.separator + "reports" + File.separator + "proforma" + File.separator;
    	
    	JasperReport jr = (JasperReport)JRLoader.loadObject(new File(webAppDir + reportRelativeDir + "ProForma.jasper"));    	
    	
    	Map<String, Object> parms = new HashMap<String, Object>();
    	parms.put("groupName", upp.getGroupName());
    	parms.put("totalRooms", this.currentHotel.getTotalRooms());
    	parms.put("forecastSnaphotID", upp.getForecastSnaphotID());
    	parms.put("statDateFrom", upp.getStatDateFrom());
    	parms.put("statDateTo", upp.getStatDateTo());
    	parms.put("SUBREPORT_DIR", webAppDir + reportRelativeDir); // La ruta de los .jasper

    	JasperPrint jasperPrint = JasperFillManager.fillReport(jr, parms, new JRBeanCollectionDataSource(Arrays.asList(upp)));
    	byte[] baos = JasperExportManager.exportReportToPdf(jasperPrint);
    	
    	if (baos.length > 0) {
    		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    		ByteArrayOutputStream baosObj = new ByteArrayOutputStream();
    		baosObj.write(baos);
    		Sessions.getCurrent().setAttribute(ReportServiceProvider.FILE_TYPE, "application/pdf");
    		Sessions.getCurrent().setAttribute(ReportServiceProvider.FILE_HEADER, "filename=" + dateFormat.format(Calendar.getInstance().getTime()) + "-" +"ProFormaReport" +".pdf");
			Sessions.getCurrent().setAttribute(ReportServiceProvider.REPORT_STREAM, baosObj);
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
    	this.updateProformaDataObj();
    	if(currentProformaData.getUserPreferencesId() == 0)
    		proformaDAO.insertProformaPreferences(currentProformaData);
    	else
    		proformaDAO.updateProformaPreferences(currentProformaData);
    	Messagebox.show("Preferences saved properly.");
    }
    
    private String validateUserPreferences() {
    	
    	String result;
    	Date pBFrom = partBDateFrom.getValue();
    	Date pBTo = partBDateTo.getValue();
    	Date pAFrom = partADateFrom.getValue();
    	Date pATo = partADateTo.getValue();
    	
    	boolean rangeAFrom =  !(pAFrom.after(pBFrom) && pAFrom.before(pBTo));
    	boolean rangeATo =  !(pATo.after(pBFrom) && pATo.before(pBTo));
    	boolean rangeBFrom =  !(pBFrom.after(pAFrom) && pBFrom.before(pATo));
    	boolean rangeBTo =  !(pBTo.after(pAFrom) && pBTo.before(pATo));
    	
    	if(rangeAFrom && rangeATo && rangeBFrom && rangeBTo)
    		result = null;
    	else
    		result =  "PartA Range must not be included in PartB Range.";	
    	
    	boolean snapshotSelected = this.forecastSnapshot.getSelectedIndex() > 0;
    	
    	if(snapshotSelected) result = null;
    	else result = "Please select a valid snapshot.";
    	
    	return result;
    	
    	
    }
    
    private void updateProformaDataObj() throws WrongValueException, ParseException {
    	
    	DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
    	//Snapshot
    	this.currentProformaData.setForecastSnaphotID(((Integer)this.forecastSnapshot.getSelectedItem().getValue()).intValue());
    	if(this.statDateFrom.getValue()!=null) this.currentProformaData.setStatDateFrom(sdf.format(statDateFrom.getValue()));
    	else
    		this.currentProformaData.setStatDateFrom(null);
    		
    	if(this.statDateTo.getValue()!=null) this.currentProformaData.setStatDateTo(sdf.format(statDateTo.getValue()));
    	else
    		this.currentProformaData.setStatDateTo(null);
    	this.currentProformaData.setGroupName(groupName.getText());
        //CPOR
    	this.currentProformaData.setDisplaced(this.displaced.getValue());
    	this.currentProformaData.setBase(this.base.getValue());
        //Revenue
        this.currentProformaData.setfBRevenue(fBRevenue.getValue() !=null ? fBRevenue.getValue() : 0);
        this.currentProformaData.setfBRevenueProfitPct(fBRevenueProfitPct.getValue() !=null ? fBRevenueProfitPct.getValue() : 0);
        this.currentProformaData.setfBRevenueProfit(fBRevenueProfit.getValue() !=null ? fBRevenueProfit.getValue() : 0 );
        this.currentProformaData.setOtherRevenue(otherRevenue.getValue() !=null ? otherRevenue.getValue() : 0);
        this.currentProformaData.setOtherRevenueProfitPct(otherRevenueProfitPct.getValue() !=null ? otherRevenueProfitPct.getValue() : 0);
        this.currentProformaData.setOtherRevenueProfit(otherRevenueProfit.getValue() !=null ? otherRevenueProfit.getValue() : 0);       
        //Part A
        if(this.partADateFrom.getValue()!=null) this.currentProformaData.setPartADateFrom(sdf.format(partADateFrom.getValue()));
    	else
    		this.currentProformaData.setPartADateFrom(null);
        if(this.partADateTo.getValue()!=null) this.currentProformaData.setPartADateTo(sdf.format(partADateTo.getValue()));
    	else
    		this.currentProformaData.setPartADateTo(null);
        this.currentProformaData.setPartARmsAllWeekDays(partARmsAllWeekDays.getValue() != null ? partARmsAllWeekDays.getValue() : 0);
        this.currentProformaData.setPartARmsMonday(partARmsMonday.getValue() != null ? partARmsMonday.getValue() : 0);
        this.currentProformaData.setPartARmsTuesday(partARmsTuesday.getValue() != null ? partARmsTuesday.getValue() : 0);
        this.currentProformaData.setPartARmsWednesday(partARmsWednesday.getValue() != null ? partARmsWednesday.getValue() : 0);
        this.currentProformaData.setPartARmsThursday(partARmsThursday.getValue() != null ? partARmsThursday.getValue() : 0);
        this.currentProformaData.setPartARmsFriday(partARmsFriday.getValue() != null ? partARmsFriday.getValue() : 0);
        this.currentProformaData.setPartARmsSaturday(partARmsSaturday.getValue() != null ? partARmsSaturday.getValue() : 0);
        this.currentProformaData.setPartARmsSunday(partARmsSunday.getValue() != null ? partARmsSunday.getValue() : 0);
        this.currentProformaData.setPartAADRAllWeekDays(partAADRAllWeekDays.getValue() != null ? partAADRAllWeekDays.getValue() : 0);
        this.currentProformaData.setPartAADRMonday(partAADRMonday.getValue() != null ? partAADRMonday.getValue() : 0);
        this.currentProformaData.setPartAADRTuesday(partAADRTuesday.getValue() != null ? partAADRTuesday.getValue() : 0);
        this.currentProformaData.setPartAADRWednesday(partAADRWednesday.getValue() != null ? partAADRWednesday.getValue() : 0);
        this.currentProformaData.setPartAADRThursday(partAADRThursday.getValue() != null ? partAADRThursday.getValue() : 0);
        this.currentProformaData.setPartAADRFriday(partAADRFriday.getValue() != null ? partAADRFriday.getValue() : 0);
        this.currentProformaData.setPartAADRSaturday(partAADRSaturday.getValue() != null ? partAADRSaturday.getValue() : 0);
        this.currentProformaData.setPartAADRSunday(partAADRSunday.getValue() != null ? partAADRSunday.getValue() : 0);
    	//Part B
        if(this.partBDateFrom.getValue()!=null) this.currentProformaData.setPartBDateFrom(sdf.format(partBDateFrom.getValue()));
    	else
    		this.currentProformaData.setPartBDateFrom(null);
        if(this.partBDateTo.getValue()!=null) this.currentProformaData.setPartBDateTo(sdf.format(partBDateTo.getValue()));
    	else
    		this.currentProformaData.setPartBDateTo(null);
        this.currentProformaData.setPartBRmsAllWeekDays(partBRmsAllWeekDays.getValue() != null ? partBRmsAllWeekDays.getValue() : 0);
        this.currentProformaData.setPartBRmsMonday(partBRmsMonday.getValue() != null ? partBRmsMonday.getValue() : 0);
        this.currentProformaData.setPartBRmsTuesday(partBRmsTuesday.getValue() != null ? partBRmsTuesday.getValue() : 0);
        this.currentProformaData.setPartBRmsWednesday(partBRmsWednesday.getValue() != null ? partBRmsWednesday.getValue() : 0);
        this.currentProformaData.setPartBRmsThursday(partBRmsThursday.getValue() != null ? partBRmsThursday.getValue() : 0);
        this.currentProformaData.setPartBRmsFriday(partBRmsFriday.getValue() != null ? partBRmsFriday.getValue() : 0);
        this.currentProformaData.setPartBRmsSaturday(partBRmsSaturday.getValue() != null ? partBRmsSaturday.getValue() : 0);
        this.currentProformaData.setPartBRmsSunday(partBRmsSunday.getValue() != null ? partBRmsSunday.getValue() : 0);
        this.currentProformaData.setPartBADRAllWeekDays(partBADRAllWeekDays.getValue() != null ? partBADRAllWeekDays.getValue() : 0);
        this.currentProformaData.setPartBADRMonday(partBADRMonday.getValue() != null ? partBADRMonday.getValue() : 0);
        this.currentProformaData.setPartBADRTuesday(partBADRTuesday.getValue() != null ? partBADRTuesday.getValue() : 0);
        this.currentProformaData.setPartBADRWednesday(partBADRWednesday.getValue() != null ? partBADRWednesday.getValue() : 0);
        this.currentProformaData.setPartBADRThursday(partBADRThursday.getValue() != null ? partBADRThursday.getValue() : 0);
        this.currentProformaData.setPartBADRFriday(partBADRFriday.getValue() != null ? partBADRFriday.getValue() : 0);
        this.currentProformaData.setPartBADRSaturday(partBADRSaturday.getValue() != null ? partBADRSaturday.getValue() : 0);
        this.currentProformaData.setPartBADRSunday(partBADRSunday.getValue() != null ? partBADRSunday.getValue() : 0);
        //Rewrite Hotel, User, and Company in case this is the first time saving settings.
        this.currentProformaData.setUserId(authService.getUserData().getUserId());
        this.currentProformaData.setHotelId(this.currentHotel.getHotelId());
        this.currentProformaData.setCompanyId(this.currentCustomer.getCustomerId());
    }

	/**
	 * @return the snapshotsForecastModel
	 */
	public ListModel<Snapshot> getSnapshotsForecastModel() {
		return snapshotsForecastModel;
	}

	/**
	 * @param snapshotsForecastModel the snapshotsForecastModel to set
	 */
	public void setSnapshotsForecastModel(ListModel<Snapshot> snapshotsForecastModel) {
		this.snapshotsForecastModel = snapshotsForecastModel;
	}
    
   
}
