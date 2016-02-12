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
import org.apache.poi.hssf.util.CellReference;

import com.hrr3.entity.ssrMigration.SSRSnapshotStarData;

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
public final class StarFileImportService extends FileImportInterface{

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
		StarFileImportService fileImportService = new StarFileImportService();				
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
	
	
	
	public List<SSRSnapshotStarData> parseFileToObjects(String fileName, int hotelId) throws ApplicationException, IOException {
		
		String sTemp;
	    String sDayFrom;
	    String sDayTo;
	    String iPos;    
	       
	    String sMonth;
	    String sYear = "";
	    String sTract;
	    String sTractScale;
	    
	    String sOCCProp;
	    String sOCCPropPC;
	    String sOCCCompSet;
	    String sOCCCompSetPC;
	    String sOCCIndex;
	    String sOCCIndexPC;
	    
	    String sARRProp;
	    String sARRPropPC;
	    String sARRCompSet;
	    String sARRCompSetPC;
	    String sARRIndex;
	    String sARRIndexPC;
	    
	    String sRPProp;
	    String sRPPropPC;
	    String sRPCompSet;
	    String sRPCompSetPC;
	    String sRPIndex;
	    String sRPIndexPC;
	    
	    String sMktShSupply = "";
	    String sMktShDemand = "";
	    String sMktShRev = "";
	    
	    String sType = "";
	    String sCapHotel;
	    String sCapWeek;
	    int nDayStarID;
	    boolean bSkip;
	    int iWSColIndex;
	    int iGroup;
	    boolean bIsSummary;
	    String sMonthSummary = "";
	    String strCol;
	    
	    final String SSR_WRKSHT= "Comp";
	    final String SSR_WRKSHTSUM = "Summary";
	    final int COL_START = 2;
	    final int COL_END = 31;
	    final String REPLACE_TRACT = "Tract: ";
	    final String REPLACE_TRACT_SCALE = "Tract Scale: ";
	    String sOutput = "";
		
		SSRSnapshotStarData dayData;
		List<SSRSnapshotStarData> dayDataList = new ArrayList<SSRSnapshotStarData>(); //Day data	
		HSSFWorkbook wb = this.readFile(fileName);			

		//1. Validate Summary SheetName
		if(wb.getNumberOfSheets() == 0  || wb.getSheet(SSR_WRKSHTSUM) == null) 
			throw new ApplicationException("Excel file must contain a valid sheet called 'Summary'");
		
		//3. Once 1 and 2 validation is done, assign proper objects for future usage in more specific validations
		HSSFSheet summarySheet = wb.getSheet(SSR_WRKSHTSUM);
		HSSFSheet compSheet = wb.getSheet(SSR_WRKSHT);
		HSSFSheet currentSheet = compSheet;
		//3.1 Instantiate main container
		dayData = new SSRSnapshotStarData();
		
		//4. Fill out Comp Data
		sCapHotel = getStringCellValue(compSheet.getRow(1).getCell(1)).trim().split("        ")[0];
		sCapWeek = "STAR Trend Report - " + formatMonth(getStringCellValue(compSheet.getRow(3).getCell(1)).trim().split("        ")[0].split(":")[1]);
		
		//Define global vars
		final int ROW_BASE_INDEX = 1;

	    final int ROW_OCC_TRACT = 11 - ROW_BASE_INDEX;
	    final int ROW_OCC_TRACTSCALE = 12 - ROW_BASE_INDEX;
	       
	    final int ROW_ADR_TRACT  = 21 - ROW_BASE_INDEX;
	    final int ROW_ADR_TRACTSCALE = 22 - ROW_BASE_INDEX;
	        
	    final int ROW_REVPAR_TRACT = 31 - ROW_BASE_INDEX;
	    final int ROW_REVPAR_TRACTSCALE = 32 - ROW_BASE_INDEX;
	    	    
	    final int ROW_OCC_MY_PROP = 21 - ROW_BASE_INDEX;
	    final int ROW_OCC_MY_PROP_PER_CHG = 26 - ROW_BASE_INDEX;
	    final int ROW_OCC_COMP_SET = 22 - ROW_BASE_INDEX;
	    final int ROW_OCC_COMP_SET_PER_CHG = 27 - ROW_BASE_INDEX;
	    final int ROW_OCC_INDEX = 23 - ROW_BASE_INDEX;
	    final int ROW_OCC_INDEX_PER_CHG = 28 - ROW_BASE_INDEX;
	    	    
	    final int ROW_ADR_MY_PROP = 33 - ROW_BASE_INDEX;
	    final int ROW_ADR_MY_PROP_PER_CHG = 38 - ROW_BASE_INDEX;
	    final int ROW_ADR_COMP_SET = 34 - ROW_BASE_INDEX;
	    final int ROW_ADR_COMP_SET_PER_CHG = 39 - ROW_BASE_INDEX;
	    final int ROW_ADR_INDEX = 35 - ROW_BASE_INDEX;
	    final int ROW_ADR_INDEX_PER_CHG = 40 - ROW_BASE_INDEX;
	    	    
	    final int ROW_REVPAR_MY_PROP = 45 - ROW_BASE_INDEX;
	    final int ROW_REVPAR_MY_PROP_PER_CHG = 50 - ROW_BASE_INDEX;
	    final int ROW_REVPAR_COMP_SET = 46 - ROW_BASE_INDEX;
	    final int ROW_REVPAR_COMP_SET_PER_CHG = 51 - ROW_BASE_INDEX;
	    final int ROW_REVPAR_INDEX = 47 - ROW_BASE_INDEX;
	    final int ROW_REVPAR_INDEX_PER_CHG = 52 - ROW_BASE_INDEX;
		
		//5. Fill out Summary Data
		for(iWSColIndex=COL_START; iWSColIndex <= COL_END + 6; iWSColIndex++){
			
			//Initialize
			bSkip = false;
			sMonth = "";
			//sType = ""
			sTract = "";
			sTractScale = "";
			iGroup = 0;
			bIsSummary = false;
			        
			sOCCProp = "";
			sOCCPropPC = "";
			sOCCCompSet = "";
			sOCCCompSetPC = "";
			sOCCIndex = "";
			sOCCIndexPC = "";
			        
			sARRProp = "";
			sARRPropPC = "";
			sARRCompSet = "";
			sARRCompSetPC = "";
			sARRIndex = "";
		    sARRIndexPC = "";
			        
			sRPProp = "";
			sRPPropPC = "";
			sRPCompSet = "";
			sRPCompSetPC = "";
			sRPIndex = "";
			sRPIndexPC = "";
			
			strCol = CellReference.convertNumToColString(iWSColIndex);
			
			//System.out.println("StrCol: " + strCol);
			
			if(iWSColIndex == COL_END + 1) {//Use summary worksheet
				
				//2. Validate Comp SheetName
				if(wb.getNumberOfSheets() == 0  || wb.getSheet(SSR_WRKSHT) == null)
					throw new ApplicationException("Excel file must contain a valid sheet called 'Comp'");
				
				//Set symmarySheet
				currentSheet = summarySheet;
			}
			
			if(iWSColIndex <= COL_END) {
				bIsSummary = false;
				
				if(isBetween(iWSColIndex,3 - ROW_BASE_INDEX ,20 - ROW_BASE_INDEX)) {
					sType = "";
		            sMonth = formatMonth(getStringCellValue(currentSheet.getRow(20 - ROW_BASE_INDEX).getCell(CellReference.convertColStringToIndex(strCol))));
		            iGroup = 1;
				}
	            
				if(isBetween(iWSColIndex,26 - ROW_BASE_INDEX,28 - ROW_BASE_INDEX)) {
					iGroup = 2;
				}
				
				if(isBetween(iWSColIndex,22 - ROW_BASE_INDEX,24 - ROW_BASE_INDEX)) {
					iGroup = 3;
				}
				
				if(isBetween(iWSColIndex,30 - ROW_BASE_INDEX,32 - ROW_BASE_INDEX)) {
					iGroup = 4;
				}
				
				if(iWSColIndex == 20 - ROW_BASE_INDEX) {
					sMonthSummary = sMonth;
				}
				
				if(iWSColIndex != (21 - ROW_BASE_INDEX) && iWSColIndex != (25- ROW_BASE_INDEX) && iWSColIndex != (29- ROW_BASE_INDEX)) {
					
	                if(isBetween(iWSColIndex,3 - ROW_BASE_INDEX,20 - ROW_BASE_INDEX)) {
	                	 sYear = findYear(currentSheet, iWSColIndex);
	                	 //sYear = getStringCellValue(currentSheet.getRow(19 - ROW_BASE_INDEX).getCell(CellReference.convertColStringToIndex(strCol))).trim();
	                }
	                
	                if(isBetween(iWSColIndex,22 - ROW_BASE_INDEX,32 - ROW_BASE_INDEX)) {
	                	sYear = getStringCellValue(currentSheet.getRow(20 - ROW_BASE_INDEX).getCell(CellReference.convertColStringToIndex(strCol))).trim();
	               }
                
	                sOCCProp = getNumberAsStringInExcelFormat(currentSheet.getRow(ROW_OCC_MY_PROP).getCell(CellReference.convertColStringToIndex(strCol))).trim();
	                sOCCPropPC = getNumberAsStringInExcelFormat(currentSheet.getRow(ROW_OCC_MY_PROP_PER_CHG).getCell(CellReference.convertColStringToIndex(strCol))).trim();
	                sOCCCompSet = getNumberAsStringInExcelFormat(currentSheet.getRow(ROW_OCC_COMP_SET).getCell(CellReference.convertColStringToIndex(strCol))).trim();
	                sOCCCompSetPC = getNumberAsStringInExcelFormat(currentSheet.getRow(ROW_OCC_COMP_SET_PER_CHG).getCell(CellReference.convertColStringToIndex(strCol))).trim();
	                sOCCIndex = getNumberAsStringInExcelFormat(currentSheet.getRow(ROW_OCC_INDEX).getCell(CellReference.convertColStringToIndex(strCol))).trim();
	                sOCCIndexPC = getNumberAsStringInExcelFormat(currentSheet.getRow(ROW_OCC_INDEX_PER_CHG).getCell(CellReference.convertColStringToIndex(strCol))).trim();
	                
	                sARRProp = getNumberAsStringInExcelFormat(currentSheet.getRow(ROW_ADR_MY_PROP).getCell(CellReference.convertColStringToIndex(strCol))).trim();
	                sARRPropPC = getNumberAsStringInExcelFormat(currentSheet.getRow(ROW_ADR_MY_PROP_PER_CHG).getCell(CellReference.convertColStringToIndex(strCol))).trim();
	                sARRCompSet = getNumberAsStringInExcelFormat(currentSheet.getRow(ROW_ADR_COMP_SET).getCell(CellReference.convertColStringToIndex(strCol))).trim();
	                sARRCompSetPC = getNumberAsStringInExcelFormat(currentSheet.getRow(ROW_ADR_COMP_SET_PER_CHG).getCell(CellReference.convertColStringToIndex(strCol))).trim();
	                sARRIndex = getNumberAsStringInExcelFormat(currentSheet.getRow(ROW_ADR_INDEX).getCell(CellReference.convertColStringToIndex(strCol))).trim();
	                sARRIndexPC = getNumberAsStringInExcelFormat(currentSheet.getRow(ROW_ADR_INDEX_PER_CHG).getCell(CellReference.convertColStringToIndex(strCol))).trim();
	                
	                sRPProp = getNumberAsStringInExcelFormat(currentSheet.getRow(ROW_REVPAR_MY_PROP).getCell(CellReference.convertColStringToIndex(strCol))).trim();
	                sRPPropPC = getNumberAsStringInExcelFormat(currentSheet.getRow(ROW_REVPAR_MY_PROP_PER_CHG).getCell(CellReference.convertColStringToIndex(strCol))).trim();
	                sRPCompSet = getNumberAsStringInExcelFormat(currentSheet.getRow(ROW_REVPAR_COMP_SET).getCell(CellReference.convertColStringToIndex(strCol))).trim();
	                sRPCompSetPC = getNumberAsStringInExcelFormat(currentSheet.getRow(ROW_REVPAR_COMP_SET_PER_CHG).getCell(CellReference.convertColStringToIndex(strCol))).trim();
	                sRPIndex = getNumberAsStringInExcelFormat(currentSheet.getRow(ROW_REVPAR_INDEX).getCell(CellReference.convertColStringToIndex(strCol))).trim();
	                sRPIndexPC = getNumberAsStringInExcelFormat(currentSheet.getRow(ROW_REVPAR_INDEX_PER_CHG).getCell(CellReference.convertColStringToIndex(strCol))).trim();
				}
	            else //Heading rows 
	            {
	                bSkip = true;
	                switch(iWSColIndex) {
	                
	                case 20:
	                	sType = formatHeader(getStringCellValue(currentSheet.getRow(18).getCell(iWSColIndex + 1)), sMonthSummary); break;
	                case 24:
	                	sType = formatHeader(getStringCellValue(currentSheet.getRow(18).getCell(iWSColIndex + 1)), sMonthSummary); break;
	                case 28:
	                	sType = formatHeader(getStringCellValue(currentSheet.getRow(18).getCell(iWSColIndex + 1)), sMonthSummary); break;
	                
	                }
	                
	               
				}
	            
			}
			
			//Summary Worksheet
			else {
				
				bIsSummary = true;
			    switch(iWSColIndex){
			    
			    	case COL_END + 1:
	                //current month	                
	                sTract = getStringCellValue(currentSheet.getRow(ROW_OCC_TRACT).getCell(CellReference.convertColStringToIndex("B"))).trim().replaceAll(REPLACE_TRACT, "");
	    	        sTractScale = getStringCellValue(currentSheet.getRow(ROW_OCC_TRACTSCALE).getCell(CellReference.convertColStringToIndex("B"))).trim().replaceAll(REPLACE_TRACT_SCALE, "");	    	                
	                iGroup = 1;
	                sType = "SummaryTract";
	                sMonth = sMonthSummary;
	                
	                //set OC, ARR, RPC - TODO roundNumber
	                sOCCCompSet = getNumberAsStringInExcelFormat(currentSheet.getRow(ROW_OCC_TRACT).getCell(CellReference.convertColStringToIndex("E"))).trim();
	                sOCCCompSetPC = getNumberAsStringInExcelFormat(currentSheet.getRow(ROW_OCC_TRACT).getCell(CellReference.convertColStringToIndex("F"))).trim();
	                sARRCompSet = getNumberAsStringInExcelFormat(currentSheet.getRow(ROW_ADR_TRACT).getCell(CellReference.convertColStringToIndex("G"))).trim();
	                sARRCompSetPC = getNumberAsStringInExcelFormat(currentSheet.getRow(ROW_ADR_TRACT).getCell(CellReference.convertColStringToIndex("H"))).trim();
	                sRPCompSet = getNumberAsStringInExcelFormat(currentSheet.getRow(ROW_REVPAR_TRACT).getCell(CellReference.convertColStringToIndex("G"))).trim();
	                sRPCompSetPC = getNumberAsStringInExcelFormat(currentSheet.getRow(ROW_REVPAR_TRACT).getCell(CellReference.convertColStringToIndex("H"))).trim();
	                break;
	                
	                
			    	case COL_END + 2:	            
			    	sTract = getStringCellValue(currentSheet.getRow(ROW_OCC_TRACT).getCell(CellReference.convertColStringToIndex("B"))).trim().replaceAll(REPLACE_TRACT, "");
	    	        sTractScale = getStringCellValue(currentSheet.getRow(ROW_OCC_TRACTSCALE).getCell(CellReference.convertColStringToIndex("B"))).trim().replaceAll(REPLACE_TRACT_SCALE, "");	
	                iGroup = 1;
	                sType = "SummaryTractScale";
	                sMonth = sMonthSummary;
	                
	                //set OC, ARR, RPC - TODO roundNumber
	    	        sOCCCompSet = getNumberAsStringInExcelFormat(currentSheet.getRow(ROW_OCC_TRACTSCALE).getCell(CellReference.convertColStringToIndex("E"))).trim();
	    	        sOCCCompSetPC = getNumberAsStringInExcelFormat(currentSheet.getRow(ROW_OCC_TRACTSCALE).getCell(CellReference.convertColStringToIndex("F"))).trim();
	    	        sARRCompSet = getNumberAsStringInExcelFormat(currentSheet.getRow(ROW_ADR_TRACTSCALE).getCell(CellReference.convertColStringToIndex("G"))).trim();
	    	        sARRCompSetPC = getNumberAsStringInExcelFormat(currentSheet.getRow(ROW_ADR_TRACTSCALE).getCell(CellReference.convertColStringToIndex("H"))).trim();
	    	        sRPCompSet = getNumberAsStringInExcelFormat(currentSheet.getRow(ROW_REVPAR_TRACTSCALE).getCell(CellReference.convertColStringToIndex("G"))).trim();
	    	        sRPCompSetPC = getNumberAsStringInExcelFormat(currentSheet.getRow(ROW_REVPAR_TRACTSCALE).getCell(CellReference.convertColStringToIndex("H"))).trim();	                
	                break;
	    	        
			    	case COL_END + 3:	            
			    	sTract = getStringCellValue(currentSheet.getRow(ROW_OCC_TRACT).getCell(CellReference.convertColStringToIndex("B"))).trim().replaceAll(REPLACE_TRACT, "");
	    	        sTractScale = getStringCellValue(currentSheet.getRow(ROW_OCC_TRACTSCALE).getCell(CellReference.convertColStringToIndex("B"))).trim().replaceAll(REPLACE_TRACT_SCALE, "");	
	                iGroup = 3;
	                sType = "SummaryTract";
	                
	                
	                //set OC, ARR, RPC - TODO roundNumber
	                sOCCCompSet = getNumberAsStringInExcelFormat(currentSheet.getRow(ROW_OCC_TRACT).getCell(CellReference.convertColStringToIndex("G"))).trim();
	                sOCCCompSetPC = getNumberAsStringInExcelFormat(currentSheet.getRow(ROW_OCC_TRACT).getCell(CellReference.convertColStringToIndex("H"))).trim();
	                sARRCompSet = getNumberAsStringInExcelFormat(currentSheet.getRow(ROW_ADR_TRACT).getCell(CellReference.convertColStringToIndex("G"))).trim();
	                sARRCompSetPC = getNumberAsStringInExcelFormat(currentSheet.getRow(ROW_ADR_TRACT).getCell(CellReference.convertColStringToIndex("H"))).trim();
	                sRPCompSet = getNumberAsStringInExcelFormat(currentSheet.getRow(ROW_REVPAR_TRACT).getCell(CellReference.convertColStringToIndex("G"))).trim();
	                sRPCompSetPC = getNumberAsStringInExcelFormat(currentSheet.getRow(ROW_REVPAR_TRACT).getCell(CellReference.convertColStringToIndex("H"))).trim();
	                break;
	            
			    	case COL_END + 4:	            
			    	sTract = getStringCellValue(currentSheet.getRow(ROW_OCC_TRACT).getCell(CellReference.convertColStringToIndex("B"))).trim().replaceAll(REPLACE_TRACT, "");
	    	        sTractScale = getStringCellValue(currentSheet.getRow(ROW_OCC_TRACTSCALE).getCell(CellReference.convertColStringToIndex("B"))).trim().replaceAll(REPLACE_TRACT_SCALE, "");	
	                iGroup = 3;
	                sType = "SummaryTractScale";
	            
	                //set OC, ARR, RPC - TODO roundNumber
	                sOCCCompSet = getNumberAsStringInExcelFormat(currentSheet.getRow(ROW_OCC_TRACTSCALE).getCell(CellReference.convertColStringToIndex("G"))).trim();
	    	        sOCCCompSetPC = getNumberAsStringInExcelFormat(currentSheet.getRow(ROW_OCC_TRACTSCALE).getCell(CellReference.convertColStringToIndex("H"))).trim();
	    	        sARRCompSet = getNumberAsStringInExcelFormat(currentSheet.getRow(ROW_ADR_TRACTSCALE).getCell(CellReference.convertColStringToIndex("G"))).trim();
	    	        sARRCompSetPC = getNumberAsStringInExcelFormat(currentSheet.getRow(ROW_ADR_TRACTSCALE).getCell(CellReference.convertColStringToIndex("H"))).trim();
	    	        sRPCompSet = getNumberAsStringInExcelFormat(currentSheet.getRow(ROW_REVPAR_TRACTSCALE).getCell(CellReference.convertColStringToIndex("G"))).trim();
	    	        sRPCompSetPC = getNumberAsStringInExcelFormat(currentSheet.getRow(ROW_REVPAR_TRACTSCALE).getCell(CellReference.convertColStringToIndex("H"))).trim();
	    	        break;
	            
			    	case COL_END + 5:
	            
		            sTract = getStringCellValue(currentSheet.getRow(ROW_OCC_TRACT).getCell(CellReference.convertColStringToIndex("B"))).trim().replaceAll(REPLACE_TRACT, "");
	    	        sTractScale = getStringCellValue(currentSheet.getRow(ROW_OCC_TRACTSCALE).getCell(CellReference.convertColStringToIndex("B"))).trim().replaceAll(REPLACE_TRACT_SCALE, "");	
	                iGroup = 4;
	                sType = "SummaryTract";
	            
	                //set OC, ARR, RPC - TODO roundNumber
	                sOCCCompSet = getNumberAsStringInExcelFormat(currentSheet.getRow(ROW_OCC_TRACT).getCell(CellReference.convertColStringToIndex("K"))).trim();
	                sOCCCompSetPC = getNumberAsStringInExcelFormat(currentSheet.getRow(ROW_OCC_TRACT).getCell(CellReference.convertColStringToIndex("L"))).trim();
	                sARRCompSet = getNumberAsStringInExcelFormat(currentSheet.getRow(ROW_ADR_TRACT).getCell(CellReference.convertColStringToIndex("K"))).trim();
	                sARRCompSetPC = getNumberAsStringInExcelFormat(currentSheet.getRow(ROW_ADR_TRACT).getCell(CellReference.convertColStringToIndex("L"))).trim();
	                sRPCompSet = getNumberAsStringInExcelFormat(currentSheet.getRow(ROW_REVPAR_TRACT).getCell(CellReference.convertColStringToIndex("K"))).trim();
	                sRPCompSetPC = getNumberAsStringInExcelFormat(currentSheet.getRow(ROW_REVPAR_TRACT).getCell(CellReference.convertColStringToIndex("L"))).trim();
	                break;
	            
			    	case COL_END + 6:
	            
			    	sTract = getStringCellValue(currentSheet.getRow(ROW_OCC_TRACT).getCell(CellReference.convertColStringToIndex("B"))).trim().replaceAll(REPLACE_TRACT, "");
	    	        sTractScale = getStringCellValue(currentSheet.getRow(ROW_OCC_TRACTSCALE).getCell(CellReference.convertColStringToIndex("B"))).trim().replaceAll(REPLACE_TRACT_SCALE, "");	
	                iGroup = 4;
	                sType = "SummaryTractScale";
	            
	                //set OC, ARR, RPC - TODO roundNumber
	                sOCCCompSet = getNumberAsStringInExcelFormat(currentSheet.getRow(ROW_OCC_TRACTSCALE).getCell(CellReference.convertColStringToIndex("K"))).trim();
	    	        sOCCCompSetPC = getNumberAsStringInExcelFormat(currentSheet.getRow(ROW_OCC_TRACTSCALE).getCell(CellReference.convertColStringToIndex("L"))).trim();
	    	        sARRCompSet = getNumberAsStringInExcelFormat(currentSheet.getRow(ROW_ADR_TRACTSCALE).getCell(CellReference.convertColStringToIndex("K"))).trim();
	    	        sARRCompSetPC = getNumberAsStringInExcelFormat(currentSheet.getRow(ROW_ADR_TRACTSCALE).getCell(CellReference.convertColStringToIndex("L"))).trim();
	    	        sRPCompSet = getNumberAsStringInExcelFormat(currentSheet.getRow(ROW_REVPAR_TRACTSCALE).getCell(CellReference.convertColStringToIndex("K"))).trim();
	    	        sRPCompSetPC = getNumberAsStringInExcelFormat(currentSheet.getRow(ROW_REVPAR_TRACTSCALE).getCell(CellReference.convertColStringToIndex("L"))).trim();
	    	        break;
	        
			    
			    }
			}//Else
			
			if(iGroup > 0){
				
				dayData = new SSRSnapshotStarData();
				
				dayData.setHotelId(hotelId);
				dayData.setType(sType);
				dayData.setGroup(iGroup);
				dayData.setSequence(iWSColIndex+5 + ROW_BASE_INDEX);
				dayData.setStarYear(Double.valueOf(sYear).intValue());
				dayData.setStarMonth(sMonth);
				dayData.setHotelCaption(sCapHotel);
				dayData.setMonthCaption(sCapWeek);
				dayData.setTract(sTract);
				dayData.setTractScale(sTractScale);
				//OCC
				dayData.setOccProp(sOCCProp);
				dayData.setOccPropPc(sOCCPropPC);
				dayData.setOccCompset(sOCCCompSet);
				dayData.setOccCompsetPc(sOCCCompSetPC);
				dayData.setOccIndex(sOCCIndex);
				dayData.setOccIndexPc(sOCCIndexPC);
				//ARR
				dayData.setArrProp(sARRProp);
				dayData.setArrPropPc(sARRPropPC);
				dayData.setArrCompset(sARRCompSet);
				dayData.setArrCompsetPc(sARRCompSetPC);
				dayData.setArrIndex(sARRIndex);
				dayData.setArrIndexPc(sARRIndexPC);
				//REVPAR
				dayData.setRevparProp(sRPProp);
				dayData.setRevparPropPc(sRPPropPC);
				dayData.setRevparCompset(sRPCompSet);
				dayData.setRevparCompsetPc(sRPCompSetPC);
				dayData.setRevparIndex(sRPIndex);
				dayData.setRevparIndexPc(sRPIndexPC);
				//MKTSH
				dayData.setMktshSupply(sMktShSupply);
				dayData.setMktshDemand(sMktShDemand);
				dayData.setMktshRev(sMktShRev);
				
				System.out.println(dayData);
				
				dayDataList.add(dayData);
				
				
			}
			
			
			
		}//For
	    
		return dayDataList;
		
		
	}
	
