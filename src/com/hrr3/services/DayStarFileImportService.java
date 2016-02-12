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
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.examples.HSSFReadWrite;

import com.hrr3.entity.ssr.ImportDayStarData;
import com.hrr3.entity.ssrMigration.SSRSnapshotDayStar;
import com.hrr3.entity.ssrMigration.SSRSnapshotDayStarData;
import com.hrr3.entity.ssrMigration.SSRSnapshotDayStarHotel;

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
public final class DayStarFileImportService extends FileImportInterface{

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
		DayStarFileImportService fileImportService = new DayStarFileImportService();				
		try {
			//Test with HotelId 41
			fileImportService.parseFileToObjects(fileName, 41);
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
	
	
	public ImportDayStarData parseFileToObjects(String fileName, int hotelId) throws ApplicationException, IOException {
		
		HSSFWorkbook wb = this.readFile(fileName);	
		ImportDayStarData dayStarData = null; //Container to save glance/response/summary
		SSRSnapshotDayStar glance = null; //To save glance data
		List<SSRSnapshotDayStarHotel> responses = null; //To save response data
		List<SSRSnapshotDayStarData> summaries = null; //To be implemented while reading excel in future version		

		//1. Validate Glance SheetName at tab#2
		if(wb.getNumberOfSheets() == 0  || wb.getSheet("Glance") == null) 
			throw new ApplicationException("Excel file must contain a valid sheet called 'Glance'");
		
		//2. Validate Glance SheetName at tab#7 or tab#12
		if(wb.getNumberOfSheets() == 0  || wb.getSheet("Response") == null) 
			throw new ApplicationException("Excel file must contain a valid sheet called 'Response'");
		
		//3. Once 1 and 2 validation is done, assign proper objects for future usage in more specific validations
		HSSFSheet glanceSheet = wb.getSheet("Glance");
		HSSFSheet responseSheet = wb.getSheet("Response");
		
		//3.1 Instantiate main container
		dayStarData = new ImportDayStarData();
		
		//4. Fill out glance Data
		glance = new SSRSnapshotDayStar();
		glance.setHotelId(hotelId);
		glance.setCapHotel(getStringCellValue(glanceSheet.getRow(1).getCell(1)));//2B
		glance.setCapHotel2(getStringCellValue(glanceSheet.getRow(2).getCell(1)));//3B
		glance.setDateFrom(null);
		glance.setDateTo(null);
		glance.setCapWeek(getStringCellValue(glanceSheet.getRow(3).getCell(1)));//4B
		
		System.out.println(glance);
		
		//4.1 Fill out Summary Data (part of the same Glance sheet)	
		summaries = new ArrayList<SSRSnapshotDayStarData>();
		for(int iIndex=1; iIndex <= 16; iIndex++) //16 is equals SUN - SAT by two weeks in the Glance sheet 
		{
			String sType = "";
			String sDateCurrYr = "";
			String sDateLastYr = "";
			String sDateRange = "";
			String sYearCurr = "";
			String sYearLast = "";
			
			String sOCCProp  = "";
			String sOCCPropPC = "";
			String sOCCCompSet = "";
			String sOCCCompSetPC = "";
			String sOCCIndex = "";
			String sOCCIndexPC = "";
		    
			String sARRProp = "";
			String sARRPropPC = "";
			String sARRCompSet = "";
			String sARRCompSetPC = "";
			String sARRIndex = "";
			String sARRIndexPC = "";
		    
			String sRPProp = "";
			String sRPPropPC = "";
			String sRPCompSet = "";
			String sRPCompSetPC = "";
			String sRPIndex = "";
			String sRPIndexPC = "";
			
			int sCol = 0;//Index for left value
			int sColPC = 0;//Index for right value
			
			switch (iIndex) {
			
				case 1:
				case 9:
	                sType = "SUN";
	                sCol = 4;
	                sColPC = 5;
	                break;
				case 2:
				case 10:
	                sType = "MON";
	                sCol = 7;
	                sColPC = 8;
	                break;
				case 3: 
				case 11:
	                sType = "TUE";
	                sCol = 10;
	                sColPC = 11;
	                break;
				case 4:
				case 12:
	                sType = "WED";
	                sCol = 13;
	                sColPC = 14;
	                break;
				case 5:
				case 13:
	                sType = "THU";
	                sCol = 16;
	                sColPC = 17;
	                break;
				case 6:
				case 14:
	                sType = "FRI";
	                sCol = 19;
	                sColPC = 20;
	                break;
				case 7:
				case 15:
	                sType = "SAT";
	                sCol = 22;
	                sColPC = 23;
	                break;
				case 8:
				case 16:
	                sType = "SUBTOT";
	                sCol = 25;
	                sColPC = 26;
	                break;
			
			}
			
			//Read Weekly section
			if(iIndex >=1 && iIndex <=8) {
				
				sType = "CURR" + sType;
				sDateRange = getStringCellValue(glanceSheet.getRow(5).getCell(1)).trim();//Read 6B
				
				sOCCProp = getStringCellValue(glanceSheet.getRow(9).getCell(sCol)).trim();
		        sOCCPropPC = getStringCellValue(glanceSheet.getRow(9).getCell(sColPC)).trim();
		        sOCCCompSet = getStringCellValue(glanceSheet.getRow(10).getCell(sCol)).trim();
		        sOCCCompSetPC = getStringCellValue(glanceSheet.getRow(10).getCell(sColPC)).trim();
		        sOCCIndex = getStringCellValue(glanceSheet.getRow(11).getCell(sCol)).trim();
		        sOCCIndexPC = getStringCellValue(glanceSheet.getRow(11).getCell(sColPC)).trim();
		                
		        sARRProp = getStringCellValue(glanceSheet.getRow(13).getCell(sCol)).trim();
		        sARRPropPC = getStringCellValue(glanceSheet.getRow(13).getCell(sColPC)).trim();
		        sARRCompSet = getStringCellValue(glanceSheet.getRow(14).getCell(sCol)).trim();
		        sARRCompSetPC = getStringCellValue(glanceSheet.getRow(14).getCell(sColPC)).trim();
		        sARRIndex = getStringCellValue(glanceSheet.getRow(15).getCell(sCol)).trim();
		        sARRIndexPC = getStringCellValue(glanceSheet.getRow(15).getCell(sColPC)).trim();
		               
		        sRPProp = getStringCellValue(glanceSheet.getRow(17).getCell(sCol)).trim();
		        sRPPropPC = getStringCellValue(glanceSheet.getRow(17).getCell(sColPC)).trim();
		        sRPCompSet = getStringCellValue(glanceSheet.getRow(18).getCell(sCol)).trim();
		        sRPCompSetPC = getStringCellValue(glanceSheet.getRow(18).getCell(sColPC)).trim();
		        sRPIndex = getStringCellValue(glanceSheet.getRow(19).getCell(sCol)).trim();
		        sRPIndexPC = getStringCellValue(glanceSheet.getRow(19).getCell(sColPC)).trim();
				
			}
			//Read Running section
			else if(iIndex > 8) {
				
				sType = "RUN" + sType;
				sDateRange = getStringCellValue(glanceSheet.getRow(22).getCell(1)).trim();//Read 23B
				
				sOCCProp = getStringCellValue(glanceSheet.getRow(26).getCell(sCol)).trim();
		        sOCCPropPC = getStringCellValue(glanceSheet.getRow(26).getCell(sColPC)).trim();
		        sOCCCompSet = getStringCellValue(glanceSheet.getRow(27).getCell(sCol)).trim();
		        sOCCCompSetPC = getStringCellValue(glanceSheet.getRow(27).getCell(sColPC)).trim();
		        sOCCIndex = getStringCellValue(glanceSheet.getRow(28).getCell(sCol)).trim();
		        sOCCIndexPC = getStringCellValue(glanceSheet.getRow(28).getCell(sColPC)).trim();
		                
		        sARRProp = getStringCellValue(glanceSheet.getRow(30).getCell(sCol)).trim();
		        sARRPropPC = getStringCellValue(glanceSheet.getRow(30).getCell(sColPC)).trim();
		        sARRCompSet = getStringCellValue(glanceSheet.getRow(31).getCell(sCol)).trim();
		        sARRCompSetPC = getStringCellValue(glanceSheet.getRow(31).getCell(sColPC)).trim();
		        sARRIndex = getStringCellValue(glanceSheet.getRow(32).getCell(sCol)).trim();
		        sARRIndexPC = getStringCellValue(glanceSheet.getRow(32).getCell(sColPC)).trim();
		               
		        sRPProp = getStringCellValue(glanceSheet.getRow(34).getCell(sCol)).trim();
		        sRPPropPC = getStringCellValue(glanceSheet.getRow(34).getCell(sColPC)).trim();
		        sRPCompSet = getStringCellValue(glanceSheet.getRow(35).getCell(sCol)).trim();
		        sRPCompSetPC = getStringCellValue(glanceSheet.getRow(35).getCell(sColPC)).trim();
		        sRPIndex = getStringCellValue(glanceSheet.getRow(36).getCell(sCol)).trim();
		        sRPIndexPC = getStringCellValue(glanceSheet.getRow(36).getCell(sColPC)).trim();
				
			}
			
			
			SSRSnapshotDayStarData summary = new SSRSnapshotDayStarData();
			
			summary.setType(sType);
			summary.setTab(2);
			summary.setSequence(iIndex);
			summary.setDatethisyr(sDateCurrYr);
			summary.setDatelastyr(sDateLastYr);
			summary.setCurrentyr(sYearCurr);
			summary.setLastyr(sYearLast);
			summary.setDaterange(sDateRange);
			
			summary.setOccProp(sOCCProp);
			summary.setOccPropPc(sOCCPropPC);
			summary.setOccCompset(sOCCCompSet);
			summary.setOccCompsetPc(sOCCCompSetPC);
			summary.setOccIndex(sOCCIndex);
			summary.setOccIndexPc(sOCCIndexPC);
			
			summary.setArrProp(sARRProp);
			summary.setArrPropPc(sARRPropPC);
			summary.setArrCompset(sARRCompSet);
			summary.setArrCompsetPc(sARRCompSetPC);
			summary.setArrIndex(sARRIndex);
			summary.setArrIndexPc(sARRIndexPC);
			
			summary.setRevparProp(sRPProp);
			summary.setRevparPropPc(sRPPropPC);
			summary.setRevparCompset(sRPCompSet);
			summary.setRevparCompsetPc(sRPCompSetPC);
			summary.setRevparIndex(sRPIndex);
			summary.setRevparIndexPc(sRPIndexPC);
			
			System.out.println(summary);
			summaries.add(summary);
		}
			
		
		
		
		
		
		//5. Find STR ID value on C (index=2) column
		int responseTotalRows = responseSheet.getPhysicalNumberOfRows();
		int strIDHeaderRow = -1;
		int currentRow = -1;
		int currentCol = -1;
		int strIDHeaderCol = 2; // Column C
		
		String strIDHeaderVal = "STR ID";
		String strHotelName="Name";
		String columnNameForHotelName="N/A";
		
		for(int i=0; i<responseTotalRows; i++) {
			String cellValue = getStringCellValue(responseSheet.getRow(i).getCell(2));	
			if(cellValue.equalsIgnoreCase(strIDHeaderVal))
				{ strIDHeaderRow = i; break; }
		}
		
		//Validate of Name is on column D or E
		if (getStringCellValue(responseSheet.getRow(strIDHeaderRow).getCell(3)).equalsIgnoreCase(strHotelName))
			columnNameForHotelName = "D";
		else if (getStringCellValue(responseSheet.getRow(strIDHeaderRow).getCell(4)).equalsIgnoreCase(strHotelName))
			columnNameForHotelName = "E";
		else
			throw new ApplicationException("Column for Hotel Name not found in row["+ strIDHeaderRow +"]");
		
		//6. If STRID could not be found, throws an error
		if(strIDHeaderRow == -1) 
			throw new ApplicationException("Response Sheet must contain STR ID Header in Column C.");
		
		//7. Once STR ID was found, we look for QRSTUVW columns at index-> strIDHeaderRow
		String sDate1 = responseSheet.getRow(strIDHeaderRow).getCell(16).toString();
		String sDate2 = responseSheet.getRow(strIDHeaderRow).getCell(17).toString();
		String sDate3 = responseSheet.getRow(strIDHeaderRow).getCell(18).toString();
		String sDate4 = responseSheet.getRow(strIDHeaderRow).getCell(19).toString();
		String sDate5 = responseSheet.getRow(strIDHeaderRow).getCell(20).toString();
		String sDate6 = responseSheet.getRow(strIDHeaderRow).getCell(21).toString();
		String sDate7 = responseSheet.getRow(strIDHeaderRow).getCell(22).toString();
		
		//8. Move cursor to next row below to start reading Hotel compsets
		currentRow = ++strIDHeaderRow;
		currentCol = strIDHeaderCol;
				
		//9. Fill out response Data
		responses = new ArrayList<SSRSnapshotDayStarHotel>();
				
		//9.1 Process response Rows while cell value is not 0 or empty
		while(currentRow < responseTotalRows && (!getStringCellValue(responseSheet.getRow(currentRow).getCell(2)).equalsIgnoreCase("0") && !getStringCellValue(responseSheet.getRow(currentRow).getCell(2)).isEmpty())) {
			
			SSRSnapshotDayStarHotel currentResponse = new SSRSnapshotDayStarHotel();
			
			//Date Data
			currentResponse.setDate1(sDate1);//sDate1
			currentResponse.setDate2(sDate2);//sDate2
			currentResponse.setDate3(sDate3);//sDate3
			currentResponse.setDate4(sDate4);//sDate4
			currentResponse.setDate5(sDate5);//sDate5
			currentResponse.setDate6(sDate6);//sDate6
			currentResponse.setDate7(sDate7);//sDate7
			
			//Str Data
			currentResponse.setStrId(getStringCellValue(responseSheet.getRow(currentRow).getCell(currentCol))); 
			currentCol = columnNameForHotelName.equalsIgnoreCase("D") ? currentCol+1 : currentCol+2;//Sum 1 if "Name" found in Column D, or Sum 2 if found in E
			currentResponse.setHotel(getStringCellValue(responseSheet.getRow(currentRow).getCell(currentCol++)));//E
			currentResponse.setCity(getStringCellValue(responseSheet.getRow(currentRow).getCell(currentCol++)));//F
			currentResponse.setZip(getStringCellValue(responseSheet.getRow(currentRow).getCell(currentCol++)));//G
			currentResponse.setPhone(getStringCellValue(responseSheet.getRow(currentRow).getCell(currentCol++)));//H
			currentResponse.setRooms(getStringCellValue(responseSheet.getRow(currentRow).getCell(currentCol++)));//I
			currentResponse.setOpendate(getStringCellValue(responseSheet.getRow(currentRow).getCell(currentCol))); currentCol+=7;//J
			
			//DayStar Data
			currentResponse.setDataDaystar1(getStringCellValue(responseSheet.getRow(currentRow).getCell(currentCol++)));//Q
			currentResponse.setDataDaystar2(getStringCellValue(responseSheet.getRow(currentRow).getCell(currentCol++)));//R
			currentResponse.setDataDaystar3(getStringCellValue(responseSheet.getRow(currentRow).getCell(currentCol++)));//S
			currentResponse.setDataDaystar4(getStringCellValue(responseSheet.getRow(currentRow).getCell(currentCol++)));//T
			currentResponse.setDataDaystar5(getStringCellValue(responseSheet.getRow(currentRow).getCell(currentCol++)));//U
			currentResponse.setDataDaystar6(getStringCellValue(responseSheet.getRow(currentRow).getCell(currentCol++)));//V
			currentResponse.setDataDaystar7(getStringCellValue(responseSheet.getRow(currentRow).getCell(currentCol))); currentCol+=3;//W
			
			//Segmented Data
			currentResponse.setDataSeg1(getStringCellValue(responseSheet.getRow(currentRow).getCell(currentCol++)));//Z
			currentResponse.setDataSeg2(getStringCellValue(responseSheet.getRow(currentRow).getCell(currentCol++)));//AA
			currentResponse.setDataSeg3(getStringCellValue(responseSheet.getRow(currentRow).getCell(currentCol++)));//AB
			currentResponse.setDataSeg4(getStringCellValue(responseSheet.getRow(currentRow).getCell(currentCol++)));//AC
			currentResponse.setDataSeg5(getStringCellValue(responseSheet.getRow(currentRow).getCell(currentCol++)));//AD
			currentResponse.setDataSeg6(getStringCellValue(responseSheet.getRow(currentRow).getCell(currentCol++)));//AE
			currentResponse.setDataSeg7(getStringCellValue(responseSheet.getRow(currentRow).getCell(currentCol))); currentCol+=3;//AF
			
			//F&B Data
			currentResponse.setDataFb1(getStringCellValue(responseSheet.getRow(currentRow).getCell(currentCol++)));//AI
			currentResponse.setDataFb2(getStringCellValue(responseSheet.getRow(currentRow).getCell(currentCol++)));//AJ
			currentResponse.setDataFb3(getStringCellValue(responseSheet.getRow(currentRow).getCell(currentCol++)));//AK
			currentResponse.setDataFb4(getStringCellValue(responseSheet.getRow(currentRow).getCell(currentCol++)));//AL
			currentResponse.setDataFb5(getStringCellValue(responseSheet.getRow(currentRow).getCell(currentCol++)));//AM
			currentResponse.setDataFb6(getStringCellValue(responseSheet.getRow(currentRow).getCell(currentCol++)));//AN
			currentResponse.setDataFb7(getStringCellValue(responseSheet.getRow(currentRow).getCell(currentCol++)));//AO
			
			System.out.println(currentResponse);
			
			responses.add(currentResponse);
			
			currentRow++;
			currentCol = strIDHeaderCol;
		}
	
		dayStarData.setGlance(glance);
		dayStarData.setSummaries(summaries);
		dayStarData.setResponse(responses);
		
		
		
		return dayStarData;
		
		
	}
}