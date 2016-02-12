package com.hrr3.modelview;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.io.Files;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.hrr3.authentication.AuthenticationServiceHRR3Impl;
import com.hrr3.entity.Hotel;
import com.hrr3.services.AuthenticationService;

public abstract class GenericImportFileMV {
	
	   private AuthenticationService authService;
	   protected Hotel currentHotel = null;
	   protected int currentUserId = 0;
	   
	   protected String filePath;
	   protected boolean fileuploaded = false;
	   AMedia fileContent;
	   protected String fileName = "No file selected";
	   protected Component mainApp;
	   
	   @Wire("#importForm")
	   public Window win;
	   
	   @Command 
	   public void closePopup(Event e) {
	   	this.win.detach();
	   }
	   
	
	   public boolean validateHotelSelected() {
		   
		   authService = new AuthenticationServiceHRR3Impl();		
			
			if(authService.getUserData().getCurrentHotel() == null || authService.getUserData().getCurrentHotel().getHotelId() ==null || authService.getUserData().getCurrentHotel().getHotelId() < 1)
				{Messagebox.show("Please select a valid Hotel and return to this section."); currentHotel = null; return false; }
			
			currentHotel = authService.getUserData().getCurrentHotel();	
			currentUserId = authService.getUserData().getUserId();
			
			return true;
			
	   }
	
	   public AMedia getFileContent() {
	        return fileContent;
	   }
	 
	   public void setFileContent(AMedia fileContent) {
	        this.fileContent = fileContent;
	   }
	 
	   public boolean isFileuploaded() {
	       return fileuploaded;
	    }
	 
	   public void setFileuploaded(boolean fileuploaded) {
	     this.fileuploaded = fileuploaded;
	   }
	   
	   public String getFileName () {
		   return this.fileName;	   
	   }
	   
	   public void setFileName(String fileName) {
		   this.fileName = fileName;
	   }
	   
	   public abstract void executeImportFromFile() throws IOException;
	 
	 	  
	   @Command
	   @NotifyChange({"fileuploaded", "fileName"})
	   public void onUploadFile(
	            @ContextParam(ContextType.BIND_CONTEXT) BindContext ctx)
	            throws IOException {
	 
	        UploadEvent upEvent = null;
	     Object objUploadEvent = ctx.getTriggerEvent();
	      if (objUploadEvent != null && (objUploadEvent instanceof UploadEvent)) {
	            upEvent = (UploadEvent) objUploadEvent;
	     }
	       if (upEvent != null) {
	          Media media = upEvent.getMedia();
	           Calendar now = Calendar.getInstance();
	          int year = now.get(Calendar.YEAR);
	          int month = now.get(Calendar.MONTH); // Note: zero based!
	           int day = now.get(Calendar.DAY_OF_MONTH);
	           filePath = Executions.getCurrent().getDesktop().getWebApp()
	                 .getRealPath("/");
	          String yearPath = File.separatorChar + "temporal" + File.separatorChar + "imports" + File.separatorChar +  year + "-" + month + "-"
	                 + day + File.separatorChar;
	           filePath = filePath + yearPath;
	         File baseDir = new File(filePath);
	          if (!baseDir.exists()) {
	                baseDir.mkdirs();
	           }
	 
	           Files.copy(new File(filePath + media.getName()),
	                    media.getStreamData());
	                   
	           
	         fileuploaded = true;
	         filePath = filePath + media.getName();
	         fileName = media.getName();
	         
	         Clients.showNotification("Fill out remaining fields (if needed).","info",this.win,"top_right",3000);
	      }
	   }
	
	
	   @Command 
	    public void importData() throws IOException {
			
			mainApp = this.win;
			this.executeImportFromFile();
			Clients.showBusy(mainApp,"Importing Data...");

			mainApp.addEventListener(Events.ON_CLIENT_INFO, new EventListener<Event>() {
		        @Override
		        public void onEvent(Event event) throws Exception {
		        	//executeImportFromFile();    
		        	
		            Clients.clearBusy(mainApp);
		            
		            mainApp.removeEventListener(Events.ON_CLIENT_INFO, this);
		        }
		    });
			
			Events.echoEvent(Events.ON_CLIENT_INFO, mainApp, null);
		}
}
