package com.hrr3.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ItemRenderer;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.impl.InputElement;
import org.zkoss.zul.impl.XulElement;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Textbox;

import com.hrr3.authentication.AuthenticationServiceHRR3Impl;
import com.hrr3.entity.Segment;
import com.hrr3.entity.renovationImpact.ReportData;
import com.hrr3.entity.transients.TransientDataRow;
import com.hrr3.model.CustomerDAO;
import com.hrr3.model.input.InputDAO;
import com.hrr3.model.input.TransientInputDAO;
import com.hrr3.services.AuthenticationService;

public class InputTransientListRender extends ListboxArrowKeyRender implements ListitemRenderer<TransientDataRow>{	
	
	
	public InputTransientListRender(int headerCount) {
		super(headerCount);
		// TODO Auto-generated constructor stub
	}
	
	int curr_customerId;
	int curr_segmentId;
	String curr_statedate;
	int curr_occValue;
	BigDecimal curr_adrValue;
	BigDecimal old_adrValue;//In case of selecting NO in the messagebox
	Decimalbox curr_adrDecimalbox;//a ref to current ADR box
	TransientDataRow currentDataRow;//Current data row
	Row rowToUpdate;//To save currentRow being edited
	Listbox listbox;
	
	
	EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
	    public void onEvent(ClickEvent event) throws Exception {   
	    	//If onNo, then return main app execution.
	    	if (event.getName() ==  Messagebox.ON_NO)   	 
	    		curr_adrDecimalbox.setValue(old_adrValue);//reset to old adr value
	    	else updateTransientSegmentData(curr_customerId, curr_segmentId, curr_statedate, new BigDecimal(curr_occValue), curr_adrValue);		    	    		
	    }
	};  
	
	
	
	private EventListener<InputEvent> generalSectionListener = new EventListener<InputEvent>() {		
	    public void onEvent(InputEvent event) throws ParseException {
	    	
	    	AuthenticationService authService = new AuthenticationServiceHRR3Impl();
	    	TransientInputDAO transientDAO = new TransientInputDAO(authService.getUserData().getCurrentHotel()); 	    	
	    	
	    	//Section for Arrow Keys initialization
	    	InputElement ie = ((InputElement)event.getTarget());	    	
	    	lstComponents = (List<XulElement>) listbox.getAttribute("lstComponents");
	    	int currentIndex = getCurrentIndex(ie);
	    	
	    	//Event info
	    	Row rowToUpdate = (Row) event.getTarget().getParent();    	
	    	int commentsColIndex = 0;//Col index for Comments
	    	int exceptionColIndex = 1;//Col index for Exceptions
	    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    	SimpleDateFormat OridateFormat = new SimpleDateFormat("MM/dd/yyyy");
	    	
	    	//General Info
	    	Date dateObj = OridateFormat.parse((String)rowToUpdate.getAttribute("statedate"));
	    	String statedate = dateFormat.format(dateObj);
	    	Textbox commentsComponent = (Textbox)rowToUpdate.getChildren().get(commentsColIndex);
	    	Textbox exceptionComponent = (Textbox)rowToUpdate.getChildren().get(exceptionColIndex);
	    	
	    	String comment = commentsComponent.getText();
	    	int exception = exceptionComponent.getText().equalsIgnoreCase("Y") ? 1 : 0;	    	
	    	
	    	int generalInfoResult = transientDAO.UpdateTransientData(statedate, comment, exception); 
	    	if(generalInfoResult == 0) { Messagebox.show("Error updating GeneralInfo in database."); return ;}
	    	
	    	//Section for Up/Right/Up/Down logic
	    	sortElements(listbox);
	    	//Reset again
	    	listbox.setAttribute("lstComponents", lstComponents);
	    	//Use same index
	    	nextElement((InputElement)lstComponents.get(currentIndex), true);
	    }	
	   
	};
	
	private  EventListener<InputEvent> segmentSectionListener = new EventListener<InputEvent>() {		
	    public void onEvent(InputEvent event) throws ParseException {
	    	
	    	AuthenticationService authService = new AuthenticationServiceHRR3Impl();	    		   
	    	int customerId = authService.getUserData().getCurrentCustomer().getCustomerId();
	    	
	    	//Section for Arrow Keys initialization
	    	InputElement ie = ((InputElement)event.getTarget());	    	
	    	lstComponents = (List<XulElement>) listbox.getAttribute("lstComponents");
	    	int currentIndex = getCurrentIndex(ie);
	    	
	    	//Event info
	    	rowToUpdate = (Row) event.getTarget().getParent(); 
	    	boolean isEditingOCC = event.getTarget() instanceof Intbox;//get field which is currently being edited. OCC or ADR
	    	boolean isEditingADR = !isEditingOCC;
	    		
	    	int generalCols = 10;//Represent the total# of Cols to allocate General+Total info.	    	
	    	int lastColToRead = (Integer)event.getTarget().getAttribute("segmentSeq")*2 + generalCols;//General Cols + SegmentSeg x 2 (OCC/ADR)
	    	int occColIndex = lastColToRead - 2;//Col index for OCC
	    	int adrColIndex = lastColToRead - 1;//Col index for ADR	    	
	    	
	    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    	SimpleDateFormat OridateFormat = new SimpleDateFormat("MM/dd/yyyy");
	    	
	    	//General Info and Segment Info
	    	Date dateObj = OridateFormat.parse((String)rowToUpdate.getAttribute("statedate"));
	    	String statedate = dateFormat.format(dateObj);
	    	
	    	int segmentId = (Integer)event.getTarget().getAttribute("segmentId");    	

	    	Intbox occIntbox = (Intbox)rowToUpdate.getChildren().get(occColIndex);
	    	Decimalbox adrDecimalbox = (Decimalbox)rowToUpdate.getChildren().get(adrColIndex);
	    	int occValue = occIntbox.getValue();
	    	BigDecimal adrValue = adrDecimalbox.getValue();
	    	
	    	//Validations
	    	InputDAO input = new InputDAO(authService.getUserData().getCurrentHotel());		
			String dataType = input.getSegmentDataType(segmentId, customerId);
			boolean isREV = dataType != null && dataType.equals("REV");
			isREV = true; //for testing...
			
			//Set All global params for REV Listener
			curr_customerId = customerId;
			curr_segmentId = segmentId ;
			curr_statedate = statedate;
			curr_occValue = occValue;
			curr_adrValue = adrValue;			
			
			//If Editing ADR, do not allow entering value other than 0 if OCC is 0
			if(isEditingADR) {
				old_adrValue = (BigDecimal)event.getPreviousValue();
				curr_adrDecimalbox = adrDecimalbox;//Save currentDecimalBox for future use
				//Do not allow ADR != 0 when OCC = 0				
				if(isREV && occValue == 0)//If is REV, then display a message asking for confirmation.
						Messagebox.show("Are you sure you want to leave the Occ set to 0?", "Salir del Sistema", new Messagebox.Button[]{Messagebox.Button.YES, Messagebox.Button.NO}, Messagebox.QUESTION, clickListener);
				else
					updateTransientSegmentData(customerId, segmentId, statedate, new BigDecimal(occValue), adrValue);	
				
			}
			else
				updateTransientSegmentData(customerId, segmentId, statedate, new BigDecimal(occValue), adrValue);
			

	    	//Section for Up/Right/Up/Down logic
			listbox.setAttribute("currentIndex", getCurrentIndex(ie));
			
	    	sortElements(listbox);
	    	//Reset again
	    	listbox.setAttribute("lstComponents", lstComponents); 
	    	
	    	//Use same index
	    	//nextElement((InputElement)lstComponents.get(currentIndex));
	    }	   
	};
	
	
	public void updateTransientSegmentData(int customerId,int segmentId, String statedate, BigDecimal occValue, BigDecimal adrValue) {
		
		AuthenticationService authService = new AuthenticationServiceHRR3Impl();
		TransientInputDAO transientDAO = new TransientInputDAO(authService.getUserData().getCurrentHotel()); 
		
		int segmentInfoResult = transientDAO.UpdateTransientSegmentData(customerId, segmentId, statedate, occValue, adrValue);
    	if(segmentInfoResult == 0) { Messagebox.show("Error updating GeneralInfo in database."); return;}	
    	
    	//else //After updating TransientSegmentData, refresh the summary section
			//refreshSummaryAndTotals();//Use in case HRR ask for it !!!!
    	    	
	}
	
	public void refreshSummaryAndTotals() {
		
		AuthenticationService authService = new AuthenticationServiceHRR3Impl();
		TransientInputDAO transientDAO = new TransientInputDAO(authService.getUserData().getCurrentHotel());		
		//Find first row for Summary&Total data
		//Row summaryRow = (Row)this.listbox.getRows().getFirstChild();
		//Delete content by setting to null and creating a new Row
		//summaryRow.getChildren().clear();
		//rowToUpdate.getChildren().clear();		
		List<TransientDataRow> gridModel = (List<TransientDataRow>)this.listbox.getListModel();
		//Render it
		CustomerDAO customerDAO = new CustomerDAO();
		List<Segment> segmentNames = customerDAO.getSegmentNames(this.curr_customerId, this.currentDataRow.getClassId());
		List<TransientDataRow> specRowList = transientDAO.getTransientDataRow(this.curr_customerId, curr_statedate , curr_statedate, this.currentDataRow.getClassId(), this.currentDataRow.getTotalType(), segmentNames.size());
		TransientDataRow specRow = specRowList.get(1);	
		gridModel.set(rowToUpdate.getIndex(), specRow);
		TransientDataRow sumRow = transientDAO.UpdateTransientDataRow(gridModel,this.currentDataRow.getClassId(), this.currentDataRow.getTotalType(), segmentNames.size());
		//gridModel.set(summaryRow.getIndex(), sumRow);	
		//this.listbox.renderAll();
		//this.render(rowToUpdate, specRow, -1);
		//this.render(summaryRow, sumRow, -1);
		
	}
	
	public void render(Listitem row, TransientDataRow data, int index){
		
		this.currentDataRow = data;//To save row state
		if(index >= 0)	this.listbox = row.getListbox();//Only if render is not called manually
		
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		String commonWidth = "40px";
		String commentsWidth = "120px";
		String isExceptionConstraint = "/([Y|N]{1})/: Introduce a valid value[Y/N].";
		String ADRFormat = "###,###,##0.00";		
		symbols.setGroupingSeparator(',');
		boolean isRowEditable = !data.getTransientDataTotals().getHotelTotal().getComments().equalsIgnoreCase("Summary Information");
		
		//Total Hotel
		if (data.getTotalType() == 1) {
			
			//General Information
			Textbox comments = new Textbox(data.getTransientDataTotals().getHotelTotal().getComments());
			comments.setInplace(true);
			comments.setReadonly(!isRowEditable);
			comments.setWidth(commentsWidth);
			comments.addEventListener("onChange", generalSectionListener);			
			
			Listcell lcComments = new Listcell();
			lcComments.appendChild(comments);
			row.appendChild(lcComments);
			
			int isException = data.getTransientDataTotals().getHotelTotal().getIsException();				
			String isExceptionVal = isException == 1 ? "Y" : "N";	
			Textbox isExceptionText = new Textbox(isExceptionVal);
			isExceptionText.setInplace(true);
			isExceptionText.setReadonly(!isRowEditable);
			isExceptionText.setConstraint(isExceptionConstraint);
			isExceptionText.setMaxlength(1);	
			isExceptionText.setWidth(commonWidth);
			isExceptionText.addEventListener("onChange", generalSectionListener);
			Listcell lcIsException = new Listcell();
			lcIsException.appendChild(isExceptionText);
			row.appendChild(lcIsException);
			
			Listcell lcDow = new Listcell();
			lcDow.appendChild(new Label(data.getTransientDataTotals().getHotelTotal().getDow()));
			Listcell lcStatDate = new Listcell();
			lcStatDate.appendChild(new Label(data.getTransientDataTotals().getHotelTotal().getStatdate()));
			
			row.appendChild(lcDow);
			row.appendChild(lcStatDate);
			
			int isActual = data.getTransientDataTotals().getHotelTotal().getIsActual();
			String isActualVal = isActual == 1 ? "A" : "F";	
			
			Listcell lcIsActual = new Listcell();
			lcIsActual.appendChild(new Label(isActualVal));
			row.appendChild(lcIsActual);
			
			//Totals
			Listcell lct1 = new Listcell();
			Listcell lct2 = new Listcell();
			Listcell lct3 = new Listcell();
			Listcell lct4 = new Listcell();
			Listcell lct5 = new Listcell();
			
			lct1.appendChild(new Label(String.valueOf(data.getTransientDataTotals().getHotelTotal().getTotOccPct())));
			lct2.appendChild(new Label(String.valueOf(data.getTransientDataTotals().getHotelTotal().getTotAdr())));
			lct3.appendChild(new Label(String.valueOf(data.getTransientDataTotals().getHotelTotal().getTotRevPar())));
			lct4.appendChild(new Label(String.valueOf(data.getTransientDataTotals().getHotelTotal().getTotOccRooms())));
			lct5.appendChild(new Label(String.valueOf(formatter.format(data.getTransientDataTotals().getHotelTotal().getTotRev()))));
			
			row.appendChild(lct1);			
			row.appendChild(lct2);
			row.appendChild(lct3);		
			row.appendChild(lct4);
			row.appendChild(lct5);
			
			//Set Attributes
			row.setAttribute("statedate", data.getTransientDataTotals().getHotelTotal().getStatdate());
			
		}
		
		//Total Paid Rooms
		else if (data.getTotalType() == 2) {			
			
			//General Information
			Textbox comments = new Textbox(data.getTransientDataTotals().getPaidRooms().getComments());
			comments.setInplace(true);
			comments.setReadonly(!isRowEditable);
			comments.setWidth(commentsWidth);
			comments.addEventListener("onChange", generalSectionListener);
			
			Listcell lcComments = new Listcell();
			lcComments.appendChild(comments);
			row.appendChild(lcComments);
			
			int isException = data.getTransientDataTotals().getPaidRooms().getIsException();				
			String isExceptionVal = isException == 1 ? "Y" : "N";	
			Textbox isExceptionText = new Textbox(isExceptionVal);
			isExceptionText.setInplace(true);
			isExceptionText.setReadonly(!isRowEditable);
			isExceptionText.setConstraint(isExceptionConstraint);
			isExceptionText.setMaxlength(1);	
			isExceptionText.setWidth(commonWidth);
			isExceptionText.addEventListener("onChange", generalSectionListener);
			
			Listcell lcIsException = new Listcell();
			lcIsException.appendChild(isExceptionText);
			row.appendChild(lcIsException);
						
			Listcell lcDow = new Listcell();
			Listcell lcStatDate = new Listcell();
			
			lcDow.appendChild(new Label(data.getTransientDataTotals().getPaidRooms().getDow()));
			lcStatDate.appendChild(new Label(data.getTransientDataTotals().getPaidRooms().getStatdate()));
			
			row.appendChild(lcDow);
			row.appendChild(lcStatDate);
			
			int isActual = data.getTransientDataTotals().getPaidRooms().getIsActual();
			String isActualVal = isActual == 1 ? "A" : "F";	
			
			Listcell lcIsActual= new Listcell();
			lcIsActual.appendChild(new Label(isActualVal));			
			row.appendChild(lcIsActual);
			
			//Totals
			Listcell lct1 = new Listcell();
			Listcell lct2 = new Listcell();
			Listcell lct3 = new Listcell();
			Listcell lct4 = new Listcell();
			Listcell lct5 = new Listcell();
			
			lct1.appendChild(new Label(String.valueOf(data.getTransientDataTotals().getPaidRooms().getTotOccPct())));
			lct2.appendChild(new Label(String.valueOf(data.getTransientDataTotals().getPaidRooms().getTotAdr())));
			lct3.appendChild(new Label(String.valueOf(data.getTransientDataTotals().getPaidRooms().getTotRevPar())));
			lct4.appendChild(new Label(String.valueOf(data.getTransientDataTotals().getPaidRooms().getTotOccRooms())));
			lct5.appendChild(new Label(String.valueOf(formatter.format(data.getTransientDataTotals().getPaidRooms().getTotRev()))));
			
			row.appendChild(lct1);			
			row.appendChild(lct2);
			row.appendChild(lct3);		
			row.appendChild(lct4);
			row.appendChild(lct5);
			
			//Set Attributes
			row.setAttribute("statedate", data.getTransientDataTotals().getPaidRooms().getStatdate());
		}
		
		//Total Group
		if (data.getTotalType() == 3) {
			
			//General Information
			Textbox comments = new Textbox(data.getTransientDataTotals().getGroupTotal().getComments());
			comments.setInplace(true);
			comments.setReadonly(!isRowEditable);
			comments.setWidth(commentsWidth);
			comments.addEventListener("onChange", generalSectionListener);
			Listcell lcComments = new Listcell();
			lcComments.appendChild(comments);
			row.appendChild(lcComments);
			
			int isException = data.getTransientDataTotals().getGroupTotal().getIsException();				
			String isExceptionVal = isException == 1 ? "Y" : "N";	
			Textbox isExceptionText = new Textbox(isExceptionVal);
			isExceptionText.setInplace(true);
			isExceptionText.setReadonly(!isRowEditable);
			isExceptionText.setConstraint(isExceptionConstraint);
			isExceptionText.setMaxlength(1);	
			isExceptionText.setWidth(commonWidth);
			isExceptionText.addEventListener("onChange", generalSectionListener);
			
			Listcell lcIsException = new Listcell();
			lcIsException.appendChild(isExceptionText);
			row.appendChild(lcIsException);
			
			Listcell lcDow = new Listcell();
			Listcell lcStatDate = new Listcell();
			
			lcDow.appendChild(new Label(data.getTransientDataTotals().getGroupTotal().getDow()));
			lcStatDate.appendChild(new Label(data.getTransientDataTotals().getGroupTotal().getStatdate()));
			
			row.appendChild(lcDow);
			row.appendChild(lcStatDate);
						
			int isActual = data.getTransientDataTotals().getGroupTotal().getIsActual();
			String isActualVal = isActual == 1 ? "A" : "F";	
			
			Listcell lcIsActual= new Listcell();
			lcIsActual.appendChild(new Label(isActualVal));			
			row.appendChild(lcIsActual);
			
			//Totals
			Listcell lct1 = new Listcell();
			Listcell lct2 = new Listcell();
			Listcell lct3 = new Listcell();
			Listcell lct4 = new Listcell();
			Listcell lct5 = new Listcell();
			
			lct1.appendChild(new Label(String.valueOf(data.getTransientDataTotals().getGroupTotal().getTotOccPct())));
			lct2.appendChild(new Label(String.valueOf(data.getTransientDataTotals().getGroupTotal().getTotAdr())));
			lct3.appendChild(new Label(String.valueOf(data.getTransientDataTotals().getGroupTotal().getTotRevPar())));
			lct4.appendChild(new Label(String.valueOf(data.getTransientDataTotals().getGroupTotal().getTotOccRooms())));
			lct5.appendChild(new Label(String.valueOf(formatter.format(data.getTransientDataTotals().getGroupTotal().getTotRev()))));
			
			row.appendChild(lct1);			
			row.appendChild(lct2);
			row.appendChild(lct3);		
			row.appendChild(lct4);
			row.appendChild(lct5);
			
			//Set Attributes
			row.setAttribute("statedate", data.getTransientDataTotals().getGroupTotal().getStatdate());
		}
		
		//Total Contract
		if (data.getTotalType() == 4) {
			
			//General Information
			Textbox comments = new Textbox(data.getTransientDataTotals().getContractTotal().getComments());
			comments.setInplace(true);
			comments.setReadonly(!isRowEditable);
			comments.setWidth(commentsWidth);
			comments.addEventListener("onChange", generalSectionListener);
			Listcell lcComments = new Listcell();
			lcComments.appendChild(comments);
			row.appendChild(lcComments);
			
			int isException = data.getTransientDataTotals().getContractTotal().getIsException();				
			String isExceptionVal = isException == 1 ? "Y" : "N";	
			Textbox isExceptionText = new Textbox(isExceptionVal);
			isExceptionText.setInplace(true);
			isExceptionText.setReadonly(!isRowEditable);
			isExceptionText.setConstraint(isExceptionConstraint);
			isExceptionText.setMaxlength(1);	
			isExceptionText.setWidth(commonWidth);
			isExceptionText.addEventListener("onChange", generalSectionListener);
			Listcell lcIsException = new Listcell();
			lcIsException.appendChild(isExceptionText);
			row.appendChild(lcIsException);
			
			Listcell lcDow = new Listcell();
			Listcell lcStatDate = new Listcell();
			
			lcDow.appendChild(new Label(data.getTransientDataTotals().getContractTotal().getDow()));
			lcStatDate.appendChild(new Label(data.getTransientDataTotals().getContractTotal().getStatdate()));
			
			row.appendChild(lcDow);
			row.appendChild(lcStatDate);
						
			int isActual = data.getTransientDataTotals().getContractTotal().getIsActual();
			String isActualVal = isActual == 1 ? "A" : "F";	

			Listcell lcIsActual= new Listcell();
			lcIsActual.appendChild(new Label(isActualVal));			
			row.appendChild(lcIsActual);
			
			//Totals
			Listcell lct1 = new Listcell();
			Listcell lct2 = new Listcell();
			Listcell lct3 = new Listcell();
			Listcell lct4 = new Listcell();
			Listcell lct5 = new Listcell();
			
			lct1.appendChild(new Label(String.valueOf(data.getTransientDataTotals().getContractTotal().getTotOccPct())));
			lct2.appendChild(new Label(String.valueOf(data.getTransientDataTotals().getContractTotal().getTotAdr())));
			lct3.appendChild(new Label(String.valueOf(data.getTransientDataTotals().getContractTotal().getTotRevPar())));
			lct4.appendChild(new Label(String.valueOf(data.getTransientDataTotals().getContractTotal().getTotOccRooms())));
			lct5.appendChild(new Label(String.valueOf(formatter.format(data.getTransientDataTotals().getContractTotal().getTotRev()))));
			
			row.appendChild(lct1);			
			row.appendChild(lct2);
			row.appendChild(lct3);		
			row.appendChild(lct4);
			row.appendChild(lct5);
			
			//Set Attributes
			row.setAttribute("statedate", data.getTransientDataTotals().getContractTotal().getStatdate());
		}
		
		//Total Transient
		if (data.getTotalType() == 5) {
			
			//General Information
			Textbox comments = new Textbox(data.getTransientDataTotals().getTransientTotal().getComments());
			comments.setInplace(true);
			comments.setReadonly(!isRowEditable);
			comments.setWidth(commentsWidth);
			comments.addEventListener("onChange", generalSectionListener);
			Listcell lcComments = new Listcell();
			lcComments.appendChild(comments);
			row.appendChild(lcComments);
			
			int isException = data.getTransientDataTotals().getTransientTotal().getIsException();				
			String isExceptionVal = isException == 1 ? "Y" : "N";	
			Textbox isExceptionText = new Textbox(isExceptionVal);
			isExceptionText.setInplace(true);
			isExceptionText.setReadonly(!isRowEditable);
			isExceptionText.setConstraint(isExceptionConstraint);
			isExceptionText.setMaxlength(1);	
			isExceptionText.setWidth(commonWidth);
			isExceptionText.addEventListener("onChange", generalSectionListener);
			Listcell lcIsException = new Listcell();
			lcIsException.appendChild(isExceptionText);
			row.appendChild(lcIsException);
			
			Listcell lcDow = new Listcell();
			Listcell lcStatDate = new Listcell();
			
			lcDow.appendChild(new Label(data.getTransientDataTotals().getTransientTotal().getDow()));
			lcStatDate.appendChild(new Label(data.getTransientDataTotals().getTransientTotal().getStatdate()));
			
			row.appendChild(lcDow);
			row.appendChild(lcStatDate);
			
			int isActual = data.getTransientDataTotals().getTransientTotal().getIsActual();
			String isActualVal = isActual == 1 ? "A" : "F";	
						
			Listcell lcIsActual= new Listcell();
			lcIsActual.appendChild(new Label(isActualVal));			
			row.appendChild(lcIsActual);
			
			//Totals
			Listcell lct1 = new Listcell();
			Listcell lct2 = new Listcell();
			Listcell lct3 = new Listcell();
			Listcell lct4 = new Listcell();
			Listcell lct5 = new Listcell();
			
			lct1.appendChild(new Label(String.valueOf(data.getTransientDataTotals().getTransientTotal().getTotOccPct())));
			lct2.appendChild(new Label(String.valueOf(data.getTransientDataTotals().getTransientTotal().getTotAdr())));
			lct3.appendChild(new Label(String.valueOf(data.getTransientDataTotals().getTransientTotal().getTotRevPar())));
			lct4.appendChild(new Label(String.valueOf(data.getTransientDataTotals().getTransientTotal().getTotOccRooms())));
			lct5.appendChild(new Label(String.valueOf(formatter.format(data.getTransientDataTotals().getTransientTotal().getTotRev()))));
			
			row.appendChild(lct1);			
			row.appendChild(lct2);
			row.appendChild(lct3);		
			row.appendChild(lct4);
			row.appendChild(lct5);
			
			//Set Attributes
			row.setAttribute("statedate", data.getTransientDataTotals().getTransientTotal().getStatdate());				
		}
		
		//Total Misc
		if (data.getTotalType() == 6) {
			
			//General Information
			Textbox comments = new Textbox(data.getTransientDataTotals().getMiscTotal().getComments());
			comments.setInplace(true);
			comments.setReadonly(!isRowEditable);
			comments.setWidth(commentsWidth);
			comments.addEventListener("onChange", generalSectionListener);
			Listcell lcComments = new Listcell();
			lcComments.appendChild(comments);
			row.appendChild(lcComments);
			
			int isException = data.getTransientDataTotals().getMiscTotal().getIsException();				
			String isExceptionVal = isException == 1 ? "Y" : "N";	
			Textbox isExceptionText = new Textbox(isExceptionVal);
			isExceptionText.setInplace(true);
			isExceptionText.setReadonly(!isRowEditable);
			isExceptionText.setConstraint(isExceptionConstraint);
			isExceptionText.setMaxlength(1);	
			isExceptionText.setWidth(commonWidth);
			isExceptionText.addEventListener("onChange", generalSectionListener);
			Listcell lcIsException = new Listcell();
			lcIsException.appendChild(isExceptionText);
			row.appendChild(lcIsException);
			
			Listcell lcDow = new Listcell();
			Listcell lcStatDate = new Listcell();
			
			lcDow.appendChild(new Label(data.getTransientDataTotals().getMiscTotal().getDow()));
			lcStatDate.appendChild(new Label(data.getTransientDataTotals().getMiscTotal().getStatdate()));
			
			row.appendChild(lcDow);
			row.appendChild(lcStatDate);
			
			int isActual = data.getTransientDataTotals().getMiscTotal().getIsActual();
			String isActualVal = isActual == 1 ? "A" : "F";	
			Listcell lcIsActual= new Listcell();
			lcIsActual.appendChild(new Label(isActualVal));			
			row.appendChild(lcIsActual);
			
			//Totals
			Listcell lct1 = new Listcell();
			Listcell lct2 = new Listcell();
			Listcell lct3 = new Listcell();
			Listcell lct4 = new Listcell();
			Listcell lct5 = new Listcell();
			
			lct1.appendChild(new Label(String.valueOf(data.getTransientDataTotals().getMiscTotal().getTotOccPct())));
			lct2.appendChild(new Label(String.valueOf(data.getTransientDataTotals().getMiscTotal().getTotAdr())));
			lct3.appendChild(new Label(String.valueOf(data.getTransientDataTotals().getMiscTotal().getTotRevPar())));
			lct4.appendChild(new Label(String.valueOf(data.getTransientDataTotals().getMiscTotal().getTotOccRooms())));
			lct5.appendChild(new Label(String.valueOf(formatter.format(data.getTransientDataTotals().getMiscTotal().getTotRev()))));
			
			row.appendChild(lct1);			
			row.appendChild(lct2);
			row.appendChild(lct3);		
			row.appendChild(lct4);
			row.appendChild(lct5);	
			
			//Set Attributes
			row.setAttribute("statedate", data.getTransientDataTotals().getMiscTotal().getStatdate());		
		}		
		
		
		//Segment Information
		
		for(int i=0; i<data.getTransientDataSegmentList().length; i++) {
			
			boolean actual = data.getTransientDataTotals().getMiscTotal().getIsActual() == 1;			
			
			Intbox OCCText = new Intbox(data.getTransientDataSegmentList()[i].getDefOcc());
			Decimalbox ADRText = new Decimalbox(data.getTransientDataSegmentList()[i].getDefAdr());
			ADRText.setFormat(ADRFormat);
			ADRText.setLocale(Locale.US);			
			OCCText.setInplace(true);
			ADRText.setInplace(true);			
			OCCText.setReadonly(actual || !isRowEditable ? true : false);
			ADRText.setReadonly(actual || !isRowEditable ? true : false);			
			OCCText.setWidth(commonWidth);
			ADRText.setWidth(commonWidth);
			OCCText.setConstraint(" no empty");
			ADRText.setConstraint("no empty");
			OCCText.setAttribute("segmentSeq", i+1);
			ADRText.setAttribute("segmentSeq", i+1);
			OCCText.setAttribute("segmentId", data.getTransientDataSegmentList()[i].getSegmentId());
			ADRText.setAttribute("segmentId", data.getTransientDataSegmentList()[i].getSegmentId());
			OCCText.addEventListener("onChange", segmentSectionListener);
			ADRText.addEventListener("onChange", segmentSectionListener);
			
			Listcell lcADR = new Listcell();
			Listcell lcOCC = new Listcell();
			lcADR.appendChild(ADRText);
			lcOCC.appendChild(OCCText);
			
			row.appendChild(lcADR);
			row.appendChild(lcOCC);
		}
			
		
			
		//row.setAlign("center");		
	}

}
