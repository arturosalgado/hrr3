package com.hrr3.modelview;

import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;

import com.hrr3.entity.RM2Hotel;


public class FilterViewModel {

	private static final String footerMessage = "A Total of %d Hotel Items";
    private HotelFilter hotelFilter = new HotelFilter();    
    private HotelData hotelData = new HotelData();
    List<RM2Hotel> currentHotels = hotelData.getAllHotels();

    public HotelFilter getHotelFilter() {
        return hotelFilter;
    }

	public ListModel<RM2Hotel> getHotelModel() {		
		return new ListModelList<RM2Hotel>(currentHotels);
	}
	
	public String getFooter() {
		return String.format(footerMessage, currentHotels.size());
	}

    @Command
    @NotifyChange({"hotelModel", "footer"})
	public void changeFilter() {
    	currentHotels = hotelData.getFilterHotels(hotelFilter);
	}
}
