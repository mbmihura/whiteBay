package com.tacs13G6.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tacs13G6.models.MockFeed;
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
            String path = request.getPathInfo();
            if (path != null)
            {
	            String feedId = path.split("/(.*?)",3)[1];
	         // IF (exist in DB) {
	            //	
		            try {
		            	// Se encontro el feed, se genera el XML y envia al cliente.
		                //TODO: Genera el XML y envia el feed.
		                String xml = new MockFeed().toXML();
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
            } else {
            	response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
    }
}