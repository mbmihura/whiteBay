package com.tacs13G6.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Feed {

          final String title;
          final String link;
          final String description;
          final String language;
          final Date pubDate;

          final List<Torrent> torrents = new ArrayList<Torrent>();

          public Feed(String title, String link, String description, String language, Date publicationDate) {
            this.title = title;
            this.link = link;
            this.description = description;
            this.language = language;
            this.pubDate = publicationDate;
          }

          public List<Torrent> getTorrents() {
            return torrents;
          }

          public String getTitle() {
            return title;
          }

          public String getLink() {
            return link;
          }

          public String getDescription() {
            return description;
          }

          public String getLanguage() {
            return language;
          }

          public Date getPubDate() {
            return pubDate;
          }

          @Override
          public String toString() {
            return "Feed [description=" + description
                + ", language=" + language + ", link=" + link + ", pubDate="
                + pubDate + ", title=" + title + "]";
          }

        } 
