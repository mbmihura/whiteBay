package com.tacs13G6;

// GAE all
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

// GAE fecth
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GaeTestServlet extends HttpServlet {
	  @Override
	  public void doGet(HttpServletRequest req, HttpServletResponse resp)
	      throws IOException {

	        resp.setContentType("text/plain");
	        resp.getWriter().println("Hello, world");

	        // ALWAYS
	        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	        Key feedKey = KeyFactory.createKey("Feed", "feedName");

	        // SAVE
	        Entity ent = new Entity(feedKey);
	        ent.setProperty("user", "martin");
	        ent.setProperty("date", new Date());
	        List<Integer> num = new ArrayList<Integer>();
	        num.add(1);
	        num.add(2);
	        num.add(3);
	        ent.setProperty("content", num);

	        datastore.put(ent);
	        resp.getWriter().println("saving test user feeds...");

	        // RETRIEVE
	        Query query = new Query("torrent", feedKey).addSort("date", Query.SortDirection.DESCENDING);
	        List<Entity> torrents = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(5));
	        resp.getWriter().println("getting user feeds...");
	        
	        if (torrents.isEmpty()) {
	          resp.getWriter().println("Empty: No user feeds...");
	        }else{
	          for (Entity torrent : torrents) {
	            resp.getWriter().println(torrent.getProperty("user"));
	            resp.getWriter().println(torrent.getProperty("date"));
	            resp.getWriter().println(torrent.getProperty("content"));
	            List<Long> numb = (List<Long>) torrent.getProperty("content");
	            for(Long i : numb)
	            {
	            	resp.getWriter().print(i);
	            }
	            resp.getWriter().println(torrent.getProperty(""));	            
	          }
	        }
	      }
	    }

