package htoropova.applogic;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;



public class WebDriverFactory {
	
	private static WebDriver webDriver = null;
	private static final String FIREFOX_PLUGIN = "src/main/resources/modify_headers-0.7.1.2b-fx.xpi";
	private static final String tWrIdOfUser = "290653";
	private static final String tCIDofUser = "290652";
	
	private WebDriverFactory() {
	    }

	public static WebDriver getInstance(final String browser) {

	if (webDriver != null) {

		return webDriver;
	}

	webDriver = configureFirefox();
	
	return webDriver;
 	}

private static WebDriver configureFirefox() {

	
    DesiredCapabilities capabilities = getCapabilitiesForFirefox();

    WebDriver driver = new FirefoxDriver(capabilities);

    driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    driver.manage().window().maximize();

    return driver;
}

private static DesiredCapabilities getCapabilitiesForFirefox() {
	FirefoxProfile profile = new FirefoxProfile();
	//profile.setEnableNativeEvents(false);
	
	File file = new File(FIREFOX_PLUGIN);
	//File file = new File("C:\\AddOn");
	try {
		profile.addExtension(file);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	profile.setPreference("extensions.modify_headers.currentVersion", "0.7.1.1-fx");
	profile.setPreference("modifyheaders.config.alwaysOn", true);
            
	profile.setPreference("modifyheaders.headers.count", 2);
	profile.setPreference("modifyheaders.headers.action0", "Add");
	profile.setPreference("modifyheaders.headers.name0", "tcid");
	profile.setPreference("modifyheaders.headers.value0", tCIDofUser);
	profile.setPreference("modifyheaders.headers.enabled0", true);
	profile.setPreference("modifyheaders.config.active", true);

	profile.setPreference("modifyheaders.headers.action1", "Add");
	profile.setPreference("modifyheaders.headers.name1", "twrid");
	profile.setPreference("modifyheaders.headers.value1", tWrIdOfUser);
	profile.setPreference("modifyheaders.headers.enabled1", true);
	profile.setPreference("modifyheaders.config.active", true);
	profile.setPreference("modifyheaders.options.headers.save", true);
	profile.setPreference("modifyheaders.config.general.uncheck.onclose", true);
	profile.setPreference("signon.rememberSignons", false);

	DesiredCapabilities capabilities = DesiredCapabilities.firefox();
    capabilities.setBrowserName("firefox");
    capabilities.setPlatform(Platform.ANY);
    capabilities.setCapability(FirefoxDriver.PROFILE, profile);

    return capabilities;
}


}
