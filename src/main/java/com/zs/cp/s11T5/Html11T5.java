package com.zs.cp.s11T5;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.jsoup.html.HtmlBase;
import com.utils.UtilsDate;
import com.utils.UtilsFile;
import com.utils.UtilsUUID;
import com.zs.cp.model.Model11T5;

public class Html11T5  extends HtmlBase{


	public static void main(String[] args) throws Exception{
		Html11T5 base = new Html11T5();
		base.whileAllDate("2009-11-11");
	}
	
	
	public void whileAllDate(String startDate) throws Exception{
		Date date = UtilsDate.getStringToDate("yyyy-MM-dd", startDate);
		List<Model11T5> list = new ArrayList<Model11T5>();
		String url = this.url;
		while(!UtilsDate.isEqualsYYYYMMDD(date, new Date())){
			System.out.println("---->:"+startDate+";"+list.size());
			 this.url = url+"?d="+startDate;
			list.addAll(this.readHtml());
			date = UtilsDate.getBeforDay(1, date);
			startDate = UtilsDate.getDateToString(date,"yyyy-MM-dd");
		}
		this.printlnInsert(list);
	}
	
	/**
	 * @param list
	 * @throws Exception
	 */
	public void printlnInsert(List<Model11T5> list)throws Exception{
		if(list != null && list.size()>0){
			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO cp_jx_11t5 ");
			Model11T5 emdm = list.get(list.size()-1);
			for (Model11T5 m : list) {
				sb.append("\nselect '"+m.getIdStr()+"', '"+m.getV_h1()+"', '"+m.getV_h2()+"', '"+m.getV_h3()+"', '"+m.getV_h4()+"', '"+m.getV_h5()+"', '"+m.getQh()+"', '"+m.getDate()+"' ");
				if( emdm != m){
					sb.append("\n union");
				}else{
					sb.append(";");
				}
			}
			//System.out.println(sb.toString());
			UtilsFile.add("C:\\Users\\huage\\Desktop\\app\\ssq_history.sql", sb.toString());
			
		}
	}

	
	/**
	 * 只查询标题中含有"新三板"字样的，才读取
	 * @param outtime
	 * @return
	 * @throws Exception
	 */
	public List<Model11T5> readHtml()throws Exception{
		List<Model11T5> list = new ArrayList<Model11T5>();
		Document doc = super.getDocument();
		if( doc == null )return list;
		Element element = doc.getElementById("draw_list");
		if( element == null)return list;
		Elements eles = element.getElementsByClass("bgcolor1");
		if( eles == null)return list;
		for (Element e : eles) {
			if( e == null )continue;
			try {
				Model11T5 model = getModel11T5(e.getElementsByTag("td"));
				if(model == null )continue;
				list.add(model);
			} catch (Exception e2) {
				//e2.printStackTrace();
			}
		}
		return list;
	}
	
	public Model11T5 getModel11T5(Elements es) throws Exception{
		if( es == null || es.size()<4)return null;
		Model11T5 model = new Model11T5();
		model.setIdStr(UtilsUUID.getUUID());
		model.setDate(es.get(0).text());
		model.setQh(es.get(1).text());
		Element els = es.get(2);
		Elements els_s = els.getElementsByClass("ball_1");
		model.setV_h1(els_s.get(0).text());
		model.setV_h2(els_s.get(1).text());
		model.setV_h3(els_s.get(2).text());
		model.setV_h4(els_s.get(3).text());
		model.setV_h5(els_s.get(4).text());
		return model;
	}
	

	public String url = "http://baidu.lecai.com/lottery/draw/list/22";
	@Override
	public String getUrl() {
		return url;
	}

}
