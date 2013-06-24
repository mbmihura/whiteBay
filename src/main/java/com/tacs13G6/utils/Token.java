package com.tacs13G6.utils;

import java.util.Random;

public class Token {

	public static String generate() {
		String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random rnd = new Random();

        StringBuilder sb = new StringBuilder(10);
		for( int i = 0; i < 10; i++ ) 
		      sb.append( AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}

}
