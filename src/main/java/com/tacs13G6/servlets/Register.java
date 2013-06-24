package com.tacs13G6.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.tacs13G6.fbApp.FB_CONSTANTS;
import com.tacs13G6.services.FacebookApi;

public class Register extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher("/register.jsp").include(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String signed_request = req.getParameter("signed_request");
		try {
			JSONObject userRegister = FacebookApi.getUserRegister(signed_request);
			if(!userRegister.has("user_id")) resp.sendRedirect("/register.jsp?error=FACEBOOK");
			
			JSONObject registration = userRegister.getJSONObject("registration");
			JSONObject user = new JSONObject();
			user.put("userID", userRegister.get("user_id"));
			user.put("register",true);
			user.put("userName", registration.get("name"));
			req.getSession().setAttribute(FB_CONSTANTS.USER, user.toString());
			//TODO : implement Objectify
			resp.sendRedirect("/");
		} catch (JSONException e) {
			e.printStackTrace();
			resp.sendRedirect("/register.jsp?error=FAILED");
		}
	}

}
