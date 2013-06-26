package com.tacs13G6.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
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
import com.tacs13G6.models.Torrent;
import com.tacs13G6.models.exceptions.FeedMalformedException;
import com.tacs13G6.models.exceptions.TorrentMalformedException;
import com.tacs13G6.models.exceptions.TorrentTitleAlreadyExistsInFeedException;

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
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter(); 
        //TODO: get current user;
        String userId = "mbmihura";
        String feedId = UrlParserService.parse(request.getPathInfo(), 1)[0];
        
      //TODO:  If (usuario es anonimo/no inicio sesion) {
      //    TODO: return url to do auth.
      //    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      //} else {
    		Gson jsonFactory = new Gson();
            if (feedId != null && !feedId.isEmpty()) {
	            try {
	            	Feed feed = Feed.find(feedId, userId);
					out.println(jsonFactory.toJson(feed));
					response.setStatus(HttpServletResponse.SC_OK);
				} catch (FeedMalformedException e) {
					e.printStackTrace();
					out.println(jsonFactory.toJson(e.getMessage()));
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				} catch (EntityNotFoundException e) {
					response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				}
            } else {
            	List<Feed> usersFeeds = Feed.find(userId);                 
                out.println(jsonFactory.toJson(usersFeeds));
                response.setStatus(HttpServletResponse.SC_OK);
            }
      //}
	    out.close();  
    }

    /**
     * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
     */
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	PrintWriter out = response.getWriter(); 
        //TODO: get current user;
        String userId = "mbmihura";
        String feedId = UrlParserService.parse(request.getPathInfo(), 1)[0];
        
      //TODO:  If (usuario es anonimo/no inicio sesion) {
      //    TODO: return url to do auth.
	  //    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	  //} else {
      	    Gson jsonFactory = new Gson();
            if (feedId != null && !feedId.isEmpty()) {
	            String link = request.getParameter("link");
	            String description = request.getParameter("description");
	            try {
	   				Feed a = Feed.createFeed(feedId, link, description, userId);
	   				a.save();
	   				response.setStatus(HttpServletResponse.SC_CREATED);
	   			} catch (FeedMalformedException e) {
	   				e.printStackTrace();
	   				response.getWriter().println(e.getMessage());
	   				response.getWriter().println("recieved params:");
	   				response.getWriter().println(feedId);
	   				response.getWriter().println(link);
	   				response.getWriter().println(description);
	   				response.getWriter().println(userId);
	   				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	   			}
            } else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
      //}
        out.close();
    }


    /**
     * @see HttpServlet#doPost(HttpServletRequest, HttpServletResponse)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	PrintWriter out = response.getWriter(); 
        //TODO: get current user;
        String userId = "mbmihura";
        String feedId = UrlParserService.parse(request.getPathInfo(), 1)[0];
        
      //TODO:  If (usuario es anonimo/no inicio sesion) {
      //    TODO: return url to do auth.
	  //    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	  //} else {
      	    Gson jsonFactory = new Gson();
            if (feedId != null && !feedId.isEmpty()) {
            	String title = request.getParameter("title");
                String link = request.getParameter("link");
                String description = request.getParameter("description");
                boolean shareInFb = request.getParameter("shareInFb").toLowerCase() == "true";
                
	            try {
	   				Feed feed = Feed.find(feedId,userId);
	   				for (Torrent t : feed.getTorrents())
	   				{
	   					if(t.getTitle().equals(title))
	   						throw new TorrentTitleAlreadyExistsInFeedException("Feed already contains a torrent with title "+ title);
	   				}
	   				Torrent newTorrent = new Torrent(title, description, link);
   					feed.getTorrents().add(newTorrent);
   					feed.save();
   					if (shareInFb){
	   					// url: SocialServlet.getAddingUrlFor(newTorrent);
   						//TODO: share in facebook;
	   				}
   					response.setStatus(HttpServletResponse.SC_OK);
	   			} catch (FeedMalformedException | TorrentMalformedException e) {
	   				e.printStackTrace();
	   				out.println(e.getMessage());
	   				out.println(feedId);
	   				out.println(link);
	   				out.println(description);
	   				out.println(userId);
	   				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	   			} catch (EntityNotFoundException e) {
	   				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	   			} catch (TorrentTitleAlreadyExistsInFeedException e) {
	   				out.println(e.getMessage());
	   				response.setStatus(HttpServletResponse.SC_PRECONDITION_FAILED);
	   			}
            } else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
      //}
        out.close();        
    }
}