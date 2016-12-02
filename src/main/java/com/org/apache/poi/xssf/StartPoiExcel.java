package com.org.apache.poi.xssf;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.utils.UtilsFile;
import com.utils.UtilsUUID;

public class StartPoiExcel {
	public static void main(String[] args) throws Exception {
		x4();
	}
	

	/**
	 * 证监行业分类
	 * @throws Exception
	 */
	public static void x4()throws Exception{
		String[] s = {"证监会行业分类"};
		String path = "C:\\Users\\huage\\Desktop\\app\\zjhhyfl.xlsx";
		for (int x = 0; x < s.length; x++) {
			String sheelname = s[x];
			List<ArrayList<Object>> list = new ExcelReader().processExcel(path, sheelname);
			if (list == null || list.size() == 0) {
				System.out.println("EXCEL null");
				return;
			}
			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO `table` ");
			//sb.append("INSERT INTO `yb_y_z_investment` ");
			for (int i = 0; i < list.size(); i++) {
				ArrayList<Object> arrayList = list.get(i);
				if( arrayList != null && arrayList.size()>0){
					sb.append("\n select '"+UtilsUUID.getUUID()+"',");
					for (int j = 0; j < arrayList.size(); j++) {
						String key = arrayList.get(j) != null ?arrayList.get(j) .toString():"";
						sb.append(" '"+key+"' ");
						if( arrayList.size()>(j+1)){
							sb.append(",");
						}
					}
					if( list.size()>(i+1)){
						sb.append("\n union ");
					}
				}
			}
			File nfile = new File("C:\\Users\\huage\\Desktop\\app\\"+sheelname+".sql");
			if (!nfile.exists()) {
				nfile.createNewFile();
			}
			FileWriter fileWriter = new FileWriter(nfile, true);
			fileWriter.write(sb.toString());
			fileWriter.flush();
			fileWriter.close();
		}
		
		System.out.println("------------------>数据完成");
		
	}
	
	
	/**
	 * 投资、管理行业分类
	 * @throws Exception
	 */
	public static void x3()throws Exception{
		String[] s = {"管理型","投资型"};
		String path = "C:\\Users\\huage\\Desktop\\app\\F1467.xlsx";
		for (int x = 0; x < s.length; x++) {
			String sheelname = s[x];
			List<ArrayList<Object>> list = new ExcelReader().processExcel(path, sheelname);
			if (list == null || list.size() == 0) {
				System.out.println("EXCEL null");
				return;
			}
			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO `table` ");
			//sb.append("INSERT INTO `yb_y_z_investment` ");
			for (int i = 3; i < list.size(); i++) {
				ArrayList<Object> arrayList = list.get(i);
				if( arrayList != null && arrayList.size()>0){
					sb.append("\n select '"+UtilsUUID.getUUID()+"',");
					for (int j = 2; j < arrayList.size(); j++) {
						String key = arrayList.get(j) != null ?arrayList.get(j) .toString():"";
						sb.append(" '"+key+"' ");
						if( arrayList.size()>(j+1)){
							sb.append(",");
						}
					}
					if( list.size()>(i+1)){
						sb.append("\n union ");
					}
				}
			}
			File nfile = new File("C:\\Users\\huage\\Desktop\\app\\"+sheelname+".sql");
			if (!nfile.exists()) {
				nfile.createNewFile();
			}
			FileWriter fileWriter = new FileWriter(nfile, true);
			fileWriter.write(sb.toString());
			fileWriter.flush();
			fileWriter.close();
		}
		
		System.out.println("------------------>数据完成");
	
		
	}

	/**
	 * 读取excel中的代码，简称，时间，市值，收盘价，总股本 用于记录行情信息的历史数据
	 * 
	 */
	public static void x2() throws Exception {
		String path = "C:\\Users\\huage\\Desktop\\总市值_11.30（近一年）";
		File file = new File(path);
		if (!file.exists())
			return;
		List<String> lists = new ArrayList<String>();
		UtilsFile.listFile(file, lists);
		System.out.println("------------------>数据开始");
		for (int i = 0; i < lists.size(); i++) {
			List<ArrayList<Object>> objList = new ExcelReader().processExcel(lists.get(i), "sheet1");
			if (objList == null || objList.size() == 0) {
				System.out.println("EXCEL null");
				return;
			}
			File nfile = new File(file.getParent() + "\\app\\1234\\20151201_" + i + ".txt");
			if (!nfile.exists()) {
				nfile.createNewFile();
			}
			FileWriter fileWriter = new FileWriter(nfile, true);
			for (int j = 0; j < objList.size(); j++) {
				ArrayList<Object> list = objList.get(j);
				if (list == null)continue;
				String stockCode = list.get(0).toString().replace(".OC", "");
				String stockName = list.get(1).toString();
				String date = list.get(2).toString();
				String zgb = list.get(5).toString();
				String s = "INSERT INTO `security_info_history` (`idStr`, `XXZQDM`, `XXZQJC`,`XXZGB`, `mdate`) ";
				s += " VALUES ";
				s += "('20151201_"+i+"_" + j + "','" + stockCode + "','" + stockName + "','" + zgb + "','" + date + "');";
				fileWriter.write(s + "\n");
			}
			fileWriter.flush();
			fileWriter.close();
			System.out.println("------------------>" + objList.size());
		}
		System.out.println("------------------>数据完成");
	}

	public static void x1() {
		String path = "C:\\Users\\huage\\Desktop\\app\\20150922.xlsx";
		String sheetName = "sheet0";
		List<ArrayList<Object>> objList = new ExcelReader().processExcel(path,
				sheetName);
		if (objList == null || objList.size() == 0) {
			System.out.println("EXCEL null");
			return;
		}
		Map<String, Integer> map = new HashMap<String, Integer>();
		int j = 1;
		for (int i = 0; i < objList.size(); i++) {
			ArrayList<Object> arrayList = objList.get(i);
			if (arrayList == null)
				continue;
			if (arrayList.size() > 2) {
				String s = (String) arrayList.get(2);
				if (s != null && s.length() > 0) {
					Integer x = map.get(s);
					if (x == null) {
						map.put(s, j++);
					}
				}
			}
			if (arrayList.size() > 3) {
				String s = (String) arrayList.get(3);
				if (s != null && s.length() > 0) {
					Integer x = map.get(s);
					if (x == null) {
						map.put(s, j++);
					}
				}
			}

			if (arrayList.size() > 4) {
				String s = (String) arrayList.get(4);
				if (s != null && s.length() > 0) {
					Integer x = map.get(s);
					if (x == null) {
						map.put(s, j++);
					}
				}
			}
		}
		/*
		 * for (String key : map.keySet()) { Integer i = map.get(key);
		 * System.out.println(i+"	"+key); }
		 */

		int y = 1;
		for (ArrayList<Object> arrayList : objList) {
			if (arrayList == null)
				continue;
			String code = arrayList.get(0).toString().replace(".OC", "");
			if (arrayList.size() > 2) {
				String s = (String) arrayList.get(2);
				if (s != null && s.length() > 0) {
					Integer x = map.get(s);
					System.out.println(y++ + "	" + code + "	" + x);
				}
			}
			if (arrayList.size() > 3) {
				String s = (String) arrayList.get(3);
				if (s != null && s.length() > 0) {
					Integer x = map.get(s);
					System.out.println(y++ + "	" + code + "	" + x);
				}
			}

			if (arrayList.size() > 4) {
				String s = (String) arrayList.get(4);
				if (s != null && s.length() > 0) {
					Integer x = map.get(s);
					System.out.println(y++ + "	" + code + "	" + x);
				}
			}
		}

	}
}
