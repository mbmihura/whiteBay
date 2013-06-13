package com.tacs13G6;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;

public class HelloServlet extends HttpServlet {
	@Override
	  public void doGet(HttpServletRequest req, HttpServletResponse resp)
	      throws IOException {

	        resp.setContentType("text/plain");
	        resp.getWriter().println("Hello World Servlet! \n\n" + new Date().toString());
	      }
	
	
}