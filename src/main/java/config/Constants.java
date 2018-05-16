package config;

import java.text.SimpleDateFormat;
import java.util.Date;

import executionEngine.Driver_TestNG;

public class Constants {

	//This is the list of System Variables
    //Declared as 'public', so that it can be used in other classes of this project
    //Declared as 'static', so that we do not need to instantiate a class object
    //Declared as 'final', so that the value of this variable can't be changed
    //'String' & 'int' are the data type for storing a type of value	
	
	//public static final String strChromePath="C:\\Users\\mampi\\Desktop\\Selenium\\Selenium Software\\Jars\\chromedriver_win32\\chromedriver.exe";
	public static final String strChromePath = System.getProperty("user.dir") + "\\src\\main\\resources\\chromedriver.exe";	
	public static final String strFirefoxPath = System.getProperty("user.dir") + "\\src\\main\\resources\\geckodriver.exe";
	public static final String strIEPath = System.getProperty("user.dir") + "\\src\\main\\resources\\IEDriverServer.exe";
	
	//public static final String Path_TestData = "C:\\Users\\mampi\\eclipse-workspace\\Keyword_Driven_Framework\\src\\dataEngine\\DataEngine.xlsx";
	public static final String Path_TestData = System.getProperty("user.dir") + "\\src\\test\\resources\\";
	public static final String File_TestData = "DataEngine.xlsx";
 
	public static final String Test_ReportPath = System.getProperty("user.dir") + "\\target\\output";
	public static final String File_Report= "TestReport.xlsx";
	
	
	Date date_snap = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
	//public static String Snapshot_Testcase_id ="";
	//public static String formattedDate = sdf.format(date_snap);
	public static final String ScreenshotPath = System.getProperty("user.dir") + "\\target\\output";
	public static String Snapshot_file= Driver_TestNG.sTestCaseID +".jpeg";
	
	//List of Data Sheet Column Numbers
	public static final int Col_TestCase_ID = 0;	
	public static final int Col_Testcase_RunMode =2 ;
	public static final int Col_Testcase_Result =3 ;
	
	public static final int Col_Teststep_ID = Col_TestCase_ID;	
	public static final int Col_Teststep_ScenarioID =1 ;
    public static final int Col_Teststep_ActionKeyword =3 ;
    public static final int Col_Teststep_PageObject =4 ;  
	public static final int Col_Teststep_Data =5 ;
	public static final int Col_Teststep_Result =6 ;
    public static final int Col_Teststep_Error_Msg =7 ;
    public static final int Col_Teststep_Timestamp =8;
  	
	//List of Data Engine Excel sheets
	public static final String Sheet_TestSteps = "Test Steps";
 
	//Constant variable for Test cases
    public static final String Sheet_TestCases = "Test Cases";
		
    //Constants variables for the Pass results & Fail result
    public static final String KEYWORD_FAIL = "FAIL";
    public static final String KEYWORD_PASS = "PASS";
    
    //Constant variables to assign error messages
    public static String Err_details="";
    
    //public static int prod_selected;
    public static int item_selected;
    public static int qty_cnt=1;
    public static float cal_price=0;
    
}
