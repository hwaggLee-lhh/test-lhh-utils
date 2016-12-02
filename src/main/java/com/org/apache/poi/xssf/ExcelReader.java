package com.org.apache.poi.xssf;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
	private XSSFWorkbook wb;

	public List<ArrayList<Object>> processExcel(String path, String sheetName) {
		try {
			wb = new XSSFWorkbook(path);
			XSSFSheet sheet = wb.getSheet(sheetName);
			if(sheet==null) return null;
			int lastRowNum = sheet.getLastRowNum()+1;
			if(lastRowNum<=0) return null;
			List<ArrayList<Object>> result = new ArrayList<ArrayList<Object>>();
			for (int i = 1; i < lastRowNum; i++) {
				XSSFRow row = sheet.getRow(i);
				if( row == null) continue;
				short lastCellNum =row.getLastCellNum();
				if(lastCellNum<=0) continue;
				ArrayList<Object> rowList = new ArrayList<Object>();
				for (int j = 0; j < lastCellNum; j++) {
					XSSFCell cell = row.getCell(j);
					if(cell==null) continue;
					Object value = null;
					switch(cell.getCellType()) {
					case XSSFCell.CELL_TYPE_BOOLEAN:
						value = cell.getBooleanCellValue();
						break;
					case XSSFCell.CELL_TYPE_NUMERIC:
						value = (int)cell.getNumericCellValue();
						break;
					case XSSFCell.CELL_TYPE_STRING:
						value = cell.getStringCellValue();
						break;
					}
					rowList.add(value);
				}
				result.add(rowList);
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
