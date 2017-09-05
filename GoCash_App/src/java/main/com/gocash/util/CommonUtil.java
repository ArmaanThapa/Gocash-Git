package com.gocash.util;

import java.util.Random;

public class CommonUtil 
{
	
	public static String generateSixRandomNumericString()
	{
		System.err.println("generate the 6 digit opt fro user");
		Random rnd = new Random();
		int n = 100000 + rnd.nextInt(900000);
		return "" + n;
	}

}
