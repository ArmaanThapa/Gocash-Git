package com.gocash.model;

public enum Gender {
	
	
	M("M"),F("F"),NA("NA");

	private String value;

	Gender(String value){
		this.value = value;
	}

	public String getValue(){
		return value;
	}



}
