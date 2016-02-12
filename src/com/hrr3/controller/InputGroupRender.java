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
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.impl.InputElement;
import org.zkoss.zul.impl.XulElement;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Textbox;

import com.hrr3.authentication.AuthenticationServiceHRR3Impl;
import com.hrr3.entity.Segment;
import com.hrr3.entity.transients.GroupDataRow;
import com.hrr3.model.CustomerDAO;
import com.hrr3.model.input.GroupInputDAO;
import com.hrr3.model.input.InputDAO;
import com.hrr3.services.AuthenticationService;

public class InputGroupRender extends GridArrowKeyRender implements RowRenderer<GroupDataRow>{
	
	int curr_customerId;
	int curr_segmentId;
	String curr_statedate;
	//CURRENT DEFINITIVE values
	int curr_defOccValue;
	BigDecimal curr_defAdrValue;
	//CURRENT TENTATIVE values
	int curr_tenOccValue;
	BigDecimal curr_tenAdrValue;
	
	BigDecimal old_tenAdrValue;//In case of selecting NO in the messagebox
	BigDecimal old_defAdrValue;//In case of selecting NO in the messagebox
	
	Decimalbox curr_defAdrDecimalbox;//a ref to current defADR box/ MAYBE NOT NEEDED FOR GROUP TODO
	Decimalbox curr_tenAdrDecimalbox;//a ref to current tenADR box/ MAYBE NOT NEEDED FOR GROUP TODO
	
	GroupDataRow currentDataRow;//Current data row
	Row rowToUpdate;//To save currentRow being edited
	Grid grid;
	
	EventListener<ClickEvent> definitiveClickListener = new EventListener<Messagebox.ClickEvent>() {
	    public void onEvent(ClickEvent event) throws Exception {   
	    	//If onNo, then return main app execution.
	    	if (event.getName() ==  Messagebox.ON_NO)   	 
	    		curr_defAdrDecimalbox.setValue(old_defAdrValue);//reset to old adr value
	    	else {
	    		updateGroupSegmentData(curr_customerId, curr_segmentId, curr_statedate, new BigDecimal(curr_tenOccValue), curr_tenAdrValue, new BigDecimal(curr_defOccValue), curr_defAdrValue);
	    		
	    		//Section for Arrow Keys initialization
		    	InputElement ie = ((InputElement)event.getTarget());	    	
		    	lstComponents = (List<XulElement>) grid.getAttribute("lstComponents");
		    	int currentIndex = getCurrentIndex(ie);
		    	//Section for Up/Right/Up/Down logic
		    	sortElements(grid.getRows());
		    	//Reset again
		    	grid.setAttribute("lstComponents", lstComponents);
		    	grid.setAttribute("currentIndex", currentIndex);
		    	//Use same index
		    	nextElement((InputElement)lstComponents.get(currentIndex));
	    	}
	    }
	};  
	
	EventListener<ClickEvent> tentativeClickListener = new EventListener<Messagebox.ClickEvent>() {
	    public void onEvent(ClickEvent event) throws Exception {   
	    	//If onNo, then return main app execution.
	    	if (event.getName() ==  Messagebox.ON_NO)   	 
	    		curr_tenAdrDecimalbox.setValue(old_tenAdrValue);//reset to old adr value
	    	else {
	    		updateGroupSegmentData(curr_customerId, curr_segmentId, curr_statedate, new BigDecimal(curr_tenOccValue), curr_tenAdrValue, new BigDecimal(curr_defOccValue), curr_defAdrValue);
	    		//Section for Arrow Keys initialization
		    	InputElement ie = ((InputElement)event.getTarget());	    	
		    	lstComponents = (List<XulElement>) grid.getAttribute("lstComponents");
		    	int currentIndex = getCurrentIndex(ie);
		    	//Section for Up/Right/Up/Down logic
		    	sortElements(grid.getRows());
		    	//Reset again
		    	grid.setAttribute("lstComponents", lstComponents);
		    	grid.setAttribute("currentIndex", currentIndex);
		    	//Use same index
		    	nextElement((InputElement)lstComponents.get(currentIndex));
	    	}
	    }
	};
	
	
	private EventListener<InputEvent> generalSectionListener = new EventListener<InputEvent>() {		
	    public void onEvent(InputEvent event) throws ParseException {
	    	
	    	AuthenticationService authService = new AuthenticationServiceHRR3Impl();
	    	GroupInputDAO groupDAO = new GroupInputDAO(authService.getUserData().getCurrentHotel()); 	    	
	    	
	    	InputElement ie = ((InputElement)event.getTarget());	    	
	    	lstComponents = (List<XulElement>) grid.getAttribute("lstComponents");
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
	    	
	    	int generalInfoResult = groupDAO.UpdateGroupData(statedate, comment, exception); 
	    	if(generalInfoResult == 0) { Messagebox.show("Error updating GeneralInfo in database."); return ;}
	    	//Section for Up/Right/Up/Down logic
	    	sortElements(grid.getRows());
	    	//Reset again
	    	grid.setAttribute("lstComponents", lstComponents);
	    	grid.setAttribute("currentIndex", currentIndex);
	    	//Use same index
	    	nextElement((InputElement)lstComponents.get(currentIndex));
	    }	
	   
	};
	
