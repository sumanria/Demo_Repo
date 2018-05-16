package config;

import java.io.File;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.io.IOException;
import java.lang.reflect.Method;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import com.google.common.io.Files;

//import static executionEngine.DriverScript_with_Testdata.*;//?????
import org.openqa.selenium.*;

import executionEngine.Driver_TestNG;
import utility.Log;

public class ActionKeywords {
	
	//public static WebDriver driver;
	WebDriver driver;
	HashMap<String,Integer> hm_items = new HashMap<String,Integer>();
	
	public ActionKeywords(WebDriver driver) {
		
		this.driver = driver;
		
	}
	
	//All the methods in this class now accept 'Object' name as an argument
	//object argument is passed in every method, 
	//even if it is not required in the method, such as ‘closeBrowser()’. 
	//This is the mandatory condition of the reflection class that all the methods 
	//will have same arguments, even if the argument is not used in some methods.
	
	public By getObject(String object) throws Exception 
	{
		String[] pageObject = object.split("~");
		
		System.out.println("Page name:"+pageObject[0]);
		System.out.println("Page element:"+pageObject[1]);
		
		Class c = Class.forName("pages." + pageObject[0]);   // Usage of Class.forName? 
		//System.out.println("Class c value:"+c);
		
		Object obj = c.newInstance();
		//System.out.println("Object of c class:"+obj);
		
			
		Method m = obj.getClass().getMethod("getObjectProperty", String.class);
		//System.out.println("Method created:"+m);
			
		String objElement = (String) m.invoke(obj, pageObject[1]);
		//System.out.println("Element Returned:"+m.invoke(obj, pageObject[1]));
		
		String locator = objElement.substring(0, objElement.indexOf('='));
		//System.out.println("Locator found:"+locator);
		
		String property = objElement.substring(objElement.indexOf('=')+1);
		//System.out.println("property found:"+property);
		
		if (locator.equalsIgnoreCase("xpath"))
			return By .xpath(property);
		else if (locator.equalsIgnoreCase("id"))
			return By .id(property);
		else if (locator.equalsIgnoreCase("name"))
			return By .name(property);
		else if (locator.equalsIgnoreCase("css"))
			return By .cssSelector(property);
		else if (locator.equalsIgnoreCase("linkText"))
			return By .linkText(property);
		else if (locator.equalsIgnoreCase("partialLinkText"))
			return By .partialLinkText(property);
		else
			throw new Exception ("Wrong object type");
	}
	
	public By getObject(String object,int item) throws Exception 
	{
		String[] pageObject = object.split("~");
		
		System.out.println("Page name:"+pageObject[0]);
		System.out.println("Page element:"+pageObject[1]);
		System.out.println("Item in get object:"+item);
		
		Class c = Class.forName("pages." + pageObject[0]);   // Usage of Class.forName? 
		//System.out.println("Class c value:"+c);
		
		Object obj = c.newInstance();
		//System.out.println("Object of c class:"+obj);
		
			
		Method m = obj.getClass().getMethod("getObjectProperty", String.class,int.class);
		//System.out.println("Method created:"+m);
			
		String objElement = (String) m.invoke(obj, pageObject[1],item);
		System.out.println("Element Returned:"+m.invoke(obj, pageObject[1],item));
		
		String locator = objElement.substring(0, objElement.indexOf('='));
		//System.out.println("Locator found:"+locator);
		
		String property = objElement.substring(objElement.indexOf('=')+1);
		//System.out.println("property found:"+property);
		
		if (locator.equalsIgnoreCase("xpath"))
			return By .xpath(property);
		else
			throw new Exception ("Wrong object type");
	}
	
	
/*
	public void openBrowser(String object,String testData) throws IOException{	
		
		try{
			
			Log.info("Opening Browser");
			//Selection of Browser
			  if(Driver_TestNG.browser_type.equalsIgnoreCase("firefox"))
				{
					System.setProperty("webdriver.firefox.marionette",Constants.strFirefoxPath);
					driver = new FirefoxDriver();
					System.out.println("In Firefox Browser");
				}
			  else if(Driver_TestNG.browser_type.equalsIgnoreCase("chrome"))
				{		
					System.setProperty("webdriver.chrome.driver", Constants.strChromePath);
					driver = new ChromeDriver();
					System.out.println("In Chrome Browser");
				}
			  else if(Driver_TestNG.browser_type.equalsIgnoreCase("iexplorer"))
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
			
		}catch(Exception e){
			Log.info("Not able to open Browser --- " + e.getMessage());
			Driver_TestNG.bResult = false;
			takeScreenShot(Constants.Test_ReportPath+"\\strTestCaseName.png");
			
			}
		
	}
	*/
	public void navigate(String object,String testData) throws IOException
	{	
		try{
			
			Log.info("Navigating to URL");
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.get(testData);
			
		}catch(Exception e){
			Log.info("Not able to navigate --- " + e.getMessage());
			takeScreenShot(Constants.ScreenshotPath+"\\"+Constants.Snapshot_file);
			Driver_TestNG.bResult = false;
			}
		
		
	}
	 
