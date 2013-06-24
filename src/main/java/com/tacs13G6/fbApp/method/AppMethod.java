package com.tacs13G6.fbApp.method;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;

public interface AppMethod {

	public JSONObject execute(HttpServletRequest request, String[] arguments) throws JSONException;
	
}
