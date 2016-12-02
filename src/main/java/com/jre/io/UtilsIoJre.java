package com.jre.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * io流处理
 * @author huage
 *
 */
public class UtilsIoJre {

	/**
	 * 将file写入BufferedOutputStream中
	 * @param bo
	 * @param file
	 * @throws Exception
	 */
	public static void converReadIO(BufferedOutputStream bo,File file) throws Exception{
		FileInputStream in = new FileInputStream(file);
		BufferedInputStream bi = new BufferedInputStream(in);
		int b;
		while ((b = in.read()) != -1) {
			bo.write(b);
		}
		closeIO(bi,in);
		bo.flush();//清空缓存
	}
	
	/**
	 * 将BufferedInputStream写入file中
	 * @param bo
	 * @param file
	 * @throws Exception
	 */
	public static void converWriteIO(BufferedInputStream bininput,File file) throws Exception{
		FileOutputStream out = new FileOutputStream(file);
		BufferedOutputStream bout = new BufferedOutputStream(out);
		int b;
		while ((b = bininput.read()) != -1) {
			bout.write(b);
		}
		closeIO(bout,out);
		bout.flush();//清空缓存
	}
	
	
	/**
	 * 关闭io
	 * @param cl
	 */
	public static void closeIO(AutoCloseable... cl){
		if( cl == null || cl.length == 0 )return;
		for (AutoCloseable c : cl) {
			try {
				c.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
