package com.hrr3.controller;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import javax.servlet.ServletContext;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.hrr3.model.UserDAO;
import com.hrr3.services.AuthenticationService;
import com.hrr3.services.EmailService;
import com.hrr3.services.EmailServiceSIZEImpl;

public class ContactSupportController extends SelectorComposer<Component>  {
	private static final long serialVersionUID = 1L;
			
	private ListModel<String> reasonsModel;
		
	@Wire
	Combobox reasonsCombo;
	
	@Wire
	Textbox subjectTxt;
	
	@Wire
	Textbox messageTxt;
		
	@Wire
    Window contactSupportForm;
	
	public ContactSupportController() {		
		reasonsModel = new ListModelList<String>(new UserDAO().getReasonType());        
    }
	
	
	public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);        
	}
	

    @Listen("onClick = #submitBtn")
    public void submit() {    	
   	
    	String reason = reasonsCombo.getSelectedItem().getValue();
    	String subject = subjectTxt.getValue();
    	String message = messageTxt.getValue();
    	sendEmail(reason + ":" + subject, message);
    	Messagebox.show("Email sent to support", "Contact Support", Messagebox.OK, Messagebox.INFORMATION);
    	contactSupportForm.detach();
    }
    
    private void sendEmail(String subject, String content) {
    	
    	ServletContext c = this.getPage().getDesktop().getWebApp().getServletContext();
    	
  	  if (c != null) {     	    	
  		  
  	    	String server = c.getInitParameter( "smtp-server");
  	    	String port = c.getInitParameter( "smtp-port");
  	    	String account = c.getInitParameter( "smtp-account");
  	    	String to = c.getInitParameter( "smtp-to");
  	    	String password = c.getInitParameter( "smtp-password");
  	    	
  	    	EmailService emailService = new EmailServiceSIZEImpl(server, port, account, password);
  	    	
  	    	try {
  	    		if(emailService.initializeEmailService())
  	    			{ emailService.sendEmail(subject, content, to); }
  	    		else
  	    			{ c.log("******** SMTP could not be initialized ********"); }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
  	    	
  	    }  	  
    }
    
    
	/**
	 * @return the hotelsModel
	 */
	public ListModel<String> getReasonsModel() {
		return reasonsModel;
	}
	
}
