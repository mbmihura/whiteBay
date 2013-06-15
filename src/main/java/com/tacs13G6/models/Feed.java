package com.tacs13G6.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// XML lib:
import java.io.StringWriter;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.stream.StreamResult;

import com.tacs13G6.models.exceptions.FeedMalformedException;
import com.tacs13G6.models.exceptions.TorrentMalformedException;


public class Feed {

          final String title;
          final String link;
          final String description;
          final Date pubDate;

          final List<Torrent> torrents = new ArrayList<Torrent>();

          public Feed(String title, String link, String description, Date publicationDate) throws FeedMalformedException {
        	  if (title == null || title.isEmpty() || description == null || description.isEmpty() || link == null || link.isEmpty())
          		throw new FeedMalformedException("At least one of title or description is requiered");
            this.title = title;
            this.link = link;
            this.description = description;
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


          public Date getPubDate() {
            return pubDate;
          }

          @Override
          public String toString() {
            return "Feed [description=" + description
                +  ", link=" + link + ", pubDate="
                + pubDate + ", title=" + title + "]";
          }
          
          public String toXML() throws Exception {
		    // Create a XMLOutputFactory
		    XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
	
		    StringWriter writer = new StringWriter();
		    
		    // Create XMLEventWriter
		    XMLEventWriter eventWriter = outputFactory
		        .createXMLEventWriter(new StreamResult(writer));
	
		    
		    // Create a EventFactory
	
		    XMLEventFactory eventFactory = XMLEventFactory.newInstance();
		    XMLEvent end = eventFactory.createDTD("\n");
	
		    // Create and write Start Tag
	
		    StartDocument startDocument = eventFactory.createStartDocument();
	
		    eventWriter.add(startDocument);
	
		    // Create open tag
		    eventWriter.add(end);
	
		    StartElement rssStart = eventFactory.createStartElement("", "", "rss");
		    eventWriter.add(rssStart);
		    eventWriter.add(eventFactory.createAttribute("version", "2.0"));
		    eventWriter.add(end);
	
		    eventWriter.add(eventFactory.createStartElement("", "", "channel"));
		    eventWriter.add(end);
	
		    // Write the different nodes
	
		    createNode(eventWriter, "title", this.getTitle());
	
		    createNode(eventWriter, "link", this.getLink());
	
		    createNode(eventWriter, "description", this.getDescription());
		
		    createNode(eventWriter, "pubdate", this.getPubDate().toString());
	
		    for (Torrent entry : this.getTorrents()) {
		      eventWriter.add(eventFactory.createStartElement("", "", "item"));
		      eventWriter.add(end);
		      createNode(eventWriter, "title", entry.getTitle());
		      //createNode(eventWriter, "description", entry.getDescription());
		      createNode(eventWriter, "link", entry.getLink());
		      //createNode(eventWriter, "guid", entry.getGuid());
		      eventWriter.add(end);
		      eventWriter.add(eventFactory.createEndElement("", "", "item"));
		      eventWriter.add(end);
	
		    }
	
		    eventWriter.add(end);
		    eventWriter.add(eventFactory.createEndElement("", "", "channel"));
		    eventWriter.add(end);
		    eventWriter.add(eventFactory.createEndElement("", "", "rss"));
	
		    eventWriter.add(end);
	
		    eventWriter.add(eventFactory.createEndDocument());
	
		    eventWriter.close();
		    
		    return writer.toString();
		  }
	
		  private void createNode(XMLEventWriter eventWriter, String name,  String value) throws XMLStreamException {
		    XMLEventFactory eventFactory = XMLEventFactory.newInstance();
		    XMLEvent end = eventFactory.createDTD("\n");
		    XMLEvent tab = eventFactory.createDTD("\t");
		    // Create Start node
		    StartElement sElement = eventFactory.createStartElement("", "", name);
		    eventWriter.add(tab);
		    eventWriter.add(sElement);
		    // Create Content
		    Characters characters = eventFactory.createCharacters(value);
		    eventWriter.add(characters);
		    // Create End node
		    EndElement eElement = eventFactory.createEndElement("", "", name);
		    eventWriter.add(eElement);
		    eventWriter.add(end);
		  }

        } 
