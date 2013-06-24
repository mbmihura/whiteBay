package com.tacs13G6.fbApp.method;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;

import com.tacs13G6.request.Request;
import com.tacs13G6.fbApp.FB_CONSTANTS;
import com.tacs13G6.utils.Utils;

public class PostMessage implements AppMethod {
	
	private String PARAMS[] = {
				FB_CONSTANTS.MESSAGE
	};

	public JSONObject execute(HttpServletRequest request,String[] arguments) throws JSONException {
		JSONObject json = new JSONObject();
		try {
			Map<String,String> params = Utils.getParams(request.getParameterMap(), PARAMS);
			params.put(FB_CONSTANTS.ACCESS_TOKEN , (String) request.getSession().getAttribute(FB_CONSTANTS.ACCESS_TOKEN));
			Request post = new Request();
			String response = post.sendPost(FB_CONSTANTS.GRAPH_URL+arguments[2]+"/feed", params);
			json = new JSONObject(response);
		} catch (IOException e) {
			e.printStackTrace();
			json.put("error", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			json.put("error", e.getMessage());
		}
		return json;
	}

}
