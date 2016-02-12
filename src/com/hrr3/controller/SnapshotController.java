package com.hrr3.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Textbox;

import com.hrr3.authentication.AuthenticationServiceHRR3Impl;
import com.hrr3.entity.Hotel;
import com.hrr3.entity.Snapshot;
import com.hrr3.entity.User;
import com.hrr3.model.HotelDAO;
import com.hrr3.model.SnapshotDAO;
import com.hrr3.services.AuthenticationService;

public class SnapshotController extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;
	
	private ListModel<Snapshot> snapshotsModel;
	
	private AuthenticationService authService;
	
	private final static int SNAPSHOT_TAKE = 1;
	
	private final static int SNAPSHOT_SHOW = 2;
	
	private final static int SNAPSHOT_DELETE = 3;
			
	private final static int SNAPSHOT_RESTORE = 4;		
	
	@Wire
	Listbox snapshotListBox;
	
	//Snapshot Form vars
	@Wire
	Textbox snapshotName;
	
	@Wire
	Combobox snapshotMonth;
	
	@Wire
	Intbox snapshotYear;
	
	@Wire
	Label snapshotYearLabel;
	
	@Wire
	Checkbox snapshotFutureOnly;
	
	@Wire
	Checkbox snapshotCF;
	
	@Wire
	Checkbox snapshotUF;
	
	@Wire
	Checkbox snapshotQF;
	
	@Wire
	Checkbox snapshotBU;
	
	@Wire
	Button takeSnapshotButton; 
	
	@Wire
	Button restoreSnapshotButton;
	
	@Wire
	Button deleteSnapshotButton;
	
	@Wire
	Button saveAsUFSnapshotButton;
	
	@Wire
	Button editNameSnapshotButton;
	
	@Wire
	Datebox snapshotRestoreFrom;
	
	@Wire
	Datebox snapshotRestoreTo;

	SnapshotDAO snapshotDAO;
	
	
	public SnapshotController() {
		
		authService = new AuthenticationServiceHRR3Impl();
		
		if(authService.getUserData().getCurrentHotel() == null || authService.getUserData().getCurrentHotel().getHotelId() ==null || authService.getUserData().getCurrentHotel().getHotelId() < 1)
			{Messagebox.show("Please select a valid Hotel and return to this section."); return;}
		
		
		Hotel currenthotel = authService.getUserData().getCurrentHotel();		
        snapshotDAO = new SnapshotDAO(currenthotel);        
        List<Snapshot> snapshotList = snapshotDAO.getAllSnapshots("ADD_SNAPSHOT");                
        snapshotsModel = new ListModelList<Snapshot>(snapshotList);
        ((ListModelList<Snapshot>)snapshotsModel).setMultiple(true);
        
     
		
    }
	
	 @Listen("onCheck =#snapshotFutureOnly")
	 public void disableCurrentForecastCombo() {
		 
		 snapshotYear.setVisible(!snapshotFutureOnly.isChecked()); snapshotYearLabel.setVisible(!snapshotFutureOnly.isChecked());
		 
		 if(snapshotFutureOnly.isChecked())
			 snapshotCF.setChecked(false); snapshotUF.setChecked(false);snapshotQF.setChecked(false); snapshotBU.setChecked(false);
		 
	 }
	
	private void reloadSnapshotListbox() {
		
		Hotel currenthotel = authService.getUserData().getCurrentHotel();	
		HotelDAO hotelDAO = new HotelDAO();
		Hotel updatedHotel = hotelDAO.getHotelDetails(currenthotel.getHotelId());
		//Get userData and set currentHotel updated with latest snapshotId/hotelRooms, etc info.
		Session sess = Sessions.getCurrent();
		User user = (User)sess.getAttribute("userData");
		user.setCurrentHotel(updatedHotel);
		//Update currentHotel for the snapshotDAO for future usage.
        snapshotDAO.setCurrentHotel(updatedHotel);
        //Get snapshot list and set Model again.
        List<Snapshot> snapshotList = snapshotDAO.getAllSnapshots("ADD_SNAPSHOT");                
        snapshotsModel = new ListModelList<Snapshot>(snapshotList);
        this.snapshotListBox.setModel(snapshotsModel);
        ((ListModelList<Snapshot>)snapshotsModel).setMultiple(true);
        //Initialize form controls to take a new snapshot.
        this.initializeFormControls(SNAPSHOT_TAKE);
	}
	
	
	
	@Listen("onFocus=#snapshotName")
    public void enableEditButton() {
		
		Set<Snapshot> selectedSnapshot = ((ListModelList<Snapshot>)snapshotsModel).getSelection();
        int size = selectedSnapshot.size();
        
        //User selected a valid snapshot in the list
        if(size > 0 && snapshotListBox.getSelectedIndex() > 0) {
        	editNameSnapshotButton.setDisabled(false);
        }
		
	}
    @Listen("onSelect =#snapshotListBox")
    public void populateSnapshotForm() {
    	
        Set<Snapshot> selectedSnapshot = ((ListModelList<Snapshot>)snapshotsModel).getSelection();
        int size = selectedSnapshot.size();
        
        //User selected a valid snapshot in the list
        if(size > 0 && snapshotListBox.getSelectedIndex() > 0) {
        	
        	Iterator<Snapshot> iterator = selectedSnapshot.iterator();
        	Snapshot lastSnapshotSelected = null;
        	
        	do{
        		lastSnapshotSelected = iterator.next();
        	
        	} while (iterator.hasNext());
        	
        	snapshotName.setValue(lastSnapshotSelected.getName());        	
        	snapshotMonth.setSelectedIndex(lastSnapshotSelected.getMonth());
        	snapshotYear.setValue(lastSnapshotSelected.getYear());
        	snapshotUF.setChecked(lastSnapshotSelected.getForecast() == 1 ? true : false);
        	snapshotCF.setChecked(lastSnapshotSelected.getCurrentForecast()== 1 ? true : false);
        	snapshotQF.setChecked(lastSnapshotSelected.getQuarterlyForecast() == 1 ? true : false);
        	snapshotBU.setChecked(lastSnapshotSelected.getBudget() == 1 ? true : false);
        	
        	initializeFormControls(SnapshotController.SNAPSHOT_SHOW);
        }
        	
        	
        
        //If selected item is the Add Snapshot dummy option, meaning the form will be used to TAKE SNAPSHOT
        else if(size > 0 && snapshotListBox.getSelectedIndex() == 0) {
        	
        	initializeFormControls(SnapshotController.SNAPSHOT_TAKE);	
        	//Show message saying please fill out the form.
        	String addMessage = "Now fill out the form, and then click on TAKE SNAPSHOT.";
        	Clients.showNotification(addMessage,"info",snapshotListBox,"top_right",2500);
        	
        }
        
        else
        	
        {
        	Messagebox.show("Error ocurred."); return;
        }
         
    }
    
    public void initializeFormControls(int type) {
    	
    	if(type == SnapshotController.SNAPSHOT_TAKE) {
    		
        	snapshotName.setValue("");        	
    		snapshotMonth.setSelectedIndex(0);
    		snapshotYear.setValue(null);
    		snapshotCF.setChecked(false);
    		snapshotUF.setChecked(false);
    		snapshotQF.setChecked(false);
    		snapshotBU.setChecked(false);
    		snapshotFutureOnly.setChecked(false);
    		
    		takeSnapshotButton.setDisabled(false);
    		deleteSnapshotButton.setDisabled(true);
    		restoreSnapshotButton.setDisabled(true); 
    		saveAsUFSnapshotButton.setDisabled(true);
    		editNameSnapshotButton.setDisabled(true);
    		    		
    		snapshotYear.setVisible(true);    		
        	snapshotYearLabel.setVisible(true);
    	}
    	
    	else if(type == SnapshotController.SNAPSHOT_SHOW) {
    		
    		snapshotFutureOnly.setChecked(false);        	
        	snapshotRestoreFrom.setDisabled(false);
        	snapshotRestoreTo.setDisabled(false);        	
        	takeSnapshotButton.setDisabled(true);
        	deleteSnapshotButton.setDisabled(false);
        	restoreSnapshotButton.setDisabled(false);  
        	saveAsUFSnapshotButton.setDisabled(false);
        	editNameSnapshotButton.setDisabled(true);
        	snapshotYear.setVisible(true);
        	snapshotYearLabel.setVisible(true);
    		
    	}
    	

    }
		
    
    @Listen("onClick =#takeSnapshotButton")
    public void takeSnapshot() {
    	
    	Integer maxNumberOfSnapshots = this.getMaxNumberOfSnapshots(this.snapshotDAO.getCurrentHotel().getHotelId());  
    	
    	if(maxNumberOfSnapshots !=null && maxNumberOfSnapshots > 0 && snapshotListBox.getItemCount() >= maxNumberOfSnapshots) {
    		Messagebox.show("The maximum number of snapshots is " + maxNumberOfSnapshots + ", please delete some snapshots before saving another snapshot."); return;
    	}
    	
    	String snapshotName = this.snapshotName.getText();    	
    	if(snapshotName.equals("") || snapshotName.length() < 5) {
    		Messagebox.show("Please enter a name for the snapshot longer than 5 characters."); return;
    	}
    	
    	String monthString = this.snapshotMonth.getSelectedItem().getValue();	
    	int month = Integer.parseInt(monthString);
    	if(month == 0) {
    		Messagebox.show("Please select a valid month."); return;
    	}
    	
    	boolean isFutureOnly = this.snapshotFutureOnly.isChecked();
    	
    	if(isFutureOnly) {
    		
    		Calendar cal = Calendar.getInstance();
			int currentYear = cal.get(Calendar.YEAR);			
			this.snapshotYear.setValue(currentYear);
    	}
    	
    	else {
    		int year = this.snapshotYear.getValue() == null ? 0 : this.snapshotYear.getValue();  
    		if((year < 2000 || year > (Calendar.getInstance().get(Calendar.YEAR) + 5))) {
        		Messagebox.show("Please enter a valid year."); return;
        	}    		
    	}
    	
    	
    	
    	confirmBudgetOverwrite();    	    	
    	
    }
    
    @Listen("onClick =#restoreSnapshotButton")
    public void restoreSnapshot() {
    	
    	//If it is not the TakeSnapshot item and we only have 1 item selected.
    	if(snapshotListBox.getSelectedIndex() > 0 && this.snapshotListBox.getSelectedCount() == 1) {
    		
    		//Validate dateFrom and dateTo when not null/empty
    		if(snapshotRestoreFrom.getValue() != null && snapshotRestoreTo.getValue() != null) {
    			
    			if (snapshotRestoreTo.getValue().getTime() < snapshotRestoreFrom.getValue().getTime()) {
	    			Messagebox.show("Please enter a valid date range where the Restore From Date occurs prior to the Restore To Date.", "Invalid Date Range", Messagebox.OK, Messagebox.ERROR);
	    			return;
	    		}
    		}    		
    					
    		
    		String overwriteSnapshotMsg = "Are you sure you wish to restore this snapshot?  The current forecast will be overwritten with this snapshot.  This action can not be undone! It is recommended that you take a snapshot of the current forecast before performing a restore.";
    		
    		EventListener<ClickEvent> snapshotRestoreListener = new EventListener<Messagebox.ClickEvent>() {
	    	    public void onEvent(ClickEvent event) throws Exception {   
	    	    	//If onNo, then return main app execution.
	    	    	if (event.getName() ==  Messagebox.ON_NO)   	 
	    	    		return;
	    	    	
	    	    	else {
	    	    		
	    	    		Set<Snapshot> selectedSnapshot = ((ListModelList<Snapshot>)snapshotsModel).getSelection();
	    	    		Iterator<Snapshot> iterator = selectedSnapshot.iterator();
	    	        	Snapshot currentSnapshotSelected = null;        	
	    	        	currentSnapshotSelected = iterator.next();
	    	        	int snapshotToRestore = currentSnapshotSelected.getSnapshotId();   
	    	        	
	    	        	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");        	
	    	        	String dateFrom = snapshotRestoreFrom.getValue() == null ? null : dateFormat.format(snapshotRestoreFrom.getValue());
	    	        	String dateTo = snapshotRestoreTo.getValue() == null ? null : dateFormat.format(snapshotRestoreTo.getValue());
	    	        	
	    	        	boolean wasRestored = snapshotDAO.restoreSnapshot(snapshotToRestore, dateFrom, dateTo);
	    	        		
	    	        		if(wasRestored)
	    	        			Messagebox.show("The snapshot [" + currentSnapshotSelected.getName() +"] has been successfully restored!");          			
	    	        		else
	    	        			Messagebox.show("The snapshot [" + currentSnapshotSelected.getName() +"] was not restored!", "Snapshot Error", Messagebox.OK, Messagebox.ERROR);      
	    	        	
	    	        	boolean wasUpdated = snapshotDAO.updateHotelSnapshotId(snapshotToRestore);
	    	        		
	    	        		if(wasUpdated)
	    	        			Messagebox.show("The snapshot [" + currentSnapshotSelected.getName() +"] is now your current working snapshot!");          			
	    	        		else
	    	        			Messagebox.show("The snapshot [" + currentSnapshotSelected.getName() +"] was not properly updated as your current working snapshot!", "Snapshot Error", Messagebox.OK, Messagebox.ERROR);      
	    	        		
	    	        		
	    	        	
	    	        	//Reload all snapshots after delete action
	    	        	reloadSnapshotListbox();
	    	    		
	    	    	}
	    	    }
			};   
			
			Messagebox.show(overwriteSnapshotMsg, "Confirm Restore", new Messagebox.Button[]{
        	        Messagebox.Button.YES, Messagebox.Button.NO}, Messagebox.QUESTION, snapshotRestoreListener);
    		
    		
    	}
    	else
    		Messagebox.show("Please select only one snapshot to restore", "Snapshot Error", Messagebox.OK, Messagebox.ERROR);
    		
    }
    
    @Listen("onClick =#deleteSnapshotButton")
    public void deleteSnapshot() {
    	
    	//If it is not the TakeSnapshot item, then delete it
    	if(snapshotListBox.getSelectedIndex() > 0) {
    		
    		Set<Snapshot> selectedSnapshot = ((ListModelList<Snapshot>)snapshotsModel).getSelection();
    		Iterator<Snapshot> iterator = selectedSnapshot.iterator();
        	Snapshot currentSnapshotSelected = null;
        	
        	do{
        		currentSnapshotSelected = iterator.next();
        		int snapshotToDelete = currentSnapshotSelected.getSnapshotId();
        		
        		//If snapshot to delete is the current-working snapshot, avoid this action
        		if(snapshotToDelete == snapshotDAO.getCurrentHotel().getSnapshotId()) {        			
        			Messagebox.show("You cannot delete the snapshot you are currently working on.", "Snapshot Error", Messagebox.OK, Messagebox.ERROR);       
        			return;
        		}        		
        	
        	} while (iterator.hasNext());
        	
        	//After validating snapshots to delete, it's time to execute DELETE action for all of them        	
        	iterator = selectedSnapshot.iterator();
        	currentSnapshotSelected = null;
        	
        	do{
        		currentSnapshotSelected = iterator.next();
        		int snapshotToDelete = currentSnapshotSelected.getSnapshotId();
        		boolean wasDelete = this.snapshotDAO.deleteSnapshost(snapshotToDelete);
        		
        		if(wasDelete)
        			Messagebox.show("The snapshot [" + currentSnapshotSelected.getName() +"] has been successfully deleted!");          			
        		else
        			Messagebox.show("The snapshot [" + currentSnapshotSelected.getName() +"] was not deleted!", "Snapshot Error", Messagebox.OK, Messagebox.ERROR);
        		
        	}while (iterator.hasNext());
        	//Reload all snapshots after delete action
        	this.reloadSnapshotListbox();
    	}
    	else
    		Messagebox.show("Can't delete this empty Snapshot", "Snapshot Error", Messagebox.OK, Messagebox.ERROR);
    		
    }
        
    @Listen("onClick =#saveAsUFSnapshotButton")
    public void saveAsUFSnapshot() {
    	
    	if(snapshotListBox.getSelectedIndex() > 0 && this.snapshotListBox.getSelectedCount() == 1) {
    		
    		String saveaASUFSnapshotMsg = "Are you sure wish  to make this snapshot the Updated Forecast?.";
    		
    		EventListener<ClickEvent> snapshotUFListener = new EventListener<Messagebox.ClickEvent>() {
	    	    public void onEvent(ClickEvent event) throws Exception {   
	    	    	//If onNo, then return main app execution.
	    	    	if (event.getName() ==  Messagebox.ON_NO)   	 
	    	    		return;
	    	    	
	    	    	else {	    	    		
	    	    		
	    	    		Set<Snapshot> selectedSnapshot = ((ListModelList<Snapshot>)snapshotsModel).getSelection();
	    	    		Iterator<Snapshot> iterator = selectedSnapshot.iterator();
	    	        	Snapshot currentSnapshotSelected = null;        	
	    	        	currentSnapshotSelected = iterator.next();
	    	        	int snapshotId = currentSnapshotSelected.getSnapshotId();   
	    	        	
	    	    		snapshotDAO.saveSnapshotUF(snapshotId);    		
	    	    		Messagebox.show("Snapshot successfully saved as UF.");
	    	    		reloadSnapshotListbox();
	    	    	}
	    	    }
			};   
			
			Messagebox.show(saveaASUFSnapshotMsg, "Confirm Save as UF", new Messagebox.Button[]{
        	        Messagebox.Button.YES, Messagebox.Button.NO}, Messagebox.QUESTION, snapshotUFListener);
    		
    	}
    		
    	else	
    		Messagebox.show("Please select one Snapshot.", "Snapshot Error", Messagebox.OK, Messagebox.ERROR);
    		
    }
    
    @Listen("onClick =#editNameSnapshotButton")
    public void editNameSnapshot() {
    	
    	if(snapshotListBox.getSelectedIndex() > 0 && this.snapshotListBox.getSelectedCount() == 1) {
    		
    		Set<Snapshot> selectedSnapshot = ((ListModelList<Snapshot>)snapshotsModel).getSelection();
    		Iterator<Snapshot> iterator = selectedSnapshot.iterator();
        	Snapshot currentSnapshotSelected = null;        	
        	currentSnapshotSelected = iterator.next();
        	int snapshotId = currentSnapshotSelected.getSnapshotId();   
        	
    		this.snapshotDAO.updateSnapshotName(snapshotId, this.snapshotName.getText());  		
    		Messagebox.show("Snapshot name was successfully edited.");
    		this.reloadSnapshotListbox();
    	}
    		
    	else	
    		Messagebox.show("Please select one Snapshot.", "Snapshot Error", Messagebox.OK, Messagebox.ERROR);
    		
    }
    
	private void confirmBudgetOverwrite() {
		
		int budget = this.snapshotBU.isChecked() ? 1: 0;  
		
		if(budget == 1) {
			
			if(this.snapshotDAO.hasBudget(this.snapshotYear.getValue())) {
    			
    			EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
		    	    public void onEvent(ClickEvent event) throws Exception {   
		    	    	//If onNo, then return main app execution.
		    	    	if (event.getName() ==  Messagebox.ON_NO)   	 
		    	    		return;
		    	    	else confirmForecastOverwrite();		    	    		
		    	    }
    			};   
    			
    			Messagebox.show("A Budget for this hotel during this year already exists.  Do you wish to overwrite the old budget?", "Confirm Overwrite", new Messagebox.Button[]{
            	        Messagebox.Button.YES, Messagebox.Button.NO}, Messagebox.QUESTION, clickListener);
    		}
				else confirmForecastOverwrite();
					
			
		} else confirmForecastOverwrite(); 
	}
	
	private void confirmForecastOverwrite() {
		
		int forecast = this.snapshotUF.isChecked() ? 1: 0;  
		
		if(forecast == 1) {
									
			if(this.snapshotDAO.hasForecast()) {
    			
    			EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
		    	    public void onEvent(ClickEvent event) throws Exception {   
		    	    	//If onNo, then return main app execution.
		    	    	if (event.getName() ==  Messagebox.ON_NO)   	 
		    	    		return;
		    	    	else confirmCurrentForecastOverwrite();		    	    		
		    	    }
    			};   
    			
    			Messagebox.show("A Update Forecast for this hotel already exists. Do you wish to overwrite?", "Confirm Overwrite", new Messagebox.Button[]{
            	        Messagebox.Button.YES, Messagebox.Button.NO}, Messagebox.QUESTION, clickListener);
    		}
				else confirmCurrentForecastOverwrite();
			
		} else confirmCurrentForecastOverwrite();	
	}
	
	private void confirmCurrentForecastOverwrite() {
		
		int current = this.snapshotCF.isChecked() ? 1: 0; 
		
		if(current == 1) {
			
			String monthString = this.snapshotMonth.getSelectedItem().getValue();	
	    	int month = Integer.parseInt(monthString); 
			
			if(this.snapshotDAO.hasCurrForecast(this.snapshotYear.getValue(),month)) {
    			
    			EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
		    	    public void onEvent(ClickEvent event) throws Exception {   
		    	    	//If onNo, then return main app execution.
		    	    	if (event.getName() ==  Messagebox.ON_NO)   	 
		    	    		return;
		    	    	else confirmQuarterForecastOverwrite();		    	    		
		    	    }
    			};   
    			
    			Messagebox.show("This month/year already exists.  Do you wish to overwrite?", "Confirm Overwrite", new Messagebox.Button[]{
            	        Messagebox.Button.YES, Messagebox.Button.NO}, Messagebox.QUESTION, clickListener);
    		}
			
			else confirmQuarterForecastOverwrite();
			
		} else confirmQuarterForecastOverwrite();
	}	
	
	private void confirmQuarterForecastOverwrite() {
		
		int quarterly = this.snapshotQF.isChecked() ? 1: 0; 
		
		if(quarterly == 1) {
			
			String monthString = this.snapshotMonth.getSelectedItem().getValue();	
	    	int month = Integer.parseInt(monthString); 
			
			if(this.snapshotDAO.hasQuarterForecast(this.snapshotYear.getValue(), month)) {
    			
    			EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
		    	    public void onEvent(ClickEvent event) throws Exception {   
		    	    	//If onNo, then return main app execution.
		    	    	if (event.getName() ==  Messagebox.ON_NO)   	 
		    	    		return;
		    	    	else executeTakeSnapshot();		    	    		
		    	    }
    			};   
    			
    			Messagebox.show("This quarter/year already exists.  Do you wish to overwrite?", "Confirm Overwrite", new Messagebox.Button[]{
            	        Messagebox.Button.YES, Messagebox.Button.NO}, Messagebox.QUESTION, clickListener);
    		}
			
			else executeTakeSnapshot();
		} else executeTakeSnapshot();
	}
	
	private void executeTakeSnapshot() {
		
		String dateFrom = null;
		String dateTo = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		int budget = this.snapshotBU.isChecked() ? 1: 0; 
		int current = this.snapshotCF.isChecked() ? 1: 0; 
		int forecast = this.snapshotUF.isChecked() ? 1: 0; 
		int quarterly = this.snapshotQF.isChecked() ? 1: 0; 
		
		String snapshotName = this.snapshotName.getText(); 
		String monthString = this.snapshotMonth.getSelectedItem().getValue();	
    	int month = Integer.parseInt(monthString);    	
    	int year = this.snapshotYear.getValue(); 
    	 
			   // '10/18/05: Save 7 years, 2 previous, current, 4 years future
			   // sDateFrom = "1/1/" & txtYear.Text - 2
			   // sDateTo = "12/31/" & txtYear.Text + 4
		
		boolean isFutureOnly = this.snapshotFutureOnly.isChecked();
		
		if(isFutureOnly) {
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			cal1.add(Calendar.DAY_OF_MONTH, -1);//reduce -1 days;
			dateFrom = dateFormat.format(cal1.getTime());				
			cal2.add(Calendar.DATE, 89);//add 89 days by default;
			dateTo = dateFormat.format(cal2.getTime()); 
						
		}
		
		else {
			
			try {
				dateFrom = dateFormat.format(dateFormat.parse((year-2) + "-01-01"));
				dateTo   = dateFormat.format(dateFormat.parse((year+4) + "-12-31"));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
			
		
		//Format for dateFrom & dateTo '2014-02-01'
		System.out.println("dateFrom: " + dateFrom + " dateTo: " + dateTo);
        boolean isSuccess = this.snapshotDAO.setSnapshot(snapshotName, year, month, budget, forecast, current, quarterly, dateFrom, dateTo);
        if(isSuccess){
        	Messagebox.show("The snapshot has been taken successfully!");
        	//Refresh listbox
        	reloadSnapshotListbox();
        }
        	
        else 
        	Messagebox.show("The snapshot has failed!", "Snapshot Error", Messagebox.OK, Messagebox.ERROR);
	}

	private int getMaxNumberOfSnapshots(int currentHotelId) {
		
		return this.snapshotDAO.getSnapshotNumberLimit();
	}

	public ListModel<Snapshot> getSnapshotsModel() {
        return snapshotsModel;
    }
	
	public void seleccionarColoniaRender(){
		
	}
		
}
