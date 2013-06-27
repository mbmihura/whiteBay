package com.tacs13G6.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.EntityNotFoundException;

// GAE fecth
import com.google.gson.Gson;

//import edu.tacs.group6.models.MockRSSFeedWriter;
import com.tacs13G6.models.Feed;
import com.tacs13G6.models.Torrent;
import com.tacs13G6.models.exceptions.FeedMalformedException;
import com.tacs13G6.models.exceptions.TorrentMalformedException;
import com.tacs13G6.models.exceptions.TorrentTitleAlreadyExistsInFeedException;
import com.tacs13G6.servlets.exceptions.NotLoggedUserException;

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
        String feedId = UrlParserService.parse(request.getPathInfo(), 1)[0];

        try {
        	String userId = AuthServlet.getCurrentUserId(request);
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
        } catch (NotLoggedUserException e) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
	    out.close();  
    }

    /**
     * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
     */
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	PrintWriter out = response.getWriter(); 
        String feedId = UrlParserService.parse(request.getPathInfo(), 1)[0];
        
  	    Gson jsonFactory = new Gson();
        if (feedId != null && !feedId.isEmpty()) {
        	String link = request.getParameter("link");
            String description = request.getParameter("description");
            String userId = null;
        	try {
	        	userId = AuthServlet.getCurrentUserId(request);
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
   			} catch (NotLoggedUserException e) {
   				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			}
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        out.close();
    }


    /**
     * @see HttpServlet#doPost(HttpServletRequest, HttpServletResponse)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	PrintWriter out = response.getWriter();     
        String feedId = UrlParserService.parse(request.getPathInfo(), 1)[0];
        
  	    Gson jsonFactory = new Gson();
        if (feedId != null && !feedId.isEmpty()) {
        	String title = request.getParameter("title");
            String link = request.getParameter("link");
            String description = request.getParameter("description");
            String userId = null;
            boolean shareInFb = request.getParameter("shareInFb").toLowerCase() == "true";
            
            try {
    	        userId = AuthServlet.getCurrentUserId(request);
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
   			} catch (NotLoggedUserException e) {
   				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			}
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        out.close();        
    }
}