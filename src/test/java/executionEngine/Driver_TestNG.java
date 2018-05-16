package executionEngine;

import java.io.File;
import java.io.IOException;
//import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
//import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;

//import com.commonUtilities.ExcelMethods;
import com.google.common.io.Files;
import config.*;
import utility.*;

public class Driver_TestNG {
	
    WebDriver driver;
	
	//This is a class object, declared as 'public static'
	//So that it can be used outside the scope of main[] method
	public static ActionKeywords action_Kw;
	public static String sActionKeyword;
	public static String sPageObject;
	public static String sTestdata;
		
	//This is reflection class object, declared as 'public static'
	//So that it can be used outside the scope of main[] method
	public static Method method_arr[];	
	
	//Declaring Excel related variables
	public static int iTestStep;
	public static int iTestLastStep;
	public static String sTestCaseID;
	public static String sRunMode;
	public static boolean bResult;
	
	//Browser Type
	public static String browser_type;
	
	File testCaseFile, reportFile;
	
	/*
	//Here we are instantiating a new object of class 'ActionKeywords'
	public Driver_TestNG() throws NoSuchMethodException, SecurityException
	{
		
		action_Kw = new ActionKeywords(driver);
		
		//This will load all the methods of the class 'ActionKeywords' in it.
	    //It will be like array of method, use the break point here and do the watch
		method_arr = action_Kw.getClass().getMethods();
		//Reflection is a very useful approach to deal with the Java class at runtime 
    	//as it can be use to load the Java class, call its methods or analysis the class 
    	//at runtime.
		//System.out.println("In ActionKeywords: "+actionKeywords);
		
		System.out.println("Reflection Methods count: "+method_arr.length);
		
	}
	*/
	
