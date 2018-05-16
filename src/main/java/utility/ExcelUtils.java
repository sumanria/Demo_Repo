package utility;

import java.io.*;
import java.text.*;
import java.util.*;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;


import config.Constants;
import executionEngine.Driver_TestNG;

public class ExcelUtils {
    private static XSSFSheet ExcelWSheet;
    private static XSSFWorkbook ExcelWBook;
    
    //private static XSSFCell Cell;
    private static org.apache.poi.ss.usermodel.Cell Cell;
    private static org.apache.poi.ss.usermodel.Cell Cell_Time;
    private static org.apache.poi.ss.usermodel.Cell Cell_Error;
    private static XSSFRow Row;
    
    //This method is to set the File path and to open the Excel file
    //Pass Excel Path and SheetName as Arguments to this method
    public static void setExcelFile(String Path) throws Exception 
    {
    	try {
            FileInputStream ExcelFile = new FileInputStream(Path);
            ExcelWBook = new XSSFWorkbook(ExcelFile);
            //ExcelWSheet = ExcelWBook.getSheet(SheetName);   
            //System.out.println("Target Path:"+Path);
            
         }catch (Exception e){
				Log.error("Class Utils | Method setExcelFile | Exception desc : "+e.getMessage());
				System.out.println(e.getMessage());
				Driver_TestNG.bResult = false;
				}
    }

    //This method is to read the test data from the Excel cell
    //In this we are passing Arguments as Row Num, Col Num & Sheet Name
    public static String getCellData(int RowNum, int ColNum, String SheetName ) throws Exception{
        	
        	 try{
        	  ExcelWSheet = ExcelWBook.getSheet(SheetName);	 
           	  Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
                 String CellData = Cell.getStringCellValue();
                 return CellData;
                 }catch (Exception e){
                	 Log.error("Class Utils | Method getCellData | Exception desc : "+e.getMessage());
                	 Driver_TestNG.bResult = false;
                     return"";
                 }
    	}

	//This method is to get the row count used of the excel sheet
	public static int getRowCount(String SheetName){
		
		int iNumber=0;
		try {
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			//iNumber=ExcelWSheet.getLastRowNum()+1;
			iNumber=ExcelWSheet.getLastRowNum();
			//System.out.println("Total no of rows in "+SheetName+" ----------------->"+iNumber);
		}catch (Exception e){
			Log.error("Class Utils | Method getRowCount | Exception desc : "+e.getMessage());
			Driver_TestNG.bResult = false;
			}
			
			return iNumber;
		}
	
	   //Clear Previous Test Results
		public static void clearCellData(String SheetName,int col_num) throws Exception    
		{
			try {
				
				
				if (SheetName.equals(Constants.Sheet_TestSteps))
				{	
					for (int i=1;i<=ExcelUtils.getRowCount(SheetName);i++)
					{
						   Row  = ExcelWSheet.getRow(i);
						   Cell = Row.getCell(col_num);
						   Cell.setCellValue("");
						   
						   //Cell_Error = Row.createCell(col_num+1);
						   //Cell_Error.setCellValue("");
						   //System.out.println("Test step Results Column No: "+col_num);
						
					}
					
				}else if (SheetName.equals(Constants.Sheet_TestCases))
				{
					
					for (int i=1;i<=ExcelUtils.getRowCount(SheetName);i++)
					{
						   Row  = ExcelWSheet.getRow(i);
						   Cell = Row.getCell(col_num);
						   Cell.setCellValue("");
						   //System.out.println("Test case Results Column No: "+col_num);
						
					}
					
					
				}
				
				// Constant variables Test Data path and Test Data file name
				//FileOutputStream fileOut = new FileOutputStream(Constants.Path_TestData);
				//ExcelWBook.write(fileOut);
				//fileOut.close();
				//ExcelWBook = new XSSFWorkbook(new FileInputStream(Constants.Path_TestData));
			}catch (Exception e)
			{
				Log.error("Class Utils | Method Clear data | Exception desc : "+e.getMessage());
				Driver_TestNG.bResult = false;
			}
				   
	     }

