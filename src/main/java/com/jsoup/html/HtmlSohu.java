package com.jsoup.html;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.jsoup.em.CollectType;
import com.jsoup.model.NewsInfo;
import com.jsoup.utils.UtilsCollect;
import com.utils.UtilsDate;

/**
 * 搜狐新三板（http://stock.sohu.com/xinsanban/index.shtml）
 * @author huage
 *
 */
class HtmlSohu extends HtmlBase{
	private static final Logger logger = Logger.getLogger(HtmlSohu.class);
	
	public static void main(String[] args) throws Exception{
		HtmlBase base = new HtmlSohu();
		base.readHtml();
		//List<NewsInfo> list_newsinfo = new ArrayList<NewsInfo>();
		//NewsInfo n = new NewsInfo();
		//list_newsinfo.add(n);
		//n.setNetaddress("http://stock.sohu.com/20151012/n422980815.shtml");
		//n.setNetaddress("http://stock.sohu.com/20151010/n422873656.shtml");
		//n.setNetaddress("http://stock.sohu.com/20151013/n423090267.shtml");
		//base.readHtmlContentBody(list_newsinfo);
	}
	
	
	
	/**
	 * 只查询标题中含有"新三板"字样的，才读取
	 * @param outtime
	 * @return
	 * @throws Exception
	 */
	public List<NewsInfo> readHtml()throws Exception{
		logger.info("start sohu newsinfo data.....");
		Document doc = super.getDocument();
		if( doc == null )return null;
		//System.out.println(doc.html());
		Element e = doc.getElementById("contentA");
		if( e == null )return null;
		//System.out.println(ele.html());
		Elements es =e.getElementsByClass("left");
		if( es == null )return null;
		//System.out.println(es.html());
		e = es.get(0);
		if( e == null )return null;
		//System.out.println(e.html());
		List<NewsInfo> list_newsinfo = new ArrayList<NewsInfo>();
		es = e.getElementsByTag("h1");
		NewsInfo n = new NewsInfo();
		if( es != null && super.isTitleChar(es.text())){
			//System.out.println(es.html());
			n.setWztitle(es.text());//文章标题
			n.setNetaddress(super.getElementAttr(es.get(0), "a", "href"));//网址
			es = e.getElementsByClass("h1_text");//摘要
			if( es != null ){
				//System.out.println(es.html());
				n.setWzabstrace(es.text());
			}
			es = e.getElementsByClass("img1");//图片
			if( es != null ){
				//System.out.println(es.html());
				n.setWzimgurl(super.getImgUploadFileName(super.getElementAttr(es.get(0), "img", "src")));
			}
			list_newsinfo.add(n);
		}
		
		
		
		/***
		 * 新闻2
		 */
		n = new NewsInfo();
		es = e.getElementsByClass("box");
		if( es != null && es.size()>0){
			Element e1 = es.get(0);
			n.setWztitle(super.getByTagHtml(e1, "a",0));//标题
			if( super.isTitleChar(n.getWztitle()) ){
				n.setNetaddress(super.getByTagAttr(e1, "a", "href",0));//网址
				n.setWzabstrace(super.getByTagHtml(e1, "div",1));//摘要
				list_newsinfo.add(n);
			}
		}
		
		
		
		/***
		 * 新闻3
		 */
		es = e.getElementsByClass("pictext");
		if( es != null && es.size()>0){
			//System.out.println(es.size());
			for (Element e1 : es) {
				n = new NewsInfo();
				n.setWztitle(super.getByTagHtml(e1, "a",1));//标题
				if( super.isTitleChar(n.getWztitle()) ){
					n.setNetaddress(super.getByTagAttr(e1, "a", "href",1));//网址
					n.setWzimgurl(super.getImgUploadFileName(super.getByTagAttr(e1, "img", "src",0)));//图片
					n.setWzabstrace(super.getByTagHtml(e1, "div",1));//摘要
					list_newsinfo.add(n);
				}
			}
		}
		readHtmlContentBody(list_newsinfo);
		
		logger.info("end sohu newsinfo data.....total "+list_newsinfo.size()+"");
		return list_newsinfo;
	}
	
	
	/**
	 * 读取文被内容,来源
	 * @param path
	 * @throws Exception
	 */
	public void readHtmlContentBody(List<NewsInfo> list_newsinfo)throws Exception{
		if( list_newsinfo == null ) return;
		for (NewsInfo news : list_newsinfo) {
			logger.info("sohu wzaddress:"+news.getNetaddress());
			Document doc = null;
			try {
				doc = UtilsCollect.getDocument(news.getNetaddress(), outtime);
			} catch (Exception e) {
				logger.error("sohu error info :"+e.getMessage());
				continue;
			}
			if( doc != null ){
				Elements els = doc.getElementsByClass("content-box");
				if( els == null || els.size() == 0)continue;
				Element e = els.get(0);
				if( e == null ) continue;
				news.setFabiaodate(UtilsDate.getStringToDate(UtilsDate.yyyy_MM_dd_HH_mm_ss, super.getByIdText(e, "pubtime_baidu")));//发表日期
				news.setLaiyuan(super.getByIdText(e, "media_span"));//来源
				news.setAuthor(super.getByIdText(e, "author_baidu"));//作者
				if(news.getAuthor()!= null){
					news.setAuthor(news.getAuthor().replace("作者：", ""));
				}
				els = e.getElementsByAttributeValue("itemprop", "articleBody");
				if( els == null || els.size() == 0)continue;
				e = els.get(0);
				super.removeElements(e.getElementsByClass("stockTrends"));
				super.removeElements(e.getElementsByClass("muLink"));
				super.deleteHtmlTag(e.getElementsByTag("a"));
				super.deleteHtmlTag(e.getElementsByTag("img"));
				news.setContent(e.html().getBytes("gbk"));//内容
			}
		}
	}



	@Override
	public String getUrl() {
		return CollectType.html_sohu.getUrl();
	}

}
