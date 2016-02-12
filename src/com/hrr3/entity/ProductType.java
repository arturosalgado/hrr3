package com.hrr3.entity;


public enum ProductType {
    RM3(1), EPVC(2);
    private int value;

    private ProductType(int value) {
            this.value = value;
    }
    
    public int getValue() {
    	
    	return this.value;
    }
}; 
