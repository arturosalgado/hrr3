package com.hrr3.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import com.hrr3.authentication.AuthenticationServiceHRR3Impl;
import com.hrr3.entity.WeeklyMtgMin;
import com.hrr3.entity.WeeklyMtgMinGroupPace;
import com.hrr3.model.WeeklyMeetingSetUpDAO;
import com.hrr3.services.AuthenticationService;

public class WeeklyGroupPaceTabController extends SelectorComposer<Component> {
	
	private static final long serialVersionUID = 1L;
	
	//AuthService
	private AuthenticationService authService;
		
	private ListModel<WeeklyMtgMinGroupPace> groupPaceModel;
	
	@Wire
	Grid groupPaceGrid;
	
	@Wire
	Datebox activityDate;
		
	int suYear;
	
	//DAO
	private WeeklyMeetingSetUpDAO weeklyDAO;
	
	private WeeklyMtgMin currentWeeklyMeeting;
	
	
	public WeeklyGroupPaceTabController() {
		
		authService = new AuthenticationServiceHRR3Impl();		
		
		if(authService.getUserData().getCurrentHotel() == null || authService.getUserData().getCurrentHotel().getHotelId() ==null || authService.getUserData().getCurrentHotel().getHotelId() < 1)
		{Messagebox.show("Please select a valid Hotel and return to this section."); return;}
				
		weeklyDAO = new WeeklyMeetingSetUpDAO(authService.getUserData().getCurrentHotel());
		currentWeeklyMeeting =  weeklyDAO.fillDataWklyMtg();
		//Query WeeklyMeeting here to get SUYEAR
		this.suYear =currentWeeklyMeeting.getSsrSnapshotSUWklyMtg().getSuYear() == 0 ? Calendar.getInstance().get(Calendar.YEAR) : weeklyDAO.fillDataWklyMtg().getSsrSnapshotSUWklyMtg().getSuYear();
				
		//Set the label at tab startup
		List<WeeklyMtgMinGroupPace> weeklyGroupPaceList = weeklyDAO.fillDataGroupPace(this.suYear, authService.getUserData().getCurrentCustomer().getCustomerId());
		groupPaceModel = new ListModelList<WeeklyMtgMinGroupPace>(weeklyGroupPaceList);
	
	}
	
	public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date actDate = new Date();
        if(this.currentWeeklyMeeting.getActivityDate() != null && !this.currentWeeklyMeeting.getActivityDate().isEmpty())
        	actDate =  df.parse(this.currentWeeklyMeeting.getActivityDate()); 
        
        activityDate.setValue(actDate);
        System.out.println("********** Weekly Tabs : ActivityDate: " + actDate);
       
	}	
	
	/**
	 * @return the groupPaceModel
	 */
	public ListModel<WeeklyMtgMinGroupPace> getGroupPaceModel() {
		return groupPaceModel;
	}
	
	
}
