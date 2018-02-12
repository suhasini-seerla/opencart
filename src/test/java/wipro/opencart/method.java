package wipro.opencart;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

//This method class extending excel_read for fetching excel data
public class method extends excel_read 
{
	//Creating objects
	public static WebDriver driver;
	static Properties OR;
	public static ExtentReports extent;
	public static ExtentTest logger;
	public String chrome="D://chromedriver.exe";
	public static String testdata_path="D:\\Selenium\\wipro.opencart\\src\\test\\utils\\Test_data.xlsx";
	
	//Creates Chrome Driver and launches the Opencart application
	public void launch() throws Exception{
		// properties
		System.setProperty("webdriver.chrome.driver", chrome);
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("disable-infobars");
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        driver = new ChromeDriver(capabilities);
		/*Maximize the window*/
		driver.manage().window().maximize();
		
		//Laoding the objects from repository
		FileInputStream fs = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\ObjRepo.properties");
		OR= new Properties();
		OR.load(fs);
		driver.get(OR.getProperty("OpencartURL"));
		System.out.println("successfully opened opencart..........");
		
	}
	
	//Creating a Data Provider to read data from excel
	@DataProvider(name="ex_read_reg")
	 public static Object[][] dataReading() throws IOException 
	{
		excel_read readObject=new excel_read();
		//Providing the Test data filepath and sheetname
		Object[][] data1= readObject.ExcelData(testdata_path,"register");
		//System.out.println(data1);
		return data1;
	}
	
	//This method register a New user
	public static void Register1(String f_name,String l_name,String email,String ph,String add,String city,String pcode,String country,String zone,String pwd) throws Exception 
	{
		/*Register page...*/
		driver.findElement(By.xpath(OR.getProperty("register"))).click();
		Thread.sleep(3000);
		System.out.println("registering the account......");

		//Providing the User details
		driver.findElement(By.name("firstname")).sendKeys(f_name);
		driver.findElement(By.name("lastname")).sendKeys(l_name);
		driver.findElement(By.name("email")).sendKeys(email);
		driver.findElement(By.name("telephone")).sendKeys(ph);
		System.out.println("Personal details entered successfully...........");

		//Enter the address details
		System.out.println("Enter the address................");
		driver.findElement(By.name("address_1")).sendKeys(add);
		driver.findElement(By.name("address_2")).sendKeys(add);
		driver.findElement(By.name("city")).sendKeys(city);
		driver.findElement(By.name("postcode")).sendKeys(pcode);
		driver.findElement(By.name("country_id")).sendKeys(country);
		driver.findElement(By.name("zone_id")).sendKeys(zone);
		System.out.println("Address success fully entered............");	
		
		//Providing the password
		driver.findElement(By.name("password")).sendKeys(pwd);
		driver.findElement(By.name("confirm")).sendKeys(pwd);
		
		//Registering the User
		driver.findElement(By.name("agree")).click();
		driver.findElement(By.xpath(OR.getProperty("continue"))).click();
		System.out.println("your account has been created sucessfully...........");
		Thread.sleep(3000);
		}
	
	//This method checks for already registered users and login into the application
	public void login_exist(String email,String pwd) throws Exception 
	{
		String acc_create=driver.findElement(By.xpath(OR.getProperty("acc_create"))).getText();
		if(acc_create.equalsIgnoreCase("Your Account Has Been Created!"))
		{
			System.out.println("successfully logged in ....");
		}
		else 
		{
		//Verifying already registered user...
		WebElement warning = driver.findElement(By.xpath(OR.getProperty("warning")));
		String Warning_Msg= warning.getText();
		System.out.println(Warning_Msg);	
		Thread.sleep(2000);
		if(Warning_Msg.equalsIgnoreCase("Warning: E-Mail Address is already registered!"))
			{
				driver.findElement(By.xpath(OR.getProperty("loginuser"))).click();	
				System.out.println("log in page ....");
			}
		
		//Login to the page
		driver.findElement(By.name("email")).sendKeys(email);
		driver.findElement(By.name("password")).sendKeys(pwd);
		driver.findElement(By.xpath(OR.getProperty("login"))).click();
		System.out.println("successfully logged in ....");
		Thread.sleep(2000);
		}
	}
	
