package com.hrr3.modelview;
 
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Messagebox;

import com.hrr3.entity.ssr.ImportSSRLRRData;
import com.hrr3.model.ImportSSRDAO;
import com.hrr3.model.ImportSSRLRRDAO;
import com.hrr3.services.LRRFileImportService;
 
public class LRRImportFileMV extends GenericImportFileMV {
 
 
   @Wire("#lrrImportFrom")
   private Datebox lrrImportFrom;
   
   @Wire("#lrrImportTo")
   private Datebox lrrImportTo;  
  
   @AfterCompose
   public void initSetup(@ContextParam(ContextType.VIEW) Component view)
             {
     Selectors.wireComponents(view, this, false);
     win.setTitle("Import LRR");
      
   }
      
    public void executeImportFromFile() throws IOException {
    	
    	if (!this.validateHotelSelected()) return;
	   
	   LRRFileImportService fileImportService = new LRRFileImportService();
	   SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	   ImportSSRLRRDAO importLRRDAO = null;
	   ImportSSRDAO  ssrDAO = null;
	   
	   try {
		   
		   if(lrrImportFrom.getValue().compareTo(lrrImportTo.getValue()) <= 0)	{
			   List<ImportSSRLRRData> listData = fileImportService.parseFileToObjects(filePath, lrrImportFrom.getValue(), lrrImportTo.getValue());
			   importLRRDAO = new ImportSSRLRRDAO(this.currentHotel);
			   int importSession = 0;
			   
			   for(int i=0; i<listData.size(); i++) {
				   
				   importSession =  importLRRDAO.saveTempSSRLRRImport(listData.get(i), importSession, currentUserId);				  
				   
				   if(importSession == 0) {
					   Messagebox.show("There was an error saving LRR Data in temporal tables.", "LRR Import", Messagebox.OK, Messagebox.ERROR);
					   return;
				   }
					   
			   }			   
			   			   
			   ssrDAO = new ImportSSRDAO(currentHotel);			   
			   ssrDAO.fillSSRData(dateFormat.format(lrrImportFrom.getValue()), dateFormat.format(lrrImportTo.getValue()));
			   
			   int successMove = importLRRDAO.moveLRRData(importSession);
			   int successFill = importLRRDAO.calcSSRLRR(dateFormat.format(lrrImportFrom.getValue()), dateFormat.format(lrrImportTo.getValue()));
			   
			   
			   if(successMove == 1 && successFill ==1) {
				   Messagebox.show("Import was successfully executed.", "LRR Import", Messagebox.OK, Messagebox.INFORMATION);
				   this.closePopup(null);
			   }
				
			   else
				   Messagebox.show("There was an error while passing to master LRR Tables.", "LRR Import", Messagebox.OK, Messagebox.ERROR);
			   
			   
		   }			   
		   else {
			   Messagebox.show("StartDate can't be greater than EndDate.", "LRR Import", Messagebox.OK, Messagebox.ERROR);
			   return;
		   }
		   
		   //Messagebox.show("Import complete.", "LRR Import", Messagebox.OK, Messagebox.INFORMATION);
			   
	} catch (Exception e) {
		e.printStackTrace();
		Messagebox.show("Error:." + e.getMessage(), "LRR Import", Messagebox.OK, Messagebox.ERROR);
		}
	   
	   //Close both DAO connections
	   finally { if(importLRRDAO != null) importLRRDAO.closeCurrentConnection(); if(ssrDAO != null) ssrDAO.closeCurrentConnection();}
 
   } 
   
   
}