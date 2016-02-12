package com.hrr3.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Include;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;

import com.hrr3.authentication.AuthenticationServiceHRR3Impl;
import com.hrr3.entity.WeeklyMtgMin;
import com.hrr3.entity.WeeklyMtgMinGroupPace;
import com.hrr3.entity.ssrMigration.SSRSnapshotSUWklyMtg;
import com.hrr3.model.WeeklyMeetingSetUpDAO;
import com.hrr3.services.AuthenticationService;

public class WeeklyMeetingTabsController extends SelectorComposer<Component> {
	
	private static final long serialVersionUID = 1L;
	
	//AuthService
	private AuthenticationService authService;
	
	//Attendees/Critique Tab
	@Wire
	Include attendeesInclude;	
	Textbox attendees;
	Textbox critique;
	
	//WeeklyRooms Tab
	@Wire
	Include weeklyRoomsInclude;
	Combobox ssrSnapshotsCombo;
	
	//Outlook/Other Tab
	@Wire
	Include outlookInclude;
	Textbox futureOutlook;
	Textbox otherBusiness;
	
	//GroupPace Tab
	@Wire
	Include groupPaceInclude;
	Grid groupPaceGrid;
	Datebox activityDate;
	
	//DAO
	WeeklyMeetingSetUpDAO weeklyDAO;
	
	//currentWeeklyMeting - useful for the transobtEntered field which does not change during lifecycle
	WeeklyMtgMin currentWeeklyMtgMin;
	
	public WeeklyMeetingTabsController() {
		
		authService = new AuthenticationServiceHRR3Impl();		
		
		if(authService.getUserData().getCurrentHotel() == null || authService.getUserData().getCurrentHotel().getHotelId() ==null || authService.getUserData().getCurrentHotel().getHotelId() < 1)
			{Messagebox.show("Please select a valid Hotel and return to this section."); return;}
		
		weeklyDAO = new WeeklyMeetingSetUpDAO(authService.getUserData().getCurrentHotel());
	}
	
	public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        
        //WeeklyMtgMeeting Object to fill atteendees and outlook Tabs
        WeeklyMeetingSetUpDAO weeklyDAO = new WeeklyMeetingSetUpDAO(authService.getUserData().getCurrentHotel());
        this.currentWeeklyMtgMin = weeklyDAO.fillDataWklyMtg();
        
        //Atteendess/Critique Tab
        attendees = (Textbox)attendeesInclude.getFellow("attendees");
        critique = (Textbox)attendeesInclude.getFellow("critique");
        
        //Outlook/Other Tab
        futureOutlook = (Textbox)outlookInclude.getFellow("futureOutlook");
        otherBusiness = (Textbox)outlookInclude.getFellow("otherBusiness");
        
        //Weekly Rooms (Sales Pace) Tab
        ssrSnapshotsCombo = (Combobox) weeklyRoomsInclude.getFellow("ssrSnapshotsCombo");
        
        //Group Pace Tab
        groupPaceGrid = (Grid) groupPaceInclude.getFellow("groupPaceGrid");
        activityDate = (Datebox) groupPaceInclude.getFellow("activityDate");
                
        //Set All components
        attendees.setText(this.currentWeeklyMtgMin.getSsrSnapshotSUWklyMtg().getAttendees());
        critique.setText(this.currentWeeklyMtgMin.getSsrSnapshotSUWklyMtg().getCritique());
        
