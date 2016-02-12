package com.hrr3.services;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

	public class FileImportInterface {
	
		public String getStringCellValue(HSSFCell cell) {
			
		if(cell== null)	return "0";
		
		switch (cell.getCellType()) {
		
			case HSSFCell.CELL_TYPE_FORMULA:
				switch(cell.getCachedFormulaResultType()) {
		            case Cell.CELL_TYPE_NUMERIC:						               
		                return Double.toString(cell.getNumericCellValue());		               
		            case Cell.CELL_TYPE_STRING:						                
		                return  cell.getRichStringCellValue().getString();
				}
				
			break;	
			
		     case HSSFCell.CELL_TYPE_NUMERIC:
		    	 return Double.toString(cell.getNumericCellValue());
		     case HSSFCell.CELL_TYPE_STRING:
		    	 return cell.getStringCellValue();
		
		}

		return "0";
				
	}
		
		public String getNumberAsStringInExcelFormat(HSSFCell cell) {
			
			if(cell== null)	return "0";
			
			double numValue = cell.getNumericCellValue();
			String formatString = cell.getCellStyle().getDataFormatString();
			DecimalFormat df = new DecimalFormat(formatString);
			return df.format(numValue);
			
					
		}
		
		//getStringCellValueAsDisplayed
		
		/**
		 * creates an {@link HSSFWorkbook} the specified OS filename.
		 */
		public HSSFWorkbook readFile(String filename) throws IOException {
			FileInputStream fis = new FileInputStream(filename);
			System.out.println(filename);
			//fis.close();
			return new HSSFWorkbook(fis);
		}
		
		
		
}
