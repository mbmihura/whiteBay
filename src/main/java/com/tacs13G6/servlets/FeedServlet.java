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
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter(); 
        UrlFeedParserService parser = new UrlFeedParserService(request.getPathInfo());

        Gson jsonFactory = new Gson();
        if (parser.hasUser())
        {
            String userId = parser.getUser();
          //TODO:  If (usuario actual NO coincide con el userId) {
	      //    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
	      //} else {
                if (parser.hasFeed()) {
                    String feedId = parser.getFeed();
		        	Feed feed; 
		            //Buscar feed en la DB, validar que sea el usuario actual y cargar el objeto en feed.
		            try {
						feed = Feed.find(feedId, userId);
						out.println(jsonFactory.toJson(feed));
						response.setStatus(HttpServletResponse.SC_OK);
					} catch (FeedMalformedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						out.println(jsonFactory.toJson(e.getMessage()));
						response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					} catch (EntityNotFoundException e) {
						// TODO Auto-generated catch block
						response.setStatus(HttpServletResponse.SC_NOT_FOUND);
					}
                } else {
                	List<Feed> usersFeeds = Feed.find(userId);                 
                    out.println(jsonFactory.toJson(usersFeeds));
                }
	      //}
                
        } else
        {
        	response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
	    out.close();  
    }

    /**
     * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
     */
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	UrlFeedParserService parser = new UrlFeedParserService(request.getPathInfo());
    	PrintWriter out = response.getWriter(); 
        
        if (parser.hasUser() && parser.hasFeed() /* TODO: && user is current user*/)
        {
             String link = request.getParameter("link");
             String description = request.getParameter("description");
             try {
				Feed a = Feed.createFeed(parser.getFeed(), link, description, parser.getUser());
				//a.getTorrents().add(new Torrent("title", "desc", "link"));
				a.save();
				response.setStatus(HttpServletResponse.SC_CREATED);
			} catch (FeedMalformedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				response.getWriter().println(e.getMessage());
				
				response.getWriter().println(parser.getFeed());
				response.getWriter().println(link);
				response.getWriter().println(description);
				response.getWriter().println(parser.getUser());
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}	        	
        }else
        {
        	response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        out.close();
    }


    /**
     * @see HttpServlet#doPost(HttpServletRequest, HttpServletResponse)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	UrlFeedParserService parser = new UrlFeedParserService(request.getPathInfo());
    	PrintWriter out = response.getWriter(); 
        
        if (parser.hasUser() && parser.hasFeed() /* TODO: && user is current user*/)
        {
        	String title = request.getParameter("title");
             String link = request.getParameter("link");
             String description = request.getParameter("description");
             try {
				Feed a = Feed.find(parser.getFeed(),parser.getUser());
				boolean titleAlreadyExists = false;
				for (Torrent t : a.getTorrents())
				{
					titleAlreadyExists = titleAlreadyExists || t.getTitle().equals(title);
				}
				if (titleAlreadyExists)
				{
					throw new TorrentTitleAlreadyExistsInFeedException("Feed already contains a torrent with title "+ title);
				} else{
				a.getTorrents().add(new Torrent(title, description, link));
				a.save();
				response.setStatus(HttpServletResponse.SC_OK);
                }
			} catch (FeedMalformedException e) {
				e.printStackTrace();
				response.getWriter().println(e.getMessage());
				
				response.getWriter().println(parser.getFeed());
				response.getWriter().println(link);
				response.getWriter().println(description);
				response.getWriter().println(parser.getUser());
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			} catch (EntityNotFoundException e) {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			} catch (TorrentTitleAlreadyExistsInFeedException e) {
				response.getWriter().println(e.getMessage());
				response.setStatus(HttpServletResponse.SC_PRECONDITION_FAILED);
			}	        	
        }else
        {
        	response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        out.close();        
    }

}