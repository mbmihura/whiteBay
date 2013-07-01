package com.tacs13G6.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

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

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tacs13G6.models.exceptions.FeedMalformedException;
import com.tacs13G6.servlets.RssServlet;
import com.tacs13G6.utils.Token;

public class Feed {
	public static List<Feed> find(String userId) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Key userKey = KeyFactory.createKey("UserA1", userId);
		Query mediaQuery = new Query().setAncestor(userKey).addFilter(
				Entity.KEY_RESERVED_PROPERTY,
				Query.FilterOperator.GREATER_THAN, userKey);

		List<Entity> results = datastore.prepare(mediaQuery).asList(FetchOptions.Builder.withDefaults());
		List<Feed> userFeeds = new ArrayList<Feed>();
		for (Entity feedEntity : results) {
			String eTitle = (String) feedEntity.getProperty("title");
			String eLink = (String) feedEntity.getProperty("link");
			String eDescription = (String) feedEntity.getProperty("description");
			Date ePubDate = (Date) feedEntity.getProperty("pubDate");
			String eUSerId = (String) feedEntity.getProperty("userId");
			String eToken = (String) feedEntity.getProperty("token");
			try {
				Feed feed;
				//if (eToken != null)
					feed = new Feed(eTitle, eLink, eDescription, ePubDate, userId, eToken);
				//else
					//feed = new FeedPrivate(eTitle, eLink, eDescription, ePubDate, userId, eToken);
				Gson gson = new Gson();
				List<String> list = (List<String>) feedEntity.getProperty("torrents");
				// Necesario, ya que aunque se carga durante el metodo save(),
				// si esta vacio datastore almacena nulo en ves de una lista
				// vacia.
				if (list != null) {
					for (String t : list) {
						java.lang.reflect.Type type = new TypeToken<Torrent>() {}.getType();
						Torrent torrent = gson.fromJson(t, type);

						feed.torrents.add(torrent);
					}
				}
				userFeeds.add(feed);
			} catch (FeedMalformedException e) {
				e.printStackTrace();
			}
		}
		return userFeeds;
	}

	public static Feed find(String title, String userId)
			throws EntityNotFoundException, FeedMalformedException {
		// Generate key
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Key feedKey = new KeyFactory.Builder("UserA1", userId).addChild(
				"FeedA1", title).getKey();

		// Get Entity
		Entity feedEntity;

		feedEntity = datastore.get(feedKey);

		String eLink = (String) feedEntity.getProperty("link");
		String eDescription = (String) feedEntity.getProperty("description");
		Date ePubDate = (Date) feedEntity.getProperty("pubDate");
		String eToken = (String) feedEntity.getProperty("token");

		try {
			Feed feed = new Feed(title, eLink, eDescription, ePubDate, userId, eToken);
			Gson gson = new Gson();
			List<String> list = (List<String>) feedEntity.getProperty("torrents");
			// Necesario, ya que aunque se carga durante el metodo save(), si
			// esta vacio datastore almacena nulo en ves de una lista vacia.
			if (list != null) {
				for (String t : list) {
					java.lang.reflect.Type type = new TypeToken<Torrent>() {
					}.getType();
					Torrent torrent = gson.fromJson(t, type);

					feed.torrents.add(torrent);
				}
			}
			return feed;
		} catch (FeedMalformedException e) {
			e.printStackTrace();
			throw e;
		}

	}

	public static Feed createFeed(String title, String link, String description, String userId) throws FeedMalformedException {
		return new Feed(title, link, description, new Date(), userId, null);
	}

	public static Feed createPrivateFeed(String title, String link, String description, String userId) throws FeedMalformedException {	
		return new Feed(title, link, description, new Date(), userId, Token.generate());
	}
	
	final String userId;
	final String title;
	final String link;
	final String description;
	final Date pubDate;
	final String token;
	final List<Torrent> torrents = new ArrayList<Torrent>();
	final String rssUrl;

	protected Feed(String title, String link, String description,Date publicationDate, String userId, String token) throws FeedMalformedException {
		if (title == null || title.isEmpty() || description == null	|| description.isEmpty() || link == null || link.isEmpty())
			throw new FeedMalformedException("Neither title, link and description may be empty.");
		
		Pattern p = Pattern.compile("^[A-Za-z0-9_]+$");
		if (!p.matcher(title).find())
			throw new FeedMalformedException("Title should be alphanumeric.");
		this.title = title;
		this.link = link;
		this.description = description;
		this.pubDate = publicationDate;
		this.userId = userId;
		this.token = token;
		this.rssUrl = RssServlet.getRssUrlFor(this);
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

	public List<Torrent> getTorrents() {
		return torrents;
	}

	public void save() {
		// Create Key
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Key key = new KeyFactory.Builder("UserA1", this.userId).addChild(
				"FeedA1", this.title).getKey();
		// SAVE: create and put entity
		Entity ent = new Entity(key);
		ent.setProperty("title", this.title);
		ent.setProperty("userId", this.userId);
		ent.setProperty("link", this.link);
		ent.setProperty("description", this.description);
		ent.setProperty("pubDate", this.pubDate);
		ent.setProperty("token", this.token);
		List<String> list = new ArrayList<String>();
		for (Torrent t : this.torrents) {
			list.add(t.toJson());
		}
		ent.setProperty("torrents", list);

		datastore.put(ent);
	}

	public String toXML() throws XMLStreamException {
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
			createNode(eventWriter, "description", entry.getDescription());
			createNode(eventWriter, "link", entry.getLink());
			// createNode(eventWriter, "guid", entry.getGuid());
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

	private void createNode(XMLEventWriter eventWriter, String name,
			String value) throws XMLStreamException {
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

	public String toString() {
		return "Feed [description=" + description + ", link=" + link
				+ ", pubDate=" + pubDate + ", title=" + title + "]";
	}

	public boolean isTokenValid(String token) {
		return token == null || this.token.equals(token);
	}
	public String getToken() {
		return this.token;
	}

	public String getUser() {
		return this.userId;
	}

}