	//This method adds samsung product,adds reviews,add to wishlist.
	public void addtocartsamg(String review_name,String review_text,String rating) throws Exception
	{
		//Navigate to Samsung product
		driver.findElement(By.xpath(OR.getProperty("gotostore"))).click();
		System.out.println("successfully navigate to home.");
		driver.findElement(By.xpath(OR.getProperty("nivo"))).click();
		System.out.println("successfully clicked on Samsung tab image");
		
		//Navigate to Review tab
		Thread.sleep(3000);
		driver.findElement(By.xpath(OR.getProperty("review"))).click();
		System.out.println("Clicked Reveiw link");
		
		//Enter Reveiw name
		Thread.sleep(3000);
		driver.findElement(By.xpath(OR.getProperty("review_name"))).sendKeys(review_name);
		System.out.println("Entered Reveiw name..");	
		
		//Validating the review text
				Thread.sleep(3000);
				int r_len=review_text.length();
				if(r_len<25)
				{
				driver.findElement(By.xpath(OR.getProperty("review_text"))).sendKeys(review_text);
				System.out.println("Entered Reveiw Text is less than 25 chars..");
				Thread.sleep(3000);
				driver.findElement(By.xpath(OR.getProperty("review_continue"))).click();
				System.out.println("Clicked review continue");
				
				Thread.sleep(3000);
				String Warning_Msg= driver.findElement(By.xpath(OR.getProperty("warning"))).getText();
				if(Warning_Msg.equalsIgnoreCase("Warning: Review Text must be between 25 and 1000 characters!"))
					{
						System.out.println(Warning_Msg);
						Thread.sleep(3000); 
					}
				}
				else if (r_len>25)
				{
		          driver.findElement(By.xpath(OR.getProperty("review_text"))).sendKeys(review_text);
		          Thread.sleep(3000);
		  		  System.out.println("Entered Reveiw Text more than 25 chars..");
		        
		  		  Thread.sleep(3000);
		  		  driver.findElement(By.xpath(OR.getProperty("review_continue"))).click();
		  		  System.out.println("Clicked review continue");
				
		  		  Thread.sleep(3000);
		  		  String review_success= driver.findElement(By.xpath(OR.getProperty("review_success"))).getText();
		  		  Thread.sleep(3000);
		  		  if(review_success.equalsIgnoreCase("Thank you for your review. It has been submitted to the webmaster for approval."))
		  		  	{
		  			  	Thread.sleep(3000);
		  			  	System.out.println(review_success);
		  		  	}
				}
		
				
		//Check the required rating
		int rate=Integer.parseInt(rating);
		if (rate==1)
		{
		driver.findElement(By.xpath(OR.getProperty("rating1"))).click();
		System.out.println("Clicked Rating 1 link");
		}
		else if(rate==2)
		{
		driver.findElement(By.xpath(OR.getProperty("rating2"))).click();
		System.out.println("Clicked Rating 2 link");
		}
		else if(rate==3)
		{
		driver.findElement(By.xpath(OR.getProperty("rating3"))).click();
		System.out.println("Clicked Rating 3 link");
		}
		else if(rate==4)
		{
		driver.findElement(By.xpath(OR.getProperty("rating4"))).click();
		System.out.println("Clicked Rating 4 link");
		}
		else if(rate==5)
		{
		driver.findElement(By.xpath(OR.getProperty("rating5"))).click();
		System.out.println("Clicked Rating 5 link");
		}
		
		
		//To Enter Captcha Manually
        System.out.println("Please enter Captcha:");
        Thread.sleep(3000);
        Scanner scanner = new Scanner(System.in);
        String captcha = scanner.nextLine();
        Thread.sleep(3000);
        System.out.println("Entered captcha Text is.."+ captcha);
		driver.findElement(By.xpath(OR.getProperty("captcha"))).sendKeys(captcha);
		System.out.println("Entered captcha Text");
		Thread.sleep(3000);
		
		
		//Add to wishlist
		Thread.sleep(3000);
		driver.findElement(By.linkText("Add to Wish List")).click();
		System.out.println("you have added successfully galaxy samsung tab..........");
		Thread.sleep(3000);
		driver.findElement(By.id("wishlist-total")).click();
		Thread.sleep(3000);
		
		
		//Text file for writing
		FileWriter fr=new FileWriter(OR.getProperty("home_path")+"\\Flat_file\\Unit_price.txt");
		//buffer for the file
		BufferedWriter br=new BufferedWriter(fr);
		//write data into buffer
		
		//reading the Euro value
		Thread.sleep(3000);
		driver.findElement(By.linkText("€")).click();
		String S1=driver.findElement(By.xpath(OR.getProperty("unit"))).getText();
		System.out.println(S1);
		br.write(S1.toString());
		br.newLine();
		Thread.sleep(3000);
		
		//Reading the Pound value
		driver.findElement(By.linkText("£")).click();
		String S2=driver.findElement(By.xpath(OR.getProperty("unit"))).getText();
		System.out.println(S2);
		br.write(S2.toString());
		br.newLine();
		Thread.sleep(3000);
		
		//Reading the Dollar value
		driver.findElement(By.linkText("$")).click();
		String S3=driver.findElement(By.xpath(OR.getProperty("unit"))).getText();
		System.out.println(S3);
		br.write(S3.toString());
		br.newLine();
		Thread.sleep(3000);
		
		System.out.println("user able to click the currency.......");
		
		//Closing the file write
		br.close();
		
		//Add to cart,verify whether removed from wishlist
		Thread.sleep(2000);
		driver.findElement(By.xpath(OR.getProperty("addtocart1"))).click();
		System.out.println("User able to click Add to cart....");
		Thread.sleep(2000);
		driver.findElement(By.id("wishlist-total")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(OR.getProperty("wishlist"))).click();	
		Thread.sleep(2000);
		System.out.println("successfully Removed....");
		
		}
		
	
	//Adds Iphone to cart
	public void Addtocartiphone() throws Exception
	{
		///*********Add Iphone to cart**********/ 	 
		driver.findElement(By.xpath("//img[@alt='Your Store']")).click();
		driver.findElement(By.linkText("iPhone")).click();
		System.out.println("you selected iphone....");
		driver.findElement(By.xpath(OR.getProperty("tab"))).click();
		System.out.println("Related Products tab opened..........");
		driver.findElement(By.xpath(OR.getProperty("addtocart"))).click();
		System.out.println("Product added in AddToCart successfully.........");
		
		//opening related products
		Thread.sleep(3000);
		driver.findElement(By.linkText("Related Products (1)")).click();
		System.out.println("Related Products 1 tab opened..........");
		Thread.sleep(3000);
		System.out.println("user able to select product..");
		
		//Add to cart
		Thread.sleep(3000);
		driver.findElement(By.xpath(OR.getProperty("addtocart"))).click();
		System.out.println("user able to add the product in addtocart");
		}
	
