package com.hrr3.model;

import java.sql.Connection;

import com.hrr3.entity.Hotel;

public class ProformaJasperDAO extends ProformaDAO {

	public ProformaJasperDAO(Hotel currentHotel) {
		super(currentHotel);
		// TODO Auto-generated constructor stub
	}
	
	//User can define his proper HRR3 connection
	public Connection getCurrentHRR3Connection () {
		
		//here default connection
		return null;
		
	}	

}
