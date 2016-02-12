package com.hrr3.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Auxhead;
import org.zkoss.zul.Auxheader;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Frozen;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Style;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.hrr3.authentication.AuthenticationServiceHRR3Impl;
import com.hrr3.entity.Customer;
import com.hrr3.entity.Hotel;
import com.hrr3.entity.trendTool.TrendTool;
import com.hrr3.entity.trendTool.TrendToolLists;
import com.hrr3.entity.trendTool.TrendToolProperties;
import com.hrr3.model.TrendToolDAO;
import com.hrr3.services.AuthenticationService;
import com.hrr3.services.ReportServiceProvider;


public class TrendToolController extends ListboxArrowKeyController  {
	private static final long serialVersionUID = 1L;
	
	@Wire
	Style customStyle;
	@Wire
    Window trendDialog;
	@Wire
	private Datebox	trendNewStatDate;
	@Wire
	private Datebox	trendNewEndDate;
	@Wire
	private Datebox	trendOldStatDate;
	@Wire
	private Datebox	trendOldEndDate;
	@Wire
	private Datebox	forecastBaseStatDate;
	@Wire
	private Datebox	forecastBaseEndDate;
		
	@Wire
	Auxhead auxHead;
	@Wire
	Listhead listHead;
	
	//Buttons
	@Wire
	private Button printGridBtn;
	@Wire
	private Button advanceBtn;
	@Wire
	private Button pasteBtn;
	@Wire
	private Button calculateTrendBtn;	
	
	private AuthenticationService authService;
	private Customer currentCustomer;
	private Hotel currentHotel;
	private TrendToolLists currentTrendToolData;
	private TrendToolDAO trendDAO;
	private HashMap<String, TrendToolProperties> propsMap;
	protected Component mainApp;
	private int colsToPaste = 0;
	
	public static int LOAD_TREND_DATA = 1;
	public static int CALCULATE_TREND_DATA = 2;
	
	public TrendToolController() {
		super(8);//Eight  elements to avoid reading in the arow key movement.
		authService = new AuthenticationServiceHRR3Impl();
		
		if(authService.getUserData().getCurrentHotel() == null || authService.getUserData().getCurrentHotel().getHotelId() ==null || authService.getUserData().getCurrentHotel().getHotelId() < 1)
		{Messagebox.show("Please select a valid Hotel and return to this section."); return;}
					
		currentCustomer = authService.getUserData().getCurrentCustomer();		
		currentHotel = authService.getUserData().getCurrentHotel();	      
		this.trendDAO = new TrendToolDAO(currentHotel);
		
		
	}
	
	public void doAfterCompose(Component comp) throws Exception {		
		super.doAfterCompose(comp);
		
		Date baseDate = new Date();
		
		Calendar calendarCurrentYear = Calendar.getInstance();
		calendarCurrentYear.setTime(baseDate);
		calendarCurrentYear.add(Calendar.MONTH, -1);
		calendarCurrentYear.set(Calendar.DAY_OF_MONTH, 1);		
		Date startDateCurrentYear = calendarCurrentYear.getTime();
		
		Calendar calendarOldYear = Calendar.getInstance();
		calendarOldYear.setTime(baseDate);
		calendarOldYear.add(Calendar.MONTH, -1);
		calendarOldYear.set(Calendar.DAY_OF_MONTH, 1);		
		calendarOldYear.add(Calendar.YEAR, -1);	
		Date startDateOldYear = calendarOldYear.getTime();
		
		this.trendNewStatDate.setValue(startDateCurrentYear);		
		this.trendOldStatDate.setValue(startDateOldYear);		
		this.forecastBaseStatDate.setValue(startDateCurrentYear);	
		
		calendarCurrentYear.add(Calendar.MONTH, 1);
		calendarCurrentYear.set(Calendar.DAY_OF_MONTH, 1);
		Date endDateCurrentYear = calendarCurrentYear.getTime();
		
		calendarOldYear.add(Calendar.MONTH, 1);
		calendarOldYear.set(Calendar.DAY_OF_MONTH, 1);
		Date endDateOldYear = calendarOldYear.getTime();
		
		this.trendNewEndDate.setValue(endDateCurrentYear);
		this.trendOldEndDate.setValue(endDateOldYear);
		this.forecastBaseEndDate.setValue(endDateCurrentYear);
		
		this.customStyle.setContent("tr.z-row td.z-row-inner, tr.z-row td.z-cell, tr.z-group td.z-group-inner, tr.z-groupfoot td.z-groupfoot-inner { " +
				"padding: 0px 0px 0px 0px; " +
				"}" +		
				" tr.z-listitem, tr.z-listitem a, tr.z-listitem a:visited { " +
				"   background-color: #EDEDF4; " +
				"   font-size:10px; " +
				"   font-weight: normal; " +
				"   color: black; " +
				"   text-decoration: none; " +
				"} " +
				"tr.z-listbox-odd { " +
				"   background-color: " + this.authService.getUserData().getScreenPreferences().getRowColor() + "; " +
				"} " +
				"tr.z-listitem-seld { " +
				"   background-color: white; " +
				"   color: orange; " +
				"   border: 1px solid cyan; " +
				"} " +
				"		.z-textbox, .z-decimalbox, .z-intbox, .z-longbox, .z-doublebox, .z-auxheader-cnt, div.z-column-cnt, div.z-row-cnt, .z-label { " +
				"		font-size: 11px; " +
				"		} " +
				"div.z-listbox-body .z-listcell { " +
			    "padding: 1px; }");		
		
	}
	