	//Creating a Data Provider to read data from excel
			@DataProvider(name="ex_read_ord")
			 public static Object[][] dataReading1() throws IOException 
			{
				excel_read readObject=new excel_read();
				//Providing the Test data filepath and sheetname
				Object[][] data1= readObject.ExcelData(testdata_path,"other");
				//System.out.println(data1);
				return data1;
			}
			
			
		//This method will allow already registered users to login into the application
		public void login(String email,String pwd) throws Exception 
			{
				//Login to the page...
				Thread.sleep(3000);
				driver.findElement(By.xpath(OR.getProperty("loginuser"))).click();	
				System.out.println("log in page ....");
				driver.findElement(By.name("email")).sendKeys(email);
				driver.findElement(By.name("password")).sendKeys(pwd);
				driver.findElement(By.xpath(OR.getProperty("login"))).click();
				System.out.println("successfully logged in ....");
				Thread.sleep(3000);
			}
			
	//Add to shopping cart
	public void shoppingcart(String qty) throws Exception
	{
		/********************Shopping cart Page**********************/
		driver.findElement(By.xpath(OR.getProperty("shop_cart"))).click();
		System.out.println("user able to open shopping cart.......");

		//Change the Qty
		Thread.sleep(3000);
		driver.findElement(By.name("quantity[40::]")).clear();
		Thread.sleep(3000);
		driver.findElement(By.name("quantity[40::]")).sendKeys(qty);
		System.out.println("user able to change the quantity......");
			
		//Update the qty
		Thread.sleep(3000);
		driver.findElement(By.xpath(OR.getProperty("update"))).click();
		System.out.println("user able to update the quantity.........");
	}
	
