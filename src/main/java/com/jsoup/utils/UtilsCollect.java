package com.jsoup.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class UtilsCollect {
	private static final Logger logger = Logger.getLogger(UtilsCollect.class);
	
	/**
	 * 删除所有的html标签
	 * @param htmlStr
	 * @return
	 */
	public static String delHTMLTag(String htmlStr){ 
        /*String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式 
        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE); 
        Matcher m_html=p_html.matcher(htmlStr); 
        htmlStr=m_html.replaceAll(""); //过滤html标签 
*/
		htmlStr=htmlStr.replaceAll("<[A-z/ ']*>", "");
        return htmlStr.trim(); //返回文本字符串 
    } 

	public static void removeElements(Elements e_del){
		if( e_del != null ){
			//System.out.println(e_del.html());
			e_del.remove();
		}
	}
	
	public static void updateAttrElements(Elements e_upd,String attrKey,String attrValue){
		if( e_upd != null ){
			//System.out.println(e.html());
			e_upd.attr(attrKey, attrValue);
		}
	}
	
	public static void deleteHtmlTag(Elements e_del_tag){
		if( e_del_tag != null ){
			for (Element e : e_del_tag) {
				Element e_p = e.parent();
				if(e_p != null ){
					//System.out.println(e_p.html());
					e_p.after(e_p.text());
					e_p.remove();
					Element e_next = e_p.nextElementSibling();
					if( e_next != null ){
						//System.out.println(e_next.html());
						e_next.remove();
					}
				}
			}
		}
	}
	
	

	/**
	 * 读取网页
	 * @param path
	 * @param outtime
	 * @return
	 * @throws Exception
	 */
	public static Document getDocument(String path,int outtime)throws Exception{
		if( StringUtils.isBlank(path)) return null;
		Document doc = null;
		
		URL url = new URL(path);
		int i = 0 ;
		while(i<5){//出现异常休眠1秒,重新刷新,共刷新5次
			try {
				doc = Jsoup.parse(url, outtime);
				break;
			} catch (IOException e) {
				//e.printStackTrace();
				logger.error("eastmoney url req jsoup error:"+e.getMessage());
				if( i >= 5 ){
					throw new SocketTimeoutException(e.getMessage());
				}
				Thread.sleep(1*1000);
			}
			i++;
		}
		return doc;
	}
	
	/**
	 * 下载图到指定目录，并返回新的文件名称
	 * @param imgurl
	 * @return
	 */
	public static String downLoadFile(String imgurl,String savePath){
		if( imgurl == null || imgurl.length()==0)return imgurl;
		int hzindex = imgurl.lastIndexOf(".");
		if( hzindex <0 )hzindex = 0 	;
		String filename = UtilsCollect.getUUID()+imgurl.substring(hzindex);
		try {
			UtilsCollect.downLoadFromUrl(imgurl, filename, savePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return filename;
	}
	

	/**
	 * 从网络Url中下载文件
	 * 
	 * @param urlStr
	 * @param fileName
	 * @param savePath
	 * @throws IOException
	 */
	public static void downLoadFromUrl(String urlStr, String fileName,String savePath) throws IOException {
		HttpURLConnection conn = null;
		try {
			URL url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
		} catch (Exception e) {
			//e.printStackTrace();
			logger.error("下载地址错误："+urlStr);
			return;
		}
		// 设置超时间为3秒
		conn.setConnectTimeout(5 * 1000);
		// 防止屏蔽程序抓取而返回403错误
		conn.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
		// 得到输入流
		InputStream inputStream = conn.getInputStream();
		// 获取自己数组
		byte[] buffer = new byte[1024];
		int len = 0;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while ((len = inputStream.read(buffer)) != -1) {
			bos.write(buffer, 0, len);
		}
		bos.close();
		
		buffer = bos.toByteArray();
		// 文件保存位置
		File saveDir = new File(savePath);
		if (!saveDir.exists()) {
			saveDir.mkdirs();
		}
		//System.out.println(saveDir.getPath());
		File file = new File(saveDir + File.separator + fileName);
		if( !file.exists()){
			file.createNewFile();
		}
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(buffer);
		if (fos != null) {
			fos.close();
		}
		if (inputStream != null) {
			inputStream.close();
		}

		//System.out.println("info:" + url + " download success");

	}
	
	/** 
     * @功能 读取流 
     * @param inStream 
     * @return 字节数组 
     * @throws Exception 
     */  
    public static byte[] readStream(InputStream inStream) throws Exception {  
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();  
        byte[] buffer = new byte[1024];  
        int len = -1;  
        while ((len = inStream.read(buffer)) != -1) {  
            outSteam.write(buffer, 0, len);  
        }  
        outSteam.close();  
        inStream.close();  
        return outSteam.toByteArray();  
    }  
	
	public static String getUUID() {   
        UUID uuid = UUID.randomUUID();   
        String str = uuid.toString();   
        // 去掉"-"符号   
        String temp = str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24);   
        return temp;   
    } 
}
