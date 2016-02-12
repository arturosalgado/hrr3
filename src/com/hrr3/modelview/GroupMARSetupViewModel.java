package com.hrr3.modelview;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;

import com.hrr3.authentication.AuthenticationServiceHRR3Impl;
import com.hrr3.controller.ListboxArrowKeyController;
import com.hrr3.entity.Hotel;
import com.hrr3.entity.LRRData;
import com.hrr3.entity.ssr.SSRMARLeadTime;
import com.hrr3.entity.ssr.SSRSUMARRate;
import com.hrr3.model.LRRDAO;
import com.hrr3.model.SSRGroupMarDAO;
import com.hrr3.services.AuthenticationService;

public class GroupMARSetupViewModel extends ListboxArrowKeyModelView {
	
	private List<SSRMARLeadTime> marListData;
	private SSRSUMARRate marInfo;
	private Hotel currentHotel;
	private SSRGroupMarDAO marDAO;
	
	@Wire("#dateFrom")
	public Datebox dateFrom;
	
	@Wire("#dateTo")
	public Datebox dateTo;
	
	@Wire("#marInfoSun")
	public Intbox marInfoSun;
	@Wire("#marInfoMon")
	public Intbox marInfoMon;
	@Wire("#marInfoTue")
	public Intbox marInfoTue;
	@Wire("#marInfoWed")
	public Intbox marInfoWed;
	@Wire("#marInfoThu")
	public Intbox marInfoThu;
	@Wire("#marInfoFri")
	public Intbox marInfoFri;
	@Wire("#marInfoSat")
	public Intbox marInfoSat;
	
	@Wire("#marInfoCpor")
	public Decimalbox marInfoCpor;
	@Wire("#marInfoOcc")
	public Decimalbox marInfoOcc;
	
	private AuthenticationService authService;
	private SimpleDateFormat dateFormat;
	
	@AfterCompose
	public void initSetup(@ContextParam(ContextType.VIEW) Component view)
	{
	     Selectors.wireComponents(view, this, false);	      
	}
		
	public GroupMARSetupViewModel() {
		
		super(0);
		
		authService = new AuthenticationServiceHRR3Impl();
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		if(authService.getUserData().getCurrentHotel() == null || authService.getUserData().getCurrentHotel().getHotelId() ==null || authService.getUserData().getCurrentHotel().getHotelId() < 1)
		{Messagebox.show("Please select a valid Hotel and return to this section."); return;}
		
		this.currentHotel = authService.getUserData().getCurrentHotel();
		this.marDAO = new SSRGroupMarDAO(authService.getUserData().getCurrentHotel());
	}
	
	@NotifyChange("marInfo")
	@Command
	public void fillMARInfo() {						
		this.marInfo = marDAO.setupLeadTime();
	}
	
	@NotifyChange("marListData")
	@Command
	public void fillMARData() {		
		this.marListData = marDAO.loadSSRData(dateFormat.format(dateFrom.getValue()), dateFormat.format(dateTo.getValue()), authService.getUserData().getCurrentCustomer().getCustomerId());
		
	}
	
	
	@Command
	public void updateMARDataRow(@BindingParam("selectedRow") SSRMARLeadTime selectedRow) {
				
		String newMARRate = "0";
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		try {
			String statdate =  dateFormat.format(df.parse(selectedRow.getStatdate()));
			this.marDAO.updateOverride(statdate, selectedRow.getOverrides());
			newMARRate = this.marDAO.calculateSSRMARRate(statdate, statdate, authService.getUserData().getCurrentCustomer().getCustomerId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		selectedRow.setMarrate(newMARRate);
		BindUtils.postNotifyChange(null, null, selectedRow, "marrate");
	}
	
	@Command
	public void saveMARInfo () {
		
		int d1 = this.marInfoSun.getValue() == null ? 0 : this.marInfoSun.getValue();
		int d2 = this.marInfoMon.getValue() == null ? 0 : this.marInfoMon.getValue();
		int d3 = this.marInfoTue.getValue() == null ? 0 : this.marInfoTue.getValue();
		int d4 = this.marInfoWed.getValue() == null ? 0 : this.marInfoWed.getValue();
		int d5 = this.marInfoThu.getValue() == null ? 0 : this.marInfoThu.getValue();
		int d6 = this.marInfoFri.getValue() == null ? 0 : this.marInfoFri.getValue();
		int d7 = this.marInfoSat.getValue() == null ? 0 : this.marInfoSat.getValue();

		BigDecimal cpor = this.marInfoCpor.getValue() == null ? new BigDecimal(0) : this.marInfoCpor.getValue();
		BigDecimal occTfdr = this.marInfoOcc.getValue() == null ? new BigDecimal(0) : this.marInfoOcc.getValue();
		
		this.marInfo.setSun(d1);
		this.marInfo.setMon(d2);
		this.marInfo.setTue(d3);
		this.marInfo.setWed(d4);
		this.marInfo.setThu(d5);
		this.marInfo.setFri(d6);
		this.marInfo.setSat(d7);
		this.marInfo.setCpor(cpor);
		this.marInfo.setOccTfdr(occTfdr);
		this.marDAO.saveMARRate(this.marInfo, dateFormat.format(dateFrom.getValue()), dateFormat.format(dateTo.getValue()), authService.getUserData().getCurrentCustomer().getCustomerId());
		
		Messagebox.show("MAR Info saved."); return;
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
	 * @return the marListData
	 */
	public List<SSRMARLeadTime> getMarListData() {
		return marListData;
	}

	/**
	 * @return the marInfo
	 */
	public SSRSUMARRate getMarInfo() {
		return marInfo;
	}
}
