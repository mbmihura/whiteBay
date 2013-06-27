package com.tacs13G6.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tacs13G6.servlets.exceptions.NotLoggedUserException;


/**
 * @see HttpServlet#HttpServlet()
 */
public class AuthServlet extends HttpServlet {
	private final static String sessionKeyCurrentUserId = "currentUserId";
	    public AuthServlet() {
	        super();
	    }

	    /**
	     * Metodo GET para obtener a partir de un id, el feed correspondiente en formato XML.
	     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	     */
	     protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    	 PrintWriter out = response.getWriter(); 
	      
	    	 String userId = request.getParameter("userId");
	         if (userId != null && !userId.isEmpty())
	         {
	        	 HttpSession session = request.getSession(true);
	        	 session.setAttribute(sessionKeyCurrentUserId, userId);
	        	 response.setStatus(HttpServletResponse.SC_OK);
	         } else
	         {
	         	response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	         	out.println("userId is requiered and its value was " + userId);
	         }
	 	     out.close();
	    }
	     static public String getCurrentUserId(HttpServletRequest request) throws NotLoggedUserException
	     {
        	 HttpSession session = request.getSession(true);
        	 Object sessionCurrentUser = session.getAttribute(sessionKeyCurrentUserId);
        	 if (sessionCurrentUser == null)
        		 throw new NotLoggedUserException("There is no object in session for the key '" + sessionKeyCurrentUserId + "' in the request send as param.");
        	 else
        		 return (String) sessionCurrentUser;

	     }
	     
	     
}
