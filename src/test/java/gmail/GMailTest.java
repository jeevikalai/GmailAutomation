package gmail;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import junit.framework.TestCase;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class GMailTest extends TestBase {
     
    public GMailTest() throws IOException {
		 
    } 
    @Test
    public void testSendEmail() throws Exception {
    	TestBase.initialization();
    	WebDriverWait wait=new WebDriverWait(driver, 20);
        driver.get("https://mail.google.com/");
       
        
        WebElement userElement = driver.findElement(By.id("identifierId"));
        userElement.sendKeys(properties.getProperty("username"));

        driver.findElement(By.id("identifierNext")).click();

        Thread.sleep(1000);

        WebElement passwordElement = driver.findElement(By.name("password"));
        passwordElement.sendKeys(properties.getProperty("password"));
        driver.findElement(By.id("passwordNext")).click();

        Thread.sleep(1000);

        WebElement composeElement = driver.findElement(By.xpath("//div[@role = 'button' and text()='Compose']"));
        composeElement.click();

        driver.findElement(By.name("to")).clear();
        driver.findElement(By.name("to")).sendKeys(String.format("%s@gmail.com", properties.getProperty("username")));
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
         
        
    

