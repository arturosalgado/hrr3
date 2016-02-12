package com.hrr3.controller;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import com.hrr3.authentication.AuthenticationServiceHRR3Impl;
import com.hrr3.entity.SSRSnapshot;
import com.hrr3.entity.WeeklyMtgMinSalesPaces;
import com.hrr3.entity.ssrMigration.SSRSnapshotSUWklyMtg;
import com.hrr3.model.SSRSnapshotDAO;
import com.hrr3.model.WeeklyMeetingSetUpDAO;
import com.hrr3.services.AuthenticationService;

public class WeeklyRoomsTabController extends SelectorComposer<Component> {
	
	private static final long serialVersionUID = 1L;
	
	//AuthService
	private AuthenticationService authService;
	
	private ListModel<SSRSnapshot> ssrSnapshotsModel;	
	private ListModel<WeeklyMtgMinSalesPaces> salesPaceModel;
	private String transotbEntered;
	private SSRSnapshotSUWklyMtg currentWeeklySnapshot;
	
	@Wire
	Combobox ssrSnapshotsCombo;
	@Wire
	Grid salesPaceGrid;
	
	//DAO
	private WeeklyMeetingSetUpDAO weeklyDAO;
	private SSRSnapshotDAO ssrSnapshotDAO;
	
	
	
	public WeeklyRoomsTabController() {
		
		authService = new AuthenticationServiceHRR3Impl();		
		
		if(authService.getUserData().getCurrentHotel() == null || authService.getUserData().getCurrentHotel().getHotelId() ==null || authService.getUserData().getCurrentHotel().getHotelId() < 1)
			{Messagebox.show("Please select a valid Hotel and return to this section."); return;}
		
		ssrSnapshotDAO = new SSRSnapshotDAO(authService.getUserData().getCurrentHotel());
		weeklyDAO = new WeeklyMeetingSetUpDAO(authService.getUserData().getCurrentHotel());
		
		List<SSRSnapshot> snapshotsModelList = ssrSnapshotDAO.getAllSSRSnapshot("REPORT_SNAPSHOT");
		ssrSnapshotsModel = new ListModelList<SSRSnapshot>(snapshotsModelList);
		
		//Set the label at tab startup
		currentWeeklySnapshot = weeklyDAO.fillDataWklyMtg().getSsrSnapshotSUWklyMtg();
		transotbEntered = currentWeeklySnapshot.getTransotbEntered();
		
	}
	
	public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        
        //Create columns for month dinamically       
       
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);        
        Columns gridCols = this.salesPaceGrid.getColumns();        
        gridCols.getChildren().addAll(this.getCustomColumns(currentMonth));
        
	}
	
	private String getMonthName(int month) {
		
		String monthName = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (month >= 1 && month <= 12 ) {
        	monthName = months[month - 1];
        }
        
        else
        	monthName = months[month%12 - 1];
        
        return monthName.substring(0, 3); //Get first 3 chars
	}
	
	private List<Column> getCustomColumns(int fromMonthIndex) {
		
		List<Column> columns = new ArrayList<Column>();
		
		for(int monthIndex=1; monthIndex <= 12 ; monthIndex++) {
			
			Column column = new Column();
			column.setWidth("65px");
			column.setLabel(this.getMonthName(++fromMonthIndex));
			columns.add(column);
		}
		
		return columns;
	}
	
	
	@Listen("onClick = #loadDataBtn")
    public void loadData() {
		
		if(this.ssrSnapshotsCombo.getSelectedIndex() == 0) {
    		Messagebox.show("Please select a Snapshot from the list.", "SSR - Weekly Meeting", Messagebox.OK, Messagebox.EXCLAMATION);
    		return;
    	} 
		
		Integer ssrSnapshotId = this.ssrSnapshotsCombo.getSelectedItem().getValue();
 
		Date today = new Date();
		
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  		  
 	  	Calendar dateFromCalendar = Calendar.getInstance();  	
 	  	Calendar dateToCalendar = Calendar.getInstance();  
 	  	dateFromCalendar.setTime(today); 
 	  	dateToCalendar.setTime(today); 
 	  	
 	  	dateFromCalendar.set(Calendar.DAY_OF_MONTH, 1);//Set first day of the month
 	  	
 	  	dateToCalendar.add(Calendar.YEAR, 1);//Next year ie: 2014-10-01
 	  	dateToCalendar.set(Calendar.DAY_OF_MONTH, 1);//Set first day of the month
 	  	dateToCalendar.add(Calendar.DATE, -1);//minus 1 day, to get last day of previous month ie 2015-09-31
 	  	
 	  	Date dateFrom = dateFromCalendar.getTime();
 	  	Date dateTo = dateToCalendar.getTime();
 	  	
 	  	System.out.println("******* dateFrom: " + sdf.format(dateFrom) + " ****************");
 	  	System.out.println("******* dateTo: " + sdf.format(dateTo) + " ****************");
			
		List<WeeklyMtgMinSalesPaces> salesPaceList = weeklyDAO.fillDataSalesPaces(ssrSnapshotId, sdf.format(dateFrom), sdf.format(dateTo), authService.getUserData().getCurrentCustomer().getCustomerId());
		//this.orderListStartingByCurrentMonth(salesPaceList);
		salesPaceModel = new ListModelList<WeeklyMtgMinSalesPaces>(salesPaceList);
		salesPaceGrid.setModel(salesPaceModel);
		salesPaceGrid.setRowRenderer(new WeeklySalesPaceRender());
		salesPaceGrid.setSizedByContent(true);
		
    }
	
	@Listen("onCreate=#ssrSnapshotsCombo;")
	public void selectSnapshot(){
	
		SSRSnapshot ssrSnapshotSaved = new SSRSnapshot();
		if(currentWeeklySnapshot.getSavedSnapshotid() == 0)
			this.ssrSnapshotsCombo.setSelectedIndex(0);
		else {
			ssrSnapshotSaved.setSnapshotId(currentWeeklySnapshot.getSavedSnapshotid());
			this.ssrSnapshotsCombo.setSelectedIndex((((ListModelList<SSRSnapshot>)this.ssrSnapshotsModel).indexOf(ssrSnapshotSaved)));	
		}				
	}

	/**
	 * @return the ssrSnapshotsModel
	 */
	public ListModel<SSRSnapshot> getSsrSnapshotsModel() {
		return ssrSnapshotsModel;
	}

	/**
	 * @param ssrSnapshotsModel the ssrSnapshotsModel to set
	 */
	public void setSsrSnapshotsModel(ListModel<SSRSnapshot> ssrSnapshotsModel) {
		this.ssrSnapshotsModel = ssrSnapshotsModel;
	}

	/**
	 * @return the salesPaceModel
	 */
	public ListModel<WeeklyMtgMinSalesPaces> getSalesPaceModel() {
		return salesPaceModel;
	}

	/**
	 * @param salesPaceModel the salesPaceModel to set
	 */
	public void setSalesPaceModel(ListModel<WeeklyMtgMinSalesPaces> salesPaceModel) {
		this.salesPaceModel = salesPaceModel;
	}

	/**
	 * @return the transotbEntered
	 */
	public String getTransotbEntered() {
		return transotbEntered;
	}

	/**
	 * @param transotbEntered the transotbEntered to set
	 */
	public void setTransotbEntered(String transotbEntered) {
		this.transotbEntered = transotbEntered;
	}

	
	
}