	private boolean isBetween(int x, int lower, int upper) {
		  return lower <= x && x <= upper;
	}
	
	private String findYear(HSSFSheet vWorksheet, int vColumn) {
	    int iCount;
	    String sValue;
	    
	    iCount = vColumn;
	    
	    do { 	        
	        sValue = getStringCellValue(vWorksheet.getRow(18).getCell(iCount)).trim();
	        //System.out.println("sValue:"+ sValue);
	        iCount = iCount - 1;
	    } while(sValue.equalsIgnoreCase("") || sValue.equalsIgnoreCase("0"));
	    
	    
	    return sValue;
	}



	private String formatHeader(String vHeader, String vSummaryMonth) {
	
		String sReturn = "";
		
		if(vHeader.equalsIgnoreCase("RUNNING 3 MONTH"))
			sReturn = "Three Months - Ending " + vSummaryMonth;
		else if (vHeader.equalsIgnoreCase("YEAR TO DATE"))
			sReturn = "Year to Date - Ending " + vSummaryMonth;
		else if (vHeader.equalsIgnoreCase("RUNNING 12 MONTH"))
			sReturn = "Twelve Months - Ending " +  vSummaryMonth;
		else
			sReturn = vHeader;
		
		return sReturn;
	
	}



	private String formatMonth(String vMonth) {
    
		String sReturn = "";
    
    
        if(vMonth.equalsIgnoreCase("JAN"))
            sReturn = "January";
        else if(vMonth.equalsIgnoreCase("FEB"))
        	sReturn = "February";
        else if(vMonth.equalsIgnoreCase("MAR"))
        	sReturn = "March";
        else if(vMonth.equalsIgnoreCase("APR"))
        	sReturn = "April";
        else if(vMonth.equalsIgnoreCase("MAY"))
        	sReturn = "May";
        else if(vMonth.equalsIgnoreCase("JUN"))
        	sReturn = "June";
        else if(vMonth.equalsIgnoreCase("JUL"))
        	sReturn = "July";
        else if(vMonth.equalsIgnoreCase("AUG"))
        	sReturn = "August";
        else if(vMonth.equalsIgnoreCase("SEP"))
        	sReturn = "September";
        else if(vMonth.equalsIgnoreCase("OCT"))
        	sReturn = "October";
        else if(vMonth.equalsIgnoreCase("NOV"))
        	sReturn = "November";
        else if(vMonth.equalsIgnoreCase("DEC"))
        	sReturn = "December";
        else
        	sReturn = vMonth;
        
        return sReturn;
      
	}
	
	
}