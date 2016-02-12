package com.hrr3.controller;

import java.util.ArrayList;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.hrr3.authentication.AuthenticationServiceHRR3Impl;
import com.hrr3.entity.Customer;
import com.hrr3.entity.Hotel;
import com.hrr3.entity.ProductType;
import com.hrr3.entity.RoleType;
import com.hrr3.entity.User;
import com.hrr3.services.AuthenticationService;

public class LoginController extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;
	
	//wire components
	@Wire
	Textbox account;
	@Wire
	Textbox password;
	@Wire
	Label message;
	@Wire
	Row customerRow;

	
	@Wire
	Cell loginButtonContainer;
	
	@Wire
	Button login;	
	
	@Wire
	Window loginWin;
		
	//services
	AuthenticationService authService = new AuthenticationServiceHRR3Impl();
	//User trying to login
	User user = null;
	
	@Listen("onClick=#login;onOK=#loginWin")
	public void doLogin(){
		
		String nm = account.getValue();
		String pd = password.getValue();
		
		//Authenticate user by validating user and password
		User user = authService.doAuthentication(nm,pd);		
		if(user == null){
			message.setValue("account or password are not correct.");
			return;
		}		
		
		
		ArrayList<Customer> customers = null;
		//If this is a common user, look for the validCustomer
		if(user.getRole() == RoleType.ADMIN)
			customers = authService.getAllCustomers();
		else
			customers = authService.findValidCustomers(user.getUserId());
			
			//If no Customers associated, display error message
			if(customers == null || customers.size() < 1){
				message.setValue("No customers associated with the " + user.getRole().toString());
				return;
			}				
			//Set the list of Customer associate with ADMIN.
			user.setCustomers(customers);		
		
		//If this User is USER type, then query of products allowed is needed.	
		//ADMIN users have all PRODUCTS available
		if(user.getRole() == RoleType.USER){
			
			ArrayList<ProductType> products = authService.findValidProducts(user.getUserId());
			if(products == null || products.size()<1) {
				
				System.out.println("*************** No Products Found *****************");
				message.setValue("No Products associated for this user");
				user.setCustomers(null);
				return;
			}
					
			else
				user.setProducts(products);
				
				
		}
		else {
			System.out.println("*************** Admin User - All Products Access *****************");	
		}
		
		
		//Save user in the session knowing it has valid customer associated and products assigned (normal users)
		authService.setUserData(user);		
		message.setValue("Welcome, "+ user.getFullName());
		message.setSclass("");
		
		//Create customer combo only if customer list is greater than 1.
		if(customers.size() > 1)
			this.createComboCustomers(user);
		else
			enterRM3App(customers.get(0));
		
		//Disable login
		this.disableLoginForm();
		
	}	
	
	private void createComboCustomers(User user) {
		
		//Logic for Customer Combo BEGIN
		Combobox customerComboBox= new Combobox();		
		customerComboBox.setWidth("180px");
		//customerComboBox.setStyle("");
		ListModelList<Customer> lmlCustomer = new ListModelList<Customer>(user.getCustomers());		
		customerComboBox.setModel(lmlCustomer);	
		customerComboBox.setReadonly(true);	
		
		Component customerRowComponentTemp = customerRow;		
		customerRowComponentTemp.setVisible(true);	
		customerRow.appendChild(customerComboBox);
		
		customerComboBox.setVisible(user.getCustomers().size() > 1);
		customerComboBox.addEventListener("onSelect", new EventListener<SelectEvent<Combobox,Customer>>() {
            public void onEvent(SelectEvent<Combobox,Customer> event) throws Exception {
            	//get Customer selected in the combo
            	Set<Customer> data = event.getSelectedObjects();
            	Customer customerSelected = data.iterator().next();
            	
            	enterRM3App(customerSelected);
            	
        }});
		//Login for Hotel Combo END		
	}
	
	public void enterRM3App(Customer customerSelected) {
		
		//get current session and add selected Customer
    	Session sess = Sessions.getCurrent();
		User user = (User)sess.getAttribute("userData");
		user.setCurrentCustomer(customerSelected);
		
		ArrayList<Hotel> hotels = null;
		//If this User is USER, then get only those Hotels associated with the Customer
		if(user.getRole() == RoleType.USER)
			hotels = authService.getUserHotels(user.getUserId(), user.getCurrentCustomer().getCustomerId());
		else
			hotels = authService.getCustomerHotels(user.getCurrentCustomer().getCustomerId());
		
			if(hotels == null || hotels.size()<1)
				System.out.println("*************** No Hotels Found *****************");	
			else {        				
				user.setHotels(hotels);
        		//user.setCurrentHotel(hotels.get(0));        				
			}
		
		//Redirect to home page
    	Executions.sendRedirect("/");
	}
	
	private void disableLoginForm() {
		
		loginButtonContainer.removeChild(login);
		account.setReadonly(true);
		password.setReadonly(true);
		loginWin.removeEventListener("onOK", loginWin.getEventListeners("onOK").iterator().next());
		
	}
}