	private  EventListener<InputEvent> definitiveSegmentSectionListener = new EventListener<InputEvent>() {		
	    public void onEvent(InputEvent event) throws ParseException {
	    	
	    	AuthenticationService authService = new AuthenticationServiceHRR3Impl();	    		   
	    	int customerId = authService.getUserData().getCurrentCustomer().getCustomerId();
	    	
	    	//Section for Arrow Keys initialization
	    	InputElement ie = ((InputElement)event.getTarget());	    	
	    	lstComponents = (List<XulElement>) grid.getAttribute("lstComponents");
	    	int currentIndex = getCurrentIndex(ie);	    	
	    	//Event info
	    	rowToUpdate = (Row) event.getTarget().getParent(); 
	    	boolean isEditingOCC = event.getTarget() instanceof Intbox;//get field which is currently being edited. OCC or ADR
	    	boolean isEditingADR = !isEditingOCC;
	    		
	    	int generalCols = 15;//Represent the total# of Cols to allocate General+Total info.	    	
	    	int lastColToRead = (Integer)event.getTarget().getAttribute("segmentSeq")*6 + generalCols;//General Cols + SegmentSeg x 6 (OCC/ADR)
	    	int occColIndex = lastColToRead - 4;//Col index for OCC = 17
	    	int adrColIndex = lastColToRead - 3;//Col index for ADR	= 18    	
	    	
	    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    	SimpleDateFormat OridateFormat = new SimpleDateFormat("MM/dd/yyyy");
	    	
	    	//General Info and Segment Info
	    	Date dateObj = OridateFormat.parse((String)rowToUpdate.getAttribute("statedate"));
	    	String statedate = dateFormat.format(dateObj);
	    	
	    	int segmentId = (Integer)event.getTarget().getAttribute("segmentId");    	

	    	//Set DEF-TEN when it's Definitive listener
	    	//get DEF boxes	    	
	    	Intbox defOccIntbox = (Intbox)rowToUpdate.getChildren().get(occColIndex);
	    	Decimalbox defAdrDecimalbox = (Decimalbox)rowToUpdate.getChildren().get(adrColIndex);
	    	//get TEN boxes
	    	Intbox tenOccIntbox = (Intbox)rowToUpdate.getChildren().get(occColIndex+2);//Add 2, to read next ten cols
	    	Decimalbox tenAdrDecimalbox = (Decimalbox)rowToUpdate.getChildren().get(adrColIndex+2);//Add 2, to read next ten cols
	    	//set DEF Values
	    	int defOccValue = defOccIntbox.getValue();
	    	BigDecimal defAdrValue = defAdrDecimalbox.getValue();
	    	//set TEN Values
	    	int tenOccValue = tenOccIntbox.getValue();
	    	BigDecimal tenAdrValue = tenAdrDecimalbox.getValue();
	    	
	    	//Validations
	    	InputDAO input = new InputDAO(authService.getUserData().getCurrentHotel());		
			String dataType = input.getSegmentDataType(segmentId, customerId);
			boolean isREV = dataType != null && dataType.equals("REV");
			isREV = true; //for testing...
			
			//Set All global params for REV Listener
			curr_customerId = customerId;
			curr_segmentId = segmentId ;
			curr_statedate = statedate;
			curr_defOccValue = defOccValue;
			curr_defAdrValue = defAdrValue;			
			curr_tenOccValue = tenOccValue;
			curr_tenAdrValue = tenAdrValue;
			
			//If Editing ADR, do not allow entering value other than 0 if OCC is 0
			if(isEditingADR) {
				old_defAdrValue = (BigDecimal)event.getPreviousValue();
				curr_defAdrDecimalbox = defAdrDecimalbox;//Save currentDecimalBox for future use
				//Do not allow ADR != 0 when OCC = 0				
				if(isREV && defOccValue == 0)//If is REV, then display a message asking for confirmation.
						Messagebox.show("Are you sure you want to leave the Occ set to 0?", "Exit", new Messagebox.Button[]{Messagebox.Button.YES, Messagebox.Button.NO}, Messagebox.QUESTION, definitiveClickListener);
				else
					updateGroupSegmentData(customerId, segmentId, statedate, new BigDecimal(tenOccValue), tenAdrValue, new BigDecimal(defOccValue), defAdrValue );	
				
			}
			else
				updateGroupSegmentData(customerId, segmentId, statedate, new BigDecimal(tenOccValue), tenAdrValue, new BigDecimal(defOccValue), defAdrValue );
			
			//Section for Up/Right/Up/Down logic
	    	sortElements(grid.getRows());
	    	//Reset again
	    	grid.setAttribute("lstComponents", lstComponents);
	    	grid.setAttribute("currentIndex", currentIndex);
	    	//Use same index
	    	nextElement((InputElement)lstComponents.get(currentIndex));
	    }	   
	};
	
