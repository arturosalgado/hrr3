package com.hrr3.controller;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import com.hrr3.authentication.AuthenticationServiceHRR3Impl;
import com.hrr3.entity.Hotel;
import com.hrr3.entity.SegmentSetting;
import com.hrr3.model.CustomerDAO;
import com.hrr3.services.AuthenticationService;

public class ReportSettingsController extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;
	
	private ListModel<Hotel> hotelsModel;
	private ListModel<SegmentSetting> segmentsReportModel;
	private ListModel<SegmentSetting> segmentsTotalModel;
	
	@Wire
	private Listbox hotelsListBox;
	
	@Wire
	private Listbox segmentsReportListBox;
	
	@Wire
	private Listbox segmentsTotalListBox;
	
	@Wire
	private Button applyChangesButton;
	
	private  CustomerDAO customerDAO;
	AuthenticationService authService = new AuthenticationServiceHRR3Impl();
	
	
	public ReportSettingsController() {
		
		customerDAO = new CustomerDAO();
		List<Hotel> hotelList = authService.getUserData().getHotels();  
		hotelsModel = new ListModelList<Hotel>(hotelList);
    }
	
	public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        //segmentsReportListBox.setSelectedIndex(0);
        //segmentsTotalListBox.setSelectedIndex(0);
	}
	
	@Listen("onSelect =#hotelsListBox")
    public void populateSegmentList() {
    	
		List<SegmentSetting> segmentList = null;
		
		int hotelIdSelected = ((Hotel)hotelsListBox.getSelectedItem().getValue()).getHotelId();		
		segmentList = this.customerDAO.getSegmentsSettings(authService.getUserData().getCurrentCustomer().getCustomerId(), hotelIdSelected);
		
		segmentsReportModel = new ListModelList<SegmentSetting>(segmentList);
		((ListModelList<SegmentSetting>)segmentsReportModel).setMultiple(true);
		segmentsReportListBox.setModel(segmentsReportModel);
		
		
		segmentsTotalModel = new ListModelList<SegmentSetting>(segmentList);
		((ListModelList<SegmentSetting>)segmentsTotalModel).setMultiple(true);
		segmentsTotalListBox.setModel(segmentsTotalModel);
		
		applyChangesButton.setDisabled(false);
	}    
	
	@Listen("onClick =#applyChangesButton")
    public void applyChanges() {
		
		List<SegmentSetting> segmentsToInclude = new ArrayList<SegmentSetting>();
		
		List<Listitem> reportItems = segmentsReportListBox.getItems();	
		List<Listitem> totalItems = segmentsTotalListBox.getItems();
		
		for(int i=0; i< reportItems.size(); i++)
		{	
			Listitem reportItem = reportItems.get(i);
			Listitem totalItem = totalItems.get(i);
			
			if(reportItem.isSelected() && !totalItem.isSelected()){		
				SegmentSetting segmentToInclude = (SegmentSetting)reportItem.getValue();
				segmentToInclude.setIncludeInReport(true);
				segmentToInclude.setIncludeInTotal(false);
				segmentsToInclude.add(segmentToInclude);				
			}
			else if(!reportItem.isSelected() && totalItem.isSelected()){					
				SegmentSetting segmentToInclude = (SegmentSetting)totalItem.getValue();
				segmentToInclude.setIncludeInReport(false);
				segmentToInclude.setIncludeInTotal(true);
				segmentsToInclude.add(segmentToInclude);					
			}
			
			else if(!reportItem.isSelected() && !totalItem.isSelected()){					
				SegmentSetting segmentToInclude = (SegmentSetting)totalItem.getValue();
				segmentToInclude.setIncludeInReport(false);
				segmentToInclude.setIncludeInTotal(false);
				segmentsToInclude.add(segmentToInclude);					
			}				
				
		}
		
		System.out.println("Segments to include: " + segmentsToInclude.size());
		int hotelIdSelected = ((Hotel)hotelsListBox.getSelectedItem().getValue()).getHotelId();
		this.customerDAO.updateSegmentSettings(segmentsToInclude, hotelIdSelected, null);
		Messagebox.show("Changes applied.");
		
	}

	/**
	 * @return the hotelsModel
	 */
	public ListModel<Hotel> getHotelsModel() {
		return hotelsModel;
	}

	/**
	 * @return the segmentsModel
	 */
	public ListModel<SegmentSetting> getSegmentsReportModel() {
		return segmentsReportModel;
	}
	
	/**
	 * @return the segmentsModel
	 */
	public ListModel<SegmentSetting> getSegmentsTotalModel() {
		return segmentsTotalModel;
	}
	
}
