package com.hrr3.controller;


import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;

import com.hrr3.authentication.AuthenticationServiceHRR3Impl;
import com.hrr3.services.AuthenticationService;

public class LogoutController extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;
	
	//services
	AuthenticationService authService = new AuthenticationServiceHRR3Impl();
	
	@Listen("onClick=#logout")
	public void doLogout(){		
		authService.logout();		
		Executions.sendRedirect("/");
		
	}
}
