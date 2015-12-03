package htoropova.pages;

import htoropova.applogic.ApplicationManager;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LocationSearchPage extends Page {

	//private static final String LOCATION_EDITOR_URL = "http://10.36.219.18/wiwauth/uhdloc";
	private static final Logger log = Logger.getLogger("LocationSearchPage");

	public LocationSearchPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	 @FindBy(id = "search_cID")
	  private WebElement fLocation_CID; 
	 
	 @FindBy(id = "search_locationZIP")
	  private WebElement fLocationZIP;
	 
	 @FindBy(id = "search_locationCity")
	  private WebElement fLocationCity;
	 
	 @FindBy(id = "search_locationStreet")
	  private WebElement fLocationStreet;
	 
	 @FindBy(id = "search_locationISO") 
	  private WebElement fLocationISO;
	 
	@FindBy(xpath = "//*[@id='newLocationButton' or name='newLocationButton']")
	 private WebElement btnNewLocationButton;
	 
	 @FindBy(id="searchButton")
	 private WebElement btnSearch;
	 
	 @FindBy(xpath="//*[@id='locationSearchResult']/table[1]/tbody/tr[1]/td[6]/div//input")
	 private WebElement btnEditLocation;
	 
	 @FindBy(xpath="//*[@id='locationSearchResult']/table")
	 private WebElement tableResult;
	 
	 @FindBy(xpath="//*[@id='locationSearchResult']/table/tbody/tr")
	 private List<WebElement> tableRows;
	 	 
	 @FindBy(xpath="//*[@id='locationSearchResult']/table/tbody/tr/td[2]")
	 private WebElement tableColumnCountry;
	 @FindBy(id="searchInputValidationText")
	 private WebElement faildMessage;
	 

	 public LocationSearchPage openLocationSearchPage(){
		 
		 ApplicationManager appManager = new ApplicationManager();
		 
		 driver.get(appManager.getUhdLocationUrl());
		 try {
			 log.info("Open LocationSearchPage");
			 wait.until(ExpectedConditions.presenceOfElementLocated(By.id("locationSearch")));
			 
		 } catch (Exception e) {
			
			log.error("LocationSearchPage open is failed:" + e.toString());
		 }
		
		 return this;
	  }
	 
	 public LocationEditPage openLocationEditPage(){
		 
		btnNewLocationButton.click();
		try {
			log.info("Open LocationEditPage...");
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("locationMasterData")));

		} catch (Exception e) {
			log.error("LocationEditPage open is failed:" + e.toString());
		}
		
		return new LocationEditPage(driver);
		  
	 }
	 
	public void searchLocations() {
		
		log.info("Click on <Search> button...");
		btnSearch.click();
					
		}
	
	public void searchLocation_byAttributes(String cid,String code, String iso, String city, String street){
		clearPageFields();
		new Select(fLocationISO).selectByVisibleText(iso);
		fLocation_CID.sendKeys(cid);
		fLocationZIP.sendKeys(code);
		fLocationCity.sendKeys(city);
		fLocationStreet.sendKeys(street);
		log.info("Search location by Attributes:" + "CID:"+ cid + " Code:"+ code + " ISO:"+iso+" City:"+city+" Street:"+ street);
			
	}

	public boolean checkTableResult(String iso_code) {
			
		if (tableResult.isDisplayed()){	
				
			int rCount = tableRows.size();
		// System.out.println("Number of rows in the table = "+rCount); 
			for (int i=1; i<=rCount;i++){
			//String country_data=driver.findElement(By.xpath("//*[@id='locationSearchResult']/table/tbody/tr/td[2]")).getText();
			if (tableColumnCountry.getText().equals(iso_code)) {
				return true;
			} else { 
				return false; 
				}				
			}		
		}
			return true;
		
	}

		public boolean checkErrorMessage() {
			//wait.until(visibilityOfElementLocated(By.xpath("//*[@id='searchInputValidationText']")));
				if (faildMessage.isDisplayed()) {
					return true;
				}
			return true;			
		}

		public void clearPageFields() {
			fLocation_CID.clear();
			fLocationZIP.clear();
			fLocationCity.clear();
			fLocationStreet.clear();			
		}

		
		public LocationEditPage goToEditLocationPage() {
		
			try {
				
				log.info("Click on <Edit> button...");
				btnEditLocation.click();
					
				} catch (Exception e) {
				
				log.info("Click on <Edit> button..." + e.toString());
				btnEditLocation.click();
		
			    }
			return new LocationEditPage(driver);
		
		}
}

	

	  

	

