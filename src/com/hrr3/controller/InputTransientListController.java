package com.hrr3.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;

import com.hrr3.authentication.AuthenticationServiceHRR3Impl;
import com.hrr3.entity.Segment;
import com.hrr3.entity.transients.TransientDataRow;
import com.hrr3.model.CustomerDAO;
import com.hrr3.model.input.TransientInputDAO;
import com.hrr3.services.AuthenticationService;
import com.hrr3.services.ReportServiceProvider;

public class InputTransientListController extends ListboxArrowKeyController {
	private static final long serialVersionUID = 1L;
	
	@Wire
	Button transientSubmit;	
	
	@Wire
	Button printSubmit;		
	
	@Wire
	Datebox transientDateFrom;
	
	@Wire
	Datebox transientDateTo;
	
	@Wire
	Radiogroup transientTotalType;
	
	@Wire
	Combobox transientInputType;
	
	@Wire
	Auxhead segmentHeaders;
	
	@Wire
	Listhead listHeaders;
	
	List<TransientDataRow> tdrList;	
	
	
	private List<Segment> segmentNames;
	
	private ListModel<TransientDataRow> transientDataRows;
	
	/**
	 * @return the transientDataRows
	 */
	public ListModel<TransientDataRow> getTransientDataRows() {
		return transientDataRows;
	}

	/**
	 * @param transientDataRows the transientDataRows to set
	 */
	public void setTransientDataRows(ListModel<TransientDataRow> transientDataRows) {
		this.transientDataRows = transientDataRows;
	}

	
	public List<Segment> populateSegmentHeaders(Integer customerId, Integer type) {
		
		List<Segment> segmentNames = new ArrayList<Segment>();
		
		CustomerDAO customerDAO = new CustomerDAO();
		customerDAO.getSegmentNames(customerId, type);
				
		return segmentNames;
	}
	
	public InputTransientListController() {
		
		super(2);
	}
	
	@Listen("onCheck=#transientTotalType")	
	public void totalTypeOnChange(){
		
		//Messagebox.show("TotalType: " + transientTotalType.getSelectedItem().getLabel());
		
		if(this.transientDataRows != null && this.transientDataRows.getSize() > 0) {
			
			String totalTypeString = transientTotalType.getSelectedItem().getValue();
			int totalTypeSelected = Integer.parseInt(totalTypeString);
			//Reload Model			
			this.genericListBox.setModel(this.updateRowTotalType(this.transientDataRows, totalTypeSelected));
			this.genericListBox.setItemRenderer(new InputTransientListRender(2));	
			//Redraw grid to allow scrollbar appears
			this.genericListBox.setSizedByContent(true);
			
		}
		
		
		
	}
	
	private ListModel<?> updateRowTotalType(
			ListModel<TransientDataRow> transientDataRow, int totalType) {
		// TODO Auto-generated method stub
		
		for (int i=0; i<transientDataRow.getSize(); i++)			
			transientDataRow.getElementAt(i).setTotalType(totalType);
		return transientDataRow;
	}


	@Listen("onClick=#transientSubmit")
	public void transientSubmit(){
		
		AuthenticationService authService = new AuthenticationServiceHRR3Impl();
		
		Date dateFrom = transientDateFrom.getValue();
		Date dateTo = transientDateTo.getValue();		
		
		if(authService.getUserData().getCurrentHotel() == null || authService.getUserData().getCurrentHotel().getHotelId() ==null || authService.getUserData().getCurrentHotel().getHotelId() < 1)
			{Messagebox.show("Please select a valid Hotel and return to this section."); return;}
		
		int hotelId = authService.getUserData().getCurrentHotel().getHotelId();
		int customerId = authService.getUserData().getCurrentCustomer().getCustomerId();
		
		String classIdString = transientInputType.getSelectedItem().getValue();		
		int classId = Integer.parseInt(classIdString);
		
		String totalTypeString = transientTotalType.getSelectedItem().getValue();
		int totalType = Integer.parseInt(totalTypeString);
		
		//Get Headers
		CustomerDAO customerDAO = new CustomerDAO();
		this.segmentNames = customerDAO.getSegmentNames(customerId, classId);
		//Clear out columns			
		//this.genericListBox.getChildren().clear();		
		this.segmentHeaders.getChildren().clear();	
		this.listHeaders.getChildren().clear();	
		//Columns currentGridColumns = this.genericListBox.
		//Load General information Headers	
		this.loadGeneralInformationHeaders();
		//Load Total information headers
		this.loadTotalInformationHeaders();
		//Load static columns for GeneralInformation section
		this.reloadStaticColumns(this.listHeaders);
				
		for (int i=0; i < this.segmentNames.size(); i++) {
			
			Auxheader auxSegmentHeader =new Auxheader(this.segmentNames.get(i).getName());
			auxSegmentHeader.setColspan(2);
			auxSegmentHeader.setStyle("text-align:center");
			segmentHeaders.appendChild(auxSegmentHeader);
			//Use the occAdrHeaders that will contain the ADR and OCC columns			
			Listheader occColumn = new Listheader("OCC");	
			Listheader adrColumn = new Listheader("ADR");	
			this.listHeaders.appendChild(occColumn);
			this.listHeaders.appendChild(adrColumn);
					
		}
					
		//Create TIDAO and set currentHotelId
		TransientInputDAO transientDAO = new TransientInputDAO(authService.getUserData().getCurrentHotel()); 
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				
		//Prepare transientData. Meaning execute calculations, copies, and all stuff needed to prepare the getTransientDataRow in the next step
		boolean isTransientDataPrepared = transientDAO.prepareTransientData(hotelId, customerId, dateFormat.format(dateFrom), dateFormat.format(dateTo));		
		if(!isTransientDataPrepared)
			{Messagebox.show("There was an error preparing segment data. Contact your administrator."); return;}			
		this.tdrList = transientDAO.getTransientDataRow(customerId, dateFormat.format(dateFrom), dateFormat.format(dateTo), classId, totalType, segmentNames.size());
						
		this.transientDataRows = new ListModelList<TransientDataRow>(this.tdrList);				
		this.genericListBox.setModel(this.transientDataRows);		
		this.genericListBox.setItemRenderer(new InputTransientListRender(2));	
		//this.genericListBox.renderAll();
		//Redraw grid to allow scrollbar appears
		this.genericListBox.setSizedByContent(true);
		//this.genericGrid.invalidate();
		this.printSubmit.setDisabled(false);
		
	}
	
