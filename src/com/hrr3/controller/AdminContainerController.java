package com.hrr3.controller;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Iframe;

public class AdminContainerController extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;
	
	private String currentHostName = Sessions.getCurrent().getServerName().equals("localhost")?"http://dev2.valetsoftware.com":"http://" + Sessions.getCurrent().getServerName();

	@Wire
	Iframe iframeAdminContent;
	
	@Wire
	Iframe includeAdminComponent;
	
	@Listen("onClick=#productsBtn")
	public void showAdminProduct(){
		
		iframeAdminContent.setSrc(this.currentHostName + "/HRR_admin_2013/product.php");
		iframeAdminContent.setVisible(true);
	}
	
	@Listen("onClick=#titlesBtn")
	public void showTitles(){
		
		iframeAdminContent.setSrc(this.currentHostName + "/HRR_admin_2013/user_titles.php");
		iframeAdminContent.setVisible(true);
	}	
	
	@Listen("onClick=#statesBtn")
	public void showStates(){
		
		iframeAdminContent.setSrc(this.currentHostName + "/HRR_admin_2013/state.php");
		iframeAdminContent.setVisible(true);
	}		
	
	@Listen("onClick=#countriesBtn")
	public void showCountries(){
		
		iframeAdminContent.setSrc(this.currentHostName + "/HRR_admin_2013/country.php");
		iframeAdminContent.setVisible(true);
	}
	
	@Listen("onClick=#brandsBtn")
	public void showHotelBrands(){
		
		iframeAdminContent.setSrc(this.currentHostName + "/HRR_admin_2013/hotel_brands.php");
		iframeAdminContent.setVisible(true);
	}	
	
	@Listen("onClick=#statusBtn")
	public void showHotelStatus(){
		
		iframeAdminContent.setSrc(this.currentHostName + "/HRR_admin_2013/hotel_status.php");
		iframeAdminContent.setVisible(true);
	}	
	
	@Listen("onClick=#customersBtn")
	public void showCustomers(){
		
		iframeAdminContent.setSrc(this.currentHostName + "/HRR_admin_2013/customers.php");
		iframeAdminContent.setVisible(true);
	}	
	
	@Listen("onClick=#hotelsBtn")
	public void showHotels(){
		
		iframeAdminContent.setSrc(this.currentHostName + "/HRR_admin_2013/hotels.php");
		iframeAdminContent.setVisible(true);
	}	
	
	@Listen("onClick=#accountsBtn")
	public void showAccounts(){
		
		iframeAdminContent.setSrc(this.currentHostName + "/HRR_admin_2013/rm3_accounts.php");
		iframeAdminContent.setVisible(true);
	}
	
	@Listen("onClick=#marketSegmentsBtn")
	public void showMarketSegments(){
		
		iframeAdminContent.setSrc(this.currentHostName + "/HRR_admin_2013/segment_account.php");
		iframeAdminContent.setVisible(true);
	}
	
	@Listen("onClick=#rm3ConfigurationBtn")
	public void showRm3ConfigurationBtn(){
		
		iframeAdminContent.setSrc(this.currentHostName + "/HRR_admin_2013/rm3_system_config.php");
		iframeAdminContent.setVisible(true);
	}
	
	@Listen("onClick=#usersBtn")
	public void showRm3UsersBtn(){
		
		iframeAdminContent.setSrc(this.currentHostName + "/HRR_admin_2013/rm3_accounts_users.php");
		iframeAdminContent.setVisible(true);
	}
	
	//@Listen("onClick=#rm3RegionsBtn")
	public void showRm3RegionsBtn(){
		
		iframeAdminContent.setSrc(this.currentHostName + "/HRR_admin_2013/rm3_accounts_users.php");
		iframeAdminContent.setVisible(true);
	}
	
	
	
	
	
}
