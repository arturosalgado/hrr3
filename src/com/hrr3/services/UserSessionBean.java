package com.hrr3.services;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;

import com.hrr3.entity.User;

/**
 * Session Bean implementation class UserSessionBean
 */
@Stateful
@LocalBean
public class UserSessionBean implements UserSessionBeanLocal {

    /**
     * Default constructor. 
     */
	private User u=null;
	
	
    public UserSessionBean() {
        // TODO Auto-generated constructor stub
    }


	public User getU() {
		return u;
	}


	public void setU(User u) {
		this.u = u;
	}

}
