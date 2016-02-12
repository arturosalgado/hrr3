package com.hrr3.controller;

import java.util.HashMap;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.hrr3.entity.Customer;
import com.hrr3.entity.RM2Hotel;
import com.hrr3.model.HotelDAO;

public class SSRMigrationHotelListController extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;
	
	@Wire
	Listbox migrationHotelList;
	
	@Listen("onSelect =#migrationHotelList")
	public void showModal() {
		
		 final HashMap<String, Object> map = new HashMap<String, Object>();
		
		System.out.println("*********** Opening Modal Page **************");
		ListModel<RM2Hotel> hotelListModel = migrationHotelList.getListModel();		
		Set<RM2Hotel> selectedHotels = ((ListModelList<RM2Hotel>)hotelListModel).getSelection();
		RM2Hotel selectedHotel = selectedHotels.iterator().next();
		System.out.println("*********** Selected hotel: " + selectedHotel.getName() + " **************");
		//create a window programmatically and use it as a modal dialog.		 
        Integer hotelId = selectedHotel.getHotelId();
        String hotelName = selectedHotel.getName();
        Customer customer = new HotelDAO().getCustomer(hotelId);
        //If the hotel is mapped to a customer...build and show the pop up
        if(customer != null && customer.getCustomerId() != null) {
        	Integer customerId = customer.getCustomerId();
            map.put("hotelId", hotelId);
            map.put("hotelName", hotelName);
            map.put("customerId", customerId);
     		Window window = (Window)Executions.createComponents("/application/admin/ssr-migration/components/snapshots-popup.zul", null, map);		
     		window.doModal();
     		//Deselect all items to allow selecting over same hotel item if required.
     		migrationHotelList.selectItem(null);
     		Clients.showNotification("Don't close this window while migration occurs!","warning",window,"top_center",2500);
     		
        }
        
        else
        	Messagebox.show("Customer not found for this Hotel in RM3 System.", "Customer Error", Messagebox.OK, Messagebox.ERROR);		
        
		
		}
}