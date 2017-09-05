package com.gocash.validation;

import java.util.Random;

public class CommonValidation {
	
	 
	private static String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
	
	
	
	public static boolean isValidMobileNumber(String number) {
		  boolean isValid = false;
		  if (number.matches("^(?=(?:[7-9]){1})(?=[0-9]{10}).*")) {
		   System.out.println("Valid phone number!");
		   isValid = true;
		  } else {
		   
		   isValid = false;
		  }
		  return isValid;
		 }
	
	
	
	public static boolean isNumeric(String str) {
		String temp = str.trim();
		boolean isNumber = false;
		if (temp.matches("[0-9]+")) {
			isNumber = true;
		}
		return isNumber;
	}
	
	
	
	/**
	 * checks the length of string is equal to 10
	 * 
	 * @param str
	 * @return
	 */
	public static boolean checkLength10(String str) {
		String temp = str.trim();
		int length = temp.length();
		System.err.println(length);
		boolean isValid = false;
		if (length == 10) {
			isValid = true;
		}
		return isValid;
	}


	
	
	
	
	
	
	
	public static boolean isValidMail(String str) {
		boolean isValid = false;
		if (str.contains("@") && str.contains(".")) {
			System.err.println("email  "+str);
			isValid = true;
		}
		return isValid;
	}
	
	
	
	
	public static boolean isNull(String str) {
		if (str == null || str.isEmpty() || str == "") {
			return true;
		} else {
			return false;
		}
	}
	
	
	public static boolean emailValid(String str)
	{
		if(str.matches(pattern))
		{
			System.out.println("email is  "+str);
			return true;
			
		}
		else
		{
			return false;
		}
	}
	
	
	

}
