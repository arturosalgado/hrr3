package com.hrr3.controller.reports.forecast;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.impl.InputElement;
import org.zkoss.zul.impl.XulElement;

import com.hrr3.authentication.AuthenticationServiceHRR3Impl;
import com.hrr3.controller.ListboxArrowKeyRender;
import com.hrr3.entity.renovationImpact.ReportData;
import com.hrr3.entity.renovationImpact.UserPreferencesRenovationImpact;
import com.hrr3.model.RenovationImpactDAO;
import com.hrr3.services.AuthenticationService;

public class RenovationImpactRender extends ListboxArrowKeyRender implements ListitemRenderer<ReportData>{	
	
	
	public RenovationImpactRender(int headerCount) {
		super(headerCount);
		// TODO Auto-generated constructor stub
	}


	private  EventListener<InputEvent> oooRoomsColumnListener = new EventListener<InputEvent>() {		
	    public void onEvent(InputEvent event) throws ParseException {
	    	    	
	    	Listitem rowToUpdate = (Listitem) event.getTarget().getParent().getParent();
	    	Listbox listbox = rowToUpdate.getListbox();
	    	
	    	//Section for Arrow Keys initialization
	    	InputElement ie = ((InputElement)event.getTarget());	    	
	    	lstComponents = (List<XulElement>) listbox.getAttribute("lstComponents");
	    	int currentIndex = getCurrentIndex(ie);
	    	
	    	ReportData currentDataRow = (ReportData)rowToUpdate.getAttribute("currentDataRow");
	    	int newOOORooms = Integer.parseInt(event.getValue());
	    	
	    	AuthenticationService authService = new AuthenticationServiceHRR3Impl();	   	    	
	    	RenovationImpactDAO dao = new RenovationImpactDAO(authService.getUserData().getCurrentHotel());	    	
	    	currentDataRow.setOooRooms(newOOORooms);	    	
	    	//Event info	    	
	    	UserPreferencesRenovationImpact updatedData = dao.updateInputRowData(currentDataRow.getUserPreferencesObj(), currentDataRow);	    	
	    	List<ReportData> gridList = new ArrayList<ReportData>();    		    	
	    	List<ReportData> summaryList = updatedData.getLstSummaryReportData();
	    	List<ReportData> reportList = updatedData.getLstReportData();	    	
	    	gridList.addAll(summaryList);    	
	    	gridList.addAll(reportList);	    	
	    	ListModel<ReportData> data = listbox.getListModel();
	    	((ListModelList<ReportData>)data).clear();
	    	((ListModelList<ReportData>)data).addAll(gridList);	 
	    	listbox.setItemRenderer(new RenovationImpactRender(2));//2fixed rows to avoid reading.
	    	listbox.renderAll();
	    	//Section for Up/Right/Up/Down logic
	    	sortElements(listbox);
	    	//Reset again
	    	listbox.setAttribute("lstComponents", lstComponents);
	    	//Use same index
	    	nextElement((InputElement)lstComponents.get(currentIndex), false);
	    	
	    }	   
	};
	
	
	@Override
	public void render(Listitem row, ReportData data, int index)
			throws Exception {
				
		DateFormat uiFormat = new SimpleDateFormat("MM/dd/yyyy"); 
		DateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		String commonWidth = "40px";
		
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		//String decimalFormat = "###,###,##0.00";		
		String integerFormat = "###,###,##0";
		symbols.setGroupingSeparator(',');
		

		//General information
		Label comments = new Label(data.getComments());
		Label dow = null;
		if(data.isSummaryRow())
			dow = new Label("");//CR by Carl - Hide DOW in Summary
		else
			dow = new Label(data.getDow());
		String dateStr = data.getStatDate() !=null && !data.getStatDate().isEmpty() ? uiFormat.format(dbFormat.parse(data.getStatDate())) : "";
		Label date = new Label(dateStr);		
		//AF
		Label af = new Label(data.getaF().equalsIgnoreCase("False") ? "F" : "A");
		//vacantSellable
		Label vacantSellable = new Label(String.valueOf(data.getVacant()));
		//occPtgAvail000
		Label occPtgAvail000 = new Label(formatter.format(data.getOccPctOOOAvailable()));
		//occPctTtlAvail
		Label occPctTtlAvail = new Label(formatter.format(data.getOccPctTtlAvailable()));
		//occRooms
		Label occRooms = new Label(String.valueOf(data.getOccRooms()));
		//ttlADR
		Label ttlADR = new Label(formatter.format(data.gettTlADR()));
		//ttlRevenue
		Label ttlRevenue = new Label(formatter.format(data.gettTlRevenue()));
		//demandTdowns
		Label demandTdowns = new Label(String.valueOf(data.getDemandTdowns()));
		//oooRooms
		Label oooRoomsLbl = null;
		Intbox oooRoomsIntbox = null;
		if(data.isSummaryRow())		
			oooRoomsLbl = new Label(String.valueOf(data.getOooRooms()));
		else {
			oooRoomsIntbox = new Intbox(data.getOooRooms());
			oooRoomsIntbox.setFormat(integerFormat);	
			oooRoomsIntbox.setLocale(Locale.US);
			oooRoomsIntbox.setInplace(true);
			oooRoomsIntbox.setConstraint(" no empty");
			oooRoomsIntbox.setWidth(commonWidth);
			
			//add listener to trigger oooRooms changes.
			oooRoomsIntbox.addEventListener("onChange", oooRoomsColumnListener);			
		}
					
		//displacedRooms
		Label displacedRooms = new Label(String.valueOf(data.getDisplacedRooms()));
		//displacedRevenue
		Label displacedRevenue = new Label(formatter.format(data.getDisplacedRevenue()));
		
		//Add to row
		Listcell lc1 = new Listcell();
		lc1.appendChild(comments);
		row.appendChild(lc1);
		
		Listcell lc2 = new Listcell();
		lc2.appendChild(dow);
		row.appendChild(lc2);
		
		Listcell lc3 = new Listcell();
		lc3.appendChild(date);
		row.appendChild(lc3);
		
		Listcell lc4 = new Listcell();
		lc4.appendChild(af);
		row.appendChild(lc4);
		
		Listcell lc5 = new Listcell();
		lc5.appendChild(vacantSellable);
		row.appendChild(lc5);
		
		Listcell lc6 = new Listcell();
		lc6.appendChild(occPtgAvail000);		
		row.appendChild(lc6);
		
		Listcell lc7 = new Listcell();
		lc7.appendChild(occPctTtlAvail);
		row.appendChild(lc7);
		
		Listcell lc8 = new Listcell();
		lc8.appendChild(occRooms);
		row.appendChild(lc8);
		
		Listcell lc9 = new Listcell();
		lc9.appendChild(ttlADR);
		row.appendChild(lc9);
		
		Listcell lc10 = new Listcell();
		lc10.appendChild(ttlRevenue);
		row.appendChild(lc10);
		
		Listcell lc11 = new Listcell();
		lc11.appendChild(demandTdowns);
		row.appendChild(lc11);
		
		if(data.isSummaryRow()) {
			Listcell lc12 = new Listcell();
			lc12.appendChild(oooRoomsLbl);
			row.appendChild(lc12);
		}
			
		else
			{
			Listcell lc12 = new Listcell();
			lc12.appendChild(oooRoomsIntbox);
			row.appendChild(lc12);
			}
		
		Listcell lc14 = new Listcell();
		lc14.appendChild(displacedRooms);
		row.appendChild(lc14);
		
		Listcell lc15 = new Listcell();
		lc15.appendChild(displacedRevenue);
		row.appendChild(lc15);
		
		row.setAttribute("currentDataRow", data);
		
	}


	

	

}
