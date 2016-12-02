package com.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UtilsFileURLUpload {


	public static void main(String[] args) {
		try {
			downLoadFromUrl(
					"http://file.neeq.com.cn/upload/disclosure/2015/2015-06-04/1433406483_511925.pdf",
					"百度.pdf", "C:\\Users\\huage\\Desktop");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**
	 * 从网络Url中下载文件
	 * 
	 * @param urlStr
	 * @param fileName
	 * @param savePath
	 * @throws IOException
	 */
	public static void downLoadFromUrl(String urlStr, String fileName,
			String savePath) throws IOException {
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		// 设置超时间为3秒
		conn.setConnectTimeout(3 * 1000);
		// 防止屏蔽程序抓取而返回403错误
		conn.setRequestProperty("User-Agent",
				"Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

		// 得到输入流
		InputStream inputStream = conn.getInputStream();
		// 获取自己数组
		byte[] buffer = new byte[1024];
		int len = 0;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while ((len = inputStream.read(buffer)) != -1) {
			bos.write(buffer, 0, len);
		}
		bos.close();
		
		buffer = bos.toByteArray();
		// 文件保存位置
		File saveDir = new File(savePath);
		if (!saveDir.exists()) {
			saveDir.mkdir();
		}
		File file = new File(saveDir + File.separator + fileName);
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(buffer);
		if (fos != null) {
			fos.close();
		}
		if (inputStream != null) {
			inputStream.close();
		}

		System.out.println("info:" + url + " download success");

	}

	/**
	 * 从输入流中获取字节数组
	 * 
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public static byte[] readInputStream(InputStream inputStream)
			throws IOException {
		byte[] buffer = new byte[1024];
		int len = 0;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while ((len = inputStream.read(buffer)) != -1) {
			bos.write(buffer, 0, len);
		}
		bos.close();
		return bos.toByteArray();
	}
	

	public static void printReader() {
		String path = "C:\\Users\\huage\\Desktop\\20150628\\5_5.txt";
		java.io.BufferedReader reader;
		try {
			reader = new java.io.BufferedReader(new java.io.FileReader(path));
			String s = reader.readLine();
			System.out.println("start link...");
			String str = "";
			while (s != null) {
				str+=s;
				s=reader.readLine(); 
			}
			System.out.println("end link...");
			int y = 29;
			String name = "http://pdf.dfcfw.com/pdf/";
			int i = str.indexOf(name);
			int x = 1;
			while(i>0){
				s = str.substring(i,i+name.length()+y);
				System.out.println(s);
				str = str.substring(i+name.length()+y);
				i = str.indexOf(name);
				x++;
			}
			System.out.println(x);
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
