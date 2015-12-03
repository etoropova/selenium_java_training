package htoropova;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import htoropova.util.PropertyLoader;


public class UHDTestBase  {

	protected WebDriver driver;

	@BeforeClass
	public void init() {
		driver = htoropova.applogic.WebDriverFactory.getInstance(PropertyLoader.loadProperty("browser.name"));   	    
	}

	@AfterSuite
	public void tearDown() {
		driver.quit();
    }
	
}