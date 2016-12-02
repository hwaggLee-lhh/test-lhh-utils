package com.jsoup.html;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.jsoup.config.ConfigJsoup;
import com.jsoup.em.CollectType;
import com.jsoup.model.NewsInfo;
import com.jsoup.utils.UtilsCollect;
import com.utils.UtilsDate;

/**
 * 读取cnfol(中金在线)中的新闻
 * 无摘要，大多无图片
 * @author huage
 *
 */
class HtmlCnfol {
	private static final Logger logger = Logger.getLogger(HtmlCnfol.class);
	
	public static void main(String[] args) throws Exception{
		int outtime = 5*1000;
		
		/*readHtml(outtime);*/
		
		NewsInfo news = new NewsInfo();
		news.setNetaddress("http://sbsc.stock.cnfol.com/xsbyw/20150907/21396189.shtml");
		readHtmlContentBody(news,outtime);
		
		/*URL url = new URL("http://sbsc.stock.cnfol.com/xsbyw/20150907/21396189.shtml");
		URLConnection c = url.openConnection();
		System.out.println(new String(UtilsCollect.readStream(c.getInputStream())));*/
	}
	
	
	
	/**
	 * 只查询标题中含有"新三板"字样的，才读取
	 * @param outtime
	 * @return
	 * @throws Exception
	 */
	public static List<NewsInfo> readHtml(int outtime)throws Exception{
		logger.info("start cnfol newsinfo data.....");
		Document doc = UtilsCollect.getDocument(CollectType.html_cnfol.getUrl(), outtime);
		if( doc == null )return null;
		//System.out.println(doc.html());
		Elements ele = doc.getElementsByClass("NewsLstItem");
		if( ele == null )return null;
		//System.out.println(ele.html());
		List<NewsInfo> list_newsinfo = new ArrayList<NewsInfo>();
		
		NewsInfo info = null;
		for (Element ele_ul : ele) {
			Elements ele_a = ele_ul.getElementsByTag("a");
			if( ele_a == null ) continue;
			for (Element ele_child : ele_a) {
				if( ele_child == null )continue;
				info = new NewsInfo();
				//System.out.println(ele_child.html());
				if( ele_child.html().contains("新三板")){
					info.setWztitle(ele_child.html());
					info.setwZType("004-002");
					info.setNetaddress(ele_child.attr("href"));
					info.setShowOrder(0);
					//行业
					info.setIndustryType("");
					//状态编辑
					info.setStatus("005-001");
					info.setDataCompiledDate(new Date());
					readHtmlContentBody(info, outtime);
					list_newsinfo.add(info);
				}else{
					logger.info("标题未含有“新三板”字样，不读取.标题："+ele_child.html()+";网址："+ele_child.attr("href"));
				}
			}
		}
		logger.info("end cnfol newsinfo data.....total "+list_newsinfo.size()+"");
		return list_newsinfo;
	}
	
	
	/**
	 * 读取文被内容,来源
	 * @param path
	 * @throws Exception
	 */
	private static void readHtmlContentBody(NewsInfo news,int outtime)throws Exception{
		if( news == null ) return ;
		logger.info("cnfol wzaddress:"+news.getNetaddress());
		Document doc = UtilsCollect.getDocument(news.getNetaddress(), outtime);
		if( doc != null ){
			//System.out.println(doc.html());
			Element e = doc.getElementById("source_baidu");
			//System.out.println(e.html());
			if( e != null ){//来源
				Elements es  = e.getElementsByClass("Mr10");
				if( es == null || es.isEmpty()){
					es = e.getElementsByTag("a");
				}
				if( es != null ){
					//System.out.println(es.html());
					news.setLaiyuan(es.html());
				}
				if( StringUtils.isBlank(news.getLaiyuan())){
					news.setLaiyuan("无");
				}
			}
			e = doc.getElementById("author_baidu");
			if( e != null ){//作者
				news.setAuthor(e.html().replace("作者：", "").replace("来源：", "").replace("编辑：", ""));
			}
			if( StringUtils.isBlank(news.getAuthor())){
				news.setAuthor("佚名");
			}
			if( StringUtils.isBlank(news.getAuthor())){
				news.setAuthor("无");
			}
			e = doc.getElementById("pubtime_baidu");
			if( e != null ){//时间
				news.setFabiaodate(UtilsDate.getStringToDate(UtilsDate.yyyy_MM_dd_HH_mm_ss, e.html().trim()));
			}
			e = doc.getElementById("Content");
			if( e != null ){
				//System.out.println(e.html());//内容
				Elements e_img = e.getElementsByTag("img");
				if( e_img != null ){//图片
					String filename = UtilsCollect.downLoadFile(e_img.attr("src"),ConfigJsoup.getProperty("uploadNewsTitleImgpath"));
					//System.out.println(e_img.attr("src"));
					if( filename != null ){
						news.setWzimgurl(filename);
						e_img.attr("src", "/web/image/newsTitleImg?fileName="+ filename);
					}
					//System.out.println(e_img);
				}

				news.setContent(e.html().getBytes("gbk"));//内容
				if(e.text().trim().length()>20){
					news.setWzabstrace(e.text().trim().substring(0, 20)+"...");
				}else{
					news.setWzabstrace(e.text().trim());
				}
			}
		}
	}
	

}
