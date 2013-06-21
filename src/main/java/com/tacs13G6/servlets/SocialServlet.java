package com.tacs13G6.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tacs13G6.models.MockFeed;

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
	     * Permite a un usuario agregar un torrent a su feed simplemente haciendo click en el link.
	     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	     */
	     protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	            PrintWriter out = response.getWriter();
	            String[] urlParams = UrlParserService.parse(request.getPathInfo(), 3);
	            
	            if(urlParams[0] != null && !urlParams[0].isEmpty()
	            		&& urlParams[1] != null && !urlParams[1].isEmpty()
	            		&& urlParams[2] != null && !urlParams[2].isEmpty())
	            {
	         // IF (exist in DB) {
		            try {
		            	// Se encontro el feed, se genera el XML y envia al cliente.
		                //TODO: Genera el XML y envia el feed.
		                String xml = MockFeed.Create().toXML();
		                response.setContentType("text/xml"); 
		                response.setStatus(HttpServletResponse.SC_OK);
		                out.print(xml);
		            } catch (Exception e) {
	                    // No se pudo generar el xml por un error del serializador.
	                    e.printStackTrace();
	                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		            }
	            // } else {
		        //    	response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	            // }
	     } else
	     {
	    	 response.setStatus(HttpServletResponse.SC_FORBIDDEN);
	     }
	    }
	
	     /**
	      * Permite al usuario publicar un determinado feed en su muro de facebook.
	      * @see HttpServlet#doPost(HttpServletRequest, HttpServletResponse)
	      */
	     protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	     	String[] urlParams = UrlParserService.parse(request.getPathInfo(), 3);

	     	
	     	PrintWriter out = response.getWriter(); 

	         String feedId = request.getParameter("feed");
	         
	         out.println("hello Put! " + feedId);
	                
	         Map m = request.getParameterMap();
	         Enumeration e = request.getParameterNames();
	         while (e.hasMoreElements()) {
	         	  Object element = e.nextElement();
	         	  out.println(element);
	         	  out.println(request.getParameter(element.toString()));
	         	}
	         response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	         
	         out.println();

//	         	String feedId = request.getPathInfo().split("/(.*?)",3)[1];
//	             DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
//	             Key key = KeyFactory.createKey("UserInfo", feedId);
//	             
//	             Entity ent = new Entity("torrent", key);
//	 	        ent.setProperty("user", "martin");
//	 	        ent.setProperty("date", new Date());
//	 	        ent.setProperty("content", "dededededesseffe");
	 //
//	 	        datastore.put(ent);
	         	response.setStatus(HttpServletResponse.SC_OK);
	             
	         
	         

	     // Converts JSON string into a collection of Student object.
	     //
	     //Type type = new TypeToken<List<Student>>(){}.getType();
	     //List<Student> studentList = gson.fromJson(jsonStudents, type);
	             //
	     //for (Student student : studentList) {
	     //    System.out.println("student.getName() = " + student.getName());
	     //}

	     out.close();
	     }
}
