package com.tacs13G6.request;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

public class Request {
	
	public String sendGet(String url, HttpParams params) throws ClientProtocolException, IOException{
		HttpRequestBase request = new HttpGet(url);
		request.setParams(params);
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse httpResponse = httpClient.execute(request);
		HttpEntity responseEntity = httpResponse.getEntity();
		if(responseEntity!=null) {
		    return EntityUtils.toString(responseEntity);
		}
		return new String();
		
	}
	
	public String sendPost(String url,Map<String,String> params) throws IOException{
		List <NameValuePair> httpParams = new ArrayList<NameValuePair>();
		for(Map.Entry<String, String> entry : params.entrySet()){
			httpParams.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
		}
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		post.setEntity(new UrlEncodedFormEntity(httpParams));
		HttpResponse httpResponse = httpclient.execute(post);
		HttpEntity responseEntity = httpResponse.getEntity();
	    if(responseEntity!=null) {
			    return EntityUtils.toString(responseEntity);
			}
		return new String();
	}
	
}
