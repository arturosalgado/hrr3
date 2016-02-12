package com.hrr3.modelview;
 
import java.io.IOException;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Messagebox;

import com.hrr3.entity.ssr.ImportDayStarData;
import com.hrr3.entity.ssrMigration.SSRSnapshotDayStarData;
import com.hrr3.entity.ssrMigration.SSRSnapshotDayStarHotel;
import com.hrr3.model.ImportSSRDayStarDAO;
import com.hrr3.services.DayStarFileImportService;
 
public class DayStarImportFileMV extends GenericImportFileMV {
	
 
   @AfterCompose
   public void initSetup(@ContextParam(ContextType.VIEW) Component view)
             {
     Selectors.wireComponents(view, this, false);
     win.setTitle("Import - Weekly STAR");
      
   }
      
    public void executeImportFromFile() throws IOException {
    	
    	if (!this.validateHotelSelected()) return;
	   
	   DayStarFileImportService fileImportService = new DayStarFileImportService();
	   ImportSSRDayStarDAO importSSRDayStarDAO = null;
	   int dayStarId = 0;
	   
	   try {
			   ImportDayStarData dayStarData = fileImportService.parseFileToObjects(filePath, this.currentHotel.getHotelId());
			   importSSRDayStarDAO = new ImportSSRDayStarDAO(this.currentHotel);
			   
			
			   //Save glance and get dayStarId.
			   dayStarId =  importSSRDayStarDAO.saveDayStarTemp(dayStarData.getGlance());		
			  
				   
			   if(dayStarId == 0) {
				   Messagebox.show("There was an error saving Glance Data in temporal tables.", "DayStar Import", Messagebox.OK, Messagebox.ERROR);
				   return;
			   }
			   
			   //Save summaries
			   for(int i=0; i<dayStarData.getSummaries().size(); i++) {
				   SSRSnapshotDayStarData currentSummary = dayStarData.getSummaries().get(i);
				   //Set dayStarId for all summary objects
				   currentSummary.setDaystarId(dayStarId);
				   importSSRDayStarDAO.saveDayStarDataTemp(currentSummary);
			   }
			   
			   
			   //Save responses
			   for(int i=0; i<dayStarData.getResponse().size(); i++) {
				   SSRSnapshotDayStarHotel currentResponse = dayStarData.getResponse().get(i);
				   //Set dayStarId for all response objects
				   currentResponse.setDaystarId(dayStarId);
				   importSSRDayStarDAO.saveDayStarHotelTemp(currentResponse);
			   }
				   
			   
			   //Move data
			   int result = importSSRDayStarDAO.moveDaySTAR(dayStarId);
			   
			   if(result == 0) {
				   Messagebox.show("There was an error saving DayStar Data in final tables.", "DayStar Import", Messagebox.OK, Messagebox.ERROR);
				   importSSRDayStarDAO.deleteDayStarTemp(dayStarId);
				   return;
			   }
			   
			   Messagebox.show("Import complete.", "DayStar Import", Messagebox.OK, Messagebox.INFORMATION);
			   this.closePopup(null);
			   
	} catch (Exception e) {
		// TODO Auto-generated catch block
		//If something goes wrong after getting the importSession, delete Temp data
		if(dayStarId != 0 && importSSRDayStarDAO != null)
			importSSRDayStarDAO.deleteDayStarTemp(dayStarId);	
		
		e.printStackTrace();
		Messagebox.show("Error:." + e.getMessage(), "DayStar Import", Messagebox.OK, Messagebox.ERROR);
		}
	   
	   finally { if(importSSRDayStarDAO != null) importSSRDayStarDAO.closeCurrentConnection(); }
 
   } 
    
    
   
   
}