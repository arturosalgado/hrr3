package com.hrr3.controller;

import java.util.Map;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.Initiator;

import com.hrr3.authentication.AuthenticationServiceHRR3Impl;
import com.hrr3.entity.User;
import com.hrr3.services.AuthenticationService;

public class AuthenticationInit implements Initiator {

	//services
	AuthenticationService authService = new AuthenticationServiceHRR3Impl();
	
	public void doInit(Page page, Map<String, Object> args) throws Exception {
		
		User cre = authService.getUserData();
		if(cre==null ){
			Executions.sendRedirect("/login.zul");
			return;
		}
	}
}