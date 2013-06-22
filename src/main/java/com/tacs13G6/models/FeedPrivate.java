package com.tacs13G6.models;

import java.util.Date;
import java.util.regex.Pattern;

import com.tacs13G6.models.exceptions.FeedMalformedException;

public class FeedPrivate extends Feed {

	String rssToken;
	
	private FeedPrivate(String title, String link, String description,
			Date publicationDate, String userId, String eToken) throws FeedMalformedException {
		super(title, link, description, publicationDate, userId);
		
		this.rssToken = (Math.random() * 1000000);
	}
	
	public String getToken() {
		return this.rssToken;
	}
	
	public boolean isTokenValid(String token) {
		return token.equals(this.title + this.userId + );
	}

}