	private  EventListener<InputEvent> tentativeSegmentSectionListener = new EventListener<InputEvent>() {		
	    public void onEvent(InputEvent event) throws ParseException {
	    	
	    	AuthenticationService authService = new AuthenticationServiceHRR3Impl();	    		   
	    	int customerId = authService.getUserData().getCurrentCustomer().getCustomerId();
	    	//Section for Arrow Keys initialization
	    	InputElement ie = ((InputElement)event.getTarget());	    	
	    	lstComponents = (List<XulElement>) grid.getAttribute("lstComponents");
	    	int currentIndex = getCurrentIndex(ie);	 
	    	//Event info
	    	rowToUpdate = (Row) event.getTarget().getParent(); 
	    	boolean isEditingOCC = event.getTarget() instanceof Intbox;//get field which is currently being edited. OCC or ADR
	    	boolean isEditingADR = !isEditingOCC;
	    		
	    	int generalCols = 15;//Represent the total# of Cols to allocate General+Total info.	    	
	    	int lastColToRead = (Integer)event.getTarget().getAttribute("segmentSeq")*6 + generalCols;//General Cols + SegmentSeg x 6 (OCC/ADR)
	    	int occColIndex = lastColToRead - 2;//Col index for OCC = 19
	    	int adrColIndex = lastColToRead - 1;//Col index for ADR	= 20    	
	    	
	    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    	SimpleDateFormat OridateFormat = new SimpleDateFormat("MM/dd/yyyy");
	    	
	    	//General Info and Segment Info
	    	Date dateObj = OridateFormat.parse((String)rowToUpdate.getAttribute("statedate"));
	    	String statedate = dateFormat.format(dateObj);
	    	
	    	int segmentId = (Integer)event.getTarget().getAttribute("segmentId");    	

	    	//Set DEF-TEN when it's Definitive listener
	    	//get DEF boxes	    	
	    	Intbox tenOccIntbox = (Intbox)rowToUpdate.getChildren().get(occColIndex);
	    	Decimalbox tenAdrDecimalbox = (Decimalbox)rowToUpdate.getChildren().get(adrColIndex);
	    	//get TEN boxes
	    	Intbox defOccIntbox = (Intbox)rowToUpdate.getChildren().get(occColIndex-2);//Less 2, to read next ten cols
	    	Decimalbox defAdrDecimalbox = (Decimalbox)rowToUpdate.getChildren().get(adrColIndex-2);//Less 2, to read next ten cols
	    	//set DEF Values
	    	int defOccValue = defOccIntbox.getValue();
	    	BigDecimal defAdrValue = defAdrDecimalbox.getValue();
	    	//set TEN Values
	    	int tenOccValue = tenOccIntbox.getValue();
	    	BigDecimal tenAdrValue = tenAdrDecimalbox.getValue();
	    	
	    	//Validations
	    	InputDAO input = new InputDAO(authService.getUserData().getCurrentHotel());		
			String dataType = input.getSegmentDataType(segmentId, customerId);
			boolean isREV = dataType != null && dataType.equals("REV");
			isREV = true; //for testing...
			
			//Set All global params for REV Listener
			curr_customerId = customerId;
			curr_segmentId = segmentId ;
			curr_statedate = statedate;
			curr_defOccValue = defOccValue;
			curr_defAdrValue = defAdrValue;			
			curr_tenOccValue = tenOccValue;
			curr_tenAdrValue = tenAdrValue;
			
			//If Editing ADR, do not allow entering value other than 0 if OCC is 0
			if(isEditingADR) {
				old_tenAdrValue = (BigDecimal)event.getPreviousValue();
				curr_tenAdrDecimalbox = tenAdrDecimalbox;//Save currentDecimalBox for future use
				//Do not allow ADR != 0 when OCC = 0				
				if(isREV && tenOccValue == 0)//If is REV, then display a message asking for confirmation.
						Messagebox.show("Are you sure you want to leave the Occ set to 0?", "Exit", new Messagebox.Button[]{Messagebox.Button.YES, Messagebox.Button.NO}, Messagebox.QUESTION, tentativeClickListener);
				else
					updateGroupSegmentData(customerId, segmentId, statedate, new BigDecimal(tenOccValue), tenAdrValue, new BigDecimal(defOccValue), defAdrValue );	
				
			}
			else
				updateGroupSegmentData(customerId, segmentId, statedate, new BigDecimal(tenOccValue), tenAdrValue, new BigDecimal(defOccValue), defAdrValue );
			
			//Section for Up/Right/Up/Down logic
	    	sortElements(grid.getRows());
	    	//Reset again
	    	grid.setAttribute("lstComponents", lstComponents);
	    	grid.setAttribute("currentIndex", currentIndex);
	    	//Use same index
	    	nextElement((InputElement)lstComponents.get(currentIndex));
	    }	   
	};
	
