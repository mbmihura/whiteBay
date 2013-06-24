package com.tacs13G6.utils;

import java.util.HashMap;
import java.util.Map;

public class Utils {

		private static boolean VALIDATE = false;
		
		public static Map<String,String> getParams(Map<String,String[]> httpParams , String[] needed) throws Exception{
			Map<String,String> params = new HashMap<String, String>();
			if(checkHttpParams(httpParams, needed, params))return params;
			else throw new Exception("Faltan parametros");
		}
	
		private static boolean checkHttpParams(Map<String,String[]> httpParams,String[] needed,Map<String,String> params){
			int i = 0;
			for(i = 0 ; i < needed.length ; i++){
				if(!httpParams.containsKey(needed[i]) && VALIDATE) break;
				addParameter(params, needed[i],(String)httpParams.get(needed[i])[0]);
			}
			return (i == needed.length);
		}
		
		private static void addParameter(Map<String,String> params,String key,String value){
			params.put(key, value);
		}
}
