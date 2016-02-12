package com.hrr3.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.impl.InputElement;
import org.zkoss.zul.impl.XulElement;

import com.hrr3.authentication.AuthenticationServiceHRR3Impl;
import com.hrr3.entity.ssr.SSRInputData;
import com.hrr3.model.LRRDAO;
import com.hrr3.model.SSRInputDAO;
import com.hrr3.services.AuthenticationService;

public class SSRInputRender extends GridArrowKeyRender implements RowRenderer<SSRInputData>{
	
	int curr_customerId;
	int curr_segmentId;
	String curr_statedate;	
	
	SSRInputData currentDataRow;//Current data row
	Row rowToUpdate;//To save currentRow being edited
	
	Grid grid;	
	
	
	
	private EventListener<InputEvent> generalSectionListener = new EventListener<InputEvent>() {		
	    public void onEvent(InputEvent event) throws ParseException {
	    	
	    	AuthenticationService authService = new AuthenticationServiceHRR3Impl();
	    	SSRInputDAO ssrInputDAO = new SSRInputDAO(authService.getUserData().getCurrentHotel());  	
	    		    	
	    	//Event info
	    	rowToUpdate = (Row) event.getTarget().getParent();   
	    	//Section for Arrow Keys initialization
	    	InputElement ie = ((InputElement)event.getTarget());	    	
	    	lstComponents = (List<XulElement>) grid.getAttribute("lstComponents");
	    	int currentIndex = getCurrentIndex(ie);
	    	
	    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    	SimpleDateFormat OridateFormat = new SimpleDateFormat("MM/dd/yyyy");
	    	
	    	//General Info
	    	Date dateObj = OridateFormat.parse((String)rowToUpdate.getAttribute("statedate"));
	    	String statedate = dateFormat.format(dateObj);
	    	Textbox commentsComponent = (Textbox)rowToUpdate.getChildren().get(0);
	    	Textbox exceptionComponent = (Textbox)rowToUpdate.getChildren().get(1);
	    	Textbox rateA1 = (Textbox)rowToUpdate.getChildren().get(8);
	    	Textbox rateB2 = (Textbox)rowToUpdate.getChildren().get(9);
	    	Textbox rateC3 = (Textbox)rowToUpdate.getChildren().get(10);
	    	Textbox rateD4 = (Textbox)rowToUpdate.getChildren().get(11);
	    	Textbox rateE5 = (Textbox)rowToUpdate.getChildren().get(12);
	    	Textbox rateF6 = (Textbox)rowToUpdate.getChildren().get(13);
	    	Textbox rateG7 = (Textbox)rowToUpdate.getChildren().get(14);
	    	Textbox rateH8 = (Textbox)rowToUpdate.getChildren().get(15);
	    	Textbox rateI9 = (Textbox)rowToUpdate.getChildren().get(16);
	    	Textbox rateHP1 = (Textbox)rowToUpdate.getChildren().get(17);
	    	Textbox rateHP2 = (Textbox)rowToUpdate.getChildren().get(18);
	    	Textbox oFactor = (Textbox)rowToUpdate.getChildren().get(19);
	    	
	    	String comment = commentsComponent.getText();
	    	int exception = exceptionComponent.getText().equalsIgnoreCase("Y") ? 1 : 0;	    	
	    	
	    	SSRInputData ssrDataToUpdate = new SSRInputData();
	    	//Update Comment,Exception and Rates values (A/1, B/2, B/3, ... I/9, HP1, HP2, Over sell factor) ONLY if Actual = F
	    	ssrDataToUpdate.setStatdate(statedate);
	    	ssrDataToUpdate.setComment(comment);
	    	ssrDataToUpdate.setIsException(exception);
	    	ssrDataToUpdate.setA1(rateA1.getText());
	    	ssrDataToUpdate.setB2(rateB2.getText());
	    	ssrDataToUpdate.setC3(rateC3.getText());
	    	ssrDataToUpdate.setD4(rateD4.getText());
	    	ssrDataToUpdate.setE5(rateE5.getText());
	    	ssrDataToUpdate.setF6(rateF6.getText());
	    	ssrDataToUpdate.setG7(rateG7.getText());
	    	ssrDataToUpdate.setH8(rateH8.getText());
	    	ssrDataToUpdate.setI9(rateI9.getText());
	    	ssrDataToUpdate.setHp1(rateHP1.getText());
	    	ssrDataToUpdate.setHp2(rateHP2.getText());
	    	ssrDataToUpdate.setOversellFactor(oFactor.getText());
	    	
	    	int generalInfoResult = ssrInputDAO.UpdateSSRData(ssrDataToUpdate);
	    	LRRDAO lrrDAO = new LRRDAO(authService.getUserData().getCurrentHotel());
	    	String lrr = lrrDAO.calSSRLRR(authService.getUserData().getCurrentHotel().getHotelId(),statedate, statedate);
	    	if(generalInfoResult == 0) { Messagebox.show("Error updating GeneralInfo in database."); return ;}	 
	    	refreshSummaryAndTotals(statedate);
	    	
	    	//Section for Up/Right/Up/Down logic
	    	sortElements(grid.getRows());
	    	//Reset again
	    	grid.setAttribute("lstComponents", lstComponents);
	    	grid.setAttribute("currentIndex", currentIndex);
	    	//Use same index
	    	nextElement((InputElement)lstComponents.get(currentIndex));
	    }	
	   
	};
	
	
	
