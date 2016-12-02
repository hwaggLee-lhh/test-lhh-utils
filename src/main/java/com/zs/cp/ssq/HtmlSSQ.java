package com.zs.cp.ssq;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.jsoup.html.HtmlBase;
import com.utils.UtilsFile;
import com.utils.UtilsUUID;
import com.zs.cp.model.ModelSSQ;

public class HtmlSSQ extends HtmlBase{
	private static final Logger logger = Logger.getLogger(HtmlSSQ.class);
	
	public static void main(String[] args) throws Exception{
		HtmlSSQ base = new HtmlSSQ();
		List<ModelSSQ> list = base.readHtml();
		base.printlnInsert(list);
	}
	
	
	
	/**
	 * 只查询标题中含有"新三板"字样的，才读取
	 * @param outtime
	 * @return
	 * @throws Exception
	 */
	public List<ModelSSQ> readHtml()throws Exception{
		List<ModelSSQ> list = new ArrayList<ModelSSQ>();
		logger.info("start ata.....");
		Document doc = super.getDocument();
		if( doc == null )return list;
		Element element = doc.getElementById("chartsTable");
		if( element == null)return list;
		Elements eles = element.getElementsByClass("tbody_tr");
		if( eles == null)return list;
		ModelSSQ model = null;
		for (Element e : eles) {
			if( e == null )continue;
			Elements es = e.getElementsByTag("td");
			if( es == null || es.size()<15)continue;
			model = new ModelSSQ();
			model.setIdStr(UtilsUUID.getUUID());
			model.setQh(es.get(0).text());
			model.setDate(es.get(1).text());
			model.setV_h1(es.get(2).text());
			model.setV_h2(es.get(3).text());
			model.setV_h3(es.get(4).text());
			model.setV_h4(es.get(5).text());
			model.setV_h5(es.get(6).text());
			model.setV_h6(es.get(7).text());
			model.setV_l7(es.get(14).text());
			list.add(model);
		}
		logger.info("end data.....total "+list.size()+"");
		return list;
	}
	
	
	/**
	 * @param list
	 * @throws Exception
	 */
	public void printlnInsert(List<ModelSSQ> list)throws Exception{
		if(list != null && list.size()>0){
			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO cp_ssq ");
			ModelSSQ emdm = list.get(list.size()-1);
			for (ModelSSQ m : list) {
				sb.append("\nselect '"+m.getIdStr()+"', '"+m.getV_h1()+"', '"+m.getV_h2()+"', '"+m.getV_h3()+"', '"+m.getV_h4()+"', '"+m.getV_h5()+"', '"+m.getV_h6()+"', '"+m.getV_l7()+"', '"+m.getQh()+"', '"+m.getDate()+"' ");
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



	public String getUrl() {
		return "http://888.qq.com/info/chart/ssq/index.php?op=lssx&from=03001&to=15146";
	}
	
}
