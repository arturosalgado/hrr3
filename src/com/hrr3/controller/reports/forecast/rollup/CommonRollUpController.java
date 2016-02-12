package com.hrr3.controller.reports.forecast.rollup;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

import com.hrr3.authentication.AuthenticationServiceHRR3Impl;
import com.hrr3.entity.Brand;
import com.hrr3.entity.Hotel;
import com.hrr3.entity.Region;
import com.hrr3.entity.User;
import com.hrr3.model.BrandDAO;
import com.hrr3.model.HotelDAO;
import com.hrr3.model.RegionDAO;
import com.hrr3.model.UserDAO;
import com.hrr3.services.AuthenticationService;

public class CommonRollUpController extends SelectorComposer<Component>  {
	private static final long serialVersionUID = 1L;
	
	
	ListModel<Hotel> hotelsModel;	
	ListModel<Brand> brandsModel;
	ListModel<Region> regionsModel;
	//User list models
	ListModel<User> adrmUsersModel;
	ListModel<User> rvpSalesUsersModel;
	ListModel<User> rvpOpsUsersModel;
	ListModel<User> rdrmUsersModel;
	
	AuthenticationService authService;
		
	@Wire
	Combobox adrmUsersCombo;
	
	@Wire
	Combobox rvpSalesUsersCombo;
	
	@Wire
	Combobox rvpOpsUsersCombo;
	
	@Wire
	Combobox rdrmUsersCombo;
	
	@Wire
	Combobox hotelBrandCombo;
	
	@Wire
	Combobox hotelRegionCombo;
	
	@Wire
    Window popupDialog;
	
	@Wire
	Listbox hotelListBox;
	
	@Wire
	Button printReportBtn;
	
	public CommonRollUpController() {
		
		authService = new AuthenticationServiceHRR3Impl();
				
		List<Hotel> hotelList = authService.getUserData().getHotels();
        hotelsModel = new ListModelList<Hotel>(hotelList);
        ((ListModelList<Hotel>)hotelsModel).setMultiple(true);
        
        //Brand
        List<Brand> brandList = new BrandDAO().getBrands("SELECT_ELEMENT");
        brandsModel = new ListModelList<Brand>(brandList);
        
        //Region
        List<Region> regionList = new RegionDAO().getRegions(authService.getUserData().getCurrentCustomer().getCustomerId(), "SELECT_ELEMENT");
        regionsModel = new ListModelList<Region>(regionList);
        
        //User lists
        UserDAO userDAO = new UserDAO();
        //ADRM user = 2
        List<User> ul1 = userDAO.getUsersByTitle(2, authService.getUserData().getCurrentCustomer().getCustomerId(), authService.getUserData().getUserId());
        adrmUsersModel = new ListModelList<User>(ul1);
        //RVPSales user = 5
        List<User> ul2 = userDAO.getUsersByTitle(5, authService.getUserData().getCurrentCustomer().getCustomerId(), authService.getUserData().getUserId());
        rvpSalesUsersModel = new ListModelList<User>(ul2);
        //RVPOp user = 6
        List<User> ul3 = userDAO.getUsersByTitle(6, authService.getUserData().getCurrentCustomer().getCustomerId(), authService.getUserData().getUserId());
        rvpOpsUsersModel = new ListModelList<User>(ul3);
        //RDRM user = 1
        List<User> ul4 = userDAO.getUsersByTitle(1, authService.getUserData().getCurrentCustomer().getCustomerId(), authService.getUserData().getUserId());
        rdrmUsersModel = new ListModelList<User>(ul4);

    }
	