	private EventListener<InputEvent> segmentSectionListener = new EventListener<InputEvent>() {		
	    public void onEvent(InputEvent event) throws ParseException {
	    	
	    	AuthenticationService authService = new AuthenticationServiceHRR3Impl();
	    	SSRInputDAO ssrInputDAO = new SSRInputDAO(authService.getUserData().getCurrentHotel()); 	
	    	
	    	int generalCols = 20;//Represent the total# of Cols to allocate General+Total info.	    	
	    	int lastColToRead = (Integer)event.getTarget().getAttribute("segmentSeq")*1 + generalCols;//General Cols + SegmentSeg x 1 (OCC)
	    	int occColIndex = lastColToRead - 1;
	    	int segmentId = (Integer)event.getTarget().getAttribute("segmentId");  
	    	
	    	//Event info
	    	rowToUpdate = (Row) event.getTarget().getParent();   
	    	InputElement ie = ((InputElement)event.getTarget());	    	
	    	lstComponents = (List<XulElement>) grid.getAttribute("lstComponents");
	    	int currentIndex = getCurrentIndex(ie);
	    	
	    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    	SimpleDateFormat OridateFormat = new SimpleDateFormat("MM/dd/yyyy");
	    	
	    	//General Info
	    	Date dateObj = OridateFormat.parse((String)rowToUpdate.getAttribute("statedate"));
	    	String statedate = dateFormat.format(dateObj);
	      	
	    	//Get current OCC segment info being edited
	    	Decimalbox occDecimalbox = (Decimalbox)rowToUpdate.getChildren().get(occColIndex);
	    	BigDecimal occValue = occDecimalbox.getValue();
	    	
	    	int generalInfoResult = ssrInputDAO.UpdateSSRSegmentData(statedate, segmentId, occValue);	    	
	    	if(generalInfoResult == 0) { Messagebox.show("Error updating SSR - SegmentInfo in database."); return ;}	 
	    	refreshSummaryAndTotals(statedate);
	    	
	    	//Section for Up/Right/Up/Down logic
	    	sortElements(grid.getRows());
	    	//Reset again
	    	grid.setAttribute("lstComponents", lstComponents);
	    	grid.setAttribute("currentIndex", currentIndex);
	    	//Use same index
	    	nextElement((InputElement)lstComponents.get(currentIndex));
	    }	
	   
	};
	
