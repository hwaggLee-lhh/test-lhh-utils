package com.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class UtilURL {
    
    /**
     * 获取某个网页的内容
     * @param url  网页的地址
     * @param code 网页的编码，不传就代表UTF-8
     * @return 网页的内容
     * @throws IOException
     */
    public static String fetch_url(String url, String code) throws IOException {
    	if(url == null ) return null;
    	if( code == null) code = "utf-8";
    	//System.out.println("-------------->url:"+url);
        BufferedReader bis = null; 
        InputStream is = null; 
        InputStreamReader inputStreamReader = null;
        try { 
            URLConnection connection = new URL(url).openConnection(); 
            connection.setConnectTimeout(20000);
            connection.setReadTimeout(20000);
            connection.setUseCaches(false);
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11");
            is = connection.getInputStream(); 
            inputStreamReader = new InputStreamReader(is, code);
            bis = new BufferedReader(inputStreamReader); 
            String line = null; 
            StringBuffer result = new StringBuffer(); 
            while ((line = bis.readLine()) != null) { 
                result.append(line); 
            } 
            return result.toString(); 
        } finally { 
            if (inputStreamReader != null) {
                try { 
                    inputStreamReader.close();
                } catch (IOException e) { 
                    e.printStackTrace(); 
                } 
            }
            if (bis != null) { 
                try { 
                    bis.close(); 
                } catch (IOException e) { 
                    e.printStackTrace(); 
                } 
            } 
            if (is != null) { 
                try { 
                    is.close(); 
               } catch (IOException e) { 
                    e.printStackTrace(); 
                } 
            } 
        } 
    }
    
}
