package com.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

public class UtilsFile {
	
	public static void main(String[] args) throws Exception {
		updateFileName(new File("C:\\Users\\huage\\Desktop\\app\\images"), "1234", false);
	}

	
	/**
	 * 修改文件名称或者目录名称
	 * @param oldFile:旧文件或目录
	 * @param newsFilename:新文件名称
	 * @param isPx:如果是文件，是否使用旧文件的后缀,true为使用旧文件后缀，如果是目录则固定值false
	 * @throws Exception
	 */
	public static void updateFileName(File oldFile,String newsFilename,boolean isPx) throws Exception {
		if( oldFile == null )return;
		String newPath = oldFile.getPath().replace(oldFile.getName(), "") + newsFilename ;
		if( oldFile.isDirectory()){//目录
			String name = oldFile.getName();
			if(  name.lastIndexOf(".")>-1 ){
				newPath += name.substring(name.lastIndexOf("."));
			}
			File fn = new File(newPath);
			oldFile.renameTo(fn);
		}else{//文件
			if( isPx ){
				String name = oldFile.getName();
				if(  name.lastIndexOf(".")>-1 ){
					newPath += name.substring(name.lastIndexOf("."));
				}
			}
			File fn = new File(newPath);
			oldFile.renameTo(fn);
		}
	}

	/**
	 * 读取文件目录，包括文件夹中的子文件
	 * @param f
	 * @param fileList
	 */
	public static void listFile(File f, List<String> fileList) {
		if (f.isDirectory()) {
			File[] files = f.listFiles();
			for (int i = 0; i < files.length; i++) {
				listFile(files[i], fileList);
			}
		} else {
			fileList.add(f.getAbsolutePath());
		}
	}

	/**
	 * 在文件的最后追加内容
	 * @param file
	 * @param conent
	 */
	public static void add(String file, String conent) {
		BufferedWriter out = null;
		try {
			File f = new File(file);
			if(!f.exists()){
				f.createNewFile();
			}
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f, true)));
			out.write(conent);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 读取文件内容
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static String readFile(String filePath) throws Exception {
		@SuppressWarnings("resource")
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(filePath)));
		StringBuffer content = new StringBuffer();
		String str = null;
		while ((str = bufferedReader.readLine()) != null) {
			content.append(str).append("\n");
		}
		return content.toString();
	}
	
}
