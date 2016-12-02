package com.base.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class PropertiesHelper {
	private static Log log = LogFactory.getLog(PropertiesHelper.class);
	private Properties objProperties;
	
	
    /**
     * 构�?函数
     * 
     * @param is
     *            属�?文件输入�?
     * @throws Exception
     */
	public PropertiesHelper(InputStream is) throws Exception {
		try{
			objProperties = new Properties();
			objProperties.load(is);	
		}
		catch(FileNotFoundException e){
            log.error("未找到属性资源文�?{}", e);
            // e.printStackTrace();
			throw e;
		}
		catch(Exception e){
            log.error("读取属�?资源文件发生未知错误!{}", e);
            // e.printStackTrace();
			throw e;
		}finally{
			is.close();
		}
	}
	

    /**
     * 构�?函数
     * 
     * @param is
     *            属�?文件输入�?
     * @throws Exception
     */
	public PropertiesHelper(InputStream is,String format) throws Exception {
		try{
			objProperties = new Properties();
			if(StringUtils.isNotBlank(format)&&format.equalsIgnoreCase("xml")){
				objProperties.loadFromXML(is);
			}
		}
		catch(FileNotFoundException e){
            log.error("未找到属性资源文�?");
			e.printStackTrace();
			throw e;
		}
		catch(Exception e){
            log.error("读取属�?资源文件发生未知错误!");
			e.printStackTrace();
			throw e;
		}finally{
			is.close();
		}
	}

    
    /**
     * 持久化属性文�?br>
     * 使用setProperty()设置属�?�?必须调用此方法才能将属�?持久化到属�?文件�?
     * 
     * @param pFileName
     *            属�?文件�?
     * @throws IOException
     */
	public void storefile(String pFileName){
		FileOutputStream outStream = null;
		try{
			File file = new File(pFileName + ".properties");
			outStream = new FileOutputStream(file);
			objProperties.store(outStream, "#eRedG4");
		}catch(Exception e){
            log.error("保存属�?文件出错.");
			e.printStackTrace();
		}finally{
			try {
				outStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

    
    /**
     * 获取属�?�?
     * 
     * @param key
     *            指定Key值，获取value
     * @return String 返回属�?�?
     */
	public String getValue(String key){
		return objProperties.getProperty(key);
	}

    
    /**
     * 获取属�?�?支持缺省设置
     * 
     * @param key
     * @param defaultValue
     *            缺省�?
     * @return String 返回属�?�?
     */
	public String getValue(String key, String defaultValue){
		return objProperties.getProperty(key, defaultValue);
	}

    
    /**
     * 删除属�?
     * 
     * @param key
     *            属�?Key
     */
	public void removeProperty(String key){
		objProperties.remove(key);
	}
	
    
    /**
     * 设置属�?
     * 
     * @param key
     *            属�?Key
     * @param value
     *            属�?�?
     */
	public void setProperty(String key, String value){
		objProperties.setProperty(key, value);
	}
	
    
    /**
     * 打印�?��属�?�?
     */
	public void printAllVlue(){
		 objProperties.list(System.out);
	}


}