	public void updateGroupSegmentData(int customerId,int segmentId, String statedate, BigDecimal TOCC, BigDecimal TADR, BigDecimal DOCC, BigDecimal DADR) {
		
		AuthenticationService authService = new AuthenticationServiceHRR3Impl();
		GroupInputDAO groupDAO = new GroupInputDAO(authService.getUserData().getCurrentHotel()); 
		
		int segmentInfoResult = groupDAO.UpdateGroupSegmentData(customerId, segmentId, statedate, TOCC, TADR, DOCC, DADR);
    	if(segmentInfoResult == 0) { Messagebox.show("Error updating GeneralInfo in database.");}	
    	
    	else //After updating GroupSegmentData, refresh the summary section
			refreshSummaryAndTotals();//Use in case HRR ask for it !!!!
    	
	}
	
	//refresh summary section and total for the date
	public void refreshSummaryAndTotals() {
		
		AuthenticationService authService = new AuthenticationServiceHRR3Impl();
		GroupInputDAO groupDAO = new GroupInputDAO(authService.getUserData().getCurrentHotel());
		
		//Find first row for Summary&Total data
		Row summaryRow = (Row)this.grid.getRows().getFirstChild();
		//Delete content by setting to null and creating a new Row
		summaryRow.getChildren().clear();
		rowToUpdate.getChildren().clear();
		
		//Render it
		CustomerDAO customerDAO = new CustomerDAO();
		List<Segment> segmentNames = customerDAO.getGroupSegmentNames(this.curr_customerId);
		//get All rows
		
		
		boolean isGroupDataPrepared = groupDAO.prepareGroupData(groupDAO.getCurrentHotel().getHotelId(), this.curr_customerId, this.currentDataRow.getStartDate(), this.currentDataRow.getEndDate());		
		if(!isGroupDataPrepared)
			{Messagebox.show("There was an error preparing segment data. Contact your administrator."); return;}
		List<GroupDataRow> rows = groupDAO.getGroupDataRow(this.curr_customerId, this.currentDataRow.getStartDate(), this.currentDataRow.getEndDate(),  segmentNames.size());		
		
		//Choose first one containing Summary data		
		this.render(summaryRow, rows.get(0), -1);
		//Choose row that was updated to show new totals for that date
		this.render(rowToUpdate, rows.get(rowToUpdate.getIndex()), -1);
		//Once filled in with data, render both
		
	}
	
