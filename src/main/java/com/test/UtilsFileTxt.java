package com.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jdbc.UtilsJDBCCreateJavaClassFile;
import com.utils.UtilsDate;

public class UtilsFileTxt {
	public static void main(String[] args) throws Exception {
		_select_text();
	}
	

	/**
	 * 修改文件名称
	 * @throws Exception
	 */
	public static void _updatefilename()throws Exception {

		String[][] xs = { { "008-001", "480x800" }, { "008-002", "640x960" },
				{ "008-003", "640x1136" }, { "008-004", "720x1280" },
				{ "008-005", "750x1334" }, { "008-006", "828x1472" },
				{ "008-007", "1080x1920" } };
		String path = "C:\\Users\\huage\\Desktop\\app\\images";
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < xs.length; i++) {
			map.put(xs[i][1], xs[i][0]);
		}
		File file = new File(path);
		if (!file.exists())
			return;
		File[] files = file.listFiles();
		if (files == null || files.length == 0)
			return;
		StringBuilder sb = new StringBuilder();
		for (File f : files) {
			if (f == null || !f.exists())
				continue;
			String n = map.get(f.getName());
			File[] filesChild = f.listFiles();
			File fn = null;
			for (int i = 0; i < filesChild.length; i++) {
				sb.append("INSERT INTO `guided_page_images` ");
				sb.append("(`idStr`, `imagename`, `status`, `resolution`, `sequential`, `dataCompiledDate`) ");
				sb.append(" VALUES");
				sb.append("( ");
				File fc = filesChild[i];
				String pufix = fc.getName().substring(
						fc.getName().lastIndexOf("."));
				fn = new File(path + "\\" + n + "-" + i + pufix);
				fc.renameTo(fn);

				sb.append("'"
						+ n
						+ "-"
						+ i
						+ "','"
						+ fn.getName()
						+ "','1','"
						+ n
						+ "','"
						+ i
						+ "','"
						+ UtilsDate
								.getSystemDateToString(UtilsDate.yyyy_MM_dd_HH_mm_ss)
						+ "'");
				sb.append("); ");
				sb.append("\n");
			}
		}
		System.out.println(sb.toString());
	
	}
	

	/**
	 * 
	 * 读取text中的内容并替换响应的关键字打印
	 * 
	 */
	public static void _select_text()throws Exception {
		File file = new File("C:\\Users\\huage\\Desktop\\usertest.txt");
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String line = "";
		Map<Integer, List<String>> map = new HashMap<Integer, List<String>>();
		int i = 0;
		while ((line = br.readLine()) != null) {
			if (i == 0) {
				i++;
			} else {
				i = 0;
			}
			List<String> list = map.get(i);
			if (list == null) {
				list = new ArrayList<String>();
				map.put(i, list);
			}
			list.add(line);
		}
		br.close();
		fr.close();

		List<String> list = x1();

		List<String> list0 = map.get(0);
		List<String> list1 = map.get(1);
		System.out.println("开始了");
		UtilsJDBCCreateJavaClassFile c = new UtilsJDBCCreateJavaClassFile();
		Statement state = c.getConnection().createStatement();
		
		
		for (int j = 0; j < list0.size(); j++) {
			for (String s : list) {
				s = s.replace("xxxxx", list0.get(j));
				s = s.replace("yyyyy", list1.get(j));
				ResultSet set = state.executeQuery(s);
				int x = 0 ;
				while(set.next()){
					x = set.getInt("c");
					break;
				}
				if( x >0){
					System.out.println(s);
				}
			}
			System.out.println("-->");
		}
		
	}

	public static List<String> x1() throws Exception {
		File file = new File("C:\\Users\\huage\\Desktop\\sqlTest.txt");
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		List<String> list = new ArrayList<String>();
		String line = "";
		while ((line = br.readLine()) != null) {
			list.add(line);
		}
		br.close();
		fr.close();
		return list;
	}
}
