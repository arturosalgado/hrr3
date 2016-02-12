package com.hrr3.services;

import com.hrr3.model.SecurityDAO;

public class RM3SecurityService {
	
	public boolean getIsMenuOptionEnabled(int menuOptionId, int roleId) {
		
		boolean result = new SecurityDAO().isMenuOptionEnabled(menuOptionId, roleId) == 1 ? true : false;
		return result;
	}

}
