package com.tacs13G6.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Set;
import java.util.HashSet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

//import edu.tacs.group6.models.MockRSSFeedWriter;
import com.tacs13G6.models.Feed;
import com.tacs13G6.models.MockFeed;

/**
 * Servlet implementation class FeedServlet
 */
public class FeedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FeedServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            PrintWriter out = response.getWriter(); 
            String path = request.getPathInfo();

            Gson jsonFactory = new Gson();
            if (path == null)
            {
                    Set<Feed> feeds = new HashSet<Feed>();
                    // TODO: get all feed for the current user from the data base.
                                            
                    out.println(jsonFactory.toJson(feeds));
            }
            else
            {
                    String feedId = request.getPathInfo().split("/(.*?)",3)[1];
                    Feed feed;
                    //TODO: Buscar feedId en la DB, validar que sea el usuario actual y cargar el objeto en feed.
                    feed = new MockFeed();
                    
                    out.println(jsonFactory.toJson(feed));
            }
    out.close();  
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            // TODO Auto-generated method stub
    }

    /**
     * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
     */
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            PrintWriter out = response.getWriter(); 

            String feedId = request.getParameter("feed");
            
            out.print("hello Put! " + feedId);

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

    /**
     * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
     */
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            // TODO Auto-generated method stub
    }

}