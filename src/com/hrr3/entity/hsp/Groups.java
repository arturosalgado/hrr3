package com.hrr3.entity.hsp;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "Groups")
public class Groups {
		
	
	private List<Group> groups  = new ArrayList<Group>();
	
	public Groups() {}
	
	public Groups(List<Group> groups) {
		
		this.groups = groups;
		
	}
	
	public List<Group> getGroups () {
		
		return this.groups;
	}
	
	@XmlElement(name = "Group", type = Group.class)
	public void setGroups (List<Group> groups) {
		
		this.groups = groups;
	}

}
