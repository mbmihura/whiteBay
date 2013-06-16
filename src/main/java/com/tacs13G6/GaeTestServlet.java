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
	        Key key = new KeyFactory.Builder("UserA1", "mbihuraA1")
	        .addChild("FeedA1", "feedTitlteA1")
	        .getKey();


	        // SAVE
	        Entity ent = new Entity(key);
	        ent.setProperty("user", "martin2");
	        ent.setProperty("date", new Date());
	        
	        List<String> list = new ArrayList<String>();
	        list.add(to.toJson());
	        list.add(to.toJson());
	        ent.setProperty("content", list);
	        
	        datastore.put(ent);

	        resp.getWriter().println("saving test user feeds...");

	        // RETRIEVE
	        resp.getWriter().println("getting user feeds...");
	        Entity ent1;
			try {
				ent1 = datastore.get(key);

	        String a = (String) ent1.getProperty("user");
            resp.getWriter().println(a);
            
            ent.setProperty("user", a +" martin");
            resp.getWriter().println(ent1.getProperty("date"));
            resp.getWriter().println("------");
            
            list = (List<String>) ent1.getProperty("content");
            Gson gson = new Gson();
            for (String t : list)
            {
                // Converts JSON string into a collection of Student object.
                //
                java.lang.reflect.Type type = new TypeToken<Torrent>(){}.getType();
                Torrent studentList = gson.fromJson(t, type);
                        //
                resp.getWriter().println(studentList.getTitle());
                resp.getWriter().println(studentList.getDescription());
                resp.getWriter().println(studentList.getLink());
                
            	
            	//String[] data = t.split("/", 3);
            	//resp.getWriter().println(new Torrent(data[0], data[1], data[2]).getLink());
            	//resp.getWriter().println(t);
            }
            resp.getWriter().println("##########");	    
			} catch (EntityNotFoundException e) {
				resp.getWriter().println("Empty: No user feeds...");
			}
	      }
	    }
