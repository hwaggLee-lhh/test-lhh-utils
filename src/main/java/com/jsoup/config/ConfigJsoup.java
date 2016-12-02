package com.jsoup.config;

import com.base.config.ConfigPropertiesFactory;
import com.base.config.PropertiesHelper;

public class ConfigJsoup {
    static PropertiesHelper pHelper =null;
	static {
		pHelper = ConfigPropertiesFactory.getPropertiesHelper("app");
	}
	
	public static String getProperty(String key, String defaultValue) {
		return getpHelper().getValue(key,defaultValue);
	}
	
	public static String getProperty(String key) {
		return getpHelper().getValue(key);
	}

	private static PropertiesHelper getpHelper() {
		return pHelper;
	}

	/**
	 * 用户头像上传url
	 * @return
	 */
	public static String getUploadUserImgpath(){
		return getpHelper().getValue("uploadUserImgpath");
	}
	
	
}
