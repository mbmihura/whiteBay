package com.tacs13G6.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLStreamException;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.tacs13G6.models.Feed;
import com.tacs13G6.models.exceptions.FeedMalformedException;
/**
 * Permite acceder mediante un pedido GET a los distintos feed del usuario en 
 * formato XML, el cual puede ser interpretado por un cliente torrent.
 */
public class RssServlet extends HttpServlet {
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RssServlet() {
        super();
    }

    /**
     * Metodo GET para obtener a partir de un id, el feed correspondiente en formato XML.
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
     protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	 PrintWriter out = response.getWriter(); 
      
         String[] urlParams = UrlParserService.parse(request.getPathInfo(), 3);
         
         if (urlParams[0] != null && !urlParams[0].isEmpty() && urlParams[1] != null && !urlParams[1].isEmpty())
         {
             String userId = urlParams[0];
             String feedId = urlParams[1];
             String token = urlParams[2];
             try {
            	Feed feed = Feed.find(feedId, userId);
            	if (feed.isTokenValid(token))
            	{
					out.println(feed.toXML());
			        response.setContentType("application/rss+xml"); 
			        response.setStatus(HttpServletResponse.SC_OK);
            	} else
            	{
            		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            	}
			 } catch (FeedMalformedException e ) {
				e.printStackTrace();
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			 } catch (XMLStreamException e) {
				e.printStackTrace();
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			 }   
             catch (EntityNotFoundException e) {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			 }  
         } else
         {
         	response.setStatus(HttpServletResponse.SC_NOT_FOUND);
         }
 	    out.close();
    }
     public static String getRssUrlFor(Feed feed)
     {
    	 StringBuilder sb = new StringBuilder();
    	 sb.append("/rss/").append(feed.getUser()).append('/').append(feed.getTitle());
    	 if (feed.getToken() != null)
    		 sb.append('/').append(feed.getToken());
    	 return sb.toString();
     }
}