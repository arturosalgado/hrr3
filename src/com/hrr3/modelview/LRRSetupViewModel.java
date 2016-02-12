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

import com.hrr3.authentication.AuthenticationServiceHRR3Impl;
import com.hrr3.entity.Hotel;
import com.hrr3.entity.LRRData;
import com.hrr3.model.LRRDAO;
import com.hrr3.services.AuthenticationService;

public class LRRSetupViewModel {
	
	private List<LRRData> lrrListData;
	private Hotel currentHotel;
	private LRRDAO lrrDAO;
		
	@Wire("#lrrGrid")
	public Listbox lrrGrid;
	
	@Wire("#dateFrom")
	public Datebox dateFrom;
	
	@Wire("#dateTo")
	public Datebox dateTo;
	
	private AuthenticationService authService;
	private SimpleDateFormat dateFormat;
	
	@AfterCompose
	public void initSetup(@ContextParam(ContextType.VIEW) Component view)
	{
	     Selectors.wireComponents(view, this, false);
	      
	}
		
	public LRRSetupViewModel() {
		
		authService = new AuthenticationServiceHRR3Impl();
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		if(authService.getUserData().getCurrentHotel() == null || authService.getUserData().getCurrentHotel().getHotelId() ==null || authService.getUserData().getCurrentHotel().getHotelId() < 1)
		{Messagebox.show("Please select a valid Hotel and return to this section."); return;}
		
		this.currentHotel = authService.getUserData().getCurrentHotel();
		
	}
	
	@NotifyChange("lrrListData")
	@Command
	public void fillLRRData() {
		
		this.lrrDAO = new LRRDAO(authService.getUserData().getCurrentHotel());
		this.lrrListData = lrrDAO.getSSRData(dateFormat.format(dateFrom.getValue()), dateFormat.format(dateTo.getValue()), authService.getUserData().getCurrentCustomer().getCustomerId());		
	}
	
	
	@Command
	public void updateSSRDataRow(@BindingParam("selectedRow") LRRData selectedRow) {
		
		String newLrr = "0";
		try {
			newLrr = lrrDAO.updateSSRLRR(selectedRow);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		selectedRow.setLrr(newLrr);
		BindUtils.postNotifyChange(null, null, selectedRow, "lrr");
	}

	/**
	 * @return the lrrListData
	 */
	public List<LRRData> getLrrListData() {
		return lrrListData;
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

	
}