    @Listen("onClick = #exitReportBtn")
    public void showModal(Event e) {
    	popupDialog.detach();
    }    

    
    @Listen("onSelect =#hotelBrandCombo; onSelect =#hotelRegionCombo; onSelect =#adrmUsersCombo; onSelect =#rvpSalesUsersCombo; onSelect =#rvpOpsUsersCombo; onSelect =#rdrmUsersCombo;")   
    public void filterHotelsList() {
    	
    	List<Hotel> hotelList = authService.getUserData().getHotels();
    	    	
    	//If Brand is selected
    	if(this.hotelBrandCombo.getSelectedIndex() > 0) {
    		
    		hotelList = this.filterHotelsByBrand(hotelList, (Integer)this.hotelBrandCombo.getSelectedItem().getValue()) ;
    	}
    	
    	//If Region is selected
    	if(this.hotelRegionCombo.getSelectedIndex() > 0 ) {
    		
    		hotelList = this.filterHotelsByRegion(hotelList, (Integer)this.hotelRegionCombo.getSelectedItem().getValue());
    	}
    	
    	//If User ADRM is selected
    	if(this.adrmUsersCombo.getSelectedIndex() > 0 ) {
    		
    		hotelList = this.filterHotelsByUser(hotelList, (Integer)this.adrmUsersCombo.getSelectedItem().getValue(), authService.getUserData().getCurrentCustomer().getCustomerId());
    	}
    	
    	//If User RVPSales is selected
    	if(this.rvpSalesUsersCombo.getSelectedIndex() > 0 ) {
    		
    		hotelList = this.filterHotelsByUser(hotelList, (Integer)this.rvpSalesUsersCombo.getSelectedItem().getValue(), authService.getUserData().getCurrentCustomer().getCustomerId());
    	}
    	
    	//If User RVPOperations is selected
    	if(this.rvpOpsUsersCombo.getSelectedIndex() > 0 ) {
    		
    		hotelList = this.filterHotelsByUser(hotelList, (Integer)this.rvpOpsUsersCombo.getSelectedItem().getValue(), authService.getUserData().getCurrentCustomer().getCustomerId());
    	}
    	
    	//If USer RDRM is selected
    	if(this.rdrmUsersCombo.getSelectedIndex() > 0 ) {
    		
    		hotelList = this.filterHotelsByUser(hotelList, (Integer)this.rdrmUsersCombo.getSelectedItem().getValue(), authService.getUserData().getCurrentCustomer().getCustomerId());
    	}
    	
    	//After all filtering, refresh the Model.
    	hotelsModel = new ListModelList<Hotel>(hotelList);    	
    	this.hotelListBox.setModel(hotelsModel);
        ((ListModelList<Hotel>)hotelsModel).setMultiple(true);
    	
    }
    
    
    private List<Hotel> filterHotelsByBrand(List<Hotel> hotelList, Integer brandId) {
    	
    	List<Hotel> filteredHotels = new ArrayList<Hotel>();
    	
    	for(int i=0; i<hotelList.size(); i++) {
    		
    		if(hotelList.get(i).getBrand().intValue() == brandId.intValue())
    			filteredHotels.add(hotelList.get(i));
    	}
    	
    	return filteredHotels;
    	
    }
    
    private List<Hotel> filterHotelsByRegion(List<Hotel> hotelList, Integer regionId) {
    	
    	List<Hotel> filteredHotels = new ArrayList<Hotel>();
    	
    	for(int i=0; i<hotelList.size(); i++) {
    		
    		if(hotelList.get(i).getRegion().intValue() == regionId.intValue())
    			filteredHotels.add(hotelList.get(i));
    	}
    	
    	return filteredHotels;
    	
    }
    
    private List<Hotel> filterHotelsByUser(List<Hotel> hotelList, Integer userId, Integer customerId) {
    	
    	HotelDAO hotelDAO = new HotelDAO();
    	List<Hotel> filteredHotels = new ArrayList<Hotel>();    	
    	List<Hotel> userHotels = hotelDAO.findUserHotels(userId, customerId);
    	
    	for(int i=0; i<hotelList.size(); i++) {
    		
    		//Query if the Hotel is related to this userId/customerId
    		if(userHotels.contains(hotelList.get(i)))
    			filteredHotels.add(hotelList.get(i));
    	}
    	
    	return filteredHotels;
    	
    }
    
    
    
    public void initializeFormControls(int type) {

    }
   

	/**
	 * @return the hotelsModel
	 */
	public ListModel<Hotel> getHotelsModel() {
		return hotelsModel;
	}
	
	@Listen("onAfterRender=#hotelListBox;")
	public void hotelListAfterRender(){
		
		//this.hotelListBox.selectAll();
	}


	/**
	 * @return the brandsModel
	 */
	public ListModel<Brand> getBrandsModel() {
		return brandsModel;
	}


	/**
	 * @return the regionsModel
	 */
	public ListModel<Region> getRegionsModel() {
		return regionsModel;
	}


	/**
	 * @return the rvpSalesUsersModel
	 */
	public ListModel<User> getRvpSalesUsersModel() {
		return rvpSalesUsersModel;
	}


	/**
	 * @return the rvpOpsUsersModel
	 */
	public ListModel<User> getRvpOpsUsersModel() {
		return rvpOpsUsersModel;
	}


	/**
	 * @return the rdrmUsersModel
	 */
	public ListModel<User> getRdrmUsersModel() {
		return rdrmUsersModel;
	}


	/**
	 * @return the adrmUsersModel
	 */
	public ListModel<User> getAdrmUsersModel() {
		return adrmUsersModel;
	}


}
