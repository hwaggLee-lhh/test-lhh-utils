package com.jsoup.em;

import org.apache.commons.lang.StringUtils;

public enum CollectType {
	html_eastmoney("006-001","http://stock.eastmoney.com/sanban.html"),//东方财富
	html_cnfol("006-002","http://sbsc.stock.cnfol.com/xsbyw/"),//中金在线
	html_sohu("006-003","http://stock.sohu.com/xinsanban/index.shtml"),//搜狐
	html_21jingji("006-004","http://www.21jingji.com/channel/money/changwaishichang/"),//21经济网
	html_test("006-000","http://mp.weixin.qq.com/s?__biz=MzA5Njc1NzEwNA==&mid=211612072&idx=1&sn=1ad3bda2b682d3999dcdf9f146ef8a48&3rd=MzA3MDU4NTYzMw==&scene=6#rd")//21经济网
	;
	
	private String key;
	private String url;
	private CollectType(String key,String url){
		this.key = key;
		this.url = url;
	}
	public String getKey() {
		return key;
	}
	public String getUrl() {
		return url;
	}
	/**
	 * 根据Key(不区分大小写)查询collectType
	 * @param key
	 * @return
	 */
	public static CollectType parseByKeyEqualsIgnoreCase(String key){
		if( StringUtils.isBlank(key)) return null;
		CollectType[] values = CollectType.values();
		for (CollectType c : values) {
			if( c.key.equalsIgnoreCase(key)) return c;
		}
		return null;
	}
}
