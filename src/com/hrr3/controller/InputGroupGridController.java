package com.hrr3.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Auxhead;
import org.zkoss.zul.Auxheader;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import com.hrr3.authentication.AuthenticationServiceHRR3Impl;
import com.hrr3.entity.Segment;
import com.hrr3.entity.transients.GroupDataRow;
import com.hrr3.model.CustomerDAO;
import com.hrr3.model.input.GroupInputDAO;
import com.hrr3.services.AuthenticationService;
import com.hrr3.services.ReportServiceProvider;

public class InputGroupGridController extends GridArrowKeyController {
	private static final long serialVersionUID = 1L;
	
	@Wire
	Button groupSubmit;	
	
	@Wire
	Datebox groupDateFrom;
	
	@Wire
	Datebox groupDateTo;
	
	@Wire
	Auxhead segmentHeaders;
	
	@Wire
	Checkbox chkComments;
	
	@Wire
	Checkbox chkTotals;
	
	@Wire
	Checkbox chkDefinitive;
	
	@Wire
	Checkbox chkTentative;
	
	Auxheader generalInformationHeader;
	Auxheader totalInformationHeader;
	
	private ListModel<GroupDataRow> groupDataRows;
	
	private List<Segment> segmentNames;
	private List<GroupDataRow> tdrList;
	
	/**
	 * @return the groupDataRows
	 */
	public ListModel<GroupDataRow> getGroupDataRows() {
		return groupDataRows;
	}

	/**
	 * @param groupDataRows the groupDataRows to set
	 */
	public void setGroupDataRows(ListModel<GroupDataRow> groupDataRows) {
		this.groupDataRows = groupDataRows;
	}

	
	public List<Segment> populateSegmentHeaders(Integer customerId) {
		
		List<Segment> segmentNames = new ArrayList<Segment>();
		
		CustomerDAO customerDAO = new CustomerDAO();
		customerDAO.getGroupSegmentNames(customerId);
		
		return segmentNames;
	}	
	
	@Listen("onCheck=#chkComments")
	public void onCommentsChecked() {
		
		Columns currentGridColumns = this.genericGrid.getColumns();
		List<Component> commentsCols = currentGridColumns.getChildren();
		Column commentsCol = (Column)commentsCols.get(0);
		commentsCol.setVisible(chkComments.isChecked());	
		
		if(chkComments.isChecked())
			this.generalInformationHeader.setColspan(this.generalInformationHeader.getColspan()+1);
		else
			this.generalInformationHeader.setColspan(this.generalInformationHeader.getColspan()-1);
		
	}
	
	@Listen("onCheck=#chkTotals")
	public void onTotalsChecked() {
		
		Columns currentGridColumns = this.genericGrid.getColumns();
		List<Component> commentsCols = currentGridColumns.getChildren();
		Column occCol = (Column)commentsCols.get(6);
		Column revCol = (Column)commentsCols.get(7);
		Column adrCol = (Column)commentsCols.get(8);
		occCol.setVisible(chkTotals.isChecked());
		revCol.setVisible(chkTotals.isChecked());	
		adrCol.setVisible(chkTotals.isChecked());	
		
		int spansToAdd = chkTotals.isChecked() ? 3:-3;
		int currentSpans = this.totalInformationHeader.isVisible() ? this.totalInformationHeader.getColspan() : 0;
		int totalSpans = currentSpans + spansToAdd;
		
		if(totalSpans > 0)	
		{ this.totalInformationHeader.setColspan(totalSpans); this.totalInformationHeader.setVisible(true); }
		else
			this.totalInformationHeader.setVisible(false);
	}
	
	@Listen("onCheck=#chkDefinitive")
	public void onDefinitiveChecked() {
		
		Columns currentGridColumns = this.genericGrid.getColumns();
		List<Component> commentsCols = currentGridColumns.getChildren();
		Column occCol = (Column)commentsCols.get(9);
		Column revCol = (Column)commentsCols.get(10);
		Column adrCol = (Column)commentsCols.get(11);
		occCol.setVisible(chkDefinitive.isChecked());
		revCol.setVisible(chkDefinitive.isChecked());	
		adrCol.setVisible(chkDefinitive.isChecked());	
		
		int spansToAdd = chkDefinitive.isChecked() ? 3:-3;
		int currentSpans = this.totalInformationHeader.isVisible() ? this.totalInformationHeader.getColspan() : 0;
		int totalSpans = currentSpans + spansToAdd;
		
		if(totalSpans > 0)	
		{ this.totalInformationHeader.setColspan(totalSpans); this.totalInformationHeader.setVisible(true); }
		else
			this.totalInformationHeader.setVisible(false);
	}
	
