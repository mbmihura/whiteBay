package com.tacs13G6.services.annotations;

import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.tacs13G6.models.*;

public class PersistenceService {
	public void save(Feed feed){
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Key feedKey = KeyFactory.createKey("Feed", "feedName");
        
        Entity ent = new Entity("Feed", feedKey);

		Field[] fields = feed.getClass().getFields();
        for (Field field : fields) {
            Persist annos = field.getAnnotation(Persist.class);
            if (annos != null) {
            	try {
					ent.setProperty(field.getName(), field.get(feed));
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
            }
        }
        Method[] methods = feed.getClass().getMethods();
        for (Method method : methods) {
            Persist annos = method.getAnnotation(Persist.class);
            if (annos != null) {
            	try {
            		ent.setProperty(method.getName(), method.invoke(feed));
            	} catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        datastore.put(ent);
    }

	public Feed getFeed()
	{
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Key feedKey = KeyFactory.createKey("Feed", "feedName");

        Query query = new Query("torrent", feedKey).addSort("date", Query.SortDirection.DESCENDING);
        List<Entity> torrents = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(5));
        
        if (!torrents.isEmpty()) {
          for (Entity torrent : torrents) {
        	  
//        	  Field[] fields = Feed.class.getFields();
//              for (Field field : fields) {
//                  Persist annos = field.getAnnotation(Persist.class);
//                  if (annos != null) {
//                  	try {
//      					field.setProperty(field.getName(), field.get(feed));
//      				} catch (IllegalArgumentException e) {
//      					e.printStackTrace();
//      				} catch (IllegalAccessException e) {
//      					e.printStackTrace();
//      				}
//                  }
//              }
//              
//            resp.getWriter().println(torrent.getProperty("user"));
//            resp.getWriter().println(torrent.getProperty("date"));
//            resp.getWriter().println(torrent.getProperty("content"));
//            List<Long> numb = (List<Long>) torrent.getProperty("content");
//            for(Long i : numb)
//            {
//            	resp.getWriter().print(i);
//            }
//            resp.getWriter().println(torrent.getProperty(""));	            
          }
        }
		return null;
	}
}