    @Listen("onClick = #exitBtn")
    public void showModal(Event e) {
    	trendDialog.detach();
    }
    
    @Listen("onClick=#pasteBtn")
	public void pasteForecast(){
		
		//Mostrar popup
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("currentTrendToolData", this.currentTrendToolData);
		Window popup = ((Window) Executions
				.createComponents(
						"/application/products/rm3/input/trend-tool/paste-forecast-popup.zul",
						this.getSelf(), map));

		popup.addEventListener("onPasteForecastClose",
				new EventListener<Event>() {
					public void onEvent(Event event) throws Exception {
						//Do something
					}
				});

		popup.doModal();
	}
    
    @Listen("onClick=#advanceBtn")
	public void advancedPaste(){
		
    	if(this.colsToPaste <= 0)
    	{Messagebox.show("Please select the values you want to be pasted."); return;}
    		
		//Mostrar popup
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("currentTrendToolData", this.currentTrendToolData);
		Window popup = ((Window) Executions
				.createComponents(
						"/application/products/rm3/input/trend-tool/advanced-paste-popup.zul",
						this.getSelf(), map));

		popup.addEventListener("onAdvancedPasteClose",
				new EventListener<Event>() {
					public void onEvent(Event event) throws Exception {
						//Do something
					}
				});

		popup.doModal();
	}
    
    private  EventListener<InputEvent> overwriteOCCListener = new EventListener<InputEvent>() {		
	    public void onEvent(InputEvent event) throws ParseException {
	    	
	    	Textbox occTxt = (Textbox) event.getTarget();	
	    	TrendToolProperties ovrProps = (TrendToolProperties)occTxt.getAttribute("customProperties");
	    	TrendToolProperties trendProps = propsMap.get(TrendToolProperties.buildKey("TREND", ovrProps.getDay(), ovrProps.getSegmentData().getSegmentId()));
	    	//Save OCC values for OVR and TREND
	    	trendProps.setOccValue(Integer.parseInt(occTxt.getText()));
	    	ovrProps.setOccValue(Integer.parseInt(occTxt.getText()));
	    	//OccWeek = (forecastBaseline * (forecastTrend + 100) / 100)
	    	TrendToolProperties weekProps = propsMap.get(TrendToolProperties.buildKey("WEEK", ovrProps.getDay(), ovrProps.getSegmentData().getSegmentId()));
	    	TrendToolProperties baseProps = propsMap.get(TrendToolProperties.buildKey("BASE", ovrProps.getDay(), ovrProps.getSegmentData().getSegmentId()));
	    	float enumPart = baseProps.getOccValue() * (Integer.parseInt(occTxt.getText()) + 100);
	    	float occWeek = enumPart/100;
	    	//Save OCC value for WEEK
	    	weekProps.getOccTxt().setText(String.valueOf(occWeek));
	    	weekProps.setOccValue(new BigDecimal(occWeek).setScale(0, BigDecimal.ROUND_CEILING).intValue());	    	
	    	//Trend/Ovr with% and value
	    	trendProps.getOccTxt().setText(occTxt.getText() + "%");	
	    	ovrProps.getOccTxt().setText(occTxt.getText()+"%");
	    	
	    }	   
	};
	
	private  EventListener<InputEvent> overwriteADRListener = new EventListener<InputEvent>() {		
	    public void onEvent(InputEvent event) throws ParseException {
	    	
	    	Textbox adrTxt = (Textbox) event.getTarget();	
	    	TrendToolProperties ovrProps = (TrendToolProperties)adrTxt.getAttribute("customProperties");
	    	TrendToolProperties trendProps = propsMap.get(TrendToolProperties.buildKey("TREND", ovrProps.getDay(), ovrProps.getSegmentData().getSegmentId()));
	    	//Save OCC values for OVR and TREND
	    	trendProps.setAdrValue(new BigDecimal(adrTxt.getText()));
	    	ovrProps.setAdrValue(new BigDecimal(adrTxt.getText()));	    	
	    	//OccWeek = (forecastBaseline * (forecastTrend + 100) / 100)
	    	TrendToolProperties weekProps = propsMap.get(TrendToolProperties.buildKey("WEEK", ovrProps.getDay(), ovrProps.getSegmentData().getSegmentId()));
	    	TrendToolProperties baseProps = propsMap.get(TrendToolProperties.buildKey("BASE", ovrProps.getDay(), ovrProps.getSegmentData().getSegmentId()));
	    	float enumPart = baseProps.getAdrValue().floatValue() * (Integer.parseInt(adrTxt.getText()) + 100);
	    	float adrWeek = enumPart/100;
	    	//Save ADR value for WEEK
	    	weekProps.getAdrTxt().setText(String.valueOf(adrWeek));
	    	weekProps.setAdrValue(new BigDecimal(adrWeek));
	    	//Trend/Ovr with%
	    	trendProps.getAdrTxt().setText(adrTxt.getText() + "%");
	    	ovrProps.getAdrTxt().setText(adrTxt.getText()+"%");	
	    	
	    }	   
	};
	
