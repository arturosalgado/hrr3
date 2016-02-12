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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.examples.HSSFReadWrite;

import com.hrr3.entity.ssr.ImportSSRData;

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
public final class SSRFileImportService extends FileImportInterface {
	

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
				
				SSRFileImportService fileImportService = new SSRFileImportService();
				Date startDate =     new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse("2005-01-01");
				Date endDate =     new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse("2014-02-22");
				fileImportService.parseFileToObjects(fileName, startDate, endDate);

				
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public List<ImportSSRData> parseFileToObjects(String fileName, Date startDate, Date endDate) throws ApplicationException, IOException {
		
		HSSFWorkbook wb = this.readFile(fileName);
		List<ImportSSRData> rowDataList = null;

		//1. Validate SheetName
		if(wb.getNumberOfSheets() == 0  || !wb.getSheetAt(0).getSheetName().equals("SellStrat")) 
			throw new ApplicationException("Excel file must contain a valid sheet called 'SellStrat'");
		
		//2. Validate row 3 (index 2) contains at least 22 columns
		if(wb.getSheetAt(0).getRow(2).getPhysicalNumberOfCells() < 22) 
			throw new ApplicationException("'SellStrat sheet must contains at least 22 columns at Row#3 to identify SSR template.");
		
		//Once 1 and 2 validation is done, assign proper objects for future usage in more specific validations
		HSSFSheet sellStrat = wb.getSheetAt(0);
		int totalRows = sellStrat.getPhysicalNumberOfRows();
		int startIndex = 3;//DataIndex to start reading srr info
		int startDateIndex  = -1; //To save row index for startdate
		int endDateIndex = -1;//To save row inxed for enddate
		
		//3.Validate date range exists (ie. 1/1/2014 to 1/20/2014) and get start and end indexes containing the data
		for(int rowIndex = startIndex; rowIndex < totalRows; rowIndex++) {
			HSSFCell currentDateCell = sellStrat.getRow(rowIndex).getCell(2);			
			
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
		rowDataList = new ArrayList<ImportSSRData>();
		
		//4. Now having start and end date, get the range of rows and fill out a list of ImportSSRData objects.		
		for(int i = startDateIndex; i <= endDateIndex; i++) {
			
			HSSFCell currentDateCell = sellStrat.getRow(i).getCell(2);	
			Date statdateCol = currentDateCell.getDateCellValue();			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			
			String comments = sellStrat.getRow(i).getCell(0) != null ? sellStrat.getRow(i).getCell(0).getStringCellValue(): "";					
			String statdate = dateFormat.format(statdateCol);			
			
			Calendar c = Calendar.getInstance();
			try {
				c.setTime(dateFormat.parse(statdate));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String[] strDays = new String[] { "Sunday", "Monday", "Tuesday", "Wednesday", "Thusday",
			        "Friday", "Saturday" };	
			
			String down =  strDays[c.get(Calendar.DAY_OF_WEEK) - 1]; //sellStrat.getRow(i).getCell(1).getStringCellValue();
			
			String a1 = sellStrat.getRow(i).getCell(3).getStringCellValue();	
			String b2 = sellStrat.getRow(i).getCell(4).getStringCellValue();	
			String c3 = sellStrat.getRow(i).getCell(5).getStringCellValue();	
			String d4 = sellStrat.getRow(i).getCell(6).getStringCellValue();	
			String e5 = sellStrat.getRow(i).getCell(7).getStringCellValue();	
			String f6 = sellStrat.getRow(i).getCell(8).getStringCellValue();	
			String g7 = sellStrat.getRow(i).getCell(9).getStringCellValue();	
			String h8 = sellStrat.getRow(i).getCell(10).getStringCellValue();	
			String i9 = sellStrat.getRow(i).getCell(11).getStringCellValue();				
			String hp1 = getStringCellValue(sellStrat.getRow(i).getCell(12));
			String hp2 = getStringCellValue(sellStrat.getRow(i).getCell(13));
			String ssrTransient = getStringCellValue(sellStrat.getRow(i).getCell(14));
			String ssrGrpblock = getStringCellValue(sellStrat.getRow(i).getCell(15));
			String ssrContract = getStringCellValue(sellStrat.getRow(i).getCell(16));
			String ssrGrppu = getStringCellValue(sellStrat.getRow(i).getCell(18));
			String ssrGrprem = getStringCellValue(sellStrat.getRow(i).getCell(19));
			String ssrDemandtd = getStringCellValue(sellStrat.getRow(i).getCell(20));
			String ssrPricetd = getStringCellValue(sellStrat.getRow(i).getCell(21));
				
			
			
			//Fill rowData object
			ImportSSRData rowData = new ImportSSRData();
			rowData.setComment(comments);
			rowData.setDow(down);
			rowData.setStatdate(statdate);
			rowData.setA1(a1);
			rowData.setB2(b2);
			rowData.setC3(c3);
			rowData.setD4(d4);
			rowData.setE5(e5);
			rowData.setF6(f6);
			rowData.setG7(g7);
			rowData.setH8(h8);
			rowData.setI9(i9);
			rowData.setHp1(hp1);
			rowData.setHp2(hp2);
			rowData.setSsrTransient(new BigDecimal(ssrTransient));
			rowData.setSsrGrpblock(new BigDecimal(ssrGrpblock));
			rowData.setSsrContract(new BigDecimal(ssrContract));
			rowData.setSsrGrppu(new BigDecimal(ssrGrppu));
			rowData.setSsrGrprem(new BigDecimal(ssrGrprem));
			rowData.setSsrDemandtd(new BigDecimal(ssrDemandtd));
			rowData.setSsrPricetd(new BigDecimal(ssrPricetd));
			//Add row to the list
			rowDataList.add(rowData);
			System.out.println(rowData);
			
			
		}
		
		System.out.println("******** Rows to return => " + rowDataList.size() + " ********");
		
		
		return rowDataList;
	}
}