package com.tacs13G6;

import java.io.IOException;
import java.util.Date;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tacs13G6.servlets.UrlParserService;

public class HelloServlet extends HttpServlet {
	@Override
	  public void doGet(HttpServletRequest req, HttpServletResponse resp)
	      throws IOException {
	        resp.setContentType("text/plain");
	        resp.getWriter().println("Hello World Servlet! \n\n" + new Date().toString());
	        
	        resp.getWriter().println("param 1: " + UrlParserService.parse(req.getPathInfo(), 1)[0]);
	        resp.getWriter().println("param 2: " + UrlParserService.parse(req.getPathInfo(), 2)[1]);
	        resp.getWriter().println("param 3: " + UrlParserService.parse(req.getPathInfo(), 3)[2]);
	      }
	
	
}