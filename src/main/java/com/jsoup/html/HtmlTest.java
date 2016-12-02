package com.jsoup.html;

import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;

import com.jsoup.em.CollectType;
import com.jsoup.model.NewsInfo;

/**
 * 测试
 * @author huage
 *
 */
class HtmlTest extends HtmlBase{
	private static final Logger logger = Logger.getLogger(HtmlTest.class);
	
	public static void main(String[] args) throws Exception{
		HtmlTest sohu = new HtmlTest();
		sohu.readHtml();
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
		System.out.println(doc.html());
		
		return null;
	}
	



	@Override
	public String getUrl() {
		return CollectType.html_test.getUrl();
	}

}
