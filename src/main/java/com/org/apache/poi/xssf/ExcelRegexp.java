package com.org.apache.poi.xssf;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelRegexp {
	public static void main(String[] args) {
		String path = "d:/testdata.xlsx";
		String sheetName = "sheet1";
		XSSFWorkbook wb = null;
		try {
			wb = new XSSFWorkbook(path);
			XSSFSheet sheet = wb.getSheet(sheetName);
			int lastRowNum = sheet.getLastRowNum();
			System.out.println(lastRowNum);
			for (int i = 0; i < lastRowNum; i++) {
				XSSFRow row = sheet.getRow(i);
				if(row==null) {
					System.out.println("####"+i);
					continue;
				}
				short lastCellNum = row.getLastCellNum();
				System.out.println("     "+lastCellNum);
				if(lastCellNum<=0) continue;
				for (int j = 0; j < lastCellNum; j++) {
					XSSFCell cell = row.getCell(j);
					String value = cell.getStringCellValue();
					value = value.replaceAll("<([^>]*)>", "########");
//					System.out.println(value);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
	}
}
