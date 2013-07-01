package com.tacs13G6.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tacs13G6.fbApp.ApplicationConfig;
import com.tacs13G6.servlets.exceptions.NotLoggedUserException;


/**
 * @see HttpServlet#HttpServlet()
 */
public class AuthServlet extends HttpServlet {
	private final static String sessionKeyCurrentUserId = "currentUserId";
	    public AuthServlet() {
	        super();
	    }

	    /**
	     * Metodo GET para obtener a partir de un id, el feed correspondiente en formato XML.
	     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	     */
	     protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    	 PrintWriter out = response.getWriter(); 
	    	 String signedRequest = request.getParameter("signedRequest");
	    	 
	         if (signedRequest != null && !signedRequest.isEmpty())
	         { 
	        	 String[] signedRequests = signedRequest.split("\\.", 2);
	        	 String UrlBase64EncodedSignature = signedRequests[0];
	        	 String payload = signedRequests[1];
	        	 
	        	 //Add '=' padding char, and replace '-' with '+' and '_' with '/' to get the signature's URLBase64 encoded version from the URLBase64 encoded one.
	    	     String padding = "";
	     	     switch (UrlBase64EncodedSignature.length() % 4) {
		     	    case 0:
		     	        break;
		     	    case 1:
		     	        padding = "===";
		     	        break;
		     	    case 2:
		     	        padding = "==";
		     	        break;
		     	    default:
		     	        padding = "=";
			    }
	     	    String Base64EncodedSignature = UrlBase64EncodedSignature.replace("-", "+").replace("_", "/").trim() + padding;
	     	    String signature = new String(Base64.decodeBase64(Base64EncodedSignature.getBytes("UTF-8")), "UTF-8");
	    	     
	     	     //Compute HMAC-SHA256 hash signature from the payload recieved using app secret code.
				 Mac mac;
				try {
					 mac = Mac.getInstance("HmacSHA256");
					 SecretKeySpec secretKey = new SecretKeySpec("8ef892954a10adfafe1d0f1d06ee988e".getBytes("UTF-8"), "HmacSHA256");
					 mac.init(secretKey);
					 byte[] hash = mac.doFinal(payload.getBytes("UTF-8"));
					 //Verified that computed signature and recieved one matches.
					 String generatedSignature = new String(hash,"UTF-8");
					 if(generatedSignature.equals(signature)) {
						//Add '=' padding char, and replace '-' with '+' and '_' with '/' to get the payload URLBase64 encoded version from the URLBase64 encoded one.
			    	     padding = "";
			     	     switch (payload.length() % 4) {
				     	    case 0:
				     	        break;
				     	    case 1:
				     	        padding = "===";
				     	        break;
				     	    case 2:
				     	        padding = "==";
				     	        break;
				     	    default:
				     	        padding = "=";
					    }
			     	    // Decode verified signed payload.
			     	    String Base64EncodedData = payload.replace("-", "+").replace("_", "/").trim() + padding;
			     	    String data = new String(Base64.decodeBase64(Base64EncodedData.getBytes("UTF-8")), "UTF-8");
			     	    // Retrieve userId and create a session for it.
			     	    String userId = new JsonParser().parse(data).getAsJsonObject().get("user_id").getAsString();
						HttpSession session = request.getSession(true);
			        	session.setAttribute(sessionKeyCurrentUserId, userId);
			        	response.setStatus(HttpServletResponse.SC_OK);
					 } else
		             {
		            	 response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		             }
//	        	     String generatedBase64EncodeSignature = new String(Base64.encodeBase64(hash), "UTF-8");
//	        	     if(generatedBase64EncodeSignature.equals(Base64EncodedSignature)) {
//					     //Correct
//					 }   
//	        	     String generatedUrlBase64EncodeSignature = generatedBase64EncodeSignature.replace("=","").replace("/", "_").replace("+", "-");
//	        	     if(generatedUrlBase64EncodeSignature.equals(signedRequests[0])) {
//					     //Correct
//					 }
				} catch (NoSuchAlgorithmException | InvalidKeyException e) {
					e.printStackTrace();
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				}
	         } else {
	         	response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	         	out.println("signedRequest is requiered and its value was " + signedRequest);
	         }
	 	     out.close();
	    }
	     static public String getCurrentUserId(HttpServletRequest request) throws NotLoggedUserException
	     {
        	 HttpSession session = request.getSession(true);
        	 Object sessionCurrentUser = session.getAttribute(sessionKeyCurrentUserId);
        	 if (sessionCurrentUser == null)
        		 throw new NotLoggedUserException("There is no object in session for the key '" + sessionKeyCurrentUserId + "' in the request send as param.");
        	 else
        		 return (String) sessionCurrentUser;
	     }	   
}
