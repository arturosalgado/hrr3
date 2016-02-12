/* ====================================================================
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
==================================================================== */

package com.hrr3.services;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.examples.HSSFReadWrite;

import com.hrr3.entity.ssr.ImportSSRLRRData;

/**
 * File for HSSF testing/examples
 *
 * THIS IS NOT THE MAIN HSSF FILE!! This is a utility for testing functionality.
 * It does contain sample API usage that may be educational to regular API
 * users.
 *
 * @see #main
 * @author Andrew Oliver (acoliver at apache dot org)
 */
public final class LRRFileImportService extends FileImportInterface{

	/**
     * Method main
     *
     * Given 1 argument takes that as the filename, inputs it and dumps the
     * cell values/types out to sys.out.<br/>
     *
     * given 2 arguments where the second argument is the word "write" and the
     * first is the filename - writes out a sample (test) spreadsheet
     * see {@link HSSFReadWrite#testCreateSampleSheet(String)}.<br/>
     *
     * given 2 arguments where the first is an input filename and the second
     * an output filename (not write), attempts to fully read in the
     * spreadsheet and fully write it out.<br/>
     *
     * given 3 arguments where the first is an input filename and the second an
     * output filename (not write) and the third is "modify1", attempts to read in the
     * spreadsheet, deletes rows 0-24, 74-99.  Changes cell at row 39, col 3 to
     * "MODIFIED CELL" then writes it out.  Hence this is "modify test 1".  If you
     * take the output from the write test, you'll have a valid scenario.
     */
	public static void main(String[] args) {
		if (args.length < 1) {
			System.err.println("At least one argument expected");
			return;
		}

		String fileName = args[0];
		try {
			
			if (args.length < 2) {
				
				LRRFileImportService fileImportService = new LRRFileImportService();
				Date startDate =     new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse("2005-01-01");
				Date endDate =     new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse("2006-01-01");
				fileImportService.parseFileToObjects(fileName, startDate, endDate);

				
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public List<ImportSSRLRRData> parseFileToObjects(String fileName, Date startDate, Date endDate) throws ApplicationException, IOException {
		
		HSSFWorkbook wb = this.readFile(fileName);	
		List<ImportSSRLRRData> rowDataList = null;

		//1. Validate SheetName
		if(wb.getNumberOfSheets() == 0  || !wb.getSheetAt(0).getSheetName().equals("LRR")) 
			throw new ApplicationException("Excel file must contain a valid sheet called 'LRR'");
		
		//2. Validate row 3 (index 2) contains at least 14 columns
		if(wb.getSheetAt(0).getRow(2).getPhysicalNumberOfCells() < 14) 
			throw new ApplicationException("'LRR setup sheet must contains at least 14 columns at Row#3 to identify SSR-LRR template.");
		
		//Once 1 and 2 validation is done, assign proper objects for future usage in more specific validations
		HSSFSheet lrrSheet = wb.getSheetAt(0);
		int totalRows = lrrSheet.getPhysicalNumberOfRows();
		int startIndex = 3;//DataIndex to start reading srr info
		int startDateIndex  = -1; //To save row index for startdate
		int endDateIndex = -1;//To save row inxed for enddate
		int dateColumnIndex = 1;
		
		//3.Validate date range exists (ie. 1/1/2014 to 1/20/2014) and get start and end indexes containing the data
		for(int rowIndex = startIndex; rowIndex < totalRows; rowIndex++) {
			HSSFCell currentDateCell = lrrSheet.getRow(rowIndex).getCell(dateColumnIndex);			
			
			//3.1 If row is not latest one, validate Date and Format
			if(rowIndex < totalRows){				
				if(currentDateCell == null || currentDateCell.getDateCellValue() == null)
					throw new ApplicationException("Column[Date] at row["+(rowIndex+1) +"] can't be empty.");
				else if(!HSSFDateUtil.isCellDateFormatted(currentDateCell))
					throw new ApplicationException("Column[Date] at row["+(rowIndex+1) +"] must be Date Format.");		
			}
			
			//3.2 Get Start and End indexes according to date range requested by user
			if(currentDateCell.getDateCellValue().compareTo(startDate) == 0)
				startDateIndex = rowIndex;
			
			if(currentDateCell.getDateCellValue().compareTo(endDate) == 0)
				{ endDateIndex = rowIndex; break; }			
		
		}
		
		System.out.println("******** RANGE ["+ startDate + "-" + endDate + "] Found *************");
		System.out.println("******** StartDate INDEX: " + startDateIndex +  " *************");
		System.out.println("******** EndDate INDEX: " + endDateIndex +  " *************");
		
		if(startDateIndex == -1 || endDateIndex == -1)
			throw new ApplicationException("Sheet does not contain data for the date range. Please provide a valid one.");
		
		//Create RowDataList to contain each row
		rowDataList = new ArrayList<ImportSSRLRRData>();
		
		//4. Now having start and end date, get the range of rows and fill out a list of ImportSSRData objects.		
		for(int i = startDateIndex; i <= endDateIndex; i++) {
			
			HSSFCell currentDateCell = lrrSheet.getRow(i).getCell(dateColumnIndex);	
			Date statdateCol = currentDateCell.getDateCellValue();			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			
			String statdate = dateFormat.format(statdateCol);
			String lrr1 = getStringCellValue(lrrSheet.getRow(i).getCell(3));	
			String lrr2 = getStringCellValue(lrrSheet.getRow(i).getCell(4));
			String lrr3 = getStringCellValue(lrrSheet.getRow(i).getCell(5));	
			String lrr4 = getStringCellValue(lrrSheet.getRow(i).getCell(6));	
			String lrr5 = getStringCellValue(lrrSheet.getRow(i).getCell(7));	
			String lrr6 = getStringCellValue(lrrSheet.getRow(i).getCell(8));	
			String lrr7 = getStringCellValue(lrrSheet.getRow(i).getCell(9));
			String lrr8 = getStringCellValue(lrrSheet.getRow(i).getCell(10));
			String lrr9 = getStringCellValue(lrrSheet.getRow(i).getCell(11));			
			String hp1 = getStringCellValue(lrrSheet.getRow(i).getCell(12));
			String hp2 = getStringCellValue(lrrSheet.getRow(i).getCell(13));						
			
			//Fill rowData object
			ImportSSRLRRData rowData = new ImportSSRLRRData();
			
			rowData.setStatdate(statdate);
			rowData.setLrr1(new BigDecimal(lrr1));
			rowData.setLrr2(new BigDecimal(lrr2));
			rowData.setLrr3(new BigDecimal(lrr3));
			rowData.setLrr4(new BigDecimal(lrr4));
			rowData.setLrr5(new BigDecimal(lrr5));
			rowData.setLrr6(new BigDecimal(lrr6));
			rowData.setLrr7(new BigDecimal(lrr7));
			rowData.setLrr8(new BigDecimal(lrr8));
			rowData.setLrr9(new BigDecimal(lrr9));
			rowData.setLrrHp1(new BigDecimal(hp1));
			rowData.setLrrHp2(new BigDecimal(hp2));
			//Add row to the list
			rowDataList.add(rowData);
			System.out.println(rowData);
			
			
		}
		
		System.out.println("******** Rows to return => " + rowDataList.size() + " ********");
		
		
		return rowDataList;
	}
}