	public void input_txt(String object,String testData) throws Exception{
		try{
			
			Log.info("Entering the text");
			
			System.out.println("within input: "+object);
			
			//System.out.println("Test data:"+testData);
			//System.out.println("Username xpath:"+getObject(object));
			
			//EXPLICIT WAIT
			WebDriverWait wait = new WebDriverWait(driver,20);
			
			//WebElement elementOpen4;
			//elementOpen4=wait.until(ExpectedConditions.visibilityOfElementLocated(By .xpath("//div[@class='J-N-Jz' and text()='Social']")));
			
			
			if (object.contains("item_qty"))
			{
				String[] arr_test_data = testData.split("~");
				String old_qty="";
				int pos_no=Search_Spec_Item("toolsqa~cart_item_name",arr_test_data[0]);
				System.out.println("input box pos_no:"+pos_no);
				driver.findElement(getObject(object,pos_no)).click();
				old_qty=driver.findElement(getObject(object,pos_no)).getAttribute("value");
				driver.findElement(getObject(object,pos_no)).clear();
				if (testData.equals(""))
				{    
					System.out.println("Blank Qty");
					driver.findElement(getObject(object,pos_no)).sendKeys("1");
				}
				
				else
				{   
					
					 old_qty=String.valueOf(Integer.parseInt(old_qty)-1);
				     driver.findElement(getObject(object,pos_no)).sendKeys(String.valueOf((Integer.parseInt(old_qty)+Integer.parseInt(arr_test_data[1]))));
					 
				
				}
			}
			else {
				System.out.println("Inside Others");
				//WebElement obj;
				//obj= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='pwd']")));
				//obj.click();
				//obj.clear();
				//obj.sendKeys(testData);
				driver.findElement(getObject(object)).click();
				driver.findElement(getObject(object)).clear();
				driver.findElement(getObject(object)).sendKeys(testData);
			}
				
			//WebElement qty_box;
			//qty_box=wait.until(ExpectedConditions.visibilityOfElementLocated(getObject(object));
			//WaitForElement(qty_box);
		    //qty_box.clear();
			//qty_box.sendKeys("5");
			
			
		}catch(Exception e){
			 Log.error("Not able to Enter text --- " + e.getMessage());
			 //Err_details=e.getMessage();
			 //System.out.println("Error while Username: "+e.getMessage());
			 takeScreenShot(Constants.ScreenshotPath+"\\"+Constants.Snapshot_file);
			 Constants.Err_details=e.getMessage();
			 Driver_TestNG.bResult = false;
		 	}
		 	
		
	}
	
	/*
	public  void input_Password(String object,String testData){
		try {
			Log.info("Entering the text in Password");
			
			System.out.println("within input_Password: "+object);
			driver.findElement(getObject(object)).sendKeys(testData);
			System.out.println("Password xpath: "+getObject(object));
			
		}catch(Exception e){
			 Log.error("Not able to Enter Password --- " + e.getMessage());
			 Constants.Err_details=e.getMessage();
			 Driver_TestNG.bResult = false;
		 	}
		
	}
	*/ 
		 
	public void waitFor(String object,String testData) throws Exception{
		try{
			Log.info("Wait for 6 seconds");
			Thread.sleep(6000);
			
		}catch(Exception e){
			 Log.error("Not able to Wait --- " + e.getMessage());
			 Driver_TestNG.bResult = false;
        	}
	
	}
	
