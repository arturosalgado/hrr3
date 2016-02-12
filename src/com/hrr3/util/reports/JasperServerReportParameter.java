package com.hrr3.util.reports;

public class JasperServerReportParameter {
	
	private String name;
	private Object value;
	
	public JasperServerReportParameter(String name, Object value) {
		super();
		this.name = name;
		this.value = value;
	}	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	

}
