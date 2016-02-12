package com.hrr3.authentication;


import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

import com.hrr3.entity.Customer;
import com.hrr3.entity.Hotel;
import com.hrr3.entity.ProductType;
import com.hrr3.entity.User;
import com.hrr3.model.CustomerDAO;
import com.hrr3.model.HotelDAO;
import com.hrr3.model.ProductDAO;
import com.hrr3.model.UserDAO;

public class AuthenticationServiceHRR3Impl extends AuthenticationServiceBasiccImpl{
	private static final long serialVersionUID = 1L;
	 
	@Override
	public User doAuthentication(String nm, String pd) {		
	
		User user = new UserDAO().findUser(nm);		
		
		//a simple plan text password verification
		if(user==null || !user.getPassword().equals(pd)){
			return null;
		}
			
		//TODO handle the role here for authorization
		return user;
	}
	
	@Override
	public ArrayList<Customer> findValidCustomers(Integer userId) {		
	
		CustomerDAO customerDAO = new CustomerDAO();
		ArrayList<Customer> customers = customerDAO.findCustomersByUserId(userId);
		
		return customers;
	
	}
	
	@Override
	public ArrayList<Customer> getAllCustomers() {		
	
		CustomerDAO customerDAO = new CustomerDAO();
		ArrayList<Customer> customers = customerDAO.getAllCustomers();
		
		return customers;
	
	}
	
	@Override
	public ArrayList<ProductType> findValidProducts(Integer userId) {		
	
		ProductDAO productDAO = new ProductDAO();
		ArrayList<ProductType> products = productDAO.findProductsByUserId(userId);
		
		return products;
	
	}
	
	@Override
	public ArrayList<Hotel>getUserHotels (Integer userId, Integer customerId) {
		
		HotelDAO hotelDAO = new HotelDAO();
		ArrayList<Hotel> hotels = hotelDAO.findUserHotels(userId, customerId);
		
		return hotels;
	}
	
	public ArrayList<Hotel> getCustomerHotels(Integer customerId) {
		
		HotelDAO hotelDAO = new HotelDAO();
		ArrayList<Hotel> hotels = hotelDAO.findCustomerHotels(customerId);
		
		return hotels;
	}
	
	@Override
	public void logout() {
		Session sess = Sessions.getCurrent();	
		
		
		sess.removeAttribute("userData");		
		sess.invalidate();
	}
}