	private  EventListener<Event> overwriteClickListener = new EventListener<Event>() {		
	    public void onEvent(Event event) throws ParseException {
	    	
	    	Textbox txt = (Textbox) event.getTarget();
	    	
	    	if(event.getName().equalsIgnoreCase("onClick")) {
	    		txt.setText(txt.getText().replace("%", ""));
	    		txt.select();
	    	}
	    	
	    	else if(event.getName().equalsIgnoreCase("onBlur") && txt.getText().length() > 1 && !txt.getText().endsWith("%")) {
	    		txt.setText(txt.getText() + "%");
	    	}	    	
	    	
	    }	   
	};
	
	private  EventListener<Event> advancedOCCListener = new EventListener<Event>() {		
	    public void onEvent(Event event) throws ParseException {
	    	
	    	Textbox occTxt = (Textbox) event.getTarget();	
	    	TrendToolProperties advProps = (TrendToolProperties)occTxt.getAttribute("customProperties");
	    	if(occTxt.getText() == null || occTxt.getValue().isEmpty()){
	    		occTxt.setText("x");
	    		advProps.setOccValue(0);
	    		colsToPaste++;
	    	}
	    	
	    	else {
	    		occTxt.setText("");
	    		advProps.setOccValue(null);
	    		colsToPaste--;
	    	}    	
	    }	   
	};
	
	private  EventListener<Event> advancedADRListener = new EventListener<Event>() {		
	    public void onEvent(Event event) throws ParseException {
	    	
	    	Textbox adrTxt = (Textbox) event.getTarget();	
	    	TrendToolProperties advProps = (TrendToolProperties)adrTxt.getAttribute("customProperties");
	    	if(adrTxt.getText() == null || adrTxt.getValue().isEmpty()){
	    		adrTxt.setText("x");
	    		advProps.setAdrValue(new BigDecimal(0));
	    	}
	    	
	    	else {
	    		adrTxt.setText("");
	    		advProps.setOccValue(null);
	    	}    	
	    }	   
	};
	
	private  EventListener<Event> weekOCCListener = new EventListener<Event>() {		
	    public void onEvent(Event event) throws ParseException {
	    	
	    	Textbox occTxt = (Textbox) event.getTarget();	
	    	TrendToolProperties props = (TrendToolProperties)occTxt.getAttribute("customProperties");
	    	props.setOccValue(Integer.parseInt(occTxt.getText()));
	    }	   
	};
	
	private  EventListener<Event> weekADRListener = new EventListener<Event>() {		
	    public void onEvent(Event event) throws ParseException {
	    	
	    	Textbox adrTxt = (Textbox) event.getTarget();	
	    	TrendToolProperties props = (TrendToolProperties)adrTxt.getAttribute("customProperties");
	    	props.setAdrValue(new BigDecimal(Integer.parseInt(adrTxt.getText())));
	    }	   
	};
	
	 @Listen("onClick = #trendGO")
	    public void loadTrendDataOnClick() throws WrongValueException, ParseException {		
		  
		    System.out.print("Go button clicked");
		 
		 	mainApp = this.trendDialog;		 	
			Clients.showBusy(mainApp,"Loading Trend Data...");
			mainApp.addEventListener(Events.ON_CLIENT_INFO, new EventListener<Event>() {
		        @Override
		        public void onEvent(Event event) throws Exception {
		        	System.out.println("*********** LOAD TREND DATA EVENT - BEGIN ***************");
		        	refreshListData(TrendToolController.LOAD_TREND_DATA);
		        	System.out.println("*********** LOAD TREND DATA EVENT - END ***************");
		            Clients.clearBusy(mainApp);		
		            mainApp.removeEventListener(Events.ON_CLIENT_INFO, this);
		        }
		    });
			
			Events.echoEvent(Events.ON_CLIENT_INFO, mainApp, null);
	 }
	 
	 @Listen("onClick = #calculateTrendBtn")
	    public void calculateTrendDataOnClick() throws WrongValueException, ParseException {		 
		 mainApp = this.trendDialog;		 	
			Clients.showBusy(mainApp,"Calculating Trend Data...");
			mainApp.addEventListener(Events.ON_CLIENT_INFO, new EventListener<Event>() {
		        @Override
		        public void onEvent(Event event) throws Exception {
		        	System.out.println("*********** CALCULATE TREND DATA EVENT - BEGIN ***************");
		        	refreshListData(TrendToolController.CALCULATE_TREND_DATA);
		        	System.out.println("*********** CALCULATE TREND DATA EVENT - END ***************");
		            Clients.clearBusy(mainApp);		   
		            mainApp.removeEventListener(Events.ON_CLIENT_INFO, this);
		        }
		    });
			
			Events.echoEvent(Events.ON_CLIENT_INFO, mainApp, null);
	 }
	 
