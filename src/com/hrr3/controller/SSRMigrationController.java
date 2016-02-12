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

import com.hrr3.entity.Hotel;
import com.hrr3.entity.SSRSnapshot;
import com.hrr3.model.HotelDAO;
import com.hrr3.model.RM2MigrationDAO;
import com.hrr3.services.AuthenticationService;

public class SSRMigrationController extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;
	
	private ListModel<SSRSnapshot> snapshotsModel;
	private RM2MigrationDAO migrationDAO;
	private Hotel currentHotel; 
	private Integer customerId;
	private String hotelName;
	
	@Wire
	Listbox snapshotToMigrateListBox;
	
	@Wire
    Window modalSnapshotListDialog;
	
	private AuthenticationService authService;
	
	public SSRMigrationController() {
		
		final Execution execution = Executions.getCurrent();
        
        migrationDAO = new RM2MigrationDAO();   
        HotelDAO hotelDAO = new HotelDAO();
        
		System.out.println("******************* HotelId in popup " + execution.getArg().get("hotelId") + " ************************");
		System.out.println("******************* CustomerId in popup " + execution.getArg().get("customerId") + " ************************");
		
		Integer hotelId = (Integer)execution.getArg().get("hotelId");
		hotelName = (String)execution.getArg().get("hotelName");
		customerId = (Integer)execution.getArg().get("customerId");		
		
		this.currentHotel = hotelDAO.getHotelDetails(hotelId);
		
		migrationDAO.setCurrentHotel(this.currentHotel);
             
        List<SSRSnapshot> snapshotList = migrationDAO.getSSRSnapshotsByHotel(hotelId);
        snapshotsModel = new ListModelList<SSRSnapshot>(snapshotList);
        ((ListModelList<SSRSnapshot>)snapshotsModel).setMultiple(true);
	}    
	
	
	public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        
        modalSnapshotListDialog.setTitle(hotelName + " - SSR Snapshot List");
	}
	
	private void reloadSnapshotListbox() {
		
	}

     
    @Listen("onClick = #closeBtn")
    public void showModal(Event e) {
    	modalSnapshotListDialog.detach();
    }
    
    @Listen("onClick = #migrateSnapthot")
    public void executeMigration() {
    	
    	//If user at least selected 1 snapshot
    	if(this.snapshotToMigrateListBox.getSelectedCount() >= 1){
    		
    		Set<SSRSnapshot> selectedSnapshots = ((ListModelList<SSRSnapshot>)snapshotsModel).getSelection();
    		Iterator<SSRSnapshot> iterator = selectedSnapshots.iterator();
    		
    		HashMap<Integer, String> ssrSegmentsMapped = new HashMap<Integer, String>();   
    		HashMap<Integer, String> transientSegmentsMapped = new HashMap<Integer, String>();       		
    		
    		ssrSegmentsMapped  = this.migrationDAO.mapSSRSegmentsToMigrate(this.currentHotel.getHotelId());
    		transientSegmentsMapped  = this.migrationDAO.mapSegmentsToMigrate(this.currentHotel.getHotelId(),this.customerId );
    		
    		if (ssrSegmentsMapped.isEmpty() || transientSegmentsMapped.isEmpty()){    			
    			Messagebox.show("No segments to match");
    		}
    		else{
    			
    			if(transientSegmentsMapped.size() == 1){
    				Entry<Integer, String> e = transientSegmentsMapped.entrySet().iterator().next();
    				//Code 0 : Some segments can't be mapped
    				if (e.getKey() == 0){
    					Messagebox.show("Could not match the following segments name [ "+ e.getValue() +"]", "Invalid Mapping", Messagebox.OK, Messagebox.ERROR);
    				}
    				else{
    					Messagebox.show("Could not match all segments in the migration.", "Invalid Mapping", Messagebox.OK, Messagebox.ERROR);
    				}
    			}
    			else if(ssrSegmentsMapped.size() == 1){
    				Entry<Integer, String> e = ssrSegmentsMapped.entrySet().iterator().next();
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
		    			SSRSnapshot snapshotToMigrate = iterator.next();
		    			
		    			if(snapshotToMigrate.isMigrated())
		    				Messagebox.show("SSR Snapshot["+ snapshotToMigrate.getNameToDisplay() +"] was previously migrated. It will be skipped.", "SSR Migration warning", Messagebox.OK, Messagebox.EXCLAMATION);
		    			else {
		    				
		    				int migrationStatus = this.migrationDAO.migrateSSRSnapshotRm2ToRm3(snapshotToMigrate.getSnapshotId(), transientSegmentsMapped, ssrSegmentsMapped);
			    			//boolean wasMigrated = true;
			    			
			    			if(migrationStatus == RM2MigrationDAO.MIGRATION_OK)
			    				Messagebox.show("SSR Snapshot["+ snapshotToMigrate.getNameToDisplay() +"] was sucessfully migrated from RM2 to RM3.");
			    			else if(migrationStatus == RM2MigrationDAO.MIGRATION_WARNING)
			    					Messagebox.show("SSR Snapshot["+ snapshotToMigrate.getNameToDisplay() +"] was completed with some issues while migrating non-existing segments which were skipped.", "Migration warning", Messagebox.OK, Messagebox.EXCLAMATION);
			    				else	
			    					Messagebox.show("SSR Snapshot["+ snapshotToMigrate.getNameToDisplay() +"] migration failed from RM2 to RM3. Check log for details.", "SSR Migration Error", Messagebox.OK, Messagebox.ERROR);
		    				
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

	public ListModel<SSRSnapshot> getSnapshotsModel() {
        return snapshotsModel;
    }
		
}