	public void render(Row row, GroupDataRow data, int index){
		
		this.currentDataRow = data;//To save row state
		if(index >= 0)	this.grid = row.getGrid();//Only if render is not called manually
		
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		String commonWidth = "40px";
		String commentsWidth = "120px";
		String isExceptionConstraint = "/([Y|N]{1})/: Introduce a valid value[Y/N].";
		String ADRFormat = "###,###,##0.00";		
		symbols.setGroupingSeparator(',');
		boolean isRowEditable = !data.getGroupDataTotal().getComments().equalsIgnoreCase("Summary Information");
		
		
		
/*		-General Information-
		Comments
		Exception
		DOW
		Date
		A/F
		Tot Occ% */
		
		Textbox comments = new Textbox(data.getGroupDataTotal().getComments());
		comments.setInplace(true);
		comments.setReadonly(!isRowEditable);
		comments.setWidth(commentsWidth);
		comments.addEventListener("onChange", generalSectionListener);			
		row.appendChild(comments);
		
		int isException = data.getGroupDataTotal().getIsException();				
		String isExceptionVal = isException == 1 ? "Y" : "N";	
		Textbox isExceptionText = new Textbox(isExceptionVal);
		isExceptionText.setInplace(true);
		isExceptionText.setReadonly(!isRowEditable);
		isExceptionText.setConstraint(isExceptionConstraint);
		isExceptionText.setMaxlength(1);	
		isExceptionText.setWidth(commonWidth);
		isExceptionText.addEventListener("onChange", generalSectionListener);
		row.appendChild(isExceptionText);
		
		row.appendChild(new Label(data.getGroupDataTotal().getDow()));
		row.appendChild(new Label(data.getGroupDataTotal().getStatdate()));
		
		int isActual = data.getGroupDataTotal().getIsActual();
		String isActualVal = isActual == 1 ? "A" : "F";				
		row.appendChild(new Label(isActualVal));
		
		row.appendChild(new Label(String.valueOf(formatter.format(data.getGroupDataTotal().getTotOccPct()))));
		
		
/*		-Totals-
		Tot Occ
		Tot Rev
		Tot ADR
		Def Occ
		Def Rev
		Def ADR
		Ten Occ
		Ten Rev
		Ten ADR*/
		
		row.appendChild(new Label(String.valueOf(formatter.format(data.getGroupDataTotal().getgTotOcc()))));
		row.appendChild(new Label(String.valueOf(formatter.format(data.getGroupDataTotal().getgTotRev()))));
		row.appendChild(new Label(String.valueOf(formatter.format(data.getGroupDataTotal().getgTotAdr()))));
		row.appendChild(new Label(String.valueOf(formatter.format(data.getGroupDataTotal().getgDefOcc()))));
		row.appendChild(new Label(String.valueOf(formatter.format(data.getGroupDataTotal().getgDefRev()))));
		row.appendChild(new Label(String.valueOf(formatter.format(data.getGroupDataTotal().getgDefAdr()))));
		row.appendChild(new Label(String.valueOf(formatter.format(data.getGroupDataTotal().getgTenOcc()))));
		row.appendChild(new Label(String.valueOf(formatter.format(data.getGroupDataTotal().getgTenRev()))));
		row.appendChild(new Label(String.valueOf(formatter.format(data.getGroupDataTotal().getgTenAdr()))));
		
/*		-Segment Information-
		TOcc
		TADR
		DOcc
		DADR
		TOcc
		TADR*/
		
		for(int i=0; i<data.getGroupDataSegmentList().length; i++) {
			
			boolean actual = data.getGroupDataTotal().getIsActual() == 1;
			
			//The fist two ones are not editable, so we keep it simple as Labels
			row.appendChild(new Label(String.valueOf(data.getGroupDataSegmentList()[i].getTotOcc())));
			row.appendChild(new Label(String.valueOf(data.getGroupDataSegmentList()[i].getTotAdr())));
			//Definitive is editable			
			Intbox DefOCCText = new Intbox(data.getGroupDataSegmentList()[i].getDefOcc());
			Decimalbox DefADRText = new Decimalbox(data.getGroupDataSegmentList()[i].getDefAdr());
			DefADRText.setFormat(ADRFormat);
			DefADRText.setLocale(Locale.US);			
			DefOCCText.setInplace(true);
			DefADRText.setInplace(true);			
			DefOCCText.setReadonly(actual || !isRowEditable ? true : false);
			DefADRText.setReadonly(actual || !isRowEditable ? true : false);			
			DefOCCText.setWidth(commonWidth);
			DefADRText.setWidth(commonWidth);
			DefOCCText.setConstraint(" no empty");
			DefADRText.setConstraint("no empty");
			DefOCCText.setAttribute("segmentSeq", i+1);
			DefADRText.setAttribute("segmentSeq", i+1);
			DefOCCText.setAttribute("segmentId", data.getGroupDataSegmentList()[i].getSegmentId());
			DefADRText.setAttribute("segmentId", data.getGroupDataSegmentList()[i].getSegmentId());
			DefOCCText.addEventListener("onChange", definitiveSegmentSectionListener);
			DefADRText.addEventListener("onChange", definitiveSegmentSectionListener);
			row.appendChild(DefOCCText);
			row.appendChild(DefADRText);
			//Tentative is editable
			Intbox TenOCCText = new Intbox(data.getGroupDataSegmentList()[i].getTenOcc());
			Decimalbox TenADRText = new Decimalbox(data.getGroupDataSegmentList()[i].getTenAdr());
			TenADRText.setFormat(ADRFormat);
			TenADRText.setLocale(Locale.US);			
			TenOCCText.setInplace(true);
			TenADRText.setInplace(true);			
			TenOCCText.setReadonly(actual || !isRowEditable ? true : false);
			TenADRText.setReadonly(actual || !isRowEditable ? true : false);			
			TenOCCText.setWidth(commonWidth);
			TenADRText.setWidth(commonWidth);
			TenOCCText.setConstraint(" no empty");
			TenADRText.setConstraint("no empty");
			TenOCCText.setAttribute("segmentSeq", i+1);
			TenADRText.setAttribute("segmentSeq", i+1);
			TenOCCText.setAttribute("segmentId", data.getGroupDataSegmentList()[i].getSegmentId());
			TenADRText.setAttribute("segmentId", data.getGroupDataSegmentList()[i].getSegmentId());
			TenOCCText.addEventListener("onChange", tentativeSegmentSectionListener);
			TenADRText.addEventListener("onChange", tentativeSegmentSectionListener);
			row.appendChild(TenOCCText);
			row.appendChild(TenADRText);
			
			//el Group tiene 6 columnas por segmento dentro del grid
			//los dos primeros de la izquierda corresponde a los totales esos NO son editables.. los que si son los siguientes 4.. 2 son de tentative y 2 de Definitive
			
		}				
		row.setAlign("center");
		//Set Date as attribute to know what the date of this row
		row.setAttribute("statedate", data.getGroupDataTotal().getStatdate());	
		
				//row.setHeight("10px");	
	}

}