	//Checkout the product
	public void checkout() throws Exception
	{
		//**********Checkout*******************/
		Thread.sleep(3000);
		driver.findElement(By.linkText("Checkout")).click();
		System.out.println("user able to checkout the products.....");
		
		//Billing section
		Thread.sleep(3000);
		driver.findElement(By.id("button-payment-address")).click();
		System.out.println("user able click continue button 2.......");
		Thread.sleep(3000);
		driver.findElement(By.id("button-shipping-address")).click();
		System.out.println("user able click continue button 3.......");
		Thread.sleep(3000);
		driver.findElement(By.id("button-shipping-method")).click();
		System.out.println("user able click continue button 4.......");
		
		//Agree the terms
		Thread.sleep(3000);
		driver.findElement(By.name("agree")).click();
		System.out.println("User able to click the Terms and conditions...........");
		Thread.sleep(3000);
		driver.findElement(By.id("button-payment-method")).click();
		System.out.println("User able to click the continue button...........");
		}
	
	//Confirm and Order is placed
	public void confirmorder() throws Exception
	{
		//*************Confirm Order*****************/
		Thread.sleep(3000);
		driver.findElement(By.xpath(OR.getProperty("confirmorder"))).click();
		System.out.println("user able to do confirm order.........");
		System.out.println("Your Order Has Been Processed!...........");
		
		//Navigate to Order History
		Thread.sleep(3000);
		driver.findElement(By.linkText("Order History")).click();
		System.out.println("user able to openOrder History successfully..");
		Thread.sleep(3000);
		driver.findElement(By.xpath(OR.getProperty("orderinfo"))).click();
		System.out.println("Order Information page id Page id displayed.... ");
		Thread.sleep(3000);
		driver.findElement(By.xpath(OR.getProperty("return"))).click();
		System.out.println("Product return tab shoud be displayed.... ");
		
		//Navigate to Home page
		Thread.sleep(3000);
		driver.findElement(By.xpath(OR.getProperty("homepage"))).click();
		System.out.println("Home Page Should be displayed..");
		}
	
	//Searches Text
	public void search(String src_text) throws Exception
	{
		//Enter the value in search box
		Thread.sleep(3000);
		driver.findElement(By.xpath(OR.getProperty("search"))).sendKeys(src_text);
		Thread.sleep(3000);
		driver.findElement(By.xpath(OR.getProperty("search"))).sendKeys(Keys.ENTER);
		Thread.sleep(3000);
		driver.findElement(By.xpath(OR.getProperty("monitor"))).click();
		driver.findElement(By.xpath(OR.getProperty("subcate"))).click();
		driver.findElement(By.xpath(OR.getProperty("search1"))).click();
		Thread.sleep(3000);
		
		
		//Click on the Product Compare link
		driver.findElement(By.xpath(OR.getProperty("product_comp2"))).click();
		  
		//Select the value
		Select dropDownSortBy=new Select(driver.findElement(By.xpath(OR.getProperty("sort"))));
		dropDownSortBy.selectByVisibleText("Price (High > Low)");
			  
		//creating the list
		List<WebElement> listCompare = driver.findElements(By.xpath(OR.getProperty("compare")));
		for(WebElement c :listCompare)
			{
				System.out.println("inside for each " + c.getText());
				Thread.sleep(3000);
				c.click();
			}
		}
	
