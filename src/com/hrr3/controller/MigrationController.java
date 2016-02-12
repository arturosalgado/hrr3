package com.hrr3.controller;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.hrr3.entity.Snapshot;
import com.hrr3.model.RM2MigrationDAO;

public class MigrationController extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;
	
	private ListModel<Snapshot> snapshotsModel;
	private RM2MigrationDAO migrationDAO;
	private Integer hotelId;
	private Integer customerId;
	private String hotelName;
	
	@Wire
	Listbox snapshotToMigrateListBox;
	
	@Wire
    Window modalSnapshotListDialog;
	
	public MigrationController() {
		
		final Execution execution = Executions.getCurrent();
        
        migrationDAO = new RM2MigrationDAO();    
        
		System.out.println("******************* HotelId in popup " + execution.getArg().get("hotelId") + " ************************");
		System.out.println("******************* CustomerId in popup " + execution.getArg().get("customerId") + " ************************");
		
		hotelId = (Integer)execution.getArg().get("hotelId");
		hotelName = (String)execution.getArg().get("hotelName");
		customerId = (Integer)execution.getArg().get("customerId");
		
        List<Snapshot> snapshotList = migrationDAO.getSnapshotsByHotel(hotelId);              
        snapshotsModel = new ListModelList<Snapshot>(snapshotList);
        ((ListModelList<Snapshot>)snapshotsModel).setMultiple(true);
	}    
	
	
	public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        
        modalSnapshotListDialog.setTitle(hotelName + " - Snapshot List");
	}
	
	private void reloadSnapshotListbox() {
		
	}

     
    @Listen("onClick = #closeBtn")
    public void showModal(Event e) {
    	modalSnapshotListDialog.detach();
    }
    
    @Listen("onClick = #migrateSnapthot")
    public void executeMigration() {
    	
    	//If user at least selected 1 snapshot and a max of 3 snapshots.
    	if(this.snapshotToMigrateListBox.getSelectedCount() >= 1){
    		
    		Set<Snapshot> selectedSnapshots = ((ListModelList<Snapshot>)snapshotsModel).getSelection();
    		Iterator<Snapshot> iterator = selectedSnapshots.iterator();
    		
    		HashMap<Integer, String> segmentsMapped = new HashMap<Integer, String>();
    		
    		segmentsMapped  = this.migrationDAO.mapSegmentsToMigrate(this.hotelId, this.customerId);
    		
    		if (segmentsMapped.isEmpty()){    			
    			Messagebox.show("No segments to match");
    		}
    		else{
    			
    			if(segmentsMapped.size() == 1){
    				Entry<Integer, String> e = segmentsMapped.entrySet().iterator().next();
    				//Code 0 : Some segments can't be mapped
    				if (e.getKey() == 0){
    					Messagebox.show("Could not match the following segments name [ "+ e.getValue() +"]", "Invalid Mapping", Messagebox.OK, Messagebox.ERROR);
    				}
    				else{
    					Messagebox.show("Could not match all segments in the migration.", "Invalid Mapping", Messagebox.OK, Messagebox.ERROR);
    				}
    			}
    			else{    				    		
		    		while(iterator.hasNext()) {
		    			Snapshot snapshotToMigrate = iterator.next();
		    			
		    			if(snapshotToMigrate.isMigrated())
		    				Messagebox.show("Snapshot["+ snapshotToMigrate.getNameToDisplay() +"] was previously migrated. It will be skipped.", "Migration warning", Messagebox.OK, Messagebox.EXCLAMATION);
		    			else {
		    				
		    				int migrationStatus = this.migrationDAO.migrateSnapshotRm2ToRm3(snapshotToMigrate.getSnapshotId(),segmentsMapped);
			    			
			    			//boolean wasMigrated = true;
			    			
			    			if(migrationStatus == RM2MigrationDAO.MIGRATION_OK)
			    				Messagebox.show("Snapshot["+ snapshotToMigrate.getNameToDisplay() +"] was sucessfully migrated from RM2 to RM3.");
			    			else if(migrationStatus == RM2MigrationDAO.MIGRATION_WARNING)
			    					Messagebox.show("Snapshot["+ snapshotToMigrate.getNameToDisplay() +"] was completed with some issues while migrating non-existing segments which were skipped.", "Migration warning", Messagebox.OK, Messagebox.EXCLAMATION);
			    				else	
			    					Messagebox.show("Snapshot["+ snapshotToMigrate.getNameToDisplay() +"] migration failed from RM2 to RM3. Check log for details.", "Migration Error", Messagebox.OK, Messagebox.ERROR);
		    				
		    			}		    						   
		    		}
    			}
    		}
    	}	
    		
    	else {
    		
    		Messagebox.show("Please select at least 1 snapshot to migrate.", "Migration Valitation", Messagebox.OK, Messagebox.ERROR);
    	}
    	
    	//Close it after migration.
    	modalSnapshotListDialog.detach();
    	
    	
    }
    
    
	
    
    public void initializeFormControls(int type) {

    }

	public ListModel<Snapshot> getSnapshotsModel() {
        return snapshotsModel;
    }
		
}
