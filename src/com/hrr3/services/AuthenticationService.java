package com.hrr3.services;


import java.util.ArrayList;

import com.hrr3.entity.Customer;
import com.hrr3.entity.Hotel;
import com.hrr3.entity.ProductType;
import com.hrr3.entity.User;

public interface AuthenticationService {

	/**do Authentication with account and password**/
	public User doAuthentication(String account, String password);
	
	/**find valid Customers for this user trying to access the System**/
	public ArrayList<Customer> findValidCustomers(Integer UserId);
	
	/**find valid Customers for this user trying to access the System**/
	public ArrayList<ProductType> findValidProducts(Integer UserId);
	
	/**find all Customers for an ADMIN user*/
	public ArrayList<Customer> getAllCustomers();
	
	/**find Hotels associated to this user and company for normal USER*/
	public ArrayList<Hotel> getUserHotels(Integer userId, Integer customerId);
	
	/**find all Hotels associated with a company for ADMIN user*/
	public ArrayList<Hotel> getCustomerHotels(Integer customerId);
	
	
	/**logout current user**/
	public void logout();
	
	/**get current user credential**/
	public User getUserData();

	public void setUserData(User user);
	
}
