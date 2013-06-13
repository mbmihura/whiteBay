package com.tacs13G6.models;

public class Torrent {
	String title;
    String description;
    String link;
    String guid;
    
    /**
     * @return the title
     */
    public String getTitle() {
            return title;
    }
    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
            this.title = title;
    }

    /**
     * @return the description
     */
    public String getDescription() {
            return description;
    }
    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
            this.description = description;
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
    public void setLink(String link) {
            this.link = link;
    }
    
    /**
     * @return the guid
     */
    public String getGuid() {
            return guid;
    }
    /**
     * @param guid the guid to set
     */
    public void setGuid(String guid) {
            this.guid = guid;
    }
    
    @Override
    public String toString() {
            return "FeedMessage [title=" + title + ", description=" + description
                    + ", link=" + link + ", guid=" + guid
                    + "]";
    }
    
}