	public void VerifyText(String object,String testData) throws Exception
	{
		try {
			
			 Log.info("Verifying Text "+ object);
		     String actualText = driver.findElement(getObject(object)).getText();
		     
		     Assert.assertTrue(actualText.equals(testData));
		          
		}	catch(AssertionError  e){
			 Log.error("Not able to Validate text --- " + e.getMessage());
			 Constants.Err_details=e.getMessage();
			 takeScreenShot(Constants.ScreenshotPath+"\\"+Constants.Snapshot_file);
			 Driver_TestNG.bResult = false;
       	}
		
	}
	
	public void Search_Item(String object,String testData) throws Exception
	{
		try {
			
			 //int item=1; 
			 Log.info("Searching Text "+ object);
			 List<WebElement> list_item = null;
			 Constants.item_selected=0;
			 
			 if (object.contains("item_heading"))
			 {
			    list_item=driver.findElements(getObject("toolsqa~items_displayed"));
			    System.out.println("No. of items displayed"+list_item.size());
			 }	
			 else if (object.contains("prod_cat"))
			 {
				 list_item=driver.findElements(getObject("toolsqa~prod_cat_list"));
				 System.out.println("No. of items displayed"+list_item.size());
				 
			 }
			 
				 //List<WebElement> cols=driver.findElements(By.xpath("//table[@class='checkout_cart']/tbody/tr[2]/td"));
				 //System.out.println("No. of heading "+cols.size());
					
			 for(int i=1;i<=list_item.size();i++)
			 { 
			    
				 String actualText = driver.findElement(getObject(object,i)).getAttribute("text");
		     
		         if (actualText.equalsIgnoreCase(testData))
		         {
		        	 
		        	 Constants.item_selected=i;
		    	     System.out.println("Item Selected:"+actualText);
		    	     break;
		         }
	
			 }
		         
		}	catch(AssertionError  e){
			 Log.error("Not able to Validate text --- " + e.getMessage());
			 Constants.Err_details=e.getMessage();
			 takeScreenShot(Constants.ScreenshotPath+"\\"+Constants.Snapshot_file);
			 Driver_TestNG.bResult = false;
       	}
		
	}
	 
	public void click(String object,String testData) throws IOException{
		try{
			
			Log.info("Clicking on Webelement "+ object);
			
			System.out.println("within click: "+object);
			
			//driver.findElement(getObject(object)).click();
			//System.out.println("click Object xpath: "+getObject(object));

			if (object.contains("prod_cat"))
			{
				
				//driver.findElement(getObject(object,Constants.item_selected)).click();
				int pos_no=Search_Spec_Item(object,testData);
				System.out.println("pos_no:"+pos_no);
				driver.findElement(getObject(object,pos_no)).click();
				
			}
			else if (object.contains("item_add"))
			{
				
				int pos_no=Search_Spec_Item("toolsqa~item_heading",testData);
				//hm_items.put(testData, pos_no);
				System.out.println("pos_no:"+pos_no);
				driver.findElement(getObject(object,pos_no)).click();
				
			}
			else if (object.contains("cart_item_update"))
			{
				
				int pos_no=Search_Spec_Item("toolsqa~cart_item_name",testData);
				driver.findElement(getObject(object,pos_no)).click();
				
			}
			else {
				
				driver.findElement(getObject(object)).click();
			
			}
			
		}catch(Exception e){
 			Log.error("Not able to click --- " + e.getMessage());
 			//System.out.println("Error while click: "+e.getMessage());
 			takeScreenShot(Constants.ScreenshotPath+"\\"+Constants.Snapshot_file);
 			Constants.Err_details=e.getMessage();
 			Driver_TestNG.bResult = false;
         	}
		
	}
	
