package htoropova.applogic;

import htoropova.util.PropertyLoader;

public class ApplicationManager {
	
	private String uhdLocationUrl;
	
	public ApplicationManager() {
		
		uhdLocationUrl = PropertyLoader.loadProperty("uhd.location.url");
				
	    }
	
	public String getUhdLocationUrl() {
		
		return uhdLocationUrl;
	    
	    }
}
