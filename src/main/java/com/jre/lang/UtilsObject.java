package com.jre.lang;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import com.utils.UtilsDate;

/**
 * Object
 * @author huage
 *
 */
public class UtilsObject {
	public static void main(String[] args) {
		byte[] ypte = "fdasfdafd".getBytes();
		System.out.println(conversionObjectToString(ypte,"utf-8"));
	}

	/**
	 * 将Object转化成String
	 * @return
	 */
	public static String conversionObjectToString(Object ob,String cheset){
		if( ob instanceof Date){
			return UtilsDate.getDateToString((Date)ob, "yyyy-MM-dd HH:mm:ss SSS");
		}
		if( ob instanceof Number){
			return ob.toString();
		}
		if( ob instanceof byte[]){
			try {
				return new String((byte[])ob, cheset);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return ob.toString();
	}
	
}
