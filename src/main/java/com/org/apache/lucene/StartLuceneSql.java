package com.org.apache.lucene;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jdbc.UtilsJDBC;
import com.jre.lang.UtilsObject;

public class StartLuceneSql extends BaseLucene{

	public static void main(String[] args) throws Exception {
		StartLuceneSql utils = new StartLuceneSql();
		//utils.createIndexSql();
		//utils.findDirectoryAllField();
		utils.findDirectoryField();
	}
	
	
	
	/**
	 * sql创建索引
	 * 
	 */
	public void createIndexSql() {
		UtilsJDBC jdbc = new UtilsJDBC("root","123456","com.mysql.jdbc.Driver","jdbc:mysql://192.168.0.245:3306/nsanban0324?characterEncoding=UTF-8&characterSetResults=UTF-8&zeroDateTimeBehavior=convertToNull");
		jdbc.getConnection();
		String sql = "select * from news_info ";
		List<Map<String, Object>> list =  null;
		try {
			list = jdbc.findModeResult(sql, null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if( list == null || list.isEmpty()) return;
		
		List<Map<String, String>> list2 =  new ArrayList<Map<String,String>>(list.size());
		Map<String, String> m = null;
		for (Map<String, Object> map : list) {
			if( map == null || map.isEmpty() ) continue;
			m = new HashMap<String, String>(map.size());
			list2.add(m);
			for (String key : map.keySet()) {
				m.put(key, UtilsObject.conversionObjectToString(map.get(key),"gbk"));
			}
		}
		super.insertIndex(list2);
		System.out.println("----->索引创建完成."+list2.size());
	}
	

	
	/**
	 * 查询目录下的所有索引数据
	 * 
	 */
	public void findDirectoryAllField(){
		List<Map<String, String>> list =  super.queryDirectoryAllField();
		System.out.println("----->查询索引完成."+list.size());
		for (Map<String, String> map : list) {
			System.out.println(map);
		}
	}
	
	/**
	 * 查询key=value
	 * 
	 */
	public void findDirectoryField(){
		String[] key = {"GuPiaoCode"};
		String[] value = {"832924"};
		List<Map<String, String>> list =  super.queryDirectoryField(key,value);
		System.out.println("----->查询索引完成."+list.size());
		for (Map<String, String> map : list) {
			System.out.println(map);
		}
	}
	
	

}
