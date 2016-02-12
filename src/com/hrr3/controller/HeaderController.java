package com.hrr3.controller;


import java.util.ArrayList;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModel;
import org.zkoss.zkplus.databind.BindingListModelListModel;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;

import com.hrr3.authentication.AuthenticationServiceHRR3Impl;
import com.hrr3.entity.Customer;
import com.hrr3.entity.Hotel;
import com.hrr3.entity.RoleType;
import com.hrr3.entity.User;
import com.hrr3.services.AuthenticationService;

public class HeaderController extends GenericForwardComposer<Component> {
	private static final long serialVersionUID = 1L;
	
	AuthenticationService authService = new AuthenticationServiceHRR3Impl();
	
	@Wire
	Iframe iframeProfileContent;
	
	private ArrayList<Customer> customerList;
	private ArrayList<Hotel> hotelList;
	
	private Customer selectedCustomer;
	private Hotel selectedHotel;
	
	private Image headerLogo;
	
	private Label selectedHotelLabel;
	
	private Combobox hotelListCombo;

	 public ArrayList<Customer> getCustomers() {
		        return customerList;
		    }
	 
	 public Customer getSelectedCustomer() {		 	
	        return this.selectedCustomer;
	        
	    }
	 
	 public ArrayList<Hotel> getHotels() {
	        return hotelList;
	    }

	 public Hotel getSelectedHotel() {		 	
     return this.selectedHotel;
     
 }
	 
	 public void setSelectedCustomer(Customer customer) {
		 this.selectedCustomer = customer;
		 User userInSession = authService.getUserData();
		 userInSession.setCurrentCustomer(customer);
		 
		 System.out.println("************* Customer InSessionVar: ["+ authService.getUserData().getCurrentCustomer().getName() +"] **************");
		 System.out.println("************* Customer InSessionObj: ["+ userInSession.getCurrentCustomer().getName() +"] **************");
		 
		 if(this.hotelList != null ) this.hotelList.clear();
		 //After setting currentCustomer, query all Hotels related to this customer
		 if(userInSession.getRole() == RoleType.ADMIN)
			 this.hotelList = authService.getCustomerHotels(customer.getCustomerId());
		 else
			 this.hotelList = authService.getUserHotels(userInSession.getUserId(), customer.getCustomerId());
		 
		 	if(this.hotelList.size() > 0){
		 		
		 		//ListModelList<Hotel> lmlHotels = new ListModelList<Hotel>(this.hotelList);	
		 		BindingListModel<Hotel> lmlHotels = new BindingListModelListModel<Hotel>(new ListModelList<Hotel>(this.hotelList));
		 		hotelListCombo.setValue("- Select Hotel -");
				hotelListCombo.setModel(lmlHotels);
				this.selectedHotelLabel.setValue("");
				//Save current hotels in session.				
				userInSession.setHotels(this.hotelList);
				userInSession.setCurrentHotel(null);
				//Set the first Item in the combo				
				//this.setSelectedHotel(hotelList.iterator().next());
				
		 	} 
		 	
		 	else  {
		 		hotelListCombo.setModel(null); 
		 		hotelListCombo.setValue("- No Hotels Found -");
		 		this.setSelectedHotel(null);
		 		this.selectedHotelLabel.setValue("");
		 		userInSession.setHotels(null);	
		 		}
		 	
			 
		
	 }
	 
	 public void setSelectedHotel(Hotel hotel) {
		 this.selectedHotel = hotel;
		 User userInSession = authService.getUserData();
		 userInSession.setCurrentHotel(hotel);
		 
		 System.out.println("************ Hotel InSessionVar: ["+ authService.getUserData().getCurrentHotel() +"] **************");
		 System.out.println("************ Hotel InSessionObj: ["+ userInSession.getCurrentHotel() +"] **************");
		
	 }	 
	 
	public void doAfterCompose(Component comp) throws Exception {
	        super.doAfterCompose(comp);
	        
	        this.customerList = authService.getUserData().getCustomers();
	        this.selectedCustomer = authService.getUserData().getCurrentCustomer();        
	        this.hotelList = authService.getUserData().getHotels();	        
	        this.selectedHotel = authService.getUserData().getCurrentHotel();
	        
	        //If there are hotels for this user/company but before refresh the page user didn't select an hotel
	        //Put label SELECT HOTEL in the dropdown
	        if(this.hotelList != null && this.hotelList.size() > 0 && this.selectedHotel == null)
	        	hotelListCombo.setValue("- Select Hotel -");
	        else
	        	hotelListCombo.setValue("- No Hotels Found -");
	       
	        
	        System.out.println("***************** Composing Combos *******************");
	 }
	
	public void onSelect$customerListCombo(Event evt) {		         
		           
		           headerLogo.setSrc("/images/companies/"+ this.selectedCustomer.getLogo());		           		          
		           Executions.sendRedirect("/");
		           
		     }
	
	public void onSelect$hotelListCombo(Event evt) {
        
        System.out.println("Selected HotelId: " + evt);        
        Executions.sendRedirect("/");
        
  }


}