	//Compare the products
	public void Compare(String qty) throws Exception
	{
			//User is printing the output
			System.out.println("Click on Add to Compare  for the first 3 phones and click on Close button");
			Thread.sleep(3000);
			
			//User is clicking on the Product comparison link.
			driver.findElement(By.xpath(OR.getProperty("prod_compare"))).click();
			System.out.println("User is able to click on the product comparision link");
			
			//User is able to click on the Palm Treo Pro link.
			driver.findElement(By.linkText("Palm Treo Pro")).click();
			System.out.println("User is able to click on the first product");
			
			//file for writing Description
			FileWriter fr=new FileWriter(OR.getProperty("home_path")+"\\Flat_file\\description.txt");
			//buffer for the file
			BufferedWriter br=new BufferedWriter(fr);
			//write data into buffer
			
			WebElement ul = driver.findElement(By.xpath(OR.getProperty("description")));
			System.out.println("User is able to go to description");
			
			//Reading the 5th feature and write into text file
			Thread.sleep(3000);
			String lis =driver.findElement(By.xpath(OR.getProperty("list_5"))).getText();
			Thread.sleep(3000);
			System.out.println(lis);
			br.write(lis);
		    br.close();
			Thread.sleep(3000);
	   
			//User is able to click on the add to cart button.
			driver.findElement(By.id("button-cart")).click();
			System.out.println("User is able to click on the add to cart link");
			Thread.sleep(3000);
			
			//User will click on the shopping cart link.
	   		driver.findElement(By.xpath(OR.getProperty("shop_cart"))).click();
	   		System.out.println("user is able to click on the shopping cart link");
	   
			//Clear the quantity field
			driver.findElement(By.xpath(OR.getProperty("quantity"))).clear();
			System.out.println("User is able to clear the quantity");
	   
	   
			//enter the quantity in the field
			driver.findElement(By.xpath(OR.getProperty("quantity"))).sendKeys(qty);
	   		System.out.println("User should be able to enter the quantity value");
	   
			//Click on the CheckOut button
			driver.findElement(By.xpath(OR.getProperty("checkout"))).click();
			System.out.println("User is able to click on the checkout button");
			Thread.sleep(3000);
	   
			//Click on the continue button present in the Billing Details pane
			driver.findElement(By.xpath(OR.getProperty("continue_billing"))).click();
			System.out.println("User is able to click on the continue billing button");
	   		Thread.sleep(3000);
	   		
			//Click on the continue button present in the Step 3: Delivery Details pane
			driver.findElement(By.xpath(OR.getProperty("continue_ship"))).click();
			System.out.println("User is able to click on the continue shipping button");
			Thread.sleep(3000);
			
			//Click on the continue button present in the Step 4: Delivery Method pane
			driver.findElement(By.xpath(OR.getProperty("continue_delivery"))).click();
			System.out.println("User is able to click on the continue delivery button");
			Thread.sleep(3000);
	   
			//Click on the check box for terms and conditions
			driver.findElement(By.xpath(OR.getProperty("terms"))).click();
	   
			//Click on Continue payment
			driver.findElement(By.xpath(OR.getProperty("continue_pay"))).click();
			System.out.println("User is able to click on the continue payment button");
			Thread.sleep(3000);
			
			//Confirm order
			driver.findElement(By.xpath(OR.getProperty("continue_confirm"))).click();
			Thread.sleep(3000);
}
	//Navigate back
	public void navigateback() throws Exception
	{
			//user is navigated back
			Thread.sleep(3000);
			driver.navigate().back();
			System.out.println("User is navigate back");
			
			//Verify for empty cart
			if (driver.findElement (By.linkText("0 item(s) - $0.00")) != null)
			{
				System.out.println("Value is zero in shopping cart");
			}
			else
			{
				System.out.println("Value is present");
			}
		}
	
