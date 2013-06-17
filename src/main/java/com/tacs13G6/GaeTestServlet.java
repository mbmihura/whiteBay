package com.tacs13G6;

// GAE all
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

// GAE fecth
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.search.query.QueryTreeContext.Type;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.tacs13G6.models.Torrent;

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

		  Torrent to = new Torrent("titulo", "", "www.link2.com/ertert?wer=34&ert=\"wewre\"");
		 		  
	        resp.setContentType("text/plain");
	        resp.getWriter().println("Hello, world");

	        // ALWAYS
	        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	  
	     // User
	        String classT = "UserA1";
	        String username = "mbmihuraA1";
	        resp.getWriter().println("creating user... " + classT + "/" + username);
	        Entity user = new Entity(classT, username);
	        Key userKey = user.getKey();
	        String userStringKey1 = KeyFactory.keyToString(userKey);
	        resp.getWriter().println("user key: " + userStringKey1);
	        datastore.put(user);
	        
	        resp.getWriter().println("Test recreating key for user... " + classT + "/" + username);
	        Key userKey2 = KeyFactory.createKey(classT, username);
	        String userStringKey2 = KeyFactory.keyToString(userKey);
	        resp.getWriter().println("user key: " + userStringKey2);
	        if (userStringKey1.equals(userStringKey2)){
	        	resp.getWriter().println("keys match");
	        } else {
	        	resp.getWriter().println("keys DO NOT match");
	        }
	        resp.getWriter().println(" ");
	        
	     // SAVE A
	        Key key1 = new KeyFactory.Builder(classT, username)
	        .addChild("FeedA1", "feedTitlteA1")
	        .getKey();
	       
	        Entity ent = new Entity(key1);
	        ent.setProperty("user", "martinA");
	        ent.setProperty("date", new Date());
	        
	        List<String> list = new ArrayList<String>();
	        list.add(to.toJson());
	        list.add(to.toJson());
	        ent.setProperty("content", list);
	        
	        datastore.put(ent);

	        resp.getWriter().println("saving test user feeds...");
	        
	     // SAVE B
	        Key key2 = new KeyFactory.Builder(classT, username)
	        .addChild("FeedA1", "feedTitlteA2")
	        .getKey();
	        
	        Entity ent2 = new Entity(key2);
	        ent2.setProperty("user", "martinB");
	        ent2.setProperty("date", new Date());
	        
	        List<String> list2 = new ArrayList<String>();
	        list2.add(to.toJson());
	        list2.add(to.toJson());
	        ent2.setProperty("content", list2);
	        
	        datastore.put(ent2);

	        resp.getWriter().println("saving test user feeds...");
	        resp.getWriter().println(" ");

	     // RETRIEVE
	        resp.getWriter().println("getting user feeds...");
	        Entity ent1;
			try {
				ent1 = datastore.get(new KeyFactory.Builder(classT, username)
		        .addChild("FeedA1", "feedTitlteA1")
		        .getKey());

		        String a = (String) ent1.getProperty("user");
	            resp.getWriter().println(a);
	            
	            ent.setProperty("user", a +" martin");
	            resp.getWriter().println(ent1.getProperty("date"));
	            resp.getWriter().println("torrents ##");
	            
	            list = (List<String>) ent1.getProperty("content");
	            Gson gson = new Gson();
	            if (list == null)
	            {
	            	resp.getWriter().println("--- torrents FAIL to load");
	            } else {
		            for (String t : list)
		            {
		                // Converts JSON string into a collection of Student object.
		                //
		                java.lang.reflect.Type type = new TypeToken<Torrent>(){}.getType();
		                Torrent studentList = gson.fromJson(t, type);
		                        //
		                resp.getWriter().println("  " + studentList.getTitle());
		                resp.getWriter().println("  " + studentList.getDescription());
		                resp.getWriter().println("  " + studentList.getLink());
		                resp.getWriter().println("  ");
		                
		            	
		            	//String[] data = t.split("/", 3);
		            	//resp.getWriter().println(new Torrent(data[0], data[1], data[2]).getLink());
		            	//resp.getWriter().println(t);
		            }
		            resp.getWriter().println("########## torrents OK");	
				}
			} catch (EntityNotFoundException e) {
				resp.getWriter().println("Empty: No user feeds...");
			}
			
			//QUERY
			resp.getWriter().println(" ");
			resp.getWriter().println("QUERYING feeds...");
						
			Query mediaQuery = new Query()
            .setAncestor(KeyFactory.createKey(classT, username))
            .addFilter(Entity.KEY_RESERVED_PROPERTY,
			                       Query.FilterOperator.GREATER_THAN,
			                       userKey);
			//By default, ancestor queries include the specified ancestor itself.
			//The following filter excludes the ancestor from the query results.
			            


			//Returns both weddingPhoto and weddingVideo,
			//even though they are of different entity kinds
			List<Entity> results = datastore.prepare(mediaQuery)
			                   .asList(FetchOptions.Builder.withDefaults());
			
			for(Entity e : results)
			{
				resp.getWriter().println(e.getProperty("user"));
			}
	      }
	    }
