package gmail;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestBase {
	public static WebDriver driver;
	public static Properties properties;
 

	public TestBase() throws IOException {
		properties = new Properties();
		FileInputStream fis = new FileInputStream("C:\\Users\\jeevi\\seleniumproject\\GmailAutomation\\src\\test\\java\\resources\\test.properties");
		properties.load(fis);
		 
	}
		
	public static void initialization()
	{
			 
			System.setProperty("webdriver.chromedriver", "webdriver.chrome.driver");
			driver = new ChromeDriver(); 
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
			
			
		 

		}

}