        futureOutlook.setText(this.currentWeeklyMtgMin.getSsrSnapshotSUWklyMtg().getOutlook());
        otherBusiness.setText(this.currentWeeklyMtgMin.getSsrSnapshotSUWklyMtg().getOther());
	}
	
	@Listen("onClick=#saveWeeklyBtn;")
	public void saveWeekly(){
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		WeeklyMtgMin weeklyMtgMin = new WeeklyMtgMin();	
		SSRSnapshotSUWklyMtg weeklySnapshot = new SSRSnapshotSUWklyMtg();	
		
		Date actDate = Calendar.getInstance().getTime();	
		int su_year = Calendar.getInstance().get(Calendar.YEAR);
		
		if(activityDate != null && activityDate.getValue() != null) 
		{ actDate = this.activityDate.getValue();}
		
		if(this.currentWeeklyMtgMin.getSsrSnapshotSUWklyMtg().getSuYear() > 0) 
		{ su_year = this.currentWeeklyMtgMin.getSsrSnapshotSUWklyMtg().getSuYear();}

		weeklyMtgMin.setActivityDate(dateFormat.format(actDate));
		
		WeeklyMtgMinGroupPace currentYearGroupPace = new WeeklyMtgMinGroupPace("CURRENT");
		WeeklyMtgMinGroupPace previousYearGroupPace = new WeeklyMtgMinGroupPace("PREVIOUS");
		WeeklyMtgMinGroupPace nextYearGroupPace = new WeeklyMtgMinGroupPace("NEXT");
		
		//Fill current GroupPace - Row 3
		this.fillGroupPace(currentYearGroupPace, 2);
		//Fill previous GroupPace - Row 6
		this.fillGroupPace(previousYearGroupPace, 5);
		//Fill next GroupPace - Row 9
		this.fillGroupPace(nextYearGroupPace, 8);
		
		weeklySnapshot.setAttendees(attendees.getText());
		weeklySnapshot.setCritique(critique.getText());
		weeklySnapshot.setOutlook(futureOutlook.getText());
		weeklySnapshot.setOther(otherBusiness.getText());
		weeklySnapshot.setTransotbEntered(this.currentWeeklyMtgMin.getSsrSnapshotSUWklyMtg().getTransotbEntered());
		weeklySnapshot.setSavedSnapshotid((Integer)ssrSnapshotsCombo.getSelectedItem().getValue());
		
		weeklyMtgMin.setSsrSnapshotSUWklyMtg(weeklySnapshot);	
		
		weeklyDAO.saveWeeklyMtgMin(weeklyMtgMin, weeklyMtgMin.getSsrSnapshotSUWklyMtg().getSavedSnapshotid(), currentYearGroupPace, previousYearGroupPace, nextYearGroupPace, su_year);
		
		Messagebox.show("Save Complete");
	}

	private void fillGroupPace(WeeklyMtgMinGroupPace groupPace, int rowIndex) {
		int col = 1;
		Rows rows = groupPaceGrid.getRows();
		Row row = (Row) rows.getChildren().get(rowIndex);
		groupPace.setJanOcc(new BigDecimal(((Intbox)row.getChildren().get(col++)).getValue()));
		groupPace.setFebOcc(new BigDecimal(((Intbox)row.getChildren().get(col++)).getValue()));
		groupPace.setMarOcc(new BigDecimal(((Intbox)row.getChildren().get(col++)).getValue()));
		groupPace.setAprOcc(new BigDecimal(((Intbox)row.getChildren().get(col++)).getValue()));
		groupPace.setMayOcc(new BigDecimal(((Intbox)row.getChildren().get(col++)).getValue()));
		groupPace.setJunOcc(new BigDecimal(((Intbox)row.getChildren().get(col++)).getValue()));
		groupPace.setJulOcc(new BigDecimal(((Intbox)row.getChildren().get(col++)).getValue()));
		groupPace.setAugOcc(new BigDecimal(((Intbox)row.getChildren().get(col++)).getValue()));
		groupPace.setSepOcc(new BigDecimal(((Intbox)row.getChildren().get(col++)).getValue()));
		groupPace.setOctOcc(new BigDecimal(((Intbox)row.getChildren().get(col++)).getValue()));
		groupPace.setNovOcc(new BigDecimal(((Intbox)row.getChildren().get(col++)).getValue()));
		groupPace.setDecOcc(new BigDecimal(((Intbox)row.getChildren().get(col++)).getValue()));
		
		System.out.println(groupPace);
		
	}

	
}
