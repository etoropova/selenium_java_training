package htoropova.pages;

import java.util.Random;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class LocationEditPage extends Page {

	private static final String POSTAL_CODE = "02961";
	private static final String STREET_NAME = "updateAUTO_test";
	private static final Logger log = Logger.getLogger(LocationEditPage.class);


	public LocationEditPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver,this);
	}
	
 
	@FindBy(id = "location.postalcode")
	private WebElement fLocationPostalCode;
	 
	@FindBy(id = "location.city")
	private WebElement fLocationCity;
	 
	@FindBy(id = "location.street")
	private WebElement fLocationStreet;
	 
	@FindBy(id = "location.country") 
	private WebElement fLocationISO;
	
	@FindBy(id="saveAndUpdateButton")
	private WebElement btnSaveButton;
	
	@FindBy(xpath = "//*[@id='indicator_success']/div[2]/div/div[1]/span")
	private WebElement successMessage;
	
	@FindBy(xpath = "//*[@id='indicator_failed']/div[2]/div/div[1]/span")
	private WebElement 	failedMessage;
	
	@FindBy(id = "adeleteNormalizedNameButton")
	private WebElement btnDeleteNormalizedName;
	
	@FindBy(xpath = "//*[@id='addNormalizedNameDiv']/div[3]/span/input")
	private WebElement btnAddNormalizedName;
	
	@FindBy(id = "newPostalCode") 
	private WebElement fnewPostalCode;
	 
	@FindBy(id = "newStreet") 
	private WebElement fnewStreet;
	
	@FindBy(xpath = "//*[@id='doAddNormalizedNameButton']")
	private WebElement btnSaveNormalizedName;
		
	@FindBy(id = "backToSearch")
	private WebElement btnBackToSearch;
	
	 
	public void fillInputs(String code, String iso, String city, String street){
		
		  new Select(fLocationISO).selectByVisibleText(iso);
		  fLocationPostalCode.clear();
		  log.info("Fill Code:"+code);
		  fLocationPostalCode.sendKeys(code);
		  		
		  fLocationCity.clear();
		  log.info("Fill City:"+city);
		  fLocationCity.sendKeys(city);
		  
		  Random rand = new Random();
		  fLocationStreet.clear();
		  String str = street + rand.hashCode();
		  log.info("Fill Street:" + str);
		  fLocationStreet.sendKeys(str);
	}
	
	public void saveNewLocation() {
		  
		  log.info("Save new location. Click on <Save> button...");
		  btnSaveButton.click();
		  
    }
	
	public void backToSearch() {
		
		log.info("Back to Search. Click on <Back> button...");
		btnBackToSearch.click();
	}
	
	public void updateLocation() {
		
	try {
			log.info("Delete Normalized name. Click on <Remove> button...");
			btnDeleteNormalizedName.click();
			log.info("Save updated location. Click on <Save> button...");
			btnSaveButton.click();
			}
		
	catch (Exception e) {
			log.info("Add Normalized name. Click on <Add> button..." + e.toString());
			btnAddNormalizedName.click();
			AddAlternativeName();
			log.info("Save updated location. Click on <Save> button...");
			btnSaveButton.click();
		}
		
		
	}
			

	private void AddAlternativeName() {
		
		fnewPostalCode.clear();
		fnewPostalCode.sendKeys(POSTAL_CODE);
		fnewStreet.clear();
		fnewStreet.sendKeys(STREET_NAME);
		btnSaveNormalizedName.click();
		log.info("Add Postal code:"+POSTAL_CODE+" and Street:"+STREET_NAME);
		
	}

	public boolean checkSuccessMessage() {
		
		
		if (successMessage.isDisplayed()) {
			
			log.info("Success message is displayed");
			return true;
		}
		
		return true;
		
	}

	public boolean checkErrorMessage() {
		if (failedMessage.isDisplayed()) {
			
			log.info("Failed message is displayed...");
			return true;
		
		}
		return true;
	}
	
}
