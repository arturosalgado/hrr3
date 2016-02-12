package com.hrr3.entity;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * User entity
 */
public class User implements Serializable,Cloneable {
	private static final long serialVersionUID = 1L;
	
	private Integer userId;
	private String account;
	private RoleType role;
	private SecurityType rm3Role;
	private ArrayList<ProductType> products;

	private String firstName;
	private String lastName;
	private String password;
	private String email;	
	private ArrayList<Customer> customers;
	private ArrayList<Hotel> hotels;
	private Customer currentCustomer;	
	private Hotel currentHotel;
	
	private ScreenPreferences screenPreferences;
	
	
	public User() {
		
		this.screenPreferences = new ScreenPreferences();
		
	}
	

	public User(Integer userId, String account, String password, String firstName, String lastName, String email) {
		
		this.userId = userId;
		this.account = account;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;		
	}
	
	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	public String getAccount() {
		return account;
	}

	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the logo
	 */
	public ArrayList<Customer> getCustomers() {
		return customers;
	}

	/**
	 * @param logo the logo to set
	 */
	public void setCustomers(ArrayList<Customer> customers) {
		this.customers = customers;
	}
	
	/**
	 * @return the currentCustomer
	 */
	public Customer getCurrentCustomer() {
		return currentCustomer;
	}


	/**
	 * @return the currentHotel
	 */
	public Hotel getCurrentHotel() {
		return currentHotel;
	}


	/**
	 * @param currentHotel the currentHotel to set
	 */
	public void setCurrentHotel(Hotel currentHotel) {
		this.currentHotel = currentHotel;
	}


	/**
	 * @param currentCustomer the currentCustomer to set
	 */
	public void setCurrentCustomer(Customer currentCustomer) {
		this.currentCustomer = currentCustomer;
	}


	/**
	 * @return the hotels
	 */
	public ArrayList<Hotel> getHotels() {
		return hotels;
	}


	/**
	 * @param hotels the hotels to set
	 */
	public void setHotels(ArrayList<Hotel> hotels) {
		this.hotels = hotels;
	}


	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}


	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}


	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * @return the lastName
	 */
	public String getFullName() {
		return firstName + " " + lastName;
	}

	/**
	 * @return the roleId
	 */
	public RoleType getRole() {
		return this.role;
	}


	/**
	 * @param roleId the roleId to set
	 */
	public void setRole(RoleType role) {
		this.role = role;
	}


	/**
	 * @return the products
	 */
	public ArrayList<ProductType> getProducts() {
		return products;
	}


	/**
	 * @param products the products to set
	 */
	public void setProducts(ArrayList<ProductType> products) {
		this.products = products;
	}
	
	public boolean containsProduct(ProductType product) {
		
		return this.products.contains(product);
	}


	/**
	 * @return the screenPreferences
	 */
	public ScreenPreferences getScreenPreferences() {
		return screenPreferences;
	}


	/**
	 * @return the rm3Role
	 */
	public SecurityType getRm3Role() {
		return rm3Role;
	}


	/**
	 * @param rm3Role the rm3Role to set
	 */
	public void setRm3Role(SecurityType rm3Role) {
		this.rm3Role = rm3Role;
	}


	/**
	 * @param screenPreferences the screenPreferences to set
	 */
	public void setScreenPreferences(ScreenPreferences screenPreferences) {
		this.screenPreferences = screenPreferences;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((account == null) ? 0 : account.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
			return false;
		return true;
	}
	
	public static User clone(User user){
		try {
			return (User)user.clone();
		} catch (CloneNotSupportedException e) {
			//not possible
		}
		return null;
	}


	@Override
	public String toString() {
		return "User [userId=" + userId + ", account=" + account + ", role=" + role + ", rm3Role=" + rm3Role
				+ ", products=" + products + ", firstName=" + firstName + ", lastName=" + lastName + ", password="
				+ password + ", email=" + email + ", customers=" + customers + ", hotels=" + hotels
				+ ", currentCustomer=" + currentCustomer + ", currentHotel=" + currentHotel + ", screenPreferences="
				+ screenPreferences + "]";
	}
}
