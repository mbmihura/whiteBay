package com.tacs13G6.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.tacs13G6.fbApp.method.AppMethod;

public class FacebookApp extends HttpServlet{
	

	private String package_methods ="edu.tacs.group6.fbApp.method.";
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		executeMethod(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		executeMethod(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		executeMethod(request, response);
	}
	
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		executeMethod(request, response);
	}

	private void executeMethod(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String path=request.getPathInfo();
		String methods[]=path.split("/");
		if(methods!=null && methods.length>1){
			AppMethod method;
			JSONObject  json = new JSONObject();
			try{
				try {
					method = (AppMethod) Class.forName(package_methods+methods[1]).newInstance();
					json = method.execute(request,methods);
				} catch (InstantiationException e) {
					json.put("error", "no existe metodo");
				} catch (IllegalAccessException e) {
					json.put("error", "no existe metodo");
				} catch (ClassNotFoundException e) {
					json.put("error", "no existe metodo");
				}
				PrintWriter out = response.getWriter();  
				response.setContentType("text/x-json;charset=UTF-8");           
		        response.setHeader("Cache-Control", "no-cache");
		        out.write(json.toString());
			}catch (JSONException e){
				e.printStackTrace();
			}
		}
	}


}