	//Navigates to Order History
	public void ordhistory() throws Exception
	{
			//User is able to click on the order history link
			Thread.sleep(3000);
			driver.findElement(By.linkText("Order History")).click();
			System.out.println("user is able to click on the history link");
	   
			//This will click on the newsletter link
			Thread.sleep(3000);
			driver.findElement(By.xpath(OR.getProperty("newsletter"))).click();
			System.out.println("User is able to click on the newsletter link");
	   
	   
			//User will click on the Specials link in the Extras section
			Thread.sleep(3000);
			driver.findElement(By.xpath(OR.getProperty("specials"))).click();
			System.out.println("User is ablt to click on the Specials link");
	   
	   
			//Click on the Grid
			Thread.sleep(3000);
			driver.findElement(By.xpath(OR.getProperty("prod_filter"))).click();
			System.out.println("User is able to click on the grid option");
	}
	
	//Order phones
	public void ordphones(String coup) throws Exception
	{
		/*Home Page should be displayed..*/
		driver.findElement(By.linkText("Home")).click();
		System.out.println("Home page should be displayed....");
	
		//Navigate to Samsung
		driver.findElement(By.xpath(OR.getProperty("nivo"))).click();
		System.out.println("Samsung Galaxy Tab 10.1 page has to be displayed....");
		driver.findElement(By.id("image")).click();
		System.out.println("The picture should be displayed in modal window.....");
		Thread.sleep(3000);
	
		//Opens the image and navigate to each picture
		driver.findElement(By.id("cboxNext")).click();
		System.out.println("User able to click the next button....");	
		Thread.sleep(3000);
		driver.findElement(By.id("cboxNext")).click();
		System.out.println("User able to click the next button....");
		Thread.sleep(3000);
		driver.findElement(By.id("cboxNext")).click();
		System.out.println("User able to click the next button....");
		Thread.sleep(3000);
		driver.findElement(By.id("cboxNext")).click();
		System.out.println("User able to click the next button....");
		Thread.sleep(3000);
		driver.findElement(By.id("cboxNext")).click();
		System.out.println("User able to click the next button....");
		Thread.sleep(3000);
		driver.findElement(By.id("cboxNext")).click();
		System.out.println("User able to click the next button....");
		
		//Close the image
		Thread.sleep(3000);
		driver.findElement(By.xpath(OR.getProperty("chkbox_close"))).click();
		System.out.println("User able to click the next button....");
	
		//Add to cart
		Thread.sleep(3000);
		driver.findElement(By.xpath(OR.getProperty("addtocart"))).click();
		System.out.println("You have added the phone must be displayed.....");
		Thread.sleep(3000);
		driver.findElement(By.linkText("Shopping Cart")).click();
		System.out.println("Shopping cart page is displayed with all the products added to the cart...");
		
		//Shipping estimate
		Thread.sleep(3000);
		driver.findElement(By.id("shipping_estimate")).click();
		System.out.println("Estimate shipping for the region , " + "state and pin code is displayed along with 'Get Quotes' button");
	
		//Get Quotes
		Thread.sleep(3000);
		driver.findElement(By.id("button-quote")).click();
		System.out.println("User able to clik the Get Quotes button...");
		Thread.sleep(3000);
		
		//Flat rate and apply shipping
		driver.findElement(By.xpath(OR.getProperty("flat_rate"))).click();
		System.out.println("User able to select the flate Shipping Rate....");
		Thread.sleep(3000);
		driver.findElement(By.xpath(OR.getProperty("apply_ship"))).click();
		System.out.println("Success message is displayed on the page and the total amount is displayed...");
		
		//Apply Coupon
		Thread.sleep(3000);
		driver.findElement(By.id("use_coupon")).click();
		System.out.println("Use Coupon tab is displayed.....");
		Thread.sleep(3000);
		driver.findElement(By.xpath(OR.getProperty("coupon"))).sendKeys(coup);
		driver.findElement(By.xpath(OR.getProperty("apply_coupon"))).click();
		System.out.println("Warning Ribbon Message is displayed on the page......");
	
		//Checkout
		Thread.sleep(3000);
		driver.findElement(By.linkText("Checkout")).click();
		System.out.println("Check out page is displayed...........");
	
		//Select the address
		Thread.sleep(3000);
		driver.findElement(By.xpath(OR.getProperty("address"))).click();
		System.out.println("User able to select the address.........");
		Thread.sleep(3000);
		
		//Billing Details
		System.out.println("Billing details....");
		Thread.sleep(3000);
		driver.findElement(By.xpath(OR.getProperty("continue_billing"))).click();
		System.out.println("Delivery Method continue_billing rollup is displayed.");
		Thread.sleep(3000);
		driver.findElement(By.xpath(OR.getProperty("continue_ship"))).click();
		System.out.println("Delivery Method continue_ship rollup is displayed.");
		Thread.sleep(3000);
		driver.findElement(By.name("comment")).sendKeys("Sample order placed");
		Thread.sleep(3000);
		driver.findElement(By.xpath(OR.getProperty("continue_delivery"))).click();
		System.out.println("delivery details....");
	
		//payment method
		Thread.sleep(3000);
		driver.findElement(By.xpath(OR.getProperty("chkbox"))).click();
		System.out.println("user able to click the checkbox....");
		Thread.sleep(3000);
	
		//Terms and Conditions
		driver.findElement(By.linkText("Terms & Conditions")).click();
		Thread.sleep(3000);
		String conditions =driver.findElement(By.xpath(OR.getProperty("terms_conditions"))).getText();
		Thread.sleep(3000);
	
		//file for writing
		FileWriter fr=new FileWriter(OR.getProperty("home_path")+"\\Flat_file\\Terms.txt");
		//buffer for the file
		BufferedWriter br=new BufferedWriter(fr);
		//write data into buffer
		System.out.println(conditions);
		System.out.println(conditions.length());
		br.write(conditions);
		br.newLine();
		br.write("The Length of the String is : ");
		br.write(new Integer(conditions.length()).toString());
		br.close();
		Thread.sleep(3000);
		driver.findElement(By.xpath(OR.getProperty("chkbox_close"))).click();
		System.out.println("Checkbox closed.............");
	
		//Payment
		Thread.sleep(3000);
		driver.findElement(By.xpath(OR.getProperty("continue_pay"))).click();
		System.out.println("Payment method.............");
	
		//Confirm order
		Thread.sleep(3000);
		driver.findElement(By.xpath(OR.getProperty("confirmorder"))).click();
		System.out.println("Your order has been processed...........");
	}
	
	//Log out from the application and close the browser
	public void logout() throws Exception
	{
		Thread.sleep(3000);
		driver.findElement(By.xpath(OR.getProperty("logout"))).click();
		System.out.println("successfully Logout....");
		Thread.sleep(3000);
	}
	
	@BeforeSuite
	public void startReport()
	{
		//filepath - path of the file
		//replaceExisting - Setting to overwrite (TRUE) the existing file or append to it
		//True (default): the file will be replaced
		//False: existing data will remain, new tests will be appended to the existing report.
		extent = new ExtentReports (System.getProperty("user.dir") +"/OpencartReport.html", true);
		extent
		.addSystemInfo("Host Name", "SU399862")
		.addSystemInfo("Environment", "Automation Testing")
		.addSystemInfo("User Name", "Suhasini");
		//extent.loadConfig(new File(System.getProperty("user.dir")+"\\extent-config.xml"));
		
	}
	
	@AfterSuite
	public void endReport()
	{
		// writing everything to document
		//flush() - to write or update test information to your report. 
		extent.flush();
		//close() - To close all the operation
		extent.close();
	}
	
	
}

