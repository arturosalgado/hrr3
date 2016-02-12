package com.hrr3.modelview;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.hrr3.authentication.AuthenticationServiceHRR3Impl;
import com.hrr3.entity.Hotel;
import com.hrr3.entity.LRRData;
import com.hrr3.entity.RateCategory;
import com.hrr3.model.LRRDAO;
import com.hrr3.model.RateCategoryDAO;
import com.hrr3.services.AuthenticationService;

public class RateCategorySetupViewModel {
	
	private RateCategory rateCategory;
	private Hotel currentHotel;
	private RateCategoryDAO rateCategoryDAO;
			
	private AuthenticationService authService;
	
	@AfterCompose
	public void initSetup(@ContextParam(ContextType.VIEW) Component view)
	{
	     Selectors.wireComponents(view, this, false);	      
	}
		
	public RateCategorySetupViewModel() {
		
		authService = new AuthenticationServiceHRR3Impl();
		if(authService.getUserData().getCurrentHotel() == null || authService.getUserData().getCurrentHotel().getHotelId() ==null || authService.getUserData().getCurrentHotel().getHotelId() < 1)
		{Messagebox.show("Please select a valid Hotel and return to this section."); return;}
		
		this.currentHotel = authService.getUserData().getCurrentHotel();
		this.rateCategoryDAO = new RateCategoryDAO(authService.getUserData().getCurrentHotel());
		
	}
	
	@NotifyChange("rateCategory")
	@Command
	public void fillRateCatInfo() {
		this.rateCategory = this.rateCategoryDAO.populateColumnData();				
	}
		
	@Command
	public void saveCurrentConfig(@BindingParam("popup")Component popup) {
		this.rateCategoryDAO.saveRateCategoryData(rateCategory);
		Messagebox.show("Configuration was saved successfully.");
		popup.detach();
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
	 * @return the rateCategory
	 */
	public RateCategory getRateCategory() {
		return rateCategory;
	}

	/**
	 * @param rateCategory the rateCategory to set
	 */
	public void setRateCategory(RateCategory rateCategory) {
		this.rateCategory = rateCategory;
	}

	
}