	/*
	public void setfocus(String object,String testData) throws IOException{
		try{
			
			Log.info("Focusing on Webelement "+ object);
			
			System.out.println("within Setfocus: "+object);
			
			WebElement mainMenu= driver.findElement(getObject(object));
			new Actions(driver).moveToElement(mainMenu).perform();
			
			//System.out.println(getObject.pageObject[0]);
			if (object.equals("toolsqa~tab_prod_cat~element_prod_cat")) {
				
				//WebElement subMenu= driver.findElement(By.xpath("//li[@id='menu-item-33']/ul/li[3]"));
				WebElement subMenu= driver.findElement(By.xpath("//li[@id='menu-item-33']"+"/ul/li[3]"));
				new Actions(driver).moveToElement(subMenu).perform();
				
			}
			
			System.out.println("Setfoucus Object xpath: "+getObject(object));
			
		}catch(Exception e){
 			Log.error("Not able to Focus --- " + e.getMessage());
 			takeScreenShot(Constants.ScreenshotPath+"\\"+Constants.Snapshot_file);
 			Constants.Err_details=e.getMessage();
 			Driver_TestNG.bResult = false;
         	}
		
	}
	*/
	
	public void setfocus_sub(String object,String testData){
		try{
			
			Log.info("Focusing on Sub Webelement "+ object);
			
			System.out.println("within Setfocus_sub: "+object);
			
			String[] pageObject_focus = object.split("~");
			
			WebElement element= driver.findElement(getObject(pageObject_focus[0]+"~"+pageObject_focus[1]));
			new Actions(driver).moveToElement(element).perform();
			
			//Searching specific item from tab
			int prod_pos=Search_Spec_Item((pageObject_focus[0]+"~"+pageObject_focus[2]),testData);
			
			WebElement element2= driver.findElement(getObject((pageObject_focus[0]+"~"+pageObject_focus[2]),prod_pos));
			new Actions(driver).moveToElement(element2).perform();
			
			//System.out.println("Setfoucus elements xpath: "+getObject(object));
			
		}catch(Exception e){
 			Log.error("Not able to Focus --- " + e.getMessage());
 			//System.out.println("Error while click: "+e.getMessage());
 			Constants.Err_details=e.getMessage();
 			Driver_TestNG.bResult = false;
         	}
	}	
	
	public void Checkout_Price_Calc(String object,String testData) throws Exception
	{
		try {
			 
			Driver_TestNG.bResult =true;
			String cart_item_qty,cart_item_price, cart_item_total;
			float cart_price_calc,grand_total;
			
			
			String actualText="";
			
			grand_total=0;
			cart_price_calc=0;
			
			Log.info("Finalizing price:"+ object);
		    
			String grand_text=drawDigitsFromString(driver.findElement(getObject("toolsqa~grand_total_header")).getText());
			
		    //List<WebElement> rows=driver.findElements(By.xpath("//table[@class='checkout_cart']/tbody/tr"));
		    List<WebElement> list_item=driver.findElements(getObject(object));
		    System.out.println("No. of items "+(list_item.size()-1));
				
			 //List<WebElement> cols=driver.findElements(By.xpath("//table[@class='checkout_cart']/tbody/tr[2]/td"));
			 //System.out.println("No. of heading "+cols.size());
				
				for(int i=2;i<=list_item.size();i++)
				{
					
					//for(int j=1;j<cols.size();j++)
					//{
					
						cart_item_qty=driver.findElement(getObject("toolsqa~cart_item_qty",i)).getAttribute("value");
						cart_item_price=driver.findElement(getObject("toolsqa~cart_item_price",i)).getText();
						cart_item_total = driver.findElement(getObject("toolsqa~cart_item_total",i)).getText();
						
						System.out.println("Item Qty:"+cart_item_qty);
						System.out.println("Item Price:"+cart_item_price);
						System.out.println("Item Total:"+cart_item_total);
						
						actualText=drawDigitsFromString(cart_item_total);
						cart_price_calc=Float.parseFloat(drawDigitsFromString(cart_item_price))+Float.parseFloat(cart_item_qty);
					     
					     System.out.println("Final price from web extract:"+actualText);
					     System.out.println("Calculated price with 2f:"+String.format("%.2f", cart_price_calc));
					     Assert.assertTrue(actualText.equals(String.format("%.2f", cart_price_calc)));
						
					     grand_total=grand_total+cart_price_calc;
				
					//}
					//System.out.println();
				}
				
			     System.out.println("Grand Total from web extract:"+grand_text);
			     System.out.println("Calculated Grand Total with 2f:"+String.format("%.2f", grand_total));
			     Assert.assertTrue(grand_text.equals(String.format("%.2f", grand_total)));
		     
		}	catch(Exception  e){
			 Log.error("Not able to Assign price: --- " + e.getMessage());
			 Constants.Err_details=e.getMessage();
			 System.out.println("Error "+e.getMessage());
			 takeScreenShot(Constants.ScreenshotPath+"\\"+Constants.Snapshot_file);
			 Driver_TestNG.bResult = false;
       	}
		
	}	
	
