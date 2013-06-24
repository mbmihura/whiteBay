package com.tacs13G6.services;

import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import com.tacs13G6.request.Request;
import com.tacs13G6.fbApp.ApplicationConfig;
import com.tacs13G6.fbApp.FB_CONSTANTS;

public class FacebookApi {
	static public String getAppAccessToken(){
		HttpParams params = new BasicHttpParams();
		Request p=new Request();
		try {
			String response = p.sendGet(FB_CONSTANTS.GRAPH_URL+"oauth/access_token?client_id="+ApplicationConfig.client_id+"&client_secret="+ApplicationConfig.client_secret+"&grant_type=client_credentials",params);
			response = response.substring(response.indexOf("=")+1);
			return response;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return "";
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
		
	}
	
	static public JSONObject getUserRegister(String signed_request) throws JSONException{
		String data64[] = signed_request.split("\\.");
		String sig = new String(Base64.decodeBase64(data64[0].getBytes()));
		String data = new String(Base64.decodeBase64(data64[1].getBytes()));
		return new JSONObject(data);
	
	}
	
}