	@Listen("onCheck=#chkTentative")
	public void onTentativeChecked() {
		
		Columns currentGridColumns = this.genericGrid.getColumns();
		List<Component> commentsCols = currentGridColumns.getChildren();
		Column occCol = (Column)commentsCols.get(12);
		Column revCol = (Column)commentsCols.get(13);
		Column adrCol = (Column)commentsCols.get(14);
		occCol.setVisible(chkTentative.isChecked());
		revCol.setVisible(chkTentative.isChecked());	
		adrCol.setVisible(chkTentative.isChecked());	
		
		int spansToAdd = chkTentative.isChecked() ? 3:-3;
		int currentSpans = this.totalInformationHeader.isVisible() ? this.totalInformationHeader.getColspan() : 0;
		int totalSpans = currentSpans + spansToAdd;
		
		if(totalSpans > 0)	
			{ this.totalInformationHeader.setColspan(totalSpans); this.totalInformationHeader.setVisible(true); }
		else
			this.totalInformationHeader.setVisible(false);
	}	

	@Listen("onClick=#groupSubmit")
	public void groupSubmit(){
		
		AuthenticationService authService = new AuthenticationServiceHRR3Impl();
		
		Date dateFrom = groupDateFrom.getValue();
		Date dateTo = groupDateTo.getValue();		
		
		if(authService.getUserData().getCurrentHotel() == null || authService.getUserData().getCurrentHotel().getHotelId() ==null || authService.getUserData().getCurrentHotel().getHotelId() < 1)
			{Messagebox.show("Please select a valid Hotel and return to this section."); return;}
		
		int hotelId = authService.getUserData().getCurrentHotel().getHotelId();
		int customerId = authService.getUserData().getCurrentCustomer().getCustomerId();
		//Get Headers
		CustomerDAO customerDAO = new CustomerDAO();
		this.segmentNames = customerDAO.getGroupSegmentNames(customerId);
		//Clear out columns			
		this.genericGrid.getColumns().getChildren().clear();		
		this.segmentHeaders.getChildren().clear();			
		Columns currentGridColumns = this.genericGrid.getColumns();
		//Load General information Headers	
		this.loadGeneralInformationHeaders();
		//Load Total information headers
		this.loadTotalInformationHeaders();
		//Load static columns for GeneralInformation section
		this.reloadStaticColumns(currentGridColumns);
				
		for (int i=0; i < segmentNames.size(); i++) {
			
			Auxheader auxSegmentHeader =new Auxheader(segmentNames.get(i).getName());
			auxSegmentHeader.setColspan(6);
			auxSegmentHeader.setStyle("text-align:center");
			segmentHeaders.appendChild(auxSegmentHeader);
			//Use the occAdrHeaders that will contain the ADR and OCC columns			
			Column totalOccColumn = new Column("TOcc");	
			Column totalAdrColumn = new Column("TADR");
			Column defOccColumn = new Column("DOcc");	
			Column defAdrColumn = new Column("DADR");
			Column tentOccColumn = new Column("TOcc");	
			Column tentAdrColumn = new Column("TADR");
			
			currentGridColumns.appendChild(totalOccColumn);
			currentGridColumns.appendChild(totalAdrColumn);
			currentGridColumns.appendChild(defOccColumn);
			currentGridColumns.appendChild(defAdrColumn);
			currentGridColumns.appendChild(tentOccColumn);
			currentGridColumns.appendChild(tentAdrColumn);
					
		}
					
		//Create TIDAO and set currentHotelId
		GroupInputDAO groupDAO = new GroupInputDAO(authService.getUserData().getCurrentHotel()); 
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		//Prepare groupData. Meaning execute calculations, copies, and all stuff needed to prepare the getGroupDataRow in the next step
		boolean isGroupDataPrepared = groupDAO.prepareGroupData(hotelId, customerId, dateFormat.format(dateFrom), dateFormat.format(dateTo));		
		if(!isGroupDataPrepared)
			{Messagebox.show("There was an error preparing segment data. Contact your administrator."); return;}
		this.tdrList = groupDAO.getGroupDataRow(customerId, dateFormat.format(dateFrom), dateFormat.format(dateTo), segmentNames.size());
		this.groupDataRows = new ListModelList<GroupDataRow>(this.tdrList);				
		this.genericGrid.setModel(this.groupDataRows);
		this.genericGrid.setRowRenderer(new InputGroupRender());	
		this.genericGrid.renderAll();
		//Redraw grid to allow scrollbar appears
		this.genericGrid.setSizedByContent(true);
		//this.transientGrid.invalidate();
		
		//Check by default checkboxes
		this.chkComments.setChecked(true);
		this.chkTotals.setChecked(true);
		this.chkDefinitive.setChecked(true);
		this.chkTentative.setChecked(true);
		
	}
	