	 @Listen("onClick = #printGridBtn")
	    public void printGridBtn() throws JRException, WrongValueException, ParseException, IOException {
		 DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 	    	
	    	String webAppDir = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/");
	    	String reportRelativeDir = "WEB-INF" + File.separator + "reports" + File.separator + "trend" + File.separator;
	    	
	    	JasperReport jr = (JasperReport)JRLoader.loadObject(new File(webAppDir + reportRelativeDir + "TrendForecast.jasper"));    	
	    	
	    	Map<String, Object> parms = new HashMap<String, Object>();
	    	parms.put("trendNewFrom", sdf.format(this.trendNewStatDate.getValue()));
	    	parms.put("trendNewTo", sdf.format(this.trendNewEndDate.getValue()));
	    	parms.put("trendOldFrom", sdf.format(this.trendOldStatDate.getValue()));
	    	parms.put("trendOldTo", sdf.format(this.trendOldEndDate.getValue()));
	    	parms.put("forecastBaseFrom", sdf.format(this.forecastBaseStatDate.getValue()));
	    	parms.put("forecastBaseTo", sdf.format(this.forecastBaseEndDate.getValue()));
	    	parms.put("SUBREPORT_DIR", webAppDir + reportRelativeDir); // La ruta de los .jasper

	    	JasperPrint jasperPrint = JasperFillManager.fillReport(jr, parms, new JRBeanCollectionDataSource(Arrays.asList(this.currentTrendToolData)));
	    	//byte[] baos = new JRXlsExporter().
	                
	    	JRXlsExporter exporter = new JRXlsExporter();
	    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    	SimpleOutputStreamExporterOutput output = new SimpleOutputStreamExporterOutput(baos);
	    	exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
	    	exporter.setExporterOutput(output);
	    	SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
	    	configuration.setOnePagePerSheet(false);
	    	configuration.setDetectCellType(true);
	    	configuration.setCollapseRowSpan(false);
	    	exporter.setConfiguration(configuration);
	    	exporter.exportReport();
	    	OutputStream os = output.getOutputStream();
	    	
	    	if (os != null) {
	    		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    		Sessions.getCurrent().setAttribute(ReportServiceProvider.FILE_TYPE, "application/msexcel");
	    		Sessions.getCurrent().setAttribute(ReportServiceProvider.FILE_HEADER, "filename=" + dateFormat.format(Calendar.getInstance().getTime()) + "-" +"TrendToolReport" +".xls");
				Sessions.getCurrent().setAttribute(ReportServiceProvider.REPORT_STREAM, os);
				Executions.getCurrent().sendRedirect("/reportserviceprovider", "_blank");
			}
	    }
    