	//This method is to get the Row number of the test case in sheet test step
	//This methods takes three arguments(Test Case name , Column Number & Sheet name)
	public static int getRowContains(String sTestCaseName, int colNum,String SheetName) throws Exception{
		
		int iRowNum=0;	
		try {
			//ExcelWSheet = ExcelWBook.getSheet(SheetName);
			int rowCount = ExcelUtils.getRowCount(SheetName);
			for (; iRowNum<rowCount; iRowNum++){
				if  (ExcelUtils.getCellData(iRowNum,colNum,SheetName).equalsIgnoreCase(sTestCaseName)){
					break;
				}
			}       			
		} catch (Exception e){
			Log.error("Class Utils | Method getRowContains | Exception desc : "+e.getMessage());
			Driver_TestNG.bResult = false;
			}
		return iRowNum;
		
	}

	//This method is to get the count of the test steps of test case
	//This method takes three arguments (Sheet name, Test Case Id & Test case row number)
	public static int getTestStepsCount(String SheetName, String sTestCaseID, int iTestCaseStart) throws Exception{
		
		try {
			for(int i=iTestCaseStart;i<=ExcelUtils.getRowCount(SheetName);i++)
			{
				
				//System.out.println("Now of rows:"+ExcelUtils.getRowCount(SheetName));
				if(!sTestCaseID.equals(ExcelUtils.getCellData(i, Constants.Col_TestCase_ID, SheetName)))
				{
					     //Continue if blank value mentioned 
					     if (ExcelUtils.getCellData(i, Constants.Col_TestCase_ID, SheetName).equals(""))
					     {
					    	 
					    	  continue;
					    	   
					     }
					     //System.out.println("Inside If");   
					     int number = i-1;
					     return number;      				
					}
				
			 }
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			//int number=ExcelWSheet.getLastRowNum()+1;
			int number=ExcelWSheet.getLastRowNum();
			return number;
		} catch (Exception e){
			Log.error("Class Utils | Method getRowContains | Exception desc : "+e.getMessage());
			Driver_TestNG.bResult = false;
			return 0;
		}
	}
    

	//@SuppressWarnings("static-access")
	//This method is use to write value in the excel sheet
	//This method accepts four arguments (Result, Row Number, Column Number & Sheet Name)
	public static void setCellData(String Result,  int RowNum, int ColNum, String SheetName) throws Exception    {
		   try{
	 
			   //System.out.println("Inside setCellData"+Result);
			   //System.out.println("Inside setCellData"+RowNum);
			   //System.out.println("Inside setCellData"+ColNum);
			   //System.out.println("Inside setCellData"+SheetName);
			   
			   ExcelWSheet = ExcelWBook.getSheet(SheetName);
			   Row  = ExcelWSheet.getRow(RowNum);
			   
			   //Cell = Row.getCell(ColNum, Row.RETURN_BLANK_AS_NULL);
			   Cell = Row.getCell(ColNum);
			   
			   if (Cell == null) {
				   
				   Cell = Row.createCell(ColNum);
				   Cell.setCellValue(Result);
				   
				   
			   } else {
				   
				   Cell.setCellValue(Result);
				   
				   if (SheetName.equals(Constants.Sheet_TestSteps)) {
					   
					   Date date = new Date();
					   SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
					   String formattedDate = sdf.format(date);
					   
					   Cell_Time = Row.createCell(Constants.Col_Teststep_Timestamp);
					   Cell_Time.setCellValue(formattedDate);
					   
				   }
				  
				   
			   }   
			   
			   //To print Errors associated with steps
			   if (SheetName.equals(Constants.Sheet_TestSteps) && !Constants.Err_details.equals("")) {
				   
				   Cell_Error = Row.createCell(ColNum+1);
				   Cell_Error = Row.createCell(Constants.Col_Teststep_Error_Msg);
				   
				   Cell_Error.setCellValue(Constants.Err_details);
				   
				   Constants.Err_details="";
				   //System.out.println(Constants.Err_details);
				   //System.out.println(Cell_Error);
				
			   }
			   
				// Constant variables Test Data path and Test Data file name
				FileOutputStream fileOut = new FileOutputStream(Constants.Test_ReportPath + "\\" + Constants.File_Report);
				ExcelWBook.write(fileOut);
				//fileOut.flush();
				fileOut.close();
				ExcelWBook = new XSSFWorkbook(new FileInputStream(Constants.Test_ReportPath + "\\" + Constants.File_Report));
				//System.out.println("Inside setCellData woithout exception result:"+Driver_TestNG.bResult);
			 }catch(Exception e){
				 Driver_TestNG.bResult = false;
				 //System.out.println("Inside setCellData exception result:"+Driver_TestNG.bResult);
				}
			}

}
