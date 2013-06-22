package com.tacs13G6.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.gson.Gson;
import com.tacs13G6.models.Feed;
import com.tacs13G6.models.MockFeed;
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
	    }
