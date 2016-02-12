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
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Messagebox;

import com.hrr3.entity.ssr.ImportSSRData;
import com.hrr3.model.ImportSSRDAO;
import com.hrr3.services.SSRFileImportService;
 
public class SSRImportFileMV extends GenericImportFileMV{
 

   @Wire("#ssrImportFrom")
   private Datebox ssrImportFrom;
   
   @Wire("#ssrImportTo")
   private Datebox ssrImportTo;
   
   @Wire("#chkComments")
   private Checkbox chkComments;
   
   @Wire("#chkRestrictions")
   private Checkbox chkRestrictions;
   
   @Wire("#chkRooms")
   private Checkbox chkRooms;
   
   @Wire("#chkPrice")
   private Checkbox chkPrice;
   
   @Wire("#chkBlockedRooms")
   private Checkbox chkBlockedRooms;
   
   
   @AfterCompose
   public void initSetup(@ContextParam(ContextType.VIEW) Component view)
             {
     Selectors.wireComponents(view, this, false);
     win.setTitle("Import SSR");
      
   }
   
    public void executeImportFromFile() throws IOException {
    	
       if (!this.validateHotelSelected()) return;
	   
	   SSRFileImportService fileImportService = new SSRFileImportService();
	   SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	   ImportSSRDAO importSSRDAO = null;
	   
	   try {
		   
		   if(ssrImportFrom.getValue().compareTo(ssrImportTo.getValue()) <= 0)	{
			   List<ImportSSRData> listData = fileImportService.parseFileToObjects(filePath, ssrImportFrom.getValue(), ssrImportTo.getValue());
			   importSSRDAO = new ImportSSRDAO(this.currentHotel);
			   int importSession = 0;
			   
			   for(int i=0; i<listData.size(); i++) {
				   
				   importSession = importSSRDAO.saveTempSSRImport(listData.get(i), importSession, currentUserId);
				   
				   if(importSession == 0) {
					   Messagebox.show("There was an error saving SSR Data in temporal tables.", "SSR Import", Messagebox.OK, Messagebox.ERROR);
					   return;
				   }
					   
			   }			   
			   
			   //Set flags depending if checkbox is checked
			   int updateSSR = chkRooms.isChecked() == true ? 1 : 0;;
			   int updateComments = chkComments.isChecked() == true ? 1 : 0;;
			   int updateRestrictions = chkRestrictions.isChecked() == true ? 1 : 0;;
			   int updatePriceDemandsTurnDows = chkPrice.isChecked() == true ? 1 : 0;;
			   int updateBlockedRooms = chkBlockedRooms.isChecked() == true ? 1 : 0;
			   
			   importSSRDAO.fillSSRData(dateFormat.format(ssrImportFrom.getValue()), dateFormat.format(ssrImportTo.getValue()));
			   int success = importSSRDAO.moveSSRData(importSession, updateSSR, updateComments, updateRestrictions, updatePriceDemandsTurnDows, updateBlockedRooms);
			   if(success == 1){
				   Messagebox.show("Import was successfully executed.", "SSR Import", Messagebox.OK, Messagebox.INFORMATION);
				   this.closePopup(null);
			   }
				   
			   else
				   Messagebox.show("There was an error while passing to master SSR Tables.", "SSR Import", Messagebox.OK, Messagebox.ERROR);
			   
			   
		   }			   
		   else {
			   Messagebox.show("StartDate can't be greater than EndDate.", "SSR Import", Messagebox.OK, Messagebox.ERROR);
			   return;
		   }
		   
		   Messagebox.show("Import complete.", "SSR Import", Messagebox.OK, Messagebox.INFORMATION);
			   
	} catch (Exception e) {
		// TODO Auto-generated catch block		
		e.printStackTrace();
		Messagebox.show("Error:." + e.getMessage(), "SSR Import", Messagebox.OK, Messagebox.ERROR);
		}
	   
	   finally  { if(importSSRDAO != null) importSSRDAO.closeCurrentConnection(); }
		
 
   } 
   
    
}