 @BeforeTest
 @Parameters("browser")
 public void initialize_parameters( String browser) throws Exception
 //public void initialize_parameters() throws Exception
  {
	 
	 //Selection of Browser
	  if(browser.equalsIgnoreCase("firefox"))
		{
			System.setProperty("webdriver.gecko.driver",Constants.strFirefoxPath);
			//System.setProperty("webdriver.firefox.bin","C:\\Program Files\\Mozilla Firefox\\firefox.exe");
			//System.setProperty("webdriver.gecko.driver","C:\\Program Files\\Mozilla Firefox\\firefox.exe");
			
			//FirefoxOptions options = new FirefoxOptions();
			//options.setBinary("C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe"); //This is the location where you have installed Firefox on your machine
	 
			//driver = new FirefoxDriver(options);
			
			driver = new FirefoxDriver();
			System.out.println("In Firefox Browser");
		}
	  else if(browser.equalsIgnoreCase("chrome"))
		{		
			System.setProperty("webdriver.chrome.driver", Constants.strChromePath);
			driver = new ChromeDriver();
			System.out.println("In Chrome Browser");
		}
	  else if(browser.equalsIgnoreCase("iexplorer"))
		{		
			System.setProperty("webdriver.ie.driver", Constants.strIEPath);
			
			// Create the DesiredCapability object of InternetExplorer
			 DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
	 
			 // Settings to Accept the SSL Certificate in the Capability object
			 capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			
			 //https://sqa.stackexchange.com/questions/13077/unable-to-run-selenium-webdriver-script-in-ie11
			 capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
			 
			 capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
			 
			 //driver = new InternetExplorerDriver();
			 driver= new InternetExplorerDriver(capabilities); 
			 System.out.println("In IE Browser");
			
		}
		else
		{
			throw new Exception("Browser is not correct");
		}
			
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  driver.manage().window().maximize();
		
	  	
	  testCaseFile = new File(Constants.Path_TestData + "\\" + Constants.File_TestData);	
	  reportFile = new File(Constants.Test_ReportPath + "\\" + Constants.File_Report);
	
 }
	

//@Test
//@Parameters("browser")
//public void executeTestCases(String browser) throws Exception {
 @Test(retryAnalyzer = config.RetryAnalyzer.class) 
public void executeTestCases() throws Exception {	  
		     
	     action_Kw = new ActionKeywords(driver);
		
		//This will load all the methods of the class 'ActionKeywords' in it.
	    //It will be like array of method, use the break point here and do the watch
		 method_arr = action_Kw.getClass().getMethods();
		//Reflection is a very useful approach to deal with the Java class at runtime 
  	    //as it can be use to load the Java class, call its methods or analysis the class 
  	    //at runtime.
		
		System.out.println("Reflection Methods count: "+method_arr.length);
		
		  
	      //IF REPORT FILE EXIST THEN DELETE IT Otherwise just copy the file
		  if(!reportFile.exists())
			{
				Files.copy(testCaseFile, reportFile);
			}
			else
			{
				reportFile.delete();
				Files.copy(testCaseFile, reportFile);
			}
		  
		  
		  // Here we are passing the Excel path and SheetName as arguments to connect with Excel file
		  ExcelUtils.setExcelFile(reportFile.getPath());
		  
		  
		  
		  //This is to start the Log4j logging in the test case
	      DOMConfigurator.configure("log4j.xml");
	      
	      //Creating StartEngine for this DriverScript class itself
		  //Driver_TestNG startEngine = new Driver_TestNG(); 
	      
	      //Call to method execution start
		  execute_TestCase();
		  
    }


//This Method is going to start test cases execution 
private void execute_TestCase() throws Exception 
	{
		//This will return the total number of test cases mentioned in the Test cases sheet
  	    int iTotalTestCases = ExcelUtils.getRowCount(Constants.Sheet_TestCases);
  	
  	    //This loop will execute number of times equal to Total number of test cases
		for(int iTestcase=1;iTestcase<=iTotalTestCases;iTestcase++)
		{
			
			//Setting the value of bResult variable to 'true' before starting every test case
			bResult = true;
			
			//This is to get the Test case name from the Test Cases sheet
			sTestCaseID = ExcelUtils.getCellData(iTestcase, Constants.Col_TestCase_ID, Constants.Sheet_TestCases); 
			
			//This is to get the value of the Run Mode column for the current test case
			sRunMode = ExcelUtils.getCellData(iTestcase, Constants.Col_Testcase_RunMode,Constants.Sheet_TestCases);
			
			
			System.out.println("Testcase no ----------------->"+sTestCaseID);
			
			//This is the condition statement on RunMode value
			if (sRunMode.equalsIgnoreCase("YES")){
				
				    //Constants.Snapshot_Testcase_id=sTestCaseID;
				    Constants.Snapshot_file= Driver_TestNG.sTestCaseID +".jpeg";
				    System.out.println("Testcase no ----------------->"+Constants.Snapshot_file);
				    
					//Only if the value of Run Mode is 'Yes', this part of code will execute
				    iTestStep = ExcelUtils.getRowContains(sTestCaseID, Constants.Col_Teststep_ID, Constants.Sheet_TestSteps);
					iTestLastStep = ExcelUtils.getTestStepsCount(Constants.Sheet_TestSteps, sTestCaseID, iTestStep);
					Log.startTestCase(sTestCaseID);
					
					System.out.println("First Step:----------------->"+iTestStep);
					System.out.println("Last Step:----------------->"+iTestLastStep);
					
					//Setting the value of bResult variable to 'true' before starting every test step
					bResult=true;
					
					
							//This loop will execute number of times equal to Total number of test steps
							for (;iTestStep<=iTestLastStep;iTestStep++)
							{
					    		sActionKeyword = ExcelUtils.getCellData(iTestStep, Constants.Col_Teststep_ActionKeyword,Constants.Sheet_TestSteps);
					    		sPageObject = ExcelUtils.getCellData(iTestStep, Constants.Col_Teststep_PageObject, Constants.Sheet_TestSteps);
					    		sTestdata= ExcelUtils.getCellData(iTestStep, Constants.Col_Teststep_Data, Constants.Sheet_TestSteps);
					    		
					    		//System.out.println("Executing Step:"+iTestStep);
					    		
					    		execute_Actions();
					    		
								//This is the result code, this code will execute after each test step
								//The execution flow will go in to this only if the value of bResult is 'false'
								if(bResult==false)
								{
									//If 'false' then store the test case result as Fail
									ExcelUtils.setCellData(Constants.KEYWORD_FAIL,iTestcase,Constants.Col_Testcase_Result,Constants.Sheet_TestCases);
									
									//End the test case in the logs
									Log.endTestCase(sTestCaseID);
									//By this break statement, execution flow will not execute any more test step of the failed test case
									break;
								}
								//System.out.println("Completed Execution of Step:"+iTestStep);
	
					         }
					
							//This will only execute after the last step of the test case, if value is not 'false' at any step	
							if(bResult==true){
							//Storing the result as Pass in the excel sheet
							ExcelUtils.setCellData(Constants.KEYWORD_PASS,iTestcase,Constants.Col_Testcase_Result,Constants.Sheet_TestCases);
							Log.endTestCase(sTestCaseID);	
			 	            }
					
			 }else {
					
					ExcelUtils.setCellData("Not Executed",iTestcase,Constants.Col_Testcase_Result,Constants.Sheet_TestCases);
					
			 }
  	}

}

//----------------------------------------------------------------------------------------
//This method contains the code to perform some action
//As it is completely different set of logic, which revolves around the action only,
//It makes sense to keep it separate from the main driver script
//This is to execute test step (Action)
private static void execute_Actions() throws Exception {
	
	System.out.println("Keyword name: "+sActionKeyword);
	System.out.println("PageObject name:"+sPageObject);
	System.out.println("TestData:"+sTestdata);
	
	//Method m1 = action_Kw.getClass().getMethod(String.class, String.class, String.class);
	
	//This is a loop which will run for the number of actions in the Action Keyword class 
	//method variable contain all the method and method.length returns the total number of methods
	for(int i = 0;i < method_arr.length;i++)
	{
		//This is now comparing the method name with the ActionKeyword value got from excel
		if(method_arr[i].getName().equals(sActionKeyword))
		{
			
			//System.out.println("In execute_Actions");
			
			HashMap<String,String> hm = new HashMap<String,String>();
			
			//hm.put("Keyword", sActionKeyword);
			
			if (sPageObject==null)
				hm.put("Object", "");
			else
				hm.put("Object", sPageObject);
			
			if (sTestdata==null)
				hm.put("TestData", "");
			else
				hm.put("TestData", sTestdata);
			
			//In case of match found, it will execute the matched method
			//method_arr[i].invoke(sActionKeyword,sPageObject,sTestdata);
			
			Method m1 = action_Kw.getClass().getMethod(sActionKeyword, String.class, String.class); //****Significance of Reflection Method Object?
			m1.invoke(action_Kw,hm.get("Object"),hm.get("TestData"));
			
			System.out.println("Value of Last call:"+bResult);
			//This code block will execute after every test step
			if(bResult==true){
				//If the executed test step value is true, Pass the test step in Excel sheet
				ExcelUtils.setCellData(Constants.KEYWORD_PASS, iTestStep, Constants.Col_Teststep_Result, Constants.Sheet_TestSteps);
				
				break;
			}else{
				
				//If the executed test step value is false, Fail the test step in Excel sheet
				ExcelUtils.setCellData(Constants.KEYWORD_FAIL, iTestStep, Constants.Col_Teststep_Result, Constants.Sheet_TestSteps);
				
				//In case of false, the test execution will not reach to last step of closing browser
				//So it make sense to close the browser before moving on to next test case
				//action_Kw.closeBrowser("","");
			
				break;
				}
			
			//Once any method is executed, this break statement will take the flow outside of for loop
			//break;
			}
		}
	}



}
