package com.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class UtilJSON {

    public static JSONObject getJSONObject(String s) {
        if( s == null ) return null;
        try {
        	return JSONObject.fromObject(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return null;
    }
    
    
    

    public static JSONArray getJSONArray(String s) {
    	if( s == null ) return null;
    	try {
    		return JSONArray.fromObject(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }
    
}
