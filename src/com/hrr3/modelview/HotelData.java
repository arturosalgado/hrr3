package com.hrr3.modelview;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.hrr3.entity.RM2Hotel;
import com.hrr3.model.RM2MigrationDAO;


public class HotelData {
	
	List<RM2Hotel> hotels =new RM2MigrationDAO().getRM2Hotels();

	public List<RM2Hotel> getAllHotels() {
		return hotels;
		
	}

	// This Method only used in "Data Filter"
	public List<RM2Hotel> getFilterHotels(HotelFilter hotelFilter) {
		
		List<RM2Hotel> filteredHotels = new ArrayList<RM2Hotel>();
		String brand = hotelFilter.getBrand().toLowerCase();
		String hotelId = hotelFilter.getHotelId().toLowerCase();
		String name = hotelFilter.getName().toLowerCase();
		String state = hotelFilter.getState().toLowerCase();
		String city = hotelFilter.getCity().toLowerCase();
		
		System.out.println("-brand:" + brand);
		System.out.println("-hotelId:" + hotelId);
		System.out.println("-name:" + name);
		System.out.println("-state:" + state);
		System.out.println("-city:" + city);
		
		for (Iterator<RM2Hotel> i = hotels.iterator(); i.hasNext();) {
			RM2Hotel tmp = i.next();
			/*System.out.println("+brand:" + tmp.getBrand());
			System.out.println("+hotelId:" + tmp.getHotelIdString());
			System.out.println("+name:" + tmp.getName());
			System.out.println("+state:" + tmp.getState());
			System.out.println("+city:" + tmp.getCity());*/
			if (tmp.getBrand().toLowerCase().contains(brand) &&
				tmp.getHotelIdString().toLowerCase().contains(hotelId) &&
				tmp.getName().toLowerCase().contains(name)  &&
				tmp.getState().toLowerCase().contains(state) &&
				tmp.getCity().toLowerCase().contains(city) ) {
				filteredHotels.add(tmp);
			}
		}
		return filteredHotels;
	}

	// This Method only used in "Header and footer"
	public List<RM2Hotel> getHotelsByBrand(String brand) {
		
		List<RM2Hotel> hotelsByBrand = new ArrayList<RM2Hotel>();
		for (Iterator<RM2Hotel> i = hotels.iterator(); i.hasNext();) {
			RM2Hotel tmp = i.next();
			if (tmp.getBrand().equalsIgnoreCase(brand)){
				hotelsByBrand.add(tmp);
			}
		}
		return hotelsByBrand;
	}
}
