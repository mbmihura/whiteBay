package com.tacs13G6;

import java.io.IOException;
import java.util.Date;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tacs13G6.servlets.UrlFeedParserService;

public class HelloServlet extends HttpServlet {
	@Override
	  public void doGet(HttpServletRequest req, HttpServletResponse resp)
	      throws IOException {
			UrlFeedParserService parser = new UrlFeedParserService(req.getPathInfo());
	        resp.setContentType("text/plain");
	        resp.getWriter().println("Hello World Servlet! \n\n" + new Date().toString());

	        resp.getWriter().println(parser.getUser());
	        resp.getWriter().println(parser.getFeed());
	        resp.getWriter().println(parser.getTorrent());
	      }
	
	
}