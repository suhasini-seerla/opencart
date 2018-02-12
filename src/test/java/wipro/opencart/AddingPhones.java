package wipro.opencart;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

//Adding phones Class extending method library
public class AddingPhones extends method{

	//Starting the extent report
	//Launching the application
	@Test(priority=1)
	public void start() throws Exception
	{
		logger = extent.startTest("AddingPhones Report started");
		launch();
		System.out.println(" launch method completed");
	    logger.log(LogStatus.PASS, "launch method is pass");
	}
	
	//Calling the data provider from method class
	//All Data values are passed from excel to this method
	@Test(priority=2,dataProvider="ex_read_ord")
	public void Addphones(String email,String pwd,String qty,String src_text,String coup) throws Exception 
	{
		//This method will allow already registered users to login into the application
		login(email,pwd);
		System.out.println(" login method completed");
	    logger.log(LogStatus.PASS, "login method is pass");
	    
	    //This method Order phones and apply coupon,flat shipping
		ordphones(coup);
		System.out.println(" ordphones method completed");
	    logger.log(LogStatus.PASS, "ordphones method is pass");
	    
	    //This method will logout from the application
	    //Close the browser
		logout();
		System.out.println(" logout method completed");
	    logger.log(LogStatus.PASS, "logout method is pass");
	    
		Thread.sleep(5000);
		//This Testcase is completed
		System.out.println("AddingPhones Testcase is passed");
		logger.log(LogStatus.PASS, "AddingPhones Testcase is passed");
	}
	
	@Test(priority=3)
	public void end() throws Exception
	{
		Thread.sleep(5000);
		//This Testcase is completed
		System.out.println("AddingPhones Testcase is passed");
		logger.log(LogStatus.PASS, "AddingPhones Testcase is passed");
		//Logging of information into Extent report is ended 
		extent.endTest(logger);
		//Close the browser
		driver.quit();
	}

}
