package htoropova;

import org.testng.Assert;
import org.testng.annotations.Test;

import htoropova.pages.LocationEditPage;
import htoropova.pages.LocationSearchPage;

public class LocationEditorTest extends UHDTestBase  {
	

	private static final String INVALID_CID = "CHAR";
	private static final String POSTAL_CODE = "02961";
	private static final String CITY_NAME = "AUTO_TEST_T";
	private static final String STREET_NAME = "AUTO_TEST_T";
	private static final String COUNTRY_ISO = "Germany (DE)";
	private static final String ISO_CODE = "DE";
	

	
	@Test(priority=0)// Update created location - remove "normalised names"
	public void scenario_updateLocation() throws Exception {
		LocationSearchPage current=new LocationSearchPage(driver);
		current.openLocationSearchPage();
		current.searchLocation_byAttributes("", POSTAL_CODE, COUNTRY_ISO, CITY_NAME, "");
		current.searchLocations();
		LocationEditPage editPage = current.goToEditLocationPage();
		editPage.updateLocation();
		Assert.assertTrue(editPage.checkSuccessMessage());
		editPage.backToSearch();
	}
	

	@Test(priority=1)
	
	public void scenario_search_location_byISOCountry() throws Exception {
		LocationSearchPage current=new LocationSearchPage(driver);
		current.openLocationSearchPage();
		current.searchLocation_byAttributes("", POSTAL_CODE, COUNTRY_ISO, CITY_NAME, "");
		current.searchLocations();
		Assert.assertTrue(current.checkTableResult(ISO_CODE));
	}
	
	@Test(priority=2)
	public void scenario_search_location_byCID_invalidCase() throws Exception {
		LocationSearchPage current=new LocationSearchPage(driver);
		current.openLocationSearchPage();
		current.searchLocation_byAttributes(INVALID_CID, "", COUNTRY_ISO, "", "");
		current.searchLocations();
		Assert.assertTrue(current.checkErrorMessage());
	}
	

	@Test(priority=3)
	public void scenario_create_new_location_positiveCase() throws Exception {
		LocationSearchPage current = new LocationSearchPage(driver);		
		LocationEditPage editPage =  current.openLocationEditPage();
		editPage.fillInputs(POSTAL_CODE, COUNTRY_ISO, CITY_NAME, STREET_NAME);
			
		editPage.saveNewLocation();
		Assert.assertTrue(editPage.checkSuccessMessage());
		editPage.backToSearch();
	}
	
	
	@Test(priority=4)
	public void scenario_create_new_location_negativeCase() throws Exception {
		LocationSearchPage current = new LocationSearchPage(driver);	
		current.openLocationSearchPage();
		LocationEditPage editPage = current.openLocationEditPage();
		editPage.fillInputs(POSTAL_CODE, COUNTRY_ISO, CITY_NAME, "");
		editPage.saveNewLocation();
		Assert.assertTrue(editPage.checkErrorMessage());
		editPage.backToSearch();
		
	}
		
}
