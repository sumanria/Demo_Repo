package pages;

import org.openqa.selenium.By;

public class toolsqa {
	
	public String getObjectProperty(String objectName)
	{
		
		//System.out.println("Within ToolsQA page:"+objectName);
		
		//switch(objectName.toUpperCase())
		switch(objectName)
		{ 
			case "btn_MyAccount":
				//System.out.println("Within ToolsQA page:"+objectName);
				return "xpath=//a[@title='My Account']";
				
			case "btn_LogOut":
				return "xpath=.//*[@id='account_logout']";
				
			case "txtbx_UserName":
				return "xpath=//input[@id='log']";
				
			case "txtbx_Password":
				return "xpath=//input[@id='pwd']";
				
			case "btn_LogIn":
				return "xpath=//input[@id='login']";
				
			//case "txtbx_Error":
			case "username_Error":	
				return "xpath=//p[text()=': The username field is empty.']";
			
			case "tab_prod_cat":	
				return "xpath=//li[@id='menu-item-33']";
				
			case "prod_cat_list":
				return "xpath=//li[@id='menu-item-33']/ul/li";		
				
			case "grid_view":
				return "xpath=//a[text()='Grid']";
				
			case "items_displayed":	
			    return "xpath=//div[@class='product_grid_display group']/div";
			    
			case "items_cart":	
			    return "xpath=//table[@class='checkout_cart']/tbody/tr";    
			
			case "checkout_header":	
				return "xpath=//div[@id='header_cart']";	
				
			case "grand_total_header":
				return "xpath=//span[@class='yourtotal']/span[@class='pricedisplay']";
				
		    default:	
				return "";
		}
		

	}
	
	public String getObjectProperty(String objectName,int item)
	{
		
		//System.out.println("Within ToolsQA page:"+objectName);
		
		//switch(objectName.toUpperCase())
		switch(objectName)
		{ 	
		
		    case "prod_cat":
			   return "xpath=//li[@id='menu-item-33']/ul/li["+item+"]/a";
		
			case  "item_heading":	
				return "xpath=//div[@class='product_grid_display group']/div["+item+"]/div[2]/h2/a";
			
			case  "item_add":	
				return "xpath=//div[@class='product_grid_display group']/div["+item+"]/div[3]";
				
			case "cart_item_name":	
				//if (item==1)
					//return "xpath=//table[@class='checkout_cart']/tbody/tr["+item+"]/th[1]";
				
				return "xpath=//table[@class='checkout_cart']/tbody/tr["+item+"]/td[2]/a";
				
			case "cart_item_qty":	
				return "xpath=//table[@class='checkout_cart']/tbody/tr["+item+"]/td[3]/form/input[1]";
				              	
			case "cart_item_update":	
				return "xpath=//table[@class='checkout_cart']/tbody/tr["+item+"]/td[3]/form/input[4]";	
			
			case "cart_item_price":	
				return "xpath=//table[@class='checkout_cart']/tbody/tr["+item+"]/td[4]";
			
			case "cart_item_total":	
				return "xpath=//table[@class='checkout_cart']/tbody/tr["+item+"]/td[5]";
				
				
		    default:	
				return "";
		}
		

	}
	
}
