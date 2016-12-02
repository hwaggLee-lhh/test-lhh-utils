package com.de.innosystec.unrar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.utils.UtilsFile;

import de.innosystec.unrar.Archive;
import de.innosystec.unrar.exception.RarException;
import de.innosystec.unrar.rarfile.FileHeader;

/**
 * RAR格式压缩文件解压工具类 不支持RAR格式压缩 支持中文,支持RAR压缩文件密码 依赖jar包 commons-io.jar
 * commons-logging.jar java-unrar-decryption-supported.jar gnu-crypto.jar
 */
public class UtilsRarDecompression {
	public static final String SEPARATOR = File.separator;
	
	
	public static void main(String[] args)  throws Exception{
		//unrar("C:\\Users\\huage\\Desktop\\test\\【PL37】刀戟戡魔錄480P-01【為布鳳歌】.rar","ifind6.com");
		unrarS("E:\\pl\\【PL40】霹雳迷城（40）【凤歌版】mkv（压缩包）","ifind6.com");
		System.out.println("完成");
	}
	

	
	/**
	 * 批量处理
	 * @param path
	 * @param pathExtract
	 * @param encoding
	 */
	public static void unrarS(String path, String password){
		List<String> fileList = new ArrayList<String>();
		UtilsFile.listFile(new File(path), fileList);
		for (String string : fileList) {
			//System.out.println(string);
			if( "rar".equalsIgnoreCase(string.substring(string.length()-3))){
				//System.out.println(string.substring(string.length()-3));
				try {
					unrar(string,password);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	

	// =============================== RAR Format
	// ================================
	/**
	 * 解压指定RAR文件到当前文件夹
	 * 
	 * @param srcRar
	 *            指定解压
	 * @param password
	 *            压缩文件时设定的密码
	 * @throws IOException
	 */
	public static void unrar(String srcRar, String password) throws IOException {
		unrar(srcRar, null, password);
	}

	/**
	 * 解压指定的RAR压缩文件到指定的目录中
	 * 
	 * @param srcRar
	 *            指定的RAR压缩文件
	 * @param destPath
	 *            指定解压到的目录
	 * @param password
	 *            压缩文件时设定的密码
	 * @throws IOException
	 */
	public static void unrar(String srcRar, String destPath, String password)
			throws IOException {
		File srcFile = new File(srcRar);
		if (!srcFile.exists()) {
			return;
		}
		if (null == destPath || destPath.length() == 0) {
			unrar(srcFile, srcFile.getParent(), password);
			return;
		}
		unrar(srcFile, destPath, password);
	}

	/**
	 * 解压指定RAR文件到当前文件夹
	 * 
	 * @param srcRarFile
	 *            解压文件
	 * @param password
	 *            压缩文件时设定的密码
	 * @throws IOException
	 */
	public static void unrar(File srcRarFile, String password)
			throws IOException {
		if (null == srcRarFile || !srcRarFile.exists()) {
			throw new IOException("指定文件不存在.");
		}
		unrar(srcRarFile, srcRarFile.getParent(), password);
	}

	/**
	 * 解压指定RAR文件到指定的路径
	 * 
	 * @param srcRarFile
	 *            需要解压RAR文件
	 * @param destPath
	 *            指定解压路径
	 * @param password
	 *            压缩文件时设定的密码
	 * @throws IOException
	 */
	public static void unrar(File srcRarFile, String destPath, String password)
			throws IOException {
		if (null == srcRarFile || !srcRarFile.exists()) {
			throw new IOException("指定压缩文件不存在.");
		}
		if (!destPath.endsWith(SEPARATOR)) {
			destPath += SEPARATOR;
		}
		Archive archive = null;
		OutputStream unOut = null;
		try {
			archive = new Archive(srcRarFile, password, false);
			FileHeader fileHeader = archive.nextFileHeader();
			while (null != fileHeader) {
				if (!fileHeader.isDirectory()) {
					// 1 根据不同的操作系统拿到相应的 destDirName 和 destFileName
					String destFileName = "";
					String destDirName = "";
					if (SEPARATOR.equals("/")) { // 非windows系统
						destFileName = (destPath + fileHeader.getFileNameW())
								.replaceAll("\\\\", "/");
						destDirName = destFileName.substring(0,
								destFileName.lastIndexOf("/"));
					} else { // windows系统
						destFileName = (destPath + fileHeader.getFileNameW())
								.replaceAll("/", "\\\\");
						destDirName = destFileName.substring(0,
								destFileName.lastIndexOf("\\"));
					}
					// 2创建文件夹
					File dir = new File(destDirName);
					if (!dir.exists() || !dir.isDirectory()) {
						dir.mkdirs();
					}
					// 抽取压缩文件
					unOut = new FileOutputStream(new File(destFileName));
					archive.extractFile(fileHeader, unOut);
					unOut.flush();
					unOut.close();
				}
				fileHeader = archive.nextFileHeader();
			}
			archive.close();
		} catch (RarException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(unOut);
		}
	}
	
    
}
