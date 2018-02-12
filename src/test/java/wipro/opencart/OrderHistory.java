package wipro.opencart;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

//Orderhistory Class extending method library
public class OrderHistory extends method {
	
	//Starting the extent report
	//Launching the application
	@Test(priority=1)
	public void start() throws Exception
	{
		logger = extent.startTest("OrderHistory Report started");
		launch();
		System.out.println(" launch method completed");
	    logger.log(LogStatus.PASS, "launch method is pass");
	}
	
	//Calling the data provider from method class
	//All Data values are passed from excel to this method
	@Test(priority=2,dataProvider="ex_read_ord")
	public void Orderhistory(String email,String pwd,String qty,String src_text,String coup) throws Exception 
	{
		//This method will allow already registered users to login into the application
		login(email,pwd);
		System.out.println(" login method completed");
	    logger.log(LogStatus.PASS, "login method is pass");
	    Thread.sleep(5000);
	    
	    //This method adds iphone to cart
		Addtocartiphone();
		System.out.println(" Addtocartiphone method completed");
	    logger.log(LogStatus.PASS, "Addtocartiphone method is pass");
	    Thread.sleep(5000);
	    
	    //This method adds to shopping cart and modify the Qty
		shoppingcart(qty);
		System.out.println(" shoppingcart method completed");
	    logger.log(LogStatus.PASS, "shoppingcart method is pass");
	    Thread.sleep(5000);
	    
	    //This method checkouts the product
		checkout();
		System.out.println(" checkout method completed");
	    logger.log(LogStatus.PASS, "checkout method is pass");
	    Thread.sleep(5000);
	    
	    //This method confirms and order is placed
		confirmorder();	
		System.out.println(" confirmorder method completed");
	    logger.log(LogStatus.PASS, "confirmorder method is pass");
	    
	    //This method will logout from the application
	    //Close the browser
	    Thread.sleep(5000);
		logout();
		System.out.println(" logout method completed");
	    logger.log(LogStatus.PASS, "logout method is pass");
		Thread.sleep(5000);
		}
	
	@Test(priority=3)
	public void end() throws Exception
	{
		//This Testcase is completed
		System.out.println("OrderHistory Testcase is passed");
		logger.log(LogStatus.PASS, "OrderHistory Testcase is passed");
		logger.log(LogStatus.PASS, "OrderHistory ended");
		Thread.sleep(5000);
		//Logging of information into Extent report is ended
		extent.endTest(logger);
		//Close the browser
		driver.quit();
	}
}


