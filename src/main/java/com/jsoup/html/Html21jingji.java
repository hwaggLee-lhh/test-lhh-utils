package com.jsoup.html;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
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
class Html21jingji extends HtmlBase{
	private static final Logger logger = Logger.getLogger(Html21jingji.class);
	
	public static void main(String[] args) throws Exception{
		HtmlBase base = new Html21jingji();
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
		Element e = doc.getElementById("newspage");
		if( e == null )return null;
		//System.out.println(e.html());
		Elements es =e.getElementsByClass("PicTitleSumlist");
		if( es == null )return null;
		List<NewsInfo> list_newsinfo = new ArrayList<NewsInfo>();
		if( es != null && es.size()>0){
			NewsInfo n = null;
			//System.out.println(es.size());
			for (Element e1 : es) {
				//System.out.println(e1.html());
				n = new NewsInfo();
				n.setWztitle(super.getByTagHtml(e1, "a",0));//标题
				if( super.isTitleChar(n.getWztitle()) ){
					n.setNetaddress(super.getByTagAttr(e1, "a", "href",0));//网址
					n.setWzabstrace(super.getByTagHtml(e1, "p",1));//摘要
					if( n.getWzabstrace()!= null ){
						n.setWzabstrace(n.getWzabstrace().substring(0, n.getWzabstrace().lastIndexOf("<a href=\"")));
					}
					n.setLaiyuan("21世纪经济报道");//来源
					String t = super.getTextOrHtml( e1.getElementsByClass("stime"), true);
					if( StringUtils.isNotBlank(t)){
						String s[] = t.split(" ");
						if( s.length>1){
							n.setAuthor(s[0]);//作者
						}
						if( s.length>2){//发表日期
							n.setFabiaodate(UtilsDate.getStringToDate(UtilsDate.yyyy_MM_dd_HH_mm_ss_zhry,s[1]+" "+s[2] ));
						}
					}
					list_newsinfo.add(n);
				}
				//break;
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
				Element e  = doc.getElementById("Article");
				if( e == null )continue;
				Elements els  = e.getElementsByTag("p");
				if( els != null && els.size()>0 ){
					Element e_end = els.get(els.size()-1);
					if( e_end!=null)e_end.remove();
				}
				String t = e.html();
				if( StringUtils.isNotBlank(t)){
					/*t = t.substring(0, t.lastIndexOf("(编辑："));
					if( t.endsWith("<p>")){
						t =  t.substring(0, t.lastIndexOf("<p>"));
					}*/
				}
				news.setContent(t.getBytes("gbk"));
				//System.out.println(new String(news.getContent(),"gbk"));
			}
		}
		//System.out.println();
	}



	@Override
	public String getUrl() {
		return CollectType.html_21jingji.getUrl();
	}
	

}