	@Listen("onClick = #printSubmit")
    public void printGridBtn() throws JRException, WrongValueException, ParseException, IOException {
		
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 	    	
    	String webAppDir = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/");
    	String reportRelativeDir = "WEB-INF" + File.separator + "reports" + File.separator + "group" + File.separator;    	
    	JasperReport jr = (JasperReport)JRLoader.loadObject(new File(webAppDir + reportRelativeDir + "GroupInput.jasper"));    	
    	
		
		AuthenticationService authService = new AuthenticationServiceHRR3Impl();
		
    	Map<String, Object> parms = new HashMap<String, Object>();
    	parms.put("hotelName", authService.getUserData().getCurrentHotel().getName());
    	parms.put("groupFrom", sdf.format(this.groupDateFrom.getValue()));
    	parms.put("groupTo", sdf.format(this.groupDateTo.getValue()));
    	parms.put("segmentNames", this.segmentNames);
    	parms.put("SUBREPORT_DIR", webAppDir + reportRelativeDir); // La ruta de los .jasper

    	JasperPrint jasperPrint = JasperFillManager.fillReport(jr, parms, new JRBeanCollectionDataSource(this.tdrList));
    	//byte[] baos = new JRXlsExporter().
                
    	JRXlsExporter exporter = new JRXlsExporter();
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	SimpleOutputStreamExporterOutput output = new SimpleOutputStreamExporterOutput(baos);
    	//exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
    	//exporter.setExporterOutput(output);
    	SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
    	configuration.setOnePagePerSheet(false);
    	configuration.setDetectCellType(true);
    	configuration.setCollapseRowSpan(false);
    	//exporter.setConfiguration(configuration);
    	exporter.exportReport();
    	OutputStream os = output.getOutputStream();
    	
    	if (os != null) {
    		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    		Sessions.getCurrent().setAttribute(ReportServiceProvider.FILE_TYPE, "application/msexcel");
    		Sessions.getCurrent().setAttribute(ReportServiceProvider.FILE_HEADER, "filename=" + dateFormat.format(Calendar.getInstance().getTime()) + "-" +"GroupInputReport" +".xls");
			Sessions.getCurrent().setAttribute(ReportServiceProvider.REPORT_STREAM, os);
			Executions.getCurrent().sendRedirect("/reportserviceprovider", "_blank");
		}
    }
	
	private void loadGeneralInformationHeaders() {
		
		this.generalInformationHeader = new Auxheader("General Information");
		this.generalInformationHeader.setColspan(6);
		this.generalInformationHeader.setRowspan(1);
		this.generalInformationHeader.setStyle("text-align:center");
		
		
		this.segmentHeaders.appendChild(this.generalInformationHeader);
						
	}
	
	private void loadTotalInformationHeaders() {
		
		this.totalInformationHeader = new Auxheader("Totals For Group");
		this.totalInformationHeader.setColspan(9);
		this.totalInformationHeader.setRowspan(1);
		this.totalInformationHeader.setStyle("text-align:center");
		
		
		this.segmentHeaders.appendChild(this.totalInformationHeader);
						
	}	

	private void reloadStaticColumns(Columns currentGridColumns) {

		//General Information
        
		Column c1 = new Column("Comments");
		c1.setStyle("text-align:center;");
		
		Column c2 = new Column("Exception");
		c2.setStyle("text-align:center;");
		
		Column c3 = new Column("DOW");
		c3.setStyle("text-align:center;");		
		
		Column c4 = new Column("Date");
		c4.setStyle("text-align:center;");	
		
		Column c5 = new Column("A/F");
		c5.setStyle("text-align:center;");	
		
		Column c6 = new Column("Tot Occ%");
		c6.setStyle("text-align:center;");	
		
		//Totals
		
		Column c7 = new Column("Tot Occ");
		c7.setStyle("text-align:center;");	
		
		Column c8 = new Column("Tot Rev");
		c8.setStyle("text-align:center;");	
		
		Column c9 = new Column("Tot ADR");
		c9.setStyle("text-align:center;");	
		
		Column c10 = new Column("Def Occ");
		c10.setStyle("text-align:center;");	
		
		Column c11 = new Column("Def Rev");
		c10.setStyle("text-align:center;");	
		
		Column c12 = new Column("Def ADR");
		c10.setStyle("text-align:center;");	
		
		Column c13 = new Column("Ten Occ");
		c10.setStyle("text-align:center;");	
		
		Column c14 = new Column("Ten Rev");
		c10.setStyle("text-align:center;");	
		
		Column c15 = new Column("Ten ADR");
		c10.setStyle("text-align:center;");	
		
		currentGridColumns.appendChild(c1);
		currentGridColumns.appendChild(c2);
		currentGridColumns.appendChild(c3);
		currentGridColumns.appendChild(c4);
		currentGridColumns.appendChild(c5);
		currentGridColumns.appendChild(c6);
		currentGridColumns.appendChild(c7);
		currentGridColumns.appendChild(c8);
		currentGridColumns.appendChild(c9);
		currentGridColumns.appendChild(c10);
		currentGridColumns.appendChild(c11);
		currentGridColumns.appendChild(c12);
		currentGridColumns.appendChild(c13);
		currentGridColumns.appendChild(c14);
		currentGridColumns.appendChild(c15);
		
	}
		
}