    public void refreshListData(int option) throws WrongValueException, ParseException {
    	
    	this.genericListBox.getChildren().clear();//Clear it after each call
    	this.listHead = new Listhead();   
    	this.auxHead = new Auxhead();   
    	propsMap = new HashMap<String, TrendToolProperties>();    	
    	DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
    	
    	if(option == LOAD_TREND_DATA){
    		this.currentTrendToolData = this.trendDAO.populateMainLists(sdf.format(trendNewStatDate.getValue()), sdf.format(trendNewEndDate.getValue()),sdf.format(trendOldStatDate.getValue()), sdf.format(trendOldEndDate.getValue()),sdf.format(forecastBaseStatDate.getValue()), sdf.format(forecastBaseEndDate.getValue()), this.currentCustomer.getCustomerId());
    	    
    	}	
       else if(option == CALCULATE_TREND_DATA){    		
    		this.currentTrendToolData.setLstForecastTrend(this.trendDAO.populateForecastTrendInformation(this.currentTrendToolData.getLstOverride(), this.currentTrendToolData.getLstActualChange(), this.currentTrendToolData.getLstModelWeek(), this.currentTrendToolData.getLstForecastBase()));
    		this.currentTrendToolData.setLstModelWeek(this.trendDAO.populateModelWeekInformation(this.currentTrendToolData.getLstForecastBase(), this.currentTrendToolData.getLstForecastTrend()));
    		this.currentTrendToolData.setLstForecastTrend(this.trendDAO.populateForecastTrendInformation(this.currentTrendToolData.getLstOverride(), this.currentTrendToolData.getLstActualChange(), this.currentTrendToolData.getLstModelWeek(), this.currentTrendToolData.getLstForecastBase()));
    	}
    	
    	
    	
   	//List<TrendTool> gridList = new ArrayList<TrendTool>();
    	long startTime;
    	long endTime;
    	startTime = System.currentTimeMillis();
    	List<TrendTool> trendOldList = currentTrendToolData.getLstTrendOld();
    	endTime   = System.currentTimeMillis();
    	System.out.println("getLstTrendOld" + (endTime - startTime));
    	
    	
    	startTime = System.currentTimeMillis();
    	List<TrendTool> trendNewList = currentTrendToolData.getLstTrendNew();
    	endTime   = System.currentTimeMillis();
    	System.out.println("getLstTrendNew : " + (endTime - startTime));
    	
    	
    	startTime = System.currentTimeMillis();
        List<TrendTool> trendActList = currentTrendToolData.getLstActualChange();
    	endTime   = System.currentTimeMillis();
    	System.out.println("getLstActualChange : " + (endTime - startTime));
    	
    	
    	
    	startTime = System.currentTimeMillis();
        List<TrendTool> trendOvrList = currentTrendToolData.getLstOverride();    	
        endTime   = System.currentTimeMillis();
    	System.out.println("getLstOverride : " + (endTime - startTime));
    	
    	startTime = System.currentTimeMillis();
        List<TrendTool> trendTrendList = currentTrendToolData.getLstForecastTrend();
        endTime   = System.currentTimeMillis();
    	System.out.println("getLstForecastTrend : " + (endTime - startTime));
    	
        
    	startTime = System.currentTimeMillis();
        List<TrendTool> trendBaseList = currentTrendToolData.getLstForecastBase();
        endTime   = System.currentTimeMillis();
    	System.out.println("getLstForecastBase : " + (endTime - startTime));
    	
    	
    	startTime = System.currentTimeMillis();
        List<TrendTool> trendWeekList = currentTrendToolData.getLstModelWeek();
        endTime   = System.currentTimeMillis();
    	System.out.println("getLstModelWeek : " + (endTime - startTime));
    	
     	startTime = System.currentTimeMillis();
        List<TrendTool> trendPasteList = currentTrendToolData.getLstAdvancedPasting();
        endTime   = System.currentTimeMillis();
    	System.out.println("getLstAdvancedPasting : " + (endTime - startTime));
    	
        
        
        System.out.print("TREND TOOL --->>>");
    	
    	//System.out.print(currentTrendToolData);
//    	
//    	//Trend Old - Section
//    	
//    	
this.paintRow(trendOldList, "Empty", 		"OLD", true, false);
this.paintRow(trendOldList, "Sunday",  		"OLD", false, false);
this.paintRow(trendOldList, "Monday",  		"OLD", false, false);
this.paintRow(trendOldList, "Tuesday",  	"OLD", false, false);   
this.paintRow(trendOldList, "Wednesday",  	"OLD", false, false);   
this.paintRow(trendOldList, "Thursday",  	"OLD", false, false);
this.paintRow(trendOldList, "Friday", 		"OLD", false, false);
this.paintRow(trendOldList, "Saturday", 	"OLD", false, false); 
this.paintRow(trendOldList, "Weekday", 		"OLD", false, false);
this.paintRow(trendOldList, "Weekend", 		"OLD", false, false);
this.paintRow(trendOldList, "Total", 		"OLD", false, false);  	
//    	
//    	//Trend New - Section
    	this.paintRow(trendNewList, "Empty", 		"NEW", false, false);
    	this.paintRow(trendNewList, "Sunday",  		"NEW", false, false);
    	this.paintRow(trendNewList, "Monday",  		"NEW", false, false);
    	this.paintRow(trendNewList, "Tuesday",  	"NEW", false, false);    
    	this.paintRow(trendNewList, "Wednesday",  	"NEW", false, false);    
    	this.paintRow(trendNewList, "Thursday",  	"NEW", false, false);  
    	this.paintRow(trendNewList, "Friday", 		"NEW", false, false);
    	this.paintRow(trendNewList, "Saturday", 	"NEW", false, false);  
    	this.paintRow(trendNewList, "Weekday", 		"NEW", false, false);
    	this.paintRow(trendNewList, "Weekend", 		"NEW", false, false);
    	this.paintRow(trendNewList, "Total", 		"NEW", false, false);    	
    	
    	//Actual Change - Section
    	this.paintRow(trendActList, "Empty", 		"ACT", false, false);
    	this.paintRow(trendActList, "Sunday",  		"ACT", false, false);
    	this.paintRow(trendActList, "Monday",  		"ACT", false, false);
    	this.paintRow(trendActList, "Tuesday",  	"ACT", false, false);    
    	this.paintRow(trendActList, "Wednesday",  	"ACT", false, false);    
    	this.paintRow(trendActList, "Thursday",  	"ACT", false, false);  
    	this.paintRow(trendActList, "Friday", 		"ACT", false, false);
    	this.paintRow(trendActList, "Saturday", 	"ACT", false, false);  
    	this.paintRow(trendActList, "Weekday", 		"ACT", false, false);
    	this.paintRow(trendActList, "Weekend", 		"ACT", false, false);
    	this.paintRow(trendActList, "Total", 		"ACT", false, false);  
//    	
//    	//Overwrite - Section
    	this.paintRow(trendOvrList, "Empty", 		"OVR", false, true);
    	this.paintRow(trendOvrList, "Sunday",  		"OVR", false, true);
    	this.paintRow(trendOvrList, "Monday",  		"OVR", false, true);
    	this.paintRow(trendOvrList, "Tuesday",  	"OVR", false, true);   
    	this.paintRow(trendOvrList, "Wednesday",  	"OVR", false, true);   
    	this.paintRow(trendOvrList, "Thursday",  	"OVR", false, true); 
    	this.paintRow(trendOvrList, "Friday", 		"OVR", false, true);
    	this.paintRow(trendOvrList, "Saturday", 	"OVR", false, true);
//    	
//    	//Forecast Trend - Section
    	this.paintRow(trendTrendList, "Empty", 		"TREND", false, false);
    	this.paintRow(trendTrendList, "Sunday",  	"TREND", false, false);
    	this.paintRow(trendTrendList, "Monday",  	"TREND", false, false);
    	this.paintRow(trendTrendList, "Tuesday",  	"TREND", false, false);    
    	this.paintRow(trendTrendList, "Wednesday", 	"TREND", false, false);    
    	this.paintRow(trendTrendList, "Thursday",  	"TREND", false, false);  
    	this.paintRow(trendTrendList, "Friday", 	"TREND", false, false);
    	this.paintRow(trendTrendList, "Saturday", 	"TREND", false, false);  
    	this.paintRow(trendTrendList, "Weekday", 	"TREND", false, false);
    	this.paintRow(trendTrendList, "Weekend", 	"TREND", false, false);
    	this.paintRow(trendTrendList, "Total", 		"TREND", false, false);  
//    	
//    	//Forecast Baseline - Section
    	this.paintRow(trendBaseList, "Empty", 		"BASE", false, false);
    	this.paintRow(trendBaseList, "Sunday",  	"BASE", false, false);
    	this.paintRow(trendBaseList, "Monday",  	"BASE", false, false);
    	this.paintRow(trendBaseList, "Tuesday",  	"BASE", false, false);    
    	this.paintRow(trendBaseList, "Wednesday",  	"BASE", false, false);    
    	this.paintRow(trendBaseList, "Thursday",  	"BASE", false, false);  
    	this.paintRow(trendBaseList, "Friday", 		"BASE", false, false);
    	this.paintRow(trendBaseList, "Saturday", 	"BASE", false, false);  
    	this.paintRow(trendBaseList, "Weekday", 	"BASE", false, false);
    	this.paintRow(trendBaseList, "Weekend", 	"BASE", false, false);
    	this.paintRow(trendBaseList, "Total", 		"BASE", false, false);  
//    	
//    	//Forecast Model Week - Section
    	this.paintRow(trendWeekList, "Empty", 		"WEEK", false , true);
    	this.paintRow(trendWeekList, "Sunday",  	"WEEK", false , true);
    	this.paintRow(trendWeekList, "Monday",  	"WEEK", false , true);
    	this.paintRow(trendWeekList, "Tuesday",  	"WEEK", false , true);    
    	this.paintRow(trendWeekList, "Wednesday",  	"WEEK", false , true);    
    	this.paintRow(trendWeekList, "Thursday",  	"WEEK", false , true);  
    	this.paintRow(trendWeekList, "Friday", 		"WEEK", false , true);
    	this.paintRow(trendWeekList, "Saturday", 	"WEEK", false , true);  
    	this.paintRow(trendWeekList, "Weekday", 	"WEEK", false , true);
    	this.paintRow(trendWeekList, "Weekend", 	"WEEK", false , true);
    	this.paintRow(trendWeekList, "Total", 		"WEEK", false , true);   
//    	
//    	//Advance Pasting - Section
    	this.paintRow(trendPasteList, "Empty", 		"PASTE", false , false);
    	this.paintRow(trendPasteList, "Sunday",  	"PASTE", false , false);
    	this.paintRow(trendPasteList, "Monday",  	"PASTE", false , false);
    	this.paintRow(trendPasteList, "Tuesday",  	"PASTE", false , false);    
    	this.paintRow(trendPasteList, "Wednesday", 	"PASTE", false , false);    
    	this.paintRow(trendPasteList, "Thursday",  	"PASTE", false , false);  
    	this.paintRow(trendPasteList, "Friday", 	"PASTE", false , false);
    	this.paintRow(trendPasteList, "Saturday", 	"PASTE", false , false);
    	
    	this.addFrozenColumn();
    	
//    	//Enable buttons
    	this.printGridBtn.setDisabled(false);
    	this.advanceBtn.setDisabled(false);
    	this.pasteBtn.setDisabled(false);
    	this.calculateTrendBtn.setDisabled(false);
    	
    	retrieveFellows();//In this case we are manually populating the rows. So we can the method directly.
    	
    }
    
