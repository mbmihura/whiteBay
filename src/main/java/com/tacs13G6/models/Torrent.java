package com.tacs13G6.models;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.tacs13G6.models.exceptions.TorrentMalformedException;

public class Torrent {
	 String title;
     String description;
     String link;
    
    /**
     * Crea un nuevo objeto torrent.
     * 
     * @param title Titulo del torrent.
     * @param description Descripcion del torrent.
     * @param link Url del torrent.
     * 
     * @exception TorrentMalformedException Si tanto el titulo como la descripcion del torrent nulos o vacios.
     */
    public Torrent(String title, String description, String link) throws TorrentMalformedException
    {
    	if (title == null || title.isEmpty())
    		throw new TorrentMalformedException("title requiered");
    	//Pattern p = Pattern.compile("^[A-Za-z0-9_]+$");
    	//if (p.matcher(title).find() || p.matcher("").find() || p.matcher("").find())
    	//	throw new TorrentMalformedException("Torrent data can not contain especial characters");
    	this.title = title;
    	this.description = description;
    	this.link = link;
    }
    
    public Torrent()
    {
    }
    /**
     * @return the title
     */
    public String getTitle() {
            return title;
    }

    /**
     * @return the description
     */
    public String getDescription() {
            return description;
    }
    
    /**
     * @return the link
     */
    public String getLink() {
            return link;
    }
    /**
     * @param link the link to set
     */
        
    @Override
    public String toString() {
            return title + "@" + description + "@" + link;
    }

	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}
