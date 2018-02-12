package wipro.opencart;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

//Reg Class extending method library
public class RegistrationAndAddToCart extends method{
	
	//Starting the extent report
	//Launching the application
	@Test(priority=1)
	public void start() throws Exception
	{
		logger = extent.startTest("RegistrationAndAddToCart Extent Report started");
		launch();
		System.out.println(" launch method completed");
	    logger.log(LogStatus.PASS, "launch method is pass");
	}
	
	
	//Calling the data provider from method class
	//All Data values are passed from excel to this method
	@Test(priority=2,dataProvider="ex_read_reg")
	public void Register_addtocart(String f_name,String l_name,String email,String ph,String add,String city,String pcode,String country,String zone,String pwd,String review_name,String review_text,String rating) throws Exception
	{
	//This method will register the new user into the application
	Register1(f_name,l_name,email,ph,add,city,pcode,country,zone,pwd);
    System.out.println(" Register method completed");
    logger.log(LogStatus.PASS, "Register method is pass");
    
    //This method checks for already registered users and login into the application
	login_exist(email,pwd);
	System.out.println(" login method completed");
	logger.log(LogStatus.PASS, "login method is pass");
    
	//This method adds samsung product,adds reviews,add to wishlist.
	addtocartsamg(review_name,review_text,rating);
    System.out.println(" addtocartsamg method completed");
    logger.log(LogStatus.PASS, "addtocartsamg method is pass");
    
	
    //This method will logout from the application
    logout();
	System.out.println(" logout method completed");
    logger.log(LogStatus.PASS, "logout method is pass");
	Thread.sleep(5000);
}
	
	
	@Test(priority=3)
	public void end() throws Exception
	{
		//This Testcase is completed
		System.out.println("RegistrationAndAddToCart Testcase is passed");
		logger.log(LogStatus.PASS, "RegistrationAndAddToCart Testcase is passed");
		logger.log(LogStatus.PASS, "RegistrationAndAddToCart ended");
		Thread.sleep(5000);
		//Logging of information into Extent report is ended 
		extent.endTest(logger);
		//Close the browser
		driver.quit();
	}
		
	}
	

