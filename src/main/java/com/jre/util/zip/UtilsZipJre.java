package com.jre.util.zip;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.jre.io.UtilsIoJre;

/**
 * zip文件处理(压缩处理时通过windows系统压缩的由于编码不是utf8的所以将导致中文抛出异常的情况)
 * @author huage
 *
 */
public class UtilsZipJre {

	public static void main(String[] args) {
		zipFileExtract("C:\\Users\\huage\\Desktop\\test\\111\\111.zip","C:\\Users\\huage\\Desktop\\test\\test");
		//zipDecompressingExtract("C:\\Users\\huage\\Desktop\\test\\111\\111.zip","C:\\Users\\huage\\Desktop\\test\\test");
		//zipCompressingExtract("C:\\Users\\huage\\Desktop\\test\\111\\test.zip",new File("C:\\Users\\huage\\Desktop\\test\\test"));

	}
	
	/**
	 * 解压
	 * @param path
	 * @param pathExtract
	 */
	@SuppressWarnings("unchecked")
	public static void zipFileExtract(String path, String pathExtract){
		ZipFile zipfile = null;
		try {
			zipfile = new ZipFile(path);
			Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>)zipfile.entries();
			if( entries!=null ){
				ZipEntry entry ;
				File file;  
				BufferedInputStream bis = null;  
				while( entries.hasMoreElements()){  
					entry = entries.nextElement();
					if(entry.isDirectory())continue; 
					file=new File(pathExtract,entry.getName());  
	                if(!file.exists()){  
	                    (new File(file.getParent())).mkdirs();  
	                } 
	                bis = new BufferedInputStream(new FileInputStream(file));
					UtilsIoJre.converWriteIO(bis, file);
	                //System.out.println(fout+"解压成功");    
		        }
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			UtilsIoJre.closeIO(zipfile);
		}
		
	}
	

	/**
	 * zip解压(本地)（官方自带zip解压无法处理中文）
	 * 
	 * @param path
	 *            :zip文件地址
	 * @param pathExtract
	 *            ：解压地址
	 */
	public static void zipDecompressingExtract(String path, String pathExtract) {
		ZipInputStream zipinput = null;
		BufferedInputStream bininput = null;
		try {
			zipinput = new ZipInputStream(new FileInputStream(path));
			bininput = new BufferedInputStream(zipinput);

			
			ZipEntry entry ;
			File fout = null;
			while ((entry = zipinput.getNextEntry()) != null) {
				if(entry.isDirectory())continue; 
				fout=new File(pathExtract,entry.getName());  
                if(!fout.exists()){  
                    (new File(fout.getParent())).mkdirs();  
                } 
				UtilsIoJre.converWriteIO(bininput, fout);
                //System.out.println(fout+"解压成功");    
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			UtilsIoJre.closeIO(bininput,zipinput);
		}
		System.out.println("解压完成");
	}

	/**
	 * zip压缩(本地)
	 * 
	 * @param zipFileName
	 * @param inputFile
	 * @throws Exception
	 */
	public static void zipCompressingExtract(String zipFileName, File inputFile) {
		ZipOutputStream out = null;
		BufferedOutputStream bo = null;
		try {
			out = new ZipOutputStream(new FileOutputStream(zipFileName));
			bo = new BufferedOutputStream(out);
			zipCompressing(out, inputFile, bo);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			UtilsIoJre.closeIO(bo,out);
		}
		System.out.println("压缩完成");
	}

	/**
	 * zip压缩
	 * 
	 * @param out
	 * @param file
	 * @param base
	 * @param bo
	 * @throws Exception
	 */
	private static void zipCompressing(ZipOutputStream out, File file, BufferedOutputStream bo) throws Exception {
		if (file.isDirectory()) {
			File[] fl = file.listFiles();
			if (fl.length == 0) {
				out.putNextEntry(new ZipEntry(file.getName()));
			}
			for (int i = 0; i < fl.length; i++) {
				zipCompressing(out, fl[i], bo);
			}
		} else {
			out.putNextEntry(new ZipEntry(file.getName() ));
			UtilsIoJre.converReadIO(bo, file);
		}
	}
}
