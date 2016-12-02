package com.base.config;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jsoup.config.ConfigJsoup;

public class ConfigPropertiesFactory {
	private static Log log = LogFactory.getLog(ConfigJsoup.class);
	/**
	 * 属�?文件实例容器
	 */
	private static Map<String,Object> container = new HashMap<String,Object>();
	
	static{
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			if (classLoader == null) {
				classLoader = ConfigPropertiesFactory.class.getClassLoader();
				}
		     //加载属�?文件global.app.properties
			 try {
				InputStream is = classLoader.getResourceAsStream("global.app.properties");
				PropertiesHelper ph = new PropertiesHelper(is);
				container.put("app", ph);
				
			 } catch (Exception e1) {
				log.error("加载属�?文件global.app.properties出错!");
				e1.printStackTrace();
			}

	}
	
    /**
     * 获取属�?文件实例
     * @param pFile 文件类型
     * @return 返回属�?文件实例
     */
	public static PropertiesHelper getPropertiesHelper(String pFile){
		PropertiesHelper ph = (PropertiesHelper)container.get(pFile);
		return ph;
	}
}
