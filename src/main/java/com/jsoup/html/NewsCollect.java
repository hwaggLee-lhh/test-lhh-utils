package com.jsoup.html;

import java.util.List;

import com.jsoup.em.CollectType;

public class NewsCollect {

	/**
	 * 新闻采集
	 * @param type
	 * @param outtime
	 * @return
	 * @throws Exception
	 */
	public static List<?> getNewsInfoList(String collectType,int outtime)throws Exception{
		CollectType type = CollectType.parseByKeyEqualsIgnoreCase(collectType);
		if(type == null )return null;
		HtmlBase base = null;
		switch(type){
			case html_eastmoney:
				return HtmlEastmoney.readHtml_eastmoney_artitileList_path(outtime);
			case html_cnfol:
				return HtmlCnfol.readHtml(outtime);
			case html_sohu:
				base = new HtmlSohu();
				break;
			case html_21jingji:
				base = new Html21jingji();
				break;
			default:
				break;
		}
		if( base != null ){
			return base.readHtml();
		}
		return null;
	}
}
