package com.tacs13G6.servlets;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.gson.Gson;
import com.tacs13G6.models.Feed;

import com.tacs13G6.models.MockFeed;
import com.tacs13G6.models.Torrent;

import com.tacs13G6.models.exceptions.FeedMalformedException;

	/**
	 * Permite realizar acciones de viralizacion.
	 */
public class SocialServlet extends HttpServlet {
	    /**
	     * @see HttpServlet#HttpServlet()
	     */
	    public SocialServlet() {
	        super();
	    }
	
	     /**
	      * Permite al usuario publicar un determinado feed en su muro de facebook.
	      * @see HttpServlet#doPost(HttpServletRequest, HttpServletResponse)
	      */
	     protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    	 PrintWriter out = response.getWriter(); 
	         //TODO: get current user;
	         String userId = "mbmihura";
	         String feedId = UrlParserService.parse(request.getPathInfo(), 1)[0];
	         
	       //TODO: If (usuario es anonimo/no inicio sesion) {
	       //    TODO: return url to do auth.
	       //    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	       //} else {
	     		Gson jsonFactory = new Gson();
	             if (feedId != null && !feedId.isEmpty()) {
	 	            try {
	 	            	Feed feed = Feed.find(feedId, userId);
	 	            	// Url: RssServlet.getRssUrlFor(feed);
	 					//TODO: share feed!
	 					response.setStatus(HttpServletResponse.SC_OK);
	 				} catch (FeedMalformedException e) {
	 					e.printStackTrace();
	 					out.println(jsonFactory.toJson(e.getMessage()));
	 					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	 				} catch (EntityNotFoundException e) {
	 					response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	 				}
	             } else {
	            	 response.setStatus(HttpServletResponse.SC_FORBIDDEN);
	             }
	       //}
	 	    out.close();  
	     } 
	     public static String getAddingUrlFor(Torrent torrent)
	     {
			final String encodeType = "UTF-8";
	    	 try {
				return new StringBuilder()
				 .append("/?title=").append(URLEncoder.encode(torrent.getTitle(), encodeType))
				 .append("&link=").append(URLEncoder.encode(torrent.getLink(), encodeType))
				 .append("&desc=").append(URLEncoder.encode(torrent.getDescription(), encodeType))
				 .toString();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return null;
			}
	     }
	    }
