package com.hrr3.modelview;
 
import java.io.IOException;
import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Messagebox;

import com.hrr3.entity.ssrMigration.SSRSnapshotStarData;
import com.hrr3.model.ImportSSRStarDataDAO;
import com.hrr3.services.StarFileImportService;
 
public class StarImportFileMV extends GenericImportFileMV {
	
 
   @AfterCompose
   public void initSetup(@ContextParam(ContextType.VIEW) Component view)
             {
     Selectors.wireComponents(view, this, false);
     win.setTitle("Import - Monthly STAR");
      
   }
      
    public void executeImportFromFile() throws IOException {
    	
    	if (!this.validateHotelSelected()) return;
	   
	   StarFileImportService fileImportService = new StarFileImportService();
	   ImportSSRStarDataDAO importSSRStarDataDAO = null;
	   int starSessionId = 0;
	   
	   try {
		   	List<SSRSnapshotStarData> starData = fileImportService.parseFileToObjects(filePath, this.currentHotel.getHotelId());
			   importSSRStarDataDAO = new ImportSSRStarDataDAO(this.currentHotel);			   
			   
			   //Save the objects
			   for(int i=0; i<starData.size(); i++) {
				   SSRSnapshotStarData currentStarData = starData.get(i);
				  
				   starSessionId = importSSRStarDataDAO.saveStarDataTemp(currentStarData, starSessionId);
				   
				   if(starSessionId == 0) {
					   Messagebox.show("There was an error saving Star Data in temporal tables.", "Star Import", Messagebox.OK, Messagebox.ERROR);
					   return;
				   }
			   }
			   
			   
			   
			   //Move data
			   int result = importSSRStarDataDAO.moveStarData(starSessionId);
			   
			   if(result == 0) {
				   Messagebox.show("There was an error saving Star Data in final tables.", "Star Import", Messagebox.OK, Messagebox.ERROR);
				   importSSRStarDataDAO.deleteStarTemp(starSessionId);
				   return;
			   }
			   
			   Messagebox.show("Import complete.", "Star Import", Messagebox.OK, Messagebox.INFORMATION);
			 this.closePopup(null);	   
			   
	} catch (Exception e) {
		// TODO Auto-generated catch block
		//If something goes wrong after getting the importSession, delete Temp data
		if(starSessionId != 0 && importSSRStarDataDAO != null)
			importSSRStarDataDAO.deleteStarTemp(starSessionId);	
		
		e.printStackTrace();
		Messagebox.show("Error:." + e.getMessage(), "Star Import", Messagebox.OK, Messagebox.ERROR);
		}
	   
	   finally { if(importSSRStarDataDAO != null) importSSRStarDataDAO.closeCurrentConnection(); }
	   
	 
   } 
    
    
   
   
}