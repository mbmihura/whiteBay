package com.tacs13G6.fbApp.method;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;

import com.tacs13G6.fbApp.FB_CONSTANTS;

public class GetUser implements AppMethod {

	public JSONObject execute(HttpServletRequest request,String[] arguments) throws JSONException {
		JSONObject json = new JSONObject();
		try {
			//get objectify by user id and accesToken 
			String userId = arguments[2];
			json.put("userName","pedro");
			json.put("userID",userId);
			json.put("register",true);
			request.getSession().setAttribute(FB_CONSTANTS.USER, json.toString());
		} catch (Exception e) {
			e.printStackTrace();
			json.put("error", e.getMessage());
		}
		return json;
	}

}
