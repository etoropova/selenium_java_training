package htoropova;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class LocationEditor extends htoropova.UHDTestBase {
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  private WebDriverWait wait;

  
  @BeforeMethod
  public void openLocationEditor(){
	  wait = new WebDriverWait(driver, 20);
	  driver.get("http://10.36.219.18/wiwauth/portal");
	  driver.findElement(By.xpath("//*[@id='applicationMenuContainer']/div[4]/div[2]/ul/li[5]/div/div")).click();
	  driver.switchTo().frame(driver.findElement(By.id("iContent")));
	  wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='locationSearch']")));
	
	  
  }
  
 @Test // Create new location in UHD (positive case - all mandatory fields are filled)
  public void createNewlocation_positiveCase() throws Exception {  
  
  wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='newLocationButton' or name='newLocationButton']"))).click();
  
  new Select(driver.findElement(By.id("location.country"))).selectByVisibleText("Germany (DE)");
  
  wait.until(ExpectedConditions.elementToBeClickable(By.id("location.postalcode"))).clear();
  driver.findElement(By.id("location.postalcode")).sendKeys("02961");

  wait.until(ExpectedConditions.elementToBeClickable(By.id("location.city"))).clear();
  driver.findElement(By.id("location.city")).sendKeys("AUTO_TEST_T");
  
  Random rand = new Random();
  wait.until(ExpectedConditions.elementToBeClickable(By.id("location.street"))).clear();
  driver.findElement(By.id("location.street")).sendKeys("AUTO_TEST_T" + rand.hashCode());
  wait.until(ExpectedConditions.elementToBeClickable(By.id("saveAndUpdateButton"))).click();
  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='indicator_success']/div[2]/div/div[1]/span")));  
  wait.until(ExpectedConditions.elementToBeClickable(By.id("backToSearch"))).click();
    
  } 

  @Test // Create new location in UHD (negative case - location.street mandatory field is missed)
  public void createNewLocation_negativeCase() throws Exception {    
 
  wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='newLocationButton' or name='newLocationButton']"))).click();
  
  new Select(driver.findElement(By.id("location.country"))).selectByVisibleText("Germany (DE)");
  
  wait.until(ExpectedConditions.elementToBeClickable(By.id("location.postalcode"))).clear();
  driver.findElement(By.id("location.postalcode")).sendKeys("02961");

  wait.until(ExpectedConditions.elementToBeClickable(By.id("location.city"))).clear();
  driver.findElement(By.id("location.city")).sendKeys("AUTO_TEST_T");
  
  wait.until(ExpectedConditions.elementToBeClickable(By.id("saveAndUpdateButton"))).click();
  
  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='indicator_failed']/div[2]/div/div[1]/span")));  
  wait.until(ExpectedConditions.elementToBeClickable(By.id("backToSearch"))).click();
    
  } 
  
  @Test // Update created location - remove "normalised names"
  public void updateLocation() throws Exception {    
	  
  new Select(driver.findElement(By.id("search_locationISO"))).selectByVisibleText("Germany (DE)");
  
  wait.until(elementToBeClickable(By.id("search_locationZIP"))).clear();
  driver.findElement(By.id("search_locationZIP")).sendKeys("02961");
  wait.until(elementToBeClickable(By.id("search_locationCity"))).clear();
  driver.findElement(By.id("search_locationCity")).sendKeys("AUTO_TEST_T*");
  
  wait.until(elementToBeClickable(By.id("searchButton"))).click();
  wait.until(visibilityOfElementLocated(By.xpath("//*[@id='locationSearchResult']/table")));
  wait.until(elementToBeClickable(By.xpath("//*[@id='locationSearchResult']/table[1]/tbody/tr[1]/td[6]/div//input"))).click();
    
  if(isElementPresent(By.id("adeleteNormalizedNameButton"))){

	  wait.until(elementToBeClickable(By.id("adeleteNormalizedNameButton"))).click();
	  wait.until(elementToBeClickable(By.id("saveAndUpdateButton"))).click();
	  
  } 
  else {
	  
	  wait.until(elementToBeClickable(By.name("addNormalizedName"))).click();
	  wait.until(elementToBeClickable(By.id("newPostalCode"))).clear();
	  driver.findElement(By.id("newPostalCode")).sendKeys("02961");
	  driver.findElement(By.id("newStreet")).clear();
	  driver.findElement(By.id("newStreet")).sendKeys("updateAUTO_test");
	  driver.findElement(By.id("doAddNormalizedNameButton")).click();
	  driver.findElement(By.id("saveAndUpdateButton")).click();
 
       }
  
  wait.until(visibilityOfElementLocated(By.xpath("//*[@id='indicator_success']/div[2]/div/div[1]/span")));
  wait.until(elementToBeClickable(By.id("backToSearch"))).click();
      
  }
  

  @Test // Search location by Country name (positive case)
  public void searchLocation_positiveCase() throws Exception {    
	  
  wait.until(elementToBeClickable(By.id("search_cID"))).clear();
  wait.until(elementToBeClickable(By.id("search_locationZIP"))).clear();
  wait.until(elementToBeClickable(By.id("search_locationCity"))).clear();
  wait.until(elementToBeClickable(By.id("search_locationStreet"))).clear();
  
  new Select(driver.findElement(By.id("search_locationISO"))).selectByVisibleText("Germany (DE)");
  wait.until(elementToBeClickable(By.id("searchButton"))).click();
  wait.until(visibilityOfElementLocated(By.xpath("//*[@id='locationSearchResult']/table")));
  
  List<WebElement> rows =driver.findElements(By.xpath("//*[@id='locationSearchResult']/table/tbody/tr"));
  int rCount = rows.size();
 // System.out.println("Number of rows in the table = "+rCount); 
  
  for (int i=1; i<=rCount; i++){
	  
	  String country_data=driver.findElement(By.xpath("//*[@id='locationSearchResult']/table/tbody/tr/td[2]")).getText();
	  Assert.assertEquals(country_data, "DE");
           
      }
  }

  
  @Test // Search location by invalid param (negative case) - check that CID is only numeric values
  public void searchLocation_negativeCase() throws Exception {    
	
  wait.until(elementToBeClickable(By.id("search_cID"))).clear();
  wait.until(elementToBeClickable(By.id("search_locationZIP"))).clear();
  wait.until(elementToBeClickable(By.id("search_locationCity"))).clear();
  wait.until(elementToBeClickable(By.id("search_locationStreet"))).clear();
  driver.findElement(By.id("search_cID")).sendKeys("CHAR");
 
  wait.until(elementToBeClickable(By.id("searchButton"))).click();
  wait.until(visibilityOfElementLocated(By.xpath("//*[@id='searchInputValidationText']")));

  }
   
   
  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }
 

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}