	@Listen("onClick = #printSubmit")
    public void printGridBtn() throws JRException, WrongValueException, ParseException, IOException {
		
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 	    	
    	String webAppDir = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/");
    	String reportRelativeDir = "WEB-INF" + File.separator + "reports" + File.separator + "transient" + File.separator;    	
    	JasperReport jr = (JasperReport)JRLoader.loadObject(new File(webAppDir + reportRelativeDir + "TransientInput.jasper"));    	
    	String totalTypeString = transientTotalType.getSelectedItem().getValue();
		int totalType = Integer.parseInt(totalTypeString);
		if(totalType == 1)
			totalType = 5;
		else if(totalType == 2)
			totalType = 6;
		else if(totalType == 3)
			totalType = 2;
		else if(totalType == 4)
			totalType = 3;
		else if(totalType == 5)
			totalType = 1;
		else if(totalType == 6)
			totalType = 4;
		
		AuthenticationService authService = new AuthenticationServiceHRR3Impl();
		
    	Map<String, Object> parms = new HashMap<String, Object>();
    	parms.put("totalType", totalType);
    	parms.put("hotelName", authService.getUserData().getCurrentHotel().getName());
    	parms.put("transientFrom", sdf.format(this.transientDateFrom.getValue()));
    	parms.put("transientTo", sdf.format(this.transientDateTo.getValue()));
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
    		Sessions.getCurrent().setAttribute(ReportServiceProvider.FILE_TYPE, "application/msexcel");
    		Sessions.getCurrent().setAttribute(ReportServiceProvider.FILE_HEADER, "filename=rm3-transient-report.xls");
			Sessions.getCurrent().setAttribute(ReportServiceProvider.REPORT_STREAM, os);
			Executions.getCurrent().sendRedirect("/reportserviceprovider", "_blank");
		}
    }
	
	private void loadGeneralInformationHeaders() {
		
		Auxheader header = new Auxheader("General Information");
		header.setColspan(5);
		header.setRowspan(1);
		header.setStyle("text-align:center");
		
		
		this.segmentHeaders.appendChild(header);
						
	}
	
	private void loadTotalInformationHeaders() {
		
		Auxheader header = new Auxheader("Totals");
		header.setColspan(5);
		header.setRowspan(1);
		header.setStyle("text-align:center");
		
		
		this.segmentHeaders.appendChild(header);
						
	}	

	private void reloadStaticColumns(Listhead currentListhead) {

        
		Listheader c1 = new Listheader("Comments");
		c1.setStyle("text-align:center;");
		
		Listheader c2 = new Listheader("Exception");
		c2.setStyle("text-align:center;");
		
		Listheader c3 = new Listheader("DOW");
		c3.setStyle("text-align:center;");		
		
		Listheader c4 = new Listheader("Date");
		c4.setStyle("text-align:center;");	
		
		Listheader c5 = new Listheader("A/F");
		c5.setStyle("text-align:center;");	
		
		Listheader c6 = new Listheader("OCC%");
		c6.setStyle("text-align:center;");	
		
		Listheader c7 = new Listheader("ADR");
		c7.setStyle("text-align:center;");	
		
		Listheader c8 = new Listheader("RevPar");
		c8.setStyle("text-align:center;");	
		
		Listheader c9 = new Listheader("Occ Rms");
		c9.setStyle("text-align:center;");	
		
		Listheader c10 = new Listheader("Revenue");
		c10.setStyle("text-align:center;");	
		
		currentListhead.appendChild(c1);
		currentListhead.appendChild(c2);
		currentListhead.appendChild(c3);
		currentListhead.appendChild(c4);
		currentListhead.appendChild(c5);
		currentListhead.appendChild(c6);
		currentListhead.appendChild(c7);
		currentListhead.appendChild(c8);
		currentListhead.appendChild(c9);
		currentListhead.appendChild(c10);
		
	}
		
}
