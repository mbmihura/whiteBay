package com.tacs13G6.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tacs13G6.models.MockFeed;
/**
 * Servlet implementation class RssServlet
 */
public class RssServlet extends HttpServlet {
        private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RssServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

        /**
         * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
         */
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                PrintWriter out = response.getWriter(); 
                // TODO: definir comportamiento para cuando estran a /rss
                String feedId = request.getPathInfo().split("/(.*?)",3)[1];
                try {

                        //TODO: Buscar feedId en la DB
                        
                        //Genera el XML y envia el feed. TODO: remplazar por el feed que devuelva la db.
                        String xml = new MockFeed().toXML();
                        response.setContentType("text/xml"); 
                        out.print(xml);
                } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
        }

        /**
         * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
         */
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                // TODO Auto-generated method stub
        }

}