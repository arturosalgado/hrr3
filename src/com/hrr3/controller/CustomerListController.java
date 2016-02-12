package com.hrr3.controller;


import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Messagebox;

import com.hrr3.authentication.AuthenticationServiceHRR3Impl;
import com.hrr3.entity.Customer;
import com.hrr3.services.AuthenticationService;

public class CustomerListController extends GenericForwardComposer<Component> {
	private static final long serialVersionUID = 1L;
	
	AuthenticationService authService = new AuthenticationServiceHRR3Impl();
	
	private List<Customer> customerList;
	private Customer selectedCustomer;

	 public List<Customer> getCustomers() {
		        return customerList;
		    }
	 
	 public Customer getSelectedCustomer() {		 	
	        return this.selectedCustomer;
	        
	    }
	 public void setSelectedCustomer(Customer customer) {
		 this.selectedCustomer = customer;
		
	 }
	 
	public void doAfterCompose(Component comp) throws Exception {
	        super.doAfterCompose(comp);
	        
	        customerList = authService.getUserData().getCustomers();
	        this.selectedCustomer = authService.getUserData().getCurrentCustomer();
	 }
	
	public void onSelect$customerListCombo(Event evt) {
		         
		           Messagebox.show("Selected CustomerId: " + this.selectedCustomer.getCustomerId());
		           
		     }

}
