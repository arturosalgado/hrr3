package com.hrr3.entity;


public enum RoleType {
    ADMIN(1), USER(2);
    private int value;

    private RoleType(int value) {
            this.value = value;
    }
    
    public int getValue() {
    	
    	return this.value;
    }
    
 
}; 
