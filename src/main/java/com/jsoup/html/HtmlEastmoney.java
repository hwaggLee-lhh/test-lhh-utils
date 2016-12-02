package com.jsoup.html;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
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
 * 读取eastmoney(东方财富)中的新闻
 * @author huage
 *
 */
class HtmlEastmoney {
	private static final Logger logger = Logger.getLogger(HtmlEastmoney.class);
	
	public static void main(String[] args) throws Exception{
		int outtime = 5*1000;
		//readHtml_eastmoney_artitileList_path(outtime);
		
		NewsInfo news = new NewsInfo();
		news.setNetaddress("");
		readHtml_eastmoney_ContentBody(news,outtime);
		
		/*String urlStr = "http://z1.dfcfw.com/2015/8/18/20150818074732554889007.jpg";
		String fileName = UUID.randomUUID().toString()+".jpg";
		String savePath = "C://Users//huage//Desktop";
		FileUtils.downLoadFromUrl(urlStr, fileName, savePath);*/
	}
	
	
	
	public static List<NewsInfo> readHtml_eastmoney_artitileList_path(int outtime)throws Exception{
		logger.info("start eastmoney newsinfo data.....");
		Document doc = UtilsCollect.getDocument(CollectType.html_eastmoney.getUrl(), outtime);
		if( doc == null )return null;
		//System.out.println(doc.html());
		Element e_root = doc.getElementById("artitileList1");
		if( e_root == null ){
			logger.error("eastmoney newsinfo data html tag lose.");
			return null;
		}
		//System.out.println(e_root.html());
		Elements els = e_root.children();
		if(els == null ){
			return null;
		}
		List<NewsInfo> list_newsinfo = new ArrayList<NewsInfo>();
		int idIndex = 1;//作为ID间
		String idStr = null;
		NewsInfo news = null;
		for (Element e : els) {
			//System.out.println(e.html());
			Elements els_imageLi =e.getElementsByClass("imageLi");
			if( els_imageLi == null)continue;
			Elements els_a = null;
			for (Element e_img : els_imageLi) {
				news = new NewsInfo();
				if( idIndex < 1000){
					idStr = "eastmoney-"+UtilsDate.getSystemDateToString(UtilsDate.yyyyMMddHHmmssSSS)+(idIndex++);
					news.setIdStr(idStr);
				}
				//System.out.println(e_img.html());
				/*List<Node> list = e_img.childNodesCopy();
				for (Node n : list) {
					if( n instanceof Element){
						Element e_f = (Element)n;
						System.out.println(e_f.html());
					}
				}*/
				els_a = e_img.getElementsByTag("a");
				if( els_a != null ){
					for (Element a : els_a) {
						//System.out.println(a.attr("href"));
						//文章链接地址
						news.setNetaddress(a.attr("href"));
						break;
					}
				}
				els_a = e_img.getElementsByTag("img");
				if( els_a != null ){
					for (Element img : els_a) {
						//System.out.println(img.attr("src"));
						//文章图片地址
						news.setWzimgurl(img.attr("src"));
						break;
					}
				}
				
				els_a = e_img.getElementsByTag("p");
				if( els_a != null ){
					for (int i = 0; i < els_a.size(); i++ ) {
						Element t = els_a.get(i);
						if(t == null )continue;
						if( i == 0 ){
							//文章标题
							//System.out.println(t.attr("title"));
							news.setWztitle(t.text());
							//文章类型
							news.setwZType("004-002");
						}else if(i == 1 ){
							//文章摘要
							//System.out.println(t.html());
							String wzbstrace = t.html();
							if( StringUtils.isNotBlank(wzbstrace)){
								if(wzbstrace.contains("&#xfffd;") ){
									wzbstrace = wzbstrace.replace("&#xfffd;", "券");
								}
							}
							//System.out.println(wzbstrace);
							news.setWzabstrace(wzbstrace);
						}else if( i == 2){
							//发布日期
							//System.out.println(t.text());
							news.setFabiaodate(UtilsDate.getStringToDate(UtilsDate.yyyy_MM_dd_HH_mm_ss_zhry, UtilsDate.getSystemDateToString(UtilsDate.yyyy)+"年"+t.text()));
						}else{
							break;
						}
					}
				}
				//System.out.println("------------------------------------------------------------>");
				//news.setKeyword("关键字");
				//news.setKeyword("扩展关键字");
				//news.setAuthor("作者");
				news.setShowOrder(0);
				//行业
				news.setIndustryType("");
				//状态编辑
				news.setStatus("005-001");
				news.setDataCompiledDate(new Date());
				readHtml_eastmoney_ContentBody(news,outtime);
				list_newsinfo.add(news);
			}
			break;
		}
		//System.out.println(list_newsinfo.size());
		logger.info("end eastmoney newsinfo data.....total "+list_newsinfo.size()+"");
		return list_newsinfo;
	}
	
	
	/**
	 * 读取文被内容,来源
	 * @param path
	 * @throws Exception
	 */
	public static void readHtml_eastmoney_ContentBody(NewsInfo news,int outtime)throws Exception{
		if( news == null ) return ;
		logger.info("eastmoney wzaddress:"+news.getNetaddress());
		Document doc = UtilsCollect.getDocument(news.getNetaddress(), outtime);
		if( doc != null ){
			//System.out.println(doc.html());
			Element e_root = doc.getElementById("ContentBody");
			if(e_root != null ){
				//System.out.println(e_root.html());
				//来源
				news.setLaiyuan("东财网");
				Element e_first = e_root.firstElementSibling();
				//System.out.println(e_first.html());
				if(e_first != null ){
					Element e_last = e_first.nextElementSibling();
					//System.out.println(e_last.html());
					if( e_last != null ){
						//来源
						/*Elements e_lalyuan = e_last.getElementsByTag("img");
						//System.out.println(e_lalyuan.attr("src"));
						if( e_last != null ){
							news.setLaiyuan(e_lalyuan.attr("src"));
						}*/
						
						//作者
						Elements e_author = e_last.getElementsMatchingOwnText("作者：");
						if( e_author != null && e_author.text()!= null){
							//System.out.println(e_author.html());
							news.setAuthor(e_author.text().replace("作者：", ""));
						}
						if( StringUtils.isBlank(news.getAuthor())){
							news.setAuthor("佚名");
						}
					}
				}
				//摘要(删除)
				removeElements(e_root.getElementsByClass("c_review"));
				//iframe广告(删除)
				removeElements(e_root.getElementsByClass("reading"));
				//删除编辑
				removeElements(e_root.getElementsByAttributeValue("style", "text-align:right; font-size:12px; color:#666;"));
				
				//处理内容中的a标签链接
				//updateAttrElements(e_root.getElementsByTag("a"),"href", "javascript:;");
				deleteHtmlTag(e_root.getElementsByTag("a"));
				
				//内容
				//System.out.println(e_root.html());
				news.setContent(e_root.html().replace("&#xfffd;", "券").getBytes("gbk"));
				//System.out.println(e_root.text());
				/*int lastindex = e_root.text().lastIndexOf("(责任编辑");
				if( lastindex == -1 ){
					news.setContent(e_root.text().getBytes("gbk"));
				}else{
					news.setContent(e_root.text().substring(0,lastindex ).getBytes("gbk"));
				}*/
				//System.out.println(new String(news.getContent()));
				
				
				
			}
		}
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
	
	

	public static final String str_title_start = "<div class=\"newText new\">                   <h1>";
	public static final String str_title_end = "</h1>";
	public static final String str_date_start = "<div class=\"Info\">	<span>";
	public static final String str_date_end = "</span>";
	public static final String str_zhaiyao_start = "<div id=\"ContentBody\" class=\"Body\">			<div class=\"c_review\">";
	public static final String str_zhaiyao_end = "</div>";
	public static final String str_content_end = "<p style=\"text-align:right; font-size:12px; color:#666;\">";
	public static void readHtmlSubstring(String path) throws Exception{
		if( StringUtils.isBlank(path)) return ;
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		InputStream in = conn.getInputStream();
		BufferedReader bf = new BufferedReader(new InputStreamReader(in,"gb2312-80"));
		StringBuilder sb = new StringBuilder();
		while(bf.read()!=-1){
			sb.append(bf.readLine());
		}
		String s_substring = sb.substring(sb.indexOf(str_title_start)+str_title_start.length());
		String s_title = s_substring.substring(0,s_substring.indexOf(str_title_end));
		System.out.println(s_title);
		s_substring =  s_substring.substring(s_substring.indexOf(str_title_end)+str_title_end.length());
		String s_date =  s_substring.substring(s_substring.indexOf(str_date_start)+str_date_start.length(),s_substring.indexOf(str_date_end));
		s_substring =  s_substring.substring(s_substring.indexOf(str_date_end)+str_date_end.length());
		s_substring =  s_substring.substring(s_substring.indexOf(str_zhaiyao_start));
		System.out.println(s_date);
		String s_zhaiyao =  s_substring.substring(str_zhaiyao_start.length(),s_substring.indexOf(str_zhaiyao_end));
		s_substring =  s_substring.substring(s_substring.indexOf(str_zhaiyao_end)+str_zhaiyao_end.length());
		System.out.println(s_zhaiyao);
		String s_content =  s_substring.substring(0,s_substring.indexOf(str_content_end));
		System.out.println(s_content);
		//System.out.println(sb);
		//s_substring =  s_substring.substring(s_substring.indexOf(str_content_end));
		//System.out.println(s_substring);
		
	}
	
	
	public enum EnumEastmoney{
		wz_title,
		wz_content
	}
}
