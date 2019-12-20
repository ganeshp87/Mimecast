package com.mimecast.qa.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


import org.testng.Assert;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;



public class ExcelUtility {

	public static String row;

//Start and ende keyword : Eg: AddTestData :: This will start read from start keyword and ends with End Keyword.	
	public String[][] getTableArray(String relativeExcelFile, String sheetName, String keyword) throws BiffException, IOException{
		String[][] tableArray=null;
        InputStream file;
        String fullPath=System.getProperty("user.dir")+relativeExcelFile;
		try {
			file = new FileInputStream(fullPath);
			Assert.assertNotNull(file,"File not found: "+ fullPath);
			
			 Workbook workbook = Workbook.getWorkbook(file);
			 Sheet sheet = workbook.getSheet(sheetName);
			 int startRow,startCol, endRow,endCol,ci,cj;
			 Cell tableStart = sheet.findCell(keyword);
			 startRow = tableStart.getRow();
			 startCol=tableStart.getColumn();
			 Cell tableEnd=sheet.findCell(keyword,startCol+1,startRow+1,100,64000,false);
			 endRow = tableEnd.getRow();
			 endCol=tableEnd.getColumn();
			 tableArray=new String[endRow-startRow-1][endCol-startCol-1];
			 ci=0;
			for(int i=startRow+1;i<endRow;i++,ci++){
				cj=0;
				for(int j=startCol+1;j<endCol;j++,cj++){
					tableArray[ci][cj]=sheet.getCell(j,i).getContents();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Assert.fail("Error Reading Excel File",e);
		}
	
		return (tableArray);
   }
	
	//Start cell Ex:"A1"  , "B5"
	//End Cell Ex: "D10", "F25"
	
	public String[][] getTableArray(String relativeExcelFile, String sheetName, String startCell,String endCell) throws BiffException, IOException{
		String[][] tableArray=null;
        InputStream file;
        String fullPath=System.getProperty("user.dir")+relativeExcelFile;
		try {
			file = new FileInputStream(fullPath);
			Assert.assertNotNull(file,"File not found: "+ fullPath);
			
			 Workbook workbook = Workbook.getWorkbook(file);
			 Sheet sheet = workbook.getSheet(sheetName);
			 int startRow,startCol, endRow,endCol,ci,cj;
			 Cell tableStart = sheet.getCell(startCell);
			 startRow = tableStart.getRow();
			 startCol=tableStart.getColumn();
			 Cell tableEnd=sheet.getCell(endCell);
			 endRow = tableEnd.getRow();
			 endCol=tableEnd.getColumn();
			 tableArray=new String[endRow-startRow+1][endCol-startCol+1];
			 ci=0;
			for(int i=startRow;i<=endRow;i++,ci++){
				cj=0;
				for(int j=startCol;j<=endCol;j++,cj++){
					tableArray[ci][cj]=sheet.getCell(j,i).getContents();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Assert.fail("Error Reading Excel File",e);
		}
	
		return (tableArray);
   }
}	
