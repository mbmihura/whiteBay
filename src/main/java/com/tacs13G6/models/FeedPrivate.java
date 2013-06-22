package com.tacs13G6.models;

import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;

import com.tacs13G6.models.exceptions.FeedMalformedException;

public class FeedPrivate extends Feed {

	String rssToken;
	
	private FeedPrivate(String title, String link, String description,
			Date publicationDate, String userId, String eToken) throws FeedMalformedException {
		super(title, link, description, publicationDate, userId);
				
		String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random rnd = new Random();

        StringBuilder sb = new StringBuilder(10);
		for( int i = 0; i < 10; i++ ) 
		      sb.append( AB.charAt(rnd.nextInt(AB.length())));
		rssToken = sb.toString();		
	}
	
	public String getToken() {
		return this.rssToken;
	}
	
	public boolean isTokenValid(String token) {
		return this.rssToken.equals(token);
	}

}