	public void closeBrowser(String object,String testData){
		try{
			
			 Log.info("Closing the browser");
			 driver.quit();
			
		}catch(Exception e){
			 Log.error("Not able to Close the Browser --- " + e.getMessage());
			 Driver_TestNG.bResult = false;
        	}
		   
		}
	
	//public void takeScreenShot(WebDriver webdriver, String destFilePath) throws IOException
	public void takeScreenShot(String destFilePath) throws IOException
	{
		//TakesScreenshot scrShot = ((TakesScreenshot)webdriver);
		TakesScreenshot scrShot = ((TakesScreenshot)driver);
		File srcFile = scrShot.getScreenshotAs(OutputType.FILE);
		File destFile = new File(destFilePath);
		Files.copy(srcFile, destFile);
	}
	
	public int Search_Spec_Item(String object,String testData) throws Exception
	{
		int position_no=0;
		int start_pos=1;
		
		try {
			
			 Log.info("Searching Specific item"+ object);
			 List<WebElement> list_item = null;
			 
			 	
			 if (object.contains("prod_cat"))
			 {
				 list_item=driver.findElements(getObject("toolsqa~prod_cat_list"));
				 System.out.println("No. of products:"+list_item.size());
				 				 
			 }	 
			 else if (object.contains("item_heading"))
			 {
			    list_item=driver.findElements(getObject("toolsqa~items_displayed"));
			    System.out.println("No. of items displayed:"+list_item.size());
			    
			 }
			 else if (object.contains("cart_item_name"))
			 {
			    list_item=driver.findElements(getObject("toolsqa~items_cart"));
			    System.out.println("No. of items displayed in cart:"+list_item.size());
			    start_pos=2;
			 }
			
			 String actualText="";
					
			 
			 for(int i=start_pos;i<=list_item.size();i++)
			 { 
				 			 
				 actualText = driver.findElement(getObject(object,i)).getAttribute("text");
		 		     
				 //System.out.println("Actual text:"+actualText.length());
				 //System.out.println("Testdata text:"+testData.length());
				 				 
		         //if (actualText.equals(testData))
		         if (String_Compare_spl_char(actualText,testData))	 
		         {
		        	 
		        	 position_no=i;
		    	     System.out.println("Item Selected:"+actualText);
		    	     break;
		         }
	
			 }
		         
			 //return position_no;
			 
		}	catch(Exception  e){
			 Log.error("Not able to Validate text --- " + e.getMessage());
			 Constants.Err_details=e.getMessage();
			 takeScreenShot(Constants.ScreenshotPath+"\\"+Constants.Snapshot_file);
			 Driver_TestNG.bResult = false;
       	}
		return position_no;
		
	}
	
	
	//Separate words from String which has digits
	//https://stackoverflow.com/questions/14974033/extract-digits-from-string-stringutils-java
	    public String drawDigitsFromString(String strValue){
	        String str = strValue.trim();
	        String digits="";
	        for (int i = 0; i < str.length(); i++) {
	            char chrs = str.charAt(i);
	            //if (chrs=='.')
	            	//break;
	                 if (Character.isDigit(chrs) || chrs=='.')
	                       digits = digits+chrs;
	        }
	        return digits;
	    }
		
	    public boolean String_Compare_spl_char(String str1,String str2){
	        //String str = strValue.trim();
	        String spl_char="-";
	        boolean status=true;
	        for (int i = 0; i < str1.length(); i++) {
	            char chrs1 = str1.charAt(i);
	            char chrs2 = str2.charAt(i);
	           
	            if (chrs1!=chrs2)
	            {
	                 
	                if (chrs1=='-' || chrs2=='-') 
                    continue;
	                
	                status=false;
	                break;
	            }   
	            
	        }
	        
	        return status;
	    }
		

}