    private void paintRow(List<TrendTool> trendList, String day, String type,  boolean withHeaders, boolean editable) {
    	 
    	Listitem row = new Listitem();	    	
    	
    	//Build headers first
		if(withHeaders) {
			
			Auxheader emptyHeader = new Auxheader("TYPE");//
			emptyHeader.setWidth("80px");
			this.auxHead.appendChild(emptyHeader);
			
			for(int j=0; j<trendList.size(); j++) {
				
				Auxheader auxSegmentHeader =new Auxheader(trendList.get(j).getSegmentName());
				auxSegmentHeader.setStyle("text-align:center");
				auxSegmentHeader.setWidth("90px");
				auxSegmentHeader.setColspan(2);
				this.auxHead.appendChild(auxSegmentHeader);				
				 
			}
			
			Listheader emptyListHeader = new Listheader("");//Empty
			emptyListHeader.setWidth("80px");
			this.listHead.appendChild(emptyListHeader);
			
			for(int j=0; j<trendList.size(); j++) {
				
				Listheader headerOcc = new Listheader("OCC");
				headerOcc.setWidth("40px");
	    		Listheader headerADR = new Listheader("ADR");
	    		headerADR.setWidth("50px");
	    		
	    		this.listHead.appendChild(headerOcc);
	    		this.listHead.appendChild(headerADR);				
				 
			}

			this.genericListBox.appendChild(this.auxHead);
			this.genericListBox.appendChild(this.listHead);
		}
		
		//Paint day first		
		Label dayLbl = new Label(day.equalsIgnoreCase("EMPTY") ? type : day);	
		if(day.equalsIgnoreCase("EMPTY")) row.setStyle("background: #ddddd4");
		Listcell lcDay = new Listcell();
		lcDay.appendChild(dayLbl);	
		row.appendChild(lcDay);
		
    		
    	for(int i=0; i< trendList.size(); i++) {
        				
    				Textbox OCCvalue = null;
    				Textbox ADRvalue = null;
    				TrendToolProperties props = new TrendToolProperties();
    				    				
    				//Sunday
    				if(day.equalsIgnoreCase("SUNDAY")) {
    					
    					OCCvalue = new Textbox(String.valueOf(trendList.get(i).getSunOCC()).equals("null") ? "" : String.valueOf(trendList.get(i).getSunOCC())); 
    					ADRvalue = new Textbox(String.valueOf(trendList.get(i).getSunADR()).equals("null") ? "" : String.valueOf(trendList.get(i).getSunADR().compareTo(BigDecimal.ZERO) == 0 ? trendList.get(i).getSunADR().setScale(0) : trendList.get(i).getSunADR()));    					
    				}
    				
    				//Monday
    				else if(day.equalsIgnoreCase("MONDAY")) {
    					OCCvalue = new Textbox(String.valueOf(trendList.get(i).getMonOCC()).equals("null") ? "" : String.valueOf(trendList.get(i).getMonOCC())); 
    					ADRvalue = new Textbox(String.valueOf(trendList.get(i).getMonADR()).equals("null") ? "" : String.valueOf(trendList.get(i).getMonADR().compareTo(BigDecimal.ZERO) == 0 ? trendList.get(i).getMonADR().setScale(0) : trendList.get(i).getMonADR()));    				
    				}
    				
    				//Tuesday
    				else if(day.equalsIgnoreCase("TUESDAY")) {    					
    					OCCvalue = new Textbox(String.valueOf(trendList.get(i).getTueOCC()).equals("null") ? "" : String.valueOf(trendList.get(i).getTueOCC())); 
    					ADRvalue = new Textbox(String.valueOf(trendList.get(i).getTueADR()).equals("null") ? "" : String.valueOf(trendList.get(i).getTueADR().compareTo(BigDecimal.ZERO) == 0 ? trendList.get(i).getTueADR().setScale(0) : trendList.get(i).getTueADR()));    					
    				}
    				
    				//Wednesday
    				else if(day.equalsIgnoreCase("WEDNESDAY")) {
    					OCCvalue = new Textbox(String.valueOf(trendList.get(i).getWenOCC()).equals("null") ? "" : String.valueOf(trendList.get(i).getWenOCC())); 
    					ADRvalue = new Textbox(String.valueOf(trendList.get(i).getWenADR()).equals("null") ? "" : String.valueOf(trendList.get(i).getWenADR().compareTo(BigDecimal.ZERO) == 0 ? trendList.get(i).getWenADR().setScale(0) : trendList.get(i).getWenADR()));    					
    				}
    				
    				//Thursday
    				else if(day.equalsIgnoreCase("THURSDAY")) {
    					OCCvalue = new Textbox(String.valueOf(trendList.get(i).getThuOCC()).equals("null") ? "" : String.valueOf(trendList.get(i).getThuOCC())); 
    					ADRvalue = new Textbox(String.valueOf(trendList.get(i).getThuADR()).equals("null") ? "" : String.valueOf(trendList.get(i).getThuADR().compareTo(BigDecimal.ZERO) == 0 ? trendList.get(i).getThuADR().setScale(0) : trendList.get(i).getThuADR()));    					
    				}
    				
    				//Friday
    				else if(day.equalsIgnoreCase("FRIDAY")) {
    					OCCvalue = new Textbox(String.valueOf(trendList.get(i).getFriOCC()).equals("null") ? "" : String.valueOf(trendList.get(i).getFriOCC())); 
    					ADRvalue = new Textbox(String.valueOf(trendList.get(i).getFriADR()).equals("null") ? "" : String.valueOf(trendList.get(i).getFriADR().compareTo(BigDecimal.ZERO) == 0 ? trendList.get(i).getFriADR().setScale(0) : trendList.get(i).getFriADR()));    					
    				} 
    				
    				//Saturday
    				else if(day.equalsIgnoreCase("SATURDAY")) {
    					OCCvalue = new Textbox(String.valueOf(trendList.get(i).getSatOCC()).equals("null") ? "" : String.valueOf(trendList.get(i).getSatOCC())); 
    					ADRvalue = new Textbox(String.valueOf(trendList.get(i).getSatADR()).equals("null") ? "" : String.valueOf(trendList.get(i).getSatADR().compareTo(BigDecimal.ZERO) == 0 ? trendList.get(i).getSatADR().setScale(0) : trendList.get(i).getSatADR()));    					
    				}  
    				
    				//Weekday
    				else if(day.equalsIgnoreCase("WEEKDAY")) {
    					OCCvalue = new Textbox(String.valueOf(trendList.get(i).getWeekOCC()).equals("null") ? "n/a" : String.valueOf(trendList.get(i).getWeekOCC())); 
    					ADRvalue = new Textbox(String.valueOf(trendList.get(i).getWeekADR()).equals("null") ? "n/a" : String.valueOf(trendList.get(i).getWeekADR().compareTo(BigDecimal.ZERO) == 0 ? trendList.get(i).getWeekADR().setScale(0) : trendList.get(i).getWeekADR()));    					
    				}
    				
    				//Weekend
    				else if(day.equalsIgnoreCase("WEEKEND")) {
    					OCCvalue = new Textbox(String.valueOf(trendList.get(i).getWeekendOCC()).equals("null") ? "n/a" : String.valueOf(trendList.get(i).getWeekendOCC())); 
    					ADRvalue = new Textbox(String.valueOf(trendList.get(i).getWeekendADR()).equals("null") ? "n/a" : String.valueOf(trendList.get(i).getWeekendADR().compareTo(BigDecimal.ZERO) == 0 ? trendList.get(i).getWeekendADR().setScale(0) : trendList.get(i).getWeekendADR()));    					
    				}
    				
    				//Total
    				else if(day.equalsIgnoreCase("TOTAL")) {
    					OCCvalue = new Textbox(String.valueOf(trendList.get(i).getTotalOCC()).equals("null") ? "n/a" : String.valueOf(trendList.get(i).getTotalOCC())); 
    					ADRvalue = new Textbox(String.valueOf(trendList.get(i).getTotalADR()).equals("null") ? "n/a" : String.valueOf(trendList.get(i).getTotalADR().compareTo(BigDecimal.ZERO) == 0 ? trendList.get(i).getTotalADR().setScale(0) : trendList.get(i).getTotalADR()));    					
    				}
    				    				
    				//Add to row    				
    				Listcell lcOCC = new Listcell();
    				Listcell lcADR = new Listcell();    				
    				
    				//If OCC/ADR were set
    				if(OCCvalue != null && ADRvalue != null) {
    					//Set custom props to bind data manually
    					props.setDay(day);   
    					props.setSegmentData(trendList.get(i));
    					props.setAdrTxt(ADRvalue);
    					props.setOccTxt(OCCvalue);
    					//Add props to HashMap to allow getting view and model values when required
    					propsMap.put(TrendToolProperties.buildKey(type, day, trendList.get(i).getSegmentId()), props);
    					
    					OCCvalue.setInplace(true); 
        				ADRvalue.setInplace(true);
        				
        				OCCvalue.setReadonly(!editable);
        				ADRvalue.setReadonly(!editable);
        				
        				if(type.equalsIgnoreCase("OVR")) {
        					OCCvalue.addEventListener("onChange", overwriteOCCListener);
            				ADRvalue.addEventListener("onChange", overwriteADRListener); 
            				OCCvalue.addEventListener("onClick", overwriteClickListener); 
            				ADRvalue.addEventListener("onClick", overwriteClickListener); 
            				OCCvalue.addEventListener("onBlur", overwriteClickListener); 
            				ADRvalue.addEventListener("onBlur", overwriteClickListener);
        				}
        				
        				else if(type.equalsIgnoreCase("PASTE")) {
            				OCCvalue.addEventListener("onClick", advancedOCCListener); 
            				ADRvalue.addEventListener("onClick", advancedADRListener); 
        				}
        				
        				else if(type.equalsIgnoreCase("WEEK")) {
            				OCCvalue.addEventListener("onChange", weekOCCListener); 
            				ADRvalue.addEventListener("onChange", weekADRListener); 
        				}
        				
        				else if(type.equalsIgnoreCase("ACT") || type.equalsIgnoreCase("TREND") || type.equalsIgnoreCase("OVR")) {
        					if(!OCCvalue.getText().equalsIgnoreCase("n/a") && !OCCvalue.getText().equalsIgnoreCase("")) OCCvalue.setText(OCCvalue.getText() + "%");
        					if(!ADRvalue.getText().equalsIgnoreCase("n/a") && !ADRvalue.getText().equalsIgnoreCase("")) ADRvalue.setText(ADRvalue.getText() + "%");
        				}
        				             				
        				OCCvalue.setAttribute("customProperties", props);
        				ADRvalue.setAttribute("customProperties", props);
        				
        				lcOCC.appendChild(OCCvalue);
        				lcADR.appendChild(ADRvalue);         				
    				}
    				    				
    				row.appendChild(lcOCC);	
    				row.appendChild(lcADR);
    				
    				
    			}
        		
    		
    		//Add Row to ListBox
			this.genericListBox.appendChild(row);
    	}
    	
    	
   
    private void addFrozenColumn(){
    	
    	Frozen frozen = new Frozen();
		frozen.setStyle("background: #DFDED8");
		frozen.setColumns(1);
		
		Div div = new Div();
		div.setStyle("padding: 0 10px;");
		
		frozen.appendChild(div);
		this.genericListBox.appendChild(frozen);
    }

}
