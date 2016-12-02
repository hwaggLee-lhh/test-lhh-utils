package com.jsoup.html;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.jsoup.config.ConfigJsoup;
import com.jsoup.utils.UtilsCollect;

public abstract class HtmlBase {
	private static final Logger logger = Logger.getLogger(HtmlBase.class);
	protected int outtime = 5*1000;
	/**新闻数据：作者，网址，来源，时间，标题，内容，摘要，图片*/
	public abstract List<?> readHtml()throws Exception;
	public abstract String getUrl();
	public static final String titleChart = "新三板";
	
	public Document getDocument(){
		try {
			return UtilsCollect.getDocument(getUrl(), outtime);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}
	
	public Document getDocument(String url){
		try {
			return UtilsCollect.getDocument(url, outtime);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}
	
	/**
	 * 查询节点中的第一个tag的classname
	 * @param e
	 * @param tag
	 * @param calssname
	 * @return
	 */
	public String getElementAttr(Element e,String tag,String calssname){
		if( e != null && e.getElementsByTag(tag)!=null ){
			return e.getElementsByTag(tag).attr(calssname);
		}
		return null;
	}
	
	/**
	 * 如果th为true,则返回text，如果th为false，则返回html
	 * @param es
	 * @param th
	 * @return
	 */
	public String getTextOrHtml(Elements es,boolean th){
		if( es != null ){
			if(th)return es.text();
			return es.html();
		}
		return null;
	}

	public String getByIdText(Element e,String idName){
		if( e == null || idName == null )return null;
		Element s = e.getElementById(idName);
		if( s == null)return null;
		return s.text();
	}
	
	public String getByIdHtml(Element e,String idName){
		if( e == null || idName == null )return null;
		Element s = e.getElementById(idName);
		if( s == null)return null;
		return s.html();
	}
	
	
	public String getByTagHtml(Element e,String tag,int index){
		if( e == null || tag == null )return null;
		Elements s = e.getElementsByTag(tag);
		if( s == null || s.size() < index)return null;
		Element v = s.get(index);
		if( v == null) return null;
		return v.html();
	}
	
	public String getByClassHtml(Element e,String classname,int index){
		if( e == null || classname == null )return null;
		Elements s = e.getElementsByClass(classname);
		if( s == null || s.size() < index)return null;
		Element v = s.get(index);
		if( v == null) return null;
		return v.html();
	}
	
	

	public String getByTagAttr(Element e,String tag,String attrname,int index){
		if( e == null || tag == null )return null;
		Elements s = e.getElementsByTag(tag);
		if( s == null || s.size() < index )return null;
		Element v = s.get(index);
		if( v == null) return null;
		return v.attr(attrname);
	}

	
	/**
	 * 图片下载
	 * @param url
	 * @return
	 */
	public String getImgUploadFileName(String url){
		if(StringUtils.isNotBlank(url))
		return UtilsCollect.downLoadFile(url,ConfigJsoup.getProperty("uploadNewsTitleImgpath"));
		return null;
	}
	
	/**
	 * 判断标题
	 * @param title
	 * @return
	 */
	public boolean isTitleChar(String title){
		if( StringUtils.isBlank( title) || ! title.contains(titleChart)){
			return false;
		}
		return true;
	}
	
	
	public void setOuttine(int outtime){
		this.outtime = outtime;
	}
	

	public void removeElements(Elements e_del){
		if( e_del != null ){
			e_del.remove();
		}
	}

	public void removeElement(Element e_del){
		if( e_del != null ){
			e_del.remove();
		}
	}
	
	public void deleteHtmlTag(Elements e_del_tag){
		if( e_del_tag != null ){
			//System.out.println(e_del_tag.html());
			e_del_tag.unwrap();
			/*for (Element e : e_del_tag) {
				e.unwrap();
			}*/
		}
	}
	
}
