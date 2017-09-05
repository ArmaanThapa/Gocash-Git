package com.gocash.model.response;



public enum ResponseStatus {
	
	


	SUCCESS("Success","S00"),
	
	FAILURE("Failure","F00"),

	INTERNAL_SERVER_ERROR("Internal Server Error","F02"),

	INVALID_SESSION("Session Invalid","F03"),

	BAD_REQUEST("Bad Request","F04"),
	
	UNAUTHORIZED_USER("Un-Authorized User","F05"),
	
	UNAUTHORIZED_ROLE("Un-Authorized Role","F06"),
	
	INVALID_HASH("Invalid hash","F07"),

	INVALID_VERSION("Invalid Version","F08"),

	INVALID_IP("Invalid IP","F09"),

	NEW_DEVICE("New Device","L01");

	private ResponseStatus() {

	}
	
	private String key;

	private String value;

	private ResponseStatus(String key,String value) {
		this.key=key;
		this.value = value;
	}
	
	public String getKey(){
		return key;
	}
	public String getValue() {
		return value;
	}

	public static ResponseStatus getEnumByValue(String value) {
		if (value == null)
			throw new IllegalArgumentException();
		for (ResponseStatus v : values())
			if (value.equalsIgnoreCase(v.getValue()))
				return v;
		throw new IllegalArgumentException();
	}
	
	public static ResponseStatus getEnumByKey(String key) {
		if (key == null)
			throw new IllegalArgumentException();
		for (ResponseStatus v : values())
			if (key.equalsIgnoreCase(v.getKey()))
				return v;
		throw new IllegalArgumentException();
	}



}
