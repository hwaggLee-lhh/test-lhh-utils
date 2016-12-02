package com.jsoup.html;

import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.jsoup.em.CollectType;
import com.jsoup.model.NewsInfo;

/**
 * 微信公众号：http://www.rx9188.com/zuixinzixun/zhongdianyaowen/10720.html
 * @author huage
 *
 */
class HtmlWeiXin extends HtmlBase{
	private static final Logger logger = Logger.getLogger(HtmlWeiXin.class);
	private static final String htmlurl_weixi = "http://weixin.sogou.com/";
	private static final String htmlurl_wzdz = htmlurl_weixi+"weixin?type=1&ie=utf8&query=";
	
	public static void main(String[] args) throws Exception{
		HtmlWeiXin sohu = new HtmlWeiXin();
		String name = "财经观察";
		String url = sohu.readUrlgzhwzdz(name);
		//System.out.println(url);
		sohu.readUrlwz(url);
		
	}
	
	public String readUrlwz(String url)throws Exception{
		Document doc = super.getDocument("http://weixin.sogou.com/gzh?openid=oIWsFt-T4t4o-AsbmBisaNbPJ_zA&ext=S9aWznSVzeEakRKa4e2Bpdi0vaT5uYsH-Tie93OnrTOihOBiXW12crKw9xDxMyIL");
		if( doc == null )return null;
		System.out.println(doc.html());
		Element e = doc.getElementById("gzh_arts_1");
		if( e == null )return null;
		Elements els = e.getElementsByClass("wx-rb3");
		if( els == null )return null;
		System.out.println(els.html());
		
		logger.info("");
		return null;
	}
	
	
	
	
	/**
	 * 读取公众好的文章列表地址
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public String readUrlgzhwzdz(String name)throws Exception{
		Document doc = super.getDocument(htmlurl_wzdz+name);
		if( doc == null )return null;
		//System.out.println(doc.html());
		Element e = doc.getElementById("sogou_vr_11002301_box_0");
		if(e == null) return null;
		return htmlurl_weixi+e.attr("href");
	}
	
	
	
	/**
	 * 只查询标题中含有"新三板"字样的，才读取
	 * @param outtime
	 * @return
	 * @throws Exception
	 */
	public List<NewsInfo> readHtml()throws Exception{
		return null;
	}
	
	




	@Override
	public String getUrl() {
		return CollectType.html_test.getUrl();
	}

}
