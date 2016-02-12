package com.hrr3.controller;

import java.text.SimpleDateFormat;
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
import com.hrr3.entity.SSRSnapshot;
import com.hrr3.entity.User;
import com.hrr3.model.HotelDAO;
import com.hrr3.model.SSRSnapshotDAO;
import com.hrr3.services.AuthenticationService;

public class SSRSnapshotController extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;
	
	private ListModel<SSRSnapshot> snapshotsModel;
	
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
	Textbox snapshotDateCreated;
	
	@Wire
	Combobox snapshotMonth;
	
	@Wire
	Intbox snapshotYear;
	
	@Wire
	Label snapshotYearLabel;
	
	@Wire
	Intbox snapshotWeekNumber;
	
	@Wire
	Datebox snapshotCurrentMeetingDate;
	
	@Wire
	Datebox snapshotLastDateActualsUsed;
	
	
	@Wire
	Button takeSnapshotButton; 
	
	@Wire
	Button restoreSnapshotButton;
	
	@Wire
	Button deleteSnapshotButton;

	SSRSnapshotDAO snapshotDAO;
	
	
	public SSRSnapshotController() {
		
		authService = new AuthenticationServiceHRR3Impl();
		
		if(authService.getUserData().getCurrentHotel() == null || authService.getUserData().getCurrentHotel().getHotelId() ==null || authService.getUserData().getCurrentHotel().getHotelId() < 1)
			{Messagebox.show("Please select a valid Hotel and return to this section."); return;}
		
		
		Hotel currenthotel = authService.getUserData().getCurrentHotel();		
        snapshotDAO = new SSRSnapshotDAO(currenthotel);        
        List<SSRSnapshot> snapshotList = snapshotDAO.getAllSSRSnapshot("ADD_SNAPSHOT");                
        snapshotsModel = new ListModelList<SSRSnapshot>(snapshotList);
        ((ListModelList<SSRSnapshot>)snapshotsModel).setMultiple(true);
        
     
		
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
        List<SSRSnapshot> snapshotList = snapshotDAO.getAllSSRSnapshot("ADD_SNAPSHOT");                
        snapshotsModel = new ListModelList<SSRSnapshot>(snapshotList);
        this.snapshotListBox.setModel(snapshotsModel);
        ((ListModelList<SSRSnapshot>)snapshotsModel).setMultiple(true);
        //Initialize form controls to take a new snapshot.
        this.initializeFormControls(SNAPSHOT_TAKE);
	}
	
    @Listen("onSelect =#snapshotListBox")
    public void populateSnapshotForm() {
    	
        Set<SSRSnapshot> selectedSnapshot = ((ListModelList<SSRSnapshot>)snapshotsModel).getSelection();
        int size = selectedSnapshot.size();
        
        //User selected a valid snapshot in the list
        if(size > 0 && snapshotListBox.getSelectedIndex() > 0) {
        	
        	Iterator<SSRSnapshot> iterator = selectedSnapshot.iterator();
        	SSRSnapshot lastSnapshotSelected = null;
        	
        	do{
        		lastSnapshotSelected = iterator.next();
        	
        	} while (iterator.hasNext());
        	
        	snapshotName.setValue(lastSnapshotSelected.getSnapshotName());       
        	snapshotDateCreated.setText(lastSnapshotSelected.getDateCreated());
        	snapshotMonth.setSelectedIndex(lastSnapshotSelected.getMonth());
        	snapshotYear.setValue(lastSnapshotSelected.getYear());
        	snapshotWeekNumber.setText(lastSnapshotSelected.getWeekNumber());
        	snapshotCurrentMeetingDate.setText(lastSnapshotSelected.getCurrentMeetingDate());
        	snapshotLastDateActualsUsed.setText(lastSnapshotSelected.getLastDate());
        	//TODO Agregar demas atributos
        	
        	
        	initializeFormControls(SSRSnapshotController.SNAPSHOT_SHOW);
        }
        	
        
        //If selected item is the Add Snapshot dummy option, meaning the form will be used to TAKE SNAPSHOT
        else if(size > 0 && snapshotListBox.getSelectedIndex() == 0) {
        	
        	initializeFormControls(SSRSnapshotController.SNAPSHOT_TAKE);	
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
    	
    	if(type == SSRSnapshotController.SNAPSHOT_TAKE) {
    		
        	snapshotName.setValue("");        	
    		snapshotMonth.setSelectedIndex(0);
    		snapshotYear.setValue(null);
    		snapshotWeekNumber.setValue(null);
    		snapshotDateCreated.setValue("");
    		snapshotCurrentMeetingDate.setValue(null);
    		snapshotLastDateActualsUsed.setValue(null);
    		
    		takeSnapshotButton.setDisabled(false);
    		deleteSnapshotButton.setDisabled(true);
    		restoreSnapshotButton.setDisabled(true);   		
    		
    		snapshotYear.setVisible(true);    		
        	snapshotYearLabel.setVisible(true);
    	}
    	
    	else if(type == SSRSnapshotController.SNAPSHOT_SHOW) {
    		    	
        		
        	takeSnapshotButton.setDisabled(true);
        	deleteSnapshotButton.setDisabled(false);
        	restoreSnapshotButton.setDisabled(false);        	
        	snapshotYear.setVisible(true);
        	snapshotYearLabel.setVisible(true);
    		
    	}
    	

    }
		
    
    @Listen("onClick =#takeSnapshotButton")
    public void takeSnapshot() {
    	
    	int maxNumberOfSnapshots = this.getMaxNumberOfSnapshots(this.snapshotDAO.getCurrentHotel().getHotelId());    	
    	if(snapshotListBox.getItemCount() > maxNumberOfSnapshots) {
    		Messagebox.show("The maximum number of SSR snapshots is " + maxNumberOfSnapshots + ", please delete some snapshots before saving another snapshot."); return;
    	}
    	
    	String snapshotName = this.snapshotName.getText();    	
    	if(snapshotName.equals("") || snapshotName.length() < 5) {
    		Messagebox.show("Please enter a name for the SSR snapshot longer than 5 characters."); return;
    	}
    	
    	String monthString = this.snapshotMonth.getSelectedItem().getValue();	
    	int month = Integer.parseInt(monthString);
    	if(month == 0) {
    		Messagebox.show("Please select a valid month."); return;
    	}
    	
    	int year = this.snapshotYear.getValue() == null ? 0 : this.snapshotYear.getValue();  
		if((year == 0)) {
    		Messagebox.show("Please enter a valid year."); return;
    	}    
		
		if(this.snapshotWeekNumber.getValue() == null || this.snapshotWeekNumber.getValue() < 0) {
			Messagebox.show("Please enter a valid week number."); return;
		}
		
		if(this.snapshotCurrentMeetingDate.getValue() == null) {
			Messagebox.show("Please enter a valid current meeting date."); return;
		}
		
		if(this.snapshotLastDateActualsUsed.getValue() == null) {
			Messagebox.show("Please enter a valid last date of actual used date."); return;
		}
			
    	
    	this.confirmTakeSnapshot();    	    	
    	
    }
    
    @Listen("onClick =#restoreSnapshotButton")
    public void restoreSnapshot() {
    	
    	//If it is not the TakeSnapshot item and we only have 1 item selected.
    	if(snapshotListBox.getSelectedIndex() > 0 && this.snapshotListBox.getSelectedCount() == 1) {
    		
    			
    		
    		String overwriteSnapshotMsg = "Are you sure you wish to restore this SSR snapshot?  The current forecast will be overwritten with this snapshot.  This action can not be undone! It is recommended that you take a snapshot of the current forecast before performing a restore.";
    		
    		EventListener<ClickEvent> snapshotRestoreListener = new EventListener<Messagebox.ClickEvent>() {
	    	    public void onEvent(ClickEvent event) throws Exception {   
	    	    	//If onNo, then return main app execution.
	    	    	if (event.getName() ==  Messagebox.ON_NO)   	 
	    	    		return;
	    	    	
	    	    	else {
	    	    		
	    	    		Set<SSRSnapshot> selectedSnapshot = ((ListModelList<SSRSnapshot>)snapshotsModel).getSelection();
	    	    		Iterator<SSRSnapshot> iterator = selectedSnapshot.iterator();
	    	    		SSRSnapshot currentSnapshotSelected = null;        	
	    	        	currentSnapshotSelected = iterator.next();
	    	        	int snapshotToRestore = currentSnapshotSelected.getSnapshotId();   
	    	        	
	    	        	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");        	
	    	        	
	    	        	//TODO Add range
	    	        	boolean wasRestored = snapshotDAO.restoreSSRSnapshot(snapshotToRestore);
	    	        		
	    	        		if(wasRestored)
	    	        			Messagebox.show("The SSR snapshot [" + currentSnapshotSelected.getSnapshotName() +"] has been successfully restored!");          			
	    	        		else
	    	        			Messagebox.show("The SSR snapshot [" + currentSnapshotSelected.getSnapshotName() +"] was not restored!", "SSR Snapshot Error", Messagebox.OK, Messagebox.ERROR);      
	    	        	
	    	        	//boolean wasUpdated = snapshotDAO.setupSnapshotInformation(ssrSnapshotId);
	    	        		
	    	        		//if(wasUpdated)
	    	        			//Messagebox.show("The snapshot [" + currentSnapshotSelected.getSnapshotName() +"] is now your current working snapshot!");          			
	    	        		//else
	    	        			//Messagebox.show("The snapshot [" + currentSnapshotSelected.getSnapshotName() +"] was not properly updated as your current working snapshot!", "Snapshot Error", Messagebox.OK, Messagebox.ERROR);      
	    	        		
	    	        		
	    	        	
	    	        	//Reload all snapshots after delete action
	    	        	reloadSnapshotListbox();
	    	    		
	    	    	}
	    	    }
			};   
			
			Messagebox.show(overwriteSnapshotMsg, "Confirm Restore", new Messagebox.Button[]{
        	        Messagebox.Button.YES, Messagebox.Button.NO}, Messagebox.QUESTION, snapshotRestoreListener);
    		
    		
    	}
    	else
    		Messagebox.show("Please select only one SSR snapshot to restore", "SSR Snapshot Error", Messagebox.OK, Messagebox.ERROR);
    		
    }
    
    @Listen("onClick =#deleteSnapshotButton")
    public void deleteSnapshot() {
    	
    	//If it is not the TakeSnapshot item, then delete it
    	if(snapshotListBox.getSelectedIndex() > 0) {
    		
    		Set<SSRSnapshot> selectedSnapshot = ((ListModelList<SSRSnapshot>)snapshotsModel).getSelection();
    		Iterator<SSRSnapshot> iterator = selectedSnapshot.iterator();
    		SSRSnapshot currentSnapshotSelected = null;
        	
        	do{
        		currentSnapshotSelected = iterator.next();
        		int snapshotToDelete = currentSnapshotSelected.getSnapshotId();
        		
        		//If SSRsnapshot to delete is the current-working snapshot, avoid this action
        		if(snapshotToDelete == snapshotDAO.getCurrentHotel().getSsrSnapshotId()) {        			
        			Messagebox.show("You cannot delete the snapshot you are currently working on.", "SSR Snapshot Error", Messagebox.OK, Messagebox.ERROR);       
        			return;
        		}        		
        	
        	} while (iterator.hasNext());
        	
        	//After validating snapshots to delete, it's time to execute DELETE action for all of them        	
        	iterator = selectedSnapshot.iterator();
        	currentSnapshotSelected = null;
        	
        	do{
        		currentSnapshotSelected = iterator.next();
        		int snapshotToDelete = currentSnapshotSelected.getSnapshotId();
        		//TODO delete here
        		int wasDelete = snapshotDAO.deleteSSRSnapshot(snapshotToDelete);
        		
        		if(wasDelete == 0)
        			Messagebox.show("The SSR snapshot [" + currentSnapshotSelected.getSnapshotName() +"] has been successfully deleted!");          			
        		else
        			Messagebox.show("The SSR snapshot [" + currentSnapshotSelected.getSnapshotName() +"] was not deleted!", "SSR Snapshot Error", Messagebox.OK, Messagebox.ERROR);
        		
        	}while (iterator.hasNext());
        	//Reload all snapshots after delete action
        	this.reloadSnapshotListbox();
    	}
    	else
    		Messagebox.show("Can't delete this empty SSR Snapshot", "SSR Snapshot Error", Messagebox.OK, Messagebox.ERROR);
    		
    }
    
	
	
	private void confirmTakeSnapshot() {
		
		
    			
    			EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
		    	    public void onEvent(ClickEvent event) throws Exception {   
		    	    	//If onNo, then return main app execution.
		    	    	if (event.getName() ==  Messagebox.ON_NO)   	 
		    	    		return;
		    	    	else executeTakeSnapshot();		    	    		
		    	    }
    			};   
    			
    			Messagebox.show("Are you sure you want to take a new SSR Snapshot?", "Confirm SSR Snapshot", new Messagebox.Button[]{
            	        Messagebox.Button.YES, Messagebox.Button.NO}, Messagebox.QUESTION, clickListener);
    		
			
	}
	
	private void executeTakeSnapshot() {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		
		
		String snapshotName = this.snapshotName.getText(); 
		String monthString = this.snapshotMonth.getSelectedItem().getValue();	
    	int month = Integer.parseInt(monthString);    	
    	String weekNumber = this.snapshotWeekNumber.getText();
    	int year = this.snapshotYear.getValue(); 
    	String snapshotCurrentMeetingDate = dateFormat.format(this.snapshotCurrentMeetingDate.getValue());
    	String snapshotLastDateActualsUsed = dateFormat.format(this.snapshotLastDateActualsUsed.getValue());
			  
    	SSRSnapshot ssSnapshot = new SSRSnapshot();
    	ssSnapshot.setSnapshotName(snapshotName);
    	ssSnapshot.setMonth(month);
    	ssSnapshot.setYear(year);
    	ssSnapshot.setWeekNumber(weekNumber);
    	ssSnapshot.setCurrentMeetingDate(snapshotCurrentMeetingDate);
    	ssSnapshot.setLastDate(snapshotLastDateActualsUsed);
    	
		
        int newSnapshotId = this.snapshotDAO.takeSSRSnapshot(ssSnapshot);
        if(newSnapshotId > 0){
        	Messagebox.show("The SSR snapshot has been taken successfully!");
        	//Refresh listbox
        	reloadSnapshotListbox();
        }
        	
        else 
        	Messagebox.show("The SSR snapshot has failed!", "Snapshot SSR Error", Messagebox.OK, Messagebox.ERROR);
	}

	private int getMaxNumberOfSnapshots(int currentHotelId) {
		// TODO Code to get MaxNumber of snapshots based on hotelId. All those NYC Hotels will have 125 of limit, all others 100
		//Let's return 100 by default in the mean time.
		return 100;
	}

	public ListModel<SSRSnapshot> getSnapshotsModel() {
        return snapshotsModel;
    }
		
}