	//refresh summary section and total for the date
	public void refreshSummaryAndTotals(String statedate) {
		
			AuthenticationService authService = new AuthenticationServiceHRR3Impl();
			int customerId = authService.getUserData().getCurrentCustomer().getCustomerId();
			int userId = authService.getUserData().getUserId();	
			
			SSRInputDAO ssrDAO = new SSRInputDAO(authService.getUserData().getCurrentHotel());			
		   	//Get summaryDataToUpdate
	    	List<SSRInputData> allData = ssrDAO.getSSRInputData(statedate, statedate, userId, customerId);
	    	
			//Find first row for Summary&Total data
			//Row summaryRow = (Row)this.grid.getRows().getFirstChild();
			//Delete content by setting to null and creating a new Row
			//summaryRow.getChildren().clear();
			rowToUpdate.getChildren().clear();			 
						
			//Choose first one containing Summary data		
			//this.render(summaryRow, allData.get(0), -1);
			//Choose row that was updated to show new totals for that date
			this.render(rowToUpdate, allData.get(1), -1);
			//Once filled in with data, render both
			
		}
	
	public void render(Row row, SSRInputData data, int index){

		this.currentDataRow = data;//To save row state
		if(index >= 0)	this.grid = row.getGrid();//Only if render is not called manually
		
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		String commonWidth = "40px";
		String rateWidth = "25px";
		String commentsWidth = "120px";
		String segmentsWidth = "70px";
		String occFormat = "###,###,##0";
		String isExceptionConstraint = "/([Y|N]{1})/: Introduce a valid value[Y/N].";
		symbols.setGroupingSeparator(',');
		boolean isRowEditable = !data.getComment().equalsIgnoreCase("Summary Information");	
		int isActual = data.getIsActual();
		String isActualVal = isActual == 1 ? "A" : "F";		
		boolean actual = isActual == 1 ? true : false;	
		
		/*		-General Information-
		Comments
		Exception
		DOW
		Date
		A/F */
		
		Textbox comments = new Textbox(data.getComment());
		comments.setInplace(true);
		comments.setReadonly(actual || !isRowEditable ? true : false);
		comments.setWidth(commentsWidth);
		comments.addEventListener("onChange", generalSectionListener);			
		row.appendChild(comments);
		
		int isException = data.getIsException();				
		String isExceptionVal = isException == 1 ? "Y" : "N";	
		Textbox isExceptionText = new Textbox(isExceptionVal);
		isExceptionText.setInplace(true);
		isExceptionText.setReadonly(actual || !isRowEditable ? true : false);
		isExceptionText.setConstraint(isExceptionConstraint);
		isExceptionText.setMaxlength(1);	
		isExceptionText.setWidth(commonWidth);
		isExceptionText.addEventListener("onChange", generalSectionListener);
		row.appendChild(isExceptionText);
				
		row.appendChild(new Label(data.getDow()));
		row.appendChild(new Label(data.getStatdate()));		
		row.appendChild(new Label(isActualVal));
		
		
		/*		-Totals-
		Occ%
		Occ Rms
		LRR
		*/
		
		row.appendChild(new Label(String.valueOf(formatter.format(data.getOccpcnt()))));
		row.appendChild(new Label(String.valueOf(formatter.format(data.getTotOcc()))));
		row.appendChild(new Label(data.getLrr()));
		
		/*		-Rate Categories-
		A/1 to I/9 , HP1, HP2, and Oversell Factor, */
		
		Textbox rateA1 = new Textbox(data.getA1());
		rateA1.setInplace(true);
		rateA1.setReadonly(actual || !isRowEditable ? true : false);
		rateA1.setWidth(rateWidth);
		rateA1.addEventListener("onChange", generalSectionListener);
		
		Textbox rateB2 = new Textbox(data.getB2());
		rateB2.setInplace(true);
		rateB2.setReadonly(actual || !isRowEditable ? true : false);
		rateB2.setWidth(rateWidth);
		rateB2.addEventListener("onChange", generalSectionListener);
		
		Textbox rateC3 = new Textbox(data.getC3());
		rateC3.setInplace(true);
		rateC3.setReadonly(actual || !isRowEditable ? true : false);
		rateC3.setWidth(rateWidth);
		rateC3.addEventListener("onChange", generalSectionListener);
		
		Textbox rateD4 = new Textbox(data.getD4());
		rateD4.setInplace(true);
		rateD4.setReadonly(actual || !isRowEditable ? true : false);
		rateD4.setWidth(rateWidth);
		rateD4.addEventListener("onChange", generalSectionListener);
		
		Textbox rateE5 = new Textbox(data.getE5());
		rateE5.setInplace(true);
		rateE5.setReadonly(actual || !isRowEditable ? true : false);
		rateE5.setWidth(rateWidth);
		rateE5.addEventListener("onChange", generalSectionListener);
		
		Textbox rateF6 = new Textbox(data.getF6());
		rateF6.setInplace(true);
		rateF6.setReadonly(actual || !isRowEditable ? true : false);
		rateF6.setWidth(rateWidth);
		rateF6.addEventListener("onChange", generalSectionListener);
		
		Textbox rateG7 = new Textbox(data.getG7());
		rateG7.setInplace(true);
		rateG7.setReadonly(actual || !isRowEditable ? true : false);
		rateG7.setWidth(rateWidth);
		rateG7.addEventListener("onChange", generalSectionListener);
		
		Textbox rateH8 = new Textbox(data.getH8());
		rateH8.setInplace(true);
		rateH8.setReadonly(actual || !isRowEditable ? true : false);
		rateH8.setWidth(rateWidth);
		rateH8.addEventListener("onChange", generalSectionListener);
		
		Textbox rateI9 = new Textbox(data.getI9());
		rateI9.setInplace(true);
		rateI9.setReadonly(actual || !isRowEditable ? true : false);
		rateI9.setWidth(rateWidth);
		rateI9.addEventListener("onChange", generalSectionListener);
		
		Textbox rateHP1 = new Textbox(data.getHp1());
		rateHP1.setInplace(true);
		rateHP1.setReadonly(actual || !isRowEditable ? true : false);
		rateHP1.setWidth(rateWidth);
		rateHP1.addEventListener("onChange", generalSectionListener);
		
		Textbox rateHP2 = new Textbox(data.getHp2());
		rateHP2.setInplace(true);
		rateHP2.setReadonly(actual || !isRowEditable ? true : false);
		rateHP2.setWidth(rateWidth);
		rateHP2.addEventListener("onChange", generalSectionListener);
		
		Textbox oFactor = new Textbox(data.getOversellFactor());
		oFactor.setInplace(true);
		oFactor.setReadonly(actual || !isRowEditable ? true : false);
		oFactor.setWidth(rateWidth);
		oFactor.addEventListener("onChange", generalSectionListener);
		
		row.appendChild(rateA1);
		row.appendChild(rateB2);
		row.appendChild(rateC3);
		row.appendChild(rateD4);
		row.appendChild(rateE5);
		row.appendChild(rateF6);
		row.appendChild(rateG7);
		row.appendChild(rateH8);
		row.appendChild(rateI9);
		row.appendChild(rateHP1);
		row.appendChild(rateHP2);
		row.appendChild(oFactor);
		
		
		/*		-Segment Information-
		Transient, GroupBlock, Group PU, Group Remain, Contract, Demand TD, Price TD*/
		Decimalbox rotbTrans = new Decimalbox(data.getRotbTrans());
		rotbTrans.setInplace(true);
		rotbTrans.setReadonly(actual || !isRowEditable ? true : false);
		rotbTrans.setWidth(segmentsWidth);
		rotbTrans.setAttribute("segmentSeq", 1);
		rotbTrans.setAttribute("segmentId", 1);//TODO research how to obtain segmentId
		rotbTrans.setConstraint(" no empty");
		rotbTrans.setFormat(occFormat);
		rotbTrans.setLocale(Locale.US);
		rotbTrans.addEventListener("onChange", segmentSectionListener);
		
		Decimalbox rotbGroup = new Decimalbox(data.getRotbGroup());
		rotbGroup.setInplace(true);
		rotbGroup.setReadonly(actual || !isRowEditable ? true : false);
		rotbGroup.setWidth(segmentsWidth);
		rotbGroup.setAttribute("segmentSeq", 2);
		rotbGroup.setAttribute("segmentId", 2);//TODO research how to obtain segmentId
		rotbGroup.setConstraint(" no empty");
		rotbGroup.setFormat(occFormat);
		rotbGroup.setLocale(Locale.US);
		rotbGroup.addEventListener("onChange", segmentSectionListener);
		
		Decimalbox grpPickedup = new Decimalbox(data.getGrpPickedup());
		grpPickedup.setInplace(true);
		grpPickedup.setReadonly(actual || !isRowEditable ? true : false);
		grpPickedup.setWidth(segmentsWidth);
		grpPickedup.setAttribute("segmentSeq", 3);
		grpPickedup.setAttribute("segmentId", 3);//TODO research how to obtain segmentId
		grpPickedup.setConstraint(" no empty");
		grpPickedup.setFormat(occFormat);
		grpPickedup.setLocale(Locale.US);
		grpPickedup.addEventListener("onChange", segmentSectionListener);
		
		Decimalbox grpRmsRem = new Decimalbox(data.getGrpRmsRem());
		grpRmsRem.setInplace(true);
		grpRmsRem.setReadonly(actual || !isRowEditable ? true : false);
		grpRmsRem.setWidth(segmentsWidth);
		grpRmsRem.setAttribute("segmentSeq", 4);
		grpRmsRem.setAttribute("segmentId", 4);//TODO research how to obtain segmentId
		grpRmsRem.setConstraint(" no empty");
		grpRmsRem.setFormat(occFormat);
		grpRmsRem.setLocale(Locale.US);
		grpRmsRem.addEventListener("onChange", segmentSectionListener);
		
		Decimalbox rotbCont = new Decimalbox(data.getRotbCont());
		rotbCont.setInplace(true);
		rotbCont.setReadonly(actual || !isRowEditable ? true : false);
		rotbCont.setWidth(segmentsWidth);
		rotbCont.setAttribute("segmentSeq", 5);
		rotbCont.setAttribute("segmentId", 5);//TODO research how to obtain segmentId
		rotbCont.setConstraint(" no empty");
		rotbCont.setFormat(occFormat);
		rotbCont.setLocale(Locale.US);
		rotbCont.addEventListener("onChange", segmentSectionListener);
		
		Decimalbox grpDemandtd = new Decimalbox(data.getGrpDemandtd());
		grpDemandtd.setInplace(true);
		grpDemandtd.setReadonly(actual || !isRowEditable ? true : false);
		grpDemandtd.setWidth(segmentsWidth);
		grpDemandtd.setAttribute("segmentSeq", 6);
		grpDemandtd.setAttribute("segmentId", 6);//TODO research how to obtain segmentId
		grpDemandtd.setConstraint(" no empty");
		grpDemandtd.setFormat(occFormat);
		grpDemandtd.setLocale(Locale.US);
		grpDemandtd.addEventListener("onChange", segmentSectionListener);
		
		Decimalbox grpPricetd = new Decimalbox(data.getGrpPricetd());
		grpPricetd.setInplace(true);
		grpPricetd.setReadonly(actual || !isRowEditable ? true : false);
		grpPricetd.setWidth(segmentsWidth);
		grpPricetd.setAttribute("segmentSeq", 7);
		grpPricetd.setAttribute("segmentId", 7);//TODO research how to obtain segmentId
		grpPricetd.setConstraint(" no empty");
		grpPricetd.setFormat(occFormat);
		grpPricetd.setLocale(Locale.US);
		grpPricetd.addEventListener("onChange", segmentSectionListener);
		
		row.appendChild(rotbTrans);
		row.appendChild(rotbGroup);
		row.appendChild(grpPickedup);
		row.appendChild(grpRmsRem);
		row.appendChild(rotbCont);
		row.appendChild(grpDemandtd);
		row.appendChild(grpPricetd);

		
		/*		-MAR Information-
		 * Seasonal MAR Rate*, MAR Rate
		 */
		
		//row.appendChild(new Label(String.valueOf(data.getSeasonalmarrate())));
		row.appendChild(new Label(data.getMarrate()));
		
		row.setAttribute("statedate", data.getStatdate());	
		
	}

}
