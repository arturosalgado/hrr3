package com.hrr3.entity.hsp;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "Days")
public class Days {
		
	
	private List<Day> dayList  = new ArrayList<Day>();
	
	public Days() {}
	
	public Days(List<Day> dayList) {
		
		this.dayList = dayList;
		
	}
	
	public List<Day> getDayList () {
		
		return this.dayList;
	}
	
	@XmlElement(name = "Day", type = Day.class)
	public void setDayList (List<Day> dayList) {
		
		this.dayList = dayList;
	}

}
