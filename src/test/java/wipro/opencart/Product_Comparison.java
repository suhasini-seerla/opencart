package wipro.opencart;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

//Prod_Comparision Class extending method library
public class Product_Comparison extends method
{

	//Starting the extent report
	//Launching the application
	@Test(priority=1)
	public void start() throws Exception
	{
		logger = extent.startTest(" Product_Comparison Extent Report started");
		launch();
		System.out.println(" launch method completed");
	    logger.log(LogStatus.PASS, "launch method is pass");
	}
	
	//Calling the data provider from method class
	//All Data values are passed from excel to this method
	@Test(priority=2,dataProvider="ex_read_ord")
	public void prodcomp(String email,String pwd,String qty,String src_text,String coup) throws Exception 
	{
	//This method will allow already registered users to login into the application
    login(email,pwd);
	System.out.println(" login method completed");
	logger.log(LogStatus.PASS, "login method is pass");
    
	//This method searches for some text
	search(src_text);
	System.out.println(" search method completed");
	logger.log(LogStatus.PASS, "search method is pass");
    
	//This method campares 3 products
	Compare(qty);
	System.out.println(" Compare method completed");
	logger.log(LogStatus.PASS, "Compare method is pass");
    
	//This method navigates back
	navigateback();
	System.out.println(" navigateback method completed");
    logger.log(LogStatus.PASS, "navigate back method is pass");
    Thread.sleep(5000);
    
    //This method goes to the order history
    ordhistory();
    System.out.println(" ordhistory method completed");
    logger.log(LogStatus.PASS, "ordhistory method is pass");
    Thread.sleep(5000);
    
    //This method will logout from the application
    //Close the browser
	logout();
	System.out.println(" logout method completed");
    logger.log(LogStatus.PASS, "logout method is pass");
	}

	@Test(priority=3)
	public void end() throws Exception
	{
		//This Testcase is completed
		Thread.sleep(5000);
		System.out.println("Product_Comparison Testcase is passed");
		logger.log(LogStatus.PASS, "Product_Comparison Testcase is passed");
		//Logging of information into Extent report is ended 
		extent.endTest(logger);
		//Close the browser
		driver.quit();
	}
}
