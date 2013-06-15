package com.tacs13G6.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

// GAE fecth
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.gson.Gson;

//import edu.tacs.group6.models.MockRSSFeedWriter;
import com.tacs13G6.models.Feed;
import com.tacs13G6.models.MockFeed;
import com.tacs13G6.models.exceptions.FeedMalformedException;

/**
 * Servlet implementation class FeedServlet
 */
public class FeedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FeedServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            PrintWriter out = response.getWriter(); 
            String path = request.getPathInfo();

            Gson jsonFactory = new Gson();
            if (path == null)
            {
                    Set<Feed> feeds = new HashSet<Feed>();
                    // TODO: get all feed for the current user from the data base.
                                            
                    out.println(jsonFactory.toJson(feeds));
            }
            else
            {
                    String feedId = request.getPathInfo().split("/(.*?)",3)[1];
                    Feed feed;
                    //TODO: Buscar feedId en la DB, validar que sea el usuario actual y cargar el objeto en feed.
                    try {
						feed = new MockFeed();
						out.println(jsonFactory.toJson(feed));
					} catch (FeedMalformedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					}
                    
                    
            }
    out.close();  
    }

    /**
     * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
     */
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String path = request.getPathInfo();
    	
    	PrintWriter out = response.getWriter(); 

        String feedId = request.getParameter("feed");
        
        out.println("hello Put! " + feedId);
               
        Map m = request.getParameterMap();
        Enumeration e = request.getParameterNames();
        while (e.hasMoreElements()) {
        	  Object element = e.nextElement();
        	  out.println(element);
        	  out.println(request.getParameter(element.toString()));
        	}
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        
        out.println();
        
        if (path != null)
        {
//        	String feedId = request.getPathInfo().split("/(.*?)",3)[1];
//            DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
//            Key key = KeyFactory.createKey("UserInfo", feedId);
//            
//            Entity ent = new Entity("torrent", key);
//	        ent.setProperty("user", "martin");
//	        ent.setProperty("date", new Date());
//	        ent.setProperty("content", "dededededesseffe");
//
//	        datastore.put(ent);
            
        	response.setStatus(HttpServletResponse.SC_CREATED);
        }
        else
        {
        	response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        
        

    // Converts JSON string into a collection of Student object.
    //
    //Type type = new TypeToken<List<Student>>(){}.getType();
    //List<Student> studentList = gson.fromJson(jsonStudents, type);
            //
    //for (Student student : studentList) {
    //    System.out.println("student.getName() = " + student.getName());
    //}

    out.close();
    }

    /**
     * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
     */
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String path = request.getPathInfo();
        if (path != null)
        {
        	String feedId = request.getPathInfo().split("/(.*?)",3)[1];
            DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
            Key key = KeyFactory.createKey("UserInfo", feedId);
            try {
				datastore.get(key);
				response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			} catch (EntityNotFoundException e) {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			}
        }
        else
        {
        	response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    /**
     * @see HttpServlet#doPost(HttpServletRequest, HttpServletResponse)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String path = request.getPathInfo();
    	
    	PrintWriter out = response.getWriter(); 

        String feedId = request.getParameter("feed");
        
        out.println("hello Put! " + feedId);
               
        Map m = request.getParameterMap();
        Enumeration e = request.getParameterNames();
        while (e.hasMoreElements()) {
        	  Object element = e.nextElement();
        	  out.println(element);
        	  out.println(request.getParameter(element.toString()));
        	}
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        
        out.println();
        
        if (path != null)
        {
//        	String feedId = request.getPathInfo().split("/(.*?)",3)[1];
//            DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
//            Key key = KeyFactory.createKey("UserInfo", feedId);
//            
//            Entity ent = new Entity("torrent", key);
//	        ent.setProperty("user", "martin");
//	        ent.setProperty("date", new Date());
//	        ent.setProperty("content", "dededededesseffe");
//
//	        datastore.put(ent);
        	response.setStatus(HttpServletResponse.SC_OK);
            
        }
        else
        {
        	response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        
        

    // Converts JSON string into a collection of Student object.
    //
    //Type type = new TypeToken<List<Student>>(){}.getType();
    //List<Student> studentList = gson.fromJson(jsonStudents, type);
            //
    //for (Student student : studentList) {
    //    System.out.println("student.getName() = " + student.getName());
    //}

    out.close();
    }

}