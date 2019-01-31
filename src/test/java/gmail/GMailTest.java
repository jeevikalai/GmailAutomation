package gmail;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public class GMailTest extends TestBase {
	public GMailTest() throws IOException {
		super(); 



	}


	public   ExtentReports extent;
	public ExtentTest extentTest; 
	
	
	@BeforeTest
	public void setExtent(){
		System.out.println("beforeTest");
		extent = new ExtentReports(System.getProperty("user.dir")+"/test-output/ExtentReport.html", true);
		extent.addSystemInfo("Host Name", "Jeevitha windows");
		extent.addSystemInfo("User Name", "jeevikalai");
		extent.addSystemInfo("Environment", "QA");
		
	}
	
	@AfterTest
	public void endReport(){
		System.out.println("afterTest");
		extent.flush();
		extent.close();
	}
	
	public static String getScreenshot(WebDriver driver, String screenshotName) throws IOException{
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		// after execution, you could see a folder "FailedTestsScreenshots"
		// under src folder
	//	System.out.println(System.getProperty("user.dir"));
	 	String destination = "c:\\Users\\jeevi\\seleniumproject\\GmailAutomation\\test-output\\testSendEmail.png";
		File finalDestination = new File("c:\\Users\\jeevi\\seleniumproject\\GmailAutomation\\tst-output\\testSendEmail.png"); 
		FileUtils.copyFile(source, finalDestination);
	
		return destination;
	}
	@AfterMethod
	public void tearDown(ITestResult result) throws IOException{
		
		System.out.println("aftermethod");
		if(result.getStatus()==ITestResult.FAILURE){
			extentTest.log(LogStatus.FAIL, "TEST CASE FAILED IS "+result.getName()); //to add name in extent report
			extentTest.log(LogStatus.FAIL, "TEST CASE FAILED IS "+result.getThrowable()); //to add error/exception in extent report
			
			String screenshotPath = GMailTest.getScreenshot(driver, result.getName());
			extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(screenshotPath)); //to add screenshot in extent report
			//extentTest.log(LogStatus.FAIL, extentTest.addScreencast(screenshotPath)); //to add screencast/video in extent report
		}
		else if(result.getStatus()==ITestResult.SKIP){
			extentTest.log(LogStatus.SKIP, "Test Case SKIPPED IS " + result.getName());
		}
		else if(result.getStatus()==ITestResult.SUCCESS){
			System.out.println(result.getName());
			extentTest.log(LogStatus.PASS, "Test Case PASSED IS " + result.getName());

		}
		
		
		extent.endTest(extentTest); //ending test and ends the current test and prepare to create html report
		driver.quit();
	}
	
	
	
    @Test
    public void testSendEmail() throws Exception {
    	TestBase.initialization();
    	extentTest = extent.startTest("SendmailTest");
    	WebDriverWait wait=new WebDriverWait(driver, 20);
        driver.get("https://mail.google.com/");
       
        
        WebElement userElement = driver.findElement(By.id("identifierId"));
        userElement.sendKeys(properties.getProperty("username"));

        driver.findElement(By.id("identifierNext")).click();

        Thread.sleep(1000);

        WebElement passwordElement = driver.findElement(By.name("password"));
        System.out.println("password " +properties.getProperty("password"));
        passwordElement.sendKeys(properties.getProperty("password"));
        driver.findElement(By.id("passwordNext")).click();

        Thread.sleep(1000);

        WebElement composeElement = driver.findElement(By.xpath("//div[@role = 'button' and text()='Compose']"));
        composeElement.click();

        driver.findElement(By.name("to")).clear();
        driver.findElement(By.name("t")).sendKeys(String.format("%s@gmail.com", properties.getProperty("username")));
        String emailSubject = properties.getProperty("email.subject");

        driver.findElement(By.xpath("//input[@name='subjectbox']")).sendKeys(emailSubject);
        String emailBody = properties.getProperty("email.body"); 
 
        driver.findElement(By.xpath("//div[@role='textbox' and @aria-label = 'Message Body']")).sendKeys(emailBody);
        Thread.sleep(2000);
  //      driver.switchTo().frame(driver.findElement(By.xpath("//div[@role='button' and @aria-label='More options']")));
        driver.findElement(By.xpath("//div[@role='button' and @aria-label='More options']")).click();

        Actions a = new Actions(driver);
    //   a.moveToElement(driver.findElement(By.xpath("//div[@role='button' and @aria-label = 'More Options']"))).click().build().perform();
         a.moveToElement(driver.findElement(By.xpath("//div[@class='J-N-Jz' and text()='Label']"))).click().build().perform();
         a.moveToElement(driver.findElement(By.xpath("//div[@role = 'menuitemcheckbox']/div[text()='Social']"))).click().build().perform();
         driver.findElement(By.xpath("//*[@role='button' and text()='Send']")).click();
         Thread.sleep(5000);
    //     driver.switchTo().frame("hist_frame");
        driver.findElement(By.xpath("//div[@class='aio aip']/span/a[@title='Social']")).click();
        List<WebElement> inboxEmails = driver.findElements(By.xpath("//div[@class='ae4 UI']/div/div/table/tbody/tr"));
        System.out.println("inbox " +inboxEmails.size());
		Boolean labelsocial = true;

        for(int i = 1 ; i<=inboxEmails.size();i++)
        {        	
        	WebElement email = driver.findElement(By.xpath("//div[@class='ae4 UI']/div/div/table/tbody/tr["+i+"]"));
        	if( email.getText().contains(emailSubject)){ 
        		labelsocial = true;
            	System.out.println("Email subject  " + emailSubject);
            	driver.findElement(By.xpath("//div[@class='ae4 UI']/div/div/table/tbody/tr["+i+"]/td[3]")).click();
            	email.click();
            	String actualsubject = driver.findElement(By.xpath("//div[@class='nH']//div/h2[@class='hP']")).getText();
            	System.out.println("actualsubject " +actualsubject);
            	Assert.assertEquals(actualsubject, emailSubject);
            	Thread.sleep(3000);

            	String actualbody = driver.findElement(By.xpath("//div[@class='gs']/div[3]/div/div/div[1]")).getText();
            	System.out.println("actualbody " +actualbody);
            	Assert.assertEquals(actualbody, emailBody);

            	break;
        	}
        	else {
        		labelsocial=false;
        	}
         }
        if( labelsocial)
        	//System.out.println("email received under Label social");
        	Assert.assertTrue(labelsocial,"email received under Label social");
        else
        	Assert.assertFalse(labelsocial, "email not received under Label social");

 
        
        
           
    
    }
}
        
     // emailSubject and emailbody to be used in this unit test.
         
        
    

