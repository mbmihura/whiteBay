package com.tacs13G6.models;

import com.tacs13G6.models.exceptions.TorrentMalformedException;

public class Torrent {
	final String title;
    final String description;
    final String link;
    
    /**
     * Crea un nuevo objeto torrent.
     * 
     * @param title Titulo del torrent.
     * @param description Descripcion del torrent.
     * @param link Url del torrent.
     * 
     * @exception TorrentMalformedException Si tanto el titulo como la descripcion del torrent nulos o vacios.
     */
    public Torrent(String title, String description, String link)
    {
    	if ((title == null || title.isEmpty()) && (description == null || description.isEmpty()))
    		throw new TorrentMalformedException("At least one of title or description is requiered");
    	this.title = title;
    	this.description = description;
    	this.link = link;
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
            return "FeedMessage [title=" + title + ", description=" + description
                    + ", link=" + link + "]";
    }
    
}
