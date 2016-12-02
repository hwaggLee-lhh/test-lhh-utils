package com.org.apache.ant;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import com.jre.io.UtilsIoJre;
import com.utils.UtilsFile;

public class UtilsZipAnt {

	public static void main(String[] args) {
		zipDecompressingExtract("C:\\Users\\huage\\Desktop\\test\\【PL37】刀戟戡魔錄480P-01【為布鳳歌】.rar","C:\\Users\\huage\\Desktop\\test\\test","gbk");
		//zipDecompressingExtractS("C:\\Users\\huage\\Desktop\\test\\111","C:\\Users\\huage\\Desktop\\test\\test","gbk");

	}
	
	
	/**
	 * 批量处理
	 * @param path
	 * @param pathExtract
	 * @param encoding
	 */
	public static void zipDecompressingExtractS(String path, String pathExtract,String encoding){
		List<String> fileList = new ArrayList<String>();
		UtilsFile.listFile(new File(path), fileList);
		for (String string : fileList) {
			//System.out.println(string);
			zipDecompressingExtract(string,pathExtract,encoding);
		}
	}
	
	
	
	/**
	 * 解压
	 * @param path:zip
	 * @param pathExtract：存放位置
	 * @param encoding：编码
	 */
	@SuppressWarnings("unchecked")
	public static void zipDecompressingExtract(String path, String pathExtract,String encoding){
		ZipFile zipFile = null;
		try {
			zipFile = new ZipFile(new File(path),encoding);
			Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>)zipFile.getEntries();
			if( entries != null ){
				ZipEntry entry = null;
				File file = null;
				BufferedInputStream bin = null;
				BufferedOutputStream bos = null;
				while(entries.hasMoreElements()){
					entry = entries.nextElement();
					if(entry.isDirectory())continue; 
					file=new File(pathExtract,entry.getName());  
	                if(!file.exists()){  
	                    (new File(file.getParent())).mkdirs(); 
	                }
	                bos = new BufferedOutputStream(new FileOutputStream(file));  
	                bin = new BufferedInputStream(zipFile.getInputStream(entry));  
	                UtilsIoJre.converWriteIO(bin, file);
				}
				bos.close();
				bin.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("解压完成");
	}